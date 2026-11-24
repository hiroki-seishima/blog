package com.example.blog.controller;
//記事に関するリクエストの窓口

import com.example.blog.entity.Post;
import com.example.blog.service.PostService;
import org.springframework.web.bind.annotation.*; //GetMappingやPostMappingなどすべてのMappingで使える
import org.springframework.security.core.Authentication;

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
	public PostResponse create(@RequestBody PostCreateRequest req,Authentication authentication) {  //リクエストのJSONデータ（{"title": "...", "content": "..."}）を、自動的にPostCreateRequestというJavaオブジェクトに変換。
		//＠Requestbodyが「HTTPリクエストのBody（本文）に入っているJSONデータを、指定した型のJavaオブジェクトに自動変換してください」とSpringに伝えるアノテーションです。reqは自分でつけた変数名
		Post post = postService.create(req.getTitle(), req.getContent(), authentication.getName());
		return PostResponse.from(post); //Serviceに処理を丸投げして、その結果（保存されたPost）をPostResponseに変換して返す（passwordを含めないため）
	}

	@GetMapping //「HTTPのGETメソッドでリクエストが来たら実行する」という指定。実際にはGET /api/postsに対応。
	public List<PostResponse> list(){
		return postService.findPublished().stream().map(PostResponse::from).toList(); //ServiceのfindPublished()を呼んで、公開済みの記事一覧を１件ずつPostResponseに変換して返す（passwordを含めないため）
	}
	//1件取得
	@GetMapping("/{id}")
	public PostResponse getById(@PathVariable Long id,Authentication authentication) { //@PathVariable Long id -> URLの{id}の部分を受け取るアノテーション。例えば/api/posts/1にアクセスすると、idに1が入ります。
		return PostResponse.from(postService.findForView(id,authentication.getName()));
	}
	//更新
	@PutMapping("/{id}")
	public PostResponse update(@PathVariable Long id,@RequestBody PostUpdateRequest req,Authentication authentication) {
		return PostResponse.from(postService.update(id, req.getTitle(), req.getContent(),authentication.getName()));
	}
	//削除
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id,Authentication authentication) {
		postService.delete(id,authentication.getName());
	}
	//公開する
	@PutMapping("/{id}/publish") //
	public PostResponse publish(@PathVariable Long id,Authentication authentication) {
		return PostResponse.from(postService.publish(id,authentication.getName()));
	}
	// @GetMapping -> データを取得（一覧・詳細）
	// @PostMapping -> データを新規作成する
	// @PutMapping -> データを更新する
	// @DeleteMapping -> データを削除する
}
