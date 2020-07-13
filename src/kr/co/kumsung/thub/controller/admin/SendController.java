package kr.co.kumsung.thub.controller.admin;

import java.io.BufferedOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.co.kumsung.thub.domain.FileInfo;
import kr.co.kumsung.thub.domain.Member;
import kr.co.kumsung.thub.domain.Send;
import kr.co.kumsung.thub.exception.CommonException;
import kr.co.kumsung.thub.service.CustomerService;
import kr.co.kumsung.thub.util.Common;
import kr.co.kumsung.thub.util.Pagination;
import kr.co.kumsung.thub.util.Validate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 회원원고관리
 * @author mikelim
 *
 */
@Component("adminSendController")
@Controller
@RequestMapping("/admin/send")
public class SendController {

	@Autowired
	private CustomerService customerService;
	
	@RequestMapping("/list.do")
	public String list(HttpServletRequest request , Model model) throws Exception
	{
		final int PAGE_SIZE = 10;
		final int BLOCK_SIZE = 10;
		
		// get paramters
		int pg = Common.getParameter(request, "pg", 1);
		int skip = (pg - 1) * PAGE_SIZE;
		int findBoardId = Common.getParameter(request, "findBoardId", 0);
		String findIsAccept = Common.getParameter(request, "findIsAccept", "");
		String findStartDate = Common.getParameter(request, "findStartDate", "");
		String findEndDate = Common.getParameter(request, "findEndDate", "");
		String findMethod = Common.getParameter(request, "findMethod", "");
		String findStr = Common.getParameter(request, "findStr", "");
		
		// make query parameter
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("findBoardId" , findBoardId);
		params.put("findIsAccept" , findIsAccept);
		params.put("findStartDate" , findStartDate);
		params.put("findEndDate" , findEndDate);
		params.put("findMethod" , findMethod);
		params.put("findStr" , findStr);
		params.put("skip" , skip);
		params.put("pageSize" , PAGE_SIZE);
		
		// get data
		int totalCount = customerService.getBoardSendListCount(params);
		int articleNum = totalCount - skip;
		List<Send> list = customerService.getBoardSendList(params);
		
		Pagination pagination = new Pagination(pg, PAGE_SIZE, BLOCK_SIZE, totalCount);
		pagination.setDelimiter("");
		pagination.setPrevLink("Prev");
		pagination.setNextLink("Next");
		pagination.initialize();
		
		// assign
		model.addAttribute("pathTitle", "회원원고관리 리스트");
		model.addAttribute("path", "열린자료마당 > 회원원고관리");
		
		model.addAttribute("pg", pg);
		model.addAttribute("findBoardId", findBoardId);
		model.addAttribute("findIsAccept", findIsAccept);
		model.addAttribute("findStartDate", findStartDate);
		model.addAttribute("findEndDate", findEndDate);
		model.addAttribute("findMethod", findMethod);
		model.addAttribute("findStr", findStr);
		
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("articleNum", articleNum);
		model.addAttribute("list", list);
		model.addAttribute("paging", pagination.print());
		
		return "admin/send/list";
	}
	
	@RequestMapping("/detail.do")
	public String detail(HttpServletRequest request , Model model) throws Exception
	{	
		// get paramters
		int sendId = Common.getParameter(request, "sendId", 0);
		int pg = Common.getParameter(request, "pg", 1);
		int findBoardId = Common.getParameter(request, "findBoardId", 0);
		String findIsAccept = Common.getParameter(request, "findIsAccept", "");
		String findStartDate = Common.getParameter(request, "findStartDate", "");
		String findEndDate = Common.getParameter(request, "findEndDate", "");
		String findMethod = Common.getParameter(request, "findMethod", "");
		String findStr = Common.getParameter(request, "findStr", "");
		
		// get data
		Send send = customerService.getBoardSend(sendId);
		
		// assign
		model.addAttribute("pathTitle", "회원원고관리 상세");
		model.addAttribute("path", "열린자료마당 > 회원원고관리");
		
		model.addAttribute("sendId", sendId);
		model.addAttribute("pg", pg);
		model.addAttribute("findBoardId", findBoardId);
		model.addAttribute("findIsAccept", findIsAccept);
		model.addAttribute("findStartDate", findStartDate);
		model.addAttribute("findEndDate", findEndDate);
		model.addAttribute("findMethod", findMethod);
		model.addAttribute("findStr", findStr);
		
		model.addAttribute("send", send);
		
		return "admin/send/detail";
	}
	
	/**
	 * 삭제
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteSend.do" , method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> deleteSend(HttpServletRequest request , Model model) throws Exception
	{
		Map<String,Object> result = new HashMap<String,Object>();
		
		// admin check
		HttpSession session = (HttpSession) request.getSession();
		Member member = (Member) session.getAttribute("adminMember");
		
		if( member == null )
		{
			result.put("result" , "SUCCESS");
			result.put("resultType" , "LOGIN");
			result.put("msg" , "로그인 하셔야 합니다.");
			return result;
		}
		
		// get parameters
		int sendId = Common.getParameter(request, "sendId", 0);
		
		if( sendId == 0 )
		{
			result.put("result" , "SUCCESS");
			result.put("resultType" , "PARAMETER");
			result.put("msg" , "정상적인 파라미터가 아닙니다.");
			return result;
		}
		
		// delete
		customerService.deleteSend(sendId);
		
		// assign
		result.put("result" , "SUCCESS");
		
		return result;
	}
	
	/**
	 * 승인상태 변경
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/changeAccept.do" , method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> changeAccept(HttpServletRequest request , Model model) throws Exception
	{
		Map<String,Object> result = new HashMap<String,Object>();
		
		// admin check
		HttpSession session = (HttpSession) request.getSession();
		Member member = (Member) session.getAttribute("adminMember");
		
		if( member == null )
		{
			result.put("result" , "SUCCESS");
			result.put("resultType" , "LOGIN");
			result.put("msg" , "로그인 하셔야 합니다.");
			return result;
		}
		
		// get parameters
		int sendId = Common.getParameter(request, "sendId", 0);
		String isAccept = Common.getParameter(request, "isAccept", "");
		
		if( sendId == 0 
				|| Validate.isEmpty(isAccept))
		{
			result.put("result" , "SUCCESS");
			result.put("resultType" , "PARAMETER");
			result.put("msg" , "정상적인 파라미터가 아닙니다.");
			return result;
		}
		
		// make query parameter
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("sendId" , sendId);
		params.put("isAccept" , isAccept);
		
		// delete
		customerService.updateSendAccept(params);
		
		// assign
		result.put("result" , "SUCCESS");
		
		return result;
	}
	
}