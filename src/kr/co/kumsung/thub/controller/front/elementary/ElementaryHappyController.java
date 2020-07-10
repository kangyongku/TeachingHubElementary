package kr.co.kumsung.thub.controller.front.elementary;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import kr.co.kumsung.thub.service.BoardService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Component("thubElementaryHappyController")
@Controller
@RequestMapping("/happy")
public class ElementaryHappyController {
	
	@Autowired
	private BoardService boardService;
	
	/**
	 * 쉬는시간 > 힐링캠핑
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/camping.do")
	public String restCamping(HttpServletRequest request , Model model) throws Exception
	{
		final int BOARD_ID = 20;
		Map<String,Object> boardAttrs = boardService.getBoardDataAttributes(request, BOARD_ID);
		model.addAllAttributes(boardAttrs);
		
		// assign
		model.addAttribute("beforePath", "쉬는시간");
		model.addAttribute("currentPath", "힐링캠핑");
		
		return "front/elementary/happy/camping";
	}
	
	/**
	 * 쉬는시간 > 추천여행
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/travel.do")
	public String restTravel(HttpServletRequest request , Model model) throws Exception
	{
		final int BOARD_ID = 21;
		Map<String,Object> boardAttrs = boardService.getBoardDataAttributes(request, BOARD_ID);
		model.addAllAttributes(boardAttrs);
		
		// assign
		model.addAttribute("beforePath", "쉬는시간");
		model.addAttribute("currentPath", "추천여행");
		
		return "front/elementary/happy/travel";
	}
	
	/**
	 * 쉬는시간 > 생활의 지혜
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/wisdom.do")
	public String restWisdom(HttpServletRequest request , Model model) throws Exception
	{
		final int BOARD_ID = 22;
		Map<String,Object> boardAttrs = boardService.getBoardDataAttributes(request, BOARD_ID);
		model.addAllAttributes(boardAttrs);
		
		// assign
		model.addAttribute("beforePath", "쉬는시간");
		model.addAttribute("currentPath", "생활의 지혜");
		
		return "front/elementary/happy/wisdom";
	}
	
	/**
	 * 쉬는시간 > 좋은글
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/emotion.do")
	public String healingEmotion(HttpServletRequest request , Model model) throws Exception
	{
		final int BOARD_ID = 11;
		Map<String,Object> boardAttrs = boardService.getBoardDataAttributes(request, BOARD_ID);
		model.addAllAttributes(boardAttrs);
		
		// assign
		model.addAttribute("beforePath", "쉬는시간");
		model.addAttribute("currentPath", "좋은글");
		
		return "front/elementary/happy/emotion";
	}
	
	/**
	 * 쉼터 > 웹툰
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/webtoons.do")
	public String healingWebtoons(HttpServletRequest request , Model model) throws Exception
	{
		final int BOARD_ID = 12;
		Map<String,Object> boardAttrs = boardService.getBoardDataAttributes(request, BOARD_ID);
		model.addAllAttributes(boardAttrs);
		
		// assign
		model.addAttribute("beforePath", "쉬는시간");
		model.addAttribute("currentPath", "웹툰");
		
		return "front/elementary/happy/webtoons";
	}

}
