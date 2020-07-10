package kr.co.kumsung.thub.domain;

import kr.co.kumsung.thub.util.Validate;

public class Book {

	private int bookId = 0;	
	private int legacyId = 0;	
	private String category = "";
	private String course = "";	
	private String name = "";
	private String author = "";	
	private String isUse = "";
	private String teacherCdView = "";	
	private String regDate = "";
	private String regId = "";
	private int sequence = 0;	
	private String userName = "";
	private String imgUrl = "";
	private String previewUrl = "";
	
	private String categoryPath = "";
	private String courseName = "";
	
	private String cdLinks = "";
	private int cdViewWidth = 1024;
	private int cdViewHeight = 768;
	
	private String special_cd = "";
	private String special_name = "";
	
	public int getBookId() {
		return bookId;
	}
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
	public int getLegacyId() {
		return legacyId;
	}
	public void setLegacyId(int legacyId) {
		this.legacyId = legacyId;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getIsUse() {
		return isUse;
	}
	public String getIsUseName(){
		if( isUse != null )
		{
			if(isUse.equals("Y"))
				return "노출";
		}
		
		return "미노출";
	}
	public void setIsUse(String isUse) {
		this.isUse = isUse;
	}
	public String getTeacherCdView() {
		return teacherCdView;
	}
	public void setTeacherCdView(String teacherCdView) {
		this.teacherCdView = teacherCdView;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
	public int getSequence() {
		return sequence;
	}
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
	public String getPreviewUrl() {
		return previewUrl;
	}
	public void setPreviewUrl(String previewUrl) {
		this.previewUrl = previewUrl;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	
	public String getCategoryPath() {
		return categoryPath;
	}
	public void setCategoryPath(String categoryPath) {
		this.categoryPath = categoryPath;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getCdLinks() {
		return cdLinks;
	}
	public void setCdLinks(String cdLinks) {
		this.cdLinks = cdLinks;
	}
	public String[] getCdLinksItems() {
		String[] items = null;
		
		if( !Validate.isEmpty(this.cdLinks) )
			items = cdLinks.split("\\|");
		
		return items;
	}
	public int getCdViewHeight() {
		return cdViewHeight;
	}
	public void setCdViewHeight(int cdViewHeight) {
		this.cdViewHeight = cdViewHeight;
	}
	public int getCdViewWidth() {
		return cdViewWidth;
	}
	public void setCdViewWidth(int cdViewWidth) {
		this.cdViewWidth = cdViewWidth;
	}
	
	public String getSpecialCd() {
		return special_cd;
	}
	public void setSpecialCd(String special_cd) {
		this.special_cd = special_cd;
	}
	
	public String getSpecialName() {
		return special_name;
	}
	public void setSpecialName(String special_name) {
		this.special_name = special_name;
	}	
}
