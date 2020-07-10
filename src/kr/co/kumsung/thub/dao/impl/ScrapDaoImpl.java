package kr.co.kumsung.thub.dao.impl;

import java.util.List;
import java.util.Map;

import kr.co.kumsung.thub.dao.ScrapDao;
import kr.co.kumsung.thub.domain.Folder;
import kr.co.kumsung.thub.domain.Scrap;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class ScrapDaoImpl extends SqlMapClientDaoSupport implements ScrapDao{
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Folder> getFolderList(String userId) {
		return (List<Folder>) getSqlMapClientTemplate().queryForList("Scrap.getFolderList" , userId);
	}

	@Override
	public Folder getFolder(Map<String, Object> params) {
		return (Folder) getSqlMapClientTemplate().queryForObject("Scrap.getFolder", params);
	}

	@Override
	public int insertFolder(Map<String, Object> params) {
		return (Integer) getSqlMapClientTemplate().insert("Scrap.insertFolder" , params);
	}

	@Override
	public void updateFolder(Map<String, Object> params) {
		getSqlMapClientTemplate().update("Scrap.updateFolder" , params);
	}

	@Override
	public void deleteFolder(Map<String, Object> params) {
		getSqlMapClientTemplate().delete("Scrap.deleteFolder" , params);
	}
	
	@Override
	public int getScrapListCount(Map<String, Object> params) {
		return (Integer) getSqlMapClientTemplate().queryForObject("Scrap.getScrapListCount", params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Scrap> getScrapList(Map<String, Object> params) {
		return (List<Scrap>) getSqlMapClientTemplate().queryForList("Scrap.getScrapList", params);
	}

	@Override
	public int isDuplicated(Map<String, Object> params) {
		return (Integer) getSqlMapClientTemplate().queryForObject("Scrap.isDuplicated" , params);
	}
	
	@Override
	public int insertScrap(Map<String, Object> params) {
		return (Integer) getSqlMapClientTemplate().insert("Scrap.insertScrap", params);
	}

	@Override
	public void deleteScrap(Map<String, Object> params) {
		getSqlMapClientTemplate().delete("Scrap.deleteScrap" , params);
	}

	@Override
	public void moveScrapFolder(Map<String, Object> params) {
		getSqlMapClientTemplate().update("Scrap.moveScrapFolder" , params);
	}
	
}
