package wallethub;

import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

public class ComplementaryPairsTest {

	@Test
	public void testSuccess()
	{
		int k = 5;
		int a[] = {4,  5,  6,  3,  1,  8, -7, -6};
		Set<String> result = ComplementaryPairs.findPairs(a, k);
		Assert.assertEquals(2, result.size());
		System.out.println(result);
	}

	@Test
	public void testSuccess2b()
	{
		int k = 5;
		int a[] = {2,  1,  -7, -6};
		Set<String> result = ComplementaryPairs.findPairs(a, k);
		Assert.assertEquals(2, result.size());
		System.out.println(result);
	}

	@Test
	public void testSuccess2a()
	{
		int k = 5;
		int a[] = {2, 3, 4, 1, 5, 0};
		Set<String> result = ComplementaryPairs.findPairs(a, k);
		Assert.assertEquals(3, result.size());
		System.out.println(result);
	}

	@Test
	public void testSuccess2()
	{
		int k = 8;
		int a[] = {2, 3, 4, 1, 5, 7};
		//         6  5  4  7  3  1
		
		//List<String> pairs = ComplementaryPairs.findPairs(a, k);
		Set<String> result = ComplementaryPairs.findPairs(a, k);
		Assert.assertEquals(2, result.size());
		System.out.println(result);
	}

	@Test
	public void testEmpty()
	{
		int k = 8;
		int a[] = {};
		//List<String> pairs = ComplementaryPairs.findPairs(a, k);
		Set<String> result = ComplementaryPairs.findPairs(a, k);
		Assert.assertEquals(0, result.size());
		System.out.println(result);
	}
}
