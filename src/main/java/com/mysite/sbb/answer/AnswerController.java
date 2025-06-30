package com.mysite.sbb.answer;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/answer") // url prifix
@RequiredArgsConstructor // 의존성 주입
public class AnswerController {

	// DI(객체 주입 ==> Question, Answer)
	private final QuestionService questionService;
	private final AnswerService answerService;

	// @RequestParam ==> @Valid AnswerForm
	@PostMapping("/create/{id}")
	public String createAnswer(Model model, @PathVariable("id") Integer id, @Valid AnswerForm answerForm,
			BindingResult bindingResult) {

		Question question = this.questionService.getQuestion(id);

		if (bindingResult.hasErrors()) {
			model.addAttribute("question", question);
			return "question_detail";
		}

		this.answerService.create(question, answerForm.getContent());

		return String.format("redirect:/question/detail/%s", id);
	}
}
