package repository;

import model.facility.Facility;
import model.facility.FacilityType;
import model.facility.Location;
import model.facility.WorkingHours;
import repository.generic.GenericRepository;

public class FacilityRepository extends GenericRepository<Facility> {
    private static FacilityRepository instance;
    public static FacilityRepository getInstance(){
        if(instance == null){
            instance = new FacilityRepository();
        }
        return instance;
    }

    private FacilityRepository() {
        Facility f = new Facility();
        f.name = "teretana";
        f.facilityType = FacilityType.GYM;
        f.available = true;
        f.location = new Location();
        f.logoUrl = "img/slika_0.jpg";
        f.workingHours = new WorkingHours(7, 0, 22, 0);
        f.content = "123";
        data.add(f);

        f = new Facility(f);
        f.name = "bazen";
        f.facilityType = FacilityType.POOL;
        f.workingHours = new WorkingHours(10, 0, 20, 0);
        data.add(f);

        for (int i = 0; i < 10; i++){
            data.add(new Facility(f));
        }

        f = new Facility(f);
        f.name = "djim";
        f.facilityType = FacilityType.GYM;
        data.add(f);
    }
}
