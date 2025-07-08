package com.mysite.sbb.answer;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.mysite.sbb.question.Question;
import com.mysite.sbb.user.SiteUser;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnswerService {

	// @RequiredArgsConstructor로 만들기 위해서는 private final를 꼭 사용해야 한다.
	private final AnswerRepository answerRepository;

	// 답글 등록 페이지 // 글쓴이 추가로 인해 return값이 void-->Answer로 변경됨.
	public Answer create(Question question, String content, SiteUser author) {

		Answer answer = new Answer();

		answer.setContent(content);
		answer.setCreateDate(LocalDateTime.now());
		answer.setQuestion(question);

		// 글쓴이 등록
		answer.setAuthor(author);

		this.answerRepository.save(answer);

		return answer;
	}
}
