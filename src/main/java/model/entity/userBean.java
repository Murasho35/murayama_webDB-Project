package model.entity;

import java.io.Serializable;

public class userBean implements Serializable{
	
	private String UserName;
	private String UserPassword;
	
	public userBean() {}

	public String getUserName() {
		return UserName;
	}

	public String getUserPassword() {
		return UserPassword;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public void setUserPassword(String userPassword) {
		UserPassword = userPassword;
	}

	
		
	}

	