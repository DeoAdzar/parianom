package com.parianom.model;

public class TransaksiModel {

    private String titleTransaksi, waktuTransaksi, pembeli, jumlahTransaksi, hargaTransaksi;
    private int imgTransaksi;

    public String getTitleTransaksi() {
        return titleTransaksi;
    }

    public void setTitleTransaksi(String titleTransaksi) {
        this.titleTransaksi = titleTransaksi;
    }

    public String getWaktuTransaksi() {
        return waktuTransaksi;
    }

    public void setWaktuTransaksi(String waktuTransaksi) {
        this.waktuTransaksi = waktuTransaksi;
    }

    public String getPembeli() {
        return pembeli;
    }

    public void setPembeli(String pembeli) {
        this.pembeli = pembeli;
    }

    public String getJumlahTransaksi() {
        return jumlahTransaksi;
    }

    public void setJumlahTransaksi(String jumlahTransaksi) {
        this.jumlahTransaksi = jumlahTransaksi;
    }

    public String getHargaTransaksi() {
        return hargaTransaksi;
    }

    public void setHargaTransaksi(String hargaTransaksi) {
        this.hargaTransaksi = hargaTransaksi;
    }

    public int getImgTransaksi() {
        return imgTransaksi;
    }

    public void setImgTransaksi(int imgTransaksi) {
        this.imgTransaksi = imgTransaksi;
    }

    public TransaksiModel(String titleTransaksi, String waktuTransaksi, String pembeli, String jumlahTransaksi, String hargaTransaksi, int imgTransaksi) {
        this.titleTransaksi = titleTransaksi;
        this.waktuTransaksi = waktuTransaksi;
        this.pembeli = pembeli;
        this.jumlahTransaksi = jumlahTransaksi;
        this.hargaTransaksi = hargaTransaksi;
        this.imgTransaksi = imgTransaksi;
    }
}
