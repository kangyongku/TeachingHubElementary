package kr.co.kumsung.thub.controller.admin;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import kr.co.kumsung.thub.domain.Member;
import kr.co.kumsung.thub.exception.CommonException;
import kr.co.kumsung.thub.service.MemberService;
import kr.co.kumsung.thub.util.Common;
import kr.co.kumsung.thub.util.DateUtil;
import kr.co.kumsung.thub.util.Validate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 로그인 컨트롤러
 * @author mikelim
 *
 */
@Component("adminLoginController")
@Controller
@RequestMapping("/admin")
public class LoginController {
	
	@Autowired
	private MemberService memberService;
	
	@RequestMapping(value="/login.do" , method=RequestMethod.GET)
	public String form(HttpServletRequest request , Model model) throws Exception
	{
		String url= request.getRemoteHost();
		System.out.println("login.do url : "+url);
		if(url.equals("127.0.0.1") || url.equals("192.168.56.103")){
			return "adminLogin";
		}else{
			throw new CommonException("사용이 만료된 페이지 입니다 \\n티칭허브 관리자에 문의 해주시기 바랍니다." , CommonException.HISTORY_BACK);	
		}
	}
	
	/**
	 * 로그인을 처리한다.
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/login.do" , method=RequestMethod.POST)
	public String formAction(HttpServletRequest request , Model model) throws Exception
	{
		
		// get parameters
		String userId = Common.getParameter(request, "userId", "");
		String passwd = Common.getParameter(request, "passwd", "");
		
		// validation check
		if( Validate.isEmpty(userId)
				|| Validate.isEmpty(passwd))
		{
			throw new CommonException("정상적인 요청이 아닙니다." , CommonException.HISTORY_BACK);
		}
		
		// 현재 입력된 정보에 대하여 조회한다.
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("userId" , userId);
		params.put("passwd" , passwd);
		
		//Member adminMember = (Member) memberService.getAdminLogin(params);
		Member adminMember = memberService.get(userId);
		System.out.println("adminMember : "+adminMember);
		if( adminMember == null ){
			throw new CommonException("아이디 올바르지 않습니다." , CommonException.HISTORY_BACK);
		}
		/*
		if( Validate.isEmpty(adminMember.getAuthType()) )
			adminMember.setAuthType("");
		*/
		// 비밀번호를 확인한다.		
/*		if(memberService.matchPassword(params) != 1 ){
			int loginCount = adminMember.getConnectCount();
			adminMember.setAuthType(adminMember.getAuthType() != null?adminMember.getAuthType():"");
			if(loginCount<3 && adminMember.getAuthType().equals("S")){
				memberService.updateLoginCount(params);
				String err="비밀번호를 "+(loginCount+1)+"회 잘못 입력하셨습니다. \\n3회 이상 잘못 입력할 경우 \\n해당 시스템 접근이 제한됩니다.";
				throw new CommonException(err , CommonException.HISTORY_BACK);
			}else{ //3회 이상 틀릴시 시스템 접근제한
				
				//Map<String,Object> updateParams = new HashMap<String,Object>();
				//updateParams.put("passwd" , "");
				//updateParams.put("userId" , userId);
				//memberService.updateMemberPasswd(updateParams);
				
				String err="비밀번호를 "+loginCount+"회 이상 잘못 입력하셨습니다. \\n 해당 시스템 접근이 제한됩니다.\\n관리자에게 문의바랍니다.";
				throw new CommonException(err , CommonException.HISTORY_BACK);
			}
		}else{ //3회 안에 정상로그인 하면 0회 초기화
			memberService.updateLoginCountDefault(params);
		}*/
		memberService.updateLoginCountDefault(params);
		System.out.println("remoateaddr : "+request.getRemoteAddr());
		//섹션에 자기 ip 등록
		adminMember.setIp(request.getRemoteAddr());
		// session을 등록한다.
		HttpSession session = (HttpSession) request.getSession();
		session.setAttribute("adminMember", adminMember);
		int diffDate= DateUtil.diffDate("month", DateUtil.getDate("yyyyMMdd"), adminMember.getConnectDate().replaceAll("-", ""));
		if(adminMember.getAuthType() == "S" && diffDate >= 6){
			throw new CommonException("비밀번호를 변경한지 6개월이 지났습니다."  ,"/admin/changePass.do",CommonException.HISTORY_BACK+1);
		}
		
		return "redirect:/admin/learning/dataList.do";
	}
	
	@RequestMapping("/logout.do")
	public String logout(HttpServletRequest request , Model model) throws Exception
	{
		HttpSession session = (HttpSession) request.getSession();
		session.removeAttribute("adminMember");
		session.invalidate(); 
		return "redirect:http://cs.kumsung.co.kr/logout.do?returnUrl=http://thub.kumsung.co.kr";
	}
	

	@RequestMapping("/changePass.do")
	public String changePass(HttpServletRequest request , Model model) throws Exception
	{
		HttpSession session = (HttpSession) request.getSession();
		Member adminMember = (Member) session.getAttribute("adminMember");
		String userId = Common.getParameter(request, "userId", "");
		String passwd = Common.getParameter(request, "passwd", "");
		String afterChange = Common.getParameter(request, "afterChange", "false");
		
		if(adminMember.getUserId() !=null && afterChange.equals("true")){
			return "redirect:/admin/learning/dataList.do";
		}
		
		//session.removeAttribute("adminMember");
		//session.invalidate();
		
		return "adminPassChange";
	}
	
}
