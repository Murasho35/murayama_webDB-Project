package model.entity;

public class productBean {

	int ProductId;
	String ProductName;
	int ProductPrice;
	int ProductStock;
	int ProductCtgrId;
	
	
	public productBean() {}

	public int getProductId() {
		return ProductId;
	}

	public String getProductName() {
		return ProductName;
	}

	public int getProductPrice() {
		return ProductPrice;
	}

	public int getProductStock() {
		return ProductStock;
	}

	public int getProductCtgrId() {
		return ProductCtgrId;
	}
	
	public void setProductId(int productId) {
		ProductId = productId;
	}

	public void setProductName(String productName) {
		ProductName = productName;
	}

	public void setProductPrice(int productPrice) {
		ProductPrice = productPrice;
	}

	public void setProductStock(int productStock) {
		ProductStock = productStock;
	}

	public void setProductCtgrId(int productCtgrId) {
		ProductCtgrId = productCtgrId;
	}



	
}
