package com.test;

import org.junit.Assert;
import org.junit.Test;

public class Solution3Test {

	@Test
	public void testOne(){
		String str = "(h[e{l<l>o}!]~)()()()(";
		int result = Solution3.hasBalancedBrackets(str);
		Assert.assertEquals(0, result);
	}

	@Test
	public void testTwo(){
		String str = "[]()[]<>";
		int result = Solution3.hasBalancedBrackets(str);
		Assert.assertEquals(1, result);
	}
	@Test
	public void testThree(){
		String str = "[([<>])]";
		int result = Solution3.hasBalancedBrackets(str);
		Assert.assertEquals(1, result);
	}
	@Test
	public void testFour(){
		String str = "[[{<(]";
		int result = Solution3.hasBalancedBrackets(str);
		Assert.assertEquals(0, result);
	}

}
