package com.parianom.model;

public class SubPanganModel {

    private int imgSubmenu;
    private String namaSubmenu;

    public SubPanganModel(int imgSubmenu, String namaSubmenu) {
        this.imgSubmenu = imgSubmenu;
        this.namaSubmenu = namaSubmenu;
    }

    public int getImgSubmenu() {
        return imgSubmenu;
    }

    public void setImgSubmenu(int imgSubmenu) {
        this.imgSubmenu = imgSubmenu;
    }

    public String getNamaSubmenu() {
        return namaSubmenu;
    }

    public void setNamaSubmenu(String namaSubmenu) {
        this.namaSubmenu = namaSubmenu;
    }
}
