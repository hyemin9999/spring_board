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
@RequestMapping("/answer")
@RequiredArgsConstructor
public class AnswerController {

	private final QuestionService questionService;
	private final AnswerService answerService;
	private final UserService userService;

	/**
	 * 답변 등록 POST
	 * 
	 * @param model         유효성 검사가 실패하면 질문 정보를 담을 객체
	 * @param qid           질문 id
	 * @param answerForm    답변 유효성 검사를 위한 객체
	 * @param bindingResult 유효성 검사 결과값
	 * @param principal     로그인 사용자 정보
	 * 
	 * @return 질문 상세 페이지 ==> "/question/detail/{qid}#answer_{id}"
	 */
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/create/{qid}")
	public String creater(Model model, @PathVariable("qid") Integer qid, @Valid AnswerForm answerForm,
			BindingResult bindingResult, Principal principal) {

		Question question = this.questionService.getItem(qid);
		SiteUser author = this.userService.getUser(principal.getName());

		if (bindingResult.hasErrors()) {
			model.addAttribute("question", question);

			return "question_detail";
		}

		Answer item = this.answerService.create(question, answerForm.getContent(), author);

		// 답변 앵커 기능을 위한 #answer_id ==> 답변 등록/수정 시 해당 답변으로 위치가 이동될수 있도록 처리
		return String.format("redirect:/question/detail/%s#answer_%s", qid, item.getId());
	}

	/**
	 * 답변 수정 GET
	 * 
	 * @param answerForm 답변 수정시 @valid를 위한 객체
	 * @param id         답변 id
	 * @param principal  로그인 사용자
	 * 
	 * @return 답변 수정 페이지 ==> "answer_form"
	 */
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

	/**
	 * 답변 수정 POST
	 * 
	 * @param answerForm    답변 수정시 유효성 검사를 위한 객체
	 * @param bindingResult 유효성 검사 결과
	 * @param id            답변 id
	 * @param principal     로그인 사용자
	 * 
	 * @return 질문 상세 페이지 ==> "/question/detail/{qid}#answer_{id}"
	 */
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

		return String.format("redirect:/question/detail/%s#answer_%s", item.getQuestion().getId(), item.getId());
	}

	/**
	 * 답변 삭제 GET
	 * 
	 * @param principal 로그인 사용자
	 * @param id        답변 id
	 * 
	 * @return 질문 상세 페이지 ==> "/question/detail/{qid}"
	 */
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

	/**
	 * 답변 추천 저장
	 * 
	 * @param principal 로그인 사용자
	 * @param id        답변 id
	 * 
	 * @return 질문 상세 페이지 ==> "/question/detail/{qid}#answer_{id}"
	 */
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/vote/{id}")
	public String vote(Principal principal, @PathVariable("id") Integer id) {

		Answer item = this.answerService.getItem(id);
		SiteUser user = this.userService.getUser(principal.getName());

		this.answerService.vote(item, user);

		return String.format("redirect:/question/detail/%s#answer_%s", item.getQuestion().getId(), item.getId());
	}
}
