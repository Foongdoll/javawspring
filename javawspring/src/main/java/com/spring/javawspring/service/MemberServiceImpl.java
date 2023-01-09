package com.spring.javawspring.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

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
	public int getMemberJoinOk(MemberVO vo) {
		
		return memberDAO.getMemberJoinOk(vo);
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
	public void getPwdcertificationProcess(String toMail, String mid, 
			HttpServletRequest request, HttpServletResponse response) {
		
		try {
				// 메세지 객체 생성 그 메세지를 담을(감싸줄) 헬퍼 객체 생성 
				MimeMessage message = mailSender.createMimeMessage();
				MimeMessageHelper  messageHelper = new MimeMessageHelper(message, true, "UTF-8");
				
				String content = "인증 번호는";
				
				messageHelper.setTo(toMail);
				messageHelper.setSubject("그린 길동이네 이메일 인증");
				messageHelper.setText(content);
				
				UUID uuid = UUID.randomUUID();
				String certificationNumber = uuid.toString().substring(0,8);
				memberDAO.setCertificationNumber(certificationNumber,mid);
				// uuid 발급 후 uuid 필드의 값으로 저장 후 메일 내용에도 인증번호 추가 
				content += certificationNumber+"입니다.";
				content = content.replace("\n", "<br/>");
				
				messageHelper.setText(content , true);
				
				mailSender.send(message);
				
			} catch (MessagingException e) {
				e.printStackTrace();
			}		
		
	}

	@Override
	public void setNewEncPswd(String encPswd, String mid) {
		memberDAO.setNewEncPswd(encPswd,mid);
	}
	
}
