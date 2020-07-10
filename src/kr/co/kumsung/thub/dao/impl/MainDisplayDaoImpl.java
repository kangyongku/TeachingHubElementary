package kr.co.kumsung.thub.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.co.kumsung.thub.dao.MainDisplayDao;
import kr.co.kumsung.thub.domain.MainData;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;


public class MainDisplayDaoImpl extends SqlMapClientDaoSupport implements MainDisplayDao{

	@SuppressWarnings("unchecked")
	@Override
	public List<MainData> getDispList(Map<String, Object> params){
		return (List<MainData>) getSqlMapClientTemplate().queryForList("MainDisplay.getDispList", params);
	}
	
	@Override	
	public MainData getDispDetail(int dispId){
		return (MainData) getSqlMapClientTemplate().queryForObject("MainDisplay.getDispDetail" , dispId);
	}
	
	@Override
	public MainData getDispConf(String dispCd) {
		return (MainData) getSqlMapClientTemplate().queryForObject("MainDisplay.getDispConf" , dispCd);
	}

	@Override
	public void insertDisplay(Map<String, Object> params) {
		getSqlMapClientTemplate().insert("MainDisplay.insertDisplay" , params);
	}

	@Override
	public void deleteDisplay(int dispId) {
		getSqlMapClientTemplate().delete("MainDisplay.deleteDisplay" , dispId);
	}
	
	@Override
	public void updateDisplay(Map<String, Object> params) {
		getSqlMapClientTemplate().update("MainDisplay.updateDisplay" , params);
	}
	
	@Override
	public void updateDisplayRanking(Map<String, Object> params) {
		getSqlMapClientTemplate().update("MainDisplay.updateDisplayRanking" , params);
	}
	
	
	@Override
	public void initDisplay(int dispId) {
		getSqlMapClientTemplate().update("MainDisplay.initDisplay" , dispId);
	}
	
	@Override
	public MainData initChkDisplay(int dispId) {
		return (MainData) getSqlMapClientTemplate().queryForObject("MainDisplay.initChkDisplay" , dispId);
	}

	@Override
	public MainData initChkMaxDisplay(String type) {
		return (MainData) getSqlMapClientTemplate().queryForObject("MainDisplay.initChkMaxDisplay" , type);
	}

	@Override
	public MainData initStepUpDisplay(Map<String, Object> params) {
		return (MainData) getSqlMapClientTemplate().queryForObject("MainDisplay.initStepUpDisplay" , params);
	}
	
	@Override
	public void initStepUpdate(Map<String, Object> params) {
		getSqlMapClientTemplate().update("MainDisplay.initStepUpdate" , params);
	}
	
	@Override
	public void initStep3update(Map<String, Object> params) {
		getSqlMapClientTemplate().update("MainDisplay.initStep3update" , params);
	}
	
	@Override
	public MainData initStepDwDisplay(Map<String, Object> params) {
		return (MainData) getSqlMapClientTemplate().queryForObject("MainDisplay.initStepDwDisplay" , params);
	}

	@Override
	public void init2Stepupdate(Map<String, Object> params) {
		// TODO Auto-generated method stub
		getSqlMapClientTemplate().update("MainDisplay.init2Stepupdate" , params);
	}

	
}
