package dao;

import java.math.BigDecimal;
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

    //tự động tạo mã
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

    public boolean insert(TransactionModel gd) {
        Connection conn = null;
        String sqlUpdateStock = "UPDATE oto SET soLuong = soLuong - ?, soLuotBan = soLuotBan + ? "
                + "WHERE maOTO = ? AND soLuong >= ?";
        String sqlInsertTx = "INSERT INTO giaodich (maGD, maKH, maNV, maOTO, tongtien, ngayGD, soLuong)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?)";
        String sqlUpdateCustomer = "UPDATE khachhang SET tongChiTieu = tongChiTieu + ?, soLanMua = soLanMua + 1 "
                + "WHERE maKH = ?";
        try {
            conn = DatabaseConnect.getConnection();
            conn.setAutoCommit(false);
            String newMaGD = this.newMaGD(conn);
            gd.setMaGD(newMaGD);

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

            try (PreparedStatement psCustomer = conn.prepareStatement(sqlUpdateCustomer)) {
                psCustomer.setBigDecimal(1, gd.getTongtien());
                psCustomer.setString(2, gd.getMaKH());

                int rowsAffected = psCustomer.executeUpdate();
                if (rowsAffected == 0) {
                    throw new SQLException("Cập nhật chi tiêu khách hàng thất bại! (Mã KH không tồn tại?)");
                }
            }

            try (PreparedStatement psTx = conn.prepareStatement(sqlInsertTx)) {
                psTx.setString(1, gd.getMaGD());
                psTx.setString(2, gd.getMaKH());
                psTx.setString(3, gd.getMaNV());
                psTx.setString(4, gd.getMaOTO());
                psTx.setBigDecimal(5, gd.getTongtien());
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
                BigDecimal tongtien = rs.getBigDecimal("tongtien");
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

    public boolean delete(String maGD) {
        Connection conn = null;
        String sqlGetTx = "SELECT maOTO, soLuong, maKH, tongtien FROM giaodich WHERE maGD = ?";
        String sqlUpdateStock = "UPDATE oto SET soLuong = soLuong + ?, soLuotBan = soLuotBan - ? WHERE maOTO = ?";
        String sqlUpdateCustomer = "UPDATE khachhang SET tongChiTieu = tongChiTieu - ?, soLanMua = soLanMua - 1 "
                + "WHERE maKH = ? AND soLanMua > 0";
        String sqlDeleteTx = "DELETE FROM giaodich WHERE maGD = ?";

        try {
            conn = DatabaseConnect.getConnection();
            conn.setAutoCommit(false);
            String maOTO = null;
            int soLuong = 0;
            String maKH = null;
            BigDecimal tongtien = null;
            try (PreparedStatement psGet = conn.prepareStatement(sqlGetTx)) {
                psGet.setString(1, maGD);
                try (ResultSet rs = psGet.executeQuery()) {
                    if (rs.next()) {
                        maOTO = rs.getString("maOTO");
                        soLuong = rs.getInt("soLuong");
                        maKH = rs.getString("maKH");
                        tongtien = rs.getBigDecimal("tongtien");
                    } else {
                        throw new SQLException("Không tìm thấy giao dịch để xóa: " + maGD);
                    }
                }
            }

            if (maOTO != null && soLuong > 0) {
                try (PreparedStatement psStock = conn.prepareStatement(sqlUpdateStock)) {
                    psStock.setInt(1, soLuong);
                    psStock.setInt(2, soLuong);
                    psStock.setString(3, maOTO);
                    psStock.executeUpdate();
                }
            }

            if (maKH != null && tongtien != null) {
                try (PreparedStatement psCustomer = conn.prepareStatement(sqlUpdateCustomer)) {
                    psCustomer.setBigDecimal(1, tongtien);
                    psCustomer.setString(2, maKH);
                    psCustomer.executeUpdate();
                }
            }

            try (PreparedStatement psDelete = conn.prepareStatement(sqlDeleteTx)) {
                psDelete.setString(1, maGD);
                int rowsAffected = psDelete.executeUpdate();
                if (rowsAffected == 0) {
                    throw new SQLException("Xóa giao dịch thất bại.");
                }
            }
            conn.commit();
            System.out.println("Đã xóa và hoàn kho/chi tiêu thành công cho maGD = " + maGD);
            return true;
        } catch (SQLException e) {
            System.err.println("Lỗi Transaction khi Xóa! Đang rollback...");
            e.printStackTrace();
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    DatabaseConnect.closeConnection(conn);
                } catch (SQLException e) { e.printStackTrace(); }
            }
        }
    }

    public boolean update(TransactionModel tx) {
        Connection conn = null;
        String sqlGetOldTx = "SELECT maOTO, soLuong, maKH, tongtien FROM giaodich WHERE maGD = ?";

        String sqlUpdateStockAdd = "UPDATE oto SET soLuong = soLuong + ?, soLuotBan = soLuotBan - ? WHERE maOTO = ?";
        String sqlUpdateStockSubtract = "UPDATE oto SET soLuong = soLuong - ?, soLuotBan = soLuotBan + ? WHERE maOTO = ? AND soLuong >= ?";
        String sqlUpdateCustomerAdd = "UPDATE khachhang SET tongChiTieu = tongChiTieu + ?, soLanMua = soLanMua + 1 WHERE maKH = ?";
        String sqlUpdateCustomerSubtract = "UPDATE khachhang SET tongChiTieu = tongChiTieu - ?, soLanMua = soLanMua - 1 WHERE maKH = ? AND soLanMua > 0";
        String sqlUpdateTx = "UPDATE giaodich SET maKH = ?, maNV = ?, maOTO = ?, tongtien = ?, ngayGD = ?, soLuong = ? "
                + "WHERE maGD = ?";

        try {
            conn = DatabaseConnect.getConnection();
            conn.setAutoCommit(false);
            String oldMaOTO = null;
            int oldSoLuong = 0;
            String oldMaKH = null;
            BigDecimal oldTongtien = null;

            try (PreparedStatement psGet = conn.prepareStatement(sqlGetOldTx)) {
                psGet.setString(1, tx.getMaGD());
                try (ResultSet rs = psGet.executeQuery()) {
                    if (rs.next()) {
                        oldMaOTO = rs.getString("maOTO");
                        oldSoLuong = rs.getInt("soLuong");
                        oldMaKH = rs.getString("maKH");
                        oldTongtien = rs.getBigDecimal("tongtien");
                    } else {
                        throw new SQLException("Không tìm thấy giao dịch để cập nhật: " + tx.getMaGD());
                    }
                }
            }

            if (oldMaOTO != null && oldSoLuong > 0) {
                try (PreparedStatement psStockAdd = conn.prepareStatement(sqlUpdateStockAdd)) {
                    psStockAdd.setInt(1, oldSoLuong);
                    psStockAdd.setInt(2, oldSoLuong);
                    psStockAdd.setString(3, oldMaOTO);
                    psStockAdd.executeUpdate();
                }
            }

            if (oldMaKH != null && oldTongtien != null) {
                try (PreparedStatement psCustSub = conn.prepareStatement(sqlUpdateCustomerSubtract)) {
                    psCustSub.setBigDecimal(1, oldTongtien);
                    psCustSub.setString(2, oldMaKH);
                    psCustSub.executeUpdate();
                }
            }

            try (PreparedStatement psStockSub = conn.prepareStatement(sqlUpdateStockSubtract)) {
                psStockSub.setInt(1, tx.getSoLuong());
                psStockSub.setInt(2, tx.getSoLuong());
                psStockSub.setString(3, tx.getMaOTO());
                psStockSub.setInt(4, tx.getSoLuong());

                int rowsAffected = psStockSub.executeUpdate();
                if (rowsAffected == 0) {
                    throw new SQLException("Trừ kho thất bại! (Sản phẩm mới không tồn tại hoặc không đủ hàng)");
                }
            }

            try (PreparedStatement psCustAdd = conn.prepareStatement(sqlUpdateCustomerAdd)) {
                psCustAdd.setBigDecimal(1, tx.getTongtien());
                psCustAdd.setString(2, tx.getMaKH());
                int rowsAffected = psCustAdd.executeUpdate();
                if (rowsAffected == 0) {
                    throw new SQLException("Cập nhật chi tiêu khách hàng mới thất bại! (Mã KH mới không tồn tại?)");
                }
            }

            try (PreparedStatement psTx = conn.prepareStatement(sqlUpdateTx)) {
                psTx.setString(1, tx.getMaKH());
                psTx.setString(2, tx.getMaNV());
                psTx.setString(3, tx.getMaOTO());
                psTx.setBigDecimal(4, tx.getTongtien());
                psTx.setString(5, tx.getNgayGD());
                psTx.setInt(6, tx.getSoLuong());
                psTx.setString(7, tx.getMaGD());

                psTx.executeUpdate();
            }
            conn.commit();
            System.out.println("Cập nhật (5 bước) thành công cho maGD = " + tx.getMaGD());
            return true;
        } catch (SQLException e) {
            System.err.println("Lỗi Transaction khi Cập nhật! Đang rollback...");
            e.printStackTrace();
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    DatabaseConnect.closeConnection(conn);
                } catch (SQLException e) { e.printStackTrace(); }
            }
        }
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
                    BigDecimal tongtien = rs.getBigDecimal("tongtien");
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


    public double getTongDoanhThu(Date start, Date end) {
        double total = 0;
        String sql = "SELECT SUM(tongtien) FROM giaodich WHERE ngayGD >= ? AND ngayGD < ?";

        try (Connection conn = until.DatabaseConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            Calendar cal = Calendar.getInstance();
            cal.setTime(end);
            cal.add(Calendar.DAY_OF_MONTH, 1);
            Date endNextDay = cal.getTime();

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
}