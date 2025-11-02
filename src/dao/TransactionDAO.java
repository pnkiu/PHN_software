package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.TransactionModel;
import until.DatabaseConnect;

public class TransactionDAO {

    public static TransactionDAO getInstance() {
        return new TransactionDAO();
    }

    /**
     * Tự động tạo Mã Giao Dịch mới (ví dụ: GD001, GD002...)
     */
    private String generateNewMaGD(Connection conn) throws SQLException {
        // SỬA: Dùng connection được truyền vào để đảm bảo Transaction
        String newMaGD = "GD001";
        String sql = "SELECT MAX(CAST(SUBSTRING(maGD, 3) AS UNSIGNED)) FROM giaodich";

        // Không dùng try-with-resources ở đây vì connection phải được quản lý bên ngoài
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

    /**
     * SỬA LẠI HOÀN TOÀN: Dùng Transaction để Trừ Kho VÀ Thêm Giao Dịch
     * Hàm này trả về boolean (thành công/thất bại)
     */
    public boolean insert(TransactionModel gd) {
        Connection conn = null;

        // SỬA: Thêm "soLuotBan = soLuotBan + ?" vào câu SQL
        String sqlUpdateStock = "UPDATE oto SET soLuong = soLuong - ?, soLuotBan = soLuotBan + ? "
                + "WHERE maOTO = ? AND soLuong >= ?";

        String sqlInsertTx = "INSERT INTO giaodich (maGD, maKH, maNV, maOTO, tongtien, ngayGD, soLuong)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            conn = DatabaseConnect.getConnection();
            // 1. BẮT ĐẦU TRANSACTION
            conn.setAutoCommit(false);

            // 2. TẠO MÃ GD MỚI (Logic này giữ nguyên)
            String newMaGD = this.generateNewMaGD(conn);
            gd.setMaGD(newMaGD);

            // 3. TRỪ KHO VÀ CỘNG LƯỢT BÁN
            try (PreparedStatement psStock = conn.prepareStatement(sqlUpdateStock)) {

                psStock.setInt(1, gd.getSoLuong());    // (tham số 1) Lượng cần trừ
                psStock.setInt(2, gd.getSoLuong());    // (tham số 2) SỬA: Lượng cần cộng (chính là số lượng bán)
                psStock.setString(3, gd.getMaOTO());   // (tham số 3) Mã xe
                psStock.setInt(4, gd.getSoLuong());    // (tham số 4) Điều kiện: Tồn kho >= Lượng cần trừ

                int rowsAffected = psStock.executeUpdate();

                if (rowsAffected == 0) {
                    throw new SQLException("Trừ kho thất bại! (Sản phẩm không tồn tại hoặc không đủ hàng)");
                }
            }

            // 4. THÊM GIAO DỊCH (Logic này giữ nguyên)
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

            // 5. NẾU TẤT CẢ THÀNH CÔNG: LƯU LẠI
            conn.commit();
            return true; // Trả về thành công

        } catch (SQLException e) {
            // 6. NẾU CÓ LỖI: HỦY TẤT CẢ
            System.err.println("Lỗi Transaction! Đang rollback...");
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            return false; // Trả về thất bại
        } finally {
            // 7. TRẢ LẠI TRẠNG THÁI CŨ VÀ ĐÓNG KẾT NỐI
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


    // SỬA: Nâng cấp hàm này lên 'try-with-resources' cho đồng bộ
    public ArrayList<TransactionModel> selectAll() {
        ArrayList<TransactionModel> ketQua = new ArrayList<>();
        String sql = "SELECT * FROM giaodich";

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
            ps.setDouble(4, tx.getTongtien()); // SỬA: 'T' hoa
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
}