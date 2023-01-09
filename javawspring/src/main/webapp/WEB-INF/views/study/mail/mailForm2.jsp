<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<title>mailForm.jsp</title>
	<jsp:include page="/WEB-INF/views/include/bs4.jsp"></jsp:include>
</head>
<script>
	'use strict'
	
	function mailValueAdd(){
		let email = $("#emailSelect").val();
		$("#toMail").val(email);
	}

</script>
<style>
	th {
		background-color: #eee
	}
</style>
<body>
  <jsp:include page="/WEB-INF/views/include/nav.jsp"></jsp:include>
  <jsp:include page="/WEB-INF/views/include/slide2.jsp"></jsp:include>
<p><br/><p>
<div class="container">
	<h2>메 일 보 내 기</h2>
	<hr/>
	<p><b>받는 사람의 메일주소를 정확히 입력해야합니다.</b></p>
	<form name="myform" method="post">
		<table class="table table-bordered text-center">
			<tr>
				<th>회원메일주소</th>
				<td>
					<select id="emailSelect" onchange="mailValueAdd()" class="form-control">
							<option>이메일 선택</option>
						<c:forEach var="vo" items="${vos}" varStatus="st">
							<option>${vo.email}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<th>받는사람</th>
				<td><input type="text" name="toMail" id="toMail" placeholder="받는사람 메일 주소를 입력하세요." class="form-control" required/></td>
			</tr>
			<tr>
				<th>메일 제목</th>
				<td><input type="text" name="title" placeholder="메일의 제목을 입력하세요." class="form-control" required/></td>
			</tr>
			<tr>
				<th>메일 내용</th>
				<td>
					<textarea rows="7" name="content" class="form-control" required></textarea>
				</td>
			</tr>
			<tr>
				<td colspan="2" class="text-center">
					<input type="submit" value="메일 발송" class="btn btn-success"/>
					<input type="reset" value="다시 쓰기" class="btn btn-secondary"/>
					<input type="button" value="돌아가기" onclick="location.href='${ctp}/member/memberMain'" class="btn btn-info"/>
				</td>
			</tr>
		</table>
	</form>
</div>
<p><br/><p>
<jsp:include page="/WEB-INF/views/include/footer.jsp"></jsp:include>
</body>
</html>