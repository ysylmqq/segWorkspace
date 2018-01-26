//查询
function queryReplaceLog() {
	$("#unitreplace_datagrid").datagrid("load", {vehicleDef:$("#vehicleDef_search").val()});
}

//导出
  function downExcel(){
  	 window.location.href=encodeURI(encodeURI("run/unitreplace_exportToExcel.action?vehicleDef="+$('#vehicleDef_search').val()));
  	 return;
  }
//打开终端换装窗口
function openReplaceLog() {
	$('#operate_form').form('clear');
	$("#dlg_operate").dialog("open");
}

//查询机械
function queryVehicleInDlg() {
	$("#vehicleDef").combogrid("grid").datagrid("load", {vehicleDef:$("#vehicledef_search").val()});
}

//查询终端
function queryUnitInDlg() {
	$("#vehicleDef").combogrid("grid").datagrid("load", {unitSn:$("#unitsn_search").val()});
}

//终端换装
function unitReplace() {
	if (!$("#operate_form").form("validate")) {
		return;
	}
	$.ajax({
		url:"run/unitreplace_unitReplace.action",
		type: "POST",
		data: {
			vehicleId: $('#vehicleId').val(),
			vehicleDef: $('#vehicleDef').combogrid('getValue'),
			oldUnitId: $('#oldUnitId').val(),
			oldUnitSn: $('#oldUnitSn').val(),
			newUnitId: $('#newUnitSn').combogrid('getValue'),
			reason: $('#reason').val(),
			replaceMan: $('#replaceMan').val(),
			replaceTime: $('#replaceTime').val()
		},
		success: function (result) {
			var r = $.parseJSON(result);
			$.messager.alert(tipMsgDilag, r.msg);
			$("#dlg_operate").dialog("close");
			$("#unitreplace_datagrid").datagrid("reload");
		},
		error : function(jqXMLRequest, textStatus, errorThrown){
		   $.messager.alert(tipMsgDilag, "错误类型：" + textStatus + "<br />错误信息：" + errorThrown, 'error');
	   }
	});
}

//窗口大小变化
function unitReplaceResize(w, h) {
	if ($("#unitreplace_datagrid")) {
		try {
			$("#unitreplace_datagrid").datagrid("options");
			$("#unitreplace_datagrid").datagrid("resize", {width:w - 2, height:h});
		}
		catch (e) {}
	}
}
function initParam() {
	var row = $("#vehicleDef").combogrid("grid").datagrid("getSelected");
	$('#vehicleId').val(row.vehicleId);
	$('#oldUnitId').val(row.unitId);
	$("#oldUnitSn").val(row.unitSn);
}
