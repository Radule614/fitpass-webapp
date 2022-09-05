package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.AddCouponDTO;
import dto.DeleteCouponDTO;
import model.Coupon;
import repository.util.LocalDateAdapter;
import service.CouponService;
import service.FacilityService;
import spark.Request;
import spark.Response;
import utility.Utility;

import javax.servlet.MultipartConfigElement;

import java.time.LocalDate;

import static utility.Utility.parseStringInput;

public class CouponController {
    public static String getAllCoupons(Request request, Response response) {
        response.type("application/json");
        try{
            return new Gson().toJson(new CouponService().getAll());
        }catch (Exception e){
            e.printStackTrace();
            response.status(400);
            return Utility.convertMessageToJSON("Couldn't fetch coupons");
        }
    }
    public static String createCoupon(Request request, Response response){
        request.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));
        response.type("application/json");
        try{
            String name = parseStringInput(request.raw().getPart("name"));
            String size = parseStringInput(request.raw().getPart("size"));
            if(name != null && name.isEmpty()) throw new Coupon.CreateCouponException("Coupon name can't be empty");
            if(size == null) throw new Coupon.CreateCouponException("Size was null");
            String id = new CouponService().createCoupon(new AddCouponDTO(name, Double.parseDouble(size)));
            return "{\"id\": \"" + id + "\"}";
        } catch(Coupon.CreateCouponException e){
            e.printStackTrace();
            response.status(400);
            return Utility.convertMessageToJSON(e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            response.status(400);
            return Utility.convertMessageToJSON("Failed to create coupon");
        }
    }

    public static String deleteCoupon(Request request, Response response) {
        response.type("application/json");
        try{
            DeleteCouponDTO dto = new Gson().fromJson(request.body(), DeleteCouponDTO.class);
            new CouponService().deleteCoupon(dto.id);
            return Utility.convertMessageToJSON("Coupon deleted successfully");
        } catch(Coupon.DeleteCouponException e){
            e.printStackTrace();
            response.status(400);
            return Utility.convertMessageToJSON(e.getMessage());
        } catch(Exception e){
            e.printStackTrace();
            response.status(400);
            return Utility.convertMessageToJSON("Failed to delete coupon");
        }
    }
}
