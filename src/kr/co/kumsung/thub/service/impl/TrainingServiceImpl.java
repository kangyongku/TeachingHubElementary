package kr.co.kumsung.thub.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import kr.co.kumsung.thub.dao.TrainingDao;
import kr.co.kumsung.thub.domain.Training;
import kr.co.kumsung.thub.domain.TrainingPostscript;
import kr.co.kumsung.thub.domain.TrainingRequest;
import kr.co.kumsung.thub.service.TrainingService;



public class TrainingServiceImpl implements TrainingService {
	
	@Autowired
	private TrainingDao trainingDao;
	
	//교사연수지원
	@Override
	public List<Training> getTrainingList(Map<String, Object> params){
		return (List<Training>) trainingDao.getTrainingList(params);
	}

	@Override
	public void insertTraining(Map<String, Object> params){
		trainingDao.insertTraining(params);
	}
	
	@Override
	public Training getTrainingDetail(int trainingId){
		return (Training) trainingDao.getTrainingDetail(trainingId);
	}
	
	@Override
	public void updateTraining(Map<String, Object> params){
		trainingDao.updateTraining(params);
	}
	
	@Override
	public int getTrainingTotalList(Map<String, Object> params){
		return (Integer) trainingDao.getTrainingTotalList(params);
	}
	
	@Override
	public List<TrainingRequest> getTrainingReqList(Map<String, Object> params){
		return (List<TrainingRequest>) trainingDao.getTrainingReqList(params);
	}
	
	@Override
	public int getRequestTrainingTotal(int trainingId){
		return (Integer) trainingDao.getRequestTrainingTotal(trainingId);
	}
	
	@Override
	public String getTitleName(int trainingId){
		return (String) trainingDao.getTitleName(trainingId);
	}
	
	@Override
	public void deleteTraining(int trainingId){
		trainingDao.deleteTraining(trainingId);
	}

	//현장스케치
	@Override
	public List<TrainingPostscript> getTrainingPostscriptList(Map<String, Object> params){
		return (List<TrainingPostscript>) trainingDao.getTrainingPostscriptList(params);
	}
	
	@Override
	public int getTrainingPostscriptTotalList(Map<String, Object> params){
		return (Integer) trainingDao.getTrainingPostscriptTotalList(params);
	}
	
	@Override
	public TrainingPostscript getTrainingPostscriptDetail(int postId){
		return (TrainingPostscript) trainingDao.getTrainingPostscriptDetail(postId);
	}
	
	@Override
	public List<Training> getTrainingIdList(){
		return (List<Training>) trainingDao.getTrainingIdList();
	}
	
	@Override
	public void insertTrainingPostscript(Map<String, Object> params){
		trainingDao.insertTrainingPostscript(params);
	}
	
	@Override
	public void updateTrainingPostscript(Map<String, Object> params){
		trainingDao.updateTrainingPostscript(params);
	}
	
	@Override
	public String getTraiingRequestContents(int requestId){
		return trainingDao.getTraiingRequestContents(requestId);
	}
	
	@Override
	public TrainingPostscript getTrainingPostscriptDup(int trainingId){
		return (TrainingPostscript) trainingDao.getTrainingPostscriptDup(trainingId);
	}
	
	@Override
	public void deleteTrainingPostscript(int postId){
		trainingDao.deleteTrainingPostscript(postId);
	}
	
	@Override
	public int getTrainingReqDup(Map<String, Object> params){
		return trainingDao.getTrainingReqDup(params);
	}
	
	@Override
	public void insertTrainingReq(Map<String, Object> params){
		trainingDao.insertTrainingReq(params);
	}
}
