<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!doctype HTML>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,user-scalable=no,maximum-scale=1">
<title>${news.title}</title>
<style type="text/css">
body{padding: 10px 2px;font-family:Arial, Helvetica, sans-serif;}
.container{width:800px;max-width:100%;margin:0px auto;padding:0px;}
h1{font-size:24px;color:#333333;margin:2px;padding:0px;}
header p{color:#9bacb9;margin:0px;padding:0px;}
hr{border-color:rgba(155,172,185,0.2);border-top:1px;}
img{max-width:100%;}
.content{font-size:18px;color:#535656;line-height:28px;}
a{overflow-wrap: break-word;}
</style>
</head>
<body>
	<div class="container">
		<article>
			<header>
				<h1>${news.title}</h1>
				<p><fmt:formatDate value="${news.startTime}" pattern="yyyy-MM-dd HH:mm" timeZone="GMT+8"/></p>
			</header>
			<hr>
			<div class="content">
				<c:choose>
					<c:when test="${news.content != null and news.content != '' }">
						${news.content}
					</c:when>
					<c:otherwise>
						<h1 style="text-align:center;">404 - 您访问的页面不存在！</h1>
					</c:otherwise>
				</c:choose>
			</div>
		</article>
	</div>
</body>
</html>
