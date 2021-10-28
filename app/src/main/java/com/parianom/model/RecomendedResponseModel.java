package com.parianom.model;

import java.util.List;

public class RecomendedResponseModel {
    private String message;
    private List<RecomendedModel>data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<RecomendedModel> getData() {
        return data;
    }

    public void setData(List<RecomendedModel> data) {
        this.data = data;
    }
}
