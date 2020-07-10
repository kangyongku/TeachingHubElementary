package kr.co.kumsung.thub.service.impl;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.swing.ImageIcon;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import net.coobird.thumbnailator.name.Rename;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import kr.co.kumsung.thub.dao.FileDao;
import kr.co.kumsung.thub.domain.BoardConfig;
import kr.co.kumsung.thub.domain.FileInfo;
import kr.co.kumsung.thub.service.BoardService;
import kr.co.kumsung.thub.service.FileService;
import kr.co.kumsung.thub.util.Common;
import kr.co.kumsung.thub.util.DateUtil;

public class FileServiceImpl implements FileService{

	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private FileDao fileDao;
	
	@Autowired
	private BoardService boardService;
	
	@Override
	public List<FileInfo> getBoardFiles(String attachment) {
		return fileDao.getBoardFiles(attachment);
	}
	
	@Override
	public FileInfo getFile(int fileId){
		return fileDao.getFile(fileId);
	}
	
	@Override
	public FileInfo upload(CommonsMultipartFile upfile, String serverFilePath,
			String prefix , int boardId)
	{
		BoardConfig boardConfig = new BoardConfig();
		
		if( boardId > 0 )
			boardConfig = boardService.getBoardConfig(boardId);
		
		FileInfo fileInfo = new FileInfo();
		
		// 파일이 존재한다.
		String directFilePath = upfile.getOriginalFilename();
		String fileExt = directFilePath.substring(directFilePath.lastIndexOf(".") + 1 , directFilePath.length()).toLowerCase();
		String fileName = String.format("%s.%s" , Common.uniqueString() , fileExt);
		
		String folderName = String.format("%s/%s" , prefix , DateUtil.getDate("YYYYMM"));
		String filePath = String.format("%s/%s" , serverFilePath , folderName);
		String absolutePath = String.format("/upfiles/%s/%s" , folderName , fileName);
		
		// check is directory
		File dir = new File(filePath);
		
		if( !dir.isDirectory() )
			dir.mkdirs();
		
		byte[] bytes = upfile.getBytes();
		
		try
		{
			File file = new File(filePath + "/" + fileName );
			BufferedOutputStream fos = new BufferedOutputStream( new FileOutputStream(file) );
			fos.write(bytes);
			fos.close();
		}catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
		
		if( prefix.equals("thumbnail") )
		{
			// 썸네일 이미지라면 image resize 및 crop을 진행한다.
			if( boardConfig.getBoardType().equals("WEBZINE")){
				makeThumbail(filePath + "/" + fileName , fileExt , 150 , 208);		// 웹진은 사이즈가 다르다.
			}else{
				makeThumbail(filePath + "/" + fileName , fileExt);
			}
		}
		
		fileInfo.setBoardId(boardId);
		fileInfo.setPrefix(prefix);
		fileInfo.setOriginName(directFilePath);
		fileInfo.setFileName(fileName);
		fileInfo.setFilePath(filePath + "/" + fileName);
		fileInfo.setFileSize(upfile.getSize());
		fileInfo.setMimeType(upfile.getContentType());
		fileInfo.setAbsolutePath(absolutePath);
		
		// editor가 아닌 경우 DB로 파일을 관리한다.
		if( !prefix.equals("editor") )
		{
			// make query parameters
			Map<String,Object> params = new HashMap<String,Object>();
			
			params.put("filePath" , filePath);
			params.put("boardId" , boardId);
			params.put("fileName" , fileName);
			params.put("originName" , directFilePath);
			params.put("directFilePath" , directFilePath);
			params.put("absolutePath" , absolutePath);
			params.put("mimeType" , upfile.getContentType());
			params.put("fileSize" , (upfile.getSize() / 1024));
			
			int fileId = fileDao.insert(params);
			fileInfo.setFileId(fileId);
		}
		
		return fileInfo;
	}
	
	@Override
	public FileInfo upload(CommonsMultipartFile upfile, String serverFilePath,
			String prefix)
	{

		return upload(upfile , serverFilePath , prefix , 0);
	}
	
	@Override
	public FileInfo upload(MultipartFile upfile, String serverFilePath , String prefix)
	{
		FileInfo fileInfo = new FileInfo();
		
		// 파일이 존재한다.
		String direcFilePath = upfile.getOriginalFilename();
		System.out.print("direcFilePath:"+direcFilePath);
		String fileExt = direcFilePath.substring(direcFilePath.lastIndexOf(".") + 1 , direcFilePath.length()).toLowerCase();
		String fileName = String.format("%s.%s" , Common.uniqueString() , fileExt);
		//String fileName =direcFilePath;
		
		String folderName = String.format("%s/%s" , prefix , DateUtil.getDate("YYYYMM"));
		String filePath = String.format("%s/%s" , serverFilePath , folderName);
		String absolutePath = String.format("/upfiles/%s/%s" , folderName , fileName);
		
		// check is directory
		File dir = new File(filePath);
		
		if( !dir.isDirectory() )
			dir.mkdirs();
		
		try
		{
			byte[] bytes = upfile.getBytes();
			
			File file = new File(filePath + "/" + fileName );
			BufferedOutputStream fos = new BufferedOutputStream( new FileOutputStream(file) );
			fos.write(bytes);
			fos.close();
			
		}catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
		
		if( prefix.equals("thumbnail") || prefix.equals("headword") )
		{
			// 썸네일 이미지라면 image resize 및 crop을 진행한다.
			makeThumbail(filePath + "/" + fileName , fileExt);
		}
		
		fileInfo.setPrefix(prefix);
		fileInfo.setOriginName(direcFilePath);
		fileInfo.setFileName(fileName);
		fileInfo.setFilePath(filePath + "/" + fileName);
		fileInfo.setFileSize(upfile.getSize());
		fileInfo.setMimeType(upfile.getContentType());
		fileInfo.setAbsolutePath(absolutePath);
		
		return fileInfo;
	}

	@Override
	public boolean checkAllowFileTypes(String allowFileTypes, String fileExt) {
		
		// 첨부파일 저장 가능한 확장자를 체크한다.
		boolean isChecked = false;
		String[] types = allowFileTypes.toLowerCase().split("\\,");
		
		for(String type : types)
		{
			if( fileExt.equals(type) )
				isChecked = true;
		}
		
		return isChecked;
	}
	
	@Override
	public int insert(Map<String,Object> params)
	{
		return fileDao.insert(params);
	}

	@Override
	public void delete(int fileId) {
		fileDao.delete(fileId);
	}
	
	@Override
	public void delete(String filePath){
		
		File file = new File(filePath);
		
		if( file.isFile() )
		{
			// 파일이라면 삭제.
			file.delete();
		}
	}
	
	@Override
	public FileInfo copy(String direcFilePath, String serverFilePath , String prefix){
		
		FileInfo fileInfo = new FileInfo();
		// 파일이 존재한다.
		String fileExt = direcFilePath.substring(direcFilePath.lastIndexOf(".") + 1 , direcFilePath.length()).toLowerCase();
		String fileName = String.format("%s.%s" , Common.uniqueString() , fileExt);
		
		String folderName = String.format("%s/%s" , prefix , DateUtil.getDate("YYYYMM"));
		String filePath = String.format("%s/%s" , serverFilePath , folderName);
		String absolutePath = String.format("/upfiles/%s/%s" , folderName , fileName);
		
		// check is directory
		File dir = new File(filePath);
		
		if( !dir.isDirectory() )
			dir.mkdirs();
		
		byte data[] = null;
		
		try
		{
			File file = new File(direcFilePath);
			FileInputStream fis =  new FileInputStream(file); 
			data = new byte[fis.available()];
			while(fis.read(data)!=-1){;}
			
			File copyFile = new File(filePath + "/" + fileName );
			BufferedOutputStream fos = new BufferedOutputStream( new FileOutputStream(copyFile) );
			fos.write(data);
			
			fis.close();
			fos.close();
		}catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
		fileInfo.setPrefix(prefix);
		fileInfo.setFileName(fileName);
		fileInfo.setFilePath(filePath + "/" + fileName);
		fileInfo.setAbsolutePath(absolutePath);
		
		return fileInfo;
		
	}
	
	/**
	 * 썸네일을 만든다. Cropping을 한다.
	 */
	public void makeThumbail(String source , String fileExt)
	{
		try {
			
			Image img = new ImageIcon(source).getImage();
			
			int height = img.getHeight(null);
			int width = img.getWidth(null);
			
			/*
			BufferedImage originalImage = ImageIO.read(new File(source));
			int height = originalImage.getHeight();
			int width = originalImage.getWidth();
			*/
			
			System.out.println("##############" + height);
			System.out.println("##############" + width);
			
			if( height > 180 || width > 180)
			{	
				Thumbnails
				.of(new File(source))
				.size(180,180)
				.outputQuality(1.0f)
				.outputFormat(fileExt).toFiles(Rename.NO_CHANGE);
				
				Thumbnails
				.of(new File(source))
				.sourceRegion(Positions.CENTER, 150, 115)
				.size(150,115)
				.outputQuality(1.0f)
				.outputFormat(fileExt).toFiles(Rename.NO_CHANGE);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 썸네일을 만든다. Cropping을 한다. (주어진 사이즈로 cropping)
	 */
	public void makeThumbail(String source , String fileExt , int width , int height)
	{
		try {
			
			Image img = new ImageIcon(source).getImage();
			
			int m_height = img.getHeight(null);
			int m_width = img.getWidth(null);
			
			/*
			BufferedImage originalImage = ImageIO.read(new File(source));
			int m_height = originalImage.getHeight();
			int m_width = originalImage.getWidth();
			*/
			
			if( m_height > height + 80 || m_width > width + 80)
			{			
				Thumbnails
				.of(new File(source))
				.size(width + 80 , height + 80)
				.outputQuality(1.0f)
				.outputFormat(fileExt).toFiles(Rename.NO_CHANGE);
				
				Thumbnails
				.of(new File(source))
				.sourceRegion(Positions.CENTER, width, height)
				.size(width,height)
				.outputQuality(1.0f)
				.outputFormat(fileExt).toFiles(Rename.NO_CHANGE);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
