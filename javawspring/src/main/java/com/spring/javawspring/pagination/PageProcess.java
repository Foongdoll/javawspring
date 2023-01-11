package com.spring.javawspring.pagination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.javawspring.dao.BoardDAO;
import com.spring.javawspring.dao.GuestDAO;
import com.spring.javawspring.dao.MemberDAO;
import com.spring.javawspring.vo.MemberVO;

@Service
public class PageProcess {
	
	@Autowired
	MemberDAO memberdao;

	@Autowired
	GuestDAO guestdao;
	
	@Autowired
	BoardDAO boardDAO;
	
	public PageVO getPagination(int pag, int pageSize,PageVO vo,String tableName) {
		int totRecCnt = 0;
		
		if(tableName.equals("member2")) {
			if(vo.getMid() == null) totRecCnt = memberdao.totRecCnt("");
			else totRecCnt =  memberdao.totRecCnt(vo.getMid());
		}
		else if(tableName.equals("guest")) {
			if(vo.getSearchStr() == null) totRecCnt = guestdao.totRecCnt("","");
			else totRecCnt = guestdao.totRecCnt(vo.getSearchStr(), vo.getCategory());
		}
		else if(tableName.equals("board2")) {
			totRecCnt = boardDAO.getTotRecCnt("","");
		}
		
		int totPage = (totRecCnt % pageSize)== 0 ? totRecCnt / pageSize : (totRecCnt / pageSize) + 1;
		int startIndexNo = (pag - 1) * pageSize;
		int curScrStartNo = totRecCnt - startIndexNo;
		int blockSize = 3;
		int curBlock = (pag - 1) / blockSize;
		int lastBlock = (totPage - 1) / blockSize;
		
		vo.setTotPage(totPage);
		vo.setStartIndexNo(startIndexNo);
		vo.setCurScrStartNo(curScrStartNo);
		vo.setBlockSize(blockSize);
		vo.setCurBlock(curBlock);
		vo.setLastBlock(lastBlock);
		vo.setPag(pag);
		vo.setPageSize(pageSize);
		vo.setTotRecCnt(totRecCnt);
		
		return vo;
	}
}
