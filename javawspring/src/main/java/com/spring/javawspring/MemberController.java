package com.spring.javawspring;

import java.util.ArrayList;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.spring.javawspring.pagination.PageProcess;
import com.spring.javawspring.pagination.PageVO;
import com.spring.javawspring.service.MemberService;
import com.spring.javawspring.vo.MemberVO;

@Controller
@RequestMapping("/member")
public class MemberController {
	private String tableName = "member2";
	@Autowired
	JavaMailSender mailSender;
	
	@Autowired
	PageProcess pageProcess;
	
	@Autowired
	MemberService memberService;
	
	@Autowired
	BCryptPasswordEncoder PasswordEncoder;
	
	@RequestMapping(value = "/memberLogin", method = RequestMethod.GET)
	public String memberLoginGet(HttpServletRequest request) {
		// 로그인폼 호출시에 기존에 저장된 쿠키가 있다면 불러와서 mid에 담아서 넘겨준다.
		Cookie[] cookies = request.getCookies();
		for(int i=0; i<cookies.length; i++) {
			if(cookies[i].getName().equals("cMid")) {
				request.setAttribute("mid", cookies[i].getValue());
				break;
			}
		}
		
		return "member/memberLogin";
	}
	
	@RequestMapping(value = "/memberLogin", method = RequestMethod.POST)
	public String memberLoginPost( HttpServletRequest request, HttpServletResponse response,
			@RequestParam(name = "mid", defaultValue = "", required = false) String mid,
			@RequestParam(name = "pwd", defaultValue = "", required = false) String pwd,
			@RequestParam(name = "idCheck", defaultValue = "", required = false) String idCheck) {
		
		MemberVO vo = memberService.getMemberIdCheck(mid);
		
		if(vo != null && PasswordEncoder.matches(pwd, vo.getPwd()) && vo.getUserDel().equals("NO")) {
			// 회원 인증 처리된 경우 수행할 내용? session에 필요한 자료를 저장, 쿠키값처리, 그날 방문지수 1증가,방문포인트 10+
			String strLevel = "";
			
			if(vo.getLevel() == 0) strLevel = "관리자";
			else if(vo.getLevel() == 1) strLevel = "운영자";
			else if(vo.getLevel() == 2) strLevel = "우수회원";
			else if(vo.getLevel() == 3) strLevel = "정회원";
			else if(vo.getLevel() == 4) strLevel = "준회원";
			
			request.getSession().setAttribute("sStrLevel", strLevel);
			request.getSession().setAttribute("sLevel", vo.getLevel());
			request.getSession().setAttribute("sMid", vo.getMid());
			request.getSession().setAttribute("sNickName", vo.getNickName());
			
			if(idCheck.equals("on")) {
				Cookie cookie = new Cookie("cMid", vo.getMid());
				cookie.setMaxAge(60 * 60 * 24 * 7);
				response.addCookie(cookie);
			}
			else {
				Cookie[] cookies = request.getCookies();
				for(int i = 0; i < cookies.length; i++) {
					if(cookies[i].getValue().equals("cMid")) {
						cookies[i].setMaxAge(0);
						response.addCookie(cookies[i]);
						break;
					}
				}
			}
			
			// 로그인한 사용자의 방문횟수 누적
			memberService.setMemberVisitCnt(vo);
			
			
			return "redirect:/msg/memberLoginOk?mid="+mid;
		}
		else return "redirect:/msg/memberLoginNo";
	}
	
	@RequestMapping(value = "/memberLogout", method = RequestMethod.GET)
	public String memberLogoutGet(HttpSession session) {
		String mid = (String)session.getAttribute("sMid");
		
		session.invalidate();
		
		return "redirect:/msg/memberLogoutOk?mid="+mid;
	}
	
	@RequestMapping(value = "/memberMain", method = RequestMethod.GET)
	public String memberMainGet(HttpSession session,Model model) {
		String mid = (String)session.getAttribute("sMid");
		
		MemberVO vo = memberService.getMemberIdCheck(mid);
		model.addAttribute("vo" , vo);
		
		return "member/memberMain"; 
	}
  
	// 회원 가입 폼
	@RequestMapping(value = "/memberJoin", method = RequestMethod.GET)
	public String memberJoinGet() {
		
		return "/member/memberJoin";
	}
	// 회원 가입 처리
	@RequestMapping(value = "/memberJoin", method = RequestMethod.POST)
	public String memberJoinPost(MultipartFile fName,MemberVO vo,HttpServletRequest request) {
		
		if(memberService.getMemberIdCheck(vo.getMid()) != null) {
			return "redirect:/msg/memberIdCheckNo";
		}
		else if(memberService.getMemberNickNameCheck(vo.getNickName()) != null){
			
			return "redirect:/msg/memberNickNameCheckNo";
		}
		else {
			vo.setPwd(PasswordEncoder.encode(vo.getPwd()));
			
			// 체크가 완료되면 vo에 담긴자료를 DB에 저장시켜준다. (회원 가입)
			int res = memberService.getMemberJoinOk(vo,fName,request);
			
			if(res == 1) return "redirect:/msg/memberJoinOk";
			else return "redirect:/msg/memberJoinNo";	
		
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/memberIdCheck", method = RequestMethod.POST)
	public String memberIdCheckPost(String mid) {
		String res = "0";
		
		MemberVO vo = memberService.getMemberIdCheck(mid);
		
		if(vo != null) res = "1";
		
		return res;
	}
	
	@ResponseBody
	@RequestMapping(value = "/memberNickNameCheck", method = RequestMethod.POST)
	public String memberNickNameCheckPost(String nickName) {
		String res = "0";
		
		MemberVO vo = memberService.getMemberNickNameCheck(nickName);
		
		if(vo != null) res = "1";
		
		return res;
	}
	
	
//	@RequestMapping(value = "/memberList", method =  RequestMethod.GET)
//	public String memberListGet(Model model,MemberVO vo,
//			@RequestParam(name="pag", defaultValue = "1", required = false) int pag,
//			@RequestParam(name="pageSize", defaultValue = "5", required = false) int pageSize) {
//		int totRecCnt = 0;
//		
//		if(vo.getMid() == null) {
//			totRecCnt = memberService.totRecCnt("");
//			PageVO pageVO = pageProcess.getPagination(pag,pageSize,totRecCnt);
//			
//			ArrayList<MemberVO> vos = memberService.getMemberList("",pageVO.getStartIndexNo(),pageSize);
//			
//			model.addAttribute("vos", vos);
//			model.addAttribute("pageVO",pageVO);
//		}
//		else {
//			totRecCnt = memberService.totRecCnt(vo.getMid());
//			PageVO pageVO = pageProcess.getPagination(pag,pageSize,totRecCnt);
//			ArrayList<MemberVO> vos = memberService.getMemberList(vo.getMid(),pageVO.getStartIndexNo(),pageSize);
//			
//			model.addAttribute("vos", vos);
//			model.addAttribute("searchMid", vo.getMid());
//			model.addAttribute("pageVO",pageVO);
//		}
//		
//		return "member/memberList";
//	}
	
	@RequestMapping(value = "/memberList", method =  RequestMethod.GET)
	public String memberListGet(Model model,PageVO vo ,
			@RequestParam(name="pag", defaultValue = "1", required = false) int pag,
			@RequestParam(name="pageSize", defaultValue = "5", required = false) int pageSize) {
		
		if(vo.getMid() == null) {
			PageVO pageVO = pageProcess.getPagination(pag,pageSize,vo,tableName);
			
			ArrayList<MemberVO> vos = memberService.getMemberList("",pageVO.getStartIndexNo(),pageSize);
			
			model.addAttribute("vos", vos);
			model.addAttribute("pageVO",pageVO);
		}
		else {
			PageVO pageVO = pageProcess.getPagination(pag,pageSize,vo,tableName);
			ArrayList<MemberVO> vos = memberService.getMemberList(vo.getMid(),pageVO.getStartIndexNo(),pageSize);
			
			model.addAttribute("vos", vos);
			model.addAttribute("searchMid", vo.getMid());
			model.addAttribute("pageVO",pageVO);
		}
		
		return "member/memberList";
	}
	

	@RequestMapping(value = "/memberIdSearch", method = RequestMethod.GET)
	public String memberIdSearchGet() {
		return "member/memberIdSearch";
	}
	
	@ResponseBody
	@RequestMapping(value = "/memberIdSearch", method = RequestMethod.POST)
	public MemberVO memberIdSearchPost(String searchStr, String category) {
	    MemberVO vo = memberService.getMemberIdSearch(searchStr,category);
		return vo;
	}
	// 비밀번호 찾기 폼	
	@RequestMapping(value = "/memberPwdSearch", method = RequestMethod.GET)
	public String memberPwdSearchGet(@RequestParam(name="pwdSw", defaultValue = "", required = false) int pwdSw,
			@RequestParam(name="mid", defaultValue = "", required = false) String mid,
			Model model) {
		model.addAttribute("pwdSw", pwdSw);
		model.addAttribute("mid", mid);
		
		return "member/memberPwdSearch";
	}
	
	@RequestMapping(value = "/memberPwdSearch", method = RequestMethod.POST)
	public String memberPwdSearchPost(String toMail, String mid, 
			HttpServletRequest request, HttpServletResponse response) {
		
		MemberVO vo = memberService.getMemberIdCheck(mid);
		if(!vo.getEmail().equals(toMail)) {
			return "redirect:/msg/emailNo";
		}
		else {
			memberService.getPswdcertificationProcess(toMail, mid, 0, request); // 지금 DB안에 uuid가 안바뀜 근대 비밀번호는 최신  uuid로 바뀜
			return "redirect:/msg/memberPwdSearchOk?mid="+mid;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/memberPwdSearchOk", method = RequestMethod.POST)
	public String memberPwdSearchOkPost(String pwdCPswd, String mid) {
		String res = "";
		
		MemberVO vo = memberService.getMemberIdCheck(mid);
		
		if(!vo.getUuid().equals(pwdCPswd)) {
			res = "1";
		}
		else {
			String uuid = UUID.randomUUID().toString().substring(0,8);
			String encPswd = PasswordEncoder.encode(uuid);
			memberService.setNewEncPswd(encPswd,mid); //DB 비밀번호를 발급받은 임시비밀번호로 바꿔줌
			res = uuid;
		}
		return res;
	}
	
	@RequestMapping(value = "/memberInfor", method = RequestMethod.GET)
	public String memberInforGet(String mid,
			@RequestParam(name="pag", defaultValue = "", required = false) int pag,
			Model model, HttpSession session) {
		MemberVO vo = memberService.getMemberIdCheck(mid);
		
		int level = session.getAttribute("sLevel") == null ? 99 : (int)session.getAttribute("sLevel");
		if(level == 99) return "redirect:/msg/memberNo";
		else if(level != 0) if(vo.getUserInfor().equals("비공개")) return "redirect:/msg/memberInforNo";
		
		
		vo.setBirthday(vo.getBirthday().substring(0,10));
		vo.setAddress(vo.getAddress().replace("/", " "));
		vo.setStartDate(vo.getStartDate().substring(0,10));
		vo.setLastDate(vo.getLastDate().substring(0,10));
		
		
		model.addAttribute("vo",vo);
		model.addAttribute("pag",pag);
		
		return "member/memberInfor";
	}
	
	@RequestMapping(value = "/memberDel", method = RequestMethod.GET)
	public String memberDelGet() {
		return "member/memberDel";
	}
	
	@RequestMapping(value = "/memberDel", method = RequestMethod.POST)
	public String memberDelPost(String mid, String pwd, String nickName) {
		MemberVO vo = memberService.getMemberIdCheck(mid);
		
		if(!PasswordEncoder.matches(pwd, vo.getPwd()) || vo.getMid() == null || vo.getNickName() != nickName) {
			return "redirect:/msg/memberDelNo";
		}
		
		memberService.setMemberDel(mid);
		
		
		return "member/memberDel";
	}
	
	
	@RequestMapping(value = "/memberPwdChange", method = RequestMethod.GET)
	public String memberPwdChangeGet() {
		
		return "member/memberPwdChange";
	}
	
	@ResponseBody
	@RequestMapping(value = "/memberPwdChange", method = RequestMethod.POST)
	public String memberPwdChangePost(String curPwd, String newPwd, HttpSession session) {
		String res = "0";
		String mid = (String)session.getAttribute("sMid");
		MemberVO vo = memberService.getMemberIdCheck(mid);
		if(!PasswordEncoder.matches(curPwd, vo.getPwd())) {
			res = "0";
		}
		else {
			newPwd = PasswordEncoder.encode(newPwd);
			memberService.setNewEncPswd(newPwd, mid);
			res = "1";
		}
		
		return res;
	}
	
	@ResponseBody
	@RequestMapping(value = "/memberCheck", method = RequestMethod.POST)
	public MemberVO memberCheckPost(HttpServletRequest request,int idx,
			@RequestParam(name = "pwd", defaultValue = "", required = false) String pwd, 
			@RequestParam(name = "email", defaultValue = "", required = false) String email) throws MessagingException {
		String mid = (String)request.getSession().getAttribute("sMid");
		MemberVO vo = memberService.getMemberIdCheck(mid);
		// 0 : 비밀번호 인증 1 : 이메일 인증
		if(idx == 0) {
			if(!PasswordEncoder.matches(pwd, vo.getPwd())) vo = null;
		}
		else {
			memberService.getPswdcertificationProcess(email, mid, 1, request);
			vo = memberService.getMemberIdCheck(mid);
		}
		
		return vo;
	}
	
	@RequestMapping(value = "/memberInforUpdate", method = RequestMethod.GET)
	public String memberInforUpdateGet(Model model,HttpSession session) {
		String mid = (String)session.getAttribute("sMid");
		MemberVO vo = memberService.getMemberIdCheck(mid);
		vo.setEmail1(vo.getEmail().split("@")[0]);
		vo.setEmail2(vo.getEmail().split("@")[1]);
		
		vo.setTel1(vo.getTel().split("-")[0]);
		vo.setTel2(vo.getTel().split("-")[1]);
		vo.setTel3(vo.getTel().split("-")[2]);
		
		if(vo.getAddress().split("/").length == 3) {
			vo.setPostcode(vo.getAddress().split("/")[0]);
			vo.setRoadAddress(vo.getAddress().split("/")[1]);
			vo.setDetailAddress(vo.getAddress().split("/")[2]);
		}
		else if(vo.getAddress().split("/").length == 4) {
			vo.setPostcode(vo.getAddress().split("/")[0]);
			vo.setRoadAddress(vo.getAddress().split("/")[1]);
			vo.setDetailAddress(vo.getAddress().split("/")[2]);
			vo.setExtraAddress(vo.getAddress().split("/")[3]);
		}
		
		model.addAttribute("vo", vo);
		
		return "member/memberInforUpdate";
	}
	
	@RequestMapping(value = "/memberInforUpdate", method = RequestMethod.POST)
	public String memberInforUpdatePost(MemberVO vo, MultipartFile fName, HttpServletRequest request) {
		
		int res = memberService.setMemberInforUpdate(vo,fName,request);
		if(res == 1) return "redirect:/msg/memberInforUpdateOk";
		else return "redirect:/msg/memberInforUpdateNo";
	}
	
}
