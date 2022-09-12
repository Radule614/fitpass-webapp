package repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Coupon;
import repository.fileHandler.FileHandler;
import repository.generic.GenericRepository;

import java.io.File;
import java.util.ArrayList;

public class CouponRepository extends GenericRepository<Coupon> {
	private static CouponRepository instance;

	public static CouponRepository getInstance(){
		if(instance == null){
			instance = new CouponRepository();
		}
		return instance;

	}

	private CouponRepository() {
		createFileHandlerAndReadData();
	}

	public Coupon getById(String id){
		for (Coupon coupon: getAll())
			if(coupon.id.equals(id))
				return coupon;
		return null;
	}

	@Override
	protected void createFileHandlerAndReadData() {
		TypeToken<ArrayList<Coupon>> typeToken = new TypeToken<ArrayList<Coupon>>() {};
		this.fileHandler = new FileHandler<>(
				System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator
						+ "java" + File.separator + "data" + File.separator + "coupon.json", typeToken, new Gson());
		this.data = fileHandler.readAll();
	}


}
