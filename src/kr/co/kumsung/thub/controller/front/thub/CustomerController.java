package kr.co.kumsung.thub.controller.front.thub;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import kr.co.kumsung.thub.domain.Category;
import kr.co.kumsung.thub.domain.CurriculumContact;
import kr.co.kumsung.thub.domain.FileInfo;
import kr.co.kumsung.thub.domain.Member;
import kr.co.kumsung.thub.domain.Send;
import kr.co.kumsung.thub.exception.CommonException;
import kr.co.kumsung.thub.service.BoardService;
import kr.co.kumsung.thub.service.CategoryService;
import kr.co.kumsung.thub.service.CurriculumContactService;
import kr.co.kumsung.thub.service.CustomerService;
import kr.co.kumsung.thub.service.FileService;
import kr.co.kumsung.thub.service.MemberService;
import kr.co.kumsung.thub.setting.Constants;
import kr.co.kumsung.thub.util.Common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


/**
 * 공지사항 관리 컨트롤러
 * @author mikelim
 *
 */
@Component("thubCustomerController")
@Controller
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	private BoardService boardService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private CurriculumContactService curriculumContactService;
	
	@Autowired
	private FileService fileService;
	
	@Value("#{common['file.path']}") String serverFilePath;		// 첨부파일의 root 경로
	
	/**
	 * 이용안내 1~4
	 * @param model
	 * @return
	 */
	@RequestMapping("/usage/usage1.do")
	public String usage1(Model model){
		// assign
		model.addAttribute("beforePath", "고객센터");
		model.addAttribute("currentPath", "이용안내");
		
		return "front/thub/customer/usage/usage1";
	}
	
	@RequestMapping("/usage/usage2.do")
	public String usage2(Model model){
		// assign
		model.addAttribute("beforePath", "고객센터");
		model.addAttribute("currentPath", "이용안내");
		
		return "front/thub/customer/usage/usage2";
	}
	
	@RequestMapping("/usage/usage3.do")
	public String usage3(Model model){
		// assign
		model.addAttribute("beforePath", "고객센터");
		model.addAttribute("currentPath", "이용안내");
		
		return "front/thub/customer/usage/usage3";
	}
	
	@RequestMapping("/usage/usage4.do")
	public String usage4(Model model){
		// assign
		model.addAttribute("beforePath", "고객센터");
		model.addAttribute("currentPath", "이용안내");
		
		return "front/thub/customer/usage/usage4";
	}
	
	@RequestMapping("/usage/usage5.do")
	public String usage5(HttpServletRequest request , Model model) throws Exception{
		HttpSession session = (HttpSession) request.getSession();
		Member adminMember = (Member) session.getAttribute("member");
		if(adminMember == null){
			model.addAttribute("beforePath", "고객센터");
			model.addAttribute("currentPath", "이용안내");
			model.addAttribute("flag","N");
			model.addAttribute("th_flag","N");
			model.addAttribute("authtype", "N");
			
			return "front/thub/customer/usage/usage5";
		}
			
		Member member = memberService.get(adminMember.getUserId());
		System.out.println("******************");
		System.out.println("member = " +member);
		System.out.println("******************");
		
		model.addAttribute("beforePath", "고객센터");
		model.addAttribute("currentPath", "이용안내");
		model.addAttribute("flag","Y");
		model.addAttribute("th_flag",member.getIsFinallyAuth());
		model.addAttribute("authtype", member.getAuthType());
		model.addAttribute("member", member);
		
		return "front/thub/customer/usage/usage5";
	}
	
	/**
	 * 저작도구 다운로드 기록
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/usage/sdfDown.do" , method=RequestMethod.POST)
	public @ResponseBody Map<String, Object>  sdfDown(HttpServletRequest request , Model model) throws Exception
	{
		HttpSession session = (HttpSession) request.getSession();
		Member member = (Member) session.getAttribute("member");
		memberService.insertSdf(member.getUserId());
		//memberService.insertSdf("memtest");
		Map<String, Object> result = new HashMap<String, Object>();

		result.put("result" , "SUCCESS");
		
		return result;
	}
	
	/**
	 * 공지사항
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/notice.do")
	public String notice(HttpServletRequest request , Model model) throws Exception
	{
		final int BOARD_ID = 16;
		Map<String,Object> boardAttrs = boardService.getBoardDataAttributes(request, BOARD_ID);
		model.addAllAttributes(boardAttrs);
		
		// assign
		model.addAttribute("beforePath", "고객센터");
		model.addAttribute("currentPath", "공지사항");
		
		return "front/thub/customer/notice";
	}
	
	/**
	 * 언론보도
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/press.do")
	public String press(HttpServletRequest request , Model model) throws Exception
	{
		final int BOARD_ID = 32;
		Map<String,Object> boardAttrs = boardService.getBoardDataAttributes(request, BOARD_ID);
		model.addAllAttributes(boardAttrs);
		
		// assign
		model.addAttribute("beforePath", "고객센터");
		model.addAttribute("currentPath", "언론보도");
		
		return "front/thub/customer/press";
	}
	
	
	/**
	 * 자주묻는질문
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/faq.do")
	public String faq(HttpServletRequest request , Model model) throws Exception
	{
		final int BOARD_ID = 19;
		Map<String,Object> boardAttrs = boardService.getBoardDataAttributes(request, BOARD_ID);
		model.addAllAttributes(boardAttrs);
		
		// assign
		model.addAttribute("beforePath", "고객센터");
		model.addAttribute("currentPath", "자주묻는질문");
		
		return "front/thub/customer/faq";
	}
	
	/**
	 * 프로그램안내
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/programs.do")
	public String programs(HttpServletRequest request , Model model) throws Exception
	{	
		// assign
		model.addAttribute("beforePath", "고객센터");
		model.addAttribute("currentPath", "프로그램안내");
		
		return "front/thub/customer/programs";
	}
	
	/**
	 * 문의전화
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/tel.do")
	public String tel(HttpServletRequest request , Model model) throws Exception
	{	
		// assign
		model.addAttribute("beforePath", "고객센터");
		model.addAttribute("currentPath", "문의전화");
		
		return "front/thub/customer/tel";
	}
	
	/**
	 * 찾아오는길
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/map.do")
	public String map(HttpServletRequest request , Model model) throws Exception
	{	
		// assign
		model.addAttribute("beforePath", "고객센터");
		model.addAttribute("currentPath", "찾아오는길");
		
		return "front/thub/customer/map";
	}
	
	/**
	 * 고객문의
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/customer.do")
	public String customer(HttpServletRequest request , Model model) throws Exception
	{	
		HttpSession session = (HttpSession) request.getSession();
		//String adminMember = "purunetcom";
		Member adminMember = (Member) session.getAttribute("member");
		String boardCategory = Common.getParameter(request, "boardCategory", "INQ");

		
		
		if ( adminMember == null )
			throw new CommonException("로그인 후 이용해주시기 바랍니다." , CommonException.HISTORY_BACK);
			
		if ( boardCategory.equals("") )
			throw new CommonException("다시 이용해주시기 바랍니다." , CommonException.HISTORY_BACK);

		Member member = memberService.getMemberInfo(adminMember.getUserId());
		
		List<Category> categories = null;
		String pathTitle = "고객문의";
		if( boardCategory.equals("C003001") )
		{
			categories = (List<Category>) categoryService.getChildren(Constants.CUSTOMER_ANSWER_CATEGORY);
			pathTitle = "고객문의";
		}
		else if( boardCategory.equals("C003002") )
		{
			categories = (List<Category>) categoryService.getChildren(Constants.LEARNING_CATEGORY);
			pathTitle = "교과서 자료요청";
		}
		else if( boardCategory.equals("C003003") )
		{
			categories = (List<Category>) categoryService.getChildren(Constants.LEARNING_CATEGORY);
			pathTitle = "교과서 내용문의";
		}
		
		
		// assign
		model.addAttribute("boardCategory", boardCategory);
		model.addAttribute("member", member);
		model.addAttribute("categories", categories);
		model.addAttribute("beforePath", "고객센터");
		model.addAttribute("currentPath", pathTitle);
		
		return "front/thub/customer/customer";
		//return "front/thub/customer/customer1";
	}
	
	/**
	 * 고객문의 등록
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/customerAction.do" , method=RequestMethod.POST)
	public String trainingSubsAction(HttpServletRequest request , Model model) throws Exception
	{
		HttpSession session = (HttpSession) request.getSession();
		Member adminMember = (Member) session.getAttribute("member");
		
		// get parameters
		String phoneNum = Common.getParameter(request, "phoneNum", "");
		String email = Common.getParameter(request, "email", "");
		String title = Common.getParameter(request, "title", "");
		String isSns = Common.getParameter(request, "isSns", "");
		String category = Common.getParameter(request, "category", "");
		String contents = Common.getParameterNoXSS(request, "contents", "");
		int bookId = Common.getParameter(request, "bookId", 0);
		
		String boardCategory = Common.getParameter(request, "boardCategory", "");
		
		String[] fileNames = Common.getParameters(request, "fileName");
		String[] dataFiles = Common.getParameters(request, "dataFile");
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		if(fileNames != null){
			for(int i=0; i<fileNames.length; i++){
				params.put("fileName"+(i+1),fileNames[i]);
			}
		}
		if(dataFiles != null){
			for(int i=0; i<dataFiles.length; i++){
				params.put("dataFile"+(i+1),dataFiles[i]);
			}
		}
				
		params.put("phoneNum", phoneNum);
		params.put("email", email);
		params.put("title", title);
		params.put("isSns", isSns);
		params.put("category", category);
		params.put("contents", contents);
		params.put("regId", adminMember.getUserId());
		params.put("boardCategory", boardCategory);
		params.put("bookId", bookId);
		
		customerService.insertCustomer(params);
		
		// 메일 발송을 위하여 담당자를 가지고 온다.
		if( boardCategory.equals("C003001"))
		{
			String mailSubject = "[티칭허브] 고객문의가 접수되었습니다.";
			String mailContents = "보낸사람 : " + adminMember.getName() + "(" + adminMember.getUserId() + ")<br/><br/>E-mail :" + email + "<br/><br/>제목 : " + title + "<br/><br/><br/>" + contents + "<br/><br/><br/><br/><br/>관리자 모드에서 상세내용을 확인하여주십시오.";
			
			//memberService.sendEmail("kstext@kumsung.co.kr,e1sang@kumsung.co.kr,teaching.kumsung@gmail.com,shshlee@kumsung.co.kr", mailSubject, mailContents);
			//memberService.sendEmail("kstext@kumsung.co.kr,e1sang@kumsung.co.kr,teaching.kumsung@gmail.com,hogamhs@naver.com", mailSubject, mailContents);
			memberService.sendEmail("kstext@kumsung.co.kr,e1sang@kumsung.co.kr,teaching.kumsung@gmail.com,ohih@kumsung.co.kr", mailSubject, mailContents);
			
		}
		else
		{
			CurriculumContact contact = curriculumContactService.getCurriculumContactDetailFromCategory(category);
			
			if( contact != null )
			{
				// 메일 발송을 위하여 데이타를 다시 가지고 온다.
				String subjectPostFix = "";
				
				if( boardCategory.equals("C003002") )
					subjectPostFix = "교과서 자료요청";
				else if( boardCategory.equals("C003003"))
					subjectPostFix = "교과서 내용문의";
				
				String mailSubject = "[티칭허브] " + subjectPostFix;
				String mailContents = "보낸사람 : " + adminMember.getName() + "(" + adminMember.getUserId() + ")<br/><br/>E-mail :" + email + "<br/><br/>제목 : " + title + "<br/><br/><br/>" + contents + "<br/><br/><br/><br/><br/>관리자 모드에서 상세내용을 확인하여주십시오.";
				
				memberService.sendEmail(contact.getEmail().replaceAll(";" , ",") , mailSubject, mailContents);
			}
		}
		
		// assign
		model.addAttribute("beforePath", "고객센터");
		model.addAttribute("currentPath", "고객문의");
		model.addAttribute("pathName", "테스트");
		
		return "redirect:customerComplete.do";
	}

	/**
	 * 파일 업로드
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 * */
	@RequestMapping("/customerUpload.do")
	public String customerUpload(HttpServletRequest request, Model model,
			@RequestParam("upfile")MultipartFile attachFile ) throws Exception
	{
		FileInfo fileInfo = new FileInfo(); 
		
		if(!attachFile.isEmpty()){
			fileInfo = fileService.upload(attachFile, serverFilePath, "attachFile");
			fileInfo.getOriginName().substring(fileInfo.getOriginName().lastIndexOf(".") + 1 , fileInfo.getOriginName().length()).toLowerCase();
		}
		
		model.addAttribute("fileInfo", fileInfo);
		model.addAttribute("msg","aa");
		
		return "fileHandler";
	}
	
	/**
	 * 파일 삭제
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 * */
	@RequestMapping(value="deleteCustomer.do" , method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> deleteCustomer(HttpServletRequest request, Model model) throws Exception{
		
		String filePath = Common.getParameter(request, "filePath", "");
		Map<String, Object> result = new HashMap<String, Object>();
		if( filePath.equals("") )
			throw new CommonException("다시 이용해주시기 바랍니다." , CommonException.HISTORY_BACK);

		String deleteFilePath = String.format("%s%s" , serverFilePath , filePath.replace("/upfiles", ""));
		fileService.delete(deleteFilePath);
		
		result.put("result" , "SUCCESS");
		
		return result;
	}
	
	/**
	 * 고객문의 등록
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/customerComplete.do")
	public String customerComplete(HttpServletRequest request , Model model) throws Exception
	{
		// assign
		model.addAttribute("beforePath", "고객센터 &gt; 접수 완료");
		model.addAttribute("currentPath", "고객문의");
		model.addAttribute("pathName", "테스트");
		
		return "front/thub/customer/customerComplete";
	}
}
