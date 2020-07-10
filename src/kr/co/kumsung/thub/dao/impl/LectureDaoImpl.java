package kr.co.kumsung.thub.dao.impl;

import java.util.List;
import java.util.Map;

import kr.co.kumsung.thub.dao.LectureDao;
import kr.co.kumsung.thub.domain.Event;
import kr.co.kumsung.thub.domain.Lecture;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class LectureDaoImpl extends SqlMapClientDaoSupport implements LectureDao{
	
	public List<Lecture> getLectureList(Map<String, Object> params){
		return (List<Lecture>) getSqlMapClientTemplate().queryForList("Lecture.getLectureList", params);
	}
	
	public int getLectureTotalList(Map<String, Object> params){
		return (Integer) getSqlMapClientTemplate().queryForObject("Lecture.getLectureTotalList", params);
	}
	
	@Override
	public Lecture getLectureDetail(int lectureId){
		return (Lecture) getSqlMapClientTemplate().queryForObject("Lecture.getLectureDetail", lectureId);
	}
	
	@Override
	public void insertLecture(Map<String, Object> params){
		getSqlMapClientTemplate().insert("Lecture.insertLecture", params);
	}
	
	@Override
	public void updateLecture(Map<String, Object> params){
		getSqlMapClientTemplate().update("Lecture.updateLecture", params);
	}
	
	@Override
	public void deleteLecture(int lectureId){
		getSqlMapClientTemplate().delete("Lecture.deleteLecture", lectureId);
	}
	
	@Override
	public String getLectureDetailThumbnail(int lectureId){
		return (String)getSqlMapClientTemplate().queryForObject("Lecture.getLectureDetailThumbnail", lectureId);
	}
	
}
