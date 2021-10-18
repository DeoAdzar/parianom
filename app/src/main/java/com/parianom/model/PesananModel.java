package com.parianom.model;

public class PesananModel {
    String timestamp,nama,alamat,foto_produk,nama_toko,kode_pesanan,status;
    int total;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getKode_pesanan() {
        return kode_pesanan;
    }

    public void setKode_pesanan(String kode_pesanan) {
        this.kode_pesanan = kode_pesanan;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getFoto_produk() {
        return foto_produk;
    }

    public void setFoto_produk(String foto_produk) {
        this.foto_produk = foto_produk;
    }

    public String getNama_toko() {
        return nama_toko;
    }

    public void setNama_toko(String nama_toko) {
        this.nama_toko = nama_toko;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
