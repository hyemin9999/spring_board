package com.mysite.sbb;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

	private String content;

	private String createDate;

	@ManyToOne // answer 기준으로 question 과의 관계는 M:1 ==> ManyToOne --> Question에도 작성. // 중요
	private Question question; // question 테이블의 id와의 관계 정의 (FK) // 중요
}
