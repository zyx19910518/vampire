package com.zhangyx.dbcp;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;

public class ConnectionSource {
    private static BasicDataSource dataSource = null;

    public ConnectionSource() {
    }

    public static void init() {

        if (dataSource != null) {
            try {
                dataSource.close();
            } catch (Exception e) {
                //
            }
            dataSource = null;
        }

        try {
            Properties p = new Properties();
            p.setProperty("driverClassName", "com.mysql.jdbc.Driver");
            p.setProperty("url", "jdbc:mysql://localhost:3306/vampire?useUnicode=true&amp;charactorEncoding=utf8");
            p.setProperty("password", "");
            p.setProperty("username", "root");
            p.setProperty("maxActive", "30");
            p.setProperty("maxIdle", "30");
            p.setProperty("maxWait", "1000");
            p.setProperty("removeAbandoned", "false");
            p.setProperty("removeAbandonedTimeout", "120");
            p.setProperty("testOnBorrow", "true");
            p.setProperty("logAbandoned", "true");

            dataSource = (BasicDataSource) BasicDataSourceFactory.createDataSource(p);

        } catch (Exception e) {
            //
        }
    }


    public static synchronized Connection getConnection() throws  SQLException {
        if (dataSource == null) {
            init();
        }
        Connection conn = null;
        if (dataSource != null) {
            conn = dataSource.getConnection();
        }
        return conn;
    }
    
    public static void main(String[] args) throws SQLException {
    	Connection conn = ConnectionSource.getConnection();
    	Statement statement = conn.createStatement();
    	ResultSet rs = statement.executeQuery("select * from user");
    	while(rs.next()){
    		System.out.println(rs.getInt("id"));
    		System.out.println(rs.getString("username"));
    	}
	}
}