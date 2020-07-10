package kr.co.kumsung.thub.dao.impl;

import java.util.List;
import java.util.Map;

import kr.co.kumsung.thub.dao.CategoryDao;
import kr.co.kumsung.thub.domain.Category;


import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class CategoryDaoImpl extends SqlMapClientDaoSupport implements CategoryDao{

	@Override
	public Category getCategory(String category){
		return (Category) getSqlMapClientTemplate().queryForObject("Category.getCategory", category);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Category> getChildren(Map<String, Object> params) {
		return (List<Category>) getSqlMapClientTemplate().queryForList("Category.getChildren" , params);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Category> getChildrenAll(Map<String, Object> params) {
		return (List<Category>) getSqlMapClientTemplate().queryForList("Category.getChildrenAll" , params);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Category> getChildrenA001(Map<String, Object> params) {
		return (List<Category>) getSqlMapClientTemplate().queryForList("Category.getChildrenA001" , params);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Category> getChildrenWithDataCount(Map<String, Object> params) {
		return (List<Category>) getSqlMapClientTemplate().queryForList("Category.getChildrenWithDataCount", params);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Category> getList(Map<String, Object> params) {
		return (List<Category>) getSqlMapClientTemplate().queryForList("Category.getList" , params);
	}
	
	@Override
	public String getMaxCategory(Map<String, Object> params) {
		return (String) getSqlMapClientTemplate().queryForObject("Category.getMaxCategory" , params);
	}

	@Override
	public void insert(Map<String, Object> params) {
		getSqlMapClientTemplate().insert("Category.insert" , params);
	}

	@Override
	public void update(Map<String, Object> params) {
		getSqlMapClientTemplate().update("Category.update" , params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Category> getSecondaryAllList(String category) {
		return (List<Category>) getSqlMapClientTemplate().queryForList("Category.getSecondaryAllList", category);
	}

	@Override
	public String getCategoryPath(String category){
		return (String) getSqlMapClientTemplate().queryForObject("Category.getCategoryPath", category); 
	}
	
	@Override
	public void updateSequence(Map<String,Object> params){
		getSqlMapClientTemplate().update("Category.updateSequence" , params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Category> getChildrenWithMultimediaCount(Map<String, Object> params) {
		return (List<Category>) getSqlMapClientTemplate().queryForList("Category.getChildrenWithMultimediaCount", params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Category> getChildrenWithHeadwordCount(
			Map<String, Object> params) {
		return (List<Category>) getSqlMapClientTemplate().queryForList("Category.getChildrenWithHeadwordCount", params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Category> getCategoryInCDDownload(String findCategory) {
		return (List<Category>) getSqlMapClientTemplate().queryForList("Category.getCategoryInCDDownload", findCategory);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Category> getChildrenWithLeaningCount(Map<String, Object> params) {
		return (List<Category>) getSqlMapClientTemplate().queryForList("Category.getChildrenWithLeaningCount", params);
	}
}
