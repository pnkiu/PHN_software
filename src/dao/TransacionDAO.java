package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;

public class TransacionDAO {

    private static TransacionDAO instance;
    private TransacionDAO() {
    }

    // 2. Phương thức getInstance() công khai (public static)
    public static TransacionDAO getInstance() {
        if (instance == null) {
            instance = new TransacionDAO();
        }
        return instance;
    }
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
        }
