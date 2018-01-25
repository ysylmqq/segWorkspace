package bjsxt.web;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.netty.channel.ChannelFuture;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import bhz.dat.protocol.RequestData;
import bjsxt.entity.DatCheckData;
import bjsxt.main.GenerateData;
import bjsxt.netty.Client;



@Controller
public class IndexController {

	@Autowired
	private GenerateData generate;
	
    @RequestMapping("/index.html")
    public ModelAndView index() throws Exception {
        ModelAndView ret = new ModelAndView();
        return ret;
    }

    @RequestMapping("/send.html")
    public void send(HttpServletRequest request, HttpServletResponse response, int count){
    	try {
    		
    		Client c = Client.getInstance();
    		ChannelFuture cf = c.getChannelFuture();
    		//
			List<DatCheckData> list = this.generate.batchAdd(count);
			for (DatCheckData datCheckData : list) {
				RequestData rd = new RequestData();
				BeanUtils.copyProperties(datCheckData, rd, new String[]{"sync"});
				cf.channel().writeAndFlush(rd);
				//在clientHandler当中设置了，如果60s没有数据交互就会断开
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	
}
