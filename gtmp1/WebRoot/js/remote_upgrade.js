//默认查询条件
var defQueryParams={'upgradeOJO.stamp':getTodayZero(),
		'upgradeOJO.stamp2':new Date().formatDate(timeFormat)};
//表格中的操作
function operate(val,row,index){
	var vehicleId = row.vehicleId;
	if(row.status!=9){
		var str1 =' <a style="color:blue;" href="javascript:void(0)" onclick="stopVehicle(\''+vehicleId+'\',9)">停用</a>';
	   return str1;
	}else{
		var str1 =' <a style="color:blue;" href="javascript:void(0)" onclick="stopVehicle(\''+vehicleId+'\',3)">恢复</a>';
	   return str1;
	}
	  
}

//查询
function queryUpgrade(){
	 var startTime= $('#start_time').val();
	 var endTime= $('#end_time').val();
	if(startTime&&endTime&&(startTime>=endTime)){
	   $.messager.alert('提示','结束时间必须在开始时间之后!');
	   return;
	}
	var t = $('#dealerId').combotree('tree');
	  var nodes = t.tree('getChecked');
    var idList=[];
    for(var i=0;i<nodes.length;i++){
        if(!idList.contains(nodes[i].id)){
  	    idList.push(nodes[i].id);
        }
    }
	 	
       $('#upgrade_datagrid').datagrid('load',{    
			'upgradeOJO.vehicleDef': $('#vehicleDef_search').val(),    
			'upgradeOJO.softwareVersion': $('#software_search').val(),
			'upgradeOJO.softwareVersion': $('#software_search').val(),
			'upgradeOJO.upgradeState': $('#upgradeState_search').combobox('getValue'),
			'upgradeOJO.deviceType': $('#deviceType_search').combobox('getValue'),
			'upgradeOJO.result': $('#result_search').combobox('getValue'),
			'upgradeOJO.pathFlag':$('#pathFlag_search').combobox('getValue'),
			'upgradeOJO.stamp': startTime,
			'upgradeOJO.stamp2':endTime,
			'upgradeOJO.supperierSn':idList.join(',')
	    });  
}
//停用车台
function stopVehicle(vehicleId,status){
	var statusStr='停用';
	if(status==3){//恢复
		statusStr='恢复';
	}
	$.messager.confirm(tipMsgDilag,'确定'+statusStr+'该设备?',
							   function(r){  
								    if (r){ 
          $.post("vehicle/vehicle_update.action", {
        	  vehicleId:vehicleId,
        	  status:status,
        	  userName:$('#span_user_name').text()
				}, function(data) {
					var r = $.parseJSON(data);
						if (r.success) {
							$('#upgrade_datagrid').datagrid('reload');
						}
						$.messager.alert(tipMsgDilag, r.msg);
				});
								    }});
}
//机型类型和机械型号联动
function typeSelect(rec){
                    var url = 'vehicle/vehicleModel_getList.action?typeId=' + rec.typeId;
                    $('#modelName_search').combobox('reload', url);
}