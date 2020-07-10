package kr.co.kumsung.thub.util;

import java.util.HashMap;

public class ResultMap extends HashMap<String , Object>{
	
	private static final long serialVersionUID = -3830114188929529138L;

	public ResultMap(){
		super();
	}
	
	public String getString(String key){
		if( this.containsKey(key) )
		{
			return castToString(this.get(key));
		}
		return "";
	}
	
	public int getInt(String key){
		if( this.containsKey(key) )
		{	
			String value = getString(key);
			
			if( Validate.isEmpty(value))
				return 0;
			
			return Integer.valueOf(value);
		}
		return 0;
	}
	
	public float getFloat(String key){
		if( this.containsKey(key) )
		{
			String value = getString(key);
			
			if( Validate.isEmpty(value))
				return 0.0f;
			
			return Float.valueOf(value);
		}
		return 0.0f;
	}
	
	public double getDouble(String key){
		if( this.containsKey(key) )
		{
			String value = getString(key);
			
			if( Validate.isEmpty(value))
				return 0.0f;
			
			return Double.valueOf(value);
		}
		return 0.0f;
	}
	
	private String castToString(Object value)
	{
		return String.valueOf(value);
	}
	
}
