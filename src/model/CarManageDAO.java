package model;

import until.DatabaseConnect;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CarManageDAO {

    private static CarManageDAO instance;

    // Singleton pattern
    public static CarManageDAO getInstance() {
        if (instance == null) {
            instance = new CarManageDAO();
        }
        return instance;
    }

    private CarManageDAO() {
        // Private constructor for singleton
    }

    public int insert(CarManageModel car) {
        int ketQua = 0;
        Connection connection = null;
        Statement st = null;
        try {
            connection = DatabaseConnect.getConnection();
            st = connection.createStatement();
            String sql = "INSERT INTO oto (maOTO, tenOTO, gia, loaiOTO, soLuong, moTa, maHang, soLuotBan)"
                    + " VALUES ('" + car.getMaOto() + "'"
                    + " , '" + car.getTenOto() + "'"
                    + " , " + car.getGia()
                    + " , '" + car.getLoaiOto() + "'"
                    + " , " + car.getSoLuong()
                    + " , '" + car.getMoTa() + "'"
                    + " , '" + car.getMaHang() + "'"
                    + " , " + car.getSoLuotBan() + ")";
            ketQua = st.executeUpdate(sql);
            System.out.println("Bạn đã thực thi " + sql);
            System.out.println("Có " + ketQua + " dòng bị thay đổi");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (st != null) st.close();
                if (connection != null) DatabaseConnect.closeConnection(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return ketQua;
    }

    public int update(CarManageModel car) {
        int ketQua = 0;
        Connection connection = null;
        Statement st = null;
        try {
            connection = DatabaseConnect.getConnection();
            st = connection.createStatement();
            String sql = "UPDATE oto SET "
                    + " tenOTO = '" + car.getTenOto() + "'"
                    + ", gia = " + car.getGia()
                    + ", loaiOTO = '" + car.getLoaiOto() + "'"
                    + ", soLuong = " + car.getSoLuong()
                    + ", moTa = '" + car.getMoTa() + "'"
                    + ", maHang = '" + car.getMaHang() + "'"
                    + ", soLuotBan = " + car.getSoLuotBan()
                    + " WHERE maOTO = '" + car.getMaOto() + "'";
            
            ketQua = st.executeUpdate(sql);
            System.out.println("Bạn đã thực thi " + sql);
            System.out.println("Có " + ketQua + " dòng bị thay đổi");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (st != null) st.close();
                if (connection != null) DatabaseConnect.closeConnection(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return ketQua;
    }

    public int delete(String maOto) {
        int ketQua = 0;
        Connection connection = null;
        Statement st = null;
        try {
            connection = DatabaseConnect.getConnection();
            st = connection.createStatement();
            String sql = "DELETE FROM oto WHERE maOTO = '" + maOto + "'";
            
            ketQua = st.executeUpdate(sql);
            System.out.println("Bạn đã thực thi " + sql);
            System.out.println("Có " + ketQua + " dòng bị thay đổi");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (st != null) st.close();
                if (connection != null) DatabaseConnect.closeConnection(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return ketQua;
    }

    public CarManageModel selectById(String maOto) {
        CarManageModel car = null;
        Connection connection = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            connection = DatabaseConnect.getConnection();
            st = connection.createStatement();
            String sql = "SELECT * FROM oto WHERE maOTO = '" + maOto + "'";
            rs = st.executeQuery(sql);
            while (rs.next()) {
                String tenOto = rs.getString("tenOTO");
                String loaiOto = rs.getString("loaiOTO");
                double gia = rs.getDouble("gia");
                int soLuong = rs.getInt("soLuong");
                String moTa = rs.getString("moTa");
                String maHang = rs.getString("maHang");
                int soLuotBan = rs.getInt("soLuotBan");
                car = new CarManageModel(gia, loaiOto, maOto, moTa,
                        soLuong, tenOto, soLuotBan, maHang);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (st != null) st.close();
                if (connection != null) DatabaseConnect.closeConnection(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return car;
    }

    public ArrayList<CarManageModel> selectAll() {
        ArrayList<CarManageModel> ketQua = new ArrayList<>();
        Connection connection = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            connection = DatabaseConnect.getConnection();
            st = connection.createStatement();
            String sql = "SELECT * FROM oto";
            rs = st.executeQuery(sql);
            while (rs.next()) {
                String maOto = rs.getString("maOTO");
                String tenOto = rs.getString("tenOTO");
                String loaiOto = rs.getString("loaiOTO");
                double gia = rs.getDouble("gia");
                int soLuong = rs.getInt("soLuong");
                String moTa = rs.getString("moTa");
                String maHang = rs.getString("maHang");
                int soLuotBan = rs.getInt("soLuotBan");
                CarManageModel car = new CarManageModel(gia, loaiOto, maOto, moTa,
                        soLuong, tenOto, soLuotBan, maHang);
                ketQua.add(car);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (st != null) st.close();
                if (connection != null) DatabaseConnect.closeConnection(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return ketQua;
    }
}