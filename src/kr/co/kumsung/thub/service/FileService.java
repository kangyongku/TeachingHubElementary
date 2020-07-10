package kr.co.kumsung.thub.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import kr.co.kumsung.thub.domain.FileInfo;

public interface FileService {

	/**
	 * 게시물의 첨부파일 리스트를 가지고 온다.
	 * @param fileIds
	 * @return
	 */
	public List<FileInfo> getBoardFiles(String attachment);
	
	/**
	 * 파일 정보를 가지고 온다.
	 * @param fileId
	 * @return
	 */
	public FileInfo getFile(int fileId);
	
	/**
	 * 게시판 전용 파일을 업로드 한다.
	 * @param upfile
	 * @param serverFilePath
	 * @param folderPrefix
	 * @return
	 */
	public FileInfo upload(CommonsMultipartFile upfile, String serverFilePath,
			String prefix , int boardId);
	
	/**
	 * 게시판 전용 파일을 업로드 한다.
	 * @param upfile
	 * @param serverFilePath
	 * @param folderPrefix
	 * @return
	 */
	public FileInfo upload(CommonsMultipartFile upfile , String serverFilePath  , String folderPrefix);
	
	/**
	 * 일반 파일을 등록한다.
	 * @param upfile
	 * @param serverFilePath
	 * @param folderPrefix
	 * @return
	 */
	public FileInfo upload(MultipartFile upfile, String serverFilePath , String folderPrefix);
	
	/**
	 * 첨부파일의 확장자가 유효한지 체크한다.
	 * @param allowFileTypes
	 * @param fileExt
	 * @return
	 */
	public boolean checkAllowFileTypes(String allowFileTypes , String fileExt);
	
	/**
	 * 파일을 등록한다.
	 * @param params
	 * @return
	 */
	public int insert(Map<String,Object> params);
	
	/**
	 * 파일을 삭제한다.
	 * @param fileId
	 */
	public void delete(int fileId);
	
	/**
	 * 해당 경로의 파일을 삭제한다.
	 * @param filePath
	 */
	public void delete(String filePath);
	
	/**
	 * 해당경로의 파일을 복사한다.
	 * @param params
	 * @return
	 */
	public FileInfo copy(String filePath, String serverFilePath , String prefix);
	
	/**
	 * 썸네일을 만든다.
	 * @param source
	 * @param fileExt
	 */
	public void makeThumbail(String source , String fileExt);
}
