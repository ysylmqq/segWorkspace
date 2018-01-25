(function($){
	    var idx = -1; //初始化
	    //公用初始化
        $("#tel").focus(function(){
        	$("#linkPhoneList").show();
        }); //自动获取焦点
        //任何地方被点击
        $(document).click(function(){
            $(".js_linkPhoneList").hide(); //隐藏推荐栏
        });
        
        /**
         * 选择推荐词
         * @param idx 推荐词索引
         */
        function next_word(idx){
            $("#linkPhoneList li").removeClass("current");
            $("#phone_"+idx).addClass("current");
        }

        /**
         * 选定推荐词
         * @param word 推荐词
         */
        function select_word(word,id){
        	if(!id){
        		return;
        	}
        	$('#tel').val(word);
            
            $("#linkPhoneList").html('').hide(); //去掉推荐栏
        }
 
        /* 函数封装 END */   

        //关键字有变化时
        $("#tel").keyup(function(event){
            //方式一：按回车时出现推荐栏
           if(!$("#linkPhoneList li.current").text() && event.keyCode==13){
            //方式二：按下的键是数字或字母或删除键时（即搜索框有变化时），出现推荐栏
           //  if((event.keyCode>47 && event.keyCode<106) || event.keyCode==8 || event.keyCode==46){ 
        	   $("#linkPhoneList").show(); //触发关键词推荐，显示出推荐栏
            }
        });

        //键盘上下移动光标和按回车
        //var idx = -1; //初始化高亮项
        $("#tel").keyup(function(event){
            max = $('#linkPhoneList li').length - 1 //一共有max个推荐词(因为从0开始计数，所以要-1)
            if(event.keyCode == 40){ //按了下箭头
                if(idx < max) idx++;
                next_word(idx);
            }else if(event.keyCode == 38){  //按了上箭头                 
                if(idx > 0) idx--;
                next_word(idx);
            }else if(event.keyCode == 13){ //按了回车
                if($("#linkPhoneList li.current").text()){ //如果此时有高亮候选词，选中它并搜索
                    var id = $('#linkPhoneList li.current').attr('name');	
                    select_word($("#linkPhoneList li.current").text(),id);
                }
            }
        });

        //鼠标点击推荐词被点击时
        $("body").on("click", "#linkPhoneList li", function(){
        	var id = $('#linkPhoneList li.current').attr('name');	
            select_word($(this).text(),id);
        });
        //鼠标滑过推荐词时
        $("body").on("mouseover", "#linkPhoneList li", function(){ 
        	if($(this).attr("id") != undefined){
	            idx = $(this).attr("id").replace(/[^0-9]/ig,""); //只取数字部分
	            next_word(idx);
        	}
        });


        /* 事件处理 END */
        $(document).bind('click',function(){
        	$('#linkPhoneList').hide();
        })
        /* form1 的相关操作 END*/
})(jQuery)