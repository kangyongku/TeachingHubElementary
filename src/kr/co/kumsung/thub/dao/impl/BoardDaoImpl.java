package kr.co.kumsung.thub.dao.impl;

import java.util.List;
import java.util.Map;

import kr.co.kumsung.thub.dao.BoardDao;
import kr.co.kumsung.thub.domain.Article;
import kr.co.kumsung.thub.domain.BoardCategory;
import kr.co.kumsung.thub.domain.BoardConfig;
import kr.co.kumsung.thub.domain.Comment;
import kr.co.kumsung.thub.domain.FileInfo;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class BoardDaoImpl extends SqlMapClientDaoSupport implements BoardDao{

	@Override
	public int getTotalArticle(Map<String, Object> params) {
		return (Integer) getSqlMapClientTemplate().queryForObject("Board.getTotalArticle" , params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Article> getArticleList(Map<String, Object> params) {
		return (List<Article>) getSqlMapClientTemplate().queryForList("Board.getArticleList" , params);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Article> getArticleSearchList(Map<String, Object> params) {
		return (List<Article>) getSqlMapClientTemplate().queryForList("Board.getArticleSearchList", params);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Article> getArticleSearchListLove(Map<String, Object> params) {
		return (List<Article>) getSqlMapClientTemplate().queryForList("Board.getArticleSearchListLove", params);
	}
	
	@Override
	public Article getArticle(int articleId){
		return (Article) getSqlMapClientTemplate().queryForObject("Board.getArticle" , articleId);
	}
	
	@Override
	public int getMaxGroupId(int boardId) {
		return (Integer) getSqlMapClientTemplate().queryForObject("Board.getMaxGroupId" , boardId);
	}
	
	@Override
	public String getMaxSteps(Map<String, Object> params) {
		return (String) getSqlMapClientTemplate().queryForObject("Board.getMaxSteps", params);
	}
	
	@Override
	public void insertArticle(Map<String, Object> params) {
		getSqlMapClientTemplate().insert("Board.insertArticle" , params);
	}
	
	@Override
	public void updateArticle(Map<String, Object> params) {
		getSqlMapClientTemplate().update("Board.updateArticle" , params);
	}
	
	@Override
	public void deleteArticle(Map<String, Object> params) {
		getSqlMapClientTemplate().delete("Board.deleteArticle" , params);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<BoardConfig> getBoardList(Map<String,Object> params) {
		return (List<BoardConfig>) getSqlMapClientTemplate().queryForList("Board.getBoardList");
	}

	@Override
	public BoardConfig getBoardConfig(int boardId) {
		return (BoardConfig) getSqlMapClientTemplate().queryForObject("Board.getBoardConfig" , boardId);
	}
	
	@Override
	public int insertBoard(Map<String, Object> params) {
		return (Integer)  getSqlMapClientTemplate().insert("Board.insertBoard" , params);
	}

	@Override
	public void updateBoard(Map<String, Object> params) {
		getSqlMapClientTemplate().update("Board.updateBoard" , params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BoardCategory> getBoardCategoryList(int boardId) {
		return (List<BoardCategory>) getSqlMapClientTemplate().queryForList("Board.getBoardCategoryList" , boardId);
	}

	@Override
	public void insertBoardCategory(Map<String, Object> params) {
		getSqlMapClientTemplate().insert("Board.insertBoardCategory" , params);
	}

	@Override
	public void updateBoardCategory(Map<String, Object> params) {
		getSqlMapClientTemplate().update("Board.updateBoardCategory" , params);
	}
	
	@Override
	public void deleteBoardCategory(Map<String,Object> params){
		getSqlMapClientTemplate().delete("Board.deleteBoardCategory" , params);
	}

	@Override
	public void updateArticleHits(int articleId) {
		getSqlMapClientTemplate().update("Board.updateArticleHits", articleId);
	}

	@Override
	public void updateArticleRecommend(int articleId) {
		getSqlMapClientTemplate().update("Board.updateArticleRecommend", articleId);
	}
	
	@Override
	public int getRecommendLogCount(Map<String, Object> params) {
		return (Integer) getSqlMapClientTemplate().queryForObject("Board.getRecommendLogCount", params);
	}

	@Override
	public void insertRecommendLog(Map<String, Object> params) {
		getSqlMapClientTemplate().insert("Board.insertRecommendLog" , params);
	}

	@Override
	public Article getPrevArticle(Map<String, Object> params) {
		return (Article) getSqlMapClientTemplate().queryForObject("Board.getPrevArticle", params);
	}

	@Override
	public Article getNextArticle(Map<String, Object> params) {
		return (Article) getSqlMapClientTemplate().queryForObject("Board.getNextArticle", params);
	}

	@Override
	public int getCommentGroupId(int boardId) {
		return (Integer) getSqlMapClientTemplate().queryForObject("Board.getCommentGroupId", boardId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Comment> getCommentList(Map<String, Object> params) {
		return (List<Comment>) getSqlMapClientTemplate().queryForList("Board.getCommentList", params);
	}
	
	@Override
	public Comment getComment(int commentId) {
		return (Comment) getSqlMapClientTemplate().queryForObject("Board.getComment", commentId);
	}
	
	@Override
	public int insertComment(Map<String, Object> params) {
		return (Integer) getSqlMapClientTemplate().insert("Board.insertComment" , params);
	}

	@Override
	public void deleteComment(int commentId) {
		getSqlMapClientTemplate().delete("Board.deleteComment" , commentId);
	}

	@Override
	public FileInfo getFileInfo(int fileId) {
		return (FileInfo) getSqlMapClientTemplate().queryForObject("Board.getFileInfo", fileId);
	}

	@Override
	public void updateFileHits(int fileId) {
		getSqlMapClientTemplate().update("Board.updateFileHits" , fileId);
	}

	@Override
	public int getIsChildeNode(int commentId) {
		return (Integer) getSqlMapClientTemplate().queryForObject("Board.getIsChildeNode" , commentId);
	}

	@Override
	public void updateCommentToDelete(int commentId) {
		getSqlMapClientTemplate().update("Board.updateCommentToDelete" , commentId);
	}

	@Override
	public int getTotalCommentListCount(Map<String, Object> params) {
		return (Integer) getSqlMapClientTemplate().queryForObject("Board.getTotalCommentListCount", params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Comment> getTotalCommentList(Map<String, Object> params) {
		return (List<Comment>) getSqlMapClientTemplate().queryForList("Board.getTotalCommentList", params);
	}

	@Override
	public int getGroupCommentCount(Map<String, Object> params) {
		return (Integer) getSqlMapClientTemplate().queryForObject("Board.getGroupCommentCount" , params);
	}

	@Override
	public Comment getLastCommentData(Map<String, Object> params) {
		return (Comment) getSqlMapClientTemplate().queryForObject("Board.getLastCommentData", params);
	}

	@Override
	public BoardConfig getBoardConfig_freestudy(int boardId, int category_id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<BoardCategory> getsub_category() {
		return (List<BoardCategory>) getSqlMapClientTemplate().queryForList("Board.getsub_category");
	}
	
	@Override
	public BoardConfig his_getBoardConfig() {
		return (BoardConfig) getSqlMapClientTemplate().queryForObject("Board.his_getBoardConfig");
	}	
	
}
