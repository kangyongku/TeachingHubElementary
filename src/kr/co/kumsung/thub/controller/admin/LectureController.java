package kr.co.kumsung.thub.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import kr.co.kumsung.thub.domain.Lecture;
import kr.co.kumsung.thub.domain.FileInfo;
import kr.co.kumsung.thub.domain.Member;
import kr.co.kumsung.thub.exception.CommonException;
import kr.co.kumsung.thub.service.CategoryService;
import kr.co.kumsung.thub.service.FileService;
import kr.co.kumsung.thub.service.LectureService;
import kr.co.kumsung.thub.util.Common;
import kr.co.kumsung.thub.util.Pagination;
import kr.co.kumsung.thub.util.Validate;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * 공감! 강연 관리
 * @author JHG
 * @date 0812~0814
 */
@Component("adminLectureController")
@Controller
@RequestMapping("/admin/lecture")
public class LectureController {

	@Autowired
	private LectureService lectureService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private FileService fileService;
	
	@Value("#{common['file.path']}") String serverFilePath;		// 첨부파일의 root 경로
	
	private static final int PAGE_SIZE = 10;
	private static final int BLOCK_SIZE = 10;
	
	/**
	 * 강연관리 리스트
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/lectureList.do")
	public String lectureList(HttpServletRequest request , Model model) throws Exception
	{
		// get parameters
		int pg = Common.getParameter(request, "pg", 1);
		int skip = (pg - 1) * PAGE_SIZE;

		//TODO: 카테고리 정의되면 수정.
		String findCategory = Common.getParameter(request, "findCategory", "");
		String findStartDate = Common.getParameter(request, "findStartDate", "");
		String findEndDate = Common.getParameter(request, "findEndDate", "");
		String findMethod = Common.getParameter(request, "findMethod", "");
		String findStr = Common.getParameter(request, "findStr", "");

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("skip", skip);
		params.put("pageSize", PAGE_SIZE);
		
		params.put("findMethod", findMethod);
		params.put("findStr", findStr);
		params.put("findStartDate", findStartDate);
		params.put("findEndDate", findEndDate);
		
		int totalLecture = lectureService.getLectureTotalList(params);
		// set pagination
		Pagination pagination = new Pagination(pg, PAGE_SIZE, BLOCK_SIZE, totalLecture);
		pagination.setDelimiter("");
		pagination.setPrevLink("Prev");
		pagination.setNextLink("Next");
		
		int lectureNum = totalLecture - skip;
		
		List<Lecture> lectureList = lectureService.getLectureList(params);
		model.addAttribute("pg", pg);
		
		model.addAttribute("findMethod", findMethod);
		model.addAttribute("findStr", findStr);
		model.addAttribute("findStartDate", findStartDate);
		model.addAttribute("findEndDate", findEndDate);
		
		model.addAttribute("lectureNum", lectureNum);
		model.addAttribute("lectureList", lectureList);
		model.addAttribute("paging", pagination.print());
		model.addAttribute("pathTitle", "공감! 강연 관리");
		model.addAttribute("path", "공감! 강연 관리");
		
		return "admin/lecture/lectureList";
	}
	
	
	/**
	 * 강연관리 등록/수정 폼
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/lectureForm.do")
	public String lectureForm(HttpServletRequest request , Model model) throws Exception
	{
		// get parfameters
		int pg = Common.getParameter(request, "pg", 1);		
		String mode = Common.getParameter(request, "mode", "add");

		int lectureId = Common.getParameter(request, "lectureId", 0);
		Lecture lecture = new Lecture();
		
		if(mode.equals("modify")){
			lecture = lectureService.getLectureDetail(lectureId);
		}
		
		model.addAttribute("lectureId", lectureId);
		model.addAttribute("lecture", lecture);
		model.addAttribute("mode", mode);
		
		model.addAttribute("pathTitle", "공감! 강연 관리");
		model.addAttribute("path", "공감! 강연 관리  등록/수정");
		model.addAttribute("pg", pg);
		
		return "admin/lecture/lectureForm";
	}
	
	/**
	 * 강연관리 등록
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="lectureAction.do" , method=RequestMethod.POST)
	public String lectureAction(HttpServletRequest request, Model model) throws Exception
	{
		String mode = Common.getParameter(request, "mode", "add");
		
		int lectureId = Common.getParameter(request, "lectureId", 0); 
		String category = Common.getParameter(request, "category", "");
		String title = Common.getParameter(request, "title", "");
		String summaryContents = Common.getParameterNoXSS(request, "summaryContents", "");
		String thumbnail = Common.getParameter(request, "thumbnail", "");
		String linkUrl = Common.getParameterNoXSS(request, "linkUrl", "");
		String contents = Common.getParameterNoXSS(request, "contents", "");
		String originText = Common.getParameter(request, "originText", "");
		String originCode = Common.getParameter(request, "originCode", "");
		String thumbnailImgPath;
		
		// 관리자 타입에 따라서 조회 권한 부여
		HttpSession session = (HttpSession) request.getSession();
		Member adminMember = (Member) session.getAttribute("adminMember");
		
		String regId = adminMember.getUserId();
		String modifyId = adminMember.getUserId();
		
		category = "TEST";
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("lectureId", lectureId);
		params.put("category", category);
		params.put("title", title);
		params.put("summaryContents", summaryContents);
		params.put("thumbnail", thumbnail);
		params.put("linkUrl", linkUrl);
		params.put("contents", contents);
		params.put("originText", originText);
		params.put("originCode", originCode);
		
		params.put("regId", regId);
		params.put("modifyId", modifyId);
		
		if(mode.equals("add")){
			lectureService.insertLecture(params);
		}else if(mode.equals("modify")){
			lectureService.updateLecture(params);
		}else if(mode.equals("delete")){
			if(lectureId == 0){
				throw new CommonException("잘못된 접근입니다." , CommonException.HISTORY_BACK);
			}
			thumbnailImgPath = lectureService.getLectureDetailThumbnail(lectureId);
			if( !Validate.isEmpty(thumbnail) )
			{
				String deleteFilePath = String.format("%s%s" , serverFilePath , thumbnail.replace("/upfiles" , ""));
				fileService.delete(deleteFilePath);
			}
			lectureService.deleteLecture(lectureId);
		}
		
		return "redirect:lectureList.do";
	}
	 
	/**
	 * 파일 업로드
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 * */
	@RequestMapping("/lectureUpload.do")
	public String lectureUpload(HttpServletRequest request, Model model,
			@RequestParam("upfile")MultipartFile attachFile ) throws Exception
	{
		
		int lectureId = (Integer) Common.getParameter(request, "lectureId",0);
		String allowFileTypes = "jpg,gif,png";
		FileInfo fileInfo = new FileInfo(); 
		if(!attachFile.isEmpty()){
			fileInfo = fileService.upload(attachFile, serverFilePath, "thumbnail");
			String fileExt = fileInfo.getOriginName().substring(fileInfo.getOriginName().lastIndexOf(".") + 1 , fileInfo.getOriginName().length()).toLowerCase();
			fileService.makeThumbail(serverFilePath + fileInfo.getAbsolutePath().replace("/upfiles" , ""), fileExt);
		}
		
		model.addAttribute("fileInfo", fileInfo);
		model.addAttribute("msg","aa");
		
		return "fileHandler";
	}
	
	
	
}