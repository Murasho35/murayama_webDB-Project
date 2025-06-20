package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class userDAO {
	public boolean getLogin(String name, String password) throws SQLException {
		
		String sql = "SELECT id FROM user_tables WHERE name=? AND password=?";
		boolean loginResult = false ;

		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql)) {

			 pstmt.setString(1, name);
	         pstmt.setString(2, password);
	         
	         ResultSet  rs = pstmt.executeQuery();
				
	         
	         if (rs.next()) {
	        	loginResult = true;
	         }

	        }
		
		return loginResult;
	    }
}
