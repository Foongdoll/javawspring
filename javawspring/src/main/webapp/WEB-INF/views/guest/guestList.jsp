<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<% pageContext.setAttribute("newLine", "\n"); %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>guestList.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp"></jsp:include>
  <style>
    th {
      text-align: center;
      background-color: #ccc;
    }
  </style>
  <script>
    'use strict';
    function delCheck(idx) {
    	let ans = confirm("정말로 삭제하시겠습니까?");
    	if(ans) location.href="${ctp}/guest/guestDelete.gu?idx="+idx;
    }
    
    function sCheck(){
    	let searchStr = $("#searchStr").val();
    	
    	if(searchStr == ""){
    		alert("검색어를 입력해주세요.");
    		$("#searchStr").focus();
    		return;
    	}
    	
    	searchForm.submit();
    	
    }
    
  </script>
</head>
<body>
	<jsp:include page="/WEB-INF/views/include/nav.jsp"></jsp:include>
  <jsp:include page="/WEB-INF/views/include/slide2.jsp"></jsp:include>
<p><br/></p>
<div class="container">
  <h2 class="text-center">방 명 록 리 스 트</h2>
  <br/>
  <table class="table table-borderless mb-0">
		<tr>
		  <td>
		    <c:if test="${sAMid != 'admin'}"><a href="${ctp}/adminLogin" class="btn btn-sm btn-secondary">관리자</a></c:if>
		    <c:if test="${sAMid == 'admin'}"><a href="${ctp}/adminLogout" class="btn btn-sm btn-secondary">관리자 로그아웃</a></c:if>
		  </td>
		  <td>
			  <form name="searchForm" method="post" action="${ctp}/guest/guestSearch">
			  	 <select name="category" class="btn btn-success">
			  	 		<option value="name">이름</option>
			  	 		<option value="email">이메일</option>
			  	 		<option value="homePage">홈페이지</option>
			  	 </select>
			  	<input type="text" name="searchStr" id="searchStr" class="form-control"/>
			  	<input type="button" value="검색" onclick="sCheck()" class="btn btn-success"/>
			  </form>	
		  </td>
		  <td style="text-align:right;"><a href="${ctp}/guest/guestInput" class="btn btn-sm btn-secondary">글쓰기</a></td>
		</tr>
  </table>
  <c:set var="no" value="${fn:length(vos)}"/>
  <c:set var="curScrStartNo" value="${pageVO.curScrStartNo}"/>
  <c:forEach var="vo" items="${vos}" varStatus="st">
	  <table class="table table-borderless mb-0">
			<tr>
			  <td>방문번호 : ${curScrStartNo}
			    <c:if test="${sAMid == 'admin'}"><a href="javascript:delCheck(${vo.idx})" class="btn btn-sm btn-danger">삭제</a></c:if>
			  </td>
			  <td style="text-align:right;">방문IP : ${vo.hostIp}</td>
			</tr>
	  </table>
	  <table class="table table-bordered">
			<tr>
			  <th style="width:20%;">성명</th>
			  <td style="width:25%;">${vo.name}</td>
			  <th style="width:20%;">방문일자</th>
			  <td style="width:35%;">${fn:substring(vo.visitDate,0,19)}</td>
			</tr>
			<tr>
			  <th>전자우편</th>
			  <td colspan="3">
			    <c:if test="${empty vo.email || fn:length(vo.email)<=4 || fn:indexOf(vo.email,'@')==-1 || fn:indexOf(vo.email,'.')==-1}">- 없음 -</c:if>
			    <c:if test="${!empty vo.email && fn:length(vo.email)>4 && fn:indexOf(vo.email,'@')!=-1 && fn:indexOf(vo.email,'.')!=-1}">${vo.email}</c:if>
			  </td>
			</tr>
			<tr>
			  <th>홈페이지</th>
			  <td colspan="3">
			    <c:if test="${fn:length(vo.homePage) <= 8}">- 없음 -</c:if>
			    <c:if test="${fn:length(vo.homePage) > 8}"><a href="${vo.homePage}" target="_blank">${vo.homePage}</a></c:if>
			  </td>
			</tr>
			<tr>
			  <th>방문소감</th>
			  <td colspan="3">${fn:replace(vo.content, newLine, '<br/>')}</td>
			</tr>
	  </table>
	  <br/>
	  <c:set var="curScrStartNo" value="${curScrStartNo - 1}"/>
	</c:forEach>
	<br/>
  <!-- 블록 페이지 시작 -->
	<div class="text-center">
	  <ul class="pagination justify-content-center">
	    <c:if test="${pageVO.pag > 1}">
	      <li class="page-item"><a class="page-link text-secondary" href="${ctp}/guest/guestList?pag=1<c:if test="${!empty searchStr}">&searchStr=${searchStr}&category=${category}</c:if>">첫페이지</a></li>
	    </c:if>
	    <c:if test="${pageVO.curBlock > 0}">
	      <li class="page-item"><a class="page-link text-secondary" href="${ctp}/guest/guestList?pag=${(pageVO.curBlock-1)* pageVO.blockSize + 1}<c:if test="${!empty searchStr}">&searchStr=${searchStr}&category=${category}</c:if>">이전블록</a></li>
	    </c:if>
	    <c:forEach var="i" begin="${(pageVO.curBlock)*pageVO.blockSize + 1}" end="${(pageVO.curBlock)*pageVO.blockSize + pageVO.blockSize}" varStatus="st">
	      <c:if test="${i <= pageVO.totPage && i == pageVO.pag}">
	    		<li class="page-item active"><a class="page-link bg-secondary border-secondary" href="${ctp}/guest/guestList?pag=${i}<c:if test="${!empty searchStr}">&searchStr=${searchStr}&category=${category}</c:if>">${i}</a></li>
	    	</c:if>
	      <c:if test="${i <= pageVO.totPage && i != pageVO.pag}">
	    		<li class="page-item"><a class="page-link text-secondary" href="${ctp}/guest/guestList?pag=${i}<c:if test="${!empty searchStr}">&searchStr=${searchStr}&category=${category}</c:if>">${i}</a></li>
	    	</c:if>
	    </c:forEach>
	    <c:if test="${pageVO.curBlock < pageVO.lastBlock}">
	      <li class="page-item"><a class="page-link text-secondary" href="${ctp}/guest/guestList?pag=${(pageVO.curBlock+1)*pageVO.blockSize + 1}<c:if test="${!empty searchStr}">&searchStr=${searchStr}&category=${category}</c:if>">다음블록</a></li>
	    </c:if>
	    <c:if test="${pageVO.pag < pageVO.totPage}">
	      <li class="page-item"><a class="page-link text-secondary" href="${ctp}/guest/guestList?pag=${pageVO.totPage}<c:if test="${!empty searchStr}">&searchStr=${searchStr}&category=${category}</c:if>">마지막페이지</a></li>
	    </c:if>
	  </ul>
</div>
<!-- 블록 페이지 끝 -->
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>