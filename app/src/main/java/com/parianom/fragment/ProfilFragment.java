package com.parianom.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.parianom.R;
import com.parianom.activity.BukaToko;
import com.parianom.activity.EditProfil;
import com.parianom.activity.FormBukaToko;
import com.parianom.activity.Konfirmasi;
import com.parianom.activity.PusatBantuan;
import com.parianom.activity.TentangKami;
import com.parianom.activity.Toko;
import com.parianom.api.BaseApiService;
import com.parianom.api.UtilsApi;
import com.parianom.utils.SessionManager;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfilFragment extends Fragment {
    View v;
    LinearLayout toko, profil, bantuan, ttg, keluar;
    TextView namaUser, email;
    CircleImageView img;
    SessionManager sessionManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_profil, container, false);

        toko = (LinearLayout) v.findViewById(R.id.btnTokoSaya);
        profil = (LinearLayout) v.findViewById(R.id.btnProfil);
        bantuan = (LinearLayout) v.findViewById(R.id.btnBantuan);
        ttg = (LinearLayout) v.findViewById(R.id.btnTtgKami);
        keluar = (LinearLayout) v.findViewById(R.id.btnKeluar);
        sessionManager = new SessionManager(getContext());
        namaUser = (TextView) v.findViewById(R.id.namaUser);
        img = v.findViewById(R.id.imgUser);
        email = v.findViewById(R.id.emailUser);
        getResourceProfil();
        keluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sessionManager.logout();
            }
        });
        toko();
        profil();
        pusatBantuan();
        tentangKami();
        return v;
    }

    private void getResourceProfil() {
        HashMap<String, String> user = sessionManager.getUserDetails();
        BaseApiService mApiService = UtilsApi.getApiService();
        Call<ResponseBody> Img = mApiService.getUser(Integer.parseInt(user.get(SessionManager.kunci_id_user)));
        Img.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonResult = new JSONObject(response.body().string());
                        if (jsonResult.getString("message").equals("success")) {
                            String emails = jsonResult.getJSONObject("data").getString("email");
                            String namas = jsonResult.getJSONObject("data").getString("nama_lengkap");
                            String images = jsonResult.getJSONObject("data").getString("foto_profil");
                            Picasso.get().load(UtilsApi.IMAGES_PROFIL + images)
                                    .placeholder(R.drawable.ic_person).into(img);
                            email.setText(emails);
                            namaUser.setText(namas);
                        } else {
                            Toast.makeText(getContext(), "Tidak ada Data", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {
                    sessionManager.logout();
                    Toast.makeText(getContext(), "No data found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public void toko() {
        toko.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, String> user = sessionManager.getUserDetails();
                BaseApiService mApiService = UtilsApi.getApiService();
                Call<ResponseBody> cekData = mApiService.getPenjual(Integer.parseInt(user.get(SessionManager.kunci_id_user)));
                cekData.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            try {
                                JSONObject jsonResult = new JSONObject(response.body().string());
                                if (jsonResult.getString("message").equals("exist")) {
                                    String status = jsonResult.getJSONObject("data").getString("status_toko");
                                    String id_penjual = jsonResult.getJSONObject("data").getString("id");
                                    String nama_toko = jsonResult.getJSONObject("data").getString("nama_toko");
                                    String kecamatan = jsonResult.getJSONObject("data").getString("kec");
                                    String alamat = jsonResult.getJSONObject("data").getString("alamat");
                                    if (status.equals("1")) {
                                        Intent intent = new Intent(getContext(), Toko.class);
                                        intent.putExtra("id_penjual",id_penjual);
                                        intent.putExtra("nama_toko",nama_toko);
                                        intent.putExtra("kecamatan",kecamatan);
                                        intent.putExtra("alamat",alamat);
                                        startActivity(intent);
                                    }else if (status.equals("0")) {
                                        Toast.makeText(getContext(), "Maaf anda bukan masyarakat kabupaten madiun, anda tidak dapat menjadi penjual", Toast.LENGTH_SHORT).show();
                                    }else{
                                        Intent i = new Intent(getContext(), Konfirmasi.class);
                                        startActivity(i);
                                    }
                                } else {
                                    Intent intent = new Intent(getContext(), BukaToko.class);
                                    startActivity(intent);
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
//                if (namaUser.getText().toString() == "Yuli Lestari") {
//                    Intent intent = new Intent(getContext(), Toko.class);
//                    startActivity(intent);
//                } else {
//                    Intent intent = new Intent(getContext(), BukaToko.class);
//                    startActivity(intent);
//                }
            }
        });
    }

    public void profil() {
        profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), EditProfil.class);
                startActivity(intent);
            }
        });
    }

    private void pusatBantuan() {
        bantuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), PusatBantuan.class);
                startActivity(intent);
            }
        });
    }

    private void tentangKami() {
        ttg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), TentangKami.class);
                startActivity(intent);
            }
        });
    }
}