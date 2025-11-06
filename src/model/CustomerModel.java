package model;

public class CustomerModel {
    private String maKH;  // Đã là String - tương đương varchar trong database
    private String tenKH;
    private String dckH;
    private String sdtKH;
    private long tongChiTieu;
    private int soLanMua;

    // Constructor
    public CustomerModel() {}

    public CustomerModel(String maKH, String tenKH, String dckH, String sdtKH, long tongChiTieu, int soLanMua) {
        this.maKH = maKH;
        this.tenKH = tenKH;
        this.dckH = dckH;
        this.sdtKH = sdtKH;
        this.tongChiTieu = tongChiTieu;
        this.soLanMua = soLanMua;
    }

    // Getters and Setters
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

    public String getDckH() {
        return dckH;
    }

    public void setDckH(String dckH) {
        this.dckH = dckH;
    }

    public String getSdtKH() {
        return sdtKH;
    }

    public void setSdtKH(String sdtKH) {
        this.sdtKH = sdtKH;
    }

    public long getTongChiTieu() {
        return tongChiTieu;
    }

    public void setTongChiTieu(long tongChiTieu) {
        this.tongChiTieu = tongChiTieu;
    }

    public int getSoLanMua() {
        return soLanMua;
    }

    public void setSoLanMua(int soLanMua) {
        this.soLanMua = soLanMua;
    }

    @Override
    public String toString() {
        return "CustomerModel{" +
                "maKH='" + maKH + '\'' +  // Sửa thành dấu ' thay vì dấu =
                ", tenKH='" + tenKH + '\'' +
                ", dckH='" + dckH + '\'' +
                ", sdtKH='" + sdtKH + '\'' +
                ", tongChiTieu=" + tongChiTieu +
                ", soLanMua=" + soLanMua +
                '}';
    }
}