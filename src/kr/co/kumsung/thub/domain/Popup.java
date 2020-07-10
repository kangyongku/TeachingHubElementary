package kr.co.kumsung.thub.domain;
/**
 * 팝업 목록 관리
 * @author jhg
 *
 */
public class Popup {
	private int popupId = 0;
	private String target = "";
	private String title = "";
	private int sizeWidth = 0;
	private int sizeHeight = 0;
	private int positionWidth = 0;
	private int positionHeight = 0;
	private String startDate = "";
	private String endDate = "";
	private String contents = "";
	private String type = "";
	private String isUse = "";
	private String regId = "";
	private String regDate = "";
	private String modifyId = "";
	private String modifyDate = "";
	
	public int getPopupId() {
		return popupId;
	}
	public void setPopupId(int popupId) {
		this.popupId = popupId;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getSizeWidth() {
		return sizeWidth;
	}
	public void setSizeWidth(int sizeWidth) {
		this.sizeWidth = sizeWidth;
	}
	public int getSizeHeight() {
		return sizeHeight;
	}
	public void setSizeHeight(int sizeHeight) {
		this.sizeHeight = sizeHeight;
	}
	public int getPositionWidth() {
		return positionWidth;
	}
	public void setPositionWidth(int positionWidth) {
		this.positionWidth = positionWidth;
	}
	public int getPositionHeight() {
		return positionHeight;
	}
	public void setPositionHeight(int positionHeight) {
		this.positionHeight = positionHeight;
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
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getIsUse() {
		return isUse;
	}
	public void setIsUse(String isUse) {
		this.isUse = isUse;
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
	
	
}
