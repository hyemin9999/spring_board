package com.mysite.sbb.question;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
	Question findBySubject(String subject); // 추상메서드 --> 실제 메서드를 구현해야 하지만, 추상메서드는 하지 않아도 된다.

	Question findBySubjectAndContent(String subject, String content); // where A and B

	List<Question> findBySubjectLike(String subject); // where like

	// 질문 전체 목록을 지정된 페이지 단위로 가져온다
	Page<Question> findAll(Pageable pageable); // 페이징

}
