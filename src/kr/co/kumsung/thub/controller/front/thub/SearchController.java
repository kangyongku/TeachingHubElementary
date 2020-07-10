package kr.co.kumsung.thub.controller.front.thub;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import kr.co.kumsung.thub.domain.Article;
import kr.co.kumsung.thub.domain.BoardConfig;
import kr.co.kumsung.thub.domain.Data;
import kr.co.kumsung.thub.domain.Headword;
import kr.co.kumsung.thub.domain.History;
import kr.co.kumsung.thub.domain.Multimedia;
import kr.co.kumsung.thub.exception.CommonException;
import kr.co.kumsung.thub.service.BoardService;
import kr.co.kumsung.thub.service.LearningService;
import kr.co.kumsung.thub.service.MultimediaService;
import kr.co.kumsung.thub.service.SmartService;
import kr.co.kumsung.thub.util.Common;
import kr.co.kumsung.thub.util.Validate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Component("frontSearchController")
@Controller
@RequestMapping("/search")
public class SearchController {

	@Autowired
	private LearningService learningService;
	
	@Autowired
	private MultimediaService multimediaService;
	
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private SmartService smartService;
	
	/**
	 * 검색결과 화면
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list.do")
	public String search(HttpServletRequest request , Model model) throws Exception
	{
		final int PAGE_SIZE = 1000;
		
//		final String findSearchStr = Common.getParameter(request, "findSearchStr", "");
/*		String findSearchStr2 = Common.getParameter(request, "findSearchStr", "");
		
		final String findSearchStr = findSearchStr2.replaceAll(" ", "");		
		final String findType = Common.getParameter(request, "findType", "");*/
		final String findSearchStr = Common.getParameter(request, "findSearchStr", "").replaceAll(" ", "");
		final String findType = Common.getParameter(request, "findType", "");

		if( Validate.isEmpty(findSearchStr) )
			throw new CommonException("검색어가 존재하지 않습니다." , CommonException.HISTORY_BACK);
		 
		// get data
		List<Data> datas = (List<Data>) learningService.getDataList( new HashMap<String,Object>(){
																			{
																				put("findStr" , findSearchStr);
																				put("findMethod" , "ss");
																				put("skip" , 0);
																				put("pageSize" , PAGE_SIZE);
																			}
																		});
		
		
		List<Multimedia> multimedias = (List<Multimedia>) multimediaService.getMultimediaList( new HashMap<String,Object>(){
																		{
																			put("findStr" , findSearchStr);
																			put("findMethod" , "st");
																			put("findIsUse" , "Y");
																			put("skip" , 0);
																			put("pageSize" , PAGE_SIZE);
																		}
																	});
		
		List<Headword> headwords = (List<Headword>) smartService.getHeadwordSearchList(new HashMap<String,Object>(){
																		{	
																			put("findSearchStr" , findSearchStr);
																			put("skip" , 0);
																			put("pageSize" , PAGE_SIZE);
																		}
																	});
		//티칭백과 추가
		List<Headword> dicheadwords = (List<Headword>) smartService.getHeadwordList(new HashMap<String,Object>(){
																		{	
																			put("findSearchStr" , findSearchStr);
																			put("findIsUse" , "Y");
																			put("findIsApprove" , "Y");
																			put("skip" , 0);
																			put("pageSize" , PAGE_SIZE);
																		}
																	});
		List<History> histories = (List<History>) smartService.getHistorySearchList(new HashMap<String,Object>(){
																		{	
																			put("findSearchStr" , findSearchStr);
																			put("skip" , 0);
																			put("pageSize" , PAGE_SIZE);
																		}
																	});
		
		List<Article> lectures = (List<Article>) boardService.getArticleList( new HashMap<String,Object>(){
																	{
																		put("boardId" , 18);
																		put("findStr" , findSearchStr);
																		put("findMethod" , "ss");
																		put("skip" , 0);
																		put("pageSize" , PAGE_SIZE);
																	}
																});
		
		List<Article> articles = (List<Article>) boardService.getArticleSearchList(new HashMap<String,Object>(){
																	{	
																		put("findStr" , findSearchStr);
																		put("findMethod" , "sk");
																		put("skip" , 0);
																		put("pageSize" , PAGE_SIZE);
																	}
																});
		
		int pg = Common.getParameter(request, "pg", 1);
		BoardConfig boardConfig = this.boardService.getBoardConfig(31);
		List<Article> list = (List<Article>) boardService.getArticleList(new HashMap<String,Object>(){
																	{	
																	       put("boardId", 31);
																	        put("findStr", findSearchStr);
																	        put("findMethod", "ss");
																	        put("skip", 0);
																	        put("pageSize", PAGE_SIZE);

																	}
																});
		model.addAttribute("pg", Integer.valueOf(pg));
		 int totalCount = ((((((datas.size() + multimedias.size()) + headwords.size()) + dicheadwords.size()) + histories.size()) + lectures.size()) + articles.size()) + list.size();


		if( totalCount > 0 
				&& findSearchStr.length() >= 2 )
		{
			// 검색어가 있다면 insert... 그나마 의미있는 데이타를 트랙킹하기 위함이다.
			Map<String,Object> params = new HashMap<String,Object>();
			
			params.put("searchWord" , findSearchStr);
			
			learningService.insertSearchWord(params);
		}
		
		// assign
		model.addAttribute("findSearchStr", findSearchStr);
		model.addAttribute("findType", findType);
		
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("datas" , datas);
		model.addAttribute("multimedias" , multimedias);
		model.addAttribute("headwords", headwords);
		model.addAttribute("dicheadwords", dicheadwords);
		model.addAttribute("histories", histories);
		model.addAttribute("lectures" , lectures);
		model.addAttribute("articles" , articles);
		model.addAttribute("list", list);

		return "front/thub/search/list";
	}
	
}
