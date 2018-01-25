(function($) {
	//开始日期、结束日期赋默认值
	var bdate = $("#startTime").val();
	var edate = $("#endTime").val();
	var modelName = "";
	if($("#modelName").val() != ""){	//车型名称
		modelName = $("#modelName").val();
	}
	var modelId = "";
	if($("#modelId").val() != ""){		//车型id
		modelId = $("#modelId").val();
	}
	var fsId = "";
	if($("#drill_4sId").val() != ""){			//4s店id
		fsId = $("#drill_4sId").val();
	}
	
	var defaults = {
	    title : "未开通明细报表",
		width : 700,
		height : 250,
		url: '../../../rpt/unServiceDetail',
		datatype : "json",
		usepager : true,
		useRp : true,
		rownumbers : true,//序号
		colModel : [ {
			display : '4S店名称',
			name : 'fc_name',
			width : 102,
			sortable : false
		}, {
			display : '车牌号码',
			name : 'plate_no',
			width : 102,
			sortable : false
		}, {
			display : '车载电话',
			name : 'call_letter',
			width : 102,
			sortable : false
		}, {
			display : 'vin',
			name : 'vin',
			width : 102,
			sortable : false,
		}, {
			display : '车辆生产日期',
			name : 'production_date',
			width : 142,
			sortable : false,
			formatter : function(value, row) {
				return value;
			}
		}, {
			display : '录单日期',
			name : 'register_date',
			width : 142,
			sortable : false,
			formatter : function(value, row) {
				return value;
			}
		} ],
		buttons : [],
		searchitems : [
			{display : '开始日期', name : 'startTime',type : 'date', value : bdate},
			{display : '结束日期',name : 'endTime', type : 'date', value : edate}
			,{name : 'modelName', type :'hidden', value : modelName}
			,{name : 'modelId', type :'hidden', value : modelId}
			,{name : '4sId', type :'hidden', value : fsId}
		]
	};
	$('#unServiceDetailContent').sgDatagrid(defaults);

})(jQuery);