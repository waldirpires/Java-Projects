package wallethub;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

import wallethub.TopPhrases.Phrase;

public class Split {
    private File file;

    public Split(File file) {
        this.file = file;
    }

    // Processes the given portion of the file.
    // Called simultaneously from several threads.
    // Use your custom return type as needed, I used String just to give an example.
    public Long processPart1(String id, long start, long end, long size)
        throws Exception
    {
    	BufferedReader reader = new BufferedReader(new FileReader(file.getName()));
    	reader.skip(start);
        int ch;
        char charToSearch='|';
        long counter=0;
        int charCount = 0;
        long totalChar = size/2;
        StringBuffer progressBar = new StringBuffer(id + ": ");
        while((ch=reader.read()) != -1 && charCount <= (end-start)) {
        	charCount++;
            if(charToSearch == (char)ch) {
                counter++;
            }
            if (charCount % (size/100) == 0){
            	progressBar.append(".");
            	System.out.println(String.format("%3.1f : %s", charCount/(float)totalChar*100, progressBar.toString()));
            }
        };
        System.out.println();
        reader.close();

        System.out.println(String.format("[%d->%d] %s: a occurs %d times", start, end, id, counter));
        System.out.println(id + ": char count: " + charCount);
        return counter;
        
    }

    // Processes the given portion of the file.
    // Called simultaneously from several threads.
    // Use your custom return type as needed, I used String just to give an example.
    public Map<Integer, Phrase> processPart(long start, long end)
        throws Exception
    {
        InputStream is = new FileInputStream(file);
        is.skip(start);
        // do a computation using the input stream,
        // checking that we don't read more than (end-start) bytes
        System.out.println("Computing the part from " + start + " to " + end);
        Thread.sleep(1000);
        System.out.println("Finished the part from " + start + " to " + end);

        is.close();
        return new HashMap<>();
    }

    // Creates a task that will process the given portion of the file,
    // when executed.
    public Callable<Long> processPartTask1(final String id, final long start, final long end, final long size) {
        return new Callable<Long>() {
            public Long call()
                throws Exception
            {
                return processPart1(id, start, end, size);
            }
        };
    }

    // Creates a task that will process the given portion of the file,
    // when executed.
    public Callable<Map<Integer, Phrase>> processPartTask(final long start, final long end) {
        return new Callable<Map<Integer, Phrase>>() {
            public Map<Integer, Phrase> call()
                throws Exception
            {
                return processPart(start, end);
            }
        };
    }

    // Splits the computation into chunks of the given size,
    // creates appropriate tasks and runs them using a 
    // given number of threads.
    public long processAllTask1(int noOfThreads, int chunkSize)
        throws Exception
    {
        int count = (int)((file.length() + chunkSize - 1) / chunkSize);
        java.util.List<Callable<Long>> tasks = new ArrayList<Callable<Long>>(count);
        for(int i = 0; i < count; i++)
            tasks.add(processPartTask1(String.valueOf(i+1), i * chunkSize, Math.min(file.length(), (i+1) * chunkSize), file.length()));
        ExecutorService es = Executors.newFixedThreadPool(noOfThreads);

        java.util.List<Future<Long>> results = es.invokeAll(tasks);
        es.shutdown();

        // use the results for something
        long sum = 0;
        for(Future<Long> result : results){
            System.out.println(result.get());
            sum += result.get();
        }
        return sum;
    }

    public static int getNumberOfSentences(String fileName) throws IOException
    {
    	long size = new File(fileName).length();
    	System.out.println("File size: " + size + " bytes");
    	BufferedReader reader = new BufferedReader(new FileReader(fileName));
        int ch;
        char charToSearch='\n';
        int counter=0;
        int charCount = 0;
        while((ch=reader.read()) != -1) {
        	charCount++;
            if(charToSearch == (char)ch) {
                counter++;
            }
            if (charCount % (size/100) == 0){
            	System.out.print(".");
            }
        };
        System.out.println();
        reader.close();

        System.out.println("a occurs " + counter+ " times");
        return counter;
    }
    
    public static void main(String argv[])
        throws Exception
    {
    	long time = System.currentTimeMillis();
        String fileName = "sample11.txt";
		int numSentences = getNumberOfSentences(fileName);
//        Split s = new Split(new File(fileName));
//        long numSentences = s.processAllTask1(8, 1000000); //10MB per chunk
		time = System.currentTimeMillis() - time;
		System.out.println("DONE: " + time + " ms");
		System.out.println("Sentences: " + numSentences);
    }
}