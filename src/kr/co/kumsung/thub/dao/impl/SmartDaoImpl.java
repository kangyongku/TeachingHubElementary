package kr.co.kumsung.thub.dao.impl;

import java.util.List;
import java.util.Map;

import kr.co.kumsung.thub.dao.SmartDao;
import kr.co.kumsung.thub.domain.Article;
import kr.co.kumsung.thub.domain.Book;
import kr.co.kumsung.thub.domain.Category;
import kr.co.kumsung.thub.domain.Comment;
import kr.co.kumsung.thub.domain.Headword;
import kr.co.kumsung.thub.domain.History;
import kr.co.kumsung.thub.domain.Knowledge;
import kr.co.kumsung.thub.domain.Multimedia;
import kr.co.kumsung.thub.domain.Pool;
import kr.co.kumsung.thub.domain.RelationHeadword;
import kr.co.kumsung.thub.domain.Unit;
import kr.co.kumsung.thub.util.ResultMap;


import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class SmartDaoImpl extends SqlMapClientDaoSupport implements SmartDao{

	@Override
	public int getTotalBooks(Map<String,Object> params) {
		return (Integer) getSqlMapClientTemplate().queryForObject("Smart.getTotalBooks" , params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Book> getBookList(Map<String, Object> params) {
		return (List<Book>) getSqlMapClientTemplate().queryForList("Smart.getBookList" , params);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Book> getTotalBookList() {
		return (List<Book>) getSqlMapClientTemplate().queryForList("Smart.getTotalBookList");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Book> getBookListByCategory(Map<String, Object> params) {
		return (List<Book>) getSqlMapClientTemplate().queryForList("Smart.getBookListByCategory" , params);
	}

	@Override
	public Book getBook(int bookId) {
		return (Book) getSqlMapClientTemplate().queryForObject("Smart.getBook" , bookId);
	}

	@Override
	public int insertBook(Map<String, Object> params) {
		return (Integer) getSqlMapClientTemplate().insert("Smart.insertBook" , params);
	}

	@Override
	public void updateBook(Map<String, Object> params) {
		getSqlMapClientTemplate().update("Smart.updateBook" , params);
	}

	@Override
	public int getHeadwordListCount(Map<String, Object> params) {
		return (Integer) getSqlMapClientTemplate().queryForObject("Smart.getHeadwordListCount" , params);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Headword> getHeadwordList(Map<String, Object> params) {
		return (List<Headword>) getSqlMapClientTemplate().queryForList("Smart.getHeadwordList" , params);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Headword> getHeadwordSearchList(Map<String, Object> params) {
		return (List<Headword>) getSqlMapClientTemplate().queryForList("Smart.getHeadwordSearchList", params);
	}
	
	@Override
	public Headword getHeadword(int headword) {
		return (Headword) getSqlMapClientTemplate().queryForObject("Smart.getHeadword" , headword);
	}

	//이전글 
	@Override
	public Headword getPrevHeadword(Map<String, Object> params) { 
		return (Headword) getSqlMapClientTemplate().queryForObject("Smart.getPrevHeadword", params);
	}
	
	//다음글
	@Override
	public Headword getNextHeadword(Map<String, Object> params) {
		return (Headword) getSqlMapClientTemplate().queryForObject("Smart.getNextHeadword", params);
	}
	
	@Override
	public int insertHeadword(Map<String, Object> params) {
		return (Integer) getSqlMapClientTemplate().insert("Smart.insertHeadword", params);
	}

	@Override
	public void updateHeadword(Map<String, Object> params) {
		getSqlMapClientTemplate().update("Smart.updateHeadword" , params);
	}
	
	
	@Override
	public void deleteHeadword(int headwordId){
		getSqlMapClientTemplate().delete("Smart.deleteHeadword" , headwordId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<History> getHistoryList(int headwordId) {
		return (List<History>) getSqlMapClientTemplate().queryForList("Smart.getHistoryList" , headwordId);
	}

	@Override
	public int insertHistory(Map<String, Object> params) {
		return (Integer) getSqlMapClientTemplate().insert("Smart.insertHistory" , params);
	}
	
	@Override
	public void updateHistory(Map<String, Object> params) {
		getSqlMapClientTemplate().update("Smart.updateHistory" , params);
	}

	@Override
	public void deleteHistory(int historyId) {
		getSqlMapClientTemplate().delete("Smart.deleteHistory" , historyId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Pool> getPoolList(int headwordId) {
		return (List<Pool>) getSqlMapClientTemplate().queryForList("Smart.getPoolList", headwordId);
	}

	@Override
	public Pool getPool(int poolId) {
		return (Pool) getSqlMapClientTemplate().queryForObject("Smart.getPool" , poolId);
	}

	@Override
	public int insertPool(Map<String, Object> params) {
		return (Integer) getSqlMapClientTemplate().insert("Smart.insertPool", params);
	}

	@Override
	public void updatePool(Map<String, Object> params) {
		getSqlMapClientTemplate().update("Smart.updatePool", params);
	}

	@Override
	public void deletePool(int poolId) {
		getSqlMapClientTemplate().delete("Smart.deletePool" , poolId);
	}

	@Override
	public int getConfirmedPoolCount(int headwordId) {
		return (Integer) getSqlMapClientTemplate().queryForObject("Smart.getConfirmedPoolCount" , headwordId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RelationHeadword> getRelationHeadwordList(int headwordId) {
		return (List<RelationHeadword>) getSqlMapClientTemplate().queryForList("Smart.getRelationHeadwordList", headwordId);
	}

	@Override
	public int insertRelationHeadword(Map<String, Object> params) {
		return (Integer) getSqlMapClientTemplate().insert("Smart.insertRelationHeadword", params);
	}

	@Override
	public void deleteRelationHeadword(int relationId) {
		getSqlMapClientTemplate().delete("Smart.deleteRelationHeadword", relationId);
	}
	
	@Override
	public void insertHeadwordCopy(Map<String, Object> params){
		getSqlMapClientTemplate().insert("Smart.insertHeadwordCopy", params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Multimedia> getAdditionalList(int headwordId) {
		return (List<Multimedia>) getSqlMapClientTemplate().queryForList("Smart.getAdditionalList" , headwordId);
	}

	@Override
	public void insertAdditional(Map<String, Object> params) {
		getSqlMapClientTemplate().insert("Smart.insertAdditional" , params);
	}

	@Override
	public void deleteAdditional(Map<String, Object> params) {
		getSqlMapClientTemplate().delete("Smart.deleteAdditional" , params);
	}

	@Override
	public void insertKnowledge(Map<String, Object> params) {
		getSqlMapClientTemplate().insert("Smart.insertKnowledge" , params);
	}

	@Override
	public int getKnowledgeListCount(Map<String, Object> params) {
		return (Integer) getSqlMapClientTemplate().queryForObject("Smart.getKnowledgeListCount", params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Knowledge> getKnowledgeList(Map<String, Object> params) {
		return (List<Knowledge>) getSqlMapClientTemplate().queryForList("Smart.getKnowledgeList" , params);
	}
	
	@Override
	public Knowledge getKnowledge(int knowledgeId) {
		return (Knowledge) getSqlMapClientTemplate().queryForObject("Smart.getKnowledge", knowledgeId);
	}

	@Override
	public void updateKnowledge(Map<String, Object> params) {
		getSqlMapClientTemplate().update("Smart.updateKnowledge" , params);
	}

	@Override
	public void deleteKnowledge(int knowledgeId) {
		getSqlMapClientTemplate().delete("Smart.deleteKnowledge" , knowledgeId);
	}

	@Override
	public int getCommentGroupId(int headwordId) {
		return (Integer) getSqlMapClientTemplate().queryForObject("Smart.getCommentGroupId", headwordId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Comment> getCommentList(Map<String, Object> params) {
		return (List<Comment>) getSqlMapClientTemplate().queryForList("Smart.getCommentList", params);
	}

	@Override
	public Comment getComment(int commentId) {
		return (Comment) getSqlMapClientTemplate().queryForObject("Smart.getComment", commentId);
	}

	@Override
	public int insertComment(Map<String, Object> params) {
		return (Integer) getSqlMapClientTemplate().insert("Smart.insertComment" , params);
	}

	@Override
	public void deleteComment(int commentId) {
		getSqlMapClientTemplate().delete("Smart.deleteComment" , commentId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Category> getCategoryWithBooksCount(Map<String, Object> params) {
		return (List<Category>) getSqlMapClientTemplate().queryForList("Smart.getCategoryWithBooksCount", params);
	}

	@Override
	public void insertLog(int headwordId) {
		getSqlMapClientTemplate().insert("Smart.insertLog" , headwordId);
	}
	
	@Override
	public void updateHeadwordHit(int headwordId) {
		getSqlMapClientTemplate().update("Smart.updateHeadwordHit" , headwordId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Headword> getHotKeywords() {
		return (List<Headword>) getSqlMapClientTemplate().queryForList("Smart.getHotKeywords");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<History> getHistorySearchList(Map<String, Object> params) {
		return (List<History>) getSqlMapClientTemplate().queryForList("Smart.getHistorySearchList", params);
	}

	@Override
	public void deleteThumbnail(int headwordId) {
		getSqlMapClientTemplate().update("Smart.deleteThumbnail" , headwordId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<History> getCalendarHistoryList(String findMonth) {
		return (List<History>) getSqlMapClientTemplate().queryForList("Smart.getCalendarHistoryList", findMonth);
	}

	@Override
	public void changeKnowledgeStatus(Map<String, Object> params) {
		getSqlMapClientTemplate().update("Smart.changeKnowledgeStatus" , params);
	}

	@Override
	public int getTotalCommentListCount() {
		return (Integer) getSqlMapClientTemplate().queryForObject("Smart.getTotalCommentListCount");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Comment> getTotalCommentList(Map<String, Object> params) {
		return (List<Comment>) getSqlMapClientTemplate().queryForList("Smart.getTotalCommentList", params);
	}

	@Override
	public int getIsChildeNode(int commentId) {
		return (Integer) getSqlMapClientTemplate().queryForObject("Smart.getIsChildeNode", commentId);
	}

	@Override
	public void updateCommentToDelete(int commentId) {
		getSqlMapClientTemplate().update("Smart.updateCommentToDelete", commentId);
	}

	@Override
	public int getGroupCommentCount(Map<String, Object> params) {
		return (Integer) getSqlMapClientTemplate().queryForObject("Smart.getGroupCommentCount", params) ;
	}

	@Override
	public Comment getLastCommentData(Map<String, Object> params) {
		return (Comment) getSqlMapClientTemplate().queryForObject("Smart.getLastCommentData", params);
	}

	@Override
	public History getDicFrontHistory() {
		return (History) getSqlMapClientTemplate().queryForObject("Smart.getDicFrontHistory");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Headword> getDicExhibitList(Map<String, Object> params) {
		return (List<Headword>) getSqlMapClientTemplate().queryForList("Smart.getDicExhibitList" , params);
	}

	@Override
	public Headword getDicExhibit(Map<String, Object> params) {
		return (Headword) getSqlMapClientTemplate().queryForObject("Smart.getDicExhibitList", params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<History> getMainCalendarHistoryList(String findDate) {
		return (List<History>) getSqlMapClientTemplate().queryForList("Smart.getMainCalendarHistoryList", findDate);
	}

	@Override
	public void insertSearchWord(Map<String, Object> params) {
		getSqlMapClientTemplate().insert("Smart.insertSearchWord" , params);
	}

	@Override
	public void studyDone(Map<String, Object> params) {
		getSqlMapClientTemplate().insert("Smart.studyDone" , params);
	}

	@Override
	public List<ResultMap> getUnitPathDataList() {
		return (List<ResultMap>) getSqlMapClientTemplate().queryForList("Smart.getUnitPathDataList");
	}

	@Override
	public void deleteHeadwordHistory(int headwordId) {
		getSqlMapClientTemplate().delete("Smart.deleteHeadwordHistory" , headwordId);
	}

	@Override
	public void deleteHeadwordKnowledge(int headwordId) {
		getSqlMapClientTemplate().delete("Smart.deleteHeadwordKnowledge" , headwordId);
	}

	@Override
	public void deleteHeadwordComment(int headwordId) {
		getSqlMapClientTemplate().delete("Smart.deleteHeadwordComment" , headwordId);
	}

	@Override
	public void deleteHeadwordPool(int headwordId) {
		getSqlMapClientTemplate().delete("Smart.deleteHeadwordPool" , headwordId);
	}

	@Override
	public int getStudyCount(Map<String, Object> params) {
		return (Integer) getSqlMapClientTemplate().queryForObject("Smart.getStudyCount", params);
	}

	@Override
	public void deleteSmartBook(int bookId) {
		getSqlMapClientTemplate().delete("Smart.deleteSmartBook" , bookId);
	}
}
