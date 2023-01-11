package com.spring.javawspring.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.spring.javawspring.dao.BoardDAO;
import com.spring.javawspring.vo.BoardVO;
import com.spring.javawspring.vo.GoodVO;

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	BoardDAO boardDAO;

	@Override
	public List<BoardVO> getboardList(String searchStr,String category, int startIndexNo, int pageSize) {
		return boardDAO.getboardList(searchStr,category, startIndexNo, pageSize);
	}

	@Override
	public int setBoardInput(BoardVO vo) {
		return boardDAO.setBoardInput(vo);
	}

	@Override
	public BoardVO getBoardContent(int idx) {
		return boardDAO.getBoardContent(idx);	
	}

	@Override
	public void setBoardReadNum(int idx) {
		boardDAO.setBoardReadNum(idx);
	}

	@Override
	public int setBoardGood(int idx) {
		return boardDAO.setBoardGood(idx);
	}

	@Override
	public GoodVO getBoardGoodCheck(int partIdx, String part, String mid) {
		return boardDAO.getBoardGoodCheck(partIdx,part,mid);
	}

	@Override
	public ArrayList<BoardVO> getPrevNext(int idx) {
		 return boardDAO.getPrevNext(idx);
	}

	@Override
	public void imgCheck(String content, HttpServletRequest request) {
		// src 부터 실제 그림파일 시작하는인덱스번호는 /resources/data/ckeditor/ 경로라고치면 31번쨰부터 
		// content안에 그림파일이 존재할때만 작업을 수행 할수 있도록 한다.
		if(content.indexOf("src=\"/") == -1) return;

		request = ((ServletRequestAttributes)(RequestContextHolder.currentRequestAttributes())).getRequest();
		String upLoadPath = request.getSession().getServletContext().getRealPath("/resources/data/ckeditor");
		
		int position = 32;
		String nextImg = content.substring(content.indexOf("src=\"/") + position);
		
		boolean sw = true;
		while(sw) {
			String imgFile = nextImg.substring(0,nextImg.indexOf("\""));
			
			String origFilePath = upLoadPath +"/"+imgFile;
			String copyFilePath = upLoadPath.replace("ckeditor", "board/") + imgFile;
			
			fileCopyCheck(origFilePath,copyFilePath);
			
			if(nextImg.indexOf(nextImg.indexOf("src=\"/")) == -1) sw = false;
			else {
				nextImg = nextImg.substring(nextImg.indexOf("src=\"/") + position);
			}
		}
	}

	public void fileCopyCheck(String origFilePath, String copyFilePath) {
		File origFile = new File(origFilePath); 
		File copyFile = new File(copyFilePath); 
		
		try {
			InputStream  is = new FileInputStream(origFile);
			OutputStream os = new FileOutputStream(copyFile);
			
			byte[] buffer = new byte[2048];
			int cnt = 0;
			
			while((cnt = is.read(buffer)) != -1) {
				os.write(buffer,0, cnt);
			}
			os.flush();
			os.close();
			is.close();
			
		} catch (FileNotFoundException e) {e.printStackTrace();} 
		  catch (IOException e) {e.printStackTrace();}
		
	}

	@Override
	public void setBoardDeleteOk(int idx) {
		boardDAO.setBoardDeleteOk(idx);
	}

	@Override
	public void imgDelete(String content,HttpServletRequest request) {
		
		request = ((ServletRequestAttributes)(RequestContextHolder.currentRequestAttributes())).getRequest();
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/board/");
		
		int position = 29;
		
		String nextImg = content.substring(content.indexOf("src=\"/")+position);
		boolean sw = true;
		
		while(sw) {
			String imgFile = nextImg.substring(0, nextImg.indexOf("\""));
			String origFilePath = realPath + imgFile;
			
			fileDelete(origFilePath);
			
			if(!nextImg.contains("src=\"/")) sw = false;
			else nextImg = nextImg.substring(nextImg.indexOf("src=\"/")+position);
		}
	}

	public void fileDelete(String origFilePath) {
		File origFile = new File(origFilePath);
		if(origFile.exists()) origFile.delete();
	}

	@Override
	public void imgCheckUpdate(String content, HttpServletRequest request) {
		// src 부터 실제 그림파일 시작하는인덱스번호는 /resources/data/ckeditor/ 경로라고치면 31번쨰부터 
		// content안에 그림파일이 존재할때만 작업을 수행 할수 있도록 한다.

		request = ((ServletRequestAttributes)(RequestContextHolder.currentRequestAttributes())).getRequest();
		String upLoadPath = request.getSession().getServletContext().getRealPath("/resources/data/board");
		
		int position = 29;
		String nextImg = content.substring(content.indexOf("src=\"/") + position);
		
		boolean sw = true;
		while(sw) {
			String imgFile = nextImg.substring(0,nextImg.indexOf("\""));
			
			String origFilePath = upLoadPath +"/"+imgFile;
			String copyFilePath = upLoadPath.replace("board", "ckeditor/") + imgFile;
			
			fileCopyCheck(origFilePath,copyFilePath);
			
			if(nextImg.indexOf(nextImg.indexOf("src=\"/")) == -1) sw = false;
			else nextImg = nextImg.substring(nextImg.indexOf("src=\"/") + position);
			
			
		}
		
	}

	@Override
	public void setContentUpdateOk(BoardVO vo) {
		boardDAO.setContentUpdateOk(vo);
	}

	@Override
	public void setBoardGoodFinger(int idx, int goodCnt, int good) {
		boardDAO.setBoardGoodFinger(idx,goodCnt,good);
	}
}

