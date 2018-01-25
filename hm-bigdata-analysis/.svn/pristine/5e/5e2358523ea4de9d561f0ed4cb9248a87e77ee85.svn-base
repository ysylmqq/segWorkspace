<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> 

<html> 
<head> 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
<title>单点退出</title> 
</head> 
<body> 
<%
session.invalidate();  
response.sendRedirect(application.getInitParameter("casServerLogoutUrl")  
	+ "?service=" + application.getInitParameter("loginUrl"));
%>
</body> 
</html>
