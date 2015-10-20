package com.book;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.book.util.DownloadThread;
import com.book.util.Util;

public class Book {
	private String bookName;
	private List<BookChapter> chapterList;
	private String bookAddress;
	private String bookStore;

	private static Logger logger = LoggerFactory.getLogger(Book.class);

	public Book(String bookAddress, String bookName) {
		this.bookName = bookName;
		this.bookAddress = bookAddress;
	}
	
	
	public  Boolean DownloadBook() {
		logger.info("start download book。 " + this.toString());
		List<BookChapter> chapterList = new ArrayList<BookChapter>();
		String store = getBookStore();
		try {
			chapterList = Util.getChapterList(getBookAddress());
		} catch (Exception e1) {
			logger.error("Get Book Catalog Failed");
			return false ;
		}
		this.setChapterList(chapterList);
		
		////debug
//		List<BookChapter> chapterList = new ArrayList<BookChapter>();
//		BookChapter bc = new BookChapter("N", "1011332.html", null);
//		bc.setPreviousChapterFileName("1011331.html");
//		bc.setNextChapterFileName("1011333.html");
//		chapterList.add(bc);
//		b.setChapterList(chapterList);
		///////
		String indexHtml = Util.createBookIndexPage(store, this);
		// produce index page
		Util.writeFile(store + "index.html", indexHtml);
		// down every chapter page
		List<DownloadThread> dltList = new ArrayList<DownloadThread>();
		int splitSize = 100;
		Map m = new HashMap();
		
		for (int i = 0; i < chapterList.size();) {
			int toIndex = i + splitSize;
			if (toIndex < chapterList.size()) {
				dltList.add(new DownloadThread(store, getBookAddress(),
						chapterList.subList(i, toIndex)));
			} else {
				dltList.add(new DownloadThread(store, getBookAddress(),
						chapterList.subList(i, chapterList.size())));
			}
			i += splitSize;

		}
		CountDownLatch latch = new CountDownLatch(dltList.size());

		for (DownloadThread h : dltList) {
			h.setLatch(latch);
			h.start();
		}
		try {
			latch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
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
