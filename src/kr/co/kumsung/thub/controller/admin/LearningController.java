package kr.co.kumsung.thub.controller.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import kr.co.kumsung.thub.domain.Book;
import kr.co.kumsung.thub.domain.Category;
import kr.co.kumsung.thub.domain.Cd;
import kr.co.kumsung.thub.domain.Data;
import kr.co.kumsung.thub.domain.FileInfo;
import kr.co.kumsung.thub.domain.Headword;
import kr.co.kumsung.thub.domain.LegacyBook;
import kr.co.kumsung.thub.domain.Member;
import kr.co.kumsung.thub.domain.Multimedia;
import kr.co.kumsung.thub.domain.Unit;
import kr.co.kumsung.thub.exception.CommonException;
import kr.co.kumsung.thub.service.CategoryService;
import kr.co.kumsung.thub.service.FileService;
import kr.co.kumsung.thub.service.LearningService;
import kr.co.kumsung.thub.service.MemberService;
import kr.co.kumsung.thub.service.MultimediaService;
import kr.co.kumsung.thub.service.UnitService;
import kr.co.kumsung.thub.service.impl.MemberServiceImpl;
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
import org.springframework.web.multipart.MultipartHttpServletRequest;



/**
 * 교수학습자료 컨트롤러
 * @author mikelim
 *
 */
@Component("adminLearningController")
@Controller
@RequestMapping("/admin/learning")
public class LearningController {
	private static final Logger logger = LoggerFactory.getLogger(LearningController.class);
	@Autowired
	private LearningService learningService;
	
	@Autowired
	private UnitService unitService;
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private MultimediaService multimediaService;
	
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
		
		params.put("pg" , pg);
		params.put("findCategory" , findCategory);
		params.put("findMethod" , findMethod);
		params.put("findStr" , findStr);
		params.put("findCourse" , findCourse);
		params.put("findIsUse" , findIsUse);
		params.put("findStartDate" , findStartDate);
		params.put("findEndDate" , findEndDate);
		params.put("skip" , skip);
		params.put("pageSize" , PAGE_SIZE);
		
		// get data
		int totalBooks = learningService.getTotalBooks(params);
		List<Book> list = learningService.getBookList(params);
		int articleNum = totalBooks - skip;
		
		// category data
		List<Category> categories = (List<Category>) categoryService.getChildren(Constants.LEARNING_CATEGORY);
		List<Category> courses = (List<Category>) categoryService.getChildren(Constants.COURSE_CATEGORY);
		
		// paging
		Pagination pagination = new Pagination(pg , PAGE_SIZE , BLOCK_SIZE , totalBooks);
		pagination.setDelimiter("");
		pagination.setPrevLink("Prev");
		pagination.setNextLink("Next");
		
		// assign
		model.addAttribute("pathTitle", "도서관리");
		model.addAttribute("path", "교수·학습 자료관리 > 도서관리");

		model.addAttribute("pg", pg);
		model.addAttribute("findCategory", findCategory);
		model.addAttribute("findMethod", findMethod);
		model.addAttribute("findStr", findStr);
		model.addAttribute("findCourse", findCourse);
		model.addAttribute("findIsUse", findIsUse);
		model.addAttribute("findStartDate", findStartDate);
		model.addAttribute("findEndDate", findEndDate);
		model.addAttribute("categories", categories);
		model.addAttribute("courses", courses);
		
		model.addAttribute("totalBooks", totalBooks);
		model.addAttribute("list", list);
		model.addAttribute("articleNum", articleNum);
		model.addAttribute("paging", pagination.print());
		
		return "admin/learning/bookList";
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
		Book book = (bookId == 0) ? new Book() : learningService.getBook(bookId);
		List<Category> categories = categoryService.getChildren(Constants.LEARNING_CATEGORY);	// 스마트 카테고리 디폴트코드
		List<Category> courses = categoryService.getChildren(Constants.COURSE_CATEGORY);	// 교육과정 코드
		List<Unit> units = unitService.getUnitList(Constants.LEARNING_CATEGORY , bookId);
		List<Cd> cdes = new ArrayList<Cd>();
		LegacyBook legacyBook = new LegacyBook();
		List<Category> specialize_categories = (List<Category>) categoryService.getChildren(Constants.SPECIALIZE_CATEGORY); //특화자료 카테고리
		
		if( mode.equals("modify") )
		{
			cdes = learningService.getCdList(bookId);
			legacyBook = learningService.getLegacyBook(book.getLegacyId());
		}
		
		// assign
		model.addAttribute("pathTitle", "도서관리");
		model.addAttribute("path", "교수·학습 자료관리 > 도서관리");

		model.addAttribute("pg", pg);		
		model.addAttribute("bookId", bookId);
		model.addAttribute("findCategory", findCategory);
		model.addAttribute("findMethod", findMethod);
		model.addAttribute("findStr", findStr);
		model.addAttribute("findCourse", findCourse);
		model.addAttribute("findIsUse", findIsUse);
		model.addAttribute("findStartDate", findStartDate);
		model.addAttribute("findEndDate", findEndDate);
		
		model.addAttribute("mode" , mode);
		model.addAttribute("book" , book);
		model.addAttribute("legacyBook" , legacyBook);
		model.addAttribute("categories" , categories);
		model.addAttribute("courses" , courses);
		model.addAttribute("units" , units);
		model.addAttribute("cdes" , cdes);
		model.addAttribute("specialize_categories" , specialize_categories);
		
		return "admin/learning/bookForm";
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
		HttpSession session = (HttpSession) request.getSession();
		Member member = (Member) session.getAttribute("adminMember");
		
		// get parameters
		int bookId = Common.getParameter(request, "bookId", 0);
		int pg = Common.getParameter(request, "pg", 1);
		int legacyId = Common.getParameter(request, "legacyId", 0);
		String mode = Common.getParameter(request, "mode", "add");
		String isUse = Common.getParameter(request, "isUse", "");
		String category = Common.getParameter(request, "category", "");
		String course = Common.getParameter(request, "course", "");
		String name = Common.getParameter(request, "name", "");
		String author = Common.getParameter(request, "author", "");
		String teacherCdView = Common.getParameterNoXSS(request, "teacherCdView", "");
		String previewUrl = Common.getParameterNoXSS(request, "previewUrl"	, "");
		int sequence = Common.getParameter(request, "sequence", 0);
		int cdViewWidth = Common.getParameter(request, "cdViewWidth", 0);
		int cdViewHeight = Common.getParameter(request, "cdViewHeight", 0);
		String special_cd = Common.getParameter(request, "spc_category"	, "");
		
		if( bookId > 0 && mode.equals("add") ) mode = "modify";
		
		// check validation
		if( Validate.isEmpty(category)
				|| Validate.isEmpty(name)
				|| legacyId == 0)
		{
			throw new CommonException("파라미터 에러." , CommonException.HISTORY_BACK);
		}
		
		// make query parameters
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("legacyId" , legacyId);
		params.put("category" , category);
		params.put("course" , course);
		params.put("name" , name);
		params.put("author" , author);
		params.put("isUse" , isUse);
		params.put("teacherCdView" , teacherCdView);
		params.put("previewUrl" , previewUrl);
		params.put("regId" , member.getUserId());
		params.put("sequence" , sequence);
		params.put("cdViewWidth" , cdViewWidth);
		params.put("cdViewHeight" , cdViewHeight);
		params.put("specialCd" , special_cd);
		
		String redirect = "";
		
		if( mode.equals("add") )
		{
			bookId = learningService.insertBook(params);
			
			redirect = String.format("redirect:/admin/learning/bookForm.do?pg=%d&bookId=%d" ,
								pg ,
								bookId
							);
		}
		else if( mode.equals("modify") )
		{
			params.put("bookId" , bookId);
			
			learningService.updateBook(params);
			
			redirect = String.format("redirect:/admin/learning/bookForm.do?pg=%d&bookId=%d" ,
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
			sequence = unitService.getMaxSequence(Constants.LEARNING_CATEGORY , params);
			params.put("sequence" , sequence);
			
			// get group
			
			// insert data
			unitId = unitService.insertUnit(Constants.LEARNING_CATEGORY , params);
			
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
			unitService.updateUnit(Constants.LEARNING_CATEGORY , params);
		}
		else if( mode.equals("delete") )
		{
			params.put("unitId" , unitId);
			boolean isDelete = unitService.deleteUnit(Constants.LEARNING_CATEGORY , unitId);
			
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
		
		unitService.updateUnitSequence(Constants.LEARNING_CATEGORY , sequences);
		
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
		
		Unit unit = unitService.getUnit(Constants.LEARNING_CATEGORY , unitId);
		
		result.put("result" , "SUCCESS");
		result.put("unit" , unit);
		
		return result;
	}
	
	/**
	 * 자료관리의 리스트
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/dataList.do")
	public String dataList(HttpServletRequest request , Model model) throws Exception
	{
		// get parameters
		int pg = Common.getParameter(request, "pg", 1);
		int findBookId = Common.getParameter(request, "findBookId", 0);
		String findCategory = Common.getParameter(request, "findCategory", "");
		String findUnitIds = Common.getParameter(request, "findUnitIds", "");
		int findUnitId = Common.getParameter(request, "findUnitId", 0);
		String findMethod = Common.getParameter(request, "findMethod", "");
		String findStr = Common.getParameter(request, "findStr", "");
		String findStartDate = Common.getParameter(request, "findStartDate", "");
		String findEndDate = Common.getParameter(request, "findEndDate", "");
		String findDataFlag = Common.getParameter(request, "findDataFlag", "");
		
		int skip = (pg - 1 ) * PAGE_SIZE;
		
		//관리권한식별(아무권한없는 자료관리자 제외)
		HttpSession session = (HttpSession) request.getSession();
		Member adminMember = (Member) session.getAttribute("adminMember");
		String adminAuth1 = adminMember.getSmartAuth();
		String adminAuth2 = adminMember.getAccessAuth();
		String adminAuth3 = adminMember.getLearningAuth();
		String adminAuth4 = adminMember.getBoardAuth();
		
		String[] adminAccessArray = adminAuth2.split(",");
		boolean accessX = false;
		List<String> adminAccessPass = new ArrayList<>();
		for(int i=0; i < adminAccessArray.length; i++) {
			System.out.println("adminAccessArray : "+adminAccessArray[i].toString());
			if(adminAccessArray[i].toString().equals("teacher")) {
				accessX = true;
			}
		}
		System.out.println("adminAuth1 : "+adminAuth1);
		System.out.println("adminAuth2 : "+adminAuth2);
		System.out.println("adminAuth3 : "+adminAuth3);
		System.out.println("adminAuth4 : "+adminAuth4);
				
		if(Validate.isNull(adminAuth1) && Validate.isNull(adminAuth2) && Validate.isNull(adminAuth3) && Validate.isNull(adminAuth4)) {
			return "redirect:dataListblank.do";
		}
		
		System.out.println("accessX :"+accessX);
		if(accessX == false) {
			return "redirect:dataListblank.do";
		}
		
		// make query parameters
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("findCategory" , findCategory);
		params.put("findBookId" , findBookId);
		params.put("findUnitIds" , findUnitIds);
		params.put("findUnitId" , findUnitId);
		params.put("findMethod" , findMethod);
		params.put("findStr" , findStr);
		params.put("findStartDate" , findStartDate);
		params.put("findEndDate" , findEndDate);
		params.put("findDataFlag" , findDataFlag);
		params.put("skip" , skip);
		params.put("pageSize" , PAGE_SIZE);
		
		// get data   
		int totalData = (Integer) learningService.getTotalDataAdmin(params);   
		int articleNum = totalData - skip;
		List<Data> list = (List<Data>) learningService.getDataListAdmin(params);
		List<Category> categories = (List<Category>) categoryService.getChildren(Constants.LEARNING_CATEGORY);
		List<Book> books = (List<Book>) learningService.getBookListByCategory(findCategory);
		
		Pagination pagination = new Pagination(pg, PAGE_SIZE, BLOCK_SIZE, totalData);
		pagination.setDelimiter("");
		pagination.setPrevLink("Prev");
		pagination.setNextLink("Next");
		
		// assign
		model.addAttribute("pathTitle", "자료관리");
		model.addAttribute("path", "교수·학습 자료관리 > 자료관리");
		
		model.addAttribute("pg", pg);
		model.addAttribute("findCategory", findCategory);
		model.addAttribute("findUnitIds" , findUnitIds);
		model.addAttribute("findUnitId", findUnitId);
		model.addAttribute("findBookId", findBookId);
		model.addAttribute("findMethod", findMethod);
		model.addAttribute("findStr", findStr);
		model.addAttribute("findStartDate", findStartDate);
		model.addAttribute("findEndDate", findEndDate);
		model.addAttribute("findDataFlag", findDataFlag);
		model.addAttribute("categories", categories);
		model.addAttribute("books", books);
		
		model.addAttribute("totalData", totalData);
		model.addAttribute("articleNum", articleNum);
		model.addAttribute("list", list);
		model.addAttribute("paging", pagination.print());
		
		return "admin/learning/dataList";
	}
	
	/**
	 * 카테고리에 속한 도서목록을 가지고 온다.
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getLearningBooks.do" , method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> getSmartBooks(HttpServletRequest request , Model model) throws Exception
	{
		Map<String,Object> result = new HashMap<String,Object>();
		
		// get parameters
		String category = Common.getParameter(request, "category", "");
		
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>" + category);	
		
		// validation check
		if( Validate.isEmpty(category) )
		{
			result.put("result" , "FAILURE");
			result.put("msg" , "파라미터 에러.");
			return result;
		}
		
		// get data
		List<Book> books = learningService.getBookListByCategory(category);
			
		result.put("result" , "SUCCESS");
		result.put("books" , books);
		
		//logger.info("books::::::::"+learningService.getBookListByCategory(category));
		
		return result;
	}
	
	@RequestMapping("dataForm.do")
	public String dataForm(HttpServletRequest request , Model model) throws Exception
	{
		// get parameters		
		int pg = Common.getParameter(request, "pg", 1);
		int dataId = Common.getParameter(request, "dataId", 0);
		int findBookId = Common.getParameter(request, "findBookId", 0);
		String findCategory = Common.getParameter(request, "findCategory", "");
		String findUnitIds = Common.getParameter(request, "findUnitIds", "");
		int findUnitId = Common.getParameter(request, "findUnitId", 0);
		String findMethod = Common.getParameter(request, "findMethod", "");
		String findStr = Common.getParameter(request, "findStr", "");
		String findStartDate = Common.getParameter(request, "findStartDate", "");
		String findEndDate = Common.getParameter(request, "findEndDate", "");
		String mode = ( dataId == 0 ) ? "add" : "modify";
		
		// get data
		Data data = new Data();
		Multimedia multimedia = new Multimedia();
		
		
		// make query parameters
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("dataId" , dataId);
		
		if( mode.equals("modify") )
		{
			data = (Data) learningService.getData(params);
			multimedia = (Multimedia) multimediaService.getMultimediaDetail(data.getMmId());
		}
		
		List<Category> categories = (List<Category>) categoryService.getChildren(Constants.LEARNING_CATEGORY);
		List<Category> chasi_categories = (List<Category>) categoryService.getChildren(Constants.TYPE_CHASI_CATEGORY);
		
		List<Category> types = (List<Category>) categoryService.getChildren(Constants.TYPE_CATEGORY);
		List<Book> books = (List<Book>) learningService.getBookListByCategory(data.getCategory());
		
		//차시자료
		List<Data> cview = (List<Data>) learningService.getCDataView(params);
		
		//차시첨부자료
		List<Data> cfile = (List<Data>) learningService.getCDataFile(params);
				
		// assign
		model.addAttribute("pathTitle", "자료관리");
		model.addAttribute("path", "교수·학습 자료관리 > 자료관리");
		
		model.addAttribute("pg", pg);
		model.addAttribute("findCategory", findCategory);
		model.addAttribute("findBookId", findBookId);
		model.addAttribute("findUnitId", findUnitId);
		model.addAttribute("findUnitIds", findUnitIds);
		model.addAttribute("findStartDate", findStartDate);
		model.addAttribute("findEndDate", findEndDate);
		model.addAttribute("findMethod", findMethod);
		model.addAttribute("findStr", findStr);
		model.addAttribute("dataId", dataId);
		model.addAttribute("mode", mode);
		
		model.addAttribute("data", data);
		model.addAttribute("multimedia", multimedia);
		model.addAttribute("categories", categories);
		model.addAttribute("chasi_categories", chasi_categories);
		model.addAttribute("types", types);
		model.addAttribute("books", books);
		model.addAttribute("cview", cview);
		model.addAttribute("cfile", cfile);
		
		return "admin/learning/dataForm";
	}
	
	@RequestMapping(value="dataFormAction.do" , method=RequestMethod.POST)
	public String dataFormAction(@RequestParam(value="upfile" , required=false) MultipartFile upfile , MultipartHttpServletRequest request , Model model) throws Exception {

		HttpSession session = (HttpSession) request.getSession();
		Member member = (Member) session.getAttribute("adminMember");
	
		int dataId = Common.getParameter(request, "dataId", 0);
		int pg = Common.getParameter(request, "pg", 1);
		String mode = Common.getParameter(request, "mode", "add");
		String category = Common.getParameter(request, "category", "");
		int bookId = Common.getParameter(request, "bookId", 0);
		int unitId = Common.getParameter(request, "unitId", 0);
		String dataFlag = Common.getParameter(request, "dataFlag", "");
		String dataType = Common.getParameter(request, "dataType", "");
		String title = Common.getParameter(request, "title", "");
		String keywords = Common.getParameter(request, "keywords", "");
		int mmId = Common.getParameter(request, "mmId", 0);
		
		String[] csubj = Common.getParameters(request, "chasi-preview-subj");
		String[] ccont = Common.getParameters(request, "chasi-preview-cont");
		String[] ctype = Common.getParameters(request, "chasi-data");

		List<MultipartFile> mf = request.getFiles("cupfile");
		String[] cfile = Common.getParameters(request, "cfile_id");
		int file_count = 0;
		int cfileId = 0;
		
		if( dataId > 0 && mode.equals("add") ) mode = "modify";
		
		if( Validate.isEmpty(category) || bookId == 0 || Validate.isEmpty(dataFlag) || Validate.isEmpty(dataType) || Validate.isEmpty(title)){
			throw new CommonException("파라미터 에러." , CommonException.HISTORY_BACK);
		}
		
		Map<String,Object> params = new HashMap<String,Object>();
		Map<String,Object> cparams = new HashMap<String,Object>();
		
		params.put("category" , category);
		params.put("bookId" , bookId);
		params.put("unitId" , unitId);
		params.put("dataFlag" , dataFlag);
		params.put("dataType" , dataType);
		params.put("title" , title);
		params.put("keywords" , keywords);
		params.put("mmId" , mmId);
		params.put("userId" , member.getUserId()); 
		
		if( !(category.substring(1, 4).equals("D003"))&&!(dataType.equals("D006")) && !upfile.isEmpty() )
		{
			FileInfo fileInfo = fileService.upload(upfile, serverFilePath, "learning");
			params.put("fileName" , fileInfo.getOriginName());
			params.put("dataFile" , fileInfo.getAbsolutePath());
		}

		String redirect = "";
		
		if( mode.equals("add") ){
			dataId = learningService.insertData(params);
			
			if(csubj!=null){
				for(int k = 0; k< csubj.length; k++){
					cparams.put("dataId" , dataId);
					cparams.put("sequence" , k+1);
					cparams.put("chasiStep" , csubj[k]);
					cparams.put("chasiPage" , ccont[k]);
					cparams.put("userId" , member.getUserId()); 
					if(!Validate.isEmpty(csubj[k])&&!Validate.isEmpty(ccont[k])) learningService.insertCData(cparams);
				}
			}

			for(int i=0; i<mf.size(); i++){
				upfile = mf.get(i);
				if(!upfile.isEmpty()&&ctype!=null){
					FileInfo fileInfo = fileService.upload(upfile, serverFilePath, "learning");
					params.put("fileName" , fileInfo.getOriginName());
					params.put("dataFile" , fileInfo.getAbsolutePath());
					params.put("dataId" , dataId);
					params.put("sequence" , i+1);
					params.put("dataType" , ctype[i]);
					learningService.insertCDataFile(params);
				}
			}
			redirect = String.format("redirect:/admin/learning/dataList.do?pg=%d" , pg);
		}
		else if( mode.equals("modify") ){
			params.put("dataId" , dataId);
			
			Data data = learningService.getData(params);

			if( !Validate.isEmpty((String)params.get("dataFile")) || mmId > 0){
				if( !Validate.isEmpty(data.getDataFile()) && mmId==0 ){
					String deleteFilePath = String.format("%s%s" , serverFilePath , data.getDataFile().replace("/upfiles" , ""));
					fileService.delete(deleteFilePath);
				}
				
				if( mmId > 0 ){
					params.put("fileName" , "");
					params.put("dataFile" , "");
				}
			}
			else{
				params.put("fileName" , data.getFileName());
				params.put("dataFile" , data.getDataFile());
			}
			
			learningService.updateData(params);
			learningService.deleteCData(params);
			
			if(csubj!=null){
				for(int k = 0; k< csubj.length; k++){
					cparams.put("dataId" , dataId);
					cparams.put("sequence" , k+1);
					cparams.put("chasiStep" , csubj[k]);
					cparams.put("chasiPage" , ccont[k]);
					cparams.put("userId" , member.getUserId()); 
					
					if(!Validate.isEmpty(csubj[k])&&!Validate.isEmpty(ccont[k])) learningService.insertCData(cparams);
				}
			}
			
			if(cfile!= null&&ctype!=null){
				for(int i=0; i<cfile.length; i++){
					upfile = mf.get(i);
					cfileId = Integer.parseInt(cfile[i]);
					if(cfileId>0&&!Validate.isEmpty(ctype[i])){
						params.put("cfileId", cfileId);
						List<Data> cdata_file = learningService.getCDataFile(params);
						cparams.put("cfileId", cfileId);
						cparams.put("dataId" , dataId);
						cparams.put("sequence" , i+1);
						cparams.put("dataType" , ctype[i]);
						cparams.put("userId" , member.getUserId()); 
						if(!upfile.isEmpty()){
							if( !Validate.isEmpty(cdata_file.get(0).getDataFile()) ){
								String deleteFilePath = String.format("%s%s" , serverFilePath , cdata_file.get(0).getDataFile().replace("/upfiles" , ""));
								fileService.delete(deleteFilePath);
							}
							FileInfo fileInfo = fileService.upload(upfile, serverFilePath, "learning");
							cparams.put("fileName" , fileInfo.getOriginName());
							cparams.put("dataFile" , fileInfo.getAbsolutePath());
						}
						else{
							cparams.put("fileName" , cdata_file.get(0).getFileName());
							cparams.put("dataFile" , cdata_file.get(0).getDataFile());
						}
						learningService.updateCDataFile(cparams);
					}
					else{
						if(!upfile.isEmpty()&&!Validate.isEmpty(ctype[i])){
							FileInfo fileInfo = fileService.upload(upfile, serverFilePath, "learning");
							params.put("fileName" , fileInfo.getOriginName());
							params.put("dataFile" , fileInfo.getAbsolutePath());
							params.put("sequence" , i+1+file_count);
							params.put("dataType" , ctype[i]);
							learningService.insertCDataFile(params);
						}
					}
				}
			}

			redirect = String.format("redirect:/admin/learning/dataForm.do?pg=%d&dataId=%d" , pg , dataId);
		}
		
		return redirect;
	}

	//차시자료 미리보기
	@RequestMapping("/chasiPreview.do")
	public String chasiPreview(HttpServletRequest request , Model model) throws Exception
	{
		// get parameters		
		int dataId = Common.getParameter(request, "dataId", 0);
		
		// make query parameters
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("dataId" , dataId);

		// get data
		Data data = learningService.getData(params);
		
		//차시자료
		List<Data> cview = (List<Data>) learningService.getCDataView(params);
		
		model.addAttribute("dataId", dataId);
		model.addAttribute("data", data);
		model.addAttribute("cview", cview);
		model.addAttribute("title", "차시 미리보기");
		model.addAttribute("footer", "N");
		
		return "popup/chasiPreview";
	}	
	
	
	
	/**
	 * 교사용 CD를 입력한다.
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/addTeacherCdDownload.do" , method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> addTeacherCdDownload(HttpServletRequest request , Model model) throws Exception
	{
		Map<String,Object> result = new HashMap<String,Object>();
		
		// get parameters
		int bookId = Common.getParameter(request, "bookId", 0);
		String name = Common.getParameter(request, "name", "");
		String url = Common.getParameter(request, "url", "");
		
		// validation check
		if( Validate.isEmpty(name)
				|| Validate.isEmpty(url)
				|| bookId == 0)
		{
			result.put("result" , "FAILURE");
			result.put("msg" , "정상적인 접근이 아닙니다.");
			return result;
		}
		
		// make query parameters
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("bookId" , bookId);
		params.put("name" , name);
		params.put("url" , url);
		
		// insert data
		int cdId = learningService.insertCd(params);
		
		// add to params
		params.put("cdId" , cdId);

		// set result
		result.put("result" , "SUCCESS");
		result.put("cd" , params);
		
		return result;
	}
	
	/**
	 * 교사용 CD를 삭제한다.
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteTeacherCdDownload.do" , method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> deleteTeacherCdDownload(HttpServletRequest request , Model model) throws Exception
	{
		Map<String,Object> result = new HashMap<String,Object>();
		
		// get parameters
		int cdId = Common.getParameter(request, "cdId", 0);
		
		// validation check
		if( cdId == 0)
		{
			result.put("result" , "FAILURE");
			result.put("msg" , "정상적인 접근이 아닙니다.");
			return result;
		}
		
		learningService.deleteCd(cdId);
		
		// set result
		result.put("result" , "SUCCESS");
		result.put("cdId" , cdId);
		
		return result;
	}

	/**
	 * 도서 검색 팝업
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/legacyBookSearchPopup.do")
	public String legacyBookSearchPopup(HttpServletRequest request , Model model) throws Exception
	{
		// get parameters
		int pg = Common.getParameter(request, "pg", 1);
		int skip = (pg - 1) * PAGE_SIZE;
		String findMethod = Common.getParameter(request, "findMethod", "");
		String findStr = Common.getParameter(request, "findStr", "");
		
		// make query parameters
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("findMethod" , findMethod);
		params.put("findStr" , findStr);
		params.put("skip" , skip);
		params.put("pageSize" , PAGE_SIZE);		
		
		// get data
		int totalCount = learningService.getLegacyBookTotalCount(params);
		int articleNum = totalCount - skip;
		List<LegacyBook> list = learningService.getLegacyBookList(params);
		
		// pagination
		Pagination pagination = new Pagination(pg , PAGE_SIZE , BLOCK_SIZE , totalCount);
		pagination.setDelimiter("");
		pagination.setPrevLink("Prev");
		pagination.setNextLink("Next");
		
		// assign
		model.addAttribute("pg", pg);
		model.addAttribute("findMethod", findMethod);
		model.addAttribute("findStr", findStr);
		
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("articleNum", articleNum);
		model.addAttribute("list", list);
		model.addAttribute("paging", pagination.print());
		
		model.addAttribute("footer", "N");
		
		return "popup/legacyBookSearchPopup";
	}

	/**
	 * 데이타 삭제
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteDataArticle.do" , method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> deleteDataArticle(HttpServletRequest request , Model model) throws Exception
	{
		
		System.out.println("삭제진입");
		
		Map<String,Object> result = new HashMap<String,Object>();
		
		// get parameters
		int dataId = Common.getParameter(request, "dataId", 0);
		
		// check validation
		if( dataId == 0 )
		{
			result.put("result" , "FAILURE");
			result.put("msg" , "파라미터 오류");
			return result;
		}
		
		// make query parameters
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("dataId" , dataId);
		
		learningService.deleteData(params);
		
		// assign
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
	@RequestMapping(value="/deleteLearningBook.do" , method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> deleteLearningBook(HttpServletRequest request , Model model) throws Exception
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
		learningService.deleteLearningBook(bookId);
		
		// assign
		result.put("result" , "SUCCESS");
		
		return result;
	}
	
	/**
	 * 자료관리의 리스트
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/spcDataList.do")
	public String spcDataList(HttpServletRequest request , Model model) throws Exception
	{
		// get parameters
		int pg = Common.getParameter(request, "pg", 1);
		String findCategory = Common.getParameter(request, "findCategory", "");
		String findMethod = Common.getParameter(request, "findMethod", "ss");
		String findStr = Common.getParameter(request, "findStr", "");
		String findStartDate = Common.getParameter(request, "findStartDate", "");
		String findEndDate = Common.getParameter(request, "findEndDate", "");
		
		int skip = (pg - 1 ) * PAGE_SIZE;
		
		// make query parameters
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("findCategory" , findCategory);
		params.put("findMethod" , findMethod);
		params.put("findStr" , findStr);
		params.put("findStartDate" , findStartDate);
		params.put("findEndDate" , findEndDate);
		params.put("skip" , skip);
		params.put("pageSize" , PAGE_SIZE);
		
		// get data   
		int totalData = (Integer) learningService.getTotalSDataAdmin(params);   
		int articleNum = totalData - skip;
		List<Data> list = (List<Data>) learningService.getSDataListAdmin(params);
		List<Category> categories = (List<Category>) categoryService.getChildren(Constants.SPECIALIZE_CATEGORY);
		List<Category> categories3 = (List<Category>) categoryService.getChildren(Constants.SPECIALIZE_CATEGORY, Constants.THIRD_DEPTH);
		
		Pagination pagination = new Pagination(pg, PAGE_SIZE, BLOCK_SIZE, totalData);
		pagination.setDelimiter("");
		pagination.setPrevLink("Prev");
		pagination.setNextLink("Next");
		
		// assign
		model.addAttribute("pathTitle", "특화자료");
		model.addAttribute("path", "교수·학습 자료관리 > 특화자료");
		
		model.addAttribute("pg", pg);
		model.addAttribute("findCategory", findCategory);
		model.addAttribute("findMethod", findMethod);
		model.addAttribute("findStr", findStr);
		model.addAttribute("findStartDate", findStartDate);
		model.addAttribute("findEndDate", findEndDate);
		model.addAttribute("categories", categories);
		model.addAttribute("categories3", categories3);
		model.addAttribute("totalData", totalData);
		model.addAttribute("articleNum", articleNum);
		model.addAttribute("list", list);
		model.addAttribute("paging", pagination.print());
		
		return "admin/learning/spcDataList";
	}
	
	@RequestMapping("spcDataForm.do")
	public String sDataForm(HttpServletRequest request , Model model) throws Exception
	{
		// get parameters		
		int pg = Common.getParameter(request, "pg", 1);
		int sdataId = Common.getParameter(request, "sdataId", 0);
		String findCategory = Common.getParameter(request, "findCategory", "");
		String findMethod = Common.getParameter(request, "findMethod", "");
		String findStr = Common.getParameter(request, "findStr", "");
		String findStartDate = Common.getParameter(request, "findStartDate", "");
		String findEndDate = Common.getParameter(request, "findEndDate", "");
		String mode = ( sdataId == 0 ) ? "add" : "modify";
		
		// get data
		Data data = new Data();
		
		// make query parameters
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("sdataId" , sdataId);
		
		if( mode.equals("modify") )
		{
			data = (Data) learningService.getSData(params);
			
		}
		
		List<Category> categories = (List<Category>) categoryService.getChildren(Constants.SPECIALIZE_CATEGORY);
		List<Category> categories3 = (List<Category>) categoryService.getChildren(Constants.SPECIALIZE_CATEGORY, Constants.THIRD_DEPTH);
				
		// assign
		model.addAttribute("pathTitle", "특화자료");
		model.addAttribute("path", "교수·학습 자료관리 > 특화자료");
		
		model.addAttribute("pg", pg);
		model.addAttribute("findCategory", findCategory);
		model.addAttribute("findStartDate", findStartDate);
		model.addAttribute("findEndDate", findEndDate);
		model.addAttribute("findMethod", findMethod);
		model.addAttribute("findStr", findStr);
		model.addAttribute("sdataId", sdataId);
		model.addAttribute("mode", mode);
		
		model.addAttribute("data", data);
		model.addAttribute("categories", categories);
		model.addAttribute("categories3", categories3);
		
		return "admin/learning/spcDataForm";
	}
	
	@RequestMapping(value="sDataFormAction.do" , method=RequestMethod.POST)
	public String sDataFormAction(@RequestParam(value="upfile" , required=false) MultipartFile upfile ,MultipartHttpServletRequest request , Model model) throws Exception
	{
		// get session
		HttpSession session = (HttpSession) request.getSession();
		Member member = (Member) session.getAttribute("adminMember");
	
		// get parameters
		int sdataId = Common.getParameter(request, "sdataId", 0);
		int pg = Common.getParameter(request, "pg", 1);
		String mode = Common.getParameter(request, "mode", "add");
		String category = Common.getParameter(request, "category", "");
		String title = Common.getParameter(request, "title", "");
		String keywords = Common.getParameter(request, "keywords", "");
		String redirect = "";
		Map<String,Object> params = new HashMap<String,Object>();
		
		if( sdataId > 0 && mode.equals("add") ) mode = "modify";
		
		// check validation
		if( Validate.isEmpty(category) || Validate.isEmpty(title)){
			throw new CommonException("파라미터 에러." , CommonException.HISTORY_BACK);
		}
		
		// make query parameters
		
		params.put("category"        , category);
		params.put("title"           , title);
		params.put("keywords"        , keywords);
		params.put("userId"          , member.getUserId());
		
		// dataFlag가 단원자료(U)일 경우 첨부파일에 대한 업로드를 시작한다.
		if( !upfile.isEmpty() ){
			
			FileInfo fileInfo = fileService.upload(upfile, serverFilePath, "learning");
			params.put("fileName" , fileInfo.getOriginName());
			params.put("dataFile" , fileInfo.getAbsolutePath());
		}
		
		if( mode.equals("add") ){
			
			sdataId = learningService.insertSData(params);
			redirect = String.format("redirect:/admin/learning/spcDataList.do?pg=%d" , pg);
			
		}
		
		else if( mode.equals("modify") ){
			
			params.put("sdataId" , sdataId);
			// 기존의 파일 정보를 가지고 온다.
			Data data = learningService.getSData(params);
			// 파일에 대한 정보가 존재하거나 멀티미디어 자료일 경우에는 기존 파일을 삭제한다.
			if( !Validate.isEmpty((String)params.get("dataFile"))){
				if( !Validate.isEmpty(data.getDataFile()) ){
					String deleteFilePath = String.format("%s%s" , serverFilePath , data.getDataFile().replace("/upfiles" , ""));
					fileService.delete(deleteFilePath);
				}
			}
			
			else{
				// 기존의 파일 정보로 대체한다.
				params.put("fileName" , data.getFileName());
				params.put("dataFile" , data.getDataFile());
			}
			
			learningService.updateSData(params);
			redirect = String.format("redirect:/admin/learning/spcDataForm.do?pg=%d&sdataId=%d" , pg , sdataId);
		}
		return redirect;
	}
	
	/**
	 * 특화데이타 삭제
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/deletesDataArticle.do" , method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> deletesDataArticle(HttpServletRequest request , Model model) throws Exception
	{
		Map<String,Object> result = new HashMap<String,Object>();
		
		// get parameters
		int sdataId = Common.getParameter(request, "sdataId", 0);
		
		// check validation
		if( sdataId == 0 )
		{
			result.put("result" , "FAILURE");
			result.put("msg" , "파라미터 오류");
			return result;
		}
		
		// make query parameters
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("sdataId" , sdataId);
		
		learningService.deleteSData(params);
		
		// assign
		result.put("result" , "SUCCESS");
		
		return result;
	}
	
	@RequestMapping(value="/delChasiViewEach.do" , method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> deleteChasiView(HttpServletRequest request , Model model) throws Exception
	{
		Map<String,Object> result = new HashMap<String,Object>();
		
		// get parameters
		int cviewId = Common.getParameter(request, "cviewId", 0);
		
		// check validation
		if( cviewId == 0 )
		{
			result.put("result" , "FAILURE");
			result.put("msg" , "파라미터 오류");
			return result;
		}
		
		// make query parameters
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("cviewId" , cviewId);
		
		learningService.delChasiViewEach(params);
		
		// assign
		result.put("result" , "SUCCESS");
		
		return result;
	}
	
	@RequestMapping(value="/delChasiFileEach.do" , method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> deleteChasiFileEach(HttpServletRequest request , Model model) throws Exception
	{
		Map<String,Object> result = new HashMap<String,Object>();
		
		// get parameters
		int cfileId = Common.getParameter(request, "cfileId", 0);
		String dataFile = Common.getParameter(request, "dataFile", "");
		
		// check validation
		if( cfileId == 0 || Validate.isEmpty(dataFile))
		{
			result.put("result" , "FAILURE");
			result.put("msg" , "파라미터 오류");
			return result;
		}
		
		// make query parameters
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("cfileId" , cfileId);
		
		learningService.delChasiFileEach(params);

		String deleteFilePath = String.format("%s%s" , serverFilePath , dataFile.replace("/upfiles" , ""));
		fileService.delete(deleteFilePath);

		
		// assign
		result.put("result" , "SUCCESS");
		
		return result;
	}
	
	@RequestMapping("/dataListblank.do")
	public String dataListblank(HttpServletRequest request , Model model) throws Exception
	{
		System.out.println("dataListblank.do");
		return "admin/member/dataListblank";
	}
}


