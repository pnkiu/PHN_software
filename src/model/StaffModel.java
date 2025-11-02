package model;

public class StaffModel {
    private String maNV;
    private String tenNV;
    private long luongNV;
    private String sdtNV;
    private int chucVu;
    private String tenDangNhap;
    private String matKhau;

    public StaffModel() {}

    public StaffModel(String maNV, String tenNV, long luongNV, String sdtNV, int chucVu, String tenDangNhap, String matKhau) {
        this.maNV = maNV;
        this.tenNV = tenNV;
        this.luongNV = luongNV;
        this.sdtNV = sdtNV;
        this.chucVu = chucVu;
        this.tenDangNhap = tenDangNhap;
        this.matKhau = matKhau;
    }

    public String getMaNV() { return maNV; }
    public void setMaNV(String maNV) { this.maNV = maNV; }

    public String getTenNV() { return tenNV; }
    public void setTenNV(String tenNV) { this.tenNV = tenNV; }

    public long getLuongNV() { return luongNV; }
    public void setLuongNV(long luongNV) { this.luongNV = luongNV; }

    public String getSdtNV() { return sdtNV; }
    public void setSdtNV(String sdtNV) { this.sdtNV = sdtNV; }

    public int getChucVu() { return chucVu; }
    public void setChucVu(int chucVu) { this.chucVu = chucVu; }

    public String getTenDangNhap() { return tenDangNhap; }
    public void setTenDangNhap(String tenDangNhap) { this.tenDangNhap = tenDangNhap; }

    public String getMatKhau() { return matKhau; }
    public void setMatKhau(String matKhau) { this.matKhau = matKhau; }

    @Override
    public String toString() {
        return this.maNV + ", " + this.tenNV;
    }
}