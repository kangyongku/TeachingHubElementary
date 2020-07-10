package kr.co.kumsung.thub.interceptor;

import java.util.ArrayList;
import java.util.List;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.co.kumsung.thub.domain.Book;
import kr.co.kumsung.thub.domain.Category;
import kr.co.kumsung.thub.domain.ChoiceBook;
import kr.co.kumsung.thub.domain.Member;
import kr.co.kumsung.thub.service.CategoryService;
import kr.co.kumsung.thub.service.LearningService;
import kr.co.kumsung.thub.service.MemberService;
import kr.co.kumsung.thub.util.ResultMap;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

	
/**
 * Thub 영역 접근에 대한 interceptor
 * @author mikelim
 *
 */
public class ThubInterceptor extends HandlerInterceptorAdapter{

	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private LearningService learningService;
	
	@Autowired
	private MemberService memberService;
	
	@Override
	public void postHandle(HttpServletRequest request ,
					HttpServletResponse response , 
					Object handler ,
					ModelAndView modelAndView) throws Exception{
		
		// LNB 게시판의 목록을 가지고 오기 위하여 DB에 요청을 한다.
		if( modelAndView != null )
		{
			// GNB 과목 데이타를 가지고 온다.
			List<Category> elSubjects = (List<Category>) categoryService.getChildren("A001");
			List<Category> mdSubjects = (List<Category>) categoryService.getChildren("A002");
			List<Category> hgSubjects = (List<Category>) categoryService.getChildren("A003");
			
			// 최초 과목의 도서 리스트를 가지고 온다.
			Category elCategory = (Category) elSubjects.get(0);
			Category mdCategory = (Category) mdSubjects.get(0);
			Category hgCategory = (Category) hgSubjects.get(0);
			
			List<Book> elBooks = (List<Book>) learningService.getBookListByCategory(elCategory.getCategory());
			List<Book> mdBooks = (List<Book>) learningService.getBookListByCategory(mdCategory.getCategory());
			List<Book> hgBooks = (List<Book>) learningService.getBookListByCategory(hgCategory.getCategory());
			
			//교육과정
			List<ResultMap> elCourses = (List<ResultMap>) learningService.getCourseListByCategory(elCategory.getCategory());
			List<ResultMap> mdCourses = (List<ResultMap>) learningService.getCourseListByCategory(mdCategory.getCategory());
			List<ResultMap> hgCourses = (List<ResultMap>) learningService.getCourseListByCategory(hgCategory.getCategory());
			
			modelAndView.getModel().put("elSubjects" , elSubjects);
			modelAndView.getModel().put("mdSubjects" , mdSubjects);
			modelAndView.getModel().put("hgSubjects" , hgSubjects);
			
			modelAndView.getModel().put("elBooks" , elBooks);
			
			System.out.println("mdBooks === " + mdBooks);
			
			modelAndView.getModel().put("mdBooks" , mdBooks);
			modelAndView.getModel().put("hgBooks" , hgBooks);
			
			modelAndView.getModel().put("elCourses" , elCourses);
			modelAndView.getModel().put("mdCourses" , mdCourses);
			modelAndView.getModel().put("hgCourses" , hgCourses);
			
			// CD 다운로드의 데이타를 가지고 온다.
			List<Category> elCdCategory = (List<Category>) categoryService.getCategoryInCDDownload("A001");
			List<Category> mdCdCategory = (List<Category>) categoryService.getCategoryInCDDownload("A002");
			List<Category> hgCdCategory = (List<Category>) categoryService.getCategoryInCDDownload("A003");
			
			modelAndView.getModel().put("elCdCategory" , elCdCategory);
			modelAndView.getModel().put("mdCdCategory" , mdCdCategory);
			modelAndView.getModel().put("hgCdCategory" , hgCdCategory);
			
			// 로그인 상태라면 나의 서적을 가지고 온다.
			HttpSession session = (HttpSession) request.getSession();
			Member member = (Member) session.getAttribute("member");
			
			List<ChoiceBook> myBooks = (member == null ) ? new ArrayList<ChoiceBook>() : (List<ChoiceBook>) memberService.getMyBooks(member.getUserId());
			
			modelAndView.getModel().put("globalMyBooks" , myBooks);
		}
		
	}
}
