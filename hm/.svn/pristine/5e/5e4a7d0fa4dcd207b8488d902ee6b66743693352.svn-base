(function($) {
	

	
	var loadSuccess_modifyCard = function() {
		
		// 填充修改页面编辑前的值
		if (editId && editObj) {
			$("#sim_id", "#preload_modifyCard").val(editObj.sim_id);
		}
		editId = null;
		editObj = null;
	}
	
	

	var loadSuccess = function() {
		
		// 填充修改页面编辑前的值
		if (editId && editObj) {
			$("#file_name", "#preload_modifyCard").val(editObj.sim_id);
		}
		editId = null;
		editObj = null;
	}
	
	
	
	

	var winClose1 = function() {// 关闭添加页面
		$(document).sgWindow('close', {
			id : 'add_upgradeFile'
		});
	}
	
	
	var winClose2 = function() {// 关闭添加页面
		$(document).sgWindow('close', {
			id : 'modify_file'
		});
	}



	
	
	
	

	var addItem = function() {

					var defaults = {
							title : '上传升级文件',
							id : 'add_upgradeFile',
							form : 'upgradeFile_addForm',
							url : 'upgradeFile_add.html',
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

function save(){
		
	}
	
	  var deleteItem = function(){
	        var obj = $('#monitoring');
	        var bDiv = $('.bDiv',obj);

	        if($('input[type=checkbox][checked=checked]',bDiv)!=null&&$('input[type=checkbox][checked=checked]',bDiv).length==0){
	            $(document).sgPup({message:'message_info',text: "请选择一个选项！"});
	        }else if($('input[type=checkbox][checked=checked]',bDiv)!=null&&$('input[type=checkbox][checked=checked]',bDiv).length>0){
	       	 $(document).sgConfirm({text: '确定吗?',cfn:function(r){ 
			    if(r){
	     		var flag = true;
	     		var pids=[];
	     		$('input[type=checkbox][checked=checked]',bDiv).each(function(){
		                if($(this).attr("checked")){    
		                	editId=this.value;
		                	editobj = $(this).data('data');
		                		pids.push(editobj.id);
		                }	
		            });
	     			//打开窗口
	            	$.ajax( {
						  type : 'post', 
	            		  async: false,   
	            		  dataType : 'json',     
	            		  url : '../../../upgradeFile/delete',   
	            		  data:{ids:pids},
	            		  success : function(data) {
	            			  if(data){
	            				 if(data.success){
	            					 $(document).sgPup({message:'message_info',text: "操作成功！"});
	            				 }else{
	            					 $(document).sgPup({message:'message_info',text: "操作失败！"});
	            				 }
	            			  }
	            		  } ,     
	            		  error : function(res,error) { 
	            		  	     if(res && res.responseText){ $(document).sgPup({message:'message_info',text: res.responseText});}     
	            		  }    
	            		}); 
	     		$('#monitoring').sgDatagrid('reload','sgDatagrid');
	     	}	
	     	 }});
	       	 
	        }
	    }
	    
        
       
	
	var viewItem = function() {
		var obj = $('#monitoring');
		var bDiv = $('.bDiv', obj);
		if ($('input[type=checkbox][checked=checked]', bDiv) != null
				&& $('input[type=checkbox][checked=checked]', bDiv).length > 1) {
			$(document).sgPup({
				message : 'message_info',
				text : "只能选择一条记录！"
			});
		} else if ($('input[type=checkbox][checked=checked]', bDiv) != null
				&& $('input[type=checkbox][checked=checked]', bDiv).length == 0) {
			$(document).sgPup({
				message : 'message_info',
				text : "请选择一个选项！"
			});
		} else if ($('input[type=checkbox][checked=checked]', bDiv) != null
				&& $('input[type=checkbox][checked=checked]', bDiv).length == 1) {
			$('input[type=checkbox][checked=checked]', bDiv).each(function() {
				if ($(this).attr("checked")) {
					editId = this.value;
					editObj = $(this).data('data');
					var defaults = {
						title : '补卡',
						id : 'preload_modifyCard',
						form : 'preload_modifyCard_form',
						url : 'preload_modifyCard.html',
						success : loadSuccess_modifyCard,
						width : 620,
						height : 246,
						buttons : [ {
							name : '保存',
							type : 'submit',
							onpress : modifyCard
						}, {
							name : '关闭',
							onpress : winClose5
						} ]
					};
					$(document).sgWindow(defaults);
				}
			});
		} else {
			$(document).sgPup({
				message : 'message_info',
				text : "请选择一个选项！"
			});
		}

	}


 	var height = 345;
 	//初始化表格
 	var defaults = {
 		        title: "升级文件列表管理",
 		        width:  '970',
 		        height: height,
 		        rownumbers:true,
 		        usepager: true,
 		        url:'../../../upgradeFile/findUpgradeFileByPage',
 		        useRp: true,
 		        colid:'id',  //主键
 		        colModel : [
 		                {display: '升级文件名', name : 'filename', width : '20%', sortable : false},
						{display: '升级服务器IP', name : 'ip', width : '25%', sortable : false},
						{display: '升级服务器端口', name : 'port', width : '22%', sortable : false},
						{display: '上传时间', name : 'stamp', width : '22%', sortable : false}
 		        ],
 		       buttons : [ 
 		         {name: '上传文件',bclass : 'add',onpress : addItem},
 		         {name: '删除',bclass : 'delete',onpress : deleteItem}
 		        /* {name: '查看升级信息',bclass : 'view',onpress : viewItem}*/
	             ],
 		        searchitems :[
 		                      {display:'文件名',name:'filename',type:'text',width:120},
 		                      {display:'开始日期',name:'startDate',type:'date',width:120},
 		  			      	  {display:'结束日期',name:'endDate',type:'date',width:120}
 		                     /* {
 		         				display : '版本号',
 		         				name : 'cur_ver',
 		         				width:'150',
 		         				html:'<input type="text" name="cur_ver" id="cur_ver" style="width:150px" placeholder="请输入版本号" list="modelDataList"/>'
 		         					+ '<input type="hidden" name="modelId" id="modelId">'
 		         					+ '<datalist id="modelDataList"></datalist>'
 		         		     },*/
 		    		        ],
 		    		       // exporturl:'../../../upgrade/exportUpgrade'
 		      		     
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