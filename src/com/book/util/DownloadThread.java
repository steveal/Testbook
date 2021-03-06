package com.book.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.book.BookChapter;

public class DownloadThread extends Thread {

	private List<BookChapter> chapterList;
	private String store;
	private String url;
	private CountDownLatch latch ;
	private DownLoadLogStorge logStorge;

	private static Logger logger = LoggerFactory.getLogger(DownloadThread.class);
	public void setLatch(CountDownLatch latch) {
		this.latch = latch;
	}

	public DownloadThread(String store, String url, List<BookChapter> chapterList) {
		this.store = store;
		this.url = url;
		this.chapterList = chapterList;
		
	}
	
	public DownloadThread(String store, String url, List<BookChapter> chapterList, DownLoadLogStorge _logStorge) {
		this(store,url,chapterList);
		this.logStorge = _logStorge;
	}
	
	@Override
	public void run() {
			logger.info(Thread.currentThread().getName() + " start!");
			for(BookChapter chapterPart : this.chapterList) {
//				String html = Util.getChapter(this.url + chapterPart.getChapterFileName());
				String html;
				try {
					html = Util.getChapter(url, chapterPart);
					Util.writeFile(store + chapterPart.getChapterFileName() , html);
					if(logStorge!=null) {
						logStorge.push(Thread.currentThread() + " download " + store + chapterPart.getChapterFileName() );
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					logger.error("Download Chapter failed, ChapterName is "  + chapterPart.getChapterName() + ", URL is " + url + "\\" + chapterPart.getChapterFileName());
				}
				
			}
			if(null != latch) {
				logger.info(Thread.currentThread().getName() + " done!");
				latch.countDown();
			}			
	}
	
	public static void main(String[] args) {
		String store = "E:\\Book\\zzz";
		String url = "http://www.xbiquge.com/10_10916/";
		List<BookChapter> chapterList = new ArrayList<BookChapter>();
		chapterList.add(new BookChapter("1","5652411.html",""));
		DownLoadLogStorge storge = new DownLoadLogStorge();
		new Thread(new LogPrint(storge)).start();
		new DownloadThread(store,url,chapterList,storge).start();
	}
}
