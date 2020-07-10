package kr.co.kumsung.thub.domain;

import java.io.Serializable;


public class Config extends CommonParamVo implements Serializable{
	/**
	 * Config용 VO
	 * BookGroupm,SCHOOL,zipcode,SIDO 테이블
	 */
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 공통영역
	 */
	/** id명(북카테고리) */
	private String id;
	/** 주소seq */
	private String seq;
	/** 전화 */
	private String tel;
	/** 팩스 */
	private String fax;
	/** 홈페이지 */
	private String homepage;
	/** 전체주소 */
	private String address;
	/** 추가입력주소 */
	private String address2;
	/** 지역구분 */
	private String sidoSeq;
	/** 우편번호 */
	private String zipCode;
	/** 시도 */
	private String sido;
	/** 구군 */
	private String gugun;
	/** 동 */
	private String dong;
	/** 번지 */
	private String bunji;
	/** 시도명 */
	private String sidoNm;
	/** 정렬순서 */
	private String orderNum;
	/** 검색어 */
	private String searchNm;
	/** 검색테이블 */
	private String searchField;
	/** 사용여부 */
	private String useFlag;
	/** 그룹값 */
	private String grValue;
	/** 지역값 */
	private String sidoValue;
	/** 폼명 */
	private String formName;
	private String ri;
	private String road;
	private String road_building;
	private String road_dong;
	private String new_road;
	private String new_bunji;
	private String new_road_building;
	private String new_road_dong;
	
	/**
	 * 학교영역
	 */
	/** 학교seq */
	private String schoolSeq;
	/** 북카테고리명 */
	private String bookGrNm;
	/** 북카테고리아이디 */
	private String bookGrid;
	/** 학교명 */
	private String schoolNm;
	
	/**
	 * 판매자영역
	 */
	/** 총 구분 */
	private String gubun;
	/** 판매자 */
	private String sellerNm;
	/** 대표명 */
	private String sellerLnm;
	/** 판매자seq */
	private String sellerSeq;
	/** 구분검색 */
	private String searchGubun;
	/** 책구분 */
	private String bGubun;
	/** 구분자 값 */
	private String gbValue;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getHomepage() {
		return homepage;
	}
	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getSidoSeq() {
		return sidoSeq;
	}
	public void setSidoSeq(String sidoSeq) {
		this.sidoSeq = sidoSeq;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getSido() {
		return sido;
	}
	public void setSido(String sido) {
		this.sido = sido;
	}
	public String getGugun() {
		return gugun;
	}
	public void setGugun(String gugun) {
		this.gugun = gugun;
	}
	public String getDong() {
		return dong;
	}
	public void setDong(String dong) {
		this.dong = dong;
	}
	public String getBunji() {
		return bunji;
	}
	public void setBunji(String bunji) {
		this.bunji = bunji;
	}
	public String getSidoNm() {
		return sidoNm;
	}
	public void setSidoNm(String sidoNm) {
		this.sidoNm = sidoNm;
	}
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public String getSearchNm() {
		return searchNm;
	}
	public void setSearchNm(String searchNm) {
		this.searchNm = searchNm;
	}
	public String getSearchField() {
		return searchField;
	}
	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}
	public String getUseFlag() {
		return useFlag;
	}
	public void setUseFlag(String useFlag) {
		this.useFlag = useFlag;
	}
	public String getGrValue() {
		return grValue;
	}
	public void setGrValue(String grValue) {
		this.grValue = grValue;
	}
	public String getSidoValue() {
		return sidoValue;
	}
	public void setSidoValue(String sidoValue) {
		this.sidoValue = sidoValue;
	}
	public String getFormName() {
		return formName;
	}
	public void setFormName(String formName) {
		this.formName = formName;
	}
	public String getRi() {
		return ri;
	}
	public void setRi(String ri) {
		this.ri = ri;
	}
	public String getRoad() {
		return road;
	}
	public void setRoad(String road) {
		this.road = road;
	}
	public String getRoad_building() {
		return road_building;
	}
	public void setRoad_building(String road_building) {
		this.road_building = road_building;
	}
	public String getRoad_dong() {
		return road_dong;
	}
	public void setRoad_dong(String road_dong) {
		this.road_dong = road_dong;
	}
	public String getNew_road() {
		return new_road;
	}
	public void setNew_road(String new_road) {
		this.new_road = new_road;
	}
	public String getNew_bunji() {
		return new_bunji;
	}
	public void setNew_bunji(String new_bunji) {
		this.new_bunji = new_bunji;
	}
	public String getNew_road_building() {
		return new_road_building;
	}
	public void setNew_road_building(String new_road_building) {
		this.new_road_building = new_road_building;
	}
	public String getNew_road_dong() {
		return new_road_dong;
	}
	public void setNew_road_dong(String new_road_dong) {
		this.new_road_dong = new_road_dong;
	}
	public String getSchoolSeq() {
		return schoolSeq;
	}
	public void setSchoolSeq(String schoolSeq) {
		this.schoolSeq = schoolSeq;
	}
	public String getBookGrNm() {
		return bookGrNm;
	}
	public void setBookGrNm(String bookGrNm) {
		this.bookGrNm = bookGrNm;
	}
	public String getBookGrid() {
		return bookGrid;
	}
	public void setBookGrid(String bookGrid) {
		this.bookGrid = bookGrid;
	}
	public String getSchoolNm() {
		return schoolNm;
	}
	public void setSchoolNm(String schoolNm) {
		this.schoolNm = schoolNm;
	}
	public String getGubun() {
		return gubun;
	}
	public void setGubun(String gubun) {
		this.gubun = gubun;
	}
	public String getSellerNm() {
		return sellerNm;
	}
	public void setSellerNm(String sellerNm) {
		this.sellerNm = sellerNm;
	}
	public String getSellerLnm() {
		return sellerLnm;
	}
	public void setSellerLnm(String sellerLnm) {
		this.sellerLnm = sellerLnm;
	}
	public String getSellerSeq() {
		return sellerSeq;
	}
	public void setSellerSeq(String sellerSeq) {
		this.sellerSeq = sellerSeq;
	}
	public String getSearchGubun() {
		return searchGubun;
	}
	public void setSearchGubun(String searchGubun) {
		this.searchGubun = searchGubun;
	}
	public String getbGubun() {
		return bGubun;
	}
	public void setbGubun(String bGubun) {
		this.bGubun = bGubun;
	}
	public String getGbValue() {
		return gbValue;
	}
	public void setGbValue(String gbValue) {
		this.gbValue = gbValue;
	}
}
