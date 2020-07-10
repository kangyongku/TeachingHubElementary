package kr.co.kumsung.thub.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import kr.co.kumsung.thub.dao.UnitDao;
import kr.co.kumsung.thub.domain.Unit;
import kr.co.kumsung.thub.service.UnitService;


public class UnitServiceImpl implements UnitService{

	@Autowired
	private UnitDao unitDao;
	
	@Override
	public List<Unit> getUnitList(String category , int bookId){
		
		List<Unit> result     = new ArrayList<Unit>();
		List<Unit> firstUnit  = new ArrayList<Unit>();		// 대단원
		List<Unit> secondUnit = new ArrayList<Unit>();		// 중단원
		List<Unit> thirdUnit  = new ArrayList<Unit>();		// 소단원
		
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("category" , category);
		params.put("bookId" , bookId);
		
		List<Unit> list = unitDao.getUnitList(params);
		
		if( list.size() > 0 ){
			for(Unit unit : list){
				if( unit.getDepth() == 1 ){
					firstUnit.add(unit);
				}
				else if( unit.getDepth() == 2 ) {
					secondUnit.add(unit);
				}
				else if( unit.getDepth() == 3 ) {
					thirdUnit.add(unit);
				}
			}
			
			for(Unit fUnit : firstUnit) {
				int unitId = fUnit.getUnitId();
				List<Unit> sChildren = new ArrayList<Unit>();
				
				for(Unit sUnit : secondUnit ) {
					if( sUnit.getParentId() == unitId ) {	
						List<Unit> tChildren = new ArrayList<Unit>();
						for(Unit tUnit : thirdUnit) {
							if( tUnit.getParentId() == sUnit.getUnitId() )
								tChildren.add(tUnit);
						}
						sUnit.setChildren(tChildren);
						sChildren.add(sUnit);
					}
				}
				fUnit.setChildren(sChildren);
				result.add(fUnit);
			}
		}
		return result;
	}
	
	@Override
	public List<Unit> getUnitDepthList(String category, Map<String, Object> params) {
		params.put("category" , category);
		return unitDao.getUnitDepthList(params);
	}
	
	@Override
	public Unit getUnit(String category , int unitId) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("category" , category);
		params.put("unitId" , unitId);
		return unitDao.getUnit(params);
	}
	
	@Override
	public int getMaxSequence(String category , Map<String, Object> params) {
		params.put("category" , category);
		return unitDao.getMaxSequence(params);
	}
	
	@Override
	public int insertUnit(String category , Map<String, Object> params) {
		int depth = (Integer) params.get("depth");
		int bookId = (Integer) params.get("bookId");
		int groupId = 0;
		
		params.put("category" , category);
		
		if( depth == 1 ) {
			groupId = unitDao.getMaxGroupId(params);
		}
		else if( depth >= 2 ) {
			groupId = unitDao.getUnitGroupId(params);
		}
			
		params.put("groupId" , groupId);
		
		String groupCode = unitDao.getUnitGroupCode(params);
		
		return unitDao.insertUnit(params);
	}
	
	@Override
	public void updateUnit(String category , Map<String, Object> params) {
		params.put("category" , category);
		unitDao.updateUnit(params);
	}

	@Override
	public boolean deleteUnit(String category , int unitId) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("category" , category);
		params.put("unitId" , unitId);
		int children = unitDao.getChildrenCount(params);
		
		if( children > 0 ){
			return false;
		}
		
		unitDao.deleteUnit(params);
		
		return true;
	}

	@Override
	public boolean updateUnitSequence(String category , String sequences) {
		String[] items = sequences.split("\\^");
		if( items.length > 0 ){
			int sequence = 1;
			for(String item : items) {
				String[] t = item.split("\\,");
				int unitId = Integer.valueOf(t[0]);
				
				Map<String,Object> params = new HashMap<String,Object>();
				
				params.put("category" , category);
				params.put("unitId" , unitId);
				params.put("sequence" , sequence);
				
				unitDao.updateUnitSequence(params);
				
				sequence++;
			}
		}
		return true;
	}

	@Override
	public List<Unit> getUnitChildren(String category, Map<String, Object> params) {
		params.put("category" , category);
		return unitDao.getUnitChildren(params);
	}

	@Override
	public List<Unit> getMasterUnitList(String category , Map<String, Object> params) {
		params.put("category" , category);
		return unitDao.getMasterUnitList(params);
	}
	
	@Override
	public List<Unit> getMasterUnitListAll(String category , Map<String, Object> params) {
		params.put("category" , category);
		return unitDao.getMasterUnitListAll(params);
	}

	@Override
	public List<Unit> getChildrenUnitList(String category, Map<String, Object> params) {
		params.put("category" , category);
		return unitDao.getChildrenUnitList(params);
	}
}