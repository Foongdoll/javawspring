package com.spring.javawspring.service;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.spring.javawspring.vo.MemberVO;

public interface MemberService {

	public MemberVO getMemberIdCheck(String mid);

	public MemberVO getMemberNickNameCheck(String nickName);

	public int getMemberJoinOk(MemberVO vo, MultipartFile fName, HttpServletRequest request);

	public void setMemberVisitCnt(MemberVO vo);

	public ArrayList<MemberVO> getMemberList(String mid, int startIndexNo, int pageSize);

	public MemberVO getMemberIdSearch(String searchStr, String category);

	public void setMemberDel(String mid);

	public int totRecCnt(String mid);

	public void getPswdcertificationProcess(String toMail, String mid, int sw, HttpServletRequest request);

	public void setNewEncPswd(String encPswd, String mid);

	public int setMemberInforUpdate(MemberVO vo, MultipartFile fName, HttpServletRequest request);

}
