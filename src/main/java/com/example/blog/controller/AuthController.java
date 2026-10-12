package com.example.blog.controller; //Authentication（認証）
//会員登録・ログインに関するリクエストの窓口

import com.example.blog.entity.User;
import com.example.blog.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	private final AuthService authService;
	
	public AuthController(AuthService authService) {
		this.authService = authService;
	}
	
	@PostMapping("/register")
	public UserResponse register(@RequestBody AuthRequest req) {
		User user = authService.register(req.getUsername(), req.getPassword());
		return UserResponse.from(user);
	}
	
}
