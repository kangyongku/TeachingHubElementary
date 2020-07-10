package kr.co.kumsung.thub.dao.impl;

import java.util.List;
import java.util.Map;

import kr.co.kumsung.thub.dao.MultimediaDao;
import kr.co.kumsung.thub.domain.Multimedia;

//import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

//import com.ibatis.sqlmap.engine.mapping.sql.Sql;

public class MultimediaDaoImpl extends SqlMapClientDaoSupport implements MultimediaDao{
	
	@Override
	public Multimedia getMultimediaDetail(int mmId) {
		return (Multimedia) getSqlMapClientTemplate().queryForObject("Multimedia.getMultimediaDetail" , mmId);
	}
	
	@Override
	public int insertMultimedia(Map<String, Object> params){
		return (Integer) getSqlMapClientTemplate().insert("Multimedia.insertMultimedia", params);
	}
	
	@Override
	public List<Multimedia> getMultimediaList(Map<String, Object> params){
		return (List<Multimedia>) getSqlMapClientTemplate().queryForList("Multimedia.getMultimediaList", params);
	}
	
	@Override
	public int getTotalMultimedia(Map<String, Object> params){
		return (Integer) getSqlMapClientTemplate().queryForObject("Multimedia.getTotalMultimedia", params);
	}
	
	@Override
	public void updateMultimedia(Map<String, Object> params){
		getSqlMapClientTemplate().update("Multimedia.updateMultimedia", params);
	}
	
	@Override
	public String getDeleteTarget(Map<String, Object> params){
		return (String) getSqlMapClientTemplate().queryForObject("Multimedia.getDeleteTarget", params);
	}
	
	@Override
	public void multimediaDelete(int param){
		getSqlMapClientTemplate().delete("Multimedia.multimediaDelete", param);
	}

	@Override
	public Multimedia getPrevMultimedia(Map<String, Object> params) {
		return (Multimedia) getSqlMapClientTemplate().queryForObject("Multimedia.getPrevMultimedia", params);
	}

	@Override
	public Multimedia getNextMultimedia(Map<String, Object> params) {
		return (Multimedia) getSqlMapClientTemplate().queryForObject("Multimedia.getNextMultimedia", params);
	}
	
	@Override
	public int getDataRefCount(int param){
		return (Integer) getSqlMapClientTemplate().queryForObject("Multimedia.getDataRefCount", param);
	}
	
}
