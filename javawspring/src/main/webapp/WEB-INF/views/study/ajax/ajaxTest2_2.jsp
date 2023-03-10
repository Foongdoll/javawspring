<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<title>ajaxTest2_2.jsp</title>
	<jsp:include page="/WEB-INF/views/include/bs4.jsp"></jsp:include>
</head>
<script>
	'use strict'
	
		
		$(function(){
			
			$("#dodo").change(function(){
				let dodo = $(this).val();
				
				if(dodo == "") {
					alert("지역을 선택하세요.");
					return;
				}
				
				$.ajax({
					type : "post",
					url  : "${ctp}/study/ajax/ajaxTest2_2",
					data : {dodo : dodo},
					success : function(res){
						let str = '<option value="">도시선택</option>';
						for(let i = 0; i < res.length; i++){
							if(res[i] == null) break;
							str += '<option>'+res[i]+'</option>' 
						}
						
						$("#city").html(str);
					},
					error : function(){
						alert("전송 오류");
					}
				}); 
			});		
		});
	
	function fCheck(){
		let dodo = $("#dodo").val();
		let city = $("#city").val();
		
		if(dodo == "" || city == ""){
			alert("지역을 선택하세요.");
			return;
		}
	
		let str = "선택하신 지역은 "+dodo+"와 "+city+" &nbsp;";
		str += '<input type="button" value="다시 검색" onclick="location.reload()" class="btn btn-warning btn-sm"/>'
		$("#demo").html(str);
	}
	
</script>
<body>
  <jsp:include page="/WEB-INF/views/include/nav.jsp"></jsp:include>
  <jsp:include page="/WEB-INF/views/include/slide2.jsp"></jsp:include>
<p><br/><p>
<div class="container">
	<h2>AJax를 활용한 값의 전달2 (객체 ArrayList 배열)</h2>
	<hr/>
	<form name="myform">
		<h3>도시를 선택하세요.</h3>
		<select name="dodo" id="dodo">
			<option value="">지역선택</option>
			<option value="서울">서울</option>
			<option value="경기">경기</option>
			<option value="충북">충북</option>
			<option value="충남">충남</option>
		</select>
		<select name="city" id="city" >
		</select>
		<input type="button" value="선택" onclick="fCheck()" class="btn btn-warning" /> &nbsp;
		<input type="button" value="돌아가기" onclick="location.href='${ctp}/study/ajax/ajaxMenu';" class="btn btn-success" />
	</form>
	<div id="demo"></div>	
</div>
<p><br/><p>
<jsp:include page="/WEB-INF/views/include/footer.jsp"></jsp:include>
</body>
</html>