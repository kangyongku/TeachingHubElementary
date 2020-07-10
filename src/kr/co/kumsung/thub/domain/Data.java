package kr.co.kumsung.thub.domain;

import kr.co.kumsung.thub.util.Validate;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import kr.co.kumsung.thub.util.Common;

public class Data {
	
	private int dataId          = 0;
	private String category     = "";
	private int bookId          = 0;
	private int unitId          = 0;	
	private String dataType     = "";
	private String dataFlag     = "";
	private String title        = "";
	private String fileName     = "";
	private String dataFile     = "";
	private String keywords     = "";
	private int mmId            = 0;	
	private int sequence        = 0;	
	private String regDate      = "";
	private String origin_code  = "";
	private String origin_name  = "";
	private String bookName     = "";	
	private String unitIds      = "";
	private String categoryPath = "";
	private String unitPath     = "";	
	private String dataTypeName = "";
	private String extImg       = "";
	private String userId       = "";	
	private String userName     = "";	
	private String thumbnail    = "";
	private String contents     = "";	
	private int downloadCount   = 0;
	private int scrapCount      = 0;	
	private int cviewId         = 0;
	private String chasiStep    = "";
	private String chasiPage    = "";
	private int chasiCount      = 0;
	private int cfileId         = 0;
	private int sdataId         = 0;
	private String cate2Name    = "";
	private String cate3Name    = "";	
	private String dnUrl        = "";	
	private int hits            = 0;
	private String rnum         = "";	
	private String max_rnum     = "";
	private String dn_url_size  = "";
	
	
	
	
	public String getDn_url_size() {
		return dn_url_size;
	}
	public void setDn_url_size(String dn_url_size) {
		this.dn_url_size = dn_url_size;
	}
	public String getMax_rnum() {
		return max_rnum;
	}
	public void setMax_rnum(String max_rnum) {
		this.max_rnum = max_rnum;
	}
	public String getRnum() {
		return rnum;
	}
	public void setRnum(String rnum) {
		this.rnum = rnum;
	}
	public String getOrigin_name() {
		return origin_name;
	}
	public void setOrigin_name(String origin_name) {
		this.origin_name = origin_name;
	}
	public String getOrigin_code() {
		return origin_code;
	}
	public void setOrigin_code(String origin_code) {
		this.origin_code = origin_code;
	}
	public String getContents() {
		Document doc = Jsoup.parse(contents);
		return Common.cutString(doc.text() , 270);
	}
	public void setContents(String contents) {
		this.contents = contents;
	}	

	public int getDataId() {
		return dataId;
	}
	public void setDataId(int dataId) {
		this.dataId = dataId;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public int getBookId() {
		return bookId;
	}
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
	public int getUnitId() {
		return unitId;
	}
	public void setUnitId(int unitId) {
		this.unitId = unitId;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getDataFlag() {
		return dataFlag;
	}
	public void setDataFlag(String dataFlag) {
		this.dataFlag = dataFlag;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getDataFile() {
		return dataFile;
	}
	public void setDataFile(String dataFile) {
		this.dataFile = dataFile;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public int getMmId() {
		return mmId;
	}
	public void setMmId(int mmId) {
		this.mmId = mmId;
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
	
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public String getUnitIds() {
		return unitIds;
	}
	public void setUnitIds(String unitIds) {
		this.unitIds = unitIds;
	}
	public String getCategoryPath() {
		return categoryPath;
	}
	public void setCategoryPath(String categoryPath) {
		this.categoryPath = categoryPath;
	}
	public String getUnitPath() {
		return unitPath;
	}
	public void setUnitPath(String unitPath) {
		this.unitPath = unitPath;
	}
	public String getDataTypeName() {
		return dataTypeName;
	}
	public void setDataTypeName(String dataTypeName) {
		this.dataTypeName = dataTypeName;
	}
	public String getExtImg() {
		return extImg;
	}
	public void setExtImg(String extImg) {
		this.extImg = extImg;
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
	public int getHits() {
		return hits;
	}
	public void setHits(int hits) {
		this.hits = hits;
	}
	public String getThumbnail() {
		
		if( Validate.isEmpty(thumbnail) )
			return "/assets/front/img/no_image.gif";
		
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	public int getDownloadCount() {
		return downloadCount;
	}
	public void setDownloadCount(int downloadCount) {
		this.downloadCount = downloadCount;
	}
	public int getScrapCount() {
		return scrapCount;
	}
	public void setScrapCount(int scrapCount) {
		this.scrapCount = scrapCount;
	}
	public String getDnUrl() {
		return dnUrl;
	}
	public void setDnUrl(String dnUrl) {
		this.dnUrl = dnUrl;
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
	public void setChasiPage(String chasiPage) {
		this.chasiPage = chasiPage;
	}
	public int getcfileId() {
		return cfileId;
	}
	public void setcfileId(int cfileId) {
		this.cfileId = cfileId;
	}	
	public int getsDataId() {
		return sdataId;
	}
	public void setsDataId(int sdataId) {
		this.sdataId = sdataId;
	}	
	public String getcate2Name() {
		return cate2Name;
	}
	public void setcate2Name(String cate2Name) {
		this.cate2Name = cate2Name;
	}
	public String getcate3Name() {
		return cate3Name;
	}
	public void setcate3Name(String cate3Name) {
		this.cate3Name = cate3Name;
	}	
	public int getChasiCount() {
		return chasiCount;
	}
	public void setChasiCount(int chasiCount) {
		this.chasiCount = chasiCount;
	}	
}