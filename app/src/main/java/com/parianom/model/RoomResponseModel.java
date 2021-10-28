package com.parianom.model;

import java.util.List;

public class RoomResponseModel {
    private String message;
    private List<RoomModel>data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<RoomModel> getData() {
        return data;
    }

    public void setData(List<RoomModel> data) {
        this.data = data;
    }
}
