package com.book.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import com.book.BookChapter;

public class DownloadThread extends Thread {

	private List<BookChapter> chapterList;
	private String store;
	private String url;
	private CountDownLatch latch ;

	public void setLatch(CountDownLatch latch) {
		this.latch = latch;
	}

	public DownloadThread(String store, String url, List<BookChapter> chapterList) {
		this.store = store;
		this.url = url;
		this.chapterList = chapterList;
		
	}
	
	@Override
	public void run() {

			for(BookChapter chapterPart : this.chapterList) {
//				String html = Util.getChapter(this.url + chapterPart.getChapterFileName());
				String html = Util.getChapter(url, chapterPart);
				Util.writeFile(store + chapterPart.getChapterFileName() , html);
			}
			if(null != latch) {
				latch.countDown();
			}			
	}
	
	public static void main(String[] args) {
		String store = "E:\\Book\\zzz";
		String url = "http://www.xbiquge.com/10_10916/";
		List<BookChapter> chapterList = new ArrayList<BookChapter>();
		chapterList.add(new BookChapter("1","5652411.html",""));
		new DownloadThread(store,url,chapterList).start();
	}
}
