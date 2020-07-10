package kr.co.kumsung.thub.service;

import java.util.Map;

public interface QuizEventService {

	/**
	 * 이벤트 신청자 등록 여부를 반환
	 * @param params
	 * @return
	 */
	public int getRegCnt(Map<String,Object> params);
	
	/**
	 * 마감일 반환
	 * @param params
	 * @return
	 */
	public String getEnddate(Map<String,Object> params);
	
	/**
	 * 이벤트 등록
	 * @param params
	 * @return
	 */
	public void insertQuizEvent(Map<String,Object> params);

}