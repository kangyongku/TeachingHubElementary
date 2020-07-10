package kr.co.kumsung.thub.service;

import java.util.List;
import java.util.Map;

import kr.co.kumsung.thub.domain.MainData;

public interface MainDisplayService {

	/**
	 * 메인화면 자료 관리 리스트
	 * @param params
	 * @return List<MainData>
	 */
	public List<MainData> getDispList(Map<String, Object> params);
	
	/**
	 * 메인화면 자료 관리 디테일
	 * @param params
	 * @return List<MainData>
	 */
	public MainData getDispDetail(int dispId);
	
	/**
	 * 메인화면 자료 설정보기
	 * @param dispCd
	 * @return MainData
	 */
	public MainData getDispConf(String dispCd);
	
	/**
	 * 메인화면 자료 입력
	 * @param params
	 * @return 
	 */
	public void insertDisplay(Map<String, Object> params);
	
	/**
	 * 메인화면 자료 삭제
	 * @param dispId
	 * @return 
	 */
	public void deleteDisplay(int dispId);

	/**
	 * 메인화면 자료 뷰 리스트
	 * @param dispCd
	 * @return List<MainData>
	 */
	public List<MainData> getDispViewList(String dispCd);

	/**
	 * 메인화면 데이터 정렬 	
	 * @param dispId
	 * @return 
	 */
	
	public void initDisplay(int dispId);

	/**
	 * 메인화면 데이터 체크
	 * @param dispId
	 * @return 
	 */
	public MainData initChkDisplay(int dispId);

	/**
	 * 메인화면 최대값 체크
	 * @param type
	 * @return 
	 */
	public MainData initChkMaxDisplay(String type);

	/**
	 * 메인화면 이동 전 값 확인(UP)
	 * @param params
	 * @return
	 */
	public MainData initStepUpDisplay(Map<String, Object> params);

	/**
	 * 메인화면 이동 전 값 변경(UP)	
	 * @param getdispCd
	 */
	public void initStepUpdate(Map<String, Object> params);

	/**
	 * 메인화면 이동 전 값 변경2(UP)
	 * @param params
	 */
	public void init2Stepupdate(Map<String, Object> params);

	/**
	 * 메인화면 수정
	 * @param params
	 */
	public void updateDisplay(Map<String, Object> params);

	
	/**
	 * 메인화면 랭킹순위 수정
	 * @param params
	 * @return
	 */
	public void updateDisplayRanking(Map<String, Object> params);
	
	
	/**
	 * 메인화면 이동 전 값 변경(DOWN)
	 * @param
	 */
	
	public MainData initStepDwDisplay(Map<String, Object> params);

	/**
	 * 메인화면 이동 전 값 변경2(down)
	 * @param params
	 */
	public void initStep3Update(Map<String, Object> params);

	
}
