<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String basePath = request.getContextPath();
%>
<form id="pagerForm" method="post" action="<%= basePath %>/admin/versions">
</form>

<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="<%= basePath %>/admin/signinreports" method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						开始时间：
						<input type="text" name="beginTime" class="date" dateFmt="yyyy-MM" readonly="readonly" value="${requestScope.beginTime}"/>
					</td>
					<td>
						结束时间：
						<input type="text" name="endTime" class="date" dateFmt="yyyy-MM" maxDate="%y-%M-%d" readonly="readonly" value="${requestScope.endTime}"/>
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
	<div id="sign_report"></div>
</div>
<script type="text/javascript">
	$(function () {
	    $('#sign_report').highcharts({
	    	credits: {
	    		enabled: false
	    	},
	        chart: {
	            type: 'column',
	            borderWidth: 0
	        },
	        title: {
	            text: '用户登录统计'
	        },
	        subtitle: {
	            text: '日期：${beginTime} ~ ${endTime}'
	        },
	        xAxis: {
	            categories: ${categories}
	        },
	        yAxis: {
	        	min: 0,
	            title: {
	                text: '登录数 (次)'
	            }
	        },
	        tooltip: {
	            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
	            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
	                '<td style="padding:0"><b>{point.y} 次</b></td></tr>',
	            footerFormat: '</table>',
	            shared: true,
	            useHTML: true
	        },
	        plotOptions: {
	            column: {
	                pointPadding: 0.2,
	                borderWidth: 0
	            }
	        },
	        series: ${barSeries}
	    });
	});
</script>