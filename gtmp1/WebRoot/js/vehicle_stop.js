//表格中的操作
function operate(val, row, index) {
	var str = "<a style=\"color:blue;\" href=\"javascript:void(0)\" onclick=\"$('#vehicleId').val('" + row.vehicleId + "');$('#unitId').val('" + row.unitId + "');$('#status').combobox('setValue','" + row.status + "');$('#dlg_vehicle_operate').dialog('open')\">转组</a>";
	str += "&nbsp;<a style=\"color:blue;\" href=\"javascript:void(0)\" onclick=\"$('#vId').val('" + row.vehicleId + "');$('#leaseFlag').combobox('setValue','" + row.leaseFlag + "');$('#dlg_leasehold_operate').dialog('open')\">融资租赁权限</a>";
	return str;
}

//查询
function queryVehicle() {
	$("#vehicle_datagrid").datagrid("load", {vehicleDef:$("#vehicleDef_search").val(), dealerId:$('#dealerId_search').combobox('getValues').join(','),status:$('#status_search').combobox('getValue') == '全部' ? '' : $('#status_search').combobox('getValue'), modelName:$("#modelName_search").combobox("getValue"), unitSn:$("#unitSn_search").val(), simNo:$("#simNo_search").val(), leaseFlag:$('#leaseFlag_search').combobox("getValue")});
}

// 融资租赁权限
function leaseHold() {
	var vehicleId = $('#vId').val();
	var leaseFlag = $('#leaseFlag').combobox('getValue');
	
	$.post("vehicle/vehicle_updateStatus.action", {vehicleId:vehicleId, leaseFlag:leaseFlag, userName:$("#span_user_name").text()}, function (data) {
		var r = $.parseJSON(data);
		if (r.success) {
			$('#dlg_leasehold_operate').dialog('close');
			$("#vehicle_datagrid").datagrid("reload");
		}
		$.messager.alert(tipMsgDilag, r.msg);
	});
}

// 机械转组
function vehicleTransfer() {
	var vehicleId = $('#vehicleId').val();
	var dealerId = $('#dealerId_search').combobox('getValues').join(',');
	var unitId = $('#unitId').val();
	var status = $('#status').combobox('getValue');

	if (status == 9) { //机械停用时，如果近期终端有位置信息上报，需要给出提示，默认10天
		$.ajax({async:false, type:"POST", data:{unitId:unitId}, url:"vehicle/vehicle_selectLastPosition.action",
			error : function(jqXMLRequest, textStatus, errorThrown){
				   $.messager.alert(tipMsgDilag, "错误类型：" + textStatus + "<br />错误信息：" + errorThrown, 'error');
			   }, success:function (result) {
			if (result) {
				var r = $.parseJSON(result);
				if (r && r.tLastPosition && r.tLastPosition.stamp) {
					var today = new Date();
					if (getDayNumber(today.formatDate("yyyy-MM-dd"), r.tLastPosition.stamp) < 10) {
						$.messager.confirm(tipMsgDilag, "该设备最近10天有位置信息上报，确定停用？", function (r) {
							if (r) {
								updateStatus(vehicleId, status);
							}
						});
					} else {
						updateStatus(vehicleId, status);
					}
				} else {
					updateStatus(vehicleId, status);
				}
			} else {
				updateStatus(vehicleId, status);
			}
		}});
	} else {
		updateStatus(vehicleId, status);
	}
}

function typeSelect(rec) {
	var url = "vehicle/vehicleModel_getList.action?typeId=" + rec.typeId;
	$("#modelName_search").combobox("reload", url);
}
function updateStatus(vehicleId, status) {
	$.post("vehicle/vehicle_updateStatus.action", {vehicleId:vehicleId, status:status}, function (data) {
		var r = $.parseJSON(data);
		if (r.success) {
			$('#dlg_vehicle_operate').dialog('close');
			$("#vehicle_datagrid").datagrid("reload");
		}
		$.messager.alert(tipMsgDilag, r.msg);
	});
}
$('#dealerId_search').combotree({
	onCheck: function(node, checked){
	    $('#dealerId_search').combotree('tree').tree('expand',node.target);
	},
	onExpand:function(node){
	  $('#dealerId_search').combotree('tree').tree('check',node.target);
	}
});
