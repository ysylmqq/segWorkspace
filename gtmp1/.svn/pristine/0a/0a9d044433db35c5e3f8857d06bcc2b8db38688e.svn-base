var polling_search_text="整机编号/SIM卡号";
var monitorMaxNumber=50;//监控机械最大数量
var selectedUnitIds=[];//要发指令的unitIds,以","分隔
var selectedVehices=[];//要发指令的vehicles；
var selectedVehiceDefs=[];//要发指令的vehicleDefs；
var selectedSupperierSns=[];//空中升级,要发指令的supperierSns,以","分隔
var selectedUnitTypeSns=[];//空中升级,要发指令的unitTypeSns,以","分隔
var commandParams={};//指令发送对象
var refreshCommandTime=3000;//刷新的GPS,工况,指令信息间隔时间 3秒
var refreshGpsWorkOnLineTime=60000*4;;//刷新的GPS,工况4分钟刷新一次
var max_point=1000;//轨迹回放最多点个数
function initpollingTree(){
    $('#polling_tree').tree({   
                 checkbox: true,   
                 onBeforeExpand:function(node,param){  
                 	if(node.attributes){//异步加载
                 	   if(node.attributes.dtype){
                 	     	$('#polling_tree').tree('options').url = "vehicle/vehiclePolling_getVehicle4Tree.action";
                 	   }
                 	}
                 	// 
                 },               
                onClick:function(node){
                	if(!node.state){//如果没有state属性，则表示是叶子节点
						  /* 	 
						   	$.ajax( 
								       { 
								        type: "POST", 
								        url: "run/run_getLastGPS.action", 
								        data:{unitId:node.attributes.unitId} ,	         
								        success: function(msg) 
								        {	
										  if(msg!=null&&msg!='null') { 
										   	 ifm_map_polling.window.addLastGpsPosition(msg,node.text,node.attributes.isLogin);
								           } 
								         } 
								        });*/	
				var gpsInfos=[];
				var ABlock='';
				var Clock='';
				if(node.attributes.alock==2){
					ABlock='A锁';
				}else if(node.attributes.alock==3){
					ABlock='A锁解锁';
				}
				else if(node.attributes.alock==1){
					ABlock='A锁未执行';
				}
				if(node.attributes.block==2){
					if(ABlock!=''){
						ABlock=ABlock+'/';
					}
					ABlock=ABlock+'B锁';
				}else if (node.attributes.block==3){
					if(ABlock!=''){
						ABlock=ABlock+'/';
					}
					ABlock=ABlock+'B锁解锁';
				}else if (node.attributes.block==1){
					if(ABlock!=''){
						ABlock=ABlock+'/';
					}
					ABlock=ABlock+'B锁未执行';
				}
				if(node.attributes.clock==2){
					Clock='禁止防拆';
				}
				if(node.attributes.clock==1){
					Clock='使能防拆'
				}
				node.attributes.lockState=ABlock+" "+Clock;
				gpsInfos.push(node.attributes);
	        	//加入到gpsInfo表格
	        	addGpsDataToGrids(gpsInfos);
	        	//地图上显示
	        	ifm_map_polling.window.addLastGpsPosition(node.attributes,node.text,node.attributes.isLogin);
	
			}
                },
                onDblClick:function(node){
                	if(!node.state){//如果没有state属性，则表示是叶子节点
                		$('#polling_tree').tree('select', node.target);
                	}else{
                		$('#polling_tree').tree('expand', node.target).tree('select', node.target);
                	}
                },
                onContextMenu: function(e, node){
					e.preventDefault();
					// select the node
					$('#polling_tree').tree('select', node.target);
					//判断是否是叶子节点
					if(!node.state){//如果没有state属性，则表示是叶子节点
		                 	   	// display context menu
							$('#polling_tree').tree('check', node.target);    
							$('#menu_remove_monitor').css({display:'none'});;//从监控列表中移除 隐藏
						     $('#menu_add_monitor').css({display:'block'});;//加入监控列表 显示
							$('#menu_polling_right').menu('show', {
								left: e.screenX,
								top: e.screenY
							});
							
                 	  }
					
				},
				onCheck:function(node, checked){
						/*if(!node.state){//如果没有state属性，则表示是叶子节点
							if(checked){
							     $.ajax( 
							       { 
							        type: "POST", 
							        url: "vehicle/vehiclePolling_getLastGPS.action", 
							        data:{unitId:node.attributes.unitId} ,	         
							        success: function(msg) 
							        {	
									  if(msg!=null&&msg!='null') { 
									   	 ifm_map_polling.window.addLastGpsPosition(msg,node.text,node.attributes.isLogin);
							           } 
							         } 
							        });	
							}
						}*/
				}
            });
}
//搜索框onblur事件
function searchTextOnblur(){
	var text=$('#polling_tree_text').val();
	var color=$('#polling_tree_text').css('color');
	if(!text){
		$('#polling_tree_text').val(polling_search_text);
		 $('#polling_tree_text').css({color:'#888'});
	}
	else if(text!=polling_search_text){
	  $('#polling_tree_text').css({color:'#000000'});
	}
	else{
	  $('#polling_tree_text').css({color:'#888'});
	}
}
//搜索框onfocus事件
function searchTextOnFocus(){
	if($('#polling_tree_text').val()==polling_search_text){
	  $('#polling_tree_text').val('');
	}
   $('#polling_tree_text').css({color:'#000000'});
}
//搜索
function querypollingTree(){
	var params={};
	var dialogIsOpen=false;//是否打开更多条件dialog
	try{
		 $('#dlg_more_condition').dialog("options");
		 if(!$('#dlg_more_condition').dialog("options").closed){//打开
		 	dialogIsOpen=true;
		 	params.status=$('#status',$('#frm_polling_query')).combobox('getValue');;
		 	params.typeId=$('#typeId',$('#frm_polling_query')).combobox('getValue');
		 	params.modelId=$('#modelId',$('#frm_polling_query')).combobox('getValue');
		 	params.unitSn=$('#unitSn',$('#frm_polling_query')).val();
		 	params.steelNo=$('#steelNo',$('#frm_polling_query')).val();
		 	params.materialNo=$('#materialNo',$('#frm_polling_query')).val();
		 }
	}catch(e){
	   $.messager.alert(e);
	}
	$('#tabs_tree').tabs('select', 0);
	var text=$('#polling_tree_text').val();
	 if(text==polling_search_text){//如果是没有任何条件的查询
	     text="";
		 if(params.status||params.typeId||params.modelId||params.unitSn||params.steelNo||params.materialNo){
			
		 }else{
		 	 $('#polling_tree').tree('options').url = "vehicle/vehiclePolling_getVehicle4Tree.action";
			 $('#polling_tree').tree('reload');
			  return;
		 }
	
	}
	 params.vehicleDef=text;
       $.ajax( 
       { 
        type: "POST", 
        url: "vehicle/vehiclePolling_getVehilces.action", 
        data:$.param(params,true) ,	         
        success: function(msg) 
        {	
		  if(msg!=null) 
		  { 
		  	 if( $("#polling_tree  li ul").length>0){//得到根节点下面的节点，并移除
		  	    $("#polling_tree  li ul").remove(); 
		  	 }
		  	 //再追加
	       $('#polling_tree').tree('append', 
			  { 
			parent: $('#polling_tree').tree("getRoot").target, 
			data:$.parseJSON(msg)
			}); 
        } 
         } 
        });	
}
//更多条件
function openQueryDialog(){
	//经销商列表
	 $('#dealerId').combotree({
	 url:'vehicle/vehiclePolling_getDealerAreas4Tree.action'
     });
     //机械类型
      $('#typeId').combobox({
      	url:"vehicle/vehicleType_getList.action",
      	valueField:'typeId',  
        textField:'typeName' ,
        onSelect: function(rec){
        	       //级联关系 add comment by zfy 2013-5-28
                   // var url = 'vehicle/vehicleModel_getList.action?typeId=' + rec.typeId;
                   // $('#modelId').combobox('reload', url);
                 }
     });
	$('#dlg_more_condition').dialog('open');
}

//全屏

function fullscreen(){
	if($('#afullscrenn').attr('title')=='全屏'){
		var rhei = $('#north_index').panel('options').height;
		$('#north_index').panel('options').height=0;
		var mhei = $('#main').panel('options').height;
		var shei = $('#south_index').panel('options').height;
        $('#south_index').panel('options').height=0;
        
		$('#main').panel('options').height= mhei+rhei+shei;
		$('#north_index').resize();
		 $('#north_index').panel('collapse');
		$('#main').resize();
		$('#south_index').resize();
		
		ifm_map_polling.window.mapresize(true);
		$('#afullscrenn').attr('title','退出全屏');
	}else{
		var rhei = $('#north_index').panel('options').height;
		$('#north_index').panel('options').height=0;
		var mhei = $('#main').panel('options').height;
		var shei = $('#south_index').panel('options').height;
        $('#south_index').panel('options').height=0;
        
		$('#main').panel('options').height= mhei+rhei+shei;
		 $('#north_index').panel('expand');
		$('#north_index').resize();
		$('#main').resize();
		$('#south_index').resize();
		
		$('#north_index').panel('options').height=78;
		$('#south_index').panel('options').height=0;//23，隐藏后改成0
		var rhei = $('#north_index').panel('options').height;
		var mhei = $('#main').panel('options').height;
		var whei = $('#south_index').panel('options').height;

		$('#main').panel('options').height= mhei-rhei-whei;
		$('#north_index').resize();
		$('#main').resize();
		$('#south_index').resize();
		
		ifm_map_polling.window.mapresize(false);
		$('#afullscrenn').attr('title','全屏');
	}
	
}
//加入监控列表
function addMonitor(){
	var result=getCheckedTree();
	if(!result||(result.length==0)){
		 $.messager.alert(tipMsgDilag, "请选择要监控的机械!");
		 return;
	}
		//监控数量最大限制为50
	var data= $('#grid_polling_monitor').datagrid('getData');
	//监控数量最大限制为50
	if(parseInt(data.total)>monitorMaxNumber){
	   $.messager.alert(tipMsgDilag, "抱歉,同一时间最多只能监控"+monitorMaxNumber+"台机械!");
	  return;
	}
	$('#tabs_tree').tabs('select', 1);
	insertDatagrid(result);
	//保存到session中
   var monitorList=[];
   data= $('#grid_polling_monitor').datagrid('getData');
	for (var index = 0; index < data.total; index++) {
		row=data.rows[index];
		monitorList.push(JSON.stringify(row)+"");
	}
	  $.post('vehicle/vehiclePolling_addMonitorList.action',
      			$.param({vehicleUnitPOJOs:monitorList},true),
    			function(result){
    				
    	});
}

function insertDatagrid(datas){
	for(var i=0; i<datas.length; i++){
		//先判断表格中是否存在这个vehicleId
		if(!isExistInGrid(datas[i].vehicleId)){
			 $('#grid_polling_monitor').datagrid('appendRow',datas[i]);
		}
	}
}
//获得选中的机械
function getCheckedTree(){
   var nodes = $('#polling_tree').tree('getChecked');
   var result=[];
	var s = '';
	for(var i=0; i<nodes.length; i++){
		if (s != '') {
		s += ',';
		}
		s += nodes[i].text;
		if(nodes[i].attributes){
			if(!nodes[i].state){//如果是机械
				result.push(nodes[i].attributes);
			}
		}
	}
	return result;
}
//判断表格中是否已存在该vehicleId
function isExistInGrid(vehicleId){
	var data= $('#grid_polling_monitor').datagrid('getData');
	var row=null;
	var flag=false;
	//监控数量最大限制为50
	if(data.total>monitorMaxNumber){
	   $.messager.alert(tipMsgDilag, "抱歉,同一时间最多只能监控"+monitorMaxNumber+"台机械!");
	   flag=true;
	}else{
		for (var index = 0; index < data.total; index++) {
			row=data.rows[index];
			if(row.vehicleId==vehicleId){
				$.messager.alert(tipMsgDilag, '整机编号为['+row.vehicleDef+"]已再监控列表中");
				flag=true;
			   break;
			}
		}
	}
	return flag;
}

//右键菜单
function monitorRightClick(e, rowIndex, rowData){
	e.preventDefault();
	$('#grid_polling_monitor').datagrid('selectRow',rowIndex);//选中当前行
	 $('#menu_remove_monitor').css({display:'block'});//从监控列表中移除 显示
	 $('#menu_add_monitor').css({display:'none'});;//加入监控列表 隐藏
	$('#menu_polling_right').menu('show', {
								left: e.screenX,
								top: e.screenY
	});
   //$('#grid_polling_monitor').datagrid('deleteRow',rowIndex); 
}
function clearMonitorData(handler){
	var rows = null;
	var monitorList=[];
	//删除选中行
	if(handler==1){
		rows=$('#grid_polling_monitor').datagrid('getSelections');
		for(var i=0;i<rows.length;i++){
		   monitorList.push(JSON.stringify(rows[i])+"");
			var index = $('#grid_polling_monitor').datagrid('getRowIndex',rows[i]);//获取某行的行号
			$('#grid_polling_monitor').datagrid('deleteRow',index);	//通过行号移除该行
			//ids.push(index);
	   } 
       //从session中移除
	   $.post('vehicle/vehiclePolling_delWatchUnitIds.action',
      			$.param({vehicleUnitPOJOs:monitorList},true),
    			function(result){
    				
    	});
	}else{//删除所有记录
		var data = $('#grid_polling_monitor').datagrid('getData');
		for (var index = 0; index < data.total; ) {
			var index2 = $('#grid_polling_monitor').datagrid('getRowIndex',data.rows[index]);//获取某行的行号
			$('#grid_polling_monitor').datagrid('deleteRow',index2);	//通过行号移除该行
			index=0;
    	}
    	
    	 //从session中清除
	   $.post('vehicle/vehiclePolling_cleanWatchVehicle.action',
    			function(result){
    				
    	});
	}
	
}

//发送指令
function sendCommand(commandTypeId,params){
	    getCheckedVehicles();
	   if(selectedVehiceDefs.length==0){
	       $.messager.alert(tipMsgDilag, "请选择要设置参数的机械!");
	       return;
	    }
	   if(typeof(commandTypeId)=='string'){
		   commandTypeId=parseInt(commandTypeId);
	   }
	     $.messager.confirm(tipMsgDilag,"整机编号为["+selectedVehiceDefs.join(',')+']的车台确定下发指令?',
							   function(r){  
								    if (r){ 
								    	 switch(commandTypeId){
								      case 32://定位
								      commandParams={
								      	unitIds:selectedUnitIds.join(','),
								      	commandTypeId:commandTypeId
								      };
								      break;
								      
								      case 34://复位
								      commandParams={
								      	unitIds:selectedUnitIds.join(','),
								      	commandTypeId:commandTypeId
								      };
								      break;
								      
								      case 54://工作时间清理
								      commandParams={
								      	unitIds:selectedUnitIds.join(','),
								      	commandTypeId:commandTypeId
								      };
								      break;
								      
								      case 215://查询控制器版本及ID信息
									      commandParams={
									      	unitIds:selectedUnitIds.join(','),
									      	commandTypeId:commandTypeId
									      };
									      break;
								   }
								   //调用action
								 sendCommandAction(commandParams,'run/command_sendCommand4Polling.action');
								    }
		}); 
  
   
}
//有窗口的情况下 发指令
function sendCommandHasWin(commandTypeId,params){
						switch(commandTypeId){
								      case 48://设置和查询短信中心号码
									      if(params){
									      	commandParams=params;
									      }
								      break;
								      
								      case 49://设置和查询IP地址和端口号
								          if(params){
									      	commandParams=params;
									      }
								      break;
								      
								      case 51://设置和查询APN\IP地址和端口号
								          if(params){
									      	commandParams=params;
									      }
								      break;
								      
								      case 81://工况数据采集
								       if(params){
									      	commandParams=params;
									      }
								      break;
								      
								      case 52://锁车管理
								       if(params){
									      	commandParams=params;
									      }
								      break;
								      
								      case 16://空中升级
								       if(params){
									      	commandParams=params;
									      }
								      break;
								      
								      case 33://实时跟踪
									       if(params){
										      	commandParams=params;
										      }
									      break;
									      
								      case 53://定时报告设置
									       if(params){
										      	commandParams=params;
										      }
									      break;
								      case 83://CAN数据透传指令
									       if(params){
										      	commandParams=params;
										      }
									      break;
								      case 225://设置锁车防拆类指令实时回复的次数
									       if(params){
										      	commandParams=params;
										      }
									      break;
								      case 227://设置精细传输时需上传的ID
									       if(params){
										      	commandParams=params;
										      }
									      break;
								   }
								   if(commandTypeId!=16){
	   									sendCommandAction(commandParams,'run/command_sendCommand4Polling.action');
								   }else{//空中升级
								   		sendCommandAction(commandParams,'run/command_sendUpgradeCommand.action');
								   }
}
//调用action 发指令
function sendCommandAction(commandParams,url){
	//标记是机械测试
	commandParams.testFlag=1;
 //调用action
								   $.post(url,
								      			$.param(commandParams,true),
								    			function(result){
								      	  	     try {
														var r = $.parseJSON(result);
														if (r.success) {
														}
														$.messager.show({
															title:tipMsgDilag,
															msg:r.msg,
															timeout:1500,
															showType:'slide'
														});
													} catch (e) {
														$.messager.alert(tipMsgDilag, '失败!');
													}
								    	}
								    	);
}
//打开参数设置窗口
function openParamsSetWin(){
		//获得要发指令的机械信息
    getCheckedVehicles();
    if(selectedVehiceDefs.length==0){
       $.messager.alert(tipMsgDilag, "请选择要设置参数的机械!");
       return;
    }
    $('#spn_params_vehicleDefs').text(selectedVehiceDefs.join(','));
    $('#dlg_params_set').dialog('open');
	var defaultVals=[];
	defaultVals['pMessageNumber']=["15116351819","短信中心号码:"];
	defaultVals['pIp']=["222.83.253.75","IP地址:","11000","端口:"];
	defaultVals['pAPN']=["222.83.253.75","IP地址:","11000","端口:","cmwap","APN:"];
	defaultVals['pReportInterval']=["60","定时报告间隔(秒):"];
	defaultVals['pCanCommand']=["00AC","CAN ID号:","0","数据:"];//CAN数据透传指令
	defaultVals['pLockTimes']=["1","次数:"];//设置锁车防拆类指令实时回复的次数
	defaultVals['pFineId']=["100102103","精细传输ID:"];//设置精细传输时需上传的ID
	$('#txt_params_val').val(defaultVals['pMessageNumber'][0]);
	$("input:radio[name='radio_params']",'#frm_params_set').bind("click", function(){
		 $('#btn_param_query').linkbutton('enable');
		//设置每个参数的默认值
		var id=$(this).attr('id');
		if(id&& defaultVals[id]){
			 $('#td_params_val').text(defaultVals[id][1]);
		     $('#txt_params_val').val(defaultVals[id][0]);
		     $('#tr_apn').hide();
		     $('#txt_params_val3').hide();
		     if(id=="pIp"){
		    	 $('#tr_port td:first').text(defaultVals[id][3]);
		    	 $('#txt_params_val2').val(defaultVals[id][2]);
		    	 $('#txt_params_val2').show();
		    	 $('#tr_port').show();
		     }else if(id=="pAPN"){
		    	 $('#tr_apn').show();
		    	 $('#tr_port td:first').text(defaultVals[id][3]);
		    	 $('#txt_params_val2').val(defaultVals[id][2]);
		    	 $('#txt_params_val2').show();
		    	 $('#tr_apn td:first').text(defaultVals[id][5]);
		    	 $('#txt_params_val3').val(defaultVals[id][4]);
		    	 $('#txt_params_val3').show();
		    	 $('#tr_port').show();
		     }else if(id=="pCanCommand"){
		    	 $('#tr_port td:first').text(defaultVals[id][3]);
			     $('#txt_params_val2').val(defaultVals[id][2]);
			     $('#txt_params_val2').show();
			     $('#tr_port').show();
		    	 //隐藏查询按钮
		        $('#btn_param_query').linkbutton('disable');
		     }else if(id=="pFineId"){
		    	 $('#tr_port').hide();
		        //隐藏查询按钮
		        $('#btn_param_query').linkbutton('disable');
		     }else{
		    	 $('#tr_port').hide();
		     }
		}
	});

}
//参数设置发送
function sendCommandParams(pType){
	var id=$("input:radio[name='radio_params']:checked",'#frm_params_set') .attr('id');
	 getCheckedVehicles();
	 var commandTypeId=48;
	 var params=null;
	if(id=='pMessageNumber'){
		commandTypeId=48;
        params={
      	unitIds:selectedUnitIds.join(','),
      	commandTypeId:commandTypeId,
      	pMessageNumber:$('#txt_params_val').val(),
      	pType:pType
      };
	}else if(id=='pIp'){
		commandTypeId=49;
	   params={
      	unitIds:selectedUnitIds.join(','),
      	commandTypeId:commandTypeId,
      	pIp:$('#txt_params_val').val(),
      	pPort:$('#txt_params_val2').val(),
      	pType:pType
      };
	}else if(id=='pAPN'){
		commandTypeId=51;
	  params={
      	unitIds:selectedUnitIds.join(','),
      	commandTypeId:commandTypeId,
    	pAPN:$('#txt_params_val3').val(),
      	pIp:$('#txt_params_val').val(),
      	pPort:$('#txt_params_val2').val(),
      	pType:pType
      };
	}else if(id=='pReportInterval'){
		commandTypeId=53;
		  params={
	      	unitIds:selectedUnitIds.join(','),
	      	commandTypeId:commandTypeId,
	      	pReportInterval:$('#txt_params_val').val(),
	      	pType:pType
	      };
		}else if(id=='pCanCommand'){
		commandTypeId=83;
		  params={
	      	unitIds:selectedUnitIds.join(','),
	      	commandTypeId:commandTypeId,
	      	pCanId:$('#txt_params_val').val(),
	      	pCanCommand:$('#txt_params_val2').val()
	      };
		}else if(id=='pLockTimes'){
			commandTypeId=225;
			  params={
		      	unitIds:selectedUnitIds.join(','),
		      	commandTypeId:commandTypeId,
		      	pLockTimes:$('#txt_params_val').val(),
		      	pType:pType
		      };
		}else if(id=='pFineId'){
			commandTypeId=227;
			  params={
		      	unitIds:selectedUnitIds.join(','),
		      	commandTypeId:commandTypeId,
		      	pFineId:$('#txt_params_val').val()
		      };
		}
	//发送
	sendCommandHasWin(commandTypeId,params);
}
//获得要发指令的机械
function getCheckedVehicles(){
   //先清空
	selectedVehices=[];
	selectedUnitIds=[];
	selectedVehiceDefs=[];
	
	selectedSupperierSns=[];
	selectedUnitTypeSns=[];
	
	if($('#tabs_tree')){
		 var tab = $('#tabs_tree').tabs('getSelected');
		 tab=tab.panel('options').title; 
		 if (tab) {
			 switch(tab){
			 	 case '机械列表':
			 	 selectedVehices= getCheckedTree();
			 	 break;
			 	 case '监控列表':
			 	 selectedVehices=getMonitorGridSelections();
			 	 break;
			 }
			 }
	}
	if(selectedVehices){
		
	    for(var i=0;i<selectedVehices.length;i++){
		 selectedUnitIds.push(selectedVehices[i].unitId);
		 selectedVehiceDefs.push(selectedVehices[i].vehicleDef);
		 selectedSupperierSns.push(selectedVehices[i].supperierSn);
		 selectedUnitTypeSns.push(selectedVehices[i].unitTypeSn);
	   }
	}
}
 function getMonitorGridSelections(){
	var results = [];
	var rows = $('#grid_polling_monitor').datagrid('getSelections');
	for(var i=0;i<rows.length;i++){
		results.push(rows[i]);
	}
	return results;
}
//定时指令信息 3秒
function refreshResponsInfo(){
	//从memcache中取数据
	 $.post('vehicle/vehiclePolling_getMemcacheData.action',
    			function(result){
    				
      	  	     try {
						var r = $.parseJSON(result);
						var gpsInfo=r.gpsInfo;//gps信息
						var workInfo=r.workInfo;//工况信息
						var response=r.response;//指令回应信息
						
						//gps信息
						if(gpsInfo&&gpsInfo.length>0){
							//$('#tabs_south').tabs('select', 0);
						   addGpsDataToGrids(gpsInfo);
						   //刷新到地图上
						   if(typeof  ifm_map_polling.window.addMapDataOfMonitor == "function"){
								 ifm_map_polling.window.addMapDataOfMonitor(gpsInfo);
						   }
						}
						
						//指令回应
						if(response&&response.length>0){
							//$('#tabs_south').tabs('select', 2);
						   addResponseDataToGrids(response);
						}
						
						//工况信息
						if(workInfo&&workInfo.length>0){
							//$('#tabs_south').tabs('select', 1);
						   addWorkinfoDataToGrids(workInfo);
						}
						
						//表格中加一条删一条
						refreshRunGridTimeoutId=setTimeout(refreshResponsInfo,refreshCommandTime);
					} catch (e) {
						//$.messager.alert(tipMsgDilag, '失败!');
					}
    	}
    	);
	
}
//定时刷新GPS,工况4分钟
function refreshGpsWork(){
	//从memcache中取数据
	 $.post('vehicle/vehiclePolling_getGpsWorkOnLineData.action',
    			function(result){
    				
      	  	     try {
						var r = $.parseJSON(result);
						var gpsInfo=r.gpsInfo;//gps信息
						var workInfo=r.workInfo;//工况信息
						//var onLineCount=r.onLineCount;//在线
						//var offLineCount=r.offLineCount;//不在线
						//gps信息
						if(gpsInfo&&gpsInfo.length>0){
							//$('#tabs_south').tabs('select', 0);
						   addGpsDataToGrids(gpsInfo);
						   //刷新到地图上
						   if(typeof  ifm_map_polling.window.addMapDataOfMonitor == "function"){
								 ifm_map_polling.window.addMapDataOfMonitor(gpsInfo);
						   }
						}
						
						//工况信息
						if(workInfo&&workInfo.length>0){
							//$('#tabs_south').tabs('select', 1);
						   addWorkinfoDataToGrids2(workInfo);
						}
						//刷新在线、离线按钮文字
						//$("#online_run_btn").val("在线("+onLineCount+")");
						//$("#offline_run_btn").val("离线("+offLineCount+")");
						//表格中加一条删一条
						refreshRunGpsWorkOnLineTimeoutId=setTimeout(refreshGpsWork,refreshGpsWorkOnLineTime);
					} catch (e) {
						//$.messager.alert(tipMsgDilag, '失败!');
					}
    	}
    	);
}
//添加一条信息到gps信息的表格中
function addGpsDataToGrid(obj){
	var row = {index: 0, row: obj};
	$('#grid_gpsInfo').datagrid('insertRow', row);
}
//删除最后一行
function removeGpsDataFromGrid(){
	var data = $('#grid_gpsInfo').datagrid('getData');
	if(data.total>=10){//最多显示10行
		$('#grid_gpsInfo').datagrid('deleteRow', data.total-1);	//通过行号移除该行
	}
}
//添加到gps信息表格中
function addGpsDataToGrids(datas){
  for(var i=0;i<datas.length;i++){
  	  removeGpsDataFromGrid();
  	  addGpsDataToGrid(datas[i]);
  }
}
//添加一条信息到response信息的表格中
function addResponseDataToGrid(obj){
	var row = {index: 0, row: obj};
	$('#grid_response').datagrid('insertRow', row);
}
//删除第一行
function removeResponseDataFromGrid(){
	var data = $('#grid_response').datagrid('getData');
	if(data.total>=10){//最多显示10行
		$('#grid_response').datagrid('deleteRow', data.total-1);	//通过行号移除该行
	}
}
//添加到Response信息表格中
function addResponseDataToGrids(datas){
  for(var i=0;i<datas.length;i++){
  	  removeResponseDataFromGrid();
  	  addResponseDataToGrid(datas[i]);
  }
}
//添加一条信息到workinfo信息的表格中
function addWorkinfoDataToGrid(obj){
	var row = {index: 0, row: obj};
	$('#grid_workInfo').datagrid('insertRow', row);
}
//删除最后一行
function removeWorkinfoDataFromGrid(){
	var data = $('#grid_workInfo').datagrid('getData');
	if(data.total>=10){//最多显示10行
		$('#grid_workInfo').datagrid('deleteRow', data.total-1);	//通过行号移除该行
	}
}
//添加到Workinfo信息表格中,指令回应
function addWorkinfoDataToGrids(datas){
  for(var i=0;i<datas.length;i++){
	  if(!isExistInWorkInfoGrid(datas[i].unitId,datas[i].nowTime)){
	  	  removeWorkinfoDataFromGrid();
	  	  addWorkinfoDataToGrid(datas[i]);
	  }
  }
}
//添加到Workinfo信息表格中,定时上报
function addWorkinfoDataToGrids2(datas){
  for(var i=0;i<datas.length;i++){
  	  removeWorkinfoDataFromGrid();
  	  addWorkinfoDataToGrid(datas[i]);
  }
}
//右键菜单 gps表格
function gpsRightClick(e, rowIndex, rowData){
	e.preventDefault();
	$('#menu_grid_gps').menu('show', {
								left: e.pageX,
								top: e.pageY
	});
}

//右键菜单 工况表格
function workInfoRightClick(e, rowIndex, rowData){
	e.preventDefault();
	$('#menu_grid_work').menu('show', {
								left: e.pageX,
								top: e.pageY
	});
}

//右键菜单 指令回应表格
function responseRightClick(e, rowIndex, rowData){
	e.preventDefault();
	$('#menu_grid_response').menu('show', {
								left: e.pageX,
								top: e.pageY
	});
}
//从表格中删除
function removeGrid(handler,gridId){
	var rows = null;
	var monitorList=[];
	//删除选中行
	if(handler==1){
		rows=$('#'+gridId).datagrid('getSelections');
		if(rows.length<1){
		   $.messager.alert(tipMsgDilag,'请选择要删除的行数据!');
		   return;
		}
		for(var i=0;i<rows.length;i++){
			monitorList.push(rows[i].unitId);
			var index = $('#'+gridId).datagrid('getRowIndex',rows[i]);//获取某行的行号
			$('#'+gridId).datagrid('deleteRow',index);	//通过行号移除该行
			//ids.push(index);
	   } 
	}else{//删除所有记录
		var data = $('#'+gridId).datagrid('getData');
		for (var index = 0; index < data.total; ) {
			var index2 = $('#'+gridId).datagrid('getRowIndex',data.rows[index]);//获取某行的行号
			$('#'+gridId).datagrid('deleteRow',index2);	//通过行号移除该行
			index=0;
    	}
	}
	
}

//打开回放窗口
function openHistoryPlay(){
	var node=getSelectNode();
	$('#dlg_history_play').dialog('setTitle',"轨迹回放["+node.vehicleDef+"]");
	//$('#start_time',$('#frm_history_play')).datetimebox('setValue','2013-07-12 16:05:00');
	//$('#end_time',$('#frm_history_play')).datetimebox('setValue','2013-07-12 16:09:00');
	$('#dlg_history_play').dialog('open');
}

//查询
function queryHistory(){
	var node=getSelectNode();
	if(node){
	   var startTime= $('#start_time',$('#frm_history_play')).datetimebox('getValue');
	   var endTime= $('#end_time',$('#frm_history_play')).datetimebox('getValue');
	  // ifm_map_polling.window.playback(node.unitId,start_time,end_time,node.vehicleDef);
	   if(node.unitId==""){
	  return;
	}
	if(!startTime){
	   $.messager.alert(tipMsgDilag,'请输入开始时间!');
	   return;
	}
	if(!startTime){
	   $.messager.alert(tipMsgDilag,'请输入结束时间!');
	   return;
	}
	if(startTime>=endTime){
	   $.messager.alert(tipMsgDilag,'结束时间必须在开始时间之后!');
	   return;
	}
 	var params={
		'unitId':node.unitId,
		'startTime': startTime,
		'endTime':endTime,
		'vehicleState':$('#cmb_time_type').combobox('getValue')
	};
				$.post("run/history_getList.action", params, function(data){
			        if(data){
			        	var trace= ifm_map_polling.window.trace;
			        	if (trace) {
							trace.clear();
							trace = null;
						}
						 ifm_map_polling.window.clearAllArray();
						data=eval(data);
						var obj=node.vehicleDef;
						if(data.length>0){
							 $('#span_record_counts').css({display:'block'});
							    $('#span_record_counts').text('共有'+data.length+"条GPS信息");
								
						  if(data.length>max_point){
						   $.messager.confirm(tipMsgDilag,'搜索出的轨迹点太多，您可缩小时间间隔再重新搜索，也可继续显示，是否继续?',
							   function(r){  
								    if (r){ 
								    	$('#btn_play').linkbutton('enable');
								       ifm_map_polling.window.traceOnMap(data,obj);
								    }else{
								    	$('#btn_play').linkbutton('disable');
								    }
								}); 
							 }else{
							 	  ifm_map_polling.window.traceOnMap(data,obj);
							  //$.messager.alert(tipMsgDilag,'没有找到该机械的历史轨迹!');
							 	 $('#btn_play').linkbutton('enable');
							 }
							 
						
						}else{
						 //$.messager.alert(tipMsgDilag,'没有找到该机械的历史轨迹!');
							 $('#span_record_counts').css({display:'block'});
							 $('#btn_play').linkbutton('disable');
						   $('#span_record_counts').text("没有找到该机械的历史轨迹!");
						}
						
			        }else{
			        	 $('#span_record_counts').css({display:'block'});
			        	 $('#btn_play').linkbutton('disable');
			        	 $('#span_record_counts').text("没有找到该机械的历史轨迹!");
			          //$.messager.alert(tipMsgDilag,'没有找到该机械的历史轨迹!');
			        }
			       
				}, 'json');
	}
}
//获得选中的树节点或者表格节点 单选
function getSelectNode(){
   var node =null;
		if($('#tabs_tree')){
		 var tab = $('#tabs_tree').tabs('getSelected');
		 tab=tab.panel('options').title; 
		 if (tab) {
			 switch(tab){
			 	 case '机械列表':
			 	 node= $('#polling_tree').tree('getSelected').attributes;
			 	 break;
			 	 case '监控列表':
			 	  node= $('#grid_polling_monitor').datagrid('getSelected');
			 	 break;
			 }
			 }
	}
	return node;
}

//播放
function playHistory(){
	$('#dlg_history_play').dialog('close');
	ifm_map_polling.window.playBegin()
}
//打开锁车管理窗口
function openLockWin(){
		//获得要发指令的机械信息
     getCheckedVehicles();
    if(selectedVehiceDefs.length==0){
       $.messager.alert(tipMsgDilag, "请选择要设置参数的机械!");
       return;
    }
    $('#spn_lock').text(selectedVehiceDefs.join(','));
    $('#dlg_lock').dialog('open');
}
//锁车指令发送
function sendCommandLock(){
	var item=$("input:radio[name='radio_lock']:checked",'#frm_lock').val();
	 getCheckedVehicles();
	 var commandTypeId=52;
	 var params={
      	unitIds:selectedUnitIds.join(','),
      	commandTypeId:commandTypeId,
      	pHeartbeatContent:item,
      	pHeartbeatInterval:$("#pHeartbeatInterval",'#frm_lock').val(),
      	pCanId:$("#pCanId",'#frm_lock').val()
      };
	//发送
	sendCommandHasWin(commandTypeId,params);
	$('#dlg_lock').dialog('close');
}
//打开空中升级窗口
function openRemoteUpgradeWin(){
	//获得要发指令的机械信息
    getCheckedVehicles();
    if(selectedVehiceDefs.length==0){
       $.messager.alert(tipMsgDilag, "请选择要进行空中升级的机械!");
       return;
    }
    $('#spn_upgrade_vehicleDefs').text(selectedVehiceDefs.join(','));
    //设置终端类型
    //供应商sn
    //$('#txt_pSupperier').val();
    $('#dlg_remote_upgrade').dialog('open');
}
//空中升级
function sendCommandUpgrade(){
	var pDeviceType=$('#cmb_pDeviceType').combobox('getValue');
	var sjType=$('#cmb_sjType').combobox('getValue');
   if(pDeviceType=='00'||pDeviceType=='01'){
		if(sjType=='02'){
			$.messager.alert(tipMsgDilag, "升级GPS终端或者控制器时，只能升级程序!");
		    return ;
		}
	}
	 getCheckedVehicles();
	 var commandTypeId=16;
	 var params=null;
		commandTypeId=16;
        params={
      	unitIds:selectedUnitIds.join(','),//'0100000005'
      	commandTypeId:commandTypeId,
      	pSupperiers:$('#cmb_pSupperiers').combobox('getValue'),//'01'
      	pUpdateType:$('#cmb_pUpdateType').combobox('getValue'),
      	pDeviceType:pDeviceType,
      	sjType:sjType,
      	pVersion:$('#txt_pVersion').val(),
      	pUnitTypes:selectedUnitTypeSns.join(','),//'TYC01'
      	pUnitType:$('#txt_pUnitType').val(),
      	pIp:$('#txt_pIp').val(),
      	pPort:$('#txt_pPort').val(),
      	pAPN:$('#txt_pAPN').val(),
      	pLocalPort:$('#txt_pLocalPort').val()
      };
	//发送
	sendCommandHasWin(commandTypeId,params);
	$('#dlg_remote_upgrade').dialog('close');
}

//工况数据采集窗口弹出
function openWorkInfWin(){
	//获得要发指令的机械信息
    getCheckedVehicles();
    if(selectedVehiceDefs.length==0){
       $.messager.alert(tipMsgDilag, "请选择要进行工况采集的机械!");
       return;
    }
    $('#spn_workinfo_vehicleDefs').text(selectedVehiceDefs.join(','));
    //设置终端类型
    //供应商sn
    //$('#txt_pSupperier').val();
    $('#dlg_workinfo').dialog('open');
}

//工况数据采集
function sendWorkInfoCommand(){
	 getCheckedVehicles();
	 var commandTypeId=81;
	 var params={
      	unitIds:selectedUnitIds.join(','),
      	commandTypeId:commandTypeId,
      	pCanSendTime:$("#txt_pCanSendTime",'#frm_workinfo').val(),
      	pCollectInterval:$("#txt_pCollectInterval",'#frm_workinfo').val()
      };
	//发送
	sendCommandHasWin(commandTypeId,params);
	$('#dlg_workinfo').dialog('close');
}

//GPS表格双击事件
function gpsDblClick(rowIndex, row){
   if(row.unitId){
			//地图上显示
			ifm_map_polling.window.selectGpsMarker(row);
	}
}

//空中升级中-设备类型选择 级联控制升级类型
function pDeviceTypeSelect(rec){
	//如果设备类型选择00-GPS终端；01-控制器，则升级类型只能选择01-升级程序
	if(rec.value=='00'||rec.value=='01'){
		$('#cmb_sjType').combobox('setValue', '01');
	}
}
//实时跟踪窗口弹出
function openTrackWin(){
	//获得要发指令的机械信息
    getCheckedVehicles();
    if(selectedVehiceDefs.length==0){
       $.messager.alert(tipMsgDilag, "请选择要进行实时跟踪的机械!");
       return;
    }
    $('#spn_track_vehicleDefs').text(selectedVehiceDefs.join(','));
    //设置终端类型
    //供应商sn
    //$('#txt_pSupperier').val();
    $('#dlg_track').dialog('open');
}

//实时跟踪
function sendTrackCommand(){
	 getCheckedVehicles();
	 var commandTypeId=33;
	 var params={
    	unitIds:selectedUnitIds.join(','),
    	commandTypeId:commandTypeId,
    	pTraceTimes:$("#txt_pTraceTimes",'#frm_track').val(),
    	pTraceInterval:$("#txt_pTraceInterval",'#frm_track').val()
    };
	//发送
	sendCommandHasWin(commandTypeId,params);
	
	//调用跟踪线程
	try {
		timeInterval=parseInt($("#txt_pTraceInterval",'#frm_track').val());
	} catch (e) {
		timeInterval=2;
	}
	trackInfo=[];
	trackInfo['unitIds']=selectedUnitIds.join(',');
	trackInfo['pTraceTimes']=$("#txt_pTraceTimes",'#frm_track').val();
	trackInfo['pTraceInterval']=timeInterval*1000;
	refreshPollingTrack();
	$('#dlg_track').dialog('close');
}

//轨迹导出
function exportHistory(){
	var node=getSelectNode();
	if(node){
	   var startTime= $('#start_time',$('#frm_history_play')).datetimebox('getValue');
	   var endTime= $('#end_time',$('#frm_history_play')).datetimebox('getValue');
	  // ifm_map_polling.window.playback(node.unitId,start_time,end_time,node.vehicleDef);
	   if(node.unitId==""){
	  return;
	}
	if(!startTime){
	   $.messager.alert(tipMsgDilag,'请输入开始时间!');
	   return;
	}
	if(!startTime){
	   $.messager.alert(tipMsgDilag,'请输入结束时间!');
	   return;
	}
	if(startTime>=endTime){
	   $.messager.alert(tipMsgDilag,'结束时间必须在开始时间之后!');
	   return;
	}
 	  window.location.href=encodeURI(encodeURI("run/history_exportToExcel.action?unitId="+node.unitId+"&referencePosition="+endTime+"&rawData="+startTime));
	   return;
	}
}
//机械状态，如有警情，需颜色区分
function vehicleStateFormat(value,row,index){
	if(row.alarmFlag==1){//警情上报
		  return '<font color="red">'+value+'</font>';
		}else{
			return value;
		}
}

//打开工作时间窗口
function openWorkHoursWin(){
	
	var node=getSelectNode();
	$('#dlg_workhours').dialog('setTitle',"累计工作时间["+node.vehicleDef+"]");
	$('#dlg_workhours').dialog('open');
	$.ajax( 
		       { 
		        type: "POST", 
		        url: "run/run_getLastWorkInfo.action", 
		        data:{unitId:node.unitId} ,	         
		        success: function(msg) 
		        {	
				  if(msg!=null&&msg!='null') {
					  msg=$.parseJSON(msg);
					  $('#totalWorkHours').val(parseFloat(msg.totalworkinghours).toFixed(1));
		           } 
		         } 
		        });
}

//查询历史工况
function openWorkInfoWin(){
	var node=getSelectNode();
	workInfData = node.unitId;
	$('<div/>').dialog({
		content : '<iframe src="jsp/run/history_workinfo.jsp" id="ifm_workinfo" name="ifm_workinfo" frameborder="0" style="border:0;width:100%;height:99%;"></iframe>',
		width : 920,
		height : 550,
		modal : true,
		cache : false,
		resizable:true,
		maximizable: true,
		title : '历史工况',
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
//打开输入密码 窗口
function openInputPwdWin(commandType){
	$('#dlg_inputpwd').dialog('open');
	$('#commandType',$('#inputpwd_form')).val(commandType);
	$('#password',$('#inputpwd_form')).val('');
}
//输入密码后的处理
function inputpwd(){
	 if(!$('#inputpwd_form').form('validate')){
	     return;
	 }
	  $.post('permi/user_checkPwd.action',
    			{'oldPassword' : $("#password").val()},
  			function(result){
    				if(result){
    					result=$.parseJSON(result);
    					if(result.success){
    						var commandType=$('#commandType',$('#inputpwd_form')).val();
    						$('#dlg_inputpwd').dialog('close');
    						sendCommand(commandType);
    					}else{
    						$.messager.alert(tipMsgDilag, result.msg);
    					}
    				}
    				
  	});
}

//指令回应的工况,工况采集，如果有重复，则不再插入
function isExistInWorkInfoGrid(unitId,nowTime){
	var data= $('#grid_workInfo').datagrid('getData');
	var row=null;
	var flag=false;
		for (var index = 0; index < data.total; index++) {
			row=data.rows[index];
			if(row.unitId==unitId&&row.nowTime==nowTime){
				flag=true;
			   break;
			}
		}
	return flag;
}
//开启跟踪
function refreshPollingTrack(){
	if(trackInfo['pTraceTimes']<1){
		 if(refreshRunTrackTimeOutId){
	         clearTimeout(refreshRunTrackTimeOutId) ;
	       }
		return;
	}
	//从memcache中取数据
	 $.post('run/run_getTrackData.action',
			{id:trackInfo['unitIds']},
   			function(result){
     	  	     try {
     	  	    	// debugger;
						var r = $.parseJSON(result);
						var gpsInfo=r.gpsInfo;//gps信息
						//gps信息
						if(gpsInfo&&gpsInfo.length>0){
							//$('#tabs_south').tabs('select', 0);
						   addGpsDataToGrids(gpsInfo);
						   //刷新到地图上
						   if(typeof  ifm_map_polling.window.addMapDataOfMonitor == "function"){
								 ifm_map_polling.window.addMapDataOfMonitor(gpsInfo);
						   }
						}
						trackInfo['pTraceTimes']=trackInfo['pTraceTimes']-1;
						refreshRunTrackTimeOutId=setTimeout(refreshPollingTrack,trackInfo['pTraceInterval']);
						
					} catch (e) {
						$.messager.alert(tipMsgDilag, e);
					}
   	}
   	);
}

function openDetailWin(){
	var node=getSelectNode();
	workInfData=node.vehicleId;
	$('<div/>').dialog({
		content : '<iframe src="jsp/run/composite_detail.jsp" id="ifm_detail" name="ifm_detail" frameborder="0" style="border:0;width:100%;height:99%;"></iframe>',
		width : 740,
		height : 480,
		modal : true,
		cache : false,
		resizable:true,
		title : '机械详细信息',
		buttons : [{
			text : '关闭',
			iconCls : 'icon-cancel',
			handler : function() {
				var d = $(this).closest('.window-body');
				d.dialog('destroy');
			}
		}],
		onLoad : function() {},
		onClose : function() {
			$(this).dialog('destroy');
		}
	});
}