package kr.co.kumsung.thub.dao.impl;

import java.util.List;
import java.util.Map;

import kr.co.kumsung.thub.dao.CurriculumContactDao;
import kr.co.kumsung.thub.domain.CurriculumContact;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class CurriculumContactDaoImpl extends SqlMapClientDaoSupport implements CurriculumContactDao{
	
	public List<CurriculumContact> getCurriculumContactList(Map<String, Object> params){
		return (List<CurriculumContact>) getSqlMapClientTemplate().queryForList("CurriculumContact.getCurriculumContactList", params);
	}
	
	public int getCurriculumContactTotalList(Map<String, Object> params){
		return (Integer) getSqlMapClientTemplate().queryForObject("CurriculumContact.getCurriculumContactTotalList", params);
	}
	
	@Override
	public CurriculumContact getCurriculumContactDetailFromCategory(String findCategory) {
		return (CurriculumContact) getSqlMapClientTemplate().queryForObject("CurriculumContact.getCurriculumContactDetailFromCategory", findCategory);
	}
	
	@Override
	public CurriculumContact getCurriculumContactDetail(int curriculumContactId){
		return (CurriculumContact) getSqlMapClientTemplate().queryForObject("CurriculumContact.getCurriculumContactDetail", curriculumContactId);
	}
	
	@Override
	public void insertCurriculumContact(Map<String, Object> params){
		getSqlMapClientTemplate().insert("CurriculumContact.insertCurriculumContact", params);
	}
	
	@Override
	public void updateCurriculumContact(Map<String, Object> params){
		getSqlMapClientTemplate().update("CurriculumContact.updateCurriculumContact", params);
	}
	
	@Override
	public void deleteCurriculumContact(int curriculumContactId){
		getSqlMapClientTemplate().delete("CurriculumContact.deleteCurriculumContact", curriculumContactId);
	}
	
	@Override
	public int getCurriculumContactDup(Map<String, Object> params){
		return (Integer)getSqlMapClientTemplate().queryForObject("CurriculumContact.getCurriculumContactDup", params);
	}
	
}