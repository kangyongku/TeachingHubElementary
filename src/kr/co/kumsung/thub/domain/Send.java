package kr.co.kumsung.thub.domain;

import kr.co.kumsung.thub.util.Validate;

public class Send {

	private int sendId;
	private int boardId;
	private int categoryId;
	private String title;
	private String contents;
	private String fileName1;
	private String fileName2;
	private String fileName3;
	private String dataFile1;
	private String dataFile2;
	private String dataFile3;
	private String userId;
	private String userName;
	private String regDate;
	private String boardName;
	private String isAccept;
	
	public String getIsAccept() {
		return isAccept;
	}
	public String getIsAcceptName() {
		
		if( !Validate.isEmpty(isAccept) )
		{
			if( isAccept.equals("Y"))
				return "승인";
		}
		
		return "대기";
	}
	public void setIsAccept(String isAccept) {
		this.isAccept = isAccept;
	}
	private String categoryName;
	
	public int getSendId() {
		return sendId;
	}
	public void setSendId(int sendId) {
		this.sendId = sendId;
	}
	public int getBoardId() {
		return boardId;
	}
	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public String getFileName1() {
		return fileName1;
	}
	public void setFileName1(String fileName1) {
		this.fileName1 = fileName1;
	}
	public String getFileName2() {
		return fileName2;
	}
	public void setFileName2(String fileName2) {
		this.fileName2 = fileName2;
	}
	public String getFileName3() {
		return fileName3;
	}
	public void setFileName3(String fileName3) {
		this.fileName3 = fileName3;
	}
	public String getDataFile1() {
		return dataFile1;
	}
	public void setDataFile1(String dataFile1) {
		this.dataFile1 = dataFile1;
	}
	public String getDataFile2() {
		return dataFile2;
	}
	public void setDataFile2(String dataFile2) {
		this.dataFile2 = dataFile2;
	}
	public String getDataFile3() {
		return dataFile3;
	}
	public void setDataFile3(String dataFile3) {
		this.dataFile3 = dataFile3;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getBoardName() {
		return boardName;
	}
	public void setBoardName(String boardName) {
		this.boardName = boardName;
	}
}
