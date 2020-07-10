package kr.co.kumsung.thub.service;

import java.util.List;
import java.util.Map;

import kr.co.kumsung.thub.domain.FileInfo;
import kr.co.kumsung.thub.domain.Multimedia;


public interface MultimediaService {
	
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
	 * 마스터 값의 이름을 가지고 온다.
	 * 프론트에서 사용되어지며 총 3개의 카테고리에 한하여 처리한다.
	 * DB에 대한 조회는 하지 않는다.(Master가 바뀌는 일은 없기 때문이라고 가정한다.)
	 * @param findMaster
	 * @return
	 */
	public String getMasterCategoryName(String findMaster);
	
	/**
	 * front 멀티미디어 자료실에 access가 가능한 코드인지 체크한다.
	 * @param findMaster
	 * @return
	 */
	public boolean isAccessMultimedia(String findMaster);
	
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
