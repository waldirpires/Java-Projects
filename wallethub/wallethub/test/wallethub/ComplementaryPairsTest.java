package wallethub;

import org.junit.Assert;
import org.junit.Test;

public class ComplementaryPairsTest {

	@Test
	public void testSuccess()
	{
		int k = 5;
		int a[] = {4,  5,  6,  3,  1,  8, -7, -6};
		//List<String> pairs = ComplementaryPairs.findPairs(a, k);
		int pairs = ComplementaryPairs.findPairs2(a, k);
		Assert.assertEquals(1, pairs);
		System.out.println(pairs);
	}

	@Test
	public void testSuccess2a()
	{
		int k = 5;
		int a[] = {2, 3, 4, 1, 5, 0};
		int pairs = ComplementaryPairs.findPairs2(a, k);
		Assert.assertEquals(3, pairs);
		System.out.println(pairs);
	}

	@Test
	public void testSuccess2()
	{
		int k = 8;
		int a[] = {2, 3, 4, 1, 5, 7};
		//List<String> pairs = ComplementaryPairs.findPairs(a, k);
		int pairs = ComplementaryPairs.findPairs2(a, k);
		Assert.assertEquals(2, pairs);
		System.out.println(pairs);
	}

	@Test
	public void testEmpty()
	{
		int k = 8;
		int a[] = {};
		//List<String> pairs = ComplementaryPairs.findPairs(a, k);
		int pairs = ComplementaryPairs.findPairs2(a, k);
		Assert.assertEquals(0, pairs);
		System.out.println(pairs);
	}
}
