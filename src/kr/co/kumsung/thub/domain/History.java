package kr.co.kumsung.thub.domain;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import kr.co.kumsung.thub.util.Common;
import kr.co.kumsung.thub.util.Validate;

public class History {

	private int historyId;	
	private int headwordId;
	private String isPrimary;
	private String historyType;	
	private String historyDate;
	private String historyExp;
	private String regDate;
	private String title;	
	private String thumbnail;
	private String summary;
	private String question;
	private String category;	
	private int bookId;
	private int weekCount;	
	
	public int getHistoryId() {
		return historyId;
	}
	public void setHistoryId(int historyId) {
		this.historyId = historyId;
	}
	public int getHeadwordId() {
		return headwordId;
	}
	public void setHeadwordId(int headwordId) {
		this.headwordId = headwordId;
	}
	public String getIsPrimary() {
		return isPrimary;
	}
	public void setIsPrimary(String isPrimary) {
		this.isPrimary = isPrimary;
	}
	public String getHistoryType() {
		return historyType;
	}
	public void setHistoryType(String historyType) {
		this.historyType = historyType;
	}
	public String getHistoryDate() {
		return historyDate;
	}
	public String getHistoryYear() {
			
			if( !Validate.isEmpty(historyDate))
			{
				String year = historyDate.substring(0, historyDate.length() - 4);
				return year;
			}
			
			return historyDate;
		}
	public String getHistoryMonth() {
		
		if( !Validate.isEmpty(historyDate))
		{
			String year = historyDate.substring(0, historyDate.length() - 4);
			String month = historyDate.substring(year.length() , year.length() + 2);
			return month;
		}
		
		return historyDate;
	}
	public String getHistoryDay() {
		
		if( !Validate.isEmpty(historyDate))
		{
			String year = historyDate.substring(0, historyDate.length() - 4);
			String month = historyDate.substring(year.length() , year.length() + 2);
			String day = historyDate.substring(year.length() + month.length() ,  historyDate.length());
			return day;
		}
		
		return historyDate;
	}
	public String getHistoryDateFormat() {
		
		if( !Validate.isEmpty(historyDate) )
		{
			String year = historyDate.substring(0, historyDate.length() - 4);
			String month = historyDate.substring(year.length() , year.length() + 2);
			String day = historyDate.substring(year.length() + month.length() ,  historyDate.length());
			return String.format("%s년 %s월 %s일" , year , month , day);
		}
		
		return historyDate;
	}
	public void setHistoryDate(String historyDate) {
		this.historyDate = historyDate;
	}
	public String getHistoryExp() {
		return historyExp;
	}
	public void setHistoryExp(String historyExp) {
		this.historyExp = historyExp;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getQuestion() {
		if( !Validate.isEmpty(question))
		{
			Document doc = Jsoup.parse(question);
			return Common.cutString(doc.text() , 50);
		}
		
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
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
	public int getWeekCount() {
		return weekCount;
	}
	public void setWeekCount(int weekCount) {
		this.weekCount = weekCount;
	}
}
