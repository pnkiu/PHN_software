package model;


import java.math.BigDecimal;

public class TransactionModel {

    private String maGD;
    private String maKH;
    private String maNV;
    private String maOTO;
    private BigDecimal tongtien;
    private String ngayGD;
    private int soLuong;
    private String tenKH;
    private String tenNV;
    private String tenOTO;

    public TransactionModel(String maGD, String maKH, String maNV, String maOTO,BigDecimal tongtien  ,String ngayGD, int soLuong) {
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

    public BigDecimal getTongtien() {
        return tongtien;
    }

    public void setTongtien(BigDecimal tongtien) {
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

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }

    public String getTenOTO() {
        return tenOTO;
    }

    public void setTenOTO(String tenOTO) {
        this.tenOTO = tenOTO;
    }
}