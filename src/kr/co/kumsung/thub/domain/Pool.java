package kr.co.kumsung.thub.domain;

/**
 * 표제어의 문제 관리 Domain Object
 * @author mikelim
 *
 */
public class Pool {

	private int poolId;	
	private int headwordId;
	private String flag;
	private String question;
	private String answer;
	private String explanation;
	private String regDate;
	
	public int getPoolId() {
		return poolId;
	}
	public void setPoolId(int poolId) {
		this.poolId = poolId;
	}
	public int getHeadwordId() {
		return headwordId;
	}
	public void setHeadwordId(int headwordId) {
		this.headwordId = headwordId;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public String getExplanation() {
		return explanation;
	}
	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
}
