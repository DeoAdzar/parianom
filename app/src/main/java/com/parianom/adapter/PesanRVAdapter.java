package com.parianom.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.parianom.R;
import com.parianom.activity.DetailBarang;
import com.parianom.model.PesanModel;

import java.util.List;

public class PesanRVAdapter extends RecyclerView.Adapter<PesanRVAdapter.MyViewHolder> {

    Context mContext;
    List<PesanModel> mData;

    public PesanRVAdapter(Context mContext, List<PesanModel> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.item_pesan, parent, false);
        MyViewHolder vHolder = new MyViewHolder(v);

        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final PesanModel pesanModel = mData.get(position);

        holder.titlePsn.setText(mData.get(position).getTitlePesan());
        holder.isiPsn.setText(mData.get(position).getIsiPesan());
        holder.imgPsn.setImageResource(mData.get(position).getImgPesan());

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DetailBarang.class);
//                intent.putExtra("waktuBelanja", pesanModel.get());
//                intent.putExtra("titlePembelian", riwayatModel.getTitleProduk());
//                intent.putExtra("totalBelanja", riwayatModel.getHargaProduk());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView titlePsn, isiPsn;
        private ImageView imgPsn;
        private LinearLayout layout;

        public MyViewHolder(View itemView) {
            super(itemView);

            titlePsn = (TextView) itemView.findViewById(R.id.titlePesan);
            isiPsn = (TextView) itemView.findViewById(R.id.isiPesan);
            imgPsn = (ImageView) itemView.findViewById(R.id.imgPesan);
            layout = (LinearLayout) itemView.findViewById(R.id.btnDetailPesan);
        }
    }
}
