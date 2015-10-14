package com.book;

public class BookChapter {
	String chapterName = "";
	String chapterFileName = "";
	String chapterIndex = "";
	String previousChapterFileName = "";
	String nextChapterFileName = "";

	public BookChapter(String chapterName, String chapterFileName,
			String chapterIndex) {
		this.chapterFileName = chapterFileName;
		this.chapterName = chapterName;
		this.chapterIndex = chapterIndex;
	}

	public String getChapterName() {
		return chapterName;
	}

	public void setChapterName(String chapterName) {
		this.chapterName = chapterName;
	}

	public String getChapterFileName() {
		return chapterFileName;
	}

	public void setChapterFileName(String chapterFileName) {
		this.chapterFileName = chapterFileName;
	}

	public String getChapterIndex() {
		return chapterIndex;
	}

	public void setChapterIndex(String chapterIndex) {
		this.chapterIndex = chapterIndex;
	}

	public String getPreviousChapterFileName() {
		return previousChapterFileName;
	}

	public void setPreviousChapterFileName(String previousChapterFileName) {
		this.previousChapterFileName = previousChapterFileName;
	}

	public String getNextChapterFileName() {
		return nextChapterFileName;
	}

	public void setNextChapterFileName(String nextChapterFileName) {
		this.nextChapterFileName = nextChapterFileName;
	}

	@Override
	public String toString() {
		return "Index: " + chapterIndex + "  Name: " + chapterName
				+ "  FileName: " + chapterFileName;
	}
}
