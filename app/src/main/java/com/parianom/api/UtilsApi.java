package com.parianom.api;

public class UtilsApi {

    public static final String BASE_URL = "https://parianom.ae-techno.com/api/";
    public static final String IMAGES_PROFIL = "https://parianom.ae-techno.com/images/app/images_profil/";
    public static final String IMAGES_PRODUK = "https://parianom.ae-techno.com/images/app/images_produk/";
    public static final String IMAGES_TOKO = "https://parianom.ae-techno.com/images/app/images_toko/";

    public static BaseApiService getApiService(){
        return RetrofitClient.getClient(BASE_URL).create(BaseApiService.class);
    }
}
