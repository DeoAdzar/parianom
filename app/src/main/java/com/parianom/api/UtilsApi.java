package com.parianom.api;

public class UtilsApi {

    public static final String BASE_URL = "https://parianom.ae-techno.com/api/";
    public static final String BASE_KTP = "http://103.132.230.62/penduduk/";

    public static BaseApiService getApiService(){
        return RetrofitClient.getClient(BASE_URL).create(BaseApiService.class);
    }
    public static BaseApiService getApiServiceKtp(){
        return RetrofitClient.getClient2(BASE_KTP).create(BaseApiService.class);
    }
}
