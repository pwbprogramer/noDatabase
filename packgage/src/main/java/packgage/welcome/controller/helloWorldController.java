package packgage.welcome.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value="/welcome")
public class helloWorldController {
     
	@RequestMapping(value="/hello")
	public void helloSpring() {
		System.out.println("hello spring");
	}
}
