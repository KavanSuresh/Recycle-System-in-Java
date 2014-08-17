package controller;

/**
 * @author Preethi Yellappa
 * 
 * Class contains item's attributes
 * 
 */

public class NewsPaper {
	
	// Private data members
	private String name;
	private double weight;
	private final String type = "Paper";
	
	// Constructor
	public NewsPaper() {
		name = "News Paper";
		weight = 2;
	}
	
	
	// setters and getters
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
