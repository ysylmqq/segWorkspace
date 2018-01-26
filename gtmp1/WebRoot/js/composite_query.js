var positionData = null;// 定位数据
var workInfData = null;// 工况数据
// 窗口大小变化
function compositeMainResize(w, h) {
	if ($('#composite_datagrid')) {
		try {
			$('#composite_datagrid').datagrid('options');
			$('#composite_datagrid').datagrid('resize', {
				width : w - 4,
				height : h
			});
		} catch (e) {
		}
	}
}

// 显示列过滤窗口打开
function openColumnsFilterWin() {
	var grid = $('#composite_datagrid');
	var hasFields = $('#composite_datagrid').datagrid('getColumnFields');// 已经有的列
	
	//从数据库中获得已有的列
	$.ajax({
		url: 'run/alarm_getUserAlarmTypes.action',
		type: 'get',
		success: function(data) {
			var showColumns = data.showColumns;
			
			// 终端信息
			var columns_unit = [];
			columns_unit.push(['supperierName', '供应商编号']);
			columns_unit.push(['materialNo', '物料编号']);
			columns_unit.push(['simNo', 'SIM卡号']);
			columns_unit.push(['steelNo', '钢号']);
			columns_unit.push(['unitType', '终端类型']);
			var div_chk_units = document.getElementById("div_chk_units");// $("#div_chk_units");
			div_chk_units.innerHTML = "";
			var ul = document.createElement("ul");
			if (columns_unit.length > 0) {
				$.each(columns_unit, function(i, n) {
					var obj = eval(n);
					// 加入复选框
					var checkBox = document.createElement("input");
					checkBox.setAttribute("type", "checkbox");
					checkBox.setAttribute("id", obj[0]);
					checkBox.setAttribute("value", obj[0]);
					checkBox.setAttribute("name", "CkbShowColumns");
					checkBox.setAttribute("checked", "checked");
					// checkBox.setAttribute("onclick", "getAlamryTypes()");
					var li = document.createElement("li");
					li.appendChild(checkBox);
					// checkBox.checked = true;
					if(showColumns){//如果cookie中有值
					   if(showColumns.contains(obj[0])){
					    	checkBox.checked = true;
						}else{
						    checkBox.checked = false;
						}
					}else{//如果禁用了cookie
						if (hasFields.contains(obj[0])) {
							if (grid.datagrid("getColumnOption", obj[0]).hidden) {
								checkBox.checked = false;
							} else {
								checkBox.checked = true;
							}
						} else {
							checkBox.checked = true;
						}
					}
					var label = document.createElement('label');
					label.setAttribute('for', obj[0]);
					label.innerHTML = obj[1];
					li.appendChild(label);
					ul.appendChild(li);
				});
			}
			div_chk_units.appendChild(ul);
			
			// 机械信息
			var columns_vehicle = [];
			columns_vehicle.push(['typeName', '机械型号']);
			columns_vehicle.push(['modelName', '机械类型']);
			columns_vehicle.push(['fixMan', '安装人']);
			columns_vehicle.push(['fixDate', '终端安装时间']);
			columns_vehicle.push(['saleDate', '销售日期']);
			columns_vehicle.push(['dealerName', '经销商']);
			columns_vehicle.push(['ownerName', '机主名称']);
			columns_vehicle.push(['ownerPhone', '机主电话']);
			var div_chk_vehicles = document.getElementById("div_chk_vehicles");// $("#div_chk_vehicles");
			div_chk_vehicles.innerHTML = "";
			var ul = document.createElement("ul");
			if (columns_vehicle.length > 0) {
				$.each(columns_vehicle, function(i, n) {
					var obj = eval(n);
					// 加入复选框
					var checkBox = document.createElement("input");
					checkBox.setAttribute("type", "checkbox");
					checkBox.setAttribute("id", obj[0]);
					checkBox.setAttribute("value", obj[0]);
					checkBox.setAttribute("name", "CkbShowColumns");
					checkBox.setAttribute("checked", "checked");
					var li = document.createElement("li");
					li.appendChild(checkBox);
					if(showColumns){ // 如果数据库中有值
					   if(showColumns.contains(obj[0])){
					    	checkBox.checked = true;
						}else{
						    checkBox.checked = false;
						}
					} else { // 如果数据库中没有值
						if (hasFields.contains(obj[0])) {
							if (grid.datagrid("getColumnOption", obj[0]).hidden) {
								checkBox.checked = false;
							} else {
								checkBox.checked = true;
							}
						} else {
							checkBox.checked = true;
						}
					}
					var label = document.createElement('label');
					label.setAttribute('for', obj[0]);
					label.innerHTML = obj[1];
					li.appendChild(label);
					ul.appendChild(li);
				});

			}
			div_chk_vehicles.appendChild(ul);
			
			// gps信息
			var columns_gps = [];
			columns_gps.push(['lon', '经度']);
			columns_gps.push(['lat', '纬度']);
			columns_gps.push(['speed', '速度']);
			columns_gps.push(['direction', '方向']);
			columns_gps.push(['gpsTime', 'GPS定位时间']);
			columns_gps.push(['vehicleState', '机械状态']);
			columns_gps.push(['province', '省']);
			columns_gps.push(['city', '市']);
			columns_gps.push(['county', '县']);
			columns_gps.push(['referencePosition', '参考位置']);
			columns_gps.push(['locationState', '是否定位']);
			columns_gps.push(['isLogin', '是否在线']);
			var div_chk_gps = document.getElementById("div_chk_gps");// $("#div_chk_gps");
			div_chk_gps.innerHTML = "";
			var ul = document.createElement("ul");
			if (columns_gps.length > 0) {
				$.each(columns_gps, function(i, n) {
					var obj = eval(n);
					// 加入复选框
					var checkBox = document.createElement("input");
					checkBox.setAttribute("type", "checkbox");
					checkBox.setAttribute("id", obj[0]);
					checkBox.setAttribute("value", obj[0]);
					checkBox.setAttribute("name", "CkbShowColumns");
					checkBox.setAttribute("checked", "checked");
					var li = document.createElement("li");
					li.appendChild(checkBox);
					if(showColumns){ // 如果数据库中有值
					   if(showColumns.contains(obj[0])){
					    	checkBox.checked = true;
						}else{
						    checkBox.checked = false;
						}
					}else{ // 如果数据库中没有值
						if (hasFields.contains(obj[0])) {
							if (grid.datagrid("getColumnOption", obj[0]).hidden) {
								checkBox.checked = false;
							} else {
								checkBox.checked = true;
							}
						} else {
							checkBox.checked = true;
						}
					}
					var label = document.createElement('label');
					label.setAttribute('for', obj[0]);
					label.innerHTML = obj[1];
					li.appendChild(label);
					ul.appendChild(li);
				});
			}
			div_chk_gps.appendChild(ul);
			
			// 工况信息
			var columns_conditions = [];
			columns_conditions.push(['engineCoolantTemperature', '发动机冷却液温度']);
			columns_conditions.push(['engineCoolantLevel', '发动机冷却液液位']);
			columns_conditions.push(['engineOilPressure', '发动机机油压力']);
			columns_conditions.push(['engineFuelLevel', '发动机燃油油位']);
			columns_conditions.push(['engineSpeed', '发动机转速']);
			columns_conditions.push(['hydraulicOilTemperature', '液压油温度']);
			columns_conditions.push(['systemVoltage', '系统电压']);
			columns_conditions.push(['beforePumpMainPressure', '挖掘机前泵主压压力']);
			columns_conditions.push(['afterPumpMainPressure', '挖掘机后泵主压压力']);
			columns_conditions.push(['pilotPressure1', '挖掘机先导压力1']);
			columns_conditions.push(['pilotPressure2', '挖掘机先导压力2']);
			columns_conditions.push(['beforePumpPressure', '挖掘机前泵负压压力']);
			columns_conditions.push(['afterPumpPressure', '挖掘机后泵负压压力']);
		    columns_conditions.push(['proportionalCurrent1', '挖掘机比例阀电流1']);
			columns_conditions.push(['proportionalCurrent2', '挖掘机比例阀电流2']);
			columns_conditions.push(['gear', '挖机档位']);
			columns_conditions.push(['totalWorkingHours', '累计工作小时']);
		    columns_conditions.push(['highEngineCoolantTemperVal', '发动机冷却液温度高报警设置值']);
			columns_conditions.push(['lowEngineOilPressureAlarmValue', '发动机机油压力低报警设置值']);
		    columns_conditions.push(['highVoltageAlarmValue', '系统电压高报警设置值']);
			columns_conditions.push(['lowVoltageAlarmValue', '系统电压低报警设置值']);
			columns_conditions.push(['highHydraulicOilTemperAlarmVal', '液压油温高报警值']);
			columns_conditions.push(['toothNumberValue', '飞轮齿数设置值']);
		    columns_conditions.push(['monitorSoftwareCode', '监控器供应商软件代号']);
		    columns_conditions.push(['monitorYcSoftwareCode', '监控器玉柴软件版本号']);
			columns_conditions.push(['controllerSoftwareCode', '控制器供应商软件代号']);
			columns_conditions.push(['controllerYcSoftwareCode', '控制器玉柴软件代号']);
			columns_conditions.push(['engineOilTemperature', '发动机机油温度']);
		//	columns_conditions.push(['productCode', '产品编号']);
			columns_conditions.push(['faultCode', '故障码']);
			columns_conditions.push(['alock', 'A锁']);
			columns_conditions.push(['block', 'B锁']);
			columns_conditions.push(['clock', '使能状态']);
			columns_conditions.push(['isWork', '是否工作']);
			columns_conditions.push(['stamp', '上报工况时间']);
			var div_chk_conditions = document.getElementById("div_chk_conditions");
			div_chk_conditions.innerHTML = "";
			var ul = document.createElement("ul");
			if (columns_conditions.length > 0) {
				$.each(columns_conditions, function(i, n) {
					var obj = eval(n);
					// 加入复选框
					var checkBox = document.createElement("input");
					checkBox.setAttribute("type", "checkbox");
					checkBox.setAttribute("id", obj[0]);
					checkBox.setAttribute("value", obj[0]);
					checkBox.setAttribute("name", "CkbShowColumns");
					checkBox.setAttribute("checked", "checked");
					var li = document.createElement("li");
					li.appendChild(checkBox);
					if(showColumns){ // 如果数据库中有值
					   if(showColumns.contains(obj[0])){
					    	checkBox.checked = true;
						}else{
						    checkBox.checked = false;
						}
					}else{ //如果数据库中没有值
						if (hasFields.contains(obj[0])) {
							if (grid.datagrid("getColumnOption", obj[0]).hidden) {
								checkBox.checked = false;
							} else {
								checkBox.checked = true;
							}
						} else {
							checkBox.checked = true;
						}
					}
					var label = document.createElement('label');
					label.setAttribute('for', obj[0]);
					label.innerHTML = obj[1];
					li.appendChild(label);
					ul.appendChild(li);
				});
			}
			div_chk_conditions.appendChild(ul);//工况信息待定
		}
	});
	$('#dlg_composite_columns').dialog('open');
}

// 显示选中列
function getCheckedValues() {
	var alarmTypes = [];
	// 设置表格列显示或隐藏
	var grid = $('#composite_datagrid');
	var hasFields = $('#composite_datagrid').datagrid('getColumnFields');// 已经有的列
	//把要显示的列，存入数据库中
	var showColumnsArray=[];
	$("input[name='CkbShowColumns']").each(function() {
		if ($(this).attr("checked") == true || $(this).attr("checked") == "checked") {
			if (hasFields.contains($(this).val())) {
				grid.datagrid('showColumn', $(this).val());
				showColumnsArray.push($(this).val());
			}
		} else {
			if (hasFields.contains($(this).val())) {
				grid.datagrid('hideColumn', $(this).val());
			}
		}
	});
	$.ajax({
		 url: 'run/alarm_setUserAlarmTypes.action',
		 type: 'post',
		 data: {'userAlarmTypesPOJO.showColumns': showColumnsArray.join(',')},
		 dataType: 'json',
		 success: function(data) {
			 $.messager.alert(tipMsgDilag, '保存成功！');
			 $('#dlg_composite_columns').dialog('close');
		 }
	});
}

// 查询条件窗口打开
function openQueryConditionsWin() {
	$('#dlg_composite_query_conditions').dialog('open');
	// 初始化数据
	try {
		$('#supperierSn', $('#frm_composite_query_conditions')).combobox("options");
	} catch (e) {
		$('#supperierSn', $('#frm_composite_query_conditions')).combobox({});
	}
	try {
		$('#unitTypeSn', $('#frm_composite_query_conditions')).combobox("options");
	} catch (e) {
		$('#unitTypeSn', $('#frm_composite_query_conditions')).combobox({});
	}
	// 经销商列表
	$('#dealerId', $('#frm_composite_query_conditions')).combotree({
		url : 'run/run_getDealerAreas4Tree.action'
	});
	// 机械类型
	$('#typeId', $('#frm_composite_query_conditions')).combobox({
		url : "vehicle/vehicleType_getList.action",
		valueField : 'typeId',
		textField : 'typeName',
		onSelect : function(rec) {
			/*var url = 'vehicle/vehicleModel_getList.action?typeId='
					+ rec.typeId;
			$('#modelId').combobox('reload', url);*/
		}
	});
	
	//终端类型
	  $('#supperierSn').combobox({
	    onSelect: function(rec){
	    	       //级联关系
	               var url = 'sys/dicUnitType_getList.action?isValid=0&supperierSn=' + rec.supperierSn;
	               $('#unitTypeSn').combobox('reload', url);
	               $('#unitTypeSn').combobox('setValue','');    
	             }
	 });
}

// 表格定位
function position(value, row, index) {
	var obj = {
		vehicleDef : row.vehicleDef,
		unitId : row.unitId,
		lon : row.lon,
		lat : row.lat,
		speed : row.speed,
		vehicleState : row.vehicleState,
		province : row.province,
		city : row.city,
		county : row.county,
		referencePosition : row.referencePosition,
		ocationState : row.locationState
	};
	return '<img onclick=positionGPS(' + JSON.stringify(obj)
			+ ') style="cursor: pointer;" src="images/run/L9-8C.gif">';
}
// 在地图上定位
function positionGPS(data) {
	positionData = data;
	$('<div/>').dialog({
		// content:'sdf',
		content : '<iframe src="jsp/run/composite_map.jsp" id="map" name="map" frameborder="0" style="border:0;width:100%;height:99%;"></iframe>',
		width : 800,
		height : 500,
		modal : true,
		maximizable : true,
		cache : false,
		title : '定位',
		buttons : [{
			text : '关闭',
			iconCls : 'icon-cancel',
			handler : function() {
				var d = $(this).closest('.window-body');
				d.dialog('destroy');
			}
		}],
		onLoad : function() {
			/*
			 * //加在地图上 if(typeof map.window.addPositionMarker == "function"){
			 * //map.window.addPositionMarker(data.lon,data.lat,data.vehicleDef);
			 * map.window.addPositionMarker(112.897095,28.227175,'常州A001'); }
			 */
		},
		onOpen : function() {
		},
		onClose : function() {
			$(this).dialog('destroy');
		},
		onResize : function(w, h) {
			var doc = document.getElementById('map').contentWindow.document;
			$('body', doc).css({'width': w-14, 'height': h-73});
		}
	});

}

// 查询
function queryComposite() {
	// $("#frm_composite_query_conditions").serialize()
	var t = $('#dealerId').combotree('tree');
	var nodes = t.tree('getChecked');
	var idList = [];
	for (var i = 0; i < nodes.length; i++) {
		if (!idList.contains(nodes[i].id)) {
			idList.push(nodes[i].id);
		}
	}
	var url='run/run_compositeQuery.action';
	var queryParams = {
			'condition.unitSn' : $('#unitSn', $("#frm_composite_query_conditions")).val(),
			'condition.materialNo' : $('#materialNo', $("#frm_composite_query_conditions")).val(),
			'condition.simNo' : $('#simNo', $("#frm_composite_query_conditions")).val(),
					
			'condition.steelNo' : $('#steelNo', $("#frm_composite_query_conditions")).val(),
			'condition.supperierSn' : $('#supperierSn', $('#frm_composite_query_conditions')).combobox('getValue'),
			'condition.unitTypeSn' : $('#unitTypeSn', $('#frm_composite_query_conditions')).combobox('getValue'),
					
			'condition.vehicleDef' : $('#vehicleDef', $("#frm_composite_query_conditions")).val(),
			'condition.typeId' : $('#typeId', $('#frm_composite_query_conditions')).combobox('getValue'),
			'condition.modelId' : $('#modelId', $('#frm_composite_query_conditions')).combobox('getValue'),
			
			'condition.status' : $('#status', $('#frm_composite_query_conditions')).combobox('getValue'),
			'condition.fixDate' : $('#fixDate', $("#frm_composite_query_conditions")).val(),
			'condition.fixDate2' : $('#fixDate2', $("#frm_composite_query_conditions")).val(),
			
			'condition.dealerIds2' : idList.join(","),
			'condition.saleDate' : $('#saleDate', $("#frm_composite_query_conditions")).val(),
			'condition.saleDate2' : $('#saleDate2', $("#frm_composite_query_conditions")).val(),
			
			'condition.ownerName' : $('#ownerName', $("#frm_composite_query_conditions")).val(),
			'condition.gpsTime' : $('#gpsTime', $("#frm_composite_query_conditions")).val(),
			'condition.gpsTime2' : $('#gpsTime2', $("#frm_composite_query_conditions")).val(),
			
			'condition.speed' : $('#speed', $("#frm_composite_query_conditions")).val(),
			'condition.speed2' : $('#speed2', $("#frm_composite_query_conditions")).val(),
			'condition.isLogin' : $('#isLogin', $("#frm_composite_query_conditions")).combobox('getValue'),
			'condition.isWork' : $('#isWork', $("#frm_composite_query_conditions")).combobox('getValue'),
			
			'condition.totalWorkingHours' : $('#totalWorkingHours', $("#frm_composite_query_conditions")).val(),
			'condition.totalWorkingHours2' : $('#totalWorkingHours2', $("#frm_composite_query_conditions")).val(),
			'condition.province' : $('#province', $("#frm_composite_query_conditions")).combobox('getValue'),
			'condition.city' : $('#city', $("#frm_composite_query_conditions")).combobox('getValue'),
			
			'condition.lock' : $('#lock', $("#frm_composite_query_conditions")).combobox('getValue'),
			'condition.alarmFlag' : $('#alarmFlag', $("#frm_composite_query_conditions")).combobox('getValue'),
			'condition.daysUnuplad' : $('#daysUnuplad', $("#frm_composite_query_conditions")).combobox('getValue'),
			'condition.daysUnwork' : $('#daysUnwork', $("#frm_composite_query_conditions")).combobox('getValue')
		};
	
	try {
		 var opt=$('#composite_datagrid').datagrid("options");
		 $('#composite_datagrid').datagrid('load',queryParams);
	} catch (e) {
		$('#composite_datagrid').datagrid('load',queryParams);
	}
	$('#dlg_composite_query_conditions').dialog('close');
}
//导出
function downExcel(){
	var idList = [];
	var params = "";
	try {
		var t = $('#dealerId').combotree('tree');
		var nodes = t.tree('getChecked');
		
		for (var i = 0; i < nodes.length; i++) {
			if (!idList.contains(nodes[i].id)) {
				idList.push(nodes[i].id);
			}
		}
		var unitSn = $('#unitSn', $("#frm_composite_query_conditions")).val();
		var materialNo = $('#materialNo', $("#frm_composite_query_conditions")).val();
		var simNo = $('#simNo', $("#frm_composite_query_conditions")).val();
		var steelNo = $('#steelNo', $("#frm_composite_query_conditions")).val();
		var supperierSn = $('#supperierSn', $('#frm_composite_query_conditions')).combobox('getValue');
		var unitTypeSn = $('#unitTypeSn', $('#frm_composite_query_conditions')).combobox('getValue');
		var vehicleDef = $('#vehicleDef', $("#frm_composite_query_conditions")).val();
		var typeId = $('#typeId', $('#frm_composite_query_conditions')).combobox('getValue');
		var modelId = $('#modelId', $('#frm_composite_query_conditions')).combobox('getValue');
		var status = $('#status', $('#frm_composite_query_conditions')).combobox('getValue');
		var fixDate = $('#fixDate', $("#frm_composite_query_conditions")).val();
		var fixDate2 = $('#fixDate2', $("#frm_composite_query_conditions")).val(); 
		var dealerIds2 = idList.join(",");
		var saleDate = $('#saleDate', $("#frm_composite_query_conditions")).val();
		var saleDate2 = $('#saleDate2', $("#frm_composite_query_conditions")).val(); 
		var ownerName = $('#ownerName', $("#frm_composite_query_conditions")).val();
		var gpsTime = $('#gpsTime', $("#frm_composite_query_conditions")).val();
		var gpsTime2 = $('#gpsTime2', $("#frm_composite_query_conditions")).val(); 
		var speed = $('#speed', $("#frm_composite_query_conditions")).val();
		var speed2 = $('#speed2', $("#frm_composite_query_conditions")).val();
		var isLogin = $('#isLogin', $("#frm_composite_query_conditions")).combobox('getValue');
		var isWork = $('#isWork', $("#frm_composite_query_conditions")).combobox('getValue'); 
		var totalWorkingHours = $('#totalWorkingHours', $("#frm_composite_query_conditions")).val();
		var totalWorkingHours2 = $('#totalWorkingHours2', $("#frm_composite_query_conditions")).val();
		var province = $('#province', $("#frm_composite_query_conditions")).combobox('getValue');
		var city = $('#city', $("#frm_composite_query_conditions")).combobox('getValue'); 
		var lock = $('#lock', $("#frm_composite_query_conditions")).combobox('getValue');
		var alarmFlag = $('#alarmFlag', $("#frm_composite_query_conditions")).combobox('getValue');
		var daysUnuplad = $('#daysUnuplad', $("#frm_composite_query_conditions")).combobox('getValue');
		var daysUnwork = $('#daysUnwork', $("#frm_composite_query_conditions")).combobox('getValue')
		params = "?condition.unitSn="+unitSn+
		"&condition.materialNo="+materialNo+
		"&condition.simNo="+simNo+
		"&condition.steelNo="+steelNo+
		"&condition.supperierSn="+supperierSn+
		"&condition.unitTypeSn="+unitTypeSn+
		"&condition.vehicleDef="+vehicleDef+
		"&condition.typeId="+typeId+
		"&condition.modelId="+modelId+
		"&condition.status="+status+
		"&condition.fixDate="+fixDate+
		"&condition.fixDate2="+fixDate2+
		"&condition.dealerIds2="+dealerIds2+
		"&condition.saleDate="+saleDate+
		"&condition.saleDate2="+saleDate2+
		"&condition.ownerName="+ownerName+
		"&condition.gpsTime="+gpsTime+
		"&condition.gpsTime2="+gpsTime2+
		"&condition.speed="+speed+
		"&condition.speed2="+speed2+
		"&condition.isLogin="+isLogin+
		"&condition.isWork="+isWork+
		"&condition.totalWorkingHours="+totalWorkingHours+
		"&condition.totalWorkingHours2="+totalWorkingHours2+
		"&condition.province="+province+
		"&condition.city="+city+
		"&condition.lock="+lock+
		"&condition.alarmFlag="+alarmFlag+
		"&condition.daysUnuplad="+daysUnuplad+
		"&condition.daysUnwork="+daysUnwork;
	} catch (e) {
			
	}
	window.location.href=encodeURI(encodeURI("run/run_compositeQueryExport.action"+params));
	return;
}
//历史工况
function openWorkInfoWin(unitId){
		workInfData = unitId;
	$('<div/>').dialog({
		content : '<iframe src="jsp/run/history_workinfo.jsp" id="ifm_workinfo" name="ifm_workinfo" frameborder="0" style="border:0;width:100%;height:99%;"></iframe>',
		width : 920,
		height : 550,
		modal : true,
		cache : false,
		resizable:true,
		maximizable: true,
		title : '历史工况',
		buttons : [{
			text : '关闭',
			iconCls : 'icon-cancel',
			handler : function() {
				var d = $(this).closest('.window-body');
				d.dialog('destroy');
			}
		}],
		onLoad : function() {
			/*
			 * //加在地图上 if(typeof map.window.addPositionMarker == "function"){
			 * //map.window.addPositionMarker(data.lon,data.lat,data.vehicleDef);
			 * map.window.addPositionMarker(112.897095,28.227175,'常州A001'); }
			 */
		},
		onOpen : function() {
		},
		onClose : function() {
			$(this).dialog('destroy');
		}
	});
}
//显示列设置:是否全选
function changeChk(){
	if(document.getElementById("chk_isSelectAll").checked){
		$("input[name='CkbShowColumns']").each(function() {
	      	$(this).attr("checked",true);
		});
	}else{
		$("input[name='CkbShowColumns']").each(function() {
	      	$(this).attr("checked", false);
		});
	}
}
//机械停用
function stop(value, row, index) {
	if(row.status!=9){//9表示已经在停用组里
	  return '<a href="#" onclick="">停用</a>';
	}
}
//机械详细信息
function openDetailWin(vehicleId){
	workInfData=vehicleId;
	$('<div/>').dialog({
		content : '<iframe src="jsp/run/composite_detail.jsp" id="ifm_detail" name="ifm_detail" frameborder="0" style="border:0;width:100%;height:99%;"></iframe>',
		width : 740,
		height : 480,
		modal : true,
		cache : false,
		resizable:true,
		title : '机械详细信息',
		buttons : [{
			text : '关闭',
			iconCls : 'icon-cancel',
			handler : function() {
				var d = $(this).closest('.window-body');
				d.dialog('destroy');
			}
		}],
		onLoad : function() {
			/*
			 * //加在地图上 if(typeof map.window.addPositionMarker == "function"){
			 * //map.window.addPositionMarker(data.lon,data.lat,data.vehicleDef);
			 * map.window.addPositionMarker(112.897095,28.227175,'常州A001'); }
			 */
		},
		onClose : function() {
			$(this).dialog('destroy');
		}
	});
}
//省市级联
function provinceChange(rec){
	 $('#city').combobox('reload', encodeURI(encodeURI("sys/maparea_getCityList.action?provinceName="+rec.name)));
     $('#city').combobox('setValue',''); 
}

//A锁B锁格式化
function lockabFormatter(val,row,index){
	if(val==1){
		return '未执行';
	}else if(val==2){
		return '锁车';
	}else if(val==3){
		return '解锁';
	}
}
//使能状态格式化
function lockcFormatter(val,row,index){
	if(val==1){
		return '使能防拆';
	}else if(val==2){
		return '禁止防拆';
	}
}

function composite_datagridLoad(){
	 $.ajax({
		 url: 'run/alarm_getUserAlarmTypes.action',
		 type: 'get',
		 dataType: 'json',
		 success: function(res) {
			 var  fields = $('#composite_datagrid').datagrid('getColumnFields');
			 var columns = [];
			 var showColumns = res.showColumns;
			 if (showColumns != null)
				 columns = showColumns.split(',');
			 $(columns).each(function(index, value) {
				 if (fields.contains(value)) {
					$('#composite_datagrid').datagrid('showColumn', value);
				 }
			 });
		 }
	 });
}