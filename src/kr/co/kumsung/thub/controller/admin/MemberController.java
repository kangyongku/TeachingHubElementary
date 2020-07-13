package kr.co.kumsung.thub.controller.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import kr.co.kumsung.thub.domain.BoardConfig;
import kr.co.kumsung.thub.domain.Category;
import kr.co.kumsung.thub.domain.Member;
import kr.co.kumsung.thub.exception.CommonException;
import kr.co.kumsung.thub.service.BoardService;
import kr.co.kumsung.thub.service.CategoryService;
import kr.co.kumsung.thub.service.MemberService;
import kr.co.kumsung.thub.setting.Constants;
import kr.co.kumsung.thub.util.Common;
import kr.co.kumsung.thub.util.Pagination;
import kr.co.kumsung.thub.util.Validate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 회원 목록
 * @author jhg
 * @date 0819~
 */
@Component("adminMemberController")
@Controller
@RequestMapping("/admin/member")
public class MemberController {
	
	private static final int PAGE_SIZE = 10;
	private static final int BLOCK_SIZE = 10;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private BoardService boardService;
	
	/**
	 * 회원목록 리스트
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/memberList.do")
	public String memberList(HttpServletRequest request , Model model) throws Exception
	{
		// get parameters
		int pg = Common.getParameter(request, "pg", 1);
		int skip = (pg - 1) * PAGE_SIZE;
		Member authMap = null;
		
		HttpSession session = (HttpSession) request.getSession();
		Member adminSession = (Member) session.getAttribute("adminMember");
		authMap = memberService.getMemberInfo(adminSession.getUserId());
	
		
/*		if(authMap.getIp() == null){
			throw new CommonException("IP가 등록되지 않았습니다." , CommonException.HISTORY_BACK);
		}else if(!authMap.getIp().equals(adminSession.getIp()) && adminSession.getAuthType().equals("S")){
			throw new CommonException("지정된 IP 주소가 아닙니다." , CommonException.HISTORY_BACK);			
		}
		*/
		//TODO : 인증방법 미구현
		String findIsFinallyAuth = Common.getParameter(request, "findIsFinallyAuth", "");
		String findAuthType = Common.getParameter(request, "findAuthType", "");
		String findMethod = Common.getParameter(request, "findMethod", "");
		String findStr = Common.getParameter(request, "findStr", "");
		
		/*아이디 jevalin 를 검색하면 xss 필터에 의해 jlin 짤림현상 남 그래서 비교함*/
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("skip", skip);
		params.put("pageSize", PAGE_SIZE);
		params.put("findIsFinallyAuth", findIsFinallyAuth);
		params.put("findAuthType", findAuthType);
		params.put("findMethod", findMethod);
		params.put("findStr", findStr);
		
		int totalMember = memberService.getMemberTotalList(params);
		
		// set pagination
		Pagination pagination = new Pagination(pg, PAGE_SIZE, BLOCK_SIZE, totalMember);
		pagination.setDelimiter("");
		pagination.setPrevLink("Prev");
		pagination.setNextLink("Next");
		
		int memberNum = totalMember - skip;
		
		List<Member> memberList = memberService.getMemberList(params);
		
		
		model.addAttribute("pg", pg);
		model.addAttribute("findMethod", findMethod);
		model.addAttribute("findStr", findStr);
		model.addAttribute("findIsFinallyAuth", findIsFinallyAuth);
		model.addAttribute("findAuthType", findAuthType);
		model.addAttribute("memberNum", memberNum);
		model.addAttribute("memberList", memberList);
		model.addAttribute("paging", pagination.print());
		model.addAttribute("pathTitle", "회원목록 ");
		model.addAttribute("path", "회원목록 ");
		
		return "admin/member/memberList";
	}
	
	
	/**
	 * 회원목록 등록/수정 폼
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/memberForm.do")
	public String memberForm(HttpServletRequest request , Model model) throws Exception
	{	
		// get parfameters
		int pg = Common.getParameter(request, "pg", 1);		
		String mode = Common.getParameter(request, "mode", "add");
		int id = Common.getParameter(request, "id", 0);
		String findIsFinallyAuth = Common.getParameter(request, "findIsFinallyAuth", "");
		String findAuthType = Common.getParameter(request, "findAuthType", "");
		String findMethod = Common.getParameter(request, "findMethod", "");
		String findStr = Common.getParameter(request, "findStr", "");
		
		HttpSession session = (HttpSession) request.getSession();
		Member adminMember = (Member) session.getAttribute("adminMember");
		
		Member member = new Member();
		Map<String, Object> params = new HashMap<String, Object>();
		
		if(mode.equals("modify")){
			member = memberService.getMemberDetail(id);
			
			params.put("siteCode", "4");	//테이블 SITE참조 4:티칭허브
			params.put("userId", adminMember.getUserId());
			params.put("name", adminMember.getName());
			params.put("ip", (String) request.getRemoteAddr());
			params.put("actionMode", "serch");
			params.put("targetId", member.getUserId());
			params.put("targetName", member.getName());
			memberService.insertAccessLog(params);
		}
		
		model.addAttribute("id", id);
		model.addAttribute("member", member);
		model.addAttribute("mode", mode);
		
		model.addAttribute("pathTitle", "회원목록 ");
		model.addAttribute("path", "회원목록   등록/수정");
		model.addAttribute("pg", pg);
		
		model.addAttribute("findMethod", findMethod);
		model.addAttribute("findStr", findStr);
		model.addAttribute("findIsFinallyAuth", findIsFinallyAuth);
		model.addAttribute("findAuthType", findAuthType);
		
		return "admin/member/memberForm";
	}
	
	/**
	 * 회원목록 등록
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="memberAction.do" , method=RequestMethod.POST)
	public String memberAction(HttpServletRequest request, Model model) throws Exception
	{
		String mode = Common.getParameter(request, "mode", "add");
		
		int id = Common.getParameter(request, "id", 0); 
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		if(mode.equals("add")){
			//memberService.insertMember(params);
		}else if(mode.equals("modify")){
			//memberService.updateMember(params);
		}else if(mode.equals("delete")){
			//memberService.deleteMember(id);
		}
		
		return "redirect:memberList.do";
	}
	
	/**
	 * 관리자 메뉴권한 관리 리스트
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/authList.do")
	public String authList(HttpServletRequest request , Model model) throws Exception
	{
		// get parameters
		int pg = Common.getParameter(request, "pg", 1);
		int skip = (pg - 1) * PAGE_SIZE;
		
		String findMethod = Common.getParameter(request, "findMethod", "");
		String findStr = Common.getParameter(request, "findStr", "");
		String findAuthType = Common.getParameter(request, "findAuthType", "");
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("findAuthType", findAuthType);
		params.put("findMethod", findMethod);
		params.put("findStr", findStr);
		
		int totalAuth = memberService.getAuthTotalList(params);
		int authNum = totalAuth - skip;
		
		//set Paging
		Pagination pagination = new Pagination(pg, PAGE_SIZE, BLOCK_SIZE, totalAuth);
		pagination.setDelimiter("");
		pagination.setPrevLink("Prev");
		pagination.setNextLink("Next");

		params.put("skip", skip);
		params.put("pageSize", PAGE_SIZE);
		
		List<Member> authList = memberService.getAuthList(params);
		
		// assign
		model.addAttribute("pathTitle", "회원관리 ");
		model.addAttribute("path", "회원관리 > 관리자 권한관리");
		
		model.addAttribute("pg", pg);
		model.addAttribute("authList", authList);
		model.addAttribute("authNum", authNum);
		model.addAttribute("paging", pagination.print());
		model.addAttribute("findAuthType", findAuthType);
		model.addAttribute("findMethod", findMethod);
		model.addAttribute("findStr", findStr);
		
		return "admin/member/authList";
	}
	
	/**
	 * 관리자 메뉴권한 관리 폼
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/authForm.do")
	public String authForm(HttpServletRequest request , Model model) throws Exception
	{
		// get parameters
		int pg = Common.getParameter(request, "pg", 1);
		String mode = Common.getParameter(request, "mode", "add");
		int authId = Common.getParameter(request, "authId", 0);
		
		// get category data
		List<Category> learningCategories = (List<Category>) categoryService.getSecondaryAllList(Constants.LEARNING_CATEGORY);
		List<Category> smartCategories = (List<Category>) categoryService.getSecondaryAllList(Constants.SMART_CATEGORY);
		List<BoardConfig> boards = (List<BoardConfig>) boardService.getBoardList(new HashMap<String,Object>());
		
		Member authMap = null;
		
		if( mode.equals("add") )
			authMap = new Member();
		else if( mode.equals("modify") ){
			if( authId == 0)
				throw new CommonException("정상적인 접근이 아닙니다." , CommonException.HISTORY_BACK);
			authMap = memberService.getAuthDetail(authId);
		}
		// assign
		model.addAttribute("pathTitle", "회원관리 ");
		model.addAttribute("path", "회원관리 > 관리자 권한관리");
		
		model.addAttribute("pg", pg);
		model.addAttribute("mode", mode);
		model.addAttribute("authId",authId);
		
		model.addAttribute("authMap", authMap);
		model.addAttribute("learningCategories", learningCategories);
		model.addAttribute("smartCategories", smartCategories);
		model.addAttribute("boards", boards);
		return "admin/member/authForm";
	}
	
	/**
	 * 관리자 메뉴 권한 관리 등록
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="authAction.do" , method=RequestMethod.POST)
	public String authAction(HttpServletRequest request , Model model) throws Exception
	{
		// get session
		HttpSession session = (HttpSession) request.getSession();
		Member adminMember = (Member) session.getAttribute("adminMember");
		String authGranter = adminMember.getName();
		
		// get parameters
		int pg = Common.getParameter(request, "pg", 1);
		int authId = Common.getParameter(request, "authId", 0);
		String userId = Common.getParameter(request, "userId", "");
		String authType = Common.getParameter(request, "authType", "N");
		String mode = Common.getParameter(request, "mode", "add");
		
		// check validation
		if( userId.equals("") || !(authType.equals("N") || authType.equals("S")))
			throw new CommonException("정상적인 접근이 아닙니다." , CommonException.HISTORY_BACK);

		String[] smartAuth = Common.getParameters(request, "smartAuth");
		String[] accessAuth = Common.getParameters(request, "accessAuth");
		String[] learningAuth = Common.getParameters(request, "learningAuth");
		String[] boardAuth = Common.getParameters(request, "boardAuth");
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("smartAuth", smartAuth);
		params.put("accessAuth", accessAuth);
		params.put("learningAuth", learningAuth);
		params.put("boardAuth", boardAuth);
		params.put("authId", authId);
		params.put("userId", userId);
		params.put("authGranter", authGranter);
		params.put("authType", authType);
		if( mode.equals("add") )
			memberService.insertAuth(params);
		else if( mode.equals("modify") )
			memberService.updateAuth(params);
		else if( mode.equals("delete"))
			memberService.deleteAuth(authId);
		// assign
		model.addAttribute("pathTitle", "회원관리 ");
		model.addAttribute("path", "회원관리 > 관리자 권한관리");
		
		model.addAttribute("pg", pg);

		
		return "redirect:authList.do";
	}
	
	/**
	 * 회원을 검색한다.
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/findUserInfo.do" , method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> findUserInfo(HttpServletRequest request , Model model) throws Exception
	{
		Map<String,Object> result = new HashMap<String,Object>();
		
		// get parameters
		String userId = Common.getParameter(request, "userId", "");
		int dupCnt = memberService.getAuthUserDup(userId);
		
		// admin테이블 중복 체크
		if(  dupCnt > 0 )
		{
			result.put("result" , "FAILURE");
			result.put("msg" , "이미 등록한 계정이 있습니다.");
			return result;
		}
		
		// validation check
		if( Validate.isEmpty(userId) )
		{
			result.put("result" , "FAILURE");
			result.put("msg" , "정상적인 요청이 아닙니다.");
			return result;
		}
		
		// find user data
		Member member = memberService.getFindUserInfo(userId);
		
		if( member == null )
		{
			result.put("result" , "FAILURE");
			result.put("msg" , "회원정보가 없습니다.");
			return result;
		}
		
		// set result data
		result.put("result" , "SUCCESS");
		result.put("member" , member);
				
		return result;
	}

	/**
	 * 회원 정보 수정
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateMemberInfo.do")
	public @ResponseBody Map<String,Object> updateMemberInfo(HttpServletRequest request , Model model) throws Exception
	{
		Map<String,Object> result = new HashMap<String,Object>();
		
		// check session
		HttpSession session = (HttpSession) request.getSession();
		Member adminMember = (Member) session.getAttribute("adminMember");
		
		if( adminMember == null )
		{
			result.put("result" , "FAILURE");
			result.put("resultType" , "LOGIN");
			result.put("msg" , "로그인 하여주십시오.");
			return result;
		}
		
		// get paremeters
		int id = Common.getParameter(request, "id", 0);
		String tel1 = Common.getParameter(request, "tel1", "");
		String tel2 = Common.getParameter(request, "tel2", "");
		String tel3 = Common.getParameter(request, "tel3", "");
		String hp1 = Common.getParameter(request, "hp1", "");
		String hp2 = Common.getParameter(request, "hp2", "");
		String hp3 = Common.getParameter(request, "hp3", "");
		String isFinallyAuth = Common.getParameter(request, "isFinallyAuth", "");
		String authType = Common.getParameter(request, "authType", "");
		String authTypeEtc = Common.getParameter(request, "authTypeEtc", "");
		String userId = Common.getParameter(request, "userId", "");
		String name = Common.getParameter(request, "name", "");
		
		// validate
		if( id == 0
				|| Validate.isEmpty(isFinallyAuth) 
				|| Validate.isEmpty(authType))
		{
			result.put("result" , "FAILURE");
			result.put("resultType" , "PARAMETER");
			result.put("msg" , "파라미터 에러");
			return result;
		}
		
		// make query parameters
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("id" , id);
		params.put("tel1" , tel1);
		params.put("tel2" , tel2);
		params.put("tel3" , tel3);
		params.put("hp1" , hp1);
		params.put("hp2" , hp2);
		params.put("hp3" , hp3);
		params.put("isFinallyAuth" , isFinallyAuth);
		params.put("authType" , authType);
		params.put("authTypeEtc" , authTypeEtc);
		
		memberService.updateMemberInfo(params);
		
		Map<String,Object> params2 = new HashMap<String,Object>();

		params2.put("siteCode", "4");	//테이블 SITE참조 4:티칭허브
		params2.put("userId", adminMember.getUserId());
		params2.put("name", adminMember.getName());
		params2.put("ip", (String) request.getRemoteAddr());
		params2.put("actionMode", "modif");
		params2.put("targetId", userId);
		params2.put("targetName", name);
		memberService.insertAccessLog(params2);

		
		
		// assign
		result.put("result" , "SUCCESS");
		
		return result;
	}
	
	
	/**
	 * 회원을 검색한다.
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/findAccessUserInfo.do" , method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> findAccessUserInfo(HttpServletRequest request , Model model) throws Exception
	{
		Map<String,Object> result = new HashMap<String,Object>();
		
		// get parameters
		String userId = Common.getParameter(request, "userId", "");
		
		// find user data
		
		Member accessMember = memberService.getFindAccessUserInfo(userId);
		
		if( accessMember == null )
		{
			result.put("result" , "FAILURE");
			result.put("msg" , "회원정보가 없습니다.");
			return result;
		}
		
		// set result data
		result.put("result" , "SUCCESS");
		result.put("accessMember" , accessMember);
		return result;
	}
	
	/**
	 * 관리자 접근권한 관리 리스트
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/accessList.do")
	public String accessList(HttpServletRequest request , Model model) throws Exception
	{
		// get parameters
		int pg = Common.getParameter(request, "pg", 1);
		int skip = (pg - 1) * PAGE_SIZE;
		
		String findMethod = Common.getParameter(request, "findMethod", "");
		String findStr = Common.getParameter(request, "findStr", "");
		String findAuthType = Common.getParameter(request, "findAuthType", "");
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("findAuthType", findAuthType);
		params.put("findMethod", findMethod);
		params.put("findStr", findStr);
		
		int totalAccess = memberService.getAccessTotalList(params);
		int authNum = totalAccess - skip;
		
		//set Paging
		Pagination pagination = new Pagination(pg, PAGE_SIZE, BLOCK_SIZE, totalAccess);
		pagination.setDelimiter("");
		pagination.setPrevLink("Prev");
		pagination.setNextLink("Next");

		params.put("skip", skip);
		params.put("pageSize", PAGE_SIZE);
		
		List<Member> AccessList = memberService.getAccessList(params);
		
		// assign
		model.addAttribute("pathTitle", "회원관리 ");
		model.addAttribute("path", "회원관리 > 접근 권한관리");
		
		model.addAttribute("pg", pg);
		model.addAttribute("AccessList", AccessList);
		model.addAttribute("authNum", authNum);
		model.addAttribute("paging", pagination.print());
		model.addAttribute("findAuthType", findAuthType);
		model.addAttribute("findMethod", findMethod);
		model.addAttribute("findStr", findStr);
		
		return "admin/member/accessList";
	}
	
	/**
	 * 관리자 접근권한 관리 폼
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/accessForm.do")
	public String accessForm(HttpServletRequest request , Model model) throws Exception
	{
		// get parameters
		int pg = Common.getParameter(request, "pg", 1);
		int skip = (pg - 1) * PAGE_SIZE;
		String userId = Common.getParameter(request, "userId", "");
		
		List<Member> AccessList = new ArrayList<Member>();
		Map<String, Object> params = new HashMap<String, Object>();
		
		if(userId !=null){
			Member accessMember = memberService.getFindAccessUserInfo(userId);
			model.addAttribute("accessMember", accessMember);
		}
		
		/*if( mode.equals("add") ){
			authMap = new Member();
			params.put("skip", 1);
			params.put("pageSize", PAGE_SIZE);
			AccessList = memberService.getAccessList(params);
			
		}else if( mode.equals("modify") ){
			if( authId == 0)
				throw new CommonException("정상적인 접근이 아닙니다." , CommonException.HISTORY_BACK);
			authMap = memberService.getAuthDetail(authId);
		}*/
		
		params.put("skip", skip);
		params.put("pageSize", PAGE_SIZE);
		AccessList = memberService.getAccessList(params);
		
		
		model.addAttribute("pathTitle", "회원관리 ");
		model.addAttribute("path", "회원관리 > 접근 권한관리");
		model.addAttribute("pg", pg);
		model.addAttribute("AccessList", AccessList);
		
		return "admin/member/accessForm";
	}
	
	/**
	 * 접근권한 관리 IP 등록(update)
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/accessAction.do" , method=RequestMethod.POST)
	public String accessAction(HttpServletRequest request, Model model) throws Exception
	{
		String mode = Common.getParameter(request, "mode", "modify");
		String userId = Common.getParameter(request, "userId", ""); 
		String ip = Common.getParameter(request, "ip", "");
		int action = 0;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("ip", ip);
		
		if(mode.equals("add")){
			//memberService.insertMember(params);
		}else if(mode.equals("modify")){
			action = memberService.updateAccessMember(params);
		}else if(mode.equals("delete")){
			action = memberService.deleteAccessMember(params);
		}
		
		if(action > 0){
			return "redirect:accessList.do";	
		}else if(action < 0 && mode.equals("modify")){
			throw new CommonException("저장이 실패 했습니다." , CommonException.HISTORY_BACK);
		}else if(action < 0 && mode.equals("delete")){
			throw new CommonException("삭제를 실패 했습니다." , CommonException.HISTORY_BACK);
		}
		return "redirect:accessList.do";
	}
	
	/**
	 * 관리자 접근권한 관리 리스트
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/memberAccessList.do")
	public String memberAccessList(HttpServletRequest request , Model model) throws Exception
	{
		// get parameters
		int pg = Common.getParameter(request, "pg", 1);
		int skip = (pg - 1) * PAGE_SIZE;
		
		String findMethod = Common.getParameter(request, "findMethod", "");
		String findStr = Common.getParameter(request, "findStr", "");
		String findAction = Common.getParameter(request, "findAction", "");
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("findAction", findAction);
		params.put("findMethod", findMethod);
		params.put("findStr", findStr);
		
		int totalMemberAccess = memberService.getMemberAccessTotalList(params);
		int accessNum = totalMemberAccess - skip;
		
		//set Paging
		Pagination pagination = new Pagination(pg, PAGE_SIZE, BLOCK_SIZE, totalMemberAccess);
		pagination.setDelimiter("");
		pagination.setPrevLink("Prev");
		pagination.setNextLink("Next");

		params.put("skip", skip);
		params.put("pageSize", PAGE_SIZE);
		
		List<Member> memberAccessList = memberService.getMemberAccessList(params);
		
		// assign
		model.addAttribute("pathTitle", "회원관리 ");
		model.addAttribute("path", "회원관리 > 회원정보 접속기록 관리");
		
		model.addAttribute("pg", pg);
		model.addAttribute("memberAccessList", memberAccessList);
		model.addAttribute("accessNum", accessNum);
		model.addAttribute("paging", pagination.print());
		model.addAttribute("findAction", findAction);
		model.addAttribute("findMethod", findMethod);
		model.addAttribute("findStr", findStr);
		
		return "admin/member/memberAccessList";
	}
}
