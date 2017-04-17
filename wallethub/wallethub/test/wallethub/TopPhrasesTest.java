package wallethub;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

public class TopPhrasesTest {

	@Test
	public void testFindTopPhrases() throws IOException
	{
		FileUtils.writeStringToFile(new File("test.txt"), "the book is on the table | the sky is blue | the book is on the table");
		
		TopPhrases tp = new TopPhrases();
		Set<String> findTopPhrases = tp.findTopPhrases("test.txt", 1);
	}
}
