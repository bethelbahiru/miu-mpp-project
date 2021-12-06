package business;

import java.util.ArrayList;
import java.util.List;

import business.Book;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

public interface ControllerInterface {
	public void login(String id, String password) throws CustomException;
	public List<String> allMemberIds();
	public List<String> allBookIds();
	public void addMember(String id, String firstName, String lastName,String tele, Address add) throws CustomException;
	public void addBook(String isbn, String title, int maxCheckOutDays, List<Author> authors) throws CustomException;
	public void deleteBooks() throws CustomException;
	public void checkoutBook(List<String> isbn, String memberId);
	
	public List<LibraryMember> getAllMembers();
	public void updateMember(LibraryMember lm) throws CustomException;
	public void removeMember(LibraryMember lm);
	public List<Book> getAllBooks();
	public Book getBookById(String id);
	LibraryMember getMemberById(String id);
	public void addBookCopy(String isbn, int copyNum) throws CustomException;
	public ArrayList<OverDueLayer> getOverDue(String isbn);

}
