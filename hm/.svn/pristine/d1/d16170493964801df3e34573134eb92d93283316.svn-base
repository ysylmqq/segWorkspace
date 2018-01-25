window.gbossLocalStorage=null;
(function($) {
	var storage=localStorage;
	var maxLength=10;
	if(storage){
		try{
			new Date().format('yyyy-MM-dd hh:mm:ss');
		}catch(e){
			Date.prototype.format = function(format) {
				var o = {
					"M+" : this.getMonth() + 1,
					"d+" : this.getDate(), 
					"h+" : this.getHours(),
					"m+" : this.getMinutes(), 
					"s+" : this.getSeconds(), 
					"q+" : Math.floor((this.getMonth() + 3) / 3), 
					"S" : this.getMilliseconds()
				};
				if (/(y+)/.test(format))
					format = format.replace(RegExp.$1, (this.getFullYear() + "")
							.substr(4 - RegExp.$1.length));
				for ( var k in o)
					if (new RegExp("(" + k + ")").test(format))
						format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
								: ("00" + o[k]).substr(("" + o[k]).length));
				return format;
			}
		}
		
		/*添加item,value 为json格式的对象*/
		var addItem=function(key,value){
			if(value){
				nowTime=new Date().format('yyyy-MM-dd hh:mm:ss');
				value.stamp=nowTime;
				var oldValue=storage.getItem(key);
				if(oldValue){
					oldValue = JSON.parse(oldValue); 
					/*如果车牌号码 或者 车载终端 存在，就删除原来的，添加新的到第一条*/
					var cindex=containsKey(oldValue,value.plateNo,value.callLetter);
					if(cindex>-1){
						/*删除  */	
						if (cindex>-1) {
							oldValue.splice(cindex,1);	
						};
					}else{
						if(oldValue && oldValue.length>=maxLength){
							oldValue.pop();/*删除最后一项  */
						}
					}
					/*数组最前面加一条记录*/
					oldValue.unshift(value);
					value=oldValue;
				}else{
					var array=[];
					array.push(value);
					value=array;
				}
				var str = JSON.stringify(value);
				storage.setItem(key,str);
			}
		};
		
		/*判断 plateNo 车牌号码,callLetter 车载终端存不存在*/
		var containsKey=function(oldValue,plateNo,callLetter){
			var cindex=-1;
			if(oldValue){
				$.each(oldValue, function(index, val) {
					if((val.plateNo && plateNo && val.plateNo==plateNo) || (val.callLetter && callLetter && val.callLetter==callLetter)){
						cindex=index;
						return false;
					}
				});	
			}
			return cindex;
		};
		
		var getItems=function(key){
			var oldValue=storage.getItem(key);
			if(oldValue){
				oldValue = JSON.parse(oldValue); 
			}
			return oldValue;
		}
		
		window.gbossLocalStorage=storage;
		/*if(localStorage.__proto__==localStorage.prototype){
		}*/
		localStorage.__proto__.addItem=addItem;
		localStorage.__proto__.getItems=getItems;
	}else{
		 alert('This browser does NOT support localStorage');
	}
})(jQuery);	
