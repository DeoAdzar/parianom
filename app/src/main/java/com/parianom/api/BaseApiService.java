package com.parianom.api;


import com.parianom.model.KecamatanModel;

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
    @GET("getUser")
    Call<ResponseBody> getUser(
            @Field("id") int id
    );

    @FormUrlEncoded
    @GET("getPenjual")
    Call<ResponseBody> getPenjual(
            @Field("id_user") int id
    );
}
