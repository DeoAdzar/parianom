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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.parianom.R;
import com.parianom.activity.Chat;
import com.parianom.activity.DetailBarang;
import com.parianom.api.UtilsApi;
import com.parianom.model.RoomModel;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PesanRVAdapter extends RecyclerView.Adapter<PesanRVAdapter.MyViewHolder> {

    Context mContext;
    List<RoomModel> mData;

    public PesanRVAdapter(Context mContext, List<RoomModel> mData) {
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
        final RoomModel rm = mData.get(position);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = null;
        String convertedDate = null;
        try {
            date = dateFormat.parse(rm.getTanggal());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy");
            convertedDate = simpleDateFormat.format(date);
            holder.tanggal.setText(convertedDate);
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.titlePsn.setText(rm.getNama_toko());
        holder.isiPsn.setText(rm.getAlamat());
        if (rm.getFoto_toko()!=null){
            byte[] decodedString = Base64.decode(rm.getFoto_toko(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            holder.imgPsn.setImageBitmap(decodedByte);

        }else {
            holder.imgPsn.setImageResource(R.drawable.ic_person);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, Chat.class);
                intent.putExtra("id_penjual",String.valueOf(rm.getId_penjual()));
                intent.putExtra("id_room",String.valueOf(rm.getId()));
                intent.putExtra("penjual", rm.getNama_toko());
                intent.putExtra("gambar_toko", rm.getFoto_toko());
                intent.putExtra("alamat", rm.getAlamat());
                intent.putExtra("status_chat","1");
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView titlePsn, isiPsn,tanggal;
        private ImageView imgPsn;
        private LinearLayout layout;

        public MyViewHolder(View itemView) {
            super(itemView);
            tanggal = itemView.findViewById(R.id.tgl_chat);
            titlePsn = (TextView) itemView.findViewById(R.id.titlePesan);
            isiPsn = (TextView) itemView.findViewById(R.id.isiPesan);
            imgPsn = (ImageView) itemView.findViewById(R.id.imgPesan);
        }
    }
}
