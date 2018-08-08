<%--
 --%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true" pageEncoding="UTF-8" language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<!DOCTYPE html>
<html>
<body>
	<h1>标题: ${title}</h1>
	<h1>消息 : ${message}</h1>
	<c:if test="${pageContext.request.userPrincipal.name != null}">
	   <h2>欢迎: ${pageContext.request.userPrincipal.name} 
           | <a href="<c:url value="/login?logout" />" > Logout</a></h2>  
	</c:if>
</body>
</html>