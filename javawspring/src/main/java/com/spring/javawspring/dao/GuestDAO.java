package com.spring.javawspring.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;

import com.spring.javawspring.vo.GuestVO;

public interface GuestDAO {

	public ArrayList<GuestVO> getGuestList(@Param("startIndexNo") int startIndexNo,@Param("pageSize") int pageSize,@Param("searchStr") String searchStr,@Param("category") String category);

	public void setGuestInput(@Param("vo") GuestVO vo);

	public int totRecCnt(@Param("searchStr") String searchStr,@Param("category") String category);

	public ArrayList<String> getGuestSearchList(@Param("searchStr") String searchStr,@Param("category") String category,@Param("startIndexNo") int startIndexNo,@Param("pageSize") int pageSize);

}
