package kr.co.kumsung.thub.controller.front;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import kr.co.kumsung.thub.domain.Member;
import kr.co.kumsung.thub.exception.CommonException;
import kr.co.kumsung.thub.service.MemberService;
import kr.co.kumsung.thub.util.Common;
import kr.co.kumsung.thub.util.CookieBox;
import kr.co.kumsung.thub.util.Validate;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Component("frontLoginController")
@Controller
public class LoginController {

	@Autowired
	private MemberService memberService;
	private static final Logger logger = Logger.getLogger(LoginController.class);
	@RequestMapping("/loginProc.do")
	public String loginAction(HttpServletRequest request , Model model) throws Exception
	{
		// get parameters
		// session data setting
		HttpSession session = (HttpSession) request.getSession();
		String userId = Common.getParameter(request, "userId", "");
		String passwd = Common.getParameter(request, "passWd", "");
		String returnUrl = Common.getParameter(request, "returnUrl", "/main.do");
		
		//System.out.println("request ::::::::::::   "+request.getParameter("passWd"));
		//System.out.println("userid ::::::::::::: "+userId);
		//System.out.println("passWd ::::::::::::::::: "+passwd);
		//logger.info("userId::"+userId);
		//logger.info("passwd::"+passwd);
		// validation check
		if( Validate.isEmpty(userId)
				|| Validate.isEmpty(passwd))
			throw new CommonException("정상적인 요청이 아닙니다." , CommonException.HISTORY_BACK);
		
		// 회원 로그인을 시킨다.
		Member member = memberService.get(userId);
		if( member == null ){
			session.setAttribute("csID", "N");
			return "redirect:/loginDone.do";
		}
		
		session.setAttribute("csID", userId);

		if( Validate.isEmpty(member.getAuthType()) ){
			member.setAuthType("");
		}
		// 비밀번호를 확인한다.
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("id" , member.getId());
		params.put("userId" , userId);
		params.put("passwd" , passwd);
/*		if( memberService.matchPassword(params) != 1 ){
			//return "redirect:/loginDone.do";
			logger.info("111111111");
			session.setAttribute("csID", "");
			return "redirect:/main.do";
		}
*/
		System.out.println("getAuthType : "+ member.getAuthType());
		if( member.getAuthType().equals("S") || member.getAuthType().equals("N"))
		{
			// 관리자는 무조건 인증된 선생님 권한을 준다.
			member.setTeacher("Y");
			member.setIsFinallyAuth("Y");
		}
		
		// 교사가 아니라면 세션생성을 하지 않는다.
		if( member.getTeacher().equals("N") ) 
			return "redirect:/loginDone.do";

		//System.out.println("ip ::::::::::::::::: "+request.getRemoteAddr());
		session.setAttribute("member", member);
	
		// 회원의 인증 구분을 가지고 온다.
		if( !Validate.isEmpty(member.getAuthType())){
			member.setIp(request.getRemoteAddr());
			logger.info("22222222 getIp "+member.getIp());
			session.setAttribute("adminMember", member);
		}
		logger.info("3333333");
		//return "redirect:https://thub.kumsung.co.kr/main.do";
		
		//회원 로그인정보 누적.
		Map<String,Object> logparams = new HashMap<String,Object>();
		
		logparams.put("userId" , userId);
		
		if(returnUrl.indexOf("localhost") == -1) {
			//서버
			logparams.put("logUrl" , returnUrl);	
		}else {
			//로컬
			returnUrl = returnUrl.replace("https", "http");
			logparams.put("logUrl" , returnUrl);
		}

		//memberService.insertLoginLog(logparams); 통합로그인에 적용해달라 함.
		
		return "redirect:"+returnUrl;
	}
	
	@RequestMapping("/testLoginProc.do")
	public String testloginAction(HttpServletRequest request , Model model) throws Exception
	{
		// get parameters
		String userId = Common.getParameter(request, "userId", "");
		String passwd = Common.getParameter(request, "passWd", "");
		
		userId = "rkddydrn2";
		passwd = "rkddydrn2+";
		
		// validation check
		if( Validate.isEmpty(userId)
				|| Validate.isEmpty(passwd))
			throw new CommonException("정상적인 요청이 아닙니다." , CommonException.HISTORY_BACK);
		
		// 회원 로그인을 시킨다.
		Member member = memberService.get(userId);
		
		if( member == null )
			return "redirect:/loginDone.do";
		
		if( Validate.isEmpty(member.getAuthType()) )
			member.setAuthType("");
		
		// 비밀번호를 확인한다.
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("id" , member.getId());
		params.put("userId" , userId);
		params.put("passwd" , passwd);
		
		if( memberService.matchPassword(params) != 1 )
			return "redirect:/loginDone.do";
		
		if( member.getAuthType().equals("S") || member.getAuthType().equals("N"))
		{
			// 관리자는 무조건 인증된 선생님 권한을 준다.
			member.setTeacher("Y");
			member.setIsFinallyAuth("Y");
		}
		
		// 교사가 아니라면 세션생성을 하지 않는다.
		if( member.getTeacher().equals("N") )
			return "redirect:/loginDone.do"; 
		
		// session data setting
		HttpSession session = (HttpSession) request.getSession();
		session.setAttribute("member", member);
		
		// 회원의 인증 구분을 가지고 온다.
		if( !Validate.isEmpty(member.getAuthType()))
			session.setAttribute("adminMember", member);
		
		return "redirect:/main.do";
	}
	
	/**
	 * 티칭허브 로그아웃
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/logout.do")
	public String logout(HttpServletRequest request , Model model) throws Exception
	{
		HttpSession session = (HttpSession) request.getSession();
		
		session.removeAttribute("member");
		session.removeAttribute("csID");
		session.invalidate();
		
		return "redirect:/loginDone.do";
	}
	
	/**
	 * 티칭허브 로그아웃(SSO용)
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/sso/logout.do")
	public String ssoLogout(HttpServletRequest request , Model model) throws Exception
	{
		HttpSession session = (HttpSession) request.getSession();
		logger.info("sso/logout.do");
		logger.info("sessionid1 : "+session.getId());
		Member member = (Member)session.getAttribute("member");
		logger.info("getAuthId : "+member.getAuthId());
		logger.info("getId : "+member.getId());
		logger.info("getIp : "+member.getIp());
		session.removeAttribute("member");
		session.invalidate();
		logger.info("sessionid2 : "+session.getId());
		return "front/thub/loginDone";
	}
	
	/**
	 * 티칭백과 로그인 처리
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/web/loginProc.do")
	public String dicLoginAction(HttpServletRequest request , Model model) throws Exception
	{
		// get parameters
		String userId = Common.getParameter(request, "userId", "");
		String passwd = Common.getParameter(request, "passWd", "");
		
		// validation check
		if( Validate.isEmpty(userId)
				|| Validate.isEmpty(passwd))
			throw new CommonException("정상적인 요청이 아닙니다." , CommonException.HISTORY_BACK);
		
		// 회원 로그인을 시킨다.
		Member member = memberService.get(userId);
		
		if( member == null ) return "redirect:/loginFail.do";
		
		// 비밀번호를 확인한다.
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("id" , member.getId());
		params.put("userId" , userId);
		params.put("passwd" , passwd);
		
		//if( memberService.matchPassword(params) != 1 )
			//return "redirect:/loginDone.do";
		
		// session data setting
		HttpSession session = (HttpSession) request.getSession();
		session.setAttribute("dicMember", member);
		
		return "redirect:/main.do";
	}
	
	/**
	 * 티칭백과 로그아웃
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/web/logout.do")
	public String webLogout(HttpServletRequest request , Model model) throws Exception
	{
		HttpSession session = (HttpSession) request.getSession();
		
		session.removeAttribute("dicMember");
		session.invalidate();
		
		return "redirect:/web/main.do";
	}
	
	/**
	 * 티칭백과 로그아웃(SSO 용)
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/web/sso/logout.do")
	public String webSsoLogout(HttpServletRequest request , Model model) throws Exception
	{
		HttpSession session = (HttpSession) request.getSession();
		
		session.removeAttribute("dicMember");
		session.invalidate();
		
		return "front/thub/loginDone";
	}
	
	/**
	 * 모바일 로그인 처리
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	@RequestMapping(value="/mobile/loginProc.do" , method=RequestMethod.POST)
	public String mobileLogin(HttpServletRequest request , Model model) throws Exception
	{
		// get parameters
		String userId = Common.getParameter(request, "userId", "");
		String passwd = Common.getParameter(request, "passWd", "");
		String saveId = Common.getParameter(request, "saveId", "");
		String returnPage = Common.getParameter(request, "returnPage", "");
		
		// validation check
		if( Validate.isEmpty(userId)
				|| Validate.isEmpty(passwd))
			throw new CommonException("정상적인 요청이 아닙니다." , CommonException.HISTORY_BACK);
		
		// 회원 로그인을 시킨다.
		Member member = memberService.get(userId);
		
		if( member == null )
			throw new CommonException("아이디가 존재하지 않습니다." , CommonException.HISTORY_BACK);
		
		// save id
		CookieBox cookieBox = new CookieBox(request);
		
		if( saveId.equals("Y") )
			cookieBox.createCookie("userId", userId);
		else
			cookieBox.createCookie("userId", "");
		
		// 비밀번호를 확인한다.
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("id" , member.getId());
		params.put("userId" , userId);
		params.put("passwd" , passwd);
		
		if( memberService.matchPassword(params) != 1 )
			throw new CommonException("비밀번호를 확인해주십시오." , CommonException.HISTORY_BACK);
		
		// session data setting
		HttpSession session = (HttpSession) request.getSession();
		session.setAttribute("mobileMember", member);
		
		if( Validate.isEmpty(returnPage) )
			return "redirect:/mobile/main.do";
		else
			return "redirect:" + returnPage;
	}
	
	/**
	 * 모바일 로그아웃
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/mobile/logout.do")
	public String mobileLogout(HttpServletRequest request , Model model) throws Exception
	{
		HttpSession session = (HttpSession) request.getSession();
		
		session.removeAttribute("mobileMember");
		session.invalidate();
		
		return "redirect:/mobile/main.do";
	}
	
	/**
	 * 로그인 상태인지 체크한다.
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/mobile/loginCheck.do")
	public @ResponseBody Map<String,Object> mobileLoginCheck(HttpServletRequest request , Model model) throws Exception
	{
		Map<String,Object> result = new HashMap<String,Object>();
		
		// get session
		HttpSession session = (HttpSession) request.getSession();
		Member member = (Member) session.getAttribute("mobileMember");
		
		if( member == null )
		{
			result.put("result" , "FAILURE");
			result.put("msg" , "로그인 상태가 아닙니다.");
			return result;
		}
		
		// assign
		result.put("result" , "SUCCESS");
		
		return result;
	}
	
	/**
	 * 티칭허브 로그인 Done
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/loginDone.do")
	public String loginDone(HttpServletRequest request , Model model) throws Exception
	{	
		return "front/thub/loginDone";
	}
	@RequestMapping("/loginFail.do")
	public String loginFail(HttpServletRequest request , Model model) throws Exception
	{	
		return "front/thub/loginFail";
	}	
	@RequestMapping("/loginChk.do")
	public String loginChk(HttpServletRequest request , Model model) throws Exception
	{	
		String userId = Common.getParameter(request, "userId", "");
		Member member = memberService.get(userId);
		String teacher = "N";
		if(member!=null) teacher = "Y";
		model.addAttribute("teacher", teacher);
				
		return "front/thub/loginChk";	

		
		
	}	
	
}
