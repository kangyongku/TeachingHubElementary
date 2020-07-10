package kr.co.kumsung.thub.domain;

import kr.co.kumsung.thub.util.Validate;

public class BoardConfig {

	public int boardId;	
	public String boardName = "";
	public String boardType = "";
	public String boardFlag = "";	
	public String headerSection = "";	
	public String footerSection = "";
	public String useCategory;
	public String useWrite;
	public String useReply;
	public String useFileUpload;
	public String useComment;	
	public String useMovie;
	public String useLink;
	public String useRecommend;
	public String useSns;	
	public String useAnnmsList;
	public String useAnnmsView;
	public String allowFileTypes;
	public int allowFileCount;	
	public int allowFileSize;		
	public String regDate;
	public String regId;
	public String modifyDate;
	public String modifyId;
	public int totalArticles;
	public String modifyRegDate;
	
	public int getBoardId() {
		return boardId;
	}
	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}
	public String getBoardName() {
		return boardName;
	}
	public void setBoardName(String boardName) {
		this.boardName = boardName;
	}
	public String getBoardType() {
		return boardType;
	}
	public void setBoardType(String boardType) {
		this.boardType = boardType;
	}
	public String getBoardFlag() {
		return boardFlag;
	}
	public void setBoardFlag(String boardFlag) {
		this.boardFlag = boardFlag;
	}
	public String getHeaderSection() {
		return headerSection;
	}
	public void setHeaderSection(String headerSection) {
		this.headerSection = headerSection;
	}
	public String getFooterSection() {
		return footerSection;
	}
	public void setFooterSection(String footerSection) {
		this.footerSection = footerSection;
	}
	public String getUseCategory() {
		return useCategory;
	}
	public void setUseCategory(String useCategory) {
		this.useCategory = useCategory;
	}
	public String getUseWrite() {
		return useWrite;
	}
	public void setUseWrite(String useWrite) {
		this.useWrite = useWrite;
	}
	public String getUseReply() {
		return useReply;
	}
	public void setUseReply(String useReply) {
		this.useReply = useReply;
	}
	public String getUseFileUpload() {
		return useFileUpload;
	}
	public void setUseFileUpload(String useFileUpload) {
		this.useFileUpload = useFileUpload;
	}
	public String getUseComment() {
		return useComment;
	}
	public void setUseComment(String useComment) {
		this.useComment = useComment;
	}
	public String getUseMovie() {
		return useMovie;
	}
	public void setUseMovie(String useMovie) {
		this.useMovie = useMovie;
	}
	public String getUseLink() {
		return useLink;
	}
	public void setUseLink(String useLink) {
		this.useLink = useLink;
	}
	public String getUseRecommend() {
		return useRecommend;
	}
	public void setUseRecommend(String useRecommend) {
		this.useRecommend = useRecommend;
	}
	public String getUseSns() {
		return useSns;
	}
	public void setUseSns(String useSns) {
		this.useSns = useSns;
	}
	public String getUseAnnmsList() {
		return useAnnmsList;
	}
	public void setUseAnnmsList(String useAnnmsList) {
		this.useAnnmsList = useAnnmsList;
	}
	public String getUseAnnmsView() {
		return useAnnmsView;
	}
	public void setUseAnnmsView(String useAnnmsView) {
		this.useAnnmsView = useAnnmsView;
	}
	public String getAllowFileTypes() {
		return allowFileTypes;
	}
	public void setAllowFileTypes(String allowFileTypes) {
		this.allowFileTypes = allowFileTypes;
	}
	public int getAllowFileCount() {
		return allowFileCount;
	}
	public void setAllowFileCount(int allowFileCount) {
		this.allowFileCount = allowFileCount;
	}
	public int getAllowFileSize() {
		return allowFileSize;
	}
	public void setAllowFileSize(int allowFileSize) {
		this.allowFileSize = allowFileSize;
	}	
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
	public String getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}
	public String getModifyId() {
		return modifyId;
	}
	public void setModifyId(String modifyId) {
		this.modifyId = modifyId;
	}
	public int getTotalArticles() {
		return totalArticles;
	}
	public void setTotalArticles(int totalArticles) {
		this.totalArticles = totalArticles;
	}
	public String getModifyRegDate() {
		return modifyRegDate;
	}
	public void setModifyRegDate(String modifyRegDate) {
		this.modifyRegDate = modifyRegDate;
	}
	
	public String getBoardFlagName() {
		
		if( !Validate.isEmpty(boardFlag) )
		{
			if( boardFlag.equals("PDS") )
				return "열린학습자료";
			else if( boardFlag.equals("CMT"))
				return "선생님행복마당";
			else if( boardFlag.equals("CS"))
				return "CS관리";
			else if( boardFlag.equals("CA"))
				return "창의적체험활동";
			else if( boardFlag.equals("M1"))
				return "학생참여수업";
			else if( boardFlag.equals("M2"))
				return "자유학년제·창체";
			else if( boardFlag.equals("M3"))
				return "나눔소통";			
		}
		
		return "";
	}	

}
