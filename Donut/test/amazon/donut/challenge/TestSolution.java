package amazon.donut.challenge;

import java.util.Arrays;

import org.junit.Test;

public class TestSolution {

	@Test
	public void testMatchDonuts(){
		String [][] inputMembers = {{"rob", "coffee"}, {"beth", "vegan"}};
		String [][] inputDonuts = {{"cruller", "vegan"}, {"beignet", "coffee"}};
		
		printResult(inputMembers, inputDonuts);
	}

	private void printResult(String[][] inputMembers, String[][] inputDonuts) {
		String [][] matches = Solution.matchDonuts(inputDonuts, inputMembers);
		if (matches != null){
			for (String [] l: matches){
				System.out.println(Arrays.toString(l));
			}
		}
	}
	
	@Test
	public void testMatchDonuts2(){
		String [][] inputMembers = {{"jose", "vegan"}, {"john", "chocolate"}, {"mary", "*"}};
		String [][] inputDonuts = {{"cruller", "vegan"}, {"eclair", "chocolate"}};
		
		printResult(inputMembers, inputDonuts);
	}
	
	@Test
	public void testMatchDonuts3(){
		String [][] inputMembers = {{"jose", "*"}, {"john", "*"}, {"mary", "*"}};
		String [][] inputDonuts = {{"cruller", "vegan"}, {"eclair", "chocolate"}};
		
		printResult(inputMembers, inputDonuts);
	}

	@Test
	public void testMatchDonuts4(){
		String [][] inputMembers = {{"jose", "blah"}, {"john", "bleh"}, {"mary", "bli"}};
		String [][] inputDonuts = {{"cruller", "vegan"}, {"eclair", "chocolate"}};
		
		printResult(inputMembers, inputDonuts);
	}
	
	@Test
	public void testMatchDonuts5(){
		String [][] inputMembers = {{"john", "chocolae"}};
		String [][] inputDonuts = {{"eclair", "chocolate"}, {"applefritter", "yummy"}};
		
		printResult(inputMembers, inputDonuts);
	}
	
	@Test
	public void testMatchDonuts6(){
		String [][] inputMembers = {{"", ""}};
		String [][] inputDonuts = {{"", ""}, {"", ""}};
		
		printResult(inputMembers, inputDonuts);
	}
	
}
