package com.mysite.sbb.question;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.mysite.sbb.DataNotFoundException;
import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.user.SiteUser;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class QuestionService {

	private final QuestionRepository questionRepository;

	/**
	 * 질문 목록을 페이징으로 반환하는 함수<br/>
	 * - 검색 기능을 위해 수정 ==> 매개변수 kw필요
	 * 
	 * @param page 페이징할 페이지번호
	 * 
	 * @return 질문 목록
	 */
	public Page<Question> getList(int page, String kw) {

		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("createDate"));
		Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
		// 추가 및 수정 - 검색기능
		Specification<Question> spec = search(kw);
		return this.questionRepository.findAll(spec, pageable);
	}

	/**
	 * 질문 항목을 반환하는 함수
	 * 
	 * @param id 질문 id (pk)
	 * 
	 * @return 질문 항목
	 */
	public Question getItem(Integer id) {

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

	/**
	 * 질문 추천 저장
	 * 
	 * @param item 질문
	 * @param user 추천한 사용자
	 */
	public void vote(Question item, SiteUser user) {

		item.getVoter().add(user);
		this.questionRepository.save(item);
	}

	/**
	 * 검색
	 * 
	 * @param kw 검색어
	 */
	private Specification<Question> search(String kw) {

		return new Specification<>() {

			private static final long seriaVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<Question> q, CriteriaQuery<?> query, CriteriaBuilder cb) {
				query.distinct(true); // 중복을 제거
				Join<Question, SiteUser> u1 = q.join("author", JoinType.LEFT); // 질문과 작성자
				Join<Question, Answer> a = q.join("answerList", JoinType.LEFT); // 질문과 답변
				Join<Answer, SiteUser> u2 = q.join("author", JoinType.LEFT); // 답변과 작성자

				return cb.or(cb.like(q.get("subject"), "%" + kw + "%"), // 질문
						cb.like(q.get("content"), "%" + kw + "%"), // 내용
						cb.like(u1.get("username"), "%" + kw + "%"), // 질문 작성자
						cb.like(a.get("content"), "%" + kw + "%"), // 답변 내용
						cb.like(u2.get("username"), "%" + kw + "%")); // 답변 작성자
			}
		};
	}
}
