package controller;
/**
 * @author Preethi Yellappa
 * 
 * Class contains item's attributes
 * 
 */
public class Glass {
	 
	private String name;
	private double weight;
	private final String type = "Glass";
 
	public Glass() {
		name = "Glass" ;
		weight = 2;
		
		
	}
 
	public String getName() {
		return name;
	}
 
	public void setName(String name) {
		this.name = name;
	}
 
	public double getWeight() {
		return weight;
	}
 
	public void setWeight(double weight) {
		this.weight = weight;
	}
	
	public String getType() {
		return type;
		
	}
}