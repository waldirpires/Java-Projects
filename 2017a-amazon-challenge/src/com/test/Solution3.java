package com.test;

import java.util.ArrayDeque;
import java.util.Deque;

public class Solution3 {

	private static String BRACKETS_OPEN = "({[<";
	private static String BRACKETS_CLOSE = ")}]>";
	
	// METHOD SIGNATURE BEGINS, THIS METHOD IS REQUIRED
	public static int hasBalancedBrackets(String str)
	{
		Deque<Integer> stack = new ArrayDeque<Integer>();
		if (str == null)
		{
			return 0;
		}
		for (char c: str.toCharArray())
		{
			if (BRACKETS_OPEN.indexOf(c) != -1)
			{
				stack.push((int)c);
			} 
			else if (BRACKETS_CLOSE.indexOf(c) != -1)
			{
				if (c == ')' && stack.peek().equals((int)'('))
				{
					stack.pop();
				}
				else if (c == '}' && stack.peek().equals((int)'{'))
				{
					stack.pop();
				}
				else if (c == ']' && stack.peek().equals((int)'['))
				{
					stack.pop();
				}
				else if (c == '>' && stack.peek().equals((int)'<'))
				{
					stack.pop();
				}				
			}
		}		
		return stack.isEmpty()?1:0;
	}
	
}
