package com.xunj.tag;

public class TagBase
{
  private int pageSize;
  private int rowCount;
  private int pageCount;
  private int pageNumber;
  private String pageSizeOption;
  public String getPageSizeOption() {
	return pageSizeOption;
}

public void setPageSizeOption(String pageSizeOption) {
	this.pageSizeOption = pageSizeOption;
}

public int getPageCount()
  {
    return this.pageCount; }

  public void setPageCount(int pageCount) {
    this.pageCount = pageCount; }

  public int getPageSize() {
    return this.pageSize; }

  public void setPageSize(int pageSize) {
    this.pageSize = pageSize; }

  public int getRowCount() {
    return this.rowCount; }

  public void setRowCount(int rowCount) {
    this.rowCount = rowCount; }

  public int getPageNumber() {
    return this.pageNumber; }

  public void setPageNumber(int pageNumber) {
    this.pageNumber = pageNumber;
  }
}