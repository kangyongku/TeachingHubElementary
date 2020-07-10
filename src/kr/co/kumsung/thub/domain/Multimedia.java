package kr.co.kumsung.thub.domain;

import kr.co.kumsung.thub.util.Validate;

public class Multimedia {
	
	private int mmId = 0;
	private String category = "";
	private String title = "";
	private String thumbnail = "";
	private String fileName = "";
	private String dataFile = "";
	private String contents = "";
	private String keyword = "";
	private String originCode = "";
	private String originTxt = "";
	private String isUse = "Y";
	private String regDate = "";
	private String regId = "";
	private String userName = "";
	private String dnUrl = "";
	
	private String categoryName = "";
	private int depth = 0;
	
	private String categoryPath = "";
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getThumbnail() {
		if( Validate.isEmpty(thumbnail) )
			return "/assets/front/img/no_image.gif";
		return thumbnail;
	}
	public String getNoImage()
	{
		return "/assets/front/img/no_image.gif";
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getDataFile() {
		return dataFile;
	}
	public void setDataFile(String dataFile) {
		this.dataFile = dataFile;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getOriginCode() {
		return originCode;
	}
	public void setOriginCode(String originCode) {
		this.originCode = originCode;
	}
	public String getOriginTxt() {
		return originTxt;
	}
	public void setOriginTxt(String originTxt) {
		this.originTxt = originTxt;
	}
	public String getIsUse() {
		return isUse;
	}
	public void setIsUse(String isUse) {
		this.isUse = isUse;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public int getMmId() {
		return mmId;
	}
	public void setMmId(int mmId) {
		this.mmId = mmId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}
	public int getDepth() {
		return depth;
	}
	public String getCategoryPath() {
		return categoryPath;
	}
	public void setCategoryPath(String categoryPath) {
		this.categoryPath = categoryPath;
	}
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getDnUrl() {
		return dnUrl;
	}
	public void setDnUrl(String dnUrl) {
		this.dnUrl = dnUrl;
	}
}
