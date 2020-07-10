package kr.co.kumsung.thub.domain;

import kr.co.kumsung.thub.util.DateUtil;
import kr.co.kumsung.thub.util.Validate;

/**
 * 이벤트 당첨자 관리
 * @author jhg
 */
public class EventWin {
	private int winId = 0;
	private int eventId = 0;
	private String title = "";
	private String contents = "";
	private String regId = "";
	private String regDate = "";
	private String modifyId = "";
	private String modifyDate = "";
	private int hits = 0;
	private int eventCnt = 0;
	private String startDate = "";
	private String endDate = "";
	public int getEventId() {
		return eventId;
	}
	public void setEventId(int eventId) {
		this.eventId = eventId;
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
	public String getModifyId() {
		return modifyId;
	}
	public void setModifyId(String modifyId) {
		this.modifyId = modifyId;
	}
	public int getWinId() {
		return winId;
	}
	public void setWinId(int winId) {
		this.winId = winId;
	}
	public int getEventCnt() {
		return eventCnt;
	}
	public void setEventCnt(int eventCnt) {
		this.eventCnt = eventCnt;
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
	public int getDiffDate()
	{
		if( !Validate.isEmpty(regDate) )
			return DateUtil.diffDate(regDate.substring(0,10).replaceAll("-" , ""));
		
		return 0;
	}
}
