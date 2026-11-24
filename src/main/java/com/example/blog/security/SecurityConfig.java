package com.example.blog.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final JwtAuthFilter jwtAuthFilter;

	public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
		this.jwtAuthFilter = jwtAuthFilter;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.csrf(csrf -> csrf.disable())  //csrf（Cross-Site Request Forgery）対策を無効化
			.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //Spring Securityがセッションを作成・利用しないよう設定
			.headers(h -> h.frameOptions(f -> f.sameOrigin())) //自分のサイト内からのiframe表示を許可
			.authorizeHttpRequests(auth -> auth
				.requestMatchers("/api/auth/**", "/h2-console/**","/error").permitAll() //指定URLは誰でもアクセスできるよう設定。認証APIとH2データベースコンソール、エラーページ
				.requestMatchers(HttpMethod.GET, "/api/posts").permitAll() //指定URLは誰でもアクセスできるよう設定。GETのページのapi/postsのページは誰でも許可。
				.anyRequest().authenticated()) //それ以外のURLは認証済みユーザーのみアクセスできる設定。
			.exceptionHandling(e -> e.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
			.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class); //JWT検証フィルターを、ユーザー名・パスワード認証フィルターより前に実行する。
		return http.build();
	}
}
