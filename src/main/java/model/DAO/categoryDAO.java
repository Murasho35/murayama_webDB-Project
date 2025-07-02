package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.entity.CategoryBean;

public class categoryDAO {

	public List<CategoryBean> getAllCategory() throws SQLException {

		List<CategoryBean> categoryList = new ArrayList<>();

		String sql = "SELECT id, name FROM category_tables";
		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {
			//rsは実行結果を行ごとに保持している

			while (rs.next()) {
				//条件式は基本boolean型
				//→trueなら実行されるから、全ての結果をBeanに詰めるまで実行
				CategoryBean category = new CategoryBean();
				category.setCategoryId(rs.getInt("id"));
				category.setCategoryName(rs.getString("name"));
				
				categoryList.add(category);
			}
			return categoryList;

		}

	}
	
	public CategoryBean getCategoryById(int id) throws SQLException {

		CategoryBean category = null;
		String sql = "SELECT id, name FROM category_tables WHERE id = ?";

		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql)) {

			pstmt.setInt(1, id);

			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) { // 結果が1件あった場合
					category = new CategoryBean();
					category.setCategoryId(rs.getInt("id"));
					category.setCategoryName(rs.getString("name"));
					
					
				}
			}
		}
		return category; 
	}
}
