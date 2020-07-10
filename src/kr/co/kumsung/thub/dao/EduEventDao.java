package kr.co.kumsung.thub.dao;

import java.util.List;
import java.util.Map;

import kr.co.kumsung.thub.util.ResultMap;

public interface EduEventDao {

	/**
	 * 데이타의 총 갯수
	 * @param params
	 * @return
	 */
	public int getTotalPerson(Map<String,Object> params);
	
	/**
	 * 마감일 체크
	 * @param params
	 * @return
	 */

	public int getEnddate(Map<String, Object> params);

	
	/**
	 * 이벤트 등록
	 * @param params
	 * @return
	 */
	public int insertEduevent(Map<String, Object> params);
	
}
