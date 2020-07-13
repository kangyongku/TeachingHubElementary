package kr.co.kumsung.thub.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import kr.co.kumsung.thub.domain.Category;
import kr.co.kumsung.thub.domain.Customer;
import kr.co.kumsung.thub.domain.Member;
import kr.co.kumsung.thub.exception.CommonException;
import kr.co.kumsung.thub.service.CategoryService;
import kr.co.kumsung.thub.service.CustomerService;
import kr.co.kumsung.thub.service.MemberService;
import kr.co.kumsung.thub.setting.Constants;
import kr.co.kumsung.thub.util.Common;
import kr.co.kumsung.thub.util.Pagination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * 공지사항 관리 컨트롤러 Admin
 * @author JHG
 *
 */
@Component("adminCustomerController")
@Controller
@RequestMapping("/admin/customer")
public class CustomerController {
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private CustomerService customerService;
	
	private static final int PAGE_SIZE = 10;
	private static final int BLOCK_SIZE = 10;
	
	
	/**
	 * 고객문의
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/customerList.do")
	public String customerList(HttpServletRequest request , Model model) throws Exception
	{
		// get parameters
		int pg = Common.getParameter(request, "pg", 1);
		int skip = (pg - 1) * PAGE_SIZE;
		
		List<Category> masterCategories = categoryService.getChildren(Constants.CUSTOMER_CATEGORY);
		List<Category> categories = categoryService.getChildren(Constants.LEARNING_CATEGORY);
	
		String findMethod = Common.getParameter(request, "findMethod", "");
		String findStr = Common.getParameter(request, "findStr", "");
		String findCategory = Common.getParameter(request, "findCategory", "");
		String findBoardCategory = Common.getParameter(request, "findBoardCategory", "");
		String findAnswerStatus = Common.getParameter(request, "findAnswerStatus", "");
		//System.out.println("#############################################################"+findBoardCategory);
		
		String searchCategory = findCategory;
		String searchBCategory = findBoardCategory;
		
		//카테고리가 꼬여있어서 별도 처리.
		if(findBoardCategory.length()>7) {
			searchCategory = findBoardCategory;
			searchBCategory = findBoardCategory.substring(0, 7);
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("skip", skip);
		params.put("pageSize", PAGE_SIZE);
		
		params.put("findMethod", findMethod);
		params.put("findStr", findStr);
		params.put("findCategory", searchCategory);
		params.put("findBoardCategory", searchBCategory);
		params.put("findAnswerStatus", findAnswerStatus);
		
		int totalCustomer = customerService.getCustomerTotalList(params);
		// set pagination
		Pagination pagination = new Pagination(pg, PAGE_SIZE, BLOCK_SIZE, totalCustomer);
		pagination.setDelimiter("");
		pagination.setPrevLink("Prev");
		pagination.setNextLink("Next");
		
		int customerNum = totalCustomer - skip;
		
		List<Customer> list = customerService.getCustomerList(params);
		
		model.addAttribute("paging", pagination.print());
		model.addAttribute("customerNum", customerNum);

		model.addAttribute("list", list);
		model.addAttribute("masterCategories", masterCategories);
		model.addAttribute("categories", categories);
		model.addAttribute("pathTitle", "문의요청게시판관리");
		model.addAttribute("path", "문의요청게시판관리 리스트");
		
		model.addAttribute("pg", pg);
		model.addAttribute("findMethod", findMethod);
		model.addAttribute("findStr", findStr);
		model.addAttribute("findCategory", findCategory);
		model.addAttribute("findBoardCategory", findBoardCategory);
		model.addAttribute("findAnswerStatus", findAnswerStatus);
		
		return "admin/customer/customerList";
	}
	
	/**
	 * 고객문의 상세보기
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/customerDetail.do")
	public String customerDetail(HttpServletRequest request , Model model) throws Exception
	{
		int customerId = Common.getParameter(request, "customerId", 0);
		
		int pg = Common.getParameter(request, "pg", 1);
		String findMethod = Common.getParameter(request, "findMethod", "");
		String findStr = Common.getParameter(request, "findStr", "");
		String findCategory = Common.getParameter(request, "findCategory", "");
		String findBoardCategory = Common.getParameter(request, "findBoardCategory", "");
		String findAnswerStatus = Common.getParameter(request, "findAnswerStatus", "");
		
		String mode = "add";
		if( customerId == 0 )
			throw new CommonException("다시 이용해주시기 바랍니다." , CommonException.HISTORY_BACK);
		
		Customer customer = (Customer)customerService.getCustomerDetail(customerId);
		
		Customer customerAnswer = null;
		if( customer.getCustomerCnt() > 0 ){
			mode = "modify";
			customerAnswer = (Customer)customerService.getCustomerAnswer(customerId);
			customer.setAnswerId(customerAnswer.getAnswerId());
		}
		
		model.addAttribute("pg", pg);
		model.addAttribute("findMethod", findMethod);
		model.addAttribute("findStr", findStr);
		model.addAttribute("findCategory", findCategory);
		model.addAttribute("findBoardCategory", findBoardCategory);
		model.addAttribute("findAnswerStatus", findAnswerStatus);
		
		model.addAttribute("pathTitle", "문의요청게시판관리");
		model.addAttribute("path", "문의요청게시판관리 폼");
		model.addAttribute("customer", customer);
		model.addAttribute("customerAnswer", customerAnswer);
		model.addAttribute("mode", mode);
		
		return "admin/customer/customerDetail";
	}
	
	/**
	 * 고객문의 등록/수정
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/customerAction.do" , method=RequestMethod.POST)
	public String customerAction(HttpServletRequest request , Model model) throws Exception
	{
		HttpSession session = (HttpSession) request.getSession();
		Member adminMember = (Member) session.getAttribute("adminMember");
		
		int customerId = Common.getParameter(request, "customerId", 0);
		int answerId = Common.getParameter(request, "answerId", 0);
		String mode = Common.getParameter(request, "mode", "");
		
		String title = Common.getParameter(request, "title", "");
		String contents = Common.getParameterNoXSS(request, "contents", "");
		
		if( customerId == 0 )
			throw new CommonException("정상적인 접근이 아닙니다." , CommonException.HISTORY_BACK);
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("customerId", customerId);
		params.put("title", title);
		params.put("contents", contents);
		params.put("regId", adminMember.getUserId());
		params.put("answerId", answerId);

		if( mode.equals("add") )
		{
			customerService.insertCustomerAnswer(params);
			
			// sms 수신을 요청한 사람인지 체크한다.
			Customer customer = (Customer) customerService.getCustomerDetail(customerId);
			
			// 등록 회원의 정보를 가지고 온다.
			Member customerMember = (Member) memberService.get(customer.getRegId());
			
			if( customer.getIsSns().equals("Y") ){
				// sms를 발송한다.
				Map<String,Object> smsParams = new HashMap<String,Object>();
				
				smsParams.put("message" , "[티칭허브] 선생님의 질문에 답변이 등록되었습니다.");
				smsParams.put("callback" , "02-2077-8097");
				smsParams.put("totalCount" , "1");
				smsParams.put("destInfo" , String.format("%s^%s|" , customerMember.getName() , customer.getPhoneNum().replaceAll("-" , "")));
				
				memberService.sendSms(smsParams);
			}
		}
		else if ( mode.equals("modify") ){
			if( answerId == 0 )
				throw new CommonException("정상적인 접근이 아닙니다." , CommonException.HISTORY_BACK);
			customerService.updateCustomerAnswer(params);
		}
		
		return "redirect:customerList.do";
	}
	
	/**
	 * 고객문의 파일다운로드
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/downLoad.do")
	public String downLoad(HttpServletRequest request , Model model) throws Exception
	{
		String datafile = Common.getParameter(request, "datafile", "null");
		String filename = Common.getParameter(request, "filename", "null");
		System.out.println("test 222");
		model.addAttribute("datafile", datafile);
		model.addAttribute("filename", filename);
		return "admin/customer/downLoad";
	}
	
}