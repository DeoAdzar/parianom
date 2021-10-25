package com.parianom.model;

import java.util.List;

public class ChatResponseModel {
    private String message;
    private List<ChatModel>data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ChatModel> getData() {
        return data;
    }

    public void setData(List<ChatModel> data) {
        this.data = data;
    }
}
