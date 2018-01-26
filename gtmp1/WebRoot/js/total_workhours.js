//窗口大小变化
function totalWorkHoursResize(w,h){
	if($('#tab_total_workhours')){
		try{
		 var tab = $('#tab_total_workhours').tabs('getSelected');
		 var index=$('#tab_total_workhours').tabs('getTabIndex',tab);; 
		 if (tab) {
			 switch(index){
			   case 0:
					if($('#total_workhours_datagrid')){
						try{
						  $('#total_workhours_datagrid').datagrid('options');
						  $('#total_workhours_datagrid').datagrid('resize', {  
								width : w-2 ,
								height:h-30
						 }); 
						}catch(e){
						}
					}
					return;
			  case 1:
				  if($('#total_workhours_detail2_datagrid')){
						try{
						  $('#total_workhours_detail2_datagrid').datagrid('options');
						  $('#total_workhours_detail2_datagrid').datagrid('resize', {  
								width : w-2 ,
								height:h-30
						 }); 
						}catch(e){
						}
					}
					return;
			  case 2:
				  if($('#daily_workhours_datagrid')){
						try{
						  $('#daily_workhours_datagrid').datagrid('options');
						  $('#daily_workhours_datagrid').datagrid('resize', {  
								width : w-2 ,
								height:h-30
						 }); 
						}catch(e){
						}
					}
					return;
			 }
			 }
		}catch (e) {
			// TODO: handle exception
		}
	}
}
//查询
function querytotalWorkHours(){	
	var vehicleCode = $('#vehicleCode_search').val();
	var vehicleArg = $('#vehicleArg_search').val();
	if(vehicleCode == '全部' || vehicleCode == null){
		vehicleCode="";
	}
	if(vehicleArg== '全部' || vehicleArg == null){
		vehicleArg="";
	}
	var param={
			 modelId:$('#modelName_search').combobox('getValue'),	//机械型号
			 dealerId:$('#dealerId_search').combobox('getValues').join(','),			//经销商
	         vehicleStatus:$('#status').combobox('getValue'),		//机械状态组
	         vehicleCode:vehicleCode,		//机械代号
	         vehicleArg:vehicleArg				//机械配置
	};
		try{
        			if($('#total_workhours_datagrid').datagrid('options').url){
        			  $('#total_workhours_datagrid').datagrid('load', param);
        			}else{
        			  $('#total_workhours_datagrid').datagrid({
		        		  url:'report/workhour_totalWorkhour.action',
		        		  queryParams:param
		        		});
        			}
        		}catch(e){
        			//初始化datagrid
        		$('#total_workhours_datagrid').datagrid({
        		  url:'report/workhour_totalWorkhour.action',
        		  queryParams:param
        		});
        		}
	
}
/**
 * 显示详细的机械累计工作时间信息
 * @param {} typeId
 * @param {} month
 * @param {} year
 */
function vehicleCountformatter(val,row,index){
	 var str1='<a style="color:blue;" href="javascript:void(0)" onclick="showDetail(\''+row.DEALERID+'\',\''+row.MODELID+'\',\''+row.VEHICLECODE+'\',\''+row.VEHICLEARG+'\')">'+val+'</a>';
	return str1;
}
function showDetail(dealerId,modelId,vehicleCode,vehicleArg){
	 var options = $("#total_workhours_detail_datagrid").datagrid("options"); 
	 if(vehicleCode=="undefined"){
		 vehicleCode = "";
	 }
	 if(vehicleArg=="undefined"){
		 vehicleArg = "";
	 }
	  var params={ 
				 modelId:modelId,
				 dealerId:dealerId,
				 vehicleCode:vehicleCode,
				 vehicleArg:vehicleArg,
		         vehicleStatus:$('#status').combobox('getValue')//机械状态组
			};
		 options.queryParams=params;
	  if(!options.url){
	    options.url="report/workhour_totalWorkhourDetail.action";
	    $("#total_workhours_detail_datagrid").datagrid(options) ;    
	  }else{
	     $('#total_workhours_detail_datagrid').datagrid('load'); 
	  
	  }
	   $('#dlg_total_workhours_detail').dialog('open');
}
//查询第二个tab的明细
function querytotalWorkHoursDetail(){
	var vehicleCode = $('#vehicleCode2_search').val();
	var vehicleArg = $('#vehicleArg2_search').val();
	if(vehicleCode == '全部' || vehicleCode == null){
		vehicleCode="";
	}
	if(vehicleArg== '全部' || vehicleArg == null){
		vehicleArg="";
	}
	var param={
			 modelId:$('#modelName2_search').combobox('getValue'),
			 vehicleCode:vehicleCode, 			//机械代号
	         vehicleArg:vehicleArg,			//机械配置
			 dealerId:$('#dealerId2_search').combobox('getValues').join(','),
	         vehicleStatus:$('#status2').combobox('getValue'),		//机械状态组
	         beginWorkhour:$('#totalWorkingHours').val(),
	         endWorkhour:$('#totalWorkingHours2').val()
	};
		try{
        			if($('#total_workhours_detail2_datagrid').datagrid('options').url){
        			  $('#total_workhours_detail2_datagrid').datagrid('load', param);
        			}else{
        			  $('#total_workhours_detail2_datagrid').datagrid({
		        		  url:'report/workhour_totalWorkhourDetail.action',
		        		  queryParams:param
		        		});
        			}
        		}catch(e){
        			//初始化datagrid
        		$('#total_workhours_detail2_datagrid').datagrid({
        		  url:'report/workhour_totalWorkhour.action',
        		  queryParams:param
        		});
        		}
	
}
//---------第三个tab--begin
//查询每日工作时间
function queryDailyWorkHours(){
	var vehicleCode = $('#vehicleCode3_search').val();
	var vehicleArg = $('#vehicleArg3_search').val();
	if(vehicleCode == '全部' || vehicleCode == null){
		vehicleCode="";
	}
	if(vehicleArg== '全部' || vehicleArg == null){
		vehicleArg="";
	}
	var param={
			 vehicleDef:$('#vehicleDef_search').val(),
			 modelId:$('#modelName3_search').combobox('getValue'),
			 vehicleCode:vehicleCode, 			//机械代号
	         vehicleArg:vehicleArg, 			//机械配置
			 dealerId:$('#dealerId3_search').combobox('getValues').join(','),
	         vehicleStatus:$('#status3').combobox('getValue'),//机械状态组
	         beginTime:$('#start_time').val(),
	         endTime:$('#end_time').val()
	};
		try{
       			if($('#daily_workhours_datagrid').datagrid('options').url){
       			  $('#daily_workhours_datagrid').datagrid('load', param);
       			}else{
       			  $('#daily_workhours_datagrid').datagrid({
		        		  url:'report/workhour_dailyWorkhour.action',
		        		  queryParams:param
		        		});
       			}
       		}catch(e){
       			//初始化datagrid
       		$('#daily_workhours_datagrid').datagrid({
       		  url:'report/workhour_dailyWorkhour.action',
       		  queryParams:param
       		});
       		}
	
}
/**
 * 显示详细的机械明日工作时间信息
 * @param {} typeId
 * @param {} month
 * @param {} year
 */
function dailyWorkhoursformatter(val,row,index){
	 var str1='<a style="color:blue;" href="javascript:void(0)" onclick="showDetailDailyHours(\''+row.VEHICLEDEF+'\')">'+val+'</a>';
	return str1;
}
function showDetailDailyHours(vehicleDef){
	$("#vehicledef_daily").val(vehicleDef);
	 var options = $("#daily_workhours_detail_datagrid").datagrid("options"); 
	 var params={ 
			     vehicleDef:vehicleDef,
			     beginTime:$('#start_time').val(),
		         endTime:$('#end_time').val()
			};
		 options.queryParams=params;
	  if(!options.url){
	    options.url="report/workhour_dailyWorkhourDetail.action";
	    $("#daily_workhours_detail_datagrid").datagrid(options) ;    
	  }else{
	     $('#daily_workhours_detail_datagrid').datagrid('load'); 
	  
	  }
	   $('#dlg_daily_workhours_detail').dialog('open');
}

//机械型号联动
$('#modelName_search').combobox({
onSelect:function(){
var obj = $('#modelName_search').combobox('getValue');
 	$("#vehicleCode_search").empty();
 	$("#vehicleArg_search").empty();
	$.post("report/workhour_modelNameSearch.action", {
		obj:obj
		}, function(data) {
			var obj = $.parseJSON(data);
			for(i=0;i<obj.code.length;i++){
					 var docNameInfo = obj.code[i].vehicleCode;
				 $("#vehicleCode_search").append("<option value='"+docNameInfo+"'>"+docNameInfo+"</option>");
			}
		});
}
});

//机械配置联动
function argSel(){
var obj = $('#vehicleCode_search').val();
 	$("#vehicleArg_search").empty();
	$.post("report/workhour_codeSearch.action", {
		obj:obj
		}, function(data) {
			var obj = $.parseJSON(data);
			for(i=0;i<obj.arg.length;i++){
					 var docNameInfo = obj.arg[i].vehicleArg;
				 $("#vehicleArg_search").append("<option value='"+docNameInfo+"'>"+docNameInfo+"</option>");
			}
		});
}

//机械型号2联动
$('#modelName2_search').combobox({
onSelect:function(){
var obj = $('#modelName2_search').combobox('getValue');
 	$("#vehicleCode2_search").empty();
 	$("#vehicleArg2_search").empty();
	$.post("report/workhour_modelNameSearch.action", {
		obj:obj
		}, function(data) {
			var obj = $.parseJSON(data);
			for(i=0;i<obj.code.length;i++){
					 var docNameInfo = obj.code[i].vehicleCode;
				 $("#vehicleCode2_search").append("<option value='"+docNameInfo+"'>"+docNameInfo+"</option>");
			}
		});
}
});

//机械配置2联动
function argSel2(){
var obj = $('#vehicleCode2_search').val();
 	$("#vehicleArg2_search").empty();
	$.post("report/workhour_codeSearch.action", {
		obj:obj
		}, function(data) {
			var obj = $.parseJSON(data);
			for(i=0;i<obj.arg.length;i++){
					 var docNameInfo = obj.arg[i].vehicleArg;
				 $("#vehicleArg2_search").append("<option value='"+docNameInfo+"'>"+docNameInfo+"</option>");
			}
		});
}

//机械型号3联动
$('#modelName3_search').combobox({
onSelect:function(){
var obj = $('#modelName3_search').combobox('getValue');
 	$("#vehicleCode3_search").empty();
 	$("#vehicleArg3_search").empty();
	$.post("report/workhour_modelNameSearch.action", {
		obj:obj
		}, function(data) {
			var obj = $.parseJSON(data);
			for(i=0;i<obj.code.length;i++){
					 var docNameInfo = obj.code[i].vehicleCode;
				 $("#vehicleCode3_search").append("<option value='"+docNameInfo+"'>"+docNameInfo+"</option>");
			}
		});
}
});

//机械配置2联动
function argSel3(){
var obj = $('#vehicleCode3_search').val();
 	$("#vehicleArg3_search").empty();
	$.post("report/workhour_codeSearch.action", {
		obj:obj
		}, function(data) {
			var obj = $.parseJSON(data);
			for(i=0;i<obj.arg.length;i++){
					 var docNameInfo = obj.arg[i].vehicleArg;
				 $("#vehicleArg3_search").append("<option value='"+docNameInfo+"'>"+docNameInfo+"</option>");
			}
		});
}
function summaryDownExcel(){
	var modelId=$('#modelName_search').combobox('getValue');	//机械型号	
	var dealerId=$('#dealerId_search').combobox('getValues').join(',');		//经销商
	var vehicleStatus=$('#status').combobox('getValue');		//机械状态组
	var vehicleCode = $('#vehicleCode_search').val();
	var vehicleArg = $('#vehicleArg_search').val();
	if(vehicleCode == '全部' || vehicleCode == null){
		vehicleCode="";
	}
	if(vehicleArg== '全部' || vehicleArg == null){
		vehicleArg="";
	}
	window.location.href = encodeURI(encodeURI("report/workhour_summaryDownExcel.action?" 
			+"modelId=" + modelId 
			+ "&dealerId=" + dealerId 
			+ "&vehicleStatus=" + vehicleStatus 
			+ "&vehicleCode=" + vehicleCode
			+ "&vehicleArg=" + vehicleArg 
			));
	return;
}
function detailedDownExcel(){
	var modelId = $('#modelName2_search').combobox('getValue');
	var vehicleCode = $('#vehicleCode2_search').val(); 			//机械代号
	var vehicleArg = $('#vehicleArg2_search').val(); 			//机械配置
	var dealerId = $('#dealerId2_search').combobox('getValues').join(',');
	var vehicleStatus = $('#status2').combobox('getValue');		//机械状态组
	var beginWorkhour = $('#totalWorkingHours').val();
	var endWorkhour = $('#totalWorkingHours2').val();
	if(vehicleCode == '全部' || vehicleCode == null){
		vehicleCode="";
	}
	if(vehicleArg== '全部' || vehicleArg == null){
		vehicleArg="";
	}
	window.location.href = encodeURI(encodeURI("report/workhour_detailedDownExcel.action?" 
			+ "modelId=" + modelId 
			+ "&dealerId=" + dealerId 
			+ "&vehicleStatus=" + vehicleStatus 
			+ "&vehicleCode=" + vehicleCode
			+ "&vehicleArg=" + vehicleArg 
			+ "&beginWorkhour=" + beginWorkhour
			+ "&endWorkhour=" + endWorkhour
	));
	return;
}
function dayDownExcel(){
	 var vehicleDef = $('#vehicleDef_search').val();
	 var modelId = $('#modelName3_search').combobox('getValue');
	 var dealerId = $('#dealerId3_search').combobox('getValues').join(',');
	 var vehicleStatus = $('#status3').combobox('getValue');
	 var beginTime = $('#start_time').val();
	 var endTime = $('#end_time').val();
	 var vehicleCode = $('#vehicleCode3_search').val();
	 var vehicleArg = $('#vehicleArg3_search').val();
	 if(vehicleCode == '全部' || vehicleCode == null){
			vehicleCode="";
		}
	if(vehicleArg== '全部' || vehicleArg == null){
			vehicleArg="";
		}

     window.location.href = encodeURI(encodeURI("report/workhour_dayDownExcel.action?" 
 			+ "modelId=" + modelId 
 			+ "&dealerId=" + dealerId 
 			+ "&vehicleStatus=" + vehicleStatus 
 			+ "&vehicleCode=" + vehicleCode
 			+ "&vehicleArg=" + vehicleArg 
 			+ "&beginTime=" + beginTime
 	        + "&endTime=" + endTime
 	        + "&vehicleDef=" + vehicleDef
 	));
 	return;
}

function dayDetailDownExcel(){
	var vehicleDef = $("#vehicledef_daily").val();
	var beginTime=$('#start_time').val();
    var endTime=$('#end_time').val();
	 window.location.href = encodeURI(encodeURI("report/workhour_dayDetailDownExcel.action?" 
	 			+ "vehicleDef=" + vehicleDef  
	 			+ "&beginTime=" + beginTime
	 	        + "&endTime=" + endTime
	 	));
	 	return;
}


$('#dealerId_search').combotree({
    onCheck: function(node, checked){
        $('#dealerId_search').combotree('tree').tree('expand',node.target);
    },
    onExpand:function(node){
      $('#dealerId_search').combotree('tree').tree('check',node.target);
    }
});
$('#dealerId2_search').combotree({
    onCheck: function(node, checked){
        $('#dealerId2_search').combotree('tree').tree('expand',node.target);
    },
    onExpand:function(node){
      $('#dealerId2_search').combotree('tree').tree('check',node.target);
    }
});
$('#dealerId3_search').combotree({
    onCheck: function(node, checked){
        $('#dealerId3_search').combotree('tree').tree('expand',node.target);
    },
    onExpand:function(node){
      $('#dealerId3_search').combotree('tree').tree('check',node.target);
    }
});


















