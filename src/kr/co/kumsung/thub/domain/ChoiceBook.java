package kr.co.kumsung.thub.domain;

import kr.co.kumsung.thub.util.Validate;

public class ChoiceBook {

	private int choiceId;	
	private String category;	
	private String userId;
	private int bookId;
	private String regDate;	
	private String name;
	private String courseName;
	private String author;
	private String imgUrl;
	private String special_cd = "";
	private String teacherCdView = "";
	private String cdLinks = "";
	private int cdViewWidth = 1024;
	private int cdViewHeight = 768;	
	
	public int getChoiceId() {
		return choiceId;
	}
	public void setChoiceId(int choiceId) {
		this.choiceId = choiceId;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getBookId() {
		return bookId;
	}
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getTeacherCdView() {
		return teacherCdView;
	}
	public void setTeacherCdView(String teacherCdView) {
		this.teacherCdView = teacherCdView;
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
		
}
