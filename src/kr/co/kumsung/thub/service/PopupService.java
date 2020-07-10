package kr.co.kumsung.thub.service;

import java.util.List;
import java.util.Map;

import kr.co.kumsung.thub.domain.Config;
import kr.co.kumsung.thub.domain.Popup;

public interface PopupService {

	/**
	 * 팝업관리 리스트
	 * @param params
	 * @return List<Popup>
	 */
	public List<Popup> getPopupList(Map<String, Object> params);
	
	/**
	 * 팝업관리 리스트 개수
	 * @param params
	 * @return int
	 */
	public int getPopupTotalList(Map<String, Object> params);

	/**
	 * 팝업관리 상세보기
	 * @param popupId
	 * @return Popup
	 */
	public Popup getPopupDetail(int popupId);
	
	/**
	 * 팝업관리 입력
	 * @param params
	 * @return 
	 */
	public void insertPopup(Map<String, Object> params);
	
	/**
	 * 팝업관리 수정
	 * @param params
	 * @return 
	 */
	public void updatePopup(Map<String, Object> params);
	
	/**
	 * 팝업관리 삭제
	 * @param params
	 * @return 
	 */
	public void deletePopup(int popupId);

	/**
	 * 주소리스트
	 * @param param
	 * @return
	 */
	public List<Config> zipList(Config param);

	
	/**
	 * 주소총 카운트
	 * @param param
	 * @return
	 */
	public int	zipListCount(Config param);
}
