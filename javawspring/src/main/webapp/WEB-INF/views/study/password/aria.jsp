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
	let cnt = 0;
	function ariaCheck(){
		let pwd = $("#pwd").val();
		
		if(pwd.trim() == ""){
			alert("비밀번호를 입력해주세요");
			return;
		}
		
		$.ajax({
			type : "post",
			url  : "${ctp}/study/password/aria",
			data : {pwd : pwd},
			success : function(res){
				cnt++;
				let str = "<b>암호화 결과</b>";
				str += "<b>"+cnt+".&nbsp; "+res+"</b><br/>"
				
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
	<h2>암호화 ARIA</h2>
	<p><b>
		ARIA암호화 방식은 경량환경 및 하드웨어 구현을 위해 최적화된, INVOLUTIONAL SPN 구조를 갖는 범용 블록 암호화이다.<br/>
		ARIA가 사용하는 연산은 대부분 XOR과 같은 단순한 바이트 단위 연산으로, 블록 크기는 128bit이다 <br/>
		ARIA는 Academy(학계), Research Institute(연구소), Agency(정부기관)의 첫글자를 따서 만들었다.<br/>
		그래서 ARIA개발에 참여한 '학/연/관/의 공동노력을 포함한다.<br/>
		ARIA 암호화는 복호화가 가능하다.
	</b></p>
	<hr/>
		<p> 비밀번호 :
			<input type="password" id="pwd" name="pwd" /> 
			<input type="button" value="aria암호화" onclick="ariaCheck()" class="btn btn-success" />
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