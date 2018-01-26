var phonebarUtil = {
	phonebar: null,
	
	queue: null,
	
	centerTel: "952100",
	
	getAgentByUserOID: function(userOID){
		var pbar = this.phonebar;
		var agentList = pbar.AgentList;
		
		for(var i = 0; i < agentList.count; i++){
			var agent;
			try{
				agent = agentList.Agents(i);
			}catch (e) {
				//数组越界(线程同步问题)
				return null;
			}
			
			var rowId = agent.UserOID;		
			if(rowId == userOID){
				return agent;
			}
		}
		
		return null;
	},
	
	/**
	 * 从联系方式中获取电话号码
	 * 15817202700@192.110.2.17
	 * 或
	 * PSTN:15817202700
	 */
	getTelFromContact: function(contact){
		var idx1 = contact.indexOf('@');
		if(idx1 != -1){
			return contact.substring(0, idx1);
		}
		
		var idx2 = contact.indexOf(':');
		if(idx2 != -1){
			return this.rclearrn(contact.substring(idx2 + 1));
		}
		
		return contact;
	},
	
	/**
	 * 去掉末尾\r和\n
	 */
	indexOfNoRN: function (str){
		for(var i = str.length - 1; i >= 0; i--){
			if(str.charAt(i) != '\n' && str.charAt(i) != '\r'){
				return i;
			}
		}
		
		return -1;
	},

	rclearrn: function(str){
		var index = this.indexOfNoRN(str);
		return str.substring(0, index + 1);
	},
	
	/**
	 * 从context提取所要字段(\r\n分隔)
	 */
	getParamFromContext: function(context, key){
		var startFlag = "@" + key + "=";
		var vs = context.split("\r\n");
		for(var i = 0; i < vs.length; i++){
			var vsi = vs[i];
			if(vsi.indexOf(startFlag) == 0){
				return vsi.substring(startFlag.length);
			}
		}
		
		return null;
	},
	
	/**
	 * 从context提取所要字段(\n分隔)
	 */
	getParamFromContext2: function(context, key){
		var startFlag = "@" + key + "=";
		var vs = context.split("\n");
		for(var i = 0; i < vs.length; i++){
			var vsi = vs[i];
			if(vsi.indexOf(startFlag) == 0){
				return vsi.substring(startFlag.length);
			}
		}
		
		return null;
	},
	
	/**
	 * 从contacts中提取联系方式
	 */
	 getContacts: function(contacts){
		var vs = contacts.split("\r\n");
		for(var i = 0; i < vs.length; i++){
			var vsi = vs[i];
			if(vsi.indexOf("PSTN") != -1){
				return vsi;
			}
		}
		
		return contacts;
	},
	
	/**
	 * 技能Id转中文
	 */
	 parseGroupCN: function(pbar, group){
		var gs = group.split("\r\n");
		var cns = "";
		for(var i = 0; i < gs.length; i++){
			var gsi = gs[i];
			if(gsi.length == 0){
				continue;
			}
			
			var cn = pbar.BTChannel.PhoneBarConfiguration.AgentGroupNames(gsi);
			cns += cn + ";";
		}
		
		if(cns.length > 0){
			cns = cns.substring(0, cns.length - 1);
		}
		
		return cns;
	},
	
	/**
	 * 状态码转中文
	 */
	 getStatusName: function(statusCode){
		if(!(statusCode & -1)){
			return "就绪";
		}
			
		if(statusCode & 0x0800){
			return "锁定";
		}
			
		if(statusCode & 0x0004){
			return "正在振铃";
		}
			
		if(statusCode & 0x0020){
			return "监听";
		}
			
		if(statusCode & 0x0010){
			return "接待客户";
		}
			
		if(statusCode & 0x0008){
			return "正在通话";
		}
			
		if(statusCode & 0x0040){
			return "保持";
		}
			
		if(statusCode & 0x0100){
			return "事后处理";
		}
			
		if(statusCode & 0x0200){
			return "请勿打扰";
		}
			
		if(statusCode & 0x0400){
			return "小休";
		}
			
		if(statusCode & 0x0001){
			return "忙";
		}
			
		return "[" + statusCode + "]";
	},
	
	/**
	 * 获取可用菜单
	 * 1:代答
	 * 2:监听
	 * 3:插话
	 * 
	 * 4.解锁  5:锁定
	 * 6:强制签退
	 * 7:允许小憩
	 * 
	 * flag_lock 0:显示解锁 1:显示锁定 
	 */
	getAllSeatMenuStatus: function(agentId, agentStatusCode){
		var pbar = phonebarUtil.phonebar;
		var selfId = pbar.UserID;
		if(selfId == agentId){
			//自己
			return {flag_lock: 1, status: [0, 0, 0, 0, 0, 0]};
		}
		
		if(!(agentStatusCode & -1))			
			//就绪
			return {flag_lock: 1, status: [0, 0, 0, 1, 1, 1]};
		if(agentStatusCode & 0x0800)		
			//锁定
			return {flag_lock: 0, status: [0, 0, 0, 1, 1, 1]};
		if(agentStatusCode & 0x0004)		
			//正在振铃
			return {flag_lock: 1, status: [1, 0, 0, 1, 1, 1]};
		if(agentStatusCode & 0x0020)
			//监听
			return {flag_lock: 1, status: [0, 0, 1, 0, 0, 0]};
		if(agentStatusCode & 0x0010)		
			//接待客户
			return {flag_lock: 1, status: [0, 1, 1, 0, 0, 0]};
		if(agentStatusCode & 0x0008)
			//正在通话
			return {flag_lock: 1, status: [0, 0, 1, 0, 0, 0]};
		if(agentStatusCode & 0x0040)
			//保持
			return {flag_lock: 1, status: [0, 1, 1, 0, 0, 0]};
		if(agentStatusCode & 0x0100)
			//事后处理
			return {flag_lock: 1, status: [0, 0, 0, 1, 1, 1]};
		if(agentStatusCode & 0x0200)
			//请勿打扰
			return {flag_lock: 1, status: [0, 0, 0, 1, 1, 1]};
		if(agentStatusCode & 0x0400)
			//小休
			return {flag_lock: 1, status: [0, 0, 0, 1, 1, 0]};
		if(agentStatusCode & 0x0001)
			//忙
			return {flag_lock: 1, status: [0, 0, 0, 1, 1, 1]};
		
		return {flag_lock: 1, status: [0, 0, 0, 0, 0, 0]};
	},
	
	/**
	 * 格式化时间，固定2位长度
	 */
	 formatTimeNumber: function(v){
		if(v < 10){
			return "0" + v;
		}
		
		return v;
	},
	
	parseNull: function(v){
		if(v == null){
			return "";
		}
		
		return v;
	},
	
	ms_days: 86400000,
	ms_hours: 3600000,
	ms_minus: 60000,
	ms_second: 1000,
	
	/**
	 * 计算持续时间
	 */
	 getLastTime: function(startTime, now){
		var d = now - startTime;
		if(d <= 0){
			return "00秒";
		}
		
		var lf = d;
		var days = parseInt(lf / this.ms_days);
		lf -=  days * this.ms_days;
		
		var hours = parseInt(lf / this.ms_hours);
		lf -=  hours * this.ms_hours;
		
		var minus = parseInt(lf / this.ms_minus);
		lf -=  minus * this.ms_minus;
		
		var sec = parseInt(lf / this.ms_second);
		
		var timeStr = "";
		if(days > 0){
			timeStr += days + "天";
		}
		
		if(hours > 0){
			timeStr += this.formatTimeNumber(hours) + "小时";
		}
		
		if(minus > 0){
			timeStr += this.formatTimeNumber(minus) + "分";
		}
		
		timeStr += this.formatTimeNumber(sec) + "秒";
		
		return timeStr;
	},
	
	/**
	 * 格式化时间
	 */
	formatTime: function(d){
		var year = d.getFullYear();
		var month = d.getMonth() + 1;
		var date = d.getDate();
		var hours = d.getHours();
		var minus = d.getMinutes();
		var seconds = d.getSeconds();
		
		return year + "-" + this.formatTimeNumber(month) + "-" + this.formatTimeNumber(date) 
			+ " " + this.formatTimeNumber(hours) + ":" + this.formatTimeNumber(minus) 
			+ ":" + this.formatTimeNumber(seconds);
	},
	
	/**
	 * 外呼电话
	 */
	lastCallOutTel: null,
	
	outOpt: null,
	
	callOut: function(tel){
		var pbar = phonebarUtil.phonebar;
		var param = CreateVBArr(tel, "", "pstn", 1, this.centerTel);
		try{
			this.outOpt = pbar.Agent.ExecOpt('81', null, null, param, null);
			this.lastCallOutTel = tel;
		}catch(e){
			alert("外呼失败:" + e);
		}
	},
	
	/**
	 * 取消邀请
	 */
	cancelInvite: function(){
		if(this.outOpt != null){
			this.outOpt.Cancel(100695808, '邀请者放弃邀请');
		}
	},
	
	/**
	 * 转接外线
	 */
	redirectOut: function(tel){
		var pbar = phonebarUtil.phonebar;
		var param = CreateVBArr(tel, "", "pstn", 1, this.centerTel);
		try{
			pbar.Agent.ExecOpt('16', null, null, param, null);
		}catch(e){
			alert("转外线失败:" + e);
		}
	},
	
	/**
	 * 邀请外线
	 */
	inviteOut: function(tel){
		var pbar = phonebarUtil.phonebar;
		var param = CreateVBArr(tel, "", "pstn", 1, this.centerTel);
		try{
			pbar.Agent.ExecOpt('11', null, null, param, null);
		}catch(e){
			alert("邀请外线失败:" + e);
		}
	},
	
	/**
	 * 代答
	 * userOID: 代答座席的userOID
	 */
	lastAnswerForHelpUserOID: null,
	
	answerForOtherSeat: function(userOID){
		var pbar = phonebarUtil.phonebar;
		try{
			pbar.Agent.ExecOpt('20', null, null, userOID, null);
			this.lastAnswerForHelpUserOID = userOID;
		}catch(e){
			alert("代答失败:" + e);
		}
	},
	
	/**
	 * 获取代答的客户电话
	 * userOID -> waitList -> userOID -> customer
	 */
	getAnswerForOtherSeatCustomerTel: function(){
		var queue = this.queue;
		if(!queue){
			return null;
		}
		
		for(var i = 0; i < queue.count; i++){
			var customer;
			try{
				customer = queue.Customers(i);
			}catch (e) {
				break;
			}
			
			if(customer.AgentUserOID == this.lastAnswerForHelpUserOID){
				var customerName = this.getParamFromContext(customer.Context, "calloutno");
				var callEnd = customerName.indexOf('@');
				var customerTel = customerName.substring(0, callEnd);
				
				return customerTel;
			}
		}
		
		return null;
	}
};