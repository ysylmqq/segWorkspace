<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style type="text/css">
#alarmTypeCkbDiv ul {
	margin: 0px;
	padding: 0px;
	list-style-type: none;
	vertical-align: middle;
}

#alarmTypeCkbDiv li {
	float: left;
	display: block;
	width: 240px;
	height: 30px;
	line-height: 30px;
	font-size: 12px;
	color: #666666;
	text-decoration: none;
	text-align: left;
	background: #ffffff;
}
#alarmTypeCkbDiv li input,label {
	vertical-align: middle;
}
</style>
<div id='loading2' style="position:absolute;z-index:1000;top:0px;left:0px;width:100%;height:100%;background:#DDDDDB;text-align:center;padding-top: 20%;">
    <img src='images/loading.gif'/> 
</div>
<div style="overflow: auto; width: 80%;margin: 20 auto;text-align: center;">
	<table width="100%" align="center">
		<tr>
			<td align="center">
				<b>
					<font color="red">提示 : 选中复选框即表示关注该警情类型的警情</font>
				</b>
				<input type="checkbox" id="chk_isSelectAll" onclick="changeChk()">
				<label for="chk_isSelectAll">是否全选</label>
			</td>
		</tr>
		<tr>
			<td align="center">
				<div id="alarmTypeCkbDiv"></div>				
			</td>
		</tr>
		<tr>
		 <td align="left">弹出时间过虑设置:<font color="red">提示 : 如果上午9点到下午5点不要弹出警情请填入900和1700</font></td>
		 </tr>
		<tr>
		 <td>从<input id="startTime" type="text" class="easyui-numberbox" max="2400" min="1" style="width: 150px;">
		         到<input id="endTime" type="text" class="easyui-numberbox" max="2400" min="1" style="width: 150px;"></td>
		<tr>
			<td align="center">  
				<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="javascript:setUserAlarmTypes()">保存</a>  
			</td>
		</tr>
	</table>
</div>

<script type="text/javascript">
	$(document).ready(function(){
		$.ajax({
			url: "run/alarm_getUserAlarmTypes.action",
			type: 'get',
			dataType: 'json',
			success: function(data){
		        if(data){
		        	 var existAlarmTypes=null;
		        	 if(data.alarmTypes){
		        		 existAlarmTypes=data.alarmTypes;
		        		 if(existAlarmTypes){
			        		 existAlarmTypes=existAlarmTypes.split(',');
			        	 }
		        	 }
		        	 var checkBox = '<ul>';
		        	 $.each(data.allAlarmTypes, function(i, n){
		        		var obj = eval(n);
		        		 // 加入复选框
				        checkBox += "<li><input type='checkbox' id='" + obj.alarmTypeId + "' name='alarmTypeCkb'";
				        if(existAlarmTypes){
				           $.each(existAlarmTypes, function(i, n){
							        if(n == obj.alarmTypeId){
							          checkBox += " checked='checked'";
							        }
							});
				        }
				        checkBox += "/><label for='"+ obj.alarmTypeId +"' title='"+ obj.alarmTypeName +"'>"+ obj.alarmTypeName +"</label></li>";
		        	});
		        	 checkBox += '</ul>';
					$('#alarmTypeCkbDiv').append(checkBox);
					$('#startTime').val(data.startTime);
		            $('#endTime').val(data.endTime);
		        }
        	}
		});
	});
	
	function getAlamryTypes(){
		var alarmTypes=[];
	    $("input[name='alarmTypeCkb']").each(function(){
	        if($(this).attr("checked") == true||$(this).attr("checked") == "checked"){
	        	alarmTypes.push($(this).attr('id'));            //动态拼取选中的checkbox的值，用“|”符号分隔
	        }
	    });
	    return alarmTypes;
	}
	
	//设置用户要查看的警情类型
	function setUserAlarmTypes(){
		var alarmTypes=getAlamryTypes();
		var startTime = $('#startTime').val();
		var endTime = $('#endTime').val();
		if(!alarmTypes||alarmTypes.length==0){
			$.messager.alert(tipMsgDilag,"请至少选择查看一种警情类型!");
			return false;
		}
		
		if((startTime==""&&endTime=="")||startTime>endTime){		 
			$.post("run/alarm_setUserAlarmTypes.action", {
	    		'userAlarmTypesPOJO.alarmTypes':alarmTypes.join(','),
	    		'userAlarmTypesPOJO.startTime':startTime,
	    		'userAlarmTypesPOJO.endTime':endTime
			}, function(data) {
				var r = $.parseJSON(data);
					if (r.success) {
						$('#alarm_type_dialog').dialog('close');
					}
					$.messager.alert(tipMsgDilag, r.msg);
			});
		
		
	}else{
	    $.messager.alert(tipMsgDilag,"时间过虑设置的开始值必须小于结束值!");
			return false;
	}
}
	//显示列设置:是否全选
	function changeChk(){
		if(document.getElementById("chk_isSelectAll").checked){
			$("input[name='alarmTypeCkb']").each(function() {
		      	$(this).attr("checked",true);
			});
		}else{
			$("input[name='alarmTypeCkb']").each(function() {
		      	$(this).attr("checked", false);
			});
		}
	}

	function show(){
    	$("#loading2").fadeOut("normal", function(){
        	$(this).remove();
            //修改表格的宽度
            var wid =$(document.body).width();
            var hei =$('#main').height();
		});
	}    
   	var delayTime;
   	$.parser.onComplete = function(obj){
    	if(delayTime) 
        	clearTimeout(delayTime);
       	delayTime = setTimeout(show,1);
   	};
</script>
