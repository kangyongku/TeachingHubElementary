package kr.co.kumsung.thub.controller.front.elementary;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import kr.co.kumsung.thub.domain.BoardConfig;
import kr.co.kumsung.thub.service.BoardService;
import kr.co.kumsung.thub.service.SmartService;
import kr.co.kumsung.thub.util.Common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Thub ElementaryMainController
 * @author yongku.kang
 * @date 2020/07/04
 */

@Component("thubElementaryController")
@Controller
@RequestMapping("/elementary")
public class ElementaryController {
	
	private static final Logger logger = LoggerFactory.getLogger(ElementaryController.class);
	
	@Autowired
	private SmartService smartService;
	
	@Autowired
	private BoardService boardService;
	
	/**
	 * 창의적 체험활동 > 오늘의 역사(초등)
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/calendar.do")
	public String volunteer_renew(HttpServletRequest request , Model model) throws Exception
	{
		model.addAllAttributes( smartService.calendarData(request) );
		return "front/elementary/freestudy/calendar";
	}
	
	/**
	 * 창의적 체험활동 > 체험활동 프로그램(초등)
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/experience.do")
	public String careerv(HttpServletRequest request , Model model) throws Exception
	{	
		int boardType = Common.getParameter(request, "boardType", 1);
		
		//임시방편으로
		final int BOARD_ID = 64;
		
			Map<String,Object> boardAttrs = boardService.getBoardDataAttributes(request, BOARD_ID);
			model.addAllAttributes(boardAttrs);	
		
		BoardConfig bconf = boardService.getBoardConfig(BOARD_ID);
		// assign
		model.addAttribute("beforePath", "창의적 체험활통");
		model.addAttribute("currentPath", bconf.getBoardName());	
		model.addAttribute("boardConfig", bconf);
		model.addAttribute("boardType", boardType);
		
		return "front/elementary/freestudy/experience";
	}
	
	/**
	 * 창의적 체험활동 > 진료교육
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/career.do")
	public String career(HttpServletRequest request , Model model) throws Exception
	{
		final int BOARD_ID = 37;
		Map<String,Object> boardAttrs = boardService.getBoardDataAttributes(request, BOARD_ID);
		model.addAllAttributes(boardAttrs);
		
		// assign
		model.addAttribute("beforePath", "창의적 체험활통");
		model.addAttribute("currentPath", "진로교육");
		
		return "front/elementary/freestudy/career";
	}
	
	/**
	 * 창의적 체험활동 > 카드뉴스
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/cardnews.do")
	public String cardnews(HttpServletRequest request , Model model) throws Exception
	{	
		final int BOARD_ID = 55;
		Map<String,Object> boardAttrs = boardService.getBoardDataAttributes(request, BOARD_ID);
		model.addAllAttributes(boardAttrs);
		
		// assign
		model.addAttribute("beforePath", "창의적 체험활통");
		model.addAttribute("currentPath", "카드뉴스");
		
		return "front/elementary/freestudy/cardnews";
	}
	
}
