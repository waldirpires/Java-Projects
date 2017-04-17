package wallethub;

public class Palindrome {

	// overall complexity:
	// time: O(n) - linear
	// space: O(1) - constant
	public static boolean isPalindrome(String s)
	{
		// if String is null or empty, it should return false
		if (s == null || s.trim().length() == 0)
		{
			return false;
		}
		// assuming that uppercase and lowercase are equal
		s = s.toLowerCase();
		boolean result = true;
		// assuming it is a palindrome, then find counter-proof
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
}
