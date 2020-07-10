package kr.co.kumsung.thub.dao;

import java.util.List;
import java.util.Map;

import kr.co.kumsung.thub.domain.Unit;

/**
 * 단원관리 DAO
 * @author mikelim
 *
 */
public interface UnitDao {
	
	/**
	 * 단원의 리스트를 가지고 온다.
	 * @param bookId
	 * @return
	 */
	public List<Unit> getUnitList(Map<String,Object> params);
	
	/**
	 * 주어진 Depth의 단원의 리스트를 가지고 온다.
	 * @param bookId
	 * @return
	 */
	public List<Unit> getUnitDepthList(Map<String,Object> params);
	
	/**
	 * 단원의 정보를 가지고 온다.
	 * @param unitId
	 * @return
	 */
	public Unit getUnit(Map<String,Object> params);
	
	/**
	 * 대단원의 그룹 아이디를 가지고 온다.
	 * @param params
	 * @return
	 */
	public int getUnitGroupId(Map<String,Object> params);
	
	/**
	 * 단원의 가장 큰 정렬 순서를 가지고 온다.
	 * @param params
	 * @return
	 */
	public int getMaxSequence(Map<String,Object> params);
	
	/**
	 * 가장 큰 그룹 아이디를 가지고 온다.
	 * @param bookId
	 * @return
	 */
	public int getMaxGroupId(Map<String,Object> params);
	
	/**
	 * 단원을 입력한다.
	 * @param params
	 * @return
	 */
	public int insertUnit(Map<String,Object> params);
	
	/**
	 * 단원의 정보를 갱신한다.
	 * @param params
	 */
	public void updateUnit(Map<String,Object> params);
	
	/**
	 * 단원을 삭제한다.
	 * @param unitId
	 */
	public void deleteUnit(Map<String,Object> params);
	
	/**
	 * 단원의 하위 노드 갯수를 구한다.
	 * @param unitId
	 * @return
	 */
	public int getChildrenCount(Map<String,Object> params);
	
	/**
	 * 단원의 순서를 변경한다.
	 * @param params
	 */
	public void updateUnitSequence(Map<String,Object> params);
	
	/**
	 * 단원의 자식 노드를 구한다.
	 * @param params
	 * @return
	 */
	public List<Unit> getUnitChildren(Map<String,Object> params);
	
	/**
	 * 단원의 그룹 코드를 구한다.
	 * @param params
	 * @return
	 */
	public String getUnitGroupCode(Map<String,Object> params);
	
	/**
	 * 해당 도서의 대단원 데이타만 가지고 온다.
	 * @param params
	 * @return
	 */
	public List<Unit> getMasterUnitList(Map<String,Object> params);
	
	/**
	 * 해당 도서의 대단원 데이타만 가지고 온다.
	 * @param params
	 * @return
	 */
	public List<Unit> getMasterUnitListAll(Map<String,Object> params);
	
	/**
	 * 해당 단원의 하위 단원을 가지고 온다.
	 * @param params
	 * @return
	 */
	public List<Unit> getChildrenUnitList(Map<String,Object> params);
}