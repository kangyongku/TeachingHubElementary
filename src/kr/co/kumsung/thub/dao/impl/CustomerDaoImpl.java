package kr.co.kumsung.thub.dao.impl;

import java.util.List;
import java.util.Map;

import kr.co.kumsung.thub.dao.CustomerDao;
import kr.co.kumsung.thub.domain.Customer;
import kr.co.kumsung.thub.domain.Send;


import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class CustomerDaoImpl extends SqlMapClientDaoSupport implements CustomerDao{

	@Override
	public void insertCustomer(Map<String, Object> params) {
		getSqlMapClientTemplate().insert("Customer.insertCustomer" , params);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Customer> getCustomerList(Map<String, Object> params){
		return (List<Customer>) getSqlMapClientTemplate().queryForList("Customer.getCustomerList", params);
	}
	
	@Override
	public int getCustomerTotalList(Map<String, Object> params){
		return (Integer) getSqlMapClientTemplate().queryForObject("Customer.getCustomerTotalList", params);
	}
	
	@Override
	public Customer getCustomerDetail(int customerId){
		return (Customer) getSqlMapClientTemplate().queryForObject("Customer.getCustomerDetail", customerId);
	}
	
	@Override
	public Customer getCustomerAnswer(int customerId){
		return (Customer) getSqlMapClientTemplate().queryForObject("Customer.getCustomerAnswer", customerId);
	}
	
	@Override
	public void insertCustomerAnswer(Map<String, Object> params){
		getSqlMapClientTemplate().insert("Customer.insertCustomerAnswer", params);
	}
	
	@Override
	public void updateCustomerAnswer(Map<String, Object> params){
		getSqlMapClientTemplate().update("Customer.updateCustomerAnswer", params);
	}

	@Override
	public int insertSend(Map<String, Object> params) {
		return (Integer) getSqlMapClientTemplate().insert("Customer.insertSend" , params);
	}

	@Override
	public int getBoardSendListCount(Map<String, Object> params) {
		return (Integer) getSqlMapClientTemplate().queryForObject("Customer.getBoardSendListCount", params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Send> getBoardSendList(Map<String, Object> params) {
		return (List<Send>) getSqlMapClientTemplate().queryForList("Customer.getBoardSendList", params);
	}

	@Override
	public Send getBoardSend(int sendId) {
		return (Send) getSqlMapClientTemplate().queryForObject("Customer.getBoardSend", sendId);
	}

	@Override
	public void deleteSend(int sendId) {
		getSqlMapClientTemplate().delete("Customer.deleteSend" , sendId);
	}

	@Override
	public void updateSendAccept(Map<String, Object> params) {
		getSqlMapClientTemplate().update("Customer.updateSendAccept" , params);
	}
}
