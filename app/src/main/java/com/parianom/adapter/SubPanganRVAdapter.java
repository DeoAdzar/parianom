package com.parianom.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.VectorEnabledTintResources;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import com.parianom.R;
import com.parianom.fragment.KriyaFragment;
import com.parianom.fragment.PanganFragment;
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
        holder.imgSubmenu.setImageResource(mSubmenu.get(position).getImgSubmenu());
        holder.namaSubmenu.setText(mSubmenu.get(position).getNamaSubmenu());

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

        private ImageView imgSubmenu;
        private TextView namaSubmenu;
        private LinearLayout layout;

        public MyViewHolder(View itemView) {
            super(itemView);

            imgSubmenu = (ImageView) itemView.findViewById(R.id.imgSubmenu);
            namaSubmenu = (TextView) itemView.findViewById(R.id.namaSubmenu);
            layout = (LinearLayout) itemView.findViewById(R.id.btnSubmenu);
        }
    }
}
