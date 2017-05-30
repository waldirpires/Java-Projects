package amazon.donut.challenge;

public class TeamMember {

	private String name;
	private String donutType;
	
	public TeamMember(String name, String donutType) {
		super();
		this.name = name;
		this.donutType = donutType;
	}
	
	@Override
	public String toString() {
		return "[".concat(name).concat(", ").concat(donutType).concat("]");
	}
}
