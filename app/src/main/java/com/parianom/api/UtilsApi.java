package com.parianom.api;

public class UtilsApi {

    public static final String BASE_URL = "https://parianom.000webhostapp.com/api/";
    public static final String IMAGES_PROFIL = "https://parianom.000webhostapp.com/storage/app/images_profil/";
    public static final String IMAGES_PRODUK = "https://parianom.000webhostapp.com/storage/app/images_produk/";

    public static BaseApiService getApiService(){
        return RetrofitClient.getClient(BASE_URL).create(BaseApiService.class);
    }
}
