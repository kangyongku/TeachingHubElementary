package kr.co.kumsung.thub.service;

import java.util.List;
import java.util.Map;

import kr.co.kumsung.thub.domain.FileInfo;
import kr.co.kumsung.thub.domain.Multimedia;
import kr.co.kumsung.thub.domain.Training;
import kr.co.kumsung.thub.domain.TrainingPostscript;
import kr.co.kumsung.thub.domain.TrainingRequest;


public interface TrainingService {
	
	/**
	 * 교사 연수지원 리스트
	 * @param params
	 * @return List<Training>
	 */
	public List<Training> getTrainingList(Map<String, Object> params);
	
	/**
	 * 교사 연수지원 리스트 개수
	 * @param params
	 * @return int
	 */
	public int getTrainingTotalList(Map<String, Object> params);
	
	/**
	 * 교사 연수지원 입력
	 * @param params
	 * @return 
	 */	
	public void insertTraining(Map<String, Object> params);
	
	/**
	 * 교사 연수지원 상세보기
	 * @param trainingId
	 * @return Training
	 */
	public Training getTrainingDetail(int trainingId);
	
	/**
	 * 교사 연수지원 수정
	 * @param params
	 * @return
	 */
	public void updateTraining(Map<String, Object> params);
	
	/**
	 * 교사 연수지원 후기 리스트
	 * @param params
	 * @return List<TrainingRequest>
	 */
	public List<TrainingRequest> getTrainingReqList(Map<String, Object> params);
	
	/**
	 * 교사 연수지원  후기  리스트
	 * @param trainingId
	 * @return int
	 */
	public int getRequestTrainingTotal(int trainingId);
	
	/**
	 * 교사 연수지원 엑셀 다운로드용 제목 가져오기
	 * @param trainingId
	 * @return String
	 */
	public String getTitleName(int trainingId);
	
	/**
	 * 교사 연수지원 삭제
	 * @param trainingId
	 * @return
	 */
	public void deleteTraining(int trainingId);
	
	
	/**
	 * 현장스케치 리스트
	 * @param params
	 * @return List<TrainingPostscript>
	 */
	public List<TrainingPostscript> getTrainingPostscriptList(Map<String, Object> params);
	
	/**
	 * 현장스케치 리스트 개수
	 * @param params
	 * @return int
	 */
	public int getTrainingPostscriptTotalList(Map<String, Object> params);
	
	/**
	 * 현장스케치 상세보기
	 * @param postId
	 * @return TrainingPostscript
	 */
	public TrainingPostscript getTrainingPostscriptDetail(int postId);
	
	/**
	 * 현장스케치 - 교사 연수 지원 등록된 ID 리스트
	 * @param 
	 * @return List<Training>
	 */
	public List<Training> getTrainingIdList();
	
	/**
	 * 현장스케치 등록
	 * @param params
	 * @return 
	 */
	public void insertTrainingPostscript(Map<String, Object> params);
	
	/**
	 * 현장스케치 수정
	 * @param params
	 * @return 
	 */
	public void updateTrainingPostscript(Map<String, Object> params);
	
	/**
	 * 현장스케치 보기 팝업창
	 * @param requestId
	 * @return String
	 */
	public String getTraiingRequestContents(int requestId);
	
	/**
	 * 현장스케치 - 교사 연수 ID 중복 체크
	 * @param trainingId
	 * @return TrainingPostscript
	 */
	public TrainingPostscript getTrainingPostscriptDup(int trainingId);
	
	/**
	 * 현장스케치 삭제
	 * @param postId
	 * @return 
	 */
	public void deleteTrainingPostscript(int postId);
	
	/**
	 * 교사연수지원 신청하기 중복체크
	 * @param params
	 * @return int
	 */
	public int getTrainingReqDup(Map<String, Object> params);
	
	/**
	 * 교사연수지원 신청 입력
	 * @param params
	 * @return 
	 */
	public void insertTrainingReq(Map<String, Object> params);
	
	
}
