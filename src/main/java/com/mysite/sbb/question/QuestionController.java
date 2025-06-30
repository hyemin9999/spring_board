package com.mysite.sbb.question;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;

@RequestMapping("/question")
@RequiredArgsConstructor
@Controller
public class QuestionController {

	// 기존에 있던 Repository 기능을 Service로 옮김
	private final QuestionService questionService;

	@GetMapping("/list")
	public String list(Model model) {

		List<Question> qlist = this.questionService.getList();
		model.addAttribute("questionList", qlist);
		return "question_list";
	}

	@GetMapping(value = "/detail/{id}")
	public String detail(Model model, @PathVariable("id") Integer id) {

		// TODO :: 서비스에서 가져옴.
		Question q = this.questionService.getQuestion(id);
		model.addAttribute("question", q);
		return "question_detail";
	}

	@GetMapping("/create")
	public String createQuestion() {
		return "question_form";
	}

	@PostMapping("/create")
	public String createQuestion(@RequestParam(value = "subject") String subject,
			@RequestParam(value = "content") String content) {

		// TODO :: 서비스에서 저장
		this.questionService.create(subject, content);
		return "redirect:/question/list"; // 질문 저장후 질문목록으로 이동
	}

}
