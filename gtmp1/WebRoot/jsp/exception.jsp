<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>系统异常</title>
  </head>
  <body>
    <h3>出错信息，请与管理员联系</h3>
<s:actionerror/>
<p>
  <s:property value="%{exception.message}"/>
</p>
<h3>详细信息</h3>
<p>
  <s:property value="%{exceptionStack}"/>
</p>
  </body>
</html>