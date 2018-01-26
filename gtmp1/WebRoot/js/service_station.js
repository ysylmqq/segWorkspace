//查询
function queryStation() {
	$('#ss_datagrid').datagrid('load',{
		stationName : $('#stationName_search').val(),
		province : $('#province_search').val(),
		city : $('#city_search').val()
	});
}

//新増
function addStation() {
	$('#ss_operate_form').form('clear');
	$('#dlg_ss_operate').dialog('open');
}

//保存
function saveStation() {
	$.ajax({
		url : 'servicemanage/service_station!addOrEdit.action',
		type : 'POST',
		data : $('#ss_operate_form').serialize(),
		dataType : 'json',
		success : function(data) {
			$.messager.alert(tipMsgDilag, data.msg);
			$('#dlg_ss_operate').dialog('close');
			$('#ss_datagrid').datagrid('load');
		},
		error : function(data) {
			$.messager.alert(tipMsgDilag, "出错啦！");
		}
	});
}

//初始化编辑窗口
function showStation(id) {
	$.ajax({
		url : 'servicemanage/service_station!show.action?id=' + id,
		type : 'GET',
		dataType : 'json',
		success : function(data) {
			$('#ss_operate_form').form('load', data);
			$('#dlg_ss_operate').dialog('open');
		},
		error : function(data) {
			$.messager.alert(tipMsgDilag, "出错啦！");
		}
	});
}

//删除
function removeStation(id){
	$.messager.confirm(
		tipMsgDilag,
		"确定删除?",
		function(flag) {
			if (flag) {
				$.ajax({
					url : 'servicemanage/service_station!delete.action?id=' + id,
					type : 'POST',
					dataType : 'json',
					success : function(data) {
						$.messager.alert(tipMsgDilag, data.msg);
						$('#ss_datagrid').datagrid('load');
					},
					error : function(data) {
						$.messager.alert(tipMsgDilag, "出错啦！");
					}
				});
			}
		}
	);
}

function openMapSelect() {
	var lon = $('#longitude').val();
	var lat = $('#latitude').val();
	$('<div/>').dialog({
		content : '<iframe src="jsp/service/mapSelect.jsp?lon='+lon+'&lat='+lat+'" id="mapSelect" frameborder="0" style="border:0;width:100%;height:99%;"></iframe>',
		width : 800,
		height : 500,
		modal : true,
		maximizable : true,
		cache : false,
		title : '选取经纬度',
		buttons : [{
			text : '确认',
			iconCls : 'icon-ok',
			handler : function() {
				var ll = $('#mapSelect')[0].contentWindow.getLonLat();
				var lal= ll.split(',');
				$('#longitude').val(lal[0]);
				$('#latitude').val(lal[1]);
				$(this).closest('.window-body').dialog('destroy');
			}
		},{
			text : '关闭',
			iconCls : 'icon-cancel',
			handler : function() {
				$(this).closest('.window-body').dialog('destroy');
			}
		}],
		onClose : function() {
			$(this).dialog('destroy');
		}
	});
}

//地图窗口
function openMap(lon, lat) {
	$('<div/>').dialog({
		content : '<iframe src="jsp/service/map.jsp?lon='+lon+'&lat='+lat+'" id="map" name="map" frameborder="0" style="border:0;width:100%;height:99%;"></iframe>',
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
				$(this).closest('.window-body').dialog('destroy');
			}
		}],
		onClose : function() {
			$(this).dialog('destroy');
		}
	});
}

//列格式化
function operateFormatter(val, row, index) {
	var id = row.id;
	var longitude = row.longitude;
	var latitude = row.latitude;
	var str = '<a style="color:blue;" href="javascript:void(0)" onclick="openMap(' + longitude + ',' + latitude + ')">查看地图</a>';
	str += '  <a style="color:blue;" href="javascript:void(0)" onclick="showStation(' + id + ')">编辑</a>';
	str += '  <a style="color:blue;" href="javascript:void(0)" onclick="removeStation(' + id + ')">删除</a>';
	return str;
}

//窗口大小变化
function serviceStationCenterResize(w,h){
	if($('#ss_datagrid')){
		try{
		  $('#ss_datagrid').datagrid('options');
		  $('#ss_datagrid').datagrid('resize', {  
				width : w-2 ,
				height:h
		 }); 
		}catch(e){}
	}
}

