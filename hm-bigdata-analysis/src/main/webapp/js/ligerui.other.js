// 获取 URL 参数
function getURLParams() {
	var parms = [];
	var search = location.search;
	if (search === "")
		return parms;
	search = search.slice(1);
	var searchList = search.split('&');
	for (var i = searchList.length - 1; i >= 0; i--) {
		var kv = searchList[i].split('=');
		parms.push({
					name : kv[0],
					value : kv[1]
				});
	}
	return parms;
}

// 获取表单数据
function getFormData(formID) {
	var data = [];
	var formData = $('#' + formID).serializeArray();
	for (var i = formData.length - 1; i >= 0; i--) {
		if (formData[i]['value'] === "")
			continue;
		data.push(formData[i]);
	}
	return data;
}

// 获取搜索内容数据
function getFormDataAll(formID) {
	var formData = $('#' + formID).serializeArray();
	var data = {};
	for (var i = formData.length - 1; i >= 0; i--) {
		data[formData[i]['name']] = formData[i]['value'];
	}
	return data;
}
// 获取form ajax data
function getFormAjaxData(formID) {
	var data = {};
	var formData = $('#' + formID).serializeArray();
	for (var i = formData.length - 1; i >= 0; i--) {
		if (formData[i]['value'] === "")
			continue;
		data[formData[i]['name']] = formData[i]['value'];
	}
	return data;
}

// 对表单进行数据注入
function injectDataToForm(formID, data) {
	var form = $('#' + formID);

	for (var key in data) {
		if (!data.hasOwnProperty(key))
			continue;
		var input = form.find('[name=' + key + ']');
		if (input.length <= 0)
			continue;
		if (input[0]['type'] === 'radio')
			return input.filter('[value=' + data[key] + ']').prop('checked',
					true);
		input.prop('value', data[key]);
	}
}

// 清空表单数据
function clearForm(formID, ignoreList) {
	if (typeof ignoreList !== 'object')
		ignoreList = [];
	var inputs = $('#' + formID).find('input,textarea').not('[type="radio"],[type="checkbox"]');
	inputs.each(function(index) {
				if (ignoreList[this.name] === undefined)
					this.value = '';
			})
}

~function() {
	var popWin = [];
	window['openDialog'] = function(dialogID, parms, callback) {
		if (popWin[dialogID] === undefined) {
			popWin[dialogID] = $.ligerDialog($.extend(true, {
						width : 500,
						allowClose : true,
						showMax : true,
						showToggle : true,
						showMin : false,
						slide : false,
						isResize : false,
						// title:title,
						// buttons:buttons,
						target : $('#' + dialogID)
					}, parms));
		}
		if (callback)
			callback(popWin[dialogID]);
		popWin[dialogID].show();
	};
	window['closeDialog'] = function(dialogID) {
	//	popWin[dialogID].hidden();
		 try{
			 popWin[dialogID].hidden();
         }catch(err){
         	//console.log("hidden这个没有定义，没有影响功能！");
         } 
		
	};
	window['getDialog'] = function(dialogID){
		return popWin[dialogID];
	};
}();

// 初始化表格
function initMainGrid(gridID, parms, cols) {
	var ml = $("#" + gridID);
	if (!ml)
		ml = $('<div class="grid" id="' + gridID + '"></div>');
	var rp_value = $(".l-dialog select[name='rp']").val();
	var optGrid = {
		height : '100%',
		columns : [],
		parms : [],
		url : false,
		data : null,
		pageSize :rp_value,
		page : 1,
		rownumbers : true,
		fixedCellHeight : true,
		enabledSort : false,
		frozenRownumbers : false,
		root : 'list',
		record : 'count',
		dataAction : 'server', // 服务器排序
		usePager : true, // 服务器分页
        headerRowHeight:50,
        rowHeight:36,
        pageSizeOptions: [5, 10, 15, 20],
        onSuccess : function(data) {
        	if(data.success==false){
            	 $.ligerDialog.warn(data.message);
            }
            if(data.datas==null){
            	return;
            }else{
            	data.list = data.datas.list;
				data.count = data.datas.count;
            }
		},
    	onLoaded:function(grid){
        	setTimeout(function(){
            	$('.l-grid-loading').hide();
            	$(".l-grid-row-cell-inner").each(function(){
                	var that=$(this);that.attr('title',that.text());
            	});
        	},200);
    	}
	};

	var columns = [];
	for (var i = 0, d = cols.length; i < d; i++) {
		if (!cols[i] || cols[i]['nogrid'])
			continue;
		var k = cols[i];
		var optCol = {};
		optCol['display'] = k['show'];
		optCol['name'] = k['field'];
		optCol['align'] = 'left';
		optCol['width'] = 90;
		if (k['width'] != undefined)
			optCol['width'] = k['width'];
		if (k['align'] != undefined)
			optCol['align'] = k['align'];
		if (k['hide'] != undefined)
			optCol['hide'] = k['hide'];
		if (k['id'] != undefined)
			optCol['id'] = k['id'];
		if (k['type'] != undefined)
			optCol['type'] = k['type'];
		if (k['format'] != undefined)
			optCol['format'] = k['format'];
		if (k['editor'] != undefined)
			optCol['editor'] = k['editor'];
		if (k['data'] != undefined)
			optCol['data'] = k['data'];
		if (k['onChangeDate'] != undefined)
			optCol['onChangeDate'] = k['onChangeDate'];
		if (k['totalSummary'])
			optCol['totalSummary'] = k['totalSummary'];
		if (k['render'] != undefined) {
			optCol['render'] = str2func(k['render']);
		};

		// else if(k['func']!=undefined){
		// var zzz=str2func(k['func']);
		// optCol['render']=function(r,ii,v){
		// return zzz(v,ii,r);
		// };
		// }
		columns.push(optCol);
	}
	for (var k in parms) {
		optGrid[k] = parms[k];
	}
	optGrid['columns'] = columns;
	return ml.ligerGrid(optGrid);
}

// 字符串转函数
function str2func(str) {
	if (typeof(str) == 'function')
		return str;
	eval("var zzz=" + str + "");
	return zzz;
}
// 渲染表格
function renderViewTable(data, tpl, key) {
	if (!data || !tpl)
		return '';
	var str = '';
	str += '<table class="table table-bordered" style="width:98%;"><tr>';

	var j = 0;
	for (var i = 0, d = tpl.length; i < d; i++) {
		var width = '', content = '', h = 0;

		var k = tpl[i]['field'];
		if (data[k] != undefined) {
			content = data[k];
		} else {
			content = '&nbsp;';
		}
		var idstr = ' id="vtd_' + k + '"';
		if (key) {
			idstr = ' id="' + key + '_vtd_' + k + '"';
		}

		var span = '', spanval = 0;
		if (tpl[i]['span'] != undefined) {
			spanval = tpl[i]['span'];
			span = ' colspan="3"';
			j += 4;
			h = 4;
		} else {
			if (i == 0)
				width = ' width="34%"';
			j += 2;
			h = 2;
		}
		if (tpl[i]['width'] != undefined) {
			width = ' width="' + tpl[i]['width'] + '"';
		}
		if (tpl[i]['tpl'] != undefined) {
			zzz = tpl[i]['tpl'];
			if (zzz != '')
				content = renderViewSubTpl(data, zzz);
		}
		if (tpl[i]['container'] != undefined) {
			zzz = tpl[i]['container'];
			content = zzz.replace('{{0}}', content);
		}
		if (tpl[i]['func'] != undefined) {
			var zzz = str2func(tpl[i]['func']);
			content = zzz(content, data);
		}

		var nowtd = '<th>' + tpl[i]['show'] + '：</th><td' + width + idstr
				+ span + '>' + content + '</td>';

		if (spanval > 0) {
			var len = str.length;
			laststr = str.substr(len - 5);
			if (laststr == '</td>') {
				nowtd = '<th>&nbsp;</td><td>&nbsp;</td></tr><tr>' + nowtd + '';
				j += 2;
			}
			nowtd += '</tr><tr>';
		}

		str += nowtd;

		if ((i + 1) == d && j % 4 != 0) {
			str += '<td colspan="2">&nbsp;</td>';
			break;
		}

		if (j % 4 == 0) {
			str += '</tr><tr>';
		}

	}
	str += '</tr></table>';
	return str;
}

//生成表头
function newTab(url, text, newwin) {
	if (newwin == '1') {
		window.open(url);
		return false;
	}
	var tid = url.replace('./', '').replace(/\./g, '').replace(/\:/g, '_1_')
			.replace(/[#;]/g, '').replace(/\&/g, '_1_');
	tid = tid.replace(/[\/\.\?\=]/g, '_0_');
	if (window.self == window.parent) {
		if (window.SiteTab == undefined) {
			window.open(url);
			return false;
		} else {
			SiteTab.addTab(tid, text, url);
		}
	} else {
		if (window.parent.SiteTab != undefined) {
			if (text == undefined)
				text = '新标签页';
			window.parent.SiteTab.addTab(tid, text, url);
		}
	}
}
//刷新按钮
function clearSearch() {
    clearForm('searchForm');
    var formParam = getFormDataAll('searchForm');
    for(var k in formParam){
           mainGrid.removeParm(k);
   }
   changeURL(setParms);
    // $("#main-grid").ligerGetGridManager().loadServerData([{
    //     name: 'page',
    //     value: 1
    // },
    // {
    //     name: 'pagesize',
    //     value: 20
    // },
    // ]);
}

//定时任务
function  TimerTask(opt){
	var options = $.extend(true,this,{
		tick: 10000,
		timer: null,
		task:[],
		onError:null
	},opt);
}
TimerTask.prototype.run = function(){
	var that = this;
	clearTimeout(that.timer);
	function handler(){
		var task = that.task;
		if(typeof(task) === 'function') task(that);
		if(typeof(task) === 'object' && task.length !== undefined){
			for(var i=0,length = task.length;i<length;i++){
				if(typeof(task[i]) === 'function') task[i](that);
			}
		}
		that.timer = setTimeout(handler,that.tick);
	}
	handler();
};
TimerTask.prototype.stop = function(){
	clearTimeout(this.timer);
};

//搜索
function saveSearch(){
    var searchData={};
    var formParam = getFormDataAll('searchForm');
    for(var k in formParam){
       var newdata;
       newdata=$.trim(formParam[k]);
       if(newdata!=''){
           searchData[k]=newdata;
           mainGrid.setParm(k,newdata);
       }else{
           mainGrid.removeParm(k);
           delete formParam[k];
       }
   }
    formParam.page = 1;
    var rp_value = $("select[name='rp']").val();
    formParam.pagesize = rp_value;
   $("#main-grid").ligerGetGridManager().loadServerData(formParam);
}

function hasParentNode(){
	//该节点有父节点，不为根节点时
	$(newdata.datas.list).each(function() {
	    var leaf={text:this.text,delay:{url:'../ twgAgency/queryTreeNodeList'+'?&nodeId='+this.id},params : this};
	    if(this.isLeaf){
	       delete leaf.delay;
	     }
	     menuData.push(leaf);
	})
    if (menuData.length <= 0)return;
    this.append(this._parentNode, menuData);
    if(parentData) delete parentData.delay;
}