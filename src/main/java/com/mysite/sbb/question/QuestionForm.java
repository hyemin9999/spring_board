package com.mysite.sbb.question;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionForm {

	@NotEmpty(message = "제목은 필수항목입니다.") // 값이 필수 // 에러메세지.
	@Size(max = 200) // 크기
	private String subject;
	@NotEmpty(message = "내용은 필수항목입니다.") // 공백(" ")은 허용하되, Null과 빈 문자열("")은 허용하지 않음
	private String content;
}
