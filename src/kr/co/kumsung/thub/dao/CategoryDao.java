package kr.co.kumsung.thub.dao;

import java.util.List;
import java.util.Map;

import kr.co.kumsung.thub.domain.Category;

public interface CategoryDao {
	
	/**
	 * 카테고리 데이타를 가지고 온다.
	 * @param category
	 * @return
	 */
	public Category getCategory(String category); 
	
	/**
	 * 자식 노드를 가지고 온다.
	 * @param params
	 * @return
	 */
	public List<Category> getChildren(Map<String,Object> params);
	
	/**
	 * 미노출까지 자식 노드를 가지고 온다.
	 * @param params
	 * @return
	 */
	public List<Category> getChildrenAll(Map<String,Object> params);
	
	/**
	 * 카테고리를 가지고 온다.
	 * @param params
	 * @return
	 */
	public List<Category> getChildrenWithDataCount(Map<String,Object> params);
	
	/**
	 * 카테고리 리스트를 가지고 온다.
	 * @param params
	 * @return
	 */
	public List<Category> getList(Map<String,Object> params);
	
	/**
	 * 최고값을 가지는 카테고리를 가지고 온다.
	 * @param params
	 * @return
	 */
	public String getMaxCategory(Map<String,Object> params);
	
	/**
	 * 카테고리를 등록한다.
	 * @param params
	 */
	public void insert(Map<String,Object> params);
	
	/**
	 * 카테고리를 갱신한다.
	 * @param params
	 */
	public void update(Map<String,Object> params);
	
	/**
	 * 2차 카테고리까지의 전체 리스트를 가지고 온다.
	 * @param category
	 * @return
	 */
	public List<Category> getSecondaryAllList(String category);
	
	/**
	 * 카테고리 전체 경로를 가져온다
	 * @param String
	 * @return String
	 */
	public String getCategoryPath(String category);
	
	/**
	 * 정렬 순서를 변경한다.
	 * @param params
	 */
	public void updateSequence(Map<String,Object> params);
	
	/**
	 * 멀티미디어 카운트를 가지는 카테고리 리스트
	 * @param params
	 * @return
	 */
	public List<Category> getChildrenWithMultimediaCount(Map<String,Object> params);
	
	/**
	 * 표제어의 카운트를 가지는 카테고리 리스트를 가지고 온다.
	 */
	public List<Category> getChildrenWithHeadwordCount(Map<String,Object> params);
	
	/**
	 * CD 다운로드 데이타가 있는 카테고리만 가지고 온다.
	 * @param findCategory
	 * @return
	 */
	public List<Category> getCategoryInCDDownload(String findCategory);

	/**
	 * 교수학습자료 세부 카테고리 카운트
	 * @param params
	 * @return
	 */
	public List<Category> getChildrenWithLeaningCount(Map<String, Object> params);
	

	/**
	 * 초등만 제한적으로 조회
	 * @param params
	 * @return
	 */
	public List<Category> getChildrenA001(Map<String, Object> params);
}
