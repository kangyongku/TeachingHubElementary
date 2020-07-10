package kr.co.kumsung.thub.service;

import java.util.Map;

public interface EduEventService {

	/**
	 * 총 이벤트 신청자를 반환한다.
	 * @return
	 */
	public int getTotalPerson(Map<String,Object> params);
	
	/**
	 * 마감일 반환한다.
	 * @param params
	 * @return
	 */
	public int getEnddate(Map<String,Object> params);
	
	/**
	 * 이벤트 등록.
	 * @param params
	 * @return
	 */
	public int insertEduevent(Map<String,Object> params);

	
}
