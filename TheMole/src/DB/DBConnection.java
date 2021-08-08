package DB;

import java.sql.*;

public class DBConnection {
	
	public DBConnection() {
		try {
		Connection con = makeConnection();
		Statement stmt = con.createStatement();
		
		//stmt.executeUpdate("delete from users where id = \"\";"); // 없애는 문

		ResultSet rs = stmt.executeQuery("SELECT * FROM gamer");
		while(rs.next()) {
			String id = rs.getString("id");
			String password = rs.getString("password");
			System.out.println(id + " " + password);
		}
		rs.close();

		stmt.close();
		con.close();
		} catch(Exception e) { System.out.println("데이터 베이스 오류 : " + e.getMessage()); }
		
	}
	
	public static Connection makeConnection() {

		String url = "jdbc:mysql://localhost:3306/user?serverTimezone=Asia/Seoul";
		// sqldb는 database 이름, serverTimezone은 시간대 설정
		String id = "root";
		String password = "root";
		Connection con = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("드라이버 적재 성공");
			con = DriverManager.getConnection(url,id,password);
			System.out.println("데이터베이스 연결 성공");
		} catch(Exception e) {
			//System.out.println("데이터베이스 연결 오류 : " + e.getMessage());
		}
		return con;
	}
	public static void main(String arg[]) {
		new DBConnection();
	}
}
