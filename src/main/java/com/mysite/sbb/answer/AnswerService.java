package com.mysite.sbb.answer;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mysite.sbb.DataNotFoundException;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.user.SiteUser;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnswerService {

	// @RequiredArgsConstructor로 만들기 위해서는 private final를 꼭 사용해야 한다.
	private final AnswerRepository answerRepository;

	/**
	 * 답글 등록 // 글쓴이 추가로 인해 return값이 void-->Answer로 변경됨.
	 * 
	 * @param question 질문
	 * @param content  내용
	 * @param author   글쓴이 ==> 로그인 사용자
	 * @return 등록한 답글 항목
	 */
	public Answer create(Question question, String content, SiteUser author) {

		Answer item = new Answer();

		item.setContent(content);
		item.setCreateDate(LocalDateTime.now());
		item.setQuestion(question);

		// 글쓴이 등록
		item.setAuthor(author);

		this.answerRepository.save(item);

		return item;
	}

	/**
	 * 
	 * */
	public Answer getItem(Integer id) {
		Optional<Answer> item = this.answerRepository.findById(id);

		if (item.isPresent()) {
			return item.get();
		} else {
			throw new DataNotFoundException("answer not found");
		}

	}

	/**
	 * 
	 * */
	public void modify(Answer item, String content) {

		item.setContent(content);
		item.setModifyDate(LocalDateTime.now());

		this.answerRepository.save(item);
	}

	public void delete(Answer item) {
		this.answerRepository.delete(item);
	}
}
