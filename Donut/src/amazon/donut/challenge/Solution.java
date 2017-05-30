package amazon.donut.challenge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class Solution {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String[][] donutConstraintPairs = null;
		String[][] candidateConstraintPairs = null;
		
		List<String> members = new ArrayList<>();
		List<String> donuts = new ArrayList<>();
		
		System.out.println("Donut Mapper:\n");
		int option = 1;
		while (option != 3){
			System.out.println("Type 1 to add team member, 2 to add donut and 3 to continue . . .");
			option = sc.nextInt();
			if (option != 3){
				String entry = null;
				System.out.println("Type in the entry separated by commas:");
				entry = sc.next();
				entry = entry.concat(sc.next());
				if (option == 1){
					members.add(entry);
					System.out.println("Added member: " + entry);
				} else {
					donuts.add(entry);
					System.out.println("Added donut: " + entry);
				}
			}
		}
		// prepare values for matching donuts
		candidateConstraintPairs = new String[members.size()][2];
		for (int i = 0; i < members.size(); i++){
			String [] values = members.get(i).split(",");
			candidateConstraintPairs[i] = values;
		}
		System.out.println("Members: ");
		printList(candidateConstraintPairs);
		donutConstraintPairs = new String[donuts.size()][2];
		for (int i = 0; i < donuts.size(); i++){
			String [] values = donuts.get(i).split(",");
			donutConstraintPairs[i] = values;
		}
		System.out.println("Donuts: ");
		printList(donutConstraintPairs);
		
		sc.close();
	}
	
	static String[][] matchDonuts(String[][] donutConstraintPairs, 
			String[][] candidateConstraintPairs) {

		String[][] matches = null;
		
		// mapping donuts
		Map<String, String> mapDonuts = createMapFromList(donutConstraintPairs);
		
		mapDonuts = invertMap(mapDonuts);
		// mapping candidates
		Map<String, String> mapCandidates = createMapFromList(candidateConstraintPairs);
		
		Map<String, Set<String>> mapCandidatesDonuts = new TreeMap<String, Set<String>>();
		
		int totalDonuts = 0;
		for (String s: mapCandidates.keySet()){
			String donutPreference = mapCandidates.get(s);
			if (mapCandidatesDonuts.get(s) == null){
				mapCandidatesDonuts.put(s, new TreeSet<String>());
			}
			if (mapDonuts.containsKey(donutPreference)){
				mapCandidatesDonuts.get(s).add(mapDonuts.get(donutPreference));
				totalDonuts++;
			} else if (donutPreference.trim().equals("*")){
				for (String t: mapDonuts.keySet()){
					mapCandidatesDonuts.get(s).add(mapDonuts.get(t));
					totalDonuts++;
				}
			}
		}
		
		if (totalDonuts > 0){
			matches = new String[totalDonuts][2];

			int i = 0;
			for (String member: mapCandidatesDonuts.keySet()){
				for (String donut: mapCandidatesDonuts.get(member)){
					matches[i][0] = member;
					matches[i][1] = donut;
					i++;
				}
			}			
		}		
		return matches;
    }
	
	static Map<String, String> createMapFromList(String [][] list){
		TreeMap<String, String> map = new TreeMap<String, String>();
		for (int i = 0; i < list.length; i++){
			if (!isEmpty(list[i][0]) && !map.containsKey(list[i][0])){
				map.put(list[i][0], list[i][1]);
			}
		}
		return map;
		
	}
	
	static boolean isEmpty(String s){
		return s == null || s.trim().equals("");
	}
	
	static Map<String, String> invertMap(Map<String, String> m){
		Map<String, String> reversedHashMap = new HashMap<String, String>();
		for (String key : m.keySet()){
		    reversedHashMap.put(m.get(key), key);
		}		
		return reversedHashMap;
	}

	static void printList(String [][] list){
		for (String[] l: list){
			System.out.println(l[0] + ", " + l[1]);
		}
	}
	
}

