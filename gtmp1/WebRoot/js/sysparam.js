//表格中的操作
function operate(val,row,index){
	var sysparamId = row.paramId;
	var str1 ='<a style="color:blue;" href="javascript:void(0)" onclick="openDlg4sysparamOperate(\''+sysparamId+'\')">编辑</a>';
	return str1;
}

//查询
function querysysparam(){
       $('#sysparam_datagrid').datagrid('load',{    
			code: $('#code_search').val(),    
			name: $('#name_search').val(), 
			value: $('#value_search').val()
	    });  
}

//新增或者更新
function savesysparam(){
	$('#code').removeAttr("disabled");
	 if(!$('#sysparam_operate_form').form('validate')){
		     return;
   }
   //防重验证
          $.post('sys/sysparam_saveOrUpdate.action',
      			$("#sysparam_operate_form").serialize(),
    			function(result){
      	  	     try {
						var r = $.parseJSON(result);
						if (r.success) {
							$('#dlg_sysparam_operate').dialog('close');
							$('#sysparam_datagrid').datagrid('reload');
						}
						$.messager.alert(tipMsgDilag, r.msg);
					} catch (e) {
						$.messager.alert(tipMsgDilag, '失败!');
					}
    	}
    	);
}

//新增或者编辑
function openDlg4sysparamOperate(sysparamId){
	$('#dlg_sysparam_operate').dialog('open');
	
	//$('#sysparam_operate_form').form('clear');	//清空之前的参数
  $('#paramId').val('');
  //$('#code').removeAttr("disabled");
  if(sysparamId){//编辑
      $.post("sys/sysparam_getById.action", {
		    		paramId:sysparamId
				}, function(data) {
					var obj = $.parseJSON(data);
					$('#sysparam_operate_form').form('load', obj);
					$('#code').attr("disabled","true");
					
				});
  }
 
}

//窗口大小变化
function sysparamMainResize(w,h){
	if($('#sysparam_datagrid')){
		try{
		  $('#sysparam_datagrid').datagrid('options');
		  $('#sysparam_datagrid').datagrid('resize', {  
				width : w-2 ,
				height:h
		 }); 
		}catch(e){
		}
	}
}