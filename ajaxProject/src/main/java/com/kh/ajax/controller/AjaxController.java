package com.kh.ajax.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.dsig.TransformService;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.kh.ajax.model.vo.Member;

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
	
	@ResponseBody
	// @RequestMapping("ajax1.do")
	@RequestMapping(value = "ajax1.do", produces = "text/html; charset=UTF-8")
	public String ajaxMethod1(String name, int age) {
		
		String responseData = "응답 문자열 : " + name + "은(는) " + age + "살입니다.";
		
		return responseData;
	}
	*/
	
	/*
	// 다수의 응답데이터가 있을 경우?
	@RequestMapping("ajax1.do")
	public void ajaxMethod1(String name, int age, HttpServletResponse response) throws IOException {
		
		// 요청 처리가 다 됐다는 가정하에 데이터 응답
		
		// response.setContentType("text/html; charset=UTF-8"); // 인코딩 처리
		// response.getWriter().print(name);
		// response.getWriter().print(age);
		// 출력하는 데이터가 하나의 문자열로 연이어져 있음
		
		// JSON(JavaScript Object Notation) 형태로 담아서 응답
		// JSONArray => [값, 값, 값, 값, ..] => 자바에서의 ArrayList와 유사
		// JSONObject => {키:값, 키:값, 키:값, ..} => 자바에서의 HashMap과 유사
		
		// 첫번째 방법. JSONArray로 담아서 응답 (JSON.simple » 1.1.1)
		// JSONArray jArr = new JSONArray(); // []
		// jArr.add(name); // ["이유나"]
		// jArr.add(age); // ["이유나", 20]
		
		// 두번째 방법. JSONObject로 담아서 응답
		JSONObject jObj = new JSONObject(); // {}
		jObj.put("name", name); // {name:" 이유나"}
		jObj.put("age", age); // {age:20, name:"이유나"} => 순서 무시
		
		response.setContentType("application/json; charset=UTF-8");
		// response.getWriter().print(jArr);
		response.getWriter().print(jObj);
		
	}
	*/
	
	@ResponseBody
	@RequestMapping(value = "ajax1.do", produces = "application/json; charset=UTF-8")
	public String ajaxMethod1(String name, int age) {
		
		JSONObject jObj = new JSONObject(); // {}
		jObj.put("name", name); // {name:" 이유나"}
		jObj.put("age", age); // {age:20, name:"이유나"}
		
		return jObj.toJSONString(); // jObj를 스트링으로 변경
	}
	
	/*
	@ResponseBody
	@RequestMapping(value = "ajax2.do", produces = "application/json; charset=UTF-8")
	public String ajaxMethod2() {
		
		// Member m = mService.selectMember(num);
		Member m = new Member("user01", "pass01", "안유진", 20, "01012345678");
		
		// JSON 형태로 만들어서 응답
		JSONObject jObj = new JSONObject();
		jObj.put("userId", m.getUserId());
		jObj.put("userName", m.getUserName());
		jObj.put("age", m.getAge());
		jObj.put("phone", m.getPhone());
		
		return jObj.toJSONString();
	}
	*/
	
	@ResponseBody
	@RequestMapping(value = "ajax2.do", produces = "application/json; charset=UTF-8")
	public String ajaxMethod2() {
		
		// Member m = mService.selectMember(num);
		Member m = new Member("user01", "pass01", "안유진", 20, "01012345678");
		
		return new Gson().toJson(m); // json 형태로 만들어서 문자열로 리턴해줌 / 멤버객체의 필드명으로 키값이 잡힘!
	}
	
	@ResponseBody
	@RequestMapping(value = "ajax3.do", produces = "application/json; charset=UTF-8")
	public String ajaxMethod3() {
		
		// ArrayList<Member> list = mService.selectMember();
		ArrayList<Member> list = new ArrayList<Member>();
		list.add(new Member("user01", "pass01", "안유진", 20, "01012312312"));
		list.add(new Member("user02", "pass02", "최정민", 21, "01045645646"));
		list.add(new Member("user03", "pass03", "김수지", 22, "01078977897"));
		
		return new Gson().toJson(list); // [{}, {}, {}]
	}
}
