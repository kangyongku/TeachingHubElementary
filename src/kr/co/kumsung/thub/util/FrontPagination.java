package kr.co.kumsung.thub.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Author : Mikelim(mikelim@mintocean.com)
 * 
 * Pagination Class
 *  
 * @properties
 * 		firstLink , prevLink, nextLink , lastLink : set up image or text if you want.
 * 		delimiter : set up image or text  
 * 		prewrap , postwrap : currentPage design wrapper tag
 */

public class FrontPagination {
	private static final Logger logger = LoggerFactory.getLogger(FrontPagination.class);
	private int totalRows = 0;
	private int firstPage = 1;
	private int currentPage = 1;
	private int pageSize = 10;
	private int blockSize = 10;	
	private int totalPages;
	private int totalBlocks;
	private int startPageNum;
	private int endPageNum;
	private int currentBlock;

	private String amp = "";
	
	// for design
	private String firstLink = "[<<]";
	private String firstOffLink = "";
	private String prevLink = "[<]";
	private String prevOffLink = "";
	private String nextLink = "[>]";
	private String nextOffLink = "";
	private String lastLink = "[>>]";
	private String lastOffLink = "";
	
	private String delimiter = "";
	
	// current Page Wrapper
	private String preWrap = "<b>";
	private String postWrap = "</b>";
	
	private String linkPage = "";
	private String queryString = "";
	
	// result temp object
	public StringBuffer pageString = new StringBuffer();
	
	public FrontPagination(int currentPage , int pageSize , int blockSize , int totalRows)
	{
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		this.blockSize = blockSize;
		this.totalRows = totalRows;
		
		initialize();
	} 
	
	public void initialize()
	{	
		this.totalPages = (int)Math.ceil((double)this.totalRows / this.pageSize);
		this.totalBlocks = (int)Math.ceil((double)this.totalPages / this.blockSize);
		this.currentBlock = (int)Math.ceil((double)((this.currentPage - 1) / this.blockSize)) + 1;
		this.startPageNum = ((this.currentBlock - 1)* this.blockSize) + 1;
		this.endPageNum = this.startPageNum + this.blockSize;
	}
	
	public void firstPrint()
	{
		//logger.info("this.startPageNum:::::::::::::"+this.startPageNum);
		//logger.info("this.currentPage:::::::::::::"+this.currentPage);
		// set prev page link    
		if(this.startPageNum > this.pageSize)
			pageString.append("<a href=\"javascript:goList('" + (this.firstPage) + "');\"><img src=\"/assets/front/2017img/bg/paging_first.png\" alt=\"처음\"></a>\r\n");		
	}
	
	public void prePrint()
	{
		// set prev page link
		if(this.currentPage > 1)
			pageString.append("<a href=\"javascript:goList('" + (this.currentPage  - 1) + "');\"><img src=\"/assets/front/2017img/bg/paging_prev.png\" alt=\"이전 5페이지 이동\"></a>\r\n");		
	}
	 
	public void postPrint()
	{
		// set next page link
		if(this.currentPage < this.totalPages )
			pageString.append("<a href=\"javascript:goList('" + (this.currentPage  + 1) + "');\" class=\"next\"><img src=\"/assets/front/2017img/bg/paging_next.png\" alt=\"다음 10페이지 이동\"></a>\r\n");
	}
	
	public void lastPrint()
	{
		//logger.info("this.endPageNum:::::::::::::"+this.endPageNum);
		//logger.info("this.totalPages:::::::::::::"+this.totalPages);
		// set prev page link
		if(this.endPageNum <= this.totalPages)
			pageString.append("<a href=\"javascript:goList('" + (this.totalPages) + "');\"><img src=\"/assets/front/2017img/bg/paging_last.png\" alt=\"마지막\"></a>\r\n");		
	}
	
	public void printList()
	{	
		pageString.append("<ul>\r\n");
		
		for(int i = startPageNum ; i <= endPageNum ; i++)
		{
			if(i > this.totalPages || i == endPageNum)
				break;
			else if(i > startPageNum)
				pageString.append(this.delimiter);
			
			if(i == this.currentPage)
				pageString.append("<li class=\"on\"><a href=\"javascript:goList('" + i + "');\">" + i + "</a></li>\r\n");
			else
				pageString.append("<li><a href=\"javascript:goList('" + i + "');\">" + i + "</a></li>\r\n");
		}
		
		pageString.append("</ul>\r\n");
	}
	
	public String print()
	{
		// set amp if already to set up queryString property
		if(!this.queryString.equals(""))
			this.amp = "&";
		
		if(this.totalPages > 1)
		{
			this.firstPrint();
			this.prePrint();
			this.printList();
			this.postPrint();
			this.lastPrint();
		}
		
		return(pageString.toString());
	}
	
	/**
	 * @param args
	 */
	/*
	public static void main(String[] args) {
		
		// TODO Auto-generated method stub
		Pagination pg = new Pagination(1 , 10, 10 , 11);
		pg.linkPage = "pagenum.jsp";
		pg.queryString = "param1=test&param2=test2";
		
		// for design
		pg.firstLink = "<img src=\"/first.gif\">";
		pg.prevLink = "<img src=\"/prev.gif\">";
		pg.nextLink = "<img src=\"/next.gif\">";
		pg.lastLink = "<img src=\"/last.gif\">";
		
		pg.firstOffLink = "[<<]";
		pg.prevOffLink = "[<]";
		pg.nextOffLink = "[>]";
		pg.lastOffLink = "[>>]";
		
		pg.delimiter = "||";
		
		
		//print
		System.out.println(pg.print());
	}
	*/

	public int getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getBlockSize() {
		return blockSize;
	}

	public void setBlockSize(int blockSize) {
		this.blockSize = blockSize;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getTotalBlocks() {
		return totalBlocks;
	}

	public void setTotalBlocks(int totalBlocks) {
		this.totalBlocks = totalBlocks;
	}

	public int getStartPageNum() {
		return startPageNum;
	}

	public void setStartPageNum(int startPageNum) {
		this.startPageNum = startPageNum;
	}

	public int getEndPageNum() {
		return endPageNum;
	}

	public void setEndPageNum(int endPageNum) {
		this.endPageNum = endPageNum;
	}

	public int getCurrentBlock() {
		return currentBlock;
	}

	public void setCurrentBlock(int currentBlock) {
		this.currentBlock = currentBlock;
	}

	public String getAmp() {
		return amp;
	}

	public void setAmp(String amp) {
		this.amp = amp;
	}

	public String getFirstLink() {
		return firstLink;
	}

	public void setFirstLink(String firstLink) {
		this.firstLink = firstLink;
	}

	public String getFirstOffLink() {
		return firstOffLink;
	}

	public void setFirstOffLink(String firstOffLink) {
		this.firstOffLink = firstOffLink;
	}

	public String getPrevLink() {
		return prevLink;
	}

	public void setPrevLink(String prevLink) {
		this.prevLink = prevLink;
	}

	public String getPrevOffLink() {
		return prevOffLink;
	}

	public void setPrevOffLink(String prevOffLink) {
		this.prevOffLink = prevOffLink;
	}

	public String getNextLink() {
		return nextLink;
	}

	public void setNextLink(String nextLink) {
		this.nextLink = nextLink;
	}

	public String getNextOffLink() {
		return nextOffLink;
	}

	public void setNextOffLink(String nextOffLink) {
		this.nextOffLink = nextOffLink;
	}

	public String getLastLink() {
		return lastLink;
	}

	public void setLastLink(String lastLink) {
		this.lastLink = lastLink;
	}

	public String getLastOffLink() {
		return lastOffLink;
	}

	public void setLastOffLink(String lastOffLink) {
		this.lastOffLink = lastOffLink;
	}

	public String getDelimiter() {
		return delimiter;
	}

	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}

	public String getPreWrap() {
		return preWrap;
	}

	public void setPreWrap(String preWrap) {
		this.preWrap = preWrap;
	}

	public String getPostWrap() {
		return postWrap;
	}

	public void setPostWrap(String postWrap) {
		this.postWrap = postWrap;
	}

	public String getLinkPage() {
		return linkPage;
	}

	public void setLinkPage(String linkPage) {
		this.linkPage = linkPage;
	}

	public String getQueryString() {
		return queryString;
	}

	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}

	public StringBuffer getPageString() {
		return pageString;
	}

	public void setPageString(StringBuffer pageString) {
		this.pageString = pageString;
	}
}

