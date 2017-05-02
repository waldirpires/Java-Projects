package wallethub;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * provides the complementary pairs whose sum gives value k
 */
public class ComplementaryPairs {

	private static int a[][] = {{}, // empty array, no pairs 
			                    {4,  5,  6,  3,  1,  8, -7, -6}, // expected two pairs
								{2,  1,  -7, -6}, // expected two pairs
								{2, 3, 4, 1, 5, 0} // expected three pairs
	};
	
	public static void main(String[] args) {
		// test case 1: empty array, no pairs
		Set<String> result = findPairs(a[0], 10);
		System.out.println(result.size() == 0);
		
		// test case 2: valid array, no pairs found
		result = findPairs(a[1], 17);
		System.out.println(result.size() == 0);
		
		// test case 3: valid array, expected two pairs
		result = findPairs(a[1], 5);
		System.out.println(result.size() == 2);
		
		// test case 4: valid array with negative values, expected two pairs
		result = findPairs(a[2], 5);
		System.out.println(result.size() == 2);
		
		// test case 5: valid array with negative values, expected three pairs
		result = findPairs(a[3], 5);
		System.out.println(result.size() == 3);		
		
		// test case 6: valid array, negative k, expected one pair
		result = findPairs(a[1], -5); 
		System.out.println(result.size() == 1);
	}
	
	/**
	 * method that returns the pairs x,y from an array whose sum is equal to k
	 * @param a the array containing the values
	 * @param k the value to which the pairs sum need to be equal to
	 * @return the list of pairs whose sum equals k
	 * <p>
	 * 	Overall complexity:
	 * <p>
	 * time: O(n) - linear
	 * <p>
	 * space: O(n) - linear
	 *<p> 
	 * Algorithm:
	 * <p>
	 * 1. calculate the delta for each position in relation to k, store this delta in a hashmap
	 * <p>
	 * 2. for each value in original array:
	 * <p>
	 * 2.1 if the complement value exists in the map
	 * <p>
	 * 2.1.1 store it as a pair
	 * <p>
	 * 3. Return pairs
	 */
	public static Set<String> findPairs(int []a, int k)
	{
		// map to store the diffs
		// O(n) space complexity, O(1) search/insert time complexity
		Map<Integer, Integer> temp = new HashMap<Integer, Integer>();
		// set to store the results
		// O(n) space complexity, O(n) search/insert time complexity
		Set<String> result = new HashSet<>();
		
		// O(n) time complexity
		for (int i = 0; i < a.length; i++)
		{
			// calculate the delta from K, and put this in a hashmap
			int key = a[i] >=0?k - a[i]:-(a[i]+k);
			temp.put(key, i);
		}			
		// O(n) time complexity
		for (int i = 0; i < a.length; i++)
		{	// obtaining the pair x and y
			int x = a[i];
			// if map contains key, return value, otherwise, return -1
			int y = temp.get(a[i])!=null?a[temp.get(a[i])]:-1;
			// checking for (x, y)
			if (temp.containsKey(x) && // if the hashmap contains the key, O(1) time complexity  
					i != temp.get(x) && // and the index values are different, to prevent a single value that is half of k of becoming a pair
					!result.contains(String.format("[%d, %d]", y, x))) // and it does not contain the duplicate (y, x), assuming (x, y) is the same as (y, x)
			{	// adding result
				result.add(String.format("[%d, %d]", x, y));
			}
		}			
		return result;
	}
}
