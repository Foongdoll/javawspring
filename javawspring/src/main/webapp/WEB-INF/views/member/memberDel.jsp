<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<title>memberDel.jsp</title>
	<jsp:include page="/WEB-INF/views/include/bs4.jsp"></jsp:include>
</head>
<script>
	'use strict'
	
	function memberDelCheck(){
		let ans = confirm('정말 탈퇴하시겠습니까?');
		
		if(!ans) return;
		
		let ans2 = prompt('정말 탈퇴하시려면 "정말탈퇴"라고 입력해주세요');
		
		if(ans2 != '정말탈퇴') return;
		else memberDelForm.submit();
	}
</script>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp"></jsp:include>
<jsp:include page="/WEB-INF/views/include/slide2.jsp"></jsp:include>
<p><br/><p>
<div class="container">
	<h2>회원 탈퇴..</h2>
	<hr/>
	<form name="memberDelForm" method="post" >
		<table class="table table-bordered">
			<tr>
				<td>
					회원 <b>탈퇴</b>하시면 30일안에 복구 가능하시며 정말 탈퇴하시려면<br/>
					아이디와 비밀번호 닉네임을 정확히 입력해주세요.(실수로 클릭하셔도 확인창 나옵니다.) 
				</td>
			</tr> 	
			<tr>
				<th>아이디</th>
				<td>
					<input type="text" name="mid" class="form-control" required/>
				</td>
			</tr>
			<tr>
				<th>비밀번호</th>
				<td>
					<input type="password" name="pwd" class="form-control" required/>
				</td>
			</tr>
			<tr>
				<th>닉네임</th>
				<td>
					<input type="text" name="nickName"  class="form-control" required/>
				</td>
			</tr>
			<tr>
				<td>
					<input type="button" value="회원탈퇴" onclick="memberDelCheck()" class="btn btn-info"/>
				</td>
			</tr>
		</table>	
	</form>
</div>
<p><br/><p>
<jsp:include page="/WEB-INF/views/include/footer.jsp"></jsp:include>
</body>
</html>