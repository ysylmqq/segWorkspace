<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%  
	response.setHeader("Cache-Control","no-cache");
	response.setHeader("Pragma","no-cache");
	response.setDateHeader ("Expires", 0);
    String path = request.getContextPath();  
    String basePath = request.getScheme() + "://"  
            + request.getServerName() + ":" + request.getServerPort()  
            + path + "/";  
%>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta charset="UTF-8">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="pragma" content="no-cache"> 
<meta http-equiv="cache-control" content="no-cache"> 
<meta http-equiv="expires" content="0">
<title>欢迎来到海马大数据分析平台</title>
<link rel="stylesheet" href="./vendor/bootstrap/dist/css/bootstrap.css"/>
<link rel="stylesheet" href="./vendor/ligerUI/skins/Aqua/css/ligerui-all.css"/>
<link rel="stylesheet" href="./css/public/ligerui-custom.css"/>
<link rel="stylesheet" href="./css/public/page_index.css"/>
<style>
	.main-menu .icon-menu1 {
	display: inline-block;
	vertical-align: middle;
	margin-right: 20px;
	width: 30px;
	height: 30px;
	background: url("./images/nav.png") no-repeat 0 -155px;
}

.main-menu .icon-menu2 {
	display: inline-block;
	vertical-align: middle;
	margin-right: 20px;
	width: 30px;
	height: 22px;
	background: url("./images/nav.png") no-repeat 0 -67px;
}

.main-menu .icon-menu3 {
	display: inline-block;
	vertical-align: middle;
	margin-right: 20px;
	width: 30px;
	height: 22px;
	background: url("./images/nav.png") no-repeat 0 -46px;
}

.main-menu .icon-menu4 {
	display: inline-block;
	vertical-align: middle;
	margin-right: 20px;
	width: 30px;
	height: 22px;
	background: url("./images/nav.png") no-repeat 0 -111px;
}

.main-menu .icon-menu5 {
	display: inline-block;
	vertical-align: middle;
	margin-right: 20px;
	width: 30px;
	height: 30px;
	background: url("./images/nav.png") no-repeat 0 6px;
}

.main-menu .icon-menu6 {
	display: inline-block;
	vertical-align: middle;
	margin-right: 20px;
	width: 30px;
	height: 22px;
	background: url("./images/nav.png") no-repeat 0 -24px;
}

.main-menu .icon-menu7 {
	display: inline-block;
	vertical-align: middle;
	margin-right: 20px;
	width: 30px;
	height: 22px;
	background: url("./images/nav.png") no-repeat 0 -89px;
}

.main-menu .icon-menu8 {
	display: inline-block;
	vertical-align: middle;
	margin-right: 20px;
	width: 30px;
	height: 21px;
	background: url("./images/nav.png") no-repeat 0 -134px;
}
.site-header .infos {
	margin-top:45px;
}
.hmlogo{
	width: 230px;
    border: 1px solid #8c7171;
    height: 100%;
    position: absolute;
    left: 0;
    top: 0;
    background: url("./images/hmlogo.png") no-repeat;
}
.menu_parent {
    position: absolute;
    width: 1px;
    float: left;
    height: 100%;
}
.menu_son {
    position: absolute;
    width: 30px;
    height: 80px;
    left: 225px;
    top: 50%;
    margin-top: -80px;
    cursor: pointer;
    z-index: 10001;
    background: url(./images/tl.png) no-repeat center;
}
.menu_son2 {
    position: absolute;
    width: 30px;
    height: 80px;
    left: -2px;
    top: 50%;
    margin-top: -80px;
    cursor: pointer;
    z-index: 10001;
    background: url(./images/tr.png) no-repeat center;
}
</style>
</head>
<body>
	
    <div class="site-header">
    	<div class="hmlogo">
    		<!-- <a>海马大数据平台</a> -->
    	</div>
    	<ul class="infos">
    		<li>欢迎你！</li>
    		<li><i class="naviconfont">&#xe60d;</i> <span id="username">海马管理员</span></li>
    		<li id ="logout"><a href="javaScript:void(0)"><i class="naviconfont">&#xe609;</i> 退出</a></li>
    	</ul>
    </div>
    <script type="text/javascript" src="js/jquery-2.0.3.min.js"></script>
    
    
    <div class="site-main">
    	<div class="menu_parent">
    		<div class="menu_son" id="winshoushuo"></div>
        </div>
        <ul class="main-menu">
        	
        </ul>
        <div class="contents">
            <div class="site-tab l-tab-custom">
                <div title="首页" tabid="home">
                    <iframe src="./htmls/index.html"></iframe> 
                </div>
            </div>
        </div>
    </div>
    <script type="text/javascript" src="./js/jquery-2.0.3.min.js"></script>
    <script src="./vendor/ligerUI/js/ligerui.all.js"></script>
     <script src="./js/ligerui.other.js"></script>
    <script src="./js/index.js"></script>
    <script src="./js/DataToView.js"></script>
        <script>  
    	$(function(){
    		$.ajax({
    			type : "post",
    			async : false,
    			contentType : "application/json",
    			dataType : 'json',
    			url : './getCurrentCompanys',
    			data : JSON.stringify({}),
    			success : function(data) {
    				if (data) {
    					$.ajax({
							type : 'post',
							async : false,
							contentType : 'application/json',
							dataType : 'json',
							url : 'setSessionCompany',
							data : JSON.stringify({parameter : 2}),
							success : function(data) {
							    document.cookie ="companyno =" + 2;
							},
							error : function(res, error) {
							}
						});
    				}
    			}
    		});
    	 	$.ajax({
    			url:'./getCurrentOperator',
    			method:"POST",
    			asysnc:false,
    			success:function(data){
    	      		  $("#username").text(data.loginname);
			    		getMenu();
    			}
    		}); 
    	});
    	
    	function getMenu(){
    		//2.获取菜单;
    		$.ajax({
    			url:'./moduleList',
    			method:"POST",
    			/* dataType:"json", */
    			asysnc:false,
    			success:function(resp){
    				if(resp.hasOwnProperty("datas")){
	    				displayMenu(resp);
    				}else{
    				}
    			},
    			error: function(XMLHttpRequest, textStatus, errorThrown) {
    			}
    		});
    	}
    	
    	function displayMenu (resp){
			//1.动态加载菜单项
			var main_menu = resp.datas.main_menu_items;
			var mainItems = new Array();
			var fItems = new Array();    				
			//2.将菜单按照级别以及层级划分
			$.each(main_menu,function(i,n){
				var mainData = main_menu[i];
				if(mainData.categoryid == 1){
					if(mainData.parentid == 0){//一级菜单
						fItems.push(mainData);
					}
				}
			});
			//排序
			fItems.sort(function(a,b){
				return a.orderid - b.orderid;
			});
			$.each(fItems,function(i,n){
				var mianItem ={};
				var sItemArr = new Array();
				$.each(main_menu,function(j,k){
					if(main_menu[j].categoryid == 1){
						if(main_menu[j].parentid == fItems[i].permissionid){//查找一级菜单的子菜单
							sItemArr.push(main_menu[j]);
							
						}
					}
				});
				sItemArr.sort(function(a,b){
					return a.orderid - b.orderid;
				});
				mianItem.fItem = fItems[i];
				mianItem.sItemArr = sItemArr;
				mainItems.push(mianItem);
			});
			//菜单结构：｛fitem,sItemArr[]｝
			//将菜单写到全局变量中
			//按照获取的菜单绘制界面
			makeindexMenu(mainItems);
			//控制点击菜单之后的动作
			menuAction();
    	};
    	//按照菜单组装页面
    	function makeindexMenu(menu){
    		if(menu == null){
    			alert('菜单为空,登录失败!');
    			location.href="../htmls/index.html";
    		}
    		var mainUL = $('.main-menu');//main-menu的 ul
    		$.each(menu,function(i,n){
    			var fItem = menu[i].fItem;
    			var sItemArr = menu[i].sItemArr;
    			
    			//1.拼接一级菜单
    			var firstLi = '';
    			if(fItem.havsubitems == 0){//没有子菜单
					if(fItem.itemname == 'IndexPageMenu'){//是首页则添加active类
						firstLi = '<li class="main-item active"><a class="main-link" onclick="SiteTab.newTab('+'\''+fItem.url+'\''+','+'\''+fItem.description+'\''+',0,true)"><i class="'+fItem.iconurl+'"></i>'+fItem.description+'</a></li>';
    				}else{
    					firstLi = '<li class="main-item"><a class="main-link" onclick="SiteTab.newTab('+'\''+fItem.url+'\''+','+'\''+fItem.description+'\''+',0,true)"><i class="'+fItem.iconurl+'"></i>'+fItem.description+'</a></li>';	
    				}
					mainUL.append(firstLi);
    			}else{//有子菜单,拼接2级菜单
    				firstLi = '<li class="main-item" id="'+ fItem.itemname+'"><a class="main-link"><i class="'+fItem.iconurl+'"></i>'+fItem.description+'</a><ul class="sub-menu"></ul></li>';
    				mainUL.append(firstLi);
    				//拼接子菜单
					var mainSonUL = $('.main-item');
    				var subMenu = $('#'+fItem.itemname).find('.sub-menu');
					$.each(sItemArr,function(i,n){
						var subItem = '<li><a onclick="SiteTab.newTab('+'\''+sItemArr[i].url+'\''+','+'\''+sItemArr[i].description+'\''+',0,true)"><span>'+sItemArr[i].description+'</span></a></li>';
						subMenu.append(subItem);
					});
    			}
    		});
    	}
    	//菜单动作样式
    	function menuAction(){
    		var mainMenu = $('.main-menu');
		    var mainItems = mainMenu.find('.main-item');
		    var subItems = mainMenu.find('.sub-menu > li');
		    
		    mainMenu.on('click','.main-link', function(e){
		       var mainItem = $(this).parent();
		        mainItems.removeClass('active').removeClass('selected').find('.sub-menu > li').removeClass('active');
		        var that = $(this).parent();
		        if(mainItem.find('.sub-menu').length <= 0){
		            mainItem.addClass('selected');
		            mainItem.siblings().removeClass("wnf_active");
		        }else{
		            //mainItem.addClass('active');
		            if(that.hasClass("wnf_active")){
		            	that.removeClass("wnf_active");
		            }else{
		            	that.addClass("wnf_active").siblings().removeClass("wnf_active");
		            }		            
		        }
		    });
		    
		    mainMenu.on('click','.sub-menu a', function(e){
		       var subItem = $(this).parent();
		        subItems.removeClass('selected');
		        subItem.addClass('selected');
		    });
    	}
    </script>
    <script type="text/javascript">
    $(function(){
    	$("#logout").click(function(){
            location.href = "logout";
        });
    	
    <%-- 	$.post("<%=basePath%>moduleList",{},function(data){
    		console.log("data ",data);
    	}); --%>
    	
         $(".menu_parent").css("height",$(".menu_parent").height()-80+"px");	
		 $("#winshoushuo").click(function(){
			 if($(this).hasClass("menu_son")){
				   $(this).css("left","-2px").parent().siblings(".main-menu").css("width","0px");
				   $(this).addClass("menu_son2").removeClass("menu_son");
				   $(this).parent().siblings(".contents").css("marginLeft","4px");
				   $(this).parent().css("position","static");
			 }else{
				   $(this).css("left","225px").parent().siblings(".main-menu").css("width","230px");
 				   $(this).addClass("menu_son").removeClass("menu_son2");
 				   $(this).parent().siblings(".contents").css("marginLeft","230px");
 				   $(this).parent().css("position","absolute");
			 }
		   
	   	});
    });
    </script> 
</body>
</html>