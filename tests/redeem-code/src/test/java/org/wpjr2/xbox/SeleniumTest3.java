package org.wpjr2.xbox;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.ListUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SeleniumTest3 {
	
	private int numThreads = 4;
	
	@Before
	public void setUp() throws Exception {
		System.setProperty("webdriver.chrome.driver", "c://dev/chromedriver.exe");
	}

	@Test
	public void testJavawebdriver() throws Exception {
		
		List<String> codes = new ArrayList<String>();
		
		StringBuffer code = new StringBuffer("437KY-*&X3WKY4GJ");
		String chars1 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		String chars2 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		String chars3 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		for (int i = 0; i < chars1.length(); i++){
			code.replace(5,6, chars1.charAt(i)+"");
			for (int k = 0; k < chars2.length(); k++){
				code.replace(6,7, chars2.charAt(k)+"");
				for (int l = 0; l < chars3.length(); l++){
					code.replace(7,8, chars3.charAt(l)+""); 
					codes.add(code.toString());
				}				
			}
		}
		System.out.println("Codes generated: " + codes.size());
		int targetSize = codes.size()/numThreads;
		List<List<String>> output = ListUtils.partition(codes, targetSize);
		for (int i = 0; i < numThreads; i++){
			SeleniumThread t = new SeleniumThread(output.get(i));
			t.start();
		}
	}

	@After
	public void tearDown() throws Exception {
	}
}
