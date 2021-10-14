package com.parianom.api;

public class UtilsApi {

    public static final String BASE_URL = "http://192.168.43.51/Parianom/public/api/";
    public static final String IMAGES_PROFIL = "http://192.168.43.51/Parianom/storage/app/images_profil/";
    public static final String IMAGES_PRODUK = "http://192.168.43.51/Parianom/storage/app/images_produk/";

    public static BaseApiService getApiService(){
        return RetrofitClient.getClient(BASE_URL).create(BaseApiService.class);
    }
}
