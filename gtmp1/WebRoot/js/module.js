//刷新树
function refreshTree(){
  $('#module_tree').tree('reload');
}
//点击树节点
function clickTreeNode(node){
			  $.post('permi/module_getById.action',
			  {moduleId:node.id},
    			function(result){
    				if(result){
    				   result=$.parseJSON(result);
    				   $('#form_module').form('load', result);
    				}
    	});
}
//保存
function save(){
	 if(!$('#form_module').form('validate')){
		     return;
   }
            $.post('permi/module_saveOrUpdate.action',
      			$("#form_module").serialize(),
    			function(result){
      	  	     try {
						var r = $.parseJSON(result);
						if (r.success) {
						   $('#module_tree').tree('reload');
						}
						$.messager.alert(tipMsgDilag, r.msg);
					} catch (e) {
						$.messager.alert(tipMsgDilag, '失败!');
					}
    	}
    	);
}
//新增保存
function saveAdd(){
	 if(!$('#form_module_add').form('validate')){
		     return;
   }
            $.post('permi/module_saveOrUpdate.action',
      			$("#form_module_add").serialize(),
    			function(result){
      	  	     try {
						var r = $.parseJSON(result);
						if (r.success) {
						   $('#module_tree').tree('reload');
						}
						$.messager.alert(tipMsgDilag, r.msg);
					} catch (e) {
						$.messager.alert(tipMsgDilag, '失败!');
					}
    	}
    	);
}

//删除
function removeTreeNode(){
	if(!$('#module_tree').tree('getSelected')){
	  $.messager.alert(tipMsgDilag, "请选择要删除的模块!");
	       return;
	}
	   $.messager.confirm(tipMsgDilag,"整机编号为["+selectedVehiceDefs.join(',')+']的车台确定下发指令?',
							   function(r){  
								    if (r){ 
										$.post("permi/module_saveOrUpdate.action",{moduleId:$('#module_tree').tree('getSelected').id,isValid:1},function(data){
															          if(data.success){
															              //刷新
															               $('#module_tree').tree('reload');
															             	$.messager.alert(tipMsgDilag, r.msg);
																		} else {
																			$.messager.alert(tipMsgDilag, '失败!');
																		}
															   });
								    }});
}
//打开新增页面
function openModuleAddoWin(){
		if(!$('#module_tree').tree('getSelected')){
	  $.messager.alert(tipMsgDilag, "请选择要新增的父模块!");
	       return;
	}
	$('#parentId',$('#form_module_add')).val($('#module_tree').tree('getSelected').id);
  $('#dlg_module').dialog('open');
}