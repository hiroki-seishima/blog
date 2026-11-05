package com.example.blog.service; //Authentication（認証）
//会員登録・ログインの中身の処理（パスワードのハッシュ化など）

import com.example.blog.entity.UserRole;
import com.example.blog.entity.User;
import com.example.blog.repository.UserRepository;
import com.example.blog.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {	
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtil jwtUtil;
	
	public AuthService(UserRepository userRepository,PasswordEncoder passwordEncoder,JwtUtil jwtUtil) {
		this.userRepository=userRepository;
		this.passwordEncoder=passwordEncoder;
		this.jwtUtil=jwtUtil;
	}
	
	public User register(String username,String rawPassword) {
		User user = new User();
		user.setUsername(username);
		user.setPassword(passwordEncoder.encode(rawPassword));
		user.setRole(UserRole.AUTHOR);
		return userRepository.save(user);
	}
	
	public String login(String username,String rawPassword) { //ログインのメソッド
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new RuntimeException("ユーザー名またはパスワードが違います"));
		
		if (!passwordEncoder.matches(rawPassword, user.getPassword())) {  //もしパスワードが一致しなければパスワードが違いますと表示
			throw new RuntimeException("ユーザー名またはパスワードが違います。");
		}
		
		return jwtUtil.generateToken(user.getUsername(),user.getRole().name());
	}
}
