;(function($){
    var showList = function(input){//param:jQuery当前搜索框
        var keyword = $.trim(input.val());//获取input内容
        if(keyword=='') {
            return false;
        }
        var recommendlist = input.siblings(".reports_search_1_list");//下拉选
        var idx = -1; //初始化高亮项
        var max = 0;
        //准备参数
        api_url = '../app/data/vehicleinfo'; 
        params = {param:keyword,count:5}
        //开始调用接口
        $.getJSON(api_url, params, function(result){
            if(result && result.success==true){
                if(result.info.length==1 && (result.info[0].plate_no==keyword || result.info[0].call_letter==keyword)){
                    input.val(result.info[0].plate_no+" "+result.info[0].call_letter);//填入搜索框
                    input.siblings(".search_plate_no").val(result.info[0].plate_no);//写入车牌
                    input.siblings(".search_call_letter").val(result.info[0].call_letter);//写入车载
                    input.siblings(".search_vehicle_id").val(result.info[0].vehicle_id);//写入车辆id
                    input.siblings(".reports_search_1_list").hide();//下拉列表隐藏
                    return false;
                }
                idx = -1; 
                recommendlist.html('');
                $.each(result.info, function(i,item){
                    recommendlist.append('<li id="pla_rec'+i+'"><span class="p_plate_no">'+item.plate_no+'</span> <span class="p_call_letter">'+item.call_letter+'</span><span class="hide p_vehicle_id">'+item.vehicle_id+'</span></li>');
                });
                recommendlist.show();

                input.keyup(function(event){

                    max = recommendlist.find("li").length - 1 //一共有max个推荐词(因为从0开始计数，所以要-1)
                    // alert("keyup事件:"+"max"+max+"。idx"+idx);
                    if(event.keyCode == 40){ //按了下箭头
                        if(idx < max) idx++;
                        next_word1(idx,recommendlist);
                    }else if(event.keyCode == 38){  //按了上箭头                 
                        if(idx > 0) idx--;
                        next_word1(idx,recommendlist);
                    }else if(event.keyCode == 13){ //按了回车
                        if(recommendlist.find("li.current").text()){ //如果此时有高亮候选词，选中它并搜索
                            select_word1(recommendlist.find("li.current"),input);
                        }
                    }
                });

                //鼠标点击推荐词被点击时
                recommendlist.find("li").on("click",function(){
                    select_word1($(this),input);
                }).on("mouseover", function(){ //鼠标滑过推荐词时
                    // alert("鼠标滑过推荐词:"+$(this).attr("id"));
                    idx = $(this).attr("id").replace(/[^0-9]/ig,""); //只取数字部分
                    next_word1(idx,recommendlist);
                });

            }else{
                recommendlist.hide();
            }
        });

        $(document).click(function(){
            recommendlist.hide(); //隐藏推荐栏
        });
    };

    var next_word1 = function(idx,objlist){//序号、下拉选对象
        // alert("执行next_word1()。idx="+idx+"。objlist.find('li')"+objlist.find('li').length)
        objlist.find("li").removeClass("current");
        $("#pla_rec"+idx,objlist).addClass("current");
    }

    var select_word1 = function(word_li,inputbox){
        var c_pla = word_li.find(".p_plate_no").html();//车牌
        var c_latter = word_li.find(".p_call_letter").html();//车载号码
        var c_id = word_li.find(".p_vehicle_id").html();//车辆id
        inputbox.val(c_pla+" "+c_latter);//将选择的那个词填入搜索框
        inputbox.siblings(".search_plate_no").val(c_pla);//写入车牌
        inputbox.siblings(".search_call_letter").val(c_latter);//写入车载
        inputbox.siblings(".search_vehicle_id").val(c_id);//写入车辆id
        inputbox.siblings(".reports_search_1_list").html('').hide();     //去掉推荐栏
    }

    var reports_recommend4 = function(input){//查询分公司,param:input
        var recomlist = input.siblings(".reports_search_4_list");//下拉框
        recomlist.html('');
        //准备参数
        api_url = '../app/user'; //推荐候选词API（常规情况测试）
        params = {}//参数无
        //开始调用接口
        $.getJSON(api_url, params, function(result){
            if(result && result.success==true){
                recomlist.append('<li id="fil_rec0"><span class="p_filiale_name">全部</span><span class="hide p_filiale_id">0</span></li>');
                $.each(result.user.company, function(i,item){
                    recomlist.append('<li id="fil_rec'+(i+1)+'"><span class="p_filiale_name">'+item.companyname+'</span><span class="hide p_filiale_id">'+item.companyno+'</span></li>');
                });
                recomlist.show();

                //鼠标点击推荐词被点击时
                recomlist.find("li").on("click",function(){
                    select_word4($(this),input);
                }).on("mouseover", function(){ //鼠标滑过推荐词时
                    var idx = $(this).attr("id").replace(/[^0-9]/ig,""); //只取数字部分
                    next_word4(idx,recomlist);
                });

            }else{
                recomlist.hide();
            }
        });

        $(document).click(function(){
            recomlist.hide(); //隐藏推荐栏
        });

    }

    var next_word4 = function(idx,objlist){
        objlist.find("li").removeClass("current");
        $("#fil_rec"+idx,objlist).addClass("current");
    }

    var select_word4 = function(word_li,inputbox){
        var filiale_name = word_li.find(".p_filiale_name").html();//获取公司名
        var filiale_id = word_li.find(".p_filiale_id").html();//获取公司id
        inputbox.val(filiale_name);//将选择的那个词填入搜索框
        inputbox.siblings(".search_filiale_id").val(filiale_id);
        inputbox.siblings(".reports_search_4_list").hide();     //去掉推荐栏
    }

    $.fn.extend({
        "bindSearch_Pla":function(){
            // alert("bindRecommendEvent");
            var serchBox = $(this).find(".reports_search_1_engine");//获取当前的输入框
            // var id = $(this).attr("id");
            // var serchBox = $(this).find("." + id +"_engine");//获取当前的输入框
            serchBox.on("input", function(){
                // console.log(serchBox);
                serchBox.siblings(".search_plate_no").val('');
                serchBox.siblings(".search_call_letter").val('');
                serchBox.siblings(".search_vehicle_id").val('');
                showList(serchBox);
            });
        },
        "bindSearch_Company":function(){
            var DropdownBtn = $(this).find(".filialeDown");//下拉按钮
            var inputbox = $(this).find(".reports_search_4_engine");//输入框
            DropdownBtn.on("click", function(){
                reports_recommend4(inputbox);//查询分公司
                // DropdownList.toggle();
            });
        }
    });
})(jQuery)

function ReDate(){
    //当前系统时间
    var NowTime = new Date();
    var NowTimeM;
    var NowMonth;
    var NowDate;
    var NowHours;
    var NowMinutes;
    var NowSeconds;

    //月
    if ((NowTime.getMonth()+1)<10){
        NowMonth = '0'+(NowTime.getMonth()+1);
    }else{
        NowMonth =NowTime.getMonth()+1;
    };
    //日
    if (NowTime.getDate()<10){
        NowDate = '0'+NowTime.getDate();
    }else{
        NowDate =NowTime.getDate();
    };
    //时
    if (NowTime.getHours()<10){
        NowHours = '0'+NowTime.getHours();
    }else{
        NowHours =NowTime.getHours();
    };
    //分
    if (NowTime.getMinutes()<10){
        NowMinutes = '0'+NowTime.getMinutes();
    }else{
        NowMinutes = NowTime.getMinutes();
    };
    //秒
    if (NowTime.getSeconds()<10){
        NowSeconds = '0'+NowTime.getSeconds();
    }else{
        NowSeconds = NowTime.getSeconds();
    };

    NowTime =NowTime.getFullYear()+ "-"+NowMonth+"-"+NowDate+" "+NowHours+":"+NowMinutes+":"+NowSeconds; 
    // NowTimeM = NowTime.substr(0,10)+" 00:00:00";
    return NowTime;
}

function OnlyDate(){
    //当前系统时间
    var NowTime = new Date();
    var NowTimeM;
    var NowMonth;
    var NowDate;

    //月
    if ((NowTime.getMonth()+1)<10){
        NowMonth = '0'+(NowTime.getMonth()+1);
    }else{
        NowMonth =NowTime.getMonth()+1;
    };
    //日
    if (NowTime.getDate()<10){
        NowDate = '0'+NowTime.getDate();
    }else{
        NowDate =NowTime.getDate();
    };

    NowTime =NowTime.getFullYear()+ "-"+NowMonth+"-"+NowDate; 
    return NowTime;
}