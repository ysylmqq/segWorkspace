//窗口大小变化
function workedYearsStatisResize(w,h){
	if($('#workedYearsStatis_datagrid')){
		try{
		  $('#workedYearsStatis_datagrid').datagrid('options');
		  $('#workedYearsStatis_datagrid').datagrid('resize', {  
				width : w-2 ,
				height:h
		 }); 
		}catch(e){
		}
	}
}
function workedYearsDetailResize(wid, hei){
	$('#worked_years_detail_datagrid').datagrid('resize', {
		width : wid - 2,
		height : hei
	});
}

//查询
function statisticWorkedYears(){
    $.ajax({
		type: 'post',
		url: 'report/statistic_statisticWorkedYears.action',
		data: {
	    	dealerId: $('#dealerId_search').combobox('getValues').join(','),
	    	modelName: $('#vehicleModel_search').combobox('getValue') == undefined ? '' : $('#vehicleModel_search').combobox('getValue'),
	    	vehicleCode: $('#vehicleCode_search').combobox('getValue') == '全部' ? '' : $('#vehicleCode_search').combobox('getValue'),
	    	vehicleArg: $('#vehicleArg_search').combobox('getValue') == '全部' ? '' : $('#vehicleArg_search').combobox('getValue')
	    },
		dataType: 'json',
		success: function(data) {
			var options = $("#workedYearsStatis_datagrid").datagrid("options"); // 取出当前datagrid的配置
			// 为了加上formatter方法，用以下方法加载列
			var columns = data.columns;
			var column = null;
			options.columns[0] = [];
			for ( var index = 0; index < columns.length; index++) {
				column = columns[index];
				if (!(column.field == 'typeId' || column.field == 'modelName')) {
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
			$("#workedYearsStatis_datagrid").datagrid(options);
			$("#workedYearsStatis_datagrid").datagrid("loadData", data.rows);
		}
	});
}
//导出
function downExcel(){
	console.log("value ",$('#dealerId_search').combobox('getValues').join(","));
	var dealerId = $('#dealerId_search').combobox('getValues') == undefined ? '' : $('#dealerId_search').combobox('getValues').join(",");
	var modelName = $('#vehicleModel_search').combobox('getValue') == undefined ? '' : $('#vehicleModel_search').combobox('getValue');
	var vehicleCode = $('#vehicleCode_search').combobox('getValue') == '全部' ? '' : $('#vehicleCode_search').combobox('getValue');
	var vehicleArg = $('#vehicleArg_search').combobox('getValue') == '全部' ? '' : $('#vehicleArg_search').combobox('getValue');
	window.location.href=encodeURI(encodeURI("report/statistic_exportToExcelWorkedYears.action?dealerId="+dealerId+"&modelName="+modelName+"&vehicleCode="+vehicleCode+"&vehicleArg="+vehicleArg));
	return;
}
/*
 * 打开图表窗口
 * */
function openChartWin(){
	var dealerId = $('#dealerId_search').combobox('getValue') == undefined ? '' : $('#dealerId_search').combobox('getValue');
	var modelName = $('#vehicleModel_search').combobox('getValue') == undefined ? '' : $('#vehicleModel_search').combobox('getValue');
	var vehicleCode = $('#vehicleCode_search').combobox('getValue') == '全部' ? '' : $('#vehicleCode_search').combobox('getValue');
	var vehicleArg = $('#vehicleArg_search').combobox('getValue') == '全部' ? '' : $('#vehicleArg_search').combobox('getValue');
	var src=encodeURI(encodeURI("report/statistic_drawChart4WorkedYears.action?dealerId="+dealerId+"&modelName="+modelName+"&vehicleCode="+vehicleCode+"&vehicleArg="+vehicleArg));
$('<div/>').dialog({
					//content:'<iframe src="obd/map.jsp" id="map" name="map" frameborder="0" style="border:0;width:100%;height:99%;"></iframe>',
					content:'<img src=\''+src+'\'/>',
					width : 760,
					height : 470,
					cache:true,
					loadingMessage:'加载中...',
					modal : true,
					resizable:true,
					title : '图表',
					onClose : function() {
						$(this).dialog('destroy');
					},
					onLoad : function() {
						
					},
					buttons:[{
						text : '导出',
						iconCls : 'icon-save',
						handler : function() {
							var dealerId = $('#dealerId_search').combobox('getValue') == undefined ? '' : $('#dealerId_search').combobox('getValue');
							var modelName = $('#vehicleModel_search').combobox('getValue') == undefined ? '' : $('#vehicleModel_search').combobox('getValue');
							var vehicleCode = $('#vehicleCode_search').combobox('getValue') == '全部' ? '' : $('#vehicleCode_search').combobox('getValue');
							var vehicleArg = $('#vehicleArg_search').combobox('getValue') == '全部' ? '' : $('#vehicleArg_search').combobox('getValue');
							window.open(encodeURI(encodeURI("report/statistic_downloadChart4WorkedYears.action?dealerId="+dealerId+"&modelName="+modelName+"&vehicleCode="+vehicleCode+"&vehicleArg="+vehicleArg)));
					    }
					},{
						text : '关闭',
						iconCls : 'icon-cancel',
						handler : function() {
							var d = $(this).closest('.window-body');
							d.dialog('destroy');  
					       }
					}]
				});
}

function workedYearsCellClick(index, field, value) {
	if (value > 0) {
		showDetail(index, field);
	}
}

// 显示详细的机械工作时间段信息
function showDetail(index, field){
	var startYears = 0;
	var endYears = 1;
	if (field == 'two') {
		startYears = 1;
		endYears = 2;
	} else if (field == 'three') {
		startYears = 2;
		endYears = 3;
	} else if (field == 'four') {
		startYears = 3;
		endYears = 4;
	} else if (field == 'five') {
		startYears = 4;
		endYears = 5;
	} else if (field == 'six') {
		startYears = 5;
		endYears = '';
	}
	var options = $("#worked_years_detail_datagrid").datagrid("options");
	var dealerId = $('#dealerId_search').combobox('getValues').join(',');
	var modelName = $('#vehicleModel_search').combobox('getValue') == undefined ? '' : $('#vehicleModel_search').combobox('getValue');
	var vehicleCode = $('#vehicleCode_search').combobox('getValue') == '全部' ? '' : $('#vehicleCode_search').combobox('getValue');
	var vehicleArg = $('#vehicleArg_search').combobox('getValue') == '全部' ? '' : $('#vehicleArg_search').combobox('getValue');
	var row = $('#workedYearsStatis_datagrid').datagrid('getRows')[index];
	if(modelName == '' && vehicleCode == '') {
		modelName = row.typeId;
	}
	var params={    
		dealerId: dealerId,
		modelName: modelName,
		vehicleCode: vehicleCode,
		vehicleArg: vehicleArg,
		startYears:startYears,
		endYears: endYears
	};
	options.queryParams=params;
	if(!options.url){
		options.url="report/statistic_getWorkedYearsDetailPage.action";
		$("#worked_years_detail_datagrid").datagrid(options) ;    
	}else{
		$('#worked_years_detail_datagrid').datagrid('load'); 
	}
	$('#dlg_worked_years_detail').dialog('open');
}

//转换时间段属性
function formatterTimeQuantum(val,row,index){
	var str='';
	if(val<1){
	  str='1年以下';
	}else if(val<2&&val>=1){
	  str='1-2年';
	}else if(val<3&&val>=2){
	  str='2-3年';
	}else if(val<4&&val>=3){
	  str='3-4年';
	}else if(val<5&&val>=4){
	  str='4-5年';
	}else if(val<6&&val>=5){
	  str='5-6年';
	}else if(val<7&&val>=6){
	  str='6-7年';
	}else if(val<8&&val>=7){
	  str='7-8年';
	}else{
	   str='8年以上';
	}
	return str;
}

//机械数量链接
function formatterVcount(val,row,index){
	var str='<a style="color: blue;text-decoration: underline;cursor: pointer;" onclick="showDetail(\''+row.YEARS+'\')">'+val+'</a>';
	return str;
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

//机械配置联动
$('#vehicleCode_search').combobox({
	onSelect:function(){
		var obj = $('#vehicleCode_search').combobox('getValue');
		$("#vehicleArg_search").empty();
		$.get(
			"vehicle/vehicleModel_getList2.action",
			{obj: obj},
			function(data) {
				var dataJson = $.parseJSON(data);
				var datas = [{'vehicleArg':'全部'}];
				for(i=0;i<dataJson.arg.length;i++){
					var docNameInfo = dataJson.arg[i].vehicleArg;
					datas.push({'vehicleArg': docNameInfo});
				}
				$('#vehicleArg_search').combobox('clear').combobox('loadData', datas).combobox('select', '全部');
			}
		);
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