package com.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.book.Book;
import com.book.util.Util;


public class NetBookTest {

	public static void main(String[] args) {
		String store = "E:\\lingyu3\\";
		String bookIndex = "http://www.xbiquge.com/0_311/";
		String bookName = "灵域";
//		String store = "E:\\ydjd\\";
//		String bookIndex = "http://www.xbiquge.com/0_434/";
//		String bookName = "一等家丁";
		
		Book b = new Book(bookIndex,bookName);
		
		b.DownloadBook();
//		List<BookChapter> chapterList = Util.getChapterList(b);
//		for(BookChapter bc: chapterList) {
//			System.out.println(bc);
//		}
//		List<DownloadThread> dltList = new ArrayList<DownloadThread>(); 
//		int splitSize = 100;
//		Map m = new HashMap();
//		for(int i = 0; i<chapterList.size();) {
//			int toIndex = i + splitSize;
//			if(toIndex<chapterList.size()) {
//				dltList.add(new DownloadThread(store,bookIndex,chapterList.subList(i, toIndex)));
//				System.out.println("scope: " + i +"--" + toIndex);
//			}else {
//				dltList.add(new DownloadThread(store,bookIndex,chapterList.subList(i, chapterList.size())));
//				System.out.println("scope: " + i +"--" + chapterList.size());
//			}
//			i+= splitSize;
//			
//		}
//		
//		
//		for(Thread h : dltList) {
//			h.start();
//		}
//		
	}

	

}
