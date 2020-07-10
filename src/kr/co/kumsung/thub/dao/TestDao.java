package kr.co.kumsung.thub.dao;

import java.util.Map;

import kr.co.kumsung.thub.domain.TestDomain;

public interface TestDao {

	public String getNow();
	
	public void insertData(Map<String,Object> params);
	
	public TestDomain getMath();
	
}
