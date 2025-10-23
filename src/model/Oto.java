package model;

public class Oto {
    private String maOTO;
    private String tenOTO;
    private float gia;
    private String loaiOTO;
    private int soLuong;
    private String moTa;
    private String maHang;

    public Oto (){
        super();
    }

	public Oto(String maOTO, String tenOTO, float gia, String loaiOTO, int soLuong, String moTa, String maHang) {
		super();
		this.maOTO = maOTO;
		this.tenOTO = tenOTO;
		this.gia = gia;
		this.loaiOTO = loaiOTO;
		this.soLuong = soLuong;
		this.moTa = moTa;
		this.maHang = maHang;
	}
    
	public String getMaOTO() {
		return maOTO;
	}
	public void setMaOTO(String maOTO) {
		this.maOTO = maOTO;
	}
	public String getTenOTO() {
		return tenOTO;
	}
	public void setTenOTO(String tenOTO) {
		this.tenOTO = tenOTO;
	}
	public float getGia() {
		return gia;
	}
	public void setGia(float gia) {
		this.gia = gia;
	}
	public String getLoaiOTO() {
		return loaiOTO;
	}
	public void setLoaiOTO(String loaiOTO) {
		this.loaiOTO = loaiOTO;
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
    @Override
	public String toString() {
		return "Oto [maOTO=" + maOTO + ", tenOTO=" + tenOTO + ", gia=" + gia + ", loaiOTO=" + loaiOTO + ", soLuong="
				+ soLuong + ", moTa=" + moTa + ", maHang=" + maHang + "]";
	}
}
