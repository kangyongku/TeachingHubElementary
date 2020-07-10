package kr.co.kumsung.thub.dao.impl;

import java.util.List;
import java.util.Map;

import kr.co.kumsung.thub.dao.UnitDao;
import kr.co.kumsung.thub.domain.Unit;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class UnitDaoImpl extends SqlMapClientDaoSupport implements UnitDao{

	@SuppressWarnings("unchecked")
	@Override
	public List<Unit> getUnitList(Map<String,Object> params) {
		return (List<Unit>) getSqlMapClientTemplate().queryForList("Unit.getUnitList", params);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Unit> getUnitDepthList(Map<String, Object> params) {
		return (List<Unit>) getSqlMapClientTemplate().queryForList("Unit.getUnitDepthList" , params);
	}
	
	@Override
	public Unit getUnit(Map<String,Object> params) {
		return (Unit) getSqlMapClientTemplate().queryForObject("Unit.getUnit" , params);
	}
	
	@Override
	public int getUnitGroupId(Map<String, Object> params) {
		return (Integer) getSqlMapClientTemplate().queryForObject("Unit.getUnitGroupId", params);
	}
	
	@Override
	public int getMaxSequence(Map<String, Object> params) {
		return (Integer) getSqlMapClientTemplate().queryForObject("Unit.getMaxSequence" , params);
	}

	@Override
	public int getMaxGroupId(Map<String,Object> params) {
		return (Integer) getSqlMapClientTemplate().queryForObject("Unit.getMaxGroupId", params);
	}
	
	@Override
	public int insertUnit(Map<String, Object> params) {
		return (Integer) getSqlMapClientTemplate().insert("Unit.insertUnit" , params);
	}

	@Override
	public void updateUnit(Map<String, Object> params) {
		getSqlMapClientTemplate().update("Unit.updateUnit" , params);
	}

	@Override
	public void deleteUnit(Map<String,Object> params) {
		getSqlMapClientTemplate().delete("Unit.deleteUnit" , params);
	}

	@Override
	public int getChildrenCount(Map<String,Object> params) {
		return (Integer) getSqlMapClientTemplate().queryForObject("Unit.getChildrenCount", params);
	}
	
	@Override
	public void updateUnitSequence(Map<String, Object> params) {
		getSqlMapClientTemplate().update("Unit.updateUnitSequence" , params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Unit> getUnitChildren(Map<String, Object> params) {
		return (List<Unit>) getSqlMapClientTemplate().queryForList("Unit.getUnitChildren" , params);
	}

	@Override
	public String getUnitGroupCode(Map<String, Object> params) {
		return (String) getSqlMapClientTemplate().queryForObject("Unit.getUnitGroupCode", params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Unit> getMasterUnitList(Map<String, Object> params) {
		return (List<Unit>) getSqlMapClientTemplate().queryForList("Unit.getMasterUnitList" , params);		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Unit> getMasterUnitListAll(Map<String, Object> params) {
		return (List<Unit>) getSqlMapClientTemplate().queryForList("Unit.getMasterUnitListAll" , params);		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Unit> getChildrenUnitList(Map<String, Object> params) {
		return (List<Unit>) getSqlMapClientTemplate().queryForList("Unit.getChildrenUnitList", params);
	}

	/*
	@SuppressWarnings("unchecked")
	@Override
	public List<Unit> getMasterUnitListAll_re(Map<String, Object> params) {
		return (List<Unit>) getSqlMapClientTemplate().queryForList("Unit.getMasterUnitListAll_re" , params);		
	}
	
	@Override
	public Unit getUnit_re(Map<String,Object> params) {
		return (Unit) getSqlMapClientTemplate().queryForObject("Unit.getUnit_re" , params);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Unit> getChildrenUnitList_re(Map<String, Object> params) {
		return (List<Unit>) getSqlMapClientTemplate().queryForList("Unit.getChildrenUnitList_re", params);
	}	
	*/
}