package kr.co.kumsung.thub.dao;

import java.util.List;
import java.util.Map;

import kr.co.kumsung.thub.domain.Article;
import kr.co.kumsung.thub.domain.BoardCategory;
import kr.co.kumsung.thub.domain.BoardConfig;
import kr.co.kumsung.thub.domain.Comment;
import kr.co.kumsung.thub.domain.FileInfo;

public interface BoardDao {

	/**
	 * 게시글의 총수를 가지고 온다.
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
	 * 통합검색의 게시글의 리스트를 가지고 온다.
	 * @param params
	 * @return
	 */
	public List<Article> getArticleSearchListLove(Map<String,Object> params);
	
	/**
	 * 게시글을 가지고 온다.
	 * @param articleId
	 * @return
	 */
	public Article getArticle(int articleId);
	
	/**
	 * 가장 큰 그룹 아이디를 가지고 온다.
	 * @param boardId
	 * @return
	 */
	public int getMaxGroupId(int boardId);
	
	/**
	 * 가장 큰 steps를 가지고 온다.
	 * @param params
	 * @return
	 */
	public String getMaxSteps(Map<String,Object> params);
	
	/**
	 * 게시글을 입력한다.
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
	 * 게시판의 설정 내역을 가지고 온다.
	 * @param boardId
	 * @return
	 */
	public BoardConfig getBoardConfig(int boardId);
	
	/**
	 * 게시판을 등록한다.
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
	 * 게시판의 카테고리를 등록한다.
	 * @param params
	 */
	public void insertBoardCategory(Map<String,Object> params);
	
	/**
	 * 게시판의 카테고리를 갱신한다.
	 * @param params
	 */
	public void updateBoardCategory(Map<String,Object> params);
	
	/**
	 * 게시판 카테고리를 삭제한다.
	 * @param params
	 */
	public void deleteBoardCategory(Map<String,Object> params);
	
	/**
	 * 게시글의 조회수를 증가한다.
	 * @param articleId
	 */
	public void updateArticleHits(int articleId);
	
	/**
	 * 게시글의 조회수를 증가한다.
	 * @param articleId
	 */
	public void updateArticleRecommend(int articleId);
	
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
	 * 이전글을 가지고 온다.
	 * @return
	 */
	public Article getPrevArticle(Map<String,Object> params);
	
	/**
	 * 다음글을 가지고 온다.
	 * @param params
	 * @return
	 */
	public Article getNextArticle(Map<String,Object> params);
	
	/**
	 * 댓글의 그룹 아이디를 가지고 온다.
	 * @param boardId
	 * @return
	 */
	public int getCommentGroupId(int boardId);
	
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
	 * 댓글을 입력한다.
	 * @return
	 */
	public int insertComment(Map<String,Object> params);
	
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
	 * 자식노드가 존재하는지 체크한다.
	 * @param commentId
	 * @return
	 */
	public int getIsChildeNode(int commentId);
	
	/**
	 * 현재 노드를 삭제상태로 변경한다.
	 * @param commentId
	 */
	public void updateCommentToDelete(int commentId);
	
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
	 * 현재 그룹의 코멘트 갯수를 가지고 온다.
	 * @param params
	 * @return
	 */
	public int getGroupCommentCount(Map<String,Object> params);
	
	/**
	 * 현재 그룹의 최상단의 코멘트중 삭제된 것이 있으면 데이타를 가지고 온다.
	 */
	public Comment getLastCommentData(Map<String,Object> params);
	
	public BoardConfig getBoardConfig_freestudy(int boardId, int category_id);
	
	public List<BoardCategory> getsub_category();
	
	public BoardConfig his_getBoardConfig();


}
