package amazon.donut.challenge;

import java.util.Arrays;

public class Donut {

	private String name;
	private String type;
	
	public Donut(String name, String type) {
		super();
		this.name = name;
		this.type = type;
	}
	
	@Override
	public String toString() {
		return Arrays.asList(name, type).toString(); 
	}
}
