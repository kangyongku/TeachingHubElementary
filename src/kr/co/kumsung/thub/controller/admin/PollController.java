package kr.co.kumsung.thub.controller.admin;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import kr.co.kumsung.thub.controller.front.thub.OpenController;
import kr.co.kumsung.thub.domain.Member;
import kr.co.kumsung.thub.domain.Poll;
import kr.co.kumsung.thub.domain.PollEntry;
import kr.co.kumsung.thub.domain.PollEntryItem;
import kr.co.kumsung.thub.domain.PollItem;
import kr.co.kumsung.thub.domain.PollResponse;
import kr.co.kumsung.thub.exception.CommonException;
import kr.co.kumsung.thub.service.PollService;
import kr.co.kumsung.thub.util.Common;
import kr.co.kumsung.thub.util.Pagination;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 설문 조사 관리
 * @author jhg
 * @date 0717~0723
 */
@Component("adminPollController")
@Controller
@RequestMapping("/admin/poll")
public class PollController {
	private static final Logger logger = LoggerFactory.getLogger(PollController.class);
	private static final int PAGE_SIZE = 10;
	private static final int BLOCK_SIZE = 10;
	
	@Autowired
	private PollService pollService;
	
	/**
	 * 설문조사 리스트
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/pollList.do")
	public String pollList(HttpServletRequest request , Model model) throws Exception
	{
		// get parameters
		int pg = Common.getParameter(request, "pg", 1);
		int skip = (pg - 1) * PAGE_SIZE;
		String findStr = Common.getParameter(request, "findStr", "");
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("findStr", findStr);
		params.put("skip", skip);
		params.put("pageSize", PAGE_SIZE);
		
		int totalPoll = pollService.getTotalPoll(params);
		int pollNum = totalPoll - skip;
		
		//set Paging
		Pagination pagination = new Pagination(pg, PAGE_SIZE, BLOCK_SIZE, totalPoll);
		pagination.setDelimiter("");
		pagination.setPrevLink("Prev");
		pagination.setNextLink("Next");
		
		List<Poll> list = pollService.getPollList(params);
		
		model.addAttribute("pg", pg);
		model.addAttribute("skip", skip);
		model.addAttribute("findStr", findStr);
		model.addAttribute("pollNum", pollNum);
		model.addAttribute("list" , list);
		model.addAttribute("totalPoll", totalPoll);
		
		// assign
		model.addAttribute("pathTitle", "설문조사 관리");
		model.addAttribute("path", "설문조사 관리 / 리스트");
		model.addAttribute("paging", pagination.print());
		
		return "admin/poll/pollList";
	}
	
	/**
	 * 설문조사 문제 등록
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/pollForm.do")
	public String pollForm(HttpServletRequest request , Model model) throws Exception
	{
		
		// get parameters
		int pollId = Common.getParameter(request, "pollId", 0);
		int pg = Common.getParameter(request, "pg", 1);
		String mode = Common.getParameter(request, "mode", "add");
		String findStr = Common.getParameter(request, "findStr", "");
		
		/*
		if( pollId == 0 )
			throw new CommonException("정상적인 접근이 아닙니다." , CommonException.HISTORY_BACK);
		 check validation
		 */
		Poll poll = null;
		List<PollEntryItem> pollEntryItems = new ArrayList<PollEntryItem>();
		
		if( mode.equals("add") )
		{
			// 신규로 등록할 경우 article object를 기본 셋팅한다.
			poll = new Poll();
		}
		else if( mode.equals("modify") )
		{
			poll = pollService.getPollDetail(pollId);
			pollEntryItems = pollService.getPollEntryItemList(pollId);
		}
		
		model.addAttribute("pathTitle", "설문조사 관리");
		model.addAttribute("path", "설문조사 관리 / 등록 수정");
		model.addAttribute("poll", poll);
		model.addAttribute("mode", mode);
		model.addAttribute("findStr", findStr);
		model.addAttribute("pg", pg);
		model.addAttribute("pollId", pollId);
		model.addAttribute("pollEntryItems", pollEntryItems);
		return "admin/poll/pollForm";
	}
	
	/**
	 * 설문조사 등록
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="pollAction.do" , method=RequestMethod.POST)
	public String pollAction(HttpServletRequest request , Model model) throws Exception
	{
		HttpSession session = (HttpSession) request.getSession();
		Member adminMember = (Member) session.getAttribute("adminMember");
		
		// get parameters
		int pg = Common.getParameter(request, "pg", 1);		
		String mode = Common.getParameter(request, "mode", "add");
		String findStr = Common.getParameter(request, "findStr", "");
		String regId = adminMember.getUserId();
		
		String subject = Common.getParameter(request, "subject", "");
		String target = Common.getParameter(request, "target", "");
		String startDate = Common.getParameter(request, "startDate", "");
		String endDate = Common.getParameter(request, "endDate", "");
		String isUse = Common.getParameter(request, "isUse", "");
		String isResult = Common.getParameter(request, "isResult", "");
		int pollId = Common.getParameter(request, "pollId", 0);
		
		// 설정에 따른 validation check
		if( !mode.equals("delete") )
		{
			if( subject.equals("") ) throw new CommonException("제목을 입력하여주십시오." , CommonException.HISTORY_BACK);
			if( target.equals("") ) throw new CommonException("권한을 설정하여주십시오." , CommonException.HISTORY_BACK);
			if( startDate.equals("") || endDate.equals("") ) throw new CommonException("날짜를 입력하여주십시오.." , CommonException.HISTORY_BACK);
		}
		
		Map<String,Object> params = new HashMap<String,Object>();
		
		// set parameter
		params.put("subject" , subject);
		params.put("target" , target);
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		params.put("pollId", pollId);
		params.put("isUse", isUse);
		params.put("isResult", isResult);
		params.put("regId", regId);
		
		String returnUrl = "";
		if( mode.equals("add") )
		{
			pollService.insertPoll(params);
			returnUrl = String.format("redirect:pollList.do?findStr=%s", findStr);
		}
		else if( mode.equals("modify") )
		{
			pollService.updatePoll(params);
			returnUrl = String.format("redirect:pollList.do?findStr=%s", findStr);
		}
		else if(mode.equals("delete")){
			pollService.updatePollEntryReset(pollId);
			pollService.deletePoll(params);
			returnUrl = String.format("redirect:pollList.do?findStr=%s", findStr);
		}
		return returnUrl;
		
	}
	/**
	 * 설문 조사 문항 리스트
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/pollEntryList.do")
	public String pollItemList(HttpServletRequest request, Model model) throws Exception{
		String returnURL = "";
		
		// get parameters
		int pg = Common.getParameter(request, "pg", 1);
		int skip = (pg - 1) * PAGE_SIZE;
		String findStr = Common.getParameter(request, "findStr", "");
		
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("findStr", findStr);
		params.put("skip", skip);
		params.put("pageSize", PAGE_SIZE);
		
		int totalPoll = pollService.getTotalPollEntry(params);
		int pollNum = totalPoll - skip;
		
		//set Paging
		Pagination pagination = new Pagination(pg, PAGE_SIZE, BLOCK_SIZE, totalPoll);
		pagination.setDelimiter("");
		pagination.setPrevLink("Prev");
		pagination.setNextLink("Next");
		
		List<PollEntry> lists = pollService.getPollEntryList(params);
		
		model.addAttribute("pathTitle", "설문 문항 관리");
		model.addAttribute("path", "설문 문항 관리 / 리스트");
		
		model.addAttribute("pg", pg);
		model.addAttribute("skip", skip);
		model.addAttribute("findStr", findStr);
		model.addAttribute("lists", lists);
		model.addAttribute("pollNum", pollNum);
		model.addAttribute("paging", pagination.print());
		
		returnURL = "admin/poll/pollEntryList";
		return returnURL; 
	}
	
	/**
	 * 설문 조사 문항 입력 폼
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/pollEntryForm.do")
	public String pollEntryForm(HttpServletRequest request, Model model) throws Exception{
		String returnURL = "";
		
		// get parameters
		int pg = Common.getParameter(request, "pg", 1);
		int skip = (pg - 1) * PAGE_SIZE;
		int entryId = Common.getParameter(request, "entryId", 0);
		String mode = Common.getParameter(request, "mode", "add");
		String findStr = Common.getParameter(request, "findStr", "");
		
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("entryId", entryId);
		
		PollEntry pollEntry = null;
		List<PollItem> pollItems = null; 
		
		if(mode.equals("add"))
		{
			pollEntry = new PollEntry();
			pollEntry.setTitle("");
		}
		else if(mode.equals("modify"))
		{
			pollEntry = (PollEntry)pollService.getPollEntryDetail(params);
			pollItems = (List<PollItem>)pollService.getPollItemDetail(params);
		}
			
		model.addAttribute("pathTitle", "설문 문항 관리");
		model.addAttribute("path", "설문 문항 관리 / 등록");
		
		model.addAttribute("mode", mode);
		model.addAttribute("pg", pg);
		model.addAttribute("skip", skip);
		model.addAttribute("findStr", findStr);
		model.addAttribute("pollItems", pollItems);
		model.addAttribute("pollEntry", pollEntry);
		model.addAttribute("entryId", entryId);
		
		returnURL = "admin/poll/pollEntryForm";
		return returnURL;
	}
	
	/**
	 * 설문조사 문항 등록 
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="pollEntryAction.do" , method=RequestMethod.POST)
	public String pollItemAction(HttpServletRequest request, Model model) throws Exception{
		String returnURL = "";
		
		// get parameters
		int pg = Common.getParameter(request, "pg", 1);		
		String mode = Common.getParameter(request, "mode", "add");
		String findStr = Common.getParameter(request, "findStr", "");

		//TODO : Session 처리 후
		String userId = "JHG";
		String title = Common.getParameterNoXSS(request, "title", "");
		String[] answer = Common.getParameters(request, "answer");
		String type = Common.getParameter(request, "type", "");
		String multiple = Common.getParameter(request, "multiple", "N");
		String ansyn = Common.getParameter(request, "ansyn", "N");
		int entryId = Common.getParameter(request, "entryId", 0);
		int pollId = Common.getParameter(request, "pollId", 0);
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("title", title);
		params.put("type", type);
		params.put("multiple", multiple);
		params.put("ansyn", ansyn);
		params.put("pollId", pollId);
		if(mode.equals("add"))
		{
			pollService.insertPollEntry(params);
			entryId = pollService.getEntryId();
		}
		
		params.put("entryId", entryId);
		if(mode.equals("modify"))
		{
			pollService.updatePollEntryDetail(params);
			pollService.deletePollItem(params);
		}else if(mode.equals("delete")){
			pollService.deletePollEntry(params);
		}
				
		if(type.equals("M")||type.equals("D"))
		{
			if(multiple.equals("Y")){ //사용되지 않는 multiple 칼럼을 기타항목으로 사용. 
				// 객관식 Validate 처리
				for(int i=0; i<answer.length; i++){
					if(i==answer.length-1){
						params.put("sequence", 99);
					}else{
						params.put("sequence", i+1);
					}
					
					params.put("answer", answer[i].toString());
					pollService.insertPollItem(params);
				}
			}else{
				// 객관식 Validate 처리
				for(int i=0; i<answer.length-1; i++){
					params.put("sequence", i+1);
					params.put("answer", answer[i].toString());
					pollService.insertPollItem(params);
				}
			}

		}else if(type.equals("W"))
		{
			//주관식 처리
			params.put("sequence", 1);
			params.put("answer", "");
			params.put("multiple", "N");
			pollService.insertPollItem(params);
		}
		
		if( mode.equals("add") )
		{
			returnURL = String.format("redirect:pollEntryList.do?findStr=%s", findStr);
		}
		else if( mode.equals("modify") )
		{
			returnURL = String.format("redirect:pollEntryList.do?findStr=%s", findStr);
		} 
		else if( mode.equals("delete") )
		{
			returnURL = String.format("redirect:pollEntryList.do?findStr=%s", findStr);
		} 
		
		return returnURL;
	}
	
	
	/**
	 * 설문조사 개별 등록 수정
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="pollEntryUpdateAction.do" , method=RequestMethod.POST)
	public String pollItemUpdateAction(HttpServletRequest request, Model model) throws Exception{
		String returnURL = "";
		
		// get parameters
		int pg = Common.getParameter(request, "pg", 1);		
		String mode = Common.getParameter(request, "mode", "add");
		String findStr = Common.getParameter(request, "findStr", "");

		//TODO : Session 처리 후
		String userId = "JHG";
		String title = Common.getParameterNoXSS(request, "title", "");
		String[] answer = Common.getParameters(request, "answer");
		String type = Common.getParameter(request, "type", "");
		String multiple = Common.getParameter(request, "multiple", "N");
		String ansyn = Common.getParameter(request, "ansyn", "N");
		int entryId = Common.getParameter(request, "entryId", 0);
		int pollId = Common.getParameter(request, "pollId", 0);
//		int itemId = Common.getParameter(request, "itemId", 0);
		String[] itemId = Common.getParameters(request, "itemId");
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("title", title);
		params.put("type", type);
		params.put("multiple", multiple);
		params.put("ansyn", ansyn);
		params.put("pollId", pollId);
	
		params.put("entryId", entryId);
		if(mode.equals("modify"))
		{//수정일때
			//logger.info("수정일때");
			if(type.equals("M")||type.equals("D"))
			{
				//logger.info("객관식");
				if(multiple.equals("Y")){
					for(int i=0; i<itemId.length; i++){
						if(i==itemId.length-1){
							params.put("sequence", 99);
						}else{
							params.put("sequence", i+1);
						}
					
						params.put("itemId", itemId[i].toString());
						params.put("answer", answer[i].toString());
						
						pollService.updatePollEntryDetail(params);
						pollService.updatePollItem(params);
					}
				}else{
					for(int i=0; i<itemId.length-1; i++){
						params.put("sequence", i+1);
						params.put("itemId", itemId[i].toString());
						params.put("answer", answer[i].toString());
						
						pollService.updatePollEntryDetail(params);
						pollService.updatePollItem(params);
					}
				}
	
				

			}else if(type.equals("W")){
				//logger.info("주관식");
					//주관식 처리
					params.put("sequence", 1);
					params.put("answer", "");
					params.put("multiple", "N");
					pollService.updatePollItem(params);
				}

		}
		
		returnURL = String.format("redirect:pollEntryList.do?findStr=%s", findStr);
		
		return returnURL;
	}
	
	
	/**
	 * 설문조사 문항 리스트 Ajax
	 * @param request
	 * @param model
	 * @return Map<String, Object>
	 * @throws Exception
	 * @comment blockUI용
	 */
	@RequestMapping(value="/pollEntryListAction.do" , method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> pollEntryListAction(HttpServletRequest request , Model model) throws Exception
	{
		Map<String, Object> result = new HashMap();
		Map<String,Object> params = new HashMap<String,Object>();
		
		int pollId = Common.getParameter(request, "pollId", 0);
		params.put("pollId", pollId);
		List<PollEntry> pollEntry = pollService.getPollEntryPopup(params);
		List<PollEntry> pollEntryed = pollService.getPollEntryed(params);
		
		result.put("pollEntry", pollEntry);
		result.put("pollEntryed", pollEntryed);
		result.put("result" , "SUCCESS");
		return result;
	}
	
	/**
	 * 설문조사 문항 등록 Ajax
	 * @param request
	 * @param model
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@SuppressWarnings("null")
	@RequestMapping(value="/updatePollEntryAction.do", method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> updatePollEntryAction(HttpServletRequest request , Model model) throws Exception
	{
		Map<String, Object> result = new HashMap();
		Map<String, Object> params = new HashMap<String,Object>();
		
		int pollId = Common.getParameter(request, "pollId", 0);
		String[] entryIds =  Common.getParameters(request, "checkPoll");
		String nonEntryId = Common.getParameter(request, "nonEntryId", "");
		String[] arrEntryid = null;
		
		if(nonEntryId.indexOf(",")>0){
			arrEntryid = nonEntryId.split(",");
			params.put("pollId", 0);
			params.put("arrEntryId",arrEntryid);
			
			pollService.updatePollEntry(params);
		}
		params.put("pollId", pollId);

		
		//다른 설문에 등록된 항목은 복사 후 등록하도록 수정
		if(entryIds != null){
			int temp_pollid = 0;
			String temp_entryid = "";

			for(int i=0; i<entryIds.length; i++){
				params.put("entryId", entryIds[i]);
				temp_pollid = pollService.getEntryPollId(params);
				if(temp_pollid>0&&temp_pollid!=pollId){
					params.put("temp_entryid", temp_entryid);
					pollService.insertEntryCopy(params);
					temp_entryid = params.get("temp_entryid").toString();
					params.put("entryId", temp_entryid);
					params.put("oldId", entryIds[i].toString());
					//System.out.print("설문번호:"+temp_pollid+", 입력번호:"+temp_entryid);
					pollService.insertEntryDetailCopy(params);
				}else if(temp_pollid == 0){
					String arrtEntryid[] = {entryIds[i].toString()};

					params.put("pollId", pollId);
					params.put("arrEntryId", arrtEntryid);
					
					pollService.updatePollEntry(params);
				}
			}

		}
		
		result.put("result", "SUCCESS");
		return result;
	}
	
	/**
	 * 설문조사 통계 리스트
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/pollHistoryList.do")
	public String pollHistoryList(HttpServletRequest request , Model model) throws Exception
	{
		// get parameters
		int pg = Common.getParameter(request, "pg", 1);
		int skip = (pg - 1) * PAGE_SIZE;
		String findStr = Common.getParameter(request, "findStr", "");
		String findMethod = Common.getParameter(request, "findMethod", "");
		String findCategory = Common.getParameter(request, "findCategory", "");
		
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("findMethod", findMethod);
		params.put("findStr", findStr);
		params.put("findCategory", findCategory);
		params.put("skip", skip);
		params.put("pageSize", PAGE_SIZE);
		
		int totalPoll = pollService.getPollResultTotalList(params);
		int pollNum = totalPoll - skip;
		
		//set Paging
		Pagination pagination = new Pagination(pg, PAGE_SIZE, BLOCK_SIZE, totalPoll);
		pagination.setDelimiter("");
		pagination.setPrevLink("Prev");
		pagination.setNextLink("Next");
		
		List<PollResponse> lists = pollService.getPollResultList(params);
		
		model.addAttribute("pg", pg);
		model.addAttribute("skip", skip);
		model.addAttribute("findStr", findStr);
		model.addAttribute("findMethod", findMethod);
		model.addAttribute("findCategory", findCategory);
		model.addAttribute("pollNum", pollNum);
		model.addAttribute("lists", lists);
		
		// assign
		model.addAttribute("pathTitle", "설문 조사 통계");
		model.addAttribute("path", "설문 조사 통계 / 리스트");
		model.addAttribute("paging", pagination.print());
		
		return "admin/poll/pollHistoryList";
	}
	
	/**
	 * 설문조사 사용자 리스트
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/pollHistoryUserlist.do")
	public String pollHistoryUserlist(HttpServletRequest request , Model model) throws Exception
	{
		int pollId = Common.getParameter(request, "pollId", 0);
		int pg = Common.getParameter(request, "pg", 1);
		int skip = (pg - 1) * PAGE_SIZE;
		
		if( pollId == 0 )
			throw new CommonException("정상적인 접근이 아닙니다." , CommonException.HISTORY_BACK);
			
		// get parameters
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("pollId", pollId);

		params.put("skip", skip);
		params.put("pageSize", PAGE_SIZE);
		
		int totalPoll = pollService.getPollResponseUserInfoTotal(params);
		int pollNum = totalPoll - skip;
		
		//set Paging
		Pagination pagination = new Pagination(pg, PAGE_SIZE, BLOCK_SIZE, totalPoll);
		pagination.setDelimiter("");
		pagination.setPrevLink("Prev");
		pagination.setNextLink("Next");
		
		List<PollResponse> lists = pollService.getPollResponseUserInfo(params);
		
		if( lists.size() < 1 )
			throw new CommonException("작성된 내용이 없습니다." , CommonException.HISTORY_BACK);
		
		Poll poll = pollService.getPollDetail(pollId);
		
		//getPollResponseUserInfo
		model.addAttribute("lists", lists);
		model.addAttribute("poll", poll);
		model.addAttribute("pollId", pollId);
		model.addAttribute("pollNum", pollNum);
		model.addAttribute("paging", pagination.print());
		model.addAttribute("pg", pg);
		// assign
		model.addAttribute("pathTitle", "설문 조사 통계");
		model.addAttribute("path", "설문 조사 통계 / 사용자 리스트");
		
		return "admin/poll/pollHistoryUserList";
	}
	
	/**
	 * 설문조사 > 설문조사 > 설문결과
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/pollHistoryDetail.do")
	public String pollHistoryDetail(HttpServletRequest request , Model model) throws Exception
	{

		// get parameters
		int pg = Common.getParameter(request, "pg", 1);
		
		int pollId = Common.getParameter(request, "pollId", 0);
		String findMethod = Common.getParameter(request, "findMethod", "");
		String findStr = Common.getParameter(request, "findStr", "");
		
		//TODO : 참여 권한 설정
		if(pollId == 0)
			throw new CommonException("정상적인 접근이 아닙니다." , CommonException.HISTORY_BACK);
		
		Poll poll = pollService.getPollDetail(pollId);
		List<PollResponse> list = pollService.getPollResult(pollId);
		
		if( list.size() < 1 ){
			throw new CommonException("작성된 설문조사가 없습니다." , CommonException.HISTORY_BACK);
		}
		
		
		// assign
/*		model.addAttribute("beforePath", "선생님 행복마당 &gt; 설문조사 결과");
		model.addAttribute("currentPath", "설문조사");
		model.addAttribute("pathName", "테스트");*/
		
		model.addAttribute("pg", pg);
		model.addAttribute("poll", poll);
		model.addAttribute("list", list);
		model.addAttribute("findMethod", findMethod);
		model.addAttribute("findStr", findStr);
		
		model.addAttribute("pathTitle", "설문 조사 통계");
		model.addAttribute("path", "설문 조사 통계 / 리스트");
		
		return "admin/poll/pollHistoryResult";
	}
	
}
