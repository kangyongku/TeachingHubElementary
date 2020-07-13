package kr.co.kumsung.thub.controller.front.elementary;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import kr.co.kumsung.thub.domain.Category;
import kr.co.kumsung.thub.domain.Event;
import kr.co.kumsung.thub.domain.EventWin;
import kr.co.kumsung.thub.domain.Multimedia;
import kr.co.kumsung.thub.domain.Poll;
import kr.co.kumsung.thub.service.BoardService;
import kr.co.kumsung.thub.service.CategoryService;
import kr.co.kumsung.thub.service.EventService;
import kr.co.kumsung.thub.service.MultimediaService;
import kr.co.kumsung.thub.service.PollService;
import kr.co.kumsung.thub.util.Common;
import kr.co.kumsung.thub.util.FrontPagination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Component("thubElementaryOpenController")
@Controller
@RequestMapping("/open")
public class ElementaryOpenController {
	
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private EventService eventService;
	
	@Autowired
	private PollService pollService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private MultimediaService multimediaService;
	
	/**
	 * 정보 * 소통 > 티칭허브+자료(초등)
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/utilize.do")
	public String shareUtilize(HttpServletRequest request , Model model) throws Exception
	{	
		final int BOARD_ID = 54;
		Map<String,Object> boardAttrs = boardService.getBoardDataAttributes(request, BOARD_ID);
		model.addAllAttributes(boardAttrs);
		
		// assign
		model.addAttribute("beforePath", "정보 &middot; 소통");
		model.addAttribute("currentPath", "티칭허브&#8314;자료");
		System.out.println("utilize.do---------");
		int td = 4;
		for(int i=0; i< td; i++){
			System.out.print("*");
		}
		
		
		
		return "front/elementary/open/utilize";
	}
	
	/**
	 * 정보 * 소통 > 수업 활용 영상
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/lecture.do")
	public String lecture(HttpServletRequest request , Model model) throws Exception
	{
		final int BOARD_ID = 18;
		Map<String,Object> boardAttrs = boardService.getBoardDataAttributes(request, BOARD_ID);
		model.addAllAttributes(boardAttrs);
		
		// assign
		model.addAttribute("beforePath", "정보 &middot; 소통");
		model.addAttribute("currentPath", "수업 활용 영상");
		
		return "front/elementary/open/lecture";
	}
	
	/**
	 * 정보 * 소통 > 학급경영자료
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/classes.do")
	public String shareClasses(HttpServletRequest request , Model model) throws Exception
	{
		final int BOARD_ID = 3;
		Map<String,Object> boardAttrs = boardService.getBoardDataAttributes(request, BOARD_ID);
		model.addAllAttributes(boardAttrs);
		
		// assign
		model.addAttribute("beforePath", "정보 &middot; 소통");
		model.addAttribute("currentPath", "학급경영자료");
		
		return "front/elementary/open/classes";
	}
	
	/**
	 * 정보 * 소통 > 유용한 링크집
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/links.do")
	public String links(HttpServletRequest request , Model model) throws Exception
	{
		final int PAGE_SIZE = 10;
		final int BLOCK_SIZE = 10;
		
		// get parameters
		int pg = Common.getParameter(request, "pg", 1);
		int skip = (pg - 1) * PAGE_SIZE;
		String findCategory = Common.getParameter(request, "findCategory", "E005001001");
		String findSecondaryCategory = findCategory.substring(0,7);
		String findMethod = Common.getParameter(request, "findMethod", "");
		String findStr = Common.getParameter(request, "findStr", "");
		
		// get category data
		List<Category> firstCategories = (List<Category>) categoryService.getChildren("E005");
		List<Category> secondCategories = (List<Category>) categoryService.getChildren(findSecondaryCategory);
		
		// 1차에 대한 카테고리 진입시 자동으로 2차의 첫번째 카테고리로 셋팅
		if( findCategory.length() == 7 && secondCategories.size() > 0 )
			findCategory = ((Category) secondCategories.get(0)).getCategory();
		
		// make query parameters
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("findCategory" , findCategory);
		params.put("findMethod" , findMethod);
		params.put("findStr" , findStr);
		params.put("findIsUse" , "Y");
		params.put("skip" , skip);
		params.put("pageSize" , PAGE_SIZE);
		
		// get data
		int totalCount = (Integer) multimediaService.getTotalMultimedia(params);
		int articleNum = totalCount - skip;
		List<Multimedia> list = (List<Multimedia>) multimediaService.getMultimediaList(params);
		
		// set pagination
		FrontPagination pagination = new FrontPagination(pg, PAGE_SIZE, BLOCK_SIZE, totalCount);
		pagination.initialize();
		
		// assign
		model.addAttribute("beforePath", "정보 &middot; 소통");
		model.addAttribute("currentPath", "유용한 링크집");
		
		model.addAttribute("firstCategories" , firstCategories);
		model.addAttribute("secondCategories" , secondCategories);
		
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("articleNum", articleNum);
		model.addAttribute("list", list);
		model.addAttribute("paging", pagination.print());
		
		model.addAttribute("pg", pg);
		model.addAttribute("findCategory", findCategory);
		model.addAttribute("findSecondaryCategory", findSecondaryCategory);
		model.addAttribute("findMethod", findMethod);
		model.addAttribute("findStr", findStr);
		
		return "front/elementary/open/links";
	}
	
	/**
	 * 정보 * 소통 > 이벤트
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/eventList.do")
	public String eventList(HttpServletRequest request , Model model) throws Exception
	{
		// page specificant variables
		int pageSize = 4;
		int blockSize = 10;

		// get parameters
		int pg = Common.getParameter(request, "pg", 1);
		int skip = (pg - 1) * pageSize;
		
		String findMethod = Common.getParameter(request, "findMethod", "");
		String findStr = Common.getParameter(request, "findStr", "");
		
		// make query parameters
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("findMethod" , findMethod);
		params.put("findStr" , findStr);
		params.put("findIsUse" , "Y");
		params.put("skip" , skip);
		params.put("pageSize" , pageSize);
		
		// get data
		int totalCount = eventService.getEventTotalList(params);
		int articleNum = totalCount - skip;
		
		List<Event> list = eventService.getEventList(params);
		
		FrontPagination pagination = new FrontPagination(pg, pageSize, blockSize, totalCount);
		pagination.initialize();
		
		// assign
		model.addAttribute("beforePath", "정보 &middot; 소통");
		model.addAttribute("currentPath", "이벤트");
		
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("articleNum", articleNum);
		model.addAttribute("list", list);
		model.addAttribute("paging", pagination.print());
		
		model.addAttribute("pg", pg);
		
		model.addAttribute("findMethod", findMethod);
		model.addAttribute("findStr", findStr);
		
		return "front/elementary/open/eventList";
	}
	
	/**
	 * 쉼터 > 당첨자 보기 > 리스트
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/eventWinList.do")
	public String eventWinList(HttpServletRequest request , Model model) throws Exception
	{
		// page specificant variables
		int pageSize = 4;
		int blockSize = 10;
		
		// get parameters
		int pg = Common.getParameter(request, "pg", 1);
		int skip = (pg - 1) * pageSize;
		
		String findMethod = Common.getParameter(request, "findMethod", "");
		String findStr = Common.getParameter(request, "findStr", "");
		
		// make query parameters
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("findMethod" , findMethod);
		params.put("findStr" , findStr);
		params.put("findIsUse" , "Y");
		params.put("skip" , skip);
		params.put("pageSize" , pageSize);
		
		// get data
		int totalCount = eventService.getEventWinTotalList(params);
		int articleNum = totalCount - skip;
		
		List<EventWin> list = eventService.getEventWinList(params);
		
		FrontPagination pagination = new FrontPagination(pg, pageSize, blockSize, totalCount);
		pagination.initialize();
		
		// assign
		model.addAttribute("beforePath", "선생님 행복마당 &gt; 쉼터");
		model.addAttribute("currentPath", "당첨자 발표");
		model.addAttribute("pathName", "테스트");
		
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("articleNum", articleNum);
		model.addAttribute("list", list);
		model.addAttribute("paging", pagination.print());
		
		model.addAttribute("pg", pg);
		
		model.addAttribute("findMethod", findMethod);
		model.addAttribute("findStr", findStr);
		
		return "front/elementary/open/eventWinList";
	}
	
	/**
	 * 정보 * 소통 > 리스트
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/pollList.do")
	public String pollList(HttpServletRequest request , Model model) throws Exception
	{
		// page specificant variables
		int pageSize = 10;
		int blockSize = 10;
		
		// get parameters
		int pg = Common.getParameter(request, "pg", 1);
		int skip = (pg - 1) * pageSize;
		
		String findMethod = Common.getParameter(request, "findMethod", "");
		String findStr = Common.getParameter(request, "findStr", "");
		
		// make query parameters
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("findMethod" , findMethod);
		params.put("findStr" , findStr);
		params.put("findIsUse" , "Y");
		params.put("elementary","Y");
		params.put("skip" , skip);
		params.put("pageSize" , pageSize);
		
		// get data
		int totalCount = pollService.getTotalPoll(params);
		int articleNum = totalCount - skip;
		
		List<Poll> list = pollService.getPollList(params);
		
		FrontPagination pagination = new FrontPagination(pg, pageSize, blockSize, totalCount);
		pagination.initialize();
		
		// assign
		model.addAttribute("beforePath", "정보 &middot; 소통");
		model.addAttribute("currentPath", "설문조사");
		
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("articleNum", articleNum);
		model.addAttribute("list", list);
		model.addAttribute("paging", pagination.print());
		
		model.addAttribute("pg", pg);
		
		model.addAttribute("findMethod", findMethod);
		model.addAttribute("findStr", findStr);
		
		return "front/elementary/open/pollList";
	}

}
