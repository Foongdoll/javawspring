package com.spring.javawspring.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.spring.javawspring.vo.BoardVO;
import com.spring.javawspring.vo.GoodVO;

public interface BoardService {

	public List<BoardVO> getboardList(String searchStr, String category, int startIndexNo, int pageSize);

	public int setBoardInput(BoardVO vo);

	public BoardVO getBoardContent(int idx);

	public void setBoardReadNum(int idx);

	public int setBoardGood(int idx);

	public GoodVO getBoardGoodCheck(int partIdx, String part, String mid);

	public ArrayList<BoardVO> getPrevNext(int idx);

	public void imgCheck(String content, HttpServletRequest request);

	public void setBoardDeleteOk(int idx);

	public void imgDelete(String content, HttpServletRequest request);

	public void imgCheckUpdate(String content, HttpServletRequest request);

	public void setContentUpdateOk(BoardVO vo);

	public void setBoardGoodFinger(int idx, int goodCnt, int good);

}
