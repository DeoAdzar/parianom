package com.parianom.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.parianom.R;
import com.parianom.model.PenjualanModel;

import java.util.List;

public class TransaksiRVAdapter extends RecyclerView.Adapter<TransaksiRVAdapter.MyViewHolder> {

    Context mContext;
    List<PenjualanModel> mData;

    public TransaksiRVAdapter(Context mContext, List<PenjualanModel> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

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
        final PenjualanModel tr = mData.get(position);

//        holder.namaTransaksi.setText(mData.get(position).getTitleProduk());
//        holder.subNamaTransaksi.setText(mData.get(position).getTitleProduk());
//        holder.pembeli.setText(mData.get(position).getPembeli());
//        holder.jumlah.setText(String.valueOf(mData.get(position).getJumlahBelanja()));
//        holder.hargaTransaksi.setText(mData.get(position).getHargaProduk());
//        holder.wktTransaksi.setText(mData.get(position).getWaktuBelanja());
//        holder.imgTransaksi.setImageResource(mData.get(position).getImgProduk());

        holder.selesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(mContext, TambahProduk.class);
//                intent.putExtra("namaPr", dfJualanModel.getNamaProdukJual());
//                intent.putExtra("imgPr", dfJualanModel.getImgProdukJual());
//                intent.putExtra("hargaPr", dfJualanModel.getHargaProdukJual());
//                intent.putExtra("kategori", dfJualanModel.getKategoriProdukJual());
//                intent.putExtra("jenis", dfJualanModel.getJenisProdukJual());
//                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView namaTransaksi, subNamaTransaksi, pembeli, jumlah, hargaTransaksi, wktTransaksi;
        private ImageView imgTransaksi;
        private Button selesai, batalkan;

        public MyViewHolder(View itemView) {
            super(itemView);

            namaTransaksi = (TextView) itemView.findViewById(R.id.titleTransaksi);
            subNamaTransaksi = (TextView) itemView.findViewById(R.id.titleTransaksi);
            pembeli = (TextView) itemView.findViewById(R.id.pembeli);
            jumlah = (TextView) itemView.findViewById(R.id.jumlahTransaksi);
            hargaTransaksi = (TextView) itemView.findViewById(R.id.hargaTotalTransaksi);
            wktTransaksi = (TextView) itemView.findViewById(R.id.waktuTransaksi);
            imgTransaksi = (ImageView) itemView.findViewById(R.id.imgTransaksi);
            selesai = (Button) itemView.findViewById(R.id.btnSelesaiTransaksi);
            batalkan = (Button) itemView.findViewById(R.id.btnBatalTransaksi);
        }
    }
}
