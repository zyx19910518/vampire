package com.zhangyx.dbcp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TestDBCPFunctionsByTimes implements Runnable {

	private long dateTime1 = 0;
	private static int count = 0;

	public static void main(String[] args) {
		// System.out.println("main start");
		TestDBCPFunctionsByTimes test = new TestDBCPFunctionsByTimes();
		test.startup();
		// System.out.println("main over");
	}

	public void startup() {
		for (int i = 0; i < 80; i++) {
			Thread t = new Thread(this);
			t.start();
		}
	}

	@Override
	public void run() {
		if (dateTime1 == 0) { // 当第一个线程进入时开始记录时间
			dateTime1 = System.currentTimeMillis();
			System.out.println("dateTime1 is " + dateTime1);
		}
		String sql = "select ID,username from user where ID=?";// 自己写
		try {
			for (int i = 0; i < 10; i++) {
				Connection conn = ConnectionSource.getConnection(); // ConnectionUtils工具类在我的博客之中
				// Connection conn=NormalConnectionUtils.getConnection();
				PreparedStatement pstm = conn.prepareStatement(sql);
				pstm.setInt(1, 1);
				ResultSet rs = pstm.executeQuery();
				while (rs.next()) {
					String accountID = rs.getString("ID");
					String accountName = rs.getString("username");
					System.out.println("accountID is "+accountID+" ,  accountName is "+accountName);
				}
				close(rs, pstm, conn);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		count++;
		if (count == 80) {
			long dateTime2 = System.currentTimeMillis();
			long result = dateTime2 - dateTime1;
			System.out.println("dateTime2 is " + dateTime2);
			System.out.println(count + "个线程平均每个线程做10次访问数据库的中的时间为 " + result);
		}
	}
	
	public void close(ResultSet rs, PreparedStatement pstm, Connection conn) throws SQLException{
		if(rs!=null && !rs.isClosed()){
			rs.close();
		}
		if(pstm!=null && !pstm.isClosed()){
			pstm.close();
		}
		if(conn!=null && !conn.isClosed()){
			conn.close();
		}
	}
}