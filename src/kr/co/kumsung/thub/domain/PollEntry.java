package kr.co.kumsung.thub.domain;
/**
 * 설문 문항
 * @author jhg
 *
 */
public class PollEntry {

	private int entryId = 0;
	private int pollId = 0;
	private String title = "";
	private String type = "M";
	private String regDate = "";
	private String multiple = "N";
	private String ansyn = "N";
	private String progress = "";
	private String[] arrId;
	
	
	public String getProgress() {
		return progress;
	}
	public void setProgress(String progress) {
		this.progress = progress;
	}
	public int getEntryId() {
		return entryId;
	}
	public void setEntryId(int entryId) {
		this.entryId = entryId;
	}
	public int getPollId() {
		return pollId;
	}
	public void setPollId(int pollId) {
		this.pollId = pollId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getMultiple() {
		return multiple;
	}
	public void setMultiple(String multiple) {
		this.multiple = multiple;
	}
	public String getAnsyn() {
		return ansyn;
	}
	public void setAnsyn(String ansyn) {
		this.ansyn = ansyn;
	}	
	public String[] getArrId() {
		return arrId;
	}
	public void setArrId(String[] arrId) {
		this.arrId = arrId;
	}
}
