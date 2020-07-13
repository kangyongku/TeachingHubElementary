package kr.co.kumsung.thub.controller.admin;

import java.io.File;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import kr.co.kumsung.thub.domain.Book;
import kr.co.kumsung.thub.domain.Category;
import kr.co.kumsung.thub.domain.Comment;
import kr.co.kumsung.thub.domain.FileInfo;
import kr.co.kumsung.thub.domain.Headword;
import kr.co.kumsung.thub.domain.History;
import kr.co.kumsung.thub.domain.Knowledge;
import kr.co.kumsung.thub.domain.Member;
import kr.co.kumsung.thub.domain.Multimedia;
import kr.co.kumsung.thub.domain.Pool;
import kr.co.kumsung.thub.domain.RelationHeadword;
import kr.co.kumsung.thub.domain.Unit;
import kr.co.kumsung.thub.exception.CommonException;
import kr.co.kumsung.thub.service.CategoryService;
import kr.co.kumsung.thub.service.FileService;
import kr.co.kumsung.thub.service.SmartService;
import kr.co.kumsung.thub.service.UnitService;
import kr.co.kumsung.thub.setting.Constants;
import kr.co.kumsung.thub.util.Common;
import kr.co.kumsung.thub.util.Pagination;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * 통합게시판관리 컨트롤러
 * @author mikelim
 *
 */
@Component("adminSmartController")
@Controller
@RequestMapping("/admin/smart")
public class SmartController {

	@Autowired
	private SmartService smartService;
	
	@Autowired
	private UnitService unitService;
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private CategoryService categoryService;
	
	private static final Logger logger = LoggerFactory.getLogger(SmartController.class);
	
	@Value("#{common['file.path']}") String serverFilePath;		// 첨부파일의 root 경로
	
	private static final int PAGE_SIZE = 10;
	private static final int BLOCK_SIZE = 10;
	
	/**
	 * 도서 리스트를 가지고 온다.
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/bookList.do")
	public String bookList(HttpServletRequest request , Model model) throws Exception
	{
		// get parameters
		int pg = Common.getParameter(request, "pg", 1);
		String findCategory = Common.getParameter(request, "findCategory", "");
		String findMethod = Common.getParameter(request, "findMethod", "");
		String findStr = Common.getParameter(request, "findStr", "");
		String findCourse = Common.getParameter(request, "findCourse", "");
		String findIsUse = Common.getParameter(request, "findIsUse", "");
		String findStartDate = Common.getParameter(request, "findStartDate", "");
		String findEndDate = Common.getParameter(request, "findEndDate", "");
		int skip = (pg - 1) * PAGE_SIZE;
		
		// make query parameters
		Map<String,Object> params = new HashMap<String,Object>();
		System.out.println("findStr : "+findStr);
		params.put("pg" , pg);
		params.put("skip" , skip);
		params.put("findCategory" , findCategory);
		params.put("findMethod" , findMethod);
		params.put("findStr" , findStr);
		params.put("findCourse" , findCourse);
		params.put("findIsUse" , findIsUse);
		params.put("findStartDate" , findStartDate);
		params.put("findEndDate" , findEndDate);
		params.put("pageSize" , PAGE_SIZE);
		
		// 관리자 타입에 따라서 조회 권한 부여
		HttpSession session = (HttpSession) request.getSession();
		Member adminMember = (Member) session.getAttribute("adminMember");
		
		if( adminMember.getAuthType().equals("N") )
		{
			String smartAuth = adminMember.getSmartAuth();
			String findSmartAuth = "'" + smartAuth.replaceAll("\\," , "'\\,'") + "'";
			params.put("findSmartAuth" , findSmartAuth);
		}
		
		// get data
		int totalBooks = smartService.getTotalBooks(params);
		List<Book> list = smartService.getBookList(params);
		int articleNum = totalBooks - skip;
		
		// category data
		List<Category> categories = (List<Category>) categoryService.getChildren(Constants.SMART_CATEGORY);
		List<Category> courses = (List<Category>) categoryService.getChildren(Constants.COURSE_CATEGORY);
		
		// pagination
		Pagination pagination = new Pagination(pg , PAGE_SIZE , BLOCK_SIZE , totalBooks);
		pagination.setDelimiter("");
		pagination.setPrevLink("Prev");
		pagination.setNextLink("Next");
		
		// assign
		model.addAttribute("pathTitle", "도서관리");
		model.addAttribute("path", "티칭백과 > 도서관리");

		model.addAttribute("pg", pg);
		model.addAttribute("findCategory", findCategory);
		model.addAttribute("findMethod", findMethod);
		model.addAttribute("findStr", findStr);
		model.addAttribute("findCourse", findCourse);
		model.addAttribute("findIsUse", findIsUse);
		model.addAttribute("findStartDate", findStartDate);
		model.addAttribute("findEndDate", findEndDate);
		
		model.addAttribute("articleNum", articleNum);
		model.addAttribute("totalBooks", totalBooks);
		model.addAttribute("list", list);
		model.addAttribute("paging", pagination.print());
		
		model.addAttribute("categories", categories);
		model.addAttribute("courses", courses);
		
		return "admin/smart/bookList";
	}
	
	@RequestMapping("/bookForm.do")
	public String bookForm(HttpServletRequest request , Model model) throws Exception
	{
		// get parameters
		int pg = Common.getParameter(request, "pg", 1);
		int bookId = Common.getParameter(request, "bookId", 0);
		String findCategory = Common.getParameter(request, "findCategory", "");
		String findMethod = Common.getParameter(request, "findMethod", "");
		String findStr = Common.getParameter(request, "findStr", "");
		String findCourse = Common.getParameter(request, "findCourse", "");
		String findIsUse = Common.getParameter(request, "findIsUse", "");
		String findStartDate = Common.getParameter(request, "findStartDate", "");
		String findEndDate = Common.getParameter(request, "findEndDate", "");
		String mode = (bookId == 0) ? "add" : "modify";
		
		// get data
		Book book = (bookId == 0) ? new Book() : smartService.getBook(bookId);
		List<Category> categories = categoryService.getChildren(Constants.SMART_CATEGORY);	// 스마트 카테고리 디폴트코드
		List<Category> courses = categoryService.getChildren(Constants.COURSE_CATEGORY);	// 교육과정 코드
		List<Unit> units = unitService.getUnitList(Constants.SMART_CATEGORY , bookId);
		
		// assign
		model.addAttribute("pathTitle", "도서관리");
		model.addAttribute("path", "티칭백과 > 도서관리");
		
		model.addAttribute("pg", pg);
		model.addAttribute("findCategory", findCategory);
		model.addAttribute("findMethod", findMethod);
		model.addAttribute("findStr", findStr);
		model.addAttribute("findCourse", findCourse);
		model.addAttribute("findIsUse", findIsUse);
		model.addAttribute("findStartDate", findStartDate);
		model.addAttribute("findEndDate", findEndDate);
		
		model.addAttribute("bookId", bookId);
		model.addAttribute("mode" , mode);
		model.addAttribute("book" , book);
		model.addAttribute("categories" , categories);
		model.addAttribute("courses" , courses);
		model.addAttribute("units" , units);
		
		return "admin/smart/bookForm";
	}
	
	/**
	 * 도서 등록/수정 액션
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/bookFormAction.do" , method=RequestMethod.POST)
	public String bookFormAction(HttpServletRequest request , Model model) throws Exception
	{	
		// get session
		HttpSession session = (HttpSession) request.getSession();
		Member adminMember = (Member) session.getAttribute("adminMember");
		
		// get parameters
		int bookId = Common.getParameter(request, "bookId", 0);
		int pg = Common.getParameter(request, "pg", 1);
		String mode = Common.getParameter(request, "mode", "add");
		String category = Common.getParameter(request, "category", "");
		String course = Common.getParameter(request, "course", "");
		String name = Common.getParameter(request, "name", "");
		String isUse = Common.getParameter(request, "isUse", "");
		int sequence = Common.getParameter(request, "sequence", 0);
		
		if( bookId > 0 && mode.equals("add") ) mode = "modify";
		
		// check validation
		if( Validate.isEmpty(category)
				|| Validate.isEmpty(name)
				|| Validate.isEmpty(isUse))
		{
			throw new CommonException("파라미터 에러." , CommonException.HISTORY_BACK);
		}
		
		// make query parameters
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("category" , category);
		params.put("course" , course);
		params.put("name" , name);
		params.put("isUse" , isUse);
		params.put("regId" , adminMember.getUserId());
		params.put("sequence" , sequence);
		
		String redirect = "";
		
		if( mode.equals("add") )
		{
			bookId = smartService.insertBook(params);
			
			redirect = String.format("redirect:/admin/smart/bookForm.do?pg=%d&bookId=%d" ,
								pg ,
								bookId
							);
		}
		else if( mode.equals("modify") )
		{
			params.put("bookId" , bookId);
			
			smartService.updateBook(params);
			
			redirect = String.format("redirect:/admin/smart/bookForm.do?pg=%d&bookId=%d" ,
					pg ,
					bookId
				);
		}
		
		return redirect;
	}
	
	/**
	 * 단원에 대한 데이터를 처리한다.
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/unitFormAction.do" , method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> unitFormAction(HttpServletRequest request , Model model) throws Exception
	{
		Map<String,Object> result = new HashMap<String,Object>();
		
		// get parameters
		int bookId = Common.getParameter(request, "bookId", 0);
		int unitId = Common.getParameter(request, "unitId", 0);
		int parentId = Common.getParameter(request, "parentId", 0);
		String name = Common.getParameter(request, "name", "");
		int depth = Common.getParameter(request, "depth", 1);
		int sequence = Common.getParameter(request, "sequence", 0);		
		String mode = Common.getParameter(request, "mode", "add");
		
		// check validation
		if( bookId == 0 
				|| Validate.isEmpty(name)
				|| depth == 0
				|| Validate.isEmpty(mode))
		{
			result.put("result" , "FAILURE");
			result.put("msg" , "파라미터 오류");
			return result;
		}
		
		// make query parameters
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("bookId" , bookId);
		params.put("name" , name);
		params.put("depth" , depth);
		params.put("parentId" , parentId);
		result.put("mode" , mode);
		
		if( mode.equals("add") )
		{ 
			// get sequence
			sequence = unitService.getMaxSequence(Constants.SMART_CATEGORY , params);
			params.put("sequence" , sequence);
			
			// get group
			
			// insert data
			unitId = unitService.insertUnit(Constants.SMART_CATEGORY , params);
			
			result.put("bookId" , bookId);
			result.put("unitId" , unitId);
			result.put("parentId" , parentId);
			result.put("depth" , depth);
			result.put("name" , name);
			result.put("sequence" , sequence);
		}
		else if( mode.equals("modify") )
		{
			params.put("unitId" , unitId);
			unitService.updateUnit(Constants.SMART_CATEGORY , params);
		}
		else if( mode.equals("delete") )
		{
			params.put("unitId" , unitId);
			boolean isDelete = unitService.deleteUnit(Constants.SMART_CATEGORY , unitId);
			
			if( !isDelete )
			{
				result.put("result" , "FAILURE");
				result.put("msg" , "하위 분류가 존재하여 삭제할 수 없습니다.");
				return result;
			}
		}
		
		result.put("result" , "SUCCESS");
		
		return result;
	}
 
	/**
	 * 단원의 순서를 변경한다.
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/updateUnitSequence.do" , method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> updateUnitSequence(HttpServletRequest request , Model model) throws Exception
	{
		Map<String,Object> result = new HashMap<String,Object>();
		
		// get parameters
		int depth = Common.getParameter(request, "depth", 0);
		String sequences = Common.getParameter(request, "sequences", "");
		
		// validation check
		if( depth == 0
				|| Validate.isEmpty(sequences))
		{
			result.put("result" , "FAILURE");
			result.put("msg" , "파라미터 오류");
			return result;
		}
		
		unitService.updateUnitSequence(Constants.SMART_CATEGORY , sequences);
		
		return result;
	}
	
	/**
	 * 단원의 상세정보를 가지고 온다.
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getUnitData.do" , method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> getUnitData(HttpServletRequest request , Model model) throws Exception
	{
		Map<String,Object> result = new HashMap<String,Object>();
		
		// get parameters
		int unitId = Common.getParameter(request, "unitId", 0);
		
		// check validation
		if( unitId == 0 )
		{
			result.put("result" , "FAILURE");
			result.put("msg" , "파라미터 에러.");
			return result;
		}
		
		Unit unit = unitService.getUnit(Constants.SMART_CATEGORY , unitId);
		
		result.put("result" , "SUCCESS");
		result.put("unit" , unit);
		
		return result;
	}
	
	/**
	 * 표제어 관리 리스트
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/headwordList.do")
	public String headwordList(HttpServletRequest request , Model model ) throws Exception
	{
		// get parameters
		int pg = Common.getParameter(request, "pg", 1);
		String findCategory = Common.getParameter(request, "findCategory", "");
		String findCourse = Common.getParameter(request, "findCourse", "");
		String findMethod = Common.getParameter(request, "findMethod", "");
		String findStr = Common.getParameter(request, "findStr", "");
		String findIsUse = Common.getParameter(request, "findIsUse", "");
		String findStartDate = Common.getParameter(request, "findStartDate", "");
		String findEndDate = Common.getParameter(request, "findEndDate", "");
		String findBookId = Common.getParameter(request, "findBookId", "");
		String findUnitIds = Common.getParameter(request, "findUnitIds", "");
		String findUnitId = Common.getParameter(request, "findUnitId", "");
		String findIsApprove = Common.getParameter(request, "findIsApprove", "");
		String findFlagType = Common.getParameter(request, "findFlagType", "");
		int skip = (pg - 1) * PAGE_SIZE;
		logger.info("headwordList.do  findUnitIds ::"+findUnitIds);
		logger.info("headwordList.do  findUnitId ::"+findUnitId);
		// make query parameters
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("skip" , skip);
		params.put("findCategory" , findCategory);
		params.put("findMethod" , findMethod);
		params.put("findStr" , findStr);
		params.put("findCourse" , findCourse);
		params.put("findIsUse" , findIsUse);
		params.put("findStartDate" , findStartDate);
		params.put("findEndDate" , findEndDate);
		params.put("findBookId" , findBookId);
		params.put("findUnitId" , findUnitId);
		params.put("findUnitIds" , Common.getUnitIdsPath(findUnitIds));
		params.put("findIsApprove" , findIsApprove);
		params.put("findFlagType" , findFlagType);
		params.put("pageSize" , PAGE_SIZE);		
		
		// 관리자 타입에 따라서 조회 권한 부여
		HttpSession session = (HttpSession) request.getSession();
		Member adminMember = (Member) session.getAttribute("adminMember");
		
		if( adminMember.getAuthType().equals("N") )
		{
			String smartAuth = adminMember.getSmartAuth();
			String findSmartAuth = "'" + smartAuth.replaceAll("\\," , "'\\,'") + "'";
			params.put("findSmartAuth" , findSmartAuth);
		}
		
		// get data
		int totalCount = smartService.getHeadwordListCount(params);
		int articleNum = totalCount - skip;
		List<Headword> list = smartService.getHeadwordList(params);
		List<Category> categories = categoryService.getChildren(Constants.SMART_CATEGORY);
		List<Category> courses = categoryService.getChildren(Constants.COURSE_CATEGORY);
		List<Book> books = smartService.getBookListByCategory(findCategory);
		
		// pagination
		Pagination pagination = new Pagination(pg , PAGE_SIZE , BLOCK_SIZE , totalCount);
		pagination.setDelimiter("");
		pagination.setPrevLink("Prev");
		pagination.setNextLink("Next");
		
		// assign
		model.addAttribute("pathTitle", "표제어관리");
		model.addAttribute("path", "티칭백과 > 표제어관리");
		
		model.addAttribute("pg", pg);
		model.addAttribute("findCategory", findCategory);
		model.addAttribute("findMethod", findMethod);
		model.addAttribute("findStr", findStr);
		model.addAttribute("findCourse", findCourse);
		model.addAttribute("findIsUse", findIsUse);
		model.addAttribute("findStartDate", findStartDate);
		model.addAttribute("findEndDate", findEndDate);
		model.addAttribute("findBookId", findBookId);
		model.addAttribute("findUnitIds", findUnitIds);
		model.addAttribute("findUnitId", findUnitId);
		model.addAttribute("findIsApprove", findIsApprove);
		model.addAttribute("findFlagType", findFlagType);
		model.addAttribute("categories", categories);
		model.addAttribute("courses", courses);
		model.addAttribute("books", books);
		
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("articleNum", articleNum);
		model.addAttribute("list", list);
		model.addAttribute("paging", pagination.print());
		
		return "admin/smart/headwordList";
	}
	
	/**
	 * 표제어 등록화면
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/headwordForm.do")
	public String headwordForm(HttpServletRequest request , Model model) throws Exception
	{
		// get parameters
		int headwordId = Common.getParameter(request, "headwordId", 0);
		int pg = Common.getParameter(request, "pg", 1);
		String findCategory = Common.getParameter(request, "findCategory", "");
		String findCourse = Common.getParameter(request, "findCourse", "");
		String findMethod = Common.getParameter(request, "findMethod", "");
		String findStr = Common.getParameter(request, "findStr", "");
		String findIsUse = Common.getParameter(request, "findIsUse", "");
		String findStartDate = Common.getParameter(request, "findStartDate", "");
		String findEndDate = Common.getParameter(request, "findEndDate", "");
		String findBookId = Common.getParameter(request, "findBookId", "");
		String findUnitIds = Common.getParameter(request, "findUnitIds", "");
		String findUnitId = Common.getParameter(request, "findUnitId", "");
		String findIsApprove = Common.getParameter(request, "findIsApprove", "");
		String findFlagType = Common.getParameter(request, "findFlagType", "");
		String mode = (headwordId == 0) ? "add" : "modify";
		
		// get default category code
		Headword headword = ( mode.equals("add") ) ? new Headword() : smartService.getHeadword(headwordId);
		List<Category> categories = categoryService.getChildren(Constants.SMART_CATEGORY);		
		List<Category> exhibits = categoryService.getChildren(Constants.EXHIBIT_CATEGORY);
		List<Book> books = smartService.getBookListByCategory(headword.getCategory());
		List<RelationHeadword> relationHeadwords = smartService.getRelationHeadwordList(headwordId);
		List<Multimedia> additionals = smartService.getAdditionalList(headwordId);
		
		// 관리자의 신규 등록이므로 승인여부는 Y로 셋팅한다.
		if( mode.equals("add") )
			headword.setIsApprove("Y");
		
		// assign
		model.addAttribute("pathTitle", "표제어관리");
		model.addAttribute("path", "티칭백과 > 표제어관리");
		
		model.addAttribute("pg", pg);
		model.addAttribute("findCategory", findCategory);
		model.addAttribute("findMethod", findMethod);
		model.addAttribute("findStr", findStr);
		model.addAttribute("findCourse", findCourse);
		model.addAttribute("findIsUse", findIsUse);
		model.addAttribute("findStartDate", findStartDate);
		model.addAttribute("findEndDate", findEndDate);
		model.addAttribute("findBookId", findBookId);
		model.addAttribute("findUnitIds", findUnitIds);
		model.addAttribute("findUnitId", findUnitId);
		model.addAttribute("findIsApprove", findIsApprove);
		model.addAttribute("findFlagType", findFlagType);
		model.addAttribute("headwordId", headwordId);
		model.addAttribute("mode", mode);
		
		model.addAttribute("headwordId", headwordId);
		model.addAttribute("headword", headword);
		model.addAttribute("categories", categories);
		model.addAttribute("exhibits", exhibits);
		model.addAttribute("relationHeadwords", relationHeadwords);
		model.addAttribute("books", books);
		model.addAttribute("additionals", additionals);
		
		return "admin/smart/headwordForm";
	}
	
	/**
	 * 표제어를 처리 액션.
	 * @param thumbnail
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="headwordFormAction.do" , method=RequestMethod.POST)
	public String headwordFormAction(@RequestParam(value="thumbnail" , required=false) MultipartFile thumbnailFile ,
										HttpServletRequest request , Model model) throws Exception
	{	
		// get session
		HttpSession session = (HttpSession) request.getSession();
		Member adminMember = (Member) session.getAttribute("adminMember");
		
		// get parameters
		int headwordId = Common.getParameter(request, "headwordId", 0);
		String flag = Common.getParameter(request, "flag", "H");
		String category = Common.getParameter(request, "category", "");
		String exhibit = Common.getParameter(request, "exhibit", "");
		int bookId = Common.getParameter(request, "bookId", 0);
		int unitId = Common.getParameter(request, "unitId", 0);
		String unitIds = Common.getParameter(request, "unitIds", "");
		String title = Common.getParameter(request, "title", "");
		String titleEng = Common.getParameter(request, "titleEng", "");
		String titleChi = Common.getParameter(request, "titleChi", "");
		String summary = Common.getParameter(request, "summary", "");
		String contents = Common.getParameterNoXSS(request, "contents", "");
		String added = Common.getParameterNoXSS(request, "added", "");
		String originCode = Common.getParameter(request, "originCode", "");
		String originTxt = Common.getParameter(request, "originTxt", "");
		String relationBooks = Common.getParameter(request, "relationBooks", "");
		String isUse = Common.getParameter(request, "isUse", "Y");
		String isApprove = Common.getParameter(request, "isApprove", "Y");
		
		int pg = Common.getParameter(request, "pg", 1);
		String findCategory = Common.getParameter(request, "findCategory", "");
		String findCourse = Common.getParameter(request, "findCourse", "");
		String findMethod = Common.getParameter(request, "findMethod", "");
		String findStr = Common.getParameter(request, "findStr", "");
		String findIsUse = Common.getParameter(request, "findIsUse", "");
		String findStartDate = Common.getParameter(request, "findStartDate", "");
		String findEndDate = Common.getParameter(request, "findEndDate", "");
		String findBookId = Common.getParameter(request, "findBookId", "");
		String findUnitIds = Common.getParameter(request, "findUnitIds", "");
		String findUnitId = Common.getParameter(request, "findUnitId", "");
		
		String queryString = String.format("pg=%d&findCategory=%s&findCourse=%s&findIsUse=%s&findMethod=%s&findStr=%s&findStartDate=%s&findEndDate=%s&findBookId=%s&findUnitIds=%s&findUnitId=%s" ,
									pg ,
									findCategory ,
									findCourse ,
									findIsUse ,
									findMethod ,
									URLEncoder.encode(findStr, "UTF-8") ,
									findStartDate ,
									findEndDate ,
									findBookId ,
									findUnitIds ,
									findUnitId
								);
		
		String mode = (headwordId == 0 ) ? "add" : "modify";
		
		// validation check
		if( Validate.isEmpty(category)
				|| bookId == 0
				|| unitId == 0
				|| Validate.isEmpty(title)
				|| Validate.isEmpty(contents))
		{
			throw new CommonException("정상적인 요청이 아닙니다." , CommonException.HISTORY_BACK);
		}
		
		// add to model
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("flag" , flag);
		params.put("category" , category);
		params.put("exhibit" , exhibit);
		params.put("bookId" , bookId);
		params.put("unitId" , unitId);
		params.put("title" , title);
		params.put("titleEng" , titleEng);
		params.put("titleChi" , titleChi);
		params.put("summary" , summary);
		params.put("contents" , contents);
		params.put("added" , added);
		params.put("originCode" , originCode);
		params.put("originTxt" , originTxt);
		params.put("relationBooks" , relationBooks);
		params.put("isUse" , isUse);
		params.put("isApprove" , isApprove);
		params.put("regId" , adminMember.getUserId());
		params.put("unitIds" , Common.getUnitIdsPath(unitIds));
		
		String returnUrl = "";
		
		if( mode.equals("add") )
		{
			// 리스트용 썸네일 이미지가 존재한다면 파일을 업로드 한다.
			if( !thumbnailFile.isEmpty() )
			{
				FileInfo fileInfo = fileService.upload(thumbnailFile, serverFilePath, "headword");
				params.put("thumbnail" , fileInfo.getAbsolutePath());
			}
			
			headwordId = smartService.insertHeadword(params);
			returnUrl = String.format("redirect:headwordForm.do?headwordId=%d&%s" , headwordId , queryString);
		}
		else if( mode.equals("modify") )
		{
			// add to query parameter 
			params.put("headwordId" , headwordId);
			
			// 리스트용 썸네일 이미지가 존재한다면 파일을 업로드 한다.(기존의 파일은 삭제한다.)
			Headword headword = smartService.getHeadword(headwordId);
			
			if( !thumbnailFile.isEmpty() )
			{
				FileInfo fileInfo = fileService.upload(thumbnailFile, serverFilePath, "headword");
				params.put("thumbnail" , fileInfo.getAbsolutePath());
				
				// 기존의 파일을 삭제한다.
				// 절대경로를 가지고 오기 때문에 기존 파일명의 앞자리 /upfiles는 삭제한다.
				if( !Validate.isEmpty(headword.getThumbnail()))
				{
					String deleteFilePath = String.format("%s%s" , serverFilePath , headword.getThumbnail().replace("/upfiles" , ""));
					fileService.delete(deleteFilePath);
				}
			}
			else
			{
				// 파일이 존재하지 않다면 기존의 파일명을 다시 입력한다.
				params.put("thumbnail" , headword.getThumbnail());
			}
			
			smartService.updateHeadword(params);
			returnUrl = String.format("redirect:headwordForm.do?headwordId=%d&%s" , headwordId , queryString);
		}
		 
		return returnUrl;
	}
	
	
	
	/**
	 * 백과플러스 리스트
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/plusList.do")
	public String plusList(HttpServletRequest request , Model model ) throws Exception
	{
		// get parameters
		int pg = Common.getParameter(request, "pg", 1);
		String findCategory = Common.getParameter(request, "findCategory", "");
		String findCourse = Common.getParameter(request, "findCourse", "");
		String findMethod = Common.getParameter(request, "findMethod", "");
		String findStr = Common.getParameter(request, "findStr", "");
		String findIsUse = Common.getParameter(request, "findIsUse", "");
		String findStartDate = Common.getParameter(request, "findStartDate", "");
		String findEndDate = Common.getParameter(request, "findEndDate", "");
		String findBookId = Common.getParameter(request, "findBookId", "");
		String findUnitIds = Common.getParameter(request, "findUnitIds", "");
		String findUnitId = Common.getParameter(request, "findUnitId", "");
		String findIsApprove = Common.getParameter(request, "findIsApprove", "");
		String findFlagType = Common.getParameter(request, "findFlagType", "");
		int skip = (pg - 1) * PAGE_SIZE;
		
		// make query parameters
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("skip" , skip);
		params.put("findCategory" , findCategory);
		params.put("findMethod" , findMethod);
		params.put("findStr" , findStr);
		params.put("findCourse" , findCourse);
		params.put("findIsUse" , findIsUse);
		params.put("findStartDate" , findStartDate);
		params.put("findEndDate" , findEndDate);
		params.put("findBookId" , findBookId);
		params.put("findUnitId" , findUnitId);
		params.put("findIsApprove" , findIsApprove);
		params.put("findFlagType" , findFlagType);
		params.put("findFlag" , "P");
		params.put("pageSize" , PAGE_SIZE);		
		
		// 관리자 타입에 따라서 조회 권한 부여
		HttpSession session = (HttpSession) request.getSession();
		Member adminMember = (Member) session.getAttribute("adminMember");
		
		if( adminMember.getAuthType().equals("N") )
		{
			String smartAuth = adminMember.getSmartAuth();
			String findSmartAuth = "'" + smartAuth.replaceAll("\\," , "'\\,'") + "'";
			params.put("findSmartAuth" , findSmartAuth);
		}
		
		// get data
		int totalCount = smartService.getHeadwordListCount(params);
		int articleNum = totalCount - skip;
		List<Headword> list = smartService.getHeadwordList(params);
		List<Category> categories = categoryService.getChildren(Constants.SMART_CATEGORY);
		List<Category> courses = categoryService.getChildren(Constants.COURSE_CATEGORY);
		List<Book> books = smartService.getBookListByCategory(findCategory);
		
		// pagination
		Pagination pagination = new Pagination(pg , PAGE_SIZE , BLOCK_SIZE , totalCount);
		pagination.setDelimiter("");
		pagination.setPrevLink("Prev");
		pagination.setNextLink("Next");
		
		// assign
		model.addAttribute("pathTitle", "백과플러스");
		model.addAttribute("path", "티칭백과 > 표제어관리");
		
		model.addAttribute("pg", pg);
		model.addAttribute("findCategory", findCategory);
		model.addAttribute("findMethod", findMethod);
		model.addAttribute("findStr", findStr);
		model.addAttribute("findCourse", findCourse);
		model.addAttribute("findIsUse", findIsUse);
		model.addAttribute("findStartDate", findStartDate);
		model.addAttribute("findEndDate", findEndDate);
		model.addAttribute("findBookId", findBookId);
		model.addAttribute("findUnitIds", findUnitIds);
		model.addAttribute("findUnitId", findUnitId);
		model.addAttribute("findIsApprove", findIsApprove);
		model.addAttribute("findFlagType", findFlagType);
		model.addAttribute("categories", categories);
		model.addAttribute("courses", courses);
		model.addAttribute("books", books);
		
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("articleNum", articleNum);
		model.addAttribute("list", list);
		model.addAttribute("paging", pagination.print());
		
		return "admin/smart/plusList";
	}
	
	/**
	 * 백과플러스 등록화면
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/plusForm.do")
	public String plusForm(HttpServletRequest request , Model model) throws Exception
	{
		// get parameters
		int headwordId = Common.getParameter(request, "headwordId", 0);
		int pg = Common.getParameter(request, "pg", 1);
		String findCategory = Common.getParameter(request, "findCategory", "");
		String findCourse = Common.getParameter(request, "findCourse", "");
		String findMethod = Common.getParameter(request, "findMethod", "");
		String findStr = Common.getParameter(request, "findStr", "");
		String findIsUse = Common.getParameter(request, "findIsUse", "");
		String findStartDate = Common.getParameter(request, "findStartDate", "");
		String findEndDate = Common.getParameter(request, "findEndDate", "");
		String findBookId = Common.getParameter(request, "findBookId", "");
		String findUnitIds = Common.getParameter(request, "findUnitIds", "");
		String findUnitId = Common.getParameter(request, "findUnitId", "");
		String findIsApprove = Common.getParameter(request, "findIsApprove", "");
		String findFlagType = Common.getParameter(request, "findFlagType", "");
		String mode = (headwordId == 0) ? "add" : "modify";
		
		// get default category code
		Headword headword = ( mode.equals("add") ) ? new Headword() : smartService.getHeadword(headwordId);
		List<Category> categories = categoryService.getChildren(Constants.SMART_CATEGORY);		
		List<Category> exhibits = categoryService.getChildren(Constants.EXHIBIT_CATEGORY);
		List<Book> books = smartService.getBookListByCategory(headword.getCategory());
		List<RelationHeadword> relationHeadwords = smartService.getRelationHeadwordList(headwordId);
		List<Multimedia> additionals = smartService.getAdditionalList(headwordId);
		
		// 관리자의 신규 등록이므로 승인여부는 Y로 셋팅한다.
		if( mode.equals("add") )
			headword.setIsApprove("Y");
		
		// assign
		model.addAttribute("pathTitle", "표제어관리");
		model.addAttribute("path", "티칭백과 > 표제어관리");
		
		model.addAttribute("pg", pg);
		model.addAttribute("findCategory", findCategory);
		model.addAttribute("findMethod", findMethod);
		model.addAttribute("findStr", findStr);
		model.addAttribute("findCourse", findCourse);
		model.addAttribute("findIsUse", findIsUse);
		model.addAttribute("findStartDate", findStartDate);
		model.addAttribute("findEndDate", findEndDate);
		model.addAttribute("findBookId", findBookId);
		model.addAttribute("findUnitIds", findUnitIds);
		model.addAttribute("findUnitId", findUnitId);
		model.addAttribute("findIsApprove", findIsApprove);
		model.addAttribute("findFlagType", findFlagType);
		model.addAttribute("headwordId", headwordId);
		model.addAttribute("mode", mode);
		
		model.addAttribute("headwordId", headwordId);
		model.addAttribute("headword", headword);
		model.addAttribute("categories", categories);
		model.addAttribute("exhibits", exhibits);
		model.addAttribute("relationHeadwords", relationHeadwords);
		model.addAttribute("books", books);
		model.addAttribute("additionals", additionals);
		
		return "admin/smart/plusForm";
	}
	
	/**
	 * 백과플러스 처리 액션.
	 * @param thumbnail
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="plusFormAction.do" , method=RequestMethod.POST)
	public String plusFormAction(@RequestParam(value="thumbnail" , required=false) MultipartFile thumbnailFile ,
										HttpServletRequest request , Model model) throws Exception
	{	
		// get session
		HttpSession session = (HttpSession) request.getSession();
		Member adminMember = (Member) session.getAttribute("adminMember");
		
		// get parameters
		int headwordId = Common.getParameter(request, "headwordId", 0);
		String flag = Common.getParameter(request, "flag", "H");
		String category = Common.getParameter(request, "category", "");
		String exhibit = Common.getParameter(request, "exhibit", "");
		int bookId = Common.getParameter(request, "bookId", 0);
		int unitId = Common.getParameter(request, "unitId", 0);
		String title = Common.getParameter(request, "title", "");
		String titleEng = Common.getParameter(request, "titleEng", "");
		String titleChi = Common.getParameter(request, "titleChi", "");
		String summary = Common.getParameter(request, "summary", "");
		String contents = Common.getParameterNoXSS(request, "contents", "");
		String added = Common.getParameterNoXSS(request, "added", "");
		String originCode = Common.getParameter(request, "originCode", "");
		String originTxt = Common.getParameter(request, "originTxt", "");
		String relationBooks = Common.getParameter(request, "relationBooks", "");
		String isUse = Common.getParameter(request, "isUse", "Y");
		String isApprove = Common.getParameter(request, "isApprove", "Y");
		
		int pg = Common.getParameter(request, "pg", 1);
		String findCategory = Common.getParameter(request, "findCategory", "");
		String findCourse = Common.getParameter(request, "findCourse", "");
		String findMethod = Common.getParameter(request, "findMethod", "");
		String findStr = Common.getParameter(request, "findStr", "");
		String findIsUse = Common.getParameter(request, "findIsUse", "");
		String findStartDate = Common.getParameter(request, "findStartDate", "");
		String findEndDate = Common.getParameter(request, "findEndDate", "");
		String findBookId = Common.getParameter(request, "findBookId", "");
		String findUnitIds = Common.getParameter(request, "findUnitIds", "");
		String findUnitId = Common.getParameter(request, "findUnitId", "");
		
		String queryString = String.format("pg=%d&findCategory=%s&findCourse=%s&findIsUse=%s&findMethod=%s&findStr=%s&findStartDate=%s&findEndDate=%s&findBookId=%s&findUnitIds=%s&findUnitId=%s" ,
									pg ,
									findCategory ,
									findCourse ,
									findIsUse ,
									findMethod ,
									URLEncoder.encode(findStr, "UTF-8") ,
									findStartDate ,
									findEndDate ,
									findBookId ,
									findUnitIds ,
									findUnitId
								);
		
		String mode = (headwordId == 0 ) ? "add" : "modify";
		
		// validation check
		if( Validate.isEmpty(category)
				|| bookId == 0
				|| unitId == 0
				|| Validate.isEmpty(title)
				|| Validate.isEmpty(contents))
		{
			throw new CommonException("정상적인 요청이 아닙니다." , CommonException.HISTORY_BACK);
		}
		
		// add to model
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("flag" , flag);
		params.put("category" , category);
		params.put("exhibit" , exhibit);
		params.put("bookId" , bookId);
		params.put("unitId" , unitId);
		params.put("title" , title);
		params.put("titleEng" , titleEng);
		params.put("titleChi" , titleChi);
		params.put("summary" , summary);
		params.put("contents" , contents);
		params.put("added" , added);
		params.put("originCode" , originCode);
		params.put("originTxt" , originTxt);
		params.put("relationBooks" , relationBooks);
		params.put("isUse" , isUse);
		params.put("isApprove" , isApprove);
		params.put("regId" , adminMember.getUserId());
		
		String returnUrl = "";
		
		if( mode.equals("add") )
		{
			// 리스트용 썸네일 이미지가 존재한다면 파일을 업로드 한다.
			if( !thumbnailFile.isEmpty() )
			{
				FileInfo fileInfo = fileService.upload(thumbnailFile, serverFilePath, "headword");
				params.put("thumbnail" , fileInfo.getAbsolutePath());
			}
			
			headwordId = smartService.insertHeadword(params);
			returnUrl = String.format("redirect:headwordForm.do?headwordId=%d&%s" , headwordId , queryString);
		}
		else if( mode.equals("modify") )
		{
			// add to query parameter 
			params.put("headwordId" , headwordId);
			
			// 리스트용 썸네일 이미지가 존재한다면 파일을 업로드 한다.(기존의 파일은 삭제한다.)
			Headword headword = smartService.getHeadword(headwordId);
			
			if( !thumbnailFile.isEmpty() )
			{
				FileInfo fileInfo = fileService.upload(thumbnailFile, serverFilePath, "headword");
				params.put("thumbnail" , fileInfo.getAbsolutePath());
				
				// 기존의 파일을 삭제한다.
				// 절대경로를 가지고 오기 때문에 기존 파일명의 앞자리 /upfiles는 삭제한다.
				if( !Validate.isEmpty(headword.getThumbnail()))
				{
					String deleteFilePath = String.format("%s%s" , serverFilePath , headword.getThumbnail().replace("/upfiles" , ""));
					fileService.delete(deleteFilePath);
				}
			}
			else
			{
				// 파일이 존재하지 않다면 기존의 파일명을 다시 입력한다.
				params.put("thumbnail" , headword.getThumbnail());
			}
			
			smartService.updateHeadword(params);
			returnUrl = String.format("redirect:plusForm.do?headwordId=%d&%s" , headwordId , queryString);
		}
			
		 
		return returnUrl;
	}
	
	/**
	 * 표제어 팝업 리스트
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */ 
	@RequestMapping("/headwordSearchPopup.do")
	public String headwordSearchPopup(HttpServletRequest request , Model model) throws Exception
	{
		// get parameters
		int pg = Common.getParameter(request, "pg", 1);
		String findCategory = Common.getParameter(request, "findCategory", "");
		String findCourse = Common.getParameter(request, "findCourse", "");
		String findMethod = Common.getParameter(request, "findMethod", "");
		String findStr = Common.getParameter(request, "findStr", "");
		String findIsUse = Common.getParameter(request, "findIsUse", "");
		String findStartDate = Common.getParameter(request, "findStartDate", "");
		String findEndDate = Common.getParameter(request, "findEndDate", "");
		String findBookId = Common.getParameter(request, "findBookId", "");
		String findUnitIds = Common.getParameter(request, "findUnitIds", "");
		String findUnitId = Common.getParameter(request, "findUnitId", "");
		int skip = (pg - 1) * PAGE_SIZE;
		
		// make query parameters
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("skip" , skip);
		params.put("findCategory" , findCategory);
		params.put("findMethod" , findMethod);
		params.put("findStr" , findStr);
		params.put("findCourse" , findCourse);
		params.put("findIsUse" , findIsUse);
		params.put("findStartDate" , findStartDate);
		params.put("findEndDate" , findEndDate);
		params.put("findBookId" , findBookId);
		params.put("findUnitId" , findUnitId);
		params.put("findUnitIds" , Common.getUnitIdsPath(findUnitIds));
		params.put("pageSize" , PAGE_SIZE);		
		
		// get data
		int totalCount = smartService.getHeadwordListCount(params);
		int articleNum = totalCount - skip;
		List<Headword> list = smartService.getHeadwordList(params);
		List<Category> categories = categoryService.getChildren(Constants.SMART_CATEGORY);
		List<Category> courses = categoryService.getChildren(Constants.COURSE_CATEGORY);
		List<Book> books = smartService.getBookListByCategory(findCategory);
		
		// pagination
		Pagination pagination = new Pagination(pg , PAGE_SIZE , BLOCK_SIZE , totalCount);
		pagination.setDelimiter("");
		pagination.setPrevLink("Prev");
		pagination.setNextLink("Next");
		
		// assign
		model.addAttribute("pathTitle", "표제어관리");
		model.addAttribute("path", "티칭백과 > 표제어관리");
		
		model.addAttribute("pg", pg);
		model.addAttribute("findCategory", findCategory);
		model.addAttribute("findMethod", findMethod);
		model.addAttribute("findStr", findStr);
		model.addAttribute("findCourse", findCourse);
		model.addAttribute("findIsUse", findIsUse);
		model.addAttribute("findStartDate", findStartDate);
		model.addAttribute("findEndDate", findEndDate);
		model.addAttribute("findBookId", findBookId);
		model.addAttribute("findUnitIds", findUnitIds);
		model.addAttribute("findUnitId", findUnitId);
		model.addAttribute("categories", categories);
		model.addAttribute("courses", courses);
		model.addAttribute("books", books);
		
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("articleNum", articleNum);
		model.addAttribute("list", list);
		model.addAttribute("paging", pagination.print());
		
		model.addAttribute("footer", "N");
		
		return "popup/headwordSearchPopup";
	}
	
	/**
	 * 카테고리에 속한 도서목록을 가지고 온다.
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getSmartBooks.do" , method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> getSmartBooks(HttpServletRequest request , Model model) throws Exception
	{
		Map<String,Object> result = new HashMap<String,Object>();
		
		// get parameters
		String category = Common.getParameter(request, "category", "");
		
		// validation check
		if( Validate.isEmpty(category) )
		{
			result.put("result" , "FAILURE");
			result.put("msg" , "파라미터 에러.");
			return result;
		}
		
		// get data
		List<Book> books = smartService.getBookListByCategory(category);
		
		result.put("result" , "SUCCESS");
		result.put("books" , books);
		
		return result;
	}
	
	/**
	 * 인물,사건의 리스트를 가지고 온다.
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getHistoryList.do")
	public @ResponseBody Map<String,Object> getHistoryList(HttpServletRequest request , Model model) throws Exception
	{
		Map<String,Object> result = new HashMap<String,Object>();
		
		// get parameter
		int headwordId = Common.getParameter(request, "headwordId", 0);
		
		if( headwordId == 0 )
		{
			result.put("result" , "FAILURE");
			result.put("msg" , "파라미터 에러");
			return result;
		}
		
		// get data
		List<History> list = smartService.getHistoryList(headwordId);
		
		result.put("result" , "SUCCESS");
		result.put("msg" , "성공");
		result.put("list" , list);
		
		return result;
	}
	
	/**
	 * 인물,사건의 액션 처리
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/historyAction.do" , method=RequestMethod.POST) 
	public @ResponseBody Map<String,Object> historyAction(HttpServletRequest request , Model model) throws Exception
	{
		Map<String,Object> result = new HashMap<String,Object>();
		logger.info("historyAction.do");	
		// get parameters
		int historyId = Common.getParameter(request, "historyId", 0);
		int headwordId = Common.getParameter(request, "headwordId", 0);
		String isPrimary = Common.getParameter(request, "isPrimary", "N");
		String historyType = Common.getParameter(request, "historyType", "");
		String historyDate = Common.getParameter(request, "historyDate", "");
		String historyExp = Common.getParameter(request, "historyExp", "");
		String mode = Common.getParameter(request, "mode", "add");
		
		// validation check
		boolean isValidated = true;
		
		if( mode.equals("add") )
		{
						
			if( Validate.isEmpty(historyType) 
					|| Validate.isEmpty(historyDate)
					|| Validate.isEmpty(historyExp))
				isValidated = true;
		}
		else if( mode.equals("delete") )
		{
		
			if( historyId == 0 )
				isValidated = true;
		}
		
		if( isValidated == false )
		{
			result.put("result" , "FAILURE");
			result.put("msg" , "파라미터 에러");
			return result;
		}
		
		// make query parameter
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("headwordId" , headwordId);
		params.put("isPrimary" , isPrimary);
		params.put("historyType" , historyType);
		params.put("historyDate", historyDate);
		params.put("historyExp" , historyExp);
		
		if( mode.equals("add") )
		{
			logger.info(":::::::::::add:::::::::::::::");			
			historyId = smartService.insertHistory(params);
		}
		else if( mode.equals("delete") )
		{
			logger.info(":::::::::::delete:::::::::::::::");	
			smartService.deleteHistory(historyId);
			
			params.put("historyId" , historyId);
		}
		
		// 입력/삭제가 모두 되었다면 현재 저장되어있는 인물,사건의 모든 데이타를 가지고 온다.
		List<History> list = smartService.getHistoryList(headwordId);
		
		// set result
		result.put("result" , "SUCCESS");
		result.put("msg" , "성공");
		result.put("list" , list);
		
		return result;
	}
	
	/**
	 * 문제의 단일 데이타를 가지고 온다.
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getPool.do")
	public @ResponseBody Map<String,Object> getPool(HttpServletRequest request , Model model) throws Exception
	{
		Map<String,Object> result = new HashMap<String,Object>();
		
		// get parameters
		int poolId = Common.getParameter(request, "poolId", 0);
		
		// check validation
		if( poolId == 0 )
		{
			result.put("result" , "FAILURE");
			result.put("msg" , "파라미터 에러");
			return result;
		}
		
		// get data
		Pool pool = smartService.getPool(poolId);
		
		// assign
		result.put("result" , "SUCCESS");
		result.put("msg" , "성공");
		result.put("pool" , pool);
		
		return result;
	}
	
	/**
	 * 문제의 리스트를 가지고 온다.
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getPoolList.do")
	public @ResponseBody Map<String,Object> getPoolList(HttpServletRequest request , Model model) throws Exception
	{
		Map<String,Object> result = new HashMap<String,Object>();
		
		// get parameters
		int headwordId = Common.getParameter(request, "headwordId", 0);
		
		// validation check
		if( headwordId == 0 )
		{
			result.put("result" , "FAILURE");
			result.put("msg" , "파라미터가 정상적이지 않습니다.");
			return result;
		}
		
		// get data
		List<Pool> list = smartService.getPoolList(headwordId);
		
		// assign
		result.put("result" , "SUCCESS");
		result.put("msg" , "성공");
		result.put("list" , list);
		
		return result;
	}
	
	/**
	 * 문제에 대한 액션 처리
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/poolAction.do" , method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> poolAction(HttpServletRequest request , Model model) throws Exception
	{
		Map<String,Object> result = new HashMap<String,Object>();
		
		// get parameters
		int poolId = Common.getParameter(request, "poolId", 0);
		int headwordId = Common.getParameter(request, "headwordId", 0);
		String flag = Common.getParameter(request, "flag", "");
		String question = Common.getParameterNoXSS(request, "question", "");
		String answer = Common.getParameter(request, "answer", "");
		String explanation = Common.getParameter(request, "explanation", "");
		String mode = Common.getParameter(request, "mode", "add");
		
		// validation check
		if( headwordId == 0 )
		{
			result.put("result" , "FAILURE");
			result.put("msg" , "파라미터가 정상적이지 않습니다.");
			return result;
		}
		
		// make query parameters
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("poolId" , poolId);
		params.put("headwordId" , headwordId);
		params.put("flag" , flag);
		params.put("question" , question);
		params.put("answer" , answer);
		params.put("explanation" , explanation);
		
		if( mode.equals("add") )
		{
			if( flag.equals("C") )
			{
				int confirmedPoolCount = smartService.getConfirmedPoolCount(headwordId);
				
				if( confirmedPoolCount > 0 )
				{
					result.put("result" , "FAILURE");
					result.put("msg" , "이미 확인문제가 등록이 되어있습니다.");
					return result;
				}
			}
			
			poolId = smartService.insertPool(params);
			
			params.put("poolId" , poolId);
		}
		else if( mode.equals("modify") )
		{
			smartService.updatePool(params);
		}
		else if( mode.equals("delete") )
		{
			smartService.deletePool(poolId);
		}
		
		// return
		result.put("result" , "SUCCESS");
		result.put("msg" , "성공");
		
		return result;
	}

	
	/**
	 * 관련 용어를 등록한다.
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addRelationHeadword.do")
	public @ResponseBody Map<String,Object> addRelationHeadword(HttpServletRequest request , Model model) throws Exception
	{
		Map<String,Object> result = new HashMap<String,Object>();
		
		// get parameters
		int headwordId = Common.getParameter(request, "headwordId", 0);
		int childrenId = Common.getParameter(request, "childrenId", 0);
		
		// validation check
		if( headwordId == 0 
				|| childrenId == 0)
		{
			result.put("result" , "FAILURE");
			result.put("msg" , "정상적인 접근이 아닙니다.");
			return result;
		}
		
		// make query parameters
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("headwordId" , headwordId);
		params.put("childrenId" , childrenId);
		
		int relationId = smartService.insertRelationHeadword(params);
		
		result.put("result" , "SUCCESS");
		result.put("relationId" , relationId);
		
		return result;
	}
	
	/**
	 * 관련 용어를 삭제한다.
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/deleteRelationHeadword.do")
	public @ResponseBody Map<String,Object> deleteRelationHeadword(HttpServletRequest request , Model model) throws Exception
	{
		Map<String,Object> result = new HashMap<String,Object>();
		
		// get parameters
		int relationId = Common.getParameter(request, "relationId", 0);
		
		// validation check
		if( relationId == 0 )
		{
			result.put("result" , "FAILURE");
			result.put("msg" , "정상적인 접근이 아닙니다.");
			return result;
		}
		
		smartService.deleteRelationHeadword(relationId);
		
		result.put("result" , "SUCCESS");
		result.put("relationId" , relationId);
		
		return result;
	}

	@RequestMapping("/editor.do")
	public String editor(HttpServletRequest request , Model model) throws Exception
	{
		// get target
		String target = Common.getParameter(request, "target", "");
		
		// assign
		model.addAttribute("target" , target);
		
		return "daumeditor/multi";
	}
	
	/**
	 * 표제어 복사 팝업
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("headwordCopyPopup.do")
	public String headwordCopyPopup(HttpServletRequest request , Model model) throws Exception
	{
		
		String[] headwordIds = Common.getParameters(request, "headwordId");
		
		//validate
		if(	headwordIds.length < 1	){
			throw new CommonException("하나 이상의 표제어를 선택하여 주십시오." , CommonException.HISTORY_BACK);
		}

		// make query parameters
		Map<String,Object> params = new HashMap<String,Object>();
		//params.put("headwordIds", headwordIds);

		List<Category> categories = categoryService.getChildren(Constants.SMART_CATEGORY);
		List<Category> courses = categoryService.getChildren(Constants.COURSE_CATEGORY);
		List<Book> books = smartService.getBookListByCategory("");
		
		// assign
		//model.addAttribute("pathTitle", "표제어관리");
		
		model.addAttribute("headwordIds", headwordIds);
		model.addAttribute("categories", categories);
		model.addAttribute("courses", courses);
		model.addAttribute("books", books);
		
		model.addAttribute("title", "표제어 복사");
		model.addAttribute("footer", "N");
		
		return "popup/headwordCopyPopup";
	}
	
	
	/**
	 * 표제어 복사
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/headwordCopyAction.do" , method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> headwordCopyAction(HttpServletRequest request , Model model) throws Exception
	{	
		Map<String, Object> result = new HashMap<String, Object>();
		
		// get session
		HttpSession session = (HttpSession) request.getSession();
		Member adminMember = (Member) session.getAttribute("adminMember");
		
		// get parameter
		String[] headwordIds = Common.getParameters(request, "headwordId");
		String category = Common.getParameter(request, "category", "");
		String bookId = Common.getParameter(request, "bookId", "");
		String unitId = Common.getParameter(request, "unitId", "");
		
		// validate
		if(	headwordIds.length == 0	&& adminMember.getUserId().equals("")){
			throw new CommonException("잘못된 접근입니다.");
		}
		
		// make query parameters
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("regId" , adminMember.getUserId());
		params.put("modifyId" , adminMember.getUserId());
		
		params.put("headwordIds", headwordIds);
		params.put("category", category);
		params.put("bookId", bookId);
		params.put("unitId", unitId);
		
		smartService.insertHeadwordCopy(params);
		result.put("result" , "SUCCESS");
		
		return result;
	}
	
	/**
	 * 본문의 내용을 미리보기
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/headwordContentsPopup.do")
	public String headwordContentsPopup(HttpServletRequest request , Model model) throws Exception
	{
		// get parameter
		String contents = Common.getParameterNoXSS(request, "preview_contents", "");
		
		// assign
		model.addAttribute("pathTitle", "표제어관리");
		model.addAttribute("path", "티칭백과 > 표제어관리");
		
		model.addAttribute("contents", contents);
		
		model.addAttribute("footer", "N");
		
		return "popup/headwordContentsPopup";
	}
	
	/**
	 * 참고자료의 데이타를 처리한다.
	 * 반환은 현재 데이타의 리스트를 반환한다.
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/additionalAction.do")
	public @ResponseBody Map<String,Object> additionalAction(HttpServletRequest request , Model model) throws Exception
	{
		Map<String,Object> result = new HashMap<String,Object>();
		
		// get parameters
		String mode = Common.getParameter(request, "mode", "");
		int headwordId = Common.getParameter(request, "headwordId", 0);
		int mmId = Common.getParameter(request, "mmId", 0);
		
		// validation check
		if( Validate.isEmpty(mode)
				|| headwordId == 0
				|| mmId == 0)
		{
			result.put("result" , "FAILURE");
			result.put("resultType" , "PARAMETER");
			result.put("msg" , "정상적인 요청이 아닙니다.");
			return result;
		}
		
		// make query paramters
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("headwordId" , headwordId);
		params.put("mmId" , mmId);
		
		if( mode.equals("add") )
			smartService.insertAdditional(params);
		else if( mode.equals("delete"))
			smartService.deleteAdditional(params);
		
		// get list data
		List<Multimedia> list = smartService.getAdditionalList(headwordId);
		
		// assign
		result.put("result" , "SUCCESS");
		result.put("list" , list);
		
		return result;
	}
	
	/**
	 * 썸네일을 삭제한다.
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteThumbnail.do" , method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> deleteThumbnail(HttpServletRequest request , Model model) throws Exception
	{
		Map<String,Object> result = new HashMap<String,Object>();
		
		// get parameter
		int headwordId = Common.getParameter(request, "headwordId", 0);
		
		if( headwordId == 0 )
		{
			result.put("result" , "FAILURE");
			result.put("resultType" , "PARAMETER");
			result.put("msg" , "파라미터 오류");
			return result;
		}
		
		// get data
		Headword headword = (Headword) smartService.getHeadword(headwordId);
		
		if( !Validate.isEmpty( headword.getThumbnail() ))
		{
			File file = new File(serverFilePath + headword.getThumbnail().replaceAll("/upfiles" , "") );
			
			if( file.isFile() )
				file.delete();
			
			// thumbnail을 갱신한다.
			smartService.deleteThumbnail(headwordId); 
		}
		
		// assign
		result.put("result" , "SUCCESS");
		
		return result;
	}
	
	/**
	 * 지식나눔 리스트
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/knowledgeList.do")
	public String knowledgeList(HttpServletRequest request , Model model) throws Exception
	{
		final int PAGE_SIZE = 10;
		final int BLOCK_SIZE = 10;
		
		// get parameter
		int pg = Common.getParameter(request, "pg", 1);
		int skip = (pg - 1) * PAGE_SIZE;
		String findIsApproval = Common.getParameter(request, "findIsApproval", "");
		String findMethod = Common.getParameter(request, "findMethod", "");
		String findStr = Common.getParameter(request, "findStr", "");
		
		// make query parameters
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("findIsApproval" , findIsApproval);
		params.put("findMethod" , findMethod);
		params.put("findStr" , findStr);
		params.put("skip" , skip);
		params.put("pageSize" , PAGE_SIZE);
		
		// get data
		int totalCount = smartService.getKnowledgeListCount(params);
		int articleNum = totalCount - skip;
		List<Knowledge> knowledges = smartService.getKnowledgeList(params);
		
		Pagination pagination = new Pagination(pg , PAGE_SIZE , BLOCK_SIZE , totalCount);
		pagination.initialize();
		
		// assign
		model.addAttribute("pathTitle", "표제어관리");
		model.addAttribute("path", "티칭백과 > 지식나눔");
		
		model.addAttribute("pg", pg);		
		model.addAttribute("findIsApproval", findIsApproval);
		model.addAttribute("findMethod", findMethod);
		model.addAttribute("findStr", findStr);
		
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("articleNum", articleNum);
		model.addAttribute("knowledges", knowledges);
		model.addAttribute("paging", pagination.print());
		
		return "admin/smart/knowledgeList";
	}
	
	/**
	 * 지식나눔 상세화면
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/knowledgeForm.do")
	public String knowledgeForm(HttpServletRequest request , Model model) throws Exception
	{
		// get parameters
		int knowledgeId = Common.getParameter(request, "knowledgeId", 0);
		int pg = Common.getParameter(request, "pg", 1);
		String findIsApproval = Common.getParameter(request, "findIsApproval", "");
		String findMethod = Common.getParameter(request, "findMethod", "");
		String findStr = Common.getParameter(request, "findStr", "");
		
		// validation check
		if( knowledgeId == 0 )
			throw new CommonException("정상적인 접근이 아닙니다." , CommonException.HISTORY_BACK);
		
		// get data
		Knowledge knowledge = (Knowledge) smartService.getKnowledge(knowledgeId);
		
		// assign
		model.addAttribute("pathTitle", "표제어관리");
		model.addAttribute("path", "티칭백과 > 지식나눔");
		
		model.addAttribute("knowledgeId", knowledgeId);
		model.addAttribute("pg", pg);
		model.addAttribute("findIsApproval", findIsApproval);
		model.addAttribute("findMethod", findMethod);
		model.addAttribute("findStr", findStr);
		
		model.addAttribute("knowledge", knowledge);
		
		return "admin/smart/knowledgeForm";
	}
	
	/**
	 * 지식나눔의 상태를 변경한다.
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/knowledgeChangeStatus.do" , method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> knowledgeChangeStatus(HttpServletRequest request , Model model) throws Exception
	{
		Map<String,Object> result = new HashMap<String,Object>();
		
		// get sessoin
		HttpSession session = (HttpSession) request.getSession();
		Member adminMember = (Member) session.getAttribute("adminMember");
		
		if( adminMember == null )
		{
			result.put("result" , "FAILURE");
			result.put("resultType" , "LOGIN");
			result.put("msg" , "로그인 후 이용가능합니다.");
			return result;
		}
		
		// get parameter
		int knowledgeId = Common.getParameter(request, "knowledgeId", 0);
		String isApproval = Common.getParameter(request, "isApproval", "");
		
		// validation check
		if( knowledgeId == 0
				|| Validate.isEmpty(isApproval))
		{
			result.put("result" , "FAILURE");
			result.put("resultType" , "PARAMETER");
			result.put("msg" , "파라미터 에러");
			return result;
		}
		
		// make query paraemters
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("isApproval" , isApproval);
		params.put("approvalId" , adminMember.getUserId());
		params.put("knowledgeId" , knowledgeId);
		
		smartService.changeKnowledgeStatus(params);

		// assign
		result.put("result" , "SUCCESS");
		
		return result;
	}
	
	/**
	 * 지식나눔을 삭제한다.
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/knowledgeDelete.do" , method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> knowledgeDelete(HttpServletRequest request , Model model) throws Exception
	{
		Map<String,Object> result = new HashMap<String,Object>();
		
		// get sessoin
		HttpSession session = (HttpSession) request.getSession();
		Member adminMember = (Member) session.getAttribute("adminMember");
		
		if( adminMember == null )
		{
			result.put("result" , "FAILURE");
			result.put("resultType" , "LOGIN");
			result.put("msg" , "로그인 후 이용가능합니다.");
			return result;
		}
		
		// get parameter
		int knowledgeId = Common.getParameter(request, "knowledgeId", 0);
		
		// validation check
		if( knowledgeId == 0 )
		{
			result.put("result" , "FAILURE");
			result.put("resultType" , "PARAMETER");
			result.put("msg" , "파라미터 에러");
			return result;
		}
		
		// make query paraemters
		smartService.deleteKnowledge(knowledgeId);

		// assign
		result.put("result" , "SUCCESS");
		
		return result;
	}
	
	/**
	 * 댓글 리스트 관리
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/commentList.do")
	public String commentList(HttpServletRequest request , Model model) throws Exception
	{
		final int PAGE_SIZE = 10;
		final int BLOCK_SIZE = 10;
		
		// get parameter
		int pg = Common.getParameter(request, "pg", 1);
		int skip = (pg - 1) * PAGE_SIZE;
		
		// make query parameters
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("skip" , skip);
		params.put("pageSize" , PAGE_SIZE);
		
		// get comment list
		int totalCount = (Integer) smartService.getTotalCommentListCount();
		int articleNum = totalCount - skip;
		List<Comment> comments = (List<Comment>) smartService.getTotalCommentList(params);
			
		// pagination
		Pagination pagination = new Pagination(pg, PAGE_SIZE, BLOCK_SIZE, totalCount);
		pagination.initialize();
		
		// assign
		model.addAttribute("pathTitle", "댓글관리");
		model.addAttribute("path", "티칭백과 > 표제어관리");
		
		model.addAttribute("pg", pg);
		
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("articleNum" , articleNum);
		model.addAttribute("comments", comments);
		model.addAttribute("paging", pagination.print());
		
		return "admin/smart/commentList";
	}
	
	/**
	 * 댓글을 삭제한다.
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/commentDelete.do" , method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> commentDelete(HttpServletRequest request , Model model) throws Exception
	{
		Map<String,Object> result = new HashMap<String,Object>();
		
		// get sessoin
		HttpSession session = (HttpSession) request.getSession();
		Member adminMember = (Member) session.getAttribute("adminMember");
		
		if( adminMember == null )
		{
			result.put("result" , "FAILURE");
			result.put("resultType" , "LOGIN");
			result.put("msg" , "로그인 후 이용가능합니다.");
			return result;
		}
		
		// get parameter
		int commentId = Common.getParameter(request, "commentId", 0);
		
		// validation check
		if( commentId == 0 )
		{
			result.put("result" , "FAILURE");
			result.put("resultType" , "PARAMETER");
			result.put("msg" , "파라미터 에러");
			return result;
		}
		
		// make query paraemters
		smartService.deleteComment(commentId);

		// assign
		result.put("result" , "SUCCESS");
		
		return result;
	}
	
	/**
	 * 표제어를 삭제한다.
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteHeadword.do" , method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> deleteHeadword(HttpServletRequest request , Model model) throws Exception
	{	
		Map<String,Object> result = new HashMap<String,Object>();
		
		// get parameters
		int headwordId = Common.getParameter(request, "headwordId", 0);
		
		// check validation
		if( headwordId == 0 )
		{
			result.put("result" , "FAILURE");
			result.put("msg" , "파라미터 에러.");
			return result;
		}
		
		smartService.deleteHeadword(headwordId);
		
		result.put("result" , "SUCCESS");
		
		return result;
	}
	
	/**
	 * 도서 삭제
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteSmartBook.do" , method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> deleteSmartBook(HttpServletRequest request , Model model) throws Exception
	{
		Map<String,Object> result = new HashMap<String,Object>();
		
		// get parameters
		int bookId = Common.getParameter(request, "bookId", 0);
		
		// check validation
		if( bookId == 0 )
		{
			result.put("result" , "FAILURE");
			result.put("msg" , "파라미터 오류");
			return result;
		}
		
		// make query parameters
		smartService.deleteSmartBook(bookId);
		
		// assign
		result.put("result" , "SUCCESS");
		
		return result;
	}
}