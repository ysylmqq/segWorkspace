//窗口大小变化
function alarmQueryResize(w, h) {
	if ($('#malfunction_statistic_datagrid')) {
		try {
			$('#malfunction_statistic_datagrid').datagrid('options');
			$('#malfunction_statistic_datagrid').datagrid('resize', {
				width : w - 2,
				height : h
			});
		} catch (e) {
		}
	}
}
// 查询
function statisticMalfunction() {
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

	var type = "POST";
	var url = "run/malfunction_statistic_search.action";
	var malfunctionCode = $('#malfunctionCode_search').combobox('getValue') == '全部' ? '' : $('#malfunctionCode_search').combobox('getValue');
	var modelName = $('#modelName_search').combobox('getValue') == '全部' ? '' : $('#modelName_search').combobox('getValue');
	var vehicleCode = $('#vehicleCode_search').combobox('getValue');
	if(vehicleCode == undefined || vehicleCode == '全部') {
		vehicleCode = '';
	}
	var vehicleArg = $('#vehicleArg_search').combobox('getValue');
	if(vehicleArg == undefined || vehicleArg == '全部') {
		vehicleArg = '';
	}
	var dealerIds = $('#dealerId_search').combotree('getValues');
	var param = {
		reportType : $('input:radio[name="report_type"]:checked').val(),
		malfunctionCode : malfunctionCode,
		modelName : modelName,
		vehicleCode : vehicleCode,
		vehicleArg : vehicleArg,
		dealerIds : dealerIds,
		startDateStr : startTime,
		endDateStr : endTime
	};
	ajaxExtend(
			type,
			url,
			param,
			true,
			function(data) {
				var options = $("#malfunction_statistic_datagrid").datagrid("options"); // 取出当前datagrid的配置
				// options.columns = data.columns; //添加服务器端返回的columns配置信息
				// 为了加上formatter方法，用以下方法加载列
				var columns = data.columns[0];
				var column = null;
				options.columns[0] = [];
				for ( var index = 0; index < columns.length; index++) {
					column = columns[index];
					if (!(column.field == 'typeId' || column.field == 'modelName' || column.field == 'vehicleCode' || column.field == 'vehicleArg' || column.field == 'dealerName')) {
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
				// options.queryParams = getQueryParams("sqlConditionId");
				// //添加查询参数
				$("#malfunction_statistic_datagrid").datagrid(options);
				// $("#malfunction_statistic_datagrid").datagrid("load") ;
				// //获取当前页信息
				$("#malfunction_statistic_datagrid").datagrid("loadData",
						data.rows);
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
	var malfunctionCode = $('#malfunctionCode_search').combobox('getValue') == '全部' ? '' : $('#malfunctionCode_search').combobox('getValue');
	var modelName = $('#modelName_search').combobox('getValue') == '全部' ? '' : $('#modelName_search').combobox('getValue') == '全部';
	var vehicleCode = $('#vehicleCode_search').combobox('getValue');
	if(vehicleCode == undefined || vehicleCode == '全部') {
		vehicleCode = '';
	}
	var vehicleArg = $('#vehicleArg_search').combobox('getValue');
	if(vehicleArg == undefined || vehicleArg == '全部') {
		vehicleArg = '';
	}
	window.location.href = encodeURI(encodeURI("run/malfunction_exportToExcelStatistic.action?reportType="
			+ $('input:radio[name="report_type"]:checked').val()
			+ "&malfunctionCode="
			+ malfunctionCode
			+ "&modelName="
			+ modelName
			+ "&vehicleCode="
			+ vehicleCode
			+ "&vehicleArg="
			+ vehicleArg
			+ "&startDateStr="
			+ startTime + "&endDateStr=" + endTime));
	return;
}

/**
 * ajax请求基础方法
 * 
 * @param type
 * @param url
 * @param param
 * @param async
 * @param callback
 */
function ajaxExtend(type, url, param, async, callback) {
	$.ajax({
		type : type,
		url : url,
		data : param,
		async : async,
		dataType : "json",
		traditional : true,
		success : function(result) {
			callback(result);
		}
	});
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
	
	var malfunctionCode = $('#malfunctionCode_search').combobox('getValue') == '全部' ? '' : $('#malfunctionCode_search').combobox('getValue');
	var modelName = $('#modelName_search').combobox('getValue') == '全部' ? '' : $('#modelName_search').combobox('getValue');
	var vehicleCode = $('#vehicleCode_search').combobox('getValue');
	if(vehicleCode == undefined || vehicleCode == '全部') {
		vehicleCode = '';
	}
	var vehicleArg = $('#vehicleArg_search').combobox('getValue');
	if(vehicleArg == undefined || vehicleArg == '全部') {
		vehicleArg = '';
	}
	var src = encodeURI(encodeURI("run/malfunction_drawChart.action?reportType="
			+ $('input:radio[name="report_type"]:checked').val()
			+ "&malfunctionCode="
			+ malfunctionCode
			+ "&modelName="
			+ modelName
			+ "&vehicleCode="
			+ vehicleCode
			+ "&vehicleArg="
			+ vehicleArg
			+ "&startDateStr="
			+ startTime + "&endDateStr=" + endTime));
	$('<div/>').dialog({
		// content:'<iframe src="obd/map.jsp" id="map" name="map"
		// frameborder="0" style="border:0;width:100%;height:99%;"></iframe>',
		content : '<img src=\'' + src + '\'/>',
		width : 760,
		height : 470,
		cache : true,
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
				var malfunctionCode = $('#malfunctionCode_search').combobox('getValue') == '全部' ? '' : $('#malfunctionCode_search').combobox('getValue');
				var modelName = $('#modelName_search').combobox('getValue') == '全部' ? '' : $('#modelName_search').combobox('getValue') == '全部';
				var vehicleCode = $('#vehicleCode_search').combobox('getValue');
				if(vehicleCode == undefined || vehicleCode == '全部') {
					vehicleCode = '';
				}
				var vehicleArg = $('#vehicleArg_search').combobox('getValue');
				if(vehicleArg == undefined || vehicleArg == '全部') {
					vehicleArg = '';
				}
				var src = encodeURI(encodeURI("run/malfunction_downloadChart.action?reportType="
						+ $('input:radio[name="report_type"]:checked').val()
						+ "&malfunctionCode="
						+ malfunctionCode
						+ "&modelName="
						+ modelName
						+ "&vehicleCode="
						+ vehicleCode
						+ "&vehicleArg="
						+ vehicleArg
						+ "&startDateStr="
						+ startTime + "&endDateStr=" + endTime));
				window.open(src);
			}
		},{
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
	var options = $("#malfunction_datagrid").datagrid("options");
	var params = {
		startTime : startTime.formatDate("yyyy-MM-dd hh:mm:ss"),
		endTime : endTime.formatDate("yyyy-MM-dd hh:mm:ss")
	};
	var reportType = $('input:radio[name="report_type"]:checked').val();
	var row = $('#malfunction_statistic_datagrid').datagrid('getRows')[rowIndex];
	if (reportType == 1) {// 机型
		var vehicleCode = $('#vehicleCode_search').combobox('getValue');
		if(vehicleCode == '全部' || vehicleCode == undefined) {
			params.modelName = row.typeId;
		} else {
			var vehicleArg = $('#vehicleArg_search').combobox('getValue');
			if(vehicleArg != '全部' && vehicleArg != undefined) {
				params.vehicleArg = row.vehicleArg;
			}
			params.vehicleCode = row.vehicleCode;
		}
	} else if (reportType == 2) {// 故障类型
		params.malfunctionCode = row.typeId;
	} else { // 区域/经销商
		params.dealerIds = row.typeId;
	}
	options.queryParams = params;
	if (!options.url) {
		options.url = "run/malfunction_search.action";
		$("#malfunction_datagrid").datagrid(options);
	} else {
		$('#malfunction_datagrid').datagrid('load');
	}

	$('#dlg_malfunction_detail').dialog('open');
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
			$('#vehicleArg_search').combobox('clear').combobox(
					'loadData', datas).combobox('select', '全部');
		});
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