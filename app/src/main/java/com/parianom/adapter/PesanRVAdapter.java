package com.parianom.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.parianom.R;
import com.parianom.model.PesanModel;

import java.util.List;
import java.util.zip.Inflater;

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
        holder.titlePsn.setText(mData.get(position).getTitlePesan());
        holder.isiPsn.setText(mData.get(position).getIsiPesan());
        holder.imgPsn.setImageResource(mData.get(position).getImgPesan());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView titlePsn, isiPsn;
        private ImageView imgPsn;

        public MyViewHolder(View itemView) {
            super(itemView);

            titlePsn = (TextView) itemView.findViewById(R.id.titlePesan);
            isiPsn = (TextView) itemView.findViewById(R.id.isiPesan);
            imgPsn = (ImageView) itemView.findViewById(R.id.imgPesan);


        }
    }
}
