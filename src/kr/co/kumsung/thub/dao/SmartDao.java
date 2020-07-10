package kr.co.kumsung.thub.dao;

import java.util.List;
import java.util.Map;

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
import kr.co.kumsung.thub.util.ResultMap;

public interface SmartDao {

	/**
	 * 총 도서 갯수를 반환한다.
	 * @return
	 */
	public int getTotalBooks(Map<String,Object> params);
	
	/**
	 * 도서리스트를 반환한다.
	 * @return
	 */
	public List<Book> getBookList(Map<String,Object> params);
	
	/**
	 * 모든 도서 리스트를 반환한다.
	 * @return
	 */
	public List<Book> getTotalBookList();
	
	/**
	 * 카테고리에 속한 도서리스트를 반환한다.
	 * @param params
	 * @return
	 */
	public List<Book> getBookListByCategory(Map<String,Object> params);
	
	/**
	 * 도서 정보를 가지고 온다.
	 * @param bookId
	 * @return
	 */
	public Book getBook(int bookId);
	
	/**
	 * 도서를 등록한다.
	 * @param params
	 * @return
	 */
	public int insertBook(Map<String,Object> params);
	
	/**
	 * 도서의 정보를 갱신한다.
	 * @param params
	 */
	public void updateBook(Map<String,Object> params);
	
	/**
	 * 표제어의 리스트카운트를 가지고 온다.
	 * @param params
	 * @return
	 */
	public int getHeadwordListCount(Map<String,Object> params);
	
	/**
	 * 표제어의 리스트를 가지고 온다.
	 * @param params
	 * @return
	 */
	public List<Headword> getHeadwordList(Map<String,Object> params);
	
	/**
	 * 통합검색용 표제어의 리스트를 가지고 온다.(역사전시관 기준)
	 * @param params
	 * @return
	 */
	public List<Headword> getHeadwordSearchList(Map<String,Object> params);
	
	/**
	 * 표제어를 가지고 온다.
	 * @param params
	 * @return
	 */
	public Headword getHeadword(int headwordId);
	
	/**
	 * 이전글을 가지고 온다.
	 * @return
	 */
	public Headword getPrevHeadword(Map<String,Object> params); 
	
	/**
	 * 다음글을 가지고 온다.
	 * @param params
	 * @return
	 */
	public Headword getNextHeadword(Map<String,Object> params);
	
	/**
	 * 표제어를 등록한다.
	 * @param params
	 * @return
	 */
	public int insertHeadword(Map<String,Object> params);
	
	/**
	 * 표제어를 수정한다.
	 * @param params
	 */
	public void updateHeadword(Map<String,Object> params);
	
	/**
	 * 표제어를 삭제한다.
	 * @param headwordId
	 */
	public void deleteHeadword(int headwordId);
	
	/**
	 * 표제어에 속한 인물,사건 리스트를 가지고 온다.
	 * @param headwordId
	 * @return
	 */
	public List<History> getHistoryList(int headwordId);
	
	/**
	 * 인물,사건 리스트를 등록한다.
	 * @param params
	 * @return
	 */
	public int insertHistory(Map<String,Object> params);
	
	/**
	 * 같은 날짜에 대하여 우선노출을 초기화한다.
	 * @param params
	 */
	public void updateHistory(Map<String,Object> params);
	
	/**
	 * 인물,사건 리스트를 삭제한다.
	 * @param headwordId
	 */
	public void deleteHistory(int historyId);
	
	/**
	 * 문제의 리스트를 가지고 온다.
	 * @param headwordId
	 * @return
	 */
	public List<Pool> getPoolList(int headwordId);
	
	/**
	 * 문제를 가지고 온다.
	 * @param poolId
	 * @return
	 */
	public Pool getPool(int poolId);
	
	/**
	 * 문제를 등록한다.
	 * @param params
	 * @return
	 */
	public int insertPool(Map<String,Object> params);
	
	/**
	 * 문제를 갱신한다.
	 * @param params
	 */
	public void updatePool(Map<String,Object> params);
	
	/**
	 * 문제를 삭제한다.
	 * @param params
	 */
	public void deletePool(int poolId);
	
	/**
	 * 확인문제가 이미 등록이 되어있는지 체크한다.
	 * @param headwordId
	 * @return
	 */
	public int getConfirmedPoolCount(int headwordId);
	
	/**
	 * 관련 표제어의 리스트를 가지고 온다.
	 * @param headwordId
	 * @return
	 */
	public List<RelationHeadword> getRelationHeadwordList(int headwordId);
	
	/**
	 * 관련 표제어를 등록한다.
	 * @param params
	 * @return
	 */
	public int insertRelationHeadword(Map<String,Object> params);
	
	/**
	 * 관련 표제어를 삭제한다.
	 * @param relationId
	 */
	public void deleteRelationHeadword(int relationId);
	
	
	/**
	 * 관련 표제어를 복사한다.
	 * @param relationId
	 */
	public void insertHeadwordCopy(Map<String, Object> params);
	
	/**
	 * 참고 자료 리스트를 가지고 온다.
	 * @param headwordId
	 * @return
	 */
	public List<Multimedia> getAdditionalList(int headwordId);
	
	/**
	 * 참고자료를 등록한다.
	 * @param params
	 */
	public void insertAdditional(Map<String,Object> params);
	
	/**
	 * 참고자료를 삭제한다.
	 * @param params
	 */
	public void deleteAdditional(Map<String,Object> params);
	
	/**
	 * 지식나눔 등록
	 * @param params
	 */
	public void insertKnowledge(Map<String,Object> params);
	
	/**
	 * 지식나눔 리스트 갯수를 가지고 온다.
	 * @param params
	 * @return
	 */
	public int getKnowledgeListCount(Map<String,Object> params);
	
	/**
	 * 지식나눔 가지고 오기
	 * @param knowledgeId
	 * @return
	 */
	public Knowledge getKnowledge(int knowledgeId);
	
	/**
	 * 지식나눔의 리스트를 가지고 온다.
	 * @param params
	 * @return
	 */
	public List<Knowledge> getKnowledgeList(Map<String,Object> params);
	
	/**
	 * 지식나눔 갱신
	 * @param params
	 */
	public void updateKnowledge(Map<String,Object> params);
	
	/**
	 * 지식나눔 삭제
	 * @param knowledgeId
	 */
	public void deleteKnowledge(int knowledgeId);
	
	/**
	 * 댓글의 그룹 아이디를 가지고 온다.
	 * @param boardId
	 * @return
	 */
	public int getCommentGroupId(int headwordId);
	
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
	 * 카테고리의 리스트를 가지고 온다.(속한 도서의 카운트와 함께)
	 * @param params
	 * @return
	 */
	public List<Category> getCategoryWithBooksCount(Map<String,Object> params);
	
	/**
	 * 표제어 조회 로그를 남긴다.
	 * @param headwordId
	 */
	public void insertLog(int headwordId);
	
	/**
	 * 표제어 조회수를 증가시킨다.
	 * @param headwordId
	 */
	public void updateHeadwordHit(int headwordId);
	
	/**
	 * 핫키워드 리스트를 가지고 온다.
	 * @return
	 */
	public List<Headword> getHotKeywords();
	
	/**
	 * 역사전시관 통합검색
	 * @param params
	 * @return
	 */
	public List<History> getHistorySearchList(Map<String,Object> params);
	
	/**
	 * 썸네일을 삭제한다.
	 * @param headwordId
	 */
	public void deleteThumbnail(int headwordId);
	
	/**
	 * 인물사건 캘린더의 프론트 리스트
	 * @param findMonth
	 * @return
	 */
	public List<History> getCalendarHistoryList(String findMonth);
	
	/**
	 * 지식나눔의 상태를 변경한다.
	 * @param params
	 */
	public void changeKnowledgeStatus(Map<String,Object> params);
	
	/**
	 * 모든 댓글의 카운트를 가지고 온다.
	 * @return
	 */
	public int getTotalCommentListCount();
	
	/**
	 * 모든 댓글의 리스트를 가지고 온다.
	 * @param params
	 * @return
	 */
	public List<Comment> getTotalCommentList(Map<String,Object> params);
	
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
	 * 현재 그룹의 코멘트 갯수를 가지고 온다.
	 * @param params
	 * @return
	 */
	public int getGroupCommentCount(Map<String,Object> params);
	
	/**
	 * 현재 그룹의 최상단의 코멘트중 삭제된 것이 있으면 데이타를 가지고 온다.
	 */
	public Comment getLastCommentData(Map<String,Object> params);
	
	/**
	 * 백과 메인의 역사 캘린더 컨텐츠 가지고 오기.
	 * @return
	 */
	public History getDicFrontHistory();
	
	/**
	 * 역사 전시관 메인용 데이타 가지고 오기
	 * @param findExhibit
	 * @return
	 */
	public List<Headword> getDicExhibitList(Map<String,Object> params);
	
	/**
	 * 역사 전시관 메인용 데이타 가지고 오기(단일 파일용)
	 * @param findExhibit
	 * @return
	 */
	public Headword getDicExhibit(Map<String,Object> params);
	
	/**
	 * 메인의 인물사건캘린더
	 * @param findDate
	 * @return
	 */
	public List<History> getMainCalendarHistoryList(String findDate);
	
	/**
	 * 검색어 저장
	 * @param params
	 */
	public void insertSearchWord(Map<String,Object> params);
	
	/**
	 * 학습 완료
	 * @param params
	 */
	public void studyDone(Map<String,Object> params);
	
	public List<ResultMap> getUnitPathDataList();
	
	/**
	 * 표제어 삭제
	 * @param headwordId
	 */
	public void deleteHeadwordHistory(int headwordId);
	
	public void deleteHeadwordKnowledge(int headwordId);
	
	public void deleteHeadwordComment(int headwordId);
	
	public void deleteHeadwordPool(int headwordId);

	/**
	 * 표제어 학습 여부
	 * @param params
	 * @return
	 */
	public int getStudyCount(Map<String,Object> params);

	/**
	 * 표제어 도서 삭제
	 * @param bookId
	 */
	public void deleteSmartBook(int bookId);


	
}
