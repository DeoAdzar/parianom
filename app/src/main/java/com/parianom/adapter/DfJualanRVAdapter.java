package com.parianom.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.parianom.activity.EditProduk;
import com.parianom.R;
import com.parianom.api.UtilsApi;
import com.parianom.model.DaftarJualanModel;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
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

        holder.namaPr.setText(dfJualanModel.getNama_toko());
        holder.titlePr.setText(dfJualanModel.getNama());
        holder.hargaPr.setText(dfJualanModel.getHarga());
        holder.kategori.setText(dfJualanModel.getKategori());
        holder.jenis.setText(dfJualanModel.getKategori_sub());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        String convertedDate = null;
        try {
            date = dateFormat.parse(dfJualanModel.getTimestamp());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM yyyy");
            convertedDate = simpleDateFormat.format(date);
            holder.tanggal.setText(convertedDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Picasso.get().load(Uri.parse(UtilsApi.IMAGES_PRODUK+dfJualanModel.getFoto_produk()))
                .placeholder(R.color.shimmer).into(holder.img);

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, EditProduk.class);
                intent.putExtra("id_produk", String.valueOf(dfJualanModel.getId()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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
