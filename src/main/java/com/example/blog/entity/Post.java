package com.example.blog.entity;

import jakarta.persistence.*;  //JPA（DBとJavaオブジェクトを対応させる仕組み）
import java.time.LocalDateTime;  //日時を扱うためのJavaの標準クラス。

//クラスレベル
@Entity //このクラスはDBのテーブルに対応するデータの型ですよ」とSpringに伝えるアノテーション
@Table(name="posts")  //DBテーブル名
public class Post {

	//フィールドレベル
	//idフィールド
	@Id //主キー（そのレコードを一意に識別するもの）
	@GeneratedValue(strategy = GenerationType.IDENTITY) //idの値をDB側（MySQLなどのAUTO_INCREMENT機能）で自動生成するよう指定。自分でidを指定する必要はなくなります。
	private Long id;  //Longは数値型の桁多め
	
	//titleフィールド
	@Column(nullable=false) // カラム（nullはダメ）
	private String title; //記事タイトル
	
	//contentフィールド
	@Lob // "Large OBject"の略。長い文章を保存するためのカラム型（DBだとTEXT型など）を使うよう指定　＜ー＞　@Basicもしくはかアノテーション不要
	private String content; //記事内容
	
	//statusフィールド
	@Enumerated(EnumType.STRING) //enum型を、DBには文字列として保存するよう指定。enumとは（列举型、Enumerationの略）。決まった選択肢の中からしか値を選べない型を作るためのJavaの機能。
	private PostStatus status; //PostStatusはenum型　記事の状態（公開か非公開かとか）
	private LocalDateTime createdAt; //作成日、投稿日時
	
	// getterとsetterはフィールド毎に必須
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
	public void setStatus(PostStatus status) {
		this.status = status;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt=createdAt;
	}
}
