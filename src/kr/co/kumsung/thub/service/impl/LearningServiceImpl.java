package kr.co.kumsung.thub.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import kr.co.kumsung.thub.dao.LearningDao;
import kr.co.kumsung.thub.domain.Book;
import kr.co.kumsung.thub.domain.Cd;
import kr.co.kumsung.thub.domain.Data;
import kr.co.kumsung.thub.domain.CData;
import kr.co.kumsung.thub.domain.MainData;
import kr.co.kumsung.thub.domain.LegacyBook;
import kr.co.kumsung.thub.domain.Member;
import kr.co.kumsung.thub.service.LearningService;
import kr.co.kumsung.thub.util.ResultMap;
import kr.co.kumsung.thub.util.Validate;

public class LearningServiceImpl implements LearningService{

	@Autowired
	private LearningDao learningDao;

	@Override
	public int getTotalBooks(Map<String,Object> params) {
		return learningDao.getTotalBooks(params);
	}

	@Override
	public List<Book> getBookList(Map<String, Object> params) {
		return learningDao.getBookList(params);
	}
	
	@Override
	public List<Book> getBookListByCategory(String category, String course) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("category" , category);
		params.put("course" , course);
		
		return learningDao.getBookListByCategory(params);
	}
	@Override
	public List<Book> getBookListByCategory(String category) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("category" , category);
		
		
		return learningDao.getBookListByCategory(params);
	}	
	
	@Override
	public List<ResultMap> getCourseListByCategory(String category) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("category" , category);
		
		
		return learningDao.getCourseListByCategory(params);
	}	

	@Override
	public Book getBook(int bookId) {
		return learningDao.getBook(bookId);
	}

	@Override
	public int insertBook(Map<String, Object> params) {		
		return learningDao.insertBook(params);
	}

	@Override
	public void updateBook(Map<String, Object> params) {
		learningDao.updateBook(params);
	}
	
	@Override
	public int getTotalData(Map<String,Object> params){
		return learningDao.getTotalData(params);
	}
	
	@Override
	public List<Data> getDataList(Map<String,Object> params){
		return (List<Data>) learningDao.getDataList(params);
	}
	
	@Override
	public int getTotalDataAdmin(Map<String,Object> params){
		return learningDao.getTotalDataAdmin(params);
	}
	
	@Override
	public List<Data> getDataListAdmin(Map<String,Object> params){
		return (List<Data>) learningDao.getDataListAdmin(params);
	}
	
	
	@Override
	public Data getData(Map<String,Object> params){
		return learningDao.getData(params);
	}
	
	@Override
	public List<Data> getCDataView(Map<String,Object> params){
		return (List<Data>) learningDao.getCDataView(params);
	}
	
	@Override
	public List<Data> getCDataFile(Map<String,Object> params){
		return (List<Data>) learningDao.getCDataFile(params);
	}
	
	
	@Override
	public int insertData(Map<String, Object> params) {
		
		// get max sequence
		int sequence = learningDao.getMaxSequence(params);
		params.put("sequence" , sequence);
		
		return learningDao.insertData(params);
	}

	@Override
	public void insertCData(Map<String, Object> params) {
		learningDao.insertCData(params);
	}
	
	@Override
	public void insertCDataFile(Map<String, Object> params) {
		learningDao.insertCDataFile(params);
	}
	
	@Override
	public void updateData(Map<String, Object> params) {
		learningDao.updateData(params);
	}
	
	@Override
	public void updateCDataFile(Map<String, Object> params) {
		learningDao.updateCDataFile(params);
	}

	@Override
	public void deleteData(Map<String, Object> params) {
		learningDao.deleteData(params);
	}

	@Override
	public void deleteCData(Map<String, Object> params) {
		learningDao.deleteCData(params);
	}
	
	@Override
	public void delChasiViewEach(Map<String, Object> params) {
		learningDao.delChasiViewEach(params);
	}
	
	@Override
	public void delChasiFileEach(Map<String, Object> params) {
		learningDao.delChasiFileEach(params);
	}
	
	@Override
	public List<Cd> getCdList(int bookId) {
		return learningDao.getCdList(bookId);
	}

	@Override
	public int insertCd(Map<String, Object> params) {
		return learningDao.insertCd(params);
	}

	@Override
	public void deleteCd(int cdId) {
		learningDao.deleteCd(cdId);
	}

	@Override
	public int getLegacyBookTotalCount(Map<String, Object> params) {
		return learningDao.getLegacyBookTotalCount(params);
	}

	@Override
	public List<LegacyBook> getLegacyBookList(Map<String, Object> params) {
		return learningDao.getLegacyBookList(params);
	}

	@Override
	public int getCommonDataTotalCount(Map<String, Object> params) {
		return learningDao.getCommonDataTotalCount(params);
	}
	
	@Override
	public List<ResultMap> getCommonDataCategoryList(Map<String, Object> params) {
		return learningDao.getCommonDataCategoryList(params);
	}
	
	@Override
	public List<ResultMap> getCommonDataSubCategoryList(Map<String, Object> params) {
		return learningDao.getCommonDataSubCategoryList(params);
	}
	
	@Override
	public List<ResultMap> getCommonDataSubCategorySList(Map<String, Object> params) {
		return learningDao.getCommonDataSubCategorySList(params);
	}

	@Override
	public List<Data> getFrontDataList(Map<String, Object> params) {
		return learningDao.getFrontDataList(params);
	}

	@Override
    public boolean isAccessLearning(Member member, String category)
    {
        if(member == null || Validate.isEmpty(member.getAuthType()))
            return false;
        if(member.getAuthType().equals("S"))
            return true;
        if(!Validate.isEmpty(member.getLearningAuth()))
        {
            String auths[] = member.getLearningAuth().split("\\,");
            String as[];
            int j = (as = auths).length;
            for(int i = 0; i < j; i++)
            {
                String auth = as[i];
                if(auth.indexOf(category) == 0)
                    return true;
            }
        }
        return false;
    }	
/*	
	public boolean isAccessLearning(Member member, String category) {

		if( member == null || Validate.isEmpty(member.getAuthType()) )
			return false;
		
		if( member.getAuthType().equals("S") )
			return true;
		else
		{
			if( !Validate.isEmpty(member.getLearningAuth()))
			{
				String[] auths = member.getLearningAuth().split("\\,");
				
				for(String auth : auths)
				{
					if( auth.indexOf(category) == 0 )
						return true;
				}
			}
		}
		
		return false;
	}
*/	
	@Override
	public String getBookName(int bookId){
		return learningDao.getBookName(bookId);
	}
	
	@Override
	public List<Data> getUnitDataList(Map<String, Object> params){
		params.put("dataFlag", "U");
		return (List<Data>) learningDao.getUnitDataList(params);
	}
	
	@Override
	public List<Data> getUnitSubDataList(Map<String, Object> params){
		return (List<Data>) learningDao.getUnitSubDataList(params);
	}
	
	@Override
	public void updateUnitDataSeq(Map<String, Object> params){
		learningDao.updateUnitDataSeq(params);
	}
	@Override
	public void updateSDataSeq(Map<String, Object> params){
		learningDao.updateSDataSeq(params);
	}

	@Override
	public LegacyBook getLegacyBook(int legacyId) {
		return learningDao.getLegacyBook(legacyId);
	}
	
	@Override
	public void updateFileHits(int dataId){
		learningDao.updateFileHits(dataId);
	}
	@Override
	public void updateSFileHits(int sdataId){
		learningDao.updateSFileHits(sdataId);
	}
	@Override
	public List<Book> getBookListWithCd(String findCategory) {
		return learningDao.getBookListWithCd(findCategory);
	}

	@Override
	public Cd getCd(int cdId) {
		return learningDao.getCd(cdId);
	}

	@Override
	public List<ResultMap> getTypesDataCategoryList(Map<String, Object> params) {
		return (List<ResultMap>) learningDao.getTypesDataCategoryList(params);
	}

	@Override
	public List<Data> getDownloadDataList(String dataIds) {
		return learningDao.getDownloadDataList(dataIds);
	}
	
	@Override
	public List<Data> getDownloadSDataList(String dataIds) {
		return learningDao.getDownloadSDataList(dataIds);
	}

	@Override
	public void insertSearchWord(Map<String, Object> params) {
		learningDao.insertSearchWord(params);
	}

	@Override
	public int getDataListCount(Map<String, Object> params) {
		return learningDao.getDataListCount(params);
	}

	@Override
	public List<ResultMap> getTypesDataListCountGroupBy(
			Map<String, Object> params) {
		return learningDao.getTypesDataListCountGroupBy(params);
	}

	@Override
	public void deleteLearningData(int dataId) {
		learningDao.deleteLearningData(dataId);
	}

	@Override
	public Data getDataForDownload(Map<String, Object> params) {
		return learningDao.getDataForDownload(params);
	}

	@Override
	public void deleteLearningBook(int bookId) {
		learningDao.deleteLearningBook(bookId);
	}
	
	@Override
	public int insertMainData(Map<String, Object> params) {
		return learningDao.insertMainData(params);
	}
	
	@Override
	public void updateMainData(Map<String, Object> params) {
		learningDao.updateMainData(params);
	}
	
	@Override
	public MainData getMainData(Map<String,Object> params){
		return learningDao.getMainData(params);
	}

	@Override
	public int getTotalMainData(Map<String,Object> params){
		return learningDao.getTotalMainData(params);
	}

	@Override
	public List<MainData> getMainDataList(Map<String,Object> params){
		return (List<MainData>) learningDao.getMainDataList(params);
	}
	
	@Override
	public void deleteMainData(Map<String, Object> params) {
		learningDao.deleteMainData(params);
	}
	
	@Override
	public int getTotalSDataAdmin(Map<String,Object> params){
		return learningDao.getTotalSDataAdmin(params);
	}
	@Override
	public List<Data> getSDataListAdmin(Map<String,Object> params){
		return (List<Data>) learningDao.getSDataListAdmin(params);
	}
	@Override
	public Data getSData(Map<String,Object> params){
		return learningDao.getSData(params);
	}
	@Override
	public int insertSData(Map<String, Object> params) {
		
		// get max sequence
		//int sequence = learningDao.getMaxSequence(params);
		int sequence = learningDao.getSpcMaxSequence(params);
		params.put("sequence" , sequence);
		
		return learningDao.insertSData(params);
	}
	@Override
	public void updateSData(Map<String, Object> params) {
		learningDao.updateSData(params);
	}
	@Override
	public void deleteSData(Map<String, Object> params) {
		learningDao.deleteSData(params);
	}
	
	@Override
	public String getPrevUrl(Map<String,Object> params){
		return learningDao.getPrevUrl(params);
	}
	@Override
	public List<Data> getChasiDownBtn(Map<String, Object> params) {
		return learningDao.getChasiDownBtn(params);
	}
	/**
	 * �듅�솕�옄猷� 移댄뀒怨좊━ 由ъ뒪�듃瑜� 諛섑솚�븳�떎.
	 * @param params
	 * @return
	 */
	public List<ResultMap> getSpecialDataCategoryList(String category){
		return learningDao.getSpecialDataCategoryList(category);
	}	
	@Override
	public List<Data> getShareDataList(Map<String, Object> params) {
		return (List<Data>) learningDao.getShareDataList(params);
	}
	
	@Override
	public List<Data> getDataList_chasi(Map<String,Object> params){
		return (List<Data>) learningDao.getDataList_chasi(params);
	}
	
	@Override
	public boolean isAccessLearning_fgclass(Member member, String category) {

		if( member == null || Validate.isEmpty(member.getFgclass()) )
			return false;
		
		if( member.getFgclass().equals("11") )
			return true;
		else
		{
			if( !Validate.isEmpty(member.getLearningAuth()))
			{
				String[] auths = member.getLearningAuth().split("\\,");
				
				for(String auth : auths)
				{
					if( auth.indexOf(category) == 0 )
						return true;
				}
			}
		}
		
		return false;
	}
	@Override
	public List<Data> getDataList_multi(Map<String,Object> params){
		return (List<Data>) learningDao.getDataList_multi(params);
	}
	
	@Override
	public Data getBigData(int dataId){
		return learningDao.getBigData(dataId);
	}
	
}
