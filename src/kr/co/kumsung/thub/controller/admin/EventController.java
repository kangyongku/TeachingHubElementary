package kr.co.kumsung.thub.controller.admin;


import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import kr.co.kumsung.thub.domain.BoardConfig;
import kr.co.kumsung.thub.domain.Comment;
import kr.co.kumsung.thub.domain.Event;
import kr.co.kumsung.thub.domain.EventWin;
import kr.co.kumsung.thub.domain.FileInfo;
import kr.co.kumsung.thub.domain.Member;
import kr.co.kumsung.thub.exception.CommonException;
import kr.co.kumsung.thub.service.BoardService;
import kr.co.kumsung.thub.service.EventService;
import kr.co.kumsung.thub.service.FileService;
import kr.co.kumsung.thub.util.Common;
import kr.co.kumsung.thub.util.Pagination;
import kr.co.kumsung.thub.util.Validate;


import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import net.coobird.thumbnailator.name.Rename;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


/**
 * 이벤트 관리
 * @author jhg
 * @date 0808~
 */
@Component("adminEventController")
@Controller
@RequestMapping("/admin/event")
public class EventController {
	
	private static final int PAGE_SIZE = 10;
	private static final int BLOCK_SIZE = 10;
	
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private EventService eventService;
	
	@Autowired
	private FileService fileService;
	
	@Value("#{common['file.path']}") String serverFilePath;
	
	/**
	 * 이벤트 리스트
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/eventList.do")
	public String eventList(HttpServletRequest request , Model model) throws Exception
	{
		
		long cutTime = System.currentTimeMillis();
		Date date = new Date( cutTime );
		SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" );
		String nowTime = sdf.format( date );

		// get parameters
		int pg = Common.getParameter(request, "pg", 1);
		int skip = (pg - 1) * PAGE_SIZE;
		
		String findStr = Common.getParameter(request, "findStr", "");
		String findMethod = Common.getParameter(request, "findMethod", "");
		String findStartDate = Common.getParameter(request, "findStartDate", "");
		String findEndDate = Common.getParameter(request, "findEndDate", "");
		String findStatusDate = Common.getParameter(request, "findStatusDate", "");
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("findStr", findStr);
		params.put("findMethod", findMethod);
		params.put("findStartDate", findStartDate);
		params.put("findEndDate", findEndDate);
		params.put("findStatusDate", findStatusDate);
		int eventTotalCnt = eventService.getEventTotalList(params);
		
		// set pagination
		Pagination pagination = new Pagination(pg, PAGE_SIZE, BLOCK_SIZE, eventTotalCnt);
		pagination.setDelimiter("");
		pagination.setPrevLink("Prev");
		pagination.setNextLink("Next");
		
		int eventNum = eventTotalCnt - skip;
		params.put("skip", skip);
		params.put("pageSize", PAGE_SIZE);
		
		List<Event> eventList = eventService.getEventList(params);
		
		model.addAttribute("findStr", findStr);
		model.addAttribute("findMethod", findMethod);
		model.addAttribute("findStartDate", findStartDate);
		model.addAttribute("findEndDate", findEndDate);		
		model.addAttribute("findStatusDate", findStatusDate);
		
		model.addAttribute("nowTime", nowTime);
		model.addAttribute("eventNum", eventNum);
		model.addAttribute("eventTotalCnt", eventTotalCnt);
		model.addAttribute("eventList", eventList);
		
		// assign
		model.addAttribute("pathTitle", "이벤트 관리");
		model.addAttribute("path", "이벤트 관리 / 리스트");
		model.addAttribute("paging", pagination.print());
		model.addAttribute("pg", pg);
		
		return "admin/event/eventList";
	}
	
	/**
	 * 이벤트댓글 리스트
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/eventCommentList.do")
	public String eventCommentList(HttpServletRequest request , Model model) throws Exception
	{
		final int PAGE_SIZE = 10;
		final int BLOCK_SIZE = 10;
		
		// get parameter
		int pg = Common.getParameter(request, "pg", 1);
		int skip = (pg - 1) * PAGE_SIZE;
		int findBoardId = Common.getParameter(request, "findBoardId", 0);
		int eventId = Common.getParameter(request, "eventId", 0);
		
		
		// make query parameters
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("findBoardId" , findBoardId);
		params.put("skip" , skip);
		params.put("pageSize" , PAGE_SIZE);
		
		// get comment list
		int totalCount = (Integer) eventService.getTotalCommentListCount(params);
		int articleNum = totalCount - skip;
		List<Comment> comments = (List<Comment>) eventService.getTotalCommentList(params);
		List<Event> eventList = eventService.getEventList(params);
															  
		// pagination
		Pagination pagination = new Pagination(pg, PAGE_SIZE, BLOCK_SIZE, totalCount);
		pagination.initialize();
		
		// assign
		model.addAttribute("eventId", eventId);
		//model.addAttribute("event", event);
		model.addAttribute("pathTitle", "댓글관리");
		model.addAttribute("path", "이벤트 > 이벤트댓글관리");
		model.addAttribute("pg", pg);
		model.addAttribute("findBoardId", findBoardId);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("articleNum" , articleNum);
		model.addAttribute("comments", comments);
		model.addAttribute("paging", pagination.print());
		model.addAttribute("eventList", eventList);
		
		return "admin/event/eventCommentList";
	}
	
	
	
	/**
	 * 이벤트 관리 수정 폼
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/eventForm.do")
	public String eventForm(HttpServletRequest request , Model model) throws Exception
	{
		// get parfameters
		int eventId = Common.getParameter(request, "eventId", 0);
		int pg = Common.getParameter(request, "pg", 1);		
		BoardConfig boardConfig = new BoardConfig();
		String mode = Common.getParameter(request, "mode", "add");
		
		
	Event event = new Event();
		if( mode.equals("add") )
		{
			// default setting
			boardConfig.setBoardName("");
			boardConfig.setBoardType("list");
			boardConfig.setBoardFlag("");
			boardConfig.setUseCategory("N");
			boardConfig.setUseWrite("Y");
			boardConfig.setUseReply("Y");
			boardConfig.setUseComment("Y");
			boardConfig.setUseMovie("Y");
			boardConfig.setUseLink("N");
			boardConfig.setUseFileUpload("Y");
			boardConfig.setUseRecommend("Y");
			boardConfig.setUseSns("Y");
			boardConfig.setUseAnnmsList("Y");
			boardConfig.setUseAnnmsView("Y");			
			boardConfig.setAllowFileTypes("jpg,gif,png,hwp,zip,xls,xlsx,doc,docx,ppt,pptx");
			boardConfig.setAllowFileCount(1);
			boardConfig.setAllowFileSize(2048);
		}
		else if( mode.equals("modify") )
		{
			event = eventService.getEventDetail(eventId);		
		}

		model.addAttribute("eventId", eventId);
		model.addAttribute("event", event);
		model.addAttribute("mode", mode);
		model.addAttribute("pathTitle", "이벤트 관리");
		model.addAttribute("path", "이벤트 관리 등록/수정");
		model.addAttribute("pg", pg);
		model.addAttribute("boardConfig", boardConfig);

		return "admin/event/eventForm";
	}
	
	/**
	 * 이벤트 관리 등록
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="eventAction.do" , method=RequestMethod.POST)
	public String eventAction(HttpServletRequest request, Model model) throws Exception
	{
		String mode = Common.getParameter(request, "mode", "add");
		
		int eventId = Common.getParameter(request, "eventId", 0); 
		String title = Common.getParameter(request, "title", "");
		String startDate = Common.getParameter(request, "startDate", "");
		String endDate = Common.getParameter(request, "endDate", "");
		String contents = Common.getParameterNoXSS(request, "contents", "");
		String summaryContents = Common.getParameterNoXSS(request, "summaryContents", "");
		String fileName = Common.getParameter(request, "fileName", "");
		String bannerImg = Common.getParameter(request, "bannerImg", "");
		String useComment = Common.getParameter(request, "useComment", "");
		String useReply = Common.getParameter(request, "useReply", "");
		String bannerImgPath;
		
		// 관리자 타입에 따라서 조회 권한 부여
		HttpSession session = (HttpSession) request.getSession();
		Member adminMember = (Member) session.getAttribute("adminMember");
		
		String regId = adminMember.getUserId();
		String modifyId = adminMember.getUserId();
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("eventId", eventId);
		params.put("title", title);
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		params.put("contents", contents);
		params.put("summaryContents", summaryContents);
		params.put("fileName", fileName);
		params.put("bannerImg", bannerImg);
		params.put("useComment", useComment);
		params.put("useReply", useReply);
		
		params.put("regId", regId);
		params.put("modifyId", modifyId);
		
		if(mode.equals("add")){
			eventService.insertEvent(params);
		}else if(mode.equals("modify")){
			eventService.updateEvent(params);
		}else if(mode.equals("delete")){
			if(eventId == 0)
				throw new CommonException("잘못된 접근입니다." , CommonException.HISTORY_BACK);
			bannerImgPath = eventService.getEventDetailBanner(eventId);
			if( !Validate.isEmpty(bannerImg) )
			{
				String deleteFilePath = String.format("%s%s" , serverFilePath , bannerImg.replace("/upfiles" , ""));
				fileService.delete(deleteFilePath);
			}
			eventService.deleteEvent(eventId);
		}
		
		return "redirect:eventList.do";
	}
	 
	/**
	 * 파일 업로드
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 * */
	@RequestMapping("/eventUpload.do")
	public String eventUpload(HttpServletRequest request, Model model,
			@RequestParam("upfile")MultipartFile attachFile ) throws Exception
	{
		
		//TODO : File validate 확인 , filehandler 확인. modify 시 파일 제거 구현
		int eventId = (Integer) Common.getParameter(request, "eventId",0);
		String allowFileTypes = "jpg,gif,png";
		FileInfo fileInfo = new FileInfo(); 
		if(!attachFile.isEmpty()){
			fileInfo = fileService.upload(attachFile, serverFilePath, "banner");
			String fileExt = fileInfo.getOriginName().substring(fileInfo.getOriginName().lastIndexOf(".") + 1 , fileInfo.getOriginName().length()).toLowerCase();
			
			BufferedImage originalImage = ImageIO.read(new File(serverFilePath + fileInfo.getAbsolutePath().replace("/upfiles" , "")));
			int height = originalImage.getHeight();
			int width = originalImage.getWidth();
			
			if( height > 330 || width > 180)
			{
				Thumbnails
				.of(new File(serverFilePath + fileInfo.getAbsolutePath().replace("/upfiles" , "")))
				.sourceRegion(Positions.CENTER, 330, 180)
				.size(330,180)
				.outputQuality(1.0f)
				.outputFormat(fileExt).toFiles(Rename.NO_CHANGE);
			}
		}
		
		model.addAttribute("fileInfo", fileInfo);
		model.addAttribute("msg","aa");
		
		return "fileHandler";
	}
	
	/**
	 * 이벤트 당첨자 리스트
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 * */
	@RequestMapping("/eventWinList.do")
	public String eventWinList(HttpServletRequest request, Model model)
	{
		// get parameters
		int pg = Common.getParameter(request, "pg", 1);
		int skip = (pg - 1) * PAGE_SIZE;
		
		String findStr = Common.getParameter(request, "findStr", "");
		String findMethod = Common.getParameter(request, "findMethod", "");
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("findStr", findStr);
		params.put("findMethod", findMethod);
		
		int eventWinTotalCnt = eventService.getEventWinTotalList(params);
		
		// set pagination
		Pagination pagination = new Pagination(pg, PAGE_SIZE, BLOCK_SIZE, eventWinTotalCnt);
		pagination.setDelimiter("");
		pagination.setPrevLink("Prev");
		pagination.setNextLink("Next");
		
		int eventWinNum = eventWinTotalCnt - skip;
		params.put("skip", skip);
		params.put("pageSize", PAGE_SIZE);
		
		List<EventWin> eventWinList = eventService.getEventWinList(params);
		
		model.addAttribute("findStr", findStr);
		model.addAttribute("findMethod", findMethod);
		
		model.addAttribute("eventWinNum", eventWinNum);
		model.addAttribute("eventWinTotalCnt", eventWinTotalCnt);
		model.addAttribute("eventWinList", eventWinList);

		
		// assign
		model.addAttribute("pathTitle", "이벤트 당첨자 관리");
		model.addAttribute("path", "이벤트 당첨자  / 리스트");
		model.addAttribute("paging", pagination.print());
		model.addAttribute("pg", pg);
		
		return "admin/event/eventWinList";
	}
	
	
	/**
	 * 이벤트 당첨자 입력 / 수정 폼
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/eventWinForm.do")
	public String eventWinForm(HttpServletRequest request , Model model) throws Exception
	{
		// get parfameters
		int pg = Common.getParameter(request, "pg", 1);		
		String mode = Common.getParameter(request, "mode", "add");

		int winId = Common.getParameter(request, "winId", 0);
		EventWin eventWin = new EventWin();
		
		List<Event> EventSelectList = eventService.getEventSelectList();
		
		if(mode.equals("modify")){
			eventWin = eventService.getEventWinDetail(winId);
		}
		
		model.addAttribute("winId", winId);
		model.addAttribute("eventWin", eventWin);
		model.addAttribute("mode", mode);
		model.addAttribute("EventSelectList", EventSelectList);
		
		model.addAttribute("pathTitle", "이벤트 당첨자 관리");
		model.addAttribute("path", "이벤트 당첨자 관리 등록/수정");
		model.addAttribute("pg", pg);
		
		return "admin/event/eventWinForm";
	}
	
	/**
	 * 이벤트 당첨자 등록
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="eventWinAction" , method=RequestMethod.POST)
	public String eventWinAction(HttpServletRequest request , Model model) throws Exception
	{
		String returnUrl = "";
		
		// get parfameters
		int pg = Common.getParameter(request, "pg", 1);		
		String mode = Common.getParameter(request, "mode", "add");

		// 관리자 타입에 따라서 조회 권한 부여
		HttpSession session = (HttpSession) request.getSession();
		Member adminMember = (Member) session.getAttribute("adminMember");
		
		String regId = adminMember.getUserId();
		String modifyId = adminMember.getUserId();
		
		int winId = Common.getParameter(request, "winId", 0);
		int eventId = Common.getParameter(request, "eventId", 0);
		String title = Common.getParameter(request, "title", "");
		String contents = Common.getParameterNoXSS(request, "contents", "");
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("winId", winId);
		params.put("eventId", eventId);
		params.put("regId", regId);
		params.put("title", title);
		params.put("contents", contents);
		params.put("modifyId", modifyId);
		
		EventWin eventDupChk = eventService.getEventWinDup(eventId);
		if(eventDupChk.getEventCnt()>0 && eventDupChk.getWinId()!= winId)
			throw new CommonException("이미 등록된 이벤트가  있습니다." , CommonException.HISTORY_BACK);
		
		if(mode.equals("add"))
			eventService.insertEventWin(params);
		else if(mode.equals("modify")){
			eventService.updateEventWin(params);
		}else if(mode.equals("delete")){
			eventService.deleteEventWin(winId);
		}
		
		return returnUrl = "redirect:eventWinList.do";
	}
	
	/**
	 * 댓글을 삭제한다.
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/commentDelete.do" , method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> commentDelete(HttpServletRequest request , Model model) throws Exception
	{
		Map<String,Object> result = new HashMap<String,Object>();
		
		// get sessoin
		HttpSession session = (HttpSession) request.getSession();
		Member adminMember = (Member) session.getAttribute("adminMember");
		
		if( adminMember == null )
		{
			result.put("result" , "FAILURE");
			result.put("resultType" , "LOGIN");
			result.put("msg" , "로그인 후 이용가능합니다.");
			return result;
		}
		
		// get parameter
		int commentId = Common.getParameter(request, "commentId", 0);
		
		// validation check
		if( commentId == 0 )
		{
			result.put("result" , "FAILURE");
			result.put("resultType" , "PARAMETER");
			result.put("msg" , "파라미터 에러");
			return result;
		}
		
		// make query paraemters
		boardService.deleteComment(commentId);

		// assign
		result.put("result" , "SUCCESS");
		
		return result;
	}
	
		
}
