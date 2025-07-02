package model.entity;

public class CategoryBean {
	
	int CategoryId;
	String CategoryName;
	
	public CategoryBean() {};
	
	public int getCategoryId() {
		return CategoryId;
	}
	public String getCategoryName() {
		return CategoryName;
	}
	public void setCategoryId(int categoryId) {
		CategoryId = categoryId;
	}
	public void setCategoryName(String categoryName) {
		CategoryName = categoryName;
	}
	
	

}
