package com.example.blog.service;

import com.example.blog.entity.Post;
import com.example.blog.entity.PostStatus;
import com.example.blog.entity.User;
import com.example.blog.entity.UserRole;
import com.example.blog.repository.PostRepository;
import com.example.blog.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.access.AccessDeniedException;


import java.time.LocalDateTime; //日時を扱うためのJavaの標準クラス。
import java.util.List; //リスト

//クラスレベルで設定
@Service //ビジネスロック（実際の処理内容）を担当するクラス　これをつけることによりこのクラスのインスタンスを１つつくって管理してくれるようになる。->この仕組みをDI(依存性注入)コンテナで管理されるという
public class PostService {
	//フィールド
	private final PostRepository postRepository;
	private final UserRepository userRepository;
	//コンストラクタで受け取る
	public PostService(PostRepository postRepository, UserRepository userRepository) {
		this.postRepository = postRepository;
		this.userRepository = userRepository;
	}

	public Post create(String title,String content,String username) { //投稿記事を作成からDB保存までのメソッド
		User author = userRepository.findByUsername(username)
				.orElseThrow(() -> new RuntimeException("ユーザーが見つかりません"));//orElseThrowは例外を投げるかどうかを決めるメソッド
		
		Post post = new Post(); //空のPostオブジェクトを作る
		post.setTitle(title); //タイトルを引数で受け取った値で埋める
		post.setContent(content); //本文を、引数で受け取った値で埋める
		post.setStatus(PostStatus.DRAFT); //ステータスは新規作成時は自動的にDRAFT（下書き）にする
		post.setCreatedAt(LocalDateTime.now()); //作成日時を現在時刻で記録
		post.setAuthor(author); //投稿者を設定
		return postRepository.save(post);  //DBに保存
	}
	//公開済の記事のみを取得するメソッド
	public List<Post> findPublished(){ //PostRepositoryのfindByStatusメソッドを使って、PUBLISHED(公開済み)の記事だけを取得。公開済みの記事だけを一覧表示したいという要件を表現
		return postRepository.findByStatus(PostStatus.PUBLISHED); 
	}
	
	//1件取得
	public Post findById(Long id) {
		return postRepository.findById(id) //postRepository.findById(id)は、JpaRepositoryが最初から持っている機能。ただし戻り値はPostではなくOptional<Post>という「値があるかもしれないし、ないかもしれない箱」型で返ってきます。
			.orElseThrow(() -> new RuntimeException("記事が見つかりません: id=" + id)); //.orElseThrow(...) — 「もし中身が空だったら（＝該当idの記事がなければ）、例外を投げる」という意味。存在しないidを指定された時に備えたエラーハンドリングです。
	}
	
	//閲覧用の1件取得（下書きは、投稿者本人と管理者だけ）
	public Post findForView(Long id, String username) {
		Post post = findById(id); //idで記事を取得（無ければ例外）
		if (post.getStatus() == PostStatus.DRAFT && !canManage(post, username)) { // 記事が下書きであり、本人も管理者でもない。
			throw new AccessDeniedException("下書きは投稿者本人のみ閲覧できます");
		}
		return post;
	}
	
	//更新
	public Post update(Long id,String title,String content,String username) {
		Post post = findById(id); //idで記事を取得
		checkOwner(post,username); //投稿者本人か確認
		post.setTitle(title); //タイトルをセット
		post.setContent(content); //本文をセット
		return postRepository.save(post); //上記の更新のための保存
	}
	//削除
	public void delete(Long id,String username) {
		Post post = findById(id); //idで記事を取得
		checkOwner(post,username);//投稿者本人か確認
		postRepository.delete(post); //DBから削除
	}
	//公開する
	public Post publish(Long id,String username) {
		Post post = findById(id); //idで記事を取得
		checkOwner(post,username);//投稿者本人か確認
		post.setStatus(PostStatus.PUBLISHED); //公開済のステータスをセット
		return postRepository.save(post); //上記ステータスの更新のための保存
	}
	
	//管理者かどうか（DBのユーザー情報で判定する）
	private boolean isAdmin(String username) {
		return userRepository.findByUsername(username) //①ユーザーを探す（結果はOptionalの箱）
				.map(user -> user.getRole() == UserRole.ADMIN)//②箱の中身をtrue/falseに
				.orElse(false); //　③箱が空ならfalseにする。
	}
	
	//この記事を扱ってよいか（投稿者本人、または管理者）
	private boolean canManage(Post post, String username) {
		return post.getAuthor().getUsername().equals(username) || isAdmin(username);
	}
	
	//本人（または管理者）の記事かどうかを確認する（違った場合は例外処理発生）
	private void checkOwner(Post post, String username) {  //privateはこのクラスのみで使うメソッドって意味
		if (!canManage(post, username)) {
			throw new AccessDeniedException("他人の記事は操作できません"); // AccessDeniedException は、Spring Securityにある「権限がない」ことを表す例外。403 Forbidden で返せます。
		}
	}
}
