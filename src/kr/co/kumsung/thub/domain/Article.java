package kr.co.kumsung.thub.domain;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import kr.co.kumsung.thub.util.Common;
import kr.co.kumsung.thub.util.DateUtil;
import kr.co.kumsung.thub.util.Validate;

public class Article {

	private int articleId = 0;
	private int boardId = 0;
	private int categoryId = 0;	
	private String categoryName = "";
	private String userId = "";
	private String userName = "";	
	private int groupId = 0;
	private String steps = "";
	private String linkUrl = "";
	private String movieUrl = "";
	private String introduce = "";
	private int thumbnail = 0;
	private String thumbnailPath = "";	
	private String subject = "";
	private String contents = "";
	private String attachment = "";
	private String etc = "";
	private String keywords = "";	
	private String regDate = "";
	private String remoteIp = "";
	private int recommend = 0;
	private int comments = 0;
	private int fileCount = 0;	
	private int hits = 0;
	private String isNotice = "";
	private String isSecret = "";	
	private String sub_category = "";
	private String code = "";
	private String codenm = "";
	private String isUse;
	
	
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCodenm() {
		return codenm;
	}
	public void setCodenm(String codenm) {
		this.codenm = codenm;
	}
	public String getSub_category() {
		return sub_category;
	}
	public void setSub_category(String sub_category) {
		this.sub_category = sub_category;
	}
	public int getArticleId() {
		return articleId;
	}
	public void setArticleId(int articleId) {
		this.articleId = articleId;
	}
	public int getBoardId() {
		return boardId;
	}
	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserIdSecret(){
		if( !Validate.isEmpty(this.userId) )
			return Common.getSecretId(this.userId);
		
		return "";
	}
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	public String getSteps() {
		return steps;
	}
	public void setSteps(String steps) {
		this.steps = steps;
	}
	public String getLinkUrl() {
		return linkUrl;
	}
	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}
	public String getMovieUrl() {
		return movieUrl;
	}
	public void setMovieUrl(String movieUrl) {
		this.movieUrl = movieUrl;
	}
	public String getIntroduce() {
		return introduce;
	}
	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	public int getThumbnail() {
		
		return thumbnail;
	}
	public void setThumbnail(int thumbnail) {
		this.thumbnail = thumbnail;
	}
	public String getThumbnailPath() {
		return thumbnailPath;
	}
	public String getNoImage(){
		return "/assets/front/img/no_image.gif";
	}
	public void setThumbnailPath(String thumbnailPath) {
		this.thumbnailPath = thumbnailPath;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContents() {
		return contents;
	}
	public String getListContents() {
		Document doc = Jsoup.parse(contents);
		return Common.cutString(doc.text() , 100);
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public String getAttachment() {
		return attachment;
	}
	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}
	public String getEtc() {
		return etc;
	}
	public void setEtc(String etc) {
		this.etc = etc;
	}
	public String getRegDate() {
		return regDate;
	}
	public String getRegDateFormat(){
		if( !Validate.isEmpty(regDate) )
			return regDate.substring(0, 10);
	
		return "";
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getRemoteIp() {
		return remoteIp;
	}
	public void setRemoteIp(String remoteIp) {
		this.remoteIp = remoteIp;
	}
	public int getRecommend() {
		return recommend;
	}
	public void setRecommend(int recommend) {
		this.recommend = recommend;
	}
	public int getHits() {
		return hits;
	}
	public void setHits(int hits) {
		this.hits = hits;
	}
	public String getIsNotice() {
		return isNotice;
	}
	public void setIsNotice(String isNotice) {
		this.isNotice = isNotice;
	}
	public String getIsSecret() {
		return isSecret;
	}
	public void setIsSecret(String isSecret) {
		this.isSecret = isSecret;
	}
	public int getComments() {
		return comments;
	}
	public void setComments(int comments) {
		this.comments = comments;
	}
	public int getFileCount() {
		
		if( !Validate.isEmpty(attachment) )
		{
			String[] t = attachment.split("\\,");
			return t.length;
		}
		
		return 0;
	}
	public int getFirstAttachmentId()
	{
		if( !Validate.isEmpty(attachment) )
		{
			String[] t = attachment.split("\\,");
			
			if( t.length > 0 )
				return Integer.valueOf(t[0]);
		}
		
		return 0;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public int getDiffDate()
	{
		if( !Validate.isEmpty(regDate) )
			return DateUtil.diffDate(regDate.substring(0,10).replaceAll("-" , ""));
		
		return 0;
	}

	public String getIsUse() {
		return isUse;
	}
	public void setIsUse(String isUse) {
		this.isUse = isUse;
	}
}
