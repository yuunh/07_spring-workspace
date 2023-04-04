package com.kh.ajax.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AjaxController {

	/*
	 * 1. HttpServletResponse 객체로 데이터 응답하기 (기존의 jsp/servlet 때 했던 stream을 이용한 방식)
	
	@RequestMapping("ajax1.do")
	public void ajaxMethod1(String name, int age, HttpServletResponse response) throws IOException {
		System.out.println(name + age);
		
		// 요청 처리를 위해 서비스 호출
		
		// 요청 처리가 다 됐다는 가정하에 요청한 페이지에 응답할 데이터가 있을 경우
		String responseData = "응답 문자열 : " + name + "은(는) " + age + "살입니다.";
		
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().print(responseData); // throws IOException // 예외 던지면 스프링이 알아서 예외처리해줌
	}
	 */
	
	/*
	 * 2. 응답할 데이터를 문자열로 리턴
	 * 		=> HttpServletResponse를 안 쓸 수 있음!
	 * 		단, 문자열 리턴하면 원래는 포워딩 방식이었음 => 응답뷰로 인식해서 해당 뷰페이지를 찾을거임
	 * 		따라서 내가 리턴하는 문자열이 응답뷰가 아닌 응답데이터야 라는 걸 선언하는 
	 * 		어노테이션인 @responseBody를 붙여야됨
	 * 		인코딩 필터는 동기식 방식에 해당하는 거라서 이거랑은 상관 없음
	 */
	@ResponseBody
	// @RequestMapping("ajax1.do")
	@RequestMapping(value = "ajax1.do", produces = "text/html; charset=UTF-8")
	public String ajaxMethod1(String name, int age) {
		
		String responseData = "응답 문자열 : " + name + "은(는) " + age + "살입니다.";
		
		return responseData;
	}
}
