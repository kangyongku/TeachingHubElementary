package kr.co.kumsung.thub.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import kr.co.kumsung.thub.domain.Article;
import kr.co.kumsung.thub.domain.BoardCategory;
import kr.co.kumsung.thub.domain.BoardConfig;
import kr.co.kumsung.thub.domain.Comment;
import kr.co.kumsung.thub.domain.FileInfo;
import kr.co.kumsung.thub.domain.Member;

public interface BoardService {

	/**
	 * 게시글의 총 갯수를 가지고 온다.
	 * @param params
	 * @return
	 */
	public int getTotalArticle(Map<String,Object> params);
	
	/**
	 * 게시글의 리스트를 가지고 온다.
	 * @param params
	 * @return
	 */
	public List<Article> getArticleList(Map<String,Object> params);
	
	/**
	 * 통합검색의 게시글의 리스트를 가지고 온다.
	 * @param params
	 * @return
	 */
	public List<Article> getArticleSearchList(Map<String,Object> params);
	
	/**
	 * 통합검색의 독도사랑 리스트를 가지고 온다.
	 * @param params
	 * @return
	 */
	public List<Article> getArticleSearchListLove(Map<String,Object> params);
	
	
	/**
	 * 게시글의 데이타를 가지고 온다.
	 * @param articleId
	 * @return
	 */
	public Article getArticle(int articleId);
	
	/**
	 * 가장 큰 그룹 아이디를 가지고 온다.
	 * @param boardId
	 * @return
	 */
	public int getMaxGroupId( int boardId);
	
	/**
	 * steps 값을 가지고 온다.
	 * @return
	 */
	public String getNewSteps( int boardId , int groupId , String steps );
	
	/**
	 * 게시글을 등록한다.
	 * @param params
	 */
	public void insertArticle(Map<String,Object> params);
	
	/**
	 * 게시글을 갱신한다.
	 * @param params
	 */
	public void updateArticle(Map<String,Object> params);
	
	/**
	 * 게시글을 삭제한다.
	 * @param params
	 */
	public void deleteArticle(Map<String,Object> params);
	
	/**
	 * 게시판의 리스트를 가지고 온다.
	 * @return
	 */
	public List<BoardConfig> getBoardList(Map<String,Object> params);
	
	/**
	 * 게시판의 설정 데이타를 가지고 온다.
	 * @param boardId
	 * @return
	 */
	public BoardConfig getBoardConfig(int boardId);
	
	/**
	 * 게시판을 생성한다.
	 * @param params
	 */
	public int insertBoard(Map<String,Object> params);
	
	/**
	 * 게시판 설정을 변경한다.
	 * @param params
	 */
	public void updateBoard(Map<String,Object> params);
	
	/**
	 * 게시판의 카테고리 리스트를 가지고 온다.
	 * @return
	 */
	public List<BoardCategory> getBoardCategoryList(int boardId);
	
	/**
	 * 게시판의 카테고리를 관린한다.
	 * @param boardId
	 * @param categoryItems(JSON type String)
	 */
	public void mngBoardCategory(int boardId , String categoryItems);
	
	/**
	 * 게시판의 카테고리를 삭제한다.
	 * @param boardId
	 */
	public void deleteBoardCategory(Map<String,Object> params);
	
	/**
	 * 게시글과 관련된 모든 데이타를 가지고 온다.
	 * model 데이타를 반납하고 이 데이타를 받은 controller는 model.addAllAttributes로 등록한다.
	 * @return
	 */
	public Map<String,Object> getBoardDataAttributes(HttpServletRequest request , int boardId);

	/**
	 * 게시글이 이미 추천되어있는지 체크한다.
	 * @param params
	 * @return
	 */
	public int getRecommendLogCount(Map<String,Object> params);
	
	/**
	 * 게시글을 추천한다.
	 * @param params
	 */
	public void insertRecommendLog(Map<String,Object> params);

	/**
	 * 댓글의 그룹 아이디를 가지고 온다.
	 * @param boardId
	 * @return
	 */
	public int getCommentGroupId(int boardId);
	
	/**
	 * 댓글을 입력한다.
	 * @return
	 */
	public int insertComment(Map<String,Object> params);
	
	/**
	 * 댓글의 리스트를 가지고 온다.
	 * @param params
	 * @return
	 */
	public List<Comment> getCommentList(Map<String,Object> params);
	
	/**
	 * 댓글을 가지고 온다.
	 * @param commentId
	 * @return
	 */
	public Comment getComment(int commentId);
	
	/**
	 * 게시판에 대한 접근 권한이 있는지 체크한다.
	 * @param boardAuth
	 * @param boardId
	 * @return
	 */
	public boolean isAccessBoardAdmin(Member member , int boardId);
	
	/**
	 * 댓글을 삭제한다.
	 * @param commentId
	 */
	public void deleteComment(int commentId);
	
	/**
	 * 파일의 정보를 가지고 온다.
	 * @param fileId
	 * @return
	 */
	public FileInfo getFileInfo(int fileId);
	
	/**
	 * 파일의 hits를 증가시킨다.
	 * @param fileId
	 */
	public void updateFileHits(int fileId);
	
	/**
	 * 총 댓글 리스트 카운트를 가지고 온다.
	 * @param params
	 * @return
	 */
	public int getTotalCommentListCount(Map<String,Object> params);
	
	/**
	 * 총 댓글 리스트를 가지고 온다.
	 * @param params
	 * @return
	 */
	public List<Comment> getTotalCommentList(Map<String,Object> params);
	
	/**
	 * 게시글의 조회수를 증가한다.
	 * @param articleId
	 */
	public void updateArticleRecommend(int articleId);
	
	public List<BoardCategory> getsub_category();
	
	public Map<String,Object> his_getBoardDataAttributes(HttpServletRequest request);

	public BoardConfig his_getBoardConfig();
	
	public Map<String,Object> hisview_getBoardDataAttributes(HttpServletRequest request);

}
