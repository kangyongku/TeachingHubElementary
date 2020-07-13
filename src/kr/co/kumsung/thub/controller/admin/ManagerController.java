package kr.co.kumsung.thub.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import kr.co.kumsung.thub.domain.Category;
import kr.co.kumsung.thub.domain.Member;
import kr.co.kumsung.thub.service.CategoryService;
import kr.co.kumsung.thub.util.Common;
import kr.co.kumsung.thub.util.Validate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 공통 통합 관리 시스템
 * @author mikelim
 *
 */
@Component("adminManagerContoller")
@Controller
@RequestMapping("/admin/manager")
public class ManagerController {

	@Autowired
	private CategoryService categoryService;
	
	/**
	 * 통합코드 관리
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/uniformCode.do")
	public String uniformCode(HttpServletRequest request , Model model) throws Exception
	{	
		// assign
		model.addAttribute("pathTitle", "통합 코드 관리");
		model.addAttribute("path", "통합 코드 관리");
		
		return "admin/manager/uniformCode";
	}
	
	@RequestMapping("/uniformCodeList.do")
	public @ResponseBody Map<String,Object> uniformCodeList(HttpServletRequest request , Model model) throws Exception
	{
		Map<String,Object> result = new HashMap<String,Object>();
		
		// get parameters
		int depth = Common.getParameter(request, "depth", 0);
		String parent = Common.getParameter(request, "parent", "");
		
		// validation check
		if( depth == 0 
				|| ( depth > 1 && Validate.isEmpty(parent)))
		{
			result.put("result" , "FAILURE");
			result.put("msg" , "파라미터 에러");
			return result;
		}
		
		// make query parameters
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("depth" , depth);
		params.put("parent" , parent);
		
		// get category list
		List<Category> list = categoryService.getList(params);
		
		result.put("result" , "SUCCESS");
		result.put("depth" , depth);
		result.put("categories" , list);
		
		return result;
	}
	
	/**
	 * 통합코드 관리 처리
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/uniformCodeAction.do" , method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> uniformCodeAction(HttpServletRequest request ,  Model model) throws Exception
	{
		Map<String,Object> result = new HashMap<String,Object>();
		
		// get parameters
		String mode = Common.getParameter(request, "mode", "");
		String name = Common.getParameter(request, "name", "");
		String parent = Common.getParameter(request, "parent", "");
		String isUse = Common.getParameter(request, "isUse", "");
		int depth = Common.getParameter(request, "depth", 0);
		
		// validation check
		if( Validate.isEmpty(mode) 
				|| Validate.isEmpty(name)
				|| Validate.isEmpty(isUse)
				|| depth == 0)
		{
			result.put("result" , "FAILURE");
			result.put("msg" , "파라미터 에러");
			return result;
		}
		
		// make parameter
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("name" , name);
		params.put("parent" , parent);
		params.put("depth" , depth);
		params.put("isUse" , isUse);
		
		if( mode.equals("add") )
		{
			// get max code
			String category = "";					
			String maxCategory = categoryService.getMaxCategory(params);
			
			if( maxCategory == null )
			{
				if( depth == 1 )
					category = "A";
				else
					category = String.format("%s001" , parent);
			}
			else
			{
				if( depth == 1 )
				{
					char c = maxCategory.charAt(0);
					int asc = (int) c;
					category = String.format("%s" , (char)((asc) + 1));
				}
				else
				{	
					String n = maxCategory.substring(maxCategory.length() - 3 , maxCategory.length());
					int d = Integer.valueOf(n) + 1;
					
					category = String.format("%s%03d" ,
										parent ,
										d
									);
				}
			}
			
			// insert
			params.put("category" , category);
			categoryService.insert(params);
			
			result.put("result" , "SUCCESS");
			result.put("name" , name);
			result.put("depth" , depth);
			result.put("isUse" , isUse);
			result.put("category" , category);
		}
		else if( mode.equals("modify") )
		{
			String category = Common.getParameter(request, "category", "");
			
			if( Validate.isEmpty(category) )
			{
				result.put("result" , "FAILURE");
				result.put("msg" , "파라미터 에러");
				return result;
			}
			
			// add to params
			params.put("category" , category);
			categoryService.update(params);
			
			result.put("result" , "SUCCESS");
			result.put("name" , name);
			result.put("depth" , depth);
			result.put("isUse" , isUse);
			result.put("category" , category);
		}
		
		return result;
	}
	
	@RequestMapping("/category.do")
	public @ResponseBody Map<String,Object> category() throws Exception
	{
		Map<String,Object> result = new HashMap<String,Object>();
		
		List<Category> list = categoryService.getChildren("A");
		
		result.put("list" , list);
		
		return result;
	}
	
	@RequestMapping("changeCategoryOrd.do")
	public @ResponseBody Map<String,Object> changeCategoryOrd(HttpServletRequest request , Model model) throws Exception
	{
		Map<String,Object> result = new HashMap<String,Object>();
		
		// session check
		HttpSession session = (HttpSession) request.getSession();
		Member adminMember = (Member) session.getAttribute("adminMember");
		
		if( adminMember.getAuthType().equals("S") )
		{
			// get parameters
			String categories = Common.getParameter(request, "categories", "");
			
			if( Validate.isEmpty(categories) )
			{
				result.put("result" , "FAILURE");
				result.put("msg" , "파라미터 에러.");
				return result;
			}
			
			int sequence = 0;
			String[] tmp = categories.split("\\,");
			
			for(String category : tmp)
			{
				Map<String,Object> params = new HashMap<String,Object>();
				
				params.put("category" , category);
				params.put("sequence" , sequence);
				
				categoryService.updateSequence(params);
				
				sequence++;
			}
		}
		else
		{
			result.put("result" , "FAILURE");
			result.put("msg" , "변경권한은 슈퍼관리자에게만 있습니다.");
			return result;
		}
		
		// assign
		result.put("result" , "SUCCESS");
		
		return result;
	}
}
