package com.spring.javawspring.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.spring.javawspring.vo.BoardVO;

public interface BoardDAO {

	public List<BoardVO> getboardList(@Param("searchStr") String searchStr,@Param("category") String category, @Param("startIndexNo") int startIndexNo,@Param("pageSize") int pageSize);

	public int getTotRecCnt(@Param("searchStr") String searchStr,@Param("category") String category);

	public int setBoardInput(@Param("vo") BoardVO vo);

	public BoardVO getBoardContent(@Param("idx") int idx);

	public void setBoardReadNum(@Param("idx") int idx);

	public int setBoardGood(@Param("idx") int idx);


}
