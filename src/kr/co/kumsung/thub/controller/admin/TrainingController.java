package kr.co.kumsung.thub.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.co.kumsung.thub.domain.Member;
import kr.co.kumsung.thub.domain.Training;
import kr.co.kumsung.thub.domain.TrainingPostscript;
import kr.co.kumsung.thub.domain.TrainingRequest;
import kr.co.kumsung.thub.exception.CommonException;
import kr.co.kumsung.thub.service.TrainingService;
import kr.co.kumsung.thub.util.Common;
import kr.co.kumsung.thub.util.Pagination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 교수연수지원 - 마스터
 * @author jhg
 * @date 0805~0807
 */
@Component("adminTrainingController")
@Controller
@RequestMapping("/admin/training")
public class TrainingController {
	
	private static final int PAGE_SIZE = 10;
	private static final int BLOCK_SIZE = 10;
	
	@Autowired
	private TrainingService trainingService;
	
	/**
	 * 교수연수지원 리스트
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/trainingList.do")
	public String traningList(HttpServletRequest request , Model model) throws Exception
	{
		// get parameters
		int pg = Common.getParameter(request, "pg", 1);
		int skip = (pg - 1) * PAGE_SIZE;

		String findStatusDate = Common.getParameter(request, "findStatusDate", ""); 
		String findStatusSketch = Common.getParameter(request, "findStatusSketch", "");
		String findMethod = Common.getParameter(request, "findMethod", "");
		String findStr = Common.getParameter(request, "findStr", "");
		String findStartDate = Common.getParameter(request, "findStartDate", "");
		String findEndDate = Common.getParameter(request, "findEndDate", "");

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("skip", skip);
		params.put("pageSize", PAGE_SIZE);
		
		params.put("findStatusDate", findStatusDate);
		params.put("findStatusSketch", findStatusSketch);
		params.put("findMethod", findMethod);
		params.put("findStr", findStr);
		params.put("findStartDate", findStartDate);
		params.put("findEndDate", findEndDate);
		
		int totalTraining = trainingService.getTrainingTotalList(params);
		// set pagination
		Pagination pagination = new Pagination(pg, PAGE_SIZE, BLOCK_SIZE, totalTraining);
		pagination.setDelimiter("");
		pagination.setPrevLink("Prev");
		pagination.setNextLink("Next");
		
		int trainingNum = totalTraining - skip;
		
		List<Training> trainingList = trainingService.getTrainingList(params);
		model.addAttribute("pg", pg);
		model.addAttribute("findStatusDate", findStatusDate);
		model.addAttribute("findStatusSketch", findStatusSketch);
		model.addAttribute("findMethod", findMethod);
		model.addAttribute("findStr", findStr);
		model.addAttribute("findStartDate", findStartDate);
		model.addAttribute("findEndDate", findEndDate);
		
		model.addAttribute("trainingNum", trainingNum);
		model.addAttribute("trainingList", trainingList);
		model.addAttribute("paging", pagination.print());
		model.addAttribute("pathTitle", "교사연수지원");
		model.addAttribute("path", "교사연수지원관리");
		
		return "admin/training/trainingList";
	}
	
	/**
	 * 교수연수지원 폼
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/trainingForm.do")
	public String trainingForm(HttpServletRequest request , Model model) throws Exception
	{
		// get parfameters
		int pg = Common.getParameter(request, "pg", 1);		
		String mode = Common.getParameter(request, "mode", "add");

		int trainingId = Common.getParameter(request, "trainingId", 0);
		int totalTrainingRequest = 0;
		Training training = new Training();
		
		if(mode.equals("modify")){
			training = trainingService.getTrainingDetail(trainingId);
			totalTrainingRequest = trainingService.getRequestTrainingTotal(trainingId);
		}
		
		model.addAttribute("pathTitle", "교사연수지원");
		model.addAttribute("path", "교사연수등록/수정");
		model.addAttribute("training", training);
		model.addAttribute("trainingId", trainingId);
		model.addAttribute("totalTrainingRequest", totalTrainingRequest);
		model.addAttribute("mode", mode);
		model.addAttribute("pg", pg);
		
		return "admin/training/trainingForm";
	}
	
	/**
	 * 교사연수 등록
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="trainingAction" , method=RequestMethod.POST)
	public String trainingAction(HttpServletRequest request , Model model) throws Exception
	{
		String returnUrl = "";
		
		// get parfameters
		int pg = Common.getParameter(request, "pg", 1);		
		String mode = Common.getParameter(request, "mode", "add");

		// 관리자 타입에 따라서 조회 권한 부여
		HttpSession session = (HttpSession) request.getSession();
		Member adminMember = (Member) session.getAttribute("adminMember");
		
		String regId = adminMember.getUserId();
		String modifyId = adminMember.getUserId();

		String title = Common.getParameter(request, "title", "");
		String contents = Common.getParameterNoXSS(request, "contents", "");
		String startDate = Common.getParameter(request, "startDate", "");
		String endDate = Common.getParameter(request, "endDate", "");
		int trainingId = Common.getParameter(request, "trainingId", 0);
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("regId", regId);
		params.put("modifyId", modifyId);
		params.put("title", title);
		params.put("contents", contents);
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		params.put("trainingId", trainingId);
		
		if(mode.equals("add"))
			trainingService.insertTraining(params);
		else if(mode.equals("modify")){
			trainingService.updateTraining(params);
		}else if(mode.equals("delete")){
			trainingService.deleteTraining(trainingId);
		}
		return returnUrl = "redirect:trainingList.do";
	}
	
	/**
	 * 교수지원 신청자 리스트 Iframe
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/trainingRequestFrame.do")
	public String trainingRequestFrame(HttpServletRequest request , Model model) throws Exception
	{
		
		String pageType = Common.getParameter(request,"pageType","page");
		
		// get parameters
		int pg = Common.getParameter(request, "pg", 1);
		int skip = (pg - 1) * PAGE_SIZE;
		int trainingId = Common.getParameter(request,"trainingId", 0);
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("skip", skip);
		params.put("pageSize", PAGE_SIZE);
		params.put("trainingId", trainingId);
		
		int totalTrainingRequest = trainingService.getRequestTrainingTotal(trainingId);
		// set pagination
		Pagination pagination = new Pagination(pg, PAGE_SIZE, BLOCK_SIZE, totalTrainingRequest);
		pagination.setDelimiter("");
		pagination.setPrevLink("Prev");
		pagination.setNextLink("Next");
		
		int  trainingRequestNum = totalTrainingRequest - skip;
		List<TrainingRequest> trainingReqList = trainingService.getTrainingReqList(params);
		
		model.addAttribute("pg", pg);
		model.addAttribute("trainingId", trainingId);
		model.addAttribute("trainingReqList", trainingReqList);
		model.addAttribute("trainingRequestNum", trainingRequestNum);
		model.addAttribute("paging", pagination.print());
		
		return "iframe/trainingRequestFrame";
	}

	/**
	 * 교수지원 신청자 엑셀 다운로드
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/trainingRequestExcel.do")
	public String trainingRequestExcel(HttpServletRequest request , Model model, HttpServletResponse response) throws Exception
	{
		// get parameters
		int trainingId = Common.getParameter(request,"trainingId", 0);
		String fileName = "Notice";
		fileName = trainingService.getTitleName(trainingId);
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("trainingId", trainingId);
		
		List<TrainingRequest> trainingReqList = trainingService.getTrainingReqList(params);

		model.addAttribute("trainingReqList", trainingReqList);
		
		response.reset();
		response.setContentType("application/vnd.ms-excel;charset=UTF-8");  
		response.setHeader("Content-Disposition", "attachment; filename="+fileName+".xls"); 
		response.setHeader("Content-Description", fileName);
		
		return "iframe/trainingRequestExcel";
	}
	
	/**
	 * 현장스케치관리
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/trainingPostscriptList.do")
	public String trainingPostscriptList(HttpServletRequest request , Model model){
		
		// get parameters
		int pg = Common.getParameter(request, "pg", 1);
		int skip = (pg - 1) * PAGE_SIZE;		
		
		String findMethod = Common.getParameter(request, "findMethod", "");
		String findStr = Common.getParameter(request, "findStr", "");
		String mode = Common.getParameter(request, "mode", "add");
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("skip", skip);
		params.put("pageSize", PAGE_SIZE);
		
		params.put("findMethod", findMethod);
		params.put("findStr", findStr);
		
		
		int totalTrainingPostscript = trainingService.getTrainingPostscriptTotalList(params);
		// set pagination
		Pagination pagination = new Pagination(pg, PAGE_SIZE, BLOCK_SIZE, totalTrainingPostscript);
		pagination.setDelimiter("");
		pagination.setPrevLink("Prev");
		pagination.setNextLink("Next");
		
		int trainingPostscriptNum = totalTrainingPostscript - skip;
		
		List<TrainingPostscript> trainingPostscriptList = trainingService.getTrainingPostscriptList(params);
		
		model.addAttribute("pg", pg);
		model.addAttribute("mode", mode);
		model.addAttribute("findMethod", findMethod);
		model.addAttribute("findStr", findStr);
		
		model.addAttribute("trainingPostscriptNum", trainingPostscriptNum);
		model.addAttribute("trainingPostscriptList", trainingPostscriptList);
		model.addAttribute("paging", pagination.print());
		
		model.addAttribute("pathTitle", "현장스케치");
		model.addAttribute("path", "현장스케치관리");
		
		
		return "admin/training/trainingPostscriptList";
	}
	
	/**
	 * 현장스케치관리 폼
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/trainingPostscriptForm.do")
	public String trainingPostscriptForm(HttpServletRequest request , Model model) throws Exception
	{
		// get parfameters
		int pg = Common.getParameter(request, "pg", 1);		
		String mode = Common.getParameter(request, "mode", "add");

		int postId = Common.getParameter(request, "postId", 0);
		TrainingPostscript trainingPostscript = new TrainingPostscript();
		List<Training> trainingIdList = trainingService.getTrainingIdList();
		if(mode.equals("modify")){
			trainingPostscript = trainingService.getTrainingPostscriptDetail(postId);
		}
		
		model.addAttribute("pathTitle", "현장스케치");
		model.addAttribute("path", "현장스케치관리 등록/수정");
		model.addAttribute("postId", postId);
		model.addAttribute("trainingPostscript", trainingPostscript);
		model.addAttribute("trainingIdList", trainingIdList);
		model.addAttribute("mode", mode);
		model.addAttribute("pg", pg);
		
		return "admin/training/trainingPostscriptForm";
	}
	
	/**
	 * 현장스케치관리 등록
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping(value="trainingPostscriptAction" , method=RequestMethod.POST)
	public String trainingPostscriptAction(HttpServletRequest request , Model model) throws Exception
	{
		String returnUrl = "";
		
		// get parfameters
		int pg = Common.getParameter(request, "pg", 1);		
		String mode = Common.getParameter(request, "mode", "add");

		// 관리자 타입에 따라서 조회 권한 부여
		HttpSession session = (HttpSession) request.getSession();
		Member adminMember = (Member) session.getAttribute("adminMember");
		
		String regId = adminMember.getUserId();
		String modifyId = adminMember.getUserId();

		String title = Common.getParameter(request, "title", "");
		String contents = Common.getParameterNoXSS(request, "contents", "");
		int trainingId = Common.getParameter(request, "trainingId", 0);
		int postId = Common.getParameter(request, "postId", 0);
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("regId", regId);
		params.put("title", title);
		params.put("contents", contents);
		params.put("trainingId", trainingId);
		params.put("postId", postId);
		params.put("modifyId", modifyId);
		TrainingPostscript trainingDupChk = trainingService.getTrainingPostscriptDup(trainingId);
		if(trainingDupChk.getPostCnt()>0 && trainingDupChk.getPostId()!=postId)
			throw new CommonException("이미 등록된 후기가 있습니다." , CommonException.HISTORY_BACK);
		
		if(mode.equals("add"))
			trainingService.insertTrainingPostscript(params);
		else if(mode.equals("modify")){
			trainingService.updateTrainingPostscript(params);
		}else if(mode.equals("delete")){
			trainingService.deleteTrainingPostscript(postId);
		}
		
		return returnUrl = "redirect:trainingPostscriptList.do";
	}
	
	/**
	 * 현장스케치관리 폼
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/trainingRequestPopup.do")
	public String trainingRequestPopup(HttpServletRequest request , Model model) throws Exception
	{
		// get parfameters
		int requestId = Common.getParameter(request, "requestId", 0);

		String contents = trainingService.getTraiingRequestContents(requestId);
		
		model.addAttribute("requestId", requestId);
		model.addAttribute("contents", contents);
		model.addAttribute("footer", "N");
		model.addAttribute("title", "연수신청 사연");
		return "popup/trainingRequestPopup";
	}
	
	
	
}
