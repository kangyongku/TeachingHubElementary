package kr.co.kumsung.thub.dao.impl;

import java.util.List;
import java.util.Map;

import kr.co.kumsung.thub.dao.StatsDao;
import kr.co.kumsung.thub.util.ResultMap;


import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class StatsDaoImpl extends SqlMapClientDaoSupport implements StatsDao{

	@Override
	public int getTotalMemberStat() {
		return (Integer) getSqlMapClientTemplate().queryForObject("Stats.getTotalMemberStat") ;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ResultMap> getMemberStat(Map<String, Object> params) {
		return (List<ResultMap>) getSqlMapClientTemplate().queryForList("Stats.getMemberStat", params);
	}

	@Override
	public int getTotalMemberSeceder() {
		return (Integer) getSqlMapClientTemplate().queryForObject("Stats.getTotalMemberSeceder") ;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ResultMap> getMemberSeceder(Map<String, Object> params) {
		return (List<ResultMap>) getSqlMapClientTemplate().queryForList("Stats.getMemberSeceder", params);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ResultMap> getHubContentStat(Map<String, Object> params) {
		return (List<ResultMap>) getSqlMapClientTemplate().queryForList("Stats.getHubContentStat", params);
	}

	@Override
	public int getTotalHubArticleStat() {
		return (Integer) getSqlMapClientTemplate().queryForObject("Stats.getTotalHubArticleStat");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ResultMap> getHubArticleStat(Map<String, Object> params) {
		return (List<ResultMap>) getSqlMapClientTemplate().queryForList("Stats.getHubArticleStat" , params);
	}

	@Override
	public int getTotalHubScrapStat() {
		return (Integer) getSqlMapClientTemplate().queryForObject("Stats.getTotalHubScrapStat");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ResultMap> getHubScrapStat(Map<String, Object> params) {
		return (List<ResultMap>) getSqlMapClientTemplate().queryForList("Stats.getHubScrapStat" , params);
	}

	@Override
	public int getTotalSmartStat() {
		return (Integer) getSqlMapClientTemplate().queryForObject("Stats.getTotalSmartStat");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ResultMap> getSmartGradeStat(Map<String, Object> params) {
		return (List<ResultMap>) getSqlMapClientTemplate().queryForList("Stats.getSmartGradeStat", params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ResultMap> getSmartSubjectStat(Map<String, Object> params) {
		return (List<ResultMap>) getSqlMapClientTemplate().queryForList("Stats.getSmartSubjectStat", params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ResultMap> getSmartBookStat(Map<String, Object> params) {
		return (List<ResultMap>) getSqlMapClientTemplate().queryForList("Stats.getSmartBookStat", params);
	}

	@Override
	public int getTotalCsRequestList(Map<String, Object> params) {
		return (Integer) getSqlMapClientTemplate().queryForObject("Stats.getTotalCsRequestList", params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ResultMap> getCsRequestList(Map<String, Object> params) {
		return (List<ResultMap>) getSqlMapClientTemplate().queryForList("Stats.getCsRequestList", params);
	}

	@Override
	public int getTotalCsSubjectList(Map<String, Object> params) {
		return (Integer) getSqlMapClientTemplate().queryForObject("Stats.getTotalCsSubjectList", params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ResultMap> getCsSubjectList(Map<String, Object> params) {
		return (List<ResultMap>) getSqlMapClientTemplate().queryForList("Stats.getCsSubjectList", params);
	}

	@Override
	public int getTotalCsContentsList(Map<String, Object> params) {
		return (Integer) getSqlMapClientTemplate().queryForObject("Stats.getTotalCsContentsList", params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ResultMap> getCsContentsList(Map<String, Object> params) {
		return (List<ResultMap>) getSqlMapClientTemplate().queryForList("Stats.getCsContentsList", params);
	}

	@Override
	public int getTotalSettingList(Map<String, Object> params) {
		return (Integer) getSqlMapClientTemplate().queryForObject("Stats.getTotalSettingList", params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ResultMap> getGradeSettingList(Map<String, Object> params) {
		return (List<ResultMap>) getSqlMapClientTemplate().queryForList("Stats.getGradeSettingList", params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ResultMap> getSubjectSettingList(Map<String, Object> params) {
		return (List<ResultMap>) getSqlMapClientTemplate().queryForList("Stats.getSubjectSettingList", params);
	}

	@Override
	public int getTotalBookSettingList(Map<String, Object> params) {
		return (Integer) getSqlMapClientTemplate().queryForObject("Stats.getTotalBookSettingList", params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ResultMap> getBookSettingList(Map<String, Object> params) {
		return (List<ResultMap>) getSqlMapClientTemplate().queryForList("Stats.getBookSettingList", params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ResultMap> getSearchWordList(Map<String, Object> params) {
		return (List<ResultMap>) getSqlMapClientTemplate().queryForList("Stats.getSearchWordList", params);
	}

	@Override
	public int getTotalHubContentStat() {
		// TODO Auto-generated method stub
		return 0;
	} 
	
	@Override
	public int getTotalsdfdown() {
		return (Integer) getSqlMapClientTemplate().queryForObject("Stats.getTotalsdfdown");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ResultMap> getSearchDownList(Map<String, Object> params) {
		return (List<ResultMap>) getSqlMapClientTemplate().queryForList("Stats.getSearchDownList", params);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<ResultMap> getSearchDownList1(Map<String, Object> params) {
		return (List<ResultMap>) getSqlMapClientTemplate().queryForList("Stats.getSearchDownList1", params);
	}
	
}
