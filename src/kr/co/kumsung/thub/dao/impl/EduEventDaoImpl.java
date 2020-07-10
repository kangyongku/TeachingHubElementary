package kr.co.kumsung.thub.dao.impl;

import java.util.List;
import java.util.Map;

import kr.co.kumsung.thub.dao.EduEventDao;
import kr.co.kumsung.thub.domain.EduEvent;
import kr.co.kumsung.thub.util.ResultMap;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class EduEventDaoImpl extends SqlMapClientDaoSupport implements EduEventDao{

	/**
	 * 총 이벤트 신청자 반환한다.
	 * @return
	 */
	@Override
	public int getTotalPerson(Map<String, Object> params) {
		return (Integer) getSqlMapClientTemplate().queryForObject("EduEvent.getTotalPerson", params);
	}
	
	/**
	 * 마감일자 반환한다.
	 * @return
	 */
	@Override
	public int getEnddate(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * 이벤트 신청 등록 한다.
	 * @return
	 */
	@Override
	public int insertEduevent(Map<String, Object> params) {
		return (Integer) getSqlMapClientTemplate().insert(
				"EduEvent.insertEduevent", params);
	}
	
}
