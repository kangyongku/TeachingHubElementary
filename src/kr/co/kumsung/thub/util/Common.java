package kr.co.kumsung.thub.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.util.Random;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class Common {
	
	/**
	 * request 파라미터를 가지고 온다.
	 * 기본적으로 XSS Filter를 적용한다.
	 * @param request
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static String getParameter( HttpServletRequest request , String key , String defaultValue )
	{
		
		String reqString = request.getParameter(key);
		if( Validate.isEmpty(request.getParameter(key)) )
			return defaultValue;
		
		String afterFilter =defaultValue( XSSFilter.filter((String) request.getParameter(key)) , defaultValue );
		
		if((reqString.length() >  afterFilter.length()) && afterFilter.length() != 0 && (reqString.length() != afterFilter.length())){
			afterFilter=reqString;
		}
		return afterFilter;
	}
	
	/**
	 * request 파라미터를 가지고 온다.
	 * XSS 필터적용을 하지 않는다.
	 * @param request
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static String getParameterNoXSS( HttpServletRequest request , String key , String defaultValue )
	{
		if( Validate.isEmpty(request.getParameter(key)) )
			return defaultValue;
		
		return defaultValue( (String) request.getParameter(key) , defaultValue );
	}
	
	/**
	 * Int 형 파라미터를 가지고 온다.
	 * @param request
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static int getParameter( HttpServletRequest request , String key , int defaultValue )
	{
		if( Validate.isEmpty(request.getParameter(key)) )
			return defaultValue;
		
		return defaultValue( (String) request.getParameter(key) , defaultValue );
	}
	
	/**
	 * request 파라미터들을 가지고 온다.
	 * 기본적으로 XSS Filter를 적용한다.
	 * @param request
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static String[] getParameters( HttpServletRequest request , String key )
	{
		String[] values = request.getParameterValues(key);
		
		if( Validate.isNull(values) )
			return null;
		
		int loopCnt = values.length;
		String[] returnValues = new String[loopCnt];
		
		for( int i = 0 ; i < loopCnt ; i++ )
		{
			returnValues[i] = XSSFilter.filter(values[i]);
		}
		
		return returnValues;
	}
	
	/**
	 * 데이타가 비어있으면 디폴트 값으로 반환한다.
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static String defaultValue( String value , String defaultValue )
	{
		if( Validate.isEmpty( value ) )
			return defaultValue;
		
		return value;
	}
	
	/**
	 * 데이타가 비어있으면 디폴트 값으로 반환한다.
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static int defaultValue( String value , int defaultValue )
	{
		if( Validate.isEmail( value ) )
			return defaultValue;
		
		return Integer.valueOf( value );
	}
	
	/**
	 * 데이타가 비어있으면 디폴트 값으로 반환한다.
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static float defaultValue( String value , float defaultValue )
	{
		if( Validate.isEmpty( value ) )
			return defaultValue;
		
		return Float.valueOf( value );
	}
	
	/**
	 * 데이타가 비어있으면 디폴트 값으로 반환한다.
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static double defaultValue( String value , double defaultValue )
	{
		if( Validate.isEmpty( value ) )
			return defaultValue;
		
		return Double.valueOf( value );
	}
	
	/**
	 * html에서 모든 tag들을 제거한다.
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static String html2text( String html )
	{
		//return Jsoup.parse(html).text().replaceAll("\\?" , "");
		
		html = html.replaceAll("\r\n", "<br>");
		html = html.replaceAll("\r", "<br>");
		html = html.replaceAll("\n", "<br>");
		
		// html <body> 부분 제거
		if(html.toLowerCase().indexOf("<body>") > 0) {
			html = html.substring(html.toLowerCase().indexOf("<body>")+6, html.toLowerCase().indexOf("</body>"));
		}
				
		return html;
	}
	
	/**
	 * 통화단위로 출력
	 * @param price
	 * @return
	 */
	public static String numberFormat(int price)
	{
		return String.format("%,3d" , price);
	}
	
   /**
	 * 초단위 시간을 분/초로 변경한다.
	 * @param time
	 * @return
	 */
	public static String getPlaytime(int time)
	{	
		int mm = (time / 60);
		int ss = time - (mm * 60);
		
		String result = String.format("%02d:%02d" , mm , ss);
		
		return result;
	}
	
	/**
	 * 모든 공백을 제거한다.
	 * @param val
	 * @return
	 */
	public static String removeBlank(String val)
	{
		if( Validate.isEmpty(val) )
			return "";
		
		return val.replaceAll("\\p{Space}" , "");
	}
	
	/**
	 * 글자의 수를 잘라준다.
	 * @param val
	 * @param size
	 * @return
	 */
	public static String cutString(String val , int size)
	{	
		if( !Validate.isEmpty(val)  
				&& val.length() > size )
			return String.format("%s..." , val.substring(0 , size));
		
		return val;
	}
	
	/**
	 * Unique한 문자열을 가지고 온다.
	 * @return
	 */
	public static String uniqueString()
	{
		SecureRandom random = new SecureRandom();
		return new BigInteger(130 , random).toString(32);
	}
	
	/**
	 * 캐리지 리턴 값을 br로 치환한다.
	 * @param data
	 * @return
	 */
	public static String nl2br(String data)
	{
		data = data.replaceAll("\r\n" , "<br/>");
		data = data.replaceAll("\r" , "<br/>");
		data = data.replaceAll("\n" , "<br/>");
		
		return data;
	}
	
	/**
	 * 축약된 주소를 가지고 온다.
	 * @param longUrl
	 * @return
	 */
	public static String getUrlShortener(String longUrl)
	{
		String shortUrl = "";
		
		try
		{
			URL url = new URL("https://www.googleapis.com/urlshortener/v1/url?key=AIzaSyCcCXLnaJ2cRqJ61MoW3tDVwoveHrJnVF4");
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	        connection.setDoOutput(true);
	        connection.setRequestMethod("POST");
	        connection.setRequestProperty("Content-type", "application/json");
	
	        OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());        
	        writer.write("{\"longUrl\" : \"" + longUrl + "\"}");
	        writer.close();
	
	        BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	        StringBuilder sb = new StringBuilder();
	        String line = null;
	        while ((line = rd.readLine()) != null) {
	            sb.append(line + '\n');
	        }
	        String json = sb.toString();
	        shortUrl = json.substring(json.indexOf("http"), json.indexOf("\"", json.indexOf("http")));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return shortUrl;
	}
	
	/**
	 * 회원의 아이디를 감춘다.
	 * @param id
	 * @return
	 */
	public static String getSecretId(String id)
	{
		String returnId = "";
		int loopCnt = id.length() - 3;
		
		returnId = id.substring(0,3);
		
		for(int i = 0 ; i < loopCnt ; i++)
		{
			returnId += "*";
		}
		
		return returnId;
	}
	
	/**
	 * 퍼센테이지 소수점 2째자리까지 계산하여 가지고 온다.
	 * @param c
	 * @param t
	 * @return
	 */
	public static String getPercentage(int c , int t )
	{
		if( t == 0 )
			return "0.00";
		else
			return String.format("%.2f" , (double)((double)c / (double)t) * 100);
	}
	
	/**
	 * 쿠키값을 가지고 온다.
	 * @param cookies
	 * @param name
	 * @return
	 */
	public static String getCookieValue(Cookie[] cookies , String name)
	{
		if( cookies == null )
			return null;
		
		for(Cookie cookie : cookies)
		{
			if( cookie.getName().equals(name))
				return cookie.getValue();
		}
		
		return null;
	}
	
	public static String getUnitIdsPath(int findUnitIds)
	{
		return getUnitIdsPath(String.valueOf(findUnitIds));
	}
	
	public static String getUnitIdsPath(String findUnitIds)
	{
		String[] t2 = findUnitIds.split("\\,");
		
		String first = "";
		String second = "";
		String third = "";
		
		if( !Validate.isEmpty(findUnitIds))
		{
			if( t2.length == 1 )
			{	
				first = t2[0];
				return String.format("L%s," , first);
			}
			else if( t2.length == 2 )
			{
				first = t2[0];
				second = t2[1];
				
				return String.format("L%s,L%s," , first , second);
			}
			else if( t2.length == 3 )
			{
				first = t2[0];
				second = t2[1];
				third = t2[2];
				
				return String.format("L%s,L%s,L%s," , first , second , third);
			}
		}
		
		return "";
	}
	
	/**
	 * 랜덤한 문자열을 원하는 길이만큼 반환합니다.
	 * 
	 * @param length 문자열 길이
	 * @return 랜덤문자열
	 */
	public static String getRandomString(int length)
	{
		StringBuffer buffer = new StringBuffer();
		Random random = new Random();

		String chars[] = 
				"a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z,0,1,2,3,4,5,6,7,8,9".split(",");

		for (int i=0 ; i<length ; i++)
		{
			buffer.append(chars[random.nextInt(chars.length)]);
		}
		return buffer.toString();
	}
	
    /**
     * null 문자열 대체 문자열로 반환
     * @param s
     * @return
     * string
     * @exception 
     */
     public static String nullStr(String s, String r) {
    	 if(s == null || "".equals(s))
    		 return r;
    	 else 
    		 return s;
     }
     
     /**
      * 통합로그인 아이디 반환
      * @param 
      * @return
      * string
      * @exception 
      */
     public static String getLoginCSID(){
 		URL url;  
 		HttpURLConnection conn = null;  
 		BufferedReader rd;  
 		String line;  
 		String result = "";  
 		 try { 
 			url = new URL("http://cs.kumsung.co.kr/logchk.do"); 
 			conn = (HttpURLConnection) url.openConnection(); 
 			conn.setRequestMethod("GET"); 
 			rd = new BufferedReader(new InputStreamReader(conn.getInputStream())); 
 			while ((line = rd.readLine()) != null) { result += line; } 
 			rd.close(); 
 		} catch (Exception e) { e.printStackTrace(); } finally {conn.disconnect();}
 		 return result;
     }
     
}