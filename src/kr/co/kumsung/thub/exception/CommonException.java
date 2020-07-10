package kr.co.kumsung.thub.exception;

/**
 * 공통 Ezception 처리
 * @author mikelim
 *
 */
public class CommonException extends RuntimeException{
	
	private static final long serialVersionUID = 6507320572002119524L;
	
	public final static int HISTORY_BACK = 0;
	public final static int REDIRECT = 1;
	public final static int PARENT_RELOAD = 2;
	
	private int actionType = 0;
	private String location = "/";
	
	public int getActionType() {
		return actionType;
	}

	public String getLocation() {
		if( location == null )
			return "/";
		
		return location;
	}

	public CommonException()
	{
		super();
	}
	
	public CommonException(String err)
	{
		super(err);
	}
	
	public CommonException(String err , int actionType)
	{
		super(err);
		
		this.actionType = actionType;
	}
	
	public CommonException(String err , String location , int actionType)
	{
		super(err);
		
		System.out.println("location = "+location);
		System.out.println("actionType = "+actionType);
		this.location = location;
		System.out.println("location = "+ this.location);
		this.actionType = actionType;
	}
	
}
