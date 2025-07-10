package com.mysite.sbb.answer;

import java.time.LocalDateTime;
import java.util.Set;

import com.mysite.sbb.question.Question;
import com.mysite.sbb.user.SiteUser;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Answer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	// 내용
	@Column(columnDefinition = "TEXT")
	private String content;

	// 등록날짜
	private LocalDateTime createDate;

	// 답변이 달려야 하는 질문
	@ManyToOne
	private Question question;

	// 작성자
	@ManyToOne
	private SiteUser author;

	// 수정 날짜
	private LocalDateTime modifyDate;

	// 추천
	@ManyToMany
	Set<SiteUser> voter;
}
