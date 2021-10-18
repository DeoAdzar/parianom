package com.parianom.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.parianom.R;
import com.parianom.activity.DetailBarang;
import com.parianom.api.UtilsApi;
import com.parianom.model.PenjualanModel;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class PenjualanRvAdapter extends RecyclerView.Adapter<PenjualanRvAdapter.MyViewHolder> {

    private Context mContext;
    private List<PenjualanModel> penjualanModelList;

    public PenjualanRvAdapter(Context mContext, List<PenjualanModel> penjualanModelList) {
        this.mContext = mContext;
        this.penjualanModelList = penjualanModelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_beranda, parent, false);
        MyViewHolder vHolder = new MyViewHolder(v);

        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder,int position) {
        PenjualanModel PM = penjualanModelList.get(position);

//        holder.imgData.setImageURI(Uri.parse(UtilsApi.IMAGES_PROFIL+PM.getFoto_produk()));
        Picasso.get().load(UtilsApi.IMAGES_PRODUK + PM.getFoto_produk())
                .placeholder(R.drawable.ic_person)
                .into(holder.imgData);
        holder.namaData.setText(PM.getNama());
        holder.kecData.setText(PM.getKec());
        holder.hargaData.setText(String.valueOf(PM.getHarga()));
        String harga = holder.hargaData.getText().toString();
        String resultRupiah = formatRupiah(Double.parseDouble(harga));
        holder.hargaData.setText(resultRupiah);

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DetailBarang.class);
                intent.putExtra("id",String.valueOf(PM.getId()));
                intent.putExtra("id_penjual",String.valueOf(PM.getId_penjual()));
                intent.putExtra("foto_profil", PM.getFoto_produk());
                intent.putExtra("nama_produk", PM.getNama());
                intent.putExtra("nama", PM.getNama_toko());
                intent.putExtra("alamat", PM.getAlamat());
                intent.putExtra("stok", String.valueOf(PM.getStok()));
                intent.putExtra("harga_produk", String.valueOf(PM.getHarga()));
                mContext.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return penjualanModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgData;
        private TextView namaData, hargaData, kecData, penjual;
        private LinearLayout layout;

        public MyViewHolder(View itemView) {
            super(itemView);

            imgData = (ImageView) itemView.findViewById(R.id.imgPrBeranda);
            namaData = (TextView) itemView.findViewById(R.id.jdlPr);
            kecData = (TextView) itemView.findViewById(R.id.kecPr);
            hargaData = (TextView) itemView.findViewById(R.id.hargaPr);
            penjual = (TextView) itemView.findViewById(R.id.namaPenjual);
            layout = (LinearLayout) itemView.findViewById(R.id.pilihData);
        }
    }
    private String formatRupiah(Double number){
        Locale localeID = new Locale("in", "ID");
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(localeID);
        return numberFormat.format(number);
    }
}
