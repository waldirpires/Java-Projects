package wallethub;

import org.junit.Assert;
import org.junit.Test;

public class PalindromeTest {

	String [] successCases = {"Avid diva", "1221", "ada"};

	String [] failedCases = {"1121", "122", "adam", "", " "};

	// success case
	@Test
	public void testSuccess()
	{
		for (String s: successCases)
		{
			boolean result = Palindrome.isPalindrome(s);
			Assert.assertTrue(result);
		}
	}
	
	// failure case
	@Test
	public void testFailure()
	{
		for (String s: failedCases)
		{
			boolean result = Palindrome.isPalindrome(s);
			Assert.assertFalse(result);
		}
	}
	
	
	// empty string case
	
	@Test
	public void testEmpty()
	{
		String s= "";
		boolean result = Palindrome.isPalindrome(s);
		Assert.assertFalse(result);
	}
	

	
	// null string case
	@Test
	public void testNull()
	{
		String s= null;
		boolean result = Palindrome.isPalindrome(s);
		Assert.assertFalse(result);
	}
	
	
}
