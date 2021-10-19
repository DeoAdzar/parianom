package com.parianom.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.parianom.R;
import com.parianom.api.UtilsApi;
import com.parianom.model.PenjualanModel;
import com.parianom.model.TransaksiModel;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TransaksiRVAdapter extends RecyclerView.Adapter<TransaksiRVAdapter.MyViewHolder> {

    Context mContext;
    List<TransaksiModel> mData;

    public TransaksiRVAdapter(Context mContext, List<TransaksiModel> mData) {
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
        final TransaksiModel tr = mData.get(position);

        holder.namaTransaksi.setText("#"+tr.getKode_pesanan());
        holder.subNamaTransaksi.setText(tr.getNama());
        holder.pembeli.setText(tr.getNama_lengkap());
        holder.jumlah.setText(String.valueOf(tr.getJumlah()));
        holder.hargaTransaksi.setText(String.valueOf(tr.getTotal()));
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
        Picasso.get().load(Uri.parse(UtilsApi.IMAGES_PRODUK+tr.getFoto_produk()))
                .placeholder(R.color.shimmer).into(holder.imgTransaksi);

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
                selesai(tr);
            }
        });
    }

    private void selesai(TransaksiModel tr) {
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
            subNamaTransaksi = (TextView) itemView.findViewById(R.id.namaTransaksi);
            pembeli = (TextView) itemView.findViewById(R.id.pembeli);
            jumlah = (TextView) itemView.findViewById(R.id.jumlahTransaksi);
            hargaTransaksi = (TextView) itemView.findViewById(R.id.hargaTotalTransaksi);
            wktTransaksi = (TextView) itemView.findViewById(R.id.waktuTransaksi);
            imgTransaksi = (ImageView) itemView.findViewById(R.id.imgTransaksi);
            selesai = (Button) itemView.findViewById(R.id.btnSelesaiTransaksi);
            batalkan = (Button) itemView.findViewById(R.id.btnBatalTransaksi);
        }
    }
    
    public void batal(){
        
    }
}
