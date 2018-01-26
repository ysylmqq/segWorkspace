var pdata=null;
$(function(){
		    var wid = $(document.body).width();
			var hei = $(document.body).height();
			
			//初始化地图数据
			if(window.parent){
			   pdata=window.parent.workInfData;
			   var url='';
			   var params={};
			  if(pdata){
			   url='../../run/run_queryHistoryCondition.action';
			   params={
					   unitId:pdata,
					   startTime:getTodayZero(),
					   endTime:new Date().formatDate(timeFormat)
					   
			   };
			  }
			  $('#work_info_datagrid').datagrid({
					width : wid,
					height :hei,
					url:url,
					queryParams:params
			   });
			}
	})
//窗口大小变化
function historyWorkInfoResize(w,h){
	if($('#work_info_datagrid')){
		try{
		  $('#work_info_datagrid').datagrid('options');
		  $('#work_info_datagrid').datagrid('resize', {  
				width : w-1,
				height:h
		 }); 
		}catch(e){
		}
	}
}
function query(){
    var startTime= $('#start_time').val();
	var endTime= $('#end_time').val();
	if(startTime&&endTime&&(startTime>=endTime)){
	   $.messager.alert('提示','结束时间必须在开始时间之后!');
	   return;
	}
	$('#work_info_datagrid').datagrid('load',{
	    unitId:pdata,
	    startTime:startTime,
	     endTime:endTime
	});
}
function downExcel(){
  var startTime= $('#start_time').val();
	 var endTime= $('#end_time').val();
	if(startTime&&endTime&&(startTime>=endTime)){
	   $.messager.alert('提示','结束时间必须在开始时间之后!');
	   return;
	}
	   window.location.href=encodeURI(encodeURI("../../run/run_exportToHisConditions.action?unitId="+pdata+"&startTimeStr="+startTime+"&endTimeStr="+endTime));
	   return;
}
/*
 * 打开图表窗口
 */
function openChartWin() {
	var startTime = $('#start_time').val();
	var endTime = $('#end_time').val();
	var chart_type = $('#chart_type').combobox('getValue');
	if (startTime && !endTime) {
		$.messager.alert('提示', '请选择结束时间!!');
		return;
	}
	if (endTime && !startTime) {
		$.messager.alert('提示', '请选择开始时间!!');
		return;
	}
	if (startTime && endTime) {
		var len = getMonthNumber(startTime, endTime);
		if (len < 0) {
			$.messager.alert('提示', '结束时间必须在开始时间之后!');
			return;
		} else if (len > 11) {
			$.messager.alert('提示', '查询月份时间段不能超过12个月!');
			return;
		}
	}
	var src = encodeURI(encodeURI("../../run/run_drawChart.action?startTime=" + startTime
			+"&endTime="+ endTime + "&unitId=" + pdata+"&chart_type="+chart_type));
	$('<div/>').dialog({
		// content:'<iframe src="obd/map.jsp" id="map" name="map"
		// frameborder="0" style="border:0;width:100%;height:99%;"></iframe>',
		content : '<img src=\'' + src + '\'/>',
		width : 760,
		height : 470,
		//cache : true,
		loadingMessage : '加载中...',
		modal : true,
		resizable : true,
		title : '图表(显示最近20次数据)',
		onClose : function() {
			$(this).dialog('destroy');
		},
		onLoad : function() {

		},
		buttons : [{
			text : '关闭',
			iconCls : 'icon-cancel',
			handler : function() {
				var d = $(this).closest('.window-body');
				d.dialog('destroy');
			}
		} ]
	});
}