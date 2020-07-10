package kr.co.kumsung.thub.service;

import java.util.List;
import java.util.Map;

import kr.co.kumsung.thub.domain.Article;
import kr.co.kumsung.thub.domain.BoardCategory;
import kr.co.kumsung.thub.domain.BoardConfig;
import kr.co.kumsung.thub.domain.Book;
import kr.co.kumsung.thub.domain.Cd;
import kr.co.kumsung.thub.domain.Data;
import kr.co.kumsung.thub.domain.MainData;
import kr.co.kumsung.thub.domain.Headword;
import kr.co.kumsung.thub.domain.History;
import kr.co.kumsung.thub.domain.LegacyBook;
import kr.co.kumsung.thub.domain.Member;
import kr.co.kumsung.thub.domain.Pool;
import kr.co.kumsung.thub.domain.Unit;
import kr.co.kumsung.thub.util.ResultMap;

public interface LearningService {

	int getTotalManagerCount = 0;

	/**
	 * 총 도서 갯수를 반환한다.
	 * @return
	 */
	public int getTotalBooks(Map<String,Object> params);
	
	/**
	 * 도서 리스트를 반환한다.
	 * @param params
	 * @return
	 */
	public List<Book> getBookList(Map<String,Object> params);
	
	/**
	 * 카테고리에 속한 도서 리스트를 반환한다.
	 * @param params
	 * @return
	 */
	public List<Book> getBookListByCategory(String category, String course);
	
	/**
	 * 카테고리에 속한 도서 리스트를 반환한다.
	 * @param params
	 * @return
	 */
	public List<Book> getBookListByCategory(String category);
	
	
	/**
	 * 카테고리에 속한 도서 리스트를 반환한다.
	 * @param params
	 * @return
	 */
	public List<ResultMap> getCourseListByCategory(String category);
	
	
	/**
	 * 도서 정보를 가지고 온다.
	 * @param bookId
	 * @return
	 */
	public Book getBook(int bookId);
	
	/**
	 * 도서를 등록한다.
	 * @param params
	 * @return
	 */
	public int insertBook(Map<String,Object> params);
	
	/**
	 * 도서의 정보를 갱신한다.
	 * @param params
	 */
	public void updateBook(Map<String,Object> params);
	
	/**
	 * 자료의 갯수를 가지고 온다.
	 * @param params
	 * @return
	 */
	public int getTotalData(Map<String,Object> params);
	
	/**
	 * 자료의 리스트를 가지고 온다.
	 * @param params
	 * @return
	 */
	public List<Data> getDataList(Map<String,Object> params);
	
	/**
	 * 어드민 자료의 갯수를 가지고 온다.
	 * @param params
	 * @return
	 */
	public int getTotalDataAdmin(Map<String,Object> params);
	
	/**
	 * 어드민 자료의 리스트를 가지고 온다.
	 * @param params
	 * @return
	 */
	public List<Data> getDataListAdmin(Map<String,Object> params);	
	
	/**
	 * 자료를 가지고 온다.
	 * @param params
	 * @return
	 */
	public Data getData(Map<String,Object> params);
	
	/**
	 * 차시 자료를 가지고 온다.
	 * @param params
	 * @return
	 */
	public List<Data> getCDataView(Map<String,Object> params);	
	
	/**
	 * 차시첨부를 가지고 온다.
	 * @param params
	 * @return
	 */
	public List<Data> getCDataFile(Map<String,Object> params);
	
	/**
	 * 다운로드를 위한 자료를 가지고 온다.
	 * @param params
	 * @return
	 */
	public Data getDataForDownload(Map<String,Object> params);
	
	/**
	 * 자료를 등록한다.
	 * @param params
	 * @return
	 */
	public int insertData(Map<String,Object> params);
	
	/**
	 * 차시자료를 등록한다.
	 * @param params
	 * @return
	 */
	public void insertCData(Map<String,Object> params);

	/**
	 * 차시첨부자료를 등록한다.
	 * @param params
	 * @return
	 */
	public void insertCDataFile(Map<String,Object> params);
	
	/**
	 * 자료를 갱신한다.
	 * @param params
	 */
	public void updateData(Map<String,Object> params);
	
	/**
	 * 차시첨부자료를 갱신한다.
	 * @param params
	 */
	public void updateCDataFile(Map<String,Object> params);
	
	/**
	 * 자료를 삭제한다.
	 * @param params
	 */
	public void deleteData(Map<String,Object> params);
	
	/**
	 * 차시자료를 삭제한다.
	 * @param params
	 */
	public void deleteCData(Map<String,Object> params);
	
	/**
	 * 차시개별자료를 삭제한다.
	 * @param params
	 */
	public void delChasiViewEach(Map<String,Object> params);
	
	/**
	 * 차시개별파일을 삭제한다.
	 * @param params
	 */
	public void delChasiFileEach(Map<String,Object> params);
	
	/**
	 * CD 리스트를 가지고 온다.
	 * @param bookId
	 * @return
	 */
	public List<Cd> getCdList(int bookId);
	
	/**
	 * CD를 입력한다.
	 * @param params
	 * @return
	 */
	public int insertCd(Map<String,Object> params);
	
	/**
	 * CD 를 삭제한다.
	 * @param cdId
	 */
	public void deleteCd(int cdId);
	
	/**
	 * 기존 도서의 리스트 카운트를 가지고 온다.
	 * @param params
	 * @return
	 */
	public int getLegacyBookTotalCount(Map<String,Object> params);
	
	/**
	 * 기존 도서의 리스트 카운트를 가지고 온다.
	 * @param params
	 * @return
	 */
	public List<LegacyBook> getLegacyBookList(Map<String,Object> params);
	
	/**
	 * 공통 데이타의 갯수를 가지고 온다.
	 * @param params
	 * @return
	 */
	public int getCommonDataTotalCount(Map<String, Object> params);
	
	/**
	 * 도서의 공통 자료에 대한 카운트를 가지고 온다.
	 * @param params
	 * @return
	 */
	public List<ResultMap> getCommonDataCategoryList(Map<String, Object> params);
	
	/**
	 * 유형별 자료 카운트를 가지고 온다.
	 * @param params
	 * @return
	 */
	public List<ResultMap> getTypesDataCategoryList(Map<String,Object> params);
	
	
	/**
	 * 도서의 단원별 자료에 대한 카운트를 가지고 온다.
	 * @param params
	 * @return
	 */
	public List<ResultMap> getCommonDataSubCategoryList(Map<String, Object> params);
	
	/**
	 * 도서의 단원별 세부자료에 대한 카운트를 가지고 온다.
	 * @param params
	 * @return
	 */
	public List<ResultMap> getCommonDataSubCategorySList(Map<String, Object> params);
	
	/**
	 * 프론트에서 사용되는 자료 리스트를 가지고 온다.
	 * @param params
	 * @return
	 */
	public List<Data> getFrontDataList(Map<String, Object> params);
	
	/**
	 * 교수학습자료의 자료등록 권한이 있는지 체크한다.
	 * @param member
	 * @param category
	 * @return
	 */
	public boolean isAccessLearning(Member member , String category);
	
	/**
	 * 책 제목을 가져온다.
	 * @param bookId
	 * @return String
	 * */
	public String getBookName(int bookId);
	
	/**
	 * 공통자료 수업자료 리스트를 가져온다
	 * @param params
	 * @return List<Data>
	 * */
	public List<Data> getUnitDataList(Map<String, Object> params);
	
	/**
	 * 공통자료 단원별 수업자료 리스트를 가져온다
	 * @param params
	 * @return List<Data>
	 * */
	public List<Data> getUnitSubDataList(Map<String, Object> params);
	
	/**
	 * 공통자료 정렬 수정
	 * @param params
	 * @return void
	 * */
	public void updateUnitDataSeq(Map<String, Object> params);
	
	/**
	 * 공통자료 정렬 수정
	 * @param params
	 * @return void
	 * */
	public void updateSDataSeq(Map<String, Object> params);
	
	/**
	 * 기존 도서의 정보를 가지고 온다.
	 * @param legacyId
	 * @return
	 */
	public LegacyBook getLegacyBook(int legacyId);
	
	/**
	 * 교수학습자료 파일 다운로드시 Hits 증가.
	 * @param dataId
	 * @return
	 */
	public void updateFileHits(int dataId);
	/**
	 * 교수학습자료 파일 다운로드시 Hits 증가.
	 * @param dataId
	 * @return
	 */
	public void updateSFileHits(int sdataId);	
	/**
	 * 
	 * @param findCategory
	 * @return
	 */
	public List<Book> getBookListWithCd(String findCategory);
	
	/**
	 * CD 데이타를 받아온다.
	 * @param cdId
	 * @return
	 */
	public Cd getCd(int cdId);
	
	/**
	 * 다운로드 데이타 리스트를 가지고 온다.
	 * @param dataIds
	 * @return
	 */
	public List<Data> getDownloadDataList(String dataIds);
	
	/**
	 * 특화자료 다운로드 데이타 리스트를 가지고 온다.
	 * @param dataIds
	 * @return
	 */
	public List<Data> getDownloadSDataList(String dataIds);
	/**
	 * 검색어 저장
	 * @param params
	 */
	public void insertSearchWord(Map<String,Object> params);
	
	/**
	 * 유형별 데이타의 총 갯수
	 * @param params
	 * @return
	 */
	public int getDataListCount(Map<String,Object> params);
	
	/**
	 * 유형별 자료들의 카운트
	 * @param params
	 * @return
	 */
	public List<ResultMap> getTypesDataListCountGroupBy(Map<String,Object> params);
	
	/**
	 * 자료를 삭제한다.
	 * @param dataId
	 */
	public void deleteLearningData(int dataId);
	
	/**
	 * 도서를 삭제한다.
	 * @param bookId
	 */
	public void deleteLearningBook(int bookId);
	
	/**
	 * 메인화면 통합자료를 등록한다.
	 * @param params
	 * @return
	 */
	public int insertMainData(Map<String,Object> params);
	
	/**
	 * 메인화면 통합자료를 갱신한다.
	 * @param params
	 */
	public void updateMainData(Map<String,Object> params);
	/**
	 * 메인화면 통합자료를 가지고 온다.
	 * @param params
	 * @return
	 */
	public MainData getMainData(Map<String,Object> params);
	
	/**
	 * 메인화면 통합자료의 갯수를 가지고 온다.
	 * @param params
	 * @return
	 */
	public int getTotalMainData(Map<String,Object> params);
	
	/**
	 * 메인화면 통합자료의 리스트를 가지고 온다.
	 * @param params
	 * @return
	 */
	public List<MainData> getMainDataList(Map<String,Object> params);
	
	/**
	 * 메인화면 통합자료를 삭제한다.
	 * @param params
	 */
	public void deleteMainData(Map<String,Object> params);
	
	/**
	 * 어드민 특화자료의 갯수를 가지고 온다.
	 * @param params
	 * @return
	 */
	public int getTotalSDataAdmin(Map<String,Object> params);
	
	/**
	 * 어드민 특화자료의 리스트를 가지고 온다.
	 * @param params
	 * @return
	 */
	public List<Data> getSDataListAdmin(Map<String,Object> params);	
	/**
	 * 특화자료를 가지고 온다.
	 * @param params
	 * @return
	 */
	public Data getSData(Map<String,Object> params);
	/**
	 * 특화자료를 등록한다.
	 * @param params
	 * @return
	 */
	public int insertSData(Map<String,Object> params);
	/**
	 * 특화자료를 갱신한다.
	 * @param params
	 */
	public void updateSData(Map<String,Object> params);
	/**
	 * 특화자료를 삭제한다.
	 * @param params
	 */
	public void deleteSData(Map<String,Object> params);
	
	/**
	 * 차시자료 이전주소를 반환한다.
	 * @param params
	 * @return
	 */
	public String getPrevUrl(Map<String,Object> params);
	
	/**
	 * 차시 자료의 다운버튼 리스트를 가지고 온다.
	 * @param params
	 * @return
	 */
	public List<Data> getChasiDownBtn(Map<String,Object> params);
	
	/**
	 * 특화자료 카테고리 리스트를 반환한다.
	 * @param params
	 * @return
	 */
	public List<ResultMap> getSpecialDataCategoryList(String category);
	
	
	/**
	 * 공유 자료를 가지고 온다.
	 * @param params
	 * @return
	 */
	public List<Data> getShareDataList(Map<String,Object> params);
	
	public List<Data> getDataList_chasi(Map<String,Object> params);
	
	public boolean isAccessLearning_fgclass(Member member , String category);
	
	public List<Data> getDataList_multi(Map<String,Object> params);

	
	/**
	 * 임시 데이터 파일서버에서 직접
	 */
	public Data getBigData(int dataId);

	
}