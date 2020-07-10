package kr.co.kumsung.thub.util;

/**
 * XSS Filter
 * @author mikelim
 *
 */
public class XSSFilter {

	/**
	 * do it!
	 * @param source
	 * @return
	 */
	public static String filter( String source )
	{
		String returnValue = "";
		returnValue = source;
		returnValue = returnValue.replaceAll("\\<(/)?(\\s)?(?i)iframe.*?\\>", "");
		returnValue = returnValue.replaceAll("\\<(/)?(\\s)?(?i)script.*?\\>", "");
		returnValue = returnValue.replaceAll("\\<(/)?(\\s)?(?i)[onload|onerror|onsubmit|onfocus|onblur|onmouseover|onmouseout].*?\\>", "");
		returnValue = returnValue.replaceAll("\\<(/)?(\\s)?(?i)[onabort|onactivate|onafterprint|onafterupdate|onbeforeactive].*?\\>", "");
		returnValue = returnValue.replaceAll("\\<(/)?(\\s)?(?i)[onclick|onfocusin|onfocusout|onhelp|onkeypress|onkeydown|onkeyup].*?\\>", "");
		//returnValue = returnValue.replaceAll("(?i)expression" , "");
		returnValue = returnValue.replaceAll("(?i)eval" , "");
		returnValue = returnValue.replaceAll("(?i)javascript:" , "");
		returnValue = returnValue.replaceAll("(?i)vbscript:" , "");
		
		return returnValue;
	}
	
//	public static void main( String[] args )
//	{
//		String html = "< Iframe name=\"actionFrame\" width=\"100\" height=\"100\"></iframe>\r\n"
//						+ "<script type=\"text/javascript\">alert('z');</script>"
//						+ "<img Onerror src=\"../images/gucking.gif\"/>javascript:alert('z);";
//		
//		System.out.println(XSSFilter.filter(html));
//	}
	
	
}
