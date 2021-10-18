package com.parianom.model;

import java.util.List;

public class PesananResponseModel {
    private String message;
    private List<PesananModel>data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<PesananModel> getData() {
        return data;
    }

    public void setData(List<PesananModel> data) {
        this.data = data;
    }
}
