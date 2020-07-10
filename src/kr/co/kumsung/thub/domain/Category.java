package kr.co.kumsung.thub.domain;

public class Category {

	private String category;
	private String name;
	private String isUse;	
	private int depth;
	private String regDate;
	private int children;
	private int books;
	private int dataCount;
	private int multimediaCount;
	private int headwordCount;
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIsUse() {
		return isUse;
	}
	public void setIsUse(String isUse) {
		this.isUse = isUse;
	}
	public int getDepth() {
		return depth;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public int getChildren() {
		return children;
	}
	public void setChildren(int children) {
		this.children = children;
	}
	public int getBooks() {
		return books;
	}
	public void setBooks(int books) {
		this.books = books;
	}
	public int getDataCount() {
		return dataCount;
	}
	public void setDataCount(int dataCount) {
		this.dataCount = dataCount;
	}
	public int getMultimediaCount() {
		return multimediaCount;
	}
	public void setMultimediaCount(int multimediaCount) {
		this.multimediaCount = multimediaCount;
	}
	public int getHeadwordCount() {
		return headwordCount;
	}
	public void setHeadwordCount(int headwordCount) {
		this.headwordCount = headwordCount;
	}
}
