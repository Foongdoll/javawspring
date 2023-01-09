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
	let jusorok = "";
	
	function jusorokView(){
		$("#myModal").on("show.bs.modal", function(e){
			$(".modal-header > #cnt").html(${cnt});
			
			$.ajax({
				type : "post",
				url  : "${ctp}/study/mail/mailJusorok",
				success : function(vos){
					jusorok += '<table class="table table-bordered"><tr><td>번호</td><td>이름</td><td>이메일주소</td></tr>';
					for(let i = 0; i < vos.length; i++){
						jusorok += '<tr><td>'+i+1+'</td><td>'+vos[i].name+'</td><td><a href="${ctp}/study/mail/mailForm?email='+vos[i].email+'" id="mail'+i+'">'+vos[i].email+'</a></td><tr>';
					}
					jusorok += '</table>';
					
					$(".modal-body").html(jusorok);
				},
				error : function(){
					alert("전송 오류");					
				}
			});
		});
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
				<th>받는사람</th>
				<td>
					<div class="row">
						<div class="col-10">
							<input type="text" name="toMail" id="toMail" value="${email}" placeholder="받는사람 메일 주소를 입력하세요." class="form-control" required/>
						</div>
						<div class="col-2">
							<button type="button" onclick="jusorokView()" class="btn btn-primary" data-toggle="modal" data-target="#myModal">주소록</button>
						</div>
					</div>
				</td>
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
<!-- 주소록을 모달로 출력  -->

<div class="modal fade" id="myModal" style="width: 700px">
	<div class="modal-dialog">
		<div class="modal-content" style="width:600px">
			<div class="modal-header" style="width:600px">
				<h4 class="modal-title">☆주 소 록☆(총건수 :<span id="cnt"></span>)</h4>
				<button type="button" class="close btn btn-danger" data-dismiss="modal">&times</button>
			</div>
			<div class="modal-body" style="width:600px; height: 400px;overflow: auto;">
				<span id="jusorok"></span>
			</div>
			<div class="modal-footer" style="width:600px">
				<button type="button" class="close btn btn-danger" data-dismiss="modal">&times</button>
			</div>
		</div>
	</div>
</div>

<p><br/><p>
<jsp:include page="/WEB-INF/views/include/footer.jsp"></jsp:include>
</body>
</html>