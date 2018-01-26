//默认查询条件
var defQueryParams={'state':1,'isValid':0};
//表格中的操作
function operate(val,row,index){
	var unitId = row.unitId;
	var isValid=row.isValid;
	 var str1='<a style="color:blue;" href="javascript:void(0)" onclick="openDlg4UnitOperate(\''+unitId+'\')">编辑</a>';
	if(isValid!=null&&isValid==0&&row.state!=2){//已安装也不能删除
		str1+=' <a style="color:blue;" href="javascript:void(0)" onclick="delete_unit(\''+unitId+'\')">删除</a>';
	}
	   return str1;
}
//查询
function queryUnit(){
       $('#unit_datagrid').datagrid('load',{    
			unitSn: $('#unitSn_search').val(),    
			materialNo: $('#materialNo_search').val(),    
			simNo: $('#simNo_search').val(),    
			steelNo: $('#steelNo_search').val(),    
			state: $('#state_search').combobox('getValue'),
			isValid: 0
	    });  
}
//新增或者更新
function saveUnit(){
	 if(!$('#unit_operate_form').form('validate')){
		     return;
   }
   var openTime = $("#openTime").val();	 
   
   var endTime = $("#endTime").val();	 
   if( openTime == "" ||openTime == null){
	   $.messager.alert(tipMsgDilag, "SIM卡服务开始日期必填!");
			return;
   }

   if( endTime == "" ||endTime == null){
	   $.messager.alert(tipMsgDilag, "SIM卡服务结束日期必填!");
			return;
   }

   //防重验证
          $.post('unit/unit_saveOrUpdate.action',
      			$("#unit_operate_form").serialize(),
    			function(result){
      	  	     try {
						var r = $.parseJSON(result);
						if (r.success) {
							$('#dlg_unit_operate').dialog('close');
							try {
								$('#dlg_unit_recycle').dialog("options");
								if ($('#dlg_unit_recycle').dialog("options").closed) {
									$('#unit_datagrid').datagrid('reload');
								}else{
									$('#unit_recycle_datagrid').datagrid('reload');
								}
							} catch (e) {
								$.messager.alert(tipMsgDilag, '失败!');
							}
						}
						$.messager.alert(tipMsgDilag, r.msg);
					} catch (e) {
						$.messager.alert(tipMsgDilag, '失败!');
					}
    	}
    	);
}
//批量导入
function batchImpUnit(){
   //判断文件类型
	var objtype=$('#upload',$('#unit_impport_Form')).val();
	 var fileType=new Array(".xls",".xlsx");
	 var flag=false;//判断是否是excel格式
	if(objtype){
		objtype=objtype.substring(objtype.lastIndexOf(".")).toLowerCase();
		 for(var i=0; i<fileType.length; i++){
        if(objtype==fileType[i])
        {
             flag=true;
             break;
        }
       }
       if(flag){
           $('#unit_impport_Form').form('submit', { 
				url: 'unit/unit_impFromExcel.action',
				onSubmit: function(){ 
				  return $(this).form('validate');
				}, 
				success:function(data){ 
				  var r=null;
				  try{
				          r = $.parseJSON(data);
					      if (r.count) {
						    $('#dlg_unit_impport').dialog('close');
						    $('#unit_datagrid').datagrid('reload');
						 	 $.messager.alert(tipMsgDilag, r.msg);
						  }else{
							  if(r.msg){
								  $.messager.alert(tipMsgDilag, r.msg);
							  }else{
								  $.messager.alert(tipMsgDilag, "导入失败，请检查Excel文档数据格式是否正确!");
							  }
						  }
					  }catch (e) {
						  $.messager.alert(tipMsgDilag,data);
					  }
				
				   } 
				}); 
       }else{
         $.messager.alert(tipMsgDilag, '请选择Excel文件!');
       }
	}else{
		$.messager.alert(tipMsgDilag, '请选择文件!');
	}
}
//新增或者编辑
function openDlg4UnitOperate(unitId){
	$('#dlg_unit_operate').dialog('open');
	//$('#unit_operate_form').form('clear');	//清空之前的参数
	 //登记人
	 $('#username').val($('#span_user_name').text());
 //初始化数据
	 /*	try{
        $('#supperierSn').combobox("options");
    }catch(e){
    	$('#supperierSn').combobox({  
    		  required:true ,
    		  editable:false
     });  
   }
 try{
        $('#unitTypeSn').combobox("options");
    }catch(e){
    	var url = 'sys/dicUnitType_getList.action?isValid=0&supperierSn=' + $('#supperierSn').combobox('getValue');
    	$('#unitTypeSn').combobox({ 
    		editable:false,
    		url:url
  }); 
   }*/
  $('#td_state_title').hide(); 
  $('#td_state').hide(); 
  $('#unitId').val('');
/*//终端类型
  $('#supperierSn').combobox({
    onSelect: function(rec){
    	       //级联关系
               var url = 'sys/dicUnitType_getList.action?isValid=0&supperierSn=' + rec.supperierSn;
               $('#unitTypeSn').combobox('reload', url);
               $('#unitTypeSn').combobox('setValue','');    
             }
 });*/
  if(unitId){//编辑
      $.post("unit/unit_getById.action", {
		    		unitId:unitId
				}, function(data) {
					var obj = $.parseJSON(data);
					//当终端已经解绑定时，如发现终端有问题，可手动修改状态，变成返修状态；
					//当终端处于返修状态时，如果终端已经修好，也可手动修改状态，变成待安装状态
					if(obj.state==3||obj.state==9){
						$('#td_state_title').show();
						$('#td_state').show();
					}
					console.log("obj ",obj);
					if( obj.endTime !="" && obj.endTime != null ){
						obj.endTime = new Date(obj.endTime.replace('T',' ')).format("yyyy-MM-dd");
					}else{
						obj.endTime = "";
					}
					if( obj.openTime !="" && obj.openTime != null ){
						obj.openTime = new Date(obj.openTime.replace('T',' ')).format("yyyy-MM-dd");
					}else{
						obj.openTime = "";
					}
					$("#oldSimNo").val(obj.simNo);
					$('#unit_operate_form').form('load', obj);
				});
  }
  
 
}
//验证终端序列号或者sim卡号是否存在
function checkUnitSnOrSimNo(flag){
	console.log("flag ",flag);
	var unitSn=$("#unitSn",$('#unit_operate_form')).val();
	var simNo=$("#simNo",$('#unit_operate_form')).val();
	var unitId=$('#unitId',$('#unit_operate_form')).val();
	var url='unit/unit_checkUnitSn.action';
	var data={unitSn:unitSn,unitId:unitId};
	var result=false;
		if(flag==1){
			if(!unitSn){
			   //$.messager.alert(tipMsgDilag, '终端序列号不能为空！');
			  	return result;
			}
		
		}else if(flag==2){
				url='unit/unit_checkSimNo.action';
				data={simNo:simNo,unitId:unitId};
				if(!simNo){
			     // $.messager.alert(tipMsgDilag, 'SIM卡号不能为空！');
			     	return result;
				}
			}
	 $.ajax({
			   url: url,
			   async: false,
			   type : "GET",
			   data:data,
			   success : function(data) {
					var obj=$.parseJSON(data);
					if(obj.success){
					  result=true;
					}else{
					  $.messager.alert(tipMsgDilag, obj.msg);
					}
				}
		});
		return result;
}
//删除车台，设置为无效
function delete_unit(unitId){
	 $.messager.confirm(tipMsgDilag,"确定删除?",
							   function(r){  
								    if (r){ 
          $.post("unit/unit_delete.action", {
		    		unitId:unitId
				}, function(data) {
					var r = $.parseJSON(data);
						if (r.success) {
							$('#unit_datagrid').datagrid('reload');
						}
						$.messager.alert(tipMsgDilag, r.msg);
				});
								    }});
}
//窗口大小变化
function unitMainResize(w,h){
	if($('#unit_datagrid')){
		try{
		  $('#unit_datagrid').datagrid('options');
		  $('#unit_datagrid').datagrid('resize', {  
				width : w-2 ,
				height:h
		 }); 
		}catch(e){
		}
	}
}
//以下是回收站功能
//查询
function queryUnitRecycle(){
       $('#unit_recycle_datagrid').datagrid('load',{    
			unitSn: $('#unitSn_search_recycle').val(),    
			materialNo: $('#materialNo_search_recycle').val(),    
			simNo: $('#simNo_search_recycle').val(),    
			steelNo: $('#steelNo_search_recycle').val()    
	    });  
}
//删除
function deleteInRecycle() {
	var nodes = $('#unit_recycle_datagrid').datagrid('getChecked');
	var idList = [];
	for ( var i = 0; i < nodes.length; i++) {
		idList.push(nodes[i].unitId);
	}
	if (!idList || nodes.length < 1) {
		$.messager.alert('消息提示', '请选择终端信息!', 'info');
		return;
	}
	 $.messager.confirm(tipMsgDilag,"确定删除,删除后不可恢复?",
			   function(r){  
				    if (r){ 
	$.post('unit/unit_deleteInRecyle.action', jQuery.param({
		idList : idList
	}, true), function(result) {
		try {
			var r = $.parseJSON(result);
			if (r.success) {
				$('#unit_recycle_datagrid').datagrid('reload');
				$('#unit_recycle_datagrid').datagrid("uncheckAll");
				$.messager.alert(tipMsgDilag, r.msg);
			}

		} catch (e) {
			$.messager.alert(tipMsgDilag, e);
		}
	});
				    }});
}
//还原
function updateUnitsIsValid() {
	var nodes = $('#unit_recycle_datagrid').datagrid('getChecked');
	var unitList = [];
	for ( var i = 0; i < nodes.length; i++) {
		nodes[i].registedTime=undefined;
		nodes[i].productionDate=undefined;
		nodes[i].stamp=undefined;
		unitList.push(JSON.stringify(nodes[i])+"");
	}
	if (!unitList || nodes.length < 1) {
		$.messager.alert('消息提示', '请选择终端信息!', 'info');
		return;
	}
	 $.messager.confirm(tipMsgDilag,"确定还原?",
			   function(r){  
				    if (r){ 
	$.post('unit/unit_updateUnitsIsValid.action',
			$.param({units:unitList},true), function(result) {
		try {
			var r = $.parseJSON(result);
			if (r.success) {
				$('#unit_recycle_datagrid').datagrid('reload');
				$('#unit_recycle_datagrid').datagrid("uncheckAll");
				$('#unit_datagrid').datagrid('reload');
				$.messager.alert(tipMsgDilag, r.msg);
			}else{
				$.messager.alert(tipMsgDilag, r.msg);
			}

		} catch (e) {
			$.messager.alert(tipMsgDilag, e);
		}
	});
				    }});
}
//打开回收站窗口
function openRecycleWin(){
	$('#dlg_unit_recycle').dialog('open');
	try{
        $('#unit_recycle_datagrid').datagrid("options");
        $('#unit_recycle_datagrid').datagrid("reload");
    }catch(e){
    	$('#unit_recycle_datagrid').datagrid({});  
   }
}

function dlgResize(w, h) {
	$('#unit_recycle_datagrid').datagrid('resize',{width: w-14, height: h-73});
}