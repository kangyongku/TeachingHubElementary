package kr.co.kumsung.thub.domain;
/**
 * 이벤트 관리
 * @author jhg
 */
public class Event {
	private int eventId = 0;
	private int commnetId = 0;
	private String title = "";
	private String summaryContents = "";
	private String startDate = "";
	private String endDate = "";
	private String fileName = "";
	private String bannerImg = "";
	private String contents = "";
	private String regId = "";
	private String regDate = "";
	private String modifyId = "";
	private String modifyDate = "";
	private int hits = 0;
	private int eventCnt = 0;
	private int winId = 0;
	private int viewCnt = 0;
	private String progress = "";
	private String useComment ="";
	private String useReply ="";
	
	public int getEventId() {
		return eventId;
	}
	public void setEventId(int eventId) {
		this.eventId = eventId;
	}
	
	public int getCommentListId() {
		return commnetId;
	}
	public void setCommnetListId(int commnetId) {
		this.commnetId = commnetId;
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
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getBannerImg() {
		return bannerImg;
	}
	public void setBannerImg(String bannerImg) {
		this.bannerImg = bannerImg;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
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
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getModifyId() {
		return modifyId;
	}
	public void setModifyId(String modifyId) {
		this.modifyId = modifyId;
	}
	public int getEventCnt() {
		return eventCnt;
	}
	public void setEventCnt(int eventCnt) {
		this.eventCnt = eventCnt;
	}
	public int getWinId() {
		return winId;
	}
	public void setWinId(int winId) {
		this.winId = winId;
	}
	public int getCnt() {
		return viewCnt;
	}
	
	public void setCnt(int viewCnt) {
		this.viewCnt = viewCnt;
	}
	
	public String getProgress() {
		return progress;
	}
	public void setProgress(String progress) {
		this.progress = progress;
	}
	
	public String getUseComment() {
		return useComment;
	}
	
	public void setUseCommnet(String useComment) {
		this.useComment = useComment;
	}
	
	public String getUseReply() {
		return useReply;
	}
	
	public void setUseReply(String useReply) {
		this.useReply = useReply;
	}
}
