package kr.co.kumsung.thub.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import kr.co.kumsung.thub.dao.ScrapDao;
import kr.co.kumsung.thub.domain.Folder;
import kr.co.kumsung.thub.domain.Scrap;
import kr.co.kumsung.thub.service.ScrapService;

public class ScrapServiceImpl implements ScrapService{

	@Autowired
	private ScrapDao scrapDao;
	
	@Override
	public List<Folder> getFolderList(String userId) {
		return scrapDao.getFolderList(userId);
	}

	@Override
	public Folder getFolder(Map<String, Object> params) {
		return scrapDao.getFolder(params);
	}

	@Override
	public int insertFolder(Map<String, Object> params) {
		return scrapDao.insertFolder(params);
	}

	@Override
	public void updateFolder(Map<String, Object> params) {
		scrapDao.updateFolder(params);
	}

	@Override
	public void deleteFolder(Map<String, Object> params) {
		scrapDao.deleteFolder(params);
	}

	@Override
	public int getScrapListCount(Map<String, Object> params) {
		return (Integer) scrapDao.getScrapListCount(params);
	}

	@Override
	public List<Scrap> getScrapList(Map<String, Object> params) {
		return (List<Scrap>) scrapDao.getScrapList(params);
	}
	
	@Override
	public int isDuplicated(Map<String, Object> params) {
		return scrapDao.isDuplicated(params);
	}
	
	@Override
	public int insertScrap(Map<String, Object> params) {
		return scrapDao.insertScrap(params);
	}

	@Override
	public void deleteScrap(Map<String, Object> params) {
		scrapDao.deleteScrap(params);
	}

	@Override
	public void moveScrapFolder(Map<String, Object> params) {
		scrapDao.moveScrapFolder(params);
	}
	
}
