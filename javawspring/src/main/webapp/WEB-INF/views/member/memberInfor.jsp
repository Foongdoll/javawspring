<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<title>memberInfor.jsp</title>
	<jsp:include page="/WEB-INF/views/include/bs4.jsp"></jsp:include>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp"></jsp:include>
<jsp:include page="/WEB-INF/views/include/slide2.jsp"></jsp:include>
<p><br/><p>
<div class="container">
	<table class="table table-bordered">
		<tr>
			<th>별명</th>
			<td>${vo.nickName }</td>
		</tr>	
		<tr>
			<th>이름</th>
				<td>${vo.name }</td>
		</tr>
		<tr>
			<th>성별</th>
			<td>${vo.gender }</td>
		</tr>
		<tr>
			<th>생일</th>
			<td>${vo.birthday }</td>
		<tr>
		<tr>
			<th>전화번호</th>
			<td>${vo.tel }</td>
		<tr>
		<tr>
			<th>주소</th>
			<td>${vo.address }</td>
		<tr>
		<tr>
			<th>이메일</th>
			<td>${vo.email }</td>
		<tr>
		<tr>
			<th>직업</th>
			<td>${vo.job }</td>
		<tr>
		<tr>
			<th>취미</th>
			<td>${vo.hobby }</td>
		<tr>
		<tr>
			<th>최초 가입일</th>
			<td>${vo.startDate }</td>
		<tr>
		<tr>
			<th>마지막 접속일</th>
			<td>${vo.lastDate }</td>
		<tr>
		<tr>
			<th>오늘 방문횟수</th>
			<td>${vo.todayCnt }</td>
		<tr>
		<tr>
			<th>총 방문횟수</th>
			<td>${vo.visitCnt }</td>
		<tr>
		<tr>
			<th>포인트</th>
			<td>${vo.point }</td>
		<tr>
		<tr>
			<th>정보공개여부</th>
			<td>${vo.userInfor }</td>
		<tr>
		<tr>
			<th>자기소개</th>
			<td>${vo.content }</td>
		</tr>
	</table>
	<p>
		<img src="${ctp}/member/${vo.photo}" width="600px">
	</p>
	<p>
		<a href="${ctp}/member/memberList?pag=${pag}" class="btn btn-warning">뒤로가기</a>
	</p>
</div>
<p><br/><p>
<jsp:include page="/WEB-INF/views/include/footer.jsp"></jsp:include>
</body>
</html>
