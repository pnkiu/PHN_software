package test;

import java.sql.Connection;

import database.JDBC_Util;


public class testConnect {
    public static void main(String[] args) {
        Connection connection = JDBC_Util.getConnection();
        JDBC_Util.printInfo(connection);
        JDBC_Util.closeConnection(connection);
    }

}
