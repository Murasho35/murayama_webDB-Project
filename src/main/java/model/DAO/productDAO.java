package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.entity.productBean;

public class productDAO {

	public ArrayList<Object> getAllProduct() throws SQLException {

		ArrayList<Object> productList = new ArrayList<>();

		String sql = "SELECT id, name, price, stock FROM product_tables";
		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {
			//rsは実行結果を行ごとに保持している

			while (rs.next()) {
				//条件式は基本boolean型
				//→trueなら実行されるから、全ての結果をBeanに詰めるまで実行
				productBean product = new productBean();
				product.setProductId(rs.getInt("id"));
				product.setProductName(rs.getString("name"));
				product.setProductPrice(rs.getInt("price"));
				product.setProductStock(rs.getInt("stock"));
				productList.add(product);
			}
			return productList;

		}
	}

	public boolean updateProduct() throws SQLException {

		//直下のやつはサーブレットでやるか
		List<productBean> productAndCategoryIdList = new ArrayList<>();

		//beanから値持ってきてupdate文を実行
		productBean product = new productBean();
		String sql = "UPDATE product_tables SET category_id = ?  WHERE id = ?; ";

		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setInt(1, product.getProductCtgrId());
			pstmt.setInt(2, product.getProductId());

			int updateResult = pstmt.executeUpdate();

			return updateResult > 0; // 1件以上登録できたらtrueを返す

		}
	}

	public productBean getProductById(int id) throws SQLException {

		productBean product = null;
		String sql = "SELECT id, name, price, stock, category_id FROM product_tables WHERE id = ?";

		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql)) {

			pstmt.setInt(1, id);

			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) { // 結果が1件あった場合
					product = new productBean();
					product.setProductId(rs.getInt("id"));
					product.setProductName(rs.getString("name"));
					product.setProductPrice(rs.getInt("price"));
					product.setProductStock(rs.getInt("stock"));

					// category_id の取得とNULL時の0詰め
					int categoryId = rs.getInt("category_id");
					if (rs.wasNull()) { // DBの値がNULLだった場合0入れたい
						product.setProductCtgrId(0); 
					} else {
						product.setProductCtgrId(categoryId);
					}
				}
			}
		}
		return product; // 取得したproductBeanを返す（見つからなければnull）
	}

}

//String sql = "SELECT * FROM product_tables LEFT OUTER JOIN category_tables ON product_tables.category_id = categories.id;";
//結合文メモ