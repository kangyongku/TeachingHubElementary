package kr.co.kumsung.thub.domain;
/**
 * 설문 문항 질의
 * @author jhg
 *
 */
public class PollEntryItem {
	private int pollId = 0;
	private int entryId = 0;
	private int itemId = 0;
	private String title = "";
	private String type = "";
	private String multiple = "N";
	private String ansyn = "N";
	private String sequence = "";
	private String answer = "";
	
	public int getPollId() {
		return pollId;
	}
	public void setPollId(int pollId) {
		this.pollId = pollId;
	}
	public int getEntryId() {
		return entryId;
	}
	public void setEntryId(int entryId) {
		this.entryId = entryId;
	}
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
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
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public String getSequence() {
		return sequence;
	}
	public void setSequence(String sequence) {
		this.sequence = sequence;
	}
}
