/*$(function() {
	CKEDITOR.replace('textAnswer');
});*/

//窗口大小变化
function askResize(w,h){
	if($('#vehicle_datagrid')){
		try{
		  $('#vehicle_datagrid').datagrid('options');
		  $('#vehicle_datagrid').datagrid('resize', {  
				width : w-2 ,
				height:h
		 }); 
		}catch(e){
		}
	}
}

//删除相关问题
function del(obj){
	 $.messager.confirm(tipMsgDilag,"确认删除?",
							   function(r){  
								    if (r){ 
  		$.ajax({
			   type : "POST",
			   data: {id:obj}, 
			   url : "userInfo/userInfo_del.action",
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

//展示弹出页面
function showDetail(obj){
	$('#hId').val(obj);
	$.post("userInfo/userInfo_getById.action", {
  	  id:obj
				}, function(data) {
					var obj = $.parseJSON(data);
					//提问内容
					var content = obj.content;
					//回复内容
					var textAnswer = obj.textAnswer;
					document.getElementById("textAnswer").value = textAnswer;
					//var w=document.getElementById('question_datagrid');
					//w.innerHTML = content;
					var tab = document.getElementById("question_datagrid"); 
					tab.deleteRow(document.getElementById("nodetr"));//删除tr
					var newTr  = tab.insertRow();   //创建一行
					var newTd1 = newTr.insertCell();//创建一个单元格
					newTd1.innerHTML = content;
					document.getElementById("Label1").innerText = obj.userName;
					document.getElementById("Label2").innerText = obj.lastAskDate;
					document.getElementById("Label3").innerText = obj.answerName; 
					document.getElementById("Label4").innerText = obj.lastAnswerDate;
					$('#question_detail').dialog('setTitle',"标题:"+obj.textContent);
				});
	
	   $('#question_detail').dialog('open');
}

function formatterText(val,row,index){
	var id = row.id;
	var str= val+'&nbsp;'+'<a  style="color: blue;text-decoration: underline;cursor: pointer;" onclick=showDetail('+id+')>回复</a>'+'&nbsp;&nbsp;'+'<a  style="color: blue;text-decoration: underline;cursor: pointer;" onclick=del('+id+')>删除</a>';
	return str;
}

function formatterAnswerName(val,row,index){
	var str;
	if(val==null|| val==''){
		str="无";
	}
	else{
		str="有";
	}
	return str;
}

//回复操作
function edSave(){
 	var id = $('#hId').val();
 	var textAnswer = $('#textAnswer').val();
 		 $.messager.confirm(tipMsgDilag,"确认回复?",
							   function(r){  
								    if (r){ 
 	$.ajax({
			   type : "POST",
			   data: {id:id,textAnswer:textAnswer,ed:1}, 
			   url : "userInfo/userInfo_saveOrUpdate.action",
			   error : function(jqXMLRequest, textStatus, errorThrown){
				   $.messager.alert(tipMsgDilag, "错误类型：" + textStatus + "<br />错误信息：" + errorThrown, 'error');
			   },
			   success : function(result){
			   	flag=1;
				   var r = $.parseJSON(result);
				  $.messager.alert(tipMsgDilag, r.msg);
				   $('#question_detail').dialog('close');
				   $('#vehicle_datagrid').datagrid('reload');
				   }
			  });
								    }});
 	
}










































































