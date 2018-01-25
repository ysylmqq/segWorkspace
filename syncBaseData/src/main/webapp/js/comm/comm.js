var urls = [ "logs/synclogs.do", "sync/series", "sync/models", "sync/ssssList",
		"sync/insurances", "sync/combos", "sync/info", "sync/accounts",
		"sync/bindinfo", "sync/bcinfo", "sync/vpinfo" ];

function getContextPath() {
	var pathName = document.location.pathname;
	var index = pathName.substr(1).indexOf("/");
	var result = pathName.substr(0, index + 1);
	return result;
}

function doSearch(dgID, queryFormId) {
	var fields = $("#" + queryFormId).serializeArray();
	var $dg = $("#" + dgID);
	var params = $dg.datagrid('options').queryParams;
	var queryFlag = false;
	$.each(fields, function(i, field) {
		if(field.value && field.value.length > 0){
			params[field.name] = field.value;
			queryFlag = true;
		}
	});
	var nid = dgID.substring(2);
	if(queryFlag)
		$dg.datagrid({
			url : getContextPath() + "/" + urls[nid],
			onLoadSuccess:function(data){
				if(data.total == 0 ) {
				  	$.messager.alert('提示','没有数据!');
				}
			}
		});
	else
		$.messager.alert("提示","请输入查询条件");
	
	// $dg.datagrid('reload');
}

function showDiv(nid) {
	if (nid) {
		if (nid >= 0 && nid <= 10) {
			for ( var i = 0; i <= 10; i++) {
				var divId = "#view_" + i;
				$(divId).hide()
			}
			var showId = "#view_" + nid;
			var dgId = "#dg" + nid;
			$(showId).show();
			var opts = $(dgId).datagrid('options');
			if (opts.url) {
				$(dgId).datagrid({
					url : opts.url
				});
			} else {
				defaultHaveScroll(dgId);
			}
			$(dgId).datagrid('resize');
		}
	}
}

$(function() {
	for ( var i = 1; i <= 10; i++) {// 10 跟按钮数对应
		$("#dg" + i).datagrid({
			url : ''
		});
	}
})

// 时间格式化
Date.prototype.format = function(format) {
	/*
	 * eg:format="yyyy-MM-dd hh:mm:ss";
	 */
	if (!format) {
		format = "yyyy-MM-dd hh:mm:ss";
	}

	var o = {
		"M+" : this.getMonth() + 1, // month
		"d+" : this.getDate(), // day
		"h+" : this.getHours(), // hour
		"m+" : this.getMinutes(), // minute
		"s+" : this.getSeconds(), // second
		"q+" : Math.floor((this.getMonth() + 3) / 3), // quarter
		"S" : this.getMilliseconds()
	// millisecond
	};

	if (/(y+)/.test(format)) {
		format = format.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	}

	for ( var k in o) {
		if (new RegExp("(" + k + ")").test(format)) {
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
					: ("00" + o[k]).substr(("" + o[k]).length));
		}
	}
	return format;
};

function doSeries_fmt(value, row, index) {
	if (row.series_id) {
		return "<a href='javascript:void(0);' onclick=\"doSync('"
				+ getContextPath() + "/sync/doSeries',{series_id:'"
				+ row.series_id + "'})\" >同步</a>";
	}
	return "<a href='javascript:void(0);' onclick='showMsg(\"无法操作\")' >同步</a>";
}

function doSsss_fmt(value, row, index) {
	if (row.company) {
		return "<a href='javascript:void(0);' onclick=\"doSync('"
				+ getContextPath() + "/sync/doSsss',{company:'" + row.company
				+ "'})\" >同步</a>";
	}
	return "<a href='javascript:void(0);' onclick='showMsg(\"无法操作\")' >同步</a>";
}

function doBindinfo_fmt(value, row, index) {
	if (row.vin) {
		return "<a href='javascript:void(0);' onclick=\"doSync('"
				+ getContextPath() + "/sync/doBindinfo',{vin:'" + row.vin
				+ "'})\" >同步</a>";
	}
	return "<a href='javascript:void(0);' onclick='showMsg(\"无法操作\")' >同步</a>";
}

function doAccounts_fmt(value, row, index) {
	if (row.username) {
		return "<a href='javascript:void(0);' onclick=\"doSync('"
				+ getContextPath() + "/sync/doAccounts',{vin:'" + row.username
				+ "'})\" >同步</a>";
	}
	return "<a href='javascript:void(0);' onclick='showMsg(\"无法操作\")' >同步</a>";
}

function doInfo_fmt(value, row, index) {
	if (row.vin) {
		return "<a href='javascript:void(0);' onclick=\"doSync('"
				+ getContextPath() + "/sync/doInfo',{vin:'" + row.vin
				+ "'})\" >同步</a>";
	}
	return "<a href='javascript:void(0);' onclick='showMsg(\"无法操作\")' >同步</a>";
}

function doModels_fmt(value, row, index) {
	if (row.model_id) {
		return "<a href='javascript:void(0);' onclick=\"doSync('"
				+ getContextPath() + "/sync/doModels',{model_id:'"
				+ row.model_id + "'})\" >同步</a>";
	}
	return "<a href='javascript:void(0);' onclick='showMsg(\"无法操作\")' >同步</a>";
}

function doCombos_fmt(value, row, index) {
	if (row.combo_id) {
		return "<a href='javascript:void(0);' onclick=\"doSync('"
				+ getContextPath() + "/sync/doCombos',{combo_id:'"
				+ row.combo_id + "'})\" >同步</a>";
	}
	return "<a href='javascript:void(0);' onclick='showMsg(\"无法操作\")' >同步</a>";
}

function doInsurances_fmt(value, row, index) {
	if (row.ic_id) {
		return "<a href='javascript:void(0);' onclick=\"doSync('"
				+ getContextPath() + "/sync/doInsurance',{ic_id:'" + row.ic_id
				+ "'})\" >同步</a>";
	}
	return "<a href='javascript:void(0);' onclick='showMsg(\"无法操作\")' >同步</a>";
}

function doCheckPwd_fmt(value, row, index) {
	return "<a href=\"javascript:void(0)\" class=\"easyui-linkbutton\" onclick=\"checkPWD('" + row.servicepwd + "')\">验证</a>"
}

function checkPWD(pwd) {
	$('#mydialog').show();
	$('#mydialog').dialog({
		collapsible : false,
		minimizable : false,
		maximizable : false,
		buttons : [ {
			text : '提交',
			iconCls : 'icon-ok',
			handler : function() {
				var inputpswd = $("#inputpswd").val();
				$.ajax({
					url: getContextPath() + "/" + "sync/checkPWD",
					data:{"inputpswd":inputpswd},
					dataType:"json",
					type:"post",
					success:function(data){
						if(data.success){
							$("#servicePswd_gen").textbox("setValue",data.pswd);
						}
					},
					error:function(data){
						showMsg("请求出错!");
					}
				});
			}
		}, {
			text : '取消',
			handler : function() {
				$('#mydialog').dialog('close');
			}
		} ]
	});
}

function doSync(url, json) {
	$.ajax({
		type : 'post',
		data : json,
		url : url,
		success : function(data) {
			if (data && data.success) {
				showMsg(data.msg);
			}
		},
		error : function(data) {
			if (data) {
				showMsg(data.msg);
			}
		}
	});
}

function localtimefmt(value, row, index) {
	if (value) {
		var unixTimestamp = new Date(value);
		return unixTimestamp.format('yyyy-MM-dd hh:mm:ss');
	}
	return value;
}

function DateHandle(objDate) {
	objDate = new Date(); // 创建一个日期对象表示当前时间
	var year = objDate.getFullYear(); // 四位数字年
	var month = objDate.getMonth() + 1; // getMonth()返回的月份是从0开始的，还要加1
	var date = objDate.getDate();
	var hours = objDate.getHours();
	var minutes = objDate.getMinutes();
	var seconds = objDate.getSeconds();
	var date = year + "-" + month + "-" + date + " " + hours + ":" + minutes
			+ ":" + seconds;
	return date;
}

function timefmt(value, row, index) {
	if (value) {
		var unixTimestamp = new Date(value * 1000);
		return unixTimestamp.format('yyyy-MM-dd hh:mm:ss');
	}
	return value;
}

function showMsg(msg) {
	$.messager.show({
		title : '提示',
		msg : msg,
		style : {
			right : '',
			bottom : ''
		}
	});
}

function defaultHaveScroll(gridid) {
	var opts = $(gridid).datagrid('options');
	// alert(Ext.util.JSON.encode(opts.columns));
	var text = '{';
	for ( var i = 0; i < opts.columns.length; i++) {
		var inner_len = opts.columns[i].length;
		for ( var j = 0; j < inner_len; j++) {
			if ((typeof opts.columns[i][j].field) == 'undefined')
				break;
			text += "'" + opts.columns[i][j].field + "':''";
			if (j != inner_len - 1) {
				text += ",";
			}
		}
	}
	text += "}";
	text = eval("(" + text + ")");
	var data = {
		"total" : 1,
		"rows" : [ text ]
	};
	$(gridid).datagrid('loadData', data);
	// $('#grid').datagrid('appendRow',text);
	$("tr[datagrid-row-index='0']").css({
		"visibility" : "hidden"
	});
}

function formRest(formId) {
	$(formId).form('clear');
}
