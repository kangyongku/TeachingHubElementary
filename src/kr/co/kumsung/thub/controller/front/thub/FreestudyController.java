package kr.co.kumsung.thub.controller.front.thub;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import kr.co.kumsung.thub.domain.Article;
import kr.co.kumsung.thub.domain.BoardCategory;
import kr.co.kumsung.thub.domain.BoardConfig;
import kr.co.kumsung.thub.domain.Category;
import kr.co.kumsung.thub.domain.Comment;
import kr.co.kumsung.thub.domain.Event;
import kr.co.kumsung.thub.domain.FileInfo;
import kr.co.kumsung.thub.domain.Headword;
import kr.co.kumsung.thub.domain.Member;
import kr.co.kumsung.thub.domain.Multimedia;
import kr.co.kumsung.thub.domain.RelationHeadword;
import kr.co.kumsung.thub.domain.Send;
import kr.co.kumsung.thub.exception.CommonException;
import kr.co.kumsung.thub.service.BoardService;
import kr.co.kumsung.thub.service.CategoryService;
import kr.co.kumsung.thub.service.CustomerService;
import kr.co.kumsung.thub.service.FileService;
import kr.co.kumsung.thub.service.MemberService;
import kr.co.kumsung.thub.service.MultimediaService;
import kr.co.kumsung.thub.service.SmartService;
import kr.co.kumsung.thub.util.Common;
import kr.co.kumsung.thub.util.FrontPagination;
import kr.co.kumsung.thub.util.Validate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Component("thubFreestudyController")
@Controller
@RequestMapping("/freestudy")
public class FreestudyController {
	private static final Logger logger = LoggerFactory.getLogger(FreestudyController.class);
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private MultimediaService multimediaService;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private SmartService smartService;
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private CustomerService customerService;
	
	@Value("#{common['file.path']}") String serverFilePath;		// 첨부파일의 root 경로
	

	
	/**
	 * 자유학년창체-체험활동
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/activity.do")
	public String activity(HttpServletRequest request , Model model) throws Exception
	{	

		int boardType = 2;
		final int BOARD_ID = 49;
		Map<String,Object> boardAttrs = boardService.getBoardDataAttributes(request, BOARD_ID);
		model.addAllAttributes(boardAttrs);	

		
		BoardConfig bconf = boardService.getBoardConfig(BOARD_ID);
		// assign
		model.addAttribute("beforePath", "자유학년∙창체");
		model.addAttribute("currentPath", bconf.getBoardName());	
		model.addAttribute("boardConfig", bconf);
		model.addAttribute("boardType", boardType);
		return "front/thub/freestudy/activity";
	}
	
	/**
	 * 자유학년창체-자율활동
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/autonomy.do")
	public String autonomy(HttpServletRequest request , Model model) throws Exception
	{	

		int boardType = Common.getParameter(request, "boardType", 1);
		final int BOARD_ID = 50;
		if(boardType==2){
			Map<String,Object> boardAttrs = boardService.getBoardDataAttributes(request, BOARD_ID);
			model.addAllAttributes(boardAttrs);	
		}
		
		BoardConfig bconf = boardService.getBoardConfig(BOARD_ID);

		// assign
		model.addAttribute("beforePath", "자유학년∙창체");
		model.addAttribute("currentPath", bconf.getBoardName());	
		model.addAttribute("boardConfig", bconf);
		model.addAttribute("boardType", boardType);
		return "front/thub/freestudy/autonomy";
	}	
	
	/**
	 * 자유학년창체-동아리활동
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/circle.do")
	public String circle(HttpServletRequest request , Model model) throws Exception
	{	
		int boardType = Common.getParameter(request, "boardType", 1);
		final int BOARD_ID = 51;
		if(boardType==2){
			Map<String,Object> boardAttrs = boardService.getBoardDataAttributes(request, BOARD_ID);
			model.addAllAttributes(boardAttrs);	
		}
		
		BoardConfig bconf = boardService.getBoardConfig(BOARD_ID);
		// assign
		model.addAttribute("beforePath", "자유학년∙창체");
		model.addAttribute("currentPath", bconf.getBoardName());	
		model.addAttribute("boardConfig", bconf);
		model.addAttribute("boardType", boardType);
		return "front/thub/freestudy/circle";
	}	
	
	/**
	 * 자유학년∙창체-봉사활동
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/volunteer.do")
	public String volunteer(HttpServletRequest request , Model model) throws Exception
	{	
		int boardType = Common.getParameter(request, "boardType", 1);
		final int BOARD_ID = 52;
		if(boardType==2){
			Map<String,Object> boardAttrs = boardService.getBoardDataAttributes(request, BOARD_ID);
			model.addAllAttributes(boardAttrs);	
		}
		
		BoardConfig bconf = boardService.getBoardConfig(BOARD_ID);
		// assign
		model.addAttribute("beforePath", "자유학년∙창체");
		model.addAttribute("currentPath", bconf.getBoardName());	
		model.addAttribute("boardConfig", bconf);
		model.addAttribute("boardType", boardType);
		return "front/thub/freestudy/volunteer";
	}		
	
	/**
	 * 자유학년∙창체-진로탐색활동
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/career.do")
	public String career(HttpServletRequest request , Model model) throws Exception
	{	
		int boardType = Common.getParameter(request, "boardType", 1);
		final int BOARD_ID = 53;
		if(boardType==2){
			Map<String,Object> boardAttrs = boardService.getBoardDataAttributes(request, BOARD_ID);
			model.addAllAttributes(boardAttrs);	
		}
		
		BoardConfig bconf = boardService.getBoardConfig(BOARD_ID);
		// assign
		model.addAttribute("beforePath", "자유학년∙창체");
		model.addAttribute("currentPath", bconf.getBoardName());	
		model.addAttribute("boardConfig", bconf);
		model.addAttribute("boardType", boardType);
		return "front/thub/freestudy/career";
	}		
	
	
/*
180516 메뉴 추가 시작
*/

	/**
	 * 자유학년창체-자유학년제_신규
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping("/freestudy.do")
	public String freestudy(HttpServletRequest request , Model model) throws Exception
	{	
		int boardType = Common.getParameter(request, "boardType", 1);
		final int BOARD_ID = 62;
		
		//if(boardType != 1){
			Map<String,Object> boardAttrs = boardService.getBoardDataAttributes(request, BOARD_ID);
			model.addAllAttributes(boardAttrs);	
		//}
		
		BoardConfig bconf = boardService.getBoardConfig(BOARD_ID);
		// assign
		model.addAttribute("beforePath", "자유학년∙창체");
		model.addAttribute("currentPath", bconf.getBoardName());	
		model.addAttribute("boardConfig", bconf);
		model.addAttribute("boardType", boardType);
		return "front/thub/freestudy/freestudy";
	}	
	
	/**
	 * 자유학년∙창체-봉사활동-renew
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/creative.do")
	public String volunteer_renew(HttpServletRequest request , Model model) throws Exception
	{	
		int boardType = Common.getParameter(request, "boardType", 1);
		final int BOARD_ID = 63;
		if(boardType==2){
			Map<String,Object> boardAttrs = boardService.getBoardDataAttributes(request, BOARD_ID);
			model.addAllAttributes(boardAttrs);	
		}
		
		BoardConfig bconf = boardService.getBoardConfig(BOARD_ID);
		// assign
		model.addAttribute("beforePath", "자유학년∙창체");
		model.addAttribute("currentPath", bconf.getBoardName());	
		model.addAttribute("boardConfig", bconf);
		model.addAttribute("boardType", boardType);
		return "front/thub/freestudy/creative";
	}		
	
	/**
	 * 자유학년∙창체-진로탐색활동
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
		model.addAttribute("beforePath", "자유학년∙창체");
		model.addAttribute("currentPath", bconf.getBoardName());	
		model.addAttribute("boardConfig", bconf);
		model.addAttribute("boardType", boardType);
		return "front/thub/freestudy/experience";
	}	

/*
180516 메뉴 추가 끝
*/
	
}