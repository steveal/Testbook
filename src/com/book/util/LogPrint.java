package com.book.util;



public class LogPrint implements Runnable{
	public DownLoadLogStorge logStorge;
	private boolean isFinished = false;

	public LogPrint(DownLoadLogStorge _logStorge) {
		this.logStorge = _logStorge;
	}
	
	@Override
	public void run() {
		while(true && !isFinished) {
			try {
				String log = logStorge.pop();
				System.out.println(log);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
		System.out.println("Log Thread finished");
	}

	public boolean isFinished() {
		return isFinished;
	}

	public void setFinished(boolean isFinished) {
		this.isFinished = isFinished;
	}
}
