package com.parianom.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.parianom.R;
import com.parianom.model.ChatModel;
import com.parianom.model.PesananModel;

import java.util.ArrayList;
import java.util.List;

public class ChatRVAdapter extends RecyclerView.Adapter<ChatRVAdapter.MyViewHolder>{
    Context mContext;
    List<ChatModel> mData;

    public ChatRVAdapter(List<ChatModel> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat, parent, false);
        MyViewHolder vHolder = new MyViewHolder(v);

        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ChatRVAdapter.MyViewHolder holder, int position) {
        ChatModel chatModel = mData.get(position);

        if (chatModel.getId_sender()==1){
//            holder.layout.setBackground(ContextCompat.getDrawable(mContext, R.drawable.card_riwayat_disable));
            holder.textViewMessage.setBackgroundColor(Color.parseColor("#C13999E2"));
            holder.textViewMessage.setText(chatModel.getPesan());
            holder.created.setText(chatModel.getCreated_at());
            holder.layout.setPadding(150,5, 5, 5);
            holder.layout.setGravity(Gravity.END);
        } else {
            holder.textViewMessage.setBackgroundColor(Color.parseColor("#F3F0F0"));
            holder.textViewMessage.setText(chatModel.getPesan());
            holder.layout.setPadding(5,5, 150, 5);
            holder.layout.setGravity(Gravity.START);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewMessage, created;
        private RelativeLayout layout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewMessage = itemView.findViewById(R.id.textViewMessage);
            created = itemView.findViewById(R.id.created);
            layout = itemView.findViewById(R.id.desainChat);
        }
    }
}
