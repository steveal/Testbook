package com.book.util;

import java.util.List;

import com.book.BookChapter;

public class DownloadThread extends Thread {

	private List<BookChapter> chapterList;
	private String store;
	private String url;

	public DownloadThread(String store, String url, List<BookChapter> chapterList) {
		this.store = store;
		this.url = url;
		this.chapterList = chapterList;
	}
	
	@Override
	public void run() {
		for(BookChapter chapterPart : this.chapterList) {
//			String html = Util.getChapter(this.url + chapterPart.getChapterFileName());
			String html = Util.getChapter(url, chapterPart);
			Util.writeFile(store + chapterPart.getChapterFileName() , html);
			System.out.println("ThreadID: " + Thread.currentThread().getId() + "   " + chapterPart.getChapterFileName() + " done!");
		}
	}
}
