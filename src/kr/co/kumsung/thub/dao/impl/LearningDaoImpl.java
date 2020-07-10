package kr.co.kumsung.thub.dao.impl;

import java.util.List;
import java.util.Map;

import kr.co.kumsung.thub.dao.LearningDao;
import kr.co.kumsung.thub.domain.Book;
import kr.co.kumsung.thub.domain.Cd;
import kr.co.kumsung.thub.domain.Data;
import kr.co.kumsung.thub.domain.CData;
import kr.co.kumsung.thub.domain.MainData;
import kr.co.kumsung.thub.domain.LegacyBook;
import kr.co.kumsung.thub.util.ResultMap;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class LearningDaoImpl extends SqlMapClientDaoSupport implements LearningDao {

	@Override
	public int getTotalBooks(Map<String, Object> params) {
		return (Integer) getSqlMapClientTemplate().queryForObject("Learning.getTotalBooks", params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Book> getBookList(Map<String, Object> params) {
		return (List<Book>) getSqlMapClientTemplate().queryForList("Learning.getBookList", params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Book> getBookListByCategory(Map<String, Object> params) {
		return (List<Book>) getSqlMapClientTemplate().queryForList("Learning.getBookListByCategory", params);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<ResultMap> getCourseListByCategory(Map<String, Object> params) {
		return (List<ResultMap>) getSqlMapClientTemplate().queryForList("Learning.getCourseListByCategory", params);
	}	

	@Override
	public Book getBook(int bookId) {
		return (Book) getSqlMapClientTemplate().queryForObject("Learning.getBook", bookId);
	}

	@Override
	public int insertBook(Map<String, Object> params) {
		return (Integer) getSqlMapClientTemplate().insert("Learning.insertBook", params);
	}

	@Override
	public void updateBook(Map<String, Object> params) {
		getSqlMapClientTemplate().update("Learning.updateBook", params);
	}

	@Override
	public int getTotalData(Map<String, Object> params) { 
		return (Integer) getSqlMapClientTemplate().queryForObject("Learning.getTotalData", params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Data> getDataList(Map<String, Object> params) {
		return (List<Data>) getSqlMapClientTemplate().queryForList("Learning.getDataList", params);
	}

	@Override
	public int getTotalDataAdmin(Map<String, Object> params) {
		return (Integer) getSqlMapClientTemplate().queryForObject("Learning.getDataListCountAdmin", params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Data> getDataListAdmin(Map<String, Object> params) {
		return (List<Data>) getSqlMapClientTemplate().queryForList("Learning.getDataListAdmin", params);
	}
	
	@Override
	public Data getData(Map<String, Object> params) {
		getSqlMapClientTemplate().update("Learning.updateLearningHits", params); // 180611 hits 추가
		return (Data) getSqlMapClientTemplate().queryForObject("Learning.getData", params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Data> getCDataView(Map<String, Object> params) {
		return (List<Data>) getSqlMapClientTemplate().queryForList("Learning.getCDataView", params);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Data> getCDataFile(Map<String, Object> params) {
		return (List<Data>) getSqlMapClientTemplate().queryForList("Learning.getCDataFile", params);
	}
	
	@Override
	public int getMaxSequence(Map<String, Object> params) {
		return (Integer) getSqlMapClientTemplate().queryForObject("Learning.getMaxSequence", params);
	}

	@Override
	public int insertData(Map<String, Object> params) {
		return (Integer) getSqlMapClientTemplate().insert("Learning.insertData", params);
	}

	@Override
	public void insertCData(Map<String, Object> params) {
		getSqlMapClientTemplate().insert("Learning.insertCData", params); 		
	}
	
	@Override
	public void insertCDataFile(Map<String, Object> params) {
		getSqlMapClientTemplate().insert("Learning.insertCDataFile", params); 		
	}
	
	@Override
	public void updateData(Map<String, Object> params) {
		getSqlMapClientTemplate().update("Learning.updateData", params);
	}

	@Override
	public void updateCDataFile(Map<String, Object> params) {
		getSqlMapClientTemplate().update("Learning.updateCDataFile", params);
	}
	
	@Override
	public void deleteData(Map<String, Object> params) {
		getSqlMapClientTemplate().delete("Learning.deleteData", params);
	}
	
	@Override
	public void deleteCData(Map<String, Object> params) {
		getSqlMapClientTemplate().delete("Learning.deleteCData", params);
	}	
	
	@Override
	public void delChasiViewEach(Map<String,Object> params){
		getSqlMapClientTemplate().delete("Learning.delChasiViewEach", params);
	}	
	
	@Override
	public void delChasiFileEach(Map<String,Object> params){
		getSqlMapClientTemplate().delete("Learning.delChasiFileEach", params);
	}	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Cd> getCdList(int bookId) {
		return (List<Cd>) getSqlMapClientTemplate().queryForList("Learning.getCdList", bookId);
	}

	@Override
	public int insertCd(Map<String, Object> params) {
		return (Integer) getSqlMapClientTemplate().insert("Learning.insertCd",params);
	}

	@Override
	public void deleteCd(int cdId) {
		getSqlMapClientTemplate().delete("Learning.deleteCd", cdId);
	}

	@Override
	public int getLegacyBookTotalCount(Map<String, Object> params) {
		return (Integer) getSqlMapClientTemplate().queryForObject("Learning.getLegacyBookTotalCount", params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LegacyBook> getLegacyBookList(Map<String, Object> params) {
		return (List<LegacyBook>) getSqlMapClientTemplate().queryForList("Learning.getLegacyBookList", params);
	}

	@Override
	public int getCommonDataTotalCount(Map<String, Object> params) {
		return (Integer) getSqlMapClientTemplate().queryForObject("Learning.getCommonDataTotalCount", params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ResultMap> getCommonDataCategoryList(Map<String, Object> params) {
		return (List<ResultMap>) getSqlMapClientTemplate().queryForList("Learning.getCommonDataCategoryList", params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ResultMap> getCommonDataSubCategoryList(Map<String, Object> params) {
		return (List<ResultMap>) getSqlMapClientTemplate().queryForList("Learning.getCommonDataSubCategoryList", params);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ResultMap> getCommonDataSubCategorySList(Map<String, Object> params) {
		return (List<ResultMap>) getSqlMapClientTemplate().queryForList("Learning.getCommonDataSubCategorySList", params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Data> getFrontDataList(Map<String, Object> params) {
		System.out.println("getFrontDataList1 === ");
		return (List<Data>) getSqlMapClientTemplate().queryForList("Learning.getFrontDataList", params);
	}

	@Override
	public String getBookName(int bookId) {
		return (String) getSqlMapClientTemplate().queryForObject("Learning.getBookName", bookId);
	}

	@Override
	public List<Data> getUnitDataList(Map<String, Object> params) {
		System.out.println("getFrontDataList2 === ");
		return (List<Data>) getSqlMapClientTemplate().queryForList("Learning.getFrontDataList", params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Data> getUnitSubDataList(Map<String, Object> params) {
		return (List<Data>) getSqlMapClientTemplate().queryForList("Learning.getUnitSubDataList", params);
	}

	@Override
	public void updateUnitDataSeq(Map<String, Object> params) {
		getSqlMapClientTemplate().update("Learning.updateUnitDataSeq", params);
	}
	@Override
	public void updateSDataSeq(Map<String, Object> params) {
		getSqlMapClientTemplate().update("Learning.updateSDataSeq", params);
	}

	@Override
	public LegacyBook getLegacyBook(int legacyId) {
		return (LegacyBook) getSqlMapClientTemplate().queryForObject(
				"Learning.getLegacyBook", legacyId);
	}

	@Override
	public void updateFileHits(int dataId) {
		getSqlMapClientTemplate().update("Learning.updateFileHits", dataId);
	}
	
	@Override
	public void updateSFileHits(int sdataId) {
		getSqlMapClientTemplate().update("Learning.updateSFileHits", sdataId);
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Book> getBookListWithCd(String findCategory){
		return (List<Book>) getSqlMapClientTemplate().queryForList("Learning.getBookListWithCd", findCategory);
	}

	@Override
	public Cd getCd(int cdId) {
		return (Cd) getSqlMapClientTemplate().queryForObject("Learning.getCd", cdId);
	}
	
	@Override
	public Data getBigData(int dataId) {
		return (Data)  getSqlMapClientTemplate().queryForObject("Learning.getBigData", dataId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ResultMap> getTypesDataCategoryList(Map<String, Object> params) {
		return (List<ResultMap>) getSqlMapClientTemplate().queryForList("Learning.getTypesDataCategoryList", params);
	}
	
	@SuppressWarnings({ "unchecked" })
	@Override
	public List<Data> getDownloadDataList(String dataIds) {
		return (List<Data>) getSqlMapClientTemplate().queryForList("Learning.getDownloadDataList" , dataIds);
	}
	
	@SuppressWarnings({ "unchecked" })
	@Override
	public List<Data> getDownloadSDataList(String dataIds) {
		return (List<Data>) getSqlMapClientTemplate().queryForList("Learning.getDownloadSDataList" , dataIds);
	}

	@Override
	public void insertSearchWord(Map<String, Object> params) {
		getSqlMapClientTemplate().insert("Learning.insertSearchWord" , params);
	}

	@Override
	public int getDataListCount(Map<String, Object> params) {
		return (Integer) getSqlMapClientTemplate().queryForObject("Learning.getDataListCount", params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ResultMap> getTypesDataListCountGroupBy(
			Map<String, Object> params) {
		return null;
	}

	@Override
	public void deleteLearningData(int dataId) {
		getSqlMapClientTemplate().delete("Learning.deleteLearningData" , dataId);
	}

	@Override
	public Data getDataForDownload(Map<String, Object> params) {
		return (Data) getSqlMapClientTemplate().queryForObject("Learning.getDataForDownload", params);
	}

	@Override
	public void deleteLearningBook(int bookId) {
		getSqlMapClientTemplate().delete("Learning.deleteLearningBook" , bookId);
	}
	
	@Override
	public int insertMainData(Map<String, Object> params) {
		return (Integer) getSqlMapClientTemplate().insert(
				"Learning.insertMainData", params);
	}
	@Override
	public void updateMainData(Map<String, Object> params) {
		getSqlMapClientTemplate().update("Learning.updateMainData", params);
	}
	
	@Override
	public MainData getMainData(Map<String, Object> params) {
		return (MainData) getSqlMapClientTemplate().queryForObject("Learning.getMainData", params);
	}
	
	@Override
	public int getTotalMainData(Map<String, Object> params) { 
		return (Integer) getSqlMapClientTemplate().queryForObject("Learning.getTotalMainData", params);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MainData> getMainDataList(Map<String, Object> params) {
		return (List<MainData>) getSqlMapClientTemplate().queryForList("Learning.getMainDataList", params);
	}
	
	@Override
	public void deleteMainData(Map<String, Object> params) {
		getSqlMapClientTemplate().delete("Learning.deleteMainData", params);
	}
	
	@Override
	public int getTotalSDataAdmin(Map<String, Object> params) {
		return (Integer) getSqlMapClientTemplate().queryForObject("Learning.getSDataListCountAdmin", params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Data> getSDataListAdmin(Map<String, Object> params) {
		return (List<Data>) getSqlMapClientTemplate().queryForList("Learning.getSDataListAdmin", params);
	}
	@Override
	public Data getSData(Map<String, Object> params) {
		return (Data) getSqlMapClientTemplate().queryForObject("Learning.getSData", params);
	}
	@Override
	public int insertSData(Map<String, Object> params) {
		return (Integer) getSqlMapClientTemplate().insert("Learning.insertSData", params);
	}
	@Override
	public void updateSData(Map<String, Object> params) {
		getSqlMapClientTemplate().update("Learning.updateSData", params);
	}
	@Override
	public void deleteSData(Map<String, Object> params) {
		getSqlMapClientTemplate().delete("Learning.deleteSData", params);
	}
	@Override
	public String getPrevUrl(Map<String, Object> params) {
		return (String) getSqlMapClientTemplate().queryForObject("Learning.getPrevUrl", params);
	}	
	@SuppressWarnings("unchecked")
	@Override
	public List<Data> getChasiDownBtn(Map<String, Object> params) {
		return (List<Data>) getSqlMapClientTemplate().queryForList("Learning.getChasiDownBtn", params);
	}	
	@SuppressWarnings("unchecked")
	@Override
	public List<ResultMap> getSpecialDataCategoryList(String category) {
		return (List<ResultMap>) getSqlMapClientTemplate().queryForList("Learning.getSpecialDataCategoryList", category);
	}		
	@SuppressWarnings("unchecked")
	@Override
	public List<Data> getShareDataList(Map<String, Object> params) {
		return (List<Data>) getSqlMapClientTemplate().queryForList("Learning.getShareDataList", params);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Data> getDataList_chasi(Map<String, Object> params) {
		return (List<Data>) getSqlMapClientTemplate().queryForList("Learning.getDataList_chasi", params);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Data> getDataList_multi(Map<String, Object> params) {
		return (List<Data>) getSqlMapClientTemplate().queryForList("Learning.getDataList_multi", params);
	}
	
	@Override
	public int getSpcMaxSequence(Map<String, Object> params) {
		return (Integer) getSqlMapClientTemplate().queryForObject("Learning.getSpcMaxSequence", params);
	}
	
}