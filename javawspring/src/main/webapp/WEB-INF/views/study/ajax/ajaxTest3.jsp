<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<title>ajaxTest3.jsp</title>
	<jsp:include page="/WEB-INF/views/include/bs4.jsp"></jsp:include>
</head>
<script>
	'use strict'
	
	function idCheck(){
		let mid = $("#mid").val();
		
		if(mid.trim() == ""){
			alert("아이디를 입력해주세요.");
			$("#mid").focus();
			return;
		}
		
		$.ajax({
			type : "post",
			url  : "${ctp}/study/ajax/ajaxTest3_1",
			data : {mid : mid},
			success : function(res){
				if(res != ''){
				let str = "<b>전송 결과</b><hr/><br/>";
				str += "<b>"+res.name+"</b><br/>";
				str += "<b>"+res.email+"</b><br/>";
				str += "<b>"+res.visitDate+"</b><br/>";
				str += "<b>"+res.hostIp+"</b>";
				str += "<b>"+res.content+"</b>";
				
				$("#demo").html(str);
				}
				else {
					str += "<font color='red'>찾는 자료가 없습니다.</font>"
				}
			},
			error : function(){
				alert("전송 오류");
			}
		});
		
	}
	function nameCheck(){
		let mid = $("#mid").val();
		
		if(mid.trim() == ""){
			alert("아이디를 입력해주세요.");
			$("#mid").focus();
			return;
		}
		$.ajax({
			type : "post",
			url  : "${ctp}/study/ajax/ajaxTest3_2",
			data : {mid : mid},
			success : function(vos){
				 if(vos != ''){
				let str = "<b>전송 결과</b><hr/><br/>";
				str += "<table class='table table-hover'>"	
				str += "<tr class='table-dark text-dark'>"	
				str += "<th>성명</th><th>이메일</th><th>글쓴날짜</th><th>아이피</th><th>내용</th>"	
				str += "</tr>"	
				for(let i = 0 ; i < vos.length; i++){
				str += "<tr>"	
				str += "<td><b>"+vos[i].name+"</b><br/></td>";
				str += "<td><b>"+vos[i].email+"</b><br/></td>";
				str += "<td><b>"+vos[i].visitDate+"</b><br/></td>";
				str += "<td><b>"+vos[i].hostIp+"</b></td>";
				str += "<td><b>"+vos[i].content+"</b></td>";
				str += "</tr>"	
				}
				str += "</table>"	
				$("#demo").html(str);
				}
				else { 
					let str2 = "<font color='red'>찾는 자료가 없습니다.</font>"
					$("#demo").html(str);
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
	<h2>AJax를 이용한 DB(아이디) 검색하기</h2>
	<hr/>
	<form name="myform" >
		<p>아이디 :
			 <input type="text" name="mid" id="mid" autofocus /> &nbsp;
			 <input type="button" value="성명 완전 일치 검색" onclick="idCheck()" class="btn btn-secondary"/>
			 <input type="button" value="성명 부분 일치 검색" onclick="nameCheck()" class="btn btn-secondary"/>
			 <input type="reset" value="다시입력" class="btn btn-primary"/>
			 <input type="button" value="돌아가기" onclick="location.href='${ctp}/study/ajax/ajaxMenu';" class="btn btn-warning"/>
		</p>
	</form>
	<p id="demo"></p>
	
</div>
<p><br/><p>
<jsp:include page="/WEB-INF/views/include/footer.jsp"></jsp:include>
</body>
</html>