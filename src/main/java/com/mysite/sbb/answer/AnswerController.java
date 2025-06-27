package com.mysite.sbb.answer;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/answer") // url prifix
@RequiredArgsConstructor // 의존성 주입
public class AnswerController {

	// DI(객체 주입 ==> Question, Answer)
	private final QuestionService questionService;
	private final AnswerService answerService;

	@PostMapping("/create/{id}")
	public String createAnswer(Model model, @PathVariable("id") Integer id,
			@RequestParam(value = "content") String content) {

		Question question = this.questionService.getQuestion(id);

		this.answerService.create(question, content);

		return String.format("redirect:/question/detail/%s", id);
	}
}
