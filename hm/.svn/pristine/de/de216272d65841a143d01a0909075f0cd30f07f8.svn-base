<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<title></title>
<style type="text/css">
        body {font-size: 12px;font-family: 'Microsoft YaHei';color: #000;}
        article{
            margin: 0 auto;
            width: 980px;
        }
        h2 {
            height: 50px;
            line-height: 50px;
            text-align: center;
            font-size: 16px;
            width: 100%;
            border-bottom: 1px dashed #333;
        }
        .sm_title {
            color: #333;
            text-align: center;
        }
        .img_up {
            overflow: hidden;
            width: 40%;
            margin: 0 auto;
        }
        .img_up img {
            float: left;
            margin:  20px 0 0 40px;
        }
        .content {
            font-size: 14px;
        }
        .img_down {
                margin: 0 auto;
                display: block;
                overflow: hidden;
                width: 900px;/*å®½åº¦èªå·±å®*/
        }
        .img_down img {display:block;margin: 0 auto;}
    </style>
</head>
<body>
	<article>
    <h2>${title}</h2>
    <p class="sm_title">${time_msg}${time} ${org_msg}${new_origin}</p>
    <div class="img_up">
    <img src="${big_img} ">
    
    <img src="${small_img }"></div>
    <p class="content">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${content}</p>
    <ul  class="img_down">
    <c:forEach items="${imgUrlList}" var="url" >
    	<li><img src="${url}"></li>
    </c:forEach>
    
    </ul>
</article>

<script language="javascript">

</script>
</body>
</html>