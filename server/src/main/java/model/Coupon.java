package model;

public class Coupon {
    public String id;
    public String name;
    public double size;
    public Coupon(){}

    public Coupon(String name, double size) {
        this.name = name;
        this.size = size;
    }

    public static class CreateCouponException extends Exception{
        public CreateCouponException(String message){
            super(message);
        }
    }

    public static class DeleteCouponException extends Exception{
        public DeleteCouponException(String message){
            super(message);
        }
    }
}
