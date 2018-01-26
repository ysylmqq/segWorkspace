//表格中的操作
function operate(val,row,index){
	var modelId = row.modelId;
	var obj={modelId:modelId,
             modelName:row.modelName,
             isValid:row.isValid,
             listNo:row.listNo,
             workhoursCycle:row.workhoursCycle,
             durationCycle:row.durationCycle
             };
	obj=JSON.stringify(obj); 

	var str1 ='<a style="color:blue;" href="javascript:void(0)" onclick=openDlg4vehicle_modelOperate('+obj+')>编辑</a>';
	  str1+=' <a style="color:blue;" href="javascript:void(0)" onclick="delete_vehicle_model(\''+modelId+'\')">删除</a>';
	   return str1;
}
//查询
function queryvehicle_model(){
       $('#vehicle_model_datagrid').datagrid('load',{    
    	   modelName: $('#modelName_search').val()    
	    });  
}
//新增或者更新
function savevehicle_model(){
	$('#modelId').removeAttr("disabled");
	 if(!$('#vehicle_model_operate_form').form('validate')){
		     return;
   }
   //防重验证
          $.post('vehicle/vehicleModel_saveOrUpdate.action',
      			$("#vehicle_model_operate_form").serialize(),
    			function(result){
      	  	     try {
						var r = $.parseJSON(result);
						if (r.success) {
							$('#dlg_vehicle_model_operate').dialog('close');
							$('#vehicle_model_datagrid').datagrid('reload');
						}
						$.messager.alert(tipMsgDilag, r.msg);
					} catch (e) {
						$.messager.alert(tipMsgDilag, '失败!');
					}
    	}
    	);
}
//新增或者编辑
function openDlg4vehicle_modelOperate(obj){
	$('#dlg_vehicle_model_operate').dialog('open');
	
	//$('#vehicle_model_operate_form').form('clear');	//清空之前的参数
  $('#modelId').val('');
  //$('#modelId').removeAttr("disabled");
  if(obj){//编辑
					$('#vehicle_model_operate_form').form('load', obj);
					$('#modelId').attr("disabled","true");
  }
 
}
//删除车台，设置为无效
function delete_vehicle_model(vehicle_modelId){
	 $.messager.confirm(tipMsgDilag,"确定删除?",
							   function(r){  
								    if (r){ 
          $.post("vehicle/vehicleModel_delete.action", {
		    		modelId:vehicle_modelId
				}, function(data) {
					var r = $.parseJSON(data);
						if (r.success) {
							$('#vehicle_model_datagrid').datagrid('reload');
						}
						$.messager.alert(tipMsgDilag, r.msg);
				});
								    }});
}
//窗口大小变化
function vehicle_modelMainResize (w,h){
	if($('#vehicle_model_datagrid')){
		try{
		  $('#vehicle_model_datagrid').datagrid('options');
		  $('#vehicle_model_datagrid').datagrid('resize', {  
				width : w-2 ,
				height:h
		 }); 
		}catch(e){
		}
	}
}