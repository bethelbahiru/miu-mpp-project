package business;

import java.util.List;

import business.Book;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

public interface ControllerInterface {
	public void login(String id, String password) throws CustomException;
	public List<String> allMemberIds();
	public List<String> allBookIds();
	public void addMember(String id, String firstName, String lastName,String tele, Address add) throws CustomException;
	
	public List<LibraryMember> getAllMembers();
	
	public void updateMember(LibraryMember lm);
	
	public void removeMember(LibraryMember lm);
	
}
