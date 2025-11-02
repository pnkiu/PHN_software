package dao;

import model.CustomerModel;
import until.DatabaseConnect;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {

    public List<CustomerModel> selectAll() throws SQLException {
        return getAllCustomers();
    }

    // lấy tất cả khách hàng
    public List<CustomerModel> getAllCustomers() throws SQLException {
        List<CustomerModel> customers = new ArrayList<>();
        String sql = "SELECT * FROM khachhang ORDER BY maKH ASC";

        try (Connection conn = DatabaseConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                CustomerModel customer = new CustomerModel();
                customer.setMaKH(rs.getString("maKH"));
                customer.setTenKH(rs.getString("tenKH"));
                customer.setDckH(rs.getString("dckH"));
                customer.setSdtKH(rs.getString("sdtKH"));
                customer.setTongChiTieu(rs.getLong("tongChiTieu"));
                customer.setSoLanMua(rs.getInt("soLanMua"));
                customers.add(customer);
            }
        }
        return customers;
    }

    //lấy dữ liệu 1 kh
    public CustomerModel getCustomer(String maKH) throws SQLException {
        String sql = "SELECT * FROM khachhang WHERE maKH = ?";
        try (Connection conn = DatabaseConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maKH);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    CustomerModel customer = new CustomerModel();
                    customer.setMaKH(rs.getString("maKH"));
                    customer.setTenKH(rs.getString("tenKH"));
                    customer.setDckH(rs.getString("dckH"));
                    customer.setSdtKH(rs.getString("sdtKH"));
                    customer.setTongChiTieu(rs.getLong("tongChiTieu"));
                    customer.setSoLanMua(rs.getInt("soLanMua"));
                    return customer;
                }
            }
        }
        return null;
    }
    //tự động tạo mã
    private String newMaKH() throws SQLException {
        String newMaKH = "KH001";
        String sql = "SELECT MAX(CAST(SUBSTRING(maKH, 3) AS UNSIGNED)) FROM khachhang";

        try (Connection conn = DatabaseConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                int maxId = rs.getInt(1);
                if (maxId > 0) {
                    int nextId = maxId + 1;
                    newMaKH = String.format("KH%03d", nextId);
                }
            }
        }
        return newMaKH;
    }

    //các chức năng
    public boolean addCustomer(CustomerModel customer) throws SQLException {
        String newMaKH = this.newMaKH();
        customer.setMaKH(newMaKH);
        String sql = "INSERT INTO khachhang (maKH, tenKH, dckH, sdtKH, tongChiTieu, soLanMua) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, customer.getMaKH());
            stmt.setString(2, customer.getTenKH());
            stmt.setString(3, customer.getDckH());
            stmt.setString(4, customer.getSdtKH());
            stmt.setLong(5, customer.getTongChiTieu());
            stmt.setInt(6, customer.getSoLanMua());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }
    public boolean updateCustomer(CustomerModel customer) throws SQLException {
        String sql = "UPDATE khachhang SET tenKH = ?, dckH = ?, sdtKH = ?, tongChiTieu = ?, soLanMua = ? WHERE maKH = ?";
        try (Connection conn = DatabaseConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, customer.getTenKH());
            stmt.setString(2, customer.getDckH());
            stmt.setString(3, customer.getSdtKH());
            stmt.setLong(4, customer.getTongChiTieu());
            stmt.setInt(5, customer.getSoLanMua());
            stmt.setString(6, customer.getMaKH());
            return stmt.executeUpdate() > 0;
        }
    }
    public boolean deleteCustomer(String maKH) throws SQLException {
        String sql = "DELETE FROM khachhang WHERE maKH = ?";
        try (Connection conn = DatabaseConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maKH);
            return stmt.executeUpdate() > 0;
        }
    }

    public List<CustomerModel> searchAllFields(String keyword) throws SQLException {
        List<CustomerModel> customers = new ArrayList<>();
        String sql = "SELECT * FROM khachhang WHERE maKH LIKE ? OR tenKH LIKE ? OR dckH LIKE ? OR sdtKH LIKE ? ORDER BY maKH ASC";
        try (Connection conn = DatabaseConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            String searchPattern = "%" + keyword + "%";
            stmt.setString(1, searchPattern);
            stmt.setString(2, searchPattern);
            stmt.setString(3, searchPattern);
            stmt.setString(4, searchPattern);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    CustomerModel customer = new CustomerModel();
                    customer.setMaKH(rs.getString("maKH"));
                    customer.setTenKH(rs.getString("tenKH"));
                    customer.setDckH(rs.getString("dckH"));
                    customer.setSdtKH(rs.getString("sdtKH"));
                    customer.setTongChiTieu(rs.getLong("tongChiTieu"));
                    customer.setSoLanMua(rs.getInt("soLanMua"));
                    customers.add(customer);
                }
            }
        }
        return customers;
    }

    public List<CustomerModel> searchByMaKH(String keyword) throws SQLException {
        List<CustomerModel> customers = new ArrayList<>();
        String sql = "SELECT * FROM khachhang WHERE maKH LIKE ? ORDER BY maKH ASC";
        try (Connection conn = DatabaseConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + keyword + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    CustomerModel customer = new CustomerModel();
                    //... (set properties)
                    customers.add(customer);
                }
            }
        }
        return customers;
    }

    //ktra sdt kh có tồn tại không
    public boolean ktrSDT(String sdtKH) throws SQLException {
        String sql = "SELECT COUNT(*) FROM khachhang WHERE sdtKH = ?";
        try (Connection conn = DatabaseConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, sdtKH);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }
}