var common_current_sm = null;
var common_current_type = null;

var common_menu = new Ext.menu.Menu({
		items: [{
			text: '踢下线',
			handler: function(){
				var sels = common_current_sm.getSelections();
				var len = sels.length;
				if(len <= 0){
					alert("请选择客户端!");
					return;
				}
				
				var max = 1000;
				if(len > max){
					alert("选择数量太多,最大" + max + ", 当前选择:" + len);
					return;
				}
				
				var info = "确定将所选择的" + (len == 1? "客户端": (len + "个客户端")) + "踢下线?";
				Ext.Msg.confirm("提示", info, function(btn){
					if(btn == "yes"){
						var info = "";
						for(var i = 0; i < len; i++){
							info += sels[i].get("address") + ";";
						}
						
						Ext.Ajax.request({
							url: "operate?type=" + common_current_type,
							method : 'post',
							params: {
								remotes: info
							},success: function(res){
								var txt = res.responseText;
								var json = Ext.decode(txt);
								if(json.success){
									var count = json.reason;
									alert("操作成功,踢下线客户端数量:" + count);
								}else{
									var info = "操作失败";
									if(json.reason){
										info += "(" + json.reason + ")";
									}
									alert(info);
								}
							},failure:function(){
								Ext.Msg.alert("提示", "连接服务器失败,请稍候再试");
							}
						});
					}
				});
			}
		}, {
			text: '取消',
			handler: function(){
			}
		}]
	});