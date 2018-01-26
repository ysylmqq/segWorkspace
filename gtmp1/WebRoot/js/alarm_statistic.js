//窗口大小变化
function alarmQueryResize(w, h) {
	if ($('#alarm_statistic_datagrid')) {
		try {
			$('#alarm_statistic_datagrid').datagrid('options');
			$('#alarm_statistic_datagrid').datagrid('resize', {
				width : w - 2,
				height : h
			});
		} catch (e) {}
	}
}
// 查询
function statisticalarm() {
	var startTime = $('#start_time').val();
	var endTime = $('#end_time').val();
	if (startTime && !endTime) {
		$.messager.alert('提示', '请选择结束时间!!');
		return;
	}
	if (endTime && !startTime) {
		$.messager.alert('提示', '请选择开始时间!!');
		return;
	}
	if (startTime && endTime) {
		var len = getMonthNumber(startTime, endTime);
		if (len < 0) {
			$.messager.alert('提示', '结束时间必须在开始时间之后!');
			return;
		} else if (len > 11) {
			$.messager.alert('提示', '查询月份时间段不能超过12个月!');
			return;
		}
	}

	$.ajax({
		type: 'post',
		url: 'run/alarm_statistic_search.action',
		data: {
			reportType : $('input:radio[name="report_type"]:checked').val(),
			vehicleModel : $('#vehicleModel_search').combobox('getValue') == undefined ? '' : $('#vehicleModel_search').combobox('getValue'),
			vehicleCode : $('#vehicleCode_search').combobox('getValue') == '全部' ? '' : $('#vehicleCode_search').combobox('getValue'),
			vehicleArg : $('#vehicleArg_search').combobox('getValue') == '全部' ? '' : $('#vehicleArg_search').combobox('getValue'),
			alarmTypeId : $('#alarmType_search').combobox('getValue') == undefined ? '' : $('#alarmType_search').combobox('getValue'),
			alarmTypeGenre: $('#alarmTypeGenre_search').combobox('getValue') == '全部' ? '' : $('#alarmTypeGenre_search').combobox('getValue'),
			startDateStr : startTime,
			endDateStr : endTime
		},
		dataType: 'json',
		success: function(data) {
			var options = $("#alarm_statistic_datagrid").datagrid("options"); // 取出当前datagrid的配置
			// 为了加上formatter方法，用以下方法加载列
			var columns = data.columns[0];
			var column = null;
			options.columns[0] = [];
			for ( var index = 0; index < columns.length; index++) {
				column = columns[index];
				if (!(column.field == 'alarmTypeName' || column.field == 'vehicleModelName' || column.field == 'vehicleCode' || column.field == 'vehicleArg')) {
					var field = column.field;
					column.formatter = function(val, row, index, field) {
						if (val > 0) {
							return '<a style="color: blue;text-decoration: underline;cursor: pointer;">' + val + '</a>';
						} else {
							return val;
						}
					}
				}
				options.columns[0].push(column);
			}
			// //添加查询参数
			$("#alarm_statistic_datagrid").datagrid(options);
			$("#alarm_statistic_datagrid").datagrid("loadData", data.rows);
		}
	});
}
// 导出
function downExcel() {
	var startTime = $('#start_time').val();
	var endTime = $('#end_time').val();
	if (startTime && !endTime) {
		$.messager.alert('提示', '请选择结束时间!!');
		return;
	}
	if (endTime && !startTime) {
		$.messager.alert('提示', '请选择开始时间!!');
		return;
	}
	if (startTime && endTime) {
		var len = getMonthNumber(startTime, endTime);
		if (len < 0) {
			$.messager.alert('提示', '结束时间必须在开始时间之后!');
			return;
		} else if (len > 11) {
			$.messager.alert('提示', '查询月份时间段不能超过12个月!');
			return;
		}
	}
	var reportType = $('input:radio[name="report_type"]:checked').val();
	var vehicleModel = $('#vehicleModel_search').combobox('getValue') == undefined ? '' : $('#vehicleModel_search').combobox('getValue');
	var vehicleCode = $('#vehicleCode_search').combobox('getValue') == '全部' ? '' : $('#vehicleCode_search').combobox('getValue');
	var vehicleArg = $('#vehicleArg_search').combobox('getValue') == '全部' ? '' : $('#vehicleArg_search').combobox('getValue');
	var alarmTypeId = $('#alarmType_search').combobox('getValue') == undefined ? '' : $('#alarmType_search').combobox('getValue');
	var alarmTypeGenre= $('#alarmTypeGenre_search').combobox('getValue') == '全部' ? '' : $('#alarmTypeGenre_search').combobox('getValue');
	window.location.href = encodeURI(encodeURI("run/alarm_exportToExcelStatistic.action?reportType=" + reportType
			+"&vehicleModel=" + vehicleModel 
			+ "&vehicleCode=" + vehicleCode 
			+ "&vehicleArg=" + vehicleArg 
			+ "&alarmTypeId=" + alarmTypeId
			+ "&startDateStr=" + startTime 
			+ "&endDateStr=" + endTime
			+"&alarmTypeGenre="+alarmTypeGenre));
	return;
}

/**
 * 将指定form参数转换为json对象
 */
function getQueryParams(conditionFormId) {
	var searchCondition = getJqueryObjById(conditionFormId).serialize();
	var obj = {};
	var pairs = searchCondition.split('&');
	var name, value;

	$.each(pairs, function(i, pair) {
		pair = pair.split('=');
		name = decodeURIComponent(pair[0]);
		value = decodeURIComponent(pair[1]);

		obj[name] = !obj[name] ? value : [].concat(obj[name]).concat(value); // 若有多个同名称的参数，则拼接
	});

	return obj;
}
/*
 * 打开图表窗口
 */
function openChartWin() {
	var startTime = $('#start_time').val();
	var endTime = $('#end_time').val();
	if (startTime && !endTime) {
		$.messager.alert('提示', '请选择结束时间!!');
		return;
	}
	if (endTime && !startTime) {
		$.messager.alert('提示', '请选择开始时间!!');
		return;
	}
	if (startTime && endTime) {
		var len = getMonthNumber(startTime, endTime);
		if (len < 0) {
			$.messager.alert('提示', '结束时间必须在开始时间之后!');
			return;
		} else if (len > 11) {
			$.messager.alert('提示', '查询月份时间段不能超过12个月!');
			return;
		}
	}
	var reportType = $('input:radio[name="report_type"]:checked').val();
	var vehicleModel = $('#vehicleModel_search').combobox('getValue') == undefined ? '' : $('#vehicleModel_search').combobox('getValue');
	var vehicleCode = $('#vehicleCode_search').combobox('getValue') == '全部' ? '' : $('#vehicleCode_search').combobox('getValue');
	var vehicleArg = $('#vehicleArg_search').combobox('getValue') == '全部' ? '' : $('#vehicleArg_search').combobox('getValue');
	var alarmTypeId = $('#alarmType_search').combobox('getValue') == undefined ? '' : $('#alarmType_search').combobox('getValue');
	var alarmTypeGenre= $('#alarmTypeGenre_search').combobox('getValue') == '全部' ? '' : $('#alarmTypeGenre_search').combobox('getValue');
	var src = encodeURI(encodeURI("run/alarm_drawChart.action?reportType=" + reportType
			+"&vehicleModel="+ vehicleModel 
			+ "&vehicleCode=" + vehicleCode 
			+ "&vehicleArg="+ vehicleArg 
			+ "&alarmTypeId=" + alarmTypeId
			+ "&startDateStr=" + startTime 
			+ "&endDateStr=" + endTime
			+"&alarmTypeGenre="+alarmTypeGenre
			));

	$('<div/>').dialog({
		// content:'<iframe src="obd/map.jsp" id="map" name="map"
		// frameborder="0" style="border:0;width:100%;height:99%;"></iframe>',
		content : '<img src=\'' + src + '\'/>',
		width : 760,
		height : 470,
		//cache : true,
		loadingMessage : '加载中...',
		modal : true,
		resizable : true,
		title : '图表',
		onClose : function() {
			$(this).dialog('destroy');
		},
		onLoad : function() {

		},
		buttons : [ {
			text : '导出',
			iconCls : 'icon-save',
			handler : function() {
				var reportType = $('input:radio[name="report_type"]:checked').val();
				var vehicleModel = $('#vehicleModel_search').combobox('getValue') == undefined ? '' : $('#vehicleModel_search').combobox('getValue');
				var vehicleCode = $('#vehicleCode_search').combobox('getValue') == '全部' ? '' : $('#vehicleCode_search').combobox('getValue');
				var vehicleArg = $('#vehicleArg_search').combobox('getValue') == '全部' ? '' : $('#vehicleArg_search').combobox('getValue');
				var alarmTypeId = $('#alarmType_search').combobox('getValue') == undefined ? '' : $('#alarmType_search').combobox('getValue');
				var alarmTypeGenre= $('#alarmTypeGenre_search').combobox('getValue') == '全部' ? '' : $('#alarmTypeGenre_search').combobox('getValue');
				window.open(encodeURI(encodeURI("run/alarm_downloadChart.action?reportType=" + reportType
						+ "&vehicleModel=" + vehicleModel 
						+ "&vehicleCode=" + vehicleCode 
						+ "&vehicleArg="+ vehicleArg 
						+ "&alarmTypeId=" + alarmTypeId
						+ "&startDateStr=" + startTime 
						+ "&endDateStr=" + endTime
						+"&alarmTypeGenre="+alarmTypeGenre)));
			}},
		    {
			text : '关闭',
			iconCls : 'icon-cancel',
			handler : function() {
				var d = $(this).closest('.window-body');
				d.dialog('destroy');
			}
		} ]
	});
}
/**
 * 显示详细的故障信息
 * 
 * @param {}
 *            typeId
 * @param {}
 *            month
 * @param {}
 *            year
 */
function showDetail(rowIndex, field) {
	var startTime = new Date();
	var endTime = new Date();
	startTime.setYear(field.replace('m','').substring(0,4));
	startTime.setMonth(field.replace('m','').substring(4) - 1);
	startTime.setDate(1);
	startTime.setHours(0);
	startTime.setMinutes(0);
	startTime.setSeconds(0);
	endTime.setYear(field.replace('m','').substring(0,4));
	endTime.setMonth(field.replace('m','').substring(4));
	endTime.setDate(1);
	endTime.setHours(0);
	endTime.setMinutes(0);
	endTime.setSeconds(0);
	var options = $("#alarm_detail_datagrid").datagrid("options");
	var params = {
		alarmStatus : '01',
		startTime : startTime.formatDate("yyyy-MM-dd hh:mm:ss"),
		endTime : endTime.formatDate("yyyy-MM-dd hh:mm:ss")
	};
	var reportType = $('input:radio[name="report_type"]:checked').val();
	var row = $('#alarm_statistic_datagrid').datagrid('getRows')[rowIndex];
	if (reportType == 1) {// 机型
		var vehicleCode = $('#vehicleCode_search').combobox('getValue');
		if(vehicleCode == '全部' || vehicleCode == undefined) {
			params.vehicleModel = row.typeId;
		} else {
			var vehicleArg = $('#vehicleArg_search').combobox('getValue');
			if(vehicleArg != '全部' && vehicleArg != undefined) {
				params.vehicleArg = row.vehicleArg;
			}
			params.vehicleCode = row.vehicleCode;
		}
	} else {// 故障类型
		params.alarmTypeId = row.typeId;
	}
	options.queryParams = params;
	if (!options.url) {
		options.url = "run/alarm_getAlarmInfo.action";
		$("#alarm_detail_datagrid").datagrid(options);
	} else {
		$('#alarm_detail_datagrid').datagrid('load');

	}
	$('#dlg_alarm_detail').dialog('open');
}

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

function malfunnStatisCellClick(rowIndex, field, value) {
	if (value > 0) {
		showDetail(rowIndex, field);
	}
}

//机械型号联动控制      
$('#vehicleModel_search').combobox({
onSelect:function(){
var obj = $('#vehicleModel_search').combobox('getValue');
if(obj != ""){  
    $.post("report/workhour_modelNameSearch.action", {
        obj:obj
        }, function(data) {
            var obj = $.parseJSON(data);
            $('#vehicleCode_search').combobox({
               data : obj.code,
               valueField:'vehicleCode',
               textField:'vehicleCode',
               onLoadSuccess: function () { //加载完成后,设置选中第一项
                var val = $(this).combobox("getData");
                for (var item in val[0]) {
                    if (item == "vehicleCode") {
                        $(this).combobox("select", val[0][item]);
                    }
                }
            }
            });
        });
}else{
   $('#vehicleCode_search').combobox('setValues', ['全部']);
   $('#vehicleArg_search').combobox('setValues', ['全部'])
}
}

});

// 机械配置联动
$('#vehicleCode_search').combobox({
	onSelect : function() {
		var obj = $('#vehicleCode_search').combobox('getValue');
		$("#vehicleArg_search").empty();
		$.get("vehicle/vehicleModel_getList2.action", {
			obj : obj
		}, function(data) {
			var dataJson = $.parseJSON(data);
			var datas = [ {
				'vehicleArg' : '全部'
			} ];
			for (i = 0; i < dataJson.arg.length; i++) {
				var docNameInfo = dataJson.arg[i].vehicleArg;
				datas.push({
					'vehicleArg' : docNameInfo
				});
			}
			$('#vehicleArg_search').combobox('clear').combobox('loadData', datas).combobox('select', '全部');
		});
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
	      	 