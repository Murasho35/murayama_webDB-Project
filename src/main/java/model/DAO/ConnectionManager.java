package model.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

	static String DATABASE_NAME = "web_product_management";
	static String PROPATIES = "?characterEncoding=UTF-8&useSSL=false&allowPublicKeyRetrieval=true";
	static String URL = "jdbc:mySQL://localhost/" + DATABASE_NAME + PROPATIES;
	//DB接続用・ユーザ定数
	static String USER = "root";
	static String PASS = "SEInama2011";
	
		//データベースに接続
		public static Connection getConnection() throws SQLException{
		
		return DriverManager.getConnection(URL, USER, PASS);
		}
}
