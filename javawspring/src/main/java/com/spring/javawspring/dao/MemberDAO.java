package com.spring.javawspring.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;

import com.spring.javawspring.vo.GuestVO;
import com.spring.javawspring.vo.MemberVO;

public interface MemberDAO {

	public MemberVO getMemberIdCheck(@Param("mid") String mid);

	public MemberVO getMemberNickNameCheck(@Param("nickName") String nickName);

	public int getMemberJoinOk(@Param("vo") MemberVO vo);

	public void setMemTotalUpdate(@Param("mid") String mid,@Param("nowTodayPoint") int nowTodayPoint,@Param("todayCnt") int todayCnt);

	public int totRecCnt(@Param("mid") String mid);

	public ArrayList<MemberVO> getMemberList(@Param("mid") String mid, @Param("startIndexNo") int startIndexNo,@Param("pageSize") int pageSize);

	public MemberVO getMemberIdSearch(@Param("searchStr") String searchStr,@Param("category") String category);

	public void setMemberDel(@Param("mid") String mid);

	public void setCertificationNumber(@Param("certificationNumber") String certificationNumber,@Param("mid") String mid);

	public void setNewEncPswd(@Param("encPswd") String encPswd,@Param("mid") String mid);

	public int setMemberInforUpdate(@Param("vo") MemberVO vo);

	public String getMemberFileName(String mid);

	
	
}
