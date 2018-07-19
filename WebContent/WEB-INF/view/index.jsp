
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css"/>

</head>
<body>
<div class="forms">
	<form:form action="/emstwo/login" method="post" modelAttribute="employee">
	      <h1>Login on EMS System</h1>
	      <p style="color: red">${message}</p>
	      <div class="input-field">
	        <label for="text">Employee Id</label>
	        <form:input path="employeeId" type="text" pattern="[0-9]+" maxlength="5" size="5" title="Employee Id Contains Only Integer" required="text" />
	        <label for="password">Password</label> 
	        <form:password path="pin" pattern="[0-9]+" maxlength="4" size="4" title="Password contains Only Integer" required="text"/>
	        <input type="submit" value="Login" class="button"/>
	      </div>
	  </form:form>
	  <p>Not a member? <span><a href="signuppage">Sign Up</span></p>
	  
	  
</div>
</body>
</html>