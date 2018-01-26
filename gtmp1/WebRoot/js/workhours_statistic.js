//窗口大小变化
function workhoursQueryResize(w,h){
	if($('#workhours_statistic_datagrid')){
		try{
		  $('#workhours_statistic_datagrid').datagrid('options');
		  $('#workhours_statistic_datagrid').datagrid('resize', {  
				width : w-2 ,
				height:h
		 }); 
		}catch(e){
		}
	}
}
//查询
function statistic_workhours(){
	 //var reportType=$('input:radio[name="report_type"]:checked').val();
	var reportType=null;
	  var startDateStr=null,endDateStr=null;
	 var start = $('#start_time2').val();
	 var intStart = start.replace("-","");
	 var end = $('#end_time2').val();
	 var intEnd = end.replace("-","");
	 if((intEnd - intStart)== 1){
	 	reportType = 1;
	 	if(intStart.substring(4)=='01'){
	 		startDateStr = intStart.substring(0,4)+"-"+01+"-"+01;
	 		endDateStr = intStart.substring(0,4)+"-"+01+"-"+31;
	 	}
	 	if(intStart.substring(4)=='02'){
	 		startDateStr = intStart.substring(0,4)+"-"+02+"-"+01;
	 		endDateStr = intStart.substring(0,4)+"-"+02+"-"+28;
	 	}
	    if(intStart.substring(4)=='03'){
	 		startDateStr = intStart.substring(0,4)+"-"+03+"-"+01;
	 		endDateStr = intStart.substring(0,4)+"-"+03+"-"+31;
	 	}
	 	if(intStart.substring(4)=='04'){
	 		startDateStr = intStart.substring(0,4)+"-"+04+"-"+01;
	 		endDateStr = intStart.substring(0,4)+"-"+04+"-"+30;
	 	}
	 	if(intStart.substring(4)=='05'){
	 		startDateStr = intStart.substring(0,4)+"-"+05+"-"+01;
	 		endDateStr = intStart.substring(0,4)+"-"+05+"-"+31;
	 	}
	 	if(intStart.substring(4)=='06'){
	 		startDateStr = intStart.substring(0,4)+"-"+06+"-"+01;
	 		endDateStr = intStart.substring(0,4)+"-"+06+"-"+30;
	 	}
	 	if(intStart.substring(4)=='07'){
	 		startDateStr = intStart.substring(0,4)+"-"+07+"-"+01;
	 		endDateStr = intStart.substring(0,4)+"-"+07+"-"+31;
	 	}
	 	if(intStart.substring(4)=='08'){
	 		startDateStr = intStart.substring(0,4)+"-"+08+"-"+01;
	 		endDateStr = intStart.substring(0,4)+"-"+08+"-"+31;
	 	}
	 	if(intStart.substring(4)=='09'){
	 		startDateStr = intStart.substring(0,4)+"-"+09+"-"+01;
	 		endDateStr = intStart.substring(0,4)+"-"+09+"-"+30;
	 	}
	 	if(intStart.substring(4)=='10'){
	 		startDateStr = intStart.substring(0,4)+"-"+10+"-"+01;
	 		endDateStr = intStart.substring(0,4)+"-"+10+"-"+31;
	 	}
	 	if(intStart.substring(4)=='11'){
	 		startDateStr = intStart.substring(0,4)+"-"+11+"-"+01;
	 		endDateStr = intStart.substring(0,4)+"-"+11+"-"+30;
	 	}
	 	if(intStart.substring(4)=='12'){
	 		startDateStr = intStart.substring(0,4)+"-"+12+"-"+01;
	 		endDateStr = intStart.substring(0,4)+"-"+12+"-"+31;
	 	}
	 }
	 else{
	 	reportType = 2;
	 	startDateStr = start;
	 	endDateStr = end;
	 }
	 if(reportType==1){//月报
	 	//startDateStr=$('#start_time').val();
	 	//endDateStr=$('#end_time').val();
	 	 if(startDateStr&&!endDateStr){
		   $.messager.alert('提示','请选择结束时间!!');
		   return;
		 }
		if(endDateStr&&!startDateStr){
		   $.messager.alert('提示','请选择开始时间!!');
		   return;
		 }
		if(startDateStr&&endDateStr){
			var len=getDayNumber(startDateStr,endDateStr);
			if(len<0){
			    $.messager.alert('提示','结束时间必须在开始时间之后!');
		   return;
			 }else if(len >31){  
			   $.messager.alert('提示','查询日期间隔不能超过31天!');
		   		return;
			 }
		}
	 }else if(reportType==2){//年报
	 	//startDateStr= $('#start_time2').val();
		 //endDateStr= $('#end_time2').val();
		 if(startDateStr&&!endDateStr){
		   $.messager.alert('提示','请选择结束时间!!');
		   return;
		 }
		if(endDateStr&&!startDateStr){
		   $.messager.alert('提示','请选择开始时间!!');
		   return;
		 }
		if(startDateStr&&endDateStr){
			var len=getMonthNumber(startDateStr,endDateStr);
			if(len<0){
			    $.messager.alert('提示','结束时间必须在开始时间之后!');
		   return;
			 }else if(len >11){  
			   $.messager.alert('提示','查询月份时间段不能超过12个月!');
		   		return;
			 }
		}
	 }
	var type = "POST";  
    var url = "report/statistic_statisticWorkHours.action";  
    var param={
		direction:reportType,
		vehicleDef:$('#vehicleDef_search').val(),
		startDateStr:startDateStr,
		vehicleType:$('#modelName_search').combobox('getValue'),
		vehicleCode:$('#vehicleCode_search').combobox('getValue'),
		vehicleArg:$('#vehicleArg_search').combobox('getValue'),
        dealerId: $('#dealerId_search').combobox('getValues').join(','),
        vehicleStatus:$('#vehicleStatus_search').combobox('getValue'),
		endDateStr:endDateStr
    };
	 ajaxExtend(type,url,param,true,function(data){  
        var options = $("#workhours_statistic_datagrid").datagrid("options");                   //取出当前datagrid的配置     
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
        $("#workhours_statistic_datagrid").datagrid(options) ;                                        
        //$("#workhours_statistic_datagrid").datagrid("load") ;                                           //获取当前页信息  
        $("#workhours_statistic_datagrid").datagrid("loadData",data.rows);  
    }); 
}
//导出
function downExcel(){
    //var reportType=$('input:radio[name="report_type"]:checked').val();
	 var reportType=null;
	  var startDateStr=null,endDateStr=null;
	 var start = $('#start_time2').val();
	 var intStart = start.replace("-","");
	 var end = $('#end_time2').val();
	 var intEnd = end.replace("-","");
	 if((intEnd - intStart)== 1){
	 	reportType = 1;
	 	if(intStart.substring(4)=='01'){
	 		startDateStr = intStart.substring(0,4)+"-"+01+"-"+01;
	 		endDateStr = intStart.substring(0,4)+"-"+01+"-"+31;
	 	}
	 	if(intStart.substring(4)=='02'){
	 		startDateStr = intStart.substring(0,4)+"-"+02+"-"+01;
	 		endDateStr = intStart.substring(0,4)+"-"+02+"-"+28;
	 	}
	    if(intStart.substring(4)=='03'){
	 		startDateStr = intStart.substring(0,4)+"-"+03+"-"+01;
	 		endDateStr = intStart.substring(0,4)+"-"+03+"-"+31;
	 	}
	 	if(intStart.substring(4)=='04'){
	 		startDateStr = intStart.substring(0,4)+"-"+04+"-"+01;
	 		endDateStr = intStart.substring(0,4)+"-"+04+"-"+30;
	 	}
	 	if(intStart.substring(4)=='05'){
	 		startDateStr = intStart.substring(0,4)+"-"+05+"-"+01;
	 		endDateStr = intStart.substring(0,4)+"-"+05+"-"+31;
	 	}
	 	if(intStart.substring(4)=='06'){
	 		startDateStr = intStart.substring(0,4)+"-"+06+"-"+01;
	 		endDateStr = intStart.substring(0,4)+"-"+06+"-"+30;
	 	}
	 	if(intStart.substring(4)=='07'){
	 		startDateStr = intStart.substring(0,4)+"-"+07+"-"+01;
	 		endDateStr = intStart.substring(0,4)+"-"+07+"-"+31;
	 	}
	 	if(intStart.substring(4)=='08'){
	 		startDateStr = intStart.substring(0,4)+"-"+08+"-"+01;
	 		endDateStr = intStart.substring(0,4)+"-"+08+"-"+31;
	 	}
	 	if(intStart.substring(4)=='09'){
	 		startDateStr = intStart.substring(0,4)+"-"+09+"-"+01;
	 		endDateStr = intStart.substring(0,4)+"-"+09+"-"+30;
	 	}
	 	if(intStart.substring(4)=='10'){
	 		startDateStr = intStart.substring(0,4)+"-"+10+"-"+01;
	 		endDateStr = intStart.substring(0,4)+"-"+10+"-"+31;
	 	}
	 	if(intStart.substring(4)=='11'){
	 		startDateStr = intStart.substring(0,4)+"-"+11+"-"+01;
	 		endDateStr = intStart.substring(0,4)+"-"+11+"-"+30;
	 	}
	 	if(intStart.substring(4)=='12'){
	 		startDateStr = intStart.substring(0,4)+"-"+12+"-"+01;
	 		endDateStr = intStart.substring(0,4)+"-"+12+"-"+31;
	 	}
	 }
	 else{
	 	reportType = 2;
	 	startDateStr = start;
	 	endDateStr = end;
	 }
	 
	 
	 if(reportType==1){//月报
	 	//startDateStr=$('#start_time').val();
	 	//endDateStr=$('#end_time').val();
	 	 if(startDateStr&&!endDateStr){
		   $.messager.alert('提示','请选择结束时间!!');
		   return;
		 }
		if(endDateStr&&!startDateStr){
		   $.messager.alert('提示','请选择开始时间!!');
		   return;
		 }
		if(startDateStr&&endDateStr){
			var len=getDayNumber(startDateStr,endDateStr);
			if(len<0){
			    $.messager.alert('提示','结束时间必须在开始时间之后!');
		   return;
			 }else if(len >31){  
			   $.messager.alert('提示','查询日期间隔不能超过31天!');
		   		return;
			 }
		}
	 }else if(reportType==2){//年报
	 	//startDateStr= $('#start_time2').val();
		// endDateStr= $('#end_time2').val();
		 if(startDateStr&&!endDateStr){
		   $.messager.alert('提示','请选择结束时间!!');
		   return;
		 }
		if(endDateStr&&!startDateStr){
		   $.messager.alert('提示','请选择开始时间!!');
		   return;
		 }
		if(startDateStr&&endDateStr){
			var len=getMonthNumber(startDateStr,endDateStr);
			if(len<0){
			    $.messager.alert('提示','结束时间必须在开始时间之后!');
		   return;
			 }else if(len >11){  
			   $.messager.alert('提示','查询月份时间段不能超过12个月!');
		   		return;
			 }
		}
	 } 
      var dealerId =$('#dealerId_search').combobox('getValues').join(',');
      var vehicleCode = $('#vehicleCode_search').combobox('getValue') == '全部' ? '' : $('#vehicleCode_search').combobox('getValue');
      var vehicleArg = $('#vehicleArg_search').combobox('getValue') == '全部' ? '' :$('#vehicleArg_search').combobox('getValue');
      var url = "report/statistic_exportToExcelWorkHours.action?direction="
            + reportType
            + "&startDateStr="+ startDateStr
            + "&endDateStr="  + endDateStr
            + "&vehicleDef="  + $('#vehicleDef_search').val()
            + "&vehicleType=" + $('#modelName_search').combobox('getValue')
            + "&vehicleCode=" + vehicleCode
            + "&vehicleArg="  + vehicleArg
            + "&dealerId="    + dealerId
            + "&vehicleStatus="+ $('#vehicleStatus_search').combobox('getValue');
	   window.location.href = encodeURI(encodeURI(url));
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
	 //var reportType=$('input:radio[name="report_type"]:checked').val();
	 //var startDateStr=null,endDateStr=null;
	var reportType=null;
	  var startDateStr=null,endDateStr=null;
	 var start = $('#start_time2').val();
	 var intStart = start.replace("-","");
	 var end = $('#end_time2').val();
	 var intEnd = end.replace("-","");
	 if((intEnd - intStart)== 1){
	 	reportType = 1;
	 	if(intStart.substring(4)=='01'){
	 		startDateStr = intStart.substring(0,4)+"-"+01+"-"+01;
	 		endDateStr = intStart.substring(0,4)+"-"+01+"-"+31;
	 	}
	 	if(intStart.substring(4)=='02'){
	 		startDateStr = intStart.substring(0,4)+"-"+02+"-"+01;
	 		endDateStr = intStart.substring(0,4)+"-"+02+"-"+28;
	 	}
	    if(intStart.substring(4)=='03'){
	 		startDateStr = intStart.substring(0,4)+"-"+03+"-"+01;
	 		endDateStr = intStart.substring(0,4)+"-"+03+"-"+31;
	 	}
	 	if(intStart.substring(4)=='04'){
	 		startDateStr = intStart.substring(0,4)+"-"+04+"-"+01;
	 		endDateStr = intStart.substring(0,4)+"-"+04+"-"+30;
	 	}
	 	if(intStart.substring(4)=='05'){
	 		startDateStr = intStart.substring(0,4)+"-"+05+"-"+01;
	 		endDateStr = intStart.substring(0,4)+"-"+05+"-"+31;
	 	}
	 	if(intStart.substring(4)=='06'){
	 		startDateStr = intStart.substring(0,4)+"-"+06+"-"+01;
	 		endDateStr = intStart.substring(0,4)+"-"+06+"-"+30;
	 	}
	 	if(intStart.substring(4)=='07'){
	 		startDateStr = intStart.substring(0,4)+"-"+07+"-"+01;
	 		endDateStr = intStart.substring(0,4)+"-"+07+"-"+31;
	 	}
	 	if(intStart.substring(4)=='08'){
	 		startDateStr = intStart.substring(0,4)+"-"+08+"-"+01;
	 		endDateStr = intStart.substring(0,4)+"-"+08+"-"+31;
	 	}
	 	if(intStart.substring(4)=='09'){
	 		startDateStr = intStart.substring(0,4)+"-"+09+"-"+01;
	 		endDateStr = intStart.substring(0,4)+"-"+09+"-"+30;
	 	}
	 	if(intStart.substring(4)=='10'){
	 		startDateStr = intStart.substring(0,4)+"-"+10+"-"+01;
	 		endDateStr = intStart.substring(0,4)+"-"+10+"-"+31;
	 	}
	 	if(intStart.substring(4)=='11'){
	 		startDateStr = intStart.substring(0,4)+"-"+11+"-"+01;
	 		endDateStr = intStart.substring(0,4)+"-"+11+"-"+30;
	 	}
	 	if(intStart.substring(4)=='12'){
	 		startDateStr = intStart.substring(0,4)+"-"+12+"-"+01;
	 		endDateStr = intStart.substring(0,4)+"-"+12+"-"+31;
	 	}
	 }
	 else{
	 	reportType = 2;
	 	startDateStr = start;
	 	endDateStr = end;
	 }
	
	
	
	 if(reportType==1){//月报
	 	//startDateStr=$('#start_time').val();
	 	//endDateStr=$('#end_time').val();
	 	 if(startDateStr&&!endDateStr){
		   $.messager.alert('提示','请选择结束时间!!');
		   return;
		 }
		if(endDateStr&&!startDateStr){
		   $.messager.alert('提示','请选择开始时间!!');
		   return;
		 }
		if(startDateStr&&endDateStr){
			var len=getDayNumber(startDateStr,endDateStr);
			if(len<0){
			    $.messager.alert('提示','结束时间必须在开始时间之后!');
		   return;
			 }else if(len >31){  
			   $.messager.alert('提示','查询日期间隔不能超过31天!');
		   		return;
			 }
		}
	 }else if(reportType==2){//年报
	 	//startDateStr= $('#start_time2').val();
		 //endDateStr= $('#end_time2').val();
		 if(startDateStr&&!endDateStr){
		   $.messager.alert('提示','请选择结束时间!!');
		   return;
		 }
		if(endDateStr&&!startDateStr){
		   $.messager.alert('提示','请选择开始时间!!');
		   return;
		 }
		if(startDateStr&&endDateStr){
			var len=getMonthNumber(startDateStr,endDateStr);
			if(len<0){
			    $.messager.alert('提示','结束时间必须在开始时间之后!');
		   return;
			 }else if(len >11){  
			   $.messager.alert('提示','查询月份时间段不能超过12个月!');
		   		return;
			 }
		}
	 } 
	  var dealerId = $('#dealerId_search').combobox('getValues').join(',');
      var vehicleCode = $('#vehicleCode_search').combobox('getValue') == '全部' ? '' : $('#vehicleCode_search').combobox('getValue');
      var vehicleArg = $('#vehicleArg_search').combobox('getValue') == '全部' ? '' :$('#vehicleArg_search').combobox('getValue');
	  var src = encodeURI(encodeURI("report/statistic_drawChart4WorkHours.action?direction="
			+ reportType
            + "&startDateStr="+ startDateStr
            + "&endDateStr="+ endDateStr
            + "&vehicleDef="+ $('#vehicleDef_search').val()
            + "&vehicleType="+ $('#modelName_search').combobox('getValue')
            + "&vehicleCode=" + vehicleCode
            + "&vehicleArg="  + vehicleArg
            + "&dealerId="    + dealerId
            + "&vehicleStatus="+ $('#vehicleStatus_search').combobox('getValue')
            ));
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
					handler : function() {
						var vehicleCode = $('#vehicleCode_search').combobox('getValue') == '全部' ? '' : $('#vehicleCode_search').combobox('getValue');
                        var vehicleArg = $('#vehicleArg_search').combobox('getValue') == '全部' ? '' :$('#vehicleArg_search').combobox('getValue');
						//var startTime = $('#start_time').val() == '全部' ? '' : $('#start_time').val();
						//var endTime = $('#end_time').val() == '全部' ? '' : $('#end_time').val();
						var vehicleDef = $('#vehicleDef_search').val() == '全部' ? '' : $('#vehicleDef_search').val();
						window.open(encodeURI(encodeURI("report/statistic_downloadChart2.action?direction="
								+ reportType
                                + "&startDateStr="+ startDateStr
                                + "&endDateStr="+ endDateStr
                                + "&vehicleDef="+ $('#vehicleDef_search').val()
                                + "&vehicleType="+ $('#modelName_search').combobox('getValue')
                                + "&vehicleCode=" + vehicleCode
                                + "&vehicleArg="  + vehicleArg
                                + "&dealerId="    + dealerId
                                + "&vehicleStatus="+ $('#vehicleStatus_search').combobox('getValue')
                          )));
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
	if(year.length>4){//月报
		 month=month.replace('d','');
	 	 startTime.setYear(year.split('-')[0]);
		 startTime.setMonth(year.split('-')[1]-1);
		 startTime.setDate(month);
		 startTime.setHours(0);
	 	 startTime.setMinutes(0);
	 	 startTime.setSeconds(0);
	 	 
	 	 endTime.setYear(year.split('-')[0]);
		 endTime.setMonth(year.split('-')[1]-1);
		 endTime.setDate(month);
		 endTime.setHours(23);
	 	 endTime.setMinutes(59);
	 	 endTime.setSeconds(59);
	}else{//年报
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
	}
	
	  var options = $("#workhours_detail_datagrid").datagrid("options"); 
	  var params={    
	     	ownerName:typeId,
			gpsTime: startTime.formatDate("yyyy-MM-dd hh:mm:ss"),
			stamp: endTime.formatDate("yyyy-MM-dd hh:mm:ss")};
		 options.queryParams=params;
	  if(!options.url){
	    options.url="report/statistic_getTimeQuanDetailPage.action";
        //$("#workhours_statistic_datagrid").datagrid("load") ;  
	    $("#workhours_detail_datagrid").datagrid(options) ;    
	  }else{
	     $('#workhours_detail_datagrid').datagrid('load'); 
	  
	  }
	   
	   $('#dlg_workhours_detail').dialog('open');
}


function malfunnStatisCellClick(rowIndex, field, value){
	      	 	/*先注释
	      	 	 * if(value>0){
	      	 	var data=	$("#workhours_statistic_datagrid").datagrid('getData');
	      	 		showDetail(data.rows[rowIndex].typeName,field,data.rows[rowIndex].dateLabel);
	      	 	}*/ 
	      	 }
	      	 
//机械型号联动控制      
$('#modelName_search').combobox({
    onSelect:function(){
        var obj = $('#modelName_search').combobox('getValue');       
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
	      	 
$('#dealerId_search').combotree({
	onCheck: function(node, checked){
	    $('#dealerId_search').combotree('tree').tree('expand',node.target);
	},
	onExpand:function(node){
	  $('#dealerId_search').combotree('tree').tree('check',node.target);
	}
	});	      	 
	      	 
	      	 
	      	 
	      	 
	      	 
	      	 
	      	 
	      	 
	      	 
	      	 
	      	 
	      	 
	      	 
	      	 
	      	 
	      	 
	      	 
	      	 
	      	 
	      	 
	      	 