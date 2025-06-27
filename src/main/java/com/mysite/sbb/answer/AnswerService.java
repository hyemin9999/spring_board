package com.mysite.sbb.answer;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.mysite.sbb.question.Question;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnswerService {

	// @RequiredArgsConstructor로 만들기 위해서는 private final를 꼭 사용해야 한다.
	private final AnswerRepository answerRepository;

	public void create(Question question, String content) {

		Answer answer = new Answer();

		answer.setContent(content);
		answer.setCreateDate(LocalDateTime.now());
		answer.setQuestion(question);

		this.answerRepository.save(answer);
	}
}
