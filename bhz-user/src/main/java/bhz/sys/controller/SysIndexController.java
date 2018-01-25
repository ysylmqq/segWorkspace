package bhz.sys.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import bhz.sys.entity.SysUser;
import bhz.sys.facade.SysUserFacade;

import com.alibaba.dubbo.config.annotation.Reference;
/**
 * <B>系统名称：</B><BR>
 * <B>模块名称：</B><BR>
 * <B>中文类名：</B><BR>
 * <B>概要说明：</B><BR>
 * @author bhz（Alienware）
 * @since 2016年2月29日
 */
@Controller
public class SysIndexController {
	
	@Autowired
	private SysUserFacade sysUserFacade;

    
    @RequestMapping(value="/test/ysy",method=RequestMethod.GET)
    @ResponseBody
    public Object test() {
    	Map<String,Object> map = new HashMap<String,Object>();
    	map.put("name","ysy");
    	map.put("age","21");
        return sysUserFacade.getUser();
    }

    @RequestMapping(value="/logout",method=RequestMethod.GET)
    public  String logout(){
    	return "redirect:https://cas.952100.com:8443/cas/logout";
    }

}
