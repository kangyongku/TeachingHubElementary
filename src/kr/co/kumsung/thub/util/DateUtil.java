package kr.co.kumsung.thub.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {

	public static String getDate()
	{
		return getDate("yyyy-MM-dd HH:ii:ss");
	}
	
	public static String getDate( String pattern )
	{	
		SimpleDateFormat format = new SimpleDateFormat( pattern );
		Date date = new Date();
		String currentDate = format.format( date );
		return currentDate;
	}
	
	public static String getDayOfWeek()
	{
		String[] tmp = getDate("yyyy-MM-dd").split("-");
		return getDayOfWeek( tmp[0] , tmp[1] , tmp[2] );
	}
	
	public static String getDayOfWeek(String year , String month , String day)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.set( Integer.valueOf(year) , Integer.valueOf(month) - 1 , Integer.valueOf(day) );
		return String.valueOf(calendar.get(Calendar.DAY_OF_WEEK));
	}
	
	/**
	 * 날짜형식으로 치환변경한다.
	 * @param value
	 * @param format
	 * @return
	 */
	public static String formatDate(String value , String format)
	{
		String returnDate = format;
		
		if( !Validate.isEmpty(value)
				&& value.length() >= 8)
		{
			String year = value.substring(0 , 4);
			String mYear = value.substring(2 , 4);		// minimum
			String month = value.substring(4 , 6);
			String day = value.substring(6 , 8);
			
			// replace format
			returnDate = returnDate.replaceAll("yyyy" , year);
			returnDate = returnDate.replaceAll("yy" , mYear);
			returnDate = returnDate.replaceAll("MM" , month);
			returnDate = returnDate.replaceAll("dd" , day);
		}
		else
			returnDate = "";
		
		return(returnDate);
	}
	
	/**
	 * 현재 날짜로부터의 날짜차이를 반환한다.
	 * @param endDay
	 * @return
	 */
	public static int diffDate(String endDay)
	{	
		int returnDate = 0;
		
		try
		{
			// 입력날짜가 8자 이상(yyyyMMdd의 포맷 이상)일 경우 자릿수를 맞춰준다.
			if( endDay.length() > 8 )
				endDay = endDay.substring(0 , 8);
			
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			
			Date beginDate = format.parse(getDate("yyyyMMdd"));
			Date endDate = format.parse(endDay);
			
			Calendar beginCal = Calendar.getInstance();
			Calendar endCal = Calendar.getInstance();
			
			beginCal.setTime(beginDate);
			endCal.setTime(endDate);
			
			long resultTime = endCal.getTime().getTime() - beginCal.getTime().getTime();
			long resultDays = resultTime / (1000 * 60 * 60 * 24);
			
			returnDate = (int)resultDays;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		
		return returnDate;
	}
	
	/**
    *일짜 차이의 일수, 월 차이의 달수 구함. 예) datediff("d", "20000101", "20010501");  일의 차 - 작은 날이 앞에, datediff("20010101", "20000501");  달의 차-작은 날이 뒤에
    *@param gubn  월, 일 중 하나를 세팅한다.(월 = "month", 일 = "date")
    *@param firstdate  
    *@param lastdate  
    *@return 더하거나 뺀 월, 일을 리턴
    */		
    public static int diffDate(String gubn, String firstdate, String lastdate) { 
        int returnValue = 0;
        long temp = 0;
        int year=0,month=0,day=0,year1=0,month1=0,day1=0;
        int year2 = 0, month2 = 0;
        
        if ( firstdate == null || firstdate.equals("") ) return returnValue;
        if ( lastdate == null || lastdate.equals("") ) return returnValue;
        
        try { 
            year  = Integer.parseInt(firstdate.substring(0,4));
            month = Integer.parseInt(firstdate.substring(4,6));
            day   = Integer.parseInt(firstdate.substring(6,8));
            
            year1  = Integer.parseInt(lastdate.substring(0,4));
            month1 = Integer.parseInt(lastdate.substring(4,6));
            day1   = Integer.parseInt(lastdate.substring(6,8));
            
            if ( gubn.equals("date") ) { 		
                TimeZone tz = TimeZone.getTimeZone("Asia/Seoul");
                Calendar calendar=Calendar.getInstance(tz);
                
                calendar.set((year-1900),(month-1),day);
                
                Calendar cal2=Calendar.getInstance(tz);
                cal2.set((year1-1900),(month1-1),day1);
                
                java.util.Date temp1 = calendar.getTime();
                java.util.Date temp2 = cal2.getTime();
                
                temp = temp2.getTime() - temp1.getTime();
                
                if ( ( temp % 10 ) < 5 )				
                temp = temp - ( temp % 10 );
                else
                temp = temp + ( 10 - ( temp % 10 ) );
                
                returnValue = (int)( temp / ( 1000 * 60 * 60 * 24 ) );	
            
                if ( returnValue == 0 ) returnValue = 1;
            } 
            else { 			
                year2  = (year - year1) * 12;
                month2 = month - month1;
                returnValue = year2 + month2;
            }
        } catch ( Exception ex ) { 
            ex.printStackTrace();
        }        
        return returnValue;
    }
	
}