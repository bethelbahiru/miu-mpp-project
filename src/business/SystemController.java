package business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dataaccess.Auth;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.User;

public class SystemController implements ControllerInterface {
	public static Auth currentAuth = null;
	
	public void login(String id, String password) throws CustomException {
		DataAccess da = new DataAccessFacade();
		HashMap<String, User> map = da.readUserMap();
		if(id.equals("") && password.equals("")) {
			throw new CustomException("Id and Password Empty");
		}
		if(!map.containsKey(id)) {
			throw new CustomException("ID " + id + " not found");
		}
		String passwordFound = map.get(id).getPassword();
		if(!passwordFound.equals(password)) {
			throw new CustomException("Password incorrect");
		}
		currentAuth = map.get(id).getAuthorization();
		
	}
	@Override
	public List<String> allMemberIds() {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<>();
		retval.addAll(da.readMemberMap().keySet());
		return retval;
	}
	
	@Override
	public List<String> allBookIds() {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<>();
		retval.addAll(da.readBooksMap().keySet());
		return retval;
	}
	@Override
	public void addMember(String id, String firstName, String lastName, String tele, Address address) throws CustomException {
		// TODO Auto-generated method stub
		LibraryMember newMember = new LibraryMember(id, firstName, lastName, tele, address);
		List<String> memberIds = allMemberIds();
		
		if(id.equals("") || 
				firstName.equals("") || 
				lastName.equals("") || 
				tele.equals("") || 
				address.getState().equals("") ||
				address.getStreet().equals("") || 
				address.getCity().equals("") ||
				address.getZip().equals("")) {
			
			throw new CustomException("One or more fields are empty.");
		}
		
		if (memberIds.contains(id)) {
			throw new CustomException("Member already exists. Please use new ID");
		}
		
		DataAccess da = new DataAccessFacade();
		da.saveNewMember(newMember);
	}
	
	@Override
	public List<LibraryMember> getAllMembers() {
		DataAccess da = new DataAccessFacade();
		HashMap<String, LibraryMember> membersHashMap = da.readMemberMap();
		List<LibraryMember> allMembers = new ArrayList<LibraryMember>(membersHashMap.values());
		return allMembers;
	}
	
	//TODO :- Refactor.
	@Override
	public void updateMember(LibraryMember lm) {
		DataAccess da = new DataAccessFacade();
		da.saveNewMember(lm);
	}
	
	@Override
	public void removeMember(LibraryMember lm) {
		DataAccess da = new DataAccessFacade();
		da.removeMember(lm);
	}

	
	
}
