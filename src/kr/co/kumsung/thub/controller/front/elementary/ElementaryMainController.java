package kr.co.kumsung.thub.controller.front.elementary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.co.kumsung.thub.domain.BoardConfig;
import kr.co.kumsung.thub.domain.Event;
import kr.co.kumsung.thub.domain.Article;
import kr.co.kumsung.thub.domain.Book;
import kr.co.kumsung.thub.domain.Category;
import kr.co.kumsung.thub.domain.Cd;
import kr.co.kumsung.thub.domain.ChoiceBook;
import kr.co.kumsung.thub.domain.Headword;
import kr.co.kumsung.thub.domain.History;
import kr.co.kumsung.thub.domain.MainData;
import kr.co.kumsung.thub.domain.Member;
import kr.co.kumsung.thub.domain.Multimedia;
import kr.co.kumsung.thub.domain.Popup;
import kr.co.kumsung.thub.exception.CommonException;
import kr.co.kumsung.thub.service.BoardService;
import kr.co.kumsung.thub.service.CategoryService;
import kr.co.kumsung.thub.service.EventService;
import kr.co.kumsung.thub.service.LearningService;
import kr.co.kumsung.thub.service.MemberService;
import kr.co.kumsung.thub.service.MultimediaService;
import kr.co.kumsung.thub.service.PopupService;
import kr.co.kumsung.thub.service.SmartService;
import kr.co.kumsung.thub.service.MainDisplayService;
import kr.co.kumsung.thub.setting.Constants;
import kr.co.kumsung.thub.util.Common;
import kr.co.kumsung.thub.util.DateUtil;
import kr.co.kumsung.thub.util.Validate;
import kr.co.kumsung.thub.util.MainPagination;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Thub ElementaryMainController
 * @author yongku.kang
 * @date 2020/07/04
 */
@Component("thubElementaryMainController")
@Controller
@RequestMapping("/elementary")
public class ElementaryMainController {
	
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private MultimediaService multimediaService;
	
	@Autowired
	private SmartService smartService;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private PopupService popupService;
	
	@Autowired
	private EventService eventService;
	
	@Autowired
	private LearningService learningService;
	
	@Autowired
	private	MainDisplayService maindisplayService;
	
	private static final int PAGE_SIZE = 4;
	private static final int BLOCK_SIZE = 5;
	private static final Logger logger = Logger.getLogger(ElementaryMainController.class);
	
	@RequestMapping("/main.do")
	public String elementarymain(HttpServletRequest request , Model model) throws Exception
	{
		// 공지사항
		List<Article> notices = (List<Article>) boardService.getArticleList(new HashMap<String,Object>(){
																			private static final long serialVersionUID = 1L;
																				{
																					put("boardId" , 16);
																					put("skip" , 0);
																					put("pageSize" , 3);
																				}
																			});
		//팝업(사용중?)
		List<Popup> popupList = (List<Popup>) popupService.getPopupList(new HashMap<String,Object>(){
			{
				put("findIsUse" , "Y");
				put("findDate" , DateUtil.getDate("yyyy-MM-dd"));
				put("findTarget" , "THUB");
				put("skip" , 0);
				put("pageSize" , 10);
			}
		});	
		
		List<History> calendarList = (List<History>) smartService.getMainCalendarHistoryList( DateUtil.getDate("MMdd"));
		
		// get session
		HttpSession session = (HttpSession) request.getSession();
		Member member = (Member) session.getAttribute("member");

		//사용중?	
		//List<Category> mySubjects = (member == null || Validate.isEmpty(member.getSubCategory())) ? new ArrayList<Category>() : (List<Category>) categoryService.getChildren(member.getSubCategory().substring(0,4));
		List<ChoiceBook> myBooks = (member == null ) ? new ArrayList<ChoiceBook>() : (List<ChoiceBook>) memberService.getMyBooks(member.getUserId());
		Category mySubject = (member == null || Validate.isEmpty(member.getSubCategory())) ? new Category() : (Category) categoryService.getCategory(member.getSubCategory().substring(0,4));

		//이달의 추천 자료
		String dispCd = "bestdata";
		List<MainData> bestdata =  (List<MainData>) maindisplayService.getDispViewList(dispCd);
		
		//우상단배너
		dispCd = "banarea_m";
		List<MainData> banarea_m =  (List<MainData>) maindisplayService.getDispViewList(dispCd);
		
		//중단배너
		dispCd = "banarea_s";
		List<MainData> banarea_s =  (List<MainData>) maindisplayService.getDispViewList(dispCd);
	
		//기획전
		dispCd = "spcont";
		List<MainData> spcont =  (List<MainData>) maindisplayService.getDispViewList(dispCd);
		
		
		String today = DateUtil.getDate("MM-dd");
		String[] t = today.split("-");
		int todayMonth = Integer.valueOf(t[0]);
		int todayDay = Integer.valueOf(t[1]);

		model.addAttribute("jsessionid", session.getId());
		model.addAttribute("notices", notices);
		
		model.addAttribute("todayMonth", todayMonth);
		model.addAttribute("todayDay", todayDay);
		
		model.addAttribute("myBooks", myBooks);
		model.addAttribute("mySubject", mySubject);
		//model.addAttribute("mySubjects", mySubjects);
		
		model.addAttribute("calendarList", calendarList);
		model.addAttribute("popupList", popupList);
		model.addAttribute("bestdata", bestdata);
		model.addAttribute("banarea_m", banarea_m);
		model.addAttribute("banarea_s", banarea_s);
		model.addAttribute("spcont", spcont);
		System.out.println("elementarymain.do");
		return "front/elementary/main";
	}
	
	//내교과서 시디다운용
	@RequestMapping(value="/ajax/teacherCdDown.do" , method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> teacherCdDown(HttpServletRequest request , Model model) throws Exception
	{
		int bookId = Common.getParameter(request, "bookId" , 0);
		Book book = (Book) learningService.getBook(bookId);
		List<Cd> cdes = new ArrayList<Cd>();
		cdes = learningService.getCdList(bookId);
		Map<String,Object> result = new HashMap<String,Object>();
		
		result.put("result" , "SUCCESS");
		result.put("book" , book);
		result.put("cdes" , cdes);
		return result;
	}
	
	
	
	/**
	 * 메인화면 교과서별 통합자료 조회.
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/mainDataChange.do" , method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> mainDataChange(HttpServletRequest request , Model model) throws Exception
	{
		Map<String,Object> result = new HashMap<String,Object>();
		int pg = Common.getParameter(request, "pg", 1);
		String findCategory = Common.getParameter(request, "findCategory", "A001");
		int skip = (pg - 1) * PAGE_SIZE;
		// make query parameter
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("findCategory" , findCategory);
		
		params.put("skip" , skip);
		params.put("pageSize" , PAGE_SIZE);
		// get data
		int totalList = learningService.getTotalMainData(params);

		int articleNum = totalList - skip;
		
		// set pagination
		MainPagination pagination = new MainPagination(pg, PAGE_SIZE, BLOCK_SIZE, totalList);
		pagination.setDelimiter("");
		pagination.setPrevLink("Prev");
		pagination.setNextLink("Next");
				
				
		List<MainData> mainlist = (List<MainData>) learningService.getMainDataList(params);
		

		// assign
		result.put("result" , "SUCCESS");
		result.put("mainlists" , mainlist);
		result.put("pg", pg);
		result.put("findCategory", findCategory);
		result.put("totalList", totalList);
		result.put("articleNum", articleNum);
		
		result.put("paging", pagination.print());
		
		return result;
	}
	
	@RequestMapping("/history/historydream.do")
	public String historydream(HttpServletRequest request ,HttpServletResponse response, Model model) throws Exception
	{
		// get session
		HttpSession session = (HttpSession) request.getSession();
		Member member = (Member) session.getAttribute("member");
		

		/*if (member == null){
			throw new CommonException("로그인 후 이용이 가능합니다.",
					CommonException.HISTORY_BACK);
		}*/
		
		model.addAttribute("Member", member);
		
		return "front/history/index";
		
	}
	
	@RequestMapping("/history/history_sub.do")
	public String history_sub(HttpServletRequest request ,HttpServletResponse response, Model model) throws Exception
	{
		String subtitle = "";
		// get session
		HttpSession session = (HttpSession) request.getSession();
		Member member = (Member) session.getAttribute("member");
		
		/*if (member == null){
			throw new CommonException("로그인 후 이용이 가능합니다.",
					CommonException.HISTORY_BACK);
		}*/
		
		String subpages = Common.getParameter(request, "sb", "");	
		
		if(subpages.equals("1_1")) {
			subtitle = "선사 시대 ~ 고조선";
		}else if(subpages.equals("1_2")) {
			subtitle = "삼국 ~ 남북국 시대";
		}else if(subpages.equals("1_3")) {
			subtitle = "고려 시대";
		}else if(subpages.equals("1_4")) {
			subtitle = "조선 시대";
		}else if(subpages.equals("1_5")) {
			subtitle = "개항 이후 ~ 일제 강점기";
		}else if(subpages.equals("1_6")) {
			subtitle = "광복 이후";
		}else if(subpages.equals("2_1")) {
			subtitle = "선사 시대";
		}else if(subpages.equals("2_2")) {
			subtitle = "동아시아";
		}else if(subpages.equals("2_3")) {
			subtitle = "서아시아, 아프리카";
		}else if(subpages.equals("2_4")) {
			subtitle = "인도, 동남아시아";
		}else if(subpages.equals("2_5")) {
			subtitle = "유럽, 아메리카";
		}else if(subpages.equals("2_6")) {
			subtitle = "1900~";
		}else if(subpages.equals("3_1")) {
			subtitle = "사진과 지도로 보는 한국사";
		}else if(subpages.equals("3_2")) {
			subtitle = "역사가 보이는 활동";
		}else if(subpages.equals("3_3")) {
			subtitle = "사료 분석집";
		}else if(subpages.equals("3_4")) {
			subtitle = "용어 해설집";
		}else if(subpages.equals("3_5")) {
			subtitle = "영상으로 보는 역사";
		}else if(subpages.equals("3_6")) {
			subtitle = "토론 수업 모형";
		}else {
			subtitle ="";
		}
		
		model.addAttribute("subTitle", subtitle);
		model.addAttribute("subPages", subpages);
		model.addAttribute("Member", member);
		
		return "front/history/sub"+subpages; 
		
	}
}
