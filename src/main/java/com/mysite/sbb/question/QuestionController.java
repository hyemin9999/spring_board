package com.mysite.sbb.question;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
		model.addAttribute("qlist", qlist);
		return "question_list";
	}

	@GetMapping(value = "/detail/{id}")
	public String detail(Model model, @PathVariable("id") Integer id) {
		Question question = this.questionService.getQuestion(id);
		model.addAttribute("qitem", question);

		return "question_detail";
	}

}
