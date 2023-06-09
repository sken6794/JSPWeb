<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file = "../include/header.jsp"%>

<section>
	<div align="center">
		<h3>(${sessionScope.user_name })님 회원 정보 수정</h3>
		<form action="user_update.user" method="post">
			<table border="1">
				<tr>
					<td>아이디</td>
					<!-- readonly 혹은 disabled -->
					<!-- readonly는 값을 parameter로 넘어감, disabled는 param으로 넘어가지 않는다. -->
					<!-- 인풋태그에 미리 값을 가지면 value를 사용 -->
					<td><input type="text" name="id" readonly="readonly" value="${vo.id }"></td>
				</tr>			
				<tr>
					<td>비밀번호</td>
					<td><input type="password" name="pw" required="required" pattern="\w{4,}"></td>
				</tr>
				<tr>
					<td>닉네임</td>
					<td><input type="text" name="name" value="${vo.name }"></td>
				</tr>
				<tr>
					<td>이메일</td>
					<td><input type="email" name="email" value="${vo.email }"></td>
				</tr>
				<tr>
					<td>성별</td>
					<td>
						<!-- radio 타입에 checked 속성을 삼항연산자로 표현하는 방법 -->
						<input type="radio" name="gender" value="M" ${vo.gender == 'M' ? 'checked':'' }>남자 
						<input type="radio" name="gender" value="F" ${vo.gender == 'F' ? 'checked':'' }>여자 
					</td>
				</tr>
			</table>
			
			<div style="color: red;">${msg }</div>
			
			
			<input type="submit" value="수정하기">
			<input type="button" value="회원페이지로 가기" onclick="location.href='user_mypage.user'">			
			
		</form>
	</div>




</section>






<%@ include file = "../include/footer.jsp"%>