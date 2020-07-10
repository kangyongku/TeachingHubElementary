package kr.co.kumsung.thub.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;


import kr.co.kumsung.thub.dao.StatsDao;
import kr.co.kumsung.thub.service.StatsService;
import kr.co.kumsung.thub.util.ResultMap;


public class StatsServiceImpl implements StatsService{

	@Autowired
	private StatsDao statsDao;

	@Override
	public int getTotalMemberStat() {
		return statsDao.getTotalMemberStat();
	}

	@Override
	public List<ResultMap> getMemberStat(Map<String, Object> params) {
		return statsDao.getMemberStat(params);
	}
	
	@Override
	public int getTotalMemberSeceder() {
		return statsDao.getTotalMemberSeceder();
	}

	@Override
	public List<ResultMap> getMemberSeceder(Map<String, Object> params) {
		return statsDao.getMemberSeceder(params);
	}
	
	@Override
	public int getTotalHubContentStat() {
		return (Integer) statsDao.getTotalHubContentStat();
	}

	@Override
	public List<ResultMap> getHubContentStat(Map<String, Object> params) {
		return statsDao.getHubContentStat(params);
	}

	@Override
	public int getTotalHubArticleStat() {
		return statsDao.getTotalHubArticleStat();
	}

	@Override
	public List<ResultMap> getHubArticleStat(Map<String, Object> params) {
		return statsDao.getHubArticleStat(params);
	}

	@Override
	public int getTotalHubScrapStat() {
		return statsDao.getTotalHubScrapStat();
	}

	@Override
	public List<ResultMap> getHubScrapStat(Map<String, Object> params) {
		return statsDao.getHubScrapStat(params);
	}

	@Override
	public int getTotalSmartStat() {
		return statsDao.getTotalSmartStat();
	}

	@Override
	public List<ResultMap> getSmartGradeStat(Map<String, Object> params) {
		return statsDao.getSmartGradeStat(params);
	}

	@Override
	public List<ResultMap> getSmartSubjectStat(Map<String, Object> params) {
		return statsDao.getSmartSubjectStat(params);
	}

	@Override
	public List<ResultMap> getSmartBookStat(Map<String, Object> params) {
		return statsDao.getSmartBookStat(params);
	}

	@Override
	public int getTotalCsRequestList(Map<String, Object> params) {
		return statsDao.getTotalCsRequestList(params);
	}

	@Override
	public List<ResultMap> getCsRequestList(Map<String, Object> params) {
		return statsDao.getCsRequestList(params);
	}

	@Override
	public int getTotalCsSubjectList(Map<String, Object> params) {
		return statsDao.getTotalCsSubjectList(params);
	}

	@Override
	public List<ResultMap> getCsSubjectList(Map<String, Object> params) {
		return statsDao.getCsSubjectList(params);
	}

	@Override
	public int getTotalCsContentsList(Map<String, Object> params) {
		return statsDao.getTotalCsContentsList(params);
	}

	@Override
	public List<ResultMap> getCsContentsList(Map<String, Object> params) {
		return statsDao.getCsContentsList(params);
	}

	@Override
	public int getTotalSettingList(Map<String, Object> params) {
		return statsDao.getTotalSettingList(params);
	}

	@Override
	public List<ResultMap> getGradeSettingList(Map<String, Object> params) {
		return statsDao.getGradeSettingList(params);
	}

	@Override
	public List<ResultMap> getSubjectSettingList(Map<String, Object> params) {
		return statsDao.getSubjectSettingList(params);
	}

	@Override
	public int getTotalBookSettingList(Map<String, Object> params) {
		return statsDao.getTotalBookSettingList(params);
	}

	@Override
	public List<ResultMap> getBookSettingList(Map<String, Object> params) {
		return statsDao.getBookSettingList(params);
	}

	@Override
	public List<ResultMap> getSearchWordList(Map<String, Object> params) {
		return statsDao.getSearchWordList(params);
	}
	
	@Override
	public int getTotalsdfdown() {
		return (Integer) statsDao.getTotalsdfdown();
	}
	
	@Override
	public List<ResultMap> getSearchDownList(Map<String, Object> params) {
		return statsDao.getSearchDownList(params);
	}
	@Override
	public List<ResultMap> getSearchDownList1(Map<String, Object> params) {
		return statsDao.getSearchDownList1(params);
	}
}
