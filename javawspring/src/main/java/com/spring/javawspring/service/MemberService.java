package com.spring.javawspring.service;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.spring.javawspring.vo.MemberVO;

public interface MemberService {

	public MemberVO getMemberIdCheck(String mid);

	public MemberVO getMemberNickNameCheck(String nickName);

	public int getMemberJoinOk(MemberVO vo);

	public void setMemberVisitCnt(MemberVO vo);

	public ArrayList<MemberVO> getMemberList(String mid, int startIndexNo, int pageSize);

	public MemberVO getMemberIdSearch(String searchStr, String category);

	public void setMemberDel(String mid);

	public int totRecCnt(String mid);

	public void getPwdcertificationProcess(String toMail, String mid, HttpServletRequest request, HttpServletResponse response);

	public void setNewEncPswd(String encPswd, String mid);

}
