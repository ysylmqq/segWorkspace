$(function(){
	Date.prototype.format = function(fmt) { //author: meizz 
		var o = {
			"M+": this.getMonth() + 1,
			//月份 
			"d+": this.getDate(),
			//日 
			"h+": this.getHours(),
			//小时 
			"m+": this.getMinutes(),
			//分 
			"s+": this.getSeconds(),
			//秒 
			"q+": Math.floor((this.getMonth() + 3) / 3),
			//季度 
			"S": this.getMilliseconds() //毫秒 
		};
		if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
		for (var k in o) if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
		return fmt;
	}
	
	var bdate = new Date();
	bdate.setDate(1);
	bdate = bdate.format("yyyy-MM-dd");
	var edate = new Date().format("yyyy-MM-dd");
	
	winClose = function(){
		$(document).sgWindow('close', {
			id: 'view_detail'
		});
		$("#drill_4sId").val("");
	}
	
	viewDetail = function(value){
		$("#drill_4sId").val(value);
		var defaults = {
			title : '未开通明细',
			id: 'view_detail',
			form: 'detail_form',
			url: 'unServiceDetail.html',
			width : 720,
			height : 390,
			buttons : [{
				name : '关闭',
				onpress : winClose
			}]
		};
		$(document).sgWindow(defaults);
	}
	
	var defaults = {
		title : "未开通汇总报表",
		width:  '100%',
        fitColumn:  true,
		height : 395,
		url : '../../../rpt/unServiceSum',
		datatype : "json",
		usepager : true,
		rownumbers : true,
		useRp : true,

		colModel : [{display : '4S店名称', name : 'fc_name', width : '180', sortable : false }
			, {display : '未开通数量', name : 'unservice_count', width : '180', sortable : false }
			, {display : '操作', 	name : '4s_id',	width : '260', sortable : false, formatter : function(value, row) {
				return '<input type="button" class="btn-default btn" value="明细" onclick="viewDetail('+value+')"/>';
			}}
		],
		searchitems : [
		    {
				display : '开始日期',
				name : 'startTime',
				html:'<input type="date" name="startTime" id="startTime" value="'+bdate+'" class="form-control schInput">'
		    },
		    {
				display : '结束日期',
				name : 'endTime',
				html:'<input type="date" name="endTime" id="endTime" value="'+edate+'" class="form-control schInput">'
		    },
		    {
				display : '车型',
				name : 'model',
				html:'<input type="text" name="modelName" id="modelName" placeholder="请输入车型" list="modelDataList"/>'
					+ '<input type="hidden" name="modelId" id="modelId">'
					+ '<datalist id="modelDataList"></datalist>'
		    },
		    {
				display : '4S店',
				name : 'fcenter',
				html:'<input type="text" name="fcenter" id="fcenter" placeholder="请输入4S店" list="fcDataList"/>'
					+ '<input type="hidden" name="4sId" id="4sId">'
					+ '<datalist id="fcDataList"></datalist>'
		    }
		],
		sortorder : "desc"
	};
	$('#monitoring').sgDatagrid(defaults);
	
	//车型下拉
	$.ajax({
		type : 'post',
		async : true,
		contentType : 'application/json',
		dataType : 'json',
		url : '../../../rpt/getModels',
		data : JSON.stringify({brandId:101}),
		success : function(data) {
			if (data) {
				var model = data;
				if (model.length > 0) {
					$("#modelDataList").empty();
					modelDataList = {};
				}
				$.each(model, function(key, value) {
					var op = $("<option></option>");
					op.val(value.model_name);
					$("#modelDataList").append(op);
					modelDataList[value.model_name] = value.model_id;
				});

				$("#modelName").on('change', function() {
					var strs = this.value.split(" ");
					if (modelDataList[strs[strs.length - 1]]) {
						$(this).val(strs[strs.length - 1]);
						$("#modelId").val(modelDataList[strs[strs.length - 1]]);
						if ($("#modelId").val().length == 0) {
							$("#modelName").val("");
						}
					} else {
						$(this).val('');
						$("#modelId").val("");
					}
				});
			} else {
				$(document).sgPup({message : 'message_info', text : data});
			}
		},
		error : function(res, error) {
			if (res && res.responseText) {
				$(document).sgPup({message : 'message_info', text : res.responseText});
			}
		}
	});

	//4s店下拉
	$.ajax({
		type : 'post',
		async : true,
		contentType : 'application/json',
		dataType : 'json',
		url : '../../../rpt/get4SShop',
		data : JSON.stringify({companyno:201}),
		success : function(data) {
			if (data) {
				var fcShop = data;
				if (fcShop.length > 0) {
					$("#fcDataList").empty();
					fcDataList = {};
				}
				$.each(fcShop, function(key, value) {
					var op = $("<option></option>");
					op.val(value.companyname);
					$("#fcDataList").append(op);
					fcDataList[value.companyname] = value.companyno;
				});

				$("#fcenter").on('change', function() {
					var strs = this.value.split(" ");
					if (fcDataList[strs[strs.length - 1]]) {
						$(this).val(strs[strs.length - 1]);
						$("#4sId").val(fcDataList[strs[strs.length - 1]]);
						if ($("#4sId").val().length == 0) {
							$("#fcenter").val("");
						}
					} else {
						$(this).val('');
						$("#4sId").val("");
					}
				});
			} else {
				$(document).sgPup({message : 'message_info', text : data});
			}
		},
		error : function(res, error) {
			if (res && res.responseText) {
				$(document).sgPup({message : 'message_info', text : res.responseText});
			}
		}
	});
});