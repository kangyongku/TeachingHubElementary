package kr.co.kumsung.thub.util;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class CookieBox {
    
    @SuppressWarnings("rawtypes")
	private Map cookieMap = new java.util.HashMap();
    
    @SuppressWarnings("unchecked")
	public CookieBox(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (int i = 0 ; i < cookies.length ; i++) {
                cookieMap.put(cookies[i].getName(), cookies[i]);
            }
        }
    }
    
    public static Cookie createCookie(String name, String value)
    throws IOException {
        return new Cookie(name, URLEncoder.encode(value, "utf-8"));
    }

    public static Cookie createCookie(
            String name, String value, String path, int maxAge) 
    throws IOException {
        Cookie cookie = new Cookie(name, 
                                URLEncoder.encode(value, "utf-8"));
        cookie.setPath(path);
        cookie.setMaxAge(maxAge);
        return cookie;
    }
    
    public static Cookie createCookie(
            String name, String value,  
            String domain, String path, int maxAge) 
    throws IOException {
        Cookie cookie = new Cookie(name, 
                  URLEncoder.encode(value, "utf-8"));
        cookie.setDomain(domain);
        cookie.setPath(path);
        cookie.setMaxAge(maxAge);
        return cookie;
    }
    
    public Cookie getCookie(String name) {
        return (Cookie)cookieMap.get(name); 
    }
    
    public String getValue(String name) throws IOException {
        Cookie cookie = (Cookie)cookieMap.get(name);
        if (cookie == null) return null;
        return URLDecoder.decode(cookie.getValue(), "utf-8");
    }
    
    public boolean exists(String name) {
        return cookieMap.get(name) != null;
    }
}