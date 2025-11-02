package model;


public class TransactionModel {

    private String maGD;
    private String maKH;
    private String maNV;
    private String maOTO;
    private double tongtien;
    private String ngayGD;
    private int soLuong;

    public TransactionModel(String maGD, String maKH, String maNV, String maOTO,double tongtien  ,String ngayGD, int soLuong) {
        this.maGD = maGD;
        this.maKH = maKH;
        this.maNV = maNV;
        this.maOTO = maOTO;
        this.tongtien = tongtien;
        this.ngayGD = ngayGD;
        this.soLuong = soLuong;
    }


    public String getMaGD() {
        return maGD;
    }

    public void setMaGD(String maGD) {
        this.maGD = maGD;
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public String  getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getMaOTO() {
        return maOTO;
    }

    public void setMaOTO(String maOTO) {
        this.maOTO = maOTO;
    }

    public double getTongtien() {
        return tongtien;
    }

    public void setTongtien(double tongtien) {
        this.tongtien = tongtien;
    }

    public String getNgayGD() {
        return ngayGD;
    }

    public void setNgayGD(String ngayGD) {
        this.ngayGD = ngayGD;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
}