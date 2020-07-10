package kr.co.kumsung.thub.dao.impl;


import java.util.List;
import java.util.Map;

import kr.co.kumsung.thub.dao.PopupDao;
import kr.co.kumsung.thub.domain.Config;
import kr.co.kumsung.thub.domain.Popup;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class PopupDaoImpl extends SqlMapClientDaoSupport implements PopupDao{
	
	public List<Popup> getPopupList(Map<String, Object> params){
		return (List<Popup>) getSqlMapClientTemplate().queryForList("Popup.getPopupList", params);
	}
	
	public int getPopupTotalList(Map<String, Object> params){
		return (Integer) getSqlMapClientTemplate().queryForObject("Popup.getPopupTotalList", params);
	}
	
	@Override
	public Popup getPopupDetail(int popupId){
		return (Popup) getSqlMapClientTemplate().queryForObject("Popup.getPopupDetail", popupId);
	}
	
	@Override
	public void insertPopup(Map<String, Object> params){
		getSqlMapClientTemplate().insert("Popup.insertPopup", params);
	}
	
	@Override
	public void updatePopup(Map<String, Object> params){
		getSqlMapClientTemplate().update("Popup.updatePopup", params);
	}
	
	@Override
	public void deletePopup(int popupId){
		getSqlMapClientTemplate().delete("Popup.deletePopup", popupId);
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Config> zipList(Config param){
		return (List<Config>) getSqlMapClientTemplate().queryForList("zip.zipList", param);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public int zipListCount(Config param){
		return (Integer)getSqlMapClientTemplate().queryForObject("zip.zipCount", param);
	}
}
