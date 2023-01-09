package com.spring.javawspring.pagination;

import lombok.Data;

@Data
public class PageVO {
	private int pag;
	private int pageSize;
	private int totRecCnt;
	private int totPage;
	private int startIndexNo;
	private int curScrStartNo;
	private int blockSize;
	private int curBlock;
	private int lastBlock;
	
	// 멤버 테이블 
	private String mid;
	
	
	// 게스트 테이블
	private String searchStr;
	private String category;
	
}
