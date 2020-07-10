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

@Component("thubOpenController")
@Controller
@RequestMapping("/open")
public class OpenController {
	private static final Logger logger = LoggerFactory.getLogger(OpenController.class);
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
	 * 멀티미디어 자료 리스트
	 * 각 메뉴에 대한 구분값은 findMaster 값.
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/multimedia/list.do")
	public String list(HttpServletRequest request , Model model) throws Exception
	{
		// page specificant variables
		final int PAGE_SIZE = 16;
		final int BLOCK_SIZE = 10;
		//
		// get parameters
		int pg = Common.getParameter(request, "pg", 1);
		int skip = (pg - 1) * PAGE_SIZE;
		String findMaster = Common.getParameter(request, "findMaster", "");
		String findMethod = Common.getParameter(request, "findMethod", "");
//		String findStr = Common.getParameter(request, "findStr", "");
		String findStr2 = Common.getParameter(request, "findStr", "");
		
		String findStr = findStr2.replaceAll(" ", "");	
		
		String findCategory = Common.getParameter(request, "findCategory", findMaster);
		String pathName = multimediaService.getMasterCategoryName(findMaster);
		
		// check validation
		if( Validate.isEmpty(findMaster)
				|| !multimediaService.isAccessMultimedia(findMaster))
			throw new CommonException("정상적인 접근이 아닙니다." , CommonException.HISTORY_BACK);
		
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
		List<Category> categories = (List<Category>) categoryService.getChildrenWithMultimediaCount(findMaster);
		List<Category> subCategories = ( Validate.isEmpty(findCategory) || findCategory.length() == 4 ) ? new ArrayList<Category>() : (List<Category>) categoryService.getChildrenWithMultimediaCount(findCategory.substring(0,7));
		
		// set pagination
		FrontPagination pagination = new FrontPagination(pg, PAGE_SIZE, BLOCK_SIZE, totalCount);
		pagination.initialize();
		
		String currentPathName = "이미지자료";
		
		if( findMaster.equals("E001") )
			currentPathName = "플래시자료";
		else if( findMaster.equals("E002"))
			currentPathName = "동영상자료";
			
		// assign
		model.addAttribute("beforePath", "나눔 &middot; 소통 &gt; 멀티미디어 자료실");
		model.addAttribute("currentPath", currentPathName);
		model.addAttribute("pathName", pathName);
		
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("articleNum", articleNum);
		model.addAttribute("list", list);
		model.addAttribute("categories", categories);
		model.addAttribute("subCategories", subCategories);
		model.addAttribute("paging", pagination.print());
		
		model.addAttribute("pg", pg);
		model.addAttribute("findMaster", findMaster);
		model.addAttribute("findCategory", findCategory);
		model.addAttribute("findMethod", findMethod);
		model.addAttribute("findStr", findStr);
		
		return "front/thub/open/multimedia/list";
	}
	
	/**
	 * 멀티미디어 자료 상세화면
	 * 각 메뉴에 대한 구분값은 findMaster 값.
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/multimedia/detail.do")
	public String detail(HttpServletRequest request , Model model) throws Exception
	{		
		// 조회권한 체크
		HttpSession session = (HttpSession) request.getSession();
		Member member = (Member) session.getAttribute("member");
		
		if( member == null ){
			throw new CommonException("로그인 후 조회 가능합니다." ,"http://cs.kumsung.co.kr/",CommonException.HISTORY_BACK+1);
		}
			
		
		// get parameters
		int mmId = Common.getParameter(request, "mmId", 0);
		int pg = Common.getParameter(request, "pg", 1);
		String findMaster = Common.getParameter(request, "findMaster", "");
		String findMethod = Common.getParameter(request, "findMethod", "");
//		String findStr = Common.getParameter(request, "findStr", "");
		String findStr2 = Common.getParameter(request, "findStr", "");
		
		String findStr = findStr2.replaceAll(" ", "");    
		
		String findCategory = Common.getParameter(request, "findCategory", findMaster);
		String pathName = multimediaService.getMasterCategoryName(findMaster);
		
		// check validation
		if( Validate.isEmpty(findMaster)
				|| !multimediaService.isAccessMultimedia(findMaster)
				|| mmId == 0)
			throw new CommonException("정상적인 접근이 아닙니다." , CommonException.HISTORY_BACK);
		
		// make query parameters
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("mmId" , mmId);
		params.put("findCategory" , findCategory);
		params.put("findMethod" , findMethod);
		params.put("findStr" , findStr);
		params.put("findIsUse" , "Y");			// 프론트 이므로 활성화 된 것만...
		
		// get data
		Multimedia multimedia = (Multimedia) multimediaService.getMultimediaDetail(mmId);
		Multimedia prevMultimedia = (Multimedia) multimediaService.getPrevMultimedia(params);
		Multimedia nextMultimedia = (Multimedia) multimediaService.getNextMultimedia(params);
		
		String currentPathName = "이미지자료";
		
		if( findMaster.equals("E001") )
			currentPathName = "플래시자료";
		else if( findMaster.equals("E002"))
			currentPathName = "동영상자료";
		
		// assign
		model.addAttribute("beforePath", "나눔 &middot; 소통");
		model.addAttribute("currentPath", currentPathName);
		model.addAttribute("pathName", pathName);
		
		model.addAttribute("multimedia", multimedia);
		model.addAttribute("prevMultimedia", prevMultimedia);
		model.addAttribute("nextMultimedia", nextMultimedia);
		
		model.addAttribute("pg", pg);
		model.addAttribute("findMaster", findMaster);
		model.addAttribute("findCategory", findCategory);
		model.addAttribute("findMethod", findMethod);
		model.addAttribute("findStr", findStr);
		
		return "front/thub/open/multimedia/detail";
	}
	
	/**
	 * 자료나눔터 > 교과활용자료
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/share/utilize.do")
	public String shareUtilize(HttpServletRequest request , Model model) throws Exception
	{	
		final int BOARD_ID = 54;
		Map<String,Object> boardAttrs = boardService.getBoardDataAttributes(request, BOARD_ID);
		model.addAllAttributes(boardAttrs);
		
		// assign
		model.addAttribute("beforePath", "나눔 &middot; 소통");
		model.addAttribute("currentPath", "티칭허브&#8314;자료");
		
		return "front/thub/open/share/utilize";
	}
	
	/**
	 * 나눔 소통 > 티칭허브 카드뉴스
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/share/cardnews.do")
	public String cardnews(HttpServletRequest request , Model model) throws Exception
	{	
		final int BOARD_ID = 55;
		Map<String,Object> boardAttrs = boardService.getBoardDataAttributes(request, BOARD_ID);
		model.addAllAttributes(boardAttrs);
		
		// assign
		model.addAttribute("beforePath", "나눔 &middot; 소통");
		model.addAttribute("currentPath", "티칭허브 카드뉴스");
		
		return "front/thub/open/share/cardnews";
	}
	
	/**
	 * 자료나눔터 > 학급운영자료
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/share/classes.do")
	public String shareClasses(HttpServletRequest request , Model model) throws Exception
	{
		final int BOARD_ID = 3;
		Map<String,Object> boardAttrs = boardService.getBoardDataAttributes(request, BOARD_ID);
		model.addAllAttributes(boardAttrs);
		
		// assign
		model.addAttribute("beforePath", "나눔 &middot; 소통");
		model.addAttribute("currentPath", "학급운영자료");
		
		return "front/thub/open/share/classes";
	}
	
	/**
	 * 자료 원고 보내기
	 */
	@RequestMapping("/share/send.do")
	public String shareSend(HttpServletRequest request , Model model) throws Exception
	{	
		HttpSession session = (HttpSession) request.getSession();
		Member member = (Member) session.getAttribute("member");
		
		if( member == null )
			throw new CommonException("로그인 후 이용가능합니다."  ,"http://cs.kumsung.co.kr/",CommonException.HISTORY_BACK+1);
		
		// get parameter
		int boardId = Common.getParameter(request, "boardId", 0);
		
		if( boardId == 0 )
			throw new CommonException("정상적인 접근이 아닙니다." , CommonException.HISTORY_BACK);
		
		// get board category
		List<BoardCategory> categories = (List<BoardCategory>) boardService.getBoardCategoryList(2);
		
		// assign
		model.addAttribute("boardId", boardId);
		model.addAttribute("categories", categories);
		
		return "front/thub/open/share/send";
	}
	
	/**
	 * 자료 원고 보내기 액션
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/share/sendAction.do" , method=RequestMethod.POST)
	public String shareSendAction(HttpServletRequest request , Model model) throws Exception
	{
		HttpSession session = (HttpSession) request.getSession();
		Member member = (Member) session.getAttribute("member");
		
		if( member == null )
			throw new CommonException("로그인 후 이용가능합니다." ,"http://cs.kumsung.co.kr/",CommonException.HISTORY_BACK+1);
		
		// get parameters
		String title = Common.getParameter(request, "title", "");
		int boardId = Common.getParameter(request, "boardId", 0);
		int categoryId = Common.getParameter(request, "categoryId", 0);
		String contents = Common.getParameterNoXSS(request, "contents", "");
		
		String[] fileNames = Common.getParameters(request, "fileName");
		String[] dataFiles = Common.getParameters(request, "dataFile");
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		if(fileNames != null){
			for(int i=0; i<fileNames.length; i++){
				params.put("fileName"+(i+1),fileNames[i]);
			}
		}
		if(dataFiles != null){
			for(int i=0; i<dataFiles.length; i++){
				params.put("dataFile"+(i+1),dataFiles[i]);
			}
		}
		
		params.put("title", title);
		params.put("boardId", boardId);
		params.put("categoryId", categoryId);
		params.put("contents", contents);
		params.put("userId", member.getUserId());
		
		int sendId = customerService.insertSend(params);
		
		// 메일 발송을 위하여 데이타를 다시 가지고 온다.
		Send send = customerService.getBoardSend(sendId);
		
		String mailSubject = "[" + send.getBoardName() + "] 자료원고가 도착하였습니다.";
		String mailContents = "보낸사람 : " + send.getUserName() + "(" + send.getUserId() + ")<br/><br/>제목 : " + send.getTitle() + "<br/>분류 : " + send.getBoardName() + "<br/><br/><br/><br/><br/>" + send.getContents() + "<br/><br/><br/><br/><br/>관리자 모드에서 상세내용을 확인하여주십시오.";
		
		memberService.sendEmail("kstext@kumsung.co.kr", mailSubject, mailContents);
		
		// assign
		model.addAttribute("beforePath", "나눔 &middot; 소통 &gt; 교사연수지원");
		model.addAttribute("currentPath", "쉼터");
		model.addAttribute("pathName", "테스트");
		
		return "redirect:sendComplete.do?boardId=" + boardId;
	}
	
	/**
	 * 자료 원고 전송 완료 페이지
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/share/sendComplete.do")
	public String shareSendComplete(HttpServletRequest request , Model model) throws Exception
	{
		int boardId = Common.getParameter(request, "boardId", 0);
		
		// assign
		model.addAttribute("boardId", boardId);
		
		return "front/thub/open/share/sendComplete";
	}
	
	/**
	 * 창의적 체험활동 > 진로직업활동
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/activity/course.do")
	public String activityCourse(HttpServletRequest request , Model model) throws Exception
	{
		final int BOARD_ID = 4;
		Map<String,Object> boardAttrs = boardService.getBoardDataAttributes(request, BOARD_ID);
		model.addAllAttributes(boardAttrs);
		
		// assign
		model.addAttribute("beforePath", "열린자료마당 &gt; 창의적 체험활동");
		model.addAttribute("currentPath", "진로·직업활동");
		
		return "front/thub/open/activity/course";
	}
	
	/**
	 * 창의적 체험활동 > 소개 1 ~ 5
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/activity/introduce/page1.do")
	public String activityIntroducePage1(HttpServletRequest request , Model model) throws Exception
	{	
		// assign
		model.addAttribute("beforePath", "열린자료마당 &gt; 창의적 체험활동");
		model.addAttribute("currentPath", "체험활동 소개");
		
		return "front/thub/open/activity/introduce/page1";
	}
	
	@RequestMapping("/activity/introduce/page2.do")
	public String activityIntroducePage2(HttpServletRequest request , Model model) throws Exception
	{	
		// assign
		model.addAttribute("beforePath", "열린자료마당 &gt; 창의적 체험활동");
		model.addAttribute("currentPath", "체험활동 소개");
		
		return "front/thub/open/activity/introduce/page2";
	}
	
	@RequestMapping("/activity/introduce/page3.do")
	public String activityIntroducePage3(HttpServletRequest request , Model model) throws Exception
	{	
		// assign
		model.addAttribute("beforePath", "열린자료마당 &gt; 창의적 체험활동");
		model.addAttribute("currentPath", "체험활동 소개");
		
		return "front/thub/open/activity/introduce/page3";
	}
	
	@RequestMapping("/activity/introduce/page4.do")
	public String activityIntroducePage4(HttpServletRequest request , Model model) throws Exception
	{	
		// assign
		model.addAttribute("beforePath", "열린자료마당 &gt; 창의적 체험활동");
		model.addAttribute("currentPath", "체험활동 소개");
		
		return "front/thub/open/activity/introduce/page4";
	}
	
	@RequestMapping("/activity/introduce/page5.do")
	public String activityIntroducePage5(HttpServletRequest request , Model model) throws Exception
	{	
		// assign
		model.addAttribute("beforePath", "열린자료마당 &gt; 창의적 체험활동");
		model.addAttribute("currentPath", "체험활동 소개");
		
		return "front/thub/open/activity/introduce/page5";
	}
	
	/**
	 * 창의적 체험활동 > 예시자료
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/activity/example.do")
	public String activityExample(HttpServletRequest request , Model model) throws Exception
	{
		final int BOARD_ID = 4;
		Map<String,Object> boardAttrs = boardService.getBoardDataAttributes(request, BOARD_ID);
		model.addAllAttributes(boardAttrs);
		
		// assign
		model.addAttribute("beforePath", "열린자료마당 &gt; 창의적 체험활동");
		model.addAttribute("currentPath", "예시자료");
		
		return "front/thub/open/activity/example";
	}
	
	/**
	 * 창의적 체험활동 > 서식자료
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/activity/form.do")
	public String activityForm(HttpServletRequest request , Model model) throws Exception
	{
		final int BOARD_ID = 5;
		Map<String,Object> boardAttrs = boardService.getBoardDataAttributes(request, BOARD_ID);
		model.addAllAttributes(boardAttrs);
		
		// assign
		model.addAttribute("beforePath", "열린자료마당 &gt; 창의적 체험활동");
		model.addAttribute("currentPath", "서식자료");
		
		return "front/thub/open/activity/form";
	}
	
	/**
	 * 창의적 체험활동 > 참고사이트
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/activity/site.do")
	public String activitySite(HttpServletRequest request , Model model) throws Exception
	{
		final int BOARD_ID = 6;
		Map<String,Object> boardAttrs = boardService.getBoardDataAttributes(request, BOARD_ID);
		model.addAllAttributes(boardAttrs);
		
		// assign
		model.addAttribute("beforePath", "열린자료마당 &gt; 창의적 체험활동");
		model.addAttribute("currentPath", "참고사이트");
		
		return "front/thub/open/activity/serve";
	}
	
	/**
	 * 창의적 체험활동 > 체험활동 서식자료
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/activity/formula.do")
	public String activityFormula(HttpServletRequest request , Model model) throws Exception
	{
		final int BOARD_ID = 8;
		Map<String,Object> boardAttrs = boardService.getBoardDataAttributes(request, BOARD_ID);
		model.addAllAttributes(boardAttrs);
		
		// assign
		model.addAttribute("beforePath", "열린자료마당 &gt; 창의적 체험활동");
		model.addAttribute("currentPath", "체험활동 서식자료");
		
		return "front/thub/open/activity/formula";
	}
	
	/**
	 * 교실밖교과서 > 역사전시관
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/outer/history.do") 
	public String outerHistory(HttpServletRequest request , Model model) throws Exception {	
        int PAGE_SIZE = 12;
        int BLOCK_SIZE = 10;
        int pg = Common.getParameter(request, "pg", 1);
        int skip = (pg - 1) * 12;
        String findMaster = Common.getParameter(request, "findMaster", "F001");
        String findExhibit = Common.getParameter(request, "findExhibit", "F001001001");
        String findMethod = Common.getParameter(request, "findMethod", "");
        String findStr2 = Common.getParameter(request, "findStr", "");
        String findStr = findStr2.replaceAll(" ", "");
        if(findMaster.equals("F002") && findExhibit.equals("F001001001")) {
        	findExhibit = "F002001001";
        }
        String findSecondCategory = findExhibit.substring(0, 7);
        List firstCategories = categoryService.getChildren(findMaster);
        List secondCategories = categoryService.getChildren(findSecondCategory);
        if(findExhibit.length() == 7 && secondCategories.size() > 0){
            Category category = (Category)secondCategories.get(0);
            findExhibit = category.getCategory();
        }
        Map params = new HashMap();
        params.put("findExhibit", findExhibit);
        params.put("findMethod", findMethod);
        params.put("findStr", findStr);
        params.put("skip", Integer.valueOf(skip));
        params.put("pageSize", Integer.valueOf(12));
        int totalCount = Integer.valueOf(smartService.getHeadwordListCount(params)).intValue();
        int articleNum = totalCount - skip;
        List headwords = smartService.getHeadwordList(params);
        FrontPagination pagination = new FrontPagination(pg, 12, 10, totalCount);
        pagination.initialize();
		model.addAttribute("beforePath", "나눔 &middot; 소통 &gt; 교실밖교과서");
		model.addAttribute("currentPath", "역사전시관");
        model.addAttribute("pg", Integer.valueOf(pg));
        model.addAttribute("findMaster", findMaster);
        model.addAttribute("findExhibit", findExhibit);
        model.addAttribute("findSecondCategory", findSecondCategory);
        model.addAttribute("findMethod", findMethod);
        model.addAttribute("findStr", findStr);
        model.addAttribute("firstCategories", firstCategories);
        model.addAttribute("secondCategories", secondCategories);
        model.addAttribute("totalCount", Integer.valueOf(totalCount));
        model.addAttribute("articleNum", Integer.valueOf(articleNum));
        model.addAttribute("headwords", headwords);
        model.addAttribute("paging", pagination.print());
        return "front/thub/open/outer/history";
	}
	
	/**
	 * 교실밖교과서 > 역사전시관 상세페이지
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/outer/historyDetail.do")
	public String outerHistoryDetail(HttpServletRequest request , Model model) throws Exception {	
        int pg = Common.getParameter(request, "pg", 1);
        int headwordId = Common.getParameter(request, "headwordId", 0);
        String findMaster = Common.getParameter(request, "findMaster", "F001");
        String findExhibit = Common.getParameter(request, "findExhibit", "F001001001");
        String findMethod = Common.getParameter(request, "findMethod", "");
        String findStr2 = Common.getParameter(request, "findStr", "");
        String findStr = findStr2.replaceAll(" ", "");
        
        if(findMaster.equals("F002") && findExhibit.equals("F001001001")) {
        	findExhibit = "F002001001";
        }
        
        String findSecondCategory = findExhibit.substring(0, 7);
        
        if(headwordId == 0) {
        	throw new CommonException("정상적인 접근이 아닙니다." , CommonException.HISTORY_BACK);
        }
            
        smartService.updateHeadwordHit(headwordId);
        Headword headword = smartService.getHeadword(headwordId);
        List relationHeadwords = smartService.getRelationHeadwordList(headwordId);
		model.addAttribute("beforePath", "열린자료마당 &gt; 교실밖교과서");
		model.addAttribute("currentPath", "역사전시관");
        Map params = new HashMap();
        params.put("headwordId", Integer.valueOf(headwordId));
        params.put("findExhibit", findExhibit);
        Headword prevHeadword = smartService.getPrevHeadword(params);
        Headword nextHeadword = smartService.getNextHeadword(params);
        if(prevHeadword == null)
            prevHeadword = null;
        if(nextHeadword == null)
            nextHeadword = null;
        model.addAttribute("prevHeadword", prevHeadword);
        model.addAttribute("nextHeadword", nextHeadword);
        model.addAttribute("pg", Integer.valueOf(pg));
        model.addAttribute("findMaster", findMaster);
        model.addAttribute("findExhibit", findExhibit);
        model.addAttribute("findSecondCategory", findSecondCategory);
        model.addAttribute("findMethod", findMethod);
        model.addAttribute("findStr", findStr);
        model.addAttribute("headword", headword);
        model.addAttribute("relationHeadwords", relationHeadwords);
        return "front/thub/open/outer/historyDetail";
	}
	
	/**
	 * 교실밖교과서 > 인물사건 캘린더
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/outer/calendar.do")
	public String outerCalendar(HttpServletRequest request , Model model) throws Exception
	{	
		model.addAllAttributes( smartService.calendarData(request) );
		
		return "front/thub/open/outer/calendar";
	}
	
	/**
	 * 인물/사건 캘린더 상세
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/outer/calendarDetail.do")
	public String outerCalendarDetail(HttpServletRequest request , Model model) throws Exception
	{
		// get parameters
		int headwordId = Common.getParameter(request, "headwordId", 0);
		String findYear = Common.getParameter(request, "findYear", "");
		String findMonth = Common.getParameter(request, "findMonth", "");
		String findDay = Common.getParameter(request, "findDay", "");
		int weekCount = Common.getParameter(request, "weekCount", 0);		// 보여질 주차
		
		// get data
		Headword headword = (Headword) smartService.getHeadword(headwordId);
		
		if( weekCount == 0 )
		{	
			Calendar calendar = new GregorianCalendar();
			calendar.set( Integer.valueOf(findYear) , Integer.valueOf(findMonth) - 1 , Integer.valueOf(findDay));
			weekCount = calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH);
		}
		
		//조회수증가
		if(headwordId > 0) smartService.updateHeadwordHit(headwordId);
		
		// assign
		model.addAllAttributes( smartService.calendarData(request));
		
		model.addAttribute("headwordId", headwordId);
		model.addAttribute("findYear", findYear);
		model.addAttribute("findMonth", findMonth);
		model.addAttribute("weekCount", weekCount);
		
		model.addAttribute("headword", headword);
		
		model.addAttribute("beforePath", "나눔 &middot; 소통");
		model.addAttribute("currentPath", "오늘의 역사");
		
		return "front/thub/open/outer/calendarDetail";
	}
	
	/**
	 * 교실밖교과서 > 신나는 교과서여행
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/outer/travel.do")
	public String outerTravel(HttpServletRequest request , Model model) throws Exception
	{	
		final int BOARD_ID = 9;
		Map<String,Object> boardAttrs = boardService.getBoardDataAttributes(request, BOARD_ID);
		model.addAllAttributes(boardAttrs);
		
		// assign
		model.addAttribute("beforePath", "열린자료마당 &gt; 교실밖교과서");
		model.addAttribute("currentPath", "신나는 교과서여행");
		
		return "front/thub/open/outer/travel";
	}
	
	/**
	 * 교실밖교과서 > 재밌는 교과서영화
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/outer/movie.do")
	public String outerMovie(HttpServletRequest request , Model model) throws Exception
	{	
		final int BOARD_ID = 10;
		Map<String,Object> boardAttrs = boardService.getBoardDataAttributes(request, BOARD_ID);
		model.addAllAttributes(boardAttrs);
		
		// assign
		model.addAttribute("beforePath", "열린자료마당 &gt; 교실밖교과서");
		model.addAttribute("currentPath", "재밌는 교과서영화");
		
		return "front/thub/open/outer/movie";
	}
	
	/**
	 * 유용한 링크집
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/multimedia/links.do")
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
		model.addAttribute("beforePath", "나눔 &middot; 소통");
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
		
		return "front/thub/open/multimedia/links";
	}
	
	/**
	 * 파일 업로드
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 * */
	@RequestMapping("/share/sendFileUpload.do")
	public String customerUpload(HttpServletRequest request, Model model,
			@RequestParam("upfile")MultipartFile attachFile ) throws Exception
	{
		HttpSession session = (HttpSession) request.getSession();
		Member member = (Member) session.getAttribute("member");
		
		if( member == null )
			throw new CommonException("로그인 후 이용가능합니다."  ,"http://cs.kumsung.co.kr/",CommonException.HISTORY_BACK+1);
		
		FileInfo fileInfo = new FileInfo(); 
		
		if(!attachFile.isEmpty()){
			fileInfo = fileService.upload(attachFile, serverFilePath, "attachFile");
			fileInfo.getOriginName().substring(fileInfo.getOriginName().lastIndexOf(".") + 1 , fileInfo.getOriginName().length()).toLowerCase();
		}
		
		model.addAttribute("fileInfo", fileInfo);
		model.addAttribute("msg","aa");
		
		return "fileHandler";
	}
	
	
	/**
	 * 프린트
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/outer/print.do")
	public String print(HttpServletRequest request , Model model) throws Exception
	{	
		// get parameters
		int pg = Common.getParameter(request, "pg", 1);
		int headwordId = Common.getParameter(request, "headwordId", 0);
		String findMaster = Common.getParameter(request, "findMaster" , "F001");
		String findExhibit = Common.getParameter(request, "findExhibit", "F001001001");
		String findMethod = Common.getParameter(request, "findMethod", "");
		String findStr = Common.getParameter(request, "findStr", "");
		System.out.println("headwordId = "+request.getParameter("headwordId"));
		// 세계관일 경우 findCategory가 없다면 세계관의 2차 분류 첫번째의 데이타로 디폴트 셋팅.
		if( findMaster.equals("F002") && findExhibit.equals("F001001001") )
			findExhibit = "F002001001";
		
		// get secondary category  
		String findSecondCategory = findExhibit.substring(0,7);
		
		if( headwordId == 0 )
			throw new CommonException("정상적인 접근이 아닙니다." , CommonException.HISTORY_BACK);

		// get data
		Headword headword = (Headword) smartService.getHeadword(headwordId);
		List<RelationHeadword> relationHeadwords = smartService.getRelationHeadwordList(headwordId);
		
		// assign
		model.addAttribute("beforePath", "열린자료마당 &gt; 교실밖교과서");
		model.addAttribute("currentPath", "역사전시관");
		// make query parameters
		Map<String,Object> params = new HashMap<String,Object>();
		
		// add articleId
		params.put("headwordId" , headwordId);
		
		
		//이전글 다음글
		Headword prevHeadword = (Headword) smartService.getPrevHeadword(params);
		Headword nextHeadword = (Headword) smartService.getNextHeadword(params);

		if( prevHeadword == null ){
			prevHeadword = null;
		}

		if( nextHeadword == null ){
			nextHeadword = null;
		}
		
		model.addAttribute("prevHeadword" , prevHeadword);
		model.addAttribute("nextHeadword" , nextHeadword);
		/////////
		
		model.addAttribute("pg", pg);
		model.addAttribute("findMaster", findMaster);
		model.addAttribute("findExhibit", findExhibit);
		model.addAttribute("findSecondCategory", findSecondCategory);
		model.addAttribute("findMethod", findMethod);
		model.addAttribute("findStr", findStr);
		
		model.addAttribute("headword", headword);
		model.addAttribute("relationHeadwords", relationHeadwords);
		
		return "front/thub/open/outer/print_page";
	}
	
	
	/**
	 * 교실밖교과서 > 놀라운 과학이야기
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/outer/science.do")
	public String outerScience(HttpServletRequest request , Model model) throws Exception
	{	
		final int BOARD_ID = 25;
		Map<String,Object> boardAttrs = boardService.getBoardDataAttributes(request, BOARD_ID);
		model.addAllAttributes(boardAttrs);
		
		// assign
		model.addAttribute("beforePath", "열린자료마당 &gt; 교실밖교과서");
		model.addAttribute("currentPath", "놀라운 과학이야기");
		
		return "front/thub/open/outer/science";
	}
	
	/**
	 * 선생님 참여 게시판(초등)
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	/*@RequestMapping("/teacher/elementary.do")
	public String elementary(HttpServletRequest request , Model model) throws Exception
	{	
		final int BOARD_ID = 27;
		Map<String,Object> boardAttrs = boardService.getBoardDataAttributes(request, BOARD_ID);
		model.addAllAttributes(boardAttrs);
		
		// assign
		model.addAttribute("beforePath", "열린자료마당");
		model.addAttribute("currentPath", "초등");	
		
		return "front/thub/open/teacher/elementary";
	}
	*/
	/**
	 * 선생님 참여 게시판(중등)
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	/*@RequestMapping("/teacher/middle.do")
	public String middle(HttpServletRequest request , Model model) throws Exception
	{	
		final int BOARD_ID = 28;
		Map<String,Object> boardAttrs = boardService.getBoardDataAttributes(request, BOARD_ID);
		model.addAllAttributes(boardAttrs);
		
		// assign
		model.addAttribute("beforePath", "열린자료마당");
		model.addAttribute("currentPath", "중등");	
		
		return "front/thub/open/teacher/middle";
	}*/
	
	
	/**
	 * 선생님 참여 게시판(고등)
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	/*@RequestMapping("/teacher/high.do")
	public String high(HttpServletRequest request , Model model) throws Exception
	{	
		final int BOARD_ID = 29;
		Map<String,Object> boardAttrs = boardService.getBoardDataAttributes(request, BOARD_ID);
		model.addAllAttributes(boardAttrs);
		
		// assign
		model.addAttribute("beforePath", "열린자료마당");
		model.addAttribute("currentPath", "고등");	
		
		return "front/thub/open/teacher/high";
	}*/
	
	@RequestMapping("/outer/re_history.do") 
	public String re_History(HttpServletRequest request , Model model) throws Exception
	{	
		final int PAGE_SIZE = 12;
		final int BLOCK_SIZE = 10;
		
		// get parameters
		int pg = Common.getParameter(request, "pg", 1);
		int skip = (pg - 1) * PAGE_SIZE;
		String findMaster = Common.getParameter(request, "findMaster" , "F001");
		String findExhibit = Common.getParameter(request, "findExhibit", "F001001001");
		String findMethod = Common.getParameter(request, "findMethod", "");
		String findStr2 = Common.getParameter(request, "findStr", "");
		
		String findStr = findStr2.replaceAll(" ", "");		
		
		// 세계관일 경우 findCategory가 없다면 세계관의 2차 분류 첫번째의 데이타로 디폴트 셋팅.
		if( findMaster.equals("F002") && findExhibit.equals("F001001001") ) {
			findExhibit = "F002001001";
		}
		
		String findSecondCategory = findExhibit.substring(0,7);
		
		List<Category> firstCategories = (List<Category>) categoryService.getChildren(findMaster);
		List<Category> secondCategories = (List<Category>) categoryService.getChildren(findSecondCategory);
		
		if( findExhibit.length() == 7  && secondCategories.size() > 0 ){	
			Category category = secondCategories.get(0);
			findExhibit = category.getCategory();
		}
		
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("findExhibit" , findExhibit);
		params.put("findMethod" , findMethod);
		params.put("findStr" , findStr);
		params.put("skip" , skip);
		params.put("pageSize" , PAGE_SIZE);
		
		// get data
		int totalCount = (Integer) smartService.getHeadwordListCount(params);
		int articleNum = totalCount - skip;
		List<Headword> headwords = (List<Headword>) smartService.getHeadwordList(params);
		FrontPagination pagination = new FrontPagination(pg, PAGE_SIZE, BLOCK_SIZE, totalCount);
		pagination.initialize();
		
		Map<String,Object> boardAttrs = boardService.his_getBoardDataAttributes(request);
		model.addAllAttributes(boardAttrs);
		model.addAttribute("beforePath", "나눔 &middot; 소통");
		model.addAttribute("currentPath", "역사 전시관");
		model.addAttribute("pg", pg);
		model.addAttribute("findMaster", findMaster);
		model.addAttribute("findExhibit", findExhibit);
		model.addAttribute("findSecondCategory", findSecondCategory);
		model.addAttribute("findMethod", findMethod);
		model.addAttribute("findStr", findStr);
		model.addAttribute("firstCategories", firstCategories);
		model.addAttribute("secondCategories", secondCategories);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("articleNum", articleNum);
		model.addAttribute("headwords", headwords);
		model.addAttribute("paging", pagination.print());
		model.addAttribute("boardSkinPath", "front/board/gallery/history_list");	
		
		return "front/thub/open/outer/re_history";
	}
	
	@RequestMapping("/outer/re_historyDetail.do")
	public String re_HistoryDetail(HttpServletRequest request , Model model) throws Exception
	{	
		// get parameters 
		int pg = Common.getParameter(request, "pg", 1);
		int headwordId = Common.getParameter(request, "headwordId", 0);
		String findMaster = Common.getParameter(request, "findMaster" , "F001");
		String findExhibit = Common.getParameter(request, "findExhibit", "F001001001");
		String findMethod = Common.getParameter(request, "findMethod", "");
		String findStr2 = Common.getParameter(request, "findStr", "");
		String findStr = findStr2.replaceAll(" ", "");    		

		if( findMaster.equals("F002") && findExhibit.equals("F001001001") ) {
			findExhibit = "F002001001";
		}
		String findSecondCategory = findExhibit.substring(0,7);
		
		if( headwordId == 0 ) {
			throw new CommonException("정상적인 접근이 아닙니다." , CommonException.HISTORY_BACK);
		}
		
		smartService.updateHeadwordHit(headwordId);

		Headword headword = (Headword) smartService.getHeadword(headwordId);
		List<RelationHeadword> relationHeadwords = smartService.getRelationHeadwordList(headwordId);
		
		model.addAttribute("beforePath", "나눔 &middot; 소통");
		model.addAttribute("currentPath", "역사 전시관");

		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("headwordId" , headwordId);
		params.put("findExhibit" , findExhibit); 
		
		Headword prevHeadword = (Headword) smartService.getPrevHeadword(params);
		Headword nextHeadword = (Headword) smartService.getNextHeadword(params);

		if( prevHeadword == null ){
			prevHeadword = null;
		}

		if( nextHeadword == null ){
			nextHeadword = null;
		}
		
		Map<String,Object> boardAttrs = boardService.hisview_getBoardDataAttributes(request);

		model.addAllAttributes(boardAttrs);
		model.addAttribute("prevHeadword" , prevHeadword);
		model.addAttribute("nextHeadword" , nextHeadword);
		model.addAttribute("pg", pg);
		model.addAttribute("findMaster", findMaster);
		model.addAttribute("findExhibit", findExhibit);
		model.addAttribute("findSecondCategory", findSecondCategory);
		model.addAttribute("findMethod", findMethod);
		model.addAttribute("findStr", findStr);
		model.addAttribute("headword", headword);
		model.addAttribute("relationHeadwords", relationHeadwords);
		
		return "front/thub/open/outer/re_historyDetail";
	}	
	
	@RequestMapping("/outer/special_movie.do")
	public String special_movie(HttpServletRequest request , Model model) throws Exception{
		final int PAGE_SIZE             = 16;
		final int BLOCK_SIZE            = 10;
		int pg                          = Common.getParameter(request, "pg", 1);
		int skip                        = (pg - 1) * PAGE_SIZE;
		String findMaster               = Common.getParameter(request, "findMaster" , "E006");
		String findExhibit              = Common.getParameter(request, "findExhibit", "E006001001");
		String findMethod               = Common.getParameter(request, "findMethod", "");
		String findStr2                 = Common.getParameter(request, "findStr", "");
		String findCategory             = Common.getParameter(request, "findCategory", findExhibit);
		String findStr                  = findStr2.replaceAll(" ", "");
		String pathName                 = multimediaService.getMasterCategoryName(findMaster);
		String findSecondCategory       = findExhibit.substring(0,7);
		String currentPathName          = "";
		List<Category> firstCategories  = (List<Category>) categoryService.getChildren(findMaster);
		List<Category> secondCategories = (List<Category>) categoryService.getChildren(findSecondCategory);		
		Map<String,Object> params       = new HashMap<String,Object>();
		
		params.put("findCategory" , findCategory);
		params.put("findMethod" , findMethod);
		params.put("findStr" , findStr);
		params.put("findIsUse" , "Y");
		params.put("skip" , skip);
		params.put("pageSize" , PAGE_SIZE);
		
		int totalCount = (Integer) multimediaService.getTotalMultimedia(params);
		int articleNum = totalCount - skip;
		List<Multimedia> list = (List<Multimedia>) multimediaService.getMultimediaList(params);
		List<Category> categories = (List<Category>) categoryService.getChildrenWithMultimediaCount(findMaster);
		List<Category> subCategories = ( Validate.isEmpty(findCategory) || findCategory.length() == 4 ) ? new ArrayList<Category>() : (List<Category>) categoryService.getChildrenWithMultimediaCount(findCategory.substring(0,7));
		
		FrontPagination pagination = new FrontPagination(pg, PAGE_SIZE, BLOCK_SIZE, totalCount);
		pagination.initialize();
		
		if( findExhibit.subSequence(0, 7).equals("E006001")) {
			currentPathName = "한국사";
		}
		else if( findExhibit.subSequence(0, 7).equals("E006002")) {
			currentPathName = "세계사";
		}
			
		model.addAttribute("beforePath"        , "나눔 &middot; 소통 &gt; 특별 영상 클립");
		model.addAttribute("currentPath"       , currentPathName);
		model.addAttribute("pathName"          , pathName);
		model.addAttribute("totalCount"        , totalCount);
		model.addAttribute("articleNum"        , articleNum);
		model.addAttribute("list"              , list);
		model.addAttribute("categories"        , categories);
		model.addAttribute("subCategories"     , subCategories);
		model.addAttribute("paging"            , pagination.print());
		model.addAttribute("pg"                , pg);
		model.addAttribute("findMaster"        , findMaster);
		model.addAttribute("findCategory"      , findCategory);
		model.addAttribute("findMethod"        , findMethod);
		model.addAttribute("findStr"           , findStr);
		model.addAttribute("firstCategories"   , firstCategories);
		model.addAttribute("secondCategories"  , secondCategories);
		model.addAttribute("findExhibit"       , findExhibit);
		model.addAttribute("findSecondCategory", findSecondCategory);
		
		return "front/thub/open/outer/special_movie";
	}
	
	@RequestMapping("/outer/tqspecial.do")
	public String tqspecial(HttpServletRequest request , Model model) throws Exception{

		model.addAttribute("beforePath"        , "나눔 &middot; 소통 &gt;");

		return "front/thub/open/outer/tqspecial";
	}	
}