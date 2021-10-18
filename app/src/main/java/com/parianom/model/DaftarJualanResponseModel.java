package com.parianom.model;

import java.util.List;

public class DaftarJualanResponseModel {
    private String message;
    private List<DaftarJualanModel>data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DaftarJualanModel> getData() {
        return data;
    }

    public void setData(List<DaftarJualanModel> data) {
        this.data = data;
    }
}
