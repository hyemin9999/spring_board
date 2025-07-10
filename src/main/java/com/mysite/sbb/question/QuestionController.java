package com.mysite.sbb.question;

import java.security.Principal;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.mysite.sbb.answer.AnswerForm;
import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/question")
@RequiredArgsConstructor
@Controller
public class QuestionController {

	private final QuestionService questionService;
	private final UserService userService;

	/**
	 * 질문 게시판 ==> 질문 목록 반환
	 * 
	 * @param model 반환할 질문 목록을 템플릿에 보낼때 사용 .addAttribute()
	 * @param page  추가한 페이지 기능의 페이지 초기값은 0
	 * 
	 * @return 질문 목록 페이지 ==> question_list
	 */
	@GetMapping("/list")
	public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page) {

		Page<Question> list = this.questionService.getList(page);

		model.addAttribute("paging", list);
		model.addAttribute("hasContent", list.hasContent());

		return "question_list";
	}

	/**
	 * 질문 상세 ==> 질문 항목 반환
	 * 
	 * @param model      반환할 질문 항목을 템플릿에 보낼때 사용 .addAttribute()
	 * @param id         질문 id
	 * @param answerForm 질문 상세 페이지에 있는 답글 등록시 사용할 @Valid 때문에 필요
	 *                   (th:object="${answerForm}")
	 * @return 질문 상세 페이지 ==> question_detail
	 */
	@GetMapping(value = "/detail/{id}")
	public String detail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm) {

		Question item = this.questionService.getItem(id);

		model.addAttribute("question", item);

		return "question_detail";
	}

	/**
	 * 질문 등록 GET
	 * 
	 * @param model        질문 등록 페이지의 상단 title(등록/수정)을 위해 사용할 객체
	 * @param questionForm 질문 등록시 사용할 @Valid 때문에 필요 (th:object="${questionForm}")
	 * 
	 * @return 질문 등록 페이지 ==> question_form
	 */
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/create")
	public String create(Model model, QuestionForm questionForm) {

		model.addAttribute("mode", "create");

		return "question_form";
	}

	/**
	 * 질문 등록 POST
	 * 
	 * @param model         질문 등록 페이지의 상단 title(등록/수정)을 위해 사용할 객체
	 * @param questionForm  질문 등록시 유효성 검사를 위한 객체
	 * @param bindingResult 유효성 검사 결과값
	 * @param principal     스프링 시큐리티에서 제공하는 로그인 사용자 정보
	 * 
	 * @return 질문 등록 페이지 ==> question_form
	 */
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/create")
	public String create(Model model, @Valid QuestionForm questionForm, BindingResult bindingResult,
			Principal principal) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("mode", "create");

			return "question_form";
		}

		SiteUser author = this.userService.getUser(principal.getName());

		this.questionService.create(questionForm.getSubject(), questionForm.getContent(), author);

		return "redirect:/question/list"; // 질문 저장후 질문목록으로 이동
	}

	/**
	 * 질문 수정 GET
	 * 
	 * @param model        질문 등록 페이지의 상단 title(등록/수정)을 위해 사용할 객체
	 * @param questionForm 질문 수정시 사용할 @Valid 때문에 필요 (th:object="${questionForm}") 필요
	 * @param id           수정할 질문 id (pk)
	 * @param principal    스프링 시큐리티에서 제공하는 로그인 사용자 정보
	 * 
	 * @return 질문 수정 페이지 ==> question_form
	 */
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/modify/{id}")
	public String modify(Model model, QuestionForm questionForm, @PathVariable("id") Integer id, Principal principal) {

		model.addAttribute("mode", "modify");

		Question item = this.questionService.getItem(id);

		if (!item.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
		}

		questionForm.setSubject(item.getSubject());
		questionForm.setContent(item.getContent());

		return "question_form";
	}

	/**
	 * 질문 수정 POST
	 * 
	 * @param model         질문 등록 페이지의 상단 title(등록/수정)을 위해 사용할 객체
	 * @param questionForm  질문 수정시 유효성 검사를 위한 객체
	 * @param bindingResult 유효성 검사 결과값
	 * @param principal     스프링 시큐리티에서 제공하는 로그인 사용자 정보
	 * 
	 * @return 질문 상세 페이지 ==> /question/detail/{id}
	 */
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/modify/{id}")
	public String modify(Model model, @Valid QuestionForm questionForm, BindingResult bindingResult,
			Principal principal, @PathVariable("id") Integer id) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("mode", "mofidy");

			return "question_form";
		}

		Question item = this.questionService.getItem(id);

		if (!item.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
		}

		this.questionService.modify(item, questionForm.getSubject(), questionForm.getContent());

		return String.format("redirect:/question/detail/%s", id);
	}

	/**
	 * 질문 삭제
	 * 
	 * @param principal 스프링 시큐리티에서 제공하는 로그인 사용자 정보
	 * @param id        질문 id
	 * 
	 * @return 질문 게시판 페이지 ==> /question/list
	 */
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/delete/{id}")
	public String delete(Principal principal, @PathVariable("id") Integer id) {

		Question item = this.questionService.getItem(id);

		if (!item.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
		}

		this.questionService.delete(item);

		return "redirect:/question/list";
	}

	/**
	 * 질문 추천 저장
	 * 
	 * @param principal 로그인한 사용자 정보
	 * @param id        질문 id
	 * 
	 * @return 질문 상세 페이지 ==> "/question/detail/{id}"
	 */
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/vote/{id}")
	public String vote(Principal principal, @PathVariable("id") Integer id) {

		Question item = this.questionService.getItem(id);
		SiteUser user = this.userService.getUser(principal.getName());

		this.questionService.vote(item, user);

		return String.format("redirect:/question/detail/%s", id);
	}
}
