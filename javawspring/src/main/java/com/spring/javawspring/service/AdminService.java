package com.spring.javawspring.service;

import javax.servlet.http.HttpServletRequest;

public interface AdminService {

	public int setMemberLevelUpdate(int idx, int level);

	public int setMemberDelete(int idx);

	public int setImsiFileDelete(String file, HttpServletRequest request);

	public int setImsiFileSelectDelete(String allFile, HttpServletRequest request);

}
