<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="path" value="${pageContext.request.contextPath}"/>
<c:set var="basePath" value="${pageContext.request.scheme}://${pageContext.request.serverName }:${pageContext.request.serverPort}${pageContext.request.contextPath}/"/>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<link rel="stylesheet" type="text/css" href="${basePath}easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${basePath}easyui/themes/default/searchbox.css">
<link rel="stylesheet" type="text/css" href="${basePath}easyui/themes/icon.css">
<script type="text/javascript" src="${basePath}easyui/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="${basePath}easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${basePath}easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${basePath}easyui/easyui.datagrid.columnMoving.js"></script> 
<script type="text/javascript" src="${basePath}js/common/json.js"></script>
<script type="text/javascript" src="${basePath}easyui/custom_validate.js"></script>
 