package kr.co.kumsung.thub.controller.admin;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import kr.co.kumsung.thub.domain.Category;
import kr.co.kumsung.thub.domain.FileInfo;
import kr.co.kumsung.thub.domain.Member;
import kr.co.kumsung.thub.domain.Multimedia;
import kr.co.kumsung.thub.exception.CommonException;
import kr.co.kumsung.thub.service.CategoryService;
import kr.co.kumsung.thub.service.FileService;
import kr.co.kumsung.thub.service.MultimediaService;
import kr.co.kumsung.thub.setting.Constants;
import kr.co.kumsung.thub.util.Common;
import kr.co.kumsung.thub.util.Pagination;

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
 * 멀티미디어 관리
 * @author jhg
 * @date 0724~0730
 */
@Component("adminMultimediaController")
@Controller
@RequestMapping("/admin/multimedia")
public class MultimediaController {
	
	private static final Logger logger = LoggerFactory.getLogger(MultimediaController.class);
	private static final int PAGE_SIZE = 10;
	private static final int BLOCK_SIZE = 10;
	
	@Autowired
	private MultimediaService multimediaService;
	
	@Autowired
	private CategoryService categoryService;

	@Autowired
	private FileService fileService;
	
	final String categoryCode = Constants.MULTIMEDIA_CATEGORY;
	
	@Value("#{common['file.path']}") String serverFilePath;
	
	
	
	
	/**
	 * 멀티미디어 리스트
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/multimediaList.do")
	public String multimediaList(HttpServletRequest request , Model model, HttpSession session) throws Exception
	{
		
		
		Member member = (Member) session.getAttribute("adminMember");
		System.out.println("getautype : "+member.getAccessAuth());
		System.out.println("request : "+request.getParameter("active"));
		// get parameters
		int pg = Common.getParameter(request, "pg", 1);
		logger.info("multimediaList::"+pg);
		int skip = (pg - 1) * PAGE_SIZE;
		
//		String findStr = Common.getParameter(request, "findStr", "");
		String findStr2 = Common.getParameter(request, "findStr", "");
		
		String findStr = findStr2.replaceAll(" ", "");
		String findMethod = Common.getParameter(request, "findMethod", "");
		String findCategory = Common.getParameter(request, "findCategory", "");
		String findStartDate = Common.getParameter(request, "findStartDate", "");
		String findEndDate = Common.getParameter(request, "findEndDate", "");
		String findIsUse = Common.getParameter(request, "findIsUse", "");
		
		
		//set parameter
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("skip", skip);
		params.put("pageSize", PAGE_SIZE);
		
		params.put("findStr", findStr);
		params.put("findMethod", findMethod);
		params.put("findCategory", findCategory);
		params.put("findStartDate", findStartDate);
		params.put("findEndDate", findEndDate);
		params.put("findIsUse", findIsUse);
		
		int totalList = multimediaService.getTotalMultimedia(params);
		int multimediaNum = totalList - skip;
		
		//set Paging
		Pagination pagination = new Pagination(pg, PAGE_SIZE, BLOCK_SIZE, totalList);
		pagination.setDelimiter("");
		pagination.setPrevLink("Prev");
		pagination.setNextLink("Next");
		
		List<Category> categories = categoryService.getChildren(categoryCode);

		List<Multimedia> multimediaList = multimediaService.getMultimediaList(params);
		
		model.addAttribute("skip", skip);
		model.addAttribute("pg", pg);
		model.addAttribute("multimediaNum", multimediaNum);
		model.addAttribute("multimediaList", multimediaList);
		model.addAttribute("paging", pagination.print());

		model.addAttribute("categories", categories);
		
		model.addAttribute("findStr", findStr);
		model.addAttribute("findMethod", findMethod);
		model.addAttribute("findCategory", findCategory);
		model.addAttribute("findStartDate", findStartDate);
		model.addAttribute("findEndDate", findEndDate);
		model.addAttribute("findIsUse", findIsUse);
		
		model.addAttribute("pathTitle", "멀티미디어 관리");
		model.addAttribute("path", "멀티미디어 관리/ 리스트");
		
		return "admin/multimedia/multimediaList";
	}
	
	/**
	 * 멀티미디어 입력폼
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 * */
	@RequestMapping("/multimediaForm")
	public String multimediaForm(HttpServletRequest request, Model model) throws Exception
	{
		String mode = Common.getParameter(request, "mode", "add");
		int mmId = Common.getParameter(request, "mmId", 0);
		
		int pg = Common.getParameter(request, "pg", 1);
//		String findStr = Common.getParameter(request, "findStr", "");
		String findStr2 = Common.getParameter(request, "findStr", "");
		
		String findStr = findStr2.replaceAll(" ", "");
		String findMethod = Common.getParameter(request, "findMethod", "");
		String findCategory = Common.getParameter(request, "findCategory", "");
		String findStartDate = Common.getParameter(request, "findStartDate", "");
		String findEndDate = Common.getParameter(request, "findEndDate", "");
		String findIsUse = Common.getParameter(request, "findIsUse", "");
		
		model.addAttribute("pathTitle", "멀티미디어 관리");
		model.addAttribute("path", "멀티미디어 관리/ 입력 수정");
		
		Multimedia multimediaMap = null;
		
		if(mode.equals("add")){
			multimediaMap = new Multimedia();
		}else if(mode.equals("modify")){
			multimediaMap = multimediaService.getMultimediaDetail(mmId);
		}else if(mode.equals("delete")){
			
		}
		
		// 분류 관련 카테고리 리스트
		List<Category> categories = categoryService.getChildren(categoryCode);
		List<Category> originCodes = categoryService.getChildren("C002");
		
		model.addAttribute("mode", mode);
		model.addAttribute("mmId", mmId);
		model.addAttribute("multimediaMap", multimediaMap);
		model.addAttribute("categories", categories);
		model.addAttribute("originCodes", originCodes);
		
		model.addAttribute("pg", pg);
		model.addAttribute("findStr", findStr);
		model.addAttribute("findMethod", findMethod);
		model.addAttribute("findCategory", findCategory);
		model.addAttribute("findStartDate", findStartDate);
		model.addAttribute("findEndDate", findEndDate);
		model.addAttribute("findIsUse", findIsUse);
		
		return "admin/multimedia/multimediaForm";
	}
	
	/**
	 * 멀티미디어 등록 
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 * */
	@RequestMapping(value="multimediaAction.do" , method=RequestMethod.POST)
	public String multimediaAction(HttpServletRequest request, Model model,  
			@RequestParam("fileName")MultipartFile attachFile,
			@RequestParam("thumbnail")MultipartFile thumbnailFile
				) throws Exception
	{
		
		String returnUrl = "";
		//Request Data Setting
		String mode = Common.getParameter(request, "mode", "add");
		String category  = Common.getParameter(request, "category", "");
		int pg = Common.getParameter(request, "pg", 1);
//		String findStr = Common.getParameter(request, "findStr", "");
		String findStr2 = Common.getParameter(request, "findStr", "");
		
		String findStr = findStr2.replaceAll(" ", "");
		String findMethod = Common.getParameter(request, "findMethod", "");
		String findCategory = Common.getParameter(request, "findCategory", "");
		String findStartDate = Common.getParameter(request, "findStartDate", "");
		String findEndDate = Common.getParameter(request, "findEndDate", "");
		String findIsUse = Common.getParameter(request, "findIsUse", "");
		
		
		//System.out.println("test1="+category);
		//부모 카테고리로 분류 타입 정의
		String masterCategory = category;
		if(category.length()>4) masterCategory = category.substring(0,4);
		//System.out.println("test="+masterCategory);
		//logger.info("multimediaList masterCategory :::::::::::::::::::::::: "+masterCategory);
		int mmId = Common.getParameter(request, "mmId", 0);
		
		// 관리자 타입에 따라서 조회 권한 부여
		HttpSession session = (HttpSession) request.getSession();
		Member adminMember = (Member) session.getAttribute("adminMember");
		
		String regId = adminMember.getUserId();
		
		//Prameter Setting
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("category", category );
		params.put("title", Common.getParameter(request, "title", "") );
		params.put("keyword", Common.getParameter(request, "keyword", "") );
		params.put("originCode", Common.getParameter(request, "originCode", "") );
		params.put("isUse", Common.getParameter(request, "isUse", "") );
		params.put("contents", Common.getParameterNoXSS(request, "contents", "") );
		params.put("originCode", Common.getParameter(request, "originCode", "") );
		params.put("originTxt", Common.getParameter(request, "originTxt", "") );
		params.put("linkUrl", Common.getParameter(request, "linkUrl", "") );
		params.put("dnUrl", Common.getParameter(request, "dnUrl", "") );
		params.put("masterCategory" , masterCategory); 
		params.put("regId", regId);
		
		/*
		if( masterCategory.equals("E004") ){
			thumbnailFile = attachFile;
		}
		*/
		
		FileInfo thumbnailInfo = new FileInfo();
		FileInfo fileInfo = new FileInfo();
		
		//Multipart 넘어온 이미지가 있을경우
		if ( !thumbnailFile.isEmpty() ){
			thumbnailInfo = fileService.upload(thumbnailFile, serverFilePath, "multimedia/thumbnail");
			params.put("thumbnail" , thumbnailInfo.getAbsolutePath());
			
			String fileExt = thumbnailInfo.getOriginName().substring(thumbnailInfo.getOriginName().lastIndexOf(".") + 1 , thumbnailInfo.getOriginName().length()).toLowerCase();
			fileService.makeThumbail(serverFilePath + thumbnailInfo.getAbsolutePath().replace("/upfiles" , ""), fileExt);
		}
			
		//Multipart 넘어온 자료가 있을경우 
		if( !attachFile.isEmpty() ){
			fileInfo = fileService.upload(attachFile, serverFilePath, "multimedia/attachFile");
			params.put("fileName", fileInfo.getOriginName() );
			params.put("dataFile", fileInfo.getAbsolutePath() );
		}
		if( mode.equals("add") )
		{
			mmId = (Integer) multimediaService.insertMultimedia(params);			
			returnUrl = "redirect:multimediaList.do?pg=" + pg + "&findCategory=" + findCategory + "&findStr=" + findStr + "&findMethod=" + findMethod + "&findStartDate=" + findStartDate + "&findEndDate=" + findEndDate + "&findIsUse=" + findIsUse;
		}else if( mode.equals("modify") )
		{
			// add to query parameter 
			params.put("mmId" , mmId);
			
			// 입력 되어있는 데이터를 가져온다.
			Multimedia multimedia = multimediaService.getMultimediaDetail(mmId);
			
			// 기존의 파일을 삭제한다.
			// 절대경로를 가지고 오기 때문에 기존 파일명의 앞자리 /upfiles는 삭제한다.
			if( !thumbnailFile.isEmpty() )
			{
				String deleteFilePath = String.format("%s%s" , serverFilePath , multimedia.getThumbnail().replace("/upfiles" , ""));
				fileService.delete(deleteFilePath);
			}
			else if(masterCategory.equals("E003"))
			{	
				//듣기자료일경우 예외 처리 한다.
				params.put("thumbnail", "");
			}
			else
			{
				// 파일이 존재하지 않다면 기존의 파일명을 다시 입력한다.
				params.put("thumbnail" , multimedia.getThumbnail());
			}
			
			// 기존자료가 웹링크/동영상이 아닐경우 기존 자료 삭제 (웹링크//동영상은 주소형태)
			if( !attachFile.isEmpty() && !(masterCategory.equals("E005") || masterCategory.equals("E002")) )
			{
				if( !multimedia.getDataFile().isEmpty() && !(masterCategory.equals("E005") || masterCategory.equals("E002")) )
				{
					String deleteFilePath = String.format("%s%s" , serverFilePath , multimedia.getDataFile().replace("/upfiles" , ""));
					fileService.delete(deleteFilePath);
				}
			}
			else
			{
				params.put("dataFile" , multimedia.getDataFile() );
				params.put("fileName" , multimedia.getFileName() );	
			}	
			//logger.info("multimediaList update ::::::::::::::::::::::::");
			multimediaService.updateMultimedia(params);
			returnUrl = "redirect:multimediaList.do?mmId=" + mmId + "&mode=modify&pg=" + pg + "&findCategory=" + findCategory + "&findStr=" + findStr + "&findMethod=" + findMethod + "&findStartDate=" + findStartDate + "&findEndDate=" + findEndDate + "&findIsUse=" + findIsUse;
		}
		
		return returnUrl;
	}
	
	/**
	 * 멀티미디어 삭제
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 * */
	@RequestMapping(value="multimediaDelete.do" , method=RequestMethod.POST)
	public String multimediaDelete(HttpServletRequest request, Model model) throws Exception
	{
		int mmId = Common.getParameter(request, "mmId", 0);
		
		if(mmId == 0){
			throw new CommonException("정상적인 접근이 아닙니다." , CommonException.HISTORY_BACK);
		}
		
		//참조된 데이터가 있는지 확인.
		int ref_count =  multimediaService.getDataRefCount(mmId);
		if(ref_count>0) {
			logger.info(ref_count+"건이 자료관리 데이터에서 사용 중.");
			throw new CommonException("다른 메뉴에서 사용 중! 수정만 가능합니다." , CommonException.HISTORY_BACK);
		}
		
		try{
			Multimedia multimedia = multimediaService.getMultimediaDetail(mmId);
			
			String thumbnail = multimedia.getThumbnail();
			String dataFile = multimedia.getDataFile();
			
			if( !thumbnail.equals("") ){
				String deleteFilePath = String.format("%s%s" , serverFilePath , thumbnail.replace("/upfiles" , ""));
				fileService.delete(deleteFilePath);
			}
			
			if( !dataFile.equals("") ){
				String deleteFilePath = String.format("%s%s" , serverFilePath , dataFile.replace("/upfiles" , ""));
				fileService.delete(deleteFilePath);
			}
			
			multimediaService.multimediaDelete(mmId);
		
		
		}catch(Exception e){
			throw new CommonException("정상적인 접근이 아닙니다." , CommonException.HISTORY_BACK);
		}
		
		return "redirect:multimediaList.do";
	}
	
	/**
	 * 파일 삭제
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 * */
	@RequestMapping(value="delete.do" , method=RequestMethod.POST)
	public @ResponseBody Map<String,Object>delete(HttpServletRequest request, Model model) throws Exception{
		Map<String, Object> result = new HashMap<String, Object>();
		
		int mmId = Common.getParameter(request, "mmId", 0);
		String type = request.getParameter("type");
		
		if( mmId == 0 ){
			result.put("result" , "FAILURE");
			result.put("msg" , "정상적인 접근이 아닙니다.");
			return result;
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("mmId", mmId);
		params.put("type", type);
		
		String targetFile = multimediaService.getDeleteTarget(params);
		
		String deleteFilePath = String.format("%s%s" , serverFilePath , targetFile.replace("/upfiles" , ""));
		fileService.delete(deleteFilePath);
		
		// set result
		result.put("result" , "SUCCESS");
		result.put("msg" , "성공");
		result.put("type" , type);
		return result;
	}
	
	/**
	 * 멀티미디어 자료검색 팝업
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 * */
	@RequestMapping("multimediaSearchPopup.do")
	public String multimediaSearchPopup(HttpServletRequest request, Model model) throws Exception
	{
		// get parameters
		int pg = Common.getParameter(request, "pg", 1);
		int skip = (pg - 1) * PAGE_SIZE;
		
		String findStr = Common.getParameter(request, "findStr", "");
		String findMethod = Common.getParameter(request, "findMethod", "");
		String category = Common.getParameter(request, "category", "");
		
		
		//set parameter
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("skip", skip);
		params.put("pageSize", PAGE_SIZE);
		
		params.put("findStr", findStr);
		params.put("findMethod", findMethod);
		params.put("findCategory", category);
		
		int totalList = multimediaService.getTotalMultimedia(params);
		int multimediaNum = totalList - skip;
		
		//set Paging
		Pagination pagination = new Pagination(pg, PAGE_SIZE, BLOCK_SIZE, totalList);
		pagination.setDelimiter("");
		pagination.setPrevLink("Prev");
		pagination.setNextLink("Next");
		
		List<Category> categories = categoryService.getChildren(categoryCode);
		List<Multimedia> multimediaList = multimediaService.getMultimediaList(params);
		
		model.addAttribute("skip", skip);
		model.addAttribute("pg", pg);
		model.addAttribute("multimediaNum", multimediaNum);
		model.addAttribute("multimediaList", multimediaList);
		model.addAttribute("paging", pagination.print());

		model.addAttribute("categories", categories);
		model.addAttribute("findStr", findStr);
		model.addAttribute("findMethod", findMethod);
		model.addAttribute("category", category);
		
		model.addAttribute("title", "자료검색");
		model.addAttribute("footer", "N");
		
		return "popup/multimediaSearchPopup";
	}
}
