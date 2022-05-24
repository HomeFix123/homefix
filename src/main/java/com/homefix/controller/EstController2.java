package com.homefix.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/estimation2")
public class EstController2 {
	
	@GetMapping("/{step}")
	public void viewPage(@PathVariable String step) {
		//return "/estimation2/" + step;
	}
	
	
	/***RESTful 방식 2가지 
 	* 기능 				기존URI							요청			***RESTful URI
--------------------------------------------------------------------------------
	'견적목록보기		/estimation2/getEstList			GET			/estimation2
	'견적작성화면		/estimation2/insertEst			GET			/estimation2/write
	'게시판작성			/estimation2/saveEst			POST		/estimation2/write
	'게시판상세보기		/estimation2/getEst?seq=글번호	GET			/estimation2/글번호
	'게시판수정			/estimation2/updateEst?seq=글번호	PUT			/estimation2/글번호
	'게시판삭제			/estimation2/deleteEst?seq=글번호	DELETE		/estimation2/글번호
-------------------------------------------------------------------------------- 
 * 1. value값 써주기 => @RequestMapping(value="/write", method=RequestMethod.GET) 
 * 2. 리턴값 뷰페이지 지정 필수
 * 3. 뷰페이지(insertBoard 페이지)에서 <a th:href='@{/board/write}'>새글등록</a> 경로를 RESTful URI로 수정해줘야 페이지가 뜸 */
	
	
//	//입력하기-----------------------------------------
//	@GetMapping("/writer") //RESTful 방식
//	public String insertEst() {
//		return "/estimation2/estimation"; //URL 방식 => restful 이라면 리턴값 따로 써주어야 함
//	}
//		
//	@PostMapping("/writer")
//	public String saveEst() {
//		empService.insertBoard();
//		return "redirect: ";
//	}
//	//--------------------------------------------------
//	
	
	
	
	
	
	
	
	
	
}
