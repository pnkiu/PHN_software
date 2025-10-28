package dao;
import database.JDBC_Util;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.Oto;

public class OtoDAO implements DAO_interface<Oto> {
    	public static OtoDAO getInstance() {
		return new OtoDAO();
	}
    @Override
    public int insert(Oto t) {
        int ketQua = 0;
        try {
        
        Connection connection = JDBC_Util.getConnection();


        
        String sql = "INSERT INTO oto (maOTO, tenOTO, gia, loaiOTO, soLuong, moTa, maHang, soLuotBan)  VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, t.getMaOTO());
        ps.setString(2, t.getTenOTO());
        ps.setFloat(3, t.getGia());
        ps.setString(4, t.getLoaiOTO());
        ps.setInt(5, t.getSoLuong());
        ps.setString(6, t.getMoTa());
        ps.setString(7, t.getMaHang());
        ps.setInt(8, t.getSoLuotBan());
        

        ketQua = ps.executeUpdate();

        //B4
        System.out.println("Bạn đã thực thi "+ sql);
		System.out.println("Có "+ketQua+" dòng bị thay đổi");

        //B5
        JDBC_Util.closeConnection(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public int update(Oto t) {
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public int delete(Oto t) {
               int ketQua = 0;
        try {
            //B1
        Connection connection = JDBC_Util.getConnection();

        //B2
        Statement st = connection.createStatement();

        //B3
        String sql = "DELETE from oto "
					+"WHERE maOTO = '"+t.getMaOTO()+"'";
        ketQua = st.executeUpdate(sql);

        //B4
        System.out.println("Bạn đã thực thi "+ sql);
		System.out.println("Có "+ketQua+" dòng bị thay đổi");

        //B5
        JDBC_Util.closeConnection(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ketQua;
        
    }

    @Override
    public ArrayList<Oto> selectAll() {
        ArrayList<Oto> ketQua = new ArrayList<>();
		try {
			// Bước 1: Tạo kết nối
			Connection connection = JDBC_Util.getConnection();
			
			// Bước 2: Tạo ra đối tượng statement
			Statement st = connection.createStatement();
					
			// Bước 3: Thực thi câu lệnh SQL
			String sql = "SELECT * FROM oto";
			ResultSet rs = st.executeQuery(sql);
			
			// Bước 4: Xử lý kết quả
			while(rs.next()) {
				String maOTO = rs.getString("maOTO");
				String tenOTO = rs.getString("tenOTO");
				float gia = rs.getFloat("gia");
                String loaiOTO = rs.getString("loaiOTO");
				int soLuong = rs.getInt("soLuong");
                String moTa = rs.getString("moTa");
                String maHang = rs.getString("maHang");
                int soLuotBan = rs.getInt("soLuotBan");
                Oto oto = new Oto(maOTO, tenOTO, gia, loaiOTO, soLuong, moTa, maHang, soLuotBan);
				ketQua.add(oto);
			}
			
			// Bước 5: Ngắt kết nối
			JDBC_Util.closeConnection(connection);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ketQua;
        
    }



}
