package com.parianom.model;

import java.util.List;

public class KecamatanResponseModel {
    private String message;
    private List<KecamatanModel>data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<KecamatanModel> getData() {
        return data;
    }

    public void setData(List<KecamatanModel> data) {
        this.data = data;
    }
}
