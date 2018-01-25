(function($) {	
	
	var loadSuccess_modifyCard = function() {
		
		// 填充修改页面编辑前的值
		if (editId && editObj) {
			$("#sim_id", "#preload_modifyCard").val(editObj.sim_id);
		}
		editId = null;
		editObj = null;
	}

	var winClose1 = function() {// 关闭添加页面
		$(document).sgWindow('close', {
			id : 'add_upgrade'
		});
	}
	
	var save = function() {
		var call_letters = [];
		var is_all = 0;
		var obj = $('#monitoring');
		var bDiv = $('.bDiv', obj);
		var ids = document.getElementsByName("checkall");
        var flag = false ;               
        for(var i=0;i<ids.length;i++){
             if(ids[i].checked){
                 flag = true ;
                 break ;
             }
         }
         if(!flag){
             $('input[type=checkbox][checked=checked]', bDiv).each(function() {
   				if ($(this).attr("checked")) {
   					call_letters.push($(this).data('data').call_letter);
   					// alert($(this).data('data').call_letter);
   				}
   			});
         }else{
        	 is_all = 1;
         }
		
		// 保存
		var params = {};
		var urladd = '../../../upgrade/add';
		params.ip = $('#ip', '#upgrade_add').val();
		params.port = $('#port', '#upgrade_add').val();
		params.ug_ver = $('#ug_ver', '#upgrade_add').val();
		params.is_all =is_all;
		params.call_letters =call_letters;
		
		if(!params.port ){
			 $(document).sgPup({message:'message_info',text: "升级服务器端口不能为空！"});
			 	return false;
		 }
		 
		if(!params.ug_ver ){
			 $(document).sgPup({message:'message_info',text: "升级版本号不能为空！"});
			 	return false;
		 }
		
		 if(!params.ip ){
			 $(document).sgPup({message:'message_info',text: "升级服务器ip不能为空！"});
			 	return false;
		 }
		 
		
		$(document).sgConfirm(
				{
					text : '确定保存该升级信息吗?',
					cfn : function(r) {
						if (r) {
							$.ajax({
								type : 'post',
								async : false,
								contentType : 'application/json',
								dataType : 'json',
								url : urladd,
								data : JSON.stringify(params),
								success : function(data) {
									if (data) {
										if (data.success) {

											$(document).sgWindow('close', {
												id : 'add_upgrade'
											});
											$('#monitoring').sgDatagrid(
													'reload', 'sgDatagrid');
										}
										$(document).sgPup({
											message : 'message_alert',
											text : data.msg
										});
									}
								},
								error : function(res, error) {
									alert(res.responseText);
								}
							});
						}
					}
				});
		$('#add_upgrade').unbind(); 
		return false;
	};
	
	
	
	

	var addItem = function() {
		var obj = $('#monitoring');
		var bDiv = $('.bDiv', obj);
		if ($('input[type=checkbox][checked=checked]', bDiv) != null
				&& $('input[type=checkbox][checked=checked]', bDiv).length == 0) {
			$(document).sgPup({
				message : 'message_info',
				text : "请选择一个选项！"
			});
		} else if ($('input[type=checkbox][checked=checked]', bDiv) != null
				&& $('input[type=checkbox][checked=checked]', bDiv).length >= 1) {
					var defaults = {
							title : '升级',
							id : 'add_upgrade',
							form : 'upgrade_form',
							url : 'upgrade_add.html',
							width : 360,
							height : 145,
							buttons : [ {
								name : '保存',
								type : 'submit',
								onpress : save,
							}, {
								name : '关闭',
								onpress : winClose1,
							} ]
						};
						$(document).sgWindow(defaults);
		}

	}

	var cancelItem = function() {

		var call_letters = [];
		var is_all = 0;
		var obj = $('#monitoring');
		var bDiv = $('.bDiv', obj);
		var ids = document.getElementsByName("checkall");
        var flag = false ;               
        for(var i=0;i<ids.length;i++){
             if(ids[i].checked){
                 flag = true ;
                 break ;
             }
         }
         if(!flag){
             $('input[type=checkbox][checked=checked]', bDiv).each(function() {
   				if ($(this).attr("checked")) {
   					call_letters.push($(this).data('data').call_letter);
   					// alert($(this).data('data').call_letter);
   				}
   			});
         }else{
        	 is_all = 1;
         }
		
		// 保存
		var params = {};
		var url = '../../../upgrade/cancel';	
		params.is_all =is_all;
		params.call_letters =call_letters;
		
		if ($('input[type=checkbox][checked=checked]', bDiv) != null
				&& $('input[type=checkbox][checked=checked]', bDiv).length == 0) {
			$(document).sgPup({
				message : 'message_info',
				text : "请选择一个选项！"
			});
		} else if ($('input[type=checkbox][checked=checked]', bDiv) != null
				&& $('input[type=checkbox][checked=checked]', bDiv).length >= 1) {
			$(document).sgConfirm(
						{
							text : '确定取消升级吗?',
							cfn : function(r) {
								if (r) {
									$.ajax({
										type : 'post',
										async : false,
										contentType : 'application/json',
										dataType : 'json',
										url : url,
										data : JSON.stringify(params),
										success : function(data) {
											if (data) {
												if (data.success) {												
													$('#monitoring').sgDatagrid(
															'reload', 'sgDatagrid');
												}
												$(document).sgPup({
													message : 'message_alert',
													text : data.msg
												});
											}
										},
										error : function(res, error) {
											alert(res.responseText);
										}
									});
								}
							}
						});

				}
						

	}
	
	// 查询配置指令
	var confItem = function() {

		var call_letters = [];
		var is_all = 0;
		var obj = $('#monitoring');
		var bDiv = $('.bDiv', obj);
		var ids = document.getElementsByName("checkall");
        var flag = false ;               
        for(var i=0;i<ids.length;i++){
             if(ids[i].checked){
                 flag = true ;
                 break ;
             }
         }
         if(!flag){
             $('input[type=checkbox][checked=checked]', bDiv).each(function() {
   				if ($(this).attr("checked")) {
   					call_letters.push($(this).data('data').call_letter);
   					// alert($(this).data('data').call_letter);
   				}
   			});
         }else{
        	 is_all = 1;
         }
		
		// 保存
		var params = {};
		var url = '../../../upgrade/confCommand';	
		params.is_all = is_all;
		params.call_letters =call_letters;
		
		if ($('input[type=checkbox][checked=checked]', bDiv) != null
				&& $('input[type=checkbox][checked=checked]', bDiv).length == 0) {
			$(document).sgPup({
				message : 'message_info',
				text : "请选择一个选项！"
			});
		} else if ($('input[type=checkbox][checked=checked]', bDiv) != null
				&& $('input[type=checkbox][checked=checked]', bDiv).length >= 1) {
			$(document).sgConfirm(
						{
							text : '确定下发查询配置指令吗?',
							cfn : function(r) {
								if (r) {
									$.ajax({
										type : 'post',
										async : false,
										contentType : 'application/json',
										dataType : 'json',
										url : url,
										data : JSON.stringify(params),
										success : function(data) {
											if (data) {
												if (data.success) {												
													$('#monitoring').sgDatagrid('reload', 'sgDatagrid');
												}
												$(document).sgPup({
													message : 'message_alert',
													text : data.msg
												});
											}
										},
										error : function(res, error) {
											alert(res.responseText);
										}
									});
								}
							}
						});

				}
	}
	
	var searchItem = function() {

		var call_letters = [];
		var is_all = 0;
		var obj = $('#monitoring');
		var bDiv = $('.bDiv', obj);
		var ids = document.getElementsByName("checkall");
        var flag = false ;               
        for(var i=0;i<ids.length;i++){
             if(ids[i].checked){
                 flag = true ;
                 break ;
             }
         }
         if(!flag){
             $('input[type=checkbox][checked=checked]', bDiv).each(function() {
   				if ($(this).attr("checked")) {
   					call_letters.push($(this).data('data').call_letter);
   					// alert($(this).data('data').call_letter);
   				}
   			});
         }else{
        	 is_all = 1;
         }
		
		// 保存
		var params = {};
		var url = '../../../upgrade/searchCommand';	
		params.is_all =is_all;
		params.call_letters =call_letters;
		
		if ($('input[type=checkbox][checked=checked]', bDiv) != null
				&& $('input[type=checkbox][checked=checked]', bDiv).length == 0) {
			$(document).sgPup({
				message : 'message_info',
				text : "请选择一个选项！"
			});
		} else if ($('input[type=checkbox][checked=checked]', bDiv) != null
				&& $('input[type=checkbox][checked=checked]', bDiv).length >= 1) {
			$(document).sgConfirm(
						{
							text : '确定下发查车唤醒终端吗?',
							cfn : function(r) {
								if (r) {
									$.ajax({
										type : 'post',
										async : false,
										contentType : 'application/json',
										dataType : 'json',
										url : url,
										data : JSON.stringify(params),
										success : function(data) {
											if (data) {
												if (data.success) {												
													$('#monitoring').sgDatagrid(
															'reload', 'sgDatagrid');
												}
												$(document).sgPup({
													message : 'message_alert',
													text : data.msg
												});
											}
										},
										error : function(res, error) {
											alert(res.responseText);
										}
									});
								}
							}
						});

				}
	}
	
	var importItem = function() {// 导入
		var defaults = {
			title : '升级资料导入',
			id : 'import_upgrade',
			form : 'upgrade_importForm',
			url : 'upgrade_import.html',
			width : 360,
			height : 149,
			buttons : [ 
			            {name: '确定', type: 'submit', onpress : subImport},
			            {name : '关闭',onpress : winClose_import}
			          ]
		};
		$(document).sgWindow(defaults);	
}

	var winClose_import = function() {// 关闭添加页面
		$(document).sgWindow('close', {
			id : 'import_upgrade'
		});
	}
	function isvalidatefile(obj){
        var extend = obj.substring(obj.lastIndexOf(".") + 1);
        // $(document).sgPup({message:'message_info',text: extend);
        if (extend == "") {
        } else {
            if (!(extend.toLocaleLowerCase() == "xls")) {
                $(document).sgPup({message:'message_info',text: "请上传后缀名为xls的文件!"});
                return false;
            }
        }
        return true;
    }

	
	
    var subImport = function(){
    	
        if ($('#upgrade_file').val() == '') {
            $(document).sgPup({message:'message_info',text: '请选择上传导入文件!'});
            $('#upgrade_file').focus();
            return false;
        }else{
            var file=$('#upgrade_file')[0];
            var files=file.files;
            if(files){
                var filesLength=files.length;
                if(filesLength>50){
                    $(document).sgPup({message:'message_info',text: '一次性最多上传50个文件!'});
                        $('#upgrade_file').focus();
                    return false;
                }
                var isGoOn=true;
                for(var i=0;i<filesLength;i++){
                    if(!isvalidatefile(files[i].name)){
                        isGoOn=false;
                        break;
                    }
                }
                if(!isGoOn){
                  return false;
                }

            }
        }
        var mask=$('<div id="imp_mask"></div>');
        mask.addClass('window-mask');
        mask.css('z-index',Number($('div.window').css('z-index'))+1);// 如果有弹出窗口，则将遮罩层置为最上层
        var span=$('<span></span>');
        span.css({position:'absolute',left:$(window).outerWidth()/2-80,top:$(window).outerHeight()/2-60,color:'red','font-size':'x-large','font-weight':'bold'});
        span.text('正在导入中...请稍作休息！');
        mask.append(span);
        $(document.body).append(mask);
        
        /*
		 * var paramsimport = {}; var urlimport =
		 * '../../../upgrade/importUpgrade'; paramsimport.ip = $('#ip',
		 * '#upgrade_import').val(); paramsimport.port = $('#port',
		 * '#upgrade_import').val(); paramsimport.upgrade_file =
		 * $('#upgrade_file', '#upgrade_import').val(); $(document).sgConfirm( {
		 * text : '确定保存该升级信息吗?', cfn : function(r) { if (r) { $.ajax({ type :
		 * 'post', async : false, contentType : 'application/json', dataType :
		 * 'json', url : urlimport, data : JSON.stringify(paramsimport), success :
		 * function(data) { if (data) { if (data.success) {
		 * 
		 * $(document).sgWindow('close', { id : 'import_upgrade' });
		 * $('#monitoring').sgDatagrid( 'reload', 'sgDatagrid'); }
		 * $(document).sgPup({ message : 'message_alert', text : data.msg }); } },
		 * error : function(res, error) { alert(res.responseText); } }); } } });
		 * $('#upgrade_import').unbind(); return false;
		 */
        if(isGoOn){
            $("#upgrade_import").submit();
           
        }
    }    
    
    /**
	 * 开启省流量指令选项
	 * 
	 * @returns
	 */
    function openItem(){
    	var obj = $('#monitoring');
		var bDiv = $('.bDiv', obj);

		if ($('input[type=checkbox][checked=checked]', bDiv) != null
				&& $('input[type=checkbox][checked=checked]', bDiv).length == 0) {
				$(document).sgPup({
					message : 'message_info',
					text : "请至少选择一个选项！"
				});
		} else if ($('input[type=checkbox][checked=checked]', bDiv) != null
				&& $('input[type=checkbox][checked=checked]', bDiv).length >= 1) {
			
			$('input[type=checkbox][checked=checked]', bDiv).each(function() {
   				if ($(this).attr("checked")) {
   					console.log($(this).data('data'));
   				}
   			});
			
			$(document).sgConfirm({text: '确定开启所选sim卡的省流量指令吗?',cfn:function(r){ 
			    if(r){
			    	var simIds=[];
			    	
			    	$('input[type=checkbox][checked=checked]',bDiv).each(function(){
		                if($(this).attr("checked")){    
		                	var editobj = $(this).data('data');
		                	simIds.push(editobj.callLetter);
		                }
		            });
			    	
			    	var params = {};
			    	params.callLetters = simIds,
			    	params.opType = 1;
	     			// 发送打开省流量指令请求
	            	$.ajax({
						  type : 'post', 
	            		  async: false,   
	            		  dataType : 'json',     
	            		  url : '../../../fccmdManage/sendSaveFlowCmd',
	            		  data:JSON.stringify(params),
	            		  contentType : 'application/json',
	            		  success : function(data) {
	            			  console.log(data);
	            			  if(data){
	            				 if(data.success){
	            					 $(document).sgPup({message:'message_info',text: data.msg});
	            				 }else{
	            					 $(document).sgPup({message:'message_info',text: data.msg});
	            				 }
	            			  }
	            		  } ,     
	            		  error : function(res,error) { 
	            		  	     if(res && res.responseText){ $(document).sgPup({message:'message_info',text: res.responseText});}     
	            		  }    
	            	});
	     		$('#monitoring').sgDatagrid('reload','sgDatagrid');
			    }
			  }
		    });
		}
    };
    /**
	 * 关闭指令选项
	 * 
	 * @returns
	 */
    function closeItem(){
    	var obj = $('#monitoring');
		var bDiv = $('.bDiv', obj);

		if ($('input[type=checkbox][checked=checked]', bDiv) != null
				&& $('input[type=checkbox][checked=checked]', bDiv).length == 0) {
				$(document).sgPup({
					message : 'message_info',
					text : "请至少选择一个选项！"
				});
		} else if ($('input[type=checkbox][checked=checked]', bDiv) != null
				&& $('input[type=checkbox][checked=checked]', bDiv).length >= 1) {
			
			$('input[type=checkbox][checked=checked]', bDiv).each(function() {
   				if ($(this).attr("checked")) {
   					console.log($(this).data('data'));
   				}
   			});
			
			$(document).sgConfirm({text: '确定关闭所选sim卡的省流量指令吗?',cfn:function(r){ 
			    if(r){
			    	var simIds=[];
			    	
			    	$('input[type=checkbox][checked=checked]',bDiv).each(function(){
		                if($(this).attr("checked")){    
		                	var editobj = $(this).data('data');
		                	simIds.push(editobj.callLetter);
		                }
		            });
			    	
			    	var params = {};
			    	params.callLetters = simIds,
			    	params.opType = 0;
	     			// 发送关闭省流量指令请求
	            	$.ajax({
						  type : 'post', 
	            		  async: false,   
	            		  dataType : 'json',     
	            		  url : '../../../fccmdManage/sendSaveFlowCmd',
	            		  data:JSON.stringify(params),
	            		  contentType : 'application/json',
	            		  success : function(data) {
	            			  console.log(data);
	            			  if(data){
	            				 if(data.success){
	            					 $(document).sgPup({message:'message_info',text: data.msg});
	            				 }else{
	            					 $(document).sgPup({message:'message_info',text: data.msg});
	            				 }
	            			  }
	            		  } ,     
	            		  error : function(res,error) { 
	            		  	     if(res && res.responseText){ $(document).sgPup({message:'message_info',text: res.responseText});}     
	            		  }    
	            	});
	     		$('#monitoring').sgDatagrid('reload','sgDatagrid');
			    }
			  }
		    });
		}
    };
	
    /**
     * 取消打开省流量指令下发
     * @returns
     */
    function cancleOpenItem(){
    	var obj = $('#monitoring');
		var bDiv = $('.bDiv', obj);

		if ($('input[type=checkbox][checked=checked]', bDiv) != null
				&& $('input[type=checkbox][checked=checked]', bDiv).length == 0) {
				$(document).sgPup({
					message : 'message_info',
					text : "请至少选择一个选项！"
				});
		} else if ($('input[type=checkbox][checked=checked]', bDiv) != null
				&& $('input[type=checkbox][checked=checked]', bDiv).length >= 1) {
			
			$('input[type=checkbox][checked=checked]', bDiv).each(function() {
   				if ($(this).attr("checked")) {
   					console.log($(this).data('data'));
   				}
   			});
			
			$(document).sgConfirm({text: '确定取消所选sim卡的省流量开启指令吗?',cfn:function(r){ 
			    if(r){
			    	var simIds=[];
			    	
			    	$('input[type=checkbox][checked=checked]',bDiv).each(function(){
		                if($(this).attr("checked")){    
		                	var editobj = $(this).data('data');
		                	simIds.push(editobj.callLetter);
		                }
		            });
			    	
			    	var params = {};
			    	params.callLetters = simIds,
			    	params.opType = 1;
	     			// 发送打开省流量指令请求
	            	$.ajax({
						  type : 'post', 
	            		  async: false,   
	            		  dataType : 'json',     
	            		  url : '../../../fccmdManage/cancleSaveFlowCmd',
	            		  data:JSON.stringify(params),
	            		  contentType : 'application/json',
	            		  success : function(data) {
	            			  console.log(data);
	            			  if(data){
	            				 if(data.success){
	            					 $(document).sgPup({message:'message_info',text: data.msg});
	            				 }else{
	            					 $(document).sgPup({message:'message_info',text: data.msg});
	            				 }
	            			  }
	            		  } ,     
	            		  error : function(res,error) { 
	            		  	     if(res && res.responseText){ $(document).sgPup({message:'message_info',text: res.responseText});}     
	            		  }    
	            	});
	     		$('#monitoring').sgDatagrid('reload','sgDatagrid');
			    }
			  }
		    });
		}
    }
    
    /**
     * 取消关闭省流量指令下发
     * @returns
     */
    function cancleCloseItem(){
    	var obj = $('#monitoring');
		var bDiv = $('.bDiv', obj);

		if ($('input[type=checkbox][checked=checked]', bDiv) != null
				&& $('input[type=checkbox][checked=checked]', bDiv).length == 0) {
				$(document).sgPup({
					message : 'message_info',
					text : "请至少选择一个选项！"
				});
		} else if ($('input[type=checkbox][checked=checked]', bDiv) != null
				&& $('input[type=checkbox][checked=checked]', bDiv).length >= 1) {
			
			$('input[type=checkbox][checked=checked]', bDiv).each(function() {
   				if ($(this).attr("checked")) {
   					console.log($(this).data('data'));
   				}
   			});
			
			$(document).sgConfirm({text: '确定取消所选sim卡的省流量关闭指令吗?',cfn:function(r){ 
			    if(r){
			    	var simIds=[];
			    	
			    	$('input[type=checkbox][checked=checked]',bDiv).each(function(){
		                if($(this).attr("checked")){    
		                	var editobj = $(this).data('data');
		                	simIds.push(editobj.callLetter);
		                }
		            });
			    	
			    	var params = {};
			    	params.callLetters = simIds,
			    	params.opType = 0;
	     			// 发送取消关闭省流量指令请求
	            	$.ajax({
						  type : 'post', 
	            		  async: false,   
	            		  dataType : 'json',     
	            		  url : '../../../fccmdManage/cancleSaveFlowCmd',
	            		  data:JSON.stringify(params),
	            		  contentType : 'application/json',
	            		  success : function(data) {
	            			  console.log(data);
	            			  if(data){
	            				 if(data.success){
	            					 $(document).sgPup({message:'message_info',text: data.msg});
	            				 }else{
	            					 $(document).sgPup({message:'message_info',text: data.msg});
	            				 }
	            			  }
	            		  } ,     
	            		  error : function(res,error) { 
	            		  	     if(res && res.responseText){ $(document).sgPup({message:'message_info',text: res.responseText});}     
	            		  }    
	            	});
	     		$('#monitoring').sgDatagrid('reload','sgDatagrid');
			    }
			  }
		    });
		}
    }
	
 	var height = '100%';
 	// 初始化表格
 	var defaults = {
				title: "下发指令日志管理",
				width:  '100%',
				height: height,
				rownumbers:true,
				rowList:[20,50,100],
				usepager: true,
				url:'../../../fccmdManage/getAllCmdLogInfo',
 		        useRp: true,
 		        colid:'id',  // 主键
 		        colModel : [
 		        		{display: '车载号码', name : 'callLetter',width : '7%',align:'center',  sortable : false}, 		                
						{display: '指令类型', name : 'cmdId', width : '10%',align:'center', sortable : false,formatter:function(value,row){
							   var statusStr = '其他';
							   if(value != null && value == 240){
									statusStr='开启导航主机';
								}
							   if(value != null && value ==241 ){
									statusStr='关闭导航主机';
								}
							   if(value != null && value ==211 ){
									statusStr='开启省流量';
								}
							   if(value != null && value ==212 ){
									statusStr='关闭省流量';
								}
							   return statusStr;
							}
						},
						{display: '指令状态', name : 'flag', width : '10%',align:'center', sortable : false,formatter:function(value,row){
							   var statusStr = '初始值';
							   if(value != null && value == 1){
									statusStr='初始值';
								}
							   if(value != null && value ==2){
									statusStr='要求下发';
								}
							   if(value != null && value ==3){
									statusStr='已发查车';
								}
							   if(value != null && value ==4){
									statusStr='已发指令';
								}
							   if(value != null && value ==5){
									statusStr='指令执行中';
								}
							   if(value != null && value ==6){
									statusStr='指令执行失败';
								}
							   if(value != null && value ==7){
									statusStr='指令执行成功';
								}
							   if(value != null && value == 8){
									statusStr='指令取消';
								}
							   return statusStr;
							}
						},
						{display: '当前导航主机状态', name : 'currNaviStatus', width : '10%',align:'center', sortable : false,formatter:function(value,row){
							var statusStr = '';
						    if(value != null && value == 1){
								statusStr='开启';
							}
						    if(value != null && value == 0){
								statusStr='关闭';
							}
						    return statusStr;
						}},
						{display: '需设置的导航主机状态', name : 'toNaviStatus', width : '10%',align:'center', sortable : false,formatter:function(value,row){
							var statusStr = '';
						    if(value != null && value == 1){
								statusStr='开启';
							}
						    if(value != null && value == 0){
								statusStr='关闭';
							}
						    return statusStr;
						}},
						{display: '当前省流量状态', name : 'currFlowCtrlStatus', width : '10%',align:'center', sortable : false,formatter:function(value,row){
							var statusStr = '';
						    if(value != null && value == 1){
								statusStr='开启';
							}
						    if(value != null && value == 0){
								statusStr='关闭';
							}
						    return statusStr;
						}},
						{display: '需设置的省流量状态', name : 'tosetFlowCtrlStatus', width : '10%',align:'center', sortable : false,formatter:function(value,row){
							var statusStr = '';
						    if(value != null && value == 1){
								statusStr='开启';
							}
						    if(value != null && value == 0){
								statusStr='关闭';
							}
						    return statusStr;
						}},
						{display: '备注', name : 'remark', width : '18%',align:'center', sortable : false},
						{display: '操作时间', name : 'stamp', width : '10%',align:'center', sortable : false}						
 		        ],
// 		       buttons : [
// 		    	  {name: '开启指令',bclass : 'open',onpress : openItem},
// 		    	  {name: '取消开启指令',bclass : 'close',onpress : cancleOpenItem},
//			      {name: '关闭指令',bclass : 'close',onpress : closeItem},
//			      {name: '取消关闭指令',bclass : 'close',onpress : cancleCloseItem},
// 		       ],
		 	   searchitems : [
				{
					display : '<span style="padding-left:20px;">车载号码</span>',
					name : 'callLetter',
					type : 'text',
					width : '110'
				},				
				{
					display : '<span style="padding-left:2px;">指令类型</span>',
					name : 'flag',
					html : '<select style="width:80px"  name="cmdId"><option value="">请选择...</option><option value="240">开启导航主机</option><option value="241">关闭导航主机</option><option value="211">开启省流量</option><option value="212">关闭省流量</option></select>'
				}]
 				//,exporturl:'../../../upgrade/exportUpgrade'
 		    };
		
	    $('#monitoring').sgDatagrid(defaults);
	    
	    
		$.ajax({
			  type : 'post', 
			  async: true,   
			  contentType : 'application/json',     
			  dataType : 'json',     
			  url : '../../../upgrade/getUpgradeList',   
			  data:JSON.stringify({}),
			  success : function(data) {
				  if(data){
					  var companys = data;
					  if(companys.length>0){
						     $("#companyDataList").empty();
					  		companyDataList = {};
					   }
					  $.each( companys, function(key,value){
						  var op = $("<option></option>");
						  op.val(value.cur_ver);
						  $("#companyDataList").append(op);
						  
						  companyDataList[value.cur_ver]=value.id;
						});
					  
					  // $("#companyName").on('keyup',stockCompany);
					  $("#cur_ver").on('change',function(){
						    var strs = this.value.split(" ");
						    if(companyDataList[strs[strs.length-1]]){
						    	$(this).val(strs[strs.length-1]);
								
								$("#subcoNo").val(companyDataList[strs[strs.length-1]]);
								
								if($("#subcoNo").val().length==0){
									$("#cur_ver").val("");
								}
						    }else{
						    	$(this).val('');
						    	$("#subcoNo").val("");
						   }
						});
					  
				  }else{
					  $(document).sgPup({message:'message_info',text: data});
				  }
			  } ,     
			  error : function(res,error) { 
			  	     if(res && res.responseText){ $(document).sgPup({message:'message_info',text: res.responseText});}     
			  }
		});
 		    
})(jQuery);