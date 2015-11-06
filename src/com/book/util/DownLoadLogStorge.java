package com.book.util;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class DownLoadLogStorge {
	BlockingQueue<String> queues = new LinkedBlockingQueue<String>(100);

    /**
     * 生产
     * 
     * @param p
     *            产品
     * @throws InterruptedException
     */
    public void push(String p) throws InterruptedException {
        queues.put(p);
    }

    /**
     * 消费
     * 
     * @return 产品
     * @throws InterruptedException
     */
    public String pop() throws InterruptedException {
        return queues.take();
    }
}
