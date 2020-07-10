package kr.co.kumsung.thub.dao;

import java.util.List;
import java.util.Map;

import kr.co.kumsung.thub.domain.Data;
import kr.co.kumsung.thub.domain.Poll;
import kr.co.kumsung.thub.domain.PollEntry;
import kr.co.kumsung.thub.domain.PollEntryItem;
import kr.co.kumsung.thub.domain.PollItem;
import kr.co.kumsung.thub.domain.PollResponse;

public interface PollDao {

	/**
	 * 설문지 등록
	 * @param params
	 * @return 
	 */
	public void insertPoll(Map<String,Object> params);

	/**
	 * 설문지 상세보기
	 * @param pollId
	 * @return Poll
	 */
	public Poll getPollDetail(int pollId);
	
	/**
	 * 설문지 리스트
	 * @param params
	 * @return List<Poll>
	 */
	public List<Poll> getPollList(Map<String,Object> params);
	
	/**
	 * 설문지 리스트 개수
	 * @param params
	 * @return int
	 */
	public int getTotalPoll(Map<String,Object> params);
	
	/**
	 * 설문지 수정
	 * @param params
	 * @return 
	 */
	public void updatePoll(Map<String,Object> params);
	
	/**
	 * 설문지 삭제
	 * @param params
	 * @return 
	 */
	public void deletePoll(Map<String,Object> params);
	
	/**
	 * 설문 조사 문항 답변 리스트
	 * @param params
	 * @return 
	 */
	public List<PollEntryItem> getPollEntryItemList(int params);
	
	/**
	 * 설문 조사 문항 입력
	 * @param params
	 * @return 
	 */
	public void insertPollEntry(Map<String, Object> params);
	
	/**
	 * 설문지 마지막 id 값
	 * @param params
	 * @return int
	 */
	public int getEntryId();
	
	/**
	 * 설문조사 문항 리스트
	 * @param params
	 * @return List<PollEntry>
	 */
	public List<PollEntry> getPollEntryList(Map<String, Object> params);
	
	/**
	 * 설문조사 문항 리스트 개수
	 * @param params
	 * @return int
	 */
	public int getTotalPollEntry(Map<String, Object> params);
	
	/**
	 * 설문조사 문항 상세 보기
	 * @param params
	 * @return PollEntry
	 */
	public PollEntry getPollEntryDetail (Map<String, Object> params);
	
	/**
	 * 설문조사 문항 팝업
	 * @param params
	 * @return List<PollEntry>
	 */
	public List<PollEntry> getPollEntryPopup (Map<String, Object> params);
	
	/**
	 * 설문조사 문항 팝업 개수
	 * @param params
	 * @return int
	 */
	public int getTotalPollEntryPopup (Map<String, Object> params);
	
	/**
	 * 설문조사 문항 수정
	 * @param params
	 * @return 
	 */
	public void updatePollEntry (Map<String, Object> params);
	
	/**
	 * 설문지 - 설문조사 문항 맵핑 등록
	 * @param params
	 * @return 
	 */
	public void updatePollEntryDetail (Map<String, Object> params);
	
	/**
	 * 설문지 등록된 문항 리스트
	 * @param params
	 * @return List<PollEntry>
	 */
	public List<PollEntry> getPollEntryed (Map<String, Object> params);
	
	/**
	 * 설문조사 문항 삭제 
	 * @param params
	 * @return 
	 */
	public void deletePollEntry(Map<String, Object> params);
	
	/**
	 * 설문지 삭제시 하위 설문 문항들 초기화
	 * @param params
	 * @return 
	 */
	public void updatePollEntryReset(int pollId);

	/**
	 * 설문조사 답변 입력
	 * @param params
	 * @return 
	 */
	public void insertPollItem(Map<String, Object> params);

	/**
	 * 설문조사 답변 수정
	 * @param params
	 * @return 
	 */
	public void updatePollItem(Map<String, Object> params);

	/**
	 * 설문조사 답변 상세보기
	 * @param params
	 * @return 
	 */
	public List<PollItem> getPollItemDetail (Map<String, Object> params);
	
	/**
	 * 설문조사 답변 삭제
	 * @param params
	 * @return 
	 */
	public void deletePollItem(Map<String, Object> params);
	
	/**
	 * 설문조사 사용자 응답
	 * @param params
	 * @return 
	 */
	public void insertPollResponse(Map<String, Object> params);
	
	/**
	 * 설문조사 설문 중복 참여 체크
	 * @param params
	 * @return int
	 */
	public int getPollResponseDup(Map<String, Object> params);
	
	/**
	 * 설문조사 설문지 문항, 답변 데이터 가져오기
	 * @param params
	 * @return List<PollResponse>
	 */
	public List<PollResponse> getPollResponseDetail(Map<String, Object> params);

	/**
	 * 설문조사 결과 데이터 가져오기
	 * @param pollId
	 * @return List<PollResponse>
	 */
	public List<PollResponse> getPollResult(int pollId);
	
	/**
	 * 설문조사 결과 주관식 레이어 팝업용
	 * @param params
	 * @return List<PollResponse>
	 */
	public List<PollResponse> getPollResultLayer(Map<String, Object> params);
	/**
	 * 설문조사 결과 기타 레이어 팝업용
	 * @param params
	 * @return List<PollResponse>
	 */
	public List<PollResponse> getPollResultEtcLayer(Map<String, Object> params);	
	/**
	 * 설문조사 통계 리스트 Admin
	 * @param params
	 * @return List<PollResponse>
	 */
	public List<PollResponse> getPollResultList(Map<String, Object> params);
	
	/**
	 * 설문조사 통계 리스트 개수
	 * @param params
	 * @return int
	 */
	public int getPollResultTotalList(Map<String, Object> params);
	
	/**
	 * 설문조사 참여자 상세 내역
	 * @param params
	 * @return List<PollResponse>
	 */
	public List<PollResponse> getPollResponseUserInfo(Map<String, Object> params);
	
	/**
	 * 설문조사 참여자 수
	 * @param params
	 * @return int
	 */
	public int getPollResponseUserInfoTotal(Map<String, Object> params);
	
	/**
	 * 특정항목의 설문번호
	 * @param params
	 * @return int
	 */
	public int getEntryPollId(Map<String, Object> params);
	
	/**
	 * 특정항목 복사
	 * @param params
	 * @return 
	 */
	public void insertEntryCopy(Map<String,Object> params);
	
	/**
	 * 특정항목 세부복사
	 * @param params
	 * @return 
	 */
	public void insertEntryDetailCopy(Map<String,Object> params);
	
	public PollResponse tqevent_select(String user_id);
	
	public PollResponse tqevent_cnt(String user_id);
	
	public void tqevent_insert(Map<String,Object> params);
	
	public PollResponse tqevent_select_test(String user_id);
	
	public PollResponse tqevent_cnt_test(String user_id);
	
	public void tqevent_insert_test(Map<String,Object> params);
	
	public PollResponse cnt_tqquizevent(String user_id);
	
	public void tqquizevent_insert(Map<String,Object> params);
	
	public PollResponse chk_teacher(String user_id);
	
	public PollResponse cnt_tq_test(String user_id);
	
	public void tq_test_insert(Map<String,Object> params);
	
	public PollResponse cnt_tq_test_190529(Map<String,Object> params);
	
	public void tq_test_insert_190529(Map<String,Object> params);

	public PollResponse chk_500();
	
	public PollResponse usergap(Map<String,Object> params);
	
	public void tq_test_insert_190619(Map<String,Object> params);
}