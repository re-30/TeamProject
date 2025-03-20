package utility;

public class Paging {
	//페이징 관련 변수	
	private int totalCount = 0 ; //총 레코드 건수
	private int totalPage = 0 ; //전체 페이지 수
	private int pageNumber = 0 ; //보여줄 페이지 넘버(표현 가능한 페이지는 1부터 totalPage까지이다.)
	private int pageSize = 0 ; //한 페이지에 보여줄 건수
	private int beginRow = 0 ; //현재 페이지의 시작 행
	private int endRow = 0 ; //현재 페이지의 끝 행
	private int pageCount = 3 ; //보여줄 페이지 링크 수
	private int beginPage = 0 ; //페이징 처리 시작 페이지 번호
	private int endPage = 0 ; //페이징 처리 끝 페이지 번호
	private int offset = 0 ;
	private int limit = 0 ;
	private String url = "" ; //http://localhost:8080/ex/list.tv
	private String pagingHtml = "";//하단의 숫자 페이지 링크
	
	//검색을 위한 변수 추가
	private String whatColumn = "" ; //검색 모드
	private String keyword = "" ; //검색할 단어 

	public int getTotalCount() {
		return totalCount;
	}


	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}


	public int getTotalPage() {
		return totalPage;
	}


	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}


	public int getPageNumber() {
		return pageNumber;
	}


	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}


	public int getPageSize() {
		return pageSize;
	}


	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}


	public int getBeginRow() {
		return beginRow;
	}


	public void setBeginRow(int beginRow) {
		this.beginRow = beginRow;
	}


	public int getEndRow() {
		return endRow;
	}


	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}


	public int getPageCount() {
		return pageCount;
	}


	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}


	public int getBeginPage() {
		return beginPage;
	}


	public void setBeginPage(int beginPage) {
		this.beginPage = beginPage;
	}


	public int getEndPage() {
		return endPage;
	}


	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}


	public int getOffset() {
		return offset;
	}


	public void setOffset(int offset) {
		this.offset = offset;
	}


	public int getLimit() {
		return limit;
	}


	public void setLimit(int limit) {
		this.limit = limit;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public String getPagingHtml() {
		return pagingHtml;
	}


	public void setPagingHtml(String pagingHtml) {
		this.pagingHtml = pagingHtml;
	}


	public String getWhatColumn() {
		return whatColumn;
	}


	public void setWhatColumn(String whatColumn) {
		this.whatColumn = whatColumn;
	}


	public String getKeyword() {
		return keyword;
	}


	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}


	public Paging(
			String _pageNumber, //선택한 페이지 번호
			String _pageSize,  //한페이지에 보이는 페이지 수
			int totalCount,
			String url, 
			String whatColumn, 
			String keyword) {		

		if( _pageNumber == null || _pageNumber.equals("null") || _pageNumber.equals("") ){
			_pageNumber = "1" ;
		}
		this.pageNumber = Integer.parseInt( _pageNumber ) ; 

		if( _pageSize == null || _pageSize.equals("null") || _pageSize.equals("") ){
			_pageSize = "3" ;
		}		
		this.pageSize = Integer.parseInt( _pageSize ) ;

		this.limit = pageSize ;

		this.totalCount = totalCount ; 

		this.totalPage = (int)Math.ceil((double)this.totalCount / this.pageSize) ;
							//소수점을 올림처리(ceil) 총 페이지수 구하는것
		this.beginRow = ( this.pageNumber - 1 )  * this.pageSize  + 1 ;
		//페이지에 몇번 몇번 레코드가 나오게 하는 작업
		this.endRow =  this.pageNumber * this.pageSize ;
		//페이지에 몇번 몇번 레코드가 나오게 하는 작업

		if( this.endRow > this.totalCount ){
			this.endRow = this.totalCount  ;
		//endrow가 totalcount보다 더 크게 나오면 totalcount랑 endrow가 같게 해라
		}

		this.beginPage = ( this.pageNumber - 1 ) / this.pageCount * this.pageCount + 1  ;
		//페이지 시작을 알려주는 번호  1~10까지 보이면 다음 누르면 11~20까지 보여주는것
		this.endPage = this.beginPage + this.pageCount - 1 ;
		//페이지 시작을 알려주는 번호  1~10까지 보이면 다음 누르면 11~20까지 보여주는것
		
		if( this.pageNumber > this.totalPage ){  
			this.pageNumber = this.totalPage ;
		}
		
		this.offset = ( pageNumber - 1 ) * pageSize ; 
		//건너뛴 레코드 갯수(한페이지에 2개의 레코드가 있다고 치고, 2페이지를 보려면 1페이지에 있는 2개를 건너뛰고 봐야되는것)
		if( this.endPage > this.totalPage ){
			this.endPage = this.totalPage ;
		}

		this.url = url ;
		this.whatColumn = whatColumn ;
		this.keyword = keyword ;

		this.pagingHtml = getPagingHtml(url) ;
	
	}

	
	private String getPagingHtml( String url ){ 
		System.out.println("getPagingHtml url:"+url);
		String result = "" ;
		
		String added_param = "&whatColumn=" + whatColumn + "&keyword=" + keyword ; 
		
		//앞쪽
		if (this.beginPage != 1) { 
			result += "&nbsp;<a href='" + url  
					+ "?pageNumber=" + ( 1 ) + "&pageSize=" + this.pageSize 
					+ added_param + "'>맨 처음</a>&nbsp;" ;
			result += "&nbsp;<a href='" + url 
					+ "?pageNumber=" + (this.beginPage - 1 ) + "&pageSize=" + this.pageSize 
					+ added_param + "'>이전</a>&nbsp;" ;
		}
		
		//가운데
		for (int i = this.beginPage; i <= this.endPage ; i++) {
			if ( i == this.pageNumber ) {
				result += "&nbsp;<font color='red'>" + i + "</font>&nbsp;"	;
						
			} else {
				result += "&nbsp;<a href='" + url   
						+ "?pageNumber=" + i + "&pageSize=" + this.pageSize 
						+ added_param + "'>" + i + "</a>&nbsp;" ;
			}
		}
		
		//뒤쪽
		if ( this.endPage != this.totalPage) {
			result += "&nbsp;<a href='" + url  
					+ "?pageNumber=" + (this.endPage + 1 ) + "&pageSize=" + this.pageSize 
					+ added_param + "'>다음</a>&nbsp;" ;
			
			result += "&nbsp;<a href='" + url  
					+ "?pageNumber=" + (this.totalPage ) + "&pageSize=" + this.pageSize 
					+ added_param + "'>맨 끝</a>&nbsp;" ;
		}		
		
		return result ;
	}	
}
