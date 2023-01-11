<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<title>fileList.jsp</title>
	<jsp:include page="/WEB-INF/views/include/bs4.jsp"></jsp:include>
</head>
<script>
	'use strict'
	$(document).ready(function() {
			$("#cbx_chkAll").click(function() {
				if($("#cbx_chkAll").is(":checked")) $("input[name=file]").prop("checked", true);
				else $("input[name=file]").prop("checked", false);
			});
			
			$("input[name=file]").click(function() {
				var total = $("input[name=file]").length;
				var checked = $("input[name=file]:checked").length;
				
				if(total != checked) $("#cbx_chkAll").prop("checked", false);
				else $("#cbx_chkAll").prop("checked", true); 
			});
		});
	
	
	function imsiFileDelete(file){
		
		$.ajax({
			type : "post",
			url  : "${ctp}/admin/file/imsiFileDelete",
			data : {file : file},
			success : function(res){
				if(res == '1'){
					alert('임시파일이 삭제되었습니다.');
					location.reload();
				}
				else {
					alert('임시파일이 삭제되지않았습니다.');
				}
			},
			error : function(){
				alert("전송오류");				
			}
		});		 
	}
	
	function imsiFileSelectDelete(){
		let ans = confirm('선택된 모든 게시물을 삭제 하시겠습니까?');
		if(!ans) return;

		
		let cnt = 0;
		let allFile = "";
		for(let i = 0; i < myform.file.length; i++){
			if(myform.file[i].checked == true) {
				allFile += myform.file[i].value + "/";
				cnt++;
			}
		}
		
		if(cnt == 0) {// 선택한거 있나없나 cnt로 센다음 처리부터하면댐
		 	alert("체크하신 파일이없습니다.");
			return;
		} 
		
		
		$.ajax({
			type : "post",
			url  : "${ctp}/admin/file/imsiFileSelectDelete",
			data : {allFile : allFile},
			success : function(res){
				alert(res);
				if(res == '1'){
					alert('임시파일이 삭제되었습니다.');
					location.reload();
				}
				else {
					alert('임시파일이 삭제되지않았습니다.');
				}
			},
			error : function(){
				alert("전송오류");				
			}
		});		 
		
		
	}
	
	
</script>
<body>
<p><br/><p>
<div class="container">
	<h2>서버 파일 리스트</h2>
	<hr/>
	<form name="myform" method="post">
		<p>전체 선택
			<input type="checkbox" id="cbx_chkAll" value="1"/>
			<input type="button" value="선택 삭제" onclick="imsiFileSelectDelete()" class="btn btn-info"/>
		</p>
	<p>서버의 파일 경로 : ${ctp}/data/ckeditor/~~파일명</p>
		<table class="table table-hover">
			<c:forEach var="file" items="${files}" varStatus="st">
				<tr>
					<td>
						<input type="checkbox" name="file" value="${file}" />
					</td>
					<td>
						<img src="${ctp}/data/ckeditor/${file}" width="150px" /><hr/>
					</td>
					<td>
						<input type="button" onclick="imsiFileDelete('${file}')" value="삭제" class="btn btn-danger"/>
					</td>
				</tr>
			</c:forEach>
		</table>
	</form>      
</div>    
<p><br/><p>        
</body>
</html>