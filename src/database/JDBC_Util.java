package database;


import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBC_Util {
	public static Connection getConnection() {
		Connection c = null;
		try {
			
			// Các thông số
			String url = "jdbc:mysql://localhost:3306/qlchoto";
			String username = "root";
			String password = "Ngocho";
						
			// Tạo kết nối
			c = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			System.out.println("Lỗi: Không tìm thấy MySQL JDBC Driver!");
			e.printStackTrace();
		}
		
		return c;
	}
	public static void closeConnection(Connection c) {
		try {
			if(c!=null) {
				c.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void printInfo(Connection c) {
		 try {
			if (c!=null) {
				DatabaseMetaData mtdt = c.getMetaData();
				System.out.println(mtdt.getDatabaseProductName());
				System.out.println(mtdt.getDatabaseProductVersion());
			} 
		 	}catch (SQLException e) {
				e.printStackTrace();
			}
		}

}
