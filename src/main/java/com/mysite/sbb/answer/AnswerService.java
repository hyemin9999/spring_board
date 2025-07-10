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

	private final AnswerRepository answerRepository;

	/**
	 * 답변 등록 // 글쓴이 추가로 인해 return값이 void-->Answer로 변경됨.
	 * 
	 * @param question 질문
	 * @param content  내용
	 * @param author   글쓴이 ==> 로그인 사용자
	 * 
	 * @return 등록한 답변 항목
	 */
	public Answer create(Question question, String content, SiteUser author) {

		Answer item = new Answer();

		item.setContent(content);
		item.setCreateDate(LocalDateTime.now());
		item.setQuestion(question);
		item.setAuthor(author);

		this.answerRepository.save(item);

		return item;
	}

	/**
	 * 답변 수정/삭제를 위한 답변 항목 반환
	 * 
	 * @param id 답변 id
	 * 
	 * @return 답변 항목
	 */
	public Answer getItem(Integer id) {

		Optional<Answer> item = this.answerRepository.findById(id);

		if (item.isPresent()) {
			return item.get();
		} else {
			throw new DataNotFoundException("answer not found");
		}

	}

	/**
	 * 답변 수정
	 * 
	 * @param item    답변
	 * @param content 내용
	 */
	public void modify(Answer item, String content) {

		item.setContent(content);
		item.setModifyDate(LocalDateTime.now());

		this.answerRepository.save(item);
	}

	/**
	 * 답변 삭제
	 * 
	 * @param item 답변
	 */
	public void delete(Answer item) {

		this.answerRepository.delete(item);
	}

	/**
	 * 답변 추천 저장
	 * 
	 * @param answer 답변
	 * @param user   추천한 사용자
	 */
	public void vote(Answer item, SiteUser user) {

		item.getVoter().add(user);

		this.answerRepository.save(item);
	}
}
