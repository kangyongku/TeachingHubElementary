package kr.co.kumsung.thub.dao.impl;

import java.util.List;
import java.util.Map;

import kr.co.kumsung.thub.dao.PollDao;
import kr.co.kumsung.thub.domain.Data;
import kr.co.kumsung.thub.domain.Poll;
import kr.co.kumsung.thub.domain.PollEntry;
import kr.co.kumsung.thub.domain.PollEntryItem;
import kr.co.kumsung.thub.domain.PollItem;
import kr.co.kumsung.thub.domain.PollResponse;


import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.sun.org.apache.bcel.internal.generic.GETSTATIC;

public class PollDaoImpl extends SqlMapClientDaoSupport implements PollDao{
	
	//Poll master
	@Override
	public Poll getPollDetail(int pollId) {
		return (Poll) getSqlMapClientTemplate().queryForObject("Poll.getPollDetail" , pollId);
	}
	
	@Override
	public void insertPoll(Map<String,Object> params){
		getSqlMapClientTemplate().insert("Poll.insertPoll", params);
	}
	
	@Override
	public List<Poll> getPollList(Map<String,Object> params){
		return (List<Poll>) getSqlMapClientTemplate().queryForList("Poll.getPollList", params);
	}
	
	@Override
	public int getTotalPoll(Map<String,Object> params){
		return (Integer) getSqlMapClientTemplate().queryForObject("Poll.getTotalPoll", params);
	}
	
	@Override
	public void updatePoll(Map<String, Object> params){
		getSqlMapClientTemplate().update("Poll.updatePoll", params);
	}
	
	@Override
	public void deletePoll(Map<String, Object> params){
		getSqlMapClientTemplate().delete("Poll.deletePoll", params);
	}
	
	@Override
	public List<PollEntryItem> getPollEntryItemList(int params){
		return (List<PollEntryItem>)getSqlMapClientTemplate().queryForList("Poll.getPollEntryItemList", params);
	}
	
	//Poll Entry
	@Override
	public void insertPollEntry(Map<String, Object> params){
		getSqlMapClientTemplate().insert("Poll.insertPollEntry", params);
	}
	
	@Override
	public int getEntryId(){
		return (Integer) getSqlMapClientTemplate().queryForObject("Poll.getEntryId");
	}
	
	@Override
	public List<PollEntry> getPollEntryList(Map<String,Object> params){
		return (List<PollEntry>) getSqlMapClientTemplate().queryForList("Poll.getPollEntryList", params);
	}
	
	@Override
	public int getTotalPollEntry(Map<String, Object> params){
		return (Integer) getSqlMapClientTemplate().queryForObject("Poll.getTotalPollEntry", params);
	}
	
	@Override
	public PollEntry getPollEntryDetail(Map<String, Object> params){
		return (PollEntry) getSqlMapClientTemplate().queryForObject("Poll.getPollEntryDetail", params);
	}
	
	@Override
	public List<PollEntry> getPollEntryPopup(Map<String, Object> params){
		return (List<PollEntry>) getSqlMapClientTemplate().queryForList("Poll.getPollEntryPopup", params);
	}
	
	@Override
	public int getTotalPollEntryPopup(Map<String, Object> params){
		return (Integer) getSqlMapClientTemplate().queryForObject("Poll.getTotalPollEntryPopup", params);
	}
	
	@Override
	public void updatePollEntry(Map<String, Object> params){
		getSqlMapClientTemplate().update("Poll.updatePollEntry", params);
	}
	
	@Override
	public void updatePollEntryDetail(Map<String, Object> params){
		getSqlMapClientTemplate().update("Poll.updatePollEntryDetail", params);
	}
	
	@Override
	public List<PollEntry> getPollEntryed(Map<String, Object> params){
		return (List<PollEntry>) getSqlMapClientTemplate().queryForList("Poll.getPollEntryed", params);
	}
	
	@Override
	public void deletePollEntry(Map<String, Object> params){
		getSqlMapClientTemplate().delete("Poll.deletePollEntry", params);
	}

	@Override
	public void updatePollEntryReset(int pollId){
		getSqlMapClientTemplate().update("Poll.updatePollEntryReset", pollId);
	}
	
	//Poll Item
	@Override
	public void insertPollItem(Map<String, Object> params){
		getSqlMapClientTemplate().insert("Poll.insertPollItem", params);
	}
	
	@Override
	public void updatePollItem(Map<String, Object> params){
		getSqlMapClientTemplate().update("Poll.updatePollItem", params);
	}
	
	@Override
	public List<PollItem> getPollItemDetail(Map<String, Object> params){
		return (List<PollItem>) getSqlMapClientTemplate().queryForList("Poll.getPollItemDetail", params);
	}
	
	@Override
	public void deletePollItem(Map<String, Object> params){
		getSqlMapClientTemplate().delete("Poll.deletePollItem", params);
	}
	
	@Override
	public void insertPollResponse(Map<String, Object> params){
		getSqlMapClientTemplate().insert("Poll.insertPollResponse", params); 
	}
	
	@Override
	public int getPollResponseDup(Map<String, Object> params){
		return (Integer)getSqlMapClientTemplate().queryForObject("Poll.getPollResponseDup", params);
	}
	
	@Override
	public List<PollResponse> getPollResponseDetail(Map<String, Object> params){
		return (List<PollResponse>) getSqlMapClientTemplate().queryForList("Poll.getPollResponseDetail", params);
	}
	
	@Override
	public List<PollResponse> getPollResult(int pollId){
		return (List<PollResponse>) getSqlMapClientTemplate().queryForList("Poll.getPollResult", pollId);
	}
	
	@Override
	public List<PollResponse> getPollResultLayer(Map<String, Object> params){
		return (List<PollResponse>) getSqlMapClientTemplate().queryForList("Poll.getPollResultLayer", params); 
	}
	@Override
	public List<PollResponse> getPollResultEtcLayer(Map<String, Object> params){
		return (List<PollResponse>) getSqlMapClientTemplate().queryForList("Poll.getPollResultEtcLayer", params); 
	}
	@Override
	public List<PollResponse> getPollResultList(Map<String, Object> params){
		return (List<PollResponse>) getSqlMapClientTemplate().queryForList("Poll.getPollResultList", params);
	}
	
	@Override
	public int getPollResultTotalList(Map<String, Object> params){
		return (Integer) getSqlMapClientTemplate().queryForObject("Poll.getPollResultTotalList", params);
	}
	
	@Override
	public List<PollResponse> getPollResponseUserInfo(Map<String, Object> params){
		return (List<PollResponse>) getSqlMapClientTemplate().queryForList("Poll.getPollResponseUserInfo", params);
	}
	
	@Override
	public int getPollResponseUserInfoTotal(Map<String, Object> params){
		return (Integer) getSqlMapClientTemplate().queryForObject("Poll.getPollResponseUserInfoTotal", params);
	}
	
	@Override
	public int getEntryPollId(Map<String, Object> params){
		return (Integer) getSqlMapClientTemplate().queryForObject("Poll.getEntryPollId", params);
	}
	
	@Override
	public void insertEntryCopy(Map<String, Object> params){
		getSqlMapClientTemplate().insert("Poll.insertEntryCopy", params); 
	}
	
	@Override
	public void insertEntryDetailCopy(Map<String, Object> params){
		getSqlMapClientTemplate().insert("Poll.insertEntryDetailCopy", params); 
	}
	
	@Override
	public PollResponse tqevent_select(String user_id){
		return (PollResponse) getSqlMapClientTemplate().queryForObject("Poll.tqevent_select", user_id);
	}	
	
	@Override
	public PollResponse tqevent_cnt(String user_id){
		return (PollResponse) getSqlMapClientTemplate().queryForObject("Poll.tqevent_cnt", user_id);
	}	
	
	@Override
	public void tqevent_insert(Map<String, Object> params){
		String user_id = (String) params.get("user_id");
		PollResponse tqevent_cnt    = tqevent_cnt(user_id);
		
		int cnt = Integer.parseInt(tqevent_cnt.getCnt()); 

		if(cnt == 0) {
			getSqlMapClientTemplate().insert("Poll.tqevent_insert", params); 
		}
		else if(cnt >= 1) {
			getSqlMapClientTemplate().insert("Poll.tqevent_update", params);
		}
	}	
	
	@Override
	public PollResponse tqevent_select_test(String user_id){
		return (PollResponse) getSqlMapClientTemplate().queryForObject("Poll.tqevent_select_test", user_id);
	}	
	
	@Override
	public PollResponse tqevent_cnt_test(String user_id){
		return (PollResponse) getSqlMapClientTemplate().queryForObject("Poll.tqevent_cnt_test", user_id);
	}	
	
	@Override
	public void tqevent_insert_test(Map<String, Object> params){
		String user_id = (String) params.get("user_id");
		PollResponse tqevent_cnt    = tqevent_cnt_test(user_id);
		
		int cnt = Integer.parseInt(tqevent_cnt.getCnt()); 

		if(cnt == 0) {
			getSqlMapClientTemplate().insert("Poll.tqevent_insert_test", params); 
		}
		else if(cnt >= 1) {
			getSqlMapClientTemplate().insert("Poll.tqevent_update_test", params);
		}
	}
	
	@Override
	public PollResponse cnt_tqquizevent(String user_id){
		return (PollResponse) getSqlMapClientTemplate().queryForObject("Poll.cnt_tqquizevent", user_id);
	}
	
	@Override
	public void tqquizevent_insert(Map<String, Object> params){
		getSqlMapClientTemplate().insert("Poll.tqquizevent_insert", params); 
	}
	
	@Override
	public PollResponse chk_teacher(String user_id){
		return (PollResponse) getSqlMapClientTemplate().queryForObject("Poll.chk_teacher", user_id);
	}
	
	@Override
	public PollResponse cnt_tq_test(String user_id){
		return (PollResponse) getSqlMapClientTemplate().queryForObject("Poll.cnt_tq_test", user_id);
	}	
	
	@Override
	public void tq_test_insert(Map<String, Object> params){
		getSqlMapClientTemplate().insert("Poll.tq_test_insert", params); 
	}
	
	@Override
	public PollResponse cnt_tq_test_190529(Map<String,Object> params){
		return (PollResponse) getSqlMapClientTemplate().queryForObject("Poll.cnt_tq_test_190529", params);
	}
	
	@Override
	public void tq_test_insert_190529(Map<String, Object> params){
		getSqlMapClientTemplate().insert("Poll.tq_test_insert_190529", params); 
	}	
	
	@Override
	public PollResponse chk_500(){
		return (PollResponse) getSqlMapClientTemplate().queryForObject("Poll.chk_500");
	}	
	
	@Override
	public PollResponse usergap(Map<String,Object> params){
		return (PollResponse) getSqlMapClientTemplate().queryForObject("Poll.usergap", params);
	}
	
	@Override
	public void tq_test_insert_190619(Map<String, Object> params){
		getSqlMapClientTemplate().insert("Poll.tq_test_insert_190619", params); 
	}		
}