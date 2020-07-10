package kr.co.kumsung.thub.service;

import java.util.List;
import java.util.Map;

import kr.co.kumsung.thub.domain.ChoiceBook;
import kr.co.kumsung.thub.domain.Member;
import kr.co.kumsung.thub.util.ResultMap;

public interface MemberService {

	public Member get(String userId);
	
	/**
	 * 회원 리스트
	 * @param params
	 * @return List<Member>
	 */
	public List<Member> getMemberList(Map<String, Object> params);
	
	/**
	 * 회원의 비밀번호를 체크한다.
	 * @param params
	 * @return
	 */
	public int matchPassword(Map<String,Object> params);
	
	/**
	 * 회원 리스트 개수
	 * @param params
	 * @return int
	 */
	public int getMemberTotalList(Map<String, Object> params);

	/**
	 * 회원 상세 보기
	 * @param params
	 * @return Member
	 */
	public Member getMemberDetail(int id);
	
	/**
	 * 회원 학교급 처리 
	 */
	
	public int getMemberSchoolInfo(String user_id);
	
	
	/**
	 * 회원 입력
	 * TODO : 미구현
	 * @param params
	 * @return
	 */
	public void insertMember(Map<String, Object> params);
	
	/**
	 * 회원 수정
	 * TODO : 미구현
	 * @param params
	 * @return
	 */
	public void updateMember(Map<String, Object> params);
	
	/**
	 * 회원 삭제
	 * TODO : 미구현
	 * @param id
	 * @return 
	 */
	public void deleteMember(int id);
	
	/**
	 * 회원 선생님 승인
	 * @param id
	 * @return
	 */
	public void updateMemberTeacher(int id);
	
	/**
	 * 관리자 회원으로 등록할 회원을 Member테이블에서 ID 조회
	 * @param userId
	 * @return Member
	 */
	public Member getFindUserInfo(String userId);
	
	/**
	 * 관리자 회원 권한 등록
	 * @param params
	 * @return 
	 */
	public void insertAuth(Map<String, Object> params);
	
	/**
	 * 관리자 회원 리스트
	 * @param params
	 * @return List<Member>
	 */
	public List<Member> getAuthList(Map<String, Object> params);
	
	/**
	 * 관리자 회원 리스트 개수
	 * @param params
	 * @return int
	 */
	public int getAuthTotalList(Map<String, Object> params);
	
	/**
	 * 관리자 회원 상세 보기
	 * @param params
	 * @return List<Member>
	 */
	public Member getAuthDetail(int authId);
	
	/**
	 * 관리자 회원 권한 수정
	 * @param params
	 * @return 
	 */
	public void updateAuth(Map<String, Object> params);

	
	/**
	 * 관리자 회원 삭제
	 * @param params
	 * @return 
	 */
	public void deleteAuth(int authId);
	
	
	/**
	 * 로그인
	 * @param params
	 * @return Member
	 */
	public Member getAdminLogin(Map<String,Object> params);
	
	/**
	 * 사용자 중복 체크
	 * @param userId
	 * @return int
	 */
	public int getAuthUserDup(String userId);
	
	/**
	 * 사용자 정보 조회
	 * 아이디, 이름, 전화번호, 이메일.
	 * getMemberInfo와  MemberInfoMap 두가지 수정.
	 * @param userId
	 * @return Member
	 */
	public Member getMemberInfo(String userId);
	
	/**
	 * 교과 정보 권한 여부 체크
	 * 권한이 있을경우 1 없으면 0
	 * userId, category를 파라미터로 던져주면 된다.
	 * @param params
	 * @return int
	 */
	public int getLearningAuth(Map<String, Object> params);
	
	/**
	 * 설정된 나의 도서를 가지고 온다.
	 * @param userId
	 * @return
	 */
	public List<ChoiceBook> getMyBooks(String userId);
	
	/**
	 * 등록된 나의 교과서 카운트
	 * @param userId
	 * @return
	 */
	public int getMyBooksCount(String userId);
	
	/**
	 * 나의 교과로 이미 설정이 되어있는지 체크한다.
	 * @param params
	 * @return
	 */
	public int isDuplicatedMyBook(Map<String,Object> params);
	
	/**
	 * 나의 교과서로 등록하기
	 * @param params
	 * @return
	 */
	public int insertMyBook(Map<String,Object> params);
	
	/**
	 * 나의 교과서에서 삭제한다.
	 * @param params
	 */
	public void deleteMyBook(Map<String,Object> params);
	
	/**
	 * 교과설정
	 * @param params
	 */
	public void updateSubCategory(Map<String,Object> params);
	
	/**
	 * 메일을 발송한다.
	 * @param target
	 * @param contents
	 * @return
	 */
	public boolean sendEmail(String target , String subject ,String contents);
	
	/**
	 * 메일을 발송한다.
	 * @param target
	 * @param contents
	 * @return
	 */
	public boolean sendEmailFromMe(String target , String from , String subject ,String contents);
	
	/**
	 * 회원정보수정
	 * @param params
	 */
	public void updateMemberInfo(Map<String,Object> params);
	
	/**
	 * SMS를 발송한다.
	 * @param params
	 */
	public void sendSms(Map<String,Object> params);
	
	/**
	 * SMS를 로그를 남긴다.
	 * @param params
	 */
	public void insertSmsSendInfo(Map<String,Object> params);
	
	/**
	 * 로그 갯수
	 * @return
	 */
	public int getSmsSendListCount();
	
	/**
	 * 로그 리스트
	 * @return
	 */ 
	public List<ResultMap> getSmsSendList(Map<String,Object> params);
	
	/**
	 * 회원 이메일로 찾기
	 * @param params
	 * @return
	 */
	public Member getFindMemberByInfo(Map<String,Object> params);
	
	/**
	 * 회원 비밀번호 갱신
	 * @param params
	 */
	public void updateMemberPasswd(Map<String,Object> params);
	
	/**
	 * 저작도구 다운 현황 등록
	 * @param params
	 * @return 
	 */
	public void insertSdf(String userId);
	
	/**
	 * 로그인 실패 갯수업데이트
	 * @param params
	 * @return 
	 */
	public void updateLoginCount(Map<String,Object> params);
	
	/**
	 *  로그인 실패 갯수 0 
	 * @param params
	 * @return 
	 */
	public void updateLoginCountDefault(Map<String, Object> params);
	
	/**
	 * 접근권한 관리 카운트
	 * @param params
	 * @return 
	 */
	public int getAccessTotalList(Map<String, Object> params);

	/**
	 * 접근권한 관리 리스트
	 * @param params
	 * @return 
	 */
	public List<Member> getAccessList(Map<String, Object> params);
	
	/**
	 * 접근권한 관리 상세정보
	 * @param userId
	 * @return 
	 */
	public Member getFindAccessUserInfo(String userId);
	
	/**
	 * 접근권한 관리 IP등록(update)
	 * @param params
	 * @return 
	 */
	public int updateAccessMember(Map<String, Object> params);
	
	/**
	 * 접근권한 관리 IP삭제(update)
	 * @param params
	 * @return 
	 */
	public int deleteAccessMember(Map<String, Object> params);

	/**
	 * 회원정보 접속기록 카운트
	 * @param params
	 * @return 
	 */
	public int getMemberAccessTotalList(Map<String, Object> params);

	/**
	 * 회원정보 접속기록 리스트
	 * @param params
	 * @return 
	 */
	public List<Member> getMemberAccessList(Map<String, Object> params);

	/**
	 * 회원정보 접속기록 log 저장
	 * @param params
	 * @return 
	 */
	public void insertAccessLog(Map<String, Object> params);


	
}
