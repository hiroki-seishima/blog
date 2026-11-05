package com.example.blog.repository; // DB操作の手段だけを提供　ユーザーのDB操作

import com.example.blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    
    
    // Optionalの中身
    // .isPresent() 　   中身があるかの確認
    // .get			　　 中身を強制的に取り出す（空だとエラーになるため注意がいる）
    // .orElseThrow()　　空なら例外を投げる、あれば中身を返す
    // .orElse(デフォルト値)　　空ならデフォルト値を返す、あれば中身を返す		
    // .isEmpty()　　　　　中身が空かどうかの確認(true/false)
}