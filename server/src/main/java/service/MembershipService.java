package service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import model.customer.Customer;
import model.customer.Membership;
import repository.MembershipRepository;

public class MembershipService {
	private static final MembershipRepository membershipRepo = MembershipRepository.getInstance();
	
	public MembershipService() {

	}
	
	public ArrayList<Membership> getAll() {
		return membershipRepo.getAll();
	}
	
	public Membership getById(String id) {
		for(Membership m : membershipRepo.getAll()) {
			if(m.id.equals(id)) return m;
		}
		
		return null;
	}
	
	public Membership getByCustomer(String username) {
		for(Membership m : membershipRepo.getAll()) {
			if(m.customerUsername.equals(username)) return m;
		}
		
		return null;
	}
	
	public boolean remove(String id) {
		return membershipRepo.delete(getById(id));
	}
	
	public void save(Membership m) {
		membershipRepo.add(m);
	}
	
	public boolean delete(Membership m) {
		return membershipRepo.delete(m);
	}
	
	public boolean deactivate(String id) {
		Membership membership = getById(id);
		if(membership != null) {
			return membershipRepo.deactivate(membership);
		}
		return false;
	}
	
	public void deactivateIfExpired(String username) {
		Membership m = getByCustomer(username);
		if(m != null && m.expirationDate.isBefore(LocalDate.now())) {
			membershipRepo.deactivate(m);
		}
	}
	
	public boolean canCheckIn(String username) {
		Membership membership = getByCustomer(username);
		if(membership == null) return false;
		if(membership.appointmentNumber == 0) return false;
		
		return true;
	}
	
	public void handleAppointmentUsage(String username) {
		Membership membership = getByCustomer(username);
		membership.appointmentNumber--;
		membership.usedAppointments++;
		membershipRepo.saveAll();
	}
	
	public void saveAll() {
		membershipRepo.saveAll();
	}
	
}
