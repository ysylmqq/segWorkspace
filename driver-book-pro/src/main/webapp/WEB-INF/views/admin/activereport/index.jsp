<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String basePath = request.getContextPath();
%>
<form id="pagerForm" method="post" action="<%= basePath %>/admin/activereports">
</form>

<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="<%= basePath %>/admin/activereports" method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						最近
						<input type="text" name="days" value="${requestScope.days}"/>
						天登录
						<input type="text" name="times" value="${requestScope.times}"/>
						次
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
	<div id="active_report"></div>
</div>
<script type="text/javascript">
	$(function () {
	    $('#active_report').highcharts({
	    	credits: {
	    		enabled: false
	    	},
	        chart: {
	        	type: 'pie',
	            plotBackgroundColor: null,
	            plotBorderWidth: 1,
	            plotShadow: false,
	            borderWidth: 0
	        },
	        title: {
	            text: '活跃用户统计'
	        },
	        tooltip: {
	    	    pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
	        },
	        plotOptions: {
	            pie: {
	                allowPointSelect: true,
	                cursor: 'pointer',
	                dataLabels: {
	                    enabled: true,
	                    format: '<b>{point.name}: {point.y}</b> ({point.percentage:.1f} %)',
	                    style: {
	                        color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
	                    }
	                }
	            }
	        },
	        series: [{
	        	name: '用户数',
	            data: ${pieSeries}
	        }]
	    });
	});
</script>