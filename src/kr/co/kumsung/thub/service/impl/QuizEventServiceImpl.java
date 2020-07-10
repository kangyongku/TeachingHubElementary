package kr.co.kumsung.thub.service.impl;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import kr.co.kumsung.thub.dao.QuizEventDao;
import kr.co.kumsung.thub.service.QuizEventService;

public class QuizEventServiceImpl implements QuizEventService {

	@Autowired
	private QuizEventDao quizEventDao;

	@Override
	public int getRegCnt(Map<String, Object> params) {
		return quizEventDao.getRegCnt(params);
	}

	@Override
	public String getEnddate(Map<String, Object> params) {
		return quizEventDao.getEnddate(params);
	}

	@Override
	public void insertQuizEvent(Map<String, Object> params) {
		quizEventDao.insertQuizEvent(params);
	}

}