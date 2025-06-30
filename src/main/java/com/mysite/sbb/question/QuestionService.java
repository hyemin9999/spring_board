package com.mysite.sbb.question;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mysite.sbb.DataNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class QuestionService {

	private final QuestionRepository questionRepository;

	public List<Question> getList() {

		return this.questionRepository.findAll();
	}

	public Question getQuestion(Integer id) {

		// 가져온 값이 없을경우(=null)를 체크하기 위해. Optional 사용
		Optional<Question> q = this.questionRepository.findById(id);

		if (q.isPresent()) {
			return q.get();
		} else {
			throw new DataNotFoundException("question not found");
		}
	}

	public void create(String subject, String content) {
		Question q = new Question();

		q.setSubject(subject);
		q.setContent(content);
		q.setCreateDate(LocalDateTime.now());

		this.questionRepository.save(q);
	}
}
