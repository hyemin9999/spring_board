package com.mysite.sbb.question;

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
		Optional<Question> item = this.questionRepository.findById(id);

		if (item.isPresent()) {
			return item.get();
		} else {
			throw new DataNotFoundException("question not found");
		}
	}
}
