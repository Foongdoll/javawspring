package com.spring.javawspring.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.spring.javawspring.common.JavawspringProvide;
import com.spring.javawspring.dao.MemberDAO;
import com.spring.javawspring.vo.MemberVO;

@Service
public class MemberServiceImpl implements MemberService {
	
	@Autowired
	JavaMailSender mailSender;
	
	@Autowired
	MemberDAO memberDAO;

	@Override
	public MemberVO getMemberIdCheck(String mid) {
		return memberDAO.getMemberIdCheck(mid);
	}

	@Override
	public MemberVO getMemberNickNameCheck(String nickName) {
		return memberDAO.getMemberNickNameCheck(nickName);
	}

	@Override
	public int getMemberJoinOk(MemberVO vo, MultipartFile fName, HttpServletRequest request) {
		int res = 0;
		try {
				String oFileName = fName.getOriginalFilename();
			if(oFileName.equals(""))  vo.setPhoto("noimage.jpg");
			else {
				String uid = UUID.randomUUID().toString();
				String saveFileName = uid + "_" + oFileName;
				
				@SuppressWarnings("deprecation")
				String realPath = request.getRealPath("/resources/member/");
				vo.setPhoto(saveFileName);
				
				new JavawspringProvide().writerFile(fName, saveFileName, request, realPath);
				res = 1;
			}
			memberDAO.getMemberJoinOk(vo);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return res;
	}

	@Override
	public void setMemberVisitCnt(MemberVO vo) {
		// 오늘 방문일자 누적 , 방문 포인트 누적
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String strNow = sdf.format(now);
		
		// 오늘 처음 방문시는 오늘 방문카운트(todayCnt)를 0으로 셋팅한다. 
		if(!vo.getLastDate().substring(0,10).equals(strNow)) {
			vo.setTodayCnt(0);
		}
		
		int todayCnt = vo.getTodayCnt() + 1;
		
		int nowTodayPoint = 0;
		if(vo.getTodayCnt() >= 5) {
			nowTodayPoint = vo.getPoint();
		}
		else {
			nowTodayPoint = vo.getPoint() + 10;
		}
		// 오늘 재방문이라면 '총방문수','오늘방문수','포인트' 누적처리
		memberDAO.setMemTotalUpdate(vo.getMid(), nowTodayPoint,todayCnt);
		
	}


	@Override
	public ArrayList<MemberVO> getMemberList(String mid,int startIndexNo, int pageSize) {
		return memberDAO.getMemberList(mid,startIndexNo, pageSize);
	}


	@Override
	public MemberVO getMemberIdSearch(String searchStr, String category) {
		
		return memberDAO.getMemberIdSearch(searchStr,category);
	}

	@Override
	public void setMemberDel(String mid) {
		memberDAO.setMemberDel(mid);
	}

	@Override
	public int totRecCnt(String mid) {
		return memberDAO.totRecCnt(mid);
	}

	@Override
	public void getPswdcertificationProcess(String toMail, String mid, int sw, HttpServletRequest request) {
		int res = 0;
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper  messageHelper = new MimeMessageHelper(message, true, "UTF-8");
			
			if(sw == 0) {
				// 메세지 객체 생성 그 메세지를 담을(감싸줄) 헬퍼 객체 생성 
				
				String content = "인증 번호는";
				
				messageHelper.setTo(toMail);
				messageHelper.setSubject("그린 길동이네 이메일 인증");
				messageHelper.setText(content);
				
				String certificationNumber = UUID.randomUUID().toString().substring(0,8);
				memberDAO.setCertificationNumber(certificationNumber,mid);
				// uuid 발급 후 uuid 필드의 값으로 저장 후 메일 내용에도 인증번호 추가 
				content += "<hr/>";
				content += "<font color='red'><b>"+certificationNumber+"</b></font>입니다.";
				content += "<hr/>그린에서 보냅니다.";
				messageHelper.setText(content , true);
				
				content = content.replace("\n", "<br/>");
				messageHelper.setText(content , true);
				FileSystemResource file = new FileSystemResource("C:\\Users\\green\\git\\springrepository\\javawspring\\src\\main\\webapp\\resources\\images\\main.JPG");
				messageHelper.addInline("main.JPG", file);
				
				file = new FileSystemResource("C:\\Users\\green\\git\\springrepository\\javawspring\\src\\main\\webapp\\resources\\images\\newyork.jpg");
				messageHelper.addAttachment("newyork.jpg", file);
				
			}
			else if(sw == 1){
				
				String certificationNumber = UUID.randomUUID().toString().substring(0,8);
				memberDAO.setCertificationNumber(certificationNumber,mid);
				String content = "<b>그린 회원정보 수정 메일인증</b>";
				content += "<p>인증번호<b>"+certificationNumber+"</b></p>";
				content += "<p>그린 그린 ~</p>";
				content = content.replace("\n", "<br/>");
				
				messageHelper.setText(content);
				
				String realPath = request.getRealPath("/resources/member/");
				FileSystemResource file = new FileSystemResource(realPath+"개굴.gif");
				messageHelper.addInline("개굴.gif", file);
				res = 1;
			}
				
				mailSender.send(message);
			} catch (MessagingException e) {
				e.printStackTrace();
			}		
	}

	@Override
	public void setNewEncPswd(String encPswd, String mid) {
		memberDAO.setNewEncPswd(encPswd,mid);
	}

	@Override
	public int setMemberInforUpdate(MemberVO vo,MultipartFile fName, HttpServletRequest request) {
		
		String uid = UUID.randomUUID().toString();
		String oFileName = fName.getOriginalFilename();
		String saveFileName = "";
		if(!oFileName.equals("")) saveFileName = uid+"_"+oFileName;
		else saveFileName = vo.getPhoto();
		String realPath = request.getRealPath("/resources/member/");
		String photo = memberDAO.getMemberFileName((String)request.getSession().getAttribute("sMid"));
		
		
		if(oFileName.equals("noimage.jpg")) {
			try {
				new JavawspringProvide().writerFile(fName, "noimage.jpg", request, realPath);
			} catch (IOException e) {e.printStackTrace();}
			vo.setPhoto("noimage.jpg");
			return memberDAO.setMemberInforUpdate(vo);
		}
		if(vo.getPhoto().equals("")) {
			File file = new File(realPath+"/"+photo);
			if(file.exists()) file.delete();
			try {
				new JavawspringProvide().writerFile(fName, saveFileName, request, realPath);
			} catch (IOException e) {e.printStackTrace();}
			
			vo.setPhoto(saveFileName);
		}
		else {
			vo.setPhoto(saveFileName);
		}
			
		return memberDAO.setMemberInforUpdate(vo);
	}

	/*새로운걸 넣었을때
	 * vo.photo : d28fbb21-bf9f-4f16-8c22-9bdf6baa1e26_2.jpg oFileName :
	 * saveFileName : 10ef7583-927d-482d-b30c-2abb40f6abf7_ photo
	 * d28fbb21-bf9f-4f16-8c22-9bdf6baa1e26_2.jpg
	 * 같은걸 다시 넣었을때
	 * 
	 * vo.photo : 
	   oFileName : 2.jpg
	   saveFileName : d28fbb21-bf9f-4f16-8c22-9bdf6baa1e26_2.jpg
	   photo b05a57b5-b828-4eb3-9d50-7f9c0bf214bc_
	 */
}
