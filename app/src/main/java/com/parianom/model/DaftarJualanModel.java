package com.parianom.model;

public class DaftarJualanModel {

    private String namaProdukJual, tglProdukJual, kategoriProdukJual, jenisProdukJual, hargaProdukJual;
    private int imgProdukJual;

    public DaftarJualanModel(String namaProdukJual, String tglProdukJual, String kategoriProdukJual, String jenisProdukJual, String hargaProdukJual, int imgProdukJual) {
        this.namaProdukJual = namaProdukJual;
        this.tglProdukJual = tglProdukJual;
        this.kategoriProdukJual = kategoriProdukJual;
        this.jenisProdukJual = jenisProdukJual;
        this.hargaProdukJual = hargaProdukJual;
        this.imgProdukJual = imgProdukJual;
    }

    public String getNamaProdukJual() {
        return namaProdukJual;
    }

    public void setNamaProdukJual(String namaProdukJual) {
        this.namaProdukJual = namaProdukJual;
    }

    public String getTglProdukJual() {
        return tglProdukJual;
    }

    public void setTglProdukJual(String tglProdukJual) {
        this.tglProdukJual = tglProdukJual;
    }

    public String getKategoriProdukJual() {
        return kategoriProdukJual;
    }

    public void setKategoriProdukJual(String kategoriProdukJual) {
        this.kategoriProdukJual = kategoriProdukJual;
    }

    public String getJenisProdukJual() {
        return jenisProdukJual;
    }

    public void setJenisProdukJual(String jenisProdukJual) {
        this.jenisProdukJual = jenisProdukJual;
    }

    public String getHargaProdukJual() {
        return hargaProdukJual;
    }

    public void setHargaProdukJual(String hargaProdukJual) {
        this.hargaProdukJual = hargaProdukJual;
    }

    public int getImgProdukJual() {
        return imgProdukJual;
    }

    public void setImgProdukJual(int imgProdukJual) {
        this.imgProdukJual = imgProdukJual;
    }
}
