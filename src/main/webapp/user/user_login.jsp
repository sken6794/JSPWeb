<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>    

<section>
	<div align="center">
		<h3>로그인페이지</h3>
		
		<form action="loginForm.user" method="post">
			<input type="text" name="id" placeholder="id입력"><br/>
			<input type="password" name="pw" placeholder="pw입력"><br/>
			<input type="submit" value="login">
			<input type="button" value="join" onclick="location.href='user_join.user' ">
			
		</form>	
		<div>${msg }</div>
		
		
		
	</div>
</section>


<%@ include file="../include/footer.jsp" %>    