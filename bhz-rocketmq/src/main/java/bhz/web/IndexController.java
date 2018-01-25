package bhz.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import bhz.entity.User;
import bhz.service.UserService;

@Controller
public class IndexController {

	@Autowired
	private UserService userService ;

    @RequestMapping("/index.html")
    public ModelAndView index() throws Exception {

        ModelAndView ret = new ModelAndView();
        User user = this.userService.getUserById("1");
        ret.addObject("tag", "hello world");
        ret.addObject("name", user.getName());
        return ret;
    }

	
	
	
	
	
	
	
}
