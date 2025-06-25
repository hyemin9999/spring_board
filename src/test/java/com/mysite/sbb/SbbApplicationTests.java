package com.mysite.sbb;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.answer.AnswerRepository;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
class SbbApplicationTests {

	// new 사용을 안하고 선언만 했는데 생성됨. ==> Autowired
	// 우리가 필요로 하는 객체를 주입.
	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private AnswerRepository answerRepository;

	// 테스트 작업할때는 서버가 돌면 충돌이나므로 서버 종료를 하고 처리.
	@Test
	@Transactional // DB 세션을 유지시켜준다. ==> 모든 DB 작업이 성공해야 커밋되고, 하나라도 실패하면 모두 롤백됨.
	// 제일마지막에 있는 질문데이터를 통해 답장 데이터를 가지고 오는작업 할때만 사용, 그외 주석 이거나 사용X
	void testJpa() {
		// 저장 --> insert
//		Question q1 = new Question();
//		// 레파지토리랑 연결이 되어 있어야 값을 넣을수 있다.
//		q1.setSubject("sbb가 도대체 무엇인가요?");
//		q1.setContent("sbb를 통해서 스프링부트 프로젝트를 공부하는걸로 알고있습니다. 좀더 sbb에 대해서 알고싶어요!!!");
//		q1.setCreateDate(LocalDateTime.now());
		// 단순하게 메서드 호출, 상단의 4줄은 데이터 대입,
		// questionRepository --> spring이 만들어준 객체.
		// .save --> insert into 와 동일하게 작동.
//		this.questionRepository.save(q1); // 첫번째 질문 저장
//
//		Question q2 = new Question();
//		// 레파지토리랑 연결이 되어 있어야 값을 넣을수 있다.
//		q2.setSubject("스프링부트 모델 질문입니다.");
//		q2.setContent("id는 자동으로 생성되나요?");
//		q2.setCreateDate(LocalDateTime.now());
//		this.questionRepository.save(q2); // 두번째 질문 저장

//		// 2-1. findAll(): 모든 엔티티를 조회합니다. ==> 목록
		// DB에서 모든 레코드를 읽어오는 메서드.
//		// .findAll() --> select * from Question
//		List<Question> all = this.questionRepository.findAll();
//
//		// Question 레코드 갯수가 동일한지... 4==> 기대값, all.size() ==> 검색값
//		assertEquals(4, all.size());
//		// assertEquals(2, all.size()); // error 기대값은 2인데 실제 findAll()은 4라서. //
//		// findAll()이 검색이 잘되었는지 테스트해봄.
//		Question q = all.get(0); // 첫번째 행 가져옴.
//		assertEquals("sbb가 도대체 무엇인가요?", q.getSubject()); // 제목이 동일한지 테스트

//		// 2-2. findById(): 주어진 id를 가진 엔티티를 조회합니다. id값으로 검색. ==> 아이템
//		// .findById(1) --> select * from Question where id==1
//		// 값이 존재하는경우, 존재하지 않는 경우 : null ==> Optional은 null값을 유연하게 처리할수 있는class
//		// Optional 중요##### 
//		Optional<Question> op = this.questionRepository.findById(1);
//
//		// isPresent() : 값이 있는지 여부
//		if (op.isPresent()) {
//			Question q = op.get();
//			assertEquals("sbb가 도대체 무엇인가요?", q.getSubject());
//		}

//		// 2-3. findBySubject() : subject를 검색. 사용자가 메서드 작업함. repository에
//		Question q = this.questionRepository.findBySubject("sbb가 도대체 무엇인가요?");
//		assertEquals(1, q.getId());

//		// 2-4. findBySubjectAndContent();
//		Question q = this.questionRepository.findBySubjectAndContent("sbb가 도대체 무엇인가요? 알려주세요!",
//				"sbb를 통해서 스프링부트 프로젝트를 공부하는걸로 알고있습니다.");
//		assertEquals(3, q.getId());

//		//2-5. findBySubjectLike(); % ==> 모든문자, _ ==> 한글자
//		List<Question> qList = this.questionRepository.findBySubjectLike("sbb%");
////		if (qList.isEmpty() == false) {
//		Question q = qList.get(0);
//		assertEquals(1, q.getId());
//		assertEquals(2, qList.size());
//		}

//		// 3. 질문데이터 수정하기 ==> update
//		// java.util.Optional
//		Optional<Question> op = this.questionRepository.findById(1);
//		assertTrue(op.isPresent()); // 검색 값이 존재하는지 확인
//
//		Question q = op.get();
//		q.setSubject("수정된 제목");
//		this.questionRepository.save(q); // 저장

//		// 4. 질문데이터 삭제하기 ==> delete 테스트용이라서 레코드 갯수 체크하면서 테스트.
//		assertEquals(4, this.questionRepository.count()); //4-1 전체 레코드 갯수 체크
//
//		Optional<Question> op = this.questionRepository.findById(4);
//		assertTrue(op.isPresent()); // 4-2 검색 값이 존재하는지 확인
//
//		Question q = op.get();
//		this.questionRepository.delete(q); //4-3 레코드 삭제
//		assertEquals(3, this.questionRepository.count()); // 4-4 전체 레코드 갯수 체크

//		// 5. 답변데이터 저장하기 --> 어떤 질문의 답변인지 확인이 중요
//		Optional<Question> op = this.questionRepository.findById(2);
//		assertTrue(op.isPresent()); // 검색 값이 존재하는지 확인
//
//		Question q = op.get();
//		Answer a = new Answer();
//		a.setContent("답변입니다.");
//		a.setQuestion(q); // 어떤 질문의 답변인지, Question 객체가 필요함. --> Question_id
//		a.setCreateDate(LocalDateTime.now());
//		this.answerRepository.save(a);

//		// 6. 답변데이터 조회하기
//
//		Optional<Answer> op = this.answerRepository.findById(1);
//		assertTrue(op.isPresent()); // 검색 값이 존재하는지 확인
//
//		Answer a = op.get();
//		assertEquals(2, a.getQuestion().getId());

		// 7. 답변 데이터를 통해 질문 데이터 찾기 vs 질문 데이터를 통해 답변 데이터 찾기

		Optional<Question> op = this.questionRepository.findById(2);
		assertTrue(op.isPresent()); // 검색 값이 존재하는지 확인

		Question q = op.get();
		List<Answer> aList = q.getAnswerList(); // 세션이 유지됨. 이후에는 세션이 끊김. 그래서 아래부터 오류발생. --> 실무에서는 발생하지 않음.
		assertEquals(1, aList.size());
		assertEquals("답변입니다.", aList.get(0).getContent());

	}

}
