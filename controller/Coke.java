package controller;

/**
 * @author Preethi Yellappa
 * 
 * This contains coke's attributes
 * 
 */

public class Coke {

	private String name;
	private double weight;
	private final String type = "Metal";

	public Coke() {
		name = "Coke";
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