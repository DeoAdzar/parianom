package com.parianom.api;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface BaseApiService {
    @GET("kecamatan")
    Call<ResponseBody> getKecamatan();

    @FormUrlEncoded
    @POST("getUser")
    Call<ResponseBody> getUser(
            @Field("id") int id
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
    @POST("register")
    Call<ResponseBody> registerUser(
            @Field("username") String username,
            @Field("nama_lengkap") String nama_lengkap,
            @Field("email") String email,
            @Field("no_hp") String no_hp,
            @Field("alamat") String alamat,
            @Field("kata_sandi") String kata_sandi
    );
}
