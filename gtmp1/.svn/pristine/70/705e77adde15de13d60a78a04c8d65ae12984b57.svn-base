//表格中的操作
function operate(val,row,index){
	//var typeId = row.typeId;
	var modelId = row.modelId;
	var indexId = row.indexId;
	//var obj={modelId:modelId,vehicleCode:row.vehicleCode,vehicleArg:row.vehicleArg,listNo:row.listNo};
	//obj=JSON.stringify(obj); 

	var str1 ='<a style="color:blue;" href="javascript:void(0)" onclick=openDlg4vehicle_typeOperate('+indexId+')>编辑</a>';
	  str1+=' <a style="color:blue;" href="javascript:void(0)" onclick="delete_vehicle_type(\''+indexId+'\')">删除</a>';
	   return str1;
}
//查询
function queryvehicle_type(){
       $('#vehicle_type_datagrid').datagrid('load',{    
    	   typeName: $('#typeName_search').val()    
	    });  
}
//新增或者更新
function savevehicle_type(){
	$('#typeId').removeAttr("disabled");
	 if(!$('#vehicle_type_operate_form').form('validate')){
		     return;
   }
   //防重验证
          $.post('vehicle/vehicleType_saveOrUpdate.action',
      			$("#vehicle_type_operate_form").serialize(),
    			function(result){
      	  	     try {
						var r = $.parseJSON(result);
						if (r.success) {
							$('#dlg_vehicle_type_operate').dialog('close');
							$('#vehicle_type_datagrid').datagrid('reload');
						}
						$.messager.alert(tipMsgDilag, r.msg);
					} catch (e) {
						$.messager.alert(tipMsgDilag, '失败!');
					}
    	}
    	);
}
//新增或者编辑
function openDlg4vehicle_typeOperate(obj){
	$('#dlg_vehicle_type_operate').dialog('open');
	//$('#vehicle_type_operate_form').form('clear');	//清空之前的参数

  $('#indexId').val(obj);
  if(obj!=null && obj!=""){//编辑
  	  //编辑
	      $.post("vehicle/vehicleType_seachEdit.action", {
	    	  indexId:obj
					}, function(data) {
						var obj = $.parseJSON(data);
						//$('#unitId').val(obj.datas.unitId);
					$('#vehicle_type_operate_form').form('load', obj.rows);
						
					});
  	  
  	  
	
	//$('#typeId').attr("disabled","true");
  }
 
}
//删除车台，设置为无效
function delete_vehicle_type(indexId){
	 $.messager.confirm(tipMsgDilag,"确定删除?",
							   function(r){  
								    if (r){ 
          $.post("vehicle/vehicleType_delete.action", {
		    		indexId:indexId
				}, function(data) {
					var r = $.parseJSON(data);
						if (r.success) {
							$('#vehicle_type_datagrid').datagrid('reload');
						}
						$.messager.alert(tipMsgDilag, r.msg);
				});
								    }});
}
//窗口大小变化
function vehicle_typeMainResize(w,h){
	if($('#vehicle_type_datagrid')){
		try{
		  $('#vehicle_type_datagrid').datagrid('options');
		  $('#vehicle_type_datagrid').datagrid('resize', {  
				width : w-2 ,
				height:h
		 }); 
		}catch(e){
		}
	}
}