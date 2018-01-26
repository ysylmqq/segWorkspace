//表格中的操作
function operate(val,row,index){
	var unitTypeSn = row.unitTypeSn;
	var obj={unitTypeSn:unitTypeSn,unitType:row.unitType,supperierSn:row.supperierSn,isValid:row.isValid};
	obj=JSON.stringify(obj); 
	var str1 ='<a style="color:blue;" href="javascript:void(0)" onclick=openDlg4unit_typeOperate('+obj+')>编辑</a>';
	  str1+=' <a style="color:blue;" href="javascript:void(0)" onclick="delete_unit_type(\''+unitTypeSn+'\')">删除</a>';
	   return str1;
}
//查询
function queryunit_type(){
       $('#unit_type_datagrid').datagrid('load',{    
			unitTypeSn: $('#unitTypeSn_search').val(),    
			unitType: $('#unitType_search').val()
	    });  
}
//新增或者更新
function saveunit_type(){
	$('#unitTypeSn').removeAttr("disabled");
	 if(!$('#unit_type_operate_form').form('validate')){
		     return;
   }
          $.post('sys/dicUnitType_saveOrUpdate.action',
      			$("#unit_type_operate_form").serialize(),
    			function(result){
      	  	     try {
						var r = $.parseJSON(result);
						if (r.success) {
							$('#dlg_unit_type_operate').dialog('close');
							$('#unit_type_datagrid').datagrid('reload');
						}
						$.messager.alert(tipMsgDilag, r.msg);
					} catch (e) {
						$.messager.alert(tipMsgDilag, '失败!');
					}
    	}
    	);
}
//新增或者编辑
function openDlg4unit_typeOperate(obj){
	$('#dlg_unit_type_operate').dialog('open');
	
	//$('#unit_type_operate_form').form('clear');	//清空之前的参数
  $('#updateId').val('');
  $('#unitTypeSn').removeAttr("disabled");
//初始化数据
	try{
      $('#supperierSn').combobox("options");
  }catch(e){
  	$('#supperierSn').combobox({  
  		  required:true ,
  		  editable:false
   });  
 }
  if(obj){//编辑
     /* $.post("sys/unit_type_getById.action", {
		    		paramId:unit_typeId
				}, function(data) {
					var obj = $.parseJSON(data);
					$('#unit_type_operate_form').form('load', obj);
					$('#unitTypeSn').attr("disabled","true");
					
				});*/
	  $('#updateId').val(obj.unitTypeSn);
	  $('#unit_type_operate_form').form('load', obj);
	  $('#unitTypeSn').attr("disabled","true");
		
  }
 
}
//删除
function delete_unit_type(unit_typeId){
	 $.messager.confirm(tipMsgDilag,"确定删除?",
							   function(r){  
								    if (r){ 
          $.post("sys/dicUnitType_delete.action", {
        	  unitTypeSn:unit_typeId
				}, function(data) {
					var r = $.parseJSON(data);
						if (r.success) {
							$('#unit_type_datagrid').datagrid('reload');
						}
						$.messager.alert(tipMsgDilag, r.msg);
				});
								    }});
}
//窗口大小变化
function unit_typeMainResize(w,h){
	if($('#unit_type_datagrid')){
		try{
		  $('#unit_type_datagrid').datagrid('options');
		  $('#unit_type_datagrid').datagrid('resize', {  
				width : w-2 ,
				height:h
		 }); 
		}catch(e){
		}
	}
}