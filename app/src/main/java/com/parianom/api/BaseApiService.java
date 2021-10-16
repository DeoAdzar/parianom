package com.parianom.api;


import com.parianom.model.KecamatanModel;
import com.parianom.model.KecamatanResponseModel;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface BaseApiService {
    @GET("kecamatan")
    Call<KecamatanResponseModel> getKecamatan();

    @FormUrlEncoded
    @POST("getUser")
    Call<ResponseBody> getUser(
            @Field("id") int id
    );
    @Multipart
    @POST("updateProfile")
    Call<ResponseBody> updateUser(
            @Part MultipartBody.Part foto_profil
            ,@Part("nama_lengkap") RequestBody nama_lengkap
            ,@Part("username") RequestBody username
            ,@Part("email") RequestBody email
            ,@Part("alamat") RequestBody alamat
            ,@Part("no_hp") RequestBody no_hp
            ,@Part("id") RequestBody id
    );
    @Multipart
    @POST("registerPenjual")
    Call<ResponseBody> registerPenjual(
            @Part MultipartBody.Part foto_ktp
            ,@Part("id_user") RequestBody id_user
            ,@Part("nama_toko") RequestBody nama_toko
            ,@Part("nik") RequestBody nik
            ,@Part("alamat") RequestBody alamat
            ,@Part("kec") RequestBody kec
    );

    @FormUrlEncoded
    @POST("getPenjual")
    Call<ResponseBody> getPenjual(
            @Field("id_user") int id
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
    @POST("getImgUser")
    Call<ResponseBody> getImgUser(
            @Field("id") int id
    );
}
