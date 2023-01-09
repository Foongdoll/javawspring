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
		let ans = prompt('아이디를 입력해주세요.');
		if(ans == ${sMid}){
			location.href='${ctp}/member/memberMain';			
		}
	});
	
	function pwdChange(){
		let curPwd = $("#curPwd").val();		
		let newPwd = $("#newPwd").val();	
		let newPwd2 = $("#newPwd2").val();	
		
		if(newPwd != newPwd2){
			alert("비밀번호와 확인 비밀번호가 다릅니다.");
			return;
		}
		
		let query = {
				curPwd,
				newPwd
		}
		
		$.ajax({
			type : "post",
			url  : "${ctp}/member/memberPwdChange",
			data : query,
			success : function(res){
				if(res == '0'){
					alert("현재 비밀번호가 틀립니다.");
					return;
				}
				else if(res == '1'){
					alert("비밀번호가 변경되었습니다.");
					location.href='${ctp}/member/memberMain';
				}
			},
			error : function(){
				alert("전송 오류");
			}
		});
		
	}
	
</script>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp"></jsp:include>
<jsp:include page="/WEB-INF/views/include/slide2.jsp"></jsp:include>
<p><br/><p>
<div class="container">
	<table>
		<tr>
			<th>현재 비밀번호</th>
			<td>
				<input type="password" name="curPwd" id="curPwd" class="form-control"/>
			</td>
		</tr>
		<tr>
			<th>변경 비밀번호</th>
			<td>
				<input type="password" name="newPwd" id="newPwd" class="form-control"/>
			</td>
		</tr>
		<tr>
			<th>변경 비밀번호 확인</th>
			<td>
				<input type="password" name="newPwd2" id="newPwd2" class="form-control"/>
			</td>
		</tr>
		<tr>
			<td>
				<input type="button" value="비밀번호 변경" onclick="pwdChange()" class="btn btn-info"/>
			</td>
		</tr>
	</table>
</div>
<p><br/><p>
<jsp:include page="/WEB-INF/views/include/footer.jsp"></jsp:include>
</body>
</html>