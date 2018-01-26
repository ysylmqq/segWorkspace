
//表格中的操作
function operate(val,row,index){
	var vehicleId = row.vehicleId;
	  var str1 ='<a style="color:blue;" href="javascript:void(0)" onclick="openDlg4VehicleOperate(\''+vehicleId+'\')">编辑</a>';
	   return str1;
}
//窗口大小变化
function vehicleResize(w,h){
	if($('#vehicle_sale_datagrid')){
		try{
		  $('#vehicle_sale_datagrid').datagrid('options');
		  $('#vehicle_sale_datagrid').datagrid('resize', {  
				width : w-2 ,
				height:h
		 }); 
		}catch(e){
		}
	}
}

//新增或者编辑
function openDlg4VehicleOperate(vehicleId){
	$('#dlg_vehicle_sale_operate').dialog('open');
 // $('#unitTypeSn').removeAttr("disabled");
  $('#id').val('');
  if(obj){//编辑
	  $('#id').val(obj.vehicleDef);
	  $('#dlg_vehicle_sale_operate').form('load', obj);
	  $('#unitTypeSn').attr("disabled","true");
		
  }/*else{
	  $.post("run/run_getVehicle4sale.action", {
		}, function(data) {
			var obj = $.parseJSON(data);
			     $("#vehicleDef").append("<option value='' selected>请选择</option>");
			for(i=0;i<obj.length;i++){
					 var docNameInfo = obj.VEHICLE_DEF;
             		 docArr.push(docNameInfo);
				 $("#vehicleDef").append("<option value='"+docNameInfo+"'>"+docNameInfo+"</option>");
			}
			$("#vehicleDef").focus().autocomplete(docArr,{
           			matchContains: true,
           			minChars: 0
           		});
           	    //obju.css("display","");
		});
  }*/
}

//新增或者更新
function saveVehicle(){
	if(!$('#vehicle_operate_form').form('validate')){
		return;
	}
    if(!$('#vehicleDef').combobox('getValue')){
        $.messager.alert(tipMsgDilag, '请选择整机编号!');
        return;
    }
	if(!$('#dealerId').combobox('getValue')){
		$.messager.alert(tipMsgDilag, '请选择经销商!');
		return;
	}
   //保存操作
	 var params={
		'vehicleSalePOJO.vehicleDef': $('#vehicleDef').combobox('getValue'),
		'vehicleSalePOJO.dealerId': $('#dealerId').combobox('getValue'),
		'vehicleSalePOJO.ownerId': $('#ownerId').combobox('getValue')
	 };
    $.post('run/run_saveDealerVechicle.action',
    		params,
		function(result){
 	     	try {
				var r = $.parseJSON(result);
				if (r.success) {
					$('#dlg_vehicle_sale_operate').dialog('close');
					$('#vehicle_sale_datagrid').datagrid('reload');
				}
				$.messager.alert(tipMsgDilag, r.msg);
			} catch (e) {
				$.messager.alert(tipMsgDilag, '失败!');
			}
		}
	);
}
//查询
function querySale(){
       $('#vehicle_sale_datagrid').datagrid('load',{    
			'vehicleSalePOJO.vehicleDef': $('#vehicleDef_search').val()   
	    });  
}
//打开销售信息导入界面
function importExcel(){
    $('#dlg_saleInfoImport').dialog('open');
    $("#workInfoFile").val('');
}
//销售信息保存
function saveSaleInfo(){
	var filePath = $("#workInfoFile").val();
	if(!filePath){
		return null;
	}

	if(filePath.substring(filePath.length-3) != 'xls'){
		alert('文件格式不支持，请导入 xls 格式的文件！');
		return null;
	}

	$.post('run/run_insertSaleInfo.action',
			{'workInfoFile':filePath},
			function(result){
				if(result){
					result=$.parseJSON(result);
					if(result.total == result.seccuss){
						$.messager.alert('工况数据导入结果',"一共导入工况信息："+result.total+" 条，成功："+result.seccuss+" 条!") 
					}else{
						$.messager.alert('工况数据导入结果',"一共导入工况信息："+result.total+" 条，成功："+result.seccuss+" 条，" +"第"+result.fail+" 条导入失败！");
					}
					 
				}
				
	});
}







