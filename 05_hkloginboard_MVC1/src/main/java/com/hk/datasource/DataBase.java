package com.hk.datasource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//JDBC 1,2,6단계 구현
public class DataBase {
	
	//1단계: 드라이버로딩
	public DataBase() {
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			System.out.println("1단계: 드라이버로딩성공");
		} catch (ClassNotFoundException e) {
			System.out.println("1단계: 드라이버로딩실패");
			e.printStackTrace();
		}
	}
	
	//2단계: DB연결
	public Connection getConnection() throws SQLException {
		String url = "jdbc:mariadb://localhost:3307/hkeduweb";
		String user ="root";
		String password = "manager";     
		
		Connection conn = DriverManager.getConnection(url, user, password);
		System.out.println("2단계: DB성공");
		return conn;
	}
	//6단계: DB연결 닫기
	public void close(ResultSet rs, PreparedStatement psmt, Connection conn) {
		try {
			if(rs!=null) {
				rs.close();
			}
			if(psmt!=null) {
				psmt.close();
			}
			if(conn!=null) {
				conn.close(); 		
			}
			System.out.println("6단계: DB닫기성공");
		} catch (SQLException e) {
			System.out.println("6단계: DB닫기실패");
			e.printStackTrace();
		}
	}
}
