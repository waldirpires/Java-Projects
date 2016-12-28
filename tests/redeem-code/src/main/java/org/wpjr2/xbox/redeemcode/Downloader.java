package org.wpjr2.xbox.redeemcode;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Downloader {
	private CyclicBarrier barrier;
	private final static int NUMBER_OF_DOWNLOADING_THREADS = 10;

	private class DownloadingThread extends Thread {
		private final String url;

		public DownloadingThread(String url) {
			super();
			this.url = url;
		}

		@Override
		public void run() {
			try {
				barrier.await();
				
				barrier.await(); // label2
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}

	public void startDownload() throws InterruptedException, BrokenBarrierException {
		// plus one for the main thread of execution
		barrier = new CyclicBarrier(NUMBER_OF_DOWNLOADING_THREADS + 1); // label0
		for (int i = 0; i < NUMBER_OF_DOWNLOADING_THREADS; i++) {
			new DownloadingThread("http://www.flickr.com/someUser/pic" + i + ".jpg").start();
		}
		barrier.await(); // label3
		System.out.println("Please wait...");
		barrier.await(); // label4
		System.out.println("Finished");
	}
}