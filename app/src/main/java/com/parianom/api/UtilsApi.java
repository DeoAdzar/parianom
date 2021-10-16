package com.parianom.api;

public class UtilsApi {

    public static final String BASE_URL = "https://ae-techno.com/Parianom/public/api/";
    public static final String IMAGES_PROFIL = "https://ae-techno.com/Parianom/storage/app/images_profil/";
    public static final String IMAGES_PRODUK = "https://ae-techno.com/Parianom/storage/app/images_produk/";

    public static BaseApiService getApiService(){
        return RetrofitClient.getClient(BASE_URL).create(BaseApiService.class);
    }
}
