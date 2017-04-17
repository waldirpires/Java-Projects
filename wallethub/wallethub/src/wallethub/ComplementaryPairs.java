package wallethub;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComplementaryPairs {

	public static List<String> findPairs(int []a, int k)
	{
		List<String> pairs = new ArrayList<>();
		
		for (int i = 0; i < a.length; i++)
		{
			if (i > k)
				continue;
			
			for (int j = 0; j < a.length; j++)
			{
				if (i == j || j > k)
					continue;
				if (a[i] + a[j] == k)
				{
					if (!pairs.contains("("+j+","+i+")"))
					{
						pairs.add("("+i+","+j+")");
					}
				}
			}
		}			
		
		return pairs;
	}

	// Overall complexity:
	// time: O(n) - linear
	// space: O(n) - linear
	public static int findPairs2(int []a, int k)
	{
		// O(n) space complexity, O(1) search/insert time complexity
		Map<Integer, Integer> temp = new HashMap<Integer, Integer>();
		// O(n) time complexity
		for (int i = 0; i < a.length; i++)
		{
			// calculate the delta from K, and put this in a hashmap
			temp.put(k - a[i], k - a[i]);
		}			
		int countPairs = 0;
		// O(n) time complexity
		for (int i = 0; i < a.length; i++)
		{
			if (temp.containsKey(a[i])) // O(1) time complexity
			{
				countPairs++;
			}
		}			
		//always returns half, because it finds the pairs twice
		return countPairs/2;
	}
}
