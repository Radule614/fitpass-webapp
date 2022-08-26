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
	
	public CustomerType() {
		
	}
	
	public CustomerType(CustomerTypeEnum type, double discount, int requiredPoints) {
		this.type = type;
		this.discount = discount;
		this.requiredPoints = requiredPoints;
	}
}
