package com.spring.javawspring.service;

import java.util.ArrayList;

import com.spring.javawspring.vo.GuestVO;
import com.spring.javawspring.vo.MemberVO;

public interface StudyService {

	public String[] getCityStringArr(String dodo);

	public ArrayList<String> getCityArrayListArr(String dodo);

	public GuestVO getGuestMid(String mid);

	public ArrayList<GuestVO> getGuestNames(String mid);

	public ArrayList<MemberVO> getMembersInfor();

	public ArrayList<MemberVO> getMemberMail();

}
