//窗口大小变化
function distributeResize(w,h){
	if($('#distribute_datagrid')){
		try{
		  $('#distribute_datagrid').datagrid('options');
		  $('#distribute_datagrid').datagrid('resize', {  
				width : w-2 ,
				height:h
		 }); 
		}catch(e){
		}
	}
}
//查询
function statisticDistribute(){
    var t = $('#dealerId').combotree('tree');
	  var nodes = t.tree('getChecked');
    var idList=[];
    for(var i=0;i<nodes.length;i++){
        if(!idList.contains(nodes[i].id)){
  	    idList.push(nodes[i].id);
        }
    }
    var params={    
     modelName: $('#vehicleModel_search').combobox('getValue') == '全部' ? '' : $('#vehicleModel_search').combobox('getValue'),
 	 vehicleCode: $('#vehicleCode_search').combobox('getValue') == '全部' ? '' : $('#vehicleCode_search').combobox('getValue'),
 	 vehicleArg: $('#vehicleArg_search').combobox('getValue') == '全部' ? '' : $('#vehicleArg_search').combobox('getValue'),
 	 direction:$('#status').combobox('getValue')== '全部' ? '': $('#status').combobox('getValue'),
     //province:$('#province').combobox('getValue'),
     //city:$('#city').combobox('getValue'),
     dealerIds:idList,
     locationState:1
    };
    //查询各个省份的机械分布数量
    ifm_map_distribute.window.initProvinceCount(params,userlon,userlat,2);                                    
}
//导出
function downExcel(){
	var t = $('#dealerId').combotree('tree');
	var nodes = t.tree('getChecked');
	var idList=[];
	for(var i=0;i<nodes.length;i++){
      if(!idList.contains(nodes[i].id)){
	    idList.push(nodes[i].id);
      }
	}
	var modelName=$('#vehicleModel_search').combobox('getValue') == '全部' ? '': $('#vehicleModel_search').combobox('getValue');
    var vehicleCode=$('#vehicleCode_search').combobox('getValue') == '全部' ? '' : $('#vehicleCode_search').combobox('getValue');
	var vehicleArg=$('#vehicleArg_search').combobox('getValue') == '全部' ? '' : $('#vehicleArg_search').combobox('getValue');
	var direction=$('#status').combobox('getValue')== '全部' ? '': $('#status').combobox('getValue');
	var dealerIds=idList;
   	window.location.href=encodeURI(encodeURI("report/statistic_exportToExcelDistribute.action?modelName="+modelName+"&dealerId="+idList.join(',')+"&direction="+direction+"&vehicleCode="+vehicleCode+"&vehicleArg="+vehicleArg));
	   return;
}
/*
 * 打开图表窗口
 * */
function openChartWin(){
	var src=encodeURI(encodeURI("report/statistic_drawChart4Distribute.action?modelName="+$('#vehicleModel_search').combobox('getValue')));
$('<div/>').dialog({
					//content:'<iframe src="obd/map.jsp" id="map" name="map" frameborder="0" style="border:0;width:100%;height:99%;"></iframe>',
					content:'<img src=\''+src+'\'/>',
					width : 760,
					height : 470,
					modal : true,
					resizable:true,
					title : '图表',
					onClose : function() {
						$(this).dialog('destroy');
					},
					onLoad : function() {
						
					},
					buttons:[{
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
function showDetail(city,modelName,dealerId,status){
	  var options = $("#distribute_detail_datagrid").datagrid("options"); 
	  var t = $('#dealerId').combotree('tree');
	  var nodes = t.tree('getChecked');
    var idList=[];
    for(var i=0;i<nodes.length;i++){
        if(!idList.contains(nodes[i].id)){
  	    idList.push(nodes[i].id);
        }
    }
    idList.join(',');
	  var params={    
			city:city,
			modelId: $('#vehicleModel_search').combobox('getValue') == '全部' ? '': $('#vehicleModel_search').combobox('getValue'),
			vehicleCode: $('#vehicleCode_search').combobox('getValue') == '全部' ? '' : $('#vehicleCode_search').combobox('getValue'),
			vehicleArg: $('#vehicleArg_search').combobox('getValue') == '全部' ? '' : $('#vehicleArg_search').combobox('getValue'),
		    speed:$('#status').combobox('getValue')== '全部' ? '': $('#status').combobox('getValue'),
					//province:$('#province').combobox('getValue'),
		     //city:$('#city').combobox('getValue'),
		    dealerIds:idList,
			dealerId:dealerId
			
			};
		 options.queryParams=params;
	  if(!options.url){
	    options.url="report/statistic_getUnUploadByPage.action";
	    $("#distribute_detail_datagrid").datagrid(options) ;    
	  }else{
	     $('#distribute_detail_datagrid').datagrid('load'); 
	  
	  }
	   $('#dlg_distribute_detail').dialog('open');
}

//省市级联
function provinceChange(rec){
	 $('#city').combobox('reload', encodeURI(encodeURI("sys/maparea_getCityList.action?provinceName="+rec.name)));
     $('#city').combobox('setValue',''); 
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

	
$('#dealerId').combotree({
	onCheck: function(node, checked){
	    $('#dealerId').combotree('tree').tree('expand',node.target);
	},
	onExpand:function(node){
		
	  $('#dealerId').combotree('tree').tree('check',node.target);
	}
});






	









