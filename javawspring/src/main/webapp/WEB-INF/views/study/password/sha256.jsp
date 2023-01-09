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
	
	function sha256Check(){
		let pwd = $("#pwd").val();
		
		if(pwd.trim() == ""){
			alert("비밀번호를 입력해주세요");
			return;
		}
		
		$.ajax({
			type : "post",
			url  : "${ctp}/study/password/sha256",
			data : {pwd : pwd},
			success : function(res){
				let str = "<b>암호화 결과</b>";
				str += "<b>"+res+"</b><br/>";
				$("#demo").append(str);
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
	<h2>암호화 SHA256(Secure Hash Algorithm)</h2>
	<p><b>
		알고리즘의 한 종류로서 256비트로 구성되며 64자리의 암호화 알고리즘이다.<br/>
		SHA256은 단반향 암호화 알고리즘이라 복호화가 불가능하고, 대신 속도가 빠르다는 장점이있다.	
	</b></p>
	<hr/>
		<p> 비밀번호 :
			<input type="password" id="pwd" name="pwd" /> 
			<input type="button" value="sha256암호화" onclick="sha256Check()" class="btn btn-success" />
			<input type="button" value="다시쓰기" onclick="location.reload()" class="btn btn-info"/> 
		</p>
		<hr/>
		<div>
			출력 결과 <br/>
			<span id="demo"></span>
		</div>
</div>
<p><br/><p>
<jsp:include page="/WEB-INF/views/include/footer.jsp"></jsp:include>
</body>
</html>