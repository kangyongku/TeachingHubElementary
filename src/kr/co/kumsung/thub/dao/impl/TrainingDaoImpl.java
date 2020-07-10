package kr.co.kumsung.thub.dao.impl;

import java.util.List;
import java.util.Map;

import kr.co.kumsung.thub.dao.TrainingDao;
import kr.co.kumsung.thub.domain.Training;
import kr.co.kumsung.thub.domain.TrainingPostscript;
import kr.co.kumsung.thub.domain.TrainingRequest;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class TrainingDaoImpl extends SqlMapClientDaoSupport implements TrainingDao{
	//교사연수지원
	public List<Training> getTrainingList(Map<String, Object> params) {
		return (List<Training>) getSqlMapClientTemplate().queryForList("Training.getTrainingList" , params);
	}
	
	public void insertTraining(Map<String, Object> params){
		getSqlMapClientTemplate().insert("Training.insertTraining", params);
	}
	
	public Training getTrainingDetail(int trainingId){
		return (Training)getSqlMapClientTemplate().queryForObject("Training.getTrainingDetail", trainingId);
	}
	
	public void updateTraining(Map<String, Object> params){
		getSqlMapClientTemplate().update("Training.updateTraining", params);
	}
	
	public int getTrainingTotalList(Map<String, Object> params){
		return (Integer) getSqlMapClientTemplate().queryForObject("Training.getTrainingTotalList", params);
	}
	
	public List<TrainingRequest> getTrainingReqList(Map<String, Object> params){
		return (List<TrainingRequest>) getSqlMapClientTemplate().queryForList("Training.getTrainingReqList", params);
	}
	
	public int getRequestTrainingTotal(int trainingId){
		return (Integer) getSqlMapClientTemplate().queryForObject("Training.getRequestTrainingTotal", trainingId);
	}
	
	public String getTitleName(int trainingId){
		return (String) getSqlMapClientTemplate().queryForObject("Training.getTitleName", trainingId);
	}
	
	public void deleteTraining(int trainingId){
		getSqlMapClientTemplate().delete("Training.deleteTraining", trainingId);
	}

	//현장스케치
	public List<TrainingPostscript> getTrainingPostscriptList(Map<String, Object> params){
		return (List<TrainingPostscript>) getSqlMapClientTemplate().queryForList("Training.getTrainingPostscriptList", params);
	}
	
	public int getTrainingPostscriptTotalList(Map<String, Object> params){
		return (Integer) getSqlMapClientTemplate().queryForObject("Training.getTrainingPostscriptTotalList", params);
	}
	
	public TrainingPostscript getTrainingPostscriptDetail(int postId){
		return (TrainingPostscript) getSqlMapClientTemplate().queryForObject("Training.getTrainingPostscriptDetail", postId);
	}
	
	public List<Training> getTrainingIdList(){
		return (List<Training>) getSqlMapClientTemplate().queryForList("Training.getTrainingIdList");
	}
	
	public void insertTrainingPostscript(Map<String, Object> params){
		getSqlMapClientTemplate().insert("Training.insertTrainingPostscript", params);
	}
	
	public void updateTrainingPostscript(Map<String, Object> params){
		getSqlMapClientTemplate().update("Training.updateTrainingPostscript", params);
	}
	
	public String getTraiingRequestContents(int requestId){
		return (String)getSqlMapClientTemplate().queryForObject("Training.getTraiingRequestContents", requestId);
	}
	
	public TrainingPostscript getTrainingPostscriptDup(int trainingId){
		return (TrainingPostscript)getSqlMapClientTemplate().queryForObject("Training.getTrainingPostscriptDup", trainingId);
	}
	
	public void deleteTrainingPostscript(int postId){
		getSqlMapClientTemplate().delete("Training.deleteTrainingPostscript", postId);
	}
	
	public int getTrainingReqDup(Map<String, Object> params){
		return (Integer) getSqlMapClientTemplate().queryForObject("Training.getTrainingReqDup", params);
	}
	
	public void insertTrainingReq(Map<String, Object> params){
		getSqlMapClientTemplate().insert("Training.insertTrainingReq", params);
	}
}
