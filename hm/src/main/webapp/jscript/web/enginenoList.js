(function($){


  
	    var idx = -1; //初始化
        //任何地方被点击
        $(document).click(function(){
            $(".js_enginenoList").hide(); //隐藏推荐栏
        });


        /* form1的相关操作 BEGIN */
        /* 函数封装 BEGIN */
        /**
         * Ajax：推荐关键词
         */
        function recommend(){
        	
        	var url='';
        	var url_tye = $('#url_tye').val();
        	if(url_tye == 1){
        		url = '../../getprivateVehicles';
        	}else if(url_tye == 2){
        		url = '../../getlargecustVehicles';
        	}else{
        		url = '../../getVehicles';
        	}
        	
        	$("#enginenoList").html('');
        	idx = -1;
        	var hqkey = $('#hqkey').val();
            var params = {};
    		params.pageNo = 1;
    		params.pageSize = 10;
    		params.filter = {};
    		params.filter.engine_no = $('#engineno').val();
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
            		  if(data){
            			  var manager = $("#engineno");
            			  if(manager){
            				  if(data.items.length>0){
            					  $("#enginenoList").html('');
            					  $.each(data.items,function(k,v){
                					  $("#enginenoList").append('<li name='+v.vehicle_id+' id="rec_'+k+'">'+v.engine_no+'</li>');
                				  	 }); 
                				  $("#enginenoList").show();
            				  }
            				  if(data.items.length==0){
            					  $("#enginenoList").append('<li>没有数据！</li>');
            					  $("#enginenoList").show();
            					  document.nw_private_form.reset();
            				  }
            				  if(data.items.length==1){
            					  var item = data.items[0];
            					  select_word(item.engine_no, item.vehicle_id);
            					  $("#enginenoList").hide();
            				  }
            				}
            		  }else{
            			  $("#enginenoList").hide();
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
            $("#enginenoList li").removeClass("current");
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
        	
        	$('#vehicle_id').val(id);
            $("#engineno").val(word);  //将选择的那个词填入搜索框 
            
		  	$('#cust_id').val('');
		  	$('#unit_id').val('');
		  	$('#custname').val('');
    		$('#searchcall').val('');
    		$('#searchnumber').val('');
		  	$('#scphone').val('');
		  	$('#vin').val('');
            
            $("#enginenoList").html('').hide(); //去掉推荐栏
            search(); //触发搜索
        }
 
        /* 函数封装 END */


        /* 事件处理 BEGIN */
        //点击搜索按钮时
        $("#submit").click(function(){
            search(); //触发搜索
        });    

        //关键字有变化时
        $("#engineno").keyup(function(event){
            //方式一：按回车时出现推荐栏
           if(!$("#enginenoList li.current").text() && event.keyCode==13){
            //方式二：按下的键是数字或字母或删除键时（即搜索框有变化时），出现推荐栏
           //  if((event.keyCode>47 && event.keyCode<106) || event.keyCode==8 || event.keyCode==46){ 
                recommend(); //触发关键词推荐，显示出推荐栏
            }
        });

        //键盘上下移动光标和按回车
        //var idx = -1; //初始化高亮项
        $("#engineno").keyup(function(event){
            max = $('#enginenoList li').length - 1 //一共有max个推荐词(因为从0开始计数，所以要-1)
            if(event.keyCode == 40){ //按了下箭头
                if(idx < max) idx++;
                next_word(idx);
            }else if(event.keyCode == 38){  //按了上箭头                 
                if(idx > 0) idx--;
                next_word(idx);
            }else if(event.keyCode == 13){ //按了回车
                if($("#enginenoList li.current").text()){ //如果此时有高亮候选词，选中它并搜索
                    var id = $('#enginenoList li.current').attr('name');	
                    select_word($("#enginenoList li.current").text(),id);
                }
            }
        });

        //鼠标点击推荐词被点击时
        $("body").on("click", "#enginenoList li", function(){
        	var id = $('#enginenoList li.current').attr('name');	
            select_word($(this).text(),id);
        });
        //鼠标滑过推荐词时
        $("body").on("mouseover", "#enginenoList li", function(){ 
            idx = $(this).attr("id").replace(/[^0-9]/ig,""); //只取数字部分
            next_word(idx);
        });


        /* 事件处理 END */
        $(document).bind('click',function(){
        	$('#enginenoList').hide();
        })
        /* form1 的相关操作 END*/




})(jQuery)