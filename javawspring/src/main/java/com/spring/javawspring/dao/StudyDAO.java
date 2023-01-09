package com.spring.javawspring.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;

import com.spring.javawspring.vo.GuestVO;
import com.spring.javawspring.vo.MemberVO;

public interface StudyDAO {

	public GuestVO getGuestMid(@Param("mid") String mid);

	public ArrayList<GuestVO> getGuestNames(@Param("name") String mid);

	public ArrayList<MemberVO> getMembersInfor();

	public ArrayList<MemberVO> getMemberMail();

}
