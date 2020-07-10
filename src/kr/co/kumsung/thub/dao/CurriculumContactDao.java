package kr.co.kumsung.thub.dao;

import java.util.List;
import java.util.Map;

import kr.co.kumsung.thub.domain.CurriculumContact;

public interface CurriculumContactDao {
	/**
	 * 교과정보관리 리스트
	 * @param params
	 * @return List<CurriculumContact>
	 */
	public List<CurriculumContact> getCurriculumContactList(Map<String, Object> params);

	/**
	 * 교과정보관리 리스트 개수
	 * @param params
	 * @return int
	 */
	public int getCurriculumContactTotalList(Map<String, Object> params);

	/**
	 * 교과정보관리 상세보기
	 * @param curriculumContactId
	 * @return CurriculumContact
	 */
	public CurriculumContact getCurriculumContactDetailFromCategory(String findCategory);
	
	/**
	 * 교과정보관리 상세보기
	 * @param curriculumContactId
	 * @return CurriculumContact
	 */
	public CurriculumContact getCurriculumContactDetail(int curriculumContactId);
	
	/**
	 * 교과정보관리 입력
	 * @param params
	 * @return 
	 */
	public void insertCurriculumContact(Map<String, Object> params);
	
	/**
	 * 교과정보관리 수정
	 * @param params
	 * @return 
	 */
	public void updateCurriculumContact(Map<String, Object> params);
	
	/**
	 * 교과정보관리 삭제
	 * @param curriculumContactId
	 * @return
	 */
	public void deleteCurriculumContact(int curriculumContactId);
	
	/**
	 * 교과정보관리 카테고리 중복체크
	 * @param params
	 * @return int
	 */
	public int getCurriculumContactDup(Map<String, Object> params);
}
