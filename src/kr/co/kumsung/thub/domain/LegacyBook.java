package kr.co.kumsung.thub.domain;

public class LegacyBook {

	private int legacyId;	
	private String legacyCode;	
	private String name;
	private String author;
	private String className;
	private String previewlink;
	
	public int getLegacyId() {
		return legacyId;
	}
	public void setLegacyId(int legacyId) {
		this.legacyId = legacyId;
	}
	public String getLegacyCode() {
		return legacyCode;
	}
	public void setLegacyCode(String legacyCode) {
		this.legacyCode = legacyCode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getPreviewlink() {
		return previewlink;
	}
	public void setPreviewlink(String previewlink) {
		this.previewlink = previewlink;
	}
	
}
