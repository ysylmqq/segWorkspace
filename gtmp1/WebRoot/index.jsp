<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ include file="jsp/common.jsp" %>
<html>
  <head>
    <title>玉柴重工GPS物联网管理平台</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  	<style type="text/css">
 		body{ padding:0px; margin:0;}
	</style>
 
 <script type="text/javascript" src="js/index.js"></script>
 <script type="text/javascript" src="${basePath}js/common/jshash.js"></script>
 <script type="text/javascript" src="js/common/globe.js"></script>
 <script type="text/javascript" src="${basePath}js/My97DatePicker/WdatePicker.js"></script> 
 <script type="text/javascript" src="${basePath}ckeditor/ckeditor.js"></script>
 
 <script type="text/javascript">
 	function alarmResize(w, h){
 		try{
 			$('#alarm_datagrid').datagrid('options');
 			$('#alarm_datagrid').datagrid('resize', {width: w-16, height: h-2});
 		}catch(e){}
 	}
 
 function closes(){
 	$("#Loading").fadeOut("fast",function(){
 		$(this).remove();
 	});
 }
 var pc;
 $.parser.onComplete = function(){
 	if(pc) clearTimeout(pc);
 	pc = setTimeout(closes, 500);
 }
 </script>
  </head>
  
 <body class="easyui-layout">
 <div id='Loading'
	style="position: absolute; z-index: 1000; top: 0px; left: 0px; width: 100%; height: 100%; text-align: center; padding-top: 20%;">
	<h1>
	<font color="#15428B">加载中···</font>
	</h1>
</div>
	 <div id="north_index" data-options="region:'north'" style="height:135px;width:100%;overflow:hidden">
	  <jsp:include page="jsp/north.jsp"></jsp:include>
	 </div>  
	<div id="main" data-options="region:'center',href:'${basePath}${firstPage}'" style="overflow-y:auto;">
	</div>
	<div id="south_index" data-options="region:'south'" style="height:0px;">
	</div> 
	<!-- 警情--begin -->
	<!-- 声音提醒 -->
	<audio id="embed_alarm" src="sound/notify.wav" autoplay="false"></audio>
	
	<div id="dlg_alarm" style="width: 800px; height: 400px; overflow: hidden" data-options="iconCls:'icon-tip',maximizable:true,closed:true,onResize:alarmResize">
		<table id="alarm_datagrid" data-options="fit:true,border:false" style="padding-top:0px;margin-top:0px;"></table>
	</div>
	<div id="alarm_toobar"  
		style="height: auto; display: none">
			整机编号： <input id="vehicleDef_alarm" 
				style="width: 120px;" />
				警情类型： <input id="alarmType_alarm" 
				style="width: 220px;" />  
				<a href="#" class="easyui-linkbutton"
				iconCls="icon-search" onclick="queryAlarm()">查询</a> 
				<a href="#" id="downBtn" class="easyui-linkbutton"
							iconCls="icon-exportexcel" onclick="javascript:downExcel();">导出</a>
				 <a href="#"
				class="easyui-linkbutton" iconCls="icon-print"
				onclick="setAlreadyRead()">设置已读</a>
	</div>
	<!-- 警情--end -->
</body>
</html>
