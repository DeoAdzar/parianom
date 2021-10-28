package com.parianom.model;

public class RoomModel {
    String tanggal,alamat,nama_toko,foto_toko;
    int id,id_penjual,id_user;

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNama_toko() {
        return nama_toko;
    }

    public void setNama_toko(String nama_toko) {
        this.nama_toko = nama_toko;
    }

    public String getFoto_toko() {
        return foto_toko;
    }

    public void setFoto_toko(String foto_toko) {
        this.foto_toko = foto_toko;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_penjual() {
        return id_penjual;
    }

    public void setId_penjual(int id_penjual) {
        this.id_penjual = id_penjual;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }
}
