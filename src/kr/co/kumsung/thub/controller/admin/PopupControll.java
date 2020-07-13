package kr.co.kumsung.thub.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import kr.co.kumsung.thub.domain.Data;
import kr.co.kumsung.thub.domain.Lecture;
import kr.co.kumsung.thub.domain.Member;
import kr.co.kumsung.thub.domain.Poll;
import kr.co.kumsung.thub.domain.Popup;
import kr.co.kumsung.thub.exception.CommonException;
import kr.co.kumsung.thub.service.PopupService;
import kr.co.kumsung.thub.util.Common;
import kr.co.kumsung.thub.util.Pagination;
import kr.co.kumsung.thub.util.Validate;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 팝업 관리
 * @author jhg
 * @date 0814~0814
 */
@Component("adminPopupControll")
@Controller
@RequestMapping("/admin/popup")
public class PopupControll {
	
	private static final int PAGE_SIZE = 10;
	private static final int BLOCK_SIZE = 10;
	
	@Autowired
	private PopupService popupService;
	
	/**
	 * 팝업 목록 리스트
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/popupList.do")
	public String popupList(HttpServletRequest request , Model model) throws Exception
	{
		// get parameters
		int pg = Common.getParameter(request, "pg", 1);
		int skip = (pg - 1) * PAGE_SIZE;

		String findMethod = Common.getParameter(request, "findMethod", "");
		String findStr = Common.getParameter(request, "findStr", "");

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("skip", skip);
		params.put("pageSize", PAGE_SIZE);
		
		params.put("findMethod", findMethod);
		params.put("findStr", findStr);
		
		int totalPopup = popupService.getPopupTotalList(params);
		// set pagination
		Pagination pagination = new Pagination(pg, PAGE_SIZE, BLOCK_SIZE, totalPopup);
		pagination.setDelimiter("");
		pagination.setPrevLink("Prev");
		pagination.setNextLink("Next");
		
		int popupNum = totalPopup - skip;
		
		List<Popup> popupList = popupService.getPopupList(params);
		model.addAttribute("pg", pg);
		
		model.addAttribute("findMethod", findMethod);
		model.addAttribute("findStr", findStr);
		
		model.addAttribute("popupNum", popupNum);
		model.addAttribute("popupList", popupList);
		model.addAttribute("paging", pagination.print());
		model.addAttribute("pathTitle", "팝업 목록");
		model.addAttribute("path", "팝업 목록 / 리스트");
		
		return "admin/popup/popupList";
	}
	
	
	
	/**
	 * 팝업 목록 폼
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/popupForm.do")
	public String popupForm(HttpServletRequest request , Model model) throws Exception
	{
		// get parfameters
		int pg = Common.getParameter(request, "pg", 1);		
		String mode = Common.getParameter(request, "mode", "add");

		int popupId = Common.getParameter(request, "popupId", 0);
		Popup popup = new Popup();
		
		if(mode.equals("modify")){
			popup = popupService.getPopupDetail(popupId);
		}
		
		model.addAttribute("popupId", popupId);
		model.addAttribute("popup", popup);
		model.addAttribute("mode", mode);
		
		model.addAttribute("pathTitle", "팝업 목록");
		model.addAttribute("path", "팝업 목록  등록/수정");
		model.addAttribute("pg", pg);
		
		return "admin/popup/popupForm";
	}
	
	/**
	 * 이벤트 관리 등록
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="popupAction.do" , method=RequestMethod.POST)
	public String popupAction(HttpServletRequest request, Model model) throws Exception
	{
		String mode = Common.getParameter(request, "mode", "add");
		
		int popupId = Common.getParameter(request, "popupId", 0); 
		String target = Common.getParameter(request, "target", "");
		String title = Common.getParameter(request, "title", "");
		String contents = Common.getParameterNoXSS(request, "contents", "");
		int sizeWidth = Common.getParameter(request, "sizeWidth", 0);
		int sizeHeight = Common.getParameter(request, "sizeHeight", 0);
		int positionWidth = Common.getParameter(request, "positionWidth", 0);
		int positionHeight = Common.getParameter(request, "positionHeight", 0);
		String startDate = Common.getParameter(request, "startDate", "");
		String endDate = Common.getParameter(request, "endDate", "");
		String type = Common.getParameter(request, "type", "");
		String isUse = Common.getParameter(request, "isUse", "");
		
		
		// 관리자 타입에 따라서 조회 권한 부여
		HttpSession session = (HttpSession) request.getSession();
		Member adminMember = (Member) session.getAttribute("adminMember");
		
		String regId = adminMember.getUserId();
		String modifyId = adminMember.getUserId();
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("popupId", popupId);
		params.put("target", target);
		params.put("title", title);
		params.put("contents", contents);
		params.put("sizeWidth", sizeWidth);
		params.put("sizeHeight", sizeHeight);
		params.put("positionWidth", positionWidth);
		params.put("positionHeight", positionHeight);
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		params.put("type", type);
		params.put("isUse", isUse);
		params.put("regId", regId);
		params.put("modifyId", modifyId);
		
		if(mode.equals("add")){
			popupService.insertPopup(params);
		}else if(mode.equals("modify")){
			popupService.updatePopup(params);
		}else if(mode.equals("delete")){
			popupService.deletePopup(popupId);
		}
		
		return "redirect:popupList.do";
	}
	
}
