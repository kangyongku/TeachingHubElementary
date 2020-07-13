package kr.co.kumsung.thub.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import kr.co.kumsung.thub.domain.Book;
import kr.co.kumsung.thub.domain.Category;
import kr.co.kumsung.thub.domain.Data;
import kr.co.kumsung.thub.domain.MainData;
import kr.co.kumsung.thub.domain.FileInfo;
import kr.co.kumsung.thub.domain.Member;
import kr.co.kumsung.thub.domain.Multimedia;
import kr.co.kumsung.thub.domain.Unit;
import kr.co.kumsung.thub.exception.CommonException;
import kr.co.kumsung.thub.service.CategoryService;
import kr.co.kumsung.thub.service.FileService;
import kr.co.kumsung.thub.service.LearningService;
import kr.co.kumsung.thub.service.MultimediaService;
import kr.co.kumsung.thub.service.UnitService;
import kr.co.kumsung.thub.service.MainDisplayService;
import kr.co.kumsung.thub.setting.Constants;
import kr.co.kumsung.thub.util.Common;
import kr.co.kumsung.thub.util.Pagination;
import kr.co.kumsung.thub.util.ResultMap;
import kr.co.kumsung.thub.util.Validate;

import org.apache.log4j.Logger;
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

@Component("adminMainController")
@Controller
@RequestMapping("/admin/main")
public class MainController {
	
	private static final Logger logger = Logger.getLogger(MainController.class);
	private static final int PAGE_SIZE = 10;
	private static final int BLOCK_SIZE = 10;
	
	@Autowired
	private LearningService learningService;
	
	@Autowired
	private MultimediaService multimediaService;
	
	@Autowired
	private UnitService unitService;
	
	@Autowired
	private CategoryService categoryService;

	@Autowired
	private FileService fileService;
	
	@Autowired
	private	MainDisplayService maindisplayService;
	
	final String categoryCode = Constants.LEARNING_CATEGORY;
	
	@Value("#{common['file.path']}") String serverFilePath;		// 첨부파일의 root 경로
	
	@RequestMapping("/dashboard.do")
	public String dashboard() throws Exception
	{
		return "admin/main/dashboard";
	}
	
	@RequestMapping(value="/editor.do" , method=RequestMethod.GET)
	public String editor(Model model) throws Exception
	{	
		return "admin/main/editor";
	}
	
	/**
	 * 메인 리스트
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/mainList.do")
	public String mainList(HttpServletRequest request , Model model, HttpSession session) throws Exception
	{
		Member member = (Member) session.getAttribute("adminMember");

		// get parameters
		int pg = Common.getParameter(request, "pg", 1);
		int skip = (pg - 1) * PAGE_SIZE;
		
		String findStr2 = Common.getParameter(request, "findStr", "");
		
		String findStr = findStr2.replaceAll(" ", "");
		String findMethod = Common.getParameter(request, "findMethod", "");
		String findCategory = Common.getParameter(request, "findCategory", "");
		String findStartDate = Common.getParameter(request, "findStartDate", "");
		String findEndDate = Common.getParameter(request, "findEndDate", "");
		
		
		//set parameter
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("skip", skip);
		params.put("pageSize", PAGE_SIZE);
		
		params.put("findStr", findStr);
		params.put("findMethod", findMethod);
		params.put("findCategory", findCategory);
		params.put("findStartDate", findStartDate);
		params.put("findEndDate", findEndDate);
		
		int totalList = learningService.getTotalMainData(params);
		int articleNum = totalList - skip;
		
		//set Paging
		Pagination pagination = new Pagination(pg, PAGE_SIZE, BLOCK_SIZE, totalList);
		pagination.setDelimiter("");
		pagination.setPrevLink("Prev");
		pagination.setNextLink("Next");
		
		List<Category> categories = categoryService.getChildren(categoryCode);

		List<MainData> list = (List<MainData>) learningService.getMainDataList(params);
		
		model.addAttribute("skip", skip);
		model.addAttribute("pg", pg);
		model.addAttribute("articleNum", articleNum);
		model.addAttribute("list", list);
		model.addAttribute("paging", pagination.print());

		model.addAttribute("categories", categories);
		
		model.addAttribute("findStr", findStr);
		model.addAttribute("findMethod", findMethod);
		model.addAttribute("findCategory", findCategory);
		model.addAttribute("findStartDate", findStartDate);
		model.addAttribute("findEndDate", findEndDate);

		model.addAttribute("pathTitle", "메인 관리");
		model.addAttribute("path", "메인 관리/ 리스트");
		
		return "admin/main/mainList";
	}
	/**
	 * 메인화면 교과서등록
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("maindataForm.do")
	public String maindataForm(HttpServletRequest request , Model model) throws Exception
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
		MainData data = new MainData();

		
		// make query parameters
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("dataId" , dataId);
		
		if( mode.equals("modify") )
		{
			data = (MainData) learningService.getMainData(params);
		}
		List<Category> categories = (List<Category>) categoryService.getChildren(Constants.LEARNING_CATEGORY);
		List<Category> types = (List<Category>) categoryService.getChildren(Constants.TYPE_CATEGORY);
		List<Book> books = (List<Book>) learningService.getBookListByCategory(data.getCategory());
		// assign
		model.addAttribute("pathTitle", "메인화면 관리");
		model.addAttribute("path", "메인화면 관리 > 교과서등록");
		
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
		model.addAttribute("categories", categories);
		model.addAttribute("types", types);
		model.addAttribute("books", books);
		
		return "admin/main/maindataForm";
	}
	
	@RequestMapping(value="maindataFormAction.do" , method=RequestMethod.POST)
	public String maindataFormAction(HttpServletRequest request , Model model) throws Exception
	{
		// get session
		HttpSession session = (HttpSession) request.getSession();
		Member member = (Member) session.getAttribute("adminMember");
		
		// get parameters
		int dataId = Common.getParameter(request, "dataId", 0);
		int pg = Common.getParameter(request, "pg", 1);
		String mode = Common.getParameter(request, "mode", "add");
		String category = Common.getParameter(request, "category", "");
		int bookId = Common.getParameter(request, "bookId", 0);
		int unitId = Common.getParameter(request, "unitId", 0);
		String dataType = Common.getParameter(request, "dataType", "");
		
		if( dataId > 0 && mode.equals("add") ) mode = "modify";
		
		// check validation
		if( Validate.isEmpty(category)
				|| bookId == 0
				|| Validate.isEmpty(dataType))
		{
			throw new CommonException("파라미터 에러." , CommonException.HISTORY_BACK);
		}
		
		// make query parameters
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("category" , category);
		params.put("bookId" , bookId);
		params.put("unitId" , unitId);
		params.put("dataType" , dataType);
		params.put("userId" , member.getUserId());

		String redirect = "";
		
		if( mode.equals("add") )
		{
			dataId = learningService.insertMainData(params);
			
			redirect = String.format("redirect:/admin/main/mainList.do?pg=%d" ,
					pg);
		}
		else if( mode.equals("modify") )
		{
			params.put("dataId" , dataId);
			
			
			learningService.updateMainData(params);
			
			redirect = String.format("redirect:/admin/main/maindataForm.do?pg=%d&dataId=%d&gubun=update" ,
					pg ,
					dataId
				);
		}
		
		return redirect;
	}
	
	/**
	 * 데이타 삭제
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteMainData.do" , method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> deleteMainData(HttpServletRequest request , Model model) throws Exception
	{
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
		
		learningService.deleteMainData(params);
		
		// assign
		result.put("result" , "SUCCESS");
		
		return result;
	}
	/**
	 * 이달의 추천자료 및 배너
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/banner.do")
	public String monthlyRecomm(HttpServletRequest request , Model model, HttpSession session) throws Exception
	{
		Member member = (Member) session.getAttribute("adminMember");
		int dispId = Common.getParameter(request, "dispId", 0);
		String mode = ( dispId == 0 ) ? "add" : "modify";
		String type = Common.getParameter(request, "type", "bestdata");

	//set parameter
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("dispCd" , type);

		 MainData maindata = (MainData)maindisplayService.getDispConf(type);
		 params.put("dispSort" , maindata.getdispSort());
		List<MainData> list = (List<MainData>) maindisplayService.getDispList(params);

		model.addAttribute("list", list);
		model.addAttribute("dispId", dispId);
		model.addAttribute("mode", mode);
		model.addAttribute("type", type);
		
		model.addAttribute("maindata", maindata);
		model.addAttribute("list", list);

		model.addAttribute("pathTitle", maindata.getdispTitle());
		model.addAttribute("path", maindata.getdispPath());
		
		return "admin/main/banner";
	}

	@RequestMapping(value="bannerAction.do" , method=RequestMethod.POST)
	public String bannerAction(@RequestParam(value="upfile" , required=false) MultipartFile upfile , MultipartHttpServletRequest request , Model model) throws Exception {

		HttpSession session = (HttpSession) request.getSession();
		Member member = (Member) session.getAttribute("adminMember");
	
		//int dispId = Common.getParameter(request, "dispId", 0);
		//String mode = Common.getParameter(request, "mode", "add");
		String type = Common.getParameter(request, "type", "bestdata");

		String dispSubj = Common.getParameter(request, "dispSubj", "");
		String dispLink = Common.getParameter(request, "dispLink", "");
		String dispTarget = Common.getParameter(request, "dispTarget", "_self");
		
		//메인배너 표현순위지정
		String dispRanking = Common.getParameter(request, "dispRanking", "");
		
		System.out.println("111111111" + dispRanking);
		Map<String,Object> params = new HashMap<String,Object>();
		

		//정렬때문에 증가값 체크 및 추가
		MainData mdata = maindisplayService.initChkMaxDisplay(type);
		int sortPlus = mdata.getdispSelect() + 1;
		
		// make query parameters
		
		params.put("dispCd"        , type);
		params.put("dispSubj"           , dispSubj);
		params.put("dispLink"        , dispLink);
		params.put("dispTarget"        , dispTarget);
		params.put("userId"          , member.getUserId());	
		params.put("dispSelect"	, sortPlus);
		params.put("dispRanking"        , dispRanking);
		
		if( Validate.isEmpty(dispLink)){
			throw new CommonException("링크된 주소가 없습니다." , CommonException.HISTORY_BACK);
		}
		
		if( !upfile.isEmpty() ){
			
			FileInfo fileInfo = fileService.upload(upfile, serverFilePath, "maindisp");
			params.put("imgurl" , fileInfo.getAbsolutePath());
		}else {
			throw new CommonException("업로드된 이미지가 없습니다." , CommonException.HISTORY_BACK);
		}
		

		maindisplayService.insertDisplay(params);
		String redirect = String.format("redirect:/admin/main/banner.do?type=%s" , type);

		return redirect;
	}


	@RequestMapping(value="/deleteBanner.do" , method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> deleteBanner(HttpServletRequest request , Model model) throws Exception
	{
		int dispId = Common.getParameter(request, "dispId", 0);
		//String mode = Common.getParameter(request, "mode", "del");
		//String type = Common.getParameter(request, "type", "bestdata");
		Map<String,Object> result = new HashMap<String,Object>();
		// check validation
		if( dispId == 0 )
		{
			result.put("result" , "FAILURE");
			result.put("msg" , "파라미터 오류");
			return result;
		}

		MainData maindata = maindisplayService.getDispDetail(dispId);
		String imgurl = maindata.getImgurl();
		System.out.print(imgurl);
		
		if(!Validate.isEmpty(imgurl)) {
			System.out.print(imgurl);
			String deleteFilePath = String.format("%s%s" , serverFilePath , imgurl.replace("/upfiles" , ""));
			fileService.delete(deleteFilePath);
		}

		
		maindisplayService.deleteDisplay(dispId);
		
		// assign
		result.put("result" , "SUCCESS");
		
		return result;
	}
	
	@RequestMapping(value="/updateBanner.do" , method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> updateBanner(HttpServletRequest request , Model model) throws Exception
	{
		HttpSession session = (HttpSession) request.getSession();
		Member member = (Member) session.getAttribute("adminMember");
		
		String type = Common.getParameter(request, "type", "bestdata");
		int dispId = Common.getParameter(request, "dispId", 0);
		String dispSubj = Common.getParameter(request, "dispSubj", "");
		String dispTarget = Common.getParameter(request, "dispTarget", "");
		String dispLink =  Common.getParameter(request, "dispLink", "");
		String dispRanking =  Common.getParameter(request, "dispRanking", "");
		
		Map<String,Object> params = new HashMap<String,Object>();

		Map<String,Object> result = new HashMap<String,Object>();
		
		// make query parameters
		
		params.put("dispId"        , dispId);
		params.put("dispSubj"           , dispSubj);
		params.put("dispTarget"        , dispTarget);
		params.put("dispLink"		, dispLink);
		params.put("dispRanking"	, dispRanking);
		params.put("userId"          , member.getUserId());		
		
	
		maindisplayService.updateDisplay(params);
		
		// assign
		result.put("result" , "SUCCESS");
		
		return result;
	
	}
	
	@RequestMapping(value="/updateBannerRanking.do" , method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> updateBannerRanking(HttpServletRequest request , Model model) throws Exception
	{
		HttpSession session = (HttpSession) request.getSession();
		Member member = (Member) session.getAttribute("adminMember");
		
		String type = Common.getParameter(request, "type", "bestdata");
		int dispId = Common.getParameter(request, "dispId", 0);
		String dispRanking =  Common.getParameter(request, "dispRanking", "");
		
		Map<String,Object> params = new HashMap<String,Object>();

		Map<String,Object> result = new HashMap<String,Object>();
		
		// make query parameters
		
		params.put("dispId"        , dispId);
		params.put("dispRanking"	, dispRanking);
		params.put("userId"          , member.getUserId());		
		
	
		maindisplayService.updateDisplayRanking(params);
		
		// assign
		result.put("result" , "SUCCESS");
		
		return result;
	
	}
	
	@RequestMapping(value="/selectBanner.do" , method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> selectBanner(HttpServletRequest request , Model model) throws Exception
	{
		int dispId = Common.getParameter(request, "dispId", 0);
		String gb = Common.getParameter(request, "gb", "");
		String type = Common.getParameter(request, "type", "");
		
		System.out.println("gb============>" + gb);
		
		Map<String,Object> result = new HashMap<String,Object>();
		Map<String,Object> params = new HashMap<String,Object>();
		// check validation
		if( dispId == 0 || gb.equals(""))
		{
			result.put("result" , "FAILURE");
			result.put("msg" , "파라미터 오류");
			return result;
		}
		
		MainData idata = maindisplayService.initChkDisplay(dispId);
		MainData mdata = maindisplayService.initChkMaxDisplay(type);
		
		//MainData mdata = maindisplayService.initChkMaxDisplay(params);
		//System.out.println("=======================" + mdata);
		
		System.out.println("=======================>1" +mdata.getdispId());
		System.out.println("=======================>2" +dispId);
		
		if(gb.equals("U")) 
		{
			if( idata.getdispSelect() == 1)
			{
				result.put("result" , "FAILURE");
				result.put("msg" , "처음입니다.");
				return result;
			}
		} else {
			if( mdata.getdispId() == dispId)
			{
			result.put("result" , "FAILURE");
			result.put("msg" , "마지막입니다.");
			return result;
		
			}
		}
		
		System.out.println("경고체크" + mdata.getdispId() + "--" + dispId);
		if(gb.equals("U")) 
		{

			// 자신의 데이터 초기화
			maindisplayService.initDisplay(dispId);
			// 이동하려는 데이터 확인
			params.put("dispId" , idata.getdispSelect());
			params.put("dispCd", type);
			
			MainData sdata = maindisplayService.initStepUpDisplay(params);
			//1차 UP일경우 - 예)초기화 0 -> 이전 번호 2 변경   
			//System.out.println("====================" + sdata.getdispSelect());
			int dispId2 = sdata.getdispId(); 
			int dispsel = sdata.getdispSelect();
			
			System.out.println("====================1" + dispId2);
			System.out.println("====================2" + dispsel);
			
			params.put("dispId" , dispId);
			params.put("dispSelect", dispsel);
			maindisplayService.initStepUpdate(params);
	
			//2차 변경 - 예) 2 > 1로
			params.put("dispSelect", idata.getdispSelect());
			params.put("dispId",  sdata.getdispId());
			System.out.println("====================3" +idata.getdispSelect());
			System.out.println("====================4" +sdata.getdispId());
			maindisplayService.initStepUpdate(params);
		
		} else {
		
			//이전 데이터 체크
			params.put("dispId", idata.getdispSelect());
			params.put("dispCd", type);
			
			MainData tdata = maindisplayService.initStepDwDisplay(params);
			int tdispId2 = tdata.getdispId(); 
			int tdispsel = tdata.getdispSelect();
			
			// 자신의 데이터 초기화
			maindisplayService.initDisplay(dispId);
			
			// 앞뒤 스위치
			params.put("dispId",  tdata.getdispId());
			params.put("dispSelect", idata.getdispSelect());
			maindisplayService.initStep3Update(params);
			
			// 자신의 데이터 이전 데이터로 select 변경
			
			params.put("dispId",  dispId);
			params.put("dispSelect",  tdispsel);
			maindisplayService.initStep3Update(params);
			
			
			// 이동하려는 데이터 확인
			System.out.println("====================5" +dispId);
			System.out.println("====================6" +tdispsel);
			
			
		}
	
		// assign
		result.put("result" , "SUCCESS");
				
		return result;
	}
	
}
