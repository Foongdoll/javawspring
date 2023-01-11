package com.spring.javawspring;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
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
import com.spring.javawspring.vo.GoodVO;
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
	public String boardInputPost(BoardVO vo, HttpServletRequest request) {
		// content에 이미지가 저장되어있다면, 저장된 이미지만 추출해서 /resources/data/board/폴더에 저장시켜준다.
		boardService.imgCheck(vo.getContent(),request);
		
		
		// 이미지 복사 작업이 끝나면, board 폴더에 실제로 저장된 파일명을 DB에 저장시켜준다.(/resources/data/ckeditor/ ===>/resources/data/board/)
		
		vo.setContent(vo.getContent().replace("/data/ckeditor/", "/data/board/"));
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
		
		String mid = (String)session.getAttribute("sMid");
		GoodVO goodVO = boardService.getBoardGoodCheck(idx,"board",mid);
		
		model.addAttribute("goodVO", goodVO);
		
		
		ArrayList<BoardVO> pnVos = boardService.getPrevNext(idx);
		
		model.addAttribute("pnVos",pnVos);
		
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
	
	@RequestMapping(value = "/boardDeleteOk", method = RequestMethod.GET)
	public String boardDelCheckGet(int idx, int pag, int pageSize,Model model, HttpServletRequest request) {
		// 게시글의 사진이 존재한다면 서버에 저장된 사진 파일을 먼저 삭제한다.
		BoardVO vo = boardService.getBoardContent(idx);
		
		if(vo.getContent().contains("src")) boardService.imgDelete(vo.getContent(),request);
			
		// DB에서 실제로 존재하는 게시글을 삭제한다.
		boardService.setBoardDeleteOk(idx);
		
		model.addAttribute("parameter", "?pag="+pag+"&pageSize="+pageSize);
		
		return "redirect:/msg/boardDeleteOk";
	}
	
	@RequestMapping(value = "/boardUpdate", method = RequestMethod.GET)
	public String boardUpdateGet(int idx, int pag, int pageSize,Model model, HttpServletRequest request) {
		// 수정창으로 이동시에는 먼저 원본파일에 그림파일이있다면 현재폴더(board)의 그림파일을 ckeditor로 복사한다.
		BoardVO vo = boardService.getBoardContent(idx);
		
		if(vo.getContent().contains("src")) boardService.imgCheckUpdate(vo.getContent(),request);
		
		model.addAttribute("vo", vo);
		model.addAttribute("pag", pag);
		model.addAttribute("pageSize",pageSize);
		
		return "board/boardUpdate";
	}
	
	@RequestMapping(value = "/boardUpdate", method = RequestMethod.POST)
	public String boardUpdatePost(BoardVO vo, int pag, int pageSize,Model model, HttpServletRequest request) {
		// 수정된 자료가 원본자료와 완전히 동일하다면 수정할 필요가없기에,  DB에 저장된 원본자료를 불러와서 비교
		BoardVO origVo = boardService.getBoardContent(vo.getIdx());
		
		if(!origVo.getContent().equals(vo.getContent())) {
			// 원래 있던 파일들 다 지움 원본파일들을 ckeditor로 옮김
			if(origVo.getContent().contains("src=\"/")) {
				vo.setContent(vo.getContent().replace("/data/board/", "/data/ckeditor/"));
				boardService.imgDelete(origVo.getContent(), request); 	  // 보드에 있는 파일들을 지움
				boardService.imgCheck(vo.getContent(), request);          // 수정된 내용에 있는 이미지파일을 ckeditor에서 board로 조장함
				vo.setContent(vo.getContent().replace("/data/ckeditor/","/data/board/"));
			}
		}
		
		boardService.setContentUpdateOk(vo); //  
		
		
		return "redirect:/msg/boardUpdateOk";
	}
	@ResponseBody
	@RequestMapping(value = "/boardGoodPlusMinus", method = RequestMethod.POST)
	public void boardGoodPlusMinusPost(int idx,int GoodCnt) {
		BoardVO vo = boardService.getBoardContent(idx);
		
		if(vo.getGood() > 0 ) boardService.setBoardGoodFinger(idx,GoodCnt,vo.getGood());
		else if(vo.getGood() == 0 && GoodCnt == 1) boardService.setBoardGoodFinger(idx,GoodCnt,vo.getGood());
	}
	
	@ResponseBody
	@RequestMapping(value = "/boardGoodDBCheck", method = RequestMethod.POST)
	public String boardGoodDBCheckPost(int totSw,int idx,HttpServletRequest request) {
		int div = totSw % 2;
		int res = 0;
		
		System.out.println("sw"+totSw);
		System.out.println("div "+div);
		System.out.println("idx"+idx);
		
		String mid = (String)request.getSession().getAttribute("sMid");
		
//		GoodVO vo = boardService.getBoardGoodCheck(, mid, mid);
		
		return String.valueOf(res);
	}
	
}