package com.example.blog.controller;

import com.example.blog.entity.User;

public class UserResponse {
	private Long id;
	private String username;
	private String role;
	
	public static UserResponse from(User user) {
		UserResponse res = new UserResponse();
		res.id = user.getId();
		res.username = user.getUsername();
		res.role = user.getRole().name();
		return res;
	}
	
	public Long getId() {
		return id;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getRole(){
		return role;
	}
}
