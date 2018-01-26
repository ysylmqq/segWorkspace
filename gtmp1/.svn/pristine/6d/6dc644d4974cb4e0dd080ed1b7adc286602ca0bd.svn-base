//默认查询条件
var defQueryParams={'commandPOJO.stamp':getTodayZero(),
		'commandPOJO.stamp2':new Date().formatDate(timeFormat)};
//窗口大小变化
function commandResize(w,h){
	if($('#command_datagrid')){
		try{
		  $('#command_datagrid').datagrid('options');
		  $('#command_datagrid').datagrid('resize', {  
				width : w-2 ,
				height:h
		 }); 
		}catch(e){
		}
	}
}
//查询
function queryCommand(){
    var startTime= $('#start_time').val();
	 var endTime= $('#end_time').val();
	if(startTime&&endTime&&(startTime>=endTime)){
	   $.messager.alert('提示','结束时间必须在开始时间之后!');
	   return;
	}
	
 	var params={
		'commandPOJO.userName':$('#userName').val(),
		'commandPOJO.param':$('#dealerId_search').combobox('getValues').join(','),
		'commandPOJO.vehicleDef':$('#vehicleDef_search').val(),
		'commandPOJO.vehicleModel': $('#vehicleModel_search').combobox('getValue'),
		'commandPOJO.vehicleCode': $('#vehicleCode_search').combobox('getValue') == '全部' ? '' : $('#vehicleCode_search').combobox('getValue'),
		'commandPOJO.vehicleArg': $('#vehicleArg_search').combobox('getValue') == '全部' ? '' : $('#vehicleArg_search').combobox('getValue'),
		'commandPOJO.commandTypeId':$('#commandTypeId').combobox('getValue'),
		'commandPOJO.gatewayBack':$('#gatewayBack_search').combobox('getValue'),
		'commandPOJO.unitBack':$('#unitBack_search').combobox('getValue'),
		'commandPOJO.pathFlag':$('#pathFlag_search').combobox('getValue'),
		'commandPOJO.stamp': startTime,
		'commandPOJO.stamp2':endTime
	};
	$('#command_datagrid').datagrid('load',params);  
}
//导出
function downExcel(){	
   var start_time = $('#start_time').val();
   var end_time =  $('#end_time').val();
	   if(start_time&&end_time&&(start_time>=end_time)){
		   $.messager.alert('提示','结束时间必须在开始时间之后!');
		   return;
		}
	   var vehicleModel = $('#vehicleModel_search').combobox('getValue');
	   var vehicleCode = $('#vehicleCode_search').combobox('getValue') == '全部' ? '' : $('#vehicleCode_search').combobox('getValue');
	   var vehicleArg = $('#vehicleArg_search').combobox('getValue') == '全部' ? '' : $('#vehicleArg_search').combobox('getValue');
	   window.location.href=encodeURI(encodeURI("run/command_exportToExcel.action?commandPOJO.vehicleModel="+vehicleModel+"&commandPOJO.vehicleCode="+vehicleCode+"&commandPOJO.vehicleArg="+vehicleArg+"&commandPOJO.userName="+$('#userName').val()+"&startTimeStr="+start_time+"&endTimeStr="+end_time+"&commandPOJO.commandTypeId="+$('#commandTypeId').combobox('getValue')+"&commandPOJO.param="+$('#dealerId_search').combobox('getValues').join(',')+"&commandPOJO.vehicleDef="+$('#vehicleDef_search').val()+"&commandPOJO.gatewayBack="+$('#gatewayBack_search').combobox('getValue')+"&commandPOJO.unitBack="+$('#unitBack_search').combobox('getValue')+"&commandPOJO.pathFlag="+$('#pathFlag_search').combobox('getValue')));
	   return;
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

$('#dealerId_search').combotree({
	onCheck: function(node, checked){
	    $('#dealerId_search').combotree('tree').tree('expand',node.target);
	},
	onExpand:function(node){
	  $('#dealerId_search').combotree('tree').tree('check',node.target);
	}
	});	 