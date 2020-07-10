package kr.co.kumsung.thub.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import kr.co.kumsung.thub.dao.BoardDao;
import kr.co.kumsung.thub.domain.Article;
import kr.co.kumsung.thub.domain.BoardCategory;
import kr.co.kumsung.thub.domain.BoardConfig;
import kr.co.kumsung.thub.domain.Comment;
import kr.co.kumsung.thub.domain.FileInfo;
import kr.co.kumsung.thub.domain.Member;
import kr.co.kumsung.thub.service.BoardService;
import kr.co.kumsung.thub.service.FileService;
import kr.co.kumsung.thub.util.Common;
import kr.co.kumsung.thub.util.FrontPagination;
import kr.co.kumsung.thub.util.Validate;

public class BoardServiceImpl implements BoardService{
	private static final Logger logger = LoggerFactory.getLogger(BoardServiceImpl.class);
	
	@Autowired
	private BoardDao boardDao;
	
	@Autowired
	private FileService fileService;
	


	@Override
	public int getTotalArticle(Map<String, Object> params) {
		return boardDao.getTotalArticle(params);
	}

	@Override
	public List<Article> getArticleList(Map<String, Object> params) {
		return boardDao.getArticleList(params);
	}
	
	@Override
	public List<Article> getArticleSearchList(Map<String, Object> params) {
		return boardDao.getArticleSearchList(params);
	}
	
	@Override
	public List<Article> getArticleSearchListLove(Map<String, Object> params) {
		return boardDao.getArticleSearchListLove(params);
	}
	
	
	@Override
	public Article getArticle(int articleId) {
		return boardDao.getArticle(articleId);
	}
	
	@Override
	public int getMaxGroupId(int boardId) {
		return boardDao.getMaxGroupId(boardId);
	}
	
	@Override
	public String getNewSteps(int boardId , int groupId , String steps){
		
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("boardId" , boardId);
		params.put("groupId" , groupId);
		params.put("steps" , steps);
		params.put("stepsCount" , steps.length());
		
		String newSteps = "";
		String maxSteps = boardDao.getMaxSteps(params);
		
		if( maxSteps == null )
			return String.format("%sA" , steps);
		
		char c = maxSteps.charAt(maxSteps.length() - 1);
		int asc = (int) c;
		char d = (char)(asc + 1);
		newSteps = String.format("%s%s" , maxSteps.substring(0 , maxSteps.length() - 1) , d);
		
		return newSteps;
	}
	
	@Override
	public void insertArticle(Map<String, Object> params) {
		boardDao.insertArticle(params);
	}
	
	@Override
	public void updateArticle(Map<String, Object> params) {
		boardDao.updateArticle(params);
	}
	
	@Override
	public void deleteArticle(Map<String, Object> params) {
		boardDao.deleteArticle(params);
	}
	
	@Override
	public List<BoardConfig> getBoardList(Map<String,Object> params) {
		return boardDao.getBoardList(params);
	}
	
	@Override
	public BoardConfig getBoardConfig(int boardId) {
		return boardDao.getBoardConfig(boardId);
	}
	
	@Override
	public int insertBoard(Map<String, Object> params) {
		return boardDao.insertBoard(params);
	}

	@Override
	public void updateBoard(Map<String, Object> params) {
		boardDao.updateBoard(params);
	}

	@Override
	public List<BoardCategory> getBoardCategoryList(int boardId) {
		return (List<BoardCategory>) boardDao.getBoardCategoryList(boardId);
	}

	@Override
	public void mngBoardCategory(int boardId, String categoryItems) {
		
		try {
			
			JSONObject json = new JSONObject(categoryItems);
			JSONArray items = json.getJSONArray("items");
			
			if( items != null
					&& items.length() > 0 )
			{
				int loopCnt = items.length();
				
				for(int i = 0 ; i < loopCnt ; i++)
				{
					JSONObject item = items.getJSONObject(i);
					
					String name = item.getString("categoryName");
					String categoryId = item.getString("categoryId");
					int seq = ( i + 1 );
					
					Map<String,Object> params = new HashMap<String,Object>();
					
					params.put("boardId" , boardId);
					params.put("name" , name);
					params.put("seq" , seq);
					
					if( Validate.isEmpty(categoryId) )
					{
						// insert
						boardDao.insertBoardCategory(params);
					}
					else
					{
						// update
						params.put("categoryId" , categoryId);
						
						boardDao.updateBoardCategory(params);
					}
				}
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void deleteBoardCategory(Map<String,Object> params){
		boardDao.deleteBoardCategory(params);
	}

	@Override
	public Map<String, Object> getBoardDataAttributes(HttpServletRequest request, int boardId) {
		
		// get session 
		HttpSession session = (HttpSession) request.getSession();
		Member member = (Member) session.getAttribute("member");
		
		if( member == null )
			member = new Member();
		
		// set return data
		Map<String,Object> result = new HashMap<String,Object>();
		
		int PAGE_SIZE = 10;
		int BLOCK_SIZE = 10;
		
		Map<String,Object> param_terms = new HashMap<String,Object>();
		
		param_terms.put("category_id", request.getParameter("category_id"));
		param_terms.put("boardId"    , boardId);
		
		BoardConfig boardConfig = (BoardConfig) getBoardConfig(boardId);
		
		//서식자료 임시
		if(boardId==34) boardConfig.setBoardType("GALLERY");
		
		if( boardConfig.getBoardType().equals("WEBZINE") || boardConfig.getBoardType().equals("GALLERY") )
			PAGE_SIZE = 12;
		
		// get parameter
		int pg = Common.getParameter(request, "pg", 1);
		int skip = (pg - 1) * PAGE_SIZE;
		int articleId = Common.getParameter(request, "articleId", 0);
		int boardType = Common.getParameter(request, "boardType", 2);
		String viewType = Common.getParameter(request, "viewType", "1");
		
		String mode = Common.getParameter(request, "mode", "list");
		int findCategory = Common.getParameter(request, "findCategory", 0);
		String findMethod = Common.getParameter(request, "findMethod", "");
//		String findStr = Common.getParameter(request, "findStr", "");
		String findStr2 = Common.getParameter(request, "findStr", "");
//System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!;;;+" +articleId);		
		String findStr = findStr2.replaceAll(" ", "");    		
		
		String returnPage = (String) request.getRequestURI();
		String findSort = Common.getParameter(request, "findSort", "articleId");
		
		// arrow access?
		result.put("isBoardAccess" , isAccessBoardAdmin(member, boardId));		
		
		// get skin path
		String boardSkinPath = "";
		
		if( mode.equals("list") )
		{
			boardSkinPath = String.format("front/board/%s/%s" ,
									boardConfig.getBoardType().toLowerCase() ,
									mode
								);
		}
		else if( mode.equals("detail") )
			boardSkinPath = "front/board/common/detail";
		else if( mode.equals("form") )
			boardSkinPath = "front/board/common/form";
		else if(mode.equals("list") && boardId == 67) {
			boardSkinPath = "front/board/preview/special_move_list";
		}
		
		// make query parameters
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("boardId" , boardId);
		params.put("findCategory" , findCategory);
		params.put("findMethod" , findMethod);
		params.put("findStr" , findStr);
		params.put("findSort" , findSort);
		params.put("skip" , skip);
		params.put("pageSize" , PAGE_SIZE);
		params.put("sub_category" , request.getParameter("sub_category"));
		
		List<BoardCategory> categories = (List<BoardCategory>) getBoardCategoryList(boardId);
		// get data
		if( mode.equals("list") )
		{
			int totalArticles = (Integer) getTotalArticle(params);
			int articleNum = totalArticles - skip;
			List<Article> articles = (List<Article>) getArticleList(params);
			List<Article> notices = new ArrayList<Article>();
			
			FrontPagination pagination = new FrontPagination(pg, PAGE_SIZE, BLOCK_SIZE, totalArticles);
			pagination.initialize();
			
			// 리스트 모드일 때는 공지글을 가지고 온다.
			if( boardConfig.getBoardType().toLowerCase().equals("list") )
			{
				// 공지글만 가지고 온다.
				Map<String,Object> noticeParams = new HashMap<String,Object>();
				
				noticeParams.put("boardId" , boardId);
				noticeParams.put("findIsNotice" , "Y");
				
				notices = (List<Article>) getArticleList(noticeParams);
			}
			
			result.put("notices" , notices);
			result.put("totalArticles" , totalArticles);
			result.put("articles" , articles);
			result.put("articleNum" , articleNum);
			result.put("paging" , pagination.print());
		}
		else if( mode.equals("detail") )
		{	
			// add articleId
			params.put("articleId" , articleId);
			
			Article article = (Article) getArticle(articleId);
			List<FileInfo> attachments = new ArrayList<FileInfo>();
			Article prevArticle = (Article) boardDao.getPrevArticle(params);
			Article nextArticle = (Article) boardDao.getNextArticle(params);
			List<Comment> comments = (List<Comment>) getCommentList(params);
			
			// 첨부파일의 데이타를 가지고 온다.
			if( !Validate.isEmpty(article.getAttachment()) )
					attachments = fileService.getBoardFiles(article.getAttachment());
			
			result.put("article" , article);
			result.put("attachments" , attachments);
			result.put("prevArticle" , prevArticle);
			result.put("nextArticle" , nextArticle);
			result.put("comments" , comments);
			
			// 조회수를 증가시킨다.
			boardDao.updateArticleHits(articleId);
		}
		else if( mode.equals("form") )
		{
			Article article = new Article();
			List<FileInfo> attachments = new ArrayList<FileInfo>();
			
			// 신규인지 체크한다.
			if( articleId > 0)
			{
				params.put("articleId" , articleId);
			
				article = (Article) getArticle(articleId);
				
				if( !Validate.isEmpty(article.getAttachment()) )
					attachments = fileService.getBoardFiles(article.getAttachment());
			}
			
			result.put("article" , article);
			result.put("attachments" , attachments);
		}
		
		//result.put("boardId" , boardId);
		// assign
		result.put("boardConfig" , boardConfig);
		result.put("pg" , pg);
		result.put("mode" , mode);
		result.put("boardType" , boardType);
		result.put("viewType" , viewType);
		result.put("findCategory" , findCategory);
		result.put("findMethod" , findMethod);
		result.put("findStr" , findStr);
		result.put("findSort" , findSort);
		result.put("returnPage" , returnPage);
		
		result.put("categories" , categories);
		result.put("boardSkinPath" , boardSkinPath);
		
		return result;
	}
	
	@Override
	public int getRecommendLogCount(Map<String, Object> params) {
		return boardDao.getRecommendLogCount(params);
	}

	@Override
	public void insertRecommendLog(Map<String, Object> params) {
		boardDao.insertRecommendLog(params);
	}

	@Override
	public int getCommentGroupId(int boardId) {
		return boardDao.getCommentGroupId(boardId);
	}

	@Override
	public List<Comment> getCommentList(Map<String, Object> params) {
		return boardDao.getCommentList(params);
	}
	
	@Override
	public Comment getComment(int commentId) {
		return boardDao.getComment(commentId);
	}
	
	@Override
	public int insertComment(Map<String, Object> params) {
		return boardDao.insertComment(params);
	}
	
	@Override
	public boolean isAccessBoardAdmin(Member member, int boardId) {
		
		if( member == null || Validate.isEmpty(member.getAuthType()) )
			return false;
		
		if( member.getAuthType().equals("S") )
			return true;
		else
		{
			if( !Validate.isEmpty(member.getLearningAuth()))
			{
				String[] auths = member.getBoardAuth().split("\\,");
				
				for(String auth : auths)
				{
					if( !Validate.isEmpty(auth) )
					{
						// cast to integer
						int authBoardId = Integer.valueOf(auth);
						
						if( boardId == authBoardId )
							return true;
					}
				}
			}
		}
		
		return false;
	}

	@Override
	public void deleteComment(int commentId) {
		
		// 현재 코멘트가 1 depth의 코멘트인지 체크한다.
		Comment comment = (Comment) getComment(commentId);
		
		if( comment.getDepth() == 1 )
		{
			int childNode = (Integer) boardDao.getIsChildeNode(commentId);
			
			if( childNode > 0 )
			{
				// 현재 상태를 삭제로만 처리한다.
				boardDao.updateCommentToDelete(commentId);
			}
			else
			{
				// 자식노드가 없으므로 삭제처리한다.
				boardDao.deleteComment(commentId);
			}
		}
		else
		{
			// 2 depth의 코멘트라면 그냥 삭제처리한다.
			boardDao.deleteComment(commentId);
			
			// 현재 그룹의 코멘트 갯수를 가지고 온다.
			Map<String,Object> params = new HashMap<String,Object>();
			
			params.put("articleId" , comment.getArticleId());
			params.put("groupId" , comment.getGroupId());
			
			int count = boardDao.getGroupCommentCount(params);
			
			// 자식 노드가 삭제된 후 마지막으로 남은 데이타가 한개라면 해당 데이타를 가지고 온다.
			if( count == 1 )
			{
				Comment lastComment = boardDao.getLastCommentData(params);
				
				if (lastComment != null)
				{
					// 데이타가 null 이 아니라면 삭제된 데이타가 존재한다는 뜻이다. 삭제처리 한다.
					boardDao.deleteComment(lastComment.getCommentId());
				}
			}
		}
	}

	@Override
	public FileInfo getFileInfo(int fileId) {
		return boardDao.getFileInfo(fileId);
	}

	@Override
	public void updateFileHits(int fileId) {
		boardDao.updateFileHits(fileId);
	}

	@Override
	public int getTotalCommentListCount(Map<String, Object> params) {
		return boardDao.getTotalCommentListCount(params);
	}

	@Override
	public List<Comment> getTotalCommentList(Map<String, Object> params) {
		return boardDao.getTotalCommentList(params);
	}
	
	@Override
	public void updateArticleRecommend(int articleId) {
		boardDao.updateArticleRecommend(articleId);
	}
	
	@Override
	public List<BoardCategory> getsub_category() {
		return (List<BoardCategory>) boardDao.getsub_category();
	}
	
	public Map<String, Object> his_getBoardDataAttributes(HttpServletRequest request) {
		
		Map<String,Object> result      = new HashMap<String,Object>();
		BoardConfig boardConfig = (BoardConfig) his_getBoardConfig();
		boardConfig.setBoardType("GALLERY");
		String boardSkinPath = "front/board/gally/list";
		result.put("isBoardAccess" , true);//테스트위해서 강제로 지정		
		result.put("boardConfig" , boardConfig);
		result.put("boardSkinPath" , boardSkinPath);
		
		return result;
	}
	@Override
	public BoardConfig his_getBoardConfig() {
		return boardDao.his_getBoardConfig();
	}
	
	public Map<String, Object> hisview_getBoardDataAttributes(HttpServletRequest request) {
		Map<String,Object> result      = new HashMap<String,Object>();
		BoardConfig boardConfig = (BoardConfig) his_getBoardConfig();
		boardConfig.setBoardType("GALLERY");
		String boardSkinPath = "front/board/common/history_detail";
		result.put("isBoardAccess" , true);//테스트위해서 강제로 지정		
		result.put("boardConfig" , boardConfig);
		result.put("boardSkinPath" , boardSkinPath);
		
		return result;
	}	

}
