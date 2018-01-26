//默认查询条件
var defQueryParams={'startTime':getTodayZero(),
		'endTime':new Date().formatDate(timeFormat)};
//窗口大小变化
function alarmQueryResize(w,h){
	if($('#alarm_query_datagrid')){
		try{
		  $('#alarm_query_datagrid').datagrid('options');
		  $('#alarm_query_datagrid').datagrid('resize', {  
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
/*	 var dealerId ='';
     if($('#areaName_search').combobox('getValue') != ''){
         dealerId = $('#areaName_search').combobox('getValue')+','+$('#dealer_search').combobox('getValue');
     }*/
	$('#alarm_query_datagrid').datagrid('load',{
	   alarmStatus:$('#alarmStatus_search').combobox('getValue'),
	   vehicleDef:$('#vehicleDef_search').val(),
	   dealerId: $('#dealerId_search').combobox('getValues').join(','),
	   vehicleModel: $('#vehicleModel_search').combobox('getValue'),
	   vehicleStatus: $('#vehicleStatus_search').combobox('getValue') == '全部' ? '' : $('#vehicleStatus_search').combobox('getValue'),
	   vehicleCode: $('#vehicleCode_search').combobox('getValue') == '全部' ? '' : $('#vehicleCode_search').combobox('getValue'),
	   vehicleArg: $('#vehicleArg_search').combobox('getValue') == '全部' ? '' : $('#vehicleArg_search').combobox('getValue'),
	   alarmTypeIds:$('#alarmType_alarm').combobox('getValues').join(','),
	   alarmTypeGenre: $('#alarm_big_type_search').combobox('getValue') == '全部' ? '' : $('#alarm_big_type_search').combobox('getValue'),
	   startTime:startTime,
	   endTime:endTime
	});
}

//导出
function downExcel(){
     var startTime= $('#start_time').val();
	 var endTime= $('#end_time').val();
	 if(startTime&&endTime&&(startTime>=endTime)){
	     $.messager.alert('提示','结束时间必须在开始时间之后!');
	     return;
	 }
	 var dealerId = $('#dealerId_search').combobox('getValues').join(',');
	 var vehicleModel = $('#vehicleModel_search').combobox('getValue');
	 var vehicleStatus = $('#vehicleStatus_search').combobox('getValue')== '全部' ? '' : $('#vehicleStatus_search').combobox('getValue');
	 var vehicleCode = $('#vehicleCode_search').combobox('getValue') == '全部' ? '' : $('#vehicleCode_search').combobox('getValue');
	 var vehicleArg = $('#vehicleArg_search').combobox('getValue') == '全部' ? '' : $('#vehicleArg_search').combobox('getValue');
	 var alarmTypeGenre= $('#alarm_big_type_search').combobox('getValue') == '全部' ? '' : $('#alarm_big_type_search').combobox('getValue');

	 window.location.href=encodeURI(encodeURI("run/alarm_exportToExcel.action?dealerId="+dealerId+"&vehicleStatus"+vehicleStatus+"&vehicleModel="+vehicleModel+"&vehicleCode="+vehicleCode+"&vehicleArg="+vehicleArg+"&alarmStatus="+$('#alarmStatus_search').combobox('getValue')+"&vehicleDef="+$('#vehicleDef_search').val()+"&referencePosition="+$('#alarmType_alarm').combobox('getValues').join(',')+"&rawData="+startTime+"&unitId="+endTime+"&startTime="+startTime+"&endTime="+endTime +"&alarmTypeGenre="+alarmTypeGenre));
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
//经销商联动
$('#areaName_search').combobox({
onSelect:function(){
var pid = $('#areaName_search').combobox('getValue');
$("#dealer_search").empty();
if(pid != ""){    
    $.post("vehicle/vehicleModel_getAreaOrDealer.action?dealerAreaPOJO.pid="+pid+"&dealerAreaPOJO.dtype=2", {},
    function(data) {
            var obj = $.parseJSON(data);
            $('#dealer_search').combobox({
               data : obj,
               valueField:'id',
               textField:'name',
               onLoadSuccess: function () { //加载完成后,设置选中第一项
                var val = $(this).combobox("getData");
                for (var item in val[0]) {
                    if (item == "id") {
                        $(this).combobox("select", val[0][item]);
                    }
                }
            }
            });
        });
}else{
    $("#dealer_search").combobox('clear');
}
}
});	      	 
	      	 
function formmatAlarmGenre(val,row){
	if(val==1){
		return 'GPS警情';
		
	}
	else if(val==2){
		return '挖机警情';
	}
	else
		return '';
}

$('#dealerId_search').combotree({
	onCheck: function(node, checked){
	    $('#dealerId_search').combotree('tree').tree('expand',node.target);
	},
	onExpand:function(node){
	  $('#dealerId_search').combotree('tree').tree('check',node.target);
	}
});