package kr.co.kumsung.thub.service;

import java.util.List;
import java.util.Map;

import kr.co.kumsung.thub.domain.Unit;

/**
 * 단원관리 Service Layer
 * @author mikelim
 *
 */
public interface UnitService {
	
	/**
	 * 단원의 목록을 가지고 온다.
	 * @param bookId
	 * @return
	 */
	public List<Unit> getUnitList(String category , int bookId);
	
	/**
	 * 주어진 Depth의 단원 목록을 가지고 온다.
	 * @param bookId
	 * @return
	 */
	public List<Unit> getUnitDepthList(String category , Map<String,Object> params);
	
	/**
	 * 단원의 정보를 가지고 온다.
	 * @param unitId
	 * @return
	 */
	public Unit getUnit(String category , int unitId);
	
	/**
	 * 단원의 가장큰 정렬 순서를 가지고온다.
	 * @param params
	 * @return
	 */
	public int getMaxSequence(String category , Map<String,Object> params);
	
	/**
	 * 단원을 입력한다.
	 * @param params
	 * @return
	 */
	public int insertUnit(String category , Map<String,Object> params);
	
	/**
	 * 단원의 정보를 갱신한다.
	 * @param params
	 */
	public void updateUnit(String category , Map<String,Object> params);
	
	/**
	 * 단원을 삭제한다.
	 * @param unitId
	 */
	public boolean deleteUnit(String category , int unitId);
	
	/**
	 * 단원의 순서를 재정렬한다.
	 * @param sequences
	 */
	public boolean updateUnitSequence(String category , String sequences);
	
	/**
	 * 단원의 자식노드를 구한다.
	 * @param category
	 * @param params
	 * @return
	 */
	public List<Unit> getUnitChildren(String category , Map<String,Object> params);
	
	/**
	 * 도서의 대단원 리스트를 가지고 온다.
	 * @param params
	 * @return
	 */
	public List<Unit> getMasterUnitList(String category , Map<String, Object> params);
	
	/**
	 * 도서의 대단원 리스트를 가지고 온다.
	 * @param params
	 * @return
	 */
	public List<Unit> getMasterUnitListAll(String category , Map<String, Object> params);
	
	/**
	 * 해당 단원의 하위 단원을 가지고 온다.
	 * @param params
	 * @return
	 */
	public List<Unit> getChildrenUnitList(String category , Map<String,Object> params);
}
