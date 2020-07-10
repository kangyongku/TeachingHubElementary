package kr.co.kumsung.thub.dao.impl;

import java.util.Map;
import kr.co.kumsung.thub.dao.QuizEventDao;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class QuizEventDaoImpl extends SqlMapClientDaoSupport implements QuizEventDao{

	/**
	 * 총 이벤트 신청자 반환
	 * @return
	 */
	@Override
	public int getRegCnt(Map<String, Object> params) {
		return (Integer) getSqlMapClientTemplate().queryForObject("QuizEvent.getRegCnt", params);
	}
	
	/**
	 * 마감일자 반환
	 * @return
	 */
	@Override
	public String getEnddate(Map<String, Object> params) {
		return (String) getSqlMapClientTemplate().queryForObject("QuizEvent.getEnddate", params);
	}

	/**
	 * 이벤트 신청 등록 한다.
	 * @return
	 */
	@Override
	public void insertQuizEvent(Map<String, Object> params){
		getSqlMapClientTemplate().insert("QuizEvent.insertQuizEvent", params);
	}
	
}