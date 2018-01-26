function checkSession(success_callback, fail_callback){
	
	
	Ext.Ajax.request({
		url: "login?type=checkSession",
		method: "post",
		success: function(res){
			var txt = res.responseText;
			if(!txt){
				alert("服务器内部错误,请稍后再试!");
				return;
			}
			//alert(txt);
			
			var json = Ext.decode(txt);
			if(!json.success){
				if(fail_callback){
					fail_callback();
				}else{
					location.href = "login.html";
				}
			}else{
				success_callback();
			}
		}
	});
}