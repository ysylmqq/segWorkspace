//表格中的操作
function operate(val,row,index){
	var supperierSn = row.supperierSn;
	var obj={supperierSn:supperierSn,supperierName:row.supperierName,isValid:row.isValid};
	obj=JSON.stringify(obj); 
	var str1 ='<a style="color:blue;" href="javascript:void(0)" onclick=openDlg4supperierOperate('+obj+')>编辑</a>';
	  str1+=' <a style="color:blue;" href="javascript:void(0)" onclick="delete_supperier(\''+supperierSn+'\')">删除</a>';
	   return str1;
}
//查询
function querysupperier(){
       $('#supperier_datagrid').datagrid('load',{    
			supperierSn: $('#supperierSn_search').val(),    
			supperierName: $('#supperierName_search').val()
	    });  
}
//新增或者更新
function savesupperier(){
	$('#supperierSn').removeAttr("disabled");
	 if(!$('#supperier_operate_form').form('validate')){
		     return;
   }
          $.post('sys/dicSupperier_saveOrUpdate.action',
      			$("#supperier_operate_form").serialize(),
    			function(result){
      	  	     try {
						var r = $.parseJSON(result);
						if (r.success) {
							$('#dlg_supperier_operate').dialog('close');
							$('#supperier_datagrid').datagrid('reload');
						}
						$.messager.alert(tipMsgDilag, r.msg);
					} catch (e) {
						$.messager.alert(tipMsgDilag, '失败!');
					}
    	}
    	);
}
//新增或者编辑
function openDlg4supperierOperate(obj){
	$('#dlg_supperier_operate').dialog('open');
	
	//$('#supperier_operate_form').form('clear');	//清空之前的参数
  $('#updateId').val('');
  //$('#supperierSn').removeAttr("disabled");
  if(obj){//编辑
     /* $.post("sys/supperier_getById.action", {
		    		paramId:supperierId
				}, function(data) {
					var obj = $.parseJSON(data);
					$('#supperier_operate_form').form('load', obj);
					$('#supperierSn').attr("disabled","true");
					
				});*/
	  $('#updateId').val(obj.supperierSn);
	  $('#supperier_operate_form').form('load', obj);
	  $('#supperierSn').attr("disabled","true");
		
  }
 
}
//删除
function delete_supperier(supperierId){
	 $.messager.confirm(tipMsgDilag,"确定删除?",
							   function(r){  
								    if (r){ 
          $.post("sys/dicSupperier_delete.action", {
        	  supperierSn:supperierId
				}, function(data) {
					var r = $.parseJSON(data);
						if (r.success) {
							$('#supperier_datagrid').datagrid('reload');
						}
						$.messager.alert(tipMsgDilag, r.msg);
				});
								    }});
}
//窗口大小变化
function supperierMainResize(w,h){
	if($('#supperier_datagrid')){
		try{
		  $('#supperier_datagrid').datagrid('options');
		  $('#supperier_datagrid').datagrid('resize', {  
				width : w-2 ,
				height:h
		 }); 
		}catch(e){
		}
	}
}