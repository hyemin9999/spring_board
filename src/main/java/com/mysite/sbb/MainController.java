package com.mysite.sbb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

	@GetMapping("/sbb")
	@ResponseBody // 응답한다고 설정
	public String index() {
//		System.out.println("index"); // consloe 에 출력
		return "sbb 프로젝트 시작해 봅시다..."; // "index" --> 템플릿 파일 --> 공부....
	}

}
