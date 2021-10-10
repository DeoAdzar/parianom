package com.parianom.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.parianom.R;
import com.parianom.model.SubPanganModel;

import java.util.List;

public class SubPanganRVAdapter extends RecyclerView.Adapter<SubPanganRVAdapter.MyViewHolder> {

    Context mContext;
    List<SubPanganModel> mSubmenu;

    public SubPanganRVAdapter(Context mContext, List<SubPanganModel> mSubmenu) {
        this.mContext = mContext;
        this.mSubmenu = mSubmenu;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.item_sub_menu, parent, false);
        MyViewHolder vHolder = new MyViewHolder(v);

        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder,int position) {
        holder.imgData.setImageResource(mSubmenu.get(position).getImgData());
        holder.namaData.setText(mSubmenu.get(position).getNamaData());
        holder.kecData.setText(mSubmenu.get(position).getKecData());
        holder.hargaData.setText(mSubmenu.get(position).getHargaData());

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        return mSubmenu.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgData;
        private TextView namaData, hargaData, kecData;
        private LinearLayout layout;

        public MyViewHolder(View itemView) {
            super(itemView);

            imgData = (ImageView) itemView.findViewById(R.id.imgPr);
            namaData = (TextView) itemView.findViewById(R.id.jdlPr);
            kecData = (TextView) itemView.findViewById(R.id.kecPr);
            hargaData = (TextView) itemView.findViewById(R.id.hargaPr);
            layout = (LinearLayout) itemView.findViewById(R.id.pilihCard);
        }
    }
}
