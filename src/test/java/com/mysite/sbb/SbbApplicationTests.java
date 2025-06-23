package com.mysite.sbb;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SbbApplicationTests {

	// new 사용을 안하고 선언만 했는데 생성됨. ==> Autowired
	// 우리가 필요로 하는 객체를 주입.
	@Autowired
	private QuestionRepository questionRepository;

	// 테스트 작업할때는 서버가 돌면 충돌이나므로 서버 종료를 하고 처리.
	@Test
	void testJpa() {
//		Question q1 = new Question();
//		// 레파지토리랑 연결이 되어 있어야 값을 넣을수 있다.
//		q1.setSubject("sbb가 도대체 무엇인가요? 알려주세요!");
//		q1.setContent("sbb를 통해서 스프링부트 프로젝트를 공부하는걸로 알고있습니다. 좀더 sbb에 대해서 알고싶어요!!!");
//		q1.setCreateDate(LocalDateTime.now());
//		this.questionRepository.save(q1); // 첫번째 질문 저장
//
//		Question q2 = new Question();
//		// 레파지토리랑 연결이 되어 있어야 값을 넣을수 있다.
//		q2.setSubject("스프링부트 모델 질문입니다.");
//		q2.setContent("id는 자동으로 생성되나요?");
//		q2.setCreateDate(LocalDateTime.now());
//		this.questionRepository.save(q2); // 두번째 질문 저장

//		// findAll(): 모든 엔티티를 조회합니다. ==> 목록
//		List<Question> all = this.questionRepository.findAll();
//
//		// Question 레코드 갯수가 동일한지... 4==> 기대값, all.size() ==> 검색값
//		// assertEquals(4, all.size());
//		assertEquals(2, all.size()); // error 기대값은 2인데 실제 findAll()은 4라서. // findAll()이 검색이 잘되었는지 테스트해봄.
//		Question q = all.get(0); // 첫번째 행 가져옴.
//		assertEquals("sbb가 도대체 무엇인가요?", q.getSubject()); // 제목이 동일한지 테스트
//		
		// findById(id): 주어진 id를 가진 엔티티를 조회합니다.
		// findById() : id값으로 검색. ==> 아이템
		Question q2 = this.questionRepository.findById(1).get();
	}

}
