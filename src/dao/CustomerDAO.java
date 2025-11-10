package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.CustomerModel;
import until.DatabaseConnect;

public class CustomerDAO {
    // private CarManageView view;
    private static CustomerDAO instance;

    public List<CustomerModel> selectAll() throws SQLException {
        return getAllCustomers();
    }

    public List<CustomerModel> getTopKhachHangTheoTongTien_AllTime() {
        List<CustomerModel> list = new ArrayList<>();
        // Câu SQL này KHÔNG CÓ "WHERE gd.ngayGD..."
        String sql = "SELECT kh.maKH, kh.tenKH, SUM(gd.tongTien) AS tongChiTieu " +
                     "FROM khachhang kh " +
                     "JOIN giaodich gd ON kh.maKH = gd.maKH " +
                     "GROUP BY kh.maKH, kh.tenKH " +
                     "ORDER BY tongChiTieu DESC LIMIT 5"; 
                     
        try (Connection conn = DatabaseConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
             
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                CustomerModel kh = new CustomerModel();
                kh.setMaKH(rs.getString("maKH"));
                kh.setTenKH(rs.getString("tenKH"));
                kh.setTongChiTieu(rs.getBigDecimal("tongChiTieu")); 
                list.add(kh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Lấy top khách hàng theo SỐ LẦN MUA (Mọi thời đại)
     */
    public List<CustomerModel> getTopKhachHangTheoSoLanMua_AllTime() {
        List<CustomerModel> list = new ArrayList<>();
        // Câu SQL này KHÔNG CÓ "WHERE gd.ngayGD..."
        String sql = "SELECT kh.maKH, kh.tenKH, COUNT(gd.maGD) AS soLanMua " +
                     "FROM khachhang kh " +
                     "JOIN giaodich gd ON kh.maKH = gd.maKH " +
                     "GROUP BY kh.maKH, kh.tenKH " +
                     "ORDER BY soLanMua DESC LIMIT 5";

        try (Connection conn = DatabaseConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
             
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                CustomerModel kh = new CustomerModel();
                kh.setMaKH(rs.getString("maKH"));
                kh.setTenKH(rs.getString("tenKH"));
                kh.setSoLanMua(rs.getInt("soLanMua")); 
                list.add(kh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
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
                customer.setDcKH(rs.getString("dcKH"));
                customer.setSdtKH(rs.getString("sdtKH"));
                customer.setTongChiTieu(rs.getBigDecimal("tongChiTieu"));
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
                    customer.setDcKH(rs.getString("dcKH"));
                    customer.setSdtKH(rs.getString("sdtKH"));
                    customer.setTongChiTieu(rs.getBigDecimal("tongChiTieu"));
                    customer.setSoLanMua(rs.getInt("soLanMua"));
                    return customer;
                }
            }
        }
        return null;
    }
        //tự động tạo mã
    public String newMaKH() throws SQLException {
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
        public boolean addCustomer(CustomerModel customerModel) throws SQLException {
        String sql = "INSERT INTO khachhang (maKH, tenKH, dcKH, sdtKH, tongChiTieu, soLanMua) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, customerModel.getMaKH());
            stmt.setString(2, customerModel.getTenKH());
            stmt.setString(3, customerModel.getDcKH());
            stmt.setString(4, customerModel.getSdtKH());
            stmt.setBigDecimal(5, customerModel.getTongChiTieu());
            stmt.setInt(6, customerModel.getSoLanMua());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }
        public boolean updateCustomer(CustomerModel customer) throws SQLException {
        String sql = "UPDATE khachhang SET tenKH = ?, dcKH = ?, sdtKH = ?, tongChiTieu = ?, soLanMua = ? WHERE maKH = ?";
        try (Connection conn = DatabaseConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, customer.getTenKH());
            stmt.setString(2, customer.getDcKH());
            stmt.setString(3, customer.getSdtKH());
            stmt.setBigDecimal(4, customer.getTongChiTieu());
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
        // Tìm kiếm
    public List<CustomerModel> searchAllFields(String keyword) throws SQLException {
        List<CustomerModel> customers = new ArrayList<>();
        String sql = "SELECT * FROM khachhang WHERE maKH LIKE ? OR tenKH LIKE ? OR dcKH LIKE ? OR sdtKH LIKE ? ORDER BY maKH ASC";
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
                    customer.setDcKH(rs.getString("dcKH"));
                    customer.setSdtKH(rs.getString("sdtKH"));
                    customer.setTongChiTieu(rs.getBigDecimal("tongChiTieu"));
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
                    // HOÀN THIỆN (bị thiếu)
                    customer.setMaKH(rs.getString("maKH"));
                    customer.setTenKH(rs.getString("tenKH"));
                    customer.setDcKH(rs.getString("dcKH"));
                    customer.setSdtKH(rs.getString("sdtKH"));
                    customer.setTongChiTieu(rs.getBigDecimal("tongChiTieu"));
                    customer.setSoLanMua(rs.getInt("soLanMua"));
                    customers.add(customer);
                }
            }
        }
        return customers;
    }
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
    public List<CustomerModel> getTopKhachHangTheoTongTien(Date start, Date end) {
    List<CustomerModel> list = new ArrayList<>();
    try (Connection conn = DatabaseConnect.getConnection();
         PreparedStatement ps = conn.prepareStatement(
            "SELECT kh.maKH, kh.tenKH, SUM(gd.tongTien) AS tongChiTieu " +
            "FROM khachhang kh " +
            "JOIN giaodich gd ON kh.maKH = gd.maKH " +
            "WHERE gd.ngayGD BETWEEN ? AND ? " +
            "GROUP BY kh.maKH, kh.tenKH " +
            "ORDER BY tongChiTieu DESC LIMIT 5")) {
        ps.setDate(1, new java.sql.Date(start.getTime()));
        ps.setDate(2, new java.sql.Date(end.getTime()));
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            CustomerModel kh = new CustomerModel();
            kh.setMaKH(rs.getString("maKH"));
            kh.setTenKH(rs.getString("tenKH"));
            kh.setTongChiTieu(rs.getBigDecimal("tongChiTieu")); // cũng không cần có cột thật
            list.add(kh);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return list;
}


public List<CustomerModel> getTopKhachHangTheoSoLanMua(Date start, Date end) {
    List<CustomerModel> list = new ArrayList<>();
    try (Connection conn = DatabaseConnect.getConnection();
         PreparedStatement ps = conn.prepareStatement(
            "SELECT kh.maKH, kh.tenKH, COUNT(gd.maGD) AS soLanMua " +
            "FROM khachhang kh " +
            "JOIN giaodich gd ON kh.maKH = gd.maKH " +
            "WHERE gd.ngayGD BETWEEN ? AND ? " +
            "GROUP BY kh.maKH, kh.tenKH " +
            "ORDER BY soLanMua DESC LIMIT 5")) {
        ps.setDate(1, new java.sql.Date(start.getTime()));
        ps.setDate(2, new java.sql.Date(end.getTime()));
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            CustomerModel kh = new CustomerModel();
            kh.setMaKH(rs.getString("maKH"));
            kh.setTenKH(rs.getString("tenKH"));
            kh.setSoLanMua(rs.getInt("soLanMua")); // mặc dù bảng KH không có cột này
            list.add(kh);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return list;
}
//     public List<CustomerModel> selectAll() throws SQLException {
//         return getAllCustomers();
//     }
//     public List<CustomerModel> getAllCustomers() throws SQLException {
//         List<CustomerModel> customers = new ArrayList<>();
//         String sql = "SELECT * FROM khachhang ORDER BY maKH ASC";

//         try (Connection conn = DatabaseConnect.getConnection();
//              PreparedStatement stmt = conn.prepareStatement(sql);
//              ResultSet rs = stmt.executeQuery()) {

//             while (rs.next()) {
//                 CustomerModel customer = new CustomerModel();
//                 customer.setMaKH(rs.getString("maKH"));
//                 customer.setTenKH(rs.getString("tenKH"));
//                 customer.setDcKH(rs.getString("dckH"));
//                 customer.setSdtKH(rs.getString("sdtKH"));
//                 customer.setTongChiTieu(rs.getLong("tongChiTieu"));
//                 customer.setSoLanMua(rs.getInt("soLanMua"));
//                 customers.add(customer);
//             }
//         }
//         return customers;
//     }
//         public CustomerModel getCustomer(String maKH) throws SQLException {
//         String sql = "SELECT * FROM khachhang WHERE maKH = ?";
//         try (Connection conn = DatabaseConnect.getConnection();
//              PreparedStatement stmt = conn.prepareStatement(sql)) {
//             stmt.setString(1, maKH);

//             try (ResultSet rs = stmt.executeQuery()) {
//                 if (rs.next()) {
//                     CustomerModel customer = new CustomerModel();
//                     customer.setMaKH(rs.getString("maKH"));
//                     customer.setTenKH(rs.getString("tenKH"));
//                     customer.setDcKH(rs.getString("dcKH"));
//                     customer.setSdtKH(rs.getString("sdtKH"));
//                     customer.setTongChiTieu(rs.getLong("tongChiTieu"));
//                     customer.setSoLanMua(rs.getInt("soLanMua"));
//                     return customer;
//                 }
//             }
//         }
//         return null;
//     }

// private String newMaKH() throws SQLException {
//         String newMaKH = "KH001";
//         String sql = "SELECT MAX(CAST(SUBSTRING(maKH, 3) AS UNSIGNED)) FROM khachhang";

//         try (Connection conn = DatabaseConnect.getConnection();
//              PreparedStatement stmt = conn.prepareStatement(sql);
//              ResultSet rs = stmt.executeQuery()) {

//             if (rs.next()) {
//                 int maxId = rs.getInt(1);
//                 if (maxId > 0) {
//                     int nextId = maxId + 1;
//                     newMaKH = String.format("KH%03d", nextId);
//                 }
//             }
//         }
//         return newMaKH;
//     }
//     public boolean ktrSDT(String sdtKH) throws SQLException {
//         String sql = "SELECT COUNT(*) FROM khachhang WHERE sdtKH = ?";
//         try (Connection conn = DatabaseConnect.getConnection();
//              PreparedStatement stmt = conn.prepareStatement(sql)) {
//             stmt.setString(1, sdtKH);
//             try (ResultSet rs = stmt.executeQuery()) {
//                 if (rs.next()) {
//                     return rs.getInt(1) > 0;
//                 }
//             }
//         }
//         return false;
//     }
//         public boolean addCustomer(CustomerModel customer) throws SQLException {
//         String newMaKH = this.newMaKH();
//         customer.setMaKH(newMaKH);
//         String sql = "INSERT INTO khachhang (maKH, tenKH, dckH, sdtKH, tongChiTieu, soLanMua) VALUES (?, ?, ?, ?, ?, ?)";
//         try (Connection conn = DatabaseConnect.getConnection();
//              PreparedStatement stmt = conn.prepareStatement(sql)) {
//             stmt.setString(1, customer.getMaKH());
//             stmt.setString(2, customer.getTenKH());
//             stmt.setString(3, customer.getDcKH());
//             stmt.setString(4, customer.getSdtKH());
//             // stmt.setLong(5, customer.getTongChiTieu());
//             stmt.setInt(6, customer.getSoLanMua());

//             int affectedRows = stmt.executeUpdate();
//             return affectedRows > 0;
//         }
//     }

//     public static CustomerDAO getInstance() {
//     if (instance == null) {
//         instance = new CustomerDAO();
//     }
//     return instance;
// }
//     public boolean updateCustomer(CustomerModel customer) throws SQLException {
//         String sql = "UPDATE khachhang SET tenKH = ?, dckH = ?, sdtKH = ?, tongChiTieu = ?, soLanMua = ? WHERE maKH = ?";
//         try (Connection conn = DatabaseConnect.getConnection();
//              PreparedStatement stmt = conn.prepareStatement(sql)) {
//             stmt.setString(1, customer.getTenKH());
//             stmt.setString(2, customer.getDcKH());
//             stmt.setString(3, customer.getSdtKH());
//             // stmt.setLong(4, customer.getTongChiTieu());
//             stmt.setInt(5, customer.getSoLanMua());
//             stmt.setString(6, customer.getMaKH());
//             return stmt.executeUpdate() > 0;
//         }
//     }
//         public boolean deleteCustomer(String maKH) throws SQLException {
//         String sql = "DELETE FROM khachhang WHERE maKH = ?";
//         try (Connection conn = DatabaseConnect.getConnection();
//              PreparedStatement stmt = conn.prepareStatement(sql)) {
//             stmt.setString(1, maKH);
//             return stmt.executeUpdate() > 0;
//         }
//     }
    // public List<CustomerModel> searchAllFields(String keyword) throws SQLException {
    //     List<CustomerModel> customers = new ArrayList<>();
    //     String sql = "SELECT * FROM khachhang WHERE maKH LIKE ? OR tenKH LIKE ? OR dckH LIKE ? OR sdtKH LIKE ? ORDER BY maKH ASC";
    //     try (Connection conn = DatabaseConnect.getConnection();
    //          PreparedStatement stmt = conn.prepareStatement(sql)) {
    //         String searchPattern = "%" + keyword + "%";
    //         stmt.setString(1, searchPattern);
    //         stmt.setString(2, searchPattern);
    //         stmt.setString(3, searchPattern);
    //         stmt.setString(4, searchPattern);
    //         try (ResultSet rs = stmt.executeQuery()) {
    //             while (rs.next()) {
    //                 CustomerModel customer = new CustomerModel();
    //                 customer.setMaKH(rs.getString("maKH"));
    //                 customer.setTenKH(rs.getString("tenKH"));
    //                 customer.setDcKH(rs.getString("dckH"));
    //                 customer.setSdtKH(rs.getString("sdtKH"));
    //                 customer.setTongChiTieu(rs.getLong("tongChiTieu"));
    //                 customer.setSoLanMua(rs.getInt("soLanMua"));
    //                 customers.add(customer);
    //             }
    //         }
    //     }
        public static CustomerDAO getInstance() {
        if (instance == null) {
            instance = new CustomerDAO();
        }
        return instance;
    }

}
