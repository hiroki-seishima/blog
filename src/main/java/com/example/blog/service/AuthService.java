package com.example.blog.service; //Authentication（認証）s
//会員登録・ログインの中身の処理（パスワードのハッシュ化など）

import com.example.blog.entity.UserRole;
import com.example.blog.entity.User;
import com.example.blog.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {	
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	public AuthService(UserRepository userRepository,PasswordEncoder passwordEncoder) {
		this.userRepository =userRepository;
		this.passwordEncoder= passwordEncoder;
	}
	
	public User register(String username,String rawPassword) {
		User user = new User();
		user.setUsername(username);
		user.setPassword(passwordEncoder.encode(rawPassword));
		user.setRole(UserRole.AUTHOR);
		return userRepository.save(user);
	}
}
