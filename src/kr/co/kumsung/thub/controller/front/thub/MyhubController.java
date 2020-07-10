package kr.co.kumsung.thub.controller.front.thub;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import kr.co.kumsung.thub.domain.Book;
import kr.co.kumsung.thub.domain.Category;
import kr.co.kumsung.thub.domain.ChoiceBook;
import kr.co.kumsung.thub.domain.Customer;
import kr.co.kumsung.thub.domain.Data;
import kr.co.kumsung.thub.domain.Folder;
import kr.co.kumsung.thub.domain.Member;
import kr.co.kumsung.thub.domain.Scrap;
import kr.co.kumsung.thub.exception.CommonException;
import kr.co.kumsung.thub.service.CategoryService;
import kr.co.kumsung.thub.service.CustomerService;
import kr.co.kumsung.thub.service.LearningService;
import kr.co.kumsung.thub.service.MemberService;
import kr.co.kumsung.thub.service.ScrapService;
import kr.co.kumsung.thub.setting.Constants;
import kr.co.kumsung.thub.util.Common;
import kr.co.kumsung.thub.util.FrontPagination;
import kr.co.kumsung.thub.util.Validate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Component("thubMyhubController")
@Controller
@RequestMapping("/myhub")
public class MyhubController {

	@Autowired
	private ScrapService scrapService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private LearningService learningService;
	
	/**
	 * 나의 교과설정
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/setting.do")
	public String setting(HttpServletRequest request , Model model) throws Exception
	{
		// get session
		HttpSession session = (HttpSession) request.getSession();
		Member member = (Member) session.getAttribute("member");
		
		if( member == null )
			throw new CommonException("로그인 후 이용가능합니다." , CommonException.HISTORY_BACK);
		
		// get data
		List<ChoiceBook> books = (List<ChoiceBook>) memberService.getMyBooks(member.getUserId());
		Category gradeCategory = (Validate.isEmpty(member.getSubCategory())) ? new Category() : (Category) categoryService.getCategory(member.getSubCategory().substring(0,4));
		Category classCategory = (Validate.isEmpty(member.getSubCategory())) ? new Category() : (Category) categoryService.getCategory(member.getSubCategory());
		
		List<Category> subjects = ( Validate.isEmpty(member.getSubCategory())) ? new ArrayList<Category>() : (List<Category>) categoryService.getChildren(member.getSubCategory().substring(0,4));
		List<Book> settingBooks = ( Validate.isEmpty(member.getSubCategory())) ? new ArrayList<Book>() : (List<Book>) learningService.getBookListByCategory(subjects.get(0).getCategory());
		
		List<Category> gradeCategories = (List<Category>) categoryService.getChildrenAll(Constants.LEARNING_CATEGORY);
		List<Category> classCategories = (Validate.isEmpty(member.getSubCategory())) ? new ArrayList<Category>() : (List<Category>) categoryService.getChildrenAll(member.getSubCategory().substring(0,4));
		
		// assign
		model.addAttribute("beforePath", "마이허브");
		model.addAttribute("currentPath", "교과설정");
		
		model.addAttribute("books", books);
		model.addAttribute("gradeCategory", gradeCategory);
		model.addAttribute("classCategory", classCategory);
		
		model.addAttribute("subjects", subjects);
		model.addAttribute("settingBooks", settingBooks);
		
		model.addAttribute("gradeCategories", gradeCategories);
		model.addAttribute("classCategories", classCategories);
		
		return "front/thub/myhub/setting";
	}
	
	/**
	 * 나의 교과설정 변경
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/mysetting.do")
	public String mysetting(HttpServletRequest request , Model model) throws Exception
	{
		// get session
		HttpSession session = (HttpSession) request.getSession();
		Member member = (Member) session.getAttribute("member");
		
		if( member == null )
			throw new CommonException("로그인 후 이용가능합니다." , CommonException.HISTORY_BACK);
		
		// get data
		List<ChoiceBook> books = (List<ChoiceBook>) memberService.getMyBooks(member.getUserId());
		Category gradeCategory = (Validate.isEmpty(member.getSubCategory())) ? new Category() : (Category) categoryService.getCategory(member.getSubCategory().substring(0,4));
		Category classCategory = (Validate.isEmpty(member.getSubCategory())) ? new Category() : (Category) categoryService.getCategory(member.getSubCategory());
		
		List<Category> subjects = ( Validate.isEmpty(member.getSubCategory())) ? new ArrayList<Category>() : (List<Category>) categoryService.getChildrenA001(member.getSubCategory().substring(0,4));
		List<Book> settingBooks = ( Validate.isEmpty(member.getSubCategory())) ? new ArrayList<Book>() : (List<Book>) learningService.getBookListByCategory(subjects.get(0).getCategory());
		
		List<Category> gradeCategories = (List<Category>) categoryService.getChildrenAll(Constants.LEARNING_CATEGORY);
		List<Category> classCategories = (Validate.isEmpty(member.getSubCategory())) ? new ArrayList<Category>() : (List<Category>) categoryService.getChildrenAll(member.getSubCategory().substring(0,4));
		
		// assign
		model.addAttribute("beforePath", "마이허브");
		model.addAttribute("currentPath", "교과설정");
		
		model.addAttribute("books", books);
		model.addAttribute("gradeCategory", gradeCategory);
		model.addAttribute("classCategory", classCategory);
		
		model.addAttribute("subjects", subjects);
		model.addAttribute("settingBooks", settingBooks);
		
		model.addAttribute("gradeCategories", gradeCategories);
		model.addAttribute("classCategories", classCategories);
		
		return "front/thub/myhub/setting";
	}
	
	/**
	 * 나의 교과설정 상세
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/settingDetail.do")
	public String settingDetail(HttpServletRequest request , Model model) throws Exception
	{
		// get session
		HttpSession session = (HttpSession) request.getSession();
		Member member = (Member) session.getAttribute("member");
		
		if( member == null )
			throw new CommonException("로그인 후 이용가능합니다." , CommonException.HISTORY_BACK);
		
		// get data
		List<ChoiceBook> books = (List<ChoiceBook>) memberService.getMyBooks(member.getUserId());
		Category gradeCategory = (Validate.isEmpty(member.getSubCategory())) ? new Category() : (Category) categoryService.getCategory(member.getSubCategory().substring(0,4));
		Category classCategory = (Validate.isEmpty(member.getSubCategory())) ? new Category() : (Category) categoryService.getCategory(member.getSubCategory());
		List<Category> gradeCategories = (List<Category>) categoryService.getChildrenAll(Constants.LEARNING_CATEGORY);
		List<Category> classCategories = (Validate.isEmpty(member.getSubCategory())) ? new ArrayList<Category>() : (List<Category>) categoryService.getChildrenAll(member.getSubCategory().substring(0,4));
		
		// assign
		model.addAttribute("beforePath", "마이허브");
		model.addAttribute("currentPath", "교과설정");
		
		model.addAttribute("books", books);
		model.addAttribute("gradeCategory", gradeCategory);
		model.addAttribute("classCategory", classCategory);
		model.addAttribute("gradeCategories", gradeCategories);
		model.addAttribute("classCategories", classCategories);
		
		return "front/thub/myhub/settingDetail";
	}
	
	/**
	 * 나의 교과설정 상세
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/saveSubCategory.do" , method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> saveSubCategory(HttpServletRequest request , Model model) throws Exception
	{
		Map<String,Object> result = new HashMap<String,Object>();
		
		// get session
		HttpSession session = (HttpSession) request.getSession();
		Member member = (Member) session.getAttribute("member");
		
		if( member == null )
		{
			result.put("result" , "FAILURE");
			result.put("resultType" , "LOGIN");
			result.put("msg" , "로그인 정보가 존재하지 않습니다.");
			return result;
		}
		
		// get parameter
		String subCategory = Common.getParameter(request, "subCategory", "");
		
		if( Validate.isEmpty(subCategory) )
		{
			result.put("result" , "FAILURE");
			result.put("resultType" , "PARAMETER");
			result.put("msg" , "정상적인 파라미터가 아닙니다.");
			return result;
		}
		
		// make query parameter
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("subCategory" , subCategory);
		params.put("userId" , member.getUserId());
		
		// update data
		memberService.updateSubCategory(params);
		
		member.setSubCategory(subCategory);
		
		// session 갱신
		session.setAttribute("member", member);
		
		result.put("result" , "SUCCESS");
		
		return result;
	}
	
	/**
	 * 스크랩 관리
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/scrap.do")
	public String scrap(HttpServletRequest request , Model model) throws Exception
	{
		final int PAGE_SIZE = 10;
		final int BLOCK_SIZE = 10;
		
		// get session
		HttpSession session = (HttpSession) request.getSession();
		Member member = (Member) session.getAttribute("member");
		
		if( member == null )
			throw new CommonException("로그인 후 이용가능합니다." , CommonException.HISTORY_BACK);
		
		// get parameters
		int pg = Common.getParameter(request, "pg", 1);
		int skip = (pg - 1) * PAGE_SIZE;
		int findFolder = Common.getParameter(request, "findFolder", 0);
		
		// make query parameter
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("userId" , member.getUserId());
		params.put("findFolder" , findFolder);
		params.put("skip" , skip);
		params.put("pageSize" , PAGE_SIZE);
		
		// get data
		List<Folder> folders = (List<Folder>) scrapService.getFolderList(member.getUserId());
		
		int totalCount = (Integer) scrapService.getScrapListCount(params);
		List<Scrap> scraps = (List<Scrap>) scrapService.getScrapList(params);
		
		// set pagination
		FrontPagination pagination = new FrontPagination(pg, PAGE_SIZE, BLOCK_SIZE, totalCount);
		pagination.initialize();
		
		// assign
		model.addAttribute("beforePath", "마이허브");
		model.addAttribute("currentPath", "나의 스크랩");
		
		model.addAttribute("pg", pg);
		model.addAttribute("findFolder", findFolder);
		
		model.addAttribute("folders", folders);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("scraps", scraps);
		model.addAttribute("paging", pagination.print());
		
		return "front/thub/myhub/scrap";
	}
	
	/**
	 * 나의 질문 리스트
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/questionList.do")
	public String questionList(HttpServletRequest request , Model model) throws Exception
	{
		final int PAGE_SIZE = 10;
		final int BLOCK_SIZE = 10;
		
		// get session
		HttpSession session = (HttpSession) request.getSession();
		Member member = (Member) session.getAttribute("member");
		
		if( member == null )
			throw new CommonException("로그인 하여주십시오." , CommonException.HISTORY_BACK);
		
		// get parameters
		int pg = Common.getParameter(request, "pg", 1);
		int skip = (pg - 1) * PAGE_SIZE;
		
		// make query parameters
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("findUserId" , member.getUserId());
		params.put("skip" , skip);
		params.put("pageSize" , PAGE_SIZE);
		
		int totalCount = (Integer) customerService.getCustomerTotalList(params);
		int articleNum = totalCount - skip;
		List<Customer> list = (List<Customer>) customerService.getCustomerList(params);
		
		// pagination
		FrontPagination pagination = new FrontPagination(pg , PAGE_SIZE , BLOCK_SIZE , totalCount);
		pagination.initialize();
		
		// assign
		model.addAttribute("beforePath", "마이허브");
		model.addAttribute("currentPath", "나의 질문");
		
		model.addAttribute("pg", pg);
		
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("articleNum", articleNum);
		model.addAttribute("list", list);
		model.addAttribute("paging", pagination.print());
		
		return "front/thub/myhub/questionList";
	}
	
	/**
	 * 나의 질문 상세
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/questionDetail.do")
	public String questionDetail(HttpServletRequest request , Model model) throws Exception
	{	
		// get session
		HttpSession session = (HttpSession) request.getSession();
		Member member = (Member) session.getAttribute("member");
		
		if( member == null )
			throw new CommonException("로그인 하여주십시오." , CommonException.HISTORY_BACK);
		
		// get parameters
		int customerId = Common.getParameter(request, "customerId", 0);
		int pg = Common.getParameter(request, "pg", 1);
	
		// validation check
		if( customerId == 0 )
			throw new CommonException("정상적인 접근이 아닙니다." , CommonException.HISTORY_BACK);
		
		// get data
		Customer customer = (Customer) customerService.getCustomerDetail(customerId);
		Customer answer = (Customer) customerService.getCustomerAnswer(customerId);
		
		// assign
		model.addAttribute("beforePath", "마이허브");
		model.addAttribute("currentPath", "나의 질문");
		
		model.addAttribute("pg", pg);
		model.addAttribute("customer", customer);
		model.addAttribute("answer", answer);
		
		return "front/thub/myhub/questionDetail";
	}
	
}
