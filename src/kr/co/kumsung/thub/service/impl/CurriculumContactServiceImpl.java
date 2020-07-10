package kr.co.kumsung.thub.service.impl;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import kr.co.kumsung.thub.dao.CurriculumContactDao;
import kr.co.kumsung.thub.domain.Event;
import kr.co.kumsung.thub.domain.CurriculumContact;
import kr.co.kumsung.thub.service.CurriculumContactService;

public class CurriculumContactServiceImpl implements CurriculumContactService{

	@Autowired
	private CurriculumContactDao curriculumContactDao;
	
	@Override
	public List<CurriculumContact> getCurriculumContactList(Map<String, Object> params){
		return (List<CurriculumContact>) curriculumContactDao.getCurriculumContactList(params);
	}
	
	@Override
	public int getCurriculumContactTotalList(Map<String, Object> params){
		return (Integer) curriculumContactDao.getCurriculumContactTotalList(params);
	}
	
	@Override
	public CurriculumContact getCurriculumContactDetailFromCategory(String findCategory) {
		return (CurriculumContact)curriculumContactDao.getCurriculumContactDetailFromCategory(findCategory);
	}
	
	@Override
	public CurriculumContact getCurriculumContactDetail(int curriculumContactId){
		return (CurriculumContact)curriculumContactDao.getCurriculumContactDetail(curriculumContactId);
	}
	
	@Override
	public void insertCurriculumContact(Map<String, Object> params){
		curriculumContactDao.insertCurriculumContact(params);
	}
	
	@Override
	public void updateCurriculumContact(Map<String, Object> params){
		curriculumContactDao.updateCurriculumContact(params);
	}
	
	@Override
	public void deleteCurriculumContact(int curriculumContactId){
		curriculumContactDao.deleteCurriculumContact(curriculumContactId);
	}
	
	@Override
	public int getCurriculumContactDup(Map<String, Object> params){
		return curriculumContactDao.getCurriculumContactDup(params);
	}
	
}
