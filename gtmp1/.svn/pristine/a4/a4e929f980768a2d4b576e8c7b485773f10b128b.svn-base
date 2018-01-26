//窗口大小变化
function avg_workhoursQueryResize(w,h){
	if($('#avg_workhours_statistic_datagrid')){
		try{
		  $('#avg_workhours_statistic_datagrid').datagrid('options');
		  $('#avg_workhours_statistic_datagrid').datagrid('resize', {  
				width : w-2 ,
				height:h
		 }); 
		}catch(e){
		}
	}
}
//查询
function statistic_avg_workhours(){
	    /*var t = $('#dealerId').combotree('tree');
	  var nodes = t.tree('getChecked');
    var idList=[];
    for(var i=0;i<nodes.length;i++){
        if(!idList.contains(nodes[i].id)){
  	    idList.push(nodes[i].id);
        }
    }*/
    var startTime= $('#start_time').val();
	 var endTime= $('#end_time').val();
	 if(startTime&&!endTime){
	   $.messager.alert('提示','请选择结束时间!!');
	   return;
	 }
	if(endTime&&!startTime){
	   $.messager.alert('提示','请选择开始时间!!');
	   return;
	 }
	if(startTime&&endTime){
		var len=getMonthNumber(startTime,endTime);
		if(len<0){
		    $.messager.alert('提示','结束时间必须在开始时间之后!');
	   return;
		 }else if(len >11){  
		   $.messager.alert('提示','查询月份时间段不能超过12个月!');
	   		return;
		 }
	}
	
	 var type = "POST";  
    var url = "report/statistic_statisticAvgWorkHours.action";  
    var param={
   modelName:$('#modelName_search').combobox('getValue'),
   //vehicleCode:$('#vehicleCode_search').val(),//机械状态组
   //vehicleArg:$('#vehicleArg_search').val(),//机械状态组
  // dealerIds:idList,//经销商
    dealerId:$('#dealerId_search').combobox('getValues').join(','),
    vehicleStatus:$('#vehicleStatus_search').combobox('getValue'),
    startDateStr:startTime,
    endDateStr:endTime
    };
	 ajaxExtend(type,url,$.param(param,true),true,function(data){  
        var options = $("#avg_workhours_statistic_datagrid").datagrid("options");                   //取出当前datagrid的配置     
      data= $.parseJSON(data);
        //options.columns = data.columns;   //添加服务器端返回的columns配置信息 
      //为了加上formatter方法，用以下方法加载列
      var columns=data.columns[0];
      var column=null;
      options.columns[0]=[];
      for (var index = 0; index < columns.length; index++) {
      	 column=columns[index];
      	 if(!(column.field=='typeId'||column.field=='typeName'||column.field=='dateLabel')){
      	 	var field=column.field;
	      	 column.formatter=function(val,row,index,field){
	      	 	if(val>0){
	      	 		 return val;
	      	 		//return '<a style="color: blue;text-decoration: underline;cursor: pointer;">'+val+'</a>';
	      	 	  // return '<a style="color: blue;text-decoration: underline;cursor: pointer;" onclick=showDetail(\''+row.typeId+'\',\''+field+'\',\''+row.dateLabel+'\')>'+val+'</a>';
	      	 	} else{
	      	 	  return val;
	      	 	}
	      	 }
      	 }
      	 options.columns[0].push(column);
      }
      //  options.queryParams = getQueryParams("sqlConditionId");             //添加查询参数  
        $("#avg_workhours_statistic_datagrid").datagrid(options) ;                                        
        //$("#avg_workhours_statistic_datagrid").datagrid("load") ;                                           //获取当前页信息  
        $("#avg_workhours_statistic_datagrid").datagrid("loadData",data.rows);  
    }); 
}
//导出
function downExcel(){
     var startTime= $('#start_time').val();
	 var endTime= $('#end_time').val();
	 if(startTime&&!endTime){
	   $.messager.alert('提示','请选择结束时间!!');
	   return;
	 }
	if(endTime&&!startTime){
	   $.messager.alert('提示','请选择开始时间!!');
	   return;
	 }
	if(startTime&&endTime){
		var len=getMonthNumber(startTime,endTime);
		if(len<0){
		    $.messager.alert('提示','结束时间必须在开始时间之后!');
	   return;
		 }else if(len >11){  
		   $.messager.alert('提示','查询月份时间段不能超过12个月!');
	   		return;
		 }
	}
   var dealerId=$('#dealerId_search').combobox('getValues').join(',');
   var vehicleStatus=$('#vehicleStatus_search').combobox('getValue');
	   window.location.href = encodeURI(encodeURI("report/statistic_exportToExcelAvgWorkHours.action?"
			+ "modelName="+ $('#modelName_search').combobox('getValue')
			+ "&dealerId="+dealerId
            + "&vehicleStatus="+vehicleStatus
            + "&startDateStr="+ startTime 
            + "&endDateStr=" + endTime));
	   return;
}

/** 
 * ajax请求基础方法 
 * @param type 
 * @param url 
 * @param param 
 * @param async 
 * @param callback 
 */  
function ajaxExtend(type,url,param,async,callback){  
    $.ajax({  
           type: type,  
           url: url,  
           data:param,  
           async : async,  
          // dataType:"json",  
           success:function(result){  
           	callback(result);                 
           }  
    });  
}  
  
  
/** 
 * 将指定form参数转换为json对象 
 */  
function getQueryParams(conditionFormId){  
 var searchCondition = getJqueryObjById(conditionFormId).serialize();  
 var obj = {};  
    var pairs = searchCondition.split('&');  
    var name,value;  
      
    $.each(pairs, function(i,pair) {  
     pair = pair.split('=');  
     name = decodeURIComponent(pair[0]);  
     value = decodeURIComponent(pair[1]);  
       
     obj[name] =  !obj[name] ? value :[].concat(obj[name]).concat(value);              //若有多个同名称的参数，则拼接  
    });  
      
    return obj;  
}  
/*
 * 打开图表窗口
 * */
function openChartWin(){
	  var startTime= $('#start_time').val();
	 var endTime= $('#end_time').val();
	 if(startTime&&!endTime){
	   $.messager.alert('提示','请选择结束时间!!');
	   return;
	 }
	if(endTime&&!startTime){
	   $.messager.alert('提示','请选择开始时间!!');
	   return;
	 }
	if(startTime&&endTime){
		var len=getMonthNumber(startTime,endTime);
		if(len<0){
		    $.messager.alert('提示','结束时间必须在开始时间之后!');
	   return;
		 }else if(len >11){  
		   $.messager.alert('提示','查询月份时间段不能超过12个月!');
	   		return;
		 }
	}
    var dealerId=$('#dealerId_search').combobox('getValues').join(',');
    var vehicleStatus=$('#vehicleStatus_search').combobox('getValue');
	var src = encodeURI(encodeURI("report/statistic_drawChart4AvgWorkHours.action?modelName="
			+ $('#modelName_search').combobox('getValue')
            + "&vehicleStatus="+vehicleStatus
            + "&startDateStr="+ startTime 
			+ "&startDateStr="
			+ startTime + "&endDateStr=" + endTime));
$('<div/>').dialog({
					//content:'<iframe src="obd/map.jsp" id="map" name="map" frameborder="0" style="border:0;width:100%;height:99%;"></iframe>',
					content:'<img src=\''+src+'\'/>',
					width : 760,
					height : 470,
					cache:false,
					loadingMessage: '正在加载数据，请稍等片刻......',
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
					handler : function() {
						var vehicleModel = $('#modelName_search').combobox('getValue') == undefined ? '' : $('#modelName_search').combobox('getValue');
						//var vehicleCode = $('#vehicleCode_search').val() == '全部' ? '' : $('#vehicleCode_search').val();
						//var vehicleArg = $('#vehicleArg_search').val() == '全部' ? '' : $('#vehicleArg_search').val();
						var dealerId=$('#dealerId_search').combobox('getValues').join(',');
                        var vehicleStatus=$('#vehicleStatus_search').combobox('getValue');
                        var startTime = $('#start_time').val() == '全部' ? '' : $('#start_time').val();
						var endTime = $('#end_time').val() == '全部' ? '' : $('#end_time').val();
						window
						.open(encodeURI(encodeURI("report/statistic_downloadChart1.action?modelName="
								+ vehicleModel
                                + "&vehicleStatus="+vehicleStatus
                                + "&startDateStr="+ startTime 
								+ "&startDateStr="
								+ startTime
								+ "&endDateStr=" + endTime)));
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
 * 显示详细的故障信息
 * @param {} typeId
 * @param {} month
 * @param {} year
 */
function showDetail(typeId,month,year){
	 var startTime= new Date();
	 var endTime=  new Date();
	 month=month.replace('m','');
	 startTime.setYear(year);
	 startTime.setMonth(month-1);
	 startTime.setDate(1);
	 startTime.setHours(0);
	 startTime.setMinutes(0);
	 startTime.setSeconds(0);
	 
	 endTime.setYear(year);
	 endTime.setMonth(month);
	 endTime.setDate(1);
	  endTime.setHours(0);
	 endTime.setMinutes(0);
	 endTime.setSeconds(0);
	  var options = $("#avg_workhours_detail_datagrid").datagrid("options"); 
	  var params={    
	     	modelName:typeId,
			gpsTime: startTime.formatDate("yyyy-MM-dd hh:mm:ss"),
			stamp: endTime.formatDate("yyyy-MM-dd hh:mm:ss")};
		 options.queryParams=params;
	  if(!options.url){
	    options.url="report/statistic_getTimeQuanDetailPage.action";
        //$("#avg_workhours_statistic_datagrid").datagrid("load") ;  
	    $("#avg_workhours_detail_datagrid").datagrid(options) ;    
	  }else{
	     $('#avg_workhours_detail_datagrid').datagrid('load'); 
	  
	  }
	   
	   $('#dlg_avg_workhours_detail').dialog('open');
}


function malfunnStatisCellClick(rowIndex, field, value){
	      	 	/*先注释
	      	 	 * if(value>0){
	      	 	var data=	$("#avg_workhours_statistic_datagrid").datagrid('getData');
	      	 		showDetail(data.rows[rowIndex].typeId,field,data.rows[rowIndex].dateLabel);
	      	 	} */
	      	 }
	      	 
//机械型号联动
/*$('#modelName_search').combobox({
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
});*/

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

$('#dealerId').combotree({
	onCheck: function(node, checked){
	    $('#dealerId').combotree('tree').tree('expand',node.target);
	},
	onExpand:function(node){
		
	  $('#dealerId').combotree('tree').tree('check',node.target);
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
	      	 
	      	 
	      	 
	      	 
	      	 
	      	 
	      	 
	      	 
	      	 
	      	 