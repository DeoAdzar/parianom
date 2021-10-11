package com.parianom.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.parianom.R;
import com.parianom.activity.TambahProduk;
import com.parianom.model.DaftarJualanModel;

import java.util.List;

public class DfJualanRVAdapter extends RecyclerView.Adapter<DfJualanRVAdapter.MyViewHolder> {

    Context mContext;
    List<DaftarJualanModel> mData;

    public DfJualanRVAdapter(Context mContext, List<DaftarJualanModel> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_daftar_jual, parent, false);
        MyViewHolder vHolder = new MyViewHolder(v);

        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final DaftarJualanModel dfJualanModel = mData.get(position);

        holder.namaPr.setText(mData.get(position).getNamaProdukJual());
        holder.titlePr.setText(mData.get(position).getNamaProdukJual());
        holder.hargaPr.setText(mData.get(position).getHargaProdukJual());
        holder.kategori.setText(mData.get(position).getKategoriProdukJual());
        holder.jenis.setText(mData.get(position).getJenisProdukJual());
        holder.tanggal.setText(mData.get(position).getTglProdukJual());
        holder.img.setImageResource(mData.get(position).getImgProdukJual());

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, TambahProduk.class);
                intent.putExtra("namaPr", dfJualanModel.getNamaProdukJual());
                intent.putExtra("imgPr", dfJualanModel.getImgProdukJual());
                intent.putExtra("hargaPr", dfJualanModel.getHargaProdukJual());
                intent.putExtra("kategori", dfJualanModel.getKategoriProdukJual());
                intent.putExtra("jenis", dfJualanModel.getJenisProdukJual());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView namaPr, hargaPr, titlePr, kategori, jenis, tanggal;
        private ImageView img;
        private Button edit, hapus;

        public MyViewHolder(View itemView) {
            super(itemView);

            namaPr = (TextView) itemView.findViewById(R.id.namaPrDfJual);
            titlePr = (TextView) itemView.findViewById(R.id.titlePrDfJual);
            hargaPr = (TextView) itemView.findViewById(R.id.hargaPrDfJual);
            kategori = (TextView) itemView.findViewById(R.id.kategoriDfJual);
            jenis = (TextView) itemView.findViewById(R.id.jenisDfJual);
            tanggal = (TextView) itemView.findViewById(R.id.wktDfJual);
            img = (ImageView) itemView.findViewById(R.id.imgDfJual);
            edit = (Button) itemView.findViewById(R.id.btnEditDfJual);
            hapus = (Button) itemView.findViewById(R.id.btnHapusDfJual);
        }
    }
}
