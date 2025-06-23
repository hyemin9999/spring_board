package com.mysite.sbb;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Question {
	// Integer ==> int 타입을 객체화 시키는...
	@Id // pk로 설정
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 자동으로 순번 적용
	private Integer id; // 추후 id --> null check --> Integer 사용

	@Column(length = 200) // 200자로 정의
	private String subject;
	@Column(columnDefinition = "TEXT") // "TEXT" 길이 제한이 없는.
	private String content;

	// createDate --> Table 매핑시 이름 --> create_date 이름이 변경됨. ==> 카멜표기법작성 --> _소문자로
	// 변경됨.
	private LocalDateTime createDate;

	// cascade = CascadeType.REMOVE --> question 삭제시 연관된 answer 삭제되도록 설정
	@OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE) // question 기준 answer와의 관계 --> 1:M --> OneToMany
	private List<Answer> answerList;

}
