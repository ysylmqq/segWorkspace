//默认查询条件
var defQueryParams={'startTime':getTodayZero(),
		'endTime':new Date().formatDate(timeFormat)};
//表格中的操作
function operate(val,row,index){
	var malfunctionCode = row.malfunctionCode;
	// var str1 ='<a style="color:blue;" href="javascript:void(0)" onclick="openSolutionWin(\''+malfunctionCode+'\')">'+val+'</a>';
	   return val;
}
function openSolutionWin(code){
    $.post('run/malfunction_getDicMalfunctionCodeInfo.action',
    $.param({'dicMalfunctionCode.malfunctionCode':code},true),
    			function(result){
						var r = $.parseJSON(result);
						if(r.length>0){
	                      $('#malfunction').val(r[0].malfunction);
	                      $('#solution').html(r[0].solution);
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
			modelName: $('#vehicleModel_search').combobox('getValue'),
			vehicleCode: $('#vehicleCode_search').combobox('getValue') == '全部' ? '' : $('#vehicleCode_search').combobox('getValue'),
			vehicleArg: $('#vehicleArg_search').combobox('getValue') == '全部' ? '' : $('#vehicleArg_search').combobox('getValue'),
			ownerName: $('#owner_search').val(),
			malfunctionCode: $('#malfunctionCode_search').combobox('getValue'),  
			startTime: $('#start_time').val(),
			endTime: $('#end_time').val()
	    });
}

//机械型号联动控制      
$('#vehicleModel_search').combobox({
    onSelect:function(){
        var obj = $('#vehicleModel_search').combobox('getValue');       
        $("#vehicleCode_search").empty();
        $.get(
                "report/workhour_modelNameSearch.action",
                {obj: obj},
                function(data) {
                    var dataJson = $.parseJSON(data);
                    var datas = [{'vehicleArg':'全部'}];
                    for(i=0;i<dataJson.code.length;i++){
                        var docNameInfo = dataJson.code[i].vehicleCode;
                        datas.push({'vehicleCode': docNameInfo});
                    }
                    $('#vehicleCode_search').combobox('clear').combobox('loadData', datas).combobox('select', '全部');
                }
            );
        
    }
});

//机械配置联动
$('#vehicleCode_search').combobox({
	onSelect:function(){
		var obj = $('#vehicleCode_search').combobox('getValue');
		$("#vehicleArg_search").empty();
		$.get(
			"vehicle/vehicleModel_getList2.action",
			{obj: obj},
			function(data) {
				var dataJson = $.parseJSON(data);
				var datas = [{'vehicleArg':'全部'}];
				for(i=0;i<dataJson.arg.length;i++){
					var docNameInfo = dataJson.arg[i].vehicleArg;
					datas.push({'vehicleArg': docNameInfo});
				}
				$('#vehicleArg_search').combobox('clear').combobox('loadData', datas).combobox('select', '全部');
			}
		);
	}
});
