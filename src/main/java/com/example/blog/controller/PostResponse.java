package com.example.blog.controller; //Postをそのまま返すと、author（User）ごとJSONに含まれ、ハッシュ化したパスワードまで外に出てしまいます。
//PostResponseは、返してよい項目だけを持つ入れ物です。

import com.example.blog.entity.Post;

import java.time.LocalDateTime;

public class PostResponse { //外に返すデータの形（投稿者はauthorNameのユーザー名だけ）
	private Long id;
	private String title;
	private String content;
	private String status;
	private LocalDateTime createdAt;
	private String authorName;

	public static PostResponse from(Post post) {
		PostResponse res = new PostResponse();
		res.id = post.getId();
		res.title = post.getTitle();
		res.content = post.getContent();
		res.status = post.getStatus().name();
		res.createdAt = post.getCreatedAt();
		res.authorName = post.getAuthor().getUsername();
		return res;
	}

	public Long getId() {
		return id;
	}
	public String getTitle() {
		return title;
	}
	public String getContent() {
		return content;
	}
	public String getStatus() {
		return status;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public String getAuthorName() {
		return authorName;
	}
}