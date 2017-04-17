package wallethub;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

public class TopPhrasesTest {

	@Test
	public void testFindTopPhrases() throws IOException
	{
		FileUtils.writeStringToFile(new File("test.txt"), 
				"the book is on the table | the sky is blue | the book is on the table| the sky is blue|The book is on the table");
		
		TopPhrases tp = new TopPhrases();
		Set<String> findTopPhrases = tp.findTopPhrases("test.txt", 1);
		
		Assert.assertThat(findTopPhrases.size(), CoreMatchers.equalTo(1));
		Assert.assertThat(findTopPhrases.iterator().next(), CoreMatchers.equalTo("the book is on the table"));
	}

	@Test
	public void testFindTopPhrases2() throws IOException
	{
		FileUtils.writeStringToFile(new File("test.txt"), 
				"the book is on the table | the sky is blue | the book is on the table| the sky is blue|The book is on the table | the garden is green | today is monday \n the book os under the table | the sky is blue | the book is on the table");
		
		TopPhrases tp = new TopPhrases();
		Set<String> findTopPhrases = tp.findTopPhrases("test.txt", 2);
		
		Assert.assertThat(findTopPhrases.size(), CoreMatchers.equalTo(2));
		Assert.assertThat(findTopPhrases.toString().contains("the book is on the table"), CoreMatchers.equalTo(true));
		Assert.assertThat(findTopPhrases.toString().contains("the sky is blue"), CoreMatchers.equalTo(true));
	}

	@Test
	public void testFindTopPhrases3() throws IOException
	{
		TopPhrases tp = new TopPhrases();
		Set<String> findTopPhrases = tp.findTopPhrases("sample.txt", 5);
		
		Assert.assertThat(findTopPhrases.size(), CoreMatchers.equalTo(5));
		Assert.assertThat(findTopPhrases.toString().contains("1"), CoreMatchers.equalTo(true));
		Assert.assertThat(findTopPhrases.toString().contains("s"), CoreMatchers.equalTo(true));
	}

	@Test
	public void testFindTopPhrases4() throws IOException
	{
		TopPhrases tp = new TopPhrases();
		Set<String> findTopPhrases = tp.findTopPhrases("sample2.txt", 5);
		
		Assert.assertThat(findTopPhrases.size(), CoreMatchers.equalTo(3));
//		Assert.assertThat(findTopPhrases.toString().contains("the book is on the table"), CoreMatchers.equalTo(true));
//		Assert.assertThat(findTopPhrases.toString().contains("the sky is blue"), CoreMatchers.equalTo(true));
	}
}
