package model;

public class CustomerModel {
    private int maKH;
    private String tenKH;
    private String dckH;
    private String sdtKH;
    private long tongChiTieu;
    private int soLanMua;

    // Constructor
    public CustomerModel() {}

    public CustomerModel(int maKH, String tenKH, String dckH, String sdtKH, long tongChiTieu, int soLanMua) {
        this.maKH = maKH;
        this.tenKH = tenKH;
        this.dckH = dckH;
        this.sdtKH = sdtKH;
        this.tongChiTieu = tongChiTieu;
        this.soLanMua = soLanMua;
    }

    // Getters and Setters
    public int getMaKH() {
        return maKH;
    }

    public void setMaKH(int maKH) {
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
                "maKH=" + maKH +
                ", tenKH='" + tenKH + '\'' +
                ", dckH='" + dckH + '\'' +
                ", sdtKH='" + sdtKH + '\'' +
                ", tongChiTieu=" + tongChiTieu +
                ", soLanMua=" + soLanMua +
                '}';
    }
}