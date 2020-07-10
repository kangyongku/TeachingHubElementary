package kr.co.kumsung.thub.domain;

import kr.co.kumsung.thub.util.Validate;

/**
 * 설문조사 설문지
 * @author jhg
 *
 */
public class Poll {

	private int pollId = 0;
	private String subject = "";
	private String target = "";
	private String startDate = "";
	private String endDate = "";
	private String regDate = "";
	private String isUse = "Y";
	private String isResult = "Y";
	private String regId = "";
	private String progress = "";
	private String pollCnt = "";
	
	public int getPollId() {
		return pollId;
	}
	public void setPollId(int pollId) {
		this.pollId = pollId;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
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
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getIsUse() {
		return isUse;
	}
	public void setIsUse(String isUse) {
		this.isUse = isUse;
	}
	public String getProgress() {
		return progress;
	}
	public void setProgress(String progress) {
		this.progress = progress;
	}
	public String getPollCnt() {
		return pollCnt;
	}
	public void setPollCnt(String pollCnt) {
		this.pollCnt = pollCnt;
	}
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
	public String getTargetName()
	{
		if( !Validate.isEmpty(this.target) )
		{
			if( target.equals("A") )
				return "전체";
			else if( target.equals("B"))
				return "초등";
			else if( target.equals("C"))
				return "중등";
			else if( target.equals("D"))
				return "고등";
		}
		
		return "";
	}
	public String getIsResult() {
		return isResult;
	}
	public void setIsResult(String isResult) {
		this.isResult = isResult;
	}
	
	
}
