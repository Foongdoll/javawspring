package com.spring.javawspring.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.spring.javawspring.vo.BoardVO;
import com.spring.javawspring.vo.GoodVO;

public interface BoardDAO {

	public List<BoardVO> getboardList(@Param("searchStr") String searchStr,@Param("category") String category, @Param("startIndexNo") int startIndexNo,@Param("pageSize") int pageSize);

	public int getTotRecCnt(@Param("searchStr") String searchStr,@Param("category") String category);

	public int setBoardInput(@Param("vo") BoardVO vo);

	public BoardVO getBoardContent(@Param("idx") int idx);

	public void setBoardReadNum(@Param("idx") int idx);

	public int setBoardGood(@Param("idx") int idx);

	public GoodVO getBoardGoodCheck(@Param("partIdx") int partIdx,@Param("part") String part,@Param("mid") String mid);

	public ArrayList<BoardVO> getPrevNext(@Param("idx") int idx);

	public void setBoardDeleteOk(@Param("idx") int idx);

	public void setContentUpdateOk(@Param("vo") BoardVO vo);

	public void setBoardGoodFinger(@Param("idx") int idx,@Param("goodCnt") int goodCnt,@Param("good") int good);


}
