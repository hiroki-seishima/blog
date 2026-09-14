package com.example.blog.controller;

import com.example.blog.entity.Post;
import com.example.blog.service.PostService;
import org.springframework.web.bind.annotation.*; //GetMappingやPostMappingなどすべてのMappingで使える

import java.util.List;


@RestController  //このクラスはHTTPリクエストを受け取って、JSON形式でレスポンスを返すControllerですとSpringに伝えるアノテーション
@RequestMapping("/api/posts") //このクラス内の全メソッドが、/api/postsから始まるURLに対応することを指定。
public class PostController {

	//フィールド
	private final PostService postService;
	//コンストラクタ
	public PostController(PostService postService) {
		this.postService = postService;
	}
	
	@PostMapping  //「HTTPのPOSTメソッドでリクエストが来たら、このメソッドを実行する」という指定。@RequestMappingと組み合わさって、実際にはPOST /api/postsに対応する。
	public Post create(@RequestBody PostCreateRequest req) {  //リクエストのJSONデータ（{"title": "...", "content": "..."}）を、自動的にPostCreateRequestというJavaオブジェクトに変換。
		//＠Requestbodyが「HTTPリクエストのBody（本文）に入っているJSONデータを、指定した型のJavaオブジェクトに自動変換してください」とSpringに伝えるアノテーションです。reqは自分でつけた変数名
		return postService.create(req.getTitle(),req.getContent()); //Serviceに処理を丸投げして、その結果（保存されたPost）をそのままレスポンスとして返す。
	} 

	@GetMapping //「HTTPのGETメソッドでリクエストが来たら実行する」という指定。実際にはGET /api/postsに対応。
	public List<Post> list(){
		return postService.findPublished(); //ServiceのfindPublished()を呼んで、公開済みの記事一覧をそのまま返す。
	}
	//1件取得
	@GetMapping("/{id}")
	public Post getById(@PathVariable Long id) { //@PathVariable Long id -> URLの{id}の部分を受け取るアノテーション。例えば/api/posts/1にアクセスすると、idに1が入ります。
		return postService.findById(id);
	}
	//更新
	@PutMapping("/{id}")
	public Post update(@PathVariable Long id,@RequestBody PostUpdateRequest req) {
		return postService.update(id, req.getTitle(), req.getContent());
	}
	//削除
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		postService.delete(id);
	}
	//公開する
	@PutMapping("/{id}/publish")
	public Post publish(@PathVariable Long id) {
		return postService.publish(id);
	}
	// @GetMapping -> データを取得（一覧・詳細）
	// @PostMapping -> データを新規作成する
	// @PutMapping -> データを更新する
	// @DeleteMapping -> データを削除する
}
