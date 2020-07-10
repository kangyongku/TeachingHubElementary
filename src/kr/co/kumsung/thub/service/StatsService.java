package kr.co.kumsung.thub.service;

import java.util.List;
import java.util.Map;

import kr.co.kumsung.thub.util.ResultMap;

public interface StatsService {

	/**
	 * 전체 회원의 통계수를 가지고 온다.
	 * @return
	 */
	public int getTotalMemberStat();
	
	/**
	 * 회원의 통계를 가지고 온다.
	 * @param params
	 * @return
	 */
	public List<ResultMap> getMemberStat(Map<String,Object> params);
	
	/**
	 * 전체 탈퇴회원의 통계수를 가지고 온다.
	 * @return
	 */
	public int getTotalMemberSeceder();
	
	/**
	 * 탈퇴회원의 통계를 가지고 온다.
	 * @param params
	 * @return
	 */
	public List<ResultMap> getMemberSeceder(Map<String,Object> params);
	
	/**
	 * 티칭허브 교수학습자료 전체
	 * @param params
	 * @return
	 */
	public int getTotalHubContentStat();
	
	/**
	 * 티칭허브 교수학습자료 통계
	 * @param params
	 * @return
	 */
	public List<ResultMap> getHubContentStat(Map<String,Object> params);
	
	/**
	 * 티칭허브 통합게시글 총갯수
	 * @return
	 */
	public int getTotalHubArticleStat();
	
	/**
	 * 티칭허브  통합게시글 통계
	 * @param params
	 * @return
	 */
	public List<ResultMap> getHubArticleStat(Map<String,Object> params);
	
	/**
	 * 티칭허브 스크랩 총갯수
	 * @return
	 */
	public int getTotalHubScrapStat();
	
	/**
	 * 티칭허브 스크랩 통계
	 * @param params
	 * @return
	 */
	public List<ResultMap> getHubScrapStat(Map<String,Object> params);
	
	/**
	 * 티칭백과 총갯수
	 * @return
	 */
	public int getTotalSmartStat();
	
	/**
	 * 티칭백과 통계(학교급별)
	 * @param params
	 * @return
	 */
	public List<ResultMap> getSmartGradeStat(Map<String,Object> params);
	
	/**
	 * 티칭백과 통계(교과별)
	 * @param params
	 * @return
	 */
	public List<ResultMap> getSmartSubjectStat(Map<String,Object> params);
	
	/**
	 * 티칭백과 통계(도서별)
	 * @param params
	 * @return
	 */
	public List<ResultMap> getSmartBookStat(Map<String,Object> params);

	/**
	 * CS관리 > 고객문의 총갯수
	 * @param params
	 * @return
	 */
	public int getTotalCsRequestList(Map<String,Object> params);
	
	/**
	 * CS관리 > 고객문의 리스트
	 * @param params
	 * @return
	 */
	public List<ResultMap> getCsRequestList(Map<String,Object> params);
	
	/**
	 * CS관리 > 교과서 자료요청
	 * @param params
	 * @return
	 */
	public int getTotalCsSubjectList(Map<String,Object> params);
	
	/**
	 * CS관리 > 교과서 자료요청
	 * @param params
	 * @return
	 */
	public List<ResultMap> getCsSubjectList(Map<String,Object> params);
	
	/**
	 * CS관리 > 교과서 내용문의
	 * @param params
	 * @return
	 */
	public int getTotalCsContentsList(Map<String,Object> params);
	
	/**
	 * CS관리 > 교과서 내용문의
	 * @param params
	 * @return
	 */
	public List<ResultMap> getCsContentsList(Map<String,Object> params);
	
	/**
	 * 교과설정의 총 카운트를 가지고 온다.
	 * @param params
	 * @return
	 */
	public int getTotalSettingList(Map<String,Object> params);
	
	/**
	 * 교과설정 > 학교급설정
	 * @param params
	 * @return
	 */
	public List<ResultMap> getGradeSettingList(Map<String,Object> params);
	
	/**
	 * 교과설정 > 교과설정
	 * @param params
	 * @return
	 */
	public List<ResultMap> getSubjectSettingList(Map<String,Object> params);
	
	/**
	 * 교과서설정의 총 카운트
	 * @param params
	 * @return
	 */
	public int getTotalBookSettingList(Map<String,Object> params);
	
	/**
	 * 교과설정 > 교과서 설정
	 * @param params
	 * @return
	 */
	public List<ResultMap> getBookSettingList(Map<String,Object> params);
	
	/**
	 * 검색어 관리
	 * @param params
	 * @return
	 */
	public List<ResultMap> getSearchWordList(Map<String,Object> params);
	
	/**
	 * 저작도구 다운로드 전체 건수 가지고 온다.
	 * @return
	 */
	public int getTotalsdfdown();
	
	/**
	 * 저작도구 기간 조회
	 * @param params
	 * @return
	 */
	public List<ResultMap> getSearchDownList(Map<String,Object> params);
	
	/**
	 * 저작도구 기간 조회
	 * @param params
	 * @return
	 */
	public List<ResultMap> getSearchDownList1(Map<String,Object> params);
}
