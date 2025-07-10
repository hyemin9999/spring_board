package com.mysite.sbb.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class SiteUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; // Long 래퍼 class

	// 이름 == ID
	@Column(unique = true)
	private String username;

	// 비밀번호
	private String password;

	// 이메일
	@Column(unique = true)
	private String email;
}
