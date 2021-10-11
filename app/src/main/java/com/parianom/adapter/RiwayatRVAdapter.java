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
import com.parianom.activity.DetailTransaksi;
import com.parianom.model.PenjualanModel;

import java.util.List;

public class RiwayatRVAdapter extends RecyclerView.Adapter<RiwayatRVAdapter.MyViewHolder> {

    Context mContext;
    List<PenjualanModel> mData;

    public RiwayatRVAdapter(Context mContext, List<PenjualanModel> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.item_riwayat, parent, false);
        MyViewHolder vHolder = new MyViewHolder(v);

        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final PenjualanModel penjualanModel = mData.get(position);

        holder.titleProduk.setText(mData.get(position).getTitleProduk());
        holder.wktBeli.setText(mData.get(position).getWaktuBelanja());
        holder.titleRwyt.setText(mData.get(position).getTitleProduk());
        holder.kecProduk.setText(mData.get(position).getKecPenjual());
        holder.hargaProduk.setText(mData.get(position).getHargaProduk());
        holder.imgProduk.setImageResource(mData.get(position).getImgProduk());

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DetailTransaksi.class);
                intent.putExtra("waktuBelanja", penjualanModel.getWaktuBelanja());
                intent.putExtra("titlePembelian", penjualanModel.getTitleProduk());
                intent.putExtra("totalBelanja", penjualanModel.getHargaProduk());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView titleProduk, wktBeli, titleRwyt, kecProduk, hargaProduk;
        private ImageView imgProduk;
        private LinearLayout layout;

        public MyViewHolder(View itemView) {
            super(itemView);

            titleProduk = (TextView) itemView.findViewById(R.id.namaProduk);
            wktBeli = (TextView) itemView.findViewById(R.id.waktuBelanja);
            titleRwyt = (TextView) itemView.findViewById(R.id.subNamaProduk);
            kecProduk = (TextView) itemView.findViewById(R.id.kecProduk);
            hargaProduk = (TextView) itemView.findViewById(R.id.hargaProduk);
            imgProduk = (ImageView) itemView.findViewById(R.id.imgProduk);
            layout = (LinearLayout) itemView.findViewById(R.id.cardRiwayat);
        }
    }
}
