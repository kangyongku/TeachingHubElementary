package kr.co.kumsung.thub.dao.impl;

import java.util.List;
import java.util.Map;

import kr.co.kumsung.thub.dao.FileDao;
import kr.co.kumsung.thub.domain.FileInfo;


import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class FileDaoImpl extends SqlMapClientDaoSupport implements FileDao{

	@SuppressWarnings("unchecked")
	@Override
	public List<FileInfo> getBoardFiles(String attachment) {
		return (List<FileInfo>) getSqlMapClientTemplate().queryForList("File.getBoardFiles", attachment);
	}
	
	@Override
	public FileInfo getFile(int fileId) {
		return (FileInfo) getSqlMapClientTemplate().queryForObject("File.getFile" , fileId);
	}
	
	@Override
	public int insert(Map<String, Object> params) {
		return (Integer) getSqlMapClientTemplate().insert("File.insert" , params);
	}

	@Override
	public void delete(int fileId) {
		getSqlMapClientTemplate().delete("File.delete" , fileId);
	}
	
}
