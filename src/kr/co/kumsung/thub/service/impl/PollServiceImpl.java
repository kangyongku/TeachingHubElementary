package kr.co.kumsung.thub.service.impl;


import java.util.List;
import java.util.Map;

import kr.co.kumsung.thub.dao.PollDao;
import kr.co.kumsung.thub.domain.Data;
import kr.co.kumsung.thub.domain.Poll;
import kr.co.kumsung.thub.domain.PollEntry;
import kr.co.kumsung.thub.domain.PollEntryItem;
import kr.co.kumsung.thub.domain.PollItem;
import kr.co.kumsung.thub.domain.PollResponse;
import kr.co.kumsung.thub.service.PollService;

import org.springframework.beans.factory.annotation.Autowired;
public class PollServiceImpl implements PollService{

	@Autowired
	private PollDao pollDao;
	
	//Poll master
	@Override
	public Poll getPollDetail(int pollId) {
		return pollDao.getPollDetail(pollId);
	}
	
	@Override
	public void insertPoll(Map<String,Object> params){
		pollDao.insertPoll(params);
	}
	
	@Override
	public List<Poll> getPollList(Map<String,Object> params){
		return (List<Poll>) pollDao.getPollList(params);
	}
	
	@Override
	public int getTotalPoll(Map<String,Object> params){
		return pollDao.getTotalPoll(params);
	}
	
	@Override
	public void updatePoll(Map<String,Object> params){
		pollDao.updatePoll(params);
	}
	
	@Override
	public void deletePoll(Map<String,Object> params){
		pollDao.deletePoll(params);
	}
	
	@Override
	public void deletePollEntry(Map<String, Object> params){
		pollDao.deletePollEntry(params);
	}
	
	@Override
	public List<PollEntryItem> getPollEntryItemList(int params){
		return (List<PollEntryItem>) pollDao.getPollEntryItemList(params);
	}
	
	@Override
	public void updatePollEntryReset(int pollId){
		pollDao.updatePollEntryReset(pollId);
	}
	//Poll Entry
	@Override
	public void insertPollEntry(Map<String, Object> params){
		pollDao.insertPollEntry(params);
	}
	
	@Override
	public int getEntryId(){
		return pollDao.getEntryId();
	}
	
	@Override
	public List<PollEntry> getPollEntryList(Map<String,Object> params){
		return (List<PollEntry>) pollDao.getPollEntryList(params);
	}
	
	@Override
	public int getTotalPollEntry(Map<String, Object> params){
		return (Integer) pollDao.getTotalPollEntry(params);
	}
	
	@Override
	public PollEntry getPollEntryDetail(Map<String, Object> params){
		return (PollEntry) pollDao.getPollEntryDetail(params);
	}
	
	@Override
	public List<PollEntry> getPollEntryPopup(Map<String, Object> params){
		return (List<PollEntry>) pollDao.getPollEntryPopup(params);
	}
	
	@Override
	public int getTotalPollEntryPopup(Map<String, Object> params){
		return pollDao.getTotalPollEntryPopup(params);
	}
	
	@Override
	public void updatePollEntry(Map<String, Object> params){
		pollDao.updatePollEntry(params);
	}
	
	@Override
	public void updatePollEntryDetail(Map<String, Object> params){
		pollDao.updatePollEntryDetail(params);
	}
	
	@Override
	public List<PollEntry> getPollEntryed(Map<String, Object> params){
		return (List<PollEntry>) pollDao.getPollEntryed(params);
	}
	
	//Poll Item
	@Override
	public void insertPollItem(Map<String, Object> params){
		pollDao.insertPollItem(params);
	}
	
	@Override
	public List<PollItem> getPollItemDetail(Map<String, Object> params){
		return (List<PollItem>) pollDao.getPollItemDetail(params);
	}

	@Override
	public void updatePollItem(Map<String, Object> params){
		pollDao.updatePollItem(params);
	}
	
	@Override
	public void deletePollItem(Map<String, Object> params){
		pollDao.deletePollItem(params);
	}
	
	@Override
	public void insertPollResponse(Map<String, Object> params){
		pollDao.insertPollResponse(params);
	}
	
	@Override
	public int getPollResponseDup(Map<String, Object> params){
		return pollDao.getPollResponseDup(params);
	}
	
	@Override
	public List<PollResponse> getPollResponseDetail(Map<String, Object> params){
		return (List<PollResponse>) pollDao.getPollResponseDetail(params);
	}
	
	@Override
	public List<PollResponse> getPollResult(int pollId){
		return (List<PollResponse>) pollDao.getPollResult(pollId);
	}
	
	@Override
	public List<PollResponse> getPollResultLayer(Map<String, Object> params){
		return	(List<PollResponse>) pollDao.getPollResultLayer(params);
	}
	@Override
	public List<PollResponse> getPollResultEtcLayer(Map<String, Object> params){
		return	(List<PollResponse>) pollDao.getPollResultEtcLayer(params);
	}	
	
	@Override
	public List<PollResponse> getPollResultList(Map<String, Object> params){
		return (List<PollResponse>) pollDao.getPollResultList(params);
	}
	
	@Override
	public int getPollResultTotalList(Map<String, Object> params){
		return (Integer) pollDao.getPollResultTotalList(params);
	}
	
	@Override
	public List<PollResponse> getPollResponseUserInfo(Map<String, Object> params){
		return (List<PollResponse>) pollDao.getPollResponseUserInfo(params);
	}
	
	@Override
	public int getPollResponseUserInfoTotal(Map<String, Object> params){
		return (Integer) pollDao.getPollResponseUserInfoTotal(params);
	}
	
	@Override
	public int getEntryPollId(Map<String, Object> params){
		return (Integer) pollDao.getEntryPollId(params);
	}
	
	@Override
	public void insertEntryCopy(Map<String,Object> params){
		pollDao.insertEntryCopy(params);
	}
	
	@Override
	public void insertEntryDetailCopy(Map<String,Object> params){
		pollDao.insertEntryDetailCopy(params);
	}
	
	@Override
	public PollResponse tqevent_select(String user_id){
		return (PollResponse) pollDao.tqevent_select(user_id);
	}	
	
	@Override
	public PollResponse tqevent_cnt(String user_id){
		return (PollResponse) pollDao.tqevent_cnt(user_id);
	}
	
	@Override
	public void tqevent_insert(Map<String,Object> params){
		pollDao.tqevent_insert(params);
	}		
	
	@Override
	public PollResponse tqevent_select_test(String user_id){
		return (PollResponse) pollDao.tqevent_select_test(user_id);
	}	
	
	@Override
	public PollResponse tqevent_cnt_test(String user_id){
		return (PollResponse) pollDao.tqevent_cnt_test(user_id);
	}
	
	@Override
	public void tqevent_insert_test(Map<String,Object> params){
		pollDao.tqevent_insert_test(params);
	}	
	
	@Override
	public PollResponse cnt_tqquizevent(String user_id){
		return (PollResponse) pollDao.cnt_tqquizevent(user_id);
	}
	
	@Override
	public void tqquizevent_insert(Map<String,Object> params){
		pollDao.tqquizevent_insert(params);
	}

	@Override
	public PollResponse chk_teacher(String user_id){
		return (PollResponse) pollDao.chk_teacher(user_id);
	}
	
	@Override
	public PollResponse cnt_tq_test(String user_id){
		return (PollResponse) pollDao.cnt_tq_test(user_id);
	}
	
	@Override
	public void tq_test_insert(Map<String,Object> params){
		pollDao.tq_test_insert(params);
	}
	
	@Override
	public PollResponse cnt_tq_test_190529(Map<String,Object> params){
		return (PollResponse) pollDao.cnt_tq_test_190529(params);
	}
	
	@Override
	public void tq_test_insert_190529(Map<String,Object> params){
		pollDao.tq_test_insert_190529(params);
	}
	
	@Override
	public PollResponse chk_500(){
		return (PollResponse) pollDao.chk_500();
	}
	
	@Override
	public PollResponse usergap(Map<String,Object> params){
		return (PollResponse) pollDao.usergap(params);
	}	

	@Override
	public void tq_test_insert_190619(Map<String,Object> params){
		pollDao.tq_test_insert_190619(params);
	}
}