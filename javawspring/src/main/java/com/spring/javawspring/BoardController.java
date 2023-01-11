package com.spring.javawspring;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.javawspring.pagination.PageProcess;
import com.spring.javawspring.pagination.PageVO;
import com.spring.javawspring.service.BoardService;
import com.spring.javawspring.service.MemberService;
import com.spring.javawspring.vo.BoardVO;
import com.spring.javawspring.vo.MemberVO;

@Controller
@RequestMapping("/board")
public class BoardController {
	private String tableName = "board2";
	  
	@Autowired
	MemberService memberService;
	
	@Autowired
	BoardService boardService;
	
	@Autowired
	PageProcess pageProcess;
	
	@RequestMapping(value = "/boardList", method = RequestMethod.GET)
	public String boardListGet(Model model,PageVO vo,
			@RequestParam(name="pag", defaultValue = "1", required = false) int pag,
			@RequestParam(name="pageSize", defaultValue = "5", required = false) int pageSize) {
		
		vo = pageProcess.getPagination(pag, pageSize, vo, tableName);
		if(vo.getSearchStr() == null) {
			List<BoardVO> vos = boardService.getboardList("","",vo.getStartIndexNo(),pageSize);
			model.addAttribute("vos", vos);
			model.addAttribute("pageVO", vo);
		}
		else {
			System.out.println("검색해서 들어옴");
			List<BoardVO> vos = boardService.getboardList(vo.getSearchStr(),vo.getCategory(),vo.getStartIndexNo(),pageSize);
			model.addAttribute("vos", vos);
			model.addAttribute("pageVO", vo);
			model.addAttribute("searchMid",vo.getMid());
		}
			
		return "board/boardList";
	}
	
	@RequestMapping(value = "/boardInput", method = RequestMethod.GET)
	public String boardInputGet(Model model, HttpSession session,
			@RequestParam(name="pag", defaultValue = "1", required = false) int pag,
			@RequestParam(name="pageSize", defaultValue = "5", required = false) int pageSize ) {
		String mid = (String)session.getAttribute("sMid");
		MemberVO vo = memberService.getMemberIdCheck(mid);
		
		model.addAttribute("pag", pag);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("homePage", vo.getHomePage());
		model.addAttribute("email", vo.getEmail());
		
		return "board/boardInput";
	}
	
	@RequestMapping(value = "/boardInput", method = RequestMethod.POST)
	public String boardInputPost(BoardVO vo) {
		int res = boardService.setBoardInput(vo);
		
		if(res == 1) return "redirect:/msg/boardInputOk";
		return "redirect:/msg/boardInputNo";
	}
	@RequestMapping(value = "/boardContent", method = RequestMethod.GET)
	public String boardContentGet(int idx,Model model,HttpSession session,
			@RequestParam(name="pag", defaultValue = "1", required = false) int pag,
			@RequestParam(name="pageSize", defaultValue = "5", required = false) int pageSize) {
		int cnt = 0;
		
		String sRead = "";
		sRead = (String)session.getAttribute("sRead") == null ? idx+"/" : (String)session.getAttribute("sRead") + idx + "/";  
		
		String[] sReadArr = sRead.split("/");
		for(int i = 0; i < sReadArr.length; i++) {
			if(sReadArr[i].equals(String.valueOf(idx))) {
				cnt++;
				if(cnt == 2) break;
			}
		}
		session.setAttribute("sRead", sRead);
		
		if(cnt != 2) boardService.setBoardReadNum(idx);
		
		BoardVO vo = boardService.getBoardContent(idx);
		
		model.addAttribute("vo",vo);
		model.addAttribute("pag",pag);
		model.addAttribute("pageSize",pageSize);
			
		return "board/boardContent";
	}
	
	@ResponseBody
	@RequestMapping(value = "/boardGood", method = RequestMethod.POST)
	public String boardGoodGet(int idx, HttpSession session) {
		int cnt = 0;
		String sGood = "";
		sGood = (String)session.getAttribute("sGood") == null ? idx+"/" : (String)session.getAttribute("sGood") + idx + "/";  
		
		String[] sReadArr = sGood.split("/");
		for(int i = 0; i < sReadArr.length; i++) {
			if(sReadArr[i].equals(String.valueOf(idx))) {
				cnt++;
				if(cnt == 2) break;
			}
		}
		session.setAttribute("sGood", sGood);
		int res = 0;
		if(cnt != 2) res = boardService.setBoardGood(idx);
	
		
		return String.valueOf(res);
	}
	
}