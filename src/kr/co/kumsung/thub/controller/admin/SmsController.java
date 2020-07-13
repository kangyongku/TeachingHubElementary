package kr.co.kumsung.thub.controller.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import kr.co.kumsung.thub.domain.Category;
import kr.co.kumsung.thub.domain.Member;
import kr.co.kumsung.thub.exception.CommonException;
import kr.co.kumsung.thub.service.CategoryService;
import kr.co.kumsung.thub.service.MemberService;
import kr.co.kumsung.thub.setting.Constants;
import kr.co.kumsung.thub.util.Common;
import kr.co.kumsung.thub.util.Pagination;
import kr.co.kumsung.thub.util.ResultMap;
import kr.co.kumsung.thub.util.Validate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * SMS 컨트롤러
 * @author mikelim
 *
 */
@Component("adminSmsController")
@Controller
@RequestMapping("/admin/sms")
public class SmsController {
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private MemberService memberService;
	
	/**
	 * SMS 발송 내역
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list.do")
	public String list(HttpServletRequest request , Model model) throws Exception
	{
		final int PAGE_SIZE = 10;
		final int BLOCK_SIZE = 10;
		
		int pg = Common.getParameter(request, "pg", 1);
		int skip = (pg - 1) * PAGE_SIZE;
		
		// make query parameter
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("skip" , skip);
		params.put("pageSize" , PAGE_SIZE);
		
		// get data
		int totalCount = memberService.getSmsSendListCount();
		int articleNum = totalCount - skip;
		List<ResultMap> list = memberService.getSmsSendList(params);
		
		Pagination pagination = new Pagination(pg, PAGE_SIZE, BLOCK_SIZE, totalCount);
		pagination.initialize();		
		
		// assign
		model.addAttribute("pathTitle", "SMS 발송");
		model.addAttribute("path", "SMS 관리");
		
		model.addAttribute("pg", pg);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("articleNum", articleNum);
		model.addAttribute("list", list);
		model.addAttribute("paging", pagination.print());
		
		return "admin/sms/list";
	}
	
	/**
	 * SMS 발송하기
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/form.do" , method=RequestMethod.GET)
	public String form(HttpServletRequest request , Model model) throws Exception
	{	
		List<Category> elCategories = (List<Category>) categoryService.getChildrenAll("A001");
		List<Category> mdCategories = (List<Category>) categoryService.getChildrenAll("A002");
		List<Category> hgCategories = (List<Category>) categoryService.getChildrenAll("A003");
		
		// assign
		model.addAttribute("pathTitle", "SMS 발송");
		model.addAttribute("path", "SMS 관리");
		
		model.addAttribute("elCategories", elCategories);
		model.addAttribute("mdCategories", mdCategories);
		model.addAttribute("hgCategories", hgCategories);
		
		return "admin/sms/form";
	}
	
	/**
	 * 
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/form.do" , method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> formAction(HttpServletRequest request , Model model) throws Exception
	{
		// session check
		HttpSession session = (HttpSession) request.getSession();
		Member member = (Member) session.getAttribute("adminMember");
		
		// get parameters
		String message = Common.getParameter(request, "message", "");
		String recievers = Common.getParameter(request, "recievers" , "");
		String recieverFlag = Common.getParameter(request, "recieverFlag", "");
		String isSms = Common.getParameter(request, "isSms", "Y");
		String senderNum = Common.getParameter(request, "senderNum", "");
		String subjects = "";
		int sendTotalCount = 0;
		
		// validation check
		if( Validate.isEmpty(message)
				|| Validate.isEmpty(recieverFlag)
				|| Validate.isEmpty(senderNum))
			throw new CommonException("파라미터 오류" , CommonException.HISTORY_BACK);

		if( recieverFlag.equals("N") )
		{
			// 미인증 회원일 경우 선생님 회원 모두에게 보낸다.
			Map<String,Object> params = new HashMap<String,Object>();
			
			params.put("findIsRecSms" , isSms);
			params.put("findIsFinallyAuth" , "N");
			
			int skip = 0;
			int totalCount = memberService.getMemberTotalList(params);
			int totalPages = (int)Math.ceil(((double)totalCount / (double)100));
			
			sendTotalCount = totalCount;
			
			for(int i = 1 ; i <= totalPages ; i++ )
			{
				params.put("skip" , (i - 1) * 100);
				params.put("pageSize" , 100);
				
				List<Member> members = (List<Member>) memberService.getMemberList(params);
				
				if( members.size() > 0 )
				{
					Map<String,Object> smsParam = new HashMap<String,Object>();
					
					smsParam.put("message" , message);
					smsParam.put("callback" , senderNum);
					smsParam.put("totalCount" , members.size());
					
					StringBuffer sb = new StringBuffer();
					
					for(Member m : members)
					{
						if( !Validate.isEmpty(m.getHpNum()) )
							sb.append(String.format("%s^%s|" , m.getName() , m.getHpNum().replaceAll("-" , "")));
					}
					
					String destInfo = sb.toString();
					
					smsParam.put("destInfo" , destInfo.substring(0 , destInfo.length() - 1));
					
					// send sms
					//System.out.println(smsParam.toString());
					memberService.sendSms(smsParam);
				}
			}
			
			subjects = "미인증 회원전체";
		}
		else
		{
			if( recievers.equals("total") )
			{
				// 인증회원 전체에게 보낸다.
				Map<String,Object> params = new HashMap<String,Object>();
				
				params.put("findIsRecSms" , isSms);
				params.put("findIsFinallyAuth" , "Y");
				
				int skip = 0;
				int totalCount = memberService.getMemberTotalList(params);
				int totalPages = (int)Math.ceil(((double)totalCount / (double)100));
				
				sendTotalCount = totalCount;
				
				for(int i = 1 ; i <= totalPages ; i++ )
				{
					params.put("skip" , (i - 1) * 100);
					params.put("pageSize" , 100);
					
					List<Member> members = (List<Member>) memberService.getMemberList(params);
					
					if( members.size() > 0 )
					{
						Map<String,Object> smsParam = new HashMap<String,Object>();
						
						smsParam.put("message" , message);
						smsParam.put("callback" , senderNum);
						smsParam.put("totalCount" , members.size());
						
						StringBuffer sb = new StringBuffer();
						
						for(Member m : members)
						{
							if( !Validate.isEmpty(m.getHpNum()) )
								sb.append(String.format("%s^%s|" , m.getName() , m.getHpNum().replaceAll("-" , "")));
						}
						
						String destInfo = sb.toString();
						
						smsParam.put("destInfo" , destInfo.substring(0 , destInfo.length() - 1));
						
						// send sms
						//System.out.println(smsParam.toString());
						memberService.sendSms(smsParam);
					}
				}
				
				
				
				subjects = "인증회원 전체";
			}
			else
			{	
				// 받는 사람들을 가지고 오자!!!
				// 전체가 포함되어있는지 사전에 체크한다.
				String[] r = recievers.split(",");
				
				for(String t : r)
				{
					// 전체에 대한 검색을 실시한다.
					Map<String,Object> params = new HashMap<String,Object>();
					
					params.put("findIsRecSms" , isSms);
					params.put("findIsFinallyAuth" , "Y");
					params.put("findSubCategory" , t);
					
					int skip = 0;
					int totalCount = memberService.getMemberTotalList(params);
					int totalPages = (int)Math.ceil(((double)totalCount / (double)100));
					
					sendTotalCount += totalCount;
					
					for(int i = 1 ; i <= totalPages ; i++ )
					{
						params.put("skip" , (i - 1) * 100);
						params.put("pageSize" , 100);
						
						List<Member> members = (List<Member>) memberService.getMemberList(params);
						
						if( members.size() > 0 )
						{
							Map<String,Object> smsParam = new HashMap<String,Object>();
							
							smsParam.put("message" , message);
							smsParam.put("callback" , senderNum);
							smsParam.put("totalCount" , members.size());
							
							StringBuffer sb = new StringBuffer();
							
							for(Member m : members)
							{
								if( !Validate.isEmpty(m.getHpNum()) )
									sb.append(String.format("%s^%s|" , m.getName() , m.getHpNum().replaceAll("-" , "")));
							}
							
							String destInfo = sb.toString();
							
							smsParam.put("destInfo" , destInfo.substring(0 , destInfo.length() - 1));
							
							// send sms
							//System.out.println(smsParam.toString());
							memberService.sendSms(smsParam);
						}
					}
					
					Category category = categoryService.getCategory(t);
					
					if( t.length() == 4 )
					{
						subjects += category.getName() + "전체<br/>";
					}
					else
					{
						if( t.substring(0,4).equals("A001") )
							subjects += "초등" + " > " + category.getName() + "<br/>";
						else if( t.substring(0,4).equals("A002") )
							subjects += "중등" + " > " + category.getName() + "<br/>";
						else if( t.substring(0,4).equals("A003") )	
							subjects += "고등" + " > " + category.getName() + "<br/>";
					}
					
				}
			}
		}

		Map<String,Object> logParams = new HashMap<String,Object>();
		
		logParams.put("subjects" , subjects);
		logParams.put("message" , message);
		logParams.put("callback" , senderNum);
		logParams.put("totalCount" , sendTotalCount);
		
		memberService.insertSmsSendInfo(logParams);
		
		return new HashMap<String,Object>(){
			{
				put("result" , "SUCCESS");
			}
		};
	}
	
	
	/**
	 * 단일 SMS 발송하기
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/single.do" , method=RequestMethod.GET)
	public String single(HttpServletRequest request , Model model) throws Exception
	{			
		// assign
		model.addAttribute("pathTitle", "개인 SMS 발송");
		model.addAttribute("path", "SMS 관리");
		
		return "admin/sms/single";
	}
	
	/**
	 * 단일 SMS 발송하기
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/single.do" , method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> singleAction(HttpServletRequest request , Model model) throws Exception
	{			
		// get parameters
		String message = Common.getParameter(request, "message", "");
		String senderNum = Common.getParameter(request, "senderNum", "");
		String recieverNum = Common.getParameter(request, "recieverNum", "");
		
		// sende sms
		Map<String,Object> smsParam = new HashMap<String,Object>();
		
		smsParam.put("message" , message);
		smsParam.put("callback" , senderNum);
		smsParam.put("totalCount" ,1);
		smsParam.put("destInfo" , String.format("^%s" , recieverNum));
		
		// send sms
		memberService.sendSms(smsParam);
		
		Map<String,Object> logParams = new HashMap<String,Object>();
		
		String subjects = "--";
		logParams.put("subjects" , subjects);
		logParams.put("message" , message);
		logParams.put("callback" , senderNum);
		logParams.put("totalCount" , 1);
		logParams.put("singleMsg", 1);	//개인발송식별
		
		memberService.insertSmsSendInfo(logParams);
		
		// assign
		return new HashMap<String,Object>(){
			{
				put("result" , "SUCCESS");
			}
		};
	}
	
	public static String arrayJoin(String glue, String array[]) {
		String result = "";

		for (int i = 0; i < array.length; i++) {
			result += array[i];
			if (i < array.length - 1) result += glue;
		}
		return result;
	}
	
}
