package model;

public class CarManageModel {
    private String maOto;
    private String tenOto;
    private double gia;
    private String loaiOto;
    private int soLuong;
    private String moTa;
    private String maHang;
    private int soLuotBan;

    // Constructor đầy đủ tham số
    public CarManageModel(double gia, String loaiOto, String maOto, String moTa, 
                         int soLuong, String tenOto, int soLuotBan, String maHang) {
        this.maOto = maOto;
        this.tenOto = tenOto;
        this.gia = gia;
        this.loaiOto = loaiOto;
        this.soLuong = soLuong;
        this.moTa = moTa;
        this.maHang = maHang;
        this.soLuotBan = soLuotBan;
    }

    // Constructor mặc định
    public CarManageModel() {
    }

    // Getter và Setter
    public String getMaOto() {
        return maOto;
    }

    public void setMaOto(String maOto) {
        this.maOto = maOto;
    }

    public String getTenOto() {
        return tenOto;
    }

    public void setTenOto(String tenOto) {
        this.tenOto = tenOto;
    }

    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }

    public String getLoaiOto() {
        return loaiOto;
    }

    public void setLoaiOto(String loaiOto) {
        this.loaiOto = loaiOto;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getMaHang() {
        return maHang;
    }

    public void setMaHang(String maHang) {
        this.maHang = maHang;
    }

    public int getSoLuotBan() {
        return soLuotBan;
    }

    public void setSoLuotBan(int soLuotBan) {
        this.soLuotBan = soLuotBan;
    }

    @Override
    public String toString() {
        return "CarManageModel{" +
                "maOto='" + maOto + '\'' +
                ", tenOto='" + tenOto + '\'' +
                ", gia=" + gia +
                ", loaiOto='" + loaiOto + '\'' +
                ", soLuong=" + soLuong +
                ", moTa='" + moTa + '\'' +
                ", maHang='" + maHang + '\'' +
                ", soLuotBan=" + soLuotBan +
                '}';
    }
}