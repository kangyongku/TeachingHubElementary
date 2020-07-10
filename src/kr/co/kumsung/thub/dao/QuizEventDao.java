package kr.co.kumsung.thub.dao;

import java.util.Map;

public interface QuizEventDao {

	/**
	 * 이벤트 등록 여부
	 * @param params
	 * @return
	 */
	public int getRegCnt(Map<String,Object> params);
	
	/**
	 * 마감일 체크
	 * @param params
	 * @return
	 */
	public String getEnddate(Map<String, Object> params);

	/**
	 * 이벤트 등록
	 * @param params
	 * @return
	 */
	public void insertQuizEvent(Map<String, Object> params);

}