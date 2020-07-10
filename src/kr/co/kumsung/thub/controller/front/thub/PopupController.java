package kr.co.kumsung.thub.controller.front.thub;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import kr.co.kumsung.thub.domain.Book;
import kr.co.kumsung.thub.domain.Data;
import kr.co.kumsung.thub.domain.Folder;
import kr.co.kumsung.thub.domain.Member;
import kr.co.kumsung.thub.exception.CommonException;
import kr.co.kumsung.thub.service.EduEventService;
import kr.co.kumsung.thub.service.LearningService;
import kr.co.kumsung.thub.service.ScrapService;
import kr.co.kumsung.thub.util.Common;
import kr.co.kumsung.thub.util.Validate;
import kr.co.kumsung.thub.util.ConvertToImage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Component("thubPopupController")
@Controller
@RequestMapping("/popup")
public class PopupController {

	@Autowired
	private ScrapService scrapService;
	
	@Autowired
	private LearningService learningService;
	
	@Autowired
	private EduEventService edueventService;
	
	private static final Logger logger = Logger.getLogger(PopupController.class);

	@RequestMapping("/scrapFolder.do")
	public String scrapFolder(HttpServletRequest request , Model model) throws Exception{	
		HttpSession session = (HttpSession) request.getSession();
		Member member = (Member) session.getAttribute("member");
		
		if( member == null ) {
			throw new CommonException("로그인 후 사용가능합니다." , CommonException.HISTORY_BACK);
		}
		
		String scrapType = Common.getParameter(request, "scrapType", "");
		String relationIds = Common.getParameter(request, "relationIds", "");
		String mode = Common.getParameter(request, "mode", "add");
		
		if( Validate.isEmpty(scrapType) || Validate.isEmpty(relationIds)) {
			throw new CommonException("정상적인 접근이 아닙니다." , CommonException.HISTORY_BACK);
		}
		
		List<Folder> folders = (List<Folder>) scrapService.getFolderList(member.getUserId());

		model.addAttribute("mode", mode);
		model.addAttribute("folders", folders);
		model.addAttribute("scrapType", scrapType);
		model.addAttribute("relationIds", relationIds);
		
		return "front/thub/popup/scrapFolder";
	}
	
	@RequestMapping("/scrapFolderManager.do")
	public String scrapFolderManager(HttpServletRequest request , Model model) throws Exception{
		
		HttpSession session = (HttpSession) request.getSession();
		Member member = (Member) session.getAttribute("member");
		
		if( member == null ) {
			throw new CommonException("로그인 후 사용가능합니다." , CommonException.HISTORY_BACK);
		}
		
		List<Folder> folders = (List<Folder>) scrapService.getFolderList(member.getUserId());
		
		model.addAttribute("folders", folders);
		
		return "front/thub/popup/scrapFolderManager";
	}
	
	@RequestMapping("/popCdView.do")
	public String popCdView(HttpServletRequest request , Model model) throws Exception{	

		HttpSession session = (HttpSession) request.getSession();
		Member member = (Member) session.getAttribute("member");
		
		if( member == null ) {
			throw new CommonException("로그인 후 사용가능합니다." , CommonException.HISTORY_BACK);
		}
		
		if( member.getIsFinallyAuth().equals("Y") ){
			
			int bookId = Common.getParameter(request, "bookId", 0);
			
			if( bookId == 0 ) {
				throw new CommonException("올바르지 않은 파라미터 입니다." , CommonException.HISTORY_BACK);
			}

			Book book = (Book) learningService.getBook(bookId);
			
			if( book == null ) {
				throw new CommonException("도서를 찾을 수 없습니다." , CommonException.HISTORY_BACK);
			}
			
			model.addAttribute("book", book);
		}
		else {
			throw new CommonException("접근 권한이 없습니다." , CommonException.HISTORY_BACK);
		}
		
		return "front/thub/popup/popCdView";
	}
	
	@RequestMapping("/popDataView.do")
	public String popDataView(HttpServletRequest request , Model model) throws Exception{
		
		HttpSession session = (HttpSession) request.getSession();
		Member member = (Member) session.getAttribute("member");
		Map<String,Object> params = new HashMap<String,Object>();
		
		int dataId = Common.getParameter(request, "dataId", 0);
		int sdataId = Common.getParameter(request, "sdataId", 0);
		
		params.put("dataId" , dataId);
		params.put("sdataId" , sdataId);
		
		Data data = (Data) learningService.getData(params);
		Date from = new Date();

		SimpleDateFormat transFormat = new SimpleDateFormat("yyyyMM");
			
		if(dataId==0&& sdataId>0) data = (Data) learningService.getSData(params);
		
        ConvertToImage cvt = new ConvertToImage();

        String inputPath = "/home/thub/www"+data.getDataFile();
        String outputPath = "/home/thub/www/upfiles/viewer/result";
        String outdate  = transFormat.format(from);

        int outputValue = cvt.convertToImage(inputPath, outputPath, outdate);

        cvt = null;
        
		model.addAttribute("data", data);
		model.addAttribute("outputValue", outputValue);
		
		return "front/thub/popup/popDataView";
	}
	
	@RequestMapping("/popChasiView.do")
	public String chasiPreview(HttpServletRequest request , Model model) throws Exception{
		
		HttpSession session = (HttpSession) request.getSession();
		Member member = (Member) session.getAttribute("member");
		
		if( member == null ) {
			throw new CommonException("로그인 후 사용가능합니다." , CommonException.HISTORY_BACK);
		}
		
		int dataId = Common.getParameter(request, "dataId", 0);
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("dataId" , dataId);
		
		Data data = learningService.getData(params);
		
		List<Data> cview = (List<Data>) learningService.getCDataView(params);
		
		model.addAttribute("dataId", dataId);
		model.addAttribute("data", data);
		model.addAttribute("cview", cview);
		model.addAttribute("title", "차시 미리보기");
		model.addAttribute("footer", "N");
		
		return "front/thub/popup/popChasiView";
	}
}
