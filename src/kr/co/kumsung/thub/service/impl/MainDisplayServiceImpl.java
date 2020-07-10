package kr.co.kumsung.thub.service.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.kumsung.thub.dao.MainDisplayDao;
import kr.co.kumsung.thub.domain.MainData;
import kr.co.kumsung.thub.service.MainDisplayService;


public class MainDisplayServiceImpl implements MainDisplayService{

	@Autowired
	private MainDisplayDao maindisplayDao;
	
	@Override
	public List<MainData> getDispList(Map<String, Object> params){
		return (List<MainData>) maindisplayDao.getDispList(params);
	}
	
	@Override	
	public MainData getDispDetail(int dispId){
		return (MainData) maindisplayDao.getDispDetail(dispId);
	}
	
	@Override
	public MainData getDispConf(String dispCd){
		return (MainData)maindisplayDao.getDispConf(dispCd);
	}
	
	@Override
	public void insertDisplay(Map<String, Object> params){
		maindisplayDao.insertDisplay(params);
	}
	

	@Override
	public void deleteDisplay(int dispId){
		maindisplayDao.deleteDisplay(dispId);
	}
	
	@Override
	public void updateDisplay(Map<String, Object> params){
		maindisplayDao.updateDisplay(params);
	}
	
	@Override
	public void updateDisplayRanking(Map<String, Object> params){
		maindisplayDao.updateDisplayRanking(params);
	}
	
	@Override
	public void initDisplay(int dispId){
		maindisplayDao.initDisplay(dispId);
	}
	
	@Override
	public MainData initChkDisplay(int dispId){
		return (MainData)maindisplayDao.initChkDisplay(dispId);
	}
	
	@Override
	public MainData initChkMaxDisplay(String type){
		return (MainData)maindisplayDao.initChkMaxDisplay(type);
	}
	
	@Override
	public MainData initStepUpDisplay(Map<String, Object> params){
		return (MainData)maindisplayDao.initStepUpDisplay(params);
	}
	
	@Override
	public void initStepUpdate(Map<String, Object> params){
		maindisplayDao.initStepUpdate(params);
	}
	
	@Override
	public void init2Stepupdate(Map<String, Object> params){
		maindisplayDao.init2Stepupdate(params);
	}
	
	@Override
	public MainData initStepDwDisplay(Map<String, Object> params){
		return (MainData)maindisplayDao.initStepDwDisplay(params);
	}
	
	@Override
	public void initStep3Update(Map<String, Object> params){
		maindisplayDao.initStep3update(params);
	}
	
	@Override
	public List<MainData> getDispViewList(String dispCd){
		
		Map<String, Object> params =  new HashMap<String,Object>();
		MainData maindata = (MainData)maindisplayDao.getDispConf(dispCd);
		params.put("dispCd" , dispCd);
		params.put("dispSort" , maindata.getdispSort());
		params.put("dispLimit" , maindata.getdispLimit());
		
		return (List<MainData>) maindisplayDao.getDispList(params);
	}

}
