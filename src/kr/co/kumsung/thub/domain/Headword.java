package kr.co.kumsung.thub.domain;

import java.util.ArrayList;

import kr.co.kumsung.thub.util.DateUtil;
import kr.co.kumsung.thub.util.Validate;

/**
 * 표제어 Domain Object
 * @author mikelim
 *
 */
public class Headword {

	private int headwordId = 0;	
	private String flag = "H";
	private String category = "";
	private String exhibit = "";
	private int bookId = 0;
	private int unitId = 0;
	private String title = "";
	private String titleEng = "";
	private String titleChi = "";
	private String thumbnail = "";
	private String summary = "";
	private String contents = "";
	private String added = "";	
	private String originCode = "";
	private String originTxt = "";
	private String relationBooks = "";
	private String regDate = "";
	private String modifyDate = "";
	private String regId = "";	
	private int hits = 0;
	private String isUse = "Y";
	private String isApprove = "Y";
	
	private String categoryPath = "";
	private String exhibitPath = "";	
	private String bookName = "";
	private String unitPath = "";
	private String unitIds = "";
	private String userName = "";
	
	private int studyCount = 0;
	
	public int getHeadwordId() {
		return headwordId;
	}
	public void setHeadwordId(int headwordId) {
		this.headwordId = headwordId;
	}
	public String getFlag() {
		return flag;
	}
	public String getFlagName() {
		if( flag.equals("H") )
			return "표제어";
		else if( flag.equals("E") )
			return "백과플러스";
		
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getExhibit() {
		return exhibit;
	}
	public void setExhibit(String exhibit) {
		this.exhibit = exhibit;
	}
	public int getBookId() {
		return bookId;
	}
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
	public int getUnitId() {
		return unitId;
	}
	public void setUnitId(int unitId) {
		this.unitId = unitId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTitleEng() {
		return titleEng;
	}
	public void setTitleEng(String titleEng) {
		this.titleEng = titleEng;
	}
	public String getTitleChi() {
		return titleChi;
	}
	public void setTitleChi(String titleChi) {
		this.titleChi = titleChi;
	}
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public String getAdded() {
		return added;
	}
	public void setAdded(String added) {
		this.added = added;
	}
	public String getOriginCode() {
		return originCode;
	}
	public void setOriginCode(String originCode) {
		this.originCode = originCode;
	}
	public String getOriginTxt() {
		if( !Validate.isEmpty(originCode) 
				&& originCode.equals("DIRECT"))
			return "직접집필";
		
		return originTxt;
	}
	public void setOriginTxt(String originTxt) {
		this.originTxt = originTxt;
	}
	public String getRelationBooks() {
		return relationBooks;
	}
	public void setRelationBooks(String relationBooks) {
		this.relationBooks = relationBooks;
	}
	public String getRegDate() {
		return regDate;
	}
	public String getRegDateFormatted( ) {
		
		if( !Validate.isEmpty(regDate) )
			return regDate.substring(0, 10);
		
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
	public int getHits() {
		return hits;
	}
	public void setHits(int hits) {
		this.hits = hits;
	}
	public String getIsUse() {
		return isUse;
	}
	public String getIsUseName() {
		
		if( isUse.equals("Y") )
			return "사용";
		else if( isUse.equals("N") )
			return "미사용";
		
		return isUse;
	}
	public void setIsUse(String isUse) {
		this.isUse = isUse;
	}
	public String getIsApprove() {
		return isApprove;
	}
	public void setIsApprove(String isApprove) {
		this.isApprove = isApprove;
	}
	
	public String getCategoryPath() {
		return categoryPath;
	}
	public void setCategoryPath(String categoryPath) {
		this.categoryPath = categoryPath;
	}
	public String getExhibitPath() {
		return exhibitPath;
	}
	public String getExhibitPathForDic() {
		if( !Validate.isEmpty(exhibitPath) )
		{
			String t = exhibitPath.replaceAll("\\p{Space}" , "");
			t = t.replaceAll("한국관>","");
			t = t.replaceAll("세계관>","");
			t = t.replaceAll(">" , "/");
			
			return t;
		}
		
		return exhibitPath;
	}
	public void setExhibitPath(String exhibitPath) {
		this.exhibitPath = exhibitPath;
	}
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public String getUnitPath() {
		return unitPath;
	}
	public void setUnitPath(String unitPath) {
		this.unitPath = unitPath;
	}
	public String getUnitIds() {
		return unitIds;
	}
	public void setUnitIds(String unitIds) {
		this.unitIds = unitIds;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getStudyCount() {
		return studyCount;
	}
	public void setStudyCount(int studyCount) {
		this.studyCount = studyCount;
	}
	
	public int getDiffDate()
	{
		if( !Validate.isEmpty(regDate) )
			return DateUtil.diffDate(regDate.substring(0,10).replaceAll("-" , ""));
		
		return 0;
	}
}
