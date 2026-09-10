package com.example.blog.service;


import com.example.blog.entity.Post;
import com.example.blog.entity.PostStatus;
import com.example.blog.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime; //日時を扱うためのJavaの標準クラス。
import java.util.List; //リスト

//クラスレベルで設定
@Service //ビジネスロック（実際の処理内容）を担当するクラス　これをつけることによりこのクラスのインスタンスを１つつくって管理してくれるようになる。->この仕組みをDI(依存性注入)コンテナで管理されるという
public class PostService {
	//フィイールド
	private final PostRepository postRepository;
	//コンストラクタで受け取る
	public PostService(PostRepository postRepository) {
		this.postRepository = postRepository;
	}

	public Post create(String title,String content) {
		Post post = new Post(); //空のPostオブジェクトを作る
		post.setTitle(title); //タイトルを引数で受け取った値で埋める
		post.setContent(content); //本文を、引数で受け取った値で埋める
		post.setStatus(PostStatus.DRAFT); //ステータスは新規作成時は自動的にDRAFT（下書き）にする
		post.setCreatedAt(LocalDateTime.now()); //作成日時を現在時刻で記録
		return postRepository.save(post);  //DBに保存
	}
	
	public List<Post> funndPublished(){ //PostRepositoryのfindByStatusメソッドを使って、PUBLISHED(公開済み)の記事だけを取得。公開済みの記事だけを一覧表示したいという要件を表現
		return postRepository.findByStatus(PostStatus.PUBLISHED); 
	}
}
