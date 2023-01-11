package com.spring.javawspring;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.javawspring.pagination.PageProcess;
import com.spring.javawspring.pagination.PageVO;
import com.spring.javawspring.service.AdminService;
import com.spring.javawspring.service.MemberService;
import com.spring.javawspring.vo.MemberVO;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	AdminService adminService;
	
	@Autowired
	PageProcess pageProcess;
	
	@Autowired
	JavaMailSender mailSender;
	
	@Autowired
	MemberService memberService;
	
	@RequestMapping(value = "/adminMain", method=RequestMethod.GET)
	public String adminMainGet() {
		return "admin/adminMain";
	}
	
	@RequestMapping(value = "/adminLeft", method=RequestMethod.GET)
	public String adminLeftGet() {
		return "admin/adminLeft";
	}
	
	@RequestMapping(value = "/adminContent", method=RequestMethod.GET)
	public String adminContentGet() {
		return "admin/adminContent";
	}
	
	
	@RequestMapping(value = "/adminMemberList", method =  RequestMethod.GET)
	public String memberListGet(Model model,PageVO vo ,
			@RequestParam(name="pag", defaultValue = "1", required = false) int pag,
			@RequestParam(name="pageSize", defaultValue = "5", required = false) int pageSize) {
		
		
		
		return "admin/member/adminMemberList";
	}
	
	@RequestMapping(value = "/member/adminMemberList", method = RequestMethod.GET)
	public String adminMemberListGet(Model model,PageVO vo,
			@RequestParam(name="pag", defaultValue = "1", required = false) int pag,
			@RequestParam(name="pageSize", defaultValue = "5", required = false) int pageSize) {
		
		String tableName = "member2";
		
		
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
		
		return "admin/member/adminMemberList";
	}
	@ResponseBody
	@RequestMapping(value = "/memberLevelUpdate", method = RequestMethod.POST)
	public String memberLevelUpdatePost(int idx,int level) {
		
		
		int res = adminService.setMemberLevelUpdate(idx,level);
		
		return res+"";
	}
	
	@ResponseBody
	@RequestMapping(value = "/adminMemberInfor", method = RequestMethod.POST)
	public MemberVO adminMemberInforPost(String mid) {
		MemberVO vo = memberService.getMemberIdCheck(mid);
		System.out.println("mid = " + mid);
		System.out.println("vo : "+vo);
		return vo;
	}
	
	@ResponseBody
	@RequestMapping(value = "/adminMemberDel", method = RequestMethod.POST)
	public String adminMemberDelPost(int idx) {
		int res = 0;
		
		res = adminService.setMemberDelete(idx);
		
		return String.valueOf(res);
	}
	
	@RequestMapping(value = "/file/fileList", method =  RequestMethod.GET)
	public String fileListGet(Model model,HttpServletRequest request) {
		String realPath = request.getRealPath("/resources/data/ckeditor/");
		String[] files = new File(realPath).list();
		
		model.addAttribute("files",files);
		
		return "admin/file/fileList";
	}
	
	@ResponseBody
	@RequestMapping(value = "/file/imsiFileDelete", method = RequestMethod.POST)
	public String imsiFileDeletePost(String file,HttpServletRequest request) {

		int res = adminService.setImsiFileDelete(file,request);
		
		return String.valueOf(res);
	}
	
	@ResponseBody
	@RequestMapping(value = "/file/imsiFileSelectDelete", method = RequestMethod.POST)
	public String imsiFileAllDeletePost(String allFile,HttpServletRequest request) {
		
		int res = adminService.setImsiFileSelectDelete(allFile,request);
		
		
		return String.valueOf(res);
	}
	
}
