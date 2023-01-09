<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<title>Insert title here</title>
	<jsp:include page="/WEB-INF/views/include/bs4.jsp"></jsp:include>
</head>
<script>
	'use strict'
	
	$(document).ready(function(){
		let pwdSw = '${pwdSw}';
		
		if(pwdSw == '0'){}
		else {
			let addForm = '인증번호입력';
			addForm += '<form name="pwdCheckForm" method="post"><table><tr>';
			addForm += '<td><input type="text" name="pwdCPswd" id="pwdCPswd" class="form-control"/></td>';
			addForm += '<td><input type="button" value="인증확인" onclick="pwdCPswdCheck()" class="btn btn-success"/></td>';
			addForm += '</tr></table></form>';
			$("#demo").html(addForm);
		}
	});
	
	function pwdCPswdCheck(){
		let pwdCPswd = $("#pwdCPswd").val();
		let mid = '${mid}';
		
		if(pwdCPswd == ''){
			alert('인증번호를 입력해주세요.');
			$("pwdCNumber").focus();
			return
		}
		
		let query = {
				pwdCPswd,
				mid
		}
		
		$.ajax({
			type : "post",
			url  : "${ctp}/member/memberPwdSearchOk",
			data : query,
			success : function(res){
				if(res == '1'){
					alert("인증에 실패하셨습니다.");
					return;
				}
				else {
					let addImsiPswd = '<table><tr>';
					addImsiPswd += '<td>임시 비밀번호 : '+res+' 입니다. </td>';
					addImsiPswd += '<td>원활한 사이트이용을 위해서 비밀번호변경을 꼭 해주세요.</td>';
					addImsiPswd += '</tr></table>';
					
					$("#demo").html(addImsiPswd);
				}
			},
			error : function(){
				
			}
		});
		
	}

	
	function eCheck(){
		let mid = $("#mid").val();
		let toMail = $("toMail").val();
		
		if(mid.trim() == ''){
			alert("아이디를 입력해주세요.");
			$("#mid").focus();
			return;
		}
		else if(toMail == ''){
			alert("이메일을 입력해주세요.");
			$("#toMail").focus();
			return;
		}
		else {
			myform.submit();
		}
	}
	
	
</script>
<body>
  <jsp:include page="/WEB-INF/views/include/nav.jsp"></jsp:include>
  <jsp:include page="/WEB-INF/views/include/slide2.jsp"></jsp:include>
<p><br/><p>
<div class="container">
	<p>
		<b>비밀번호 찾기</b>
		*이메일 인증 : 입력하신 이메일로인증번호가 발송됩니다 인증이 성공적으로 완료되시면 임시비밀번호가 발급됩니다.<br/>
		꼭 회원가입하실때 입력하신 이메일로 인증번호를 받아주세요.*
	</p>
	<form name="myform" method="post">
		<table class="table table-bordered text-center">
			<tr>
				<th>아이디</th>
				<td>
					<input type="text" name="mid" id="mid" class="form-control" required/>
				</td>
			</tr>
			<tr>
				<th>이메일</th>
				<td><input type="text" name="toMail" id="toMail" placeholder="인증번호를 받으실 이메일 주소를 입력해주세요." class="form-control" required/></td>
			</tr>
			<tr>
				<td colspan="2" class="text-center">
					<input type="button" value="이메일 인증번호발급" onclick="eCheck()" class="btn btn-success"/>
					<input type="reset" value="다시 쓰기" class="btn btn-secondary"/>
					<input type="button" value="돌아가기" onclick="location.href='${ctp}/member/memberLogin'" class="btn btn-info"/>
				</td>
			</tr>
		</table>
		<p>
			<span id="demo"></span>
		</p>
	</form>
</div>
<p><br/><p>
<jsp:include page="/WEB-INF/views/include/footer.jsp"></jsp:include>
</body>
</html>