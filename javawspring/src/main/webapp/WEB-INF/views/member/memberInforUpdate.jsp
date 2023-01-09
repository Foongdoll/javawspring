<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>memJoin.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp"/>
  <script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
  <script src="${ctp}/js/woo.js"></script>
  <script>
    'use strict';
    
    // 회원가입폼 체크후 서버로 전송(submit)
    function fCheck() {
    	// 폼의 유효성 검사~~~~
    	let regMid = /^[a-z0-9_]{4,20}$/;
      // let regPwd = /(?=.*[a-zA-Z])(?=.*?[#?!@$%^&*-]).{4,24}/;
      let regPwd = /(?=.*[0-9a-zA-Z]).{4,20}$/;
      let regNickName = /^[가-힣]+$/;
      let regName = /^[가-힣a-zA-Z]+$/;
      let regEmail =/^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/;
      let regURL = /^(https?:\/\/)?([a-z\d\.-]+)\.([a-z\.]{2,6})([\/\w\.-]*)*\/?$/;
      let regTel = /\d{2,3}-\d{3,4}-\d{4}$/g;
      
      let submitFlag = 0;		// 전송체크버튼으로 값이 1이면 체크완료되어 전송처리한다.

      // 유효성검사를 위해 폼안의 내용들을 모두 변수에 담는다.
    	let name = myform.name.value;
    	let email1 = myform.email1.value;
    	let email2 = myform.email2.value;
      let email = email1 + '@' + email2;
      let homePage = myform.homePage.value;
      let tel1 = myform.tel1.value;
      let tel2 = myform.tel2.value;
      let tel3 = myform.tel3.value;
    	let tel = tel1 + "-" + tel2 + "-" + tel3;
    	
    	// 사진 업로드 체크를 위한 준비
    	let maxSize = 1024 * 1024 * 1; 	// 업로드할 회원사진의 용량은 1MByte까지로 제한한다.
    	let fName = myform.fName.value;
    	let ext = fName.substring(fName.lastIndexOf(".")+1);	// 파일 확장자 발췌
    	let uExt = ext.toUpperCase();		// 확장자를 대문자로 변환
    	
    	
    	// 유효성 검사체크처리한다.(필수 입력필드는 꼭 처리해야 한다.)
      if(!regName.test(name)) {
        alert("성명은 한글과 영문대소문자만 사용가능합니다.");
        myform.name.focus();
        return false;
      }
      else if(!regEmail.test(email)) {
        alert("이메일 형식에 맞지않습니다.");
        myform.email1.focus();
        return false;
      }
      else if((homePage != "http://" && homePage != "")) {
        if(!regURL.test(homePage)) {
	        alert("작성하신 홈페이지 주소가 URL 형식에 맞지않습니다.");
	        myform.homePage.focus();
	        return false;
        }
        else {
	    	  submitFlag = 1;
	      }
      }
    	
    	// 선택사항인 전화번호가 입력되어서 전송되었다면 전화번호형식을 체크해 준다.
      if(tel2 != "" || tel3 != "") {
	      if(!regTel.test(tel)) {
	        alert("전화번호 형식에 맞지않습니다.(000-0000-0000)");
	        myform.tel2.focus();
	        return false;
	      }
	      else {
	    	  submitFlag = 1;
	      }
      }
      else {	// 전화번호를 입력하지 않을시 DB에는 '010- - '의 형태로 저장하고자 한다.
    	  tel2 = " ";
    	  tel3 = " ";
    	  tel = tel1 + '-' + tel2 + '-' + tel3;
    	  submitFlag = 1;
      }
  		// 전송전에 '주소'를 하나로 묶어서 전송처리 준비한다.
  		let postcode = myform.postcode.value + " ";
  		let roadAddress = myform.roadAddress.value + " ";
  		let detailAddress = myform.detailAddress.value + " ";
  		let extraAddress = myform.extraAddress.value + " ";
  		myform.address.value = postcode + "/" + roadAddress + "/" + detailAddress + "/" + extraAddress + "/";
  		// 전송전에 파일에 관한 사항체크...(회원사진의 내역이 비었으면 noimage를 hidden필드인 photo필드에 담아서 전송한다.)
  		if(fName.trim() == "") {
  			myform.photo.value = '${vo.photo}';
				submitFlag = 1;
  		}
  		else {
  			let fileSize = document.getElementById("file").files[0].size;
  			
  			if(uExt != "JPG" && uExt != "GIF" && uExt != "PNG") {
  				alert("업로드 가능한 파일은 'JPG/GIF/PNG'파일 입니다.");
  				return false;
  			}
  			else if(fName.indexOf(" ") != -1) {
  				alert("업로드 파일명에 공백을 포함할 수 없습니다.");
  				return false;
  			}
  			else if(fileSize > maxSize) {
  				alert("업로드 파일의 크기는 1MByte를 초과할수 없습니다.");
  				return false;
  			}
  			
    		submitFlag = 1;
    	} 
    	
  		// 전송전에 모든 체크가 끝나서 submitFlag가 1이되면 서버로 전송한다.
    	if(submitFlag == 1) {
	  			myform.email.value = email;
	  			myform.tel.value = tel;
	  			
	  			myform.submit();
    		}
    	else alert("회원가입 실패~~");
    }
    
    
  </script>
</head>
<body>
	<jsp:include page="/WEB-INF/views/include/nav.jsp"></jsp:include>
  <jsp:include page="/WEB-INF/views/include/slide2.jsp"></jsp:include>
<p><br/></p>
<div class="container" style="padding:30px">
 <!--  <form name="myform" method="post" class="was-validated" enctype="multipart/form-data"> -->
  <form name="myform" method="post" enctype="multipart/form-data" class="was-validated">
    <h2>회 원 정 보 수 정</h2>
    <br/>
    <div class="form-group">
      <label for="mid">아이디 : </label>
      <input type="text" class="form-control" name="mid" id="mid" value="${vo.mid}"   readonly/>
    </div>
    <div class="form-group">
      <label for="pwd">비밀번호 :</label>
      <input type="password" class="form-control" id="pwd" name="pwd" value="${vo.pwd}" readonly required />
    </div>
    <div class="form-group">
      <label for="nickName">닉네임 :</label> 
      <input type="text" class="form-control" id="nickName" value="${vo.nickName}" name="nickName" readonly required />
    </div>
    <div class="form-group">
      <label for="name">성명 :</label>
      <input type="text" class="form-control" id="name" value="${vo.name}" name="name" required />
    </div>
    <div class="form-group">
      <label for="email1">Email address:</label>
				<div class="input-group mb-3">
				  <input type="text" class="form-control"  id="email1" name="email1" value="${vo.email1 }" required />
				  <div class="input-group-append">
				    <select name="email2" class="custom-select">
					    <option value="naver.com" 	<c:if test="${vo.email2 == 'naver.com'}">selected</c:if>>naver.com</option>
					    <option value="hanmail.net" <c:if test="${vo.email2 == 'hanmail.net'}">selected</c:if>>hanmail.net</option>
					    <option value="hotmail.com" <c:if test="${vo.email2 == 'hotmail.com'}">selected</c:if>>hotmail.com</option>
					    <option value="gmail.com" 	<c:if test="${vo.email2 == 'gmail.com'}">selected</c:if>>gmail.com</option>
					    <option value="nate.com" 		<c:if test="${vo.email2 == 'nate.com'}">selected</c:if>>nate.com</option>
					    <option value="yahoo.com"		<c:if test="${vo.email2 == 'yahoo.com'}">selected</c:if>>yahoo.com</option>
					  </select>
				  </div>
				</div>
	  </div>
    <div class="form-group">
      <div class="form-check-inline">
        <span class="input-group-text">성별 :</span> &nbsp; &nbsp;
			  <label class="form-check-label">
			    <input type="radio" class="form-check-input" name="gender" value="남자" <c:if test="${vo.gender == '남자'}">checked</c:if>>남자
			  </label>
			</div>
			<div class="form-check-inline">
			  <label class="form-check-label">
			    <input type="radio" class="form-check-input" name="gender"  value="여자" <c:if test="${vo.gender == '여자'}">checked</c:if>>여자
			  </label>
			</div>
    </div>
    <div class="form-group">
      <label for="birthday">생일</label>
      <input type="date" name="birthday" value="<%=java.time.LocalDate.now() %>" class="form-control"/>
    </div>
    <div class="form-group">
      <div class="input-group mb-3">
	      <div class="input-group-prepend">
	        <span class="input-group-text">전화번호 :</span> &nbsp;&nbsp;
			      <select name="tel1" class="custom-select">
					    <option value="010" <c:if test="${vo.tel1 =='010'}">selected</c:if>>010</option>
					    <option value="02" <c:if test="${vo.tel1 =='02'}">selected</c:if>>서울</option>
					    <option value="031" <c:if test="${vo.tel1 =='031'}">selected</c:if>>경기</option>
					    <option value="032" <c:if test="${vo.tel1 =='032'}">selected</c:if>>인천</option>
					    <option value="041" <c:if test="${vo.tel1 =='041'}">selected</c:if>>충남</option>
					    <option value="042" <c:if test="${vo.tel1 =='042'}">selected</c:if>>대전</option>
					    <option value="043" <c:if test="${vo.tel1 =='043'}">selected</c:if>>충북</option>
			        <option value="051" <c:if test="${vo.tel1 =='051'}">selected</c:if>>부산</option>
			        <option value="052" <c:if test="${vo.tel1 =='052'}">selected</c:if>>울산</option>
			        <option value="061" <c:if test="${vo.tel1 =='061'}">selected</c:if>>전북</option>
			        <option value="062" <c:if test="${vo.tel1 =='062'}">selected</c:if>>광주</option>
					  </select>-
	      </div>
	      <input type="text" name="tel2" value="${vo.tel2}" class="form-control"/>-
	      <input type="text" name="tel3" value="${vo.tel3}" class="form-control"/>
	    </div> 
    </div>
    <div class="form-group">
      <label for="address">주소</label>
			<input type="hidden" name="address" id="address">
			<div class="input-group mb-1">
				<input type="text" name="postcode" id="sample6_postcode" placeholder="우편번호" value="${vo.postcode}" class="form-control">
				<div class="input-group-append">
					<input type="button" onclick="sample6_execDaumPostcode()" value="우편번호 찾기" class="btn btn-secondary">
				</div>
			</div>
			<input type="text" name="roadAddress" id="sample6_address" size="50" placeholder="주소" value="${vo.roadAddress}" class="form-control mb-1">
			<div class="input-group mb-1">
				<input type="text" name="detailAddress" id="sample6_detailAddress" placeholder="상세주소" value="${vo.detailAddress}" class="form-control"> &nbsp;&nbsp;
				<div class="input-group-append">
					<input type="text" name="extraAddress" id="sample6_extraAddress" placeholder="참고항목" value="${vo.extraAddress}" class="form-control">
				</div>
			</div>
    </div>
    <div class="form-group">
	    <label for="homepage">Homepage address:</label>
	    <input type="text" class="form-control" name="homePage" value="${vo.homePage}" placeholder="홈페이지를 입력하세요." id="homePage"/>
	  </div>
    <div class="form-group">
      <label for="job">직업</label>
      <select class="form-control" id="job" name="job">
        <option <c:if test="${vo.job == '학생'}">selected</c:if>>학생</option>
        <option <c:if test="${vo.job == '회사원'}">selected</c:if>>회사원</option>
        <option <c:if test="${vo.job == '공무원'}">selected</c:if>>공무원</option>
        <option <c:if test="${vo.job == '군인'}">selected</c:if>>군인</option>
        <option <c:if test="${vo.job == '의사'}">selected</c:if>>의사</option>
        <option <c:if test="${vo.job == '법조인'}">selected</c:if>>법조인</option>
        <option <c:if test="${vo.job == '세무인'}">selected</c:if>>세무인</option>
        <option <c:if test="${vo.job == '자영업'}">selected</c:if>>자영업</option>
        <option <c:if test="${vo.job == '기타'}">selected</c:if>>기타</option>
      </select>
    </div>
    <div class="form-group">
      <div class="form-check-inline">
        <span class="input-group-text">취미</span> &nbsp; &nbsp;
			  <label class="form-check-label">
			    <input type="checkbox" class="form-check-input" value="등산" name="hobby"/>등산
			  </label>
			</div>
			<div class="form-check-inline">
			  <label class="form-check-label">
			    <input type="checkbox" class="form-check-input" value="낚시" name="hobby"/>낚시
			  </label>
			</div>
			<div class="form-check-inline">
			  <label class="form-check-label">
			    <input type="checkbox" class="form-check-input" value="수영" name="hobby"/>수영
			  </label>
			</div>
			<div class="form-check-inline">
			  <label class="form-check-label">
			    <input type="checkbox" class="form-check-input" value="독서" name="hobby"/>독서
			  </label>
			</div>
			<div class="form-check-inline">
			  <label class="form-check-label">
			    <input type="checkbox" class="form-check-input" value="영화감상" name="hobby"/>영화감상
			  </label>
			</div>
			<div class="form-check-inline">
			  <label class="form-check-label">
			    <input type="checkbox" class="form-check-input" value="바둑" name="hobby"/>바둑
			  </label>
			</div>
			<div class="form-check-inline">
			  <label class="form-check-label">
			    <input type="checkbox" class="form-check-input" value="축구" name="hobby"/>축구
			  </label>
			</div>
			<div class="form-check-inline">
			  <label class="form-check-label">
			    <input type="checkbox" class="form-check-input" value="기타" name="hobby" checked/>기타
			  </label>
			</div>
    </div>
    <div class="form-group">
      <label for="content">자기소개</label>
      <textarea rows="5" class="form-control" id="content" name="content"  placeholder="자기소개를 입력하세요.">${vo.content }</textarea>
    </div>
    <div class="form-group">
      <div class="form-check-inline">
        <span class="input-group-text">정보공개</span>  &nbsp; &nbsp; 
			  <label class="form-check-label">
			    <input type="radio" class="form-check-input" name="userInfor" value="공개" <c:if test="${vo.userInfor == '공개' }">checked</c:if>/>공개
			  </label>
			</div>
			<div class="form-check-inline">
			  <label class="form-check-label">
			    <input type="radio" class="form-check-input" name="userInfor" value="비공개" <c:if test="${vo.userInfor == '비공개' }">checked</c:if>/>비공개
			  </label>
			</div>
    </div>
    <div  class="form-group">
      회원 사진(파일용량:2MByte이내) :
      <input type="file" name="fName" id="file" onchange="photoChange()" class="form-control-file border"/>(${vo.photo})
      <c:if test="${vo.photo != ''}">
      <img src="${ctp}/member/${vo.photo}">
      </c:if>
      <c:if test="${vo.photo == ''}">
      <h4>사진없음</h4>
      </c:if>
    </div>
    <button type="button" class="btn btn-secondary" onclick="fCheck()">회원 정보 수정</button> &nbsp;
    <button type="reset" class="btn btn-secondary">다시작성</button> &nbsp;
    <button type="button" class="btn btn-secondary" onclick="location.href='${ctp}/member/memberLogin';">돌아가기</button>
    <input type="hidden" name="photo"/>
    <input type="hidden" name="tel"/>
    <input type="hidden" name="email"/>
  </form>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>