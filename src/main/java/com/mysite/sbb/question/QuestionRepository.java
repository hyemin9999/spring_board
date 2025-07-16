package com.mysite.sbb.question;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Integer> {

	Question findBySubject(String subject); // 추상메서드 --> 실제 메서드를 구현해야 하지만, 추상메서드는 하지 않아도 된다.

	Question findBySubjectAndContent(String subject, String content); // where A and B

	List<Question> findBySubjectLike(String subject); // where like

	/**
	 * 질문 테이블에서 페이징기능이 있는 목록 반환<br/>
	 * 질문 전체 목록을 지정된 페이지 단위로 가져온다
	 * 
	 * @param Pageable :페이징 타입
	 * @return Page<Question>
	 */
	Page<Question> findAll(Pageable pageable); // 페이징

	// DB에서 Question Entity를 조회한 결과를 페이징하여 반환함(중요)
	Page<Question> findAll(Specification<Question> spec, Pageable pageable);
}
