//默认查询条件
var defQueryParams={'stamp':getTodayZero(),
		'stamp2':new Date().formatDate(timeFormat)};
//窗口大小变化
function operateLogResize(w,h){
	if($('#operate_log_datagrid')){
		try{
		  $('#operate_log_datagrid').datagrid('options');
		  $('#operate_log_datagrid').datagrid('resize', {  
				width : w-2 ,
				height:h
		 }); 
		}catch(e){
		}
	}
}
//查询
function queryOpl(){
	var logType = $('#logType').combobox('getValue') == '全部' ? '' : $('#logType').combobox('getValue');
    var startTime= $('#start_time').val();
	var endTime= $('#end_time').val();
	if(startTime&&endTime&&(startTime>=endTime)){
	   $.messager.alert('提示','结束时间必须在开始时间之后!');
	   return;
	}
 	var params={
		'userName':$('#userName').val(),
		'logType': logType,
		'stamp': startTime,
		'stamp2':endTime
	};
			$('#operate_log_datagrid').datagrid('load',params);  
}