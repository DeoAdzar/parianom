package com.parianom.model;

public class PenjualanModel {

   private String kategori,kategori_sub,nama,foto_produk,foto_produk2,foto_produk3,foto_produk4,foto_produk5,timestamp,nama_toko,alamat,kec,deskripsi,status_toko,foto_toko;
   private int id,id_penjual,harga,stok,id_user,status_deleted;

   public String getFoto_toko() {
      return foto_toko;
   }

   public String getFoto_produk2() {
      return foto_produk2;
   }

   public void setFoto_produk2(String foto_produk2) {
      this.foto_produk2 = foto_produk2;
   }

   public String getFoto_produk3() {
      return foto_produk3;
   }

   public void setFoto_produk3(String foto_produk3) {
      this.foto_produk3 = foto_produk3;
   }

   public String getFoto_produk4() {
      return foto_produk4;
   }

   public void setFoto_produk4(String foto_produk4) {
      this.foto_produk4 = foto_produk4;
   }

   public String getFoto_produk5() {
      return foto_produk5;
   }

   public void setFoto_produk5(String foto_produk5) {
      this.foto_produk5 = foto_produk5;
   }

   public void setFoto_toko(String foto_toko) {
      this.foto_toko = foto_toko;
   }

   public String getStatus_toko() {
      return status_toko;
   }

   public void setStatus_toko(String status_toko) {
      this.status_toko = status_toko;
   }

   public String getDeskripsi() {
      return deskripsi;
   }

   public void setDeskripsi(String deskripsi) {
      this.deskripsi = deskripsi;
   }

   public int getStatus_deleted() {
      return status_deleted;
   }

   public void setStatus_deleted(int status_deleted) {
      this.status_deleted = status_deleted;
   }

   public String getKategori() {
      return kategori;
   }

   public void setKategori(String kategori) {
      this.kategori = kategori;
   }

   public String getKategori_sub() {
      return kategori_sub;
   }

   public void setKategori_sub(String kategori_sub) {
      this.kategori_sub = kategori_sub;
   }

   public String getNama() {
      return nama;
   }

   public void setNama(String nama) {
      this.nama = nama;
   }

   public String getFoto_produk() {
      return foto_produk;
   }

   public void setFoto_produk(String foto_produk) {
      this.foto_produk = foto_produk;
   }

   public String getTimestamp() {
      return timestamp;
   }

   public void setTimestamp(String timestamp) {
      this.timestamp = timestamp;
   }

   public String getNama_toko() {
      return nama_toko;
   }

   public void setNama_toko(String nama_toko) {
      this.nama_toko = nama_toko;
   }

   public String getAlamat() {
      return alamat;
   }

   public void setAlamat(String alamat) {
      this.alamat = alamat;
   }

   public String getKec() {
      return kec;
   }

   public void setKec(String kec) {
      this.kec = kec;
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

   public int getHarga() {
      return harga;
   }

   public void setHarga(int harga) {
      this.harga = harga;
   }

   public int getStok() {
      return stok;
   }

   public void setStok(int stok) {
      this.stok = stok;
   }

   public int getId_user() {
      return id_user;
   }

   public void setId_user(int id_user) {
      this.id_user = id_user;
   }
}
