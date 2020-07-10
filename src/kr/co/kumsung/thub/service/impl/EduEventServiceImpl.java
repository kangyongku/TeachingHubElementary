package kr.co.kumsung.thub.service.impl;

import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;

import kr.co.kumsung.thub.dao.EduEventDao;
import kr.co.kumsung.thub.service.EduEventService;


public  class EduEventServiceImpl implements EduEventService {

	@Autowired
	private EduEventDao eduEventDao;

	@Override
	public int getTotalPerson(Map<String, Object> params) {
		return eduEventDao.getTotalPerson(params);
	}

	@Override
	public int getEnddate(Map<String, Object> params) {
		return eduEventDao.getEnddate(params);
	}

	@Override
	public int insertEduevent(Map<String, Object> params) {
		return eduEventDao.insertEduevent(params);
	}


	
}
