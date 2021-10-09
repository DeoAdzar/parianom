package com.parianom.model;

public class RiwayatModel {

    private String titleProduk, waktuBelanja, titleRiwayat, alamatPenjual, hargaProduk;
    private int imgProduk, jumlahBelanja;

    public RiwayatModel(String titleProduk, String waktuBelanja, String titleRiwayat, String alamatPenjual, String hargaProduk, int imgProduk, int jumlahBelanja) {
        this.titleProduk = titleProduk;
        this.waktuBelanja = waktuBelanja;
        this.titleRiwayat = titleRiwayat;
        this.alamatPenjual = alamatPenjual;
        this.hargaProduk = hargaProduk;
        this.imgProduk = imgProduk;
        this.jumlahBelanja = jumlahBelanja;
    }

    public String getTitleProduk() {
        return titleProduk;
    }

    public void setTitleProduk(String titleProduk) {
        this.titleProduk = titleProduk;
    }

    public String getWaktuBelanja() {
        return waktuBelanja;
    }

    public void setWaktuBelanja(String waktuBelanja) {
        this.waktuBelanja = waktuBelanja;
    }

    public String getTitleRiwayat() {
        return titleRiwayat;
    }

    public void setTitleRiwayat(String titleRiwayat) {
        this.titleRiwayat = titleRiwayat;
    }

    public String getAlamatPenjual() {
        return alamatPenjual;
    }

    public void setAlamatPenjual(String alamatPenjual) {
        this.alamatPenjual = alamatPenjual;
    }

    public String getHargaProduk() {
        return hargaProduk;
    }

    public void setHargaProduk(String hargaProduk) {
        this.hargaProduk = hargaProduk;
    }

    public int getImgProduk() {
        return imgProduk;
    }

    public void setImgProduk(int imgProduk) {
        this.imgProduk = imgProduk;
    }

    public int getJumlahBelanja() {
        return jumlahBelanja;
    }

    public void setJumlahBelanja(int jumlahBelanja) {
        this.jumlahBelanja = jumlahBelanja;
    }
}
