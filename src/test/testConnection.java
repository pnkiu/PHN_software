package test;

import database.JDBC_Util;
import java.sql.Connection;

public class testConnection {
    public static void main(String[] args) {
        Connection connection = JDBC_Util.getConnection();
        
        JDBC_Util.printInfo(connection);

        JDBC_Util.closeConnection(connection);
    }

}
