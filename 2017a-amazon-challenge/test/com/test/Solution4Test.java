package com.test;

import org.junit.Assert;
import org.junit.Test;

public class Solution4Test {

	@Test
	public void testOne()
	{
		int []a = {5, 6, 3, 1, 2, 4};
		int t = 6;
		int n1 = 2;
		int n2 = 4;
		int dist = Solution4.bstDistance(a, t, n1, n2);
		Assert.assertEquals(3, dist);
	}

	@Test
	public void testTwo()
	{
		int []a = {9, 7, 5, 3, 1};
		int t = 5;
		int n1 = 7;
		int n2 = 20;
		Solution4.bstDistance(a, t, n1, n2);
		int dist = Solution4.bstDistance(a, t, n1, n2);
		Assert.assertEquals(-1, dist);
	}

	@Test
	public void testThree()
	{
		int []a = {9, 7, 5, 3, 1};
		int t = 5;
		int n1 = 7;
		int n2 = 9;
		int dist = Solution4.bstDistance(a, t, n1, n2);
		Assert.assertEquals(1, dist);
	}
	
	@Test
	public void testFour()
	{
		int []a = {9, 7, 5, 3, 1};
		int t = 5;
		int n1 = 7;
		int n2 = 7;
		int dist = Solution4.bstDistance(a, t, n1, n2);
		Assert.assertEquals(0, dist);
	}
	
}
