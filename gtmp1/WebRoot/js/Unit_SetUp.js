//窗口大小变化
function workhoursQueryResize(w,h){
	if($('#unit_datagrid')){
		try{
		  $('#unit_datagrid').datagrid('options');
		  $('#unit_datagrid').datagrid('resize', {  
				width : w-2 ,
				height:h
		 }); 
		}catch(e){
		}
	}
	findUnitSetUp();
}
//查询
function findUnitSetUp(){
	var type = "POST";  
    var url = "unit/unit_searchUnitSetUp.action";  
    var param={
		vehicleDef:$('#vehicleDef_search').val(),
		unitStatus:$('#unitStatus').combobox('getValue'),
		simNo:$('#simNo').val(),
		unitId: $('#unitId').val()
    };
   // alert($('#simNo').val())
    $("#unit_detail_datagrid").datagrid({   
        columns:[[   
            {field:'id',title:'编号',hidden:true},  
            {field:'vehicleDef',title:'整机编号'},  
            {field:'unitId',title:'终端编号'},
            {field:'simNo',title:'SIM号'},
            {field:'unitStatus',title:'机械状态',
            	formatter: function(value,row,index){
            		if(value==-1){
            			return "<span style='color:gray'>停机保号</span>";
            		}else if(value==1){
            			return "<span style='color:green'>开通</span>";
            		}else if(value==2){
            			return "续费";
            		}else if(value==3){
            			return "<span style='color:red'>注销</span>";
            		}
            	}
            },   
            {field:'driverName',title:'车手名称',hidden:true}, 
            {field:'driverNumber',title:'车手手机号码',hidden:true}
        ]]   
    });  
	 ajaxExtend(type,url,param,true,function(data){                 //取出当前datagrid的配置     
       var data2= $.parseJSON(data);  //获取当前页信息  
        $("#unit_detail_datagrid").datagrid("loadData",data2.rows);  
    }); 
}
//导出
function downExcel(){
	 var param="vehicleDef="+$('#vehicleDef_search').val()
				+"&unitStatus="+$('#unitStatus').combobox('getValue')
				+"&simNo="+$('#simNo').val()
				+"&unitId="+$('#unitId').val()
      var url = "unit/unit_toExcelunitSetUp.action?"+param;
	   	window.location.href = url;
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
	      	 
//新增或者编辑窗口
function addunitSetUp(){
	$('#dlg_unitSetUp').dialog('open');
 	//初始化数据(新增时)
    $('#unitSetUp_form').form('clear');
}	  
function editunitSetUp(){
    var row = $('#unit_detail_datagrid').datagrid('getSelected');
    if (row){
    	$('#dlg_unitSetUp').dialog('open');
    	$("#add_unitId").val(row.unitId);
    	$("#add_vehicleDef").val(row.vehicleDef);
    	$("#add_unitStatus").combobox('setValue',row.unitStatus);
    }else{
    	alert("请选择需要修改的数据");
    	return;
    }
}	

//新增或者更新
function saveunitSetUp(){
	 if(!$('#unitSetUp_form').form('validate')){
		 $.messager.alert(tipMsgDilag,"缺少必填项");
		     return;
   }
   //保存操作
    $.post('unit/unit_saveOrUpdateUnitSetUp.action',
		$("#unitSetUp_form").serialize(),
		function(result){
 	     	try {
				var r = $.parseJSON(result);
				if (r.success) {
					$('#dlg_unitSetUp').dialog('close');
					$('#unit_detail_datagrid').datagrid('reload');
				}
				$.messager.alert(tipMsgDilag, r.msg);
			} catch (e) {
				$.messager.alert(tipMsgDilag, '失败!');
			}
		}
	);
}
	      	 