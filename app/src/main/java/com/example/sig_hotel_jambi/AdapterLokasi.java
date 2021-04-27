package com.example.sig_hotel_jambi;

public class AdapterLokasi {


    private String Nama;
    private String Alamat;
    private String Nomor;

    private String Tipe1;
    private String Tipe2;
    private String Tipe3;

    private String Harga1;
    private String Harga2;
    private String Harga3;

    private float myRating;

    private String Latitude;
    private String Longitude;
    private String ImageUrl;

    public AdapterLokasi(String nama, String alamat, String nomor, String tipe1, String tipe2, String tipe3, String harga1, String harga2, String harga3, float myRating, String lat, String lng, String imageUrl) {
        Nama = nama;
        Alamat = alamat;
        Nomor = nomor;
        Tipe1 = tipe1;
        Tipe2 = tipe2;
        Tipe3 = tipe3;
        Harga1 = harga1;
        Harga2 = harga2;
        Harga3 = harga3;
        this.myRating = myRating;
        Latitude = lat;
        Longitude = lng;
        ImageUrl = imageUrl;
    }

    public AdapterLokasi() {
    }

    public String getNama() {
        return Nama;
    }

    public void setNama(String nama) {
        Nama = nama;
    }

    public String getAlamat() {
        return Alamat;
    }

    public void setAlamat(String alamat) {
        Alamat = alamat;
    }

    public String getNomor() {
        return Nomor;
    }

    public void setNomor(String nomor) {
        Nomor = nomor;
    }

    public String getTipe1() {
        return Tipe1;
    }

    public void setTipe1(String tipe1) {
        Tipe1 = tipe1;
    }

    public String getTipe2() {
        return Tipe2;
    }

    public void setTipe2(String tipe2) {
        Tipe2 = tipe2;
    }

    public String getTipe3() {
        return Tipe3;
    }

    public void setTipe3(String tipe3) {
        Tipe3 = tipe3;
    }

    public String getHarga1() {
        return Harga1;
    }

    public void setHarga1(String harga1) {
        Harga1 = harga1;
    }

    public String getHarga2() {
        return Harga2;
    }

    public void setHarga2(String harga2) {
        Harga2 = harga2;
    }

    public String getHarga3() {
        return Harga3;
    }

    public void setHarga3(String harga3) {
        Harga3 = harga3;
    }

    public float getMyRating() {
        return myRating;
    }

    public void setMyRating(float myRating) {
        this.myRating = myRating;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }
}