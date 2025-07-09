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

	/**
	 * 질문 목록을 페이징으로 반환하는 함수
	 * 
	 * @param page 페이징할 페이지번호
	 * @return 질문 목록
	 */
	public Page<Question> getList(int page) {

		// 최근등록 게시물부터 보이도록 sort
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("createDate"));

		Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));

		return this.questionRepository.findAll(pageable);
	}

	/**
	 * 질문 항목을 반환하는 함수
	 * 
	 * @param id 질문 id (pk)
	 * @return 질문 항목
	 */
	public Question getItem(Integer id) {

		// 가져온 값이 없을경우(=null)를 체크하기 위해. Optional 사용
		Optional<Question> item = this.questionRepository.findById(id);

		if (item.isPresent()) {
			return item.get();
		} else {
			throw new DataNotFoundException("question not found");
		}
	}

	/**
	 * 질문 등록 함수
	 * 
	 * @param subject 제목
	 * @param content 내용
	 * @param author  글쓴이 ==> 로그인 사용자
	 */
	public void create(String subject, String content, SiteUser author) {

		Question item = new Question();

		item.setSubject(subject);
		item.setContent(content);
		item.setCreateDate(LocalDateTime.now());
		// 글쓴이
		item.setAuthor(author);

		this.questionRepository.save(item);
	}

	/**
	 * 질문 수정 함수 :: 수정날짜 저장
	 * 
	 * @param item    질문
	 * @param subject 제목
	 * @param content 내용
	 */
	public void modify(Question item, String subject, String content) {

		item.setSubject(subject);
		item.setContent(content);
		item.setModifyDate(LocalDateTime.now());

		this.questionRepository.save(item);
	}

	/**
	 * 질문 삭제 함수
	 * 
	 * @param item 질문
	 */
	public void delete(Question item) {
		this.questionRepository.delete(item);
	}
}
