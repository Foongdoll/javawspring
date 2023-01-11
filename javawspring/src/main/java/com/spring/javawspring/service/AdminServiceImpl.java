package com.spring.javawspring.service;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.javawspring.dao.AdminDAO;

@Service
public class AdminServiceImpl implements AdminService {
	
	@Autowired
	AdminDAO adminDAO;

	@Override
	public int setMemberLevelUpdate(int idx, int level) {
		return adminDAO.setMemberLevelUpdate(idx, level);
	}

	@Override
	public int setMemberDelete(int idx) {
		return adminDAO.setMemberDelete(idx);
	}

	@Override
	public int setImsiFileDelete(String file, HttpServletRequest request) {
		int res = 0;
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/ckeditor");

			File ckFile = new File(realPath+"/"+file);
			
			if(ckFile.exists()) {
				ckFile.delete();
				if(res != 1)res = 1;
			}
			return res;
		}

	@Override
	public int setImsiFileSelectDelete(String allFile, HttpServletRequest request) {
		int res = 0;
		
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/ckeditor");

		String[] file = allFile.split("/");
		
		for(int i = 0; i < file.length; i++) {
			File ckFile = new File(realPath+"/"+file[i]);
			
			if(ckFile.exists()) {
				ckFile.delete();
				if(res != 1) res = 1;
			}
		}
		return res;
	}
		
}
