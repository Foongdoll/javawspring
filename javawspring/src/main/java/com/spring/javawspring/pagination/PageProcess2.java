package com.spring.javawspring.pagination;

import org.springframework.stereotype.Service;

@Service
public class PageProcess2 {
	

	public PageVO getPagination(int pag, int pageSize, int totRecCnt) {
		PageVO vo = new PageVO();
		 
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
