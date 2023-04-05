package com.kh.spring.board.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.kh.spring.board.model.service.BoardServiceImpl;
import com.kh.spring.board.model.vo.Board;
import com.kh.spring.common.model.vo.PageInfo;
import com.kh.spring.common.template.Pagination;

@Controller
public class BoardController {

	@Autowired
	private BoardServiceImpl bService;
	
	// 메뉴바 클릭시	/list.bo					(기본적으로 1번 페이지 요청)
	// 페이징바 클릭시	/list.bo?cpage=요청한 페이지수
	
	/*
	@RequestMapping("list.bo") 
	public String selectList(@RequestParam(value = "cpage", defaultValue = "1") int currentPage, Model model) { //
		System.out.println(currentPage);
	
		int listCount = bService.selectListCount();
	 
	 	PageInfo pi = Pagination.getPageInfo(listCount, currentPage, 10, 5);
	 
		ArrayList<Board> list = bService.selectList(pi);
	 
		model.addAttribute("pi", pi); 
		model.addAttribute("list", list);

		return "board/boardListView";
	 */
		
	@RequestMapping("list.bo")
	public ModelAndView selectList(@RequestParam(value = "cpage", defaultValue = "1") int currentPage, ModelAndView mv) {
		
		int listCount = bService.selectListCount();
		
		PageInfo pi = Pagination.getPageInfo(listCount, currentPage, 10, 5);
		
		ArrayList<Board> list = bService.selectList(pi);
		
		/*
		mv.addObject("pi", pi);
		mv.addObject("list", list);
		mv.setViewName("board/boardListView");
		*/
		
		// 메소드 체이닝
		mv.addObject("pi", pi).addObject("list", list).setViewName("board/boardListView");
		
		return mv;
	}
	
	@RequestMapping("enrollForm.bo")
	public String enrollForm() {
		
		return "board/boardEnrollForm";
	}
	
	@RequestMapping("insert.bo")
	public String insertBoard(Board b, MultipartFile upfile, HttpSession session, Model model) {
		// System.out.println(b);
		// System.out.println(upfile); // 첨부파일을 선택했든 안했든 생성됨! (다만 filename에 원본명이 있냐 or "")
		
		// 객체는 생겼지만 값이 null임
		// => 추가적인 라이브러리 필요함
		
		// 전달된 파일이 있을 경우 => 파일명 수정 작업 후 서버 업로드 => 원본명, 서버업로드된 경로를 b에 이어서 담기
		if (!upfile.getOriginalFilename().equals("")) {
			
			/*
			// 파일명 수정 작업 후 서버에 업로드 시키기 ("flower.png" => "2023033110185578945.png")
			String originName = upfile.getOriginalFilename(); // flower.png
			
			// "20230331101855" (년월일시분초)
			String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			
			// 랜덤한 숫자 5자리
			int ranNum = (int)(Math.random() * 90000 + 10000); // 10000~99999 사이의 랜덤값
			
			// 확장자
			String ext = originName.substring(originName.lastIndexOf("."));
			
			// 최종 수정명
			String changeName = currentTime + ranNum + ext;
			
			// 업로드 시키고자 하는 폴더의 물리적인 경로 알아내기
			String savePath = session.getServletContext().getRealPath("/resources/uploadFiles/"); // "/" 쓰면 webapp 가리킴
			
			// 서버에 파일을 업로드
			try {
				upfile.transferTo(new File(savePath + changeName));
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
			*/
			
			String changeName = saveFile(upfile, session);
			
			b.setOriginName(upfile.getOriginalFilename());
			b.setChangeName("resources/uploadFiles/" + changeName);
		}
		
		// 넘어온 첨부파일이 있을 경우 b : 제목, 작성자, 내용, 파일원본명, 파일저장경로
		// 넘어온 첨부파일이 없을 경우 b : 제목, 작성자, 내용
		
		int result = bService.insertBoard(b);
		
		if (result > 0) { // 성공 => 게시글 리스트 페이지 url 재요청 ("list.bo")
			
			session.setAttribute("alertMsg", "성공적으로 게시글이 등록되었습니다!");
			
			return "redirect:list.bo";
			
		} else { // 실패 => 에러페이지 포워딩
			
			model.addAttribute("errorMsg", "게시글 등록 실패!");
			
			return "common/errorPage";
		}
	}
	
	@RequestMapping("detail.bo")
	public ModelAndView selectBoard(int bno, Model model, ModelAndView mv) {
		// bno에는 상세조회하고자 하는 해당 게시글 번호 담겨있음
		System.out.println(bno);
		// 해당 게시글 조회수 증가용 서비스 호출 결과 받기 (update 하고 오기)
		int result = bService.increaseCount(bno);
		
		if (result > 0) { 
		// >> 성공적으로 조회수 증가 (유효한 게시글 맞다)
		//		>> boardDetailView.jsp 상에 필요한 데이터 조회 (게시글 상세정보 조회용 서비스 호출)
			Board b = bService.selectBoard(bno);
			
		// 		>> 조회된 데이터 담아서
		// 		>> board/boardDetailView.jsp 포워딩(모델!!)
			/* 
			   model.addAttribute("b", b); 
			   return "board/boardDetailView";
			 */
			
			mv.addObject("b", b).setViewName("board/boardDetailView");
			
		} else {
		// >> 조회수 증가 실패
		// 		>> 에러문구 담아서 에러페이지 포워딩
			/*
			model.addAttribute("errorMsg", "게시글 상세 조회 실패!");
			
			return "common/errorPage";
			*/
			
			mv.addObject("errorMsg", "게시글 상세 조회 실패!").setViewName("common/errorPage");
		}
		
		return mv;
	}
	
	// 현재 넘어온 첨부파일 그 자체를 서버의 폴더에 저장시키는 역할
	public String saveFile(MultipartFile upfile, HttpSession session) {
		// 파일명 수정 작업 후 서버에 업로드 시키기 ("flower.png" => "2023033110185578945.png")
		String originName = upfile.getOriginalFilename(); // flower.png
		
		// "20230331101855" (년월일시분초)
		String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		
		// 랜덤한 숫자 5자리
		int ranNum = (int)(Math.random() * 90000 + 10000); // 10000~99999 사이의 랜덤값
		
		// 확장자
		String ext = originName.substring(originName.lastIndexOf("."));
		
		// 최종 수정명
		String changeName = currentTime + ranNum + ext;
		
		// 업로드 시키고자 하는 폴더의 물리적인 경로 알아내기
		String savePath = session.getServletContext().getRealPath("/resources/uploadFiles/"); // "/" 쓰면 webapp 가리킴
		
		// 서버에 파일을 업로드
		try {
			upfile.transferTo(new File(savePath + changeName));
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
		
		return changeName;
	}
	
}
