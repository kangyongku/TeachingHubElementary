package kr.co.kumsung.thub.dao.impl;

import java.util.Map;

import kr.co.kumsung.thub.dao.TestDao;
import kr.co.kumsung.thub.domain.TestDomain;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class TestDaoImpl extends SqlMapClientDaoSupport implements TestDao{

	@Override
	public String getNow() {
		return (String) getSqlMapClientTemplate().queryForObject("Test.getNow");
	}

	@Override
	public void insertData(Map<String, Object> params) {
		getSqlMapClientTemplate().insert("Test.insertData", params);
	}

	@Override
	public TestDomain getMath() {
		return (TestDomain) getSqlMapClientTemplate().queryForObject("Test.getMath");
	}
}
