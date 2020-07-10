package kr.co.kumsung.thub.domain;

import kr.co.kumsung.thub.util.Common;
import kr.co.kumsung.thub.util.Validate;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Knowledge {

	private int knowledgeId;
	private int headwordId;
	private String flag = "";	
	private String userId = "";
	private String userName = "";
	private String contents = "";
	private String regDate = "";  
	private String isApproval = "";
	private String approvalDate = "";
	private String approvalId = "";
	private String approvalName = "";
	private String originCode = "";
	private String originTxt = "";
	private String title = "";
	
	public String getUserIdSecret(){
		if( !Validate.isEmpty(this.userId) )
			return Common.getSecretId(this.userId);
		
		return "";
	}
	public int getKnowledgeId() {
		return knowledgeId;
	}
	public void setKnowledgeId(int knowledgeId) {
		this.knowledgeId = knowledgeId;
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
	public String getFlagName() {
		
		if( !Validate.isEmpty(flag) )
		{
			if( flag.equals("S") )
				return "지식나눔";
			else if( flag.equals("M"))
				return "수정요청";
		}
		
		return flag;
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
	public String getContents() {
		return contents;
	}
	public String getContentsText() {
		
		if( !Validate.isEmpty(contents) )
		{
			Document doc = Jsoup.parse(contents);
			return Common.cutString(doc.text() , 70);
		}
		
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public String getRegDate() {
		return regDate;
	}
	public String getRegDateFormat() {
		if( !Validate.isEmpty(regDate))
			return regDate.substring(0, 10);
		
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getIsApproval() {
		return isApproval;
	}
	public String getIsApprovalName() {
		
		if( !Validate.isEmpty(isApproval) )
		{
			if( isApproval.equals("Y"))
				return "승인";
			else if( isApproval.equals("D"))
				return "미승인";
			else if( isApproval.equals("N"))
				return "대기";
		}
		
		return isApproval;
	}
	public void setIsApproval(String isApproval) {
		this.isApproval = isApproval;
	}
	public String getApprovalDate() {
		return approvalDate;
	}
	public String getApprovalDateFormat() {
		if( !Validate.isEmpty(approvalDate))
			return approvalDate.substring(0, 10);
		
		return approvalDate;
	}
	public void setApprovalDate(String approvalDate) {
		this.approvalDate = approvalDate;
	}	
	public String getApprovalId() {
		return approvalId;
	}
	public void setApprovalId(String approvalId) {
		this.approvalId = approvalId;
	}
	public String getApprovalName() {
		return approvalName;
	}
	public void setApprovalName(String approvalName) {
		this.approvalName = approvalName;
	}
	public String getOriginCode() {
		return originCode;
	}
	public void setOriginCode(String originCode) {
		this.originCode = originCode;
	}
	public String getOriginTxt() {
		
		if( !Validate.isEmpty(originCode) 
				&& originCode.equals("DIRECT"))
			return "직접등록";
		
		return originTxt;
	}
	public void setOriginTxt(String originTxt) {
		this.originTxt = originTxt;
	}	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
}
