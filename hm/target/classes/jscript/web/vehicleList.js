(function($){


  
	    var idx = -1; //初始化
	    //公用初始化
        $("#searchnumber").focus(); //自动获取焦点
        //任何地方被点击
        $(document).click(function(){
            $(".js_vehicleList").hide(); //隐藏推荐栏
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
        	
        	$("#vehicleList").html('');
        	idx = -1;
        	var hqkey = $('#hqkey').val();
            var params = {};
    		params.pageNo = 1;
    		params.pageSize = 10;
    		params.filter = {};
    		params.filter.plate_no = $('#searchnumber').val();
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
            			  var manager = $("#searchnumber");
            			  if(manager){
            				  if(data.items.length>0){
            					  $("#vehicleList").html('');
            					  $.each(data.items,function(k,v){
                					  $("#vehicleList").append('<li name='+v.vehicle_id+' id="rec_'+k+'">'+v.plate_no+'</li>');
                				  	 }); 
                				  $("#vehicleList").show();
            				  }
            				  if(data.items.length==0){
            					  $("#vehicleList").append('<li>没有数据！</li>');
            					  $("#vehicleList").show();
            					  document.nw_private_form.reset();
            				  }
            				  if(data.items.length==1){
            					  var item = data.items[0];
            					  select_word(item.plate_no, item.vehicle_id);
            					  $("#vehicleList").hide();
            				  }
            				}
            		  }else{
            			  $("#vehicleList").hide();
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
            $("#vehicleList li").removeClass("current");
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
        	
        	$('#name').val('');
        	
        	$('#custname').val('');
    	  	$('#cust_id').val('');
    		$('#searchcall').val('');
    	  	$('#unit_id').val('');
    		$('#vin').val('');
		  	$('#unit_id').val('');
		  	$('#scphone').val('');
		  	$('#engineno').val('');
        	
        	$('#vehicle_id').val(id);
            $("#searchnumber").val(word);  //将选择的那个词填入搜索框 
            
            $("#vehicleList").html('').hide(); //去掉推荐栏
            search(); //触发搜索
        }
 
        /* 函数封装 END */


        /* 事件处理 BEGIN */
        //点击搜索按钮时
        $("#submit").click(function(){
            search(); //触发搜索
        });    

        //关键字有变化时
        $("#searchnumber").keyup(function(event){
            //方式一：按回车时出现推荐栏
           if(!$("#vehicleList li.current").text() && event.keyCode==13){
            //方式二：按下的键是数字或字母或删除键时（即搜索框有变化时），出现推荐栏
           //  if((event.keyCode>47 && event.keyCode<106) || event.keyCode==8 || event.keyCode==46){ 
                recommend(); //触发关键词推荐，显示出推荐栏
            }
        });

        //键盘上下移动光标和按回车
        //var idx = -1; //初始化高亮项
        $("#searchnumber").keyup(function(event){
            max = $('#vehicleList li').length - 1 //一共有max个推荐词(因为从0开始计数，所以要-1)
            if(event.keyCode == 40){ //按了下箭头
                if(idx < max) idx++;
                next_word(idx);
            }else if(event.keyCode == 38){  //按了上箭头                 
                if(idx > 0) idx--;
                next_word(idx);
            }else if(event.keyCode == 13){ //按了回车
                if($("#vehicleList li.current").text()){ //如果此时有高亮候选词，选中它并搜索
                    var id = $('#vehicleList li.current').attr('name');	
                    select_word($("#vehicleList li.current").text(),id);
                }
            }
        });

        //鼠标点击推荐词被点击时
        $("body").on("click", "#vehicleList li", function(){
        	var id = $('#vehicleList li.current').attr('name');	
            select_word($(this).text(),id);
        });
        //鼠标滑过推荐词时
        $("body").on("mouseover", "#vehicleList li", function(){ 
            idx = $(this).attr("id").replace(/[^0-9]/ig,""); //只取数字部分
            next_word(idx);
        });


        /* 事件处理 END */
        $(document).bind('click',function(){
        	$('#vehicleList').hide();
        })
        /* form1 的相关操作 END*/




})(jQuery)