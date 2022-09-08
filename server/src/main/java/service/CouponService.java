package service;

import dto.AddCouponDTO;
import model.Coupon;
import repository.CouponRepository;
import utility.UIDGenerator;

import java.util.ArrayList;

public class CouponService {
    public static final CouponRepository couponRepository = CouponRepository.getInstance();
    public CouponService(){}

    public ArrayList<Coupon> getAll(){
        return couponRepository.getAll();
    }

    public String createCoupon(AddCouponDTO dto) throws Coupon.CreateCouponException {
        if(dto == null) throw new Coupon.CreateCouponException("Something went wrong");
        Coupon c = new Coupon(dto.name, dto.size);
        c.id = UIDGenerator.generate();
        couponRepository.add(c);
        couponRepository.saveAll();
        return c.id;
    }

    public void deleteCoupon(String coupon_id) throws Coupon.DeleteCouponException {
        if(coupon_id == null) throw new Coupon.DeleteCouponException("Something went wrong");
        Coupon c = couponRepository.getById(coupon_id);
        if(c != null){
            couponRepository.delete(c);
            couponRepository.saveAll();
        }else{
            throw new Coupon.DeleteCouponException("Coupon not found");
        }
    }
    
    public Coupon get(String id) {
    	return couponRepository.getById(id);
    }
    
    public boolean isValid(String coupon) {
    	Coupon c = get(coupon);
    	return c != null;
    }
}
