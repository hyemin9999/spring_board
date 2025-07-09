package com.mysite.sbb.answer;

import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionService;
import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/answer") // url prifix
@RequiredArgsConstructor // 의존성 주입
public class AnswerController {

	// DI(객체 주입 ==> Question, Answer, SiteUser)
	private final QuestionService questionService;
	private final AnswerService answerService;
	private final UserService userService;

	// principal ==> 로그아웃 상태에서는 사용불가.
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/create/{qid}")
	public String creater(Model model, @PathVariable("qid") Integer id, @Valid AnswerForm answerForm,
			BindingResult bindingResult, Principal principal) {

		Question question = this.questionService.getItem(id);
		SiteUser author = this.userService.getUser(principal.getName());

		if (bindingResult.hasErrors()) {
			model.addAttribute("question", question);
			return "question_detail";
		}

		this.answerService.create(question, answerForm.getContent(), author);

		return String.format("redirect:/question/detail/%s", id);
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/modify/{id}")
	public String modify(AnswerForm answerForm, @PathVariable("id") Integer id, Principal principal) {

		Answer item = this.answerService.getItem(id);

		if (!item.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
		}
		answerForm.setContent(item.getContent());
		return "answer_form";
	}

	@PreAuthorize("isAuthenticated()")
	@PostMapping("/modify/{id}")
	public String modify(@Valid AnswerForm answerForm, BindingResult bindingResult, @PathVariable("id") Integer id,
			Principal principal) {

		if (bindingResult.hasErrors()) {
			return "answer_form";
		}

		Answer item = this.answerService.getItem(id);

		if (!item.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
		}

		this.answerService.modify(item, answerForm.getContent());

		return String.format("redirect:/question/detail/%s", item.getQuestion().getId());
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/delete/{id}")
	public String delete(Principal principal, @PathVariable("id") Integer id) {

		Answer item = this.answerService.getItem(id);

		if (!item.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다");
		}

		this.answerService.delete(item);

		return String.format("redirect:/question/detail/%s", item.getQuestion().getId());
	}
}
