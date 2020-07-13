package kr.co.kumsung.thub.controller.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import kr.co.kumsung.thub.domain.Article;
import kr.co.kumsung.thub.domain.BoardCategory;
import kr.co.kumsung.thub.domain.BoardConfig;
import kr.co.kumsung.thub.domain.Comment;
import kr.co.kumsung.thub.domain.FileInfo;
import kr.co.kumsung.thub.domain.Member;
import kr.co.kumsung.thub.exception.CommonException;
import kr.co.kumsung.thub.service.BoardService;
import kr.co.kumsung.thub.service.FileService;
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

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

/**
 * 통합게시판관리 컨트롤러
 * @author mikelim
 *
 */
@Component("adminBoardController")
@Controller
@RequestMapping("/admin/board")
public class BoardController {

	@Autowired
	private BoardService boardService;
	
	@Autowired
	private FileService fileService;
	
	private static final int PAGE_SIZE = 10;
	private static final int BLOCK_SIZE = 10;
	
	/**
	 * 게시판의 리스트
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list.do")
	public String list(HttpServletRequest request , Model model) throws Exception
	{
		
		HttpSession session = (HttpSession) request.getSession();
		Member adminMember = (Member) session.getAttribute("adminMember");
		String userId = adminMember.getUserId();
		
		// get parameters
		int boardId = Common.getParameter(request, "boardId", 0);
		int pg = Common.getParameter(request, "pg", 1);
		int skip = (pg - 1) * PAGE_SIZE;
		String findMethod = Common.getParameter(request, "findMethod", "");
		String findStr = Common.getParameter(request, "findStr", "");
		String findType = Common.getParameter(request, "findType", "");
		
		// check validation
		if( boardId == 0 )
			throw new CommonException("정상적인 접근이 아닙니다." , CommonException.HISTORY_BACK);
		
		// make query parameter
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("boardId" , boardId);
		params.put("findMethod" , findMethod);
		params.put("findStr" , findStr);
		params.put("findType" , findType);
		params.put("userId", userId);
		params.put("skip" , skip);
		params.put("pageSize" , PAGE_SIZE);
		
		// get data
		BoardConfig boardConfig = boardService.getBoardConfig(boardId);
		int totalArticle = boardService.getTotalArticle(params);
		int articleNum = totalArticle - skip;
		List<Article> articles = boardService.getArticleList(params);
		
		// set pagination
		Pagination pagination = new Pagination(pg, PAGE_SIZE, BLOCK_SIZE, totalArticle);
		pagination.setDelimiter("");
		pagination.setPrevLink("Prev");
		pagination.setNextLink("Next");
		
		// assign
		model.addAttribute("pathTitle", boardConfig.getBoardName());
		model.addAttribute("path", boardConfig.getBoardName() + " / 리스트");
		
		model.addAttribute("boardId", boardId);
		model.addAttribute("pg", pg);
		model.addAttribute("findMethod", findMethod);
		model.addAttribute("findStr", findStr);
		model.addAttribute("findType", findType);
		
		model.addAttribute("boardConfig", boardConfig);
		model.addAttribute("totalArticle", totalArticle);
		model.addAttribute("articleNum", articleNum);
		model.addAttribute("articles", articles);
		
		model.addAttribute("paging", pagination.print());
		
		return "admin/board/list";
	}
	
	/**
	 * 게시글 폼
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/form.do")
	public String form(HttpServletRequest request, Model model) throws Exception
	{
		// get parameters
		int boardId = Common.getParameter(request, "boardId", 0);
		int articleId = Common.getParameter(request, "articleId", 0);
		int pg = Common.getParameter(request, "pg", 1);
		String mode = Common.getParameter(request, "mode", "add");
		String findMethod = Common.getParameter(request, "findMethod", "");
		String findStr = Common.getParameter(request, "findStr", "");
		String isUse = Common.getParameter(request, "isUse", "");
		
		if( articleId != 0 && !mode.equals("reply"))
			mode = "modify";
		
		// check validation
		if( boardId == 0 )
			throw new CommonException("정상적인 접근이 아닙니다." , CommonException.HISTORY_BACK);
		
		// get data
		BoardConfig boardConfig = boardService.getBoardConfig(boardId);
		List<BoardCategory> boardCategories = boardService.getBoardCategoryList(boardId);
		Article article = boardService.getArticle(articleId);
		List<FileInfo> attachments = new ArrayList<FileInfo>();
		
		List<BoardCategory> sub_category = boardService.getsub_category();
		
		if( mode.equals("add") )
		{
			// 신규로 등록할 경우 article object를 기본 셋팅한다.
			article = new Article();
		}
		else if( mode.equals("reply") )
		{
			// reply 일 경우 기본 값만 저장한다.
			article.setSubject(String.format(" ::[Re] %s" , article.getSubject()));
			article.setIsNotice("N");
			article.setIntroduce("");
			article.setThumbnail(0);
			article.setThumbnailPath("");
			article.setContents(String.format("<p>----- %s님께서 등록하신 원본글</p>%s" , 
							article.getUserId() , 
							article.getContents()));
		}
		else
		{
			// 첨부파일의 데이타를 가지고 온다.
			if( !Validate.isEmpty(article.getAttachment()) )
					attachments = fileService.getBoardFiles(article.getAttachment());
		}
		
		// assign
		model.addAttribute("pathTitle", boardConfig.getBoardName());
		model.addAttribute("path", boardConfig.getBoardName() + " / 폼");
		
		model.addAttribute("boardId", boardId);		
		model.addAttribute("articleId", articleId);
		model.addAttribute("pg", pg);
		model.addAttribute("mode", mode);
		model.addAttribute("findMethod", findMethod);
		model.addAttribute("findStr", findStr);
		
		model.addAttribute("boardConfig", boardConfig);
		model.addAttribute("boardCategories", boardCategories);
		model.addAttribute("article", article);
		model.addAttribute("attachments", attachments);
		
		model.addAttribute("sub_category", sub_category);
		model.addAttribute("isUse", isUse);
		
		
		return "admin/board/form";
	}
	
	/**
	 * 글에 대한 처리를 진행한다.
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="articleAction.do" , method=RequestMethod.POST)
	public String articleAction(HttpServletRequest request , Model model) throws Exception
	{
		HttpSession session = (HttpSession) request.getSession();
		Member adminMember = (Member) session.getAttribute("adminMember");
		
		// get parameters
		int boardId = Common.getParameter(request, "boardId", 0);
		int articleId = Common.getParameter(request, "articleId", 0);
		int pg = Common.getParameter(request, "pg", 1);		
		String findMethod = Common.getParameter(request, "findMethod", "");
		String findStr = Common.getParameter(request, "findStr", "");
		String mode = Common.getParameter(request, "mode", "");
		
		int categoryId = Common.getParameter(request, "categoryId", 0);
		String userId = adminMember.getUserId();
		String isNotice = Common.getParameter(request, "isNotice", "N");
		String isSecret = Common.getParameter(request, "isSecret", "N");
		String linkUrl = Common.getParameter(request, "linkUrl", "");
		String movieUrl = Common.getParameter(request, "movieUrl", "");
		String subject = Common.getParameter(request, "subject", "");
		String introduce = Common.getParameter(request, "introduce", "");
		String contents = Common.getParameterNoXSS(request, "contents", "");
		int thumbnail = Common.getParameter(request, "thumbnail", 0);
		String attachment = Common.getParameter(request, "attachment", "");
		String etc = Common.getParameter(request, "etc", "");
		String keywords = Common.getParameter(request, "keywords", "");
		String sub_category = Common.getParameter(request, "sub_category", "");
		String isUse = Common.getParameter(request, "isUse", "");


		// check validation
		if( boardId == 0  || Validate.isEmpty(mode) )
			throw new CommonException("정상적인 접근이 아닙니다." , CommonException.HISTORY_BACK);
		
		// get board config
		BoardConfig boardConfig = boardService.getBoardConfig(boardId);
		
		// 설정에 따른 validation check
		if( !mode.equals("delete") )
		{	
			if( boardConfig.getUseCategory().equals("Y") && categoryId == 0 ) throw new CommonException("카테고리를 선택하여주십시오." , CommonException.HISTORY_BACK);
			if( boardConfig.getUseLink().equals("Y") && Validate.isEmpty(linkUrl) ) throw new CommonException("외부 링크 주소를 입력하여주십시오." , CommonException.HISTORY_BACK);
			if( boardConfig.getUseMovie().equals("Y") && Validate.isEmpty(movieUrl) ) throw new CommonException("동영상 주소를 입력하여주십시오." , CommonException.HISTORY_BACK);
		}
		
		// set default parameter
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("boardId" , boardId);
		params.put("userId" , userId);
		params.put("categoryId" , categoryId);
		params.put("isNotice" , isNotice);
		params.put("isSecret" , isSecret);
		params.put("linkUrl" , linkUrl);
		params.put("movieUrl" , movieUrl);
		params.put("subject" , subject);
		params.put("introduce" , introduce);
		params.put("thumbnail" , thumbnail);
		params.put("contents" , contents);
		params.put("attachment" , attachment);
		params.put("etc" , etc);
		params.put("keywords" , keywords);
		params.put("remoteIp" , (String) request.getRemoteAddr());
		params.put("sub_category" , sub_category);
		params.put("isUse" , isUse);
		
		
		String returnUrl = "";
		
		if( mode.equals("add") )
		{
			int groupId = boardService.getMaxGroupId(boardId);
			
			// add to parameter
			params.put("groupId" , groupId);
			params.put("steps" , "");
			
			boardService.insertArticle(params);
			returnUrl = String.format("redirect:list.do?boardId=%d&pg=%d&findMethod=%s&findStr=%s" ,
									boardId ,
									pg ,
									findMethod ,
									findStr
								);
		}
		else if( mode.equals("modify") )
		{
			params.put("articleId" , articleId);
			
			boardService.updateArticle(params);
			returnUrl = String.format("redirect:form.do?articleId=%d&boardId=%d&pg=%d&findMethod=%s&findStr=%s" ,
									articleId ,
									boardId ,
									pg ,
									findMethod ,
									findStr
								);
		}
		else if( mode.equals("reply") )
		{
			// get arcile
			Article article = boardService.getArticle(articleId);
			
			int groupId = article.getGroupId();
			String steps = boardService.getNewSteps(boardId, article.getGroupId() , article.getSteps());
			
			// add to parameter
			params.put("groupId" , groupId);
			params.put("steps" , steps);
			
			boardService.insertArticle(params);
			returnUrl = String.format("redirect:list.do?boardId=%d&pg=%d&findMethod=%s&findStr=%s" ,
									boardId ,
									pg ,
									findMethod ,
									findStr
								);
			
		}
		else if( mode.equals("delete") )
		{
			// 데이타를 삭제한다.
			params.put("articleId" , articleId);
			
			boardService.deleteArticle(params);
			returnUrl = String.format("redirect:list.do?boardId=%d&pg=%d&findMethod=%s&findStr=%s" ,
									boardId ,
									pg ,
									findMethod ,
									findStr
								);
		}
		
		return returnUrl;
	}
	
	/**
	 * 전체 게시판의 리스트
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/boardList.do")
	public String boardList(HttpServletRequest request , Model model) throws Exception
	{
		// get data
		List<BoardConfig> list = boardService.getBoardList(new HashMap<String,Object>());
		
		model.addAttribute("pathTitle", "통합게시판관리");
		model.addAttribute("path", "통합게시판관리 / 리스트");
		model.addAttribute("list", list);
		
		return "admin/board/boardList";
	}
	
	/**
	 * 게시판 설정에 대한 폼
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/boardForm.do")
	public String boardForm(HttpServletRequest request , Model model) throws Exception
	{
		// get parameters
		int boardId = Common.getParameter(request, "boardId", 0);
		String mode = (boardId == 0) ? "add" : "modify";
		
		// get data
		BoardConfig boardConfig = new BoardConfig();
		List<BoardCategory> boardCategories = new ArrayList<BoardCategory>();
		
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
			boardConfig = boardService.getBoardConfig(boardId);
			boardCategories = boardService.getBoardCategoryList(boardId);
		}
		
		// add to model
		model.addAttribute("pathTitle", "통합게시판관리");
		model.addAttribute("path", "통합게시판관리 / 입력");		
		model.addAttribute("mode", mode);
		model.addAttribute("boardId", boardId);
		model.addAttribute("boardConfig", boardConfig);
		model.addAttribute("boardCategories", boardCategories);
		
		return "admin/board/boardForm";
	}
	
	/**
	 * 게시판 설정의 저장,갱신,삭제를 위한 handling
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/boardFormAction.do" , method=RequestMethod.POST)
	public String boardFormAction(HttpServletRequest request , Model model) throws Exception
	{
		// get parameters
		int boardId = Common.getParameter(request, "boardId", 0);
		String mode = Common.getParameter(request, "mode", "");
		String boardName = Common.getParameter(request, "boardName", "");
		String boardType = Common.getParameter(request, "boardType", "");
		String boardFlag = Common.getParameter(request, "boardFlag", "");
		String headerSection = Common.getParameterNoXSS(request, "headerSection", "");
		String footerSection = Common.getParameterNoXSS(request, "footerSection", "");
		String categoryItems = Common.getParameter(request, "categoryItems", "");
		String useCategory = Common.getParameter(request, "useCategory", "");		
		String useWrite = Common.getParameter(request, "useWrite", "");
		String useReply = Common.getParameter(request, "useReply", "");
		String useFileUpload = Common.getParameter(request, "useFileUpload", "");
		String useComment = Common.getParameter(request, "useComment", "");
		String useMovie = Common.getParameter(request, "useMovie", "");
		String useLink = Common.getParameter(request, "useLink", "");
		String useRecommend = Common.getParameter(request, "useRecommend", "");
		String useSns = Common.getParameter(request, "useSns", "");
		String useAnnmsList = Common.getParameter(request, "useAnnmsList", "");
		String useAnnmsView = Common.getParameter(request, "useAnnmsView", "");
		String allowFileTypes = Common.getParameter(request, "allowFileTypes", "");
		int allowFileCount = Common.getParameter(request, "allowFileCount", 0);
		int allowFileSize = Common.getParameter(request, "allowFileSize", 0);
		String modifyRegDate = Common.getParameter(request, "modifyRegDate", "");
		String regId = "mikelim";
		String modifyId = "mikelim";
		
		// vadidation check
		if( Validate.isEmpty(mode) 
				|| Validate.isEmpty(boardName)
				|| Validate.isEmpty(boardType))
		{
			throw new CommonException("정상적인 접근이 아닙니다." , CommonException.HISTORY_BACK);
		}
		
		// make default parameters
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("boardName" , boardName);
		params.put("boardType" , boardType);
		params.put("boardFlag" , boardFlag);
		params.put("headerSection" , headerSection);
		params.put("footerSection" , footerSection);
		params.put("useCategory" , useCategory);
		params.put("useWrite" , useWrite);
		params.put("useReply" , useReply);
		params.put("useFileUpload" , useFileUpload);
		params.put("useComment" , useComment);
		params.put("useMovie" , useMovie);
		params.put("useLink" , useLink);
		params.put("useRecommend" , useRecommend);
		params.put("useSns" , useSns);
		params.put("useAnnmsList" , useAnnmsList);
		params.put("useAnnmsView" , useAnnmsView);
		params.put("allowFileTypes" , allowFileTypes);
		params.put("allowFileCount" , allowFileCount);
		params.put("allowFileSize" , allowFileSize);
		params.put("regId" , regId);
		params.put("modifyId" , modifyId);
		params.put("modifyRegDate", modifyRegDate);
		
		// return page
		String redirect = "";
		
		if( mode.equals("add") )
		{	
			boardId = boardService.insertBoard(params);
			
			redirect = "/admin/board/boardList.do";
		}
		else if (mode.equals("modify") )
		{
			// add parameter
			params.put("boardId" , boardId);
			
			boardService.updateBoard(params);
			
			redirect = "/admin/board/boardForm.do?boardId=" + boardId;
		}
		
		// category에 대하여 처리한다.
		if( useCategory.equals("Y") && !Validate.isEmpty(categoryItems) )
			boardService.mngBoardCategory(boardId , categoryItems);
		
		return String.format("redirect:%s" , redirect);
	}
	
	/**
	 * 게시판 카테고리를 삭제한다.
	 * @param request
	 * @param model
	 * @return JSON
	 */
	@RequestMapping(value="/deleteCategory.do" , method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> deleteCategory(HttpServletRequest request , Model model) throws Exception
	{	
		Map<String,Object> result = new HashMap<String,Object>();
		
		// get parameters
		int boardId = Common.getParameter(request, "boardId", 0);
		int categoryId = Common.getParameter(request, "categoryId", 0);
		
		if( boardId == 0 || categoryId == 0 )
		{
			result.put("result" , "FAILURE");
			result.put("msg" , "정상적인 파라미터가 아닙니다.");
			
			return result;
		}
		
		// processing
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("boardId" , boardId);
		params.put("categoryId" , categoryId);
		
		boardService.deleteBoardCategory(params);
		
		// set result
		result.put("result" , "SUCCESS");
		result.put("msg" , "성공");
		
		return result;
	}

	/**
	 * 댓글 리스트 관리
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/commentList.do")
	public String commentList(HttpServletRequest request , Model model) throws Exception
	{
		final int PAGE_SIZE = 10;
		final int BLOCK_SIZE = 10;
		
		// get parameter
		int pg = Common.getParameter(request, "pg", 1);
		int skip = (pg - 1) * PAGE_SIZE;
		int findBoardId = Common.getParameter(request, "findBoardId", 0);
		
		// make query parameters
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("findBoardId" , findBoardId);
		params.put("skip" , skip);
		params.put("pageSize" , PAGE_SIZE);
		
		// get comment list
		int totalCount = (Integer) boardService.getTotalCommentListCount(params);
		int articleNum = totalCount - skip;
		List<BoardConfig> boards = (List<BoardConfig>) boardService.getBoardList(params);
		List<Comment> comments = (List<Comment>) boardService.getTotalCommentList(params);
			
		// pagination
		Pagination pagination = new Pagination(pg, PAGE_SIZE, BLOCK_SIZE, totalCount);
		pagination.initialize();
		
		// assign
		model.addAttribute("pathTitle", "댓글관리");
		model.addAttribute("path", "티칭백과 > 통합게시판관리");
		
		model.addAttribute("pg", pg);
		model.addAttribute("findBoardId", findBoardId);
		
		model.addAttribute("boards", boards);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("articleNum" , articleNum);
		model.addAttribute("comments", comments);
		model.addAttribute("paging", pagination.print());
		
		return "admin/board/commentList";
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
