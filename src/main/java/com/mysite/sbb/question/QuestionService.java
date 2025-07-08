package com.mysite.sbb.question;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mysite.sbb.DataNotFoundException;
import com.mysite.sbb.user.SiteUser;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class QuestionService {

	private final QuestionRepository questionRepository;

	public Page<Question> getList(int page) {
		// 최근등록 게시물부터 보이도록 sort
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("createDate"));

		Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));

		return this.questionRepository.findAll(pageable);
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

	// 글쓴이 저장을 위한 SiteUser 추가
	public void create(String subject, String content, SiteUser author) {
		Question q = new Question();

		q.setSubject(subject);
		q.setContent(content);
		q.setCreateDate(LocalDateTime.now());
		// 글쓴이
		q.setAuthor(author);

		this.questionRepository.save(q);
	}
}
