"use strict";

//菜单
~function(){
    var mainMenu = $('.main-menu');
    mainMenu.on('click','.main-link', function(e){
       var mainItem = $(this).parent();
	   var mainItems=$(this).parent().siblings();
       mainItems.removeClass('active').removeClass('selected').find('.sub-menu > li').removeClass('active');
        if(mainItem.find('.sub-menu').length <= 0){ 
            mainItem.addClass('selected');
        }else{
            mainItem.addClass('active');
        }
    });
    mainMenu.on('click','.sub-menu a', function(e){
       var subItem = $(this).parent();
	   var subItems=$(this).parent().siblings();
        subItems.removeClass('selected');
        subItem.addClass('selected');
    });
}();

//主页面 tab
~function(){
    var SiteTab = {};
    var tabDOM = $('.site-tab');
    var tab = tabDOM.ligerTab({
        height : '100%',
        changeHeightOnResize:true,
        ShowSwitchInTab:true,
        showSwitch : true,
        //设置展出层不可用或可用
        onAfterRemoveTabItem:function(targettabid){

        },
        onAfterSelectTabItem : function(tabid){

        }
    });
    tab.setContentHeight = _setContentHeight;
    tab.setContentHeight();

    //设置 content 高度为 .l-tab 高度减去 .l-tab-links高度;
    //.l-tab-links 高度包含 margin;
    function _setContentHeight(){
        var g = this, p = this.options;
        var newheight = g.tab.height() - g.tab.links.outerHeight(true);
        g.tab.content.height(newheight);
        $("> .l-tab-content-item", g.tab.content).height(newheight);
    }
    SiteTab.newTab = function(url, text, isnewwin, showClose) {
        if (isnewwin == '1') {
            window.open(url);
            return false;
        }
        var tid = url.replace('./', '').replace(/\./g, '')
            .replace(/\:/g, '_1_').replace(/[#;]/g, '').replace(/\&/g,
            '_1_');
        tid = tid.replace(/[\/\.\?\=]/g, '_0_');
        if (window.self == window.parent) {
            if (window.SiteTab.addTab == undefined) {
                window.open(url);
                return false;
            } else {
                SiteTab.addTab(tid, text, url, showClose);
            }
        } else {
            if (window.parent.SiteTab.addTab != undefined) {
                if (text == undefined)
                    text = '新标签页';
                window.parent.SiteTab.addTab(tid, text, url, showClose);
            }
        };
    };
    SiteTab.addTab = function(tabid, text, url, showClose) {
        // if(tab.isTabItemExist(tabid) && !showClose) return
        // tab.selectTabItem(tabid);
        tab.addTabItem({
            tabid : tabid,
            text : text,
            url :'./htmls/'+url,

            showClose : showClose === false ? false : true
        });
        //修复 tabHeight 错误
        $(window).trigger('resize');
    };
    window.SiteTab = SiteTab;
}();