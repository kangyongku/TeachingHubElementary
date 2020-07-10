package kr.co.kumsung.thub.controller.front.thub;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import kr.co.kumsung.thub.domain.Member;
import kr.co.kumsung.thub.exception.CommonException;
import kr.co.kumsung.thub.service.MemberService;
import kr.co.kumsung.thub.service.EduEventService;
import kr.co.kumsung.thub.service.impl.MemberServiceImpl;
import kr.co.kumsung.thub.util.Common;
import kr.co.kumsung.thub.util.Validate;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/popup")
public class VthinkingController {
	
	@Autowired
	private EduEventService edueventService;

	@Autowired
	private MemberService memberService;
	
	@RequestMapping("/vthinking.do")
	public String test_vbanking(HttpServletRequest request , Model model) throws Exception
	{
		
		String edu_gubun = Common.getParameter(request, "edu_gubun", "edu_gubun");
		String edu_chk = Common.getParameter(request, "edu_gubun", "edu_gubun");
		String userid = Common.getParameter(request, "userid", "userid");
		
		String sLecture_day = "";
		String teacher_gubun = "";
		String total_cnt = "";
		String teacher_chk = "";
		String user_id = "";
		
		Map<String,Object> params = new HashMap<String,Object>();
		
		HttpSession session = (HttpSession) request.getSession();
		Member member = (Member) session.getAttribute("member");

		//
		
		//if( user_id == null ) {
		//	throw new CommonException("로그인 후 신청하시기 바랍니다.",
		//			CommonException.HISTORY_BACK);
		//}
		
		if(member != null) {
			  
			teacher_chk = member.getIsFinallyAuth();
			
			 	if(teacher_chk == "N") {
						teacher_gubun = "false";
					}else{
						teacher_gubun = "true";
					}
			 	
		}
		
		if(edu_chk.equals("1")) {
			edu_chk = "2017819";
		}else{
			edu_chk = "2017902";
		}
		
		params.put("edu_day" , edu_chk);
		int totalCount = edueventService.getTotalPerson(params);
		
		if(totalCount == 50) {
			total_cnt = "0";
		}else{
			total_cnt = "1";
		}
		
		if(total_cnt.equals("1")) {
		 
		if(member != null) {	
			params.put("edu_day" , edu_chk);
			params.put("user_id" , member.getUserId());
		
			totalCount = edueventService.getTotalPerson(params);
		   }
		 }
		
		if(totalCount >= 1) {
			total_cnt = "2";
		}
		  
		
		
		if(edu_gubun.equals("1")) {
			sLecture_day = "2017년 8월 19일(토)";
		}else{
			sLecture_day = "2017년 9월 2일(토)";
		}
		
		model.addAttribute("sLecture_day", sLecture_day);
		model.addAttribute("teacher_gubun", teacher_gubun);
		model.addAttribute("totalCount", total_cnt);
		
        return "/popup/vthinking";
	}
	
	@RequestMapping(value="/eduEventAction.do",  method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> eduEventAction(HttpServletRequest request , Model model) throws Exception
	{

		HttpSession session = (HttpSession) request.getSession();
		Member member = (Member) session.getAttribute("member");
		
		String user_id = member.getUserId();
		String edu_day = Common.getParameter(request, "edu_day", "edu_day");
		String school = Common.getParameter(request, "school", "school");
		String tel = Common.getParameter(request, "vtel", "vtel");

		//String person_agreement = Common.getParameter(request, "person_agreement", "person_agreement");
		String person_agreement = "Y";
				
		Map<String,Object> params = new HashMap<String,Object>();
		
		if(edu_day.equals("2017년 8월 19일(토)")) {
			edu_day = "2017819";
		} else {
			edu_day = "2017902";
		}
		
		params.put("user_id" , user_id);
		params.put("edu_day" , edu_day);
		params.put("person_agreement" , person_agreement);
		params.put("school" , school);
		params.put("tel" , tel);
		
		//Map<String,Object> params = new HashMap<String,Object>();
		
		//params.put("eventId" , eventId);
				
		//int totalCount = edueventService.getTotalPerson(params);
				
	//	if(totalCount > 36) {
		//	result.put("result", "Y");
		//} else {
		//	result.put("result", "N");
		//}

		Map<String, Object> result = new HashMap<String, Object>();
		edueventService.insertEduevent(params);
		
		result.put("result", "SUCCESS");
		
		return result;
	}
	
	@RequestMapping("/fileTest.do")
	public String filetest(HttpServletRequest request , Model model) throws Exception
	{
	
		///String filedown = null;
		
		return "/popup/filedown";
	}
}
