package kr.co.kumsung.thub.service;

import java.util.List;
import java.util.Map;

import kr.co.kumsung.thub.domain.Event;
import kr.co.kumsung.thub.domain.Lecture;

public interface LectureService {
	/**
	 * 공감! 강연관리 리스트
	 * @param params
	 * @return List<Lecture>
	 */
	public List<Lecture> getLectureList(Map<String, Object> params);
	
	/**
	 * 공감! 강연관리 리스트 개수
	 * @param params
	 * @return int
	 */
	public int getLectureTotalList(Map<String, Object> params);
	
	/**
	 * 공감! 강연관리 상세보기
	 * @param lectureId
	 * @return Lecture
	 */
	public Lecture getLectureDetail(int lectureId);
	
	/**
	 * 공감! 강연관리 등록
	 * @param params
	 * @return
	 */
	public void insertLecture(Map<String, Object> params);
	
	/**
	 * 공감! 강연관리 수정
	 * @param params
	 * @return
	 */
	public void updateLecture(Map<String, Object> params);
	
	/**
	 * 공감! 강연관리 삭제
	 * @param lectureId
	 * @return
	 */
	public void deleteLecture(int lectureId);
	
	/**
	 * 공감! 강연관리 등록 되어있는 섬네일 경로 가져오기 (삭제용)
	 * @param lectureId
	 * @return String
	 */
	public String getLectureDetailThumbnail(int lectureId);

}
