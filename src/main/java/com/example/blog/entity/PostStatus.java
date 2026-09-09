package com.example.blog.entity;

public enum PostStatus { //普通にStringだと自由に文字を入れられるため、タイポ（入力ミスや打ち間違いのこと）を避けるためにPostStatus型を設定。
	DRAFT, //下書き状態
	PUBLISHED //公開状態に変更
}
//enumは他にもある。必要に応じて作成が必要。enum１つにつき１ファイルで作成したほうが管理は楽(パッケージは揃えることは必須)
//(例)
//com.example.blog.entity
//├── Post.java
//├── PostStatus.java　->　enum
//├── PostVisibility.java -> enum
//├── User.java
//└── UserRole.java -> enum