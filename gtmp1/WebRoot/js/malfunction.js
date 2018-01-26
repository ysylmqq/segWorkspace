//默认查询条件
var defQueryParams={'startTime':getTodayZero(),
		'endTime':new Date().formatDate(timeFormat)};
//表格中的操作
function operate(val,row,index){
	var malfunctionCode = row.malfunctionCode;
	  var str1 ='<a style="color:blue;" href="javascript:void(0)" onclick="openSolutionWin(\''+malfunctionCode+'\')">'+val+'</a>';
	   return str1;
}
function openSolutionWin(code){
    $.post('run/malfunction_getDicMalfunctionCodeInfo.action',
    $.param({'dicMalfunctionCode.malfunctionCode':code},true),
    			function(result){
						var r = $.parseJSON(result);
						if(r.length>0){
	                      $('#malfunction').val(r[0].malfunction);
	                     // $('#solution').html(r[0].solution);
	                       $('#solution').text(r[0].solution);
	                       var solution= CKEDITOR.instances.solution;
	                      //solution.txt='';
	                      solution.setData( r[0].solution );
	                      //solution.insertHtml( r[0].solution );
	                      $('#dlg_solution').dialog('open');
						}
      	  	     }
);
}
// 窗口大小变化
function malFunctionMainResize(w, h) {
	if ($('#malfunction_datagrid')) {
		try {
			$('#malfunction_datagrid').datagrid('options');
			$('#malfunction_datagrid').datagrid('resize', {
				width : w - 4,
				height : h
			});
		} catch (e) {
		}
	}
}
//机型类型和机械型号联动
function typeSelect(rec){
                    var url = 'vehicle/vehicleModel_getList.action?typeId=' + rec.typeId;
                    $('#modelName_search').combobox('reload', url);
}


//查询
function queryMalfunction(){
	  var startTime= $('#start_time').val();
	   var endTime= $('#end_time').val();
	 
		if(startTime&&endTime&&startTime>=endTime){
		   $.messager.alert('提示','结束时间必须在开始时间之后!');
		   return;
		}
       $('#malfunction_datagrid').datagrid('load',{    
			vehicleDef: $('#vehicleDef_search').val(),    
			typeName: $('#typeName_search').combobox('getValue'),    
			modelName: $('#modelName_search').combobox('getValue'),    
			ownerName: $('#owner_search').val(),    
			malfunctionCode: $('#malfunctionCode_search').combobox('getValue'),  
			startTime: $('#start_time').val(),
			endTime: $('#end_time').val()
	    });  
}