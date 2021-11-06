package com.parianom.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.parianom.R;
import com.parianom.activity.DetailTransaksi;
import com.parianom.activity.GenerateQR;
import com.parianom.api.UtilsApi;
import com.parianom.model.PenjualanModel;
import com.parianom.model.PesananModel;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RiwayatRVAdapter extends RecyclerView.Adapter<RiwayatRVAdapter.MyViewHolder> {

    Context mContext;
    List<PesananModel> mData;

    public RiwayatRVAdapter(Context mContext, List<PesananModel> mData) {
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
        PesananModel pm = mData.get(position);
        holder.titleProduk.setText(pm.getNama_toko());
        holder.titleRwyt.setText(pm.getNama());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        String convertedDate = null;
        try {
            date = dateFormat.parse(pm.getTimestamp());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM yyyy");
            convertedDate = simpleDateFormat.format(date);
            holder.wktBeli.setText(convertedDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.alamat.setText(pm.getAlamat());
        holder.totalHarga.setText(String.valueOf(pm.getTotal()));

        String harga = holder.totalHarga.getText().toString();
        String resultRupiah = formatRupiah(Double.parseDouble(harga));
        holder.totalHarga.setText(resultRupiah);
        byte[] decodedString = Base64.decode(pm.getFoto_produk(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        holder.imgProduk.setImageBitmap(decodedByte);
        switch (String.valueOf(pm.getStatus())) {
            case "1":
                holder.layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(mContext, DetailTransaksi.class);
                        i.putExtra("kode_pesanan", pm.getKode_pesanan());
                        mContext.startActivity(i);

                    }
                });
                holder.detail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(mContext, DetailTransaksi.class);
                        i.putExtra("kode_pesanan", pm.getKode_pesanan());
                        mContext.startActivity(i);
                    }
                });
                break;
            case "0":
                holder.layout.setBackground(ContextCompat.getDrawable(mContext, R.drawable.card_riwayat_disable));
                holder.layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(mContext, "Pesanan Anda telah dibatalkan", Toast.LENGTH_SHORT).show();
                    }
                });
                holder.detail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(mContext, "Pesanan Anda telah dibatalkan", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case "null":
                holder.detail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(mContext, GenerateQR.class);
                        i.putExtra("kode_pesanan", pm.getKode_pesanan());
                        mContext.startActivity(i);
                    }
                });
                holder.layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(mContext, GenerateQR.class);
                        i.putExtra("kode_pesanan", pm.getKode_pesanan());
                        mContext.startActivity(i);

                    }
                });
                break;
        }
//        if (pm.getStatus()==1){
//            holder.layout.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                        Intent i = new Intent(mContext, DetailTransaksi.class);
//                        i.putExtra("kode_pesanan", pm.getKode_pesanan());
//                        mContext.startActivity(i);
//
//                }
//            });
//            holder.detail.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                        Intent i = new Intent(mContext, DetailTransaksi.class);
//                        i.putExtra("kode_pesanan", pm.getKode_pesanan());
//                        mContext.startActivity(i);
//                }
//            });
//        }else if (pm.getStatus()==0){
//            holder.layout.setBackground(ContextCompat.getDrawable(mContext,R.drawable.card_riwayat_disable));
//            holder.layout.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Toast.makeText(mContext, "Pesanan Anda telah dibatalkan", Toast.LENGTH_SHORT).show();
//                }
//            });
//            holder.detail.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Toast.makeText(mContext, "Pesanan Anda telah dibatalkan", Toast.LENGTH_SHORT).show();
//                }
//            });
//
//        }else{
//            holder.detail.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent i = new Intent(mContext, GenerateQR.class);
//                    i.putExtra("kode_pesanan", pm.getKode_pesanan());
//                    mContext.startActivity(i);
//                }
//            });
//            holder.layout.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent i = new Intent(mContext, GenerateQR.class);
//                    i.putExtra("kode_pesanan", pm.getKode_pesanan());
//                    mContext.startActivity(i);
//
//                }
//            });
//        }

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView titleProduk, wktBeli, titleRwyt, alamat, totalHarga;
        private ImageView imgProduk;
        private LinearLayout layout;
        private Button detail;
        public MyViewHolder(View itemView) {
            super(itemView);
            titleProduk = (TextView) itemView.findViewById(R.id.namaProduk);
            wktBeli = (TextView) itemView.findViewById(R.id.waktuBelanja);
            titleRwyt = (TextView) itemView.findViewById(R.id.subNamaProduk);
            alamat = (TextView) itemView.findViewById(R.id.alamatProduk);
            totalHarga = (TextView) itemView.findViewById(R.id.totalHarga);
            imgProduk = (ImageView) itemView.findViewById(R.id.imgProduk);
            layout = (LinearLayout) itemView.findViewById(R.id.cardRiwayat);
            detail = (Button) itemView.findViewById(R.id.btnDetailRiwayat);
        }
    }

    private String formatRupiah(Double number){
        Locale localeID = new Locale("in", "ID");
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(localeID);
        return numberFormat.format(number);
    }
}
