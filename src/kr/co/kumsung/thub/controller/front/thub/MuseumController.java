package kr.co.kumsung.thub.controller.front.thub;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Component("thubMuseumController")
@Controller
@RequestMapping("/museum")
public class MuseumController {
	private static final Logger logger = LoggerFactory.getLogger(MuseumController.class);
	

	/**
	 * museum 메인
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/main.do")
	public String dokdo(HttpServletRequest request , Model model) throws Exception
	{	
		
		return "front/museum/main";
	}
	
}
