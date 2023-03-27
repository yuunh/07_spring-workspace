package com.kh.spring.member.controller;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.kh.spring.member.model.service.MemberServiceImpl;
import com.kh.spring.member.model.vo.Member;

@Controller // Controller 타입의 어노테이션을 붙여주면 빈스캐닝을 통해 자동으로 빈 등록
public class MemberController {
	
	// private MemberServiceImpl mService = new MemberServiceImpl();
	// 이렇게 쓰면 결합도가 높아짐! 만약 개발 이후에 이름이 변경된다고 하면? 전부 다 바꿔야됨 번거롭다..
	
	@Autowired // DI(Dependency Injection) 특징 = 의존성 주입
	// private MemberService mService; // 권장
	private MemberServiceImpl mService; // 편의를 위해 이걸 쓰겠음..
	
	/*
	@RequestMapping(value = "login.me") // RequestMapping 타입의 어노테이션을 붙여줌으로서 HandlerMapping 등록
	public void loginMember() {
		
	}
	
	public void insertMember() {
		
	}
	
	public void updateMember() {
		
	}
	*/
	
	/*
	 * * 파라미터(요청시 전달값)를 받는 방법 (요청시 전달값을 처리 방법)
	 * 
	 * 1. HttpServlerRequest를 이용해서 전달받기 (기존 jsp/servler 방식)
	 * 	  해당 메소드의 매개변수 HttpServlerRequest를 작성해두면
	 * 	  스프링 컨테이너가 해당 메소드 호출시(실행시) 자동으로 해당 객체를 생성해서 인자로 주입해줌
	 
		@RequestMapping("login.me") // 속성 하나만 쓸 때
		public String loginMember(HttpServletRequest request) {
			String userId = request.getParameter("id");
			String userPwd = request.getParameter("pwd");
			
			System.out.println("id : " + userId);
			System.out.println("pwd : " + userPwd);
			
			return "main";
	}
	*/
	
	/*
	 * 2. @RequestParam 어노테이션을 이용하는 방법
	 * 	  request.getParameter("키") : 벨류 의 역할을 대신해주는 어노테이션
	 
		@RequestMapping("login.me")
		public String loginMember(@RequestParam(value = "id", defaultValue = "aaa") String userId,
								  @RequestParam("pwd") String userPwd) {
			System.out.println("id : " + userId);
			System.out.println("pwd : " + userPwd);
			
			return "main";
		}
	*/
	
	/*
	 * 3. @RequestParam 어노테이션을 생략하는 방법
	 * 	  ** 단, 매개변수명 name값(요청시 전달값의 키값)과 동일하게 세팅해둬야 자동으로 값이 주입됨
	 
		@RequestMapping("login.me")
		public String loginMember(String id, String pwd) {
			
			System.out.println("id : " + id);
			System.out.println("pwd : " + pwd);
			
			Member m = new Member();
			m.setUserId(id);
			m.setUserPwd(pwd);
			
			// Service 메소드에 m을 전달하며 조회
			
			return "main";
		}
	*/
	
	/*
	 * 4. 커맨드 객체 방식
	 * 	  해당 메소드의 매개변수로
	 * 	  요청시 전달값을 담고자 하는 vo클래스 타입을 세팅 후
	 *    요청시 전달값의 키값(name값)을 vo클래스에 담고자 하는 *필드명*으로 작성
	 *    
	 *    스프링 컨테이너가 해당 객체를 기본생성자로 생성 후 setter 메소드 찾아서
	 *    요청시 전달값을 해당 필드에 담아주는 내부적인 원리
	 
		@RequestMapping("login.me")
		public String loginMember(Member m) {
			
			// System.out.println("ID : " + m.getUserId());
			// System.out.println("PWD : " + m.getUserPwd());
			
			Member loginUser = mService.loginMember(m);
			
			if (loginUser == null) { // 로그인 실패 => requestScope에 담아서 에러페이지 포워딩
				System.out.println("로그인 실패");
			} else { // 로그인 성공 => loginuser sessionScope에 담아서 메인페이지 url 재요청
				System.out.println("로그인 성공");
			}
			
			return "main"; // "WEB-INF/views" + "main" + ".jsp"
		}
	*/
	
	/*
	 * * 요청 처리 후 응답페이지로 포워딩 또는 url 재요청, 응답데이터 담는 방법
	 * 
	 * 1. 스프링에서 제공하는 Model 객체를 사용하는 방법
	 * 	  포워딩할 뷰로 전달하고자 하는 데이터를 맵 형식(key-value)으로 담을 수 있는 영역
	 * 	  Model 객체는 requestScope 이다.
	 * 	  단, setAttribute가 아닌 addAttribute 메소드 이용
	
		@RequestMapping("login.me")
		public String loginMember(Member m, Model model, HttpSession session) {
			
			Member loginUser = mService.loginMember(m);
			
			if (loginUser == null) { // 로그인 실패 => requestScope에 담아서 에러페이지 포워딩
				
				model.addAttribute("errorMsg", "로그인 실패!");
				
				return "common/errorPage"; // "WEB-INF/views" + "common/errorPage" + ".jsp"
				
			} else { // 로그인 성공 => loginuser sessionScope에 담아서 메인페이지 url 재요청
				
				session.setAttribute("loginUser", loginUser);
				
				return "redirect:/"; // 이렇게 하면 포워딩 방식이 아닌 url 재요청 방식 / 쓰면 메인화면으로 이동
			}
		}
	 */
	
	/*
	 * 2. 스프링에서 제공하는 ModelAndView 객체를 이용하는 방법
	 * 	  
	 * 	  Model은 데이터를 key-value 세트로 담을 수 있는 공간이라고 한다면
	 * 	  View는 응답뷰에 대한 정보를 담을 수 있는 공간
	 */
	@RequestMapping("login.me")
	public ModelAndView loginMember(Member m, HttpSession session, ModelAndView mv) {
		
		Member loginUser = mService.loginMember(m);
		
		if (loginUser == null) { // 로그인 실패 => requestScope에 담아서 에러페이지 포워딩
			
			mv.addObject("errorMsg", "로그인 실패!");
			mv.setViewName("common/errorPage");
			
		} else { // 로그인 성공 => loginuser sessionScope에 담아서 메인페이지 url 재요청
			
			session.setAttribute("loginUser", loginUser);
			mv.setViewName("redirect:/");
			
		}
		
		return mv;
	}
	
	@RequestMapping("logout.me")
	public String logoutMember(HttpSession session) {
		
		session.invalidate();
		
		return "redirect:/"; // "main"
	}
	
	@RequestMapping("enrollForm.me")
	public String enrollForm() {
		
		return "member/memberEnrollerForm";
	}
}
