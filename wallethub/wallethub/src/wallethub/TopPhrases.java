package wallethub;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
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

	/**
	 * inner class to store phrase data, including line, column (position in the line), frequency and hashcode
	 */
	class Phrase implements Comparable<Phrase>
	{
		int line; // line/row in the text file
		int col; // collumn in the text file 
		int frequency; // frequency of phrase in the file
		int hashCode; // hashcode for phrase, since we are not allowed to store the sentences themselves in memory when analyzing the entire file
		
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

		/**
		 * compareTo checks whether the frequency is equal or different
		 */
		@Override
		public int compareTo(Phrase o) {
			return o.frequency - this.frequency;
		}
	}
	
	/**
	 * comparator implemented to sort phrases based on their position in the file
	 * this is used for better organizing the process of getting the desired top sentences
	 *
	 */
	class PositionComparator implements Comparator<Phrase>
	{
		@Override
		public int compare(Phrase o1, Phrase o2) {
			return o1.line - o2.line + o1.col-o2.col;
		}		
	}

	/**
	 * frequency comparator between phrases
	 *
	 */
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
		int lineNumber = 0;
		long numCharsRead = 0;
		
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		
		String line = br.readLine();
		// O(n) where n = number of lines in text
		System.out.print("[");
		while(line != null)
		{
			String[] split = line.split(SPLIT_REGEX);
			// O(n x m) where m = number of sentences per line
			numCharsRead += line.length();
			Phrase p = null;
			for (int collumn = 0; collumn < split.length; collumn++)
			{
				String sentence = split[collumn].trim().toLowerCase();
				int hashCode = sentence.hashCode();
				if (hashCode == 0)
				{
					continue;
				}
				// sentence does not exist, create it and store at the hash map
				// assuming we are not allowed to store the sentence itself, since there are too many of them
				// so we store its hashcode, location (line, column) and frequency so that we can retrieve it later
				p = phrases.get(sentence.hashCode());
				if (p == null)
				{
					p = new Phrase(lineNumber, collumn, hashCode, 1);
					phrases.put(hashCode, p);
				} else { // sentence already exists, retrieve it from the hash map and update its frequency
					p.frequency++;
				}
			}
			lineNumber++;
			// this was put to help when testing huge files, one dot per megabyte
			// tested with a file as big as 12GB
			if (numCharsRead % (1024000) == 0)
			{
				System.out.print(".");
			}
			line = br.readLine();
		}
		br.close();
		System.out.println("]");
		// statistics information
		LOG.info("Total number of characters read: " + numCharsRead);
		LOG.info("Number of single sentences found: " + phrases.size());		
		return phrases;
	}
	
	// input validation
	private void validateInput(String fileName, int topSize)
	{  // if input is invalid
		if (fileName == null || fileName.trim().isEmpty() || topSize <=0)
		{
			LOG.severe("ERROR: invalid parameters defined.");
			throw new RuntimeException("ERROR 001: invalid parameters defined.");
		}
		
		File file = new File(fileName);
		// if file does not exist or if it´s empty
		if (!file.exists() || file.length() == 0)
		{
			LOG.severe("ERROR: file informed does NOT exist or empty: " + fileName);
			throw new RuntimeException("ERROR 002: invalid parameters defined.");
		}
		LOG.info("File size: " + file.length() + " bytes");
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
	 * Algorithm works as follows:
	 * <ul>
	 * <li>1. Retrieve the set of sentences from the file with the frequency of each of them (hashmap of hashcode with phrase data)</li>
	 * <li>2. Sort the list of sentences by frequency in decreasing order</li>
	 * <li>3. Get the sublist of sentences (most frequent ones using a limit/top size)</li>
	 * <li>4. Retrieve the actual sentences from the file based on the phrase data (i.e. line and column)</li>
	 * <li></li>
	 * </ul>
	 */
	public Set<String> findTopPhrases(String fileName, int topSize)
	{
		Set<String> topPhrases = null;
		long time = System.currentTimeMillis();
		
		validateInput(fileName, topSize);
		
		try {
			// a hashmap to store the phrases themselves
			// O(n x m), where n = number of lines in file and m = number of sentences per line
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
			// to organize it serially for reading the sentences from the file
			// O(nlogn) where n = number of sentences to be retrieved
			Collections.sort(phrasesList, new PositionComparator());
			// retrieve the top most frequent sentences considering the topSize
			topPhrases = retrieveTopPhrases(fileName, phrasesList);
		} catch (IOException e) {
			LOG.severe(e.getMessage());
		}
		
		time = System.currentTimeMillis() - time;
		LOG.info("DONE: " + time + " ms");
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
		
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		
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
		return topPhrases;
	}

	public static void main(String[] args) throws IOException {
		
		Set<String> result = null;
		TopPhrases tp = new TopPhrases();
		String fileName = null;
		String expectedErrorCode = null;
		
		// test with invalid parameters, expected exception
		expectedErrorCode = "001";
		try {
			tp.findTopPhrases(fileName, -1);
		} catch (Exception e) {
			checkAssert(e.getMessage().contains(expectedErrorCode), "Expected error code: " + expectedErrorCode);
		}
		
		// test with non-existent file, expected exception
		expectedErrorCode = "002";
		try {
			tp.findTopPhrases("test.blah", 2);
		} catch (Exception e) {
			checkAssert(e.getMessage().contains(expectedErrorCode), "Expected error code: " + expectedErrorCode);
		}
				
		// test with empty file
		fileName = "test.txt";
		File f = new File(fileName);
		if (f.exists())
		{
			f.delete();
		}
		f.createNewFile();
		
		// test with non-existent file, expected exception
		expectedErrorCode = "002";
		try {
			tp.findTopPhrases(fileName, 2);
		} catch (Exception e) {
			checkAssert(e.getMessage().contains(expectedErrorCode), "Expected error code: " + expectedErrorCode);
		}
		
		// test with invalid topSize
		expectedErrorCode = "001";
		try {
			tp.findTopPhrases(fileName, -2);
		} catch (Exception e) {
			checkAssert(e.getMessage().contains(expectedErrorCode), "Expected error code: " + expectedErrorCode);
		}
		
		// test with valid file and topSize
		FileWriter fw = new FileWriter(f);
		fw.write("the book is on the table | the sky is blue | the book is on the table| the sky is blue|The book is on the table | the garden is green | today is monday \n the book os under the table | the sky is blue | the book is on the table");
		fw.close();
		
		result = tp.findTopPhrases(fileName, 1);
		checkAssert(result.size() == 1, "Expected one top sentence");
	}
	
	private static void checkAssert(boolean result, String msg)
	{
		if (!result)
		{
			throw new RuntimeException("Error: " + msg);
		}
	}
	
}
