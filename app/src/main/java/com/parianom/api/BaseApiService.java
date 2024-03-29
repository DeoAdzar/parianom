package com.parianom.api;


import com.google.gson.JsonObject;
import com.parianom.model.ChatResponseModel;
import com.parianom.model.DaftarJualanResponseModel;
import com.parianom.model.KecamatanModel;
import com.parianom.model.KecamatanResponseModel;
import com.parianom.model.PenjualanModel;
import com.parianom.model.PenjualanResponseModel;
import com.parianom.model.PesananModel;
import com.parianom.model.PesananResponseModel;
import com.parianom.model.RecomendedResponseModel;
import com.parianom.model.RoomResponseModel;
import com.parianom.model.TransaksiResponseModel;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface BaseApiService {
    @GET("kecamatan")
    Call<KecamatanResponseModel> getKecamatan();
    @GET("search")
    Call<RecomendedResponseModel> getRecom();
    @FormUrlEncoded
    @POST("getUser")
    Call<ResponseBody> getUser(
            @Field("id") int id
    );
    @Multipart
    @POST("updateProfile")
    Call<ResponseBody> updateUser(
            @Part ("foto_profil")RequestBody foto_profil
            ,@Part("nama_lengkap") RequestBody nama_lengkap
            ,@Part("username") RequestBody username
            ,@Part("email") RequestBody email
            ,@Part("alamat") RequestBody alamat
            ,@Part("no_hp") RequestBody no_hp
            ,@Part("id") RequestBody id
    );
    @FormUrlEncoded
    @POST("updateProfile")
    Call<ResponseBody> updateUser2(
            @Field("nama_lengkap") String nama_lengkap
            ,@Field("username") String username
            ,@Field("email") String email
            ,@Field("alamat") String alamat
            ,@Field("no_hp") String no_hp
            ,@Field("id") int id
    );
    @Multipart
    @POST("registerPenjual")
    Call<ResponseBody> registerPenjual(
            @Part ("foto_ktp") RequestBody foto_ktp
            ,@Part("id_user") RequestBody id_user
            ,@Part("nama_toko") RequestBody nama_toko
            ,@Part("nik") RequestBody nik
            ,@Part("npwp") RequestBody npwp
            ,@Part("lain") RequestBody lain
            ,@Part("alamat") RequestBody alamat
            ,@Part("kec") RequestBody kec
    );
    @FormUrlEncoded
    @POST("inputProdukBase")
    Call<ResponseBody> inputProduk(
            @Field ("foto_produk") String foto_produk
            ,@Field ("foto_produk2") String foto_produk2
            ,@Field ("foto_produk3") String foto_produk3
            ,@Field ("foto_produk4") String foto_produk4
            ,@Field ("foto_produk5") String foto_produk5
            ,@Field("id_penjual") int id_penjual
            ,@Field("kategori") String kategori
            ,@Field("kategori_sub") String kategori_sub
            ,@Field("nama") String nama
            ,@Field("deskripsi") String deskripsi
            ,@Field("harga") int harga
            ,@Field("stok") int stok
    );
    @FormUrlEncoded
    @POST("updateProduk")
    Call<ResponseBody> updateProduk(
            @Field ("foto_produk") String foto_produk
            ,@Field ("foto_produk2") String foto_produk2
            ,@Field ("foto_produk3") String foto_produk3
            ,@Field ("foto_produk4") String foto_produk4
            ,@Field ("foto_produk5") String foto_produk5
            ,@Field("kategori") String kategori
            ,@Field("kategori_sub") String kategori_sub
            ,@Field("nama") String nama
            ,@Field("harga") int harga
            ,@Field("deskripsi") String deskripsi
            ,@Field("stok") int stok
            ,@Field("id") int id_produk
    );
    @FormUrlEncoded
    @POST("updateProduk")
    Call<ResponseBody> updateProduk2(
            @Field("kategori") String kategori
            ,@Field("kategori_sub") String kategori_sub
            ,@Field("nama") String nama
            ,@Field("deskripsi") String deskripsi
            ,@Field("harga") int harga
            ,@Field("stok") int stok
            ,@Field("id") int id_produk
    );
    @Multipart
    @POST("updatePenjual")
    Call<ResponseBody> updatePenjual(
            @Part ("foto_toko")RequestBody foto_toko
            ,@Part("id_user") RequestBody id_user
            ,@Part("nama_toko") RequestBody nama_toko
            ,@Part("kec") RequestBody kec
            ,@Part("alamat") RequestBody alamat
    );
    @FormUrlEncoded
    @POST("updatePenjual")
    Call<ResponseBody> updatePenjual2(
            @Field("id_user") int id_user
            ,@Field("nama_toko") String nama_toko
            ,@Field("kec") String kec
            ,@Field("alamat") String alamat
    );
    @FormUrlEncoded
    @POST("getPenjual")
    Call<ResponseBody> getPenjual(
            @Field("id_user") int id
    );
    @FormUrlEncoded
    @POST("selesai")
    Call<ResponseBody> selesai(
            @Field("id_pesanan") int id
    );
    @FormUrlEncoded
    @POST("cancel")
    Call<ResponseBody> cancel(
            @Field("id_pesanan") int id
    );
    @FormUrlEncoded
    @POST("login")
    Call<ResponseBody> login(
            @Field("username") String username,
            @Field("kata_sandi") String password
    );
    @FormUrlEncoded
    @POST("cekUsername")
    Call<ResponseBody> cekUsername(
            @Field("username") String username
    );
    @FormUrlEncoded
    @POST("cekPhone")
    Call<ResponseBody> cekPhone(
            @Field("no_hp") String noHp
    );
    @FormUrlEncoded
    @POST("cekEmail")
    Call<ResponseBody> cekEmail(
            @Field("email") String email
    );
    @FormUrlEncoded
    @POST("cekNik")
    Call<ResponseBody> cekNik(
            @Field("nik") String nik
    );
    @FormUrlEncoded
    @POST("register")
    Call<ResponseBody> registerUser(
            @Field("username") String username,
            @Field("nama_lengkap") String nama_lengkap,
            @Field("email") String email,
            @Field("no_hp") String no_hp,
            @Field("alamat") String alamat,
            @Field("kata_sandi") String kata_sandi
    );
    @FormUrlEncoded
    @POST("getProduk")
    Call<PenjualanResponseModel> getProduk(
            @Field("kategori") String kategori,
            @Field("kategori_sub") String kategori_sub
    );
    @FormUrlEncoded
    @POST("getProdukByPenjual")
    Call<DaftarJualanResponseModel> getProdukPenjual(
            @Field("id_penjual")int id_penjual,
            @Field("kategori") String kategori
    );
    @FormUrlEncoded
    @POST("getProdukById")
    Call<ResponseBody> getProdukById(
            @Field("id") int id_produk
    );
    @FormUrlEncoded
    @POST("inputPesanan")
    Call<ResponseBody> inputPesanan(
            @Field("id_produk") int id_produk,
            @Field("id_user") int id_user,
            @Field("id_penjual") int id_penjual,
            @Field("jumlah") int jumlah,
            @Field("kode_pesanan") String kode_pesanan,
            @Field("total") int total
    );
    @FormUrlEncoded
    @POST("scan")
    Call<ResponseBody> scanning(
            @Field("kode_pesanan") String kode_pesanan,
            @Field("id_penjual") int id_penjual
    );
    @FormUrlEncoded
    @POST("getConfirm")
    Call<ResponseBody> getConfirm(
            @Field("kode_pesanan") String kode_pesanan
    );
    @FormUrlEncoded
    @POST("delete")
    Call<ResponseBody> delete(
            @Field("id_produk") int id_produk
    );
    @FormUrlEncoded
    @POST("getDetailPesananByUser")
    Call<ResponseBody> getDetailPesananUser(
            @Field("kode_pesanan") String kode_pesanan,
            @Field("id_user") int id_user
    );
    @FormUrlEncoded
    @POST("getPesananByUser")
    Call<PesananResponseModel> getPesananUser(
            @Field("id_user") int id_user
    );
    @FormUrlEncoded
    @POST("getPesananByPenjual")
    Call<TransaksiResponseModel> getPesananPenjual(
            @Field("id_penjual") int id_penjual
    );
    @FormUrlEncoded
    @POST("cekRoom")
    Call<ResponseBody> cekRoom(
            @Field("id_penjual") int id_penjual,
            @Field("id_user") int id_user
    );
    @FormUrlEncoded
    @POST("createRoom")
    Call<ResponseBody> createRoom(
            @Field("id_penjual") int id_penjual,
            @Field("id_user") int id_user
    );
    @FormUrlEncoded
    @POST("getHpPenjual")
    Call<ResponseBody> getHp(
            @Field("id_penjual") int id_penjual
    );
    @FormUrlEncoded
    @POST("getMessage")
    Call<ChatResponseModel>getMessage (
            @Field("id_room") int id_room
    );
    @FormUrlEncoded
    @POST("chat")
    Call<ResponseBody> send_chat(
            @Field("number") String no_hp,
            @Field("message") String pesan
    );
    @FormUrlEncoded
    @POST("inputMessage")
    Call<ResponseBody> inputMessage(
            @Field("id_room") int id_room,
            @Field("id_sender") int id_sender,
            @Field("pesan") String pesan
    );
    @FormUrlEncoded
    @POST("gantiPw")
    Call<ResponseBody> gantiPass(
            @Field("id_user") int id_user,
            @Field("kata_sandi") String kata_sandi,
            @Field("kata_sandi_baru") String kata_sandi_baru
    );
    @FormUrlEncoded
    @POST("getRoomByUser")
    Call<RoomResponseModel> getRoom(
            @Field("id_user") int id_user
    );
    @Multipart
    @POST("inputAduan")
    Call<ResponseBody> inputAduan(
            @Part ("bukti_pengaduan") RequestBody bukti_pengaduan
            ,@Part("id_user") RequestBody id_user
            ,@Part("id_penjual") RequestBody id_penjual
            ,@Part("kategori_pengaduan") RequestBody kategori_pengaduan
            ,@Part("deskripsi") RequestBody deskripsi
    );
    @FormUrlEncoded
    @POST("searchByKecamatan")
    Call<PenjualanResponseModel> searchProduk(
            @Field("nama_produk") String nama_produk,
            @Field("kecamatan") String kecamatan
    );
    @FormUrlEncoded
    @POST("searchByPenjual")
    Call<PenjualanResponseModel> searchProdukByPenjual(
            @Field("nama_toko") String nama_toko,
            @Field("kecamatan") String kecamatan
    );
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("cek_nik.php")
    Call<ResponseBody> nik(@Body JsonObject nik);
}
