package kr.co.kumsung.thub.domain;

import kr.co.kumsung.thub.util.Common;
import kr.co.kumsung.thub.util.Validate;

public class Comment {

	private int commentId;
	private int boardId;
	private int headwordId;	
	private int articleId;	
	private int groupId;
	private int depth;
	private String userId;
	private String userName;
	private String comment;
	private String regDate;
	private String remoteIp;
	private String isDel;
	private int eventId;
	private int isAdmin;
	
	public String getCommentIdSecret(){
		if( !Validate.isEmpty(this.userId) )
			return Common.getSecretId(this.userId);
		
		return "";
	}
	
	public String getRegDateFormat() {
		if( !Validate.isEmpty(regDate))
			return regDate.substring(0, 10);
		
		return regDate;
	}
	
	public int getCommentId() {
		return commentId;
	}
	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}
	public int getBoardId() {
		return boardId;
	}
	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}
	public int getHeadwordId() {
		return headwordId;
	}
	public void setHeadwordId(int headwordId) {
		this.headwordId = headwordId;
	}
	public int getArticleId() {
		return articleId;
	}
	public void setArticleId(int articleId) {
		this.articleId = articleId;
	}
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	public int getDepth() {
		return depth;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserIdSecret(){
		if( !Validate.isEmpty(this.userId) )
			return Common.getSecretId(this.userId);
		
		return "";
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getRemoteIp() {
		return remoteIp;
	}
	public void setRemoteIp(String remoteIp) {
		this.remoteIp = remoteIp;
	}
	public String getIsDel() {
		return isDel;
	}
	public void setIsDel(String isDel) {
		this.isDel = isDel;
	}
	
	public int getEventId() {
		return eventId;
	}
	public void setEventId(int eventId) {
		this.eventId = eventId;
	}
	
	public int getIsAdmin() {
		return isAdmin;
	}
	public void setIsAdmin(int isAdmin) {
		this.isAdmin = isAdmin;
	}
}
