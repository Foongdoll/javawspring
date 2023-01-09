<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<title>ajaxMenu.jsp</title>
	<jsp:include page="/WEB-INF/views/include/bs4.jsp"></jsp:include>
</head>
<script>
	'use strict'

	function ajaxTest1_1(idx){
	
		$.ajax({
			type : "post",
			url  : "${ctp}/study/ajax/ajaxTest1_1",
			data : {idx,idx},
			success : function(res){
				$("#demo1").html(res);				
			},
			error : function(){
				alert("전송 오류");
			}
		});
			
	}
	
	function ajaxTest1_2(idx){
	
		$.ajax({
			type : "post",
			url  : "${ctp}/study/ajax/ajaxTest1_2",
			data : {idx,idx},
			success : function(res){
				$("#demo1").html(res);				
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
	<h2>AJax 연습</h2>
	<hr/>
	<p>	기본(String) : 
		<a href="javascript:ajaxTest1_1(10)" class="btn btn-success">값 전달1</a>
		<a href="javascript:ajaxTest1_2(10)" class="btn btn-success">값 전달2</a>
		<span id="demo1"></span>
	</p>
	<p>	응용1(배열) : 
		<a href="${ctp}/study/ajax/ajaxTest2_1" class="btn btn-success">시(도)/구(시,군,동) 배열</a>
		<a href="${ctp}/study/ajax/ajaxTest2_2" class="btn btn-success">시(도)/구(시,군,동) 객체(ArrayList)배열</a>
		<a href="${ctp}/study/ajax/ajaxTest2_3" class="btn btn-success">시(도)/구(시,군,동) 객체(HashMap)으로 값 받기</a>
		<span id="demo2"></span>
	</p>
	<p> 응용2(DB) ;
		<a href="${ctp}/study/ajax/ajaxTest3" class="btn btn-info">DB</a>
	</p>
</div>
<p><br/><p>
  <jsp:include page="/WEB-INF/views/include/footer.jsp"></jsp:include>
</body>
</html>