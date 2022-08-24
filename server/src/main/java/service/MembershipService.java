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
	
}