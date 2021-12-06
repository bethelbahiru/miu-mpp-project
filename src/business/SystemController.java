package business;

import java.time.LocalDate;
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
	
	
	@Override
	public List<Book> getAllBooks() {
		DataAccess da = new DataAccessFacade();
		HashMap<String, Book> membersHashMap = da.readBooksMap();
		List<Book> allBooks = new ArrayList<Book>(membersHashMap.values());
		return allBooks;
	}
	
	@Override
	public void updateMember(LibraryMember lm) throws CustomException{
		DataAccess da = new DataAccessFacade();
		
		if(lm.getMemberId().equals("") || 
		   lm.getFirstName().equals("") || 
		   lm.getLastName().equals("") || 
		   lm.getTelephone().equals("") || 
		   lm.getAddress().getState().equals("") ||
		   lm.getAddress().getStreet().equals("") || 
		   lm.getAddress().getCity().equals("") ||
		   lm.getAddress().getZip().equals("")) {
			
			throw new CustomException("Some fields are empty.");
		}
		
		da.updateMember(lm);
	}
	
	@Override
	public void removeMember(LibraryMember lm) {
		DataAccess da = new DataAccessFacade();
		da.removeMember(lm);
	}
	
	@Override
	public void addBook(String isbn, String title, int maxCheckOutDays, List<Author> authors) throws CustomException{
		// TODO Auto-generated method stub
		DataAccess da = new DataAccessFacade();
		Book b = new Book(isbn,title,maxCheckOutDays,authors);
		if (isbn.equals("") || title.equals("")) {
			throw new CustomException("One or more fields are empty.");
		}
		
		List<String> bookIds = allBookIds();
		
		if (bookIds.contains(isbn)) {
			throw new CustomException("Book already exists. Please use new ISBN.");
		}
		
		da.saveNewBook(b);
	}
	
	@Override
	public void deleteBooks() {
		DataAccess da = new DataAccessFacade();
		da.removeBooks();
	}
	@Override
	public void checkoutBook(List<String> isbns, String memberId) {
		DataAccess da = new DataAccessFacade();
		HashMap<String,LibraryMember> libMembers = da.readMemberMap();
		HashMap<String,Book> books = da.readBooksMap();
		LibraryMember lb = libMembers.get(memberId);
		
		for(String isbn: isbns) {
			// book = books.get(isbn); // getbook by isbn my edit
			Book book = getBookById(isbn);
			
			BookCopy currentCopy =  book.getNextAvailableCopy();
			currentCopy.checkoutCopy();
			if(lb.getMemberId().equalsIgnoreCase("1001")) {
				lb.getCheckouts().add(makeOverdue(lb, currentCopy));	
			}
			else {
				lb.getCheckouts().add(new CheckoutRecord(LocalDate.now().plusDays(currentCopy.getBook().getMaxCheckoutLength()),currentCopy));

			}
			da.saveNewBook(book);
			da.saveNewMember(lb);
			
		}	
	}
	
	/*Make a few books overdue for user 1001*/
	public CheckoutRecord makeOverdue(LibraryMember lb, BookCopy copy) {
		return new CheckoutRecord(LocalDate.now().minusDays(50), copy);
	}
	@Override
	public Book getBookById(String id) {
		// TODO Auto-generated method stub
		DataAccess da = new DataAccessFacade();
		HashMap<String,Book> books = da.readBooksMap();
		Book book = books.get(id);
		return book;
	}
	
	@Override
	public LibraryMember getMemberById(String id) {
		// TODO Auto-generated method stub
		DataAccess da = new DataAccessFacade();
		HashMap<String,LibraryMember> lbs = da.readMemberMap();
		LibraryMember lb = lbs.get(id);
		return lb;
	}
	@Override
	public void addBookCopy(String isbn, int copyNum) throws CustomException {
		// TODO Auto-generated method stub
		DataAccess da = new DataAccessFacade();
		
		if (isbn.equals("") || copyNum == 0) {
			throw new CustomException("Incorrect value of ISBN or Book copy.");
		}
		
		Book b = getBookById(isbn);
		b.addCopy(copyNum);	
		da.saveNewBook(b);
	}
	
	@Override
	public ArrayList<OverDueLayer> getOverDue(String isbn) {
		// TODO Auto-generated method stub
		DataAccess da = new DataAccessFacade();
		Book b = getBookById(isbn);
		List<LibraryMember> lbs = getAllMembers();
		ArrayList<OverDueLayer> overduesData = new ArrayList<OverDueLayer>();
		
		for(BookCopy c : b.getCopies()) {
			if(c.isAvailable()) {
				overduesData.add(OverDueLayer.bookIsAvailable(b.getIsbn(), b.getTitle(), c.getCopyNum()));
			}
			else {
				String memberId = "";
				LocalDate dueDate = null;
				for(LibraryMember member : lbs) {
					if (member.getCheckouts() != null) {
						if(getMemberIdFromCopies(c, member)) {
							memberId = member.getMemberId();
							dueDate = findDueDate(c, member);
							break;
						}
					}
				}
				overduesData.add(OverDueLayer.bookNotAvailable(b.getIsbn(), b.getTitle(), c.getCopyNum(), dueDate, memberId));
			}
		}
		
		return overduesData;
	}
	
	public boolean getMemberIdFromCopies(BookCopy c, LibraryMember mem) {
		boolean found =  false;
		for(CheckoutRecord cr : mem.getCheckouts()) {
			if(c.equals(cr.getBookCopy())) {
				found = true;
				
				break;
			}
		}
		return found;
	}

	public LocalDate findDueDate(BookCopy c, LibraryMember mem) {
		LocalDate found =  null;
		for(CheckoutRecord cr : mem.getCheckouts()) {
			if(c.equals(cr.getBookCopy())) {
				found = cr.getDueDate();
				
				break;
			}
		}
		return found;
	}

	
	
}
