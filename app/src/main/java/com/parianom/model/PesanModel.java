package com.parianom.model;

public class PesanModel {

    private String titlePesan, isiPesan;
    private int imgPesan;

    public PesanModel(String titlePesan, String isiPesan, int imgPesan) {
        this.titlePesan = titlePesan;
        this.isiPesan = isiPesan;
        this.imgPesan = imgPesan;
    }

    public String getTitlePesan() {
        return titlePesan;
    }

    public void setTitlePesan(String titlePesan) {
        this.titlePesan = titlePesan;
    }

    public String getIsiPesan() {
        return isiPesan;
    }

    public void setIsiPesan(String isiPesan) {
        this.isiPesan = isiPesan;
    }

    public int getImgPesan() {
        return imgPesan;
    }

    public void setImgPesan(int imgPesan) {
        this.imgPesan = imgPesan;
    }
}
