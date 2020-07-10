package kr.co.kumsung.thub.controller.front.thub;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import kr.co.kumsung.thub.domain.Book;
import kr.co.kumsung.thub.domain.Category;
import kr.co.kumsung.thub.domain.Cd;
import kr.co.kumsung.thub.domain.CurriculumContact;
import kr.co.kumsung.thub.domain.Data;
import kr.co.kumsung.thub.domain.FileInfo;
import kr.co.kumsung.thub.domain.Member;
import kr.co.kumsung.thub.domain.Multimedia;
import kr.co.kumsung.thub.domain.Unit;
import kr.co.kumsung.thub.exception.CommonException;
import kr.co.kumsung.thub.service.CategoryService;
import kr.co.kumsung.thub.service.CurriculumContactService;
import kr.co.kumsung.thub.service.FileService;
import kr.co.kumsung.thub.service.LearningService;
import kr.co.kumsung.thub.service.MemberService;
import kr.co.kumsung.thub.service.MultimediaService;
import kr.co.kumsung.thub.service.UnitService;
import kr.co.kumsung.thub.setting.Constants;
import kr.co.kumsung.thub.util.Common;
import kr.co.kumsung.thub.util.FrontPagination2;
import kr.co.kumsung.thub.util.ResultMap;
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

@Component("thubLearningController")
@Controller
@RequestMapping("/learning")
public class LearningController {
	private static final Logger logger = LoggerFactory.getLogger(LearningController.class);
	@Autowired
	private LearningService learningService;
	
	@Autowired
	private UnitService unitService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private MultimediaService multimediaService;
	
	@Autowired
	private CurriculumContactService curriculumContactService;
	
	@Value("#{common['file.path']}") String serverFilePath;		// 첨부파일의 root 경로
	
	@RequestMapping("/detail.do")
	public String detail(HttpServletRequest request , Model model) throws Exception {
		HttpSession session = (HttpSession) request.getSession();
		Member member = (Member) session.getAttribute("member");
		String category = Common.getParameter(request, "category", "");
		
		int bookId = Common.getParameter(request, "bookId" , 0);
		int unitId = Common.getParameter(request, "unitId", 0);
		int sunitId = Common.getParameter(request, "sunitId", 0);
		String dataType = Common.getParameter(request, "dataType", "D001");
		int cunitId = unitId;
		
		if( bookId == 0 ) {
			throw new CommonException("정상적인 접근이 아닙니다." , CommonException.HISTORY_BACK);
		}
			
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("category" , category);
		params.put("bookId" , bookId);
		params.put("depth" , "2");
		params.put("dataType" , dataType);
		String authCategory = category;
		if(category.length()>7) {
			authCategory = category.substring(0, 7);
		}
			
		boolean isAccessLearning = learningService.isAccessLearning(member, authCategory);
		boolean isAccessLearning_fgclass = learningService.isAccessLearning_fgclass(member, authCategory);
		Book book = (Book) learningService.getBook(bookId);
		
		List<Unit> units = (List<Unit>) unitService.getMasterUnitListAll(Constants.LEARNING_CATEGORY , params);
		//List<Unit> units = (List<Unit>) unitService.getMasterUnitListAll_re(Constants.LEARNING_CATEGORY , params);
		
		if( unitId == 0 && units.size() > 0 ) {
			unitId = ((Unit) units.get(0)).getUnitId();
		}
					
		params.put("unitId" , unitId);			
		List<Data> unitDataList = (List<Data>)learningService.getUnitDataList(params);
		//List<Data> unitDataList = (List<Data>)learningService.getUnitDataList_re(params);
		List<ResultMap> commonDataSubList = (List<ResultMap>) learningService.getCommonDataSubCategoryList(params);
		//List<ResultMap> commonDataSubList = (List<ResultMap>) learningService.getCommonDataSubCategoryList_re(params);
		List<ResultMap> commonDataSubSList = (List<ResultMap>) learningService.getCommonDataSubCategorySList(params);
		//List<ResultMap> commonDataSubSList = (List<ResultMap>) learningService.getCommonDataSubCategorySList_re(params);
		Unit unit = (Unit) unitService.getUnit(Constants.LEARNING_CATEGORY, unitId);
		//Unit unit = (Unit) unitService.getUnit_re(Constants.LEARNING_CATEGORY, unitId);
		List<Unit> childrenUnits = (List<Unit>) unitService.getChildrenUnitList(Constants.LEARNING_CATEGORY, params);
		//List<Unit> childrenUnits = (List<Unit>) unitService.getChildrenUnitList_re(Constants.LEARNING_CATEGORY, params);
		int totalCommonDataCount = (Integer) learningService.getCommonDataTotalCount(params);
		//int totalCommonDataCount = (Integer) learningService.getCommonDataTotalCount_re(params);
		List<ResultMap> commonDataList = (List<ResultMap>) learningService.getCommonDataCategoryList(params);
		//List<ResultMap> commonDataList = (List<ResultMap>) learningService.getCommonDataCategoryList_re(params);
		List<ResultMap> typesDataList = (List<ResultMap>) learningService.getTypesDataCategoryList(params);
		//List<ResultMap> typesDataList = (List<ResultMap>) learningService.getTypesDataCategoryList_re(params);
		List<Data> shareDataList = (List<Data>) learningService.getShareDataList(params);
		//List<Data> shareDataList = (List<Data>) learningService.getShareDataList_re(params);
		List<Book> books = (List<Book>) learningService.getBookListByCategory(book.getCategory());
		//List<Book> books = (List<Book>) learningService.getBookListByCategory_re(book.getCategory());
		CurriculumContact curriculum = (CurriculumContact) curriculumContactService.getCurriculumContactDetailFromCategory(category);
		//CurriculumContact curriculum = (CurriculumContact) curriculumContactService.getCurriculumContactDetailFromCategory_re(category);
		List<Cd> cdes = new ArrayList<Cd>();
		
		cdes = learningService.getCdList(bookId);
		model.addAttribute("beforePath", "교수학습자료");
		model.addAttribute("currentPath", book.getName());
		model.addAttribute("unitDataList", unitDataList);
		model.addAttribute("commonDataSubList", commonDataSubList);
		model.addAttribute("commonDataSubSList", commonDataSubSList);
		model.addAttribute("category", category);
		model.addAttribute("book", book);
		model.addAttribute("unit", unit);
		model.addAttribute("sunitId", sunitId);
		model.addAttribute("cunitId", cunitId);
		model.addAttribute("dataType", dataType);
		model.addAttribute("units", units);
		model.addAttribute("childrenUnits", childrenUnits);
		model.addAttribute("isAccessLearning", isAccessLearning);
		model.addAttribute("totalCommonDataCount", totalCommonDataCount);
		model.addAttribute("commonDataList", commonDataList);
		model.addAttribute("typesDataList", typesDataList);
		model.addAttribute("shareDataList", shareDataList);
		model.addAttribute("books", books);
		model.addAttribute("curriculum", curriculum);
		model.addAttribute("cdes" , cdes);
		model.addAttribute("isAccessLearning_fgclass", isAccessLearning_fgclass);

		if( unit == null )
			throw new CommonException("단원 미등록 과목입니다." , CommonException.HISTORY_BACK);
		
		return "front/thub/learning/detail";
	}
	
	/**
	 * 유형별 자료모음
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/types.do")
    public String types(HttpServletRequest request, Model model) throws Exception {
        int PAGE_SIZE = 10;
        int BLOCK_SIZE = 10;
        HttpSession session = request.getSession();
        Member member = (Member)session.getAttribute("member");
        int pg = Common.getParameter(request, "pg", 1);
        String category = Common.getParameter(request, "category", "");
        int bookId = Common.getParameter(request, "bookId", 0);
        int unitId = Common.getParameter(request, "unitId", 0);
        String dataType = Common.getParameter(request, "dataType", "D001");
        String findTypeCategory = Common.getParameter(request, "findTypeCategory", "");
        int findParentUnitId = Common.getParameter(request, "findParentUnitId", 0);
        
		if( findTypeCategory.indexOf("D003") >= 0 ){
			PAGE_SIZE = 12;
		}else if(findTypeCategory.indexOf("D006") >= 0 ){
			PAGE_SIZE = 1000;
		}
        
        int skip = (pg - 1) * PAGE_SIZE;

        if(bookId == 0)
        	throw new CommonException("정상적인 접근이 아닙니다." , CommonException.HISTORY_BACK);
        
        Map params = new HashMap();
        
        params.put("category", category);
        params.put("findCategory", category);
        params.put("bookId", Integer.valueOf(bookId));
        params.put("findBookId", Integer.valueOf(bookId));
        params.put("depth", "2");
        params.put("dataType", dataType);
        params.put("findTypeCategory", findTypeCategory);
        params.put("findParentUnitId", Integer.valueOf(findParentUnitId));
        params.put("order", "sequence");
        params.put("skip", Integer.valueOf(skip));
        params.put("pageSize", Integer.valueOf(PAGE_SIZE));
        
        String findTypeCategoryForChildren = findTypeCategory;
        Category currentCategory = categoryService.getCategory(findTypeCategoryForChildren.substring(0, 4));
        String authCategory = category;
        
        if(category.length() > 7)
            authCategory = category.substring(0, 7);
        
        boolean isAccessLearning = learningService.isAccessLearning(member, authCategory);
        boolean isAccessLearning_fgclass = learningService.isAccessLearning_fgclass(member, authCategory);

		Book book = learningService.getBook(bookId);
        List units = unitService.getMasterUnitList("A", params);
        List a_units = unitService.getMasterUnitListAll("A", params);
        
        if(unitId == 0 && units.size() > 0)
            unitId = ((Unit)units.get(0)).getUnitId();
        
        params.put("unitId", Integer.valueOf(unitId));
        params.put("findParentUnitId", Integer.valueOf(findParentUnitId));
        
        List cdes = new ArrayList();
        cdes = learningService.getCdList(bookId);
        List unitDataList = learningService.getUnitDataList(params);
        Unit unit = unitService.getUnit("A", unitId);
        int totalCommonDataCount = Integer.valueOf(learningService.getCommonDataTotalCount(params)).intValue();
        List commonDataList = learningService.getCommonDataCategoryList(params);
        List typesDataList = learningService.getTypesDataCategoryList(params);
        List listCategories = categoryService.getChildrenWithDataCount(findTypeCategoryForChildren.substring(0, 4), params);
        
        if(listCategories.size() > 0 && findTypeCategory.length() == 4){
            for(Iterator iterator = listCategories.iterator(); iterator.hasNext();){
                Category typeCategory = (Category)iterator.next();
                if(typeCategory.getDataCount() > 0)
                    return (new StringBuilder("redirect:types.do?findTypeCategory=")).append(typeCategory.getCategory()).append("&findParentUnitId=0&category=").append(category).append("&bookId=").append(bookId).toString();
            }
        }
        int totalDataCount = Integer.valueOf(learningService.getDataListCount(params)).intValue();
        
        //List dataList = learningService.getDataList(params);
		//006 차시별 자료일때 쿼리 
		if(findTypeCategory.indexOf("D006") >= 0){
			List dataList = (List<Data>) learningService.getDataList_chasi(params);
			model.addAttribute("dataList", dataList);
		}
		//003 멀티미디어자료
		else if(findTypeCategory.indexOf("D003") >= 0){
			List dataList = (List<Data>) learningService.getDataList_multi(params);
			model.addAttribute("dataList", dataList);
		}		
		else{
			List dataList = (List<Data>) learningService.getDataList(params);
			model.addAttribute("dataList", dataList);
		}        
        
        List books = learningService.getBookListByCategory(category);
        CurriculumContact curriculum = curriculumContactService.getCurriculumContactDetailFromCategory(category);
        List sub2ListCategories = categoryService.getChildrenWithLeaningCount(findTypeCategoryForChildren, params);
        String selectCategory = category.substring(0, 7);
        FrontPagination2 pagination = new FrontPagination2(pg, PAGE_SIZE, BLOCK_SIZE, totalDataCount);
        pagination.initialize();
        model.addAttribute("beforePath", "교수학습자료");
        model.addAttribute("currentPath", book.getName());
        model.addAttribute("unitDataList", unitDataList);
        model.addAttribute("pg", Integer.valueOf(pg));
        model.addAttribute("category", category);
        model.addAttribute("book", book);
        model.addAttribute("unit", unit);
        model.addAttribute("dataType", dataType);
        model.addAttribute("units", units);
        model.addAttribute("a_units", a_units);
        model.addAttribute("findTypeCategory", findTypeCategory);
        model.addAttribute("findCategory", category);
        model.addAttribute("findParentUnitId", Integer.valueOf(findParentUnitId));
        model.addAttribute("isAccessLearning", Boolean.valueOf(isAccessLearning));
        model.addAttribute("totalCommonDataCount", Integer.valueOf(totalCommonDataCount));
        model.addAttribute("commonDataList", commonDataList);
        model.addAttribute("typesDataList", typesDataList);
        model.addAttribute("listCategories", listCategories);
        //model.addAttribute("dataList", dataList);
        model.addAttribute("currentCategory", currentCategory);
        model.addAttribute("books", books);
        model.addAttribute("curriculum", curriculum);
        model.addAttribute("selectCategory", selectCategory);
        model.addAttribute("sub2ListCategories", sub2ListCategories);
        model.addAttribute("paging", pagination.print());
        model.addAttribute("cdes", cdes);
        model.addAttribute("isAccessLearning_fgclass", isAccessLearning_fgclass);
        
        if(unitDataList.size() == 0)
        	throw new CommonException("단원 미등록 과목입니다." , CommonException.HISTORY_BACK);
        else
            return "front/thub/learning/types";
    }

	@RequestMapping("/special.do")
	public String special(HttpServletRequest request , Model model) throws Exception
	{
		// get session 
		HttpSession session = (HttpSession) request.getSession();
		Member member = (Member) session.getAttribute("member");
		
		// get parameters
		String category = Common.getParameter(request, "category", "");
		int bookId = Common.getParameter(request, "bookId" , 0);
		String special_cd = Common.getParameter(request, "specialCd", "");
		String findCategory = Common.getParameter(request, "findCategory", "");
		int pg = Common.getParameter(request, "pg", 1);
		int PAGE_SIZE = 10;
		int BLOCK_SIZE = 10;
		int skip = (pg - 1) * PAGE_SIZE;
		
		// validation check
		if( bookId == 0 )
			throw new CommonException("정상적인 접근이 아닙니다." , CommonException.HISTORY_BACK);
		
		// make query parameter
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("category" , category);
		params.put("bookId" , bookId);
		params.put("depth" , "2");
		params.put("special_cd" , special_cd);
		params.put("skip" , skip);
		params.put("pageSize" , PAGE_SIZE);
		
		//관리자권한 추가를 위해 변경 2018.02.13
		String authCategory = category;
		if(category.length()>7) authCategory = category.substring(0, 7);
		// access check
		boolean isAccessLearning = learningService.isAccessLearning(member, authCategory);
		
		// get default data
		Book book = (Book) learningService.getBook(bookId);
		List<Category> listCategories = (List<Category>) categoryService.getChildren(special_cd, 3);
			
		if( "".equals(findCategory) && listCategories.size() > 0 )
			findCategory = ((Category) listCategories.get(0)).getCategory();
		
		params.put("findCategory" , findCategory);
		params.put("order" , "sequence");
		
		
		
		//TODO : 작업 시작
		List<Cd> cdes = new ArrayList<Cd>();
		cdes = learningService.getCdList(bookId);
		
		List<ResultMap> specialDataCategoryList = (List<ResultMap>) learningService.getSpecialDataCategoryList(special_cd);
		List<Book> books = (List<Book>) learningService.getBookListByCategory(book.getCategory());
		List<Data> specialDataList = (List<Data>) learningService.getSDataListAdmin(params);
		int totalDataCount = (Integer) learningService.getTotalSDataAdmin(params); 
		System.out.print(totalDataCount);		
		FrontPagination2 pagination = new FrontPagination2(pg, PAGE_SIZE, BLOCK_SIZE, totalDataCount);
		pagination.initialize();
		

		
		// assign
		model.addAttribute("beforePath", "교수학습자료");
		model.addAttribute("currentPath", book.getName());
		

		
		
		model.addAttribute("category", category);
		model.addAttribute("book", book);
		model.addAttribute("specialCd", special_cd);
		model.addAttribute("findCategory", findCategory);
		model.addAttribute("listCategories", listCategories);
		model.addAttribute("isAccessLearning", isAccessLearning);
		model.addAttribute("books", books);
		model.addAttribute("specialDataCategoryList", specialDataCategoryList);
		model.addAttribute("specialDataList", specialDataList);
		model.addAttribute("paging", pagination.print());
		model.addAttribute("cdes" , cdes);
		
		
/*		if( unit == null )
			throw new CommonException("단원 미등록 과목입니다." , CommonException.HISTORY_BACK);*/
		
		
		return "front/thub/learning/special";
	}
	
	/**
	 * 공통자료의 리스트
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/ajax/commonDataList.do" , method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> commonDataList(HttpServletRequest request , Model model) throws Exception
	{
		// get parameters
		int bookId = Common.getParameter(request, "bookId", 0);
		String dataType = Common.getParameter(request, "dataType", "");
		String dataFlag = Common.getParameter(request, "dataFlag", "");
		
		// validation check
		if( bookId== 0
				&& Validate.isEmpty(dataFlag)
				&& Validate.isEmpty(dataType))
			throw new CommonException("정상적인 요청이 아닙니다." , CommonException.HISTORY_BACK);
		
		// make query parameters
		// 주의! 파라미터명을 findBookId , findCategory로 맞춰야 합니다.
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("bookId" , bookId);
		params.put("dataType" , dataType);
		params.put("dataFlag" , dataFlag);
		
		// get data
		List<Data> list = (List<Data>) learningService.getFrontDataList(params);
		
		// assign
		Map<String, Object> result = new HashMap<String, Object>();		
		result.put("list", list);
		result.put("result", "SUCCESS");
		
		return result;
	}
	
	
	/**
	 * 자료등록
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/dataForm.do")
	public String dataForm(HttpServletRequest request , Model model) throws Exception
	{
		HttpSession session = (HttpSession) request.getSession();
		Member member = (Member) session.getAttribute("adminMember");
		
		if( member == null )
			throw new CommonException("로그인이 필요한 서비스입니다." , CommonException.HISTORY_BACK);
		
		// get parameters		
		String category = Common.getParameter(request, "category", "");
		int bookId = Common.getParameter(request, "bookId", 0);
		int unitId = Common.getParameter(request, "unitId", 0);
		int dataId = Common.getParameter(request, "dataId", 0);
		//String mode = Common.getParameter(request, "mode", "");
		String mode = ( dataId == 0 ) ? "add" : "modify";
		if( bookId == 0  )
			throw new CommonException("잘못된 접근입니다." , CommonException.HISTORY_BACK);
		
		// get data
		Data data = new Data();
		Multimedia multimedia = new Multimedia();


		Map<String,Object> params = new HashMap<String,Object>();
		params.put("dataId" , dataId);

		if( mode.equals("modify") )
		{
			data = (Data) learningService.getData(params);
			multimedia = (Multimedia) multimediaService.getMultimediaDetail(data.getMmId());
		}
		
		//관리자권한 추가를 위해 변경 2018.02.13
		String authCategory = category;
		if(category.length()>7) authCategory = category.substring(0, 7);
		// access check
		boolean isAccessLearning = learningService.isAccessLearning(member, authCategory);
		
	
		params.put("bookId", bookId);
		params.put("category", category);
		
		// get default data
		Book book = (Book) learningService.getBook(bookId);
		List<Unit> units = (List<Unit>) unitService.getMasterUnitList(Constants.LEARNING_CATEGORY , params);
		List<Unit> a_units = (List<Unit>) unitService.getMasterUnitListAll(Constants.LEARNING_CATEGORY , params);
		List<ResultMap> typesDataList = (List<ResultMap>) learningService.getTypesDataCategoryList(params);
		List<Cd> cdes = new ArrayList<Cd>();
		cdes = learningService.getCdList(bookId);
		
		List<Category> categories = (List<Category>) categoryService.getChildren(Constants.LEARNING_CATEGORY);
		List<Category> chasi_categories = (List<Category>) categoryService.getChildren(Constants.TYPE_CHASI_CATEGORY);
		
		List<Category> types = (List<Category>) categoryService.getChildren(Constants.TYPE_CATEGORY);
		List<Book> books = (List<Book>) learningService.getBookListByCategory(data.getCategory());
		
		//차시자료
		List<Data> cview = (List<Data>) learningService.getCDataView(params);
		
		//차시첨부자료
		List<Data> cfile = (List<Data>) learningService.getCDataFile(params);

		
		// assign

		model.addAttribute("beforePath", "교수학습자료");
		model.addAttribute("currentPath", "자료등록");
		
		

		model.addAttribute("units", units);
		model.addAttribute("a_units", a_units);	
		model.addAttribute("unitId", unitId);
		
		//union data
		model.addAttribute("dataId", dataId);
		model.addAttribute("mode", mode);
		model.addAttribute("category", category);
		model.addAttribute("bookId", bookId);
		model.addAttribute("isAccessLearning", isAccessLearning);
		model.addAttribute("books", books);
		model.addAttribute("data", data);
		model.addAttribute("multimedia", multimedia);
		model.addAttribute("categories", categories);
		model.addAttribute("chasi_categories", chasi_categories);
		model.addAttribute("types", types);
		model.addAttribute("book", book);		
		model.addAttribute("typesDataList", typesDataList);
		model.addAttribute("cdes" , cdes);
		model.addAttribute("cview", cview);
		model.addAttribute("cfile", cfile);
		
		return "front/thub/learning/dataForm";
	}
	
	/**
	 * 자료등록
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	//thub
	//thub
	@RequestMapping(value="dataFormAction.do" , method=RequestMethod.POST)
	public String dataFormAction(@RequestParam(value="upfile" , required=false) MultipartFile upfile , Model model, MultipartHttpServletRequest request) throws Exception{
		HttpSession session = (HttpSession) request.getSession();
		Member member = (Member) session.getAttribute("member");
		
		if(member == null){
			throw new CommonException("로그인 후 이용가능합니다." , CommonException.HISTORY_BACK);
		}
		
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

		if( Validate.isEmpty(category) || bookId == 0 || Validate.isEmpty(dataFlag) || Validate.isEmpty(dataType)|| Validate.isEmpty(title)){
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
		
		if(!(category.substring(1, 4).equals("D003"))&&!(dataType.equals("D006")) && !upfile.isEmpty()){
			FileInfo fileInfo = fileService.upload(upfile, serverFilePath, "learning");
			params.put("fileName" , fileInfo.getOriginName());
			params.put("dataFile" , fileInfo.getAbsolutePath());
		}

		String redirect = "";
		
		if( mode.equals("add")){
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
			
			redirect = String.format("redirect:detail.do?category=%s&bookId=%d" , category , bookId);
		}
		else if( mode.equals("modify")){
			params.put("dataId" , dataId);
			Data data = learningService.getData(params);
			if(!Validate.isEmpty((String)params.get("dataFile")) || mmId > 0){
				if(!Validate.isEmpty(data.getDataFile())&& mmId==0 ){
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
							if(!Validate.isEmpty(cdata_file.get(0).getDataFile())){
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
			redirect = String.format("redirect:detail.do?category=%s&bookId=%d", category, bookId);
		}
		return redirect;
	}
	
	/**
	 * 자료등록시 학교급 권한 체크
	 * @param request
	 * @param model
	 * @return result
	 * @throws Exception
	 */
	@RequestMapping(value="/learningAuthCheck.do" , method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> findUserInfo(HttpServletRequest request , Model model) throws Exception
	{
		HttpSession session = (HttpSession) request.getSession();
		Member adminMember = (Member) session.getAttribute("member");
		
		String category = Common.getParameter(request, "category", "");
		
		if( category.equals("") )
			throw new CommonException("정상적인 접근이 아닙니다." , CommonException.HISTORY_BACK);

		String authCategory = category;
		if(category.length()>7) authCategory = category.substring(0, 7);
		// access check
		
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", adminMember.getUserId());
		params.put("category", authCategory);
		
		
		int dupCnt = memberService.getLearningAuth(params);
		
		if( adminMember.getAuthType().equals("S") )
			dupCnt = 1;
		
		Map<String,Object> result = new HashMap<String,Object>();
		
		if ( !adminMember.getAuthType().equals("S") && dupCnt == 0 ){
			result.put("result", "FAILURE");
			result.put("msg" , "권한이 부족합니다. 관리자에게 문의해주세요.");
		}
		else{
			result.put("result" , "SUCCESS");
		}
		
		return result;
	}

	/**
	 * 서브 카테고리 선택시 Ajax
	 * @param request
	 * @param model
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value="/subList.do" , method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> subList(HttpServletRequest request , Model model) throws Exception
	{
		//request setting
		String dataType = Common.getParameter(request, "dataType" ,"");
		String category = Common.getParameter(request, "category", "");
		int unitId = Common.getParameter(request, "unitId", 0);
		int bookId = Common.getParameter(request, "bookId", 0);
		
		System.out.println("dataType : "+dataType);
		System.out.println("unitId : "+unitId);
		System.out.println("category : "+category);
		System.out.println("bookId : "+bookId);
		//params setting
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("dataType", dataType);
		params.put("unitId", unitId);
		params.put("category", category);
		params.put("bookId", bookId);

		//get data
		List<Data> unitDataList = (List<Data>)learningService.getUnitSubDataList(params);		
		Map<String, Object> result = new HashMap<String,Object>();
		
		//result setting
		if( unitDataList.size() > 0 ){
			result.put("result", "SUCCESS");
			result.put("unitDataList", unitDataList);
		}
		else{
			result.put("result", "FAILER");
			result.put("msg", "등록된 자료가 없습니다.");
		}
		
		return result;
	}
	
	
	/**
	 * 단원별 데이터 정렬 기준 수정 Ajax
	 * @param model
	 * @throws 
	 */
	@RequestMapping(value="/ajax/updateSeq.do" , method=RequestMethod.POST)
	public @ResponseBody String updateSeq(HttpServletRequest request , Model model) throws Exception
	{
		String result = "FAILER";
		//request setting
		String dataIds = Common.getParameter(request, "dataIds", "");
		String sequences = Common.getParameter(request, "sequences", "");
		
		String[] dataId = dataIds.split(",");
		String[] sequence = sequences.split(",");
		
		Map<String,Object> params = new HashMap<String,Object>();
		for(int i=0; i < dataId.length; i++){
			params.put("dataId", dataId[i]);
			params.put("sequence", sequence[i]);
			learningService.updateUnitDataSeq(params);
		}
		
		result = "SUCCESS";

		return result;
	}
	
	/**
	 * 단원별 데이터 정렬 기준 수정 Ajax
	 * @param model
	 * @throws 
	 */
	@RequestMapping(value="/ajax/updateSDataSeq.do" , method=RequestMethod.POST)
	public @ResponseBody String updateSDataSeq(HttpServletRequest request , Model model) throws Exception
	{
		
		HttpSession session = (HttpSession) request.getSession();
		Member member = (Member) session.getAttribute("member");		
		
		String result = "FAILER";
		//request setting
		String dataIds = Common.getParameter(request, "dataIds", "");
		String sequences = Common.getParameter(request, "sequences", "");
		
		String[] dataId = dataIds.split(",");
		String[] sequence = sequences.split(",");

		
		Map<String,Object> params = new HashMap<String,Object>();
		for(int i=0; i < dataId.length; i++){
			params.put("dataId", dataId[i]);
			params.put("sequence", sequence[i]);
			params.put("userId" , member.getUserId());
			
			learningService.updateSDataSeq(params);
		}
		
		result = "SUCCESS";

		return result;
	}

	/**
	 * 차시자료의 이전/이후 주소
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/ajax/prevChasiData.do" , method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> prevChasiData(HttpServletRequest request , Model model) throws Exception
	{
		// get parameters
		int dataId = Common.getParameter(request, "dataId", 0);
		int chasiSeq = Common.getParameter(request, "chasiSeq", 0);
		String swt = Common.getParameter(request, "swt", "");
		
		// validation check
		if( dataId== 0 || chasiSeq == 0)
			throw new CommonException("정상적인 요청이 아닙니다." , CommonException.HISTORY_BACK);
		
		// make query parameters
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("dataId" , dataId);
		params.put("sequence" , chasiSeq);
		params.put("swt" , swt);
		
		System.out.print("dataId="+dataId+", sequence="+chasiSeq+", swt="+swt);
		
		// get data
		String url = learningService.getPrevUrl(params);
		
		// assign
		Map<String, Object> result = new HashMap<String, Object>();		
		result.put("url", url);
		result.put("result", "SUCCESS");
		
		return result;
	}
	/**
	 * 차시자료의 다운로드버튼 세팅
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/ajax/getDownBtns.do" , method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> getDownBtns(HttpServletRequest request , Model model) throws Exception
	{
		// get parameters
		int bookId = Common.getParameter(request, "bookId", 0);
		
		// validation check
		if( bookId== 0 )
			throw new CommonException("정상적인 요청이 아닙니다." , CommonException.HISTORY_BACK);
		
		// make query parameters
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("bookId" , bookId);
		
		// get data
		List<Data> downBtns = (List<Data>) learningService.getChasiDownBtn(params);
		
		// assign
		Map<String, Object> result = new HashMap<String, Object>();		
		result.put("downBtns", downBtns);
		result.put("result", "SUCCESS");
		
		return result;
	}	
	
	/**
	 * 특화자료의 리스트
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/ajax/specialDataList.do" , method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> specialDataList(HttpServletRequest request , Model model) throws Exception
	{
		// get parameters
		String findCategory = Common.getParameter(request, "category", "");
		//String findCategory = Common.getParameter(request, "findCategory", "");
		int pg = Common.getParameter(request, "pg", 1);
		int PAGE_SIZE = 10;
		int BLOCK_SIZE = 10;
		int skip = (pg - 1) * PAGE_SIZE;
		// validation check
		if( Validate.isEmpty(findCategory) )
			throw new CommonException("정상적인 요청이 아닙니다." , CommonException.HISTORY_BACK);
		
		// make query parameters
		// 주의! 파라미터명을 findBookId , findCategory로 맞춰야 합니다.
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("findCategory" , findCategory);
		params.put("skip" , skip);
		params.put("pageSize" , PAGE_SIZE);
		params.put("order", "sequence");
		
		// get data
		List<Data> list = (List<Data>) learningService.getSDataListAdmin(params);
		int totalDataCount = (Integer) learningService.getTotalSDataAdmin(params); 
		System.out.print(totalDataCount);		
		FrontPagination2 pagination = new FrontPagination2(pg, PAGE_SIZE, BLOCK_SIZE, totalDataCount);
		pagination.initialize();
		
		// assign
		Map<String, Object> result = new HashMap<String, Object>();		
		result.put("list", list);
		result.put("result", "SUCCESS");
		result.put("paging", pagination.print());
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
	
	@RequestMapping(value="/deleteDataArticle.do" , method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> deleteDataArticle(HttpServletRequest request , Model model) throws Exception{
		
		Map<String,Object> result = new HashMap<String,Object>();
		Map<String,Object> params = new HashMap<String,Object>();
		int dataId = Common.getParameter(request, "dataId", 0);
		
		if( dataId == 0 ){
			result.put("result" , "FAILURE");
			result.put("msg" , "파라미터 오류");
			return result;
		}
		
		params.put("dataId" , dataId);
		
		learningService.deleteData(params);
		
		result.put("result" , "SUCCESS");
		
		return result;
	}	
}
