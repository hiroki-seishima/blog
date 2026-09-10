package com.example.blog.repository;//DB操作の手段だけを提供

import com.example.blog.entity.Post; //Post.javaと連携
import com.example.blog.entity.PostStatus; //PostStatus.javaと連携
import org.springframework.data.jpa.repository.JpaRepository; //JpaRepositoryという、Spring Data JPAが用意している「DB操作の基本機能一式」を継承するためのアノテーション
import java.util.List;  //リスト

public interface PostRepository extends JpaRepository<Post, Long> {  //JpaRepositoryという、Spring Data JPAが用意している「DB操作の基本機能一式」を継承。また、<対象のEntity, そのEntityの主キーの型>
	//この形は決まり

	//これらが自動で反映
	//postRepository.save(post);          // 保存・更新
	//postRepository.findById(1L);        // idで1件検索
	//postRepository.findAll();           // 全件取得
	//postRepository.deleteById(1L);      // idで削除
	//postRepository.count();             // 件数取得
	List<Post> findByStatus(PostStatus status); //"公開済みの記事だけ取得したい"というブログ特有の要件に対応するためのメソッド
	//SQLで言うと->SELECT * FROM posts WHERE status = ?
	
	//このような指示もあり
//	findByTitle(String title);                          // タイトルで検索
//	findByTitleContaining(String keyword);               // タイトルに部分一致
//	findByCreatedAtAfter(LocalDateTime date);            // 指定日時より後の記事
//	findByStatusAndTitleContaining(PostStatus status, String keyword); // 複数条件
	
	
	
}


