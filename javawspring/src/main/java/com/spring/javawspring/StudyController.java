package com.spring.javawspring;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.spring.javawspring.common.ARIAUtil;
import com.spring.javawspring.common.SecurityUtil;
import com.spring.javawspring.dao.MemberDAO;
import com.spring.javawspring.service.StudyService;
import com.spring.javawspring.vo.GuestVO;
import com.spring.javawspring.vo.MailVO;
import com.spring.javawspring.vo.MemberVO;

@Controller
@RequestMapping("/study")
public class StudyController {
	@Autowired
	StudyService studyService;
	
	@Autowired
	BCryptPasswordEncoder PasswordEncoder;
	
	@Autowired
	JavaMailSender mailSender;
	
	@RequestMapping(value = "/ajax/ajaxMenu", method = RequestMethod.GET)
	public String ajaxMenuGet() {
		
		return "/study/ajax/ajaxMenu";
	}
	// String 일반 값 주고 받기 Ajax(숫자/영어)
	@ResponseBody
	@RequestMapping(value = "/ajax/ajaxTest1_1", method = RequestMethod.POST)
	public String ajaxTest1_1Post(int idx) {
		idx = (int)(Math.random()*idx) + 1;
		String res = idx + " : Happy a Good Time!!!!";
		
		return res;
	}
	// String 값 전달 Ajax(한글)
	@ResponseBody
	@RequestMapping(value = "/ajax/ajaxTest1_2", method = RequestMethod.POST, produces = "application/text; charset=UTF8")   
	public String ajaxTest1_2Post(int idx) {
		idx = (int)(Math.random()*idx) + 1;
		String res = idx + " : 행복한 하루 Happy a Good Time!!!!";
		return res;
	}
	
	@RequestMapping(value = "ajax/ajaxTest2_1", method = RequestMethod.GET)
	public String ajaxTest2_1Get() {
		return "study/ajax/ajaxTest2_1";
	}
	
	@ResponseBody
	@RequestMapping(value = "ajax/ajaxTest2_1", method = RequestMethod.POST)
	public String[] ajaxTest2_1Post(String dodo) {
//		String[] strArr = new String[100]; 
//		strArr = studyService.getCityStringArr(dodo);
//		return strArr;
		
		return studyService.getCityStringArr(dodo);
	}
	
	@RequestMapping(value = "ajax/ajaxTest2_2", method = RequestMethod.GET)
	public String ajaxTest2_2Get() {
		return "study/ajax/ajaxTest2_2";
	}

//	객체배열 (ArrayList) 값의 전달
	@ResponseBody
	@RequestMapping(value = "ajax/ajaxTest2_2", method = RequestMethod.POST)
	public ArrayList<String> ajaxTest2_2Post(String dodo) {
		
		return studyService.getCityArrayListArr(dodo);
	}
	
	@RequestMapping(value = "ajax/ajaxTest2_3", method = RequestMethod.GET)
	public String ajaxTest2_3Get() {
		return "study/ajax/ajaxTest2_3";
	}
	
//	객체배열 (ArrayList) 값의 전달
	@ResponseBody
	@RequestMapping(value = "ajax/ajaxTest2_3", method = RequestMethod.POST)
	public HashMap<Object, Object> ajaxTest2_3Post(String dodo) {
		ArrayList<String> vos = new ArrayList<String>();
		vos = studyService.getCityArrayListArr(dodo);
			
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		
		map.put("city" , vos);
		
		
		return map;
	}
	
	// DB 폼
	@RequestMapping(value = "/ajax/ajaxTest3", method = RequestMethod.GET)
	public String ajaxTest3Get() {
		return "study/ajax/ajaxTest3";
	}
	
	// DB를 활용한 값의 전달1 (vo)
	@ResponseBody
	@RequestMapping(value = "/ajax/ajaxTest3_1", method = RequestMethod.POST)
	public GuestVO ajaxTest3_1Post(String mid) {

		return studyService.getGuestMid(mid);
	}
	
	// DB를 활용한 값의 전달2 arraylist(vos)
	@ResponseBody
	@RequestMapping(value = "/ajax/ajaxTest3_2", method = RequestMethod.POST)
	public ArrayList<GuestVO> ajaxTest3_2Post(String mid) {
		
		return studyService.getGuestNames(mid);
	}
	
	// 암호화 뷰 
	@RequestMapping(value = "/password/sha256", method = RequestMethod.GET)
	public String sha256Get() {
		return "/study/password/sha256";
	}
	
	@ResponseBody
	@RequestMapping(value = "/password/sha256", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public String sha256Post(String pwd) {
		String encpwd = SecurityUtil.encryptSHA256(pwd);
		
		String res = "원본 비밀번호 : "+pwd+" / 암호화된 비밀번호"+ encpwd ;
		
		return res;
	}
	
	// 암호화 뷰 
	@RequestMapping(value = "/password/aria", method = RequestMethod.GET)
	public String ariaGet() {
		return "/study/password/aria";
	}
	
	@ResponseBody
	@RequestMapping(value = "/password/aria", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public String ariaPost(String pwd) {
		String res = "";
		String encPwd = "";
		String decPwd = "";
		
		try {
			encPwd = ARIAUtil.ariaEncrypt(pwd);
			decPwd = ARIAUtil.ariaDecrypt(encPwd);
		} catch (InvalidKeyException e) {} 
		  catch (UnsupportedEncodingException e) {}
		
		
		res = "암호화 전 비밀번호 :"+decPwd+" / 암호화된 비밀번호 :"+encPwd;
		return res;
	}
	
	// 암호화 뷰 
	@RequestMapping(value = "/password/bCrypt", method = RequestMethod.GET)
	public String bCryptGet() {
		return "/study/password/bCrypt";
	}
	
	@ResponseBody
	@RequestMapping(value = "/password/bCrypt", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public String bCryptPost(String pwd) {
		String res = "";
		String encPwd = "";
		boolean pwdB; 
		
		encPwd = PasswordEncoder.encode(pwd);
		
		pwdB = PasswordEncoder.matches(pwd, encPwd);
		
		res = "암호화 전 비밀번호 :"+pwd+" / 암호화된 비밀번호 :"+encPwd+" / 같은 비밀번호인가 : "+pwdB;
		return res;
	}
	
//	SMTP 메일 보내기
	// 메일 작성 폼
	@RequestMapping(value = "/mail/mailForm", method = RequestMethod.GET)
	public String mailFormGet(Model model,String email) {
		ArrayList<MemberVO> vos = studyService.getMembersInfor();
		
		model.addAttribute("vos",vos);
		model.addAttribute("email",email);
		
		return "study/mail/mailForm";
	}
	
	// 메일 전송하기
	@RequestMapping(value = "/mail/mailForm", method = RequestMethod.POST)
	public String mailFormPost(MailVO vo,HttpServletRequest request) {
		
		try {
			String toMail = vo.getToMail();
			String title = vo.getTitle();
			String content = vo.getContent();
//			메일 보낼 연습용 이미지 D:\JavaWorkspace\springframework\Works\javawspring\src\main\webapp\resources\images\main.JPG
			
			
			// 메일을 전송하기위한 객체 : MimeMessage() , MimeMessageHelper()
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
			
			// 메일보관함에 회원이 보내온 메세지들을 모두 저장시킨다.
			messageHelper.setTo(toMail);
			messageHelper.setSubject(title);
			messageHelper.setText(content);
			
			// 메세지 보관함의 내용(content)에 필요한 정보를 추가로 담아서 전송시킬수 있도록 한다.
			content = content.replace("\n", "<br/>");
			content += "<p><img src=\"cid:main.JPG\" width='500px'></p>";
			content += "<br/><hr><h3><a href='http://49.142.157.251:9090/green2209J_10/artMain.art'>그린 옥션</a></h3><hr><br/>";
			content += "<br/><hr><h3>CJ Green에서 보냅니다.</h3><hr><br/>";
			content += "<hr/>";
			
			messageHelper.setText(content, true);
			
			// 본문에 기재된 그림 파일의 경로를 따로 표시시켜준다. 그리고 보관함에 다시 저장시켜준다.
			
			FileSystemResource file = new FileSystemResource("D:\\JavaWorkspace\\springframework\\Works\\javawspring\\src\\main\\webapp\\resources\\images\\main.JPG");
			messageHelper.addInline("main.JPG", file); 
			
			file = new FileSystemResource("D:\\JavaWorkspace\\springframework\\Works\\javawspring\\src\\main\\webapp\\resources\\images\\chicago.jpg");
			messageHelper.addAttachment("chicago.jpg", file);
			
			file = new FileSystemResource("D:\\JavaWorkspace\\springframework\\Works\\javawspring\\src\\main\\webapp\\resources\\images\\paris.jpg");
			messageHelper.addAttachment("paris.jpg", file);
			
			file = new FileSystemResource(request.getRealPath("/resources/images/paris.jpg")); 
			
			file = new FileSystemResource(request.getSession().getServletContext().getRealPath("/resources/images/paris.jpg")); 
			messageHelper.addAttachment("paris.jpg",file);
			 
			// 메일 전송하기
			mailSender.send(message);
			
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
		
		return "redirect:/msg/mailSendOk";
	}
	
	// UUID 폼 
	@RequestMapping(value = "/uuid/uuidForm", method = RequestMethod.GET)
	public String uuidFormGet() {
		return "study/uuid/uuidForm";
	}
//	  UUID 생성
	@ResponseBody
	@RequestMapping(value = "/uuid/uuidProcess", method = RequestMethod.POST)
	public String uuidFormPost() {
		UUID uuid = UUID.randomUUID();
		
		return uuid.toString();
	}
	
	@ResponseBody
	@RequestMapping(value = "/mail/mailJusorok", method = RequestMethod.POST)
	public ArrayList<MemberVO> mailJusorokPost() {
		ArrayList<MemberVO> vos = studyService.getMembersInfor();
		
		return vos;
	}
	@RequestMapping(value = "/fileUpload/fileUploadForm", method = RequestMethod.GET)
	public String fileUploadFormGet() {
		return "study/fileUpload/fileUploadForm";
	}
	
	@RequestMapping(value = "/fileUpload/fileUploadForm", method = RequestMethod.POST)
	public String fileUploadFormPost(MultipartFile fName, HttpServletRequest request) {
		int res = studyService.FileUpload(fName,request);
		
		if(res == 1) return "redirect:/msg/fileUploadOk"; 
		return "redirect:/msg/fileUploadNo";
	}
	
	
}
