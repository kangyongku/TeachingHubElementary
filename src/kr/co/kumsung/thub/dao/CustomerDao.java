package kr.co.kumsung.thub.dao;

import java.util.List;
import java.util.Map;

import kr.co.kumsung.thub.domain.Customer;
import kr.co.kumsung.thub.domain.Send;


public interface CustomerDao {

	/**
	 * 고객문의를 등록한다.
	 * @param params
	 * @return
	 */
	public void insertCustomer(Map<String, Object> params);
	
	/**
	 * 문의요청게시판 리스트
	 * @param params
	 * @return List<Customer>
	 */
	public List<Customer> getCustomerList(Map<String, Object> params);
	
	/**
	 * 문의요청게시판 게시글 수
	 * @param params
	 * @return int
	 */
	public int getCustomerTotalList(Map<String, Object> params);
	
	/**
	 * 문의요청 게시판  상세보기
	 * @param params
	 * @return Customer
	 */
	public Customer getCustomerDetail(int customerId);
	
	/**
	 * 문의요청 답변 내용
	 * @param customerId
	 * @return Customer
	 */
	public Customer getCustomerAnswer(int customerId);
	
	/**
	 * 문의요청 답변 입력
	 * @param params
	 * @return void
	 */
	public void insertCustomerAnswer(Map<String, Object> params);

	/**
	 * 문의요청 답변 수정
	 * @param params
	 * @return void
	 */
	public void updateCustomerAnswer(Map<String, Object> params);
	
	/**
	 * 자료 원고 보내기 등록
	 * @param params
	 * @return
	 */
	public int insertSend(Map<String,Object> params);
	
	/**
	 * 자료 원고 보내기의 총 갯수
	 * @param params
	 * @return
	 */
	public int getBoardSendListCount(Map<String,Object> params);
	
	/**
	 * 자료 원고 보내기의 리스트
	 * @param params
	 * @return
	 */
	public List<Send> getBoardSendList(Map<String,Object> params);
	
	/**
	 * 자료 원고 보내기의 데이타
	 * @param sendId
	 * @return
	 */
	public Send getBoardSend(int sendId);
	
	/**
	 * 자료 원고 관리 삭제
	 * @param sendId
	 */
	public void deleteSend(int sendId);
	
	/**
	 * 자료 원고 관리 승인 상태 변경
	 * @param params
	 */
	public void updateSendAccept(Map<String,Object> params);
}
