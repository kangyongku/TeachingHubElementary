package kr.co.kumsung.thub.dao.impl;

import java.util.List;
import java.util.Map;

import kr.co.kumsung.thub.dao.MemberDao;
import kr.co.kumsung.thub.domain.ChoiceBook;
import kr.co.kumsung.thub.domain.Member;
import kr.co.kumsung.thub.util.ResultMap;

//import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class MemberDaoImpl extends SqlMapClientDaoSupport implements MemberDao{

	@Override
	public Member get(String userId) {
		return (Member) getSqlMapClientTemplate().queryForObject("Member.get" , userId);
	}

	@Override
	public List<Member> getMemberList(Map<String, Object> params){
		return (List<Member>) getSqlMapClientTemplate().queryForList("Member.getMemberList", params);
	}
	
	@Override
	public int getMemberTotalList(Map<String, Object> params){
		return (Integer) getSqlMapClientTemplate().queryForObject("Member.getMemberTotalList", params);
	}
	
	@Override
	public Member getMemberDetail(int id){
		return (Member) getSqlMapClientTemplate().queryForObject("Member.getMemberDetail", id);
	}
	
	@Override
	public void insertMember(Map<String, Object> params){
		getSqlMapClientTemplate().insert("Member.insertMember", params);
	}
	
	@Override
	public void updateMember(Map<String, Object> params){
		getSqlMapClientTemplate().update("Member.updateMember", params);
	}
	
	@Override
	public void deleteMember(int id){
		getSqlMapClientTemplate().delete("Member.deleteMember", id);
	}
	
	@Override
	public void updateMemberTeacher(int id){
		getSqlMapClientTemplate().update("Member.updateMemberTeacher", id);
	}
	
	@Override
	public Member getFindUserInfo(String userId){
		return (Member) getSqlMapClientTemplate().queryForObject("Member.getFindUserInfo", userId);
	}
	
	@Override
	public void insertAuth(Map<String, Object> params){
		getSqlMapClientTemplate().insert("Member.insertAuth", params);
	}
	
	@Override
	public List<Member> getAuthList(Map<String, Object> params){
		return (List<Member>) getSqlMapClientTemplate().queryForList("Member.getAuthList", params);
	}
	
	@Override
	public int getAuthTotalList(Map<String, Object> params){
		return (Integer) getSqlMapClientTemplate().queryForObject("Member.getAuthTotalList", params);
	}
	
	@Override
	public Member getAuthDetail(int authId){
		return (Member) getSqlMapClientTemplate().queryForObject("Member.getAuthDetail", authId);
	}
	
	@Override
	public void updateAuth(Map<String, Object> params){
		getSqlMapClientTemplate().update("Member.updateAuth", params);
	}

	@Override
	public Member getAdminLogin(Map<String, Object> params) {
		return (Member) getSqlMapClientTemplate().queryForObject("Member.getAdminLogin", params);
	}
	
	@Override
	public int getAuthUserDup(String userId){
		return (Integer) getSqlMapClientTemplate().queryForObject("Member.getAuthUserDup", userId);
	}

	@Override
	public int matchPassword(Map<String, Object> params) {
		return (Integer) getSqlMapClientTemplate().queryForObject("Member.matchPassword", params);
	}
	
	@Override
	public Member getMemberInfo(String userId){
		return (Member) getSqlMapClientTemplate().queryForObject("Member.getMemberInfo", userId);
	}
	
	@Override
	public int getLearningAuth(Map<String, Object> params){
		return (Integer) getSqlMapClientTemplate().queryForObject("Member.getLearningAuth", params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ChoiceBook> getMyBooks(String userId) {
		return (List<ChoiceBook>) getSqlMapClientTemplate().queryForList("Member.getMyBooks", userId);
	}
	
	@Override
	public int getMyBooksCount(String userId) {
		return (Integer) getSqlMapClientTemplate().queryForObject("Member.getMyBooksCount", userId);
	}
	
	@Override
	public int isDuplicatedMyBook(Map<String,Object> params){
		return (Integer) getSqlMapClientTemplate().queryForObject("Member.isDuplicatedMyBook", params);
	}

	@Override
	public int insertMyBook(Map<String, Object> params) {
		return (Integer) getSqlMapClientTemplate().insert("Member.insertMyBook" , params);
	}

	@Override
	public void deleteMyBook(Map<String, Object> params) {
		getSqlMapClientTemplate().delete("Member.deleteMyBook" , params);
	}

	@Override
	public void updateSubCategory(Map<String, Object> params) {
		getSqlMapClientTemplate().update("Member.updateSubCategory" , params);
	}

	@Override
	public void updateMemberInfo(Map<String, Object> params) {
		getSqlMapClientTemplate().update("Member.updateMemberInfo" , params);
	}

	@Override
	public void sendSms(Map<String, Object> params) {
		getSqlMapClientTemplate().insert("Member.sendSms" , params);
	}

	@Override
	public void insertSmsSendInfo(Map<String, Object> params) {
		getSqlMapClientTemplate().insert("Member.insertSmsSendInfo" , params);
	}

	@Override
	public int getSmsSendListCount() {
		return (Integer) getSqlMapClientTemplate().queryForObject("Member.getSmsSendListCount");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ResultMap> getSmsSendList(Map<String, Object> params) {
		return (List<ResultMap>) getSqlMapClientTemplate().queryForList("Member.getSmsSendList", params);
	}

	@Override
	public Member getFindMemberByInfo(Map<String, Object> params) {
		return (Member) getSqlMapClientTemplate().queryForObject("Member.getFindMemberByInfo", params);
	}

	@Override
	public void updateMemberPasswd(Map<String, Object> params) {
		getSqlMapClientTemplate().update("Member.updateMemberPasswd" , params);
	}
	
	@Override
	public void insertSdfdown(String userId){
		getSqlMapClientTemplate().insert("Member.insertSdfdown", userId);
	}

	@Override
	public void deleteAuth(int authId) {
		getSqlMapClientTemplate().delete("Member.deleteAuth",authId);
		
	}

	@Override
	public void updateLoginCount(Map<String,Object> params) {
		getSqlMapClientTemplate().update("Member.updateLoginCount",params);
	}

	@Override
	public void updateLoginCountDefault(Map<String, Object> params) {
		getSqlMapClientTemplate().update("Member.updateLoginCountDefault",params);
	}
	@Override
	public Integer getAccessTotalList(Map<String, Object> params) {
		return (Integer) getSqlMapClientTemplate().queryForObject("Member.getAccessTotalList", params);
	}

	@Override
	public List<Member> getAccessList(Map<String, Object> params) {
		return (List<Member>) getSqlMapClientTemplate().queryForList("Member.getAccessList", params);
	}

	@Override
	public Member getFindAccessUserInfo(String userId) {
		return (Member) getSqlMapClientTemplate().queryForObject("Member.getFindAccessUserInfo", userId);
	}

	@Override
	public int updateAccessMember(Map<String, Object> params) {
		return getSqlMapClientTemplate().update("Member.updateAccessMember",params);
	}

	@Override
	public int deleteAccessMember(Map<String, Object> params) {
		return getSqlMapClientTemplate().update("Member.deleteAccessMember",params);
	}

	@Override
	public Integer getMemberAccessTotalList(Map<String, Object> params) {
		return (Integer) getSqlMapClientTemplate().queryForObject("Member.getMemberAccessTotalList", params);
	}

	@Override
	public List<Member> getMemberAccessList(Map<String, Object> params) {
		return (List<Member>) getSqlMapClientTemplate().queryForList("Member.getMemberAccessList", params);
	}

	@Override
	public void insertAccessLog(Map<String, Object> params) {
		getSqlMapClientTemplate().insert("Member.insertAccessLog", params);
		
	}
	
	@Override
	public Member getMemberSchoolInfo(String userId){
		return (Member) getSqlMapClientTemplate().queryForObject("Member.getMemberSchoolInfo", userId);
	}
	

}
