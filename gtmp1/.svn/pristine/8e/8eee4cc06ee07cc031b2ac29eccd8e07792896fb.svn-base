//窗口大小变化
function timeQuantumStatisResize(w,h){
	if($('#timeQuantumStatis_datagrid')){
		try{
		  $('#timeQuantumStatis_datagrid').datagrid('options');
		  $('#timeQuantumStatis_datagrid').datagrid('resize', {  
				width : w-2 ,
				height:h
		 }); 
		}catch(e){
		}
	}
}
//查询
function statistictime_quantum(){
	var type = "POST";  
    var url = "report/statistic_statisticTimeQuantum.action";  
    var param={
        dealerId:$('#dealerId_search').combobox('getValues').join(','),
        vehicleStatus:$('#vehicleStatus_search').combobox('getValue'),
        modelNameId: $('#vehicleModel_search').combobox('getValue'),
        vehicleCode: $('#vehicleCode_search').combobox('getValue') == '全部' ? '' : $('#vehicleCode_search').combobox('getValue'),
        vehicleArg: $('#vehicleArg_search').combobox('getValue') == '全部' ? '' : $('#vehicleArg_search').combobox('getValue')
    
    };
        var options = $("#timeQuantumStatis_datagrid").datagrid("options");                   //取出当前datagrid的配置     
        options.url=url;
        options.queryParams=param;
        $("#timeQuantumStatis_datagrid").datagrid(options) ;                                        
}
//导出
function downExcel(){
    var vehicleCode = $('#vehicleCode_search').combobox('getValue') == '全部' ? '' : $('#vehicleCode_search').combobox('getValue');
    var vehicleArg = $('#vehicleArg_search').combobox('getValue') == '全部' ? '' : $('#vehicleArg_search').combobox('getValue');
	var url ="report/statistic_exportToExcelTimeQuantum.action?"
            + "dealerId="+$('#dealerId_search').combobox('getValues').join(',')
            + "&vehicleStatus="+ $('#vehicleStatus_search').combobox('getValue')
            + "&modelNameId=" + $('#vehicleModel_search').combobox('getValue')
            + "&vehicleCode=" + vehicleCode
            + "&vehicleArg="  + vehicleArg  
    window.location.href = encodeURI(encodeURI(url));
	   return;
}
/*
 * 打开图表窗口
 * */
function openChartWin(){
	var vehicleCode = $('#vehicleCode_search').combobox('getValue') == '全部' ? '' : $('#vehicleCode_search').combobox('getValue');
    var vehicleArg = $('#vehicleArg_search').combobox('getValue') == '全部' ? '' : $('#vehicleArg_search').combobox('getValue');	 
	var src = encodeURI(encodeURI("report/statistic_drawChart4TimeQuantum.action?"
			+ "dealerId="+$('#dealerId_search').combobox('getValues').join(',')
            + "&vehicleStatus="+ $('#vehicleStatus_search').combobox('getValue')
            + "&modelNameId=" + $('#vehicleModel_search').combobox('getValue')
            + "&vehicleCode=" + vehicleCode
            + "&vehicleArg="  + vehicleArg));
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
					buttons:[
						{
					text : '导出',
					iconCls : 'icon-save',
					handler : function() 
						{
						var vehicleCode = $('#vehicleCode_search').combobox('getValue') == '全部' ? '' : $('#vehicleCode_search').combobox('getValue');
                        var vehicleArg = $('#vehicleArg_search').combobox('getValue') == '全部' ? '' : $('#vehicleArg_search').combobox('getValue');
						window.open(encodeURI(encodeURI("report/statistic_downloadChart.action?"
								+ "dealerId="+$('#dealerId_search').combobox('getValues').join(',')
                        + "&vehicleStatus="+ $('#vehicleStatus_search').combobox('getValue')
                        + "&modelNameId=" + $('#vehicleModel_search').combobox('getValue')
                        + "&vehicleCode=" + vehicleCode
                        + "&vehicleArg="  + vehicleArg)));
						}},
				{
						text : '关闭',
						iconCls : 'icon-cancel',
						handler : function() {
							var d = $(this).closest('.window-body');
							d.dialog('destroy');  
					       }
					}]
				});
}
/**
 * 显示详细的机械工作时间段信息
 * @param {} typeId
 * @param {} month
 * @param {} year
 */

function showDetail(objType,timeType){
	  var options = $("#time_quantum_detail_datagrid").datagrid("options"); 
	  var params={    
			//vehicleDef: $('#dealerId_search').combobox('getValue'),
			//direction: timeQuantum*1000,
			//speed: (parseInt(timeQuantum)+1)*1000,
	  		//dealerId:$('#dealerId_search').combobox('getValue'),
			modelName:objType, 
            vehicleCode: $('#vehicleCode_search').combobox('getValue') == '全部' ? '' : $('#vehicleCode_search').combobox('getValue'),
            vehicleArg: $('#vehicleArg_search').combobox('getValue') == '全部' ? '' : $('#vehicleArg_search').combobox('getValue'),   
    		dealerId:$('#dealerId_search').combobox('getValues').join(','),
            vehicleStatus:$('#vehicleStatus_search').combobox('getValue'),
            workTime:timeType 
			};
		 options.queryParams=params;
	  if(!options.url){
	    options.url="report/statistic_getTimeQuanDetailPage.action";
	    $("#time_quantum_detail_datagrid").datagrid(options) ;    
	  }else{
	     $('#time_quantum_detail_datagrid').datagrid('load'); 
	  
	  }
	   $('#dlg_time_quantum_detail').dialog('open');
}

//转换时间段属性
function formatterTimeQuantum(val,row,index){
	//var workTime = $('#workTime_search').val();
	//var str='';
	//if(val<1){
	//  str='1000以下';
	//}else if(val<2&&val>=1){
	//  str='1000-2000';
	//}else if(val<3&&val>=2){
	//  str='2000-3000';
	//}else if(val<4&&val>=3){
	//  str='3000-4000';
	//}else if(val<5&&val>=4){
	//  str='4000-5000';
	//}else if(val<6&&val>=5){
	//  str='5000-6000';
	//}else if(val<7&&val>=6){
	//  str='6000-7000';
	//}else if(val<8&&val>=7){
	//  str='7000-8000';
	//}else{
	//   str='7000-8000以上';
	//}
	return workTime;
}



//机械数量链接
/*function formatterVcount(val,row,index){
	var obj = row.timeQuantum;
	var str;
		if(obj=='100'){
		str = 100;
	}
		if(obj=='100-200'){
		str = 200;
	}
		if(obj=='200-500'){
		str = 500;
	}
		if(obj=='500-1000'){
		str = 1000;
	}
		if(obj=='1000-2000'){
		str = 2000;
	}
		if(obj=='2000-3000'){
		str = 3000;
	}
		if(obj=='3000以上'){
		str = 3001;
	}
	var str='<a style="color: blue;text-decoration: underline;cursor: pointer;" onclick=showDetail('+str+')>'+val+'</a>';
	return str;
}
*/
function formatterVcount1(val,row,index){
	var vehicleType =row.modelName;
	var timeType = 100;
	var str=val;
    if(val>0)
    str = '<a style="color: blue;text-decoration: underline;cursor: pointer;" onclick=showDetail("'+vehicleType+'",'+timeType+')>'+val+'</a>';
	return str;
}
function formatterVcount2(val,row,index){
	var vehicleType =row.modelName;
	var timeType = 200;
	var str=val;
    if(val>0)
    str = '<a style="color: blue;text-decoration: underline;cursor: pointer;" onclick=showDetail("'+vehicleType+'",'+timeType+')>'+val+'</a>';
	return str;
}
function formatterVcount3(val,row,index){
	var vehicleType =row.modelName;
	var timeType = 500;
	var str=val;
    if(val>0)
    str = '<a style="color: blue;text-decoration: underline;cursor: pointer;" onclick=showDetail("'+vehicleType+'",'+timeType+')>'+val+'</a>';
	return str;
}
function formatterVcount4(val,row,index){
	var vehicleType =row.modelName;
	var timeType = 1000;
	var str=val;
    if(val>0)
    str = '<a style="color: blue;text-decoration: underline;cursor: pointer;" onclick=showDetail("'+vehicleType+'",'+timeType+')>'+val+'</a>';
	return str;
}
function formatterVcount5(val,row,index){
	var vehicleType =row.modelName;
	var timeType = 2000;
	var str=val;
    if(val>0)
    str = '<a style="color: blue;text-decoration: underline;cursor: pointer;" onclick=showDetail("'+vehicleType+'",'+timeType+')>'+val+'</a>';
	return str;
}
function formatterVcount6(val,row,index){
	var vehicleType =row.modelName;
	var timeType = 3000;
	var str=val;
    if(val>0)
    str = '<a style="color: blue;text-decoration: underline;cursor: pointer;" onclick=showDetail("'+vehicleType+'",'+timeType+')>'+val+'</a>';
	return str;
}
function formatterVcount7(val,row,index){
	var vehicleType =row.modelName;
	var timeType = 3001;
	var str=val;
    if(val>0)
    str = '<a style="color: blue;text-decoration: underline;cursor: pointer;" onclick=showDetail("'+vehicleType+'",'+timeType+')>'+val+'</a>';
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











