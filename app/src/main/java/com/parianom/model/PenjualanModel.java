package com.parianom.model;

public class PenjualanModel {

    private String titleProduk, waktuBelanja, kecPenjual, alamatPenjual, hargaProduk, pembeli, penjual, kategori, jenis;
    private int imgProduk, jumlahBelanja;

    public PenjualanModel(String titleProduk, String waktuBelanja, String kecPenjual, String alamatPenjual, String hargaProduk, String pembeli, String penjual, String kategori, String jenis, int imgProduk, int jumlahBelanja) {
        this.titleProduk = titleProduk;
        this.waktuBelanja = waktuBelanja;
        this.kecPenjual = kecPenjual;
        this.alamatPenjual = alamatPenjual;
        this.hargaProduk = hargaProduk;
        this.pembeli = pembeli;
        this.penjual = penjual;
        this.kategori = kategori;
        this.jenis = jenis;
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

    public String getKecPenjual() {
        return kecPenjual;
    }

    public void setKecPenjual(String kecPenjual) {
        this.kecPenjual = kecPenjual;
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

    public String getPembeli() {
        return pembeli;
    }

    public void setPembeli(String pembeli) {
        this.pembeli = pembeli;
    }

    public String getPenjual() {
        return penjual;
    }

    public void setPenjual(String penjual) {
        this.penjual = penjual;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
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
