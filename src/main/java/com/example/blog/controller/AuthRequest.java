package com.example.blog.controller;
// リクエストの受け皿
//会員登録・ログインで受け取るデータ（username, password）の入れ物

public class AuthRequest {
	private String username;
	private String password;
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	
}
