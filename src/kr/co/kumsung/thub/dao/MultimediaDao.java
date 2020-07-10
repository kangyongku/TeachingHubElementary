package kr.co.kumsung.thub.dao;

import java.util.List;
import java.util.Map;

import kr.co.kumsung.thub.domain.Multimedia;

public interface MultimediaDao {
	/**
	 * 멀티미디어 상세보기
	 * @param mmId
	 * @return Multimedia
	 */
	public Multimedia getMultimediaDetail(int mmId);
	
	/**
	 * 멀티미디어 입력
	 * @param params
	 * @return int
	 */
	public int insertMultimedia(Map<String, Object> params);

	/**
	 * 멀티미디어 리스트
	 * @param params
	 * @return List<Multimedia>
	 */
	public List<Multimedia> getMultimediaList(Map<String, Object> params);
	
	/**
	 * 멀티미디어 리스트 개수
	 * @param params
	 * @return int
	 */
	public int getTotalMultimedia(Map<String, Object> params);
	
	/**
	 * 멀티미디어 수정
	 * @param params
	 * @return
	 */
	public void updateMultimedia(Map<String, Object> params);
	
	/**
	 * 멀티미디어 첨부파일 삭제하기 위해 경로를 가져온다.
	 * @param params
	 * @return String
	 */
	public String getDeleteTarget(Map<String, Object> params);
	
	/**
	 * 해당 게시글을 삭제
	 * @param param
	 * @return
	 */
	public void multimediaDelete(int param);
	
	/**
	 * 이전 멀티미디어 데이타를 가지고 온다.
	 * @param params
	 * @return
	 */
	public Multimedia getPrevMultimedia(Map<String,Object> params);
	
	/**
	 * 다음 멀티디이어 데이타를 가지고 온다.
	 * @param params
	 * @return
	 */
	public Multimedia getNextMultimedia(Map<String,Object> params);
	
	/**
	 * 멀티미디어 자료가 자료관리 데이터에 참조된 횟수.
	 * @param params
	 * @return int
	 */
	public int getDataRefCount(int param);
}
