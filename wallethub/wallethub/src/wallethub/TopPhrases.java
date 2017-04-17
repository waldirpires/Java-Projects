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
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

public class TopPhrases {

	Logger LOG = Logger.getLogger(TopPhrases.class.getName());
	
	private static final String SPLIT_REGEX = "\\|";

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
			return String.format("[%d,%d], %d, %d", line, col, hashCode, frequency);
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
	
	/**
	 * from the file name, it builds the list of phrases along with their frequencies
	 * @param fileName the file name
	 * @return map of phrases by hashcode
	 * @throws IOException
	 */
	private Map<Integer, Phrase> map(String fileName) throws IOException
	{
		// another hashmap to store the phrases themselves
		Map<Integer, Phrase> phrases = new HashMap<>();
		FileReader reader = new FileReader(fileName);
		BufferedReader br = new BufferedReader(reader);
		
		String line = br.readLine();
		int lineNumber = 0;
		// O(n) where n = number of lines in text
		while(line != null)
		{
			String[] split = line.split(SPLIT_REGEX);
			// O(n x m) where m = number of sentences per line
			for (int collumn = 0; collumn < split.length; collumn++)
			{
				String sentence = split[collumn].trim().toLowerCase();
				int hashCode = sentence.hashCode();
				if (hashCode == 0)
				{
					continue;
				}
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
		LOG.info("Number of single sentences found: " + phrases.size());		
		return phrases;
	}
	
	/**
	 * finds the n-top phrases in the text-based file
	 * @param fileName the name of the file
	 * @param topSize the top size for seeking
	 * @return the set of the top n-phrases on the text file
	 * @throws IOException
	 * <p>
	 * Assumptions:
	 * <p><ul>
	 * <li>there is not enough RAM memory to store the entire file nor its entire set of sentences</li>
	 * <li>we only retrieve the set of sentences from the file when it is known the top n most frequent sentences from file.</li>
	 * </ul><p>
	 * 
	 */
	public Set<String> findTopPhrases(String fileName, int topSize) throws IOException
	{
		// another hashmap to store the phrases themselves
		Map<Integer, Phrase> phrases = map(fileName);

		List<Phrase> phrasesList = new ArrayList<>();
		phrasesList.addAll(phrases.values());
		// sorting the phrases by frequency
		// O(nlogn) a variation from MergeSort or QuickSort
		Collections.sort(phrasesList, new FrequencyComparator());
		// calculating the size of the top phrases, in case it is less than the topSize informed
		int max = phrasesList.size() >= topSize?topSize:phrasesList.size();
		// retrieve the sublist that contains the top phrases
		// O(n), where n = number of items to copy to sublist
		phrasesList = phrasesList.subList(0, max);
		// sorting the same list based on the position of the sentence in the text file (serialization)
		// O(nlogn) where n = number of sentences to be retrieved
		Collections.sort(phrasesList, new PositionComparator());
		
		Set<String> topPhrases = retrieveTopPhrases(fileName, phrasesList);

		return topPhrases;
	}

	/**
	 * retrieves the top phrases found in the previous phrases map operation
	 * @param fileName the filename which contains the sentences
	 * @param phrasesList the list of phrases found by the map operation
	 * @return the list of the n-most frequent phrases
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private Set<String> retrieveTopPhrases(String fileName, List<Phrase> phrasesList)
			throws FileNotFoundException, IOException {
		// iterating through the list
		Iterator<Phrase> iterator = phrasesList.iterator();
		Phrase phrase = iterator.next();

		// set of top phrases, ordered by the positon they occur at the text file
		Set<String> topPhrases = new HashSet<>();
		
		FileReader reader = new FileReader(fileName);
		BufferedReader br = new BufferedReader(reader);
		
		String line = br.readLine();
		while(line != null && phrase != null)
		{
			String[] split = line.split(SPLIT_REGEX);
			// O(n x m) where m = number of sentences per line (average)
			for (int collumn = 0; collumn < split.length; collumn++)
			{
				String sentence = split[collumn].trim().toLowerCase();
				int hashCode = sentence.hashCode();
				if (phrase != null && hashCode == phrase.hashCode)
				{
					topPhrases.add(sentence);
					if (iterator.hasNext())
					{
						phrase = iterator.next();
					} else
					{
						phrase = null;
					}
				}
			}
			line = br.readLine();
		}
		br.close();
		reader.close();
		return topPhrases;
	}
	
}
