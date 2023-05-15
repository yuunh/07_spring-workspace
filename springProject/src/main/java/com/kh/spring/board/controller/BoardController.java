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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.kh.spring.board.model.service.BoardServiceImpl;
import com.kh.spring.board.model.vo.Board;
import com.kh.spring.board.model.vo.Reply;
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
	
	/*
	 * * 만약 다중파일 업로드 기능시?
	 * 	 여러개의 input type = "file" 요소에 다 동일한 키값으로 부여 (ex. upfile)
	 * 	 MultipartFile[] upfile로 받으면 됨 (0번 인덱스부터 첨부파일이 담겨 있음) => 반복문 돌려가면서 수행
	 * 	 파일 여러개 올릴 거면 디비 설게시 첨부파일만을 담을 수 있는 테이블로 셋팅할 것!
	 */
	
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
		// System.out.println(bno);
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
	
	@RequestMapping("delete.bo")
	public String deleteBoard(int bno, String filePath, HttpSession session, Model model) {
		
		int result = bService.deleteBoard(bno);
		
		if (result > 0) { // 삭제 성공
			// 첨부파일이 있을 경우 => 파일 삭제
			if (!filePath.equals("")) { // filePath = "resources/xxx/xxx.jsp"
				new File(session.getServletContext().getRealPath(filePath)).delete(); // 서버에 업로드된 파일을 삭제함
			}
			
			// 게시판 리스트 페이지 list.bo.url 재요청
			session.setAttribute("alertMsg", "성공적으로 게시글이 삭제되었습니다.");
			return "redirect:list.bo";
		} else { // 삭제 실패
			// 에러문구 담아서 에러페이지 포워딩(모델)
			model.addAttribute("errorMsg", "게시글 삭제 실패!");
			return "common/errorPage";
		}
	}
	
	@RequestMapping("updateForm.bo")
	public String updateForm(int bno, Model model) {
		// 해당 게시글 조회수 셋팅
		model.addAttribute("b", bService.selectBoard(bno));
		return "board/boardUpdateForm"; // 포워딩(모델)
	}
	
	@RequestMapping("update.bo")
	public String updateBoard(Board b, MultipartFile reupfile, HttpSession session, Model model) {
		
		// 새로 넘어온 첨부파일이 있을 경우
		if (!reupfile.getOriginalFilename().equals("")) {
			
			// 기존에 첨부파일이 있었을 경우 => 기존 첨부파일 지우기
			if (b.getOriginName() != null) {
				new File(session.getServletContext().getRealPath(b.getChangeName())).delete();
			}
			
			// 새로 넘어온 첨부파일 서버 업로드 시키기
			String changeName = saveFile(reupfile, session);
			
			// b에 새로 넘어온 첨부파일에 대한 원본명, 저장 경로 담기
			b.setOriginName(reupfile.getOriginalFilename());
			b.setChangeName("resources/uploadFiles/" + changeName);
		}
		
		/*
		 * b에 boardNo, boardTitle, boardContent 무조건 담겨 있음!!
		 * 
		 * 1. 새로 첨부된 파일 x, 기존 첨부 파일 x
		 * 	=> originName : null, changeName : null
		 * 
		 * 2. 새로 첨부된 파일 x, 기존 첨부파일 o
		 * 	=> originName : 기존 첨부 파일 원본명, changeName : 기존 첨부 파일 저장 경로
		 * 
		 * 3. 새로 첨부된 파일 o, 기존 첨부 파일 x
		 * 	=> originName : 새로운 첨부 파일 원본명, changeName : 새로운 첨부 파일 저장 경로
		 * 
		 * 4. 새로 첨부된 파일 o, 기존 첨부 파일 o
		 * 	=> originName : 새로운 첨부 파일 원본명, changeName : 새로운 첨부 파일 저장 경로
		 */
		
		int result = bService.updateBoard(b);

		if (result > 0) { // 수정 성공 => 상세페이지
			session.setAttribute("alertMsg", "성공적으로 게시글이 수정되었습니다.");
			return "redirect:detail.bo?bno=" + b.getBoardNo();
		} else { // 수정 실패 => 에러페이지 포워딩(모델)
			model.addAttribute("errorMsg", "게시글 수정 실패!");
			return "common/errorPage";
		}
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
	
	@ResponseBody
	@RequestMapping(value = "rlist.bo", produces = "application/json; charset=UTF-8")
	public String ajaxSelectReplyList(int bno) {
		
		ArrayList<Reply> list = bService.selectReplyList(bno);
		
		return new Gson().toJson(list);
	}
	
	@ResponseBody
	@RequestMapping("rinsert.bo")
	public String ajaxInsertReply(Reply r) {

		int result = bService.insertReply(r);
		
		return result > 0 ? "success" : "fail";
	}
	
	@ResponseBody
	@RequestMapping(value = "topList.bo", produces = "application/json; charset=UTF-8")
	public String ajaxTopBoardList() {
		
		ArrayList<Board> list = bService.selectTopBoardList();
		
		// System.out.println(new Gson().toJson(list));
		
		return new Gson().toJson(list);
	} 
}
