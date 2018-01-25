<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String basePath = request.getContextPath();
%>
<script type="text/javascript">
	$(function() {
		$(".combox option[value='${page.numPerPage}']").attr("selected", true);
	});
</script>
<form id="pagerForm" method="post" action="<%= basePath %>/admin/versions">
	<input type="hidden" name="pageNum" value="${page.pageNum}" />
	<input type="hidden" name="numPerPage" value="${page.numPerPage}" />
</form>

<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="<%= basePath %>/admin/versions" method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						平台系统：
						<select name="origin" >
							<option value="">--不限--</option>
							<c:choose>
								<c:when test="${requestScope.origin == 1}">
									<option value="1" selected="selected">车圣互联</option>
								</c:when>
								<c:otherwise>
									<option value="1">车圣互联</option>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${requestScope.origin == 2}">
									<option value="2" selected="selected">海马</option>
								</c:when>
								<c:otherwise>
									<option value="2">海马</option>
								</c:otherwise>
							</c:choose>
						</select>
					</td>
					<td>
						版本名：
						<input type="text" name="versionName" value="${requestScope.versionName}" />
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">
									检索
								</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li>
				<a class="add" href="<%=basePath%>/admin/versions/new" target="dialog" rel="addVersion" title="上传版本">
					<span>添加</span>
				</a>
			</li>
			<li class="line"></li>
			<li>
				<a class="delete" href="<%=basePath%>/admin/versions/{id}/delete" target="ajaxTodo" rel="navTab" title="确定要删除吗?">
					<span>删除</span>
				</a>
			</li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th>平台系统</th>
				<th>版本名</th>
				<th>下载地址</th>
				<th>更新说明</th>
				<th>更新时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${versionList}" var="version">
				<tr target="id" rel="${version.id}">
					<td>
						<c:choose>
							<c:when test="${version.origin == 1}">车圣互联</c:when>
							<c:when test="${version.origin == 2}">海马</c:when>
						</c:choose>
					</td>
					<td>${version.versionName}</td>
					<td>
						<a href="${version.url}" target="_blank">${version.url}</a>
					</td>
					<td title="${version.caption}">${version.caption}</td>
					<td>
						<fmt:formatDate value="${version.stamp}" pattern="yyyy-MM-dd HH:mm:ss" timeZone="GMT+8"/>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="panelBar">
		<div class="pages">
			<span>显示</span>
			<select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value})">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select>
			<span>条，共${page.totalCount}条</span>
		</div>
		<div class="pagination" targetType="navTab" totalCount="${page.totalCount}" numPerPage="${page.numPerPage}" pageNumShown="10" currentPage="${page.pageNum}"></div>
	</div>
</div>
