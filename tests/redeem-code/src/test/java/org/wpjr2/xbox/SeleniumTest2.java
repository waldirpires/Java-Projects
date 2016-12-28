package org.wpjr2.xbox;

import org.junit.Test;

public class SeleniumTest2 {

	StringBuffer code = new StringBuffer("437KY-*&X3WKY4GJ");
	String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

	@Test
	public void testJavawebdriver() throws Exception {
		int count = 0;
		int threadMaxCount = 2;
		int numThreads = 0;
		for (int h = 0; h < 1; h++) {
			code.replace(4, 5, chars.charAt(h) + "");
			for (int i = 0; i < chars.length(); i++) {
				code.replace(5, 6, chars.charAt(i) + "");
				//System.out.println(count + ": " + code);
				for (int k = 0; k < chars.length(); k++) {
					code.replace(6, 7, chars.charAt(k) + "");
					for (int l = 0; l < chars.length(); l++) {
						count++;
						code.replace(7, 8, chars.charAt(l) + "");
						System.out.println(count + ": " + code);
						SeleniumThread t = new SeleniumThread(code.toString());
						t.start();
						t.join();
					}
				}
			}
		}
	}
}
