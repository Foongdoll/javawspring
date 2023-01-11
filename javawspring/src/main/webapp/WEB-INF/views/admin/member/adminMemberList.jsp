<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>adminMemberList.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp" />
  <script>
    'use strict';
    
    function midSearch() {
      let mid = myform.mid.value;
      if(mid.trim() == "") {
    	  alert("아이디를 입력하세요!");
    	  myform.mid.focus();
      }
      else {
    	  myform.submit();
      }
    }
    
    function delCheck(idx) {
    	let ans = confirm("탈퇴처리 시키겠습니까?");
    	if(!ans) return;
    	
    	$.ajax({
    		type : "post",
    		url  : "${ctp}/admin/adminMemberDel",
    		data : {idx : idx},
    		success : function(res){
    			if(res == '1'){
    				alert('탈퇴처리되었습니다.');
    				location.reload();
    			}
    			else {
    				alert('탈퇴처리에 실패하였습니다.');
    			}
    		},
    		error : function(){
					alert("전송오류");    			
    		}
    	});
    }
    
    function LevelUpdateCheck(idx) {
    	let ans = confirm("등급을 수정하시겠습니까?");
    	if(!ans) return false;
			
    	let level = $("#level").val();
    	
    	let query = {
    			idx,
    			level
    	}
    	
    	$.ajax({
    		type  : "post",
    		url   : "${ctp}/admin/memberLevelUpdate",
    		data  : query,
    		success:function(res) {
    			if(res == '1'){
	  				alert("등급 수정완료!");
	    			location.reload();
    			}
    			else {
    				alert("등급 수정에 실패하셨습니다.");
	    			location.reload();
    			}    				
    		},
    		error : function() {     
    			alert("전송 오류~~");
    		}
    	});
    }
    
    function memberInforModal(mid,nickName,name,gender,birthday,tel,address,email,homePage,job,hobby,photo,content,userInfor,userDel,point,level,visitCnt,startDate,lastDate){
    	
    	address = address.replace(/\//g,' ');
    	$("#myModal").on("show.bs.modal", function(e){
    		$(".modal-header #mid").html(mid);	
    		$(".modal-body #nickName").html(nickName);	
    		$(".modal-body #name").html(name);	
    		$(".modal-body #gender").html(gender);	
    		$(".modal-body #birthday").html(birthday.substring(0,10));	
    		$(".modal-body #tel").html(tel);	
    		$(".modal-body #address").html(address);	
    		$(".modal-body #email").html(email);	
    		$(".modal-body #homePage").html(homePage);	
    		$(".modal-body #job").html(job);	
    		$(".modal-body #hobby").html(hobby);	
    		$(".modal-body #photo").html('<img src="${ctp}/member/'+photo+'" width="150px"/>');	
    		$(".modal-body #content").html(content);	
    		$(".modal-body #userInfor").html(userInfor);	
    		$(".modal-body #userDel").html(userDel);	
    		$(".modal-body #point").html(point);	
    		$(".modal-body #level").html(level);	
    		$(".modal-body #visitCnt").html(visitCnt);	
    		$(".modal-body #startDate").html(startDate.substring(0,10));	
    		$(".modal-body #lastDate").html(lastDate.substring(0,10));	
    	});
    }

  </script>
</head>
<body>
<p><br/></p>
<div class="container">
  <h2 class="text-center">전체 회원 리스트</h2>
  <br/>
  <form name="myform">
  	<div class="row mb-2">
  	  <div class="col form-inline">
  	    <input type="text" name="mid" class="form-control" />&nbsp;
  	    <input type="button" value="아이디개별검색" onclick="midSearch();" class="btn btn-secondary" />
  	  </div>
  	  <div class="col text-right"><button type="button" onclick="location.href='${ctp}/admin/member/memberList';" class="btn btn-secondary">전체검색</button></div>
  	</div>
  </form>
  <table class="table table-hover text-center">
    <tr class="table-dark text-dark">
      <th>번호</th>
      <th>아이디</th>
      <th>별명</th>
      <th>성명</th>
      <th>최초가입일</th>
      <th>마지막접속일</th>
      <th>등급</th>
      <th>탈퇴유무</th>
    </tr>
    <c:if test="${empty vos}"><tr><td colspan="8">찾으시는 자료가 없습니다</td></tr></c:if>
    <c:if test="${!empty vos}">
	    <c:forEach var="vo" items="${vos}" varStatus="st">
	      <tr>
	        <td>${vo.idx}</td>
	        <td><a href="#" onclick="memberInforModal('${vo.mid}','${vo.nickName}','${vo.name}','${vo.gender}','${vo.birthday}','${vo.tel}','${vo.address}','${vo.email}','${vo.homePage}','${vo.job}','${vo.hobby}','${vo.photo}','${vo.content}','${vo.userInfor}','${vo.userDel}','${vo.point}','${vo.level}','${vo.visitCnt}','${vo.startDate}','${vo.lastDate}')" data-toggle="modal" data-target="#myModal" >${vo.mid}</a></td>
	        <td>${vo.nickName}</td>
	        <td>${vo.name}<c:if test="${sLevel == 0 && vo.userInfor == '비공개'}"><font color='red'>(비공개)</font></c:if></td>
	        <td>${fn:substring(vo.startDate,0,19)}</td>
	        <td>${fn:substring(vo.lastDate,0,19)}</td>
	        <td>(${vo.level })
	          <form name="levelForm" method="post">
	            <!-- <select name="level" onchange="javascript:alert('회원정보를 변경하시려면, 등급변경버튼을 클릭하세요.');"> -->
	            <select name="level" id="level"> 
	              <option value="0" <c:if test="${vo.level==0}">selected</c:if>>관리자</option>
	              <option value="1" <c:if test="${vo.level==1}">selected</c:if>>운영자</option>
	              <option value="2" <c:if test="${vo.level==2}">selected</c:if>>우수회원</option>
	              <option value="3" <c:if test="${vo.level==3}">selected</c:if>>정회원</option>
	              <option value="4" <c:if test="${vo.level==4}">selected</c:if>>준회원</option>
	            </select>
	            <input type="button" value="등급변경" onclick="LevelUpdateCheck(${vo.idx})" class="btn btn-warning btn-sm"/>
	          </form>
	        </td>
	        <td>
	          <c:if test="${vo.userDel=='OK'}"><a href="javascript:delCheck(${vo.idx})"><font color="red">탈퇴신청</font></a></c:if>
	          <c:if test="${vo.userDel!='OK'}">활동중</c:if>
	        </td>
	      </tr>
	    </c:forEach>
	  </c:if>
    <tr><td colspan="8" class="m-0 p-0"></td></tr>
  </table>
</div>
<br/>
<!-- 블록 페이지 시작 -->
<div class="text-center">
  <ul class="pagination justify-content-center">
    <c:if test="${pageVO.pag > 1}">
      <li class="page-item"><a class="page-link text-secondary" href="${ctp}/admin/member/adminMemberList?mid=${searchMid}&pag=1">첫페이지</a></li>
    </c:if>
    <c:if test="${pageVO.curBlock > 0}">
      <li class="page-item"><a class="page-link text-secondary" href="${ctp}/admin/member/adminMemberList?mid=${searchMid}&pag=${(pageVO.curBlock-1)* pageVO.blockSize + 1}">이전블록</a></li>
    </c:if>
    <c:forEach var="i" begin="${(pageVO.curBlock)*pageVO.blockSize + 1}" end="${(pageVO.curBlock)*pageVO.blockSize + pageVO.blockSize}" varStatus="st">
      <c:if test="${i <= pageVO.totPage && i == pageVO.pag}">
    		<li class="page-item active"><a class="page-link bg-secondary border-secondary" href="${ctp}/admin/member/adminMemberList?mid=${searchMid}&pag=${i}">${i}</a></li>
    	</c:if>
      <c:if test="${i <= pageVO.totPage && i != pageVO.pag}">
    		<li class="page-item"><a class="page-link text-secondary" href="${ctp}/admin/member/adminMemberList?mid=${searchMid}&pag=${i}">${i}</a></li>
    	</c:if>
    </c:forEach>
    <c:if test="${pageVO.curBlock < pageVO.lastBlock}">
      <li class="page-item"><a class="page-link text-secondary" href="${ctp}/admin/member/adminMemberList?mid=${searchMid}&pag=${(pageVO.curBlock+1)*pageVO.blockSize + 1}">다음블록</a></li>
    </c:if>
    <c:if test="${pageVO.pag < pageVO.totPage}">
      <li class="page-item"><a class="page-link text-secondary" href="${ctp}/admin/member/adminMemberList?mid=${searchMid}&pag=${pageVO.totPage}">마지막페이지</a></li>
    </c:if>
  </ul>
</div>
<!-- 블록 페이지 끝 -->
<p><br/></p>
<!-- The Modal -->
<div class="modal fade" id="myModal" style="width:690px;">
  <div class="modal-dialog">
    <div class="modal-content" style="width:600px;">
    
      <!-- Modal Header -->
      <div class="modal-header" style="width:600px;">
        <h4 class="modal-title">☆ 회원 상세정보 ☆(아이디:<span id="mid"></span>)</h4>
        <button type="button" class="close" data-dismiss="modal">&times;</button>
      </div>
      
      <!-- Modal body -->
      <div class="modal-body" style="width:600px;height:400px;overflow:auto;">
        <table class="table table-bordered" style="font-size:10pt">
          <tr>
          	<th>성명</th><td id="name"></td><th>닉네임</th><td id="nickName"></td>
          </tr>
          <tr>
          	<th>성별</th><td id="gender"></td><th>생일</th><td id="birthday"></td>
	        </tr>
          <tr>
          	<th>전화번호</th><td id="tel"></td><th>전자우편</th><td id="email"></td>
	        </tr>
          <tr>
          	<th>주소</th><td colspan="3" id="address"></td>
	        </tr>
          <tr>
          	<th>홈페이지</th><td id="homePage"></td><th>직업</th><td id="job"></td>
	        </tr>
          <tr>
          	<th>취미</th><td id="hobby"></td><th>정보공개여부</th><td id="userInfor"></td>
	        </tr>
          <tr>
          	<th>자기소개</th><td colspan="3" id="content"></td>
	        </tr>
          <tr>
          	<th>탈퇴여부</th><td id="userDel"></td><th>포인트</th><td id="point"></td>
	        </tr>
          <tr>
          	<th>오늘방문횟수</th><td id="todayCnt"></td><th>총 방문횟수</th><td id="visitCnt"></td>
	        </tr>
          <tr>
          	<th>최초가입일</th><td id="startDate"></td><th>최종방문일</th><td id="lastDate"></td>
	        </tr>
	        <tr>
          	<th>회원사진</th><td colspan="3" id="photo"></td>
	        </tr>
        </table>
      </div>
      
      <!-- Modal footer -->
      <div class="modal-footer" style="width:600px;">
        <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
      </div>
      
    </div>
  </div>
</div>
</body>
</html>