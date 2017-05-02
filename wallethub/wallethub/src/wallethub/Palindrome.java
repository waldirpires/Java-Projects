package wallethub;

public class Palindrome {

	private static String [] successCases = {"Avid diva", "1221", "ada", "adda"};

	private static String [] failedCases = {"1121", "122", "adam", "", " ", null};
	
	// overall complexity:
	// time: O(n) - linear
	// space: O(1) - constant
	public static boolean isPalindrome(String s)
	{
		// validation
		// if String is null or empty, it should return false
		if (s == null || s.trim().length() == 0)
		{
			return false;
		}
		// assuming that uppercase and lowercase are equal
		s = s.toLowerCase();
		// assuming it is a palindrome, then find counter-proof
		boolean result = true;
		// pointers in the beginning and end of String for testing
		int i = 0, j = s.length()-1;
		// complexity O(n/2) = O(n)
		while (i <=j) 
		{
			if (s.charAt(i) != s.charAt(j))
			{
				result = false;
				break;
			}
			i++;
			j--;
		}		 
		
		return result;
	}
	
	public static void main(String[] args) {
		// success cases
		
		int countTests = 0;
		// success cases
		for (String s: successCases)
		{
			if (!isPalindrome(s))
			{
				countTests++;
			}
		}
		if (countTests!=0)
		{
			throw new RuntimeException("ERROR: expected no failures!");
		}
		// false cases
		countTests = 0;
		for (String s: failedCases)
		{
			if (isPalindrome(s)){
				countTests++;
			}
		}
		if (countTests!=0)
		{
			throw new RuntimeException("ERROR: expected no successes!");
		}
	}
}
