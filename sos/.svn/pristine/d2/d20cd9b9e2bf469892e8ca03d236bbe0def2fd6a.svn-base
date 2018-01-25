(function($){


  
	    var idx = -1; //初始化
        //任何地方被点击
        $(document).click(function(){
            $(".js_phoneList").hide(); //隐藏推荐栏
        });


        /* form1的相关操作 BEGIN */
        /* 函数封装 BEGIN */
        /**
         * Ajax：推荐关键词
         */
        function recommend(){
        	
        	var url='../../getCompanyPhone';
        	var url_tye = $('#url_tye').val();
        	
        	$("#phoneList").html('');
        	idx = -1;
        	var hqkey = $('#hqkey').val();
            var params = {};
    		params.pageNo = 1;
    		params.pageSize = 10;
    		params.filter = {};
    		if(url_tye == 1){
    			params.filter = {cust_type : 0};
        	}else if(url_tye == 2){
        		params.filter = {nocust_type : 0};
        	}else{
        	}
    		params.filter.phone = $('#scphone').val();
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
            			  var manager = $("#scphone");
            			  if(manager){
            				  if(data.items.length>0){
            					  $("#phoneList").html('');
            					  $.each(data.items,function(k,v){
                					  $("#phoneList").append('<li name='+v.customer_id+' id="rec_'+k+'">'+v.phone+'</li>');
                				  	 }); 
                				  $("#phoneList").show();
            				  }
            				  if(data.items.length==0){
            					  $("#phoneList").append('<li>没有数据！</li>');
            					  $("#phoneList").show();
            					  document.nw_private_form.reset();
            				  }
            				  if(data.items.length==1){
            					  var item = data.items[0];
            					  select_word(item.phone, item.customer_id);
            					  $("#phoneList").hide();
            				  }
            				}
            		  }else{
            			  $("#phoneList").hide();
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
            $("#phoneList li").removeClass("current");
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
        	
        	$('#cust_id').val(id);
            $("#scphone").val(word);  //将选择的那个词填入搜索框 
            
		  	$('#vehicle_id').val('');
		  	$('#unit_id').val('');
		  	$('#custname').val('');
			$('#searchnumber').val('');
			$('#vin').val('');
			$('#searchcall').val('');
		  	$('#engineno').val('');
            
            $("#phoneList").html('').hide(); //去掉推荐栏
            search(); //触发搜索
        }
 
        /* 函数封装 END */


        /* 事件处理 BEGIN */
        //点击搜索按钮时
        $("#submit").click(function(){
            search(); //触发搜索
        });    

        //关键字有变化时
        $("#scphone").keyup(function(event){
            //方式一：按回车时出现推荐栏
           if(!$("#phoneList li.current").text() && event.keyCode==13){
            //方式二：按下的键是数字或字母或删除键时（即搜索框有变化时），出现推荐栏
           //  if((event.keyCode>47 && event.keyCode<106) || event.keyCode==8 || event.keyCode==46){ 
                recommend(); //触发关键词推荐，显示出推荐栏
            }
        });

        //键盘上下移动光标和按回车
        //var idx = -1; //初始化高亮项
        $("#scphone").keyup(function(event){
            max = $('#phoneList li').length - 1 //一共有max个推荐词(因为从0开始计数，所以要-1)
            if(event.keyCode == 40){ //按了下箭头
                if(idx < max) idx++;
                next_word(idx);
            }else if(event.keyCode == 38){  //按了上箭头                 
                if(idx > 0) idx--;
                next_word(idx);
            }else if(event.keyCode == 13){ //按了回车
                if($("#phoneList li.current").text()){ //如果此时有高亮候选词，选中它并搜索
                    var id = $('#phoneList li.current').attr('name');	
                    select_word($("#phoneList li.current").text(),id);
                }
            }
        });

        //鼠标点击推荐词被点击时
        $("body").on("click", "#phoneList li", function(){
        	var id = $('#phoneList li.current').attr('name');	
            select_word($(this).text(),id);
        });
        //鼠标滑过推荐词时
        $("body").on("mouseover", "#phoneList li", function(){ 
        	if($(this).attr("id") != undefined){
	            idx = $(this).attr("id").replace(/[^0-9]/ig,""); //只取数字部分
	            next_word(idx);
        	}
        });


        /* 事件处理 END */
        $(document).bind('click',function(){
        	$('#phoneList').hide();
        })
        /* form1 的相关操作 END*/




})(jQuery)