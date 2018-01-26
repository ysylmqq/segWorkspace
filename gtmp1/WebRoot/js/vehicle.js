refreshtLastPositionTime = 30000;//刷新的位置,指令信息间隔时间 10秒
outTime = 30000;//每隔十秒钟取一次最新位置和工况信息
var outTimeFlag = true;
var testFlag = false; //统计次数
var workInfData = "";
var uid2="";
var vehicleDef=null;//用于地图
var uid = "";
var vehicleId_test ="";
//默认查询条件
var defQueryParams={'fixDateStart':getTodayZero(), 'fixDateEnd':new Date().formatDate(timeFormat)};

//表格中的操作
function operate(val,row,index){
	var searchFlag = $('#status_search').combobox('getValue');
	console.log("index ",searchFlag);
	var vehicleId = row.vehicleId;
	var testFlag = row.testFlag;
	var isValid = row.isValid;
	var status = row.status;
	var removeFlag = row.removeFlag;
	var str1="";
	  if(status!=3 && testFlag!=1){
	   str1+='<a style="color:blue;" href="javascript:void(0)" onclick="openDlg4VehicleOperate(\''+vehicleId+'\')">编辑</a>';
	  }
	  if(status!=3 && testFlag!=1){
	  str1+=' <a style="color:blue;" href="javascript:void(0)" onclick="delete_vehicle(\''+vehicleId+'\')">删除</a>';
	  }
	  if(testFlag==0 && status != 3 && removeFlag!=1){
		  str1+=' <a style="color:blue;" href="javascript:void(0)" onclick="testCheck(\''+vehicleId+'\')">测试</a>';  
	  }
	  if(testFlag==1){
		  str1+=' <font color="green">通过测试</font>';  
	  }
	  //if(status != 3) {
	  //	str1+=' <a style="color:blue;" href="javascript:void(0)" onclick="testCheck(\''+vehicleId+'\')">测试</a>';
	  //}  
	  str1+=' <a style="color:blue;" href="javascript:void(0)" onclick="openWorkInfoWin(\''+vehicleId+'\')">历史工况信息</a>';
	  
	  if(removeFlag==1){
	  	  str1+=' <font color="red">已解除绑定</font>';
	  }
	  if((status==1 || status == 2) && removeFlag !=1 && testFlag!=1){
	  	  str1+=' <a style="color:blue;" href="javascript:void(0)" onclick="removeUnitSn(\''+vehicleId+'\')">解除绑定</a>';
	  }
	  if(isValid == 1){
		  str1 = '<a style="color:blue;" href="javascript:void(0)" onclick="openDlg4VehicleOperate(\''+vehicleId+'\')">编辑</a>';
		  if(removeFlag==1){
		  	  str1+=' <font color="red">已解除绑定</font>';
		  }
		  if(status==1 || status == 2 && removeFlag !=1){
		  	  str1+=' <a style="color:blue;" href="javascript:void(0)" onclick="removeUnitSn(\''+vehicleId+'\')">解除绑定</a>';
		  }
	  }
	  if(searchFlag == "3"){
	  	  str1+=' <a style="color:blue;" href="javascript:void(0)" onclick="updateSaleDate(\''+vehicleId+'\')">修改销售日期</a>';
	  }
	  
	  return str1;
}

//更新销售日期
function updateSaleDateBtn(){
	var saleDate = $("#saleDate").val();
	var vehicleId =	$("#updateVehId").val();

	$.post("vehicle/vehicle_updateSaleDate.action",{vehicleId:vehicleId,saleDate:saleDate},function(result){
		if(result != null  && result != ""){
			var obj = JSON.parse(result); 
			if(obj.success){
				$('#vehicle_datagrid').datagrid('reload');
				$('#updateSaleDateWindow').dialog('close');
			}
		}
	});
	
}
//修改销售日期
function  updateSaleDate(vehicleId){
	$("#updateVehId").val(vehicleId);
	$('#updateSaleDateWindow').dialog('open');
	$.post("vehicle/vehicle_selectVehicleByVehicleId.action",{vehicleId:vehicleId},function(result){
		if(result != null  && result != ""){
			var obj = JSON.parse(result); 
			$("#saleDate").val(obj.saleDate);
		}
	});
	
}

//解除终端
function removeUnitSn(vehicleId){
	$.messager.confirm(tipMsgDilag,"确定解除终端绑定？",
		function(r){  
			if (r){ 
				$.post("vehicle/vehicle_removeUnitSn.action",
					{vehicleId:vehicleId},
					function(data) {
						var obj = $.parseJSON(data);
						if(obj.datas){
							$.messager.alert(tipMsgDilag, '解除绑定成功！');
							$('#vehicle_datagrid').datagrid('reload');
							$('#vehicle_recycle_datagrid').datagrid('reload');
						} else {
							$.messager.error(tipMsgDilag, '解除绑定失败！');
						}
					}
				);
		}});
}

//查询历史工况记录
function openWorkInfoWin(vehicleId){
	$.post("vehicle/vehicle_getById.action", {
  	  vehicleId:vehicleId
				}, function(data) {
					var obj = $.parseJSON(data);
					$('#unitId').val(obj.datas.unitId);
					uid = obj.datas.unitId;
					workInfData = obj.datas.unitId;
					$('#vehicleId').val(vehicleId);
					$('#unitIdTest').val(obj.datas.unitId);
					$('#vehicleIdTest').val(vehicleId);
				});
				
	$('<div/>').dialog({
		content : '<iframe src="jsp/run/history_workinfo.jsp" id="ifm_workinfo" name="ifm_workinfo" frameborder="0" style="border:0;width:100%;height:99%;"></iframe>',
		width : 920,
        height : 550,
		modal : true,
		cache : false,
		resizable:true,
		title : '历史工况',
		maximizable : true,
		buttons : [{
			text : '关闭',
			iconCls : 'icon-cancel',
			handler : function() {
				var d = $(this).closest('.window-body');
				d.dialog('destroy');
			}
		}],
		onLoad : function() {
			/*
			 * //加在地图上 if(typeof map.window.addPositionMarker == "function"){
			 * //map.window.addPositionMarker(data.lon,data.lat,data.vehicleDef);
			 * map.window.addPositionMarker(112.897095,28.227175,'常州A001'); }
			 */
		},
		onOpen : function() {
		},
		onClose : function() {
			$(this).dialog('destroy');
		}
	});
	
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

//机械注册测试指令下发
function sendCommand(commandTypeId,pHeartbeatContent){
	//开关控制标志位
	var openFlag = true;
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

	
	
	var unitId = $('#unitId').val();
	
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


// 机械测试页面弹出框
function testCheck(vehicleId){
	vehicleId_test = vehicleId;
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
				});
				uid2 = uid
				outTimeInfo(uid);
	
	
	
}

//每隔十秒钟取一次最新位置和工况信息
function outTimeInfo(){
	//每隔十秒钟取一次最新位置和工况信息
	if(outTimeFlag){
	setTimeout(outTimeInfo,outTime);	
	}
	$.ajax({
		   type : "POST",
		   data: {unitId:uid2}, 
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

//查询
function queryVehicle(){
       $('#vehicle_datagrid').datagrid('load',{    
	       	unitSn: $('#unintSn_search').val(), 
	       	simNo: $('#simNo_search').val(), 
			vehicleDef: $('#vehicleDef_search').val(),    
			//typeName: $('#typeName_search').combobox('getValue'),
			vehicleCode: $('#vehicleCode_search').combobox('getValue'),
			vehicleArg: $('#vehicleArg_search').val(),
			modelName: $('#modelName_search').combobox('getValue'),
			status: $('#status_search').combobox('getValue'),
			fixMan: $('#fixMan_search').val(),    
			fixDateStart: $('#fixDateStart_search').val(),    
			fixDateEnd: $('#fixDateEnd_search').val(),
			testFlag: $('#testFlag_search').combobox('getValue')
	    });  
}

//查询测试指令
function queryTest(){
	       $('#vehicleTest_datagrid').datagrid('load',{    
			vehicleDef: $('#vehicleDef_test').val(),    
			fixDateStart: $('#fixDateStart_test').val(),    
			fixDateEnd: $('#fixDateEnd_test').val(),
			typeId: $('#vehicleType_test').datebox('getValue')
	    });  
	
}

//机械测试指令导出
function downExcel(){
	
	 var vehicleDef =  $('#vehicleDef_test').val();    
	 var vehicleType =   $('#vehicleType_test').datebox('getValue');
	 var startTime= $('#fixDateStart_test').val();
	 var endTime= $('#fixDateEnd_test').val();
	 
	window.location.href=encodeURI(encodeURI("vehicle/vehicle_exportToExcel.action?vehicleDef="+$('#vehicleDef_test').val()+"&startTime="+startTime+"&endTime="+endTime+"&vehicleType="+vehicleType));
	   return;
}

//机机注册导出
function downExcelVehicle(){
	var vehicleCode = $('#vehicleCode_search').combobox('getValue') ;
	var vehicleArg = $('#vehicleArg_search').val();
	if($('#vehicleCode_search').combobox('getValue')=='全部'){
		vehicleCode='';
	}
	if($('#vehicleArg_search').val()==null){
		vehicleArg='';
	}
	window.location.href=encodeURI(encodeURI("vehicle/vehicle_exportToExcelVehicle.action?unitSn="+$('#unintSn_search').val()+
							"&simNo="+$('#simNo_search').val()+
							"&vehicleDef="+$('#vehicleDef_search').val()+
							"&modelName="+$('#modelName_search').combobox('getValue')+
							"&vehicleCode="+vehicleCode+
							"&vehicleArg="+vehicleArg+
							"&status="+$('#status_search').combobox('getValue')+
							"&fixMan="+$('#fixMan_search').val()+
							"&fixDateStart="+$('#fixDateStart_search').val()+
							"&fixDateEnd="+$('#fixDateEnd_search').val()+
							"&testFlag="+$('#testFlag_search').combobox('getValue')));
	   return;
}

//新增或者更新
function saveVehicle(){
	 if(!$('#vehicle_operate_form').form('validate')){
		 $.messager.alert(tipMsgDilag,"缺少必填项");
		     return;
   }
   //保存操作
    $.post('vehicle/vehicle_saveOrUpdate.action',
		$("#vehicle_operate_form").serialize(),
		function(result){
 	     	try {
				var r = $.parseJSON(result);
				if (r.success) {
					$('#dlg_vehicle_operate').dialog('close');
					$('#vehicle_datagrid').datagrid('reload');
					$('#vehicle_recycle_datagrid').datagrid('reload');
					/*$('#sava').hide();
					$('#savaSuc').show();*/
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
	var objtype=$('#upload',$('#vehicle_impport_Form')).val();
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
           $('#vehicle_impport_Form').form('submit', { 
				url: 'vehicle/vehicle_impFromExcel.action',
				onSubmit: function(){ 
				  return $(this).form('validate');
				}, 
				success:function(data){ 
				  var r=null;
				  try{
				          r = $.parseJSON(data);
					      if (r.success) {
						    $('#dlg_vehicle_impport').dialog('close');
						    $.messager.alert(tipMsgDilag, r.msg);
						  }
						  else{
						  	$.messager.alert(tipMsgDilag, r.msg);
						  }
						  
					  }catch (e) {
						  $.messager.alert(tipMsgDilag,"程序内部数据错误");
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

//新增或者编辑窗口
function openDlg4VehicleOperate(vehicleId){
	var docArr = [];   
	$('#dlg_vehicle_operate').dialog('open');
 	//初始化数据(新增时)
	if(vehicleId==null){
		$('#vehicle_operate_form').form('clear');
		$('#testId').show();
		$('#testIdTmp').hide();
		$.ajax({
			url: "vehicle/vehicle_searchUsefulUnitInfoList.action",
			type: 'get',
			dataType: 'json',
			success: function(data) {
				for(var i=0; i<data.length; i++){
					docArr.push(data[i].materialNo);
				}
				$("#materialNo").autocomplete(
					docArr,{
						matchContains: true,
						minChars: 0
					}
				).result(function(event, item){
					for ( var i = 0; i < data.length; i++) {
						if (item == data[i].materialNo) {
							$('#unitId').val(data[i].unitId);
							$('#unitSn').val(data[i].unitSn);
							$('#steelNo').val(data[i].steelNo);
							$('#simNo').val(data[i].simNo);
							return;
						}
					}
				});
			}
		});
	}
	else{
		$("#modelName").empty();
		//编辑
	      $.post("vehicle/vehicle_getById.action", {
	    	  vehicleId:vehicleId
					}, function(data) {
						var obj = $.parseJSON(data);
//						$('#unitId').val(obj.datas.unitId);
//						$("#unitSn").val(obj.datas.unitSn);
						if(obj.datas.modelName!=null && obj.datas.modelName!="" && obj.datas.modelName!="null"){
							$("#modelName").append("<option value='"+obj.datas.modelId+"'>"+obj.datas.modelName+"</option>");
						}
						$('#vehicle_operate_form').form('load', obj.datas);
						if(obj.datas.removeFlag == 0){ // 如果未解绑定，则不允许修改终端信息
							$('#materialNo').attr("readonly",true);
						} else { // 如果已解绑定，则提供终端信息初始化处理
							$.ajax({
								url: "vehicle/vehicle_searchUsefulUnitInfoList.action",
								type: 'get',
								dataType: 'json',
								success: function(data) {
									for(var i=0; i<data.length; i++){
										docArr.push(data[i].materialNo);
									}
									$("#materialNo").autocomplete(
										docArr,{
											matchContains: true,
											minChars: 0
										}
									).result(function(event, item){
										for ( var i = 0; i < data.length; i++) {
											if (item == data[i].materialNo) {
												$('#unitId').val(data[i].unitId);
												$('#unitSn').val(data[i].unitSn);
												$('#steelNo').val(data[i].steelNo);
												$('#simNo').val(data[i].simNo);
												return;
											}
										}
									});
								}
							});
						}
					});
	}
    
}

//
function unitIdSelect(obj,str){
	$('#unitSnT').val(obj);
	if(str==1){
		$('#unitSn').hide();
	}
	
	$.ajax({
		   type : "POST",
		   data: {obj:obj}, 
		   url : "vehicle/vehicle_searchByUnitId.action",
		   error : function(jqXMLRequest, textStatus, errorThrown){
			   $.messager.alert(tipMsgDilag, "获取终端信息出错!<br />错误类型：" + textStatus + "<br />错误信息：" + errorThrown, 'error');
		   },
		   success : function(json){
			    $('#wl').show();
				$('#sim').show();
				$('#gh').show();
			   var str = eval("("+json+")");
			 if(str.datas==null){
			 	//$.messager.alert(tipMsgDilag, "该序列号没有数据!");
			 	//物料
			 $('#materialNo').val("");
			 //sim卡
			 $('#simNo').val("");
			 //钢号
			 $('#steelNo').val("");
			 	return;
			 }
			 //物料
			 $('#materialNo').val(str.datas.materialNo);
			 //sim卡
			 $('#simNo').val(str.datas.simNo);
			 //钢号
			 $('#steelNo').val(str.datas.steelNo);
			 //unitId
			 $('#unitId').val(str.datas.unitId);
			   }
		  });
	
}

//验证终端序列号或者sim卡号是否存在
function checkUnitSnOrSimNo(flag){
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
function delete_vehicle(vehicleId){
	
		 $.messager.confirm(tipMsgDilag,"确定删除?",
							   function(r){  
								    if (r){ 
          $.post("vehicle/vehicle_delete.action", {
        	  vehicleId:vehicleId
				}, function(data) {
					var r = $.parseJSON(data);
						if (r.success) {
							$('#vehicle_datagrid').datagrid('reload');
						}
						$.messager.alert(tipMsgDilag, r.msg);
				});
								    }});
								    


}
//窗口大小变化
function vehicleResize(w,h){
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

function dlgTestClose(){
	outTimeFlag = false;
}

//打开回收站窗口
function openRecycleWin(){
	$('#dlg_vehicle_recycle').dialog('open');
	try{
        $('#vehicle_recycle_datagrid').datagrid("options");
        $('#vehicle_recycle_datagrid').datagrid("reload");
    }catch(e){
    	$('#vehicle_recycle_datagrid').datagrid({});  
   }
}

//回收站查询
function queryVehicleRecycle(){
	$('#vehicle_recycle_datagrid').datagrid("load",{
		unitSn: $('#recycle_unintSn_search').val(), 
       	simNo: $('#recycle_simNo_search').val(),
       	status: $('#recycle_status_search').combobox('getValue'),
		vehicleDef: $('#recycle_vehicleDef_search').val(),    
		//typeName: $('#recycle_typeName_search').combobox('getValue'),
		modelName: $('#recycle_modelName_search').combobox('getValue'),
		vehicleCode: $('#recycle_vehicleCode_search').combobox('getValue'),
		vehicleArg: $('#recycle_vehicleArg_search').val(),
		fixMan: $('#recycle_fixMan_search').val(),    
		fixDateStart: $('#recycle_fixDateStart_search').val(),    
		fixDateEnd: $('#recycle_fixDateEnd_search').val(),
		testFlag: $('#recycle_testFlag_search').combobox('getValue')
	});
}

//机械配置联动
$('#vehicleCode_search').combobox({
onSelect:function(){
var obj = $('#vehicleCode_search').combobox('getValue');
 	$("#vehicleArg_search").empty();
	$.post("vehicle/vehicleModel_getList2.action", {
		obj:obj
		}, function(data) {
			var obj = $.parseJSON(data);
			for(i=0;i<obj.arg.length;i++){
					 var docNameInfo = obj.arg[i].vehicleArg;
				 $("#vehicleArg_search").append("<option value='"+docNameInfo+"'>"+docNameInfo+"</option>");
			}
		});
}
});

//回收站机械配置联动
$('#recycle_vehicleCode_search').combobox({
onSelect:function(){
var obj = $('#recycle_vehicleCode_search').combobox('getValue');
 	$("#recycle_vehicleArg_search").empty();
	$.post("vehicle/vehicleModel_getList2.action", {
		obj:obj
		}, function(data) {
			var obj = $.parseJSON(data);
			for(i=0;i<obj.arg.length;i++){
					 var docNameInfo = obj.arg[i].vehicleArg;
				 $("#recycle_vehicleArg_search").append("<option value='"+docNameInfo+"'>"+docNameInfo+"</option>");
			}
		});
}
});

//机械型号联动控制      
$('#modelName_search').combobox({
onSelect:function(){
var obj = $('#modelName_search').combobox('getValue');
 	if(obj!=null && obj!=""){
 		//$('#vehicleArg_search').empty();
 		$('#vehicleCode_search').combobox('setValues', ['全部']);
 		$('#vehicleArg_search').empty();
 		$('#vehicleArg_search').attr("disabled",true);
 		$('#vehicleCode_search').combobox({ 
      		disabled:true 
      }); 
 	}
 	else{
 		$('#vehicleArg_search').attr("disabled",false);
 		$('#vehicleArg_search').empty();
 		$('#vehicleCode_search').combobox({ 
      		disabled:false 
      }); 
 	}

}
});

//回收站机械型号联动控制      
$('#recycle_modelName_search').combobox({
onSelect:function(){
var obj = $('#recycle_modelName_search').combobox('getValue');
 	if(obj!=null && obj!=""){
 		//$('#vehicleArg_search').empty();
 		$('#recycle_vehicleCode_search').combobox('setValues', ['全部']);
 		$('#recycle_vehicleArg_search').empty();
 		$('#recycle_vehicleArg_search').attr("disabled",true);
 		$('#recycle_vehicleCode_search').combobox({ 
      		disabled:true 
      }); 
 	}
 	else{
 		$('#recycle_vehicleArg_search').attr("disabled",false);
 		$('#recycle_vehicleArg_search').empty();
 		$('#recycle_vehicleCode_search').combobox({ 
      		disabled:false 
      }); 
 	}

}
});


//整休编号自动识别
function checkDef(obj){
	if(obj.length <10)
		return;
	var str1 = obj.substring(0,obj.length-5);
	if(obj.length==10 || obj.length==11){
	//机械代号
	var str3 = str1.substring(0,str1.length-2);
	$('#vehicleCode').val(str3);
	//$('#vehicleCode').combobox('setValues', str3);
	//机械配置
	var str2 = str1.substring(str1.length-2,str1.length);
	$('#vehicleArg').val(str2);
	//$('#vehicleArg').combobox('setValues', str2); 
	$.post("vehicle/vehicle_selectVehicleMod.action",{
    	  vehicleCode:str3,
    	  vehicleArg:str2
			}, function(data) {
				var r = $.parseJSON(data);
					if (r.vePOJO==null) {
						$.messager.alert(tipMsgDilag, "机械编号输入有误!");
						$('#vehicleCode').val('');
						$('#vehicleArg').val('');
						$('#modelName').combobox('');
					}
					else{
						//$('#modelName').val(r.vePOJO.modelName);r.vePOJO.modelIdNew
						$("#modelName").empty();
						$('#modelName').attr("readonly",true);
						$("#modelName").append("<option value='"+r.vePOJO.modelIdNew+"'>"+r.vePOJO.modelName+"</option>");
					}
			});
	
	}
	else{
						$('#vehicleCode').val('');
						$('#vehicleArg').val('');
						$('#modelName').combobox('');
	}
}

//回收站删除
function deleteInRecycle(){
	var vehicleIds = [];
	var rows = $('#vehicle_recycle_datagrid').datagrid('getSelections');
	for(var i=0;i<rows.length;i++){
		vehicleIds.push(rows[i].vehicleId);  
    }
    if (!vehicleIds || rows.length < 1) {
		$.messager.alert('消息提示', '请选择机械信息！', 'info');
		return;
	}

	$.messager.confirm(
		tipMsgDilag,
		"删除后将无法恢复！确定删除？",
		function(r){
			if (r){ 
          		$.post("vehicle/vehicle_deleteInRecycle.action", 
          		$.param({vehicleIds: vehicleIds}, true),
				function(data) {
					try {
						var r = $.parseJSON(data);
						if (r.success) {
							$('#vehicle_recycle_datagrid').datagrid('reload');
						}
						$.messager.alert(tipMsgDilag, r.msg);
					} catch (e) {
						$.messager.alert(tipMsgDilag, e);
					}
				});
			}
		}
	);
}

//回收站还原
function updateVehiclesIsValid(){
	var vehicleIds = [];
	var rows = $('#vehicle_recycle_datagrid').datagrid('getSelections');
	for(var i=0;i<rows.length;i++){
		vehicleIds.push(rows[i].vehicleId);  
    }
    if (!vehicleIds || rows.length < 1) {
		$.messager.alert('消息提示', '请选择机械信息！', 'info');
		return;
	}
	
	$.messager.confirm(
		tipMsgDilag,
		"确定还原？",
		function(r){
			if (r){ 
          		$.post("vehicle/vehicle_updateVehiclesIsValid.action", 
          		$.param({vehicleIds: vehicleIds}, true),
				function(data) {
					try {
						var r = $.parseJSON(data);
						if (r.success) {
							$('#vehicle_recycle_datagrid').datagrid('reload');
							$('#vehicle_datagrid').datagrid('reload');
						}
						$.messager.alert(tipMsgDilag, r.msg);
					} catch (e) {
						$.messager.alert(tipMsgDilag, e);
					}
				});
			}
		}
	);
}

//查询特定机械编号的测试日志
function openTestLog(){
	 unitid = $('#unitId').val();
	 vehicleDefs = $('#vehicleDefs').val();
	 veDef = $('#unitId').val();
	$('<div/>').dialog({
		content : '<iframe src="jsp/run/history_TestLog.jsp" id="ifm_workinfo" name="ifm_workinfo" frameborder="0" style="border:0;width:100%;height:99%;"></iframe>',
		width : 740,
		height : 480,
		modal : true,
		cache : false,
		resizable:true,
		title : '机械测试日志查询',
		buttons : [{
			text : '关闭',
			iconCls : 'icon-cancel',
			handler : function() {
				var d = $(this).closest('.window-body');
				d.dialog('destroy');
			}
		}],
		onLoad : function() {
			/*
			 * //加在地图上 if(typeof map.window.addPositionMarker == "function"){
			 * //map.window.addPositionMarker(data.lon,data.lat,data.vehicleDef);
			 * map.window.addPositionMarker(112.897095,28.227175,'常州A001'); }
			 */
		},
		onOpen : function() {
		},
		onClose : function() {
			$(this).dialog('destroy');
		}
		
	});


}

function vehicleDlgResize(wei, hei) {
	$('#vehicle_recycle_datagrid').datagrid('resize',{width:wei-14, height:hei-73});
}
