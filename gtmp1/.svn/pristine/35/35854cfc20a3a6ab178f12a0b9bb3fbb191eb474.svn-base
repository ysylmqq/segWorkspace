//窗口大小变化
function roleMainResize(w,h){
	if($('#role_datagrid')){
		try{
		  $('#role_datagrid').datagrid('options');
		  $('#role_datagrid').datagrid('resize', {  
				width : w-2 ,
				height:h
		 }); 
		}catch(e){
		}
	}
}
//表格中的操作
function operate(val,row,index){
	var roleId = row.roleId;
	  var str1 ='<a style="color:blue;" href="javascript:void(0)" onclick="openDlg4roleOperate(\''+roleId+'\',\''+row.roleName+'\')">编辑</a>';
	if(roleId!=1 && roleId!=3 && roleId!=4 && roleId!=84){//超级管理员、终端供应商、经销商、融资租赁不能删除
	  str1+=' <a style="color:blue;" href="javascript:void(0)" onclick="deleteRole(\''+roleId+'\')">删除</a>';
	}
	   return str1;
}
//查询
function queryrole(){
       $('#role_datagrid').datagrid('load',{    
			roleName: $('#roleName_search').val()
	    });  
}
//新增或者更新
function saverole(){
	 if(!$('#role_operate_form').form('validate')){
		     return;
   }
   //防重验证
          $.post('permi/role_saveOrUpdate.action',
      			$("#role_operate_form").serialize(),
    			function(result){
      	  	     try {
						var r = $.parseJSON(result);
						if (r.success) {
							$('#dlg_role_operate').dialog('close');
							$('#role_datagrid').datagrid('reload');
						}
						$.messager.alert(tipMsgDilag, r.msg);
					} catch (e) {
						$.messager.alert(tipMsgDilag, '失败!');
					}
    	}
    	);
}

//新增或者编辑
function openDlg4roleOperate(roleId,roleName){
	$('#dlg_role_operate').dialog('open');
	
	//$('#role_operate_form').form('clear');	//清空之前的参数
  $('#roleId').val('');
  $('#dlg_role_operate').dialog('setTitle',"角色新增");
  if(roleId){//编辑
  	$('#dlg_role_operate').dialog('setTitle',"角色编辑");
	$('#role_operate_form').form('load', {roleId:roleId,roleName:roleName});
  }
}
   function deleteRole(id) {
	   $.messager.confirm(tipMsgDilag,'确定删除该角色?',
							   function(r){  
								    if (r){ 
										$.post("permi/role_update.action",{roleId:id,isValid:1},function(data){
											data=$.parseJSON(data);				         
											if(data.success){
															              //刷新
															            	$('#role_datagrid').datagrid('reload');
															             	$.messager.alert(tipMsgDilag, data.msg);
																		} else {
																			$.messager.alert(tipMsgDilag, '失败!');
																		}
															   });
								    }});
       }
 //模块分配
   function assignModeles(){
   	if(!$('#role_datagrid').datagrid('getSelected')){
      	  $.messager.alert(tipMsgDilag, "请选择要分配模块的角色!");
	       return;
   	}
   	//树
   	$('#module_tree').tree({
    	url:'permi/module_search4Role.action?roleId='+$('#role_datagrid').datagrid('getSelected').roleId
   	});
   $('#dlg_role_module').dialog('open');
   }
  //模块分配保存
   function saveModules(){
    var nodes = $('#module_tree').tree('getChecked');
   var result=[];
	for(var i=0; i<nodes.length; i++){
		result.push(nodes[i].id);
	}
	  $.ajax( 
       { 
        type: "POST", 
        url: "permi/role_addRoleModules.action", 
        data:$.param({
          idList:result,
          roleId:$('#role_datagrid').datagrid('getSelected').roleId
        },true) ,	         
        success: function(msg) 
        {	
        	 try {
						var r = $.parseJSON(msg);
						if (r.success) {
							$('#dlg_role_module').dialog('close');
						}
						$.messager.alert(tipMsgDilag, r.msg);
					} catch (e) {
						$.messager.alert(tipMsgDilag, '失败!');
					}
        }});
	return result;
   }