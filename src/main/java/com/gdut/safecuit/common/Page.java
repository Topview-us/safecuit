package com.gdut.safecuit.common;

/**
 * Created by Garson in 16:16 2018/1/18
 * Description : 分页类
 */
public class Page {

	private final Integer DEFAULT_SIZE = 10;
	//页数大小
	private Integer pageSize;
	//当前页数
	private Integer pageNo;
	//查询总数目
	private Integer total;
	//总页数目
	private Integer totalPages;
	//数据库index
	private Integer index;

	public Page() {
	}

	public Page(Integer pageSize, Integer pageNo) {
		this.pageSize = pageSize;
		this.pageNo = pageNo;
	}

	public Page(int pageNo, Integer total) {
		init(DEFAULT_SIZE,pageNo,total);
	}

	public Page(int pageSize, int pageNo, int total) {
		init(pageSize,pageNo,total);
	}

	private void init(int pageSize, int pageNo, int total){
		this.pageSize = pageSize;
		this.pageNo = pageNo;
		this.total = total;
		this.totalPages=count(total,pageSize);
		this.index = ((pageNo-1)*pageSize)>total?((totalPages-1)*pageSize):((pageNo-1)*pageSize);
		System.out.println("====================index = " + index );
	}

	/**
	 * Count int.
	 *  计算页数
	 * @param total    the total
	 * @param pageSize the page size
	 * @return the int
	 */
	private int count(int total, int pageSize) {
		if (total % pageSize == 0) {
			return total / pageSize;
		} else {
			return total / pageSize + 1;
		}
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	@Override
	public String toString() {
		return "Page{" +
				"DEFAULT_SIZE=" + DEFAULT_SIZE +
				", pageSize=" + pageSize +
				", pageNo=" + pageNo +
				", total=" + total +
				", totalPages=" + totalPages +
				", index=" + index +
				'}';
	}

}
