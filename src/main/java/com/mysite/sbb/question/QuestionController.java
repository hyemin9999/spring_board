package com.mysite.sbb.question;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;

// 초기화 되지 않은 final이나 @NonNull이 붙은 경우의 필드에 대해 생성자를 만들어줌 
// Controller나 Service 클래스를 만들때 자주 사용하는..
// @Autowired 를 사용하지 않고. DI(의존성) == 객체 주입
@RequiredArgsConstructor // import lombok.RequiredArgsConstructor ==> lombok @Getter, @Setter,
							// @Autowired(생성자 사용안해도되도록)

@Controller // controller 생성시 첫번째로 작업해야 함, Controller로 사용하겠다.
public class QuestionController {

	private final QuestionRepository questionRepository;

	@GetMapping("/question/list") // url 매핑
	public String list(Model model) { // import org.springframework.ui.Model;

		// 보고자 하는것 --> 질문 목록 --> DB에 있음.
		// DB에 있는거 불러올때 사용하는것 --> repository
		List<Question> qlist = this.questionRepository.findAll(); // 복수의 레코드 --> List사용

		// "qlist" : 이름 ${qlist}
		// qlist : 값 = value
		model.addAttribute("qlist", qlist); // DB에서 가지고온 값을 model에 저장
		// "question_list" 템플릿에서 model에 접근해서 사용
		return "question_list"; // 'question_list.html' template 불러옴.--> 브라우저가 랜더링
	}

//	@GetMapping("/question/list")
//	@ResponseBody // 화면에 보이도록. --> template 사용하기위해 사용X

//	public String list() {
// //   return "question list";
//		return "question_list"; // 'question_list.html' template 불러온것.--> 브라우저가 랜더링
//	}
}
