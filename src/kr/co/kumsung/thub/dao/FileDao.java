package kr.co.kumsung.thub.dao;

import java.util.List;
import java.util.Map;

import kr.co.kumsung.thub.domain.FileInfo;

public interface FileDao {

	/**
	 * 게시판에 등록된 파일 데이타를 가지고 온다.
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
	 * 파일을 등록한다.
	 * @param params
	 */
	public int insert(Map<String,Object> params);
	
	/**
	 * 파일을 삭제한다.
	 * @param fileId
	 */
	public void delete(int fileId);
	
}
