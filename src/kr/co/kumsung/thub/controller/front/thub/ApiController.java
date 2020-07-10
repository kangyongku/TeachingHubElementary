package kr.co.kumsung.thub.controller.front.thub;

import java.io.BufferedOutputStream;
import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import kr.co.kumsung.thub.domain.Article;
import kr.co.kumsung.thub.domain.Book;
import kr.co.kumsung.thub.domain.Category;
import kr.co.kumsung.thub.domain.Cd;
import kr.co.kumsung.thub.domain.Comment;
import kr.co.kumsung.thub.domain.Data;
import kr.co.kumsung.thub.domain.FileInfo;
import kr.co.kumsung.thub.domain.Folder;
import kr.co.kumsung.thub.domain.Member;
import kr.co.kumsung.thub.domain.Multimedia;
import kr.co.kumsung.thub.exception.CommonException;
import kr.co.kumsung.thub.service.BoardService;
import kr.co.kumsung.thub.service.CategoryService;
import kr.co.kumsung.thub.service.EventService;
import kr.co.kumsung.thub.service.FileService;
import kr.co.kumsung.thub.service.LearningService;
import kr.co.kumsung.thub.service.MemberService;
import kr.co.kumsung.thub.service.MultimediaService;
import kr.co.kumsung.thub.service.ScrapService;
import kr.co.kumsung.thub.service.SmartService;
import kr.co.kumsung.thub.util.Common;
import kr.co.kumsung.thub.util.ResultMap;
import kr.co.kumsung.thub.util.Validate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 모든 Ajax 통신에 대한 Action을 담당한다.
 * return 형태는 JSON으로 통일한다.
 * @author mikelim
 *
 */
@Component("thubApiController")
@Controller
@RequestMapping("/api")
public class ApiController {

	@Autowired
	private BoardService boardService;
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private ScrapService scrapService;
	
	@Autowired
	private LearningService learningService;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private MultimediaService multimediaService;
	
	@Autowired
	private SmartService smartService;
	
	@Autowired
	private EventService eventService;
	
	/**
	 * 로그인 체크 여부
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping("/isLogin.do")
	public @ResponseBody Map<String,Object> isLogin(HttpServletRequest request) throws Exception
	{
		Map<String,Object> result = new HashMap<String,Object>();
		
		// get session
		HttpSession session = (HttpSession) request.getSession();
		Member member = (Member) session.getAttribute("member");
		
		if( member == null )
			result.put("login" , false);
		else
			result.put("login" , true);
		
		return result;
	}
	
	/**
	 * 인증교사 로그인 체크 여부
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping("/isTeacherLogin.do")
	public @ResponseBody Map<String,Object> isTeacherLogin(HttpServletRequest request) throws Exception
	{
		Map<String,Object> result = new HashMap<String,Object>();
		
		// get session
		HttpSession session = (HttpSession) request.getSession();
		Member member = (Member) session.getAttribute("member");
		
		if( member == null )
			result.put("login" , false);
		else
		{
			if( member.getIsFinallyAuth().equals("Y") )
				result.put("login" , true);
			else
				result.put("login" , false);
		}
		
		return result;
	}
	
	/**
	 * 교수학습자료의 데이타를 가지고 온다.
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value="/getLearningBookData.do" , method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> getLearningBookData(HttpServletRequest request) throws Exception
	{
		Map<String,Object> result = new HashMap<String,Object>();
		
		// get session
		HttpSession session = (HttpSession) request.getSession();
		Member member = (Member) session.getAttribute("member");
		
		if( member == null 
				|| !member.getIsFinallyAuth().equals("Y"))
		{
			result.put("result" , "FAILURE");
			result.put("msg" , "접근권한이 없습니다.");
			return result;
		}
		
		int bookId = Common.getParameter(request, "bookId", 0);
		Book book = learningService.getBook(bookId);
		
		result.put("result" , "SUCCESS");
		result.put("url" , "http://thub.kumsung.co.kr/upfiles" + book.getTeacherCdView());
		
		return result;
	}
	
	/**
	 * 교수학습자료의 데이타를 가지고 온다.
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value="/learning/deleteLearningData.do" , method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> deleteLearningData(HttpServletRequest request) throws Exception
	{
		Map<String,Object> result = new HashMap<String,Object>();
		
		// get session
		HttpSession session = (HttpSession) request.getSession();
		Member member = (Member) session.getAttribute("member");
		
		if( member == null 
				|| !member.getIsFinallyAuth().equals("Y"))
		{
			result.put("result" , "FAILURE");
			result.put("msg" , "접근권한이 없습니다.");
			return result;
		}
		
		int dataId = Common.getParameter(request, "dataId", 0);
		
		learningService.deleteLearningData(dataId);
		
		result.put("result" , "SUCCESS");
		
		return result;
	}

	/**
	 * 데이타를 가지고 온다.
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/learning/getData.do")
	public @ResponseBody Map<String,Object> getData(HttpServletRequest request , Model model) throws Exception
	{
		Map<String,Object> result = new HashMap<String,Object>();
		
		// get parameter
		int dataId = Common.getParameter(request, "dataId", 0);
		
		if( dataId == 0 )
		{
			result.put("result" , "FAILURE");
			result.put("resultType" , "PARAMETER");
			result.put("msg" , "정상적인 파라미터가 아닙니다.");
			return result;
		}
		
		// make query parameter
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("dataId" , dataId);
		
		// get data
		Data data = learningService.getData(params);
		
		result.put("result" , "SUCCESS");
		result.put("data" , data);
		
		return result;
	}
	
	/**
	 * 카테고리에 포함된 도서 목록을 가지고 온다.
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/learning/getBooksByCategory.do")
	public @ResponseBody Map<String,Object> getBooksByCategory(HttpServletRequest request , Model model) throws Exception
	{
		Map<String,Object> result = new HashMap<String,Object>();
		
		// get parameter
		String category = Common.getParameter(request, "category", "");
		String course = Common.getParameter(request, "course", "");
		
		System.out.println("category : "+category);
		if( Validate.isEmpty(category) )
		{
			result.put("result" , "FAILURE");
			result.put("resultType" , "PARAMETER");
			result.put("msg" , "정상적인 파라미터가 아닙니다.");
			return result;
		}
		
		// get data
		List<Book> books = learningService.getBookListByCategory(category, course);
		
		//교육과정
		List<ResultMap> Courses = (List<ResultMap>) learningService.getCourseListByCategory(category);
		
		
		result.put("result" , "SUCCESS");
		result.put("books" , books);
		result.put("courses" , Courses);
		
		return result;
	}
	
	@RequestMapping("/learning/getSubjectByCategory.do")
	public @ResponseBody Map<String,Object> getSubjectByCategory(HttpServletRequest request , Model model) throws Exception
	{
		Map<String,Object> result = new HashMap<String,Object>();
		
		// get parameter
		String category = Common.getParameter(request, "category", "");
		// GNB 과목 데이타를 가지고 온다.
		List<Category> subjects = (List<Category>) categoryService.getChildren(category);
		
		// 최초 과목의 도서 리스트를 가지고 온다.
		Category Category = (Category) subjects.get(0);

		List<Book> books = (List<Book>) learningService.getBookListByCategory(Category.getCategory());
		List<ResultMap> Courses = (List<ResultMap>) learningService.getCourseListByCategory(Category.getCategory());
		System.out.println("category : "+category);
		if( Validate.isEmpty(category) )
		{
			result.put("result" , "FAILURE");
			result.put("resultType" , "PARAMETER");
			result.put("msg" , "정상적인 파라미터가 아닙니다.");
			return result;
		}
		
		
		result.put("result" , "SUCCESS");
		result.put("books" , books);
		result.put("subjects" , subjects);
		result.put("courses" , Courses);
		
		return result;
	}
	
	/**
	 * 게시글을 추천한다.
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/article/getAttachments.do")
	public @ResponseBody Map<String,Object> articleGetAttachments(HttpServletRequest request , Model model) throws Exception
	{
		Map<String,Object> result = new HashMap<String,Object>();
		
		// get parameters
		int articleId = Common.getParameter(request, "articleId", 0);
		
		if( articleId == 0 )
		{
			result.put("result" , "FAILURE");
			result.put("resultType" , "PARAMETER");
			result.put("msg" , "파라미터가 올바르지 않습니다.");
			return result;
		}
		
		// get board data
		Article article = boardService.getArticle(articleId);
		
		// get attachments
		List<FileInfo> attachments = fileService.getBoardFiles(article.getAttachment());
		
		result.put("result" , "SUCCESS");
		result.put("attachments" , attachments);
		
		return result;
	}
	
	/**
	 * 게시글을 추천한다.
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/article/recommend.do")
	public @ResponseBody Map<String,Object> articleRecommend(HttpServletRequest request , Model model) throws Exception
	{
		Map<String,Object> result = new HashMap<String,Object>();
		
		// get session data
		HttpSession session = (HttpSession) request.getSession();
		Member member = (Member) session.getAttribute("member");
		
		// validation check
		if( member == null)
		{
			result.put("result" , "FAILURE");
			result.put("resultType" , "LOGIN");
			result.put("msg" , "로그인이 필요합니다.");
			return result;
		}
		
		// get parameters
		int articleId = Common.getParameter(request, "articleId", 0);
		String userId = member.getUserId();
		String remoteIp = (String) request.getRemoteAddr();
		
		// validation check
		if( articleId == 0 )
		{
			result.put("result" , "FAILURE");
			result.put("resultType" , "PARAMETER");
			result.put("msg" , "파라미터 전송 오류");
			return result;
		}
		
		// make query parameters
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("articleId" , articleId);
		params.put("userId" , userId);
		params.put("remoteIp" , remoteIp);
		
		// check duplicated
		if( boardService.getRecommendLogCount(params) > 0 )
		{
			result.put("result" , "FAILURE");
			result.put("resultType" , "DUPLICATED");
			result.put("msg" , "이미 추천을 하였습니다.");
			return result;
		}else{
			// insert & update data
			boardService.insertRecommendLog(params);
			boardService.updateArticleRecommend(articleId);
			
			// result assign
			result.put("result" , "SUCCESS");
		}
		

		
		
		return result;
	}
	
	/**
	 * 게시글의 댓글을 입력한다.
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/article/addComment.do" , method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> addComment(HttpServletRequest request , Model model) throws Exception
	{
		Map<String,Object> result = new HashMap<String,Object>();
		
		// get session 
		HttpSession session = (HttpSession) request.getSession();
		Member member = (Member) session.getAttribute("member");
		
		if( member == null )
		{
			result.put("result" , "FAILURE");
			result.put("resultType" , "LOGIN");
			result.put("msg" , "로그인하여주십시오.");
			return result;
		}
		
		// get parameters
		int boardId = Common.getParameter(request, "boardId", 0);
		int articleId = Common.getParameter(request, "articleId", 0);
		int commentId = Common.getParameter(request, "commentId", 0);
		String comment = Common.getParameter(request, "comment"	, "");
		int eventId = Common.getParameter(request, "eventId", 0);
		String userId = member.getUserId();
		
		// validation check
		if( Validate.isEmpty(comment)
				|| Validate.isEmpty(userId)
				|| boardId == 0
				|| articleId == 0)
		{
			result.put("result" , "FAILURE");
			result.put("resultType" , "PARAMETER");
			result.put("msg" , "정상적인 요청이 아닙니다.");
			return result;
		}
		
		// get group id
		int groupId = boardService.getCommentGroupId(boardId);
		int depth = 1;
		
		if( commentId > 0 )
		{
			// commentId가 0보다 크므로 reply이다.
			// 기존의 group id를 가지고 온다.
			Comment parentComment = boardService.getComment(commentId);
			groupId = parentComment.getGroupId();
			depth = 2;
		}
		
		// make query parameters
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("boardId" , boardId);
		params.put("groupId" , groupId);
		params.put("articleId" , articleId);
		params.put("eventId" , eventId);
		params.put("depth" , depth);
		params.put("userId" , userId);
		params.put("comment" , comment);
		params.put("remoteIp" , (String) request.getRemoteAddr());
		
		commentId = boardService.insertComment(params);
		
		// assign
		result.put("result" , "SUCCESS");
		result.put("commentId" , commentId);
		
		return result;
	}
	
	/**
	 * 게시글의 댓글을 삭제한다.
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/article/deleteComment.do" , method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> deleteComment(HttpServletRequest request , Model model) throws Exception
	{
		Map<String,Object> result = new HashMap<String,Object>();
		
		// get session 
		HttpSession session = (HttpSession) request.getSession();
		Member member = (Member) session.getAttribute("member");
		
		if( member == null )
		{
			result.put("result" , "FAILURE");
			result.put("resultType" , "LOGIN");
			result.put("msg" , "로그인하여주십시오.");
			return result;
		}
		
		// get parameters
		int commentId = Common.getParameter(request, "commentId", 0);
		int boardId = Common.getParameter(request, "boardId", 0);
		String userId = member.getUserId();
		
		// validation check
		if( commentId == 0)
		{
			result.put("result" , "FAILURE");
			result.put("resultType" , "PARAMETER");
			result.put("msg" , "파라미터가 올바르지 않습니다.");
			return result;
		}
			
		// get data
		Comment comment = boardService.getComment(commentId);
		
		// 현재 관리자가 사용자인지 관리자인지 체크한다.
		boolean isAccessBoard = boardService.isAccessBoardAdmin(member, boardId);
		
		if( isAccessBoard == true
				|| comment.getUserId().equals(member.getUserId()))
		{
			boardService.deleteComment(commentId);
		}
		else
		{
			result.put("result" , "FAILURE");
			result.put("resultType" , "AUTH");
			result.put("msg" , "접근권한이 없습니다.");
			return result;
		}
		
		// assign
		result.put("result" , "SUCCESS");
		result.put("commentId" , commentId);
		
		return result;
	}
	
	/**
	 * 스크랩 폴더를 생성한다.
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/scrap/addScrapFolder.do")
	public @ResponseBody Map<String,Object> addScrapFolder(HttpServletRequest request , Model model) throws Exception
	{
		Map<String,Object> result = new HashMap<String,Object>();
		
		// get session
		HttpSession session = (HttpSession) request.getSession();
		Member member = (Member) session.getAttribute("member");
		
		if( member == null )
		{
			result.put("result" , "FAILURE");
			result.put("resultType" , "LOGIN");
			result.put("msg" , "로그인이 필요합니다.");
			return result;
		}
	
		// get parameters
		String name = Common.getParameter(request, "name", "");
		
		// validation check
		if( Validate.isEmpty(name) )
		{
			result.put("result" , "FAILURE");
			result.put("resultType" , "PARAMETER");
			result.put("msg" , "파라미터 오류");
			return result;
		}
		
		// make query parameter
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("userId" , member.getUserId());
		params.put("name" , name);
		
		// add
		scrapService.insertFolder(params);
		
		// assign
		result.put("result" , "SUCCESS");
		
		return result;
	}
	
	/**
	 * 스크랩을 진행한다.
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/scrap/addScrap.do" , method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> addScrap(HttpServletRequest request , Model model) throws Exception
	{
		Map<String,Object> result = new HashMap<String,Object>();
		
		// session check
		HttpSession session = (HttpSession) request.getSession();
		Member member = (Member) session.getAttribute("member");
		
		if( member == null )
		{
			result.put("result" , "FAILURE");
			result.put("resultType" , "LOGIN");
			result.put("msg" , "로그인이 필요합니다.");
			return result;
		}
		
		// get parameters
		int folderId = Common.getParameter(request, "folderId", 0);
		String scrapType = Common.getParameter(request, "scrapType", "");
		String relationIds = Common.getParameter(request, "relationIds", "");
		
		// validation check
		if( folderId == 0
				|| Validate.isEmpty(scrapType)
				|| Validate.isEmpty(relationIds))
		{
			result.put("result" , "FAILURE");
			result.put("resultType" , "PARAMETER");
			result.put("msg" , "파라미터 에러.");
			return result;
		}
		
		// loop
		String[] tmp = relationIds.split("\\,");
		
		for(String t : tmp)
		{
			// cast to integer
			int relationId = Integer.valueOf(t);
			
			Map<String,Object> params = new HashMap<String,Object>();
			
			params.put("folderId" , folderId);
			params.put("scrapType" , scrapType);
			params.put("userId" , member.getUserId());
			params.put("relationId" , relationId);
			
			// duplication check
			int dupCount = scrapService.isDuplicated(params);
			
			if( dupCount == 0 )
			{
				scrapService.insertScrap(params);
			}
		}
		
		// assign
		result.put("result" , "SUCCESS");
		
		return result;
	}
	
	/**
	 * 스크랩을 삭제한다.
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/scrap/deleteScrap.do" , method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> deleteScrap(HttpServletRequest request , Model model) throws Exception
	{
		Map<String,Object> result = new HashMap<String,Object>();
		
		// get session
		HttpSession session = (HttpSession) request.getSession();
		Member member = (Member) session.getAttribute("member");
		
		if( member == null )
		{
			result.put("result" , "FAILURE");
			result.put("resultType" , "LOGIN");
			result.put("msg" , "로그인 후 이용가능합니다.");
			return result;
		}
		
		// get parameters
		String ids = Common.getParameter(request, "ids", "");
		
		// validation check
		if( Validate.isEmpty(ids) )
		{
			result.put("result" , "FAILURE");
			result.put("resultType" , "PARAMETER");
			result.put("msg" , "파라미터가 올바르지 않습니다.");
			return result;
		}	
		
		// loop
		String[] tmp = ids.split("\\,");
		
		for(String t : tmp)
		{
			int scrapId = Integer.valueOf(t);
			
			Map<String,Object> params = new HashMap<String,Object>();
			
			params.put("scrapId" , scrapId);
			params.put("userId" , member.getUserId());
			
			scrapService.deleteScrap(params);
		}
		
		// assign
		result.put("result" , "SUCCESS");
		
		return result;
	}
	
	/**
	 * 나의 교과서로 설정
	 * 12개까지만 된다.
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/member/addMyBook.do" , method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> addMyBook(HttpServletRequest request , Model model) throws Exception
	{
		Map<String,Object> result = new HashMap<String,Object>();
		
		// session check
		HttpSession session = (HttpSession) request.getSession();
		Member member = (Member) session.getAttribute("member");
		
		if( member == null )
		{
			result.put("result" , "FAILURE");
			result.put("resultType" , "LOGIN");
			result.put("msg" , "로그인 후 이용가능합니다.");
			return result;
		}
		
		// get parameter
		int bookId = Common.getParameter(request, "bookId", 0);
		
		if( bookId == 0 )
		{
			result.put("result" , "FAILURE");
			result.put("resultType" , "PARAMETER");
			result.put("msg" , "파라미터 에러.");
			return result;
		}
		
		// check count
		int totalCount = memberService.getMyBooksCount(member.getUserId());
		
		if( totalCount > 12 )
		{
			result.put("result" , "FAILURE");
			result.put("resultType" , "EXCEED");
			result.put("msg" , "내 교과설정은 최대 12개까지만 가능합니다.");
			return result;
		}
		else
		{
			Map<String,Object> params = new HashMap<String,Object>();
			
			params.put("userId" , member.getUserId());
			params.put("bookId" , bookId);
			
			if( memberService.isDuplicatedMyBook(params) > 0 )
			{
				result.put("result" , "FAILURE");
				result.put("resultType" , "DUPLICATED");
				result.put("msg" , "이미 내 교과로 설정이 되어있는 도서입니다.");
				return result;
			}
			
			memberService.insertMyBook(params);
		}
		
		// assign
		result.put("result" , "SUCCESS");
		
		return result;
	}
	
	/**
	 * 나의 교과서로 설정
	 * 12개까지만 된다.
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/member/addMyBooks.do" , method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> addMyBooks(HttpServletRequest request , Model model) throws Exception
	{
		Map<String,Object> result = new HashMap<String,Object>();
		
		// session check
		HttpSession session = (HttpSession) request.getSession();
		Member member = (Member) session.getAttribute("member");
		
		if( member == null )
		{
			result.put("result" , "FAILURE");
			result.put("resultType" , "LOGIN");
			result.put("msg" , "로그인 후 이용가능합니다.");
			return result;
		}
		
		// get parameter
		String bookIds = Common.getParameter(request, "bookIds", "");
		
		if( Validate.isEmpty(bookIds) )
		{
			result.put("result" , "FAILURE");
			result.put("resultType" , "PARAMETER");
			result.put("msg" , "파라미터 에러.");
			return result;
		}
		
		// check max books
		int totalCount = memberService.getMyBooksCount(member.getUserId());
		
		if( totalCount >= 12 )
		{
			result.put("result" , "FAILURE");
			result.put("resultType" , "EXCEED");
			result.put("msg" , "내 교과설정은 최대 12개까지만 가능합니다.");
			return result;
		}
		
		// check count
		String[] t = bookIds.split("\\,");
		
		for(String tmp : t)
		{
			int bookId = Integer.valueOf(tmp);
			
			totalCount = memberService.getMyBooksCount(member.getUserId());
			
			if( totalCount >= 12 )
			{
				result.put("result" , "FAILURE");
				result.put("resultType" , "EXCEED");
				result.put("msg" , "교과설정이 12개까지 가능하며 12개 까지의 데이타는 저장 완료하였습니다.");
				return result;
			}
			else
			{
				Map<String,Object> params = new HashMap<String,Object>();
				
				params.put("userId" , member.getUserId());
				params.put("bookId" , bookId);
				
				if( memberService.isDuplicatedMyBook(params) == 0 )
					memberService.insertMyBook(params);
			}
		}
		
		// assign
		result.put("result" , "SUCCESS");
		
		return result;
	}
	
	/**
	 * 나의 교과서를 삭제한다.
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/member/removeMyBook.do" , method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> removeMyBook(HttpServletRequest request , Model model) throws Exception
	{
		Map<String,Object> result = new HashMap<String,Object>();
		
		// session check
		HttpSession session = (HttpSession) request.getSession();
		Member member = (Member) session.getAttribute("member");
		
		if( member == null )
		{
			result.put("result" , "FAILURE");
			result.put("resultType" , "LOGIN");
			result.put("msg" , "로그인 후 이용가능합니다.");
			return result;
		}
		
		// get parameter
		int bookId = Common.getParameter(request, "bookId", 0);
		
		if( bookId == 0 )
		{
			result.put("result" , "FAILURE");
			result.put("resultType" , "PARAMETER");
			result.put("msg" , "파라미터 에러.");
			return result;
		}
		
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("userId" , member.getUserId());
		params.put("bookId" , bookId);
		
		memberService.deleteMyBook(params);
		
		// assign
		result.put("result" , "SUCCESS");
		
		return result;
	}
	
	/**
	 * 스크랩 이동
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/scrap/moveScrap.do" , method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> moveScrap(HttpServletRequest request , Model model) throws Exception
	{
		Map<String,Object> result = new HashMap<String,Object>();
		
		// session check
		HttpSession session = (HttpSession) request.getSession();
		Member member = (Member) session.getAttribute("member");
		
		if( member == null )
		{
			result.put("result" , "FAILURE");
			result.put("resultType" , "LOGIN");
			result.put("msg" , "로그인 후 이용가능합니다.");
			return result;
		}
		
		// get parameter
		int folderId = Common.getParameter(request, "folderId", 0);
		String scrapIds = Common.getParameter(request, "relationIds", "");
		
		if( folderId == 0
				|| Validate.isEmpty(scrapIds) )
		{
			result.put("result" , "FAILURE");
			result.put("resultType" , "PARAMETER");
			result.put("msg" , "파라미터 에러.");
			return result;
		}
		
		// make query parameters
		String[] t = scrapIds.split("\\,");
		
		for(String scrapId : t)
		{
			Map<String,Object> params = new HashMap<String,Object>();
			
			params.put("userId" , member.getUserId());
			params.put("scrapId" , Integer.valueOf(scrapId));
			params.put("folderId" , folderId);
			
			scrapService.moveScrapFolder(params);
		}
		
		// assign
		result.put("result" , "SUCCESS");
		
		return result;
	}
	
	/**
	 * 스크랩 폴더 수정
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/scrap/modifyFolder.do" , method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> modifyFolder(HttpServletRequest request , Model model) throws Exception
	{
		Map<String,Object> result = new HashMap<String,Object>();
		
		// session check
		HttpSession session = (HttpSession) request.getSession();
		Member member = (Member) session.getAttribute("member");
		
		if( member == null )
		{
			result.put("result" , "FAILURE");
			result.put("resultType" , "LOGIN");
			result.put("msg" , "로그인 후 이용가능합니다.");
			return result;
		}
		
		// get parameter
		int folderId = Common.getParameter(request, "folderId", 0);
		String name = Common.getParameter(request, "name", "");
		
		if( folderId == 0
				|| Validate.isEmpty(name) )
		{
			result.put("result" , "FAILURE");
			result.put("resultType" , "PARAMETER");
			result.put("msg" , "파라미터 에러.");
			return result;
		}
		
		// make query parameter
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("name" , name);
		params.put("folderId" , folderId);
		params.put("userId" , member.getUserId());
		
		scrapService.updateFolder(params);
		
		// assign
		result.put("result" , "SUCCESS");
		
		return result;
	}
	
	/**
	 * 스크랩 폴더 삭제
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/scrap/deleteFolder.do" , method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> deleteFolder(HttpServletRequest request , Model model) throws Exception
	{
		Map<String,Object> result = new HashMap<String,Object>();
		
		// session check
		HttpSession session = (HttpSession) request.getSession();
		Member member = (Member) session.getAttribute("member");
		
		if( member == null )
		{
			result.put("result" , "FAILURE");
			result.put("resultType" , "LOGIN");
			result.put("msg" , "로그인 후 이용가능합니다.");
			return result;
		}
		
		// get parameter
		int folderId = Common.getParameter(request, "folderId", 0);
		
		if( folderId == 0 )
		{
			result.put("result" , "FAILURE");
			result.put("resultType" , "PARAMETER");
			result.put("msg" , "파라미터 에러.");
			return result;
		}
		
		// make query parameter
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("folderId" , folderId);
		params.put("userId" , member.getUserId());
		
		scrapService.deleteFolder(params);
		
		// assign
		result.put("result" , "SUCCESS");
		
		return result;
	}
	
	/**
	 * HTML로 ajax 반환을 하기 위한 도서 목록
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/learning/ajax/getBookListWithCd.do")
	public String getBookListWithCd(HttpServletRequest request , Model model) throws Exception
	{
		// get paraemters
		String findCategory = Common.getParameter(request, "findCategory", "A001001");
		
		// get list
		List<Category> categories = categoryService.getChildren("C001");
		List<Book> tmpBooks = learningService.getBookListWithCd(findCategory);
		
		Map<String,Object> bookList = new HashMap<String,Object>();
		
		// 각각의 교육과정에 대하여 데이타가 존재하는지 체크한다.
		for(Category category : categories)
		{
			List<Book> books = new ArrayList<Book>();
			
			for(Book book : tmpBooks)
			{	
				if( book.getCourse().equals(category.getCategory()) 
						&& !Validate.isEmpty(book.getCdLinks()))
					books.add(book);
			}
			
			bookList.put(category.getCategory() , books);
		}
		
		// assign
		model.addAttribute("categories", categories);
		model.addAttribute("bookList", bookList);
		
		return "front/thub/learning/ajax/bookListWithCd";
	}
	
	/**
	 * Cd데이타 받아오기
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/learning/getCdLink.do" , method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> getCdLink(HttpServletRequest request , Model model) throws Exception
	{
		Map<String,Object> result = new HashMap<String,Object>();
		
		// get parameter
		int cdId = Common.getParameter(request, "cd_id", 0);
		
		if( cdId == 0 )
		{
			result.put("result" , "FAILURE");
			result.put("resultType" , "PARAMETER");
			result.put("msg" , "파라미터 에러.");
			return result;
		}
		
		// make query parameters
		Cd cd = learningService.getCd(cdId);
		
		// assign
		result.put("result" , "SUCCESS");
		result.put("url" , cd.getUrl());
		
		return result;
	}
	
	/**
	 * 데이타 체크하기.
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/learning/checkDataType.do" , method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> checkDataType(HttpServletRequest request , Model model) throws Exception
	{
		Map<String,Object> result = new HashMap<String,Object>(); 
		
		// get session
		HttpSession session = (HttpSession) request.getSession();
		Member member = (Member) session.getAttribute("member");
		
		if (member == null){
			result.put("result" , "FAILURE");
			result.put("resultType" , "LOGIN");
			result.put("msg" , "로그인 후 이용가능합니다.");
			return result;
		}
		
		if( member.getIsFinallyAuth().equals("N") )
		{
			result.put("result" , "FAILURE");
			result.put("resultType" , "AUTH");
			result.put("msg" , "교사 인증회원만 다운로드 가능합니다.");
			return result;
		}
		
		// get parameter
		int dataId = Common.getParameter(request, "dataId", 0);
		if (dataId == 0){
			result.put("result" , "FAILURE");
			result.put("resultType" , "Parameter");
			result.put("msg" , "정상적인 요청이 아닙니다.");
			return result;
		}
		
		// make query parameter
		Map<String, Object> params = new HashMap<String, Object>();

		params.put("dataId", dataId);

		// get data
		Data data = learningService.getData(params);
		
		if( data.getDataType().indexOf("D003") >= 0 ){
			Multimedia multimediaData = multimediaService.getMultimediaDetail(data.getMmId());
			if( multimediaData.getDataFile() != null || !multimediaData.getDataFile().equals("") )
			{
				if( !Validate.isEmpty(multimediaData.getDnUrl()) )
				{
					result.put("result" , "SUCCESS");
					result.put("url" , multimediaData.getDnUrl() );
					return result;
				}	
			}
		}
		
		// assign
		result.put("result" , "SUCCESS");
		result.put("url" , "DIRECT");
		
		return result;
	}
	
	/**
	 * 다운로드 데이타 받아오기
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/learning/getDownloadDataList.do" , method= {RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Map<String,Object> getDownloadDataList(HttpServletRequest request , Model model) throws Exception
	{
		Map<String,Object> result = new HashMap<String,Object>();
		
		// http session
		HttpSession session = (HttpSession) request.getSession();
		Member member = (Member) session.getAttribute("member");
		
		if( member == null )
		{
			result.put("result" , "FAILURE");
			result.put("resultType" , "LOGIN");
			result.put("msg" , "로그인 후 이용가능합니다.");
			return result;
		}
		
		// get parameter
		String dataIds = Common.getParameter(request, "dataIds", "");
		
		System.out.println("------------" + dataIds);
		/*
		if( Validate.isEmpty(dataIds) )
		{
			result.put("result" , "FAILURE");
			result.put("resultType" , "PARAMETER");
			result.put("msg" , "파라미터 에러.");
			return result;
		}
		*/
		// download count를 올려준다.
		String[] t = dataIds.split("\\,");
		
		for(String dataId : t)
			learningService.updateFileHits(Integer.valueOf(dataId));
		
		// make query parameters
		List<Data> datas = (List<Data>) learningService.getDownloadDataList(dataIds);
		List<Data> resultData = new ArrayList<Data>();
		
		// 동영상은 외부 URL이기 때문에 다시 정리.
		for(Data data : datas)
		{
			if( data.getDataType().indexOf("D003002") >= 0 )
				data.setDataFile(data.getDnUrl());
			
			resultData.add(data);
		}
		
		System.out.println(resultData.toString());
		
		// assign
		result.put("result" , "SUCCESS");
		result.put("datas" , resultData);
		
		return result;
	}
	
	/**
	 * 다운로드 멀티미디어 데이타 받아오기
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/learning/getDownloadMultiDataList.do" , method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> getDownloadMultiDataList(HttpServletRequest request , Model model) throws Exception
	{
		Map<String,Object> result = new HashMap<String,Object>();
		
		// http session
		HttpSession session = (HttpSession) request.getSession();
		Member member = (Member) session.getAttribute("member");
		
		if( member == null )
		{
			result.put("result" , "FAILURE");
			result.put("resultType" , "LOGIN");
			result.put("msg" , "로그인 후 이용가능합니다.");
			return result;
		}
		
		// get parameter
		String dataIds = Common.getParameter(request, "dataIds", "");
		
		if( Validate.isEmpty(dataIds) )
		{
			result.put("result" , "FAILURE");
			result.put("resultType" , "PARAMETER");
			result.put("msg" , "파라미터 에러.");
			return result;
		}
		
		// download count를 올려준다.
		String[] t = dataIds.split("\\,");
		
		for(String dataId : t)
			learningService.updateFileHits(Integer.valueOf(dataId));
		
		int dataIdsChg = Integer.parseInt(dataIds);
		// make query parameters
		//@SuppressWarnings("unchecked")
		//Multimedia multimeda =  multimediaService.getMultimediaDetail(dataIdsChg);
		List<Data> datas = (List<Data>) multimediaService.getMultimediaDetail(dataIdsChg);
		List<Data> resultData = new ArrayList<Data>();
		
		// 동영상은 외부 URL이기 때문에 다시 정리. resultData확인
		for(Data data : datas)
		{
			if( data.getDataType().indexOf("D003002") >= 0 )
				data.setDataFile(data.getDnUrl());
			
			resultData.add(data);
		}
		
		//System.out.println(resultData.toString());
		//System.out.println("여기");
		
		// assign
		result.put("result" , "SUCCESS");
		result.put("datas" , resultData);
		
		return result;
	}
	
	/**
	 * 특화자료 다운로드 데이타 받아오기
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/learning/getDownloadSDataList.do" , method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> getDownloadSDataList(HttpServletRequest request , Model model) throws Exception
	{
		Map<String,Object> result = new HashMap<String,Object>();
		
		// http session
		HttpSession session = (HttpSession) request.getSession();
		Member member = (Member) session.getAttribute("member");
		
		if( member == null )
		{
			result.put("result" , "FAILURE");
			result.put("resultType" , "LOGIN");
			result.put("msg" , "로그인 후 이용가능합니다.");
			return result;
		}
		
		// get parameter
		String dataIds = Common.getParameter(request, "dataIds", "");
		
		if( Validate.isEmpty(dataIds) )
		{
			result.put("result" , "FAILURE");
			result.put("resultType" , "PARAMETER");
			result.put("msg" , "파라미터 에러.");
			return result;
		}
		
		// download count를 올려준다.
		String[] t = dataIds.split("\\,");
		
		for(String dataId : t)
			learningService.updateSFileHits(Integer.valueOf(dataId));
		
		// make query parameters
		List<Data> datas = (List<Data>) learningService.getDownloadSDataList(dataIds);
		List<Data> resultData = new ArrayList<Data>();
		
		// 동영상은 외부 URL이기 때문에 다시 정리.
		for(Data data : datas)
		{
			if( data.getDataType().indexOf("D003002") >= 0 )
				data.setDataFile(data.getDnUrl());
			
			resultData.add(data);
		}
		
		System.out.println(resultData.toString());
		
		// assign
		result.put("result" , "SUCCESS");
		result.put("datas" , resultData);
		
		return result;
	}
	
	/**
	 * Event 01
	 */
	@RequestMapping("/event01/getMyFlag.do")
	public @ResponseBody Map<String,Object>  getEvent01MyFlag(HttpServletRequest request , Model model)
	{
		Map<String,Object> result = new HashMap<String,Object>();
		
		HttpSession session = (HttpSession) request.getSession();
		Member member = (Member) session.getAttribute("member");
		
		if( member != null )
		{
			// 확률은 1/5로 정한다.
			//int count = 1;
			int count = (int)(Math.random() * 5) + 1; 
			
			if( count == 1 )
			{
				// get my flags
				List<ResultMap> myFlags = (List<ResultMap>) eventService.getEvent01MyFlags(member.getUserId());
				
				List<String> flags = new ArrayList<String>();
				
				flags.add("티");
				flags.add("칭");
				flags.add("허");
				flags.add("브");
				
				for(ResultMap myFlag : myFlags)
				{
					int loopCnt = flags.size();
					
					for(int i = 0 ; i < loopCnt ; i++ )
					{
						String flag = flags.get(i);
						
						if( myFlag.get("flag").equals(flag) )
						{
							flags.remove(i);
							break;
						}
					}
				}
				
				if( flags.size() > 0 )
				{
					Random random = new Random();
					int idx = random.nextInt(flags.size());
					result.put("result" , "SUCCESS" );
					result.put("flag" , flags.get(idx) );
				}
				else
					result.put("result" , "FAILURE");
			}
			else
				result.put("result" , "FAILURE");
		}
		else
		{
			result.put("result" , "FAILURE");
		}
		
		return result;
	}
	
	@RequestMapping("/event01/addMyFlag.do")
	public @ResponseBody Map<String,Object> addEvent01MyFlag(HttpServletRequest request , Model model) throws Exception
	{
		Map<String,Object> result = new HashMap<String,Object>();
		
		// session check
		HttpSession session = (HttpSession) request.getSession();
		Member member = (Member) session.getAttribute("member");
		
		if( member == null )
		{
			result.put("result" , "FAILURE");
			result.put("resultType" , "LOGIN");
			result.put("msg" , "로그인 후 이용가능합니다.");
			return result;
		}
		
		// get parameters
		String flag = Common.getParameter(request, "flag", "");
		
		if( Validate.isEmpty(flag) )
		{
			result.put("result" , "FAILURE");
			result.put("resultType" , "PARAMETER");
			result.put("msg" , "올바르지 않은 파라미터 입니다.");
			return result;
		}
		
		// make parameter
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("userId" , member.getUserId());
		params.put("flag" , flag);
		
		eventService.insertEvent01MyFlag(params);
		
		// 모두 모았는지 체크한다.
		List<ResultMap> myFlags = (List<ResultMap>) eventService.getEvent01MyFlags(member.getUserId());
		
		// assign
		result.put("result" , "SUCCESS");
		result.put("myFlags" , myFlags.size());
		
		return result;
	}
	
}