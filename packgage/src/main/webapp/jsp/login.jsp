<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login Page</title>
<style>
.error {
	padding: 15px;
	margin-bottom: 20px;
	border: 1px solid transparent;
	border-radius: 4px;
	color: #a94442;
	background-color: #f2dede;
	border-color: #ebccd1;
}

.msg {
	padding: 15px;
	margin-bottom: 20px;
	border: 1px solid transparent;
	border-radius: 4px;
	color: #31708f;
	background-color: #d9edf7;
	border-color: #bce8f1;
}

#login-box {
	width: 300px;
	padding: 20px;
	margin: 100px auto;
	background: #fff;
	-webkit-border-radius: 2px;
	-moz-border-radius: 2px;
	border: 1px solid #000;
}

.center {
    width:400px;
    height:200px; 
    margin: -100px 0 0 -200px;
    position: fixed;
    top: 50%;
    left: 50%;
}
</style>
<link href="<c:url value='/css/bootstrap/bootstrap.min.css'/>" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="<c:url value='/js/bootstrap/bootstrap.min.js'/>"></script>
</head>
<body background="<c:url value='/img/background2.jpg'/>" onload='document.loginForm.username.focus();'>
	<div class="center">
		<c:if test="${not empty error}">
			<div class="error">${error}</div>
		</c:if>
		<c:if test="${not empty msg}">
			<div class="msg">${msg}</div>
		</c:if>
		<form name='loginForm' action="<c:url value='/login' />" method='POST'
			class="form-horizontal">
			<div class="form-group">
				<label class="col-md-3 control-label">用户名:</label>
				<div class="col-md-9">
					<input type='text' name='username' value='' class="form-control">
				</div>
			</div>
			<div class="form-group">
				<label class="col-md-3 control-label">密码:</label>
				<div class="col-md-9">
					<input type='text' name='password' value='' class="form-control">
				</div>
			</div>
			<div class="form-group pull-right">
			    <button type="submit" class="btn btn-default">登录</button> 
			</div>
			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />
		</form>
	</div>
</body>
</html>