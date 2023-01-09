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
	
	function searchCheck(){
		let searchStr = $("#searchStr").val();
		let category = $("#category").val();
		
		if(searchStr == ""){
			alert("검색어를 입력해주세요.");
			$("#searchStr").focus();
			return;
		}

		let query = {
				searchStr,
				category
		}
		
		$.ajax({
			type : "post",
			url  : "${ctp}/member/memberIdSearch",
			data : query,
			success : function(vo){
				if(vo.res != '1'){
					let str = "<p>";
					str += "당신의 아이디는 <b>"+vo.mid+"</b> 입니다";
					str += "<p>";
					
					$("#demo").html(str);
				}
				else if(vo.res != '0'){
					alert("입력하신 정보와 일치하는 아이디가없습니다.");
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
	<h2>아이디 찾기</h2>
	<hr/>
	<form name="searchForm" method="post">
	<table class="table table-bordered text-center">
		<tr>
			<th  style="background-color: #eee " colspan="3">아이디찾기</th>
		</tr>	
		<tr>
			<td>
				<select name="category" id="category" class="form-control">
					<option value="nickName">닉네임</option>
					<option value="name">이름</option>
				</select>
			</td>
			<td>
				<input type="text" name="searchStr" id="searchStr" class="form-control"/>
			</td>
			<td>
				<input type="button" value="아이디 찾기" onclick="searchCheck()" class="btn btn-success form-control"/>
			</td>
		</tr>	
	</table>
	</form>
	<div id="demo"></div>
</div>
<p><br/><p>
<jsp:include page="/WEB-INF/views/include/footer.jsp"></jsp:include>
</body>
</html>