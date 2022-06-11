package model.customer;

public class CustomerType {
	public CustomerTypeEnum type;
	public double discount;
	public int requiredPoints;
	
	
	public enum CustomerTypeEnum {
		BRONZE, 
		SILVER,
		GOLD
	}
}
