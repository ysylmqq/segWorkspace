(function($){


  
	    var idx = -1; //初始化
	    //公用初始化
  //      $("#searchjobno").focus(); //自动获取焦点
        //任何地方被点击
        $(document).click(function(){
            $(".jobnoList").hide(); //隐藏推荐栏
        });


        /* form1的相关操作 BEGIN */
        /* 函数封装 BEGIN */
        /**
         * Ajax：推荐关键词
         */
        function recommend(){
        	
        	var url='../../getJobNo';
        	var url_tye = $('#url_tye').val();
        	
        	$("#jobnoList").html('');
        	idx = -1;
        	var hqkey = $('#hqkey').val();
            var params = {};
    		params.pageNo = 1;
    		params.pageSize = 10;
    		params.filter = {};
    		params.filter.jobNo = $('#searchjobno').val();
    		if(hqkey==0){
    			params.searchValue = hqkey;
    		}
            $.ajax( {
            	 type : 'post', 
            	 async: true,   
            	 contentType : 'application/json',     
            	 dataType : 'json',     
            	 url : url,   
            	 data:JSON.stringify(params),
            	 success : function(data) {
					  var form = $(document.nw_private_form);	//重置隐藏项
					  var inputHidden = form.find("input[type='hidden']");
					  inputHidden.each(function(k, v){
						  var obj = $(this);
						  var objId = obj.attr("id");
						  if(objId != "hqkey" && objId != "url_tye"){	//设置搜索标识不清空
							  obj.val("");
						  }
					  })
            		  if(data){
            			  var manager = $("#searchjobno");
            			  if(manager){
            				  if(data.items.length>0){
            					  $("#jobnoList").html('');
            					  $.each(data.items,function(k,v){
                					  $("#jobnoList").append('<li name='+v.vehicleId+' id="rec_'+k+'">'+v.jobNo+'</li>');
                				  	 }); 
                				  $("#jobnoList").show();
            				  }
            				  if(data.items.length==0){
            					  $("#jobnoList").append('<li>没有数据！</li>');
            					  $("#jobnoList").show();
            					  document.nw_private_form.reset();
            				  }
            				  if(data.items.length==1){
            					  var item = data.items[0];
            					  select_word(item.jobNo, item.vehicleId);
            					  $("#jobnoList").hide();
            				  }
            				}
            		  }else{
            			  $("#jobnoList").hide();
            		  }
            	 },     
            	 error : function(res,error) {
            		  $(document).sgPup({message:'message_alert',text: res.responseText}); 
            	 }    
            	});
        	
            
        }

        /**
         * Ajax：搜索
         */
        function search(){
        	
        	searchcust();
        }
        
        /**
         * 选择推荐词
         * @param idx 推荐词索引
         */
        function next_word(idx){
            $("#jobnoList li").removeClass("current");
            $("#rec_"+idx).addClass("current");
        }

        /**
         * 选定推荐词
         * @param word 推荐词
         */
        function select_word(word,id){
        	if(!id){
        		return;
        	}
        	
        	$('#cust_id').val('');
            $("#searchjobno").val(word);  //将选择的那个词填入搜索框 
            
		  	$('#vehicle_id').val(id);
		  	$('#unit_id').val('');
		  	$('#custname').val('');
			$('#searchnumber').val('');
			$('#vin').val('');
			$('#searchcall').val('');
		  	$('#engineno').val('');
            
            $("#jobnoList").html('').hide(); //去掉推荐栏
            search(); //触发搜索
        }
 
        /* 函数封装 END */


        /* 事件处理 BEGIN */
        //点击搜索按钮时
        $("#submit").click(function(){
            search(); //触发搜索
        });    

        //关键字有变化时
        $("#searchjobno").keyup(function(event){
            //方式一：按回车时出现推荐栏
           if(!$("#jobnoList li.current").text() && event.keyCode==13){
            //方式二：按下的键是数字或字母或删除键时（即搜索框有变化时），出现推荐栏
           //  if((event.keyCode>47 && event.keyCode<106) || event.keyCode==8 || event.keyCode==46){ 
                recommend(); //触发关键词推荐，显示出推荐栏
            }
        });

        //键盘上下移动光标和按回车
        //var idx = -1; //初始化高亮项
        $("#searchjobno").keyup(function(event){
            max = $('#jobnoList li').length - 1 //一共有max个推荐词(因为从0开始计数，所以要-1)
            if(event.keyCode == 40){ //按了下箭头
                if(idx < max) idx++;
                next_word(idx);
            }else if(event.keyCode == 38){  //按了上箭头                 
                if(idx > 0) idx--;
                next_word(idx);
            }else if(event.keyCode == 13){ //按了回车
                if($("#jobnoList li.current").text()){ //如果此时有高亮候选词，选中它并搜索
                    var id = $('#jobnoList li.current').attr('name');	
                    select_word($("#jobnoList li.current").text(),id);
                }
            }
        });

        //鼠标点击推荐词被点击时
        $("body").on("click", "#jobnoList li", function(){
        	var id = $('#jobnoList li.current').attr('name');	
            select_word($(this).text(),id);
        });
        //鼠标滑过推荐词时
        $("body").on("mouseover", "#jobnoList li", function(){ 
        	if($(this).attr("id") != undefined){
	            idx = $(this).attr("id").replace(/[^0-9]/ig,""); //只取数字部分
	            next_word(idx);
        	}
        });


        /* 事件处理 END */
        $(document).bind('click',function(){
        	$('#jobnoList').hide();
        })
        /* form1 的相关操作 END*/

})(jQuery)