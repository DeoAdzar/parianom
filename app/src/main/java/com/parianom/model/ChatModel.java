package com.parianom.model;

public class ChatModel {
    String pesan, created_at;
    int id, id_room, id_sender;

    public ChatModel(String pesan, String created_at, int id, int id_room, int id_sender) {
        this.pesan = pesan;
        this.id = id;
        this.id_room = id_room;
        this.id_sender = id_sender;
        this.created_at = created_at;
    }

    public String getPesan() {
        return pesan;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_room() {
        return id_room;
    }

    public void setId_room(int id_room) {
        this.id_room = id_room;
    }

    public int getId_sender() {
        return id_sender;
    }

    public void setId_sender(int id_sender) {
        this.id_sender = id_sender;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
