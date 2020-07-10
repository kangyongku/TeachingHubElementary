package kr.co.kumsung.thub.dao.impl;

import java.util.List;
import java.util.Map;

import kr.co.kumsung.thub.dao.CategoryDao;
import kr.co.kumsung.thub.dao.EventDao;
import kr.co.kumsung.thub.domain.Article;
import kr.co.kumsung.thub.domain.Comment;
import kr.co.kumsung.thub.domain.Event;
import kr.co.kumsung.thub.domain.EventWin;
import kr.co.kumsung.thub.util.ResultMap;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class EventDaoImpl extends SqlMapClientDaoSupport implements EventDao{
	//이벤트 관리
	@Override
	public List<Event> getEventList(Map<String, Object> params){
		return (List<Event>) getSqlMapClientTemplate().queryForList("Event.getEventList", params);
	}
	
	@Override
	public int getEventTotalList(Map<String, Object> params){
		return (Integer) getSqlMapClientTemplate().queryForObject("Event.getEventTotalList", params);
	}
	
	@Override
	public Event getEventDetail(int eventId){
		               getSqlMapClientTemplate().update("Event.updateEventHits", eventId); // 180611 hits 추가		               
		return (Event) getSqlMapClientTemplate().queryForObject("Event.getEventDetail", eventId);
	}
	
	@Override
	public void insertEvent(Map<String, Object> params){
		getSqlMapClientTemplate().insert("Event.insertEvent", params);
	}
	
	@Override
	public void updateEvent(Map<String, Object> params){
		getSqlMapClientTemplate().update("Event.updateEvent", params);
	}
	
	@Override
	public void deleteEvent(int eventId){
		getSqlMapClientTemplate().delete("Event.deleteEvent", eventId);
	}
	
	@Override
	public String getEventDetailBanner(int eventId){
		return (String)getSqlMapClientTemplate().queryForObject("Event.getEventDetailBanner", eventId);
	}
	
	//이벤트 당첨자 관리
	@Override
	public List<EventWin> getEventWinList(Map<String, Object> params){
		return (List<EventWin>) getSqlMapClientTemplate().queryForList("Event.getEventWinList", params);
	}
	
	@Override
	public int getEventWinTotalList(Map<String, Object> params){
		return (Integer) getSqlMapClientTemplate().queryForObject("Event.getEventWinTotalList", params);
	}
	
	@Override
	public List<Event> getEventSelectList(){
		return (List<Event>) getSqlMapClientTemplate().queryForList("Event.getEventSelectList");
	}
	
	@Override
	public EventWin getEventWinDetail(int winId){
						  getSqlMapClientTemplate().update("Event.updateEventWinHits", winId);      // 181116 hits 추가	
		return (EventWin) getSqlMapClientTemplate().queryForObject("Event.getEventWinDetail", winId);
	}
	
	@Override
	public EventWin getEventWinDup(int eventId){
		return (EventWin) getSqlMapClientTemplate().queryForObject("Event.getEventWinDup", eventId);
	}
	
	@Override
	public void insertEventWin(Map<String, Object> params){
		getSqlMapClientTemplate().insert("Event.insertEventWin", params);
	}
	
	@Override
	public void updateEventWin(Map<String, Object> params){
		getSqlMapClientTemplate().update("Event.updateEventWin", params);
	}
	
	@Override
	public void deleteEventWin(int winId){
		getSqlMapClientTemplate().delete("Event.deleteEventWin", winId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ResultMap> getEvent01MyFlags(String userId) {
		return (List<ResultMap>) getSqlMapClientTemplate().queryForList("Event.getEvent01MyFlags", userId);
	}

	@Override
	public void insertEvent01MyFlag(Map<String, Object> params) {
		getSqlMapClientTemplate().insert("Event.insertEvent01MyFlag" , params);
	}

	@Override
	public void updateEvent01MyResult(String userId) {
		getSqlMapClientTemplate().update("Event.updateEvent01MyResult" , userId);
	}
	
	@Override
	public int getCommentTotalList(Map<String, Object> params){
		return (Integer) getSqlMapClientTemplate().queryForObject("Event.getComment", params);
	}
	
	@Override
	public Comment getComment1(int eventId) {
		return (Comment) getSqlMapClientTemplate().queryForObject("Event.getComment1", eventId);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Comment> getCommentList(Map<String, Object> params) {
		return (List<Comment>) getSqlMapClientTemplate().queryForList("Event.getCommentList", params);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Article> getArticleList(Map<String, Object> params) {
		return (List<Article>) getSqlMapClientTemplate().queryForList("Event.getArticleList" , params);
	}
	
	@Override
	public Article getArticle(int eventId){
		return (Article) getSqlMapClientTemplate().queryForObject("Event.getArticle" , eventId);
	}
	
	@Override
	public int getTotalCommentListCount(Map<String, Object> params) {
		return (Integer) getSqlMapClientTemplate().queryForObject("Event.getTotalCommentListCount", params);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Comment> getTotalCommentList(Map<String, Object> params) {
		return (List<Comment>) getSqlMapClientTemplate().queryForList("Event.getTotalCommentList", params);
	}

	
}
