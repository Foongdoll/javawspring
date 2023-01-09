package com.spring.javawspring;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MessageController {
	
	@RequestMapping(value = "/msg/{msgFlag}", method = RequestMethod.GET)
	public String msgGet(@PathVariable String msgFlag, Model model,
		@RequestParam(value="mid", defaultValue = "", required = false) String mid) {
		
		if(msgFlag.equals("memberLoginOk")) {
			model.addAttribute("msg", mid+"님 로그인되셨습니다.");
			model.addAttribute("url", "/member/memberMain");
		}
		else if(msgFlag.equals("memberLoginNo")) {
			model.addAttribute("msg", "로그인에 실패하셨습니다.");
			model.addAttribute("url", "/member/memberLogin");
		}
		else if(msgFlag.equals("memberLogoutOk")) {
			model.addAttribute("msg", mid+"님 로그아웃 되셨습니다.");
			model.addAttribute("url", "/member/memberLogin");
		}
		else if(msgFlag.equals("guestInputOk")) {
			model.addAttribute("msg", "방명록에 글이 등록되었습니다.");
			model.addAttribute("url", "/guest/guestList");
		}
		else if(msgFlag.equals("adminLogoutOk")) {
			model.addAttribute("msg", mid+"님 로그아웃되셨습니다.");
			model.addAttribute("url", "/guest/guestList");
		}
		else if(msgFlag.equals("memberJoinOk")) {
			model.addAttribute("msg", "회원 가입이 정상처리되셨습니다.");
			model.addAttribute("url", "/member/memberLogin");
		}
		else if(msgFlag.equals("memberIdCheckNo")) {
			model.addAttribute("msg", "중복된 아이디입니다.");
			model.addAttribute("url", "/member/memberJoin");
		}
		else if(msgFlag.equals("memberNickNameCheckNo")) {
			model.addAttribute("msg", "중복된 닉네임입니다.");
			model.addAttribute("url", "/member/memberJoin");
		}
		else if(msgFlag.equals("memberJoinOk")) {
			model.addAttribute("msg", "회원가입이 완료되었습니다.");
			model.addAttribute("url", "/member/memberLogin");
		}
		else if(msgFlag.equals("memberJoinNo")) {
			model.addAttribute("msg", "회원가입이 정상처리되지않았습니다.");
			model.addAttribute("url", "/member/memberLogin");
		}
		else if(msgFlag.equals("adminNo")) {
			model.addAttribute("msg", "관리자가 아니시구요...");
			model.addAttribute("url", "/member/memberLogin");
		}
		else if(msgFlag.equals("memberNo")) {
			model.addAttribute("msg", "로그인 후 사용하세요.");
			model.addAttribute("url", "/member/memberLogin");
		}
		else if(msgFlag.equals("levelCheck")) {
			model.addAttribute("msg", "회원 등급을 확인해주세요.");
			model.addAttribute("url", "/member/memberLogin");
		}
		else if(msgFlag.equals("mailSendOk")) {
			model.addAttribute("msg", "메일이 전송되었습니다.");
			model.addAttribute("url", "study/mail/mailForm");
		}
		else if(msgFlag.equals("memberLevel4No")) {
			model.addAttribute("msg", "준회원 등급은 이용불가능합니다.");
			model.addAttribute("url", "member/memberMain");
		}
		else if(msgFlag.equals("memberInforNo")) {
			model.addAttribute("msg", "이 회원은 정보공개를 비동의한 회원입니다.");
			model.addAttribute("url", "member/memberList");
		}
		else if(msgFlag.equals("memberDelNo")) {
			model.addAttribute("msg", "회원 정보가 일치하지 않습니다.");
			model.addAttribute("url", "member/memberList");
		}
		else if(msgFlag.equals("memberPwdSearchOk")) {
			model.addAttribute("msg", "인증 번호가 이메일로 발송되었습니다.");
			model.addAttribute("url", "member/memberPwdSearch?pwdSw=1&mid="+mid);
		}
		else if(msgFlag.equals("emailNo")) {
			model.addAttribute("msg", "회원 정보가 일치하지않습니다.");
			model.addAttribute("url", "member/memberPwdSearch?pwdSw=0");
		}
		
		
		return "include/message";
	}
}
