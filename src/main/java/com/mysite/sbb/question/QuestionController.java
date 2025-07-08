package com.mysite.sbb.question;

import java.security.Principal;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mysite.sbb.answer.AnswerForm;
import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/question")
@RequiredArgsConstructor
@Controller
public class QuestionController {

	// 기존에 있던 Repository 기능을 Service로 옮김
	private final QuestionService questionService;
	private final UserService userService;

	@GetMapping("/list")
	public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page) {
		Page<Question> list = this.questionService.getList(page);
		model.addAttribute("paging", list);
		return "question_list";
	}

	@GetMapping(value = "/detail/{id}")
	public String detail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm) {
		// TODO :: 서비스에서 가져옴.
		Question q = this.questionService.getQuestion(id);
		model.addAttribute("question", q);
		return "question_detail";
	}

	// PreAuthorize("isAuthenticated()") ==> 로그인일때만 실행, 로그아웃의 경우 로그인페이지로 이동
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/create")
	public String createQuestion(QuestionForm questionForm) {
		return "question_form";
	}

	// 스프링 시큐리티에서 제공하는 Principal 객체를 매개변수 추가
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/create")
	public String createQuestion(@Valid QuestionForm questionForm, BindingResult bindingResult, Principal principal) {

		// validation(유효성 검사 추가)
		if (bindingResult.hasErrors()) {
			return "question_form";
		}

		SiteUser author = this.userService.getUser(principal.getName());

		// 질문이 등록될때 글쓴이 저장
		this.questionService.create(questionForm.getSubject(), questionForm.getContent(), author);
		return "redirect:/question/list"; // 질문 저장후 질문목록으로 이동
	}
}
