package com.spring.javawspring.common;

import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

public class JavawspringProvide {

	public void writerFile(MultipartFile fName, String saveFileName, HttpServletRequest request, String realPath) throws IOException {
		byte[] data = fName.getBytes();
		
		FileOutputStream fos = new FileOutputStream(realPath+saveFileName);
		fos.write(data);
		fos.close();
	}
}