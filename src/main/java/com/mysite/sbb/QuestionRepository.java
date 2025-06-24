package com.mysite.sbb;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
	Question findBySubject(String subject); // 추상메서드 --> 실제 메서드를 구현해야 하지만, 추상메서드는 하지 않아도 된다.

	Question findBySubjectAndContent(String subject, String content); // where A and B

	List<Question> findBySubjectLike(String subject); // where like

}
