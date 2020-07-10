package kr.co.kumsung.thub.controller.front.thub;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import kr.co.kumsung.thub.domain.Comment;
import kr.co.kumsung.thub.domain.Data;
import kr.co.kumsung.thub.domain.Event;
import kr.co.kumsung.thub.domain.EventWin;
import kr.co.kumsung.thub.domain.Member;
import kr.co.kumsung.thub.domain.Poll;
import kr.co.kumsung.thub.domain.PollResponse;
import kr.co.kumsung.thub.domain.Training;
import kr.co.kumsung.thub.domain.TrainingPostscript;
import kr.co.kumsung.thub.domain.QuizEvent;
import kr.co.kumsung.thub.exception.CommonException;
import kr.co.kumsung.thub.service.BoardService;
import kr.co.kumsung.thub.service.EventService;
import kr.co.kumsung.thub.service.MemberService;
import kr.co.kumsung.thub.service.PollService;
import kr.co.kumsung.thub.service.QuizEventService;
import kr.co.kumsung.thub.service.TrainingService;
import kr.co.kumsung.thub.util.Common;
import kr.co.kumsung.thub.util.FrontPagination;
import kr.co.kumsung.thub.util.ResultMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.ModelAndView;

@Component("thubHappyController")
@Controller
@RequestMapping("/happy")
public class HappyController {
//20191118
	@Autowired
	private TrainingService trainingService;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private EventService eventService;
	
	@Autowired
	private PollService pollService;
	
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private QuizEventService quizEventService;
	
	/**
	 * 공감! 강연
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/lecture.do")
	public String lecture(HttpServletRequest request , Model model) throws Exception
	{
		final int BOARD_ID = 18;
		Map<String,Object> boardAttrs = boardService.getBoardDataAttributes(request, BOARD_ID);
		model.addAllAttributes(boardAttrs);
		
		// assign
		model.addAttribute("beforePath", "나눔 &middot; 소통");
		model.addAttribute("currentPath", "수업 활용 영상");
		
		return "front/thub/happy/lecture";
	}
	
	/**
	 * 힐링 > 캠핑
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/healing/camping.do")
	public String restCamping(HttpServletRequest request , Model model) throws Exception
	{
		final int BOARD_ID = 20;
		Map<String,Object> boardAttrs = boardService.getBoardDataAttributes(request, BOARD_ID);
		model.addAllAttributes(boardAttrs);
		
		// assign
		model.addAttribute("beforePath", "선생님 행복마당 &gt; 힐링타임");
		model.addAttribute("currentPath", "캠핑");
		
		return "front/thub/happy/healing/camping";
	}
	
	/**
	 * 힐링 > 여행
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/healing/travel.do")
	public String restTravel(HttpServletRequest request , Model model) throws Exception
	{
		final int BOARD_ID = 21;
		Map<String,Object> boardAttrs = boardService.getBoardDataAttributes(request, BOARD_ID);
		model.addAllAttributes(boardAttrs);
		
		// assign
		model.addAttribute("beforePath", "선생님 행복마당 &gt; 힐링타임");
		model.addAttribute("currentPath", "여행");
		
		return "front/thub/happy/healing/travel";
	}
	
	/**
	 * 쉼터 > 감동글
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/rest/emotion.do")
	public String healingEmotion(HttpServletRequest request , Model model) throws Exception
	{
		final int BOARD_ID = 11;
		Map<String,Object> boardAttrs = boardService.getBoardDataAttributes(request, BOARD_ID);
		model.addAllAttributes(boardAttrs);
		
		// assign
		model.addAttribute("beforePath", "선생님 행복마당 &gt; 쉼터");
		model.addAttribute("currentPath", "좋은글");
		
		return "front/thub/happy/rest/emotion";
	}
	
	/**
	 * 쉼터 > 웹툰
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/rest/webtoons.do")
	public String healingWebtoons(HttpServletRequest request , Model model) throws Exception
	{
		final int BOARD_ID = 12;
		Map<String,Object> boardAttrs = boardService.getBoardDataAttributes(request, BOARD_ID);
		model.addAllAttributes(boardAttrs);
		
		// assign
		model.addAttribute("beforePath", "선생님 행복마당 &gt; 쉼터");
		model.addAttribute("currentPath", "웹툰");
		
		return "front/thub/happy/rest/webtoons";
	}
	
	/**
	 * 쉼터 > 생활의 지혜
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/rest/wisdom.do")
	public String restWisdom(HttpServletRequest request , Model model) throws Exception
	{
		final int BOARD_ID = 22;
		Map<String,Object> boardAttrs = boardService.getBoardDataAttributes(request, BOARD_ID);
		model.addAllAttributes(boardAttrs);
		
		// assign
		model.addAttribute("beforePath", "선생님 행복마당 &gt; 쉼터");
		model.addAttribute("currentPath", "생활의 지혜");
		
		return "front/thub/happy/rest/wisdom";
	}
	
	/**
	 * 쉼터 > 교사연수지원
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/rest/trainingList.do")
	public String trainingList(HttpServletRequest request , Model model) throws Exception
	{
		// page specificant variables
		int pageSize = 7;
		int blockSize = 10;

		// get parameters
		int pg = Common.getParameter(request, "pg", 1);
		int skip = (pg - 1) * pageSize;
		
		String findMethod = Common.getParameter(request, "findMethod", "");
		String findStr = Common.getParameter(request, "findStr", "");
		
		//TODO : Path 및 경로 지정.
		
		// make query parameters
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("findMethod" , findMethod);
		params.put("findStr" , findStr);
		params.put("findIsUse" , "Y");
		params.put("skip" , skip);
		params.put("pageSize" , pageSize);
		
		// get data
		int totalCount = trainingService.getTrainingTotalList(params);
		int articleNum = totalCount - skip;
		
		List<Training> list = trainingService.getTrainingList(params);;
		
		// assign
		model.addAttribute("beforePath", "선생님 행복마당 &gt; 교사연수지원");
		model.addAttribute("currentPath", "쉼터");
		model.addAttribute("pathName", "테스트");
		
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("articleNum", articleNum);
		model.addAttribute("list", list);
		
		model.addAttribute("pg", pg);
		
		model.addAttribute("findMethod", findMethod);
		model.addAttribute("findStr", findStr);
		
		return "front/thub/happy/rest/trainingList";
	}
	
	/**
	 * 쉼터 > 교사연수지원 > 상세보기
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/rest/trainingDetail.do")
	public String trainingDetail(HttpServletRequest request , Model model) throws Exception
	{
		// get parameters
		int pg = Common.getParameter(request, "pg", 1);
		int trainingId = Common.getParameter(request, "trainingId", 0);
		String findMethod = Common.getParameter(request, "findMethod", "");
		String findStr = Common.getParameter(request, "findStr", "");
		
		if(trainingId == 0)
			throw new CommonException("정상적인 접근이 아닙니다." , CommonException.HISTORY_BACK);
		
		//TODO : Path 및 경로 지정.
		
		// make query parameters
		Map<String,Object> params = new HashMap<String,Object>();
		
		Training training = trainingService.getTrainingDetail(trainingId);
		
		// assign
		model.addAttribute("beforePath", "선생님 행복마당 &gt; 교사연수지원");
		model.addAttribute("currentPath", "쉼터");
		model.addAttribute("pathName", "테스트");
		
		model.addAttribute("pg", pg);
		model.addAttribute("training", training);
		model.addAttribute("findMethod", findMethod);
		model.addAttribute("findStr", findStr);
		
		return "front/thub/happy/rest/trainingDetail";
	}
	
	
	/**
	 * 쉼터 > 교사연수지원
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/rest/postscriptList.do")
	public String postscriptList(HttpServletRequest request , Model model) throws Exception
	{
		// page specificant variables
		int pageSize = 7;
		int blockSize = 10;
		
		// get parameters
		int pg = Common.getParameter(request, "pg", 1);
		int skip = (pg - 1) * pageSize;
		
		String findMethod = Common.getParameter(request, "findMethod", "");
		String findStr = Common.getParameter(request, "findStr", "");
		
		//TODO : Path 및 경로 지정.
		
		// make query parameters
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("findMethod" , findMethod);
		params.put("findStr" , findStr);
		params.put("skip" , skip);
		params.put("pageSize" , pageSize);
		
		// get data
		int totalCount = trainingService.getTrainingPostscriptTotalList(params);
		int articleNum = totalCount - skip;
		
		List<TrainingPostscript> list = trainingService.getTrainingPostscriptList(params);;
		
		// assign
		model.addAttribute("beforePath", "선생님 행복마당 &gt; 현장스케치");
		model.addAttribute("currentPath", "쉼터");
		model.addAttribute("pathName", "테스트");
		
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("articleNum", articleNum);
		model.addAttribute("list", list);
		
		model.addAttribute("pg", pg);
		
		model.addAttribute("findMethod", findMethod);
		model.addAttribute("findStr", findStr);
		
		return "front/thub/happy/rest/postscriptList";
	}
	
	/**
	 * 쉼터 > 교사연수지원 > 상세보기
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/rest/postscriptDetail.do")
	public String postscriptDetail(HttpServletRequest request , Model model) throws Exception
	{
		// get parameters
		int pg = Common.getParameter(request, "pg", 1);
		int postId = Common.getParameter(request, "postId", 0);
		String findMethod = Common.getParameter(request, "findMethod", "");
		String findStr = Common.getParameter(request, "findStr", "");
		
		if(postId == 0)
			throw new CommonException("정상적인 접근이 아닙니다." , CommonException.HISTORY_BACK);
		
		//TODO : Path 및 경로 지정.
		
		
		TrainingPostscript postscript = trainingService.getTrainingPostscriptDetail(postId);
		
		// assign
		model.addAttribute("beforePath", "선생님 행복마당 &gt; 교사연수지원");
		model.addAttribute("currentPath", "쉼터");
		model.addAttribute("pathName", "테스트");
		
		model.addAttribute("pg", pg);
		model.addAttribute("postscript", postscript);
		model.addAttribute("findMethod", findMethod);
		model.addAttribute("findStr", findStr);
		
		return "front/thub/happy/rest/postscriptDetail";
	}
	
	/**
	 * 쉼터 > 교사연수지원 > 신청 폼
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/rest/trainingSubsForm.do")
	public String trainingSubsForm(HttpServletRequest request , Model model) throws Exception
	{
		HttpSession session = (HttpSession) request.getSession();
		Member adminMember = (Member) session.getAttribute("adminMember");

		// get parameters
		int pg = Common.getParameter(request, "pg", 1);
		int trainingId = Common.getParameter(request, "trainingId", 0);
		
		String findMethod = Common.getParameter(request, "findMethod", "");
		String findStr = Common.getParameter(request, "findStr", "");
		
		if(trainingId == 0)
			throw new CommonException("정상적인 접근이 아닙니다." , CommonException.HISTORY_BACK);
		
		//TODO : Path 및 경로 지정.
		
		String userId = adminMember.getUserId();
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("trainingId", trainingId);
		
		
		int trainingDup = trainingService.getTrainingReqDup(params);
	
		if(	trainingDup	> 0 )
			throw new CommonException("이미 신청하였습니다." , CommonException.HISTORY_BACK);
		
		Member member = memberService.getMemberInfo(userId);
		// assign
		model.addAttribute("beforePath", "선생님 행복마당 &gt; 교사연수지원");
		model.addAttribute("currentPath", "쉼터");
		model.addAttribute("pathName", "테스트");
		
		model.addAttribute("trainingId", trainingId);
		model.addAttribute("userId", userId);
		model.addAttribute("member", member);
		
		model.addAttribute("pg", pg);
		model.addAttribute("findMethod", findMethod);
		model.addAttribute("findStr", findStr);
		
		return "front/thub/happy/rest/trainingSubsForm";
	}
	
	/**
	 * 쉼터 > 교사연수지원 > 신청
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/rest/trainingSubsAction.do" , method=RequestMethod.POST)
	public String trainingSubsAction(HttpServletRequest request , Model model) throws Exception
	{
		HttpSession session = (HttpSession) request.getSession();
		Member adminMember = (Member) session.getAttribute("adminMember");
		
		// get parameters
		int trainingId = Common.getParameter(request, "trainingId", 0);
		String phoneNum = Common.getParameter(request, "phoneNum", "");
		String email = Common.getParameter(request, "email", "");
		String contents = Common.getParameter(request, "contents", "");
		
		
		if(trainingId == 0)
			throw new CommonException("정상적인 접근이 아닙니다." , CommonException.HISTORY_BACK);
		
		//TODO : Path 및 경로 지정.
		
		String userId = adminMember.getUserId();
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("trainingId", trainingId);
		params.put("phoneNum", phoneNum);
		params.put("email", email);
		params.put("contents", contents);
		
		trainingService.insertTrainingReq(params);
		
		
		// assign
		model.addAttribute("beforePath", "선생님 행복마당 &gt; 교사연수지원");
		model.addAttribute("currentPath", "쉼터");
		model.addAttribute("pathName", "테스트");
		
		model.addAttribute("trainingId", trainingId);
		model.addAttribute("adminMember", adminMember);
		
		return "redirect:trainingDetail.do?training_id="+trainingId;
	}
	
	
	/**
	 * 쉼터 > 이벤트
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/rest/eventList.do")
	public String eventList(HttpServletRequest request , Model model) throws Exception
	{
		// page specificant variables
		int pageSize = 4;
		int blockSize = 10;

		// get parameters
		int pg = Common.getParameter(request, "pg", 1);
		int skip = (pg - 1) * pageSize;
		
		String findMethod = Common.getParameter(request, "findMethod", "");
		String findStr = Common.getParameter(request, "findStr", "");
		
		// make query parameters
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("findMethod" , findMethod);
		params.put("findStr" , findStr);
		params.put("findIsUse" , "Y");
		params.put("skip" , skip);
		params.put("pageSize" , pageSize);
		
		// get data
		int totalCount = eventService.getEventTotalList(params);
		int articleNum = totalCount - skip;
		
		List<Event> list = eventService.getEventList(params);
		
		FrontPagination pagination = new FrontPagination(pg, pageSize, blockSize, totalCount);
		pagination.initialize();
		
		// assign
		model.addAttribute("beforePath", "나눔 &middot; 소통");
		model.addAttribute("currentPath", "이벤트");
		model.addAttribute("pathName", "테스트");
		
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("articleNum", articleNum);
		model.addAttribute("list", list);
		model.addAttribute("paging", pagination.print());
		
		model.addAttribute("pg", pg);
		
		model.addAttribute("findMethod", findMethod);
		model.addAttribute("findStr", findStr);
		
		return "front/thub/happy/rest/eventList";
	}
	
	/**
	 * 쉼터 > 이벤트 > 상세보기
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/rest/eventDetail.do")
	public String eventDetail(HttpServletRequest request , Model model) throws Exception
	{
		// page specificant variables
		int pageSize = 10;
		int blockSize = 10;	
		
		int pg = Common.getParameter(request, "pg", 1);
		int skip = (pg - 1) * pageSize;
		
		int eventId = Common.getParameter(request, "eventId", 0);
		
		String findMethod = Common.getParameter(request, "findMethod", "");
		String findStr = Common.getParameter(request, "findStr", "");
		
		
		if(eventId == 0)
			throw new CommonException("정상적인 접근이 아닙니다." , CommonException.HISTORY_BACK);
		
		//TODO : Path 및 경로 지정.
		
		// make query parameters
		Map<String,Object> params = new HashMap<String,Object>();
		Map<String,Object> result = new HashMap<String,Object>();
		params.put("eventId" , eventId);
		
		int totalCount = eventService.getCommentTotalList(params);
		FrontPagination pagination = new FrontPagination(pg, pageSize, blockSize, totalCount);
		pagination.initialize();
		
		int eventNum = totalCount - skip;
		params.put("skip", skip);
		params.put("pageSize", pageSize);
		
		Event event = eventService.getEventDetail(eventId);	
		List<Comment> comments = eventService.getCommentList(params);
		
		// assign
		model.addAttribute("beforePath", "나눔 &middot; 소통");
		model.addAttribute("currentPath", "이벤트");
		model.addAttribute("pathName", "테스트");
		
		//model.addAttribute("parentComment", parentComment);
		model.addAttribute("event", event);
		model.addAttribute("findMethod", findMethod);
		model.addAttribute("findStr", findStr);
		model.addAttribute("eventId", eventId);
		model.addAttribute("comments" , comments);
		
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("eventNum", eventNum);
		model.addAttribute("paging", pagination.print());
		model.addAttribute("pg", pg);

		return "front/thub/happy/rest/eventDetail";
	}
	
	
	/**
	 * 쉼터 > 당첨자 보기 > 리스트
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/rest/eventWinList.do")
	public String eventWinList(HttpServletRequest request , Model model) throws Exception
	{
		// page specificant variables
		int pageSize = 4;
		int blockSize = 10;
		
		// get parameters
		int pg = Common.getParameter(request, "pg", 1);
		int skip = (pg - 1) * pageSize;
		
		String findMethod = Common.getParameter(request, "findMethod", "");
		String findStr = Common.getParameter(request, "findStr", "");
		
		// make query parameters
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("findMethod" , findMethod);
		params.put("findStr" , findStr);
		params.put("findIsUse" , "Y");
		params.put("skip" , skip);
		params.put("pageSize" , pageSize);
		
		// get data
		int totalCount = eventService.getEventWinTotalList(params);
		int articleNum = totalCount - skip;
		
		List<EventWin> list = eventService.getEventWinList(params);
		
		FrontPagination pagination = new FrontPagination(pg, pageSize, blockSize, totalCount);
		pagination.initialize();
		
		// assign
		model.addAttribute("beforePath", "선생님 행복마당 &gt; 쉼터");
		model.addAttribute("currentPath", "당첨자 발표");
		model.addAttribute("pathName", "테스트");
		
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("articleNum", articleNum);
		model.addAttribute("list", list);
		model.addAttribute("paging", pagination.print());
		
		model.addAttribute("pg", pg);
		
		model.addAttribute("findMethod", findMethod);
		model.addAttribute("findStr", findStr);
		
		return "front/thub/happy/rest/eventWinList";
	}
	
	/**
	 * 쉼터 > 당첨자 보기 > 상세보기
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/rest/eventWinDetail.do")
	public String eventWinDetail(HttpServletRequest request , Model model) throws Exception
	{
		// get parameters
		int pg = Common.getParameter(request, "pg", 1);
		int winId = Common.getParameter(request, "winId", 0);
		String findMethod = Common.getParameter(request, "findMethod", "");
		String findStr = Common.getParameter(request, "findStr", "");
		
		if(winId == 0)
			throw new CommonException("정상적인 접근이 아닙니다." , CommonException.HISTORY_BACK);
		
		//TODO : Path 및 경로 지정.
		
		EventWin eventWin = eventService.getEventWinDetail(winId);
		
		// assign
		model.addAttribute("beforePath", "선생님 행복마당 &gt; 쉼터");
		model.addAttribute("currentPath", "당첨자 발표");
		model.addAttribute("pathName", "테스트");
		
		model.addAttribute("pg", pg);
		model.addAttribute("eventWin", eventWin);
		model.addAttribute("findMethod", findMethod);
		model.addAttribute("findStr", findStr);
		
		return "front/thub/happy/rest/eventWinDetail";
	}
	
	/**
	 * 더드림 > Family 웹진
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/added/family.do")
	public String addedFamily(HttpServletRequest request , Model model) throws Exception
	{
		final int BOARD_ID = 14;
		Map<String,Object> boardAttrs = boardService.getBoardDataAttributes(request, BOARD_ID);
		model.addAllAttributes(boardAttrs);
		
		// assign
		model.addAttribute("beforePath", "선생님 행복마당 &gt; 더 드림");
		model.addAttribute("currentPath", "Family 웹진");
		
		return "front/thub/happy/added/family";
	}
	
	/**
	 * 더 드림 > 스토리텔링과 교과서
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/added/telling.do")
	public String addedTelling(HttpServletRequest request , Model model) throws Exception
	{
		final int BOARD_ID = 23;
		Map<String,Object> boardAttrs = boardService.getBoardDataAttributes(request, BOARD_ID);
		model.addAllAttributes(boardAttrs);
		
		// assign
		model.addAttribute("beforePath", "선생님 행복마당 &gt; 더 드림");
		model.addAttribute("currentPath", "스토리텔링과 교과서");
		
		return "front/thub/happy/added/telling";
	}
	
	/**
	 * 더 드림 > 교과서 이렇게 만들어요!
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/added/made.do")
	public String addedMade(HttpServletRequest request , Model model) throws Exception
	{
		final int BOARD_ID = 24;
		Map<String,Object> boardAttrs = boardService.getBoardDataAttributes(request, BOARD_ID);
		model.addAllAttributes(boardAttrs);
		
		// assign
		model.addAttribute("beforePath", "선생님 행복마당 &gt; 더 드림");
		model.addAttribute("currentPath", "교과서 이렇게 만들어요!");
		
		return "front/thub/happy/added/made";
	}
	
	/**
	 * 설문조사 > 설문조사 > 리스트
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/poll/pollList.do")
	public String pollList(HttpServletRequest request , Model model) throws Exception
	{
		// page specificant variables
		int pageSize = 10;
		int blockSize = 10;
		
		// get parameters
		int pg = Common.getParameter(request, "pg", 1);
		int skip = (pg - 1) * pageSize;
		
		String findMethod = Common.getParameter(request, "findMethod", "");
		String findStr = Common.getParameter(request, "findStr", "");
		
		//TODO : Path 및 경로 지정.
		
		// make query parameters
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("findMethod" , findMethod);
		params.put("findStr" , findStr);
		params.put("findIsUse" , "Y");
		params.put("skip" , skip);
		params.put("pageSize" , pageSize);
		
		// get data
		int totalCount = pollService.getTotalPoll(params);
		int articleNum = totalCount - skip;
		
		List<Poll> list = pollService.getPollList(params);
		
		FrontPagination pagination = new FrontPagination(pg, pageSize, blockSize, totalCount);
		pagination.initialize();
		
		// assign
		model.addAttribute("beforePath", "나눔 &middot; 소통");
		model.addAttribute("currentPath", "설문조사");
		model.addAttribute("pathName", "테스트");
		
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("articleNum", articleNum);
		model.addAttribute("list", list);
		model.addAttribute("paging", pagination.print());
		
		model.addAttribute("pg", pg);
		
		model.addAttribute("findMethod", findMethod);
		model.addAttribute("findStr", findStr);
		
		return "front/thub/happy/poll/pollList";
	}
	
	/**
	 * 설문조사 > 설문조사 > 설문
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/poll/pollResponseDetail.do")
	public String pollResponseDetail(HttpServletRequest request , Model model) throws Exception
	{
		HttpSession session = (HttpSession) request.getSession();
		Member adminMember = (Member) session.getAttribute("member");
		
		if ( adminMember == null )
			throw new CommonException("로그인 후 이용해주시기 바랍니다." , CommonException.HISTORY_BACK);
		
		// get parameters
		int pg = Common.getParameter(request, "pg", 1);
		int pollId = Common.getParameter(request, "pollId", 0);
		String findMethod = Common.getParameter(request, "findMethod", "");
		String findStr = Common.getParameter(request, "findStr", "");
		
		
		//TODO : 참여 권한 설정
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("regId", adminMember.getUserId());
		params.put("pollId", pollId);
		
		int resDup = pollService.getPollResponseDup(params);
		
		//설문지 중복 참여 체크
		if( resDup > 0 )
			throw new CommonException("이미 참여하셨습니다." , CommonException.HISTORY_BACK);
	
		if(pollId == 0)
			throw new CommonException("정상적인 접근이 아닙니다." , CommonException.HISTORY_BACK);
		
		//TODO : Path 및 경로 지정.
		
		Poll poll = pollService.getPollDetail(pollId);
		List<PollResponse> list = pollService.getPollResponseDetail(params);
		
		// assign
		model.addAttribute("beforePath", "나눔 &middot; 소통");
		model.addAttribute("currentPath", "설문조사");
		model.addAttribute("pathName", "테스트");
		
		model.addAttribute("pg", pg);
		model.addAttribute("poll", poll);
		model.addAttribute("list", list);
		model.addAttribute("findMethod", findMethod);
		model.addAttribute("findStr", findStr);
		
		return "front/thub/happy/poll/pollResponseDetail";
	}
	
	
	/**
	 * 설문조사 > 설문조사 > 설문등록
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/poll/pollResponseAction.do" , method=RequestMethod.POST)
	public String pollResponseAction(HttpServletRequest request , Model model) throws Exception
	{
		HttpSession session = (HttpSession) request.getSession();
		Member adminMember = (Member) session.getAttribute("member");
		// get parameters
		int pollId = Common.getParameter(request, "pollId", 0);
		String[] answers = Common.getParameters(request, "answers");
		String[] entryIds = Common.getParameters(request, "entryIds");
		String[] itemIds = Common.getParameters(request, "itemIds");
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("regId", adminMember.getUserId());
		params.put("pollId", pollId);
		
		int resDup = pollService.getPollResponseDup(params);
		
		//설문지 중복 참여 체크
		if( resDup > 0 )
			throw new CommonException("이미 참여하셨습니다." , CommonException.HISTORY_BACK);
		
		if(pollId == 0)
			throw new CommonException("정상적인 접근이 아닙니다." , CommonException.HISTORY_BACK);

		//TODO : Path 및 경로 지정.
		
		for( int i=0; i<answers.length; i++ ){
			params.put("answer", answers[i]);
			params.put("entryId", entryIds[i]);
			params.put("itemId", itemIds[i]);
			
			pollService.insertPollResponse(params);
		}
		// assign
		model.addAttribute("beforePath", "나눔 &middot; 소통");
		model.addAttribute("currentPath", "설문조사");
		model.addAttribute("pathName", "테스트");
		
		Poll poll = pollService.getPollDetail(pollId);
		model.addAttribute("poll", poll);
		
		return "front/thub/happy/poll/pollResponseComplete";
	}
	
	
	/**
	 * 설문조사 > 설문조사 > 설문
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/poll/pollResponseComplete.do")
	public String pollResponseComplete(HttpServletRequest request , Model model) throws Exception
	{
		HttpSession session = (HttpSession) request.getSession();
		Member adminMember = (Member) session.getAttribute("member");
		
		// get parameters
		int pg = Common.getParameter(request, "pg", 1);
		int pollId = Common.getParameter(request, "pollId", 0);
		String findMethod = Common.getParameter(request, "findMethod", "");
		String findStr = Common.getParameter(request, "findStr", "");
		
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("regId", adminMember.getUserId());
		params.put("pollId", pollId);
		
		int resDup = pollService.getPollResponseDup(params);
		
		if(pollId == 0)
			throw new CommonException("정상적인 접근이 아닙니다." , CommonException.HISTORY_BACK);

		// assign
		model.addAttribute("pg", pg);
		model.addAttribute("findMethod", findMethod);
		model.addAttribute("findStr", findStr);
		
		return "front/thub/happy/poll/pollResponseComplete";
	}
	
	/**
	 * 설문조사 > 설문조사 > 설문결과
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/poll/pollResult.do")
	public String pollResult(HttpServletRequest request , Model model) throws Exception
	{

		// get parameters
		int pg = Common.getParameter(request, "pg", 1);
		int pollId = Common.getParameter(request, "pollId", 0);
		String findMethod = Common.getParameter(request, "findMethod", "");
		String findStr = Common.getParameter(request, "findStr", "");
		
		//TODO : 참여 권한 설정
		
		if(pollId == 0)
			throw new CommonException("정상적인 접근이 아닙니다." , CommonException.HISTORY_BACK);
		
		//TODO : Path 및 경로 지정.
		
		Poll poll = pollService.getPollDetail(pollId);
		List<PollResponse> list = pollService.getPollResult(pollId);
		
		// assign
		model.addAttribute("beforePath", "나눔 &middot; 소통");
		model.addAttribute("currentPath", "설문조사");
		model.addAttribute("pathName", "테스트");
		
		model.addAttribute("pg", pg);
		model.addAttribute("poll", poll);
		model.addAttribute("list", list);
		model.addAttribute("findMethod", findMethod);
		model.addAttribute("findStr", findStr);
		
		return "front/thub/happy/poll/pollResult";
	}
	
	
	
	/**
	 * 설문조사 > 설문조사 > 설문결과 레이어 팝업
	 * @param request
	 * @param model
	 * @return JSON
	 */
	@RequestMapping(value="/poll/getPollResultLayer.do" , method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> getPollResultLayer(HttpServletRequest request , Model model) throws Exception
	{	
		Map<String,Object> result = new HashMap<String,Object>();
		
		// get parameters
		int entryId = Common.getParameter(request, "entryId", 0);
		int itemId = Common.getParameter(request, "itemId", 0);
		int pollId = Common.getParameter(request, "pollId", 0);
		
		if( entryId == 0 || pollId == 0 )
		{
			result.put("result" , "FAILURE");
			result.put("msg" , "정상적인 파라미터가 아닙니다.");
			
			return result;
		}
		
		Poll poll = pollService.getPollDetail(pollId);
		
		// processing
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("entryId" , entryId);
		params.put("pollId" , pollId);
		
		
		List<PollResponse> pollRes = null;
		
		if(itemId>0){
			params.put("itemId" , itemId);
			pollRes = pollService.getPollResultEtcLayer(params);
		}else{
			pollRes = pollService.getPollResultLayer(params);
		}

		
		// set result
		result.put("poll", poll);
		result.put("pollRes", pollRes); 
		result.put("result" , "SUCCESS");
		result.put("msg" , "성공");
		
		return result;
	}

	/**
	 * 첫번째 이벤트
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/rest/event01.do")
	public String event01(HttpServletRequest request , Model model) throws Exception
	{
		HttpSession session = (HttpSession) request.getSession();
		Member member = (Member) session.getAttribute("member");
		
		List<ResultMap> myFlags = (member == null ) ? new ArrayList<ResultMap>() : (List<ResultMap>) eventService.getEvent01MyFlags(member.getUserId());
		
		// assign
		model.addAttribute("beforePath", "선생님 행복마당");
		model.addAttribute("currentPath", "이벤트");
		model.addAttribute("myFlags", myFlags);
		
		return "front/thub/happy/rest/event01";
	}
	
	/**
	 * Quiz 이벤트
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/rest/quizEvent.do" , method=RequestMethod.GET)
	public @ResponseBody Map<String, Object> quizEvent(HttpServletRequest request , Model model) throws Exception
	{
		// get parameters
		String user_id = Common.getParameter(request, "user_id", "");
		String quiz_date = Common.getParameter(request, "quiz_date", "");
		int event_id = Common.getParameter(request, "event_id", 0);
		
		if (user_id.equals("")) {
			throw new CommonException("로그인이 필요합니다.", CommonException.PARENT_RELOAD);
		}

		if (quiz_date.equals("") || event_id == 0) {
			throw new CommonException("필수 항목이 없습니다.", CommonException.PARENT_RELOAD);
		}

		Map<String, Object> params = new HashMap<String, Object>();

		params.put("user_id", user_id);
		params.put("quiz_date", quiz_date);
		params.put("event_id", event_id);

		int regCnt = quizEventService.getRegCnt(params);
	
		if ( regCnt > 0 ) {
			throw new CommonException("이미 신청하셨습니다.", "", CommonException.REDIRECT);
		} else {
			quizEventService.insertQuizEvent(params);
			throw new CommonException("응모 됐습니다.", "", CommonException.REDIRECT);
		}
	}
	
	@RequestMapping("/rest/attendance.do")
	public String tqevent(HttpServletRequest request , Model model) throws Exception {
		HttpSession session = (HttpSession) request.getSession();
		Member member = (Member) session.getAttribute("member");
		
		Calendar cal = Calendar.getInstance();
		Date date = cal.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String formatStr = sdf.format(date);
		
		if( member == null ) {
			
			PollResponse tqevent_select = pollService.tqevent_select("");
			PollResponse tqevent_cnt    = pollService.tqevent_cnt("");
			
			model.addAttribute("tqevent_select", tqevent_select);     // 출첵
			model.addAttribute("tqevent_cnt"   , tqevent_cnt);        // 출첵횟수
			model.addAttribute("tqevent_day"   , formatStr);          // 오늘날짜
			
			//return "front/thub/happy/rest/tqevent";			
		}
		else {
			String user_id = member.getUserId();
			
			PollResponse tqevent_select = pollService.tqevent_select(user_id);
			PollResponse tqevent_cnt    = pollService.tqevent_cnt(user_id);

			model.addAttribute("tqevent_select", tqevent_select);  // 출첵
			model.addAttribute("tqevent_cnt", tqevent_cnt);        // 출첵횟수
			model.addAttribute("tqevent_day", formatStr);          // 오늘날짜
			
			//return "front/thub/happy/rest/tqevent";
		}
		
		return "front/thub/happy/rest/tqevent";
	}

	@RequestMapping(value="/rest/tqevent_insert.do" , method=RequestMethod.POST)
	public @ResponseBody ModelAndView tqevent_insert(HttpServletRequest request , Model model) throws Exception
	{
		HttpSession session = (HttpSession) request.getSession();
		Member member = (Member) session.getAttribute("member");
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/rest/attendance.do");
		
		String user_id = member.getUserId();
		String date_813 = Common.getParameter(request, "date_813", "");
		String date_814 = Common.getParameter(request, "date_814", "");
		String date_815 = Common.getParameter(request, "date_815", "");
		String date_816 = Common.getParameter(request, "date_816", "");
		String date_817 = Common.getParameter(request, "date_817", "");
		String date_818 = Common.getParameter(request, "date_818", "");
		String date_819 = Common.getParameter(request, "date_819", "");
		String date_820 = Common.getParameter(request, "date_820", "");
		String date_821 = Common.getParameter(request, "date_821", "");
		String date_822 = Common.getParameter(request, "date_822", "");
		String date_823 = Common.getParameter(request, "date_823", "");
		String date_824 = Common.getParameter(request, "date_824", "");
		String date_825 = Common.getParameter(request, "date_825", "");
		String date_826 = Common.getParameter(request, "date_826", "");
		String date_827 = Common.getParameter(request, "date_827", "");
		String date_828 = Common.getParameter(request, "date_828", "");
		String date_829 = Common.getParameter(request, "date_829", "");
		String date_830 = Common.getParameter(request, "date_830", "");
		String date_831 = Common.getParameter(request, "date_831", "");
		String date_901 = Common.getParameter(request, "date_901", "");
		String date_902 = Common.getParameter(request, "date_902", "");
		String date_903 = Common.getParameter(request, "date_903", "");
		String date_904 = Common.getParameter(request, "date_904", "");
		String date_905 = Common.getParameter(request, "date_905", "");
		String date_906 = Common.getParameter(request, "date_906", "");
		String date_907 = Common.getParameter(request, "date_907", "");
		String date_908 = Common.getParameter(request, "date_908", "");
		String date_909 = Common.getParameter(request, "date_909", "");
		
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("user_id" , user_id);
		params.put("date_813", date_813);
		params.put("date_814", date_814);
		params.put("date_815", date_815);
		params.put("date_816", date_816);
		params.put("date_817", date_817);
		params.put("date_818", date_818);
		params.put("date_819", date_819);
		params.put("date_820", date_820);
		params.put("date_821", date_821);
		params.put("date_822", date_822);
		params.put("date_823", date_823);
		params.put("date_824", date_824);
		params.put("date_825", date_825);
		params.put("date_826", date_826);
		params.put("date_827", date_827);
		params.put("date_828", date_828);
		params.put("date_829", date_829);
		params.put("date_830", date_830);
		params.put("date_831", date_831);
		params.put("date_901", date_901);
		params.put("date_902", date_902);
		params.put("date_903", date_903);
		params.put("date_904", date_904);
		params.put("date_905", date_905);
		params.put("date_906", date_906);
		params.put("date_907", date_907);
		params.put("date_908", date_908);
		params.put("date_909", date_909);
		
		pollService.tqevent_insert(params);
		
		return mv;
	}
	
	@RequestMapping(value="/rest/tqquizevent.do", method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> tqquizevent(HttpServletRequest request , Model model) throws Exception{
		
		HttpSession session = (HttpSession) request.getSession();
		Map<String,Object> params = new HashMap<String,Object>();
		Map<String,Object> result = new HashMap<String,Object>();
		Member member = (Member) session.getAttribute("member");

		int eventid       = Common.getParameter(request, "event_id", 0);
		String answer     = Common.getParameter(request, "answer"  , "");

		if( member == null){
			
			result.put("result", "FAILURE");
			result.put("msg"   , "로그인 후 참여 바랍니다.");
			
			return result;
		}				
		
		String userid     = member.getUserId();		
		
		PollResponse poll = pollService.cnt_tqquizevent(userid);
		PollResponse poll_chk = pollService.chk_teacher(userid);

		if(Integer.parseInt(poll_chk.getCnt()) == 0) {

			result.put("result", "FAILURE");
			result.put("msg"   , "교사 인증 회원만 참여 가능합니다.");
		}
		else {

			if(Integer.parseInt(poll.getCnt())>0) {
				
				result.put("result", "FAILURE");
				result.put("msg"   , "이미 참여하셨습니다.");			
			}
			else if(answer.equals("[object HTMLCollection]")) {
				
				result.put("result", "FAILURE");
				result.put("msg"   , "금성출판사 띠를 선택하세요.");				
			}
			else {
				
				params.put("eventid", eventid);
				params.put("answer" , answer);
				params.put("userid" , userid);
				
				pollService.tqquizevent_insert(params);
				
				result.put("result", "SUCCESS");
				result.put("msg"   , "정상적으로 참여하셨습니다.");					
			}
		}
		
		return result;
	}	
	
	@RequestMapping(value="/rest/tq_test.do", method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> tq_test(HttpServletRequest request , Model model) throws Exception{
		
		HttpSession session = (HttpSession) request.getSession();
		Map<String,Object> params = new HashMap<String,Object>();
		Map<String,Object> result = new HashMap<String,Object>();
		Member member = (Member) session.getAttribute("member");

		int eventid    = Common.getParameter(request, "event_id", 0);
		String answer1 = Common.getParameter(request, "answer1" , "");
		String answer2 = Common.getParameter(request, "answer2" , "");
		String answer3 = Common.getParameter(request, "answer3" , "");
		String answer4 = Common.getParameter(request, "answer4" , "");

		if( member == null){
			
			result.put("result", "FAILURE");
			result.put("msg"   , "로그인 후 참여 바랍니다.");
			
			return result;
		}				
		
		String userid     = member.getUserId();		
		
		PollResponse poll = pollService.cnt_tq_test(userid);
		PollResponse poll_chk = pollService.chk_teacher(userid);

		if(Integer.parseInt(poll_chk.getCnt()) == 0) {

			result.put("result", "FAILURE");
			result.put("msg"   , "교사 인증 회원만 참여 가능합니다.");
		}
		else {

			if(Integer.parseInt(poll.getCnt())>0) {
				
				result.put("result", "FAILURE");
				result.put("msg"   , "이미 참여하셨습니다.");			
			}
			else {
				
				params.put("eventid" , eventid);
				params.put("answer1" , answer1);
				params.put("answer2" , answer2);
				params.put("answer3" , answer3);
				params.put("answer4" , answer4);
				params.put("userid"  , userid);
				
				pollService.tq_test_insert(params);
				
				result.put("result", "SUCCESS");
				result.put("msg"   , "정상적으로 참여하셨습니다.");					
			}
		}
		
		return result;
	}
	
	@RequestMapping(value="/rest/tq_test_191118.do", method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> tq_test_191118(HttpServletRequest request , Model model) throws Exception{
		
		HttpSession session = (HttpSession) request.getSession();
		Map<String,Object> params = new HashMap<String,Object>();
		Map<String,Object> result = new HashMap<String,Object>();
		Member member = (Member) session.getAttribute("member");

		int eventid    = Common.getParameter(request, "event_id", 0);
		String answer1 = Common.getParameter(request, "answer1" , "");
		String answer2 = Common.getParameter(request, "answer2" , "");
		
		if( member == null){
			
			result.put("result", "FAILURE");
			result.put("msg"   , "본 이벤트는 교사인증 회원대상 이벤트 입니다.\r\n" + 
					"로그인 후, 이용하시기 바랍니다. \r\n");
			
			return result;
		}				
		
		String userid         = member.getUserId();
		
		params.put("userid"  , userid);
		params.put("eventid" , eventid);		
		PollResponse poll     = pollService.cnt_tq_test_190529(params);
		PollResponse poll_chk = pollService.chk_teacher(userid);
		
		if(Integer.parseInt(poll_chk.getCnt()) == 0) {
			result.put("result", "FAILURE");
			result.put("msg"   , "교사 인증 회원만 참여 가능합니다.");
		}
		else {
			if(Integer.parseInt(poll.getCnt())>0) {
				result.put("result", "FAILURE");
				result.put("msg"   , "이미 참여하셨습니다.");			
			}
			else {
				params.put("answer1" , answer1);
				params.put("answer2" , answer2);
				
				pollService.tq_test_insert_190529(params);
				
				result.put("result", "SUCCESS");
				result.put("msg"   , "정상적으로 참여하셨습니다.");					
			}
		}
		
		return result;
	}	
	
	@RequestMapping(value="/rest/tq_test_190529.do", method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> tq_test_190529(HttpServletRequest request , Model model) throws Exception{
		
		HttpSession session = (HttpSession) request.getSession();
		Map<String,Object> params = new HashMap<String,Object>();
		Map<String,Object> result = new HashMap<String,Object>();
		Member member = (Member) session.getAttribute("member");

		int eventid    = Common.getParameter(request, "event_id", 0);
		String answer1 = Common.getParameter(request, "answer1" , "");
		String answer2 = Common.getParameter(request, "answer2" , "");
		
		if( member == null){
			
			result.put("result", "FAILURE");
			result.put("msg"   , "로그인 후 참여 바랍니다.");
			
			return result;
		}				
		
		String userid         = member.getUserId();
		
		params.put("userid"  , userid);
		params.put("eventid" , eventid);		
		PollResponse poll     = pollService.cnt_tq_test_190529(params);
		PollResponse poll_chk = pollService.chk_teacher(userid);
		
		if(Integer.parseInt(poll_chk.getCnt()) == 0) {
			result.put("result", "FAILURE");
			result.put("msg"   , "교사 인증 회원만 참여 가능합니다.");
		}
		else {
			if(Integer.parseInt(poll.getCnt())>0) {
				result.put("result", "FAILURE");
				result.put("msg"   , "이미 참여하셨습니다.");			
			}
			else {
				params.put("answer1" , answer1);
				params.put("answer2" , answer2);
				
				pollService.tq_test_insert_190529(params);
				
				result.put("result", "SUCCESS");
				result.put("msg"   , "정상적으로 참여하셨습니다.");					
			}
		}
		
		return result;
	}			
	@RequestMapping(value="/rest/tq_test_190614.do", method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> tq_test_190614(HttpServletRequest request , Model model) throws Exception{
		
		HttpSession session = (HttpSession) request.getSession();
		Map<String,Object> params = new HashMap<String,Object>();
		Map<String,Object> result = new HashMap<String,Object>();
		Member member = (Member) session.getAttribute("member");
		int eventid    = Common.getParameter(request, "event_id", 0);
		String schoolinfoseq = Common.getParameter(request, "schoolinfoseq" , "");
		String userid  = member.getUserId();
		String hp      = Common.getParameter(request, "hp" , "");
		String address = Common.getParameter(request, "address" , "");
		String zipcode = Common.getParameter(request, "zipcode" , "");
		String remark = Common.getParameter(request, "remark" , "");
		String qty = Common.getParameter(request, "qty" , "");
		
		if( member == null){
			
			result.put("result", "FAILURE");
			result.put("msg"   , "로그인 후 참여 바랍니다.");
			
			return result;
		}				
		
		params.put("eventid" , eventid);	
		params.put("userid"  , userid);
		
		PollResponse poll     = pollService.cnt_tq_test_190529(params);
		PollResponse poll_chk = pollService.chk_teacher(userid);
		PollResponse poll_500 = pollService.chk_500();
		
		if(Integer.parseInt(poll_chk.getCnt()) == 0) {
			result.put("result", "FAILURE");
			result.put("msg"   , "교사 인증 회원만 참여 가능합니다.");
		}
		else {
			if(Integer.parseInt(poll.getCnt())>0) {
				result.put("result", "FAILURE");
				result.put("msg"   , "이미 참여하셨습니다.");			
			}
			else if(Integer.parseInt(poll_500.getCnt()) >= 500) {
				result.put("result", "FAILURE");
				result.put("msg"   , "준비된 수량이 모두 소진되었습니다.성원에 감사드립니다.PDF 다운로드를 이용하시기 바랍니다.");				
			}
			else if(500 - Integer.parseInt(poll_500.getCnt()) == 1) {
				if(Integer.parseInt(qty) != 1) {
					result.put("result", "FAILURE");
					result.put("msg"   , "1개만 주문이 가능합니다.");
				}
			}
			else {
				params.put("hp"             , hp);
				params.put("schoolinfoseq"  , schoolinfoseq);
				params.put("address"        , address);
				params.put("zipcode"        , zipcode);
				params.put("remark"         , remark);
				params.put("qty"            , qty);
				
				pollService.tq_test_insert_190619(params);
				
				result.put("result", "SUCCESS");
				result.put("msg"   , "정상적으로 참여하셨습니다.");				
			}
		}
		return result;
	}

	@RequestMapping("/rest/history_test.do")
	public String history_test(HttpServletRequest request , Model model) throws Exception {
		HttpSession session = (HttpSession) request.getSession();
		Member member = (Member) session.getAttribute("member");
		Calendar cal = Calendar.getInstance();
		Date date = cal.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String formatStr = sdf.format(date);
		Map<String,Object> params = new HashMap<String,Object>();
		
		if( member != null ) {
			
			String user_id = member.getUserId();
			params.put("userid"  , user_id);
			params.put("eventid" , "224");

			PollResponse usergap     = pollService.usergap(params);

			model.addAttribute("usergap", usergap);  // 출첵			
		}
		
		return "front/thub/happy/rest/tqhistory";
	}
	
	@RequestMapping(value="/rest/tq_test_190614_qty.do", method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> tq_test_190614_qty(HttpServletRequest request , Model model) throws Exception{
		
		HttpSession session = (HttpSession) request.getSession();
		Map<String,Object> params = new HashMap<String,Object>();
		Map<String,Object> result = new HashMap<String,Object>();
		int eventid    = Common.getParameter(request, "event_id", 0);
		String qty     = Common.getParameter(request, "qty" , "");
		
		params.put("eventid" , eventid);	
		
		PollResponse poll_500 = pollService.chk_500();
		
		int tmp_qty  = Integer.parseInt(poll_500.getCnt());
		int tmp_qty2 = 500 - Integer.parseInt(poll_500.getCnt());
		
			if(tmp_qty >= 500) {
				result.put("result", "FAILURE");
				result.put("msg"   , "준비된 수량이 모두 소진되었습니다.성원에 감사드립니다.PDF 다운로드를 이용하시기 바랍니다.");				
			}
			else if(tmp_qty2 == 1) {
				if(Integer.parseInt(qty) == 1) {
					result.put("result", "SUCCESS");
				}
				else {
					result.put("result", "QTY");
					result.put("msg"   , "1개만 주문이 가능합니다.");					
				}
			}
			else {
				result.put("result", "SUCCESS");
			}
		return result;
	}	
	
	// 이벤트 시 학교급 체크 추가 2020.05.08	
	@RequestMapping("/rest/isSchoolInfo.do")
	public @ResponseBody Map<String,Object> isTeacherLogin(HttpServletRequest request) throws Exception
	{
		Map<String,Object> result = new HashMap<String,Object>();
		
		// get session
		HttpSession session = (HttpSession) request.getSession();
		Member member = (Member) session.getAttribute("member");
		
		String getCg = member.getSubCategory().substring(0,4);
		
		if(!getCg.equals("")) {
		result.put("result", getCg);
		} else {
		result.put("result", "FAILURE");	
		}
		
		return result;
	}
}
