package kr.co.kumsung.thub.util;

import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;



/**
 * Validation Checker
 * @author mikelim
 *
 */
public class Validate {

	
	/**
	 * NULL 값인지 체크한다.
	 * @param value
	 * @return true : value is null , false : value is not null
	 */
	public static boolean isNull( String value )
	{
		if( value == null 
				|| value.equals("") )
			return true;
		
		return false;
	}
	
	/**
	 * NULL 값인지 체크한다.
	 * @param value
	 * @return true : value is null , false : value is not null
	 */
	public static boolean isNull( String[] values )
	{
		if( values == null )
			return true;
		
		return false;
	}
	
	/**
	 * 빈값인지 체크한다.
	 * 빈값은 일반 공백 , NULL을 포함한다.
	 * @param value
	 * @return true : value is empty , false : value is not empty
	 */
	public static boolean isEmpty( String value )
	{
		if( isNull( value ) 
				|| value.replaceAll("\\p{Space}" , "").equals("")
				|| value.toLowerCase().equals("null")
				|| value.equals("<p>&nbsp;</p>")
				|| value.equals("<p><br></p>"))
			return true;
				
		return false;
	}
	
	/**
	 * 이메일 주소가 맞는지 체크한다.
	 * @param email
	 * @return true valid email , false invalid email
	 */
	public static boolean isEmail( String email )
	{	
		String regExp = "^[_a-zA-Z0-9-\\+]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9]+)*(\\.[a-zA-Z]{2,})$";
		return isMatch( regExp , email);
	}
	
	/**
	 * 홈페이지 주소가 맞는지 체크한다.
	 * @param url
	 * @return true valid url , false invalid url
	 */
	public static boolean isUrl( String url  )
	{
		String regExp = "^((http|https)://)[a-z0-9-]+(\\.[a-z0-9-]+)+([/?].*)?$";
		return isMatch( regExp , url );
	}
	
	/**
	 * 전화번호 형식 체크
	 * 국내 전화번호 형식에 대한 체크를 한다.
	 * @param regExp
	 * @param value
	 * @return true is matched , false is not matched.
	 */
	public static boolean isTel( String tel )
	{
		String regExp = "^(02|031|032|033|041|042|043|051|052|053|054|055|061|062|063|064|070)"
							+ "(-([0-9]){3,4}){2}";
		return isMatch( regExp , tel );
	}
	
	/**
	 * 핸드폰 번호 형식 체크
	 * 국내 핸드폰 형식에 대한 체크를 한다.
	 * @param regExp
	 * @param value
	 * @return true is matched , false is not matched.
	 */
	public static boolean isCell( String cell )
	{
		String regExp = "^(010|011|016|017|018|019)"
							+ "(-([0-9]){3,4}){2}";
		return isMatch( regExp , cell );
	}
	
	/**
	 * 주어진 regular expression에 대응하는지 체크한다.
	 * @param regExp
	 * @param value
	 * @return true is matched , false is not matched.
	 */
	public static boolean isMatch( String regExp , String value )
	{
		Pattern pattern = Pattern.compile(regExp);
		Matcher matcher = pattern.matcher(value);
		return matcher.matches();
	}
	
	/**
	 * 허용된 ip  체크한다.
	 * @param value
	 * @return true : value is null , false : value is not null
	 * @throws UnknownHostException 
	 */
	public static boolean ipCheck( String value ) throws UnknownHostException
	{
		
		 HttpServletRequest request =((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		String ClientIp = request.getRemoteAddr();
		//if(ClientIp.equals("211.106.68.195") || ClientIp.equals("211.106.68.196") || ClientIp.equals("128.134.78.2") || ClientIp.equals("211.106.68.193") || ClientIp.equals("211.106.68.194") || ClientIp.equals("121.78.33.182")){
			return true;
		//}else{
		//	return false;
		//}
	}
	
	public static void main(String[] args){
		
//		String empty = "    1    ";
//		System.out.println( Validate.isEmpty(empty) );
//		
//		String email = "test.tes@test.com";
//		System.out.println( Validate.isEmail(email) );
//		
//		String url  = "http://naver/";
//		System.out.println( Validate.isUrl(url));
//		
//		// 아이디 확인
//		// 첫번째는 무조건 영문 , 한글 및 특수기호(.,_ 제외)가 존재하면 false
//		// 총 6자 이상
//		String regExp = "^[a-zA-Z][\\.a-zA-Z0-9_]{5,}$";
//		String myId = "mikelim2&한글";
//		System.out.println( Validate.isMatch(regExp, myId));
		
//		String tel = "010-418-8596";
//		System.out.println( Validate.isTel(tel));
		
//		String cell = "010-258-8596";
//		System.out.println( Validate.isCell(cell));
				
		
	}
	
}
