$(function() {
	CKEDITOR.replace('content');
});

//问题表格展开收缩标志位
var flag = 0;
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
			   url : "userInfo/userInfo_saveOrUpdate.action",
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
				   $('#question_datagrid').datagrid('reload');
				   }
			  });
								    }});
	
}
function showDetail(obj){
	
	$.post("userInfo/userInfo_getById.action", {
  	  id:obj
				}, function(data) {
					var obj = $.parseJSON(data);
					//提问内容
					var content = obj.content;
					//回复内容
					var textAnswer = obj.textAnswer;
					//var w=document.getElementById('questions_datagrid');
					//w.innerHTML = content;
					//var u=document.getElementById('answer_datagrid');
					//u.innerHTML = textAnswer;
					var tab = document.getElementById("questions_datagrid"); 
					tab.deleteRow(document.getElementById("nodetr"));//删除tr
					var newTr  = tab.insertRow();   //创建一行
					var newTd1 = newTr.insertCell();//创建一个单元格
					newTd1.innerHTML = content;
					document.getElementById("textAnswer").value = textAnswer;
					//document.getElementById("textAnswer").disabled = true;
					document.getElementById("textAnswer").readOnly = true;

					document.getElementById("Label1").innerHTML = obj.userName;
					document.getElementById("Label2").innerHTML = obj.lastAskDate;
					document.getElementById("Label3").innerHTML = obj.answerName; 
					document.getElementById("Label4").innerHTML = obj.lastAnswerDate;
					$('#question_detail').dialog('setTitle',"标题:"+obj.textContent);
				});
	
	   $('#question_detail').dialog('open');
}

function formatterText(val,row,index){
	var id = row.id;
	var str= val+'&nbsp;'+'<a  style="color: blue;text-decoration: underline;cursor: pointer;" onclick=showDetail('+id+')>详细</a>';
	return str;
}



function formatterAnswerName(val,row,index){
	var str;
	if(val==null|| val==''){
		str=' <font color="red">无</font>'; 
		
	}
	else{
		//str="有";
		str=' <font color="green">有</font>'; 
	}
	return str;
}











































































