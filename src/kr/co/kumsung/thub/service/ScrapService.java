package kr.co.kumsung.thub.service;

import java.util.List;
import java.util.Map;

import kr.co.kumsung.thub.domain.Folder;
import kr.co.kumsung.thub.domain.Scrap;

public interface ScrapService {

	/**
	 * 스크랩 폴더의 리스트를 가지고 온다.
	 * @param userId
	 * @return
	 */
	public List<Folder> getFolderList(String userId);
	
	/**
	 * 스크랩 폴더의 데이타를 가지고 온다.
	 * @return
	 */
	public Folder getFolder(Map<String,Object> params);
	
	/**
	 * 폴더를 생성한다.
	 * @param params
	 * @return
	 */
	public int insertFolder(Map<String,Object> params);
	
	/**
	 * 폴더를 수정한다.
	 * @param params
	 */
	public void updateFolder(Map<String,Object> params);
	
	/**
	 * 폴더를 삭제한다.
	 * @param params
	 */
	public void deleteFolder(Map<String,Object> params);
	
	/**
	 * 스크랩 카운트 구하기
	 * @param params
	 * @return
	 */
	public int getScrapListCount(Map<String,Object> params);
	
	/**
	 * 스크랩 리스트를 구한다.
	 * @param params
	 * @return
	 */
	public List<Scrap> getScrapList(Map<String,Object> params);
	
	/**
	 * 스크랩 중복체크
	 * @param params
	 * @return
	 */
	public int isDuplicated(Map<String,Object> params);
	
	/**
	 * 스크랩을 진행한다.
	 * @param params
	 * @return
	 */
	public int insertScrap(Map<String,Object> params);
	
	/**
	 * 스크랩을 삭제한다.
	 * @param params
	 */
	public void deleteScrap(Map<String,Object> params);
	
	/**
	 * 스크랩 폴더를 변경한다.
	 * @param params
	 */
	public void moveScrapFolder(Map<String,Object> params);
	
}

