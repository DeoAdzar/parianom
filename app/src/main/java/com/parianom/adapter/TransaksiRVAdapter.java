package com.parianom.adapter;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
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
import com.parianom.activity.DetailTransaksi;
import com.parianom.activity.GenerateQR;
import com.parianom.activity.ScanQr;
import com.parianom.activity.Transaksi;
import com.parianom.api.BaseApiService;
import com.parianom.api.UtilsApi;
import com.parianom.model.PenjualanModel;
import com.parianom.model.TransaksiModel;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final TransaksiModel tr = mData.get(position);
        holder.namaTransaksi.setText(tr.getKode_pesanan());
        holder.subNamaTransaksi.setText(tr.getNama());
        holder.pembeli.setText(tr.getNama_lengkap());
        holder.jumlah.setText(String.valueOf(tr.getJumlah()));
        holder.hargaTransaksi.setText(String.valueOf(tr.getTotal()));
        holder.id_penjual.setText(String.valueOf(tr.getId_penjual()));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        String convertedDate = null;
        try {
            date = dateFormat.parse(tr.getTimestamp());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM yyyy");
            convertedDate = simpleDateFormat.format(date);
            holder.wktTransaksi.setText(convertedDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Picasso.get().load(Uri.parse(UtilsApi.IMAGES_PRODUK + tr.getFoto_produk()))
                .placeholder(R.color.shimmer).into(holder.imgTransaksi);
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
                break;
        }
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView namaTransaksi, subNamaTransaksi, pembeli, jumlah, hargaTransaksi, wktTransaksi,id_penjual;
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
            id_penjual = itemView.findViewById(R.id.id_penjual_transaksi);
            card = itemView.findViewById(R.id.cardTransaksi);
        }
    }

}
