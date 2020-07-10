package kr.co.kumsung.thub.service.impl;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import kr.co.kumsung.thub.dao.LectureDao;
import kr.co.kumsung.thub.domain.Event;
import kr.co.kumsung.thub.domain.Lecture;
import kr.co.kumsung.thub.service.LectureService;

public class LectureServiceImpl implements LectureService{

	@Autowired
	private LectureDao lectureDao;
	
	@Override
	public List<Lecture> getLectureList(Map<String, Object> params){
		return (List<Lecture>) lectureDao.getLectureList(params);
	}
	
	@Override
	public int getLectureTotalList(Map<String, Object> params){
		return (Integer) lectureDao.getLectureTotalList(params);
	}
	
	@Override
	public Lecture getLectureDetail(int lectureId){
		return (Lecture)lectureDao.getLectureDetail(lectureId);
	}
	
	@Override
	public void insertLecture(Map<String, Object> params){
		lectureDao.insertLecture(params);
	}
	
	@Override
	public void updateLecture(Map<String, Object> params){
		lectureDao.updateLecture(params);
	}
	
	@Override
	public void deleteLecture(int lectureId){
		lectureDao.deleteLecture(lectureId);
	}
	
	@Override
	public String getLectureDetailThumbnail(int lectureId){
		return lectureDao.getLectureDetailThumbnail(lectureId);
	}
}
