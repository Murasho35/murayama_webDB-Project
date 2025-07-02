package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import model.entity.productBean;

public class productDAO {

	public List<productBean> getAllProduct() throws SQLException {

		List<productBean> productList = new ArrayList<>();

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
				// category_id の取得とNULL時の0詰め
				int categoryId = rs.getInt("category_id");
				if (rs.wasNull()) { // DBの値がNULLだった場合0入れたい
					product.setProductCtgrId(0);
				} else {
					product.setProductCtgrId(categoryId);
				}
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

	public boolean addProduct(productBean product) throws SQLException {
        // SQL文：idは自動生成されるため、INSERT文に含めない
        String sql = "INSERT INTO product_tables (name, price, stock, category_id) VALUES (?, ?, ?, ?)";

        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            // PreparedStatementに値をセット
            pstmt.setString(1, product.getProductName());  // 商品名
            pstmt.setInt(2, product.getProductPrice());    // 価格
            pstmt.setInt(3, product.getProductStock());    // 在庫数

            // カテゴリIDの処理：productCtgrIdが0の場合はデータベースにNULLをセット
            if (product.getProductCtgrId() == 0) {
                pstmt.setNull(4, Types.INTEGER); // INT型のNULLをセット
            } else {
                pstmt.setInt(4, product.getProductCtgrId()); // カテゴリID
            }

            // SQLを実行し、影響を受けた行数を取得
            int affectedRows = pstmt.executeUpdate();

            // 1行以上が挿入されたら成功とみなす
            return affectedRows > 0;

        } 
	}

	public List<productBean> getAllProductsAndCategoryName() throws SQLException {
        List<productBean> productList = new ArrayList<>();

        // LEFT JOIN を使用して、カテゴリが設定されていない商品も表示
        String sql = "SELECT product_tables.name AS ProductName, product_tables.price AS Price, product_tables.stock AS Stock, category_tables.name AS CategoryName FROM product_tables LEFT JOIN category_tables ON product_tables.category_id = category_tables.id";

        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                productBean product = new productBean();
                product.setProductId(rs.getInt("id"));
                product.setProductName(rs.getString("productName"));
                product.setProductPrice(rs.getInt("price"));
                product.setProductStock(rs.getInt("stock"));

                // カテゴリ名を取得。DBがNULLの場合は"カテゴリなし"とする
                String categoryName = rs.getString("categoryName");
                if (rs.wasNull()) { // DBのcategoryNameがNULL（LEFT JOINでマッチしなかった場合）
                    product.setProductCtgrName("カテゴリなし");
                } else {
                    product.setProductCtgrName(categoryName);
                }
                productList.add(product);
            }
        }
        return productList;
    }
}

//String sql = "SELECT * FROM product_tables LEFT OUTER JOIN category_tables ON product_tables.category_id = category_tables.id;";
//結合文メモ