package kr.co.kumsung.thub.domain;

import kr.co.kumsung.thub.util.Validate;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import kr.co.kumsung.thub.util.Common;

public class CData {
	
	private int dataId = 0;
	private int sequence = 0;	
	private String regDate = "";
	private String userId = "";	
	private int cviewId = 0;
	private String chasiStep = "";
	private String chasiPage = "";
	
	
	private int hits = 0;
	
	public int getDataId() {
		return dataId;
	}
	public void setDataId(int dataId) {
		this.dataId = dataId;
	}
	public int getSequence() {
		return sequence;
	}
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getHits() {
		return hits;
	}
	public void setHits(int hits) {
		this.hits = hits;
	}
	public int getcviewId() {
		return cviewId;
	}
	public void setcviewId(int cviewId) {
		this.cviewId = cviewId;
	}
	public String getChasiStep() {
		return chasiStep;
	}
	public void setChasiStep(String chasiStep) {
		this.chasiStep = chasiStep;
	}
	public String getChasiPage() {
		return chasiPage;
	}
	public void setChasiText(String chasiPage) {
		this.chasiPage = chasiPage;
	}	
}
