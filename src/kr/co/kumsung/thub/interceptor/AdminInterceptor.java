package kr.co.kumsung.thub.interceptor;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.co.kumsung.thub.domain.BoardConfig;
import kr.co.kumsung.thub.domain.Member;
import kr.co.kumsung.thub.service.BoardService;
import kr.co.kumsung.thub.util.Validate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


/**
 * 관리자 영역 접근에 대한 interceptor
 * @author mikelim
 *
 */
public class AdminInterceptor extends HandlerInterceptorAdapter{

	@Autowired
	private BoardService boardService;
	
	@Override
	public boolean preHandle(HttpServletRequest request , 
					HttpServletResponse response , Object handler) throws Exception{
						
		// 관리자 세션을 체크한다.
		HttpSession session = (HttpSession) request.getSession();
		Member adminMember = (Member) session.getAttribute("adminMember");
		
		if( adminMember == null 
				|| Validate.isEmpty(adminMember.getName()))
		{
			response.sendRedirect("/admin/login.do");
			//response.sendRedirect("https://thub.kumsung.co.kr/admin/login.do");
			return false;
		}
		
		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest request ,
					HttpServletResponse response , 
					Object handler ,
					ModelAndView modelAndView) throws Exception{
		
		// LNB 게시판의 목록을 가지고 오기 위하여 DB에 요청을 한다.
		if( modelAndView != null )
		{
			List<BoardConfig> lnbBoardList = boardService.getBoardList( new HashMap<String,Object>() );
			modelAndView.getModel().put("lnbBoardList" , lnbBoardList);
		}
		
	}
}
