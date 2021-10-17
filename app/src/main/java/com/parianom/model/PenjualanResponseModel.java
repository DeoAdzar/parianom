package com.parianom.model;

import java.util.List;

public class PenjualanResponseModel {
    private String message;
    private List<PenjualanModel>data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<PenjualanModel> getData() {
        return data;
    }

    public void setData(List<PenjualanModel> data) {
        this.data = data;
    }
}
