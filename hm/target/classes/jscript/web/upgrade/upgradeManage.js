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
   					//alert($(this).data('data').call_letter);
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
   					//alert($(this).data('data').call_letter);
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
	
	//查询配置指令
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
   					//alert($(this).data('data').call_letter);
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
   					//alert($(this).data('data').call_letter);
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
        //$(document).sgPup({message:'message_info',text: extend);
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
        mask.css('z-index',Number($('div.window').css('z-index'))+1);//如果有弹出窗口，则将遮罩层置为最上层
        var span=$('<span></span>');
        span.css({position:'absolute',left:$(window).outerWidth()/2-80,top:$(window).outerHeight()/2-60,color:'red','font-size':'x-large','font-weight':'bold'});
        span.text('正在导入中...请稍作休息！');
        mask.append(span);
        $(document.body).append(mask);
        
        /*var paramsimport = {};
		var urlimport = '../../../upgrade/importUpgrade';
		paramsimport.ip = $('#ip', '#upgrade_import').val();
		paramsimport.port = $('#port', '#upgrade_import').val();
		paramsimport.upgrade_file = $('#upgrade_file', '#upgrade_import').val();
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
								url : urlimport,
								data : JSON.stringify(paramsimport),
								success : function(data) {
									if (data) {
										if (data.success) {

											$(document).sgWindow('close', {
												id : 'import_upgrade'
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
		$('#upgrade_import').unbind(); 
		return false;
        */
        if(isGoOn){
            $("#upgrade_import").submit();
           
        }
    }    

	var but_;
	$.ajax({
		  type : 'post', 
		  async: false,   
		  contentType : 'application/json',     
		  dataType : 'json',     
		  url : '../../../upgrade/getIsOpen',   
		  data:JSON.stringify({}),
		  success : function(data) {
			  if(data){
				if(data.msg==1){
					but_ = [ {name: '升级',bclass : 'add',onpress : addItem},
					         {name: '取消',bclass : 'delete',onpress : cancelItem},
	                         {name: '导入',bclass : 'import',onpress : importItem},
	                         {name: '查车',bclass : 'jihuo',onpress : searchItem},
	                         {name: '查询配置',bclass : 'jihuo',onpress : confItem}]
				}else{
					but_ = '';		
			  }
		  }  
		  }
		}); 
	
 	var height = 345;
 	//初始化表格
 	var defaults = {
 		        title: "空中升级管理",
 		        width:  '970',
 		        height: height,
 		        rownumbers:true,
 		        usepager: true,
 		        url:'../../../upgrade/findSimUpgradeByPage',
 		        useRp: true,
 		        colid:'id',  //主键
 		        colModel : [
 		                {display: '终端呼号', name : 'call_letter',width : '8%', sortable : false},
						{display: 'TBOX条码', name : 'barcode',width : '9%',  sortable : false},
						{display: '车架号', name : 'vin', width : '6%', sortable : false},
						{display: '当前版本', name : 'cur_ver', width : '8%', sortable : false},
						{display: '升级文件名', name : 'ug_ver', width : '8%',sortable : false},
						{display: '升级状态', name : 'flag', width : '8%', sortable : true,formatter:function(value,row){
    	  	                if(value==null || value==0 ){
    	  	                    value = '未升级';
    	  	                }else if(value==1){
    	  	                    value = '要求升级';
    	  	                }else if(value ==2){
    	  	                	 value = '已发查车';
    	  	                }else if(value ==3){
    	  	                	 value = '已发升级';
    	  	                }else if(value ==4){
    	  	                	 value = '升级中';
    	  	                }else if(value ==5){
    	  	                	 value = '升级失败';
    	  	                }else if(value ==6){
    	  	                	 value = '升级成功';
    	  	                }else if(value ==7){
    	  	                	 value = '升级取消';
    	  	                }
    	  	                return value;
    	  	           }},
    	  	           {display: '批次名称', name : 'upgrade_name',width : '8%',  sortable : false},
    	  	           {display: '扫描时间', name : 'scan_time',width : '8%',  sortable : false},
	    	  	       {display: '体检状态', name : 'is_on',width : '6%',  sortable : false,formatter:function(value,row){
	    	  	    	   		if(value==0 ){
	    	  	    	   			return value = '关闭';
			  	                }else if(value == 1){
			  	                	return value = '开启';
			  	                }
			  	                return value;
		  	           		}
		  	           },
		  	           {display: '配置简码', name : 'equip_code',width : '2%',  sortable : false},
    	  	           {display: '配置信息', name : 'conf_code',width : '7%',  sortable : false},
    	  	           {display: '配置状态', name : 'conf_flag',width : '8%',  sortable : false,formatter:function(value,row){
    	  	        	   		if(value==null || value==0 ){
			  	                    value = '未配置';
			  	                }else if(value==1){
			  	                    value = '已发查询指令';
			  	                }else if(value ==2){
			  	                	 value = '已发设置指令';
			  	                }else if(value ==3){
			  	                	 value = '配置失败';
			  	                }else if(value ==4){
			  	                	 value = '配置成功';
			  	                }
			  	                return value;
    	  	           		}
    	  	           },
    	  	           {display: '升级开始时间', name : 's_time',width : '6%',  sortable : false},
    	  	           {display: '升级结束时间', name : 'e_time',width : '6%',  sortable : false}
 		        ],
 		       buttons : but_
 		        /*,
 		         {name: '查看升级信息',bclass : 'view',onpress : viewItem}*/
	             ,
		 		        searchitems : [
				{
					display : '<span style="padding-left:2px;">终端呼号</span>',
					name : 'call_letter',
					type : 'text',
					width : '100'
				},
				{
					display : '<span style="padding-left:20px;">车架号</span>',
					name : 'vin',
					type : 'text',
					width : '110'
				},
				{
					display : '<span style="padding-left:24px;">TBOX条码</span>',
					name : 'barcode',
					type : 'text',
					width : '140'
				},
				{
					display : '<span style="padding-left:20px;">版本号</span>',
					html : '<input type="text" name="cur_ver" style="width:110px" id="cur_ver"  list="companyDataList"/><input type="hidden" name="subcoNo" id="subcoNo" />'
				}, 
				{
					display : '<span style="padding-left:2px;">批次名称</span>',
					name : 'upgrade_name',
					type : 'text',
					width : '100'
				},
				{
					display : '<span style="padding-left:2px;">升级状态</span>',
					name : 'flag',
					html : '<select style="width:120px"  name="flag"><option value="" selected>请选择...</option><option value="0">未升级</option><option value="1">要求升级</option><option value="2">已发查车</option><option value="3">已发升级</option><option value="4">升级中</option><option value="5">升级失败</option><option value="6">升级成功</option><option value="7">升级取消</option></select>'
				},
				{
					display : '<span style="padding-left:20px;">扫描时间    从</span>',
					name : 'startDate',
					html : '<input id="startDate" name="startDate" class="Wdate" onfocus="WdatePicker({dateFmt:\'yyyy-MM-dd HH:mm:ss\'})" style="width: 140px"  >'
				}, {
					display : '<span style="padding-left:10px;">到</span>',
					name : 'endDate',
					html : '<input id="endDate" name="endDate" class="Wdate" onfocus="WdatePicker({dateFmt:\'yyyy-MM-dd HH:mm:ss\'})" style="width: 140px"  >'
				},
				{
					display : '<span style="padding-left:2px;">配置状态</span>',
					name : 'flag',
					html : '<select style="width:120px"  name="conf_flag"><option value="" selected>请选择...</option><option value="0">未配置</option><option value="1">已发查询指令</option><option value="2">已发设置指令</option><option value="3">配置失败</option><option value="4">配置成功</option></select>'
				}
				,
				{
					display : '<span style="padding-left:2px;">体检开关</span>',
					name : 'flag',
					html : '<select style="width:80px"  name="is_on"><option value="" selected>请选择...</option><option value="0">关闭</option><option value="1">开启</option></select>'
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
						  
						  //$("#companyName").on('keyup',stockCompany);
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