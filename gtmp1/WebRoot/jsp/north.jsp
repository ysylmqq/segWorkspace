<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<html>
	<head>
		<style type="text/css">
#sessionInfoDiv a {
	cursor: pointer;
	color: #2C4D79;
	text-decoration:underline;
}

#sessionInfoDiv a:hover {
	color: #149193;
	text-decoration:underline;
}
#sessionInfoDiv a:link{text-decoration:none;}
#sessionInfoDiv a:visited{text-decoration:none;}

.menubar {
	color: #2C4D79;
	cursor: pointer;
	line-height: 22px;
	text-align: center;
	font-weight: 900;
}
</style>
		<script type="text/javascript">
		//初始化菜单项
  function initMenu(){
  		$.ajax({
			   url: '${basePath}permi/user_getAccessibleModues.action',
			   async: false,
			   type : "post",
			   success : function(data) {
					var obj = eval(data);
					if(obj){
						var data =obj;
						for(var index = 0; index < data.length; index++){
						     //menubutton
							 $("#topmenu").append('<a href="javascript:void(0)" id=\''+data[index].attributes.control1+'_toobar\' class=\'menubar\'>'+data[index].text+'</a>');
							
							if(data[index].children){//子节点
							   appendChildMBtns(data[index]);
							}
							//menubutton初始化菜单
							 $('#'+data[index].attributes.control1+'_toobar').menubutton({  
						        menu:'#'+data[index].attributes.control1+'_menu'
						     });
							 
						}
					}else{
						$.messager.alert('登录信息',"无权限");
					}
					
				}
		});
}
function appendChildMBtns(menu){
   childrens = menu.children;
   var menuDiv= $('<div style="width:150px;" id=\''+menu.attributes.control1+'_menu\'></div>').appendTo('body');
    for (var i = 0; i < childrens.length; i++) {
            var cMenu = childrens[i];
            if (cMenu == '-') {
                var sep = $('<div class="menu-sep"></div>');
                menu.append(sep);
                continue;
            }
	            if (cMenu.children) {
				    /*var item = $('<div/>').append($('<span></span>').html('权限管理')).data("data", cMenu);
	       			 var ci = $('<div style="width:150px;"></div>');
	        		 var cItem = $('<div/>').html('用户管理').data("data", {text:'ss'});
	        		item.append(ci.append(cItem));*/
	        		menuDiv.append(appendChild(cMenu));
	            } else {
				    menuDiv.append('<div onclick=toPage(\''+cMenu.attributes.url+'\',\''+cMenu.attributes.control2+'\')>'+cMenu.text+'</div>');
	            }
            
        }
}
 /**
     * 递归添加子菜单
     * @param menu
     */
    function appendChild(menu) {
        var itemText = menu.text,
        children = menu.children;
        var item = $('<div/>').append($('<span></span>').html(itemText)).data("data", menu);
        
        if (menu.href)
            item.attr({href:menu.href,target:'_blank'});
        if (menu.iconCls) {
            item.attr('iconCls', menu.iconCls);
        }
        
        var ci = $('<div style="width:200px;"></div>');
        for (var i = 0; i < children.length; i++) {
            var cMenu = children[i];
            if (cMenu == '-') {
                var sep = $('<div class="menu-sep"></div>');
                menu.append(sep);
                continue;
            }
            if (cMenu.children) {
                item.append(ci.append(appendChild(cMenu)));
            } else {
                var cItem = $('<div onclick=toPage(\''+cMenu.attributes.url+'\',\''+cMenu.attributes.control2+'\') />').html(cMenu.text).data("data", cMenu);
                
                if (cMenu.href) {
                    cItem.attr({href:cMenu.href,target:'_blank'});
                }
                if (cMenu.iconCls) {
                    cItem.attr('iconCls', cMenu.iconCls);
                }
                item.append(ci.append(cItem));
            }
        }
        return item;
    }
 $(function ()
{
 //构造动态菜单
  initMenu();
});
function toPage(url,jsUrl){
 //如果操作手册页面,则新增一个窗口
 if(url.indexOf('operate_doc')>-1){
     window.open(url);
 }else{
 //只刷新中间部分
 	if($('#main')){
	   //移除之前的元素
	   $('#main').empty();
	   //移除之前弹出窗口
	   //除开north面板内的dialog id="dlg_updatepwd"
	   //$("div[class='panel window']").remove();
	 	//停止已运行的线程,目前需要停止的线程有运营监管页面、机械巡检页面从memcache刷新gps、工况、指令回应数据线程Id
       if(refreshRunGridTimeoutId){
         clearTimeout(refreshRunGridTimeoutId) ;
       }
       if(refreshRunGpsWorkOnLineTimeoutId){
           clearTimeout(refreshRunGpsWorkOnLineTimeoutId) ;
         }
       //跟踪
       if(refreshRunTrackTimeOutId){
           clearTimeout(refreshRunTrackTimeOutId) ;
         }
	    $("div[class='panel window']>div[class^='easyui-dialog'][id!='dlg_updatepwd'][id!='dlg_alarm']").remove();
	    if(url){
	       $('#main').panel('open').panel('refresh',url);
	    }
 
 }
 }
 //window.location.href=url;
}

//系统退出
function logout(){
	$.messager.confirm('确认', '确定退出系统?', function(r){
		if (r){
			$.ajax({
				url : 'loginOut.action',
				type : "GET",
				success : function(data) {
					var obj = $.parseJSON(data);
					if(obj.success){
						$.messager.alert('系统信息',obj.msg);
						top.location.href="login.jsp";
					}else{
						top.location.href="login.jsp";
					}
					
				}
			});
		}
	});
	
}
//修改密码
function openUpdatePwdWin(){
	$('#dlg_updatepwd').dialog('open');
	$('#user_updateForm').form('clear');
}
//修改密码
function updatePwd(){
	if($('#user_updateForm').form('validate')){
          	$.post("permi/user_updatePwd.action", {
          	    'userId':'${userId}',
          	    'oldPassword':$("#old_password").val(),
      			'password' : $("#newlogin_password").val()
      		}, function(data) {
      			var obj = eval(data);
      			if(obj.success){
      				$.messager.alert('系统提示', obj.msg);
      				$('#dlg_updatepwd').dialog('close');
      			}else{
      				$.messager.alert('系统提示', obj.msg);
      			}
      		}, 'json');
		}
}


function get_time()
{
  var date=new Date();
  var year="",month="",day="",week="",hour="",minute="",second="";
  year=date.getFullYear();
  month=add_zero(date.getMonth()+1);
  day=add_zero(date.getDate());
  week=date.getDay();
 switch (date.getDay()) {
 case 0:val="星期天";break
 case 1:val="星期一";break
 case 2:val="星期二";break
 case 3:val="星期三";break
 case 4:val="星期四";break
 case 5:val="星期五";break
 case 6:val="星期六";break
   }
 hour=add_zero(date.getHours());
 minute=add_zero(date.getMinutes());
 second=add_zero(date.getSeconds());
 //timetable.innerText=" "+year+"年"+month+"月"+day+"日   "+hour+":"+minute+":"+second+"  "+val;
 var systime = " "+year+"年"+month+"月"+day+"日   "+hour+":"+minute+":"+second+"  "+val;
 
 document.getElementById("time123").innerHTML=systime;
 //$('#time').val(systime);
}

function add_zero(temp)
{
 if(temp<10) return "0"+temp;
 else return temp;
}
setInterval("get_time()",1000);

function change1(){
	 var csst1 = document.getElementById('span1').style;
	 csst1.color='8C00FF';
}

function change2(){
	 var csst1 = document.getElementById('span1').style;
	 csst1.color='#E4FCEF';
}

function change3(){
	 var csst2 = document.getElementById('span2').style;
	 csst2.color='8C00FF';
}

function change4(){
	 var csst2 = document.getElementById('span2').style;
	 csst2.color='#E4FCEF';
}
  </script>
	</head>

	<body style="padding: 0px; overflow: hidden;">
<%-- 		<div style="background: #E0ECFF;">
			<img src="${pageContext.request.contextPath}/images/log.png">
		</div> --%>
		<div >
			<table cellpadding="0" cellspacing="0" width="100%">
			<tr>
			<td>
			<div style="background: #E0ECFF;" >
			<img src="${pageContext.request.contextPath}/images/top_01.png">
		    </div>
			</td>
			<%-- <td width="100%"  background="${pageContext.request.contextPath}/images/top_02.png"> --%>
			<td width="100%">
			<div  style="background:url(${pageContext.request.contextPath}/images/top_02.png);repeat-x; width:100%; height:106px;"  width="100%">
			
		    </div>
			</td>
			
			<td>
			<div style="background: #E0ECFF;">
			<img src="${pageContext.request.contextPath}/images/top_03.png">
		    </div>
			</td>
			</tr>
			</table>
		</div>
		<div id="sessionInfoDiv" style="position:absolute;right:5px; top:10px;">
		<table cellpadding="0" cellspacing="0" style="font-size: 14px;font-weight:bold;color:#E4FCEF ">
		<tr>
		<td>欢迎,&nbsp;&nbsp;</td>
		<td>
		<img src="${pageContext.request.contextPath}/images/52_05.png" >&nbsp;
		</td>
		<td>
			<span id="span_user_name">${userInfo.userName}</span>&nbsp;&nbsp;&nbsp;
			<a id="span1" onclick="openUpdatePwdWin();" style="color: #E4FCEF;text-decoration:none;" onmouseover="change1()" onmouseout="change2()">修改密码</a>
			<a id="span2" onclick="logout();" style="color:#E4FCEF;text-decoration:none;" onmouseover="change3()" onmouseout="change4()">退出系统</a>
		</td>
		</tr>
		<tr>
		<td></td>
		<td>
		<img src="${pageContext.request.contextPath}/images/52_03.png" >&nbsp;
		</td>
		<td>
		<label id="time123" ></label>&nbsp;&nbsp;&nbsp;
		</td>
		</tr>
		</table>
		
	

			
		</div>
		<div id="topmenu" style="background: #E0ECFF; width: 100%">
		</div>

       <!-- 修改密码 --begin -->
		<div id="dlg_updatepwd"  class="easyui-dialog"
			data-options="iconCls:'icon-save',closed:true,modal:true,title:'修改密码'"
			buttons="#btns_updatepwd"
			style="padding: 5px; width: 300px; height: 190px;">
			<form id="user_updateForm" method="POST">
				<table class="tableForm"
					style="widht: 100%; height: 80%; font-size: 12px;">
					<tr>
						<th widht="100px">
							原密码：
						</th>
						<td>
							<input type="password" id="old_password" name="old_password"
								class="easyui-validatebox" data-options="required:true" />
						</td>
					</tr>
					<tr>
						<th widht="100px">
							新密码：
						</th>
						<td>
							<input type="password" id="newlogin_password"
								name="newlogin_password" validType="length[6,16]"
								class="easyui-validatebox" data-options="required:true" />
						</td>
					</tr>
					<tr>
						<th>
							确认新密码：
						</th>
						<td>
							<input type="password" id="newlogin_password2"
								validType="equalTo['#newlogin_password','2个新密码不一致!']"
								class="easyui-validatebox" data-options="required:true" />
						</td>
					</tr>
				</table>
			</form>
			
			<div id="btns_updatepwd">
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok"
			onclick="updatePwd()">保存</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-cancel"
			onclick="javascript:$('#dlg_updatepwd').dialog('close')">取消</a>
	</div>
		</div>
		<!-- 修改密码 --end -->
	</body>
</html>
