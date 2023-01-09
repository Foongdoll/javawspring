<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<title>fileUploadForm.jsp</title>
	<jsp:include page="/WEB-INF/views/include/bs4.jsp"></jsp:include>
</head>
<body>
  <jsp:include page="/WEB-INF/views/include/nav.jsp"></jsp:include>
  <jsp:include page="/WEB-INF/views/include/slide2.jsp"></jsp:include>
<p><br/><p>
<div class="container">
	<h2>파일 업로드 연습</h2>
	<hr/>
	<form name="myform" method="post" enctype="multipart/form-data">
		<p>
			파일명 : 
				<input type="file" name="fName" id="fName" class="form-control file-border" accept=".jpg,.gif,.zip"/>
		</p>
		<input type="submit" value="파일 업로드" class="btn btn-primary"/>
		<input type="reset" value="다시 쓰기" class="btn btn-primary"/>
		<input type="button" value="돌아가기" onclick="${ctp}/" class="btn btn-primary"/>
	</form>
</div>
<p><br/><p>
<jsp:include page="/WEB-INF/views/include/footer.jsp"></jsp:include>
</body>
</html>