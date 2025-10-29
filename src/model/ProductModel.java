package model;

public class ProductModel {
    private String maOto;
    private String tenOto;
    private String loaiOto;
    private double gia;
    private int soLuong;
    private String moTa;
    private String maHang;
    private int soLuotBan;

    public ProductModel(double gia, String loaiOto, String maOto, String moTa, int soLuong, String tenOto, int soLuotBan, String maHang) {
        this.gia = gia;
        this.loaiOto = loaiOto;
        this.maOto = maOto;
        this.moTa = moTa;
        this.soLuong = soLuong;
        this.tenOto = tenOto;
        this.soLuotBan = soLuotBan;
        this.maHang = maHang;
    }
    public ProductModel(){
        
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

    public String getMaOto() {
        return maOto;
    }

    public void setMaOto(String maOto) {
        this.maOto = maOto;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getTenOto() {
        return tenOto;
    }

    public void setTenOto(String tenOto) {
        this.tenOto = tenOto;
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
}