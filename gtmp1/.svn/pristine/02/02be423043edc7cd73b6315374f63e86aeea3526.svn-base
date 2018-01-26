//默认查询条件
var refreshtLastPositionTime = 30000;//刷新的位置,指令信息间隔时间 10秒
var outTimeFlag = true;
var vehicleDef=null;//用于地图
var uid ="";
var vehicleId_test="";
var outTime = 30000;//每隔十秒钟取一次最新位置和工况信息
var defQueryParams={'testLocation':1};

//窗口大小变化
function vehicleQaResize(w,h){
	if($('#vehicle_qa_datagrid')){
		try{
		  $('#vehicle_qa_datagrid').datagrid('options');
		  $('#vehicle_qa_datagrid').datagrid('resize', {  
				width : w-2,
				height:h
		 }); 
		}catch(e){
		}
	}
}
//查询
function queryQa(){
	var testTime= $('#testTime_search').val();
	 var testTime2= $('#testTime2_search').val();
	if(testTime&&testTime2&&(testTime>=testTime2)){
	   $.messager.alert('提示','测试结束时间必须在测试开始时间之后!');
	   return;
	}
	var qaTime= $('#qaTime_search').val();
	 var qaTime2= $('#qaTime2_search').val();
	if(qaTime&&qaTime2&&(qaTime>=qaTime2)){
	   $.messager.alert('提示','质检结束时间必须在质检开始时间之后!');
	   return;
	}
       $('#vehicle_qa_datagrid').datagrid('load',{    
			vehicleDef: $('#vehicleDef_search').val(),    
			//testResult: $('#testResult_search').combobox('getValue'),
			supperierSn: $('#supperierSn_search').combobox('getValue'),
			qaResult: $('#qaResult_search').combobox('getValue'),
			testLocation: $('#isQa_search').combobox('getValue'),
			testTime: testTime,
			testTime2: testTime2,    
			qaTime: qaTime,    
			qaTime2: qaTime2
	    });  
}
//质检结果设置
function setQaPass(){
	  var nodes = $('#vehicle_qa_datagrid').datagrid('getChecked');
		 var idList=[];
		  var vehicleIdList=[];
	      for(var i=0;i<nodes.length;i++){
	    	  if(nodes[i].qaResult !=0){
	    	      idList.push(nodes[i].testId);
	    	      vehicleIdList.push(nodes[i].vehicleId);
	    	  }
	      }
		if(idList.length<1||nodes.length<1){
			$.messager.alert(tipMsgDilag,'请选择未质检或者末通过质检的测试信息!','info');
			return;
		}
	$.messager.prompt(tipMsgDilag, '是否通过？输入Y表示通过，其他字符表示不通过！', function(r){
	if (r){
		var qaResult=0;//通过
		if(!(r=='Y'||r=='y')){
		  qaResult=1;
		}
		 $.post('vehicle/vehicleTest_updateTest.action',
				 jQuery.param({
				 qaResult:qaResult,	
				 idList:idList,
				 vehicleIdList:vehicleIdList
				 },true),
	    			function(result){
	      	  	     try {
							var r = $.parseJSON(result);
							if (r.success) {
								$('#vehicle_qa_datagrid').datagrid('reload');
								$.messager.alert('提示', r.msg);
							} 
          
						} catch (e) {
							$.messager.alert('提示', e);
						}
	      			});
	}
});
}
function commandFormatter(val,row,dataIndex){
	 var testFlag = row.testFlag;
	  //if(testFlag==0 && status != 3 && removeFlag!=1){
		  return '<a style="color:blue;cursor:pointer" onclick=testCheck(\''+val+'\',\''+row.vehicleId+'\')>测试</a>|<a style="color:blue;cursor:pointer" onclick=openTestCommandWin(\''+val+'\')>详细测试指令</a>';
	 // }
	 // else if(testFlag==1){
	//	  return '<font color="green">通过测试</font>|<a style="color:blue;cursor:pointer" onclick=openTestCommandWin(\''+val+'\')>详细测试指令</a>';
	//  }else{
	//	  return '<a style="color:blue;cursor:pointer" onclick=openTestCommandWin(\''+val+'\')>详细测试指令</a>';
	//  }
	
}
//机械测试页面弹出框
//

//机械测试页面弹出框
function testCheck(val,vehicleId){
	uid=val;
	vehicleId_test =vehicleId;
	var data = $('#test_result').datagrid('getData');
	if(data.total>=1){//清空表格
		$('#test_result').datagrid('deleteRow',0);	//通过行号移除该行
	}
	$('#test_result').datagrid('appendRow',{
	unitId:'',
	commandTypeName:'',
	commandResult: 9,
	unitBack: 9,
	stamp:''
});
	outTimeFlag = true;
	$('#dlg_vehicleTest_operate').dialog('open');
	//$('#tLastPosition_operate_form').form('clear');
	$('#tLastCondition_operate_form').form('clear');
	//$('#vehicle_operateTest_form').form('clear');	
	//outTimeInfo();
	$('#check0').val(3);
	$('#check7').val(3);
	$('#check1').val(3);
	$('#check3').val(3);
	$('#check2').val(3);
	$('#check4').val(3);
	$('#check5').val(3);
	$('#check6').val(3);
	$.ajaxSettings.async = false;
    $.post("vehicle/vehicle_getById.action", {
  	  vehicleId:vehicleId
				}, function(data) {
					var obj = $.parseJSON(data);
					$('#unitId').val(obj.datas.unitId);
					uid = obj.datas.unitId;
					$('#vehicleId').val(vehicleId);
					$('#unitIdTest').val(obj.datas.unitId);
					$('#vehicleIdTest').val(vehicleId);
					$('#vehicleDefs').val(obj.datas.vehicleDef);
					if(obj.test){
					$('#check0').val(obj.test.testLocation);
					$('#check7').val(obj.test.test_reserve1);
					$('#check1').val(obj.test.testAEnable);
					$('#check3').val(obj.test.testBEnable);
					$('#check2').val(obj.test.testADisable);
					$('#check4').val(obj.test.testBDisable);
					$('#check5').val(obj.test.testProtectEnable);
					$('#check6').val(obj.test.testProtectDisable);
					}
					vehicleDef= obj.datas.vehicleDef;//用于地图
					//$('#vehicleDefTest').val(vehicleDef);
					$('#dlg_vehicleTest_operate').dialog('setTitle',"机械编号:"+vehicleDef);
					outTimeInfo(uid);
				});
}




//每隔十秒钟取一次最新位置和工况信息
function outTimeInfo(){
	//每隔十秒钟取一次最新位置和工况信息
	if(outTimeFlag){
	setTimeout(outTimeInfo,outTime);	
	}
	$.ajax({
		   type : "POST",
		   data: {unitId:uid}, 
		   url : "vehicle/vehicle_selectLastPosition.action",
		   /*error : function(jqXMLRequest, textStatus, errorThrown){
			   $.messager.alert(tipMsgDilag, "错误类型：" + textStatus + "<br />错误信息：" + errorThrown, 'error');
		   },*/
		   success : function(result){
			   var r = $.parseJSON(result);
			   if(r.tLastPosition!=null){
			   		r.tLastPosition.lon = r.tLastPosition.lon+"度"; //车台经纬度
			   		r.tLastPosition.lat = r.tLastPosition.lat+"度"; //车台经纬度
			   		r.tLastPosition.speed = r.tLastPosition.speed+"KM/H"; //车速度
					if( r.tLastPosition.locationState==1){
				 	   r.tLastPosition.locationState='定位';
					}
					if( r.tLastPosition.locationState==0){
				 	   r.tLastPosition.locationState='正在定位';
					}
					if( r.tLastPosition.isLogin==1){
				 	   r.tLastPosition.isLogin='未登录';
					}
					if( r.tLastPosition.isLogin==0){
				 	   r.tLastPosition.isLogin='已登录';
					}
			   }
					//工况加上单位处理
			   if(r.tLastConditions!=null){
				 //是否工作
					r.tLastConditions.isWork='未工作';
					if((r.tLastConditions.enginespeed>600)|| (r.tLastConditions.engineoilpressure>0.1) ||
							(r.tLastConditions.systemvoltage>26.1&&r.tLastConditions.systemvoltage<29)||(r.tLastConditions.systemvoltage>12.5&&r.tLastConditions.systemvoltage<15)
							){
				 	   r.tLastConditions.isWork='工作';
				 	   
					}
					r.tLastConditions.enginecoolanttemperature = r.tLastConditions.enginecoolanttemperature +"℃";//发动机冷却液温度
					r.tLastConditions.enginecoolantlevel = r.tLastConditions.enginecoolantlevel +"%";//发动机冷却液液位
					r.tLastConditions.engineoilpressure = r.tLastConditions.engineoilpressure +"MPa";//发动机机油压力
					r.tLastConditions.enginefuellevel = r.tLastConditions.enginefuellevel +"%";//发动机燃油油位
					r.tLastConditions.enginespeed = r.tLastConditions.enginespeed;//发动机转速
					r.tLastConditions.systemvoltage = r.tLastConditions.systemvoltage +"V";//系统电压
					r.tLastConditions.beforepumpmainpressure = r.tLastConditions.beforepumpmainpressure +"MPa";//挖掘机前泵主压压力
					r.tLastConditions.afterpumpmainpressure = r.tLastConditions.afterpumpmainpressure +"MPa";//挖掘机后泵主压压力
					r.tLastConditions.hydraulicoiltemperature = r.tLastConditions.hydraulicoiltemperature +"℃";//液压油温度
					r.tLastConditions.pilotpressure1 = r.tLastConditions.pilotpressure1 +"MPa";//挖掘机先导压力1
					r.tLastConditions.pilotpressure2 = r.tLastConditions.pilotpressure2 +"MPa";//挖掘机先导压力2
					r.tLastConditions.beforepumppressure = r.tLastConditions.beforepumppressure +"MPa";//挖掘机前泵负压压力
					r.tLastConditions.afterpumppressure = r.tLastConditions.afterpumppressure +"MPa";//挖掘机后泵负压压力
					r.tLastConditions.totalworkinghours = r.tLastConditions.totalworkinghours +"H";//累计工作小时
					r.tLastConditions.correcthours = r.tLastConditions.correcthours +"H";//修正时间
					r.tLastConditions.gear = r.tLastConditions.gear +"档";//挖机档位
					r.tLastConditions.proportionalcurrent1 = r.tLastConditions.proportionalcurrent1 +"mA";//挖掘机比例阀电流1
					r.tLastConditions.highenginecoolanttemperval = r.tLastConditions.highenginecoolanttemperval +"℃";//发动机冷却液温度高报警设置值
					r.tLastConditions.lowengineoilpressurealarmvalue = r.tLastConditions.lowengineoilpressurealarmvalue +"MPa";//发动机机油压力低报警设置值
					r.tLastConditions.highvoltagealarmvalue = r.tLastConditions.highvoltagealarmvalue +"V";//系统电压高报警设置值
					r.tLastConditions.proportionalcurrent2 = r.tLastConditions.proportionalcurrent2 +"mA";//挖掘机比例阀电流2
					r.tLastConditions.lowvoltagealarmvalue = r.tLastConditions.lowvoltagealarmvalue +"V";//系统电压低报警设置值
					r.tLastConditions.highhydraulicoiltemperalarmval = r.tLastConditions.highhydraulicoiltemperalarmval +"℃";//液压油温高报警值
					r.tLastConditions.engineOilTemperature = r.tLastConditions.engineOilTemperature +"℃";//发动机机油温度
					r.tLastConditions.enginespeed = r.tLastConditions.enginespeed +"r/min";//发动机转速
					
					
					//a锁
					
					if(r.tLastConditions.aLock==2){
					 	   r.tLastConditions.aLock='A锁锁车';
						}else if(r.tLastConditions.aLock==3){
	                       r.tLastConditions.aLock='A锁解锁';
	                    }else if(r.tLastConditions.aLock==1){
	                       r.tLastConditions.aLock='A锁未执行';
	                    }else {
	                       r.tLastConditions.aLock='';
	                    }
					
					//b锁
					
					if(r.tLastConditions.bLock==2){
				 	   r.tLastConditions.bLock='B锁锁车';
					}else if(r.tLastConditions.bLock==3){
                       r.tLastConditions.bLock='B锁解锁';
                    }else if(r.tLastConditions.bLock==1){
                       r.tLastConditions.bLock='B锁未执行';
                    }else {
                       r.tLastConditions.bLock='';
                    }
					
					//使能状态
					if(r.tLastConditions.cLock==0){
						
				 	   r.tLastConditions.cLock='';
					}
					if(r.tLastConditions.cLock==1){
				 	   r.tLastConditions.cLock='使能防拆';
					}
					if(r.tLastConditions.cLock==2){
				 	   r.tLastConditions.cLock='禁止防拆';
					}
					
			   }
			 
			 //地图上显示
				 if(typeof  ifm_map_test.window.addLastGpsPosition == "function"){
				 	//if(r.tLastPosition!=null){
					 ifm_map_test.window.addLastGpsPosition(r.tLastPosition,vehicleDef,0,r.tLastConditions);
				 	//}
			     }	
			     
			   //$('#tLastPosition_operate_form').form('load', r.tLastPosition);
				  $('#tLastCondition_operate_form').form('load', r.tLastConditions);
			   }
		  });
	
	

}


//打开测试指令详细信息
function openTestCommandWin(unitId){
	$('#test_command_datagrid').datagrid({
		iconCls : 'icon-ok',
		width : 'auto',
		height : 'auto',

		 pagination:true,//显示分页  
         pageSize:10,//分页大小  
         pageList:[10,15,20],//每页的个数  
         fit:true,//自动补全  
         fitColumns:true,   
		url:'vehicle/vehicleTest_searchTestCommand.action',
		queryParams:{unitId:unitId},
		idField:'commandId',
		singleSelect:false,
		columns : [ [{
			title : '指令类型',
			field : 'commandTypeName',
			width : '195',
			align : 'center'
		},{
			title : '参数',
			field : 'param',
			width : '120',
			align : 'center'
		},{
			title : '指令下发结果',
			field : 'commandResult',
			width : '100',
			align : 'center',
			formatter:function(val,row,index){
				if(val==0){
				  return '成功';
				}else{
				  return '失败';
				}
			}
		} ,{
			title : '测试人员',
			field : 'userName',
			width : '120',
			align : 'center'
		},{
			title : '时间',
			field : 'stamp',
			width : '150',
			align : 'center'
		}] ],
		rownumbers : true
	});
	
		$('#dlg_test_command').dialog('open');
}


//导出
function downExcel(){
 var testTime= $('#testTime_search').val();
	 var testTime2= $('#testTime2_search').val();
	if(testTime&&testTime2&&(testTime>=testTime2)){
	   $.messager.alert('提示','测试结束时间必须在测试开始时间之后!');
	   return;
	}
	var qaTime= $('#qaTime_search').val();
	 var qaTime2= $('#qaTime2_search').val();
	if(qaTime&&qaTime2&&(qaTime>=qaTime2)){
	   $.messager.alert('提示','质检结束时间必须在质检开始时间之后!');
	   return;
	}
	    var url="vehicle/vehicleTest_exportToExcel.action?vehicleDef="+$('#vehicleDef_search').val();
	    //url+="&testResult="+$('#testResult_search').combobox('getValue');
	    url+="&supperierSn="+$('#supperierSn_search').combobox('getValue');
	    url+="&qaResult="+$('#qaResult_search').combobox('getValue');
	    url+="&testLocation="+$('#isQa_search').combobox('getValue');
	    url+="&testTime="+testTime;
	    url+="&testTime2="+testTime2;
	    url+="&qaTime="+qaTime;
	    url+="&qaTime2="+qaTime2;
	    
	   window.location.href=encodeURI(encodeURI(url));
	   return;
}


//机械测试页面确认
function saveVehicleTest(){
	

			 $.messager.confirm(tipMsgDilag,"确定保存测试结果?",
							   function(r){  
								    if (r){ 
  		$.ajax({
			   type : "POST",
			   data : $("#vehicle_operateTest_form").serialize(), 
			   url : "vehicle/vehicleTest_saveOrUpdateTest.action?unitId="+uid+"&vehicleId="+vehicleId_test,
			   error : function(jqXMLRequest, textStatus, errorThrown){
				   $.messager.alert(tipMsgDilag, "错误类型：" + textStatus + "<br />错误信息：" + errorThrown, 'error');
			   },
			   success : function(result){
				   var r = $.parseJSON(result);
				   $.messager.alert(tipMsgDilag, r.msg);
				   $('#dlg_vehicleTest_operate').dialog('close');
				   $('#vehicle_datagrid').datagrid('reload');
				   }
			  });
								    }});
	
}


function dlgTestClose(){
	outTimeFlag = false;
}

//4秒钟后开关打开
function openTrue(){
var bn = document.getElementsByTagName("input");
	//for (var i = 0; i < bn.length; i++) {						
	//if (bn[i].type == "button" ) 					
	//	{				
	//	 bn[i].disabled = false;
	//	}
	//}	
	document.getElementById("testButtonTrue1").style.display = "block";
	document.getElementById("testButtonTrue2").style.display = "block";
	document.getElementById("testButtonTrue3").style.display = "block";
	document.getElementById("testButtonTrue4").style.display = "block";
	document.getElementById("testButtonTrue5").style.display = "block";
	document.getElementById("testButtonTrue6").style.display = "block";
	document.getElementById("testButtonTrue7").style.display = "block";
	document.getElementById("testButtonTrue8").style.display = "block";
	document.getElementById("testButtonFalse1").style.display = "none";
	document.getElementById("testButtonFalse2").style.display = "none";
	document.getElementById("testButtonFalse3").style.display = "none";
	document.getElementById("testButtonFalse4").style.display = "none";
	document.getElementById("testButtonFalse5").style.display = "none";
	document.getElementById("testButtonFalse6").style.display = "none";
	document.getElementById("testButtonFalse7").style.display = "none";
	document.getElementById("testButtonFalse8").style.display = "none";
	openFlag = false;
}
var openFlag;
//机械注册测试指令下发
function sendCommand(commandTypeId,pHeartbeatContent){
	//开关控制标志位
	 openFlag = true;
	//var bn = document.getElementsByTagName("input");
	//for (var i = 0; i < bn.length; i++) {						
	//if (bn[i].type == "button" ) 					
	//	{				
	//	 bn[i].disabled = true;
	//	 openFlag = false;
	//	}
	//}
	document.getElementById("testButtonTrue1").style.display = "none";
	document.getElementById("testButtonTrue2").style.display = "none";
	document.getElementById("testButtonTrue3").style.display = "none";
	document.getElementById("testButtonTrue4").style.display = "none";
	document.getElementById("testButtonTrue5").style.display = "none";
	document.getElementById("testButtonTrue6").style.display = "none";
	document.getElementById("testButtonTrue7").style.display = "none";
	document.getElementById("testButtonTrue8").style.display = "none";
	document.getElementById("testButtonFalse1").style.display = "block";
	document.getElementById("testButtonFalse2").style.display = "block";
	document.getElementById("testButtonFalse3").style.display = "block";
	document.getElementById("testButtonFalse4").style.display = "block";
	document.getElementById("testButtonFalse5").style.display = "block";
	document.getElementById("testButtonFalse6").style.display = "block";
	document.getElementById("testButtonFalse7").style.display = "block";
	document.getElementById("testButtonFalse8").style.display = "block";
	openFlag = false;
	if(!openFlag){
		//4秒钟后开关打开
		setTimeout(openTrue,4000);	
	}

	
	
	var unitId = uid;
	
	if(commandTypeId!=null && commandTypeId!="" && pHeartbeatContent!=null && pHeartbeatContent!=""){
	 switch(commandTypeId){
     case 52://心跳信息(a锁，b锁等)
     commandParams={
     	unitIds:unitId,
     	commandTypeId:commandTypeId,
     	pHeartbeatContent:pHeartbeatContent,
     	pHeartbeatInterval:2,
     	pCanId:"00AC",
     	testFlag:1
     };
     break;
	 }
	}
	else{
		switch(commandTypeId){
	     case 32://位置信息
	     commandParams={
	     	unitIds:unitId,
	     	commandTypeId:commandTypeId,
	     	testFlag:1
	     };
	     break;
	     case 81://工况信息
		     commandParams={
		     	unitIds:unitId,
		     	commandTypeId:commandTypeId,
		     	pCanSendTime:2,
		     	pCollectInterval:100,
		     	testFlag:1
		     };
		     break;
	     
		 }	
		
	}
	//调用action
	 sendCommandAction(commandParams);
	
}

//调用action 发指令
function sendCommandAction(commandParams) {
	testFlag = false;
	// 调用action
	$.post('run/command_sendCommand4Test.action', $.param(commandParams, true),
			function(result) {
				try {
					var r = $.parseJSON(result);
					if (r.success) {
					}
					$.messager.show({
						title : tipMsgDilag,
						msg : r.msg,
						timeout : 1500,
						showType : 'slide'
					});
				} catch (e) {
					$.messager.alert(tipMsgDilag, '失败!');
				}
			});
	//位置测试
	//if(commandParams.commandTypeId == 32){
		refreshtLastPositionWorkResponsInfo(commandParams.commandTypeId);
	//}
}

//定时刷新GPS,工况,指令信息 10秒
function refreshtLastPositionWorkResponsInfo(commandTypeId){
	if(testFlag==false){
		//从memcache中取数据
		 $.post('run/run_getMemcacheDataTest.action',
	    			function(result){
	      	  	     try {
							var r = $.parseJSON(result);
							var response=r.response;//指令回应信息
							var isSuccess=response[0].isSuccess;//指令是否成功
							var workInfo=r.workInfo;//工况信息
							var gpsInfo=r.gpsInfo;//gps信息
							var testlog=r.testlog;//指令反馈  展示用的
							addGpsInfos(testlog);
							if(isSuccess==00){
								$.messager.alert(tipMsgDilag, "该指令网关已经下发!");
								testFlag = true;
									if( gpsInfo[0].locationState==1){
								 	   gpsInfo[0].locationState='定位';
									}
									if( gpsInfo[0].locationState==0){
								 	   gpsInfo[0].locationState='正在定位';
									}
									//地图上显示
									 if(typeof  ifm_map_test.window.addLastGpsPosition == "function"){
										 ifm_map_test.window.addLastGpsPosition(gpsInfo[0],vehicleDef,0);
								     }
									//$('#tLastPosition_operate_form').form('load', gpsInfo[0]);
									$('#tLastCondition_operate_form').form('load', workInfo[0]);
								
							}
						} catch (e) {
						}
	    	}
	    	);
	
	setTimeout(refreshtLastPositionWorkResponsInfo,refreshtLastPositionTime);
	}
}

//添加一条信息到gps信息的表格中
function addGpsInfos(obj){
	//删除最后一行
var data = $('#test_result').datagrid('getData');
	if(data.total>=1){//清空表格
		$('#test_result').datagrid('deleteRow',0);	//通过行号移除该行
	}
	//赋值
	$('#test_result').datagrid('appendRow',obj);
}
