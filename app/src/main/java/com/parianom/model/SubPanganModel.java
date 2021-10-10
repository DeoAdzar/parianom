package com.parianom.model;

public class SubPanganModel {

    private int imgData;
    private String namaData, hargaData, kecData;

    public SubPanganModel(int imgData, String namaData, String hargaData, String kecData) {
        this.imgData = imgData;
        this.namaData = namaData;
        this.hargaData = hargaData;
        this.kecData = kecData;
    }

    public int getImgData() {
        return imgData;
    }

    public void setImgData(int imgData) {
        this.imgData = imgData;
    }

    public String getNamaData() {
        return namaData;
    }

    public void setNamaData(String namaData) {
        this.namaData = namaData;
    }

    public String getHargaData() {
        return hargaData;
    }

    public void setHargaData(String hargaData) {
        this.hargaData = hargaData;
    }

    public String getKecData() {
        return kecData;
    }

    public void setKecData(String kecData) {
        this.kecData = kecData;
    }
}
