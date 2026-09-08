package com.example.blog.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name="posts")  //DBテーブル名
public class Post {

//カラム
	@Id //
	@GeneratedValue(strategy = GenerationType.IDENTITY) //
	private Long id;  //Longは数値型の桁多め

	@Column(nullable=false) // 
	private String title;
	
	@Lob
	private String content; //
	
	@Enumerated(EnumType.STRING) //
	private PostStatus status; //
	private LocalDateTime createdAt; //作成日
	
	public Long getId() {
		return id;
	} //getterはこれが定型文
	public void setId(Long id) {
		this.id = id;
	} //setterもこれが定型文
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public PostStatus getStatus() {
		return status;
	}
	public void setStatus(Poststatus status) {
		this.status = status;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt=createdAt;
	}
}
