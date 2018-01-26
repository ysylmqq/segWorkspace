Ext.BLANK_IMAGE_URL = 'js/ext-3.4.0/resources/images/default/s.gif';

var initApp = function(){
	//alert("initx");
	
	var homePagePanel = new HomePagePanel();
	new Ext.Viewport({
        layout: 'fit',
        items:[homePagePanel]
    });
};

Ext.onReady(function(){
	Ext.QuickTips.init();
	checkSession(initApp);
});

//登出
function logout(){
	Ext.Msg.confirm("提示", "确定退出系统?", function(btn){
		if(btn == 'yes'){
			Ext.Ajax.request({
				url: "login?type=logout",
				success: function(res){
					location.href = "login.html";
				},
				failure:function(){
					Ext.Msg.alert("提示", "连接服务器失败,请稍候再试");
				}
			});			
		}
	});
}