package kr.co.kumsung.thub.service.impl;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import kr.co.kumsung.thub.dao.PopupDao;
import kr.co.kumsung.thub.domain.Config;
import kr.co.kumsung.thub.domain.Popup;
import kr.co.kumsung.thub.service.PopupService;

public class PopupServiceImpl implements PopupService{

	@Autowired
	private PopupDao popupDao;
	
	@Override
	public List<Popup> getPopupList(Map<String, Object> params){
		return (List<Popup>) popupDao.getPopupList(params);
	}
	
	@Override
	public int getPopupTotalList(Map<String, Object> params){
		return (Integer) popupDao.getPopupTotalList(params);
	}
	
	@Override
	public Popup getPopupDetail(int popupId){
		return (Popup)popupDao.getPopupDetail(popupId);
	}
	
	@Override
	public void insertPopup(Map<String, Object> params){
		popupDao.insertPopup(params);
	}
	
	@Override
	public void updatePopup(Map<String, Object> params){
		popupDao.updatePopup(params);
	}
	
	@Override
	public void deletePopup(int popupId){
		popupDao.deletePopup(popupId);
	}
	
	@Override
	public List<Config> zipList(Config param){
		return (List<Config>) popupDao.zipList(param);
	}		
	
	@Override
	public int zipListCount(Config param){
		return (Integer) popupDao.zipListCount(param);
	}
}
