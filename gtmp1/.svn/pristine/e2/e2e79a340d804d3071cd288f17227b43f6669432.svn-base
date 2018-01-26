$(function() {
	CKEDITOR.replace('content');
});

//问题表格展开收缩标志位
var flag = 0;
//窗口大小变化
function askResize(w,h){
	if($('#reseach_datagrid')){
		try{
		  $('#reseach_datagrid').datagrid('options');
		  $('#reseach_datagrid').datagrid('resize', {  
				width : w-2 ,
				height:h
		 }); 
		}catch(e){
		}
	}
}

//展开提问栏
function askChecked(){
	//$('#tb1').toggle();
	

	if(document.getElementById("tb1").style.display == 'none'){
		document.getElementById("testButtonTrue1").style.display = "block";
		document.getElementById("testButtonFalse1").style.display = "none";
	}
	else{
		document.getElementById("testButtonTrue1").style.display = "none";
		document.getElementById("testButtonFalse1").style.display = "block";
	}
	if(flag==0){
	$('#tb1').show();	
	$('#tb2').hide();
	flag=1;
	return;
	}
	if(flag==1){
	$('#tb1').hide();	
	$('#tb2').show();
	flag=0;
	}
	
}

//问题发布
function askSend(){
	var content = CKEDITOR.instances.content.getData();
	var textContent = $('#textContent').val();
	if(textContent==null || textContent==""){
		 $.messager.alert(tipMsgDilag, "标题不能为空。");
		 return;
	}
	
	if(content==null || content==""){
		 $.messager.alert(tipMsgDilag, "内容不能为空。");
		 return;
	}
	//alert(content);
				 $.messager.confirm(tipMsgDilag,"确认发布?",
							   function(r){  
								    if (r){ 
  		$.ajax({
			   type : "POST",
			   data: {textContent:textContent,content:content}, 
			   url : "userInfo/reseach_saveOrUpdate.action",
			   error : function(jqXMLRequest, textStatus, errorThrown){
				   $.messager.alert(tipMsgDilag, "错误类型：" + textStatus + "<br />错误信息：" + errorThrown, 'error');
			   },
			   success : function(result){
			   	flag=0;
				   var r = $.parseJSON(result);
				  $.messager.alert(tipMsgDilag, r.msg);
				   //$('#dlg_vehicleTest_operate').dialog('close');
				  $('#tb1').hide();
				  $('#tb2').show();
				   $('#reseach_datagrid').datagrid('reload');
				   }
			  });
								    }});
	
}
function showDetail(obj){
	$('#hid').val(obj);
	$.post("userInfo/reseach_getById.action", {
  	  id:obj
				}, function(data) {
					var obj = $.parseJSON(data);
					//提问内容
					var content = obj.content;
					//回复内容
					//var textAnswer = obj.textAnswer;
					//var w=document.getElementById('reseachs_datagrid');
					//w.innerHTML = content;
					var tab = document.getElementById("reseachs_datagrid"); 
					tab.deleteRow(document.getElementById("nodetr"));//删除tr
					var newTr  = tab.insertRow();   //创建一行
					newTr.vAlign = "top";
					var newTd1 = newTr.insertCell();//创建一个单元格
					newTd1.innerHTML = content;
					document.getElementById("Label1").innerHTML = obj.userName;
					document.getElementById("Label2").innerHTML = obj.lastAskDate;
					$('#reseach_detail').dialog('setTitle',"标题:"+obj.textContent);
				});
	
	   $('#reseach_detail').dialog('open');
}

function formatterText(val,row,index){
	var id = row.id;
	var str= val+'&nbsp;'+'<a  style="color: blue;text-decoration: underline;cursor: pointer;" onclick=showDetail('+id+')>详细</a>';
	return str;
}



function formatterCount(val,row,index){
	var id = row.id;
	if(val==null || val=='null'){
		val=0;
	}
	var str= '<a  style="color: blue;text-decoration: underline;cursor: pointer;" onclick=showCount('+id+')>'+val+'</a>';
	return str;
}

//删除
function del(obj){
	 $.messager.confirm(tipMsgDilag,"确认删除?",
							   function(r){  
								    if (r){ 
  		$.ajax({
			   type : "POST",
			   data: {id:obj}, 
			   url : "userInfo/reseach_del.action",
			   error : function(jqXMLRequest, textStatus, errorThrown){
				   $.messager.alert(tipMsgDilag, "错误类型：" + textStatus + "<br />错误信息：" + errorThrown, 'error');
			   },
			   success : function(result){
				   var r = $.parseJSON(result);
				  $.messager.alert(tipMsgDilag, r.msg);
				   //$('#dlg_vehicleTest_operate').dialog('close');
				   $('#answer_datagrid').datagrid('reload');
				   }
			  });
								    }});
}

function vehicleDlgResize(wei, hei) {
	$('#reseachList_datagrid').datagrid('resize',{width:wei-14, height:hei-73});
}

function showCount(id){
	$('#reseachList').dialog('open');
	     $('#reseachList_datagrid').datagrid('load',{    
	       	researchId:id
	    });  
	
}

function editReseach(){
	flag=1;
	$('#fb').hide();
	$('#bj').show();
	$.post("userInfo/reseach_getById.action", {
  	  id:$('#hid').val()
				}, function(data) {
					var obj = $.parseJSON(data);
					//标题
					var textContent = obj.textContent;
					//内容
					var content = obj.content;
					
					//赋值操作
					//标题
					$('#textContent').val(textContent);
					//内容
					CKEDITOR.instances.content.setData(content);
				});
	//详细信息弹出窗口关闭
	$('#reseach_detail').dialog('close');
	//富窗口打开
	$('#tb1').show();
	//表格关闭
	$('#tb2').hide();
	
}

function back(){
	$('#tb1').hide();
	$('#tb2').show();
	flag=0;
}


function askEditSend(){
	//标题
	var textContent = $('#textContent').val();
	//正文
	var content = CKEDITOR.instances.content.getData();
	
	if(textContent==null || textContent==""){
		 $.messager.alert(tipMsgDilag, "标题不能为空。");
		 return;
	}
	
	if(content==null || content==""){
		 $.messager.alert(tipMsgDilag, "内容不能为空。");
		 return;
	}
	//alert(content);
				 $.messager.confirm(tipMsgDilag,"确认发布?",
							   function(r){  
								    if (r){ 
  		$.ajax({
			   type : "POST",
			   data: {textContent:textContent,content:content,id:$('#hid').val()}, 
			   url : "userInfo/reseach_editReseach.action",
			   error : function(jqXMLRequest, textStatus, errorThrown){
				   $.messager.alert(tipMsgDilag, "错误类型：" + textStatus + "<br />错误信息：" + errorThrown, 'error');
			   },
			   success : function(result){
			   	flag=1;
				   var r = $.parseJSON(result);
				  $.messager.alert(tipMsgDilag, r.msg);
				   //$('#dlg_vehicleTest_operate').dialog('close');
				  $('#tb1').hide();
				  $('#tb2').show();
				   $('#reseach_datagrid').datagrid('reload');
				   }
			  });
								    }});
					  	//工程信息标题
						$('#textContent').val('');
						//工程信息内容
						CKEDITOR.instances.content.setData('');
				  	$('#fb').show();
					$('#bj').hide();
}




































































