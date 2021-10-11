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
import com.parianom.activity.DetailBarang;
import com.parianom.model.PenjualanModel;

import java.util.List;

public class BerandaRvAdapter extends RecyclerView.Adapter<BerandaRvAdapter.MyViewHolder> {

    Context mContext;
    List<PenjualanModel> mData;

    public BerandaRvAdapter(Context mContext, List<PenjualanModel> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.item_beranda, parent, false);
        MyViewHolder vHolder = new MyViewHolder(v);

        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder,int position) {
        holder.imgData.setImageResource(mData.get(position).getImgProduk());
        holder.namaData.setText(mData.get(position).getTitleProduk());
        holder.kecData.setText(mData.get(position).getKecPenjual());
        holder.hargaData.setText(mData.get(position).getHargaProduk());

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DetailBarang.class);
                mContext.startActivity(intent);
//                DrawableCompat.setTint(holder.imgSubmenu.getDrawable(), ContextCompat.getColor(mContext, R.color.buttonClicked));
//              Fragment Tujuan
//                KriyaFragment fr = new KriyaFragment();
//                //Fragment Asal
//                AppCompatActivity appCompatActivity = (AppCompatActivity) view.getContext();
//                appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.submenuContainer, fr).commit();

//                if (position == 1) {
//                    //Fragment Tujuan
//                    KriyaFragment fr = new KriyaFragment();
//                    //Fragment Asal
//                    AppCompatActivity appCompatActivity = (AppCompatActivity) view.getContext();
//                    appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.submenuContainer, fr).commit();
//                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgData;
        private TextView namaData, hargaData, kecData, penjual;
        private LinearLayout layout;

        public MyViewHolder(View itemView) {
            super(itemView);

            imgData = (ImageView) itemView.findViewById(R.id.imgPr);
            namaData = (TextView) itemView.findViewById(R.id.jdlPr);
            kecData = (TextView) itemView.findViewById(R.id.kecPr);
            hargaData = (TextView) itemView.findViewById(R.id.hargaPr);
            penjual = (TextView) itemView.findViewById(R.id.namaPenjual);
            layout = (LinearLayout) itemView.findViewById(R.id.pilihData);
        }
    }
}
