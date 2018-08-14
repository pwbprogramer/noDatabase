<%--
        登录成功后首页
 --%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true" pageEncoding="UTF-8" language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<style type="text/css">
html,body{
  height: 100%;
}
.main-Left{
  background-color:red;
  height: 100%;
} 
.main-Right{
  background-color:blue;
  height: 100%;
} 
</style>
<script type="text/javascript" src="<c:url value='/js/jquery/jquery-3.0.0.min.js'/>"></script>
<link href="<c:url value='/css/bootstrap/bootstrap.min.css'/>" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="<c:url value='/js/bootstrap/bootstrap.min.js'/>"></script>
</head>
<body>
	<div id="mainLeft" class="col-md-2 main-Left">
	<h1>标题: ${title}</h1>
	<h1>消息 : ${message}</h1>
	<c:if test="${pageContext.request.userPrincipal.name != null}">
	   <h2>欢迎: ${pageContext.request.userPrincipal.name} 
           | <a href="<c:url value="/login?logout" />" > Logout</a></h2>  
	</c:if>
	</div>
	<div id="mainRight" class="col-md-10 main-Right">
	</div>
	
</body>
</html>