//窗口大小变化
function unWorkResize(w,h){
	if($('#unwork_query_datagrid')){
		try{
		  $('#unwork_query_datagrid').datagrid('options');
		  $('#unwork_query_datagrid').datagrid('resize', {  
				width : w-2 ,
				height:h
		 }); 
		}catch(e){
		}
	}
}
//查询
function queryUnwork(){
	if(!$('#days_search').combobox('getValue')){
	      $.messager.alert('提示','请选择未工作的天数!!');
		   return;
	}
	var param={
	   direction:$('#days_search').combobox('getValue'),
	   vehicleDef:$('#vehicleDef_search').val(),
	   dealerId:$('#dealerId_search').combobox('getValues').join(','),
	   modelId: $('#vehicleModel_search').combobox('getValue'),
	   vehicleStatus:$('#vehicleStatus_search').combobox('getValue') == '全部' ? '' : $('#vehicleStatus_search').combobox('getValue'),
	   vehicleCode: $('#vehicleCode_search').combobox('getValue') == '全部' ? '' : $('#vehicleCode_search').combobox('getValue'),
	   vehicleArg: $('#vehicleArg_search').combobox('getValue') == '全部' ? '' : $('#vehicleArg_search').combobox('getValue')
	};
		try{
        			if($('#unwork_query_datagrid').datagrid('options').url){
        			  $('#unwork_query_datagrid').datagrid('load', param);
        			}else{
        			  $('#unwork_query_datagrid').datagrid({
		        		  url:'report/statistic_getUnWorkByPage.action',
		        		  queryParams:param
		        		});
        			}
        		}catch(e){
        			//初始化在线treegrid
        		$('#unwork_query_datagrid').datagrid({
        		  url:'report/statistic_getUnWorkByPage.action',
        		  queryParams:param
        		});
        		}
	
}
//导出
function downExcel(){
	var vehicleModel = $('#vehicleModel_search').combobox('getValue');
	var vehicleStatus = $('#vehicleStatus_search').combobox('getValue')== '全部' ? '' : $('#vehicleStatus_search').combobox('getValue');
	var vehicleCode = $('#vehicleCode_search').combobox('getValue') == '全部' ? '' : $('#vehicleCode_search').combobox('getValue');
	var vehicleArg = $('#vehicleArg_search').combobox('getValue') == '全部' ? '' : $('#vehicleArg_search').combobox('getValue');
	window.location.href=encodeURI(encodeURI("report/statistic_exportToExcelUnWork.action?modelId="+vehicleModel+"&vehicleStatus"+vehicleStatus+"&vehicleCode="+vehicleCode+"&vehicleArg="+vehicleArg+"&direction="+$('#days_search').combobox('getValue')+"&vehicleDef="+$('#vehicleDef_search').val()+"&dealerId="+$('#dealerId_search').combobox('getValues').join(',')));
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

