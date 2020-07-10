package kr.co.kumsung.thub.domain;
import java.io.Serializable;

public class CommonParamVo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 페이지 번호
	 */
	private String 	pageNo;
	
	/**
	 * 현재 페이지 번호
	 */
	private String	currentPage;
	
	/**
	 * 현재 페이지 번호 
	 */
	private int		currentPageNum;
	
	/**
	 * 한페이지에 보여줄 리스트 갯수
	 */
	private int 	pageSize = 10;
	
	/**
	 * 페이징 범위 << 1 2 3 4 >>
	 */
	private int 	pageListSize = 10;
	
	/**
	 * 게시물 총 카운트
	 */
	private int 	totalCount;
	
	/**
	 * 게시물 총 페이지 카운트
	 */
	private int 	totalPageCount;
	
	/**
	 * 한 페이징의 시작 페이지
	 */
	private int		startPage = 0;
	
	/**
	 * 한페이징의 마지막 페이지
	 */
	private int		endPage = 0;
	
	/**
	 * 한 페이징의 시작 페이지
	 */
	private int		startRow = 0;
	
	/**
	 * 한페이징의 마지막 페이지
	 */
	private int		endRow = 0;

	/**
	 * 검색 필드명
	 */
	private String	searchField;
	
	/**
	 * 검색어
	 */
	private String	searchString;

	/**
	 * 검색 시작일
	 */
	private String searchFromDate;
	
	/**
	 * 검색 마지막일
	 */
	private String searchToDate;
	
	/**
	 * 리스크 체크박스
	 */
	private String[] arrCheckBox;
	
	private	String	regDt;
	
	private String	regId;
	
	private String	regNm;
	
	private	String	udtDt;
	
	private String	udtId;
	
	private String	udtNm;
	
	public String getPageNo() {
		return pageNo;
	}

	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}

	public String getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
		this.currentPageNum = Integer.parseInt(currentPage);
	}

	public int getCurrentPageNum() {
		return currentPageNum;
	}

	public void setCurrentPageNum(int currentPageNum) {
		this.currentPageNum = currentPageNum;
	}
	
	public int getSkipPageNum(){
		return (currentPageNum - 1) * pageSize;
	}
	
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageListSize() {
		return pageListSize;
	}

	public void setPageListSize(int pageListSize) {
		this.pageListSize = pageListSize;
	}

	public int getTotalPageCount() {
		return totalPageCount;
	}

	public void setTotalPageCount(int totalPageCount) {
		this.totalPageCount = totalPageCount;
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public String getSearchField() {
		return searchField;
	}

	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public String getSearchFromDate() {
		return searchFromDate;
	}

	public void setSearchFromDate(String searchFromDate) {
		this.searchFromDate = searchFromDate;
	}

	public String getSearchToDate() {
		return searchToDate;
	}

	public void setSearchToDate(String searchToDate) {
		this.searchToDate = searchToDate;
	}

	public String[] getArrCheckBox() {
		return arrCheckBox;
	}

	public void setArrCheckBox(String[] arrCheckBox) {
		this.arrCheckBox = arrCheckBox;
	}

	public String getRegDt() {
		return regDt;
	}

	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}

	public String getRegId() {
		return regId;
	}

	public void setRegId(String regId) {
		this.regId = regId;
	}

	public String getRegNm() {
		return regNm;
	}

	public void setRegNm(String regNm) {
		this.regNm = regNm;
	}

	public String getUdtDt() {
		return udtDt;
	}

	public void setUdtDt(String udtDt) {
		this.udtDt = udtDt;
	}

	public String getUdtId() {
		return udtId;
	}

	public void setUdtId(String udtId) {
		this.udtId = udtId;
	}

	public String getUdtNm() {
		return udtNm;
	}

	public void setUdtNm(String udtNm) {
		this.udtNm = udtNm;
	}

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public int getEndRow() {
		return endRow;
	}

	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}	
	
	
}
