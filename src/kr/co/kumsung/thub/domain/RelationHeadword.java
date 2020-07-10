package kr.co.kumsung.thub.domain;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import kr.co.kumsung.thub.util.Common;
import kr.co.kumsung.thub.util.Validate;

public class RelationHeadword {

	private int relationId;	
	private int headwordId;
	private int childrenId;
	private String category;
	private int bookId;
	private String thumbnail;	
	private String title;
	private String titleEng;
	private String titleChi;
	
	public int getRelationId() {
		return relationId;
	}
	public void setRelationId(int relationId) {
		this.relationId = relationId;
	}
	public int getHeadwordId() {
		return headwordId;
	}
	public void setHeadwordId(int headwordId) {
		this.headwordId = headwordId;
	}
	public int getChildrenId() {
		return childrenId;
	}
	public void setChildrenId(int childrenId) {
		this.childrenId = childrenId;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public int getBookId() {
		return bookId;
	}
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
	public String getThumbnail() {
		if( Validate.isEmpty(thumbnail) )
			return "/assets/front/img/no_image.gif";
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	public String getTitle() {
		//박상선 대리 요청 제목 다보이도록 수정 2014.07.14
//		Document doc = Jsoup.parse(title);
//		return Common.cutString(doc.text() , 10);
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
}
