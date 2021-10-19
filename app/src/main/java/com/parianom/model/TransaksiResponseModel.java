package com.parianom.model;

import java.util.List;

public class TransaksiResponseModel {
    private String message;
    private List<TransaksiModel>data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<TransaksiModel> getData() {
        return data;
    }

    public void setData(List<TransaksiModel> data) {
        this.data = data;
    }
}
