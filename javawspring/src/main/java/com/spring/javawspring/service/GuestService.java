package com.spring.javawspring.service;

import java.util.ArrayList;

import com.spring.javawspring.vo.GuestVO;

public interface GuestService {

	public ArrayList<GuestVO> getGuestList(int startIndexNo, int pageSize, String searchStr, String category);

	public void setGuestInput(GuestVO vo);

	public ArrayList<String> getGuestSearchList(String searchStr, String category, int startIndexNo, int pageSize);

	public int totRecCnt(String searchStr, String category);

	
}
