var refreshAlarmTime = 60000;// 刷新的警情间隔时间 60秒
var isSetReaded=false;//是否执行过设置警情已读
// 初始化
$(document).ready(function() {
	// 判断是否是终端供应商，如果不是才需要刷新警情
	$.post('permi/user_isSupperier.action', {
		
	}, function(result) {
		try {
			var r = $.parseJSON(result);
			if (!r.success) {
				// 刷新警情
				refreshAlarmInfo();
			}

		} catch (e) {
			// $.messager.alert(tipMsgDilag, '失败!');
		}
	});
});
// 警情：声音播放
function playSound() {
	document.getElementById('embed_alarm').play();
}

// 定时刷新警情 60秒
function refreshAlarmInfo() {
	// 取出未读数据
	$.post('run/alarm_getNonReadAlarmCount.action',
					{
						alarmStatus : '01',
						isRead:1
					},
					function(result) {
						try {
							var r = $.parseJSON(result);
							var total = r.total;// 总条数
							var obj = r.result;// 警情信息
							if (total > 0) {
								try {
									$('#dlg_alarm').dialog("options");
									if ($('#dlg_alarm').dialog("options").closed) {
										// 声音提醒
										playSound();

										// 弹出框提醒
										$.messager
												.show({
													title : '警情消息',
													msg : '您好：整机编号为【'
															+ obj.vehicleDef
															+ '】上报【'
															+ obj.alarmTypeName
															+ '】的消息，请<a href="javascript:alarmopen(\''
															+ obj.alarm_id
															+ '\');" style="font-weight:bold;font-size:14px;">查看</a>!',
															timeout : 59000,
															showType : 'slide'
												});
									}
								} catch (e) {
									// 声音提醒
									playSound();

									// 弹出框提醒
									$.messager
											.show({
												title : '警情消息',
												msg : '您好：整机编号为【'
														+ obj.vehicleDef
														+ '】上报【'
														+ obj.alarmTypeName
														+ '】的消息，请<a href="javascript:alarmopen(\''
														+ obj.alarm_id
														+ '\');" style="font-weight:bold;font-size:14px;">查看</a>!',
												timeout : 59000,
												showType : 'slide'
											});
								}
							}
						} catch (e) {
							// $.messager.alert(tipMsgDilag, '失败!');
						}
					});
	// 表格中加一条删一条
	setTimeout(refreshAlarmInfo, refreshAlarmTime);
}
// 修改阅读状态
function setAlreadyRead() {
	var nodes = $('#alarm_datagrid').datagrid('getChecked');
	var idList = [];
	for ( var i = 0; i < nodes.length; i++) {
		idList.push(nodes[i].alarmId);
	}
	if (!idList || nodes.length < 1) {
		$.messager.alert('消息提示', '请选择报警信息!', 'info');
		return;
	}
	$.post('run/alarm_updateAlarm.action', jQuery.param({
		idList : idList
	}, true), function(result) {
		try {
			var r = $.parseJSON(result);
			if (r.success) {
				isSetReaded=true;
				$('#alarm_datagrid').datagrid('reload');
				$('#alarm_datagrid').datagrid("uncheckAll");
				$.messager.alert('提示', r.msg);
			}

		} catch (e) {
			$.messager.alert('提示', e);
		}
	});

}

function alarmopen(id) {
	// 警情类型
	$('#alarmType_alarm').combobox({
		url : 'run/alarm_getAlarmType.action',
		valueField : 'alarmTypeId',
		textField : 'alarmTypeName',
		multiple : true
	});

	$('#alarm_datagrid').datagrid({
		iconCls : 'icon-ok',
		width : 'auto',
		height : '265',
		pageSize : 10,
		url : 'run/alarm_getAlarmInfo.action',
		queryParams : {
			alarmStatus : '01',
			isRead : 1
		},
		idField : 'unit_id',
		singleSelect : false,
		columns : [ [ {
			field : 'ck',
			checkbox : true,
			align : 'center'
		}, {
			title : '设备名称',
			field : 'vehicleDef',
			width : '120',
			align : 'center'
		}, {
			title : '报警类型',
			field : 'alarmTypeName',
			width : '120',
			align : 'center'
		}, {
			title : '报警时间',
			field : 'stamp',
			width : '150',
			align : 'center'
		}, {
			title : '经度',
			field : 'lon',
			width : '100',
			align : 'center'
		}, {
			title : '纬度',
			field : 'lat',
			width : '100',
			align : 'center'
		}, {
			title : '速度',
			field : 'speed',
			width : '150',
			align : 'center'
		}, {
			title : '参考位置',
			field : 'referencePosition',
			width : '260',
			align : 'left'
		} ] ],
		pagination : true,
		rownumbers : true,
		toolbar : '#alarm_toobar',
		/*
		 * onClickRow:function(rowIndex, rowData){ //加在地图上 if(typeof
		 * mapbar.window.addMapDataOfAlarm == "function"){
		 * mapbar.window.addMapDataOfAlarm(rowData); } },
		 */
		onSelect : function(rowIndex, rowData) {
			/*
			 * //加在地图上 if(typeof mapbar.window.addMapDataOfAlarm == "function"){
			 * mapbar.window.addMapDataOfAlarm(rowData); }
			 */
		},
		onUnselect : function(rowIndex, rowData) {
			/*
			 * //从地图上删除此标注 if(typeof mapbar.window.removeMapDataOfAlarm ==
			 * "function"){ mapbar.window.removeMapDataOfAlarm(rowData); }
			 */
		},
		onCheckAll : function(rows) {
			/*
			 * //加在地图上 if(typeof mapbar.window.addAllMapDataOfAlarm ==
			 * "function"){ mapbar.window.addAllMapDataOfAlarm(rows); }
			 */
		},
		onUnCheckAll : function(rows) {
		},
		onSselectAll : function(rows) {
		},
		onUnselectAll : function(rows) {
			/*
			 * //从地图上删除此标注 if(typeof mapbar.window.removeAllMapDataOfAlarm ==
			 * "function"){ mapbar.window.removeAllMapDataOfAlarm(rows); }
			 */
		},
		onLoadSuccess : function(data) {
			//如果运营监管页面打开了，则刷新警情数量
			if($('#alarm_run_btn')&&isSetReaded){
				var data=$('#alarm_datagrid').datagrid('getData');
				$('#alarm_run_btn').val("警情("+data.total+")");
			}
		}
	});
	$('#dlg_alarm').dialog({
		title : "警情信息",
		// href:'jsp/alarm_view.jsp',
		onClose : function() {
			/*
			 * //加在地图上 if(typeof mapbar.window.removeMapDatasOfAlarm ==
			 * "function"){ mapbar.window.removeMapDatasOfAlarm(); }
			 */
		}
	});
	var cwid = $(document.body).width();
	var chei = $(document.body).height();
	var wid = $('#dlg_alarm').panel('options').width;
	var hei = $('#dlg_alarm').panel('options').height;
	if(id){//全局页面的弹出框
		$('#dlg_alarm').dialog('move', {
			left : cwid - wid,
			top : chei - hei
		});
	}else{//运营页面的弹出框
		$('#dlg_alarm').dialog('move', {
			left : cwid/2- wid/2,
			top : chei/2-hei/2
		});
	}
	
	$('#dlg_alarm').dialog('open');
	isSetReaded=false;
}
// 查询警情
function queryAlarm() {
	isSetReaded=false;
	$('#alarm_datagrid').datagrid(
			'load',
			{
				alarmStatus : '01',
				isRead:1,
				vehicleDef : $('#vehicleDef_alarm').val(),
				alarmTypeIds : $('#alarmType_alarm').combobox('getValues')
						.join(',')
			});
}
//导出
function downExcel(){  
	var page = $('#alarm_datagrid').datagrid('options').pageNumber;
	var rows = $('#alarm_datagrid').datagrid('options').pageSize;
	 var vehicleDef = $('#vehicleDef_alarm').val();
	 var alarmType_alarm = $('#alarmType_alarm').combobox('getValues').join(',');	 
	 window.location.href=encodeURI(encodeURI("run/alarm_indexToExcel.action?vehicleDef="+vehicleDef+"&referencePosition="+alarmType_alarm+"&page="+page+"&rows="+rows));
	 return;
}
