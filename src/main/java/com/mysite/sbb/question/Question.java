package com.mysite.sbb.question;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.user.SiteUser;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Question {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	// 제목
	@Column(length = 200)
	private String subject;

	// 내용
	@Column(columnDefinition = "TEXT")
	private String content;

	// 등록날짜
	private LocalDateTime createDate;

	// 해당 질문에 있는 답변 목록
	@OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
	private List<Answer> answerList;

	// 작성자
	@ManyToOne
	private SiteUser author;

	// 수정날짜
	private LocalDateTime modifyDate;

	// 추천
	// Set ==> 집합 ==> 중복된 요소를 허용하지 않는 컬렉션, 수학에서의 집합과 유사, 순서가 보장되지 않음.
	// 하나의 질문에 여러개의 추천이 있을수 있고, 추천은 여러개의 질문에 있을수 있다.
	// M:M의 경우 새로운 테이블이 존재 해야 한다.
	@ManyToMany
	Set<SiteUser> voter;
}
