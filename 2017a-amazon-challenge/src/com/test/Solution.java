package com.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Solution {
	// how much wood would a woodchuck chuck if a woodchuck could chuck wood chuck

	public static void main(String[] args) {
 		 Map<String, List<Integer>> mapWords = new TreeMap<>();
 		 Map<String, Integer> mapDistance = new TreeMap<>();
		 Helper helper = new Helper();
	        int limit = helper.limit();
	        int pos = 1;
	        while(helper.hasNext()) {
				String word = helper.next();
				if (mapWords.get(word)==null){
					mapWords.put(word, new ArrayList<>());
				}
				mapWords.get(word).add(pos);
				pos = pos + 1;
	        }
	        for (String k: mapWords.keySet()){
	        	if (mapWords.get(k).size() == 1)
	        		continue;
		        int i = 0;
	        	while (i < mapWords.get(k).size()-1){
	        		int distance = mapWords.get(k).get(i+1) - mapWords.get(k).get(i);
	        		if (distance <= limit){
	        			if (mapDistance.get(k)== null || distance < mapDistance.get(k)){
	        				mapDistance.put(k, distance);
	        			}
	        		}
	        		i++;
	        	}
	        }
	        for (String s: mapDistance.keySet()){
	        	System.out.println(s + " " + mapDistance.get(s));
	        }
	}
}
