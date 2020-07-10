package kr.co.kumsung.thub.domain;

import kr.co.kumsung.thub.util.Validate;

/**
 * 공감! 강연 
 * @author jhg
 */
public class Lecture {
	private int lectureId;
	private String category = "";
	private String title = "";
	private String summaryContents = "";
	private String thumbnail = "";
	private String linkUrl = "";
	private String contents = "";
	private String originCode = "";
	private String originText = "";
	private String regId = "";
	private String regDate = "";
	private String modifyId = "";
	private String modifyDate = "";
	private int hits;
	
	private String categoryName = "";
	
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
	public String getSummaryContents() {
		return summaryContents;
	}
	public void setSummaryContents(String summaryContents) {
		this.summaryContents = summaryContents;
	}
	public String getThumbnail() {
		if( Validate.isEmpty(thumbnail) )
			return "/assets/front/img/no_image.gif";
		
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	public String getLinkUrl() {
		return linkUrl;
	}
	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public String getOriginCode() {
		return originCode;
	}
	public void setOriginCode(String originCode) {
		this.originCode = originCode;
	}
	public String getOriginText() {
		return originText;
	}
	public void setOriginText(String originText) {
		this.originText = originText;
	}
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getModifyId() {
		return modifyId;
	}
	public void setModifyId(String modifyId) {
		this.modifyId = modifyId;
	}
	public String getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}
	public int getHits() {
		return hits;
	}
	public void setHits(int hits) {
		this.hits = hits;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public int getLectureId() {
		return lectureId;
	}
	public void setLectureId(int lectureId) {
		this.lectureId = lectureId;
	}
}
