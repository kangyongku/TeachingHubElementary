package kr.co.kumsung.thub.service;

import java.util.List;
import java.util.Map;

import kr.co.kumsung.thub.domain.Article;
import kr.co.kumsung.thub.domain.Comment;
import kr.co.kumsung.thub.domain.Event;
import kr.co.kumsung.thub.domain.EventWin;
import kr.co.kumsung.thub.util.ResultMap;

public interface EventService {
	
	/**
	 * 이벤트 리스트
	 * @param params
	 * @return List<Event>
	 */
	public List<Event> getEventList(Map<String, Object> params);
	
	/**
	 * 이벤트 리스트 개수
	 * @param params
	 * @return int
	 */
	public int getEventTotalList(Map<String, Object> params);

	/**
	 * 이벤트 상세보기
	 * @param eventId
	 * @return Event
	 */
	public Event getEventDetail(int eventId);
	
	/**
	 * 이벤트 입력
	 * @param params
	 * @return 
	 */
	public void insertEvent(Map<String, Object> params);
	
	/**
	 * 이벤트 수정
	 * @param params
	 * @return 
	 */
	public void updateEvent(Map<String, Object> params);
	
	/**
	 * 이벤트 삭제
	 * @param eventId
	 * @return 
	 */
	public void deleteEvent(int eventId);
	
	/**
	 * 이벤트 배너 이미지
	 * @param eventId
	 * @return String
	 */
	
	public String getEventDetailBanner(int eventId);
	
	/**
	 * 이벤트 당첨자 리스트
	 * @param params
	 * @return List<EventWin>
	 */
	public List<EventWin> getEventWinList(Map<String, Object> params);
	
	/**
	 * 이벤트 당첨자 리스트 개수
	 * @param params
	 * @return int
	 */
	public int getEventWinTotalList(Map<String, Object> params);
	
	/**
	 * 이벤트 당첨자 - 입력된 이벤트 리스트 가져오기
	 * @param params
	 * @return List<Event>
	 */
	public List<Event> getEventSelectList();
	
	/**
	 * 이벤트 당첨자 상세보기
	 * @param winId
	 * @return EventWin
	 */
	public EventWin getEventWinDetail(int winId);
	
	/**
	 * 이벤트 당첨자 - 입력된 이벤트 중복 체크
	 * @param eventId
	 * @return EventWin
	 */
	public EventWin getEventWinDup(int eventId);
	
	/**
	 * 이벤트 당첨자 입력
	 * @param params
	 * @return 
	 */
	public void insertEventWin(Map<String, Object> params);
	
	/**
	 * 이벤트 당첨자 수정
	 * @param params
	 * @return 
	 */
	public void updateEventWin(Map<String, Object> params);
	
	/**
	 * 이벤트 당첨자 삭제
	 * @param winId
	 * @return
	 */
	public void deleteEventWin(int winId);
	
	/**
	 * 이벤트 댓글 개수
	 * @param params
	 * @return int
	 */
	public int getCommentTotalList(Map<String, Object> params);
	
	/**
	 * 댓글을 가지고 온다.
	 * @param commentId
	 * @return
	 */
	public Comment getComment1(int eventId);
	
	/**
	 * 댓글의 리스트를 가지고 온다.
	 * @param params
	 * @return
	 */
	public List<Comment> getCommentList(Map<String,Object> params);
	
	/**
	 * 게시글의 리스트를 가지고 온다.
	 * @param params
	 * @return
	 */
	public List<Article> getArticleList(Map<String,Object> params);
	
	/**
	 * 게시글의 데이타를 가지고 온다.
	 * @param articleId
	 * @return
	 */
	public Article getArticle(int eventId);
	
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
	
	public List<ResultMap> getEvent01MyFlags(String userId);
	
	public void insertEvent01MyFlag(Map<String,Object> params);
	
	public void updateEvent01MyResult(String userId);
}
