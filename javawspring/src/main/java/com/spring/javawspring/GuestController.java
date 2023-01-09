package com.spring.javawspring;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.javawspring.pagination.PageProcess;
import com.spring.javawspring.pagination.PageVO;
import com.spring.javawspring.service.GuestService;
import com.spring.javawspring.vo.GuestVO;

@Controller
@RequestMapping("/guest")
public class GuestController {
	private String tableName = "guest";
	
	@Autowired
	PageProcess pageProcess;
	
	@Autowired
	GuestService guestService;
	/*  페이징처리
	   1. 페이지(pag)를 결정한다. 처음 접속시의 페이지는 무조건 1Page이다. : pag = 1
	   2. 한 페이지의 분량을 결정한다. : pageSize = 5(사용자가 결정한다.) 이곳에서는 한페이지분량을 5로 했다. pageSize = 5
	   3. 총 레코드 건수를 구한다. totRecCnt => SQL함수중 count(*)을 사용한다.
	   4. 총 페이지 건수를 구한다. totPage => totRecCnt % pageSize 값이 0이면 '몫', 0이 아니면 '몫+1'
	   5. 현재페이지의 시작 인덱스번호를 구한다. startIndexNo => (pag - 1) * pageSize
	   6. 현재 화면에 보여주는 시작번호를 구한다. curScrStartNo = totRecCnt - startIndexNo
	 */
	
	
	@RequestMapping(value = "/guestList", method = RequestMethod.GET)
	public String GuestListGet(Model model,PageVO vo,
	@RequestParam(name="pag" , defaultValue = "1", required = false) int pag)  {
		int pageSize = 5;
				
		if(vo.getSearchStr() == null) {
			PageVO pageVO = pageProcess.getPagination(pag, pageSize,vo,tableName);
			
			ArrayList<GuestVO> vos = guestService.getGuestList(pageVO.getStartIndexNo(),pageSize,"","");
			
			model.addAttribute("vos", vos);
			model.addAttribute("pageVO", pageVO);
		}
		else {
			PageVO pageVO = pageProcess.getPagination(pag, pageSize,vo,tableName);
			ArrayList<GuestVO> vos = guestService.getGuestList(pageVO.getStartIndexNo(),pageSize,vo.getSearchStr(),vo.getCategory());
			
			model.addAttribute("vos", vos);
			model.addAttribute("pageVO", pageVO);
			model.addAttribute("searchStr",vo.getSearchStr());
			model.addAttribute("category",vo.getCategory());
			
		}
		
		return "guest/guestList";
	}
	
//	@RequestMapping(value = "/guestSearch", method = RequestMethod.POST)
//	public String guestSearchPost(String searchStr, String category, Model model,
//			@RequestParam(name="pag" , defaultValue = "1", required = false) int pag) {
//		PageVO vo = new PageVO();
//		
//		return "guest/guestList";
//	}
	
	@RequestMapping(value = "/guestInput", method = RequestMethod.GET)
	public String GuestInputGet() {
		
		return "guest/guestInput";
	}
	
	@RequestMapping(value = "/guestInput", method = RequestMethod.POST)
	public String guestInputPost(GuestVO vo) {
		guestService.setGuestInput(vo);
		
		return "redirect:/msg/guestInputOk";
	}
	
	
	
	
	
}
