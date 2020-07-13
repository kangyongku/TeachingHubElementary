package kr.co.kumsung.thub.controller.admin;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import kr.co.kumsung.thub.domain.Book;
import kr.co.kumsung.thub.domain.Category;
import kr.co.kumsung.thub.service.CategoryService;
import kr.co.kumsung.thub.service.LearningService;
import kr.co.kumsung.thub.service.SmartService;
import kr.co.kumsung.thub.service.StatsService;
import kr.co.kumsung.thub.setting.Constants;
import kr.co.kumsung.thub.util.Common;
import kr.co.kumsung.thub.util.DateUtil;
import kr.co.kumsung.thub.util.ResultMap;
import kr.co.kumsung.thub.util.Validate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Component("adminStatsController")
@Controller
@RequestMapping("/admin/stats")
public class StatsController {

	@Autowired
	private StatsService statsService;
	
	@Autowired
	private SmartService smartService;
	
	@Autowired
	private LearningService learningService;
	
	@Autowired
	private CategoryService categoryService;
	
	/**
	 * 회원관리 통계
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/memberList.do")
	public String memberList(HttpServletRequest request , Model model) throws Exception
	{
		// get parameters
		String findAuthType = Common.getParameter(request, "findAuthType", "");
		String findStartDate = Common.getParameter(request, "findStartDate", "");
		String findEndDate = Common.getParameter(request, "findEndDate", "");
		
		if( Validate.isEmpty(findStartDate)
				|| Validate.isEmpty(findEndDate))
		{
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, -7);
			java.util.Date weekAgo = cal.getTime();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd" , Locale.getDefault());
			findStartDate = format.format(weekAgo);
			findEndDate = DateUtil.getDate("yyyy-MM-dd");
		}
		
		// make query paraemters
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("findAuthType", findAuthType);
		params.put("findStartDate", findStartDate);
		params.put("findEndDate", findEndDate);
		
		// get data
		int totalCount = (Integer) statsService.getTotalMemberStat();
		int currentTotalCount = 0;
		List<ResultMap> list = (List<ResultMap>) statsService.getMemberStat(params);
		
		// 현재 검색된 데이타의 총합을 구한다.
		// percentage를 계산하기 위함.
		if( list.size() > 0 )
		{ 
			for(ResultMap item : list){
				currentTotalCount += item.getInt("cnt");
			}
		}
		
		// assign
		model.addAttribute("pathTitle", "회원통계");
		model.addAttribute("path", "통계관리");
		
		model.addAttribute("findAuthType", findAuthType);
		model.addAttribute("findStartDate", findStartDate);
		model.addAttribute("findEndDate", findEndDate);
		
		model.addAttribute("totalCount"	, totalCount);
		model.addAttribute("currentTotalCount"	, currentTotalCount);
		model.addAttribute("list"	, list);
		
		return "admin/stats/memberList";
	}
	
	
	/**
	 * 탈퇴회원관리 통계
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/memberSecederList.do")
	public String gMemberSeceder(HttpServletRequest request , Model model) throws Exception
	{
		// get parameters
		String findAuthType = Common.getParameter(request, "findAuthType", "");
		String findStartDate = Common.getParameter(request, "findStartDate", "");
		String findEndDate = Common.getParameter(request, "findEndDate", "");
		
		if( Validate.isEmpty(findStartDate)
				|| Validate.isEmpty(findEndDate))
		{
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, -7);
			java.util.Date weekAgo = cal.getTime();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd" , Locale.getDefault());
			findStartDate = format.format(weekAgo);
			findEndDate = DateUtil.getDate("yyyy-MM-dd");
		}
		
		// make query paraemters
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("findAuthType", findAuthType);
		params.put("findStartDate", findStartDate);
		params.put("findEndDate", findEndDate);
		
		// get data
		int totalCount = (Integer) statsService.getTotalMemberSeceder();
		int currentTotalCount = 0;
		List<ResultMap> list = (List<ResultMap>) statsService.getMemberSeceder(params);
		
		// 현재 검색된 데이타의 총합을 구한다.
		// percentage를 계산하기 위함.
		if( list.size() > 0 )
		{ 
			for(ResultMap item : list){
				currentTotalCount += item.getInt("cnt");
			}
		}
		
		// assign
		model.addAttribute("pathTitle", "회원통계");
		model.addAttribute("path", "통계관리");
		
		model.addAttribute("findAuthType", findAuthType);
		model.addAttribute("findStartDate", findStartDate);
		model.addAttribute("findEndDate", findEndDate);
		
		model.addAttribute("totalCount"	, totalCount);
		model.addAttribute("currentTotalCount"	, currentTotalCount);
		model.addAttribute("list"	, list);
		
		return "admin/stats/memberSecederList";
	}	
	/**
	 * 티칭허브 컨텐츠관리 > 교수학습자료
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/hub/learningList.do")
	public String hubLearningList(HttpServletRequest request , Model model) throws Exception
	{
		// get parameters
		String findCategory = Common.getParameter(request, "findCategory", "");
		String findBookId = Common.getParameter(request, "findBookId", "");
		String findStartDate = Common.getParameter(request, "findStartDate", "");
		String findEndDate = Common.getParameter(request, "findEndDate", "");
		String findType = Common.getParameter(request, "findType", "");
		
		if( Validate.isEmpty(findStartDate)
				|| Validate.isEmpty(findEndDate))
		{
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, -7);
			java.util.Date weekAgo = cal.getTime();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd" , Locale.getDefault());
			findStartDate = format.format(weekAgo);
			findEndDate = DateUtil.getDate("yyyy-MM-dd");
		}
		
		// make query parameters
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("findType" , findType);
		params.put("findCategory" , findCategory);
		params.put("findBookId" , findBookId);
		params.put("findStartDate" , findStartDate);
		params.put("findEndDate" , findEndDate);
		
		// get data
		List<Category> types = categoryService.getChildren("D");
		List<Category> categories = categoryService.getChildren(Constants.LEARNING_CATEGORY);
		List<Book> books = learningService.getBookListByCategory(findCategory);
		
		// 총 갯수를 구한다.
		int totalCount = (Integer) statsService.getTotalHubContentStat();
		int currentTotalCount = 0;
		List<ResultMap> list = statsService.getHubContentStat(params);
		
		for(ResultMap item : list)
			currentTotalCount += item.getInt("cnt");
		
		// assign
		model.addAttribute("pathTitle", "교수학습자료");
		model.addAttribute("path", "통계관리 > 티칭허브 콘텐츠 관리");
		
		model.addAttribute("findCategory", findCategory);
		model.addAttribute("findStartDate", findStartDate);
		model.addAttribute("findEndDate", findEndDate);
		model.addAttribute("findBookId", findBookId);
		model.addAttribute("findType", findType);
		
		model.addAttribute("categories", categories);
		model.addAttribute("books", books);
		model.addAttribute("types", types);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("currentTotalCount", currentTotalCount);
		model.addAttribute("list", list);
		
		return "admin/stats/hub/learningList";
	}
	
	/**
	 * 티칭허브 컨텐츠관리 > 통합게시판
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/hub/articleList.do")
	public String hubArticleList(HttpServletRequest request , Model model) throws Exception
	{
		// get parameters
		String findStartDate = Common.getParameter(request, "findStartDate", "");
		String findEndDate = Common.getParameter(request, "findEndDate", "");
		
		if( Validate.isEmpty(findStartDate)
				|| Validate.isEmpty(findEndDate))
		{
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, -7);
			java.util.Date weekAgo = cal.getTime();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd" , Locale.getDefault());
			findStartDate = format.format(weekAgo);
			findEndDate = DateUtil.getDate("yyyy-MM-dd");
		}
		
		// make query parameters
		Map<String,Object> params = new HashMap<String,Object>();

		params.put("findStartDate" , findStartDate);
		params.put("findEndDate" , findEndDate);
		
		// 총 갯수를 구한다.
		int totalCount = (Integer) statsService.getTotalHubArticleStat();
		int currentTotalCount = 0;
		List<ResultMap> list = statsService.getHubArticleStat(params);
		
		for(ResultMap item : list)
			currentTotalCount += item.getInt("cnt");
		
		// assign
		model.addAttribute("pathTitle", "통합게시판");
		model.addAttribute("path", "통계관리 > 티칭허브 콘텐츠 관리");
		
		model.addAttribute("findStartDate", findStartDate);
		model.addAttribute("findEndDate", findEndDate);
		
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("currentTotalCount", currentTotalCount);
		model.addAttribute("list", list);
		
		return "admin/stats/hub/articleList";
	}
	
	/**
	 * 티칭허브 컨텐츠관리 > 스크랩
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/hub/scrapList.do")
	public String hubScrapList(HttpServletRequest request , Model model) throws Exception
	{
		// get parameters
		String findStartDate = Common.getParameter(request, "findStartDate", "");
		String findEndDate = Common.getParameter(request, "findEndDate", "");
		
		if( Validate.isEmpty(findStartDate)
				|| Validate.isEmpty(findEndDate))
		{
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, -7);
			java.util.Date weekAgo = cal.getTime();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd" , Locale.getDefault());
			findStartDate = format.format(weekAgo);
			findEndDate = DateUtil.getDate("yyyy-MM-dd");
		}
		
		// make query parameters
		Map<String,Object> params = new HashMap<String,Object>();

		params.put("findStartDate" , findStartDate);
		params.put("findEndDate" , findEndDate);
		
		// 총 갯수를 구한다.
		int totalCount = (Integer) statsService.getTotalHubScrapStat();
		int currentTotalCount = 0;
		List<ResultMap> list = statsService.getHubScrapStat(params);
		
		for(ResultMap item : list)
			currentTotalCount += item.getInt("cnt");
		
		// assign
		model.addAttribute("pathTitle", "스크랩 순위");
		model.addAttribute("path", "통계관리 > 티칭허브 콘텐츠 관리");
		
		model.addAttribute("findStartDate", findStartDate);
		model.addAttribute("findEndDate", findEndDate);
		
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("currentTotalCount", currentTotalCount);
		model.addAttribute("list", list);
		
		return "admin/stats/hub/scrapList";
	}
	
	
	/**
	 * 티칭백과 컨텐츠관리 > 학교급별
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/smart/gradeList.do")
	public String smartGradeList(HttpServletRequest request , Model model) throws Exception
	{
		// get parameters
		String findFlag = Common.getParameter(request, "findFlag", "H");
		String findStartDate = Common.getParameter(request, "findStartDate", "");
		String findEndDate = Common.getParameter(request, "findEndDate", "");
		
		if( Validate.isEmpty(findStartDate)
				|| Validate.isEmpty(findEndDate))
		{
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, -7);
			java.util.Date weekAgo = cal.getTime();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd" , Locale.getDefault());
			findStartDate = format.format(weekAgo);
			findEndDate = DateUtil.getDate("yyyy-MM-dd");
		}
		
		// make query parameters
		Map<String,Object> params = new HashMap<String,Object>();

		params.put("findFlag" , findFlag);
		params.put("findStartDate" , findStartDate);
		params.put("findEndDate" , findEndDate);
		
		// 총 갯수를 구한다.
		int totalCount = (Integer) statsService.getTotalSmartStat();
		int currentTotalCount = 0;
		List<ResultMap> list = statsService.getSmartGradeStat(params);
		
		for(ResultMap item : list)
			currentTotalCount += item.getInt("cnt");
		
		// assign
		model.addAttribute("pathTitle", "학교급별");
		model.addAttribute("path", "통계관리 > 티칭백과 콘텐츠 관리");
		
		model.addAttribute("findFlag", findFlag);
		model.addAttribute("findStartDate", findStartDate);
		model.addAttribute("findEndDate", findEndDate);
		
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("currentTotalCount", currentTotalCount);
		model.addAttribute("list", list);
		
		return "admin/stats/smart/gradeList";
	}
	
	/**
	 * 티칭백과 컨텐츠관리 > 교과별
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/smart/subjectList.do")
	public String smartSubjectList(HttpServletRequest request , Model model) throws Exception
	{
		// get parameters
		String findCategory = Common.getParameter(request, "findCategory", "B002");
		String findFlag = Common.getParameter(request, "findFlag", "H");
		String findStartDate = Common.getParameter(request, "findStartDate", "");
		String findEndDate = Common.getParameter(request, "findEndDate", "");
		
		if( Validate.isEmpty(findStartDate)
				|| Validate.isEmpty(findEndDate))
		{
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, -7);
			java.util.Date weekAgo = cal.getTime();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd" , Locale.getDefault());
			findStartDate = format.format(weekAgo);
			findEndDate = DateUtil.getDate("yyyy-MM-dd");
		}
		
		// get data
		List<Category> categories = categoryService.getChildren(Constants.SMART_CATEGORY);
		
		// make query parameters
		Map<String,Object> params = new HashMap<String,Object>();

		params.put("findCategory" , findCategory);
		params.put("findFlag" , findFlag);
		params.put("findStartDate" , findStartDate);
		params.put("findEndDate" , findEndDate);
		
		// 총 갯수를 구한다.
		int totalCount = (Integer) statsService.getTotalSmartStat();
		int currentTotalCount = 0;
		List<ResultMap> list = statsService.getSmartSubjectStat(params);
		
		for(ResultMap item : list)
			currentTotalCount += item.getInt("cnt");
		
		// assign
		model.addAttribute("pathTitle", "학교급별");
		model.addAttribute("path", "통계관리 > 티칭백과 콘텐츠 관리");
		
		model.addAttribute("findCategory", findCategory);
		model.addAttribute("findFlag", findFlag);
		model.addAttribute("findStartDate", findStartDate);
		model.addAttribute("findEndDate", findEndDate);
		
		model.addAttribute("categories", categories);
		
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("currentTotalCount", currentTotalCount);
		model.addAttribute("list", list);
		
		return "admin/stats/smart/subjectList";
	}
	
	/**
	 * 티칭백과 컨텐츠관리 > 도서별
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/smart/bookList.do")
	public String smartBookList(HttpServletRequest request , Model model) throws Exception
	{
		// get parameters
		String findCategory = Common.getParameter(request, "findCategory", "B002");
		String findFlag = Common.getParameter(request, "findFlag", "H");
		String findStartDate = Common.getParameter(request, "findStartDate", "");
		String findEndDate = Common.getParameter(request, "findEndDate", "");
		
		if( Validate.isEmpty(findStartDate)
				|| Validate.isEmpty(findEndDate))
		{
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, -7);
			java.util.Date weekAgo = cal.getTime();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd" , Locale.getDefault());
			findStartDate = format.format(weekAgo);
			findEndDate = DateUtil.getDate("yyyy-MM-dd");
		}
		
		// get data
		List<Category> categories = categoryService.getChildren(Constants.SMART_CATEGORY);
		
		// make query parameters
		Map<String,Object> params = new HashMap<String,Object>();

		params.put("findCategory" , findCategory);
		params.put("findFlag" , findFlag);
		params.put("findStartDate" , findStartDate);
		params.put("findEndDate" , findEndDate);
		
		// 총 갯수를 구한다.
		int totalCount = (Integer) statsService.getTotalSmartStat();
		int currentTotalCount = 0;
		List<ResultMap> list = statsService.getSmartBookStat(params);
		
		for(ResultMap item : list)
			currentTotalCount += item.getInt("cnt");
		
		// assign
		model.addAttribute("pathTitle", "학교급별");
		model.addAttribute("path", "통계관리 > 티칭백과 콘텐츠 관리");
		
		model.addAttribute("findCategory", findCategory);
		model.addAttribute("findFlag", findFlag);
		model.addAttribute("findStartDate", findStartDate);
		model.addAttribute("findEndDate", findEndDate);
		
		model.addAttribute("categories", categories);
		
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("currentTotalCount", currentTotalCount);
		model.addAttribute("list", list);
		
		return "admin/stats/smart/bookList";
	}
	
	/**
	 * CS관라 > 고객문의
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/cs/requestList.do")
	public String csRequestList(HttpServletRequest request , Model model) throws Exception
	{
		// get parameters
		String findStartDate = Common.getParameter(request, "findStartDate", "");
		String findEndDate = Common.getParameter(request, "findEndDate", "");
		
		if( Validate.isEmpty(findStartDate)
				|| Validate.isEmpty(findEndDate))
		{
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, -7);
			java.util.Date weekAgo = cal.getTime();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd" , Locale.getDefault());
			findStartDate = format.format(weekAgo);
			findEndDate = DateUtil.getDate("yyyy-MM-dd");
		}
		
		// make query parameters
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("findStartDate" , findStartDate);
		params.put("findEndDate" , findEndDate);
		
		// 총 갯수를 구한다.
		int totalCount = (Integer) statsService.getTotalCsRequestList(params);
		int currentTotalCount = 0;
		List<ResultMap> list = statsService.getCsRequestList(params);
		
		for(ResultMap item : list)
			currentTotalCount += item.getInt("cnt");
		
		// assign
		model.addAttribute("pathTitle", "고객문의");
		model.addAttribute("path", "통계관리 > CS 관리");
		
		model.addAttribute("findStartDate", findStartDate);
		model.addAttribute("findEndDate", findEndDate);
		
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("currentTotalCount", currentTotalCount);
		model.addAttribute("list", list);
		
		return "admin/stats/cs/requestList";
	}
	
	/**
	 * CS관라 > 고객문의
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/cs/subjectList.do")
	public String csSubjectList(HttpServletRequest request , Model model) throws Exception
	{
		// get parameters
		String findCategory = Common.getParameter(request, "findCategory", "");
		String findStartDate = Common.getParameter(request, "findStartDate", "");
		String findEndDate = Common.getParameter(request, "findEndDate", "");
		
		if( Validate.isEmpty(findStartDate)
				|| Validate.isEmpty(findEndDate))
		{
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, -7);
			java.util.Date weekAgo = cal.getTime();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd" , Locale.getDefault());
			findStartDate = format.format(weekAgo);
			findEndDate = DateUtil.getDate("yyyy-MM-dd");
		}
		
		// get data
		List<Category> categories = categoryService.getChildren(Constants.SMART_CATEGORY);
		
		// make query parameters
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("findCategory" , findCategory);
		params.put("findStartDate" , findStartDate);
		params.put("findEndDate" , findEndDate);
		
		// 총 갯수를 구한다.
		int totalCount = (Integer) statsService.getTotalCsSubjectList(params);
		int currentTotalCount = 0;
		List<ResultMap> list = statsService.getCsSubjectList(params);
		
		for(ResultMap item : list)
			currentTotalCount += item.getInt("cnt");
		
		// assign
		model.addAttribute("pathTitle", "교과서 자료요청");
		model.addAttribute("path", "통계관리 > CS 관리");
		
		model.addAttribute("findCategory", findCategory);
		model.addAttribute("findStartDate", findStartDate);
		model.addAttribute("findEndDate", findEndDate);
		
		model.addAttribute("categories", categories);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("currentTotalCount", currentTotalCount);
		model.addAttribute("list", list);
		
		return "admin/stats/cs/subjectList";
	}
	
	/**
	 * CS관라 > 고객문의
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/cs/contentsList.do")
	public String csContentsList(HttpServletRequest request , Model model) throws Exception
	{
		// get parameters
		String findCategory = Common.getParameter(request, "findCategory", "");
		String findStartDate = Common.getParameter(request, "findStartDate", "");
		String findEndDate = Common.getParameter(request, "findEndDate", "");
		
		if( Validate.isEmpty(findStartDate)
				|| Validate.isEmpty(findEndDate))
		{
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, -7);
			java.util.Date weekAgo = cal.getTime();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd" , Locale.getDefault());
			findStartDate = format.format(weekAgo);
			findEndDate = DateUtil.getDate("yyyy-MM-dd");
		}
		
		// get data
		List<Category> categories = categoryService.getChildren(Constants.SMART_CATEGORY);
		
		// make query parameters
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("findCategory" , findCategory);
		params.put("findStartDate" , findStartDate);
		params.put("findEndDate" , findEndDate);
		
		// 총 갯수를 구한다.
		int totalCount = (Integer) statsService.getTotalCsContentsList(params);
		int currentTotalCount = 0;
		List<ResultMap> list = statsService.getCsContentsList(params);
		
		for(ResultMap item : list)
			currentTotalCount += item.getInt("cnt");
		
		// assign
		model.addAttribute("pathTitle", "교과서 내용문의");
		model.addAttribute("path", "통계관리 > CS 관리");
		
		model.addAttribute("findCategory", findCategory);
		model.addAttribute("findStartDate", findStartDate);
		model.addAttribute("findEndDate", findEndDate);
		
		model.addAttribute("categories", categories);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("currentTotalCount", currentTotalCount);
		model.addAttribute("list", list);
		
		return "admin/stats/cs/contentsList";
	}
	
	/**
	 * 교과설정관리 > 학교급설정
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/setting/gradeList.do")
	public String settingGradeList(HttpServletRequest request , Model model) throws Exception
	{	
		String findAuthType = Common.getParameter(request, "findAuthType", "");
		String findStartDate = Common.getParameter(request, "findStartDate", "");
		String findEndDate = Common.getParameter(request, "findEndDate", "");
		
		if( Validate.isEmpty(findStartDate)
				|| Validate.isEmpty(findEndDate))
		{
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, -7);
			java.util.Date weekAgo = cal.getTime();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd" , Locale.getDefault());
			findStartDate = format.format(weekAgo);
			findEndDate = DateUtil.getDate("yyyy-MM-dd");
		}
		
		// make query parameters
		Map<String,Object> params = new HashMap<String,Object>();	
		params.put("findAuthType" , findAuthType);
		params.put("findStartDate" , findStartDate);
		params.put("findEndDate" , findEndDate);
		
		// 총 갯수를 구한다.
		int totalCount = (Integer) statsService.getTotalSettingList(params);
		int currentTotalCount = 0;
		List<ResultMap> list = statsService.getGradeSettingList(params);
		
		for(ResultMap item : list)
			currentTotalCount += item.getInt("cnt");
		
		// assign
		model.addAttribute("pathTitle", "학교급설정");
		model.addAttribute("path", "통계관리 > 교과설정관리");
		
		model.addAttribute("findAuthType", findAuthType);
		model.addAttribute("findStartDate", findStartDate);
		model.addAttribute("findEndDate", findEndDate);
		
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("currentTotalCount", currentTotalCount);
		model.addAttribute("list", list);
		
		return "admin/stats/setting/gradeList";
	}
	
	/**
	 * 교과설정관리 > 교과설정
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/setting/subjectList.do")
	public String settingSubjectList(HttpServletRequest request , Model model) throws Exception
	{	
		// get parameter
		String findCategory = Common.getParameter(request, "findCategory", "");
		String findAuthType = Common.getParameter(request, "findAuthType", "");
		String findStartDate = Common.getParameter(request, "findStartDate", "");
		String findEndDate = Common.getParameter(request, "findEndDate", "");
		
		if( Validate.isEmpty(findStartDate)
				|| Validate.isEmpty(findEndDate))
		{
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, -7);
			java.util.Date weekAgo = cal.getTime();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd" , Locale.getDefault());
			findStartDate = format.format(weekAgo);
			findEndDate = DateUtil.getDate("yyyy-MM-dd");
		}
		
		// make query parameters
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("findCategory" , findCategory);
		params.put("findAuthType" , findAuthType);
		params.put("findStartDate" , findStartDate);
		params.put("findEndDate" , findEndDate);
		
		// 총 갯수를 구한다.
		int totalCount = (Integer) statsService.getTotalSettingList(params);
		int currentTotalCount = 0;
		List<ResultMap> list = statsService.getSubjectSettingList(params);
		
		for(ResultMap item : list)
			currentTotalCount += item.getInt("cnt");
		
		// assign
		model.addAttribute("pathTitle", "교과설정");
		model.addAttribute("path", "통계관리 > 교과설정관리");
	
		model.addAttribute("findCategory", findCategory);
		model.addAttribute("findAuthType", findAuthType);
		model.addAttribute("findStartDate", findStartDate);
		model.addAttribute("findEndDate", findEndDate);
		
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("currentTotalCount", currentTotalCount);
		model.addAttribute("list", list);
		
		return "admin/stats/setting/subjectList";
	}
	
	/**
	 * 교과설정관리 > 교과서설정
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/setting/bookList.do")
	public String settingBookList(HttpServletRequest request , Model model) throws Exception
	{	
		// get parameter
		String findCategory = Common.getParameter(request, "findCategory", "");
		String findAuthType = Common.getParameter(request, "findAuthType", "");
		String findStartDate = Common.getParameter(request, "findStartDate", "");
		String findEndDate = Common.getParameter(request, "findEndDate", "");
System.out.println("findAuthType>>>>>>>>>>>>>>;;;" + findAuthType);		
		if( Validate.isEmpty(findStartDate)
				|| Validate.isEmpty(findEndDate))
		{
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, -7);
			java.util.Date weekAgo = cal.getTime();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd" , Locale.getDefault());
			findStartDate = format.format(weekAgo);
			findEndDate = DateUtil.getDate("yyyy-MM-dd");
		}
		
		
		List<Category> categories = (List<Category>) categoryService.getChildren(Constants.LEARNING_CATEGORY);
		
		// make query parameters
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("findCategory" , findCategory);
		params.put("findAuthType" , findAuthType);
		params.put("findStartDate" , findStartDate);
		params.put("findEndDate" , findEndDate);
		
		// 총 갯수를 구한다.
		int totalCount = (Integer) statsService.getTotalBookSettingList(params);
		int currentTotalCount = 0;
		List<ResultMap> list = statsService.getBookSettingList(params);
		
		for(ResultMap item : list)
			currentTotalCount += item.getInt("cnt");
		
		// assign
		model.addAttribute("pathTitle", "교과설정");
		model.addAttribute("path", "통계관리 > 교과설정관리");
	
		model.addAttribute("findCategory", findCategory);
		model.addAttribute("findAuthType", findAuthType);
		model.addAttribute("findStartDate", findStartDate);
		model.addAttribute("findEndDate", findEndDate);
		
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("currentTotalCount", currentTotalCount);
		model.addAttribute("list", list);
		model.addAttribute("categories", categories);
		
		return "admin/stats/setting/bookList";
	}
	
	/**
	 * 검색어관리
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/searchList.do")
	public String searchList(HttpServletRequest request , Model model) throws Exception
	{	
		// get parameter
		String findFlag = Common.getParameter(request, "findFlag", "THUB");
		String findStartDate = Common.getParameter(request, "findStartDate", "");
		String findEndDate = Common.getParameter(request, "findEndDate", "");
		
		if( Validate.isEmpty(findStartDate)
				|| Validate.isEmpty(findEndDate))
		{
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, -7);
			java.util.Date weekAgo = cal.getTime();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd" , Locale.getDefault());
			findStartDate = format.format(weekAgo);
			findEndDate = DateUtil.getDate("yyyy-MM-dd");
		}
		
		// make query parameters
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("findFlag" , findFlag);
		params.put("findStartDate" , findStartDate);
		params.put("findEndDate" , findEndDate);
		
		// 총 갯수를 구한다.
		int currentTotalCount = 0;
		List<ResultMap> list = statsService.getSearchWordList(params);
		
		for(ResultMap item : list)
			currentTotalCount += item.getInt("cnt");
		
		// assign
		model.addAttribute("pathTitle", "검색어관리");
		model.addAttribute("path", "통계관리");
	
		model.addAttribute("findFlag", findFlag);
		model.addAttribute("findStartDate", findStartDate);
		model.addAttribute("findEndDate", findEndDate);
		
		model.addAttribute("currentTotalCount", currentTotalCount);
		model.addAttribute("list", list);
		
		return "admin/stats/searchList";
	}
	
	
	/**
	 * 저작도구 다운 관리
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/sdfdownList.do")
	public String sdfdownList(HttpServletRequest request , Model model) throws Exception
	{	
		// get parameter
		String findFlag = Common.getParameter(request, "findFlag", "THUB");
		String findStartDate = Common.getParameter(request, "findStartDate", "");
		String findEndDate = Common.getParameter(request, "findEndDate", "");
		String findType = Common.getParameter(request, "findType", "2");
		String category_1 = Common.getParameter(request, "category_1", "all");
		String category_2 = Common.getParameter(request, "category_2", "all");
		
		if( Validate.isEmpty(findStartDate)
				|| Validate.isEmpty(findEndDate))
		{
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, -7);
			java.util.Date weekAgo = cal.getTime();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd" , Locale.getDefault());
			findStartDate = format.format(weekAgo);
			findEndDate = DateUtil.getDate("yyyy-MM-dd");
		}
		
		// make query parameters
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("findFlag" , findFlag);
		if(findType.equals("1")){
			params.put("category_1" , category_1);
			params.put("category_2" , category_2);
		}else{
			category_1="all";
			category_2="all";
			params.put("findStartDate" , findStartDate);
			params.put("findEndDate" , findEndDate);
		}
		
		// 총 갯수를 구한다.
		int totalCount = (Integer) statsService.getTotalsdfdown();
		int currentTotalCount = 0;
		List<ResultMap> list=null;
		if(findType.equals("1")){
			list = statsService.getSearchDownList1(params);
		}else{
			list = statsService.getSearchDownList(params);
		}
		for(ResultMap item : list)
			currentTotalCount += item.getInt("cnt");
		
		// assign
		System.out.println("category_1 ==== "+category_1);
		model.addAttribute("pathTitle", "저작도구 다운로드 현황");
		model.addAttribute("path", "통계관리");
		model.addAttribute("category_1", category_1);
		model.addAttribute("category_2", category_2);
		model.addAttribute("findFlag", findFlag);
		model.addAttribute("findStartDate", findStartDate);
		model.addAttribute("findEndDate", findEndDate);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("currentTotalCount", currentTotalCount);
		model.addAttribute("list", list);
		
		return "admin/stats/sdfdownList";
	}
}
