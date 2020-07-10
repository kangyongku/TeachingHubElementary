package kr.co.kumsung.thub.controller.front.thub;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import kr.co.kumsung.thub.domain.Event;
import kr.co.kumsung.thub.domain.EventWin;
import kr.co.kumsung.thub.domain.Member;
import kr.co.kumsung.thub.domain.Poll;
import kr.co.kumsung.thub.domain.PollResponse;
import kr.co.kumsung.thub.domain.Training;
import kr.co.kumsung.thub.domain.TrainingPostscript;
import kr.co.kumsung.thub.exception.CommonException;
import kr.co.kumsung.thub.service.BoardService;
import kr.co.kumsung.thub.service.CategoryService;
import kr.co.kumsung.thub.service.EventService;
import kr.co.kumsung.thub.service.MemberService;
import kr.co.kumsung.thub.service.PollService;
import kr.co.kumsung.thub.service.TrainingService;
import kr.co.kumsung.thub.util.Common;
import kr.co.kumsung.thub.util.FrontPagination;
import kr.co.kumsung.thub.util.ResultMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Component("thubActivityController")
@Controller
@RequestMapping("/activity")
public class ActivityController {

	@Autowired
	private TrainingService trainingService;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private EventService eventService;
	
	@Autowired
	private PollService pollService;
	
	@Autowired
	private BoardService boardService;
	
	/**
	 * 진료교육
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
		model.addAttribute("beforePath", "창의적 체험활동");
		model.addAttribute("currentPath", "진로교육");
		
		return "front/thub/activity/career";
	}
	
	/**
	 * 창의적 체험활동 > 소개 1 ~ 5
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/introduce/page1.do")
	public String activityIntroducePage1(HttpServletRequest request , Model model) throws Exception
	{	
		// assign
		model.addAttribute("beforePath", "창의적 체험활동");
		model.addAttribute("currentPath", "체험활동 소개");
		
		return "front/thub/activity/introduce/page1";
	}
	
	@RequestMapping("/introduce/page2.do")
	public String activityIntroducePage2(HttpServletRequest request , Model model) throws Exception
	{	
		// assign
		model.addAttribute("beforePath", "창의적 체험활동");
		model.addAttribute("currentPath", "체험활동 소개");
		
		return "front/thub/activity/introduce/page2";
	}
	
	@RequestMapping("/introduce/page3.do")
	public String activityIntroducePage3(HttpServletRequest request , Model model) throws Exception
	{	
		// assign
		model.addAttribute("beforePath", "창의적 체험활동");
		model.addAttribute("currentPath", "체험활동 소개");
		
		return "front/thub/activity/introduce/page3";
	}
	
	@RequestMapping("/introduce/page4.do")
	public String activityIntroducePage4(HttpServletRequest request , Model model) throws Exception
	{	
		// assign
		model.addAttribute("beforePath", "창의적 체험활동");
		model.addAttribute("currentPath", "체험활동 소개");
		
		return "front/thub/activity/introduce/page4";
	}
	
	@RequestMapping("/introduce/page5.do")
	public String activityIntroducePage5(HttpServletRequest request , Model model) throws Exception
	{	
		// assign
		model.addAttribute("beforePath", "창의적 체험활동");
		model.addAttribute("currentPath", "체험활동 소개");
		
		return "front/thub/activity/introduce/page5";
	}
	
	/**
	 * 창의적 체험활동 > 서식자료
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/form.do")
	public String activityForm(HttpServletRequest request , Model model) throws Exception
	{
		final int BOARD_ID = 34;
		Map<String,Object> boardAttrs = boardService.getBoardDataAttributes(request, BOARD_ID);
		model.addAllAttributes(boardAttrs);
		
		// assign
		model.addAttribute("beforePath", "창의적 체험활동");
		model.addAttribute("currentPath", "서식자료");
		
		return "front/thub/activity/form";
	}
	
	/**
	 * 창의적 체험활동 > 참고사이트
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/site.do")
	public String activitySite(HttpServletRequest request , Model model) throws Exception
	{
		final int BOARD_ID = 35;
		Map<String,Object> boardAttrs = boardService.getBoardDataAttributes(request, BOARD_ID);
		model.addAllAttributes(boardAttrs);
		
		// assign
		model.addAttribute("beforePath", "창의적 체험활동");
		model.addAttribute("currentPath", "참고사이트");
		
		return "front/thub/activity/site";
	}
	
	/**
	 * 자유학기제
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/freesem.do")
	public String freesem(HttpServletRequest request , Model model) throws Exception
	{
		final int BOARD_ID = 36;
		Map<String,Object> boardAttrs = boardService.getBoardDataAttributes(request, BOARD_ID);
		model.addAllAttributes(boardAttrs);
		
		// assign
		model.addAttribute("beforePath", "창의적 체험활동");
		model.addAttribute("currentPath", "자유학기제");
		
		return "front/thub/activity/freesem";
	}
	
}
