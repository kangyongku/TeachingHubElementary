package kr.co.kumsung.thub.service.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import kr.co.kumsung.thub.dao.SmartDao;
import kr.co.kumsung.thub.domain.Book;
import kr.co.kumsung.thub.domain.Category;
import kr.co.kumsung.thub.domain.Comment;
import kr.co.kumsung.thub.domain.FileInfo;
import kr.co.kumsung.thub.domain.Headword;
import kr.co.kumsung.thub.domain.History;
import kr.co.kumsung.thub.domain.Knowledge;
import kr.co.kumsung.thub.domain.Multimedia;
import kr.co.kumsung.thub.domain.Pool;
import kr.co.kumsung.thub.domain.RelationHeadword;
import kr.co.kumsung.thub.domain.Unit;
import kr.co.kumsung.thub.service.FileService;
import kr.co.kumsung.thub.service.SmartService;
import kr.co.kumsung.thub.util.Common;
import kr.co.kumsung.thub.util.DateUtil;
import kr.co.kumsung.thub.util.ResultMap;
import kr.co.kumsung.thub.util.Validate;


public class SmartServiceImpl implements SmartService{

	@Autowired
	private SmartDao smartDao;
	
	@Autowired
	private FileService fileService;
	
	@Value("#{common['file.path']}") String serverFilePath;		// 첨부파일의 root 경로
	
	@Override
	public int getTotalBooks(Map<String,Object> params) {
		return smartDao.getTotalBooks(params);
	}

	@Override
	public List<Book> getBookList(Map<String, Object> params) {
		return smartDao.getBookList(params);
	}

	@Override
	public List<Book> getTotalBookList(){
		return smartDao.getTotalBookList();
	}
	
	@Override
	public Map<String,List<Book>> getTotalBookListToGroup(List<Book> books){
		Map<String,List<Book>> result = new HashMap<String,List<Book>>();
		
		
		if( books != null 
				&& books.size() > 0 )
		{
			for(Book book : books )
			{
				List<Book> children = (result.containsKey(book.getCategory())) ? (List<Book>) result.get(book.getCategory()) : new ArrayList<Book>();
				children.add(book);					
				result.put(book.getCategory() , children);
			}
		}
		
		return result;
	}
	
	@Override
	public List<Book> getBookListByCategory(String category) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("category" , category);
		
		return smartDao.getBookListByCategory(params);
	}

	@Override
	public Book getBook(int bookId) {
		return smartDao.getBook(bookId);
	}

	@Override
	public int insertBook(Map<String, Object> params) {		
		return smartDao.insertBook(params);
	}

	@Override
	public void updateBook(Map<String, Object> params) {
		smartDao.updateBook(params);
	}

	@Override
	public int getHeadwordListCount(Map<String, Object> params) {
		return smartDao.getHeadwordListCount(params);
	}
	
	@Override
	public List<Headword> getHeadwordList(Map<String, Object> params) {
		return smartDao.getHeadwordList(params);
	}
	
	@Override
	public Headword getHeadword(int headword) {
		return smartDao.getHeadword(headword);
	}

	@Override
	public Headword getPrevHeadword(Map<String, Object> params) {
		return smartDao.getPrevHeadword(params);
	}
	
	@Override
	public Headword getNextHeadword(Map<String, Object> params) {
		return smartDao.getNextHeadword(params);
	}
	
	@Override
	public int insertHeadword(Map<String, Object> params) {
		return smartDao.insertHeadword(params);
	}

	@Override
	public void updateHeadword(Map<String, Object> params) {
		smartDao.updateHeadword(params);
	}
	
	@Override
	public void deleteHeadword(int headwordId){
		smartDao.deleteHeadword(headwordId);
		smartDao.deleteHeadwordComment(headwordId);
		smartDao.deleteHeadwordHistory(headwordId);
		smartDao.deleteHeadwordKnowledge(headwordId);
		smartDao.deleteHeadwordPool(headwordId);
	}
	


	@Override
	public List<History> getHistoryList(int headwordId) {
		return smartDao.getHistoryList(headwordId);
	}
	
	@Override
	public List<Headword> getHeadwordSearchList(Map<String, Object> params) {
		return smartDao.getHeadwordSearchList(params);
	}

	@Override
	public int insertHistory(Map<String, Object> params) {
		
		// isPrimary의 값이 Y일 경우 기존의 데이타를 모두 초기화한다.
		String isPrimary = (String) params.get("isPrimary");
		
		if( isPrimary.equals("Y") )
			smartDao.updateHistory(params);
		
		return smartDao.insertHistory(params);
	}

	@Override
	public void deleteHistory(int historyId) {
		smartDao.deleteHistory(historyId);
	}

	@Override
	public List<Pool> getPoolList(int headwordId) {
		return smartDao.getPoolList(headwordId);
	}

	@Override
	public Pool getPool(int poolId) {
		return smartDao.getPool(poolId);
	}

	@Override
	public int insertPool(Map<String, Object> params) {
		return smartDao.insertPool(params);
	}

	@Override
	public void updatePool(Map<String, Object> params) {
		smartDao.updatePool(params);
	}

	@Override
	public void deletePool(int poolId) {
		smartDao.deletePool(poolId);
	}

	@Override
	public int getConfirmedPoolCount(int headwordId) {
		return smartDao.getConfirmedPoolCount(headwordId);
	}

	@Override
	public List<RelationHeadword> getRelationHeadwordList(int headwordId) {
		return smartDao.getRelationHeadwordList(headwordId);
	}

	@Override
	public int insertRelationHeadword(Map<String, Object> params) {
		return smartDao.insertRelationHeadword(params);
	}

	@Override
	public void deleteRelationHeadword(int relationId) {
		smartDao.deleteRelationHeadword(relationId);
	}
	
	@Override
	public void insertHeadwordCopy(Map<String, Object> params){
		String[] headwordIds = (String[]) params.get("headwordIds");
		Headword headword;
		
		for( String headwordId : headwordIds){
			
			headword = smartDao.getHeadword(Integer.parseInt(headwordId));
			params.put("headwordId", headwordId);
			if(!headword.getThumbnail().isEmpty()){
				FileInfo fileInfo = fileService.copy(serverFilePath+headword.getThumbnail().replace("/upfiles", ""), serverFilePath, "headword");
				params.put("thumbnail" , fileInfo.getAbsolutePath());
			}
			
			smartDao.insertHeadwordCopy(params);
		}
	}

	@Override
	public List<Multimedia> getAdditionalList(int headwordId) {
		return smartDao.getAdditionalList(headwordId);
	}

	@Override
	public void insertAdditional(Map<String, Object> params) {
		smartDao.insertAdditional(params);
	}

	@Override
	public void deleteAdditional(Map<String, Object> params) {
		smartDao.deleteAdditional(params);
	}

	@Override
	public void insertKnowledge(Map<String, Object> params) {
		smartDao.insertKnowledge(params);
	}

	@Override
	public int getKnowledgeListCount(Map<String, Object> params) {
		return smartDao.getKnowledgeListCount(params);
	}

	@Override
	public List<Knowledge> getKnowledgeList(Map<String, Object> params) {
		return smartDao.getKnowledgeList(params);
	}

	@Override
	public Knowledge getKnowledge(int knowledgeId) {
		return smartDao.getKnowledge(knowledgeId);
	}

	@Override
	public void updateKnowledge(Map<String, Object> params) {
		smartDao.updateKnowledge(params);
	}

	@Override
	public void deleteKnowledge(int knowledgeId) {
		smartDao.deleteKnowledge(knowledgeId);
	}

	@Override
	public int getCommentGroupId(int headwordId) {
		return smartDao.getCommentGroupId(headwordId);
	}

	@Override
	public List<Comment> getCommentList(Map<String, Object> params) {
		return smartDao.getCommentList(params);
	}

	@Override
	public Comment getComment(int commentId) {
		return smartDao.getComment(commentId);
	}

	@Override
	public int insertComment(Map<String, Object> params) {
		return smartDao.insertComment(params);
	}

	@Override
	public void deleteComment(int commentId) {
		// 현재 코멘트가 1 depth의 코멘트인지 체크한다.
		Comment comment = (Comment) getComment(commentId);
		
		if( comment.getDepth() == 1 )
		{
			int childNode = (Integer) smartDao.getIsChildeNode(commentId);
			
			if( childNode > 0 )
			{
				// 현재 상태를 삭제로만 처리한다.
				smartDao.updateCommentToDelete(commentId);
			}
			else
			{
				// 자식노드가 없으므로 삭제처리한다.
				smartDao.deleteComment(commentId);
			}
		}
		else
		{
			// 2 depth의 코멘트라면 그냥 삭제처리한다.
			smartDao.deleteComment(commentId);
			
			// 현재 그룹의 코멘트 갯수를 가지고 온다.
			Map<String,Object> params = new HashMap<String,Object>();
			
			params.put("headwordId" , comment.getHeadwordId());
			params.put("groupId" , comment.getGroupId());
			
			int count = smartDao.getGroupCommentCount(params);
			
			// 자식 노드가 삭제된 후 마지막으로 남은 데이타가 한개라면 해당 데이타를 가지고 온다.
			if( count == 1 )
			{
				Comment lastComment = smartDao.getLastCommentData(params);
				
				if (lastComment != null)
				{
					// 데이타가 null 이 아니라면 삭제된 데이타가 존재한다는 뜻이다. 삭제처리 한다.
					smartDao.deleteComment(lastComment.getCommentId());
				}
			}
		}
	}

	@Override
	public List<Category> getCategoryWithBooksCount(String category) {
		List<Category> list = new ArrayList<Category>();
		
		// 요청된 category의 depth를 가지고 온다.
		if( category != null )
		{	
			int length = category.length();
			int depth = 0;
			
			switch(length)
			{
			case 1 : 
				depth = 2;
				break;
			case 4 : 
				depth = 3;
				break;
			case 7 : 
				depth = 4;
				break;
			}
			
			if( depth > 0 )
			{
				Map<String,Object> params = new HashMap<String,Object>();
				
				params.put("category" , category);
				params.put("depth" , depth);
				
				list = smartDao.getCategoryWithBooksCount(params);
			}
		}
		
		return list;
	}

	@Override
	public void insertLog(int headwordId) {
		smartDao.insertLog(headwordId);
	}
	
	@Override
	public void updateHeadwordHit(int headwordId) {
		smartDao.updateHeadwordHit(headwordId);
	}

	@Override
	public List<Headword> getHotKeywords() {
		return smartDao.getHotKeywords();
	}

	@Override
	public List<History> getHistorySearchList(Map<String, Object> params) {
		return smartDao.getHistorySearchList(params);
	}

	@Override
	public void deleteThumbnail(int headwordId) {
		smartDao.deleteThumbnail(headwordId);
	}

	@Override
	public List<History> getCalendarHistoryList(String findMonth) {
		return smartDao.getCalendarHistoryList(findMonth);
	}

	@Override
	public void changeKnowledgeStatus(Map<String, Object> params) {
		smartDao.changeKnowledgeStatus(params);
	}

	@Override
	public int getTotalCommentListCount() {
		return smartDao.getTotalCommentListCount();
	}

	@Override
	public List<Comment> getTotalCommentList(Map<String, Object> params) {
		return smartDao.getTotalCommentList(params);
	}

	@Override
	public History getDicFrontHistory() {
		return smartDao.getDicFrontHistory();
	}

	@Override
	public List<Headword> getDicExhibitList(Map<String, Object> params) {
		return smartDao.getDicExhibitList(params);
	}

	@Override
	public Headword getDicExhibit(Map<String, Object> params) {
		return smartDao.getDicExhibit(params);
	}

	@Override
	public Map<String, Object> calendarData(HttpServletRequest request) {
		
		Map<String,Object> result = new HashMap<String,Object>();
		
		// get parameter
		String findYear = Common.getParameter(request, "findYear", "");
		String findMonth = Common.getParameter(request, "findMonth", "");
		
		if( Validate.isEmpty(findYear)
				|| Validate.isEmpty(findMonth))
		{
			// 입력된 날짜가 존재하지 않으므로 현재 날짜를 셋팅한다.
			String currentDate = DateUtil.getDate("yyyy-MM");
			String[] t = currentDate.split("-");
			
			findYear = t[0];
			findMonth = t[1];
		}
		
		// 오늘의 날짜를 가지고 온다.
		int currentDay = Integer.valueOf(DateUtil.getDate("dd"));
		
		// 주어진 날짜의 마지막 날자를 가지고 온다.
		Calendar cal = Calendar.getInstance();
		cal.set(Integer.valueOf(findYear), Integer.valueOf(findMonth) - 1, 1);
		int firstDay = 1;
		int lastDay = cal.getActualMaximum( Calendar.DATE );
		int startWeekOfDay = cal.get(Calendar.DAY_OF_WEEK);

		// 이전달을 구한다.
		cal.add(cal.MONTH , -1);
		SimpleDateFormat prevDateFormat = new SimpleDateFormat("yyyy-MM");
		String[] prevData = prevDateFormat.format(cal.getTime()).split("-");		
		String prevFindYear = prevData[0];
		String prevFindMonth = prevData[1];
		
		// 다음달을 구한다.
		cal.add(cal.MONTH , 2);
		SimpleDateFormat nextDateFormat = new SimpleDateFormat("yyyy-MM");
		String[] nextData = nextDateFormat.format(cal.getTime()).split("-");		
		String nextFindYear = nextData[0];
		String nextFindMonth = nextData[1];
		
		// 마지막날의 요일을 구한다.
		cal.set(Integer.valueOf(findYear), Integer.valueOf(findMonth) - 1, lastDay);
		int lastWeekOfDay = cal.get(Calendar.DAY_OF_WEEK);
		
		// 이전달의 마지막 날짜를 가지고 온다.
		cal.set(Integer.valueOf(findYear), Integer.valueOf(findMonth) - 2 , 1);
		int prevLastDay = cal.getActualMaximum(Calendar.DATE);
		
		// calendar index var
		List<ResultMap> calendarList = new ArrayList<ResultMap>();

		// 이전달에 대한 데이타를 입력해준다.
		if( startWeekOfDay > 1 )
		{
			int loopCnt = startWeekOfDay - 1;
			
			for(int i = 0 ; i < loopCnt ; i++)
			{
				ResultMap item = new ResultMap();
				
				item.put("disabled" , "true");
				item.put("date" , (prevLastDay - loopCnt) + (i + 1));
				
				calendarList.add(item);
			}
		}
		
		for(int i = 1 ; i <= lastDay ; i++ )
		{
			ResultMap item = new ResultMap();
			
			item.put("disabled" , "false");
			item.put("date" , i);
			
			calendarList.add(item);
		}
			
		
		// 마지막 날 이후의 데이타를 셋팅한다.
		if( lastWeekOfDay < 7 )
		{
			int loopCnt = 7 - lastWeekOfDay;
			
			for(int i = 1 ; i <=  loopCnt ; i++)
			{
				ResultMap item = new ResultMap();
				
				item.put("disabled" , "true");
				item.put("date" , i);
				
				calendarList.add(item);
			}
		}
		
		// get data
		List<History> histories = (List<History>) getCalendarHistoryList(findMonth);
		
		
		// 날짜별 hashmap으로..
		Map<String,Object> list = new HashMap<String,Object>();
		
		for(int i = 1 ; i <= lastDay ; i++ )
		{
			String key = String.format("%s%02d" , findMonth , i);
			List<History> values = new ArrayList<History>();
			
			for(History history : histories)
			{
				if( history.getHistoryDate().equals(key) )
					values.add(history);
			}
			
			list.put(key , values);
		}
		
		// assign
		result.put("findYear", findYear);
		result.put("findMonth", findMonth);
		
		result.put("prevFindYear", prevFindYear);
		result.put("prevFindMonth", prevFindMonth);
		
		result.put("nextFindYear", nextFindYear);
		result.put("nextFindMonth", nextFindMonth);
		
		result.put("currentDay", currentDay);
		result.put("firstDay", firstDay);
		result.put("lastDay", lastDay);
		result.put("startWeekOfDay", startWeekOfDay);
		result.put("prevLastDay", prevLastDay);
		
		result.put("calendarList", calendarList);				// 달력을 그리기 위한 데이타 값만 셋팅
		result.put("histories", histories);
		result.put("list", list);
		
		result.put("beforePath", "나눔 &middot; 소통");
		result.put("currentPath", "인물&middot;사건 캘린더");

		return result;
	}

	@Override
	public List<History> getMainCalendarHistoryList(String findDate) {
		return smartDao.getMainCalendarHistoryList(findDate);
	}

	@Override
	public void insertSearchWord(Map<String, Object> params) {
		smartDao.insertSearchWord(params);
	}

	@Override
	public void studyDone(Map<String, Object> params) {
		smartDao.studyDone(params);
	}

	@Override
	public List<ResultMap> getUnitPathDataList() {
		return smartDao.getUnitPathDataList();
	}

	@Override
	public int getStudyCount(Map<String, Object> params) {
		return smartDao.getStudyCount(params);
	}

	@Override
	public void deleteSmartBook(int bookId) {
		smartDao.deleteSmartBook(bookId);
	}


}
