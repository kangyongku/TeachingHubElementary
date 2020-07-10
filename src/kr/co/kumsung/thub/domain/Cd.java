package kr.co.kumsung.thub.domain;

public class Cd {

	private int cdId;	
	private int bookId;
	private String name;
	private String url;
	private String regDate;
	
	public int getCdId() {
		return cdId;
	}
	public void setCdId(int cdId) {
		this.cdId = cdId;
	}
	public int getBookId() {
		return bookId;
	}
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	
}
