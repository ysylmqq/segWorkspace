var HomePagePanel = function(){
	//var panel = this;

    var logoutBtn = new Ext.Button({
    	icon: 'resources/images/logout.gif',
		text: '退出系统',
		handler: function(){
			logout();
		}
    });
	
	var topBar = new Ext.Toolbar({
		items: ['<div style="width:31px;height:32px;background:url(resources/images/monitor.png) no-repeat"></div>',
		        '<div style="color:#75A64F;font-size:15px;font-weight:bolder;padding-top:6px">Gateway Status<div>', 
		        '->', logoutBtn, '-']
	});
	
	var centerPanel = new CenterPanel();
	
	var unitPanel = new UnitPanel();
	var aplanClientPanel = new AplanClientPanel();
	var aplanAlarmClientPanel = new AplanAlarmClientPanel();
	var seatPanel = new SeatPanel();
	
	var westPanel = new Ext.TabPanel({
		activeTab: 0,
		border: false,
		items: [unitPanel, aplanClientPanel, aplanAlarmClientPanel, seatPanel]
	});
	
	HomePagePanel.superclass.constructor.call(this,{
		tbar: topBar,
		layout: 'border',
		border: false,
		items: [{
			split: true,
			width: 400,
			margins: '1 0 1 1',
			region: 'west',
			layout: 'fit',
			collapsible: true,
			title: '客户端查询',
			items: [westPanel]
		},{
			title: '服务器状态',
			margins: '1 1 1 0',
			region: 'center',
			layout: 'fit',
			items: [centerPanel]
		}]
	});
};
Ext.extend(HomePagePanel, Ext.Panel);