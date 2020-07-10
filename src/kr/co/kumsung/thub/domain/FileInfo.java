package kr.co.kumsung.thub.domain;

import kr.co.kumsung.thub.util.Validate;

/**
 * 파일정보 Domain Object
 * @author mikelim
 *
 */
public class FileInfo {
	
	private int fileId;
	private int boardId;	
	private String prefix;	
	private String fileName;
	private String originName;	
	private String filePath;
	private String absolutePath;
	private long fileSize;
	private String mimeType;
	private int hits;
	private String regDate;
	
	public int getFileId() {
		return fileId;
	}
	public void setFileId(int fileId) {
		this.fileId = fileId;
	}
	public int getBoardId() {
		return boardId;
	}
	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getOriginName() {
		return originName;
	}
	public void setOriginName(String originName) {
		this.originName = originName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getAbsolutePath() {
		return absolutePath;
	}
	public void setAbsolutePath(String absolutePath) {
		this.absolutePath = absolutePath;
	}
	public long getFileSize() {
		return fileSize;
	}
	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}
	public String getMimeType() {
		return mimeType;
	}
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
	public int getHits() {
		return hits;
	}
	public void setHits(int hits) {
		this.hits = hits;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getFileIconExt()
	{
		String allowedFileExt = "jpg,png,gif,xls,ppt,doc,hwp,zip,swf,pdf";
		String fileExt = "";
		
		if( !Validate.isEmpty(fileName) )
		{
			fileExt = fileName.substring(fileName.lastIndexOf(".") + 1 , fileName.length()).toLowerCase();
		}
		
		if( fileExt.length() > 3 )
			fileExt = fileExt.substring(0 , 3);
		
		if( allowedFileExt.indexOf(fileExt) == -1 )
			return "default";
		else
		{
			if( fileExt.equals("jpg") || fileExt.equals("png") || fileExt.equals("gif"))
				return "image";
		}
		
		return fileExt;
	}
}
