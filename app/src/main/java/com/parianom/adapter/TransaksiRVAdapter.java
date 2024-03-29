package com.parianom.adapter;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
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
import com.parianom.activity.Transaksi;
import com.parianom.api.BaseApiService;
import com.parianom.api.UtilsApi;
import com.parianom.model.TransaksiModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransaksiRVAdapter extends RecyclerView.Adapter<TransaksiRVAdapter.MyViewHolder> {

    Context mContext;
    List<TransaksiModel> mData;

    public TransaksiRVAdapter(Context mContext, List<TransaksiModel> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

//    cobaa

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transaksi, parent, false);
        MyViewHolder vHolder = new MyViewHolder(v);

        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final TransaksiModel tr = mData.get(position);
        holder.namaTransaksi.setText("#" + tr.getKode_pesanan());
        holder.subNamaTransaksi.setText(tr.getNama());
        holder.pembeli.setText(tr.getNama_lengkap());
        holder.jumlah.setText(String.valueOf(tr.getJumlah()));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        String convertedDate = null;
        holder.hargaTransaksi.setText(formatRupiah(Double.parseDouble(String.valueOf(tr.getTotal()))));
        try {
            date = dateFormat.parse(tr.getTimestamp());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM yyyy");
            convertedDate = simpleDateFormat.format(date);
            holder.wktTransaksi.setText(convertedDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        byte[] decodedString = Base64.decode(tr.getFoto_produk(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        holder.imgTransaksi.setImageBitmap(decodedByte);
        switch (String.valueOf(tr.getStatus())) {
            case "1":
                holder.selesai.setVisibility(View.GONE);
                holder.batalkan.setVisibility(View.GONE);
                break;
            case "0":
                holder.selesai.setVisibility(View.GONE);
                holder.batalkan.setVisibility(View.GONE);
                holder.card.setBackground(ContextCompat.getDrawable(mContext, R.drawable.card_riwayat_disable));
                break;
            case "null":
                holder.selesai.setVisibility(View.VISIBLE);
                holder.batalkan.setVisibility(View.VISIBLE);
                holder.selesai.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder dialog = new AlertDialog.Builder(view.getContext());

                        dialog.setTitle("Transaksi Selesai");
                        dialog
                                .setMessage("Yakin ingin menyelesaikan transaksi?")
                                .setCancelable(false)
                                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        BaseApiService mApiService = UtilsApi.getApiService();
                                        Call<ResponseBody> cek = mApiService.selesai(tr.getId());
                                        cek.enqueue(new Callback<ResponseBody>() {
                                            @Override
                                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                                if (response.isSuccessful()) {
                                                    try {
                                                        JSONObject jsonResult = new JSONObject(response.body().string());
                                                        if (jsonResult.getString("message").equals("success")) {
                                                            Toast.makeText(mContext, "Berhasil konfirmasi transaksi", Toast.LENGTH_SHORT).show();
                                                            Intent i = new Intent(mContext, Transaksi.class);
                                                            i.putExtra("id_penjual",tr.getId_penjual());
                                                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                            mContext.startActivity(i);
                                                        } else {
                                                            String message = jsonResult.getString("message");
                                                            Log.d(TAG, "onResponseScan: " + tr.getKode_pesanan() + " " + tr.getId_penjual());
                                                            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                                                        }
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    } catch (IOException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<ResponseBody> call, Throwable t) {

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
                holder.batalkan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder dialog = new AlertDialog.Builder(view.getContext());

                        dialog.setTitle("Batalkan Transaksi");
                        dialog
                                .setMessage("Yakin ingin membatalkan transaksi?")
                                .setCancelable(false)
                                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        BaseApiService mApiService = UtilsApi.getApiService();
                                        Call<ResponseBody> cek = mApiService.cancel(tr.getId());
                                        cek.enqueue(new Callback<ResponseBody>() {
                                            @Override
                                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                                if (response.isSuccessful()) {
                                                    try {
                                                        JSONObject jsonResult = new JSONObject(response.body().string());
                                                        if (jsonResult.getString("message").equals("success")) {
                                                            Toast.makeText(mContext, "Berhasil membatalkan transaksi", Toast.LENGTH_SHORT).show();
                                                            Intent i = new Intent(mContext, Transaksi.class);
                                                            i.putExtra("id_penjual",tr.getId_penjual());
                                                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                            mContext.startActivity(i);
                                                        } else {
                                                            String message = jsonResult.getString("message");
                                                            Log.d(TAG, "onResponseScan: " + tr.getKode_pesanan() + " " + tr.getId_penjual());
                                                            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                                                        }
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    } catch (IOException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<ResponseBody> call, Throwable t) {

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
                break;
        }

    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView namaTransaksi, subNamaTransaksi, pembeli, jumlah, hargaTransaksi, wktTransaksi;
        private ImageView imgTransaksi;
        private Button selesai, batalkan;
        LinearLayout card;

        public MyViewHolder(View itemView) {
            super(itemView);

            namaTransaksi = (TextView) itemView.findViewById(R.id.titleTransaksi);
            subNamaTransaksi = (TextView) itemView.findViewById(R.id.namaTransaksi);
            pembeli = (TextView) itemView.findViewById(R.id.pembeli);
            jumlah = (TextView) itemView.findViewById(R.id.jumlahTransaksi);
            hargaTransaksi = (TextView) itemView.findViewById(R.id.hargaTotalTransaksi);
            wktTransaksi = (TextView) itemView.findViewById(R.id.waktuTransaksi);
            imgTransaksi = (ImageView) itemView.findViewById(R.id.imgTransaksi);
            selesai = (Button) itemView.findViewById(R.id.btnSelesaiTransaksi);
            batalkan = (Button) itemView.findViewById(R.id.btnBatalTransaksi);
            card = itemView.findViewById(R.id.cardTransaksi);
        }
    }

    private String formatRupiah(Double number){
        Locale localeID = new Locale("in", "ID");
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(localeID);
        return numberFormat.format(number);
    }
}
