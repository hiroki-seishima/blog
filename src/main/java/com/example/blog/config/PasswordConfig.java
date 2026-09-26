package com.example.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration; //このクラスは、アプリ全体の設定を行うクラスですよ。とSpringに伝えるアノテーション。
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordConfig {

    @Bean //「このメソッドが返すオブジェクト（BCryptPasswordEncoder）を、Springが管理する共有部品として登録してください」という意味。
    	  //これにより、他のクラス（AuthControllerなど）で、このPasswordEncoderをDI（依存性注入）で受け取って使えるようになります。　他のクラスではコンストラクタを入れるだけで使用可能
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}