package kr.co.kumsung.thub.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import kr.co.kumsung.thub.dao.EventDao;
import kr.co.kumsung.thub.domain.Article;
import kr.co.kumsung.thub.domain.Comment;
import kr.co.kumsung.thub.domain.Event;
import kr.co.kumsung.thub.domain.EventWin;
import kr.co.kumsung.thub.service.EventService;
import kr.co.kumsung.thub.util.ResultMap;

public class EventServiceImpl implements EventService{

	@Autowired
	private EventDao eventDao;

	//이벤트 관리
	@Override
	public List<Event> getEventList(Map<String, Object> params){
		return (List<Event>)eventDao.getEventList(params);
	}
	
	@Override
	public int getEventTotalList(Map<String, Object> params){
		return (Integer)eventDao.getEventTotalList(params);
	}
	
	@Override
	public Event getEventDetail(int eventId){
		return (Event)eventDao.getEventDetail(eventId);
	}
	
	@Override
	public void insertEvent(Map<String, Object> params){
		eventDao.insertEvent(params);
	}
	
	@Override
	public void updateEvent(Map<String, Object> params){
		eventDao.updateEvent(params);
	}
	
	@Override
	public void deleteEvent(int eventId){
		eventDao.deleteEvent(eventId);
	}
	
	@Override
	public String getEventDetailBanner(int eventId){
		return (String) eventDao.getEventDetailBanner(eventId);
	}
	
	//이벤트 당첨자 관리
	@Override
	public List<EventWin> getEventWinList(Map<String, Object> params){
		return (List<EventWin>) eventDao.getEventWinList(params);
	}
	
	@Override
	public int getEventWinTotalList(Map<String, Object> params){
		return (Integer) eventDao.getEventWinTotalList(params);
	}
	
	@Override
	public List<Event> getEventSelectList(){
		return (List<Event>) eventDao.getEventSelectList();
	}
	
	@Override
	public EventWin getEventWinDetail(int winId){
		return (EventWin) eventDao.getEventWinDetail(winId);
	}
	
	@Override
	public EventWin getEventWinDup(int eventId){
		return (EventWin) eventDao.getEventWinDup(eventId);
	}
	
	@Override
	public void insertEventWin(Map<String, Object> params){
		eventDao.insertEventWin(params);
	}
	
	@Override
	public void updateEventWin(Map<String, Object> params){
		eventDao.updateEventWin(params);
	}
	
	@Override
	public void deleteEventWin(int winId){
		eventDao.deleteEventWin(winId);
	}

	@Override
	public List<ResultMap> getEvent01MyFlags(String userId) {
		return eventDao.getEvent01MyFlags(userId);
	}

	@Override
	public void insertEvent01MyFlag(Map<String, Object> params) {
		eventDao.insertEvent01MyFlag(params);
	}

	@Override
	public void updateEvent01MyResult(String userId) {
		eventDao.updateEvent01MyResult(userId);
	}
	
	@Override
	public int getCommentTotalList(Map<String, Object> params){
		return (Integer)eventDao.getCommentTotalList(params);
	}
	
	@Override
	public Comment getComment1(int eventId) {
		return eventDao.getComment1(eventId);
	}
	
	@Override
	public List<Comment> getCommentList(Map<String, Object> params) {
		return eventDao.getCommentList(params);
	}
	
	@Override
	public List<Article> getArticleList(Map<String, Object> params) {
		return eventDao.getArticleList(params);
	}
	
	@Override
	public Article getArticle(int eventId) {
		return eventDao.getArticle(eventId);
	}
	
	@Override
	public int getTotalCommentListCount(Map<String, Object> params) {
		return eventDao.getTotalCommentListCount(params);
	}
	
	@Override
	public List<Comment> getTotalCommentList(Map<String, Object> params) {
		return eventDao.getTotalCommentList(params);
	}
}
