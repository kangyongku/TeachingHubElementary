package kr.co.kumsung.thub.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import kr.co.kumsung.thub.controller.front.thub.LearningController;
import kr.co.kumsung.thub.dao.CategoryDao;
import kr.co.kumsung.thub.domain.Category;
import kr.co.kumsung.thub.service.CategoryService;
 
public class CategoryServiceImpl implements CategoryService{
	private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private CategoryDao categoryDao;

	@Override
	public Category getCategory(String category) {
		return categoryDao.getCategory(category);
	}
	
	@Override
	public List<Category> getChildren(String category) {
		List<Category> list = new ArrayList<Category>();
		
		// 요청된 category의 depth를 가지고 온다.
		if( category != null )
		{	
			int length = category.length();
			int depth = 0;
			
			switch(length)
			{
			case 1 : 
				depth = 2;
				break;
			case 4 : 
				depth = 3;
				break;
			case 7 : 
				depth = 4;
				break;
			}
			
			if( depth > 0 )
			{
				Map<String,Object> params = new HashMap<String,Object>();
				
				params.put("category" , category);
				params.put("depth" , depth);
				
				list = categoryDao.getChildren(params);
				
			}
		}
		
		return list;
	}

	@Override
	public List<Category> getChildren(String category, int depth) {
		List<Category> list = new ArrayList<Category>();
	
		if( depth > 0 )
			{
				Map<String,Object> params = new HashMap<String,Object>();
				
				params.put("category" , category);
				params.put("depth" , depth);
				
				list = categoryDao.getChildren(params);
			
			}
		
		
		return list;
	}
	
	@Override
	public List<Category> getChildrenA001(String category, int depth) {
		List<Category> list = new ArrayList<Category>();
	
		if( depth > 0 )
			{
				Map<String,Object> params = new HashMap<String,Object>();
				
				params.put("category" , category);
				params.put("depth" , depth);
				
				list = categoryDao.getChildrenA001(params);
			
			}
		
		
		return list;
	}
	
	@Override
	public List<Category> getChildrenA001(String category) {
		List<Category> list = new ArrayList<Category>();
		
		// 요청된 category의 depth를 가지고 온다.
		if( category != null )
		{	
			int length = category.length();
			int depth = 0;
			
			switch(length)
			{
			case 1 : 
				depth = 2;
				break;
			case 4 : 
				depth = 3;
				break;
			case 7 : 
				depth = 4;
				break;
			}
			
			if( depth > 0 )
			{
				Map<String,Object> params = new HashMap<String,Object>();
				
				params.put("category" , category);
				params.put("depth" , depth);
				
				list = categoryDao.getChildrenA001(params);
				
			}
		}
		
		return list;
	}
	@Override
	public List<Category> getChildrenAll(String category) {
		
		List<Category> list = new ArrayList<Category>();
		
		// 요청된 category의 depth를 가지고 온다.
		if( category != null )
		{	
			int length = category.length();
			int depth = 0;
			
			switch(length)
			{
			case 1 : 
				depth = 2;
				break;
			case 4 : 
				depth = 3;
				break;
			case 7 : 
				depth = 4;
				break;
			}
			
			if( depth > 0 )
			{
				Map<String,Object> params = new HashMap<String,Object>();
				
				params.put("category" , category);
				params.put("depth" , depth);
				
				list = categoryDao.getChildrenAll(params);
			}
		}
		
		return list;
	}
	
	@Override
	public List<Category> getChildrenWithDataCount(String category , Map<String,Object> params) {
		List<Category> list = new ArrayList<Category>();
		
		// 요청된 category의 depth를 가지고 온다.
		if( category != null )
		{	
			int length = category.length();
			int depth = 0;
			
			switch(length)
			{
			case 1 : 
				depth = 2;
				break;
			case 4 : 
				depth = 3;
				break;
			case 7 : 
				depth = 4;
				break;
			}
			
			if( depth > 0 )
			{	
				Map<String,Object> newParams = new HashMap<String,Object>();
				newParams.put("findCategory" , params.get("category"));
				newParams.put("bookId" , params.get("bookId"));
				newParams.put("findBookId" , params.get("bookId"));
				newParams.put("dataType" , params.get("dataType"));
				newParams.put("findTypeCategory" , params.get("findTypeCategory"));
				newParams.put("findParentUnitId" , params.get("findParentUnitId"));
				newParams.put("skip" , params.get("skip"));
				newParams.put("pageSize" , params.get("pageSize"));
				newParams.put("category" , category);
				newParams.put("depth" , depth);
				
				String findTypeCategoryForChildren = (String) params.get("findTypeCategory");
				if( findTypeCategoryForChildren != null 
						&& findTypeCategoryForChildren.length() > 4 )
					newParams.put("findTypeCategory" , findTypeCategoryForChildren.substring(0,4));
				list = categoryDao.getChildrenWithDataCount(newParams);
			}
		}
		
		return list;
	}
	
	@Override
	public List<Category> getList(Map<String, Object> params) {
		return categoryDao.getList(params);
	}
	
	@Override
	public String getMaxCategory(Map<String, Object> params) {
		return categoryDao.getMaxCategory(params);
	}

	@Override
	public void insert(Map<String, Object> params) {
		categoryDao.insert(params);
	}

	@Override
	public void update(Map<String, Object> params) {
		categoryDao.update(params);
	}

	@Override
	public List<Category> getSecondaryAllList(String category) {
		return categoryDao.getSecondaryAllList(category);
	}
	
	@Override
	public String getCategoryPath(String category){
		return categoryDao.getCategoryPath(category);
	}

	@Override
	public void updateSequence(Map<String, Object> params) {
		categoryDao.updateSequence(params);
	}

	@Override
	public List<Category> getChildrenWithMultimediaCount(String category) {
		List<Category> list = new ArrayList<Category>();
		
		// 요청된 category의 depth를 가지고 온다.
		if( category != null )
		{	
			int length = category.length();
			int depth = 0;
			
			switch(length)
			{
			case 1 : 
				depth = 2;
				break;
			case 4 : 
				depth = 3;
				break;
			case 7 : 
				depth = 4;
				break;
			}
			
			if( depth > 0 )
			{
				Map<String,Object> params = new HashMap<String,Object>();
				
				params.put("category" , category);
				params.put("depth" , depth);
				
				list = categoryDao.getChildrenWithMultimediaCount(params);
			}
		}
		
		return list;
	}

	@Override
	public List<Category> getChildrenWithLeaningCount(String category , Map<String,Object> params) {
		List<Category> list = new ArrayList<Category>();
		//logger.info("getChildrenWithLeaningCountcategory["+category+"]");
		
		// 요청된 category의 depth를 가지고 온다.
		if( category != null && category.length() > 4 )
		{
			int length = category.length();
			
			int depth = 0;
			
			switch(length)
			{
			case 1 : 
				depth = 2;
				break;
			case 4 : 
				depth = 3;
				break;
			case 7 : 
				depth = 4;				
			case 10 : 
				depth = 4;
				break;
			}
			if( depth > 0 )
			{	
				Map<String,Object> newParams = new HashMap<String,Object>();
				newParams.put("bookId" , params.get("bookId"));
				newParams.put("category" , category.substring(0,7));
				newParams.put("depth" , 4);
				
				
				String findTypeCategoryForChildren = (String) params.get("findTypeCategory");
				//logger.info("findTypeCategoryForChildren["+findTypeCategoryForChildren+"]");
				if( findTypeCategoryForChildren != null 
						&& findTypeCategoryForChildren.length() > 7 )
					newParams.put("category" , findTypeCategoryForChildren.substring(0,7));
				
				list = categoryDao.getChildrenWithLeaningCount(newParams);
			}
		}
		
		return list;
	}
	
	
	
	@Override
	public List<Category> getChildrenWithHeadwordCount(String category) {
		
		List<Category> list = new ArrayList<Category>();
		
		// 요청된 category의 depth를 가지고 온다.
		if( category != null )
		{	
			int length = category.length();
			int depth = 0;
			
			switch(length)
			{
			case 1 : 
				depth = 2;
				break;
			case 4 : 
				depth = 3;
				break;
			case 7 : 
				depth = 4;
				break;
			}
			
			if( depth > 0 )
			{
				Map<String,Object> params = new HashMap<String,Object>();
				
				params.put("category" , category);
				params.put("depth" , depth);
				
				list = categoryDao.getChildrenWithHeadwordCount(params);
			}
		}
		
		return list;
	}

	@Override
	public List<Category> getCategoryInCDDownload(String findCategory) {
		return categoryDao.getCategoryInCDDownload(findCategory);
	}
	
}
