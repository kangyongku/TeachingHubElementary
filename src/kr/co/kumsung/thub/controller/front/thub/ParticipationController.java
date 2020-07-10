package kr.co.kumsung.thub.controller.front.thub;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import kr.co.kumsung.thub.domain.Article;
import kr.co.kumsung.thub.domain.BoardCategory;
import kr.co.kumsung.thub.domain.BoardConfig;
import kr.co.kumsung.thub.domain.Category;
import kr.co.kumsung.thub.domain.Event;
import kr.co.kumsung.thub.domain.FileInfo;
import kr.co.kumsung.thub.domain.Headword;
import kr.co.kumsung.thub.domain.Member;
import kr.co.kumsung.thub.domain.Multimedia;
import kr.co.kumsung.thub.domain.RelationHeadword;
import kr.co.kumsung.thub.domain.Send;
import kr.co.kumsung.thub.exception.CommonException;
import kr.co.kumsung.thub.service.BoardService;
import kr.co.kumsung.thub.service.CategoryService;
import kr.co.kumsung.thub.service.CustomerService;
import kr.co.kumsung.thub.service.FileService;
import kr.co.kumsung.thub.service.MemberService;
import kr.co.kumsung.thub.service.MultimediaService;
import kr.co.kumsung.thub.service.SmartService;
import kr.co.kumsung.thub.util.Common;
import kr.co.kumsung.thub.util.FrontPagination;
import kr.co.kumsung.thub.util.Validate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Component("thubParticipationController")
@Controller
@RequestMapping("/participation")
public class ParticipationController {
	private static final Logger logger = LoggerFactory.getLogger(ParticipationController.class);
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private MultimediaService multimediaService;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private SmartService smartService;
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private CustomerService customerService;
	
	@Value("#{common['file.path']}") String serverFilePath;		// 첨부파일의 root 경로
	
	@RequestMapping("/project.do")
	public String project(HttpServletRequest request , Model model) throws Exception{	

		int boardType = Common.getParameter(request, "boardType", 1);
		final int BOARD_ID = 42;
		
		if(boardType==2){
			Map<String,Object> boardAttrs = boardService.getBoardDataAttributes(request, BOARD_ID);
			model.addAllAttributes(boardAttrs);	
		}
		
		BoardConfig bconf = boardService.getBoardConfig(BOARD_ID);

		model.addAttribute("beforePath", "학생참여수업");
		model.addAttribute("currentPath", bconf.getBoardName());	//
		model.addAttribute("boardConfig", bconf);
		model.addAttribute("boardType", boardType);
		return "front/thub/participation/project";
	}
	
	@RequestMapping("/reverse.do")
	public String reverse(HttpServletRequest request , Model model) throws Exception{	

		int boardType = Common.getParameter(request, "boardType", 1);
		final int BOARD_ID = 43;
		
		if(boardType==2){
			Map<String,Object> boardAttrs = boardService.getBoardDataAttributes(request, BOARD_ID);
			model.addAllAttributes(boardAttrs);	
		}
		
		BoardConfig bconf = boardService.getBoardConfig(BOARD_ID);

		model.addAttribute("beforePath", "학생참여수업");
		model.addAttribute("currentPath", bconf.getBoardName());	
		model.addAttribute("boardConfig", bconf);
		model.addAttribute("boardType", boardType);
		return "front/thub/participation/reverse";
	}	
	
	@RequestMapping("/havruta.do")
	public String havruta(HttpServletRequest request , Model model) throws Exception {
		
		int boardType = Common.getParameter(request, "boardType", 1);
		final int BOARD_ID = 44;
		
		if(boardType==2){
			Map<String,Object> boardAttrs = boardService.getBoardDataAttributes(request, BOARD_ID);
			model.addAllAttributes(boardAttrs);	
		}
		
		BoardConfig bconf = boardService.getBoardConfig(BOARD_ID);

		model.addAttribute("beforePath", "학생참여수업");
		model.addAttribute("currentPath", bconf.getBoardName());	
		model.addAttribute("boardConfig", bconf);
		model.addAttribute("boardType", boardType);
		return "front/thub/participation/havruta";
	}	
	
	@RequestMapping("/vthinking.do")
	public String vthinking(HttpServletRequest request , Model model) throws Exception {
		
		int boardType = Common.getParameter(request, "boardType", 1);
		final int BOARD_ID = 45;
		
		if(boardType==2){
			Map<String,Object> boardAttrs = boardService.getBoardDataAttributes(request, BOARD_ID);
			model.addAllAttributes(boardAttrs);	
		}
		
		BoardConfig bconf = boardService.getBoardConfig(BOARD_ID);

		model.addAttribute("beforePath", "학생참여수업");
		model.addAttribute("currentPath", bconf.getBoardName());	
		model.addAttribute("boardConfig", bconf);
		model.addAttribute("boardType", boardType);
		return "front/thub/participation/vthinking";
	}		
	
	@RequestMapping("/discuss.do")
	public String discuss(HttpServletRequest request , Model model) throws Exception {
		
		int boardType = Common.getParameter(request, "boardType", 1);
		final int BOARD_ID = 46;
		
		if(boardType==2){
			Map<String,Object> boardAttrs = boardService.getBoardDataAttributes(request, BOARD_ID);
			model.addAllAttributes(boardAttrs);	
		}
		
		BoardConfig bconf = boardService.getBoardConfig(BOARD_ID);

		model.addAttribute("beforePath", "학생참여수업");
		model.addAttribute("currentPath", bconf.getBoardName());	
		model.addAttribute("boardConfig", bconf);
		model.addAttribute("boardType", boardType);
		return "front/thub/participation/discuss";
	}		
	
	@RequestMapping("/subject.do")
	public String subject(HttpServletRequest request , Model model) throws Exception {
		
		int boardType = Common.getParameter(request, "boardType", 1);
		final int BOARD_ID = 47;
		
		if(boardType==2){
			Map<String,Object> boardAttrs = boardService.getBoardDataAttributes(request, BOARD_ID);
			model.addAllAttributes(boardAttrs);	
		}
		
		BoardConfig bconf = boardService.getBoardConfig(BOARD_ID);

		model.addAttribute("beforePath", "학생참여수업");
		model.addAttribute("currentPath", bconf.getBoardName());	
		model.addAttribute("boardConfig", bconf);
		model.addAttribute("boardType", boardType);
		return "front/thub/participation/subject";
	}		
	
	@RequestMapping("/format.do")
	public String format(HttpServletRequest request , Model model) throws Exception {
		
		int boardType = 2;
		final int BOARD_ID = 48;
		Map<String,Object> boardAttrs = boardService.getBoardDataAttributes(request, BOARD_ID);
		model.addAllAttributes(boardAttrs);	
		
		BoardConfig bconf = boardService.getBoardConfig(BOARD_ID);

		model.addAttribute("beforePath", "학생참여수업");
		model.addAttribute("currentPath", bconf.getBoardName());	
		model.addAttribute("boardConfig", bconf);
		model.addAttribute("boardType", boardType);
		return "front/thub/participation/format";
	}		
	
	@RequestMapping("/qnr.do")
	public String qnr(HttpServletRequest request , Model model) throws Exception {
		
		int boardType = Common.getParameter(request, "boardType", 1);
		final int BOARD_ID = 66;
		
		if(boardType==2){
			Map<String,Object> boardAttrs = boardService.getBoardDataAttributes(request, BOARD_ID);
			model.addAllAttributes(boardAttrs);	
		}
		
		BoardConfig bconf = boardService.getBoardConfig(BOARD_ID);

		model.addAttribute("beforePath", "학생참여수업");
		model.addAttribute("currentPath", bconf.getBoardName());	
		model.addAttribute("boardConfig", bconf);
		model.addAttribute("boardType", boardType);
		return "front/thub/participation/qnr";
	}		
}