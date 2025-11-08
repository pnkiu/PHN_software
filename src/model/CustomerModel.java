package model;

import java.math.BigDecimal;

public class CustomerModel {
    private String maKH;
    private String tenKH;
    private String dcKH;
    private String sdtKH;
    private BigDecimal tongChiTieu;
    private int soLanMua;

    public CustomerModel() {
    }

    public CustomerModel(String maKH, String tenKH, String dcKH, String sdtKH, BigDecimal tongChiTieu, int soLanMua) {
        this.maKH = maKH;
        this.tenKH = tenKH;
        this.dcKH = dcKH;
        this.sdtKH = sdtKH;
        this.tongChiTieu = tongChiTieu;
        this.soLanMua = soLanMua;
    }
    public String getMaKH() {
        return maKH;
    }
    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }
    public String getTenKH() {
        return tenKH;
    }
    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }
    public String getDcKH() {
        return dcKH;
    }
    public void setDcKH(String dcKH) {
        this.dcKH = dcKH;
    }
    public String getSdtKH() {
        return sdtKH;
    }
    public void setSdtKH(String sdtKH) {
        this.sdtKH = sdtKH;
    }
    public BigDecimal getTongChiTieu()
    { return tongChiTieu; }
    public void setTongChiTieu(BigDecimal tongChiTieu)
    { this.tongChiTieu = tongChiTieu; }

    public int getSoLanMua()
    { return soLanMua; }
    public void setSoLanMua(int soLanMua)
    { this.soLanMua = soLanMua; }

    @Override
    public String toString() {
        return tenKH + ", " + sdtKH;
    }
}