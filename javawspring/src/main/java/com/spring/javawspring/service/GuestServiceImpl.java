package com.spring.javawspring.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.javawspring.dao.GuestDAO;
import com.spring.javawspring.vo.GuestVO;


@Service
public class GuestServiceImpl implements GuestService{

	@Autowired
	GuestDAO guestDAO;
	
	@Override
	public ArrayList<GuestVO> getGuestList(int startIndexNo, int pageSize, String searchStr, String category){
		return guestDAO.getGuestList(startIndexNo, pageSize, searchStr, category);
	}

	@Override
	public void setGuestInput(GuestVO vo) {
		
		guestDAO.setGuestInput(vo);
	}

	@Override
	public ArrayList<String> getGuestSearchList(String searchStr, String category, int startIndexNo, int pageSize) {
		return guestDAO.getGuestSearchList(searchStr, category ,startIndexNo, pageSize);
	}

	@Override
	public int totRecCnt(String searchStr, String category) {
		return guestDAO.totRecCnt(searchStr, category);
	}

	
}