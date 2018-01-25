package bhz.mst.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import bhz.mst.facade.MstSiteFacade;


@Controller
public class MstIndexController {

	@Autowired
	private MstSiteFacade mstSiteFacade;
	
	@RequestMapping(value="/index/ysy",method=RequestMethod.GET)
	@ResponseBody
	public Object ysy() throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
    	map.put("name","ysy");
    	map.put("age","21");
        return mstSiteFacade.getList();
	}
	
	@RequestMapping(value="/logout",method=RequestMethod.GET)
    public  String logout(){
    	return "redirect:https://cas.952100.com:8443/cas/logout";
    }
}
