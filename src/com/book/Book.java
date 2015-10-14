package com.book;

import java.util.List;

public class Book {
	private String bookName;
	private List<BookChapter> chapterList;
	private String bookAddress;
	private String bookStore;

	public Book(String bookAddress, String bookName) {
		this.bookName = bookName;
		this.bookAddress = bookAddress;
	}

	public String getBookAddress() {
		return bookAddress;
	}

	public void setBookAddress(String bookAddress) {
		this.bookAddress = bookAddress;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public List<BookChapter> getChapterList() {
		return chapterList;
	}

	public void setChapterList(List<BookChapter> chapterList) {
		this.chapterList = chapterList;
	}

	public String toString() {
		return "BookName :" + bookName + "  BookAddress: " + bookAddress  + " BookStore: " + bookStore;
	}

	public String getBookStore() {
		return bookStore;
	}

	public void setBookStore(String bookStore) {
		this.bookStore = bookStore;
	}

}
