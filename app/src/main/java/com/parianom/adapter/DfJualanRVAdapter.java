package com.parianom.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.parianom.activity.DaftarJualan;
import com.parianom.activity.EditProduk;
import com.parianom.R;
import com.parianom.api.BaseApiService;
import com.parianom.api.UtilsApi;
import com.parianom.model.DaftarJualanModel;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        byte[] decodedString = Base64.decode(dfJualanModel.getFoto_produk(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        holder.img.setImageBitmap(decodedByte);
        String harga = holder.hargaPr.getText().toString();
        String resultRupiah = formatRupiah(Double.parseDouble(harga));
        holder.hargaPr.setText(resultRupiah);

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, EditProduk.class);
                intent.putExtra("id_produk", String.valueOf(dfJualanModel.getId()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });
        holder.hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(view.getContext());
                dialog
                        .setTitle("Hapus Jualan")
                        .setMessage("Yakin ingin menghapus jualan?")
                        .setCancelable(false)
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                BaseApiService mApiService = UtilsApi.getApiService();
                                Call<ResponseBody> delete = mApiService.delete(dfJualanModel.getId());
                                delete.enqueue(new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                        Toast.makeText(mContext, "sukses", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(mContext, DaftarJualan.class);
                                        intent.putExtra("id_penjual", String.valueOf(dfJualanModel.getId_penjual()));
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        mContext.startActivity(intent);
                                    }

                                    @Override
                                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                                        Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        })
                        .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialog.setCancelable(true);
                            }
                        });
                AlertDialog alertDialog = dialog.create();
                alertDialog.show();
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
    private String formatRupiah(Double number){
        Locale localeID = new Locale("in", "ID");
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(localeID);
        return numberFormat.format(number);
    }
}
