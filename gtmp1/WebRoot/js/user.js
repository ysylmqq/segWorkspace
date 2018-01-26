//窗口大小变化
function userMainResize(w,h){
	if($('#user_datagrid')){
		try{
		  $('#user_datagrid').datagrid('options');
		  $('#user_datagrid').datagrid('resize', {  
				width : w-2 ,
				height:h
		 }); 
		}catch(e){
		}
	}
}
//表格中的操作
function operate(val,row,index){
	var userId = row.userId;
	var obj={
	  userId:row.userId,
	  loginName:row.loginName,
	  userName:row.userName,
	  password:row.password,
	  tel:row.tel,
	  sex:row.sez,
	  email:row.email,
	  departId:row.departId,
	  isValid:row.isValid
	};
	obj=JSON.stringify(obj);
	  var str1 ='<a style="color:blue;" href="javascript:void(0)" onclick=openDlg4userOperate('+obj+')>编辑</a>';
	 str1+=' <a style="color:blue;" href="javascript:void(0)" onclick="resetUserPwd(\''+userId+'\',\'123456\')">密码重置</a>';
	  str1+=' <a style="color:blue;" href="javascript:void(0)" onclick="deleteuser(\''+userId+'\')">删除</a>';
	   return str1;
}

//机主表格中的操作
function ownerOperate(val, row, index) {
    var ownerId = row.ownerId;
    var obj = {
        ownerId: row.ownerId,
        loginName: row.loginName,
        ownerName: row.ownerName,
        password: row.password,
        ownerPhoneNumber: row.ownerPhoneNumber,
        sex: row.sex,
        isValid: row.isValid
    };
    obj = JSON.stringify(obj);
    var str1 = '<a style="color:blue;" href="javascript:void(0)" onclick=editOwner(' + obj + ')>编辑</a>';
    str1 += ' <a style="color:blue;" href="javascript:void(0)" onclick="resetOwnerUserPwd(\'' + ownerId + '\')">密码重置</a>';
    str1 += ' <a style="color:blue;" href="javascript:void(0)" onclick="deleteOwnerUser(\'' + ownerId + '\')">删除</a>';
    return str1;
}

//查询
function queryuser(){
	$('#user_datagrid').datagrid('load',{    
		userName: $('#userName_search').val(),
		isValid: $('#isValid_search').combobox('getValue')
	});  
}

//导出
function downExcel(){
	 var userName = $('#userName_search').val();
	 var isValid = $('#isValid_search').combobox('getValue');
	 window.location.href=encodeURI(encodeURI("permi/user_exportToExcel.action?userName="+userName+"&isValid="+isValid));
	 return;
}
//查询机主用户
function queryOwner(){
       $('#owner_datagrid').datagrid('load',{    
			ownerName : $('#ownerName_search').val()
	    });  
}
//新增或者更新
function saveuser(){
	if(!$('#user_operate_form').form('validate')){
		return;
	}
   //防重验证
	$.post('permi/user_saveOrUpdate.action',
		$("#user_operate_form").serialize(),
		function(result){
	  		try {
	  			var r = $.parseJSON(result);
				if (r.success) {
					$('#dlg_user_operate').dialog('close');
					$('#user_datagrid').datagrid('reload');
				}
				$.messager.alert(tipMsgDilag, r.msg, 'info');
			} catch (e) {
				$.messager.alert(tipMsgDilag, '失败!', 'error');
			}
  		});
}

//新増或编辑机主用户
function saveOwner() {
	if(!$('#owner_operate_form').form('validate')) {
		return;
	}
	
	$.ajax({
		url : 'permi/ownerUser_saveOrUpdate.action',
		data : $('#owner_operate_form').serialize(),
		type : 'post',
		dataType : 'json',
		success : function(data) {
			if(data.success) {
				$('#owner_datagrid').datagrid('reload');
				$('#dlg_owner_operate').dialog('close');
			}
			$.messager.alert(tipMsgDilag, data.msg, 'info');
		},
		error : function(data) {
			$.messager.alert(tipMsgDilag, '操作失败！', 'error');
		}
	});
}

//新增或者编辑
function openDlg4userOperate(obj){
	$('#dlg_user_operate').dialog('open');
	
	//$('#user_operate_form').form('clear');	//清空之前的参数
  $('#userId').val('');
   var url='permi/depart_search.action';
  $('#dlg_user_operate').dialog('setTitle',"用户新增");
  if(obj){//编辑
  	url+='?address='+obj.userId;
  	 //机构
   $('#departId').combotree({  
    url: url
    });
  	$('#dlg_user_operate').dialog('setTitle',"用户编辑");
	$('#user_operate_form').form('load',obj);
	$('#password').val('');
  }else{
     //机构
   $('#departId').combotree({  
    url: url
    });
   $('#password').val('123456');
  }
  
}

//新增机主用户
function addOwner(){
	$('#owner_operate_form').form('clear');	//清空之前的参数
	$('#dlg_owner_operate').dialog('open');
}

//编辑机主用户
function editOwner(data){
	$('#dlg_owner_operate').dialog('setTitle', '编辑机主用户');
//	$('#ownerId').val(data.ownerId);
	$('#owner_operate_form').form('load', data);
	$('#dlg_owner_operate').dialog('open');
}

//删除用户
function deleteuser(id) {
    $.messager.confirm(tipMsgDilag, '确定删除该用户？',
        function(r) {
            if (r) {
                $.post("permi/user_update.action", {
                    userId: id,
                    isValid: 1
                }, function(data) {
                    data = $.parseJSON(data);
                    if (data.success) {
                        //刷新
                        $('#user_datagrid').datagrid('reload');
                        $.messager.alert(tipMsgDilag, data.msg, 'info');
                    } else {
                        $.messager.alert(tipMsgDilag, '失败！', 'error');
                    }
                });
            }
        });
}
   
// 删除机主用户
function deleteOwnerUser(ownerId) {
	$.messager.confirm(tipMsgDilag, '确定删除该用户？',
        function(r) {
            if (r) {
                $.post("permi/ownerUser_delete.action", {
                    ownerId : ownerId
                }, function(data) {
                    data = $.parseJSON(data);
                    if (data.success) {
                        //刷新
                        $('#owner_datagrid').datagrid('reload');
                        $.messager.alert(tipMsgDilag, data.msg, 'info');
                    } else {
                        $.messager.alert(tipMsgDilag, '失败！', 'error');
                    }
                });
            }
        });
}

 //角色分配
   function assignRoles(){
   	if(!$('#user_datagrid').datagrid('getSelected')){
      	  $.messager.alert(tipMsgDilag, "请选择要分配角色的用户!", 'warning');
	       return;
   	}
   	//{userId:$('#user_datagrid').datagrid('getSelected').userId}
	//设置已选中的角色
   	$.post("permi/user_getUserRoles.action",{'userRolePOJO.userId':$('#user_datagrid').datagrid('getSelected').userId},function(data){
											data=$.parseJSON(data);				         
											if(data.data){
											var	 roleIds=data.data.split(",");
											$('#role_datagrid').datagrid('unselectAll');
											$.each(roleIds,function(i,o){
												//selectRecord
												$('#role_datagrid').datagrid('selectRecord',o);
											});
											
	}});
   	
   $('#dlg_user_role').dialog('open');
   }
   
// 机主用户角色分配
function assignOwnerRoles() {
	if (!$('#owner_datagrid').datagrid('getSelected')) {
        $.messager.alert(tipMsgDilag, "请选择要分配角色的机主用户!", 'warning');
        return;
    }
    //设置已选中的角色
    $.ajax({
        url: "permi/ownerUser_getOwnerRoles.action",
        data: {
            'userRolePOJO.userId': $('#owner_datagrid').datagrid('getSelected').ownerId
        },
        type: 'post',
        dataType: 'json',
        success: function(data) {
            if (data.data) {
                var roleIds = data.data.split(",");
                $('#owner_role_datagrid').datagrid('unselectAll');
                $.each(roleIds, function(i, o) {
                    $('#owner_role_datagrid').datagrid('selectRecord', o);
                });
            }
        }
    });
    $('#dlg_owner_role').dialog('open');
}

  //角色分配保存
   function saveRoles(){
    var nodes = $('#role_datagrid').datagrid('getChecked');
   var result=[];
	for(var i=0; i<nodes.length; i++){
		result.push(nodes[i].roleId);
	}
	  $.ajax( 
       { 
        type: "POST", 
        url: "permi/user_setUserRoles.action", 
        data:$.param({
        	userId : $('#user_datagrid').datagrid('getSelected').userId,
        	roleIds : result,
        	userType : 0
        },true) ,	         
        success: function(msg) 
        {	
        	 try {
						var r = $.parseJSON(msg);
						if (r.success) {
							$('#dlg_user_role').dialog('close');
						}
						$.messager.alert(tipMsgDilag, r.msg, 'info');
					} catch (e) {
						$.messager.alert(tipMsgDilag, '失败!', 'error');
					}
        }});
   }
   
//保存机主用户权限
function saveOwnerRoles() {
	var nodes = $('#owner_role_datagrid').datagrid('getChecked');
    var result = [];
    for (var i = 0; i < nodes.length; i++) {
        result.push(nodes[i].roleId);
    }
    $.ajax({
        type: "POST",
        url: "permi/user_setUserRoles.action",
        data: $.param({
        	userId : $('#owner_datagrid').datagrid('getSelected').ownerId,
            roleIds : result,
            userType : 1
        }, true),
        success: function(msg) {
            try {
                var r = $.parseJSON(msg);
                if (r.success) {
                    $('#dlg_owner_role').dialog('close');
                }
                $.messager.alert(tipMsgDilag, r.msg, 'info');
            } catch (e) {
                $.messager.alert(tipMsgDilag, '失败!', 'error');
            }
        }
    });
}

//密码重置
 function resetUserPwd(userId,pwd){
 	  $.messager.confirm(tipMsgDilag,'确定重置该用户密码?',
							   function(r){  
								    if (r){ 
										$.post("permi/user_update.action",{userId:userId,password:pwd},function(data){
											data=$.parseJSON(data);				         
											if(data.success){
															              //刷新
															            	$('#user_datagrid').datagrid('reload');
															             	$.messager.alert(tipMsgDilag, data.msg, 'info');
																		} else {
																			$.messager.alert(tipMsgDilag, '失败!', 'error');
																		}
															   });
								    }});
 }
 
//重置机主用户密码
function resetOwnerUserPwd(ownerId) {
	$.messager.confirm(tipMsgDilag, '确定重置该用户密码?',
	    function(r) {
            if (r) {
                $.post("permi/ownerUser_resetPwd.action", {
                    ownerId : ownerId
                }, function(data) {
                    data = $.parseJSON(data);
                    if (data.success) {
                        //刷新
                        $('#owner_datagrid').datagrid('reload');
                        $.messager.alert(tipMsgDilag, data.msg, 'info');
                    } else {
                        $.messager.alert(tipMsgDilag, '失败!', 'error');
                    }
                });
            }
        });
}

 
//查询角色
function queryrole(){
       $('#role_datagrid').datagrid('load',{    
			roleName: $('#roleName_search').val()
	    });  
}
//为机主用户查询角色
function queryOwnerRole(){
    $('#owner_role_datagrid').datagrid('load',{    
    	roleName: $('#owner_roleName_search').val()
    });  
}
//导出
function downExcel2(){
	alert(1);
	 var ownerName = $('#ownerName_search').val();
	 window.location.href=encodeURI(encodeURI("permi/ownerUser_exportToExcel.action?ownerName="+ownerName));
	 return;
}