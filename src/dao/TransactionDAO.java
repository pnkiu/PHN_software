package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import model.TransactionModel;
import until.DatabaseConnect;

public class TransactionDAO {
    
        public static TransactionDAO getInstance() {
        return new TransactionDAO();
    }

    // private static TransactionDAO instance;
    // private TransactionDAO() {
    // }

    // 2. Phương thức getInstance() công khai (public static)
//     public static TransacionDAO getInstance() {
//         if (instance == null) {
//             instance = new TransacionDAO();
//         }
//         return instance;
//     }
public double getTongDoanhThu(Date start, Date end) {
    double total = 0;
    
    // SỬA LẠI SQL: Dùng >= và <
    String sql = "SELECT SUM(tongtien) FROM giaodich WHERE ngayGD >= ? AND ngayGD < ?";
    
    try (Connection conn = until.DatabaseConnect.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(end);
            cal.add(Calendar.DAY_OF_MONTH, 1); 
            Date endNextDay = cal.getTime();

        // SET PARAMETER
        ps.setDate(1, new java.sql.Date(start.getTime()));
        ps.setDate(2, new java.sql.Date(endNextDay.getTime())); 
        
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            total = rs.getDouble(1);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return total;
}
private String newMaGD(Connection conn) throws SQLException {
        String newMaGD = "GD001";
        String sql = "SELECT MAX(CAST(SUBSTRING(maGD, 3) AS UNSIGNED)) FROM giaodich";

        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            int maxId = rs.getInt(1);
            if (maxId > 0) {
                int nextId = maxId + 1;
                newMaGD = String.format("GD%03d", nextId);
            }
        }
        rs.close();
        stmt.close();
        return newMaGD;
    }
    //cái lon này khủng lắm
    public boolean insert(TransactionModel gd) {
        Connection conn = null;

        String sqlUpdateStock = "UPDATE oto SET soLuong = soLuong - ?, soLuotBan = soLuotBan + ? "
                + "WHERE maOTO = ? AND soLuong >= ?";
        String sqlInsertTx = "INSERT INTO giaodich (maGD, maKH, maNV, maOTO, tongtien, ngayGD, soLuong)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            conn = DatabaseConnect.getConnection();
            conn.setAutoCommit(false);
            String newMaGD = this.newMaGD(conn);
            gd.setMaGD(newMaGD);

            // quản lí số lượng oto
            try (PreparedStatement psStock = conn.prepareStatement(sqlUpdateStock)) {
                psStock.setInt(1, gd.getSoLuong());
                psStock.setInt(2, gd.getSoLuong());
                psStock.setString(3, gd.getMaOTO());
                psStock.setInt(4, gd.getSoLuong());

                int rowsAffected = psStock.executeUpdate();
                if (rowsAffected == 0) {
                    throw new SQLException("Trừ kho thất bại! (Sản phẩm không tồn tại hoặc không đủ hàng)");
                }
            }

            try (PreparedStatement psTx = conn.prepareStatement(sqlInsertTx)) {
                psTx.setString(1, gd.getMaGD());
                psTx.setString(2, gd.getMaKH());
                psTx.setString(3, gd.getMaNV());
                psTx.setString(4, gd.getMaOTO());
                psTx.setDouble(5, gd.getTongtien());
                psTx.setString(6, gd.getNgayGD());
                psTx.setInt(7, gd.getSoLuong());

                psTx.executeUpdate();
            }
            conn.commit();
            return true;

        } catch (SQLException e) {
            System.err.println("Lỗi Transaction! Đang rollback...");
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    DatabaseConnect.closeConnection(conn);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //hiển thị ds gd
    public ArrayList<TransactionModel> selectAll() {
        ArrayList<TransactionModel> ketQua = new ArrayList<>();
        String sql = "SELECT g.*, kh.tenKH, nv.tenNV, o.tenOTO " +
                "FROM giaodich g " +
                "LEFT JOIN khachhang kh ON g.maKH = kh.maKH " +
                "LEFT JOIN nhanvien nv ON g.maNV = nv.maNV " +
                "LEFT JOIN oto o ON g.maOTO = o.maOTO";

        try (Connection connection = DatabaseConnect.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String maGD = rs.getString("maGD");
                String maKH = rs.getString("maKH");
                String maNV = rs.getString("maNV");
                String maOTO = rs.getString("maOTO");
                double tongtien = rs.getDouble("tongtien");
                String ngayGD = rs.getString("ngayGD");
                int soLuong = rs.getInt("soLuong");


                TransactionModel gd = new TransactionModel(maGD, maKH, maNV, maOTO,
                        tongtien, ngayGD, soLuong);
                gd.setTenKH(rs.getString("tenKH"));
                gd.setTenNV(rs.getString("tenNV"));
                gd.setTenOTO(rs.getString("tenOTO"));
                ketQua.add(gd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    //xóa
    public int delete(String maGD) {
        int ketQua = 0;
        String sql = "DELETE FROM giaodich WHERE maGD = ?";

        try (Connection connection = DatabaseConnect.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, maGD);
            ketQua = ps.executeUpdate();
            System.out.println("Đã thực thi xóa " + ketQua + " dòng với maGD = " + maGD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    //sửa
    public int update(TransactionModel tx) {
        int ketQua = 0;
        String sql = "UPDATE giaodich SET maKH = ?, maNV = ?, maOTO = ?, tongtien = ?, ngayGD = ?, soLuong = ? "
                + "WHERE maGD = ?";

        try (Connection connection = DatabaseConnect.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, tx.getMaKH());
            ps.setString(2, tx.getMaNV());
            ps.setString(3, tx.getMaOTO());
            ps.setDouble(4, tx.getTongtien());
            ps.setString(5, tx.getNgayGD());
            ps.setInt(6, tx.getSoLuong());
            ps.setString(7, tx.getMaGD());

            ketQua = ps.executeUpdate();
            System.out.println("Có " + ketQua + " dòng được cập nhật");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ketQua;
    }
    //tìm kiếm
    public ArrayList<TransactionModel> search(String keyword) {
        ArrayList<TransactionModel> ketQua = new ArrayList<>();

        String sql = "SELECT g.*, kh.tenKH, nv.tenNV, o.tenOTO " +
                "FROM giaodich g " +
                "LEFT JOIN khachhang kh ON g.maKH = kh.maKH " +
                "LEFT JOIN nhanvien nv ON g.maNV = nv.maNV " +
                "LEFT JOIN oto o ON g.maOTO = o.maOTO " +
                "WHERE g.maGD LIKE ? OR kh.tenKH LIKE ? OR o.tenOTO LIKE ? OR g.ngayGD LIKE ?";

        try (Connection connection = DatabaseConnect.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            String searchKeyword = "%" + keyword + "%";
            ps.setString(1, searchKeyword);
            ps.setString(2, searchKeyword);
            ps.setString(3, searchKeyword);
            ps.setString(4, searchKeyword);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String maGD = rs.getString("maGD");
                    String maKH = rs.getString("maKH");
                    String maNV = rs.getString("maNV");
                    String maOTO = rs.getString("maOTO");
                    double tongtien = rs.getDouble("tongtien");
                    String ngayGD = rs.getString("ngayGD");
                    int soLuong = rs.getInt("soLuong");

                    TransactionModel gd = new TransactionModel(maGD, maKH, maNV, maOTO,
                            tongtien, ngayGD, soLuong);

                    gd.setTenKH(rs.getString("tenKH"));
                    gd.setTenNV(rs.getString("tenNV"));
                    gd.setTenOTO(rs.getString("tenOTO"));

                    ketQua.add(gd);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ketQua;
    }




}
