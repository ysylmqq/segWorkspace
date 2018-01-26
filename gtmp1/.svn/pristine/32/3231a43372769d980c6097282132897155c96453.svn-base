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
function statistic_workhours_year(){
	var type = "POST";  
    var url = "report/statistic_statisticWorkHoursYear.action";  
    var param={
		vehicleDef:$('#vehicleDef_search').val(),
		yearTime:$("#year_time").val(),
		vehicleType:$('#modelName_search').combobox('getValue'),
		vehicleCode:$('#vehicleCode_search').combobox('getValue'),
		vehicleArg:$('#vehicleArg_search').combobox('getValue'),
        dealerId: $('#dealerId_search').combobox('getValues').join(','),
        vehicleStatus:$('#vehicleStatus_search').combobox('getValue')
    };
    $("#workhours_statistic_datagrid").datagrid({   
        columns:[[   
            {field:'typeName',title:'整机编号'},   
            {field:'dealerName',title:'经销商'},   
            {field:'areaName',title:'区域'}, 
            {field:'vehicleStatus',title:'机械状态'},   
            {field:'modelName',title:'机械类型'},   
            {field:'vehicleCode',title:'机械代号'},   
            {field:'vehicleArg',title:'机械配置'},   
            {field:'endYears',title:'年份'}, 
            {field:'totalTime',title:'合计工作时间',formatter:function(val,rec){ 
            	if(val<1&&val!=0){
            		return '0'+val; 
            	}else{
            		return val; 
            	}
        		}
            },  
            {field:'addTime',title:'累计工作时间',formatter:function(val,rec){ 
            	if(val<1&&val!=0){
            		return '0'+val; 
            	}else{
            		return val; 
            	}
            		
            	}
            }
        ]]   
    });  
    
	 ajaxExtend(type,url,param,true,function(data){                 //取出当前datagrid的配置     
       var data2= $.parseJSON(data);  //获取当前页信息  
        $("#workhours_statistic_datagrid").datagrid("loadData",data2.rows);  
    }); 
}
//导出
function downExcel(){
	 var param="vehicleDef="+$('#vehicleDef_search').val()
	 			+"&yearTime="+$("#year_time").val()
				+"&vehicleType="+$('#modelName_search').combobox('getValue')
				+"&vehicleCode="+$('#vehicleCode_search').combobox('getValue')
				+"&vehicleArg="+$('#vehicleArg_search').combobox('getValue')
		        +"&dealerId="+ $('#dealerId_search').combobox('getValues').join(',')
		        +"&vehicleStatus="+$('#vehicleStatus_search').combobox('getValue');
      var url = "report/statistic_exportToExcelWorkHoursYear.action?"+param;
	   	window.location.href = url;
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
	var param="vehicleDef="+$('#vehicleDef_search').val()
				+"&yearTime="+$("#year_time").val()
				+"&vehicleType="+$('#modelName_search').combobox('getValue')
				+"&vehicleCode="+$('#vehicleCode_search').combobox('getValue')
				+"&vehicleArg="+$('#vehicleArg_search').combobox('getValue')
			    +"&dealerId="+$('#dealerId_search').combobox('getValues').join(',')
			    +"&vehicleStatus="+$('#vehicleStatus_search').combobox('getValue')
			    +"&direction=2";
	  var src = "report/statistic_drawChart4WorkHours.action?"+param;
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
						/**
						var param2="vehicleDef="+$('#vehicleDef_search').val()
						+"&yearTime="+$("#year_time").val()
						+"&vehicleType="+$('#modelName_search').combobox('getValue')
						+"&vehicleCode="+$('#vehicleCode_search').combobox('getValue')
						+"&vehicleArg="+$('#vehicleArg_search').combobox('getValue')
					    +"&dealerId="+ $('#dealerId_search').combobox('getValues').join(',')
					    +"&vehicleStatus="+$('#vehicleStatus_search').combobox('getValue');
					    **/
						window.open("report/statistic_downloadChart2.action?"+param
                          );
						}
						},
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
	      	 
	      	 
	      	 
	      	 
	      	 
	      	 
	      	 
	      	 
	      	 
	      	 
	      	 
	      	 
	      	 
	      	 
	      	 
	      	 
	      	 
	      	 
	      	 
	      	 
	      	 