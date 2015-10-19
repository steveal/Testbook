package com.book.util;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import com.book.BookChapter;

public class DownloadThread extends Thread {

	private List<BookChapter> chapterList;
	private String store;
	private String url;
	private CountDownLatch latch;

	public void setCd(CountDownLatch latch) {
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
			latch.countDown();
	}
}
