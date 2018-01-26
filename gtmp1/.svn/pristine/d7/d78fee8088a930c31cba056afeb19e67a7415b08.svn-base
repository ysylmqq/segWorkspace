//刷新树
function refreshTree(){
  $('#depart_tree').tree('reload');
}
//点击树节点
function clickTreeNode(node){
			  $.post('permi/depart_getById.action',
			  {departId:node.id},
    			function(result){
    				if(result){
    				   result=$.parseJSON(result);
    				   $('#form_depart').form('load', result);
    				}
    	});
}
//保存
function save(){
	 if(!$('#form_depart').form('validate')){
		     return;
   }
            $.post('permi/depart_saveOrUpdate.action',
      			$("#form_depart").serialize(),
    			function(result){
      	  	     try {
						var r = $.parseJSON(result);
						if (r.success) {
						   $('#depart_tree').tree('reload');
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
	 if(!$('#form_depart_add').form('validate')){
		     return;
   }
            $.post('permi/depart_saveOrUpdate.action',
      			$("#form_depart_add").serialize(),
    			function(result){
      	  	     try {
						var r = $.parseJSON(result);
						if (r.success) {
						   $('#depart_tree').tree('reload');
						   	$('#dlg_depart').dialog('close');
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
	var selNode=$('#depart_tree').tree('getSelected');
	if(!selNode){
	  $.messager.alert(tipMsgDilag, "请选择要删除的机构!");
	       return;
	}else{
				    if($("#depart_tree").tree('getChildren',selNode.target).length){
						   $.messager.alert(tipMsgDilag,"该机构下有子机构，不能删除!");
							return;
						}
						if(selNode.id==0||selNode.id==1||selNode.id==2||selNode.id==3||selNode.id==4){//玉柴重工、内部机构、经销商、终端供应商不能删除
						  	 $.messager.alert(tipMsgDilag,"该机构为基础机构，不能删除!");
							return;
						}
	}
	
		
	   $.messager.confirm(tipMsgDilag,'确定删除该机构?',
							   function(r){  
								    if (r){ 
										$.post("permi/depart_deleteDepartById.action",{departId:selNode.id,isValid:1},function(data){
											data=$.parseJSON(data);				         
											if(data.success){
															              //刷新
															            	$('#dlg_depart').dialog('close');
															               $('#depart_tree').tree('reload');
															             	$.messager.alert(tipMsgDilag, data.msg);
																		} else {
																			$.messager.alert(tipMsgDilag, '失败!');
																		}
															   });
								    }});
}
//打开新增页面
function opendepartAddoWin(){
		if(!$('#depart_tree').tree('getSelected')){
	  $.messager.alert(tipMsgDilag, "请选择要新增的父机构!");
	       return;
	}
	$('#pid',$('#form_depart_add')).val($('#depart_tree').tree('getSelected').id);
  $('#dlg_depart').dialog('open');
}