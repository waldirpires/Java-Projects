package wallethub;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class TopPhrases {

	class Pair{
		int left;
		int right;
	}
	
	class Phrase implements Comparable<Phrase>
	{
		int line; // line/row in the text file
		int col; // collumn in the text file 
		int frequency;
		int hashCode;
		
		public Phrase(int line, int col, int hashCode, int frequency)
		{
			this.line = line;
			this.col = col;
			this.hashCode = hashCode;
			this.frequency = 1;
		}
		
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return String.valueOf(hashCode);
		}
		
		
		
		@Override
		public boolean equals(Object obj) {
			if (obj == null) return false;
			return this.toString().equals(obj.toString());
		}
		
		public void setFrequency(int frequency) {
			this.frequency = frequency;
		}

		@Override
		public int compareTo(Phrase o) {
			return o.frequency - this.frequency;
		}
	}
	
	class PositionComparator implements Comparator<Phrase>
	{

		@Override
		public int compare(Phrase o1, Phrase o2) {
			return o1.line - o2.line + o1.col-o2.col;
		}
		
	}
	
	class FrequencyComparator implements Comparator<Phrase>
	{

		@Override
		public int compare(Phrase o1, Phrase o2) {
			return o2.frequency - o1.frequency;
		}
		
	}
	
	public Map<Integer, Phrase> map(String fileName) throws IOException
	{
		// another hashmap to store the phrases themselves
		Map<Integer, Phrase> phrases = new HashMap<>();
		
		FileReader reader = new FileReader(fileName);
		BufferedReader br = new BufferedReader(reader);
		
		String line = br.readLine();
		int lineNumber = 0;
		while(line != null)
		{
			String[] split = line.split("\\|");
			for (int collumn = 0; collumn < split.length; collumn++)
			{
				String sentence = split[collumn].trim();
				int hashCode = sentence.hashCode();
				// sentence does not exist, create it and store at the hash map
				if (phrases.get(sentence.hashCode()) == null)
				{
					Phrase p = new Phrase(lineNumber, collumn, hashCode, 1);
					phrases.put(hashCode, p);
				} else { // sentence already exists, retrieve it from the hash map and update its frequency
					phrases.get(hashCode).frequency++;
				}
			}
			line = br.readLine();
		}
		br.close();
		return phrases;
	}
	
	public Set<String> findTopPhrases(String fileName, int topSize) throws IOException
	{
		// another hashmap to store the phrases themselves
		Map<Integer, Phrase> phrases = map(fileName);

		List<Phrase> phrasesList = new ArrayList<>();
		phrasesList.addAll(phrases.values());
		// sorting the phrases by frequency
		Collections.sort(phrasesList, new FrequencyComparator());
		// calculating the size of the top phrases, in case it is less than the topSize informed
		int max = phrasesList.size() >= topSize?topSize:phrasesList.size();
		// retrieve the sublist that contains the top phrases
		phrasesList = phrasesList.subList(0, max);
		Collections.sort(phrasesList, new PositionComparator());
		
		// iterating through the list
		Iterator<Phrase> iterator = phrasesList.iterator();
		Phrase phrase = iterator.next();

		// set of top phrases, ordered by the positon they occur at the text file
		Set<String> topPhrases = new HashSet<>();
		
		FileReader reader = new FileReader(fileName);
		BufferedReader br = new BufferedReader(reader);
		
		String line = br.readLine();
		int lineNumber = 0;
		while(line != null)
		{
			String[] split = line.split("\\|");
			for (int collumn = 0; collumn < split.length; collumn++)
			{
				String sentence = split[collumn].trim();
				int hashCode = sentence.hashCode();
				if (hashCode == phrase.hashCode)
				{
					topPhrases.add(sentence);
					phrase = iterator.next();
				}
			}
		}

		return topPhrases;
	}
	
	// sort a map by value
	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
	    return map.entrySet()
	              .stream()
	              .sorted(Map.Entry.comparingByValue(/*Collections.reverseOrder()*/))
	              .collect(Collectors.toMap(
	                Map.Entry::getKey, 
	                Map.Entry::getValue, 
	                (e1, e2) -> e1, 
	                LinkedHashMap::new
	              ));
	}
}
