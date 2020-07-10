package kr.co.kumsung.thub.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import kr.co.kumsung.thub.dao.CustomerDao;
import kr.co.kumsung.thub.domain.Customer;
import kr.co.kumsung.thub.domain.Send;
import kr.co.kumsung.thub.service.CustomerService;
import kr.co.kumsung.thub.service.FileService;
import kr.co.kumsung.thub.util.Validate;

public class CustomerServiceImpl implements CustomerService{

	@Autowired
	private CustomerDao customerDao;
	
	@Autowired
	private FileService fileService;
	
	@Value("#{common['file.path']}")
	String serverFilePath;
	
	@Override
	public void insertCustomer(Map<String, Object> params) {
		customerDao.insertCustomer(params);
	}
	
	@Override
	public List<Customer> getCustomerList(Map<String, Object> params){
		return (List<Customer>) customerDao.getCustomerList(params);
	}
	
	@Override
	public int getCustomerTotalList(Map<String, Object> params){
		return (Integer) customerDao.getCustomerTotalList(params);
	}
	
	@Override
	public Customer getCustomerDetail(int customerId){
		return (Customer) customerDao.getCustomerDetail(customerId);
	}
	
	@Override
	public Customer getCustomerAnswer(int customerId){
		return (Customer) customerDao.getCustomerAnswer(customerId);
	}
	
	@Override
	public void insertCustomerAnswer(Map<String, Object> params){
		customerDao.insertCustomerAnswer(params);
	}
	
	@Override
	public void updateCustomerAnswer(Map<String, Object> params){
		customerDao.updateCustomerAnswer(params);
	}

	@Override
	public int insertSend(Map<String, Object> params) {
		return customerDao.insertSend(params);
	}

	@Override
	public int getBoardSendListCount(Map<String, Object> params) {
		return customerDao.getBoardSendListCount(params);
	}

	@Override
	public List<Send> getBoardSendList(Map<String, Object> params) {
		return customerDao.getBoardSendList(params);
	}

	@Override
	public Send getBoardSend(int sendId) {
		return customerDao.getBoardSend(sendId);
	}

	@Override
	public void deleteSend(int sendId) {
		// get data
		Send send = customerDao.getBoardSend(sendId);
		
		if( !Validate.isEmpty(send.getDataFile1()) )
		{
			String filePath = serverFilePath + send.getDataFile1().replace("/upfiles" , "");
			fileService.delete(filePath);
		}
		
		if( !Validate.isEmpty(send.getDataFile2()) )
		{
			String filePath = serverFilePath + send.getDataFile2().replace("/upfiles" , "");
			fileService.delete(filePath);
		}
		
		if( !Validate.isEmpty(send.getDataFile3()) )
		{
			String filePath = serverFilePath + send.getDataFile3().replace("/upfiles" , "");
			fileService.delete(filePath);
		}
		
		customerDao.deleteSend(sendId);
	}

	@Override
	public void updateSendAccept(Map<String, Object> params) {
		customerDao.updateSendAccept(params);
	}
	
}
