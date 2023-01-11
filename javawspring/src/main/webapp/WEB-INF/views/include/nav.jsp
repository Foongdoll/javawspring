<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!-- Navbar -->
<div class="w3-top">
  <div class="w3-bar w3-black w3-card">
    <a class="w3-bar-item w3-button w3-padding-large w3-hide-medium w3-hide-large w3-right" href="javascript:void(0)" onclick="myFunction()" title="Toggle Navigation Menu"><i class="fa fa-bars"></i></a>
    <a href="${ctp}/" class="w3-bar-item w3-button w3-padding-large">HOME</a>
    <a href="${ctp}/guest/guestList" class="w3-bar-item w3-button w3-padding-large w3-hide-small">GUEST</a>
    <c:if test="${sLevel < 5 || sLevel >= 0 }">
    <a href="${ctp}/board/boardList<c:if test="${sLevel == '4'}">?sw=1</c:if>" class="w3-bar-item w3-button w3-padding-large w3-hide-small">BOARD</a>
    <a href="${ctp}/pds/pdsList" class="w3-bar-item w3-button w3-padding-large w3-hide-small">PDS</a>
    <div class="w3-dropdown-hover w3-hide-small">
      <button class="w3-padding-large w3-button" >STUDY1<i class="fa fa-caret-down"></i></button>     
      <div class="w3-dropdown-content w3-bar-block w3-card-4">
        <a href="${ctp}/study/password/sha256" class="w3-bar-item w3-button">암호화 연습1(SHA256)</a>
        <a href="${ctp}/study/password/aria" class="w3-bar-item w3-button">암호화 연습2(Aria)</a>
        <a href="${ctp}/study/password/bCrypt" class="w3-bar-item w3-button">암호화 연습3(DECRYPTE)</a>
        <a href="${ctp}/study/ajax/ajaxMenu" class="w3-bar-item w3-button">AJax연습</a>
        <a href="${ctp}/study/mail/mailForm" class="w3-bar-item w3-button">메 일 연 습</a>
        <a href="${ctp}/study/uuid/uuidForm" class="w3-bar-item w3-button">UUID</a>
        <a href="${ctp}/study/fileUpload/fileUploadForm" class="w3-bar-item w3-button">파일업로드 연습</a>
        <a href="#" class="w3-bar-item w3-button">달 력</a>
        <c:if test="${sLevel == 0}">
        <a href="#" class="w3-bar-item w3-button">관리자 메뉴</a>
        </c:if>
      </div>
    </div>
    <div class="w3-dropdown-hover w3-hide-small">
      <button class="w3-padding-large w3-button" >STUDY2<i class="fa fa-caret-down"></i></button>     
      <div class="w3-dropdown-content w3-bar-block w3-card-4">
        <a href="#" class="w3-bar-item w3-button">쿠폰(QR코드)</a>
        <a href="#" class="w3-bar-item w3-button">웹 메세지</a>
        <a href="#" class="w3-bar-item w3-button">지도</a>
        <a href="#" class="w3-bar-item w3-button">트랜잭션</a>
        <a href="#" class="w3-bar-item w3-button">달 력</a>
        <c:if test="">
        <a href="#" class="w3-bar-item w3-button">관리자 메뉴</a>
        </c:if>
      </div>
    </div>
    <div class="w3-dropdown-hover w3-hide-small">
      <button class="w3-padding-large w3-button" title="${sNickName}">${sNickName}<i class="fa fa-caret-down"></i></button>     
      <div class="w3-dropdown-content w3-bar-block w3-card-4">
        <a href="${ctp}/member/memberMain" class="w3-bar-item w3-button">회원 메인화면</a>
        <a href="${ctp}/member/memberList<c:if test="${sLevel == 4}">?sw=1</c:if>" class="w3-bar-item w3-button">회원 리스트</a>
        <a href="${ctp}/member/memberInforUpdate" class="w3-bar-item w3-button">회원정보수정</a>
        <a href="${ctp}/member/memberPwdChange" class="w3-bar-item w3-button">비밀번호 변경</a>
        <a href="${ctp}/member/memberDel" class="w3-bar-item w3-button">회원 탈퇴</a>
        <c:if test="${sLevel ==  0}">
        <a href="${ctp}/admin/adminMain" class="w3-bar-item w3-button">관리자 메뉴</a>
        </c:if>
      </div>
    </div>
    </c:if>
    <c:if test="${empty sLevel}">
    <div class="w3-dropdown-hover w3-hide-small">
      <a href="${ctp}/member/memberLogin" class="w3-padding-large w3-button" title="로그인">LOGIN</a>     
    </div>
    <div class="w3-dropdown-hover w3-hide-small">
      <a href="${ctp}/member/memberJoin" class="w3-padding-large w3-button" title="회원가입">JOIN</a>     
    </div>
    </c:if>
    <c:if test="${!empty sLevel}">
    <div class="w3-dropdown-hover w3-hide-small">
      <a href="${ctp}/member/memberLogout" class="w3-padding-large w3-button" title="로그아웃">LOGOUT</a>     
    </div>
    </c:if>
    <a href="javascript:void(0)" class="w3-padding-large w3-hover-red w3-hide-small w3-right"><i class="fa fa-search"></i></a>
  </div>
</div>

<!-- Navbar on small screens (remove the onclick attribute if you want the navbar to always show on top of the content when clicking on the links) -->
<div id="navDemo" class="w3-bar-block w3-black w3-hide w3-hide-large w3-hide-medium w3-top" style="margin-top:46px">
  <a href="#band" class="w3-bar-item w3-button w3-padding-large" onclick="myFunction()">BAND</a>
  <a href="#tour" class="w3-bar-item w3-button w3-padding-large" onclick="myFunction()">TOUR</a>
  <a href="#contact" class="w3-bar-item w3-button w3-padding-large" onclick="myFunction()">CONTACT</a>
  <a href="#" class="w3-bar-item w3-button w3-padding-large" onclick="myFunction()">MERCH</a>
</div>