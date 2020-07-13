package kr.co.kumsung.thub.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import kr.co.kumsung.thub.domain.Category;
import kr.co.kumsung.thub.domain.CurriculumContact;
import kr.co.kumsung.thub.exception.CommonException;
import kr.co.kumsung.thub.service.CategoryService;
import kr.co.kumsung.thub.service.CurriculumContactService;
import kr.co.kumsung.thub.setting.Constants;
import kr.co.kumsung.thub.util.Common;
import kr.co.kumsung.thub.util.Pagination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 교과정보관리
 * @author jhg
 * @date 0814~
 */
@Component("adminCurriculumContactControll")
@Controller
@RequestMapping("/admin/curriculumContact")
public class CurriculumContactController {
	
	private static final int PAGE_SIZE = 10;
	private static final int BLOCK_SIZE = 10;

	@Autowired
	private CategoryService categoryService;
	
	final String categoryCode = Constants.SMART_CATEGORY;
	
	
	@Autowired
	private CurriculumContactService curriculumContactService;
	
	/**
	 * 교과별담당자등록
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/curriculumContactList.do")
	public String curriculumContactList(HttpServletRequest request , Model model) throws Exception
	{
		// get parameters
		int pg = Common.getParameter(request, "pg", 1);
		int skip = (pg - 1) * PAGE_SIZE;
		String mode = Common.getParameter(request, "mode", "add");
		String category = Common.getParameter(request, "category", "");
		String findCategory = Common.getParameter(request, "findCategory", "");
		int curriculumId = Common.getParameter(request, "curriculumId", 0);
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("skip", skip);
		params.put("pageSize", PAGE_SIZE);
		params.put("category", category);
		params.put("findCategory", findCategory);
		params.put("findFlag", "A");
		
		List<Category> categories = categoryService.getChildren(Constants.LEARNING_CATEGORY);
		
		int totalCurriculumContact = curriculumContactService.getCurriculumContactTotalList(params);
		// set pagination
		Pagination pagination = new Pagination(pg, PAGE_SIZE, BLOCK_SIZE, totalCurriculumContact);
		pagination.setDelimiter("");
		pagination.setPrevLink("Prev");
		pagination.setNextLink("Next");
		int curriculumContactNum = totalCurriculumContact - skip;
		List<CurriculumContact> curriculumContactList = curriculumContactService.getCurriculumContactList(params);
		
		CurriculumContact curriculumContact = new CurriculumContact();
		if(curriculumId > 0 && mode.equals("modify")){
			curriculumContact = curriculumContactService.getCurriculumContactDetail(curriculumId);
		}
		
		model.addAttribute("categoryCode", categoryCode);
		model.addAttribute("categories", categories);
		model.addAttribute("category", category);
		model.addAttribute("findCategory", findCategory);
		model.addAttribute("curriculumContact", curriculumContact);
		model.addAttribute("curriculumContactNum", curriculumContactNum);
		model.addAttribute("curriculumContactList", curriculumContactList);
		model.addAttribute("curriculumId", curriculumId);
		
		model.addAttribute("mode", mode);
		model.addAttribute("pg", pg);
		model.addAttribute("paging", pagination.print());
		model.addAttribute("pathTitle", "교과별담당자설정");
		model.addAttribute("path", "교과별담당자설정");
		
		return "admin/curriculumContact/curriculumContactList";
	}
	
	/**
	 * 교과별 담당자 등록
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="curriculumContactAction.do" , method=RequestMethod.POST)
	public String curriculumContactAction(HttpServletRequest request, Model model) throws Exception
	{
		String mode = Common.getParameter(request, "mode", "add");
		
		int curriculumId = Common.getParameter(request, "curriculumId", 0);
		String category = Common.getParameter(request, "category", "");
		String email = Common.getParameterNoXSS(request, "email", "");
		String linkUrl = Common.getParameter(request, "linkUrl", "");
		String flag = Common.getParameter(request, "flag", "A");
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("curriculumId", curriculumId);
		params.put("category", category);
		params.put("email", email);
		params.put("linkUrl", linkUrl);
		params.put("flag", "A");
		params.put("findFlag", "A");
		
		int curriculumContactDup = curriculumContactService.getCurriculumContactDup(params);
		
		if( curriculumContactDup > 0)
			throw new CommonException("중복된 학교급/교과가 존재합니다." , CommonException.HISTORY_BACK);
		
		if(mode.equals("add")){
			curriculumContactService.insertCurriculumContact(params);
		}else if(mode.equals("modify")){
			curriculumContactService.updateCurriculumContact(params);
		}else if(mode.equals("delete")){
			curriculumContactService.deleteCurriculumContact(curriculumId);
		}
		
		return "redirect:curriculumContactList.do";
	}
	
	/**
	 * 교과별담당자등록
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/curriculumContactList2.do")
	public String curriculumContactList2(HttpServletRequest request , Model model) throws Exception
	{
		// get parameters
		int pg = Common.getParameter(request, "pg", 1);
		int skip = (pg - 1) * PAGE_SIZE;
		String mode = Common.getParameter(request, "mode", "add");
		String category = Common.getParameter(request, "category", "");
		String findCategory = Common.getParameter(request, "findCategory", "");
		int curriculumId = Common.getParameter(request, "curriculumId", 0);
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("skip", skip);
		params.put("pageSize", PAGE_SIZE);
		params.put("category", category);
		params.put("findCategory", findCategory);
		params.put("findFlag", "B");
		
		List<Category> categories = categoryService.getChildren(Constants.SMART_CATEGORY);
		
		int totalCurriculumContact = curriculumContactService.getCurriculumContactTotalList(params);
		// set pagination
		Pagination pagination = new Pagination(pg, PAGE_SIZE, BLOCK_SIZE, totalCurriculumContact);
		pagination.setDelimiter("");
		pagination.setPrevLink("Prev");
		pagination.setNextLink("Next");
		int curriculumContactNum = totalCurriculumContact - skip;
		List<CurriculumContact> curriculumContactList = curriculumContactService.getCurriculumContactList(params);
		
		CurriculumContact curriculumContact = new CurriculumContact();
		if(curriculumId > 0 && mode.equals("modify")){
			curriculumContact = curriculumContactService.getCurriculumContactDetail(curriculumId);
		}
		
		model.addAttribute("categoryCode", categoryCode);
		model.addAttribute("categories", categories);
		model.addAttribute("category", category);
		model.addAttribute("findCategory", findCategory);
		model.addAttribute("curriculumContact", curriculumContact);
		model.addAttribute("curriculumContactNum", curriculumContactNum);
		model.addAttribute("curriculumContactList", curriculumContactList);
		model.addAttribute("curriculumId", curriculumId);
		
		model.addAttribute("mode", mode);
		model.addAttribute("pg", pg);
		model.addAttribute("paging", pagination.print());
		model.addAttribute("pathTitle", "교과별담당자설정");
		model.addAttribute("path", "교과별담당자설정");
		
		return "admin/curriculumContact/curriculumContactList2";
	}
	
	/**
	 * 교과별 담당자 등록
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="curriculumContactAction2.do" , method=RequestMethod.POST)
	public String curriculumContactAction2(HttpServletRequest request, Model model) throws Exception
	{
		String mode = Common.getParameter(request, "mode", "add");
		
		int curriculumId = Common.getParameter(request, "curriculumId", 0);
		String category = Common.getParameter(request, "category", "");
		String email = Common.getParameterNoXSS(request, "email", "");
		String linkUrl = Common.getParameter(request, "linkUrl", "");
		String flag = Common.getParameter(request, "flag", "B");
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("curriculumId", curriculumId);
		params.put("category", category);
		params.put("email", email);
		params.put("linkUrl", linkUrl);
		params.put("flag", "B");
		params.put("findFlag", "B");
		
		int curriculumContactDup = curriculumContactService.getCurriculumContactDup(params);
		
		if( curriculumContactDup > 0)
			throw new CommonException("중복된 학교급/교과가 존재합니다." , CommonException.HISTORY_BACK);
		
		if(mode.equals("add")){
			curriculumContactService.insertCurriculumContact(params);
		}else if(mode.equals("modify")){
			curriculumContactService.updateCurriculumContact(params);
		}else if(mode.equals("delete")){
			curriculumContactService.deleteCurriculumContact(curriculumId);
		}
		
		return "redirect:curriculumContactList2.do";
	}
}
