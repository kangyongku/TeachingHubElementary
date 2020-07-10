package kr.co.kumsung.thub.service.impl;

import java.util.List;
import java.util.Map;

import kr.co.kumsung.thub.dao.MultimediaDao;
import kr.co.kumsung.thub.domain.Multimedia;
import kr.co.kumsung.thub.service.MultimediaService;
import kr.co.kumsung.thub.setting.Constants;

import org.springframework.beans.factory.annotation.Autowired;

public class MultimediaServiceImpl implements MultimediaService {

	@Autowired
	private MultimediaDao multimediaDao;

	@Override
	public Multimedia getMultimediaDetail(int mmId) {
		return (Multimedia) multimediaDao.getMultimediaDetail(mmId);
	}

	@Override
	public int insertMultimedia(Map<String, Object> params) {
		//웹링크시 Data 수정
		String masterCategory = (String)params.get("masterCategory");
		if(masterCategory.equals("E005") || masterCategory.equals("E002")){
			params.put("fileName", params.get("linkUrl"));
			params.put("dataFile", params.get("linkUrl"));
		}
		return (Integer) multimediaDao.insertMultimedia(params);
	}

	@Override
	public List<Multimedia> getMultimediaList(Map<String, Object> parmas) {
		return (List<Multimedia>) multimediaDao.getMultimediaList(parmas);
	}

	@Override
	public int getTotalMultimedia(Map<String, Object> params) {
		return (Integer) multimediaDao.getTotalMultimedia(params);
	}

	@Override
	public void updateMultimedia(Map<String, Object> params) {
		//웹링크시 Data 수정
		String category = (String)params.get("category");
		
		if( category.indexOf("E002") >= 0 || category.indexOf("E005") >= 0){
			params.put("fileName", params.get("linkUrl"));
			params.put("dataFile", params.get("linkUrl"));
		}
		
		multimediaDao.updateMultimedia(params);
	}

	@Override
	public String getDeleteTarget(Map<String, Object> params) {
		return (String) multimediaDao.getDeleteTarget(params);
	}
	
	@Override
	public void multimediaDelete(int param){
		multimediaDao.multimediaDelete(param);
	}

	@Override
	public String getMasterCategoryName(String findMaster) {
		
		if( findMaster.equals(Constants.MULTIMEDIA_FLASH_CATEGORY))
			return "플래시자료";
		else if( findMaster.equals(Constants.MULTIMEDIA_IMAGE_CATEGORY))
			return "이미지자료";	
		else if( findMaster.equals(Constants.MULTIMEDIA_MOVIE_CATEGORY))
			return "동영상자료";
		
		return "";
	}
	
	@Override
	public boolean isAccessMultimedia(String findMaster) {
		if( findMaster.equals(Constants.MULTIMEDIA_FLASH_CATEGORY)
				|| findMaster.equals(Constants.MULTIMEDIA_IMAGE_CATEGORY)
				|| findMaster.equals(Constants.MULTIMEDIA_MOVIE_CATEGORY))
			return true;
		
		return false;
	}

	@Override
	public Multimedia getPrevMultimedia(Map<String, Object> params) {
		return multimediaDao.getPrevMultimedia(params);
	}

	@Override
	public Multimedia getNextMultimedia(Map<String, Object> params) {
		return multimediaDao.getNextMultimedia(params);
	}
	
	@Override
	public int getDataRefCount(int param) {
		return (Integer) multimediaDao.getDataRefCount(param);
	}
}
