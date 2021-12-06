package business;

import java.time.LocalDate;

public class OverDueLayer{

	private String isbn;
	private String title;
	private String copyNumber;
	private String dueDate;
	private String isOverDue;
	private String isAvailable;
	private String memberId;
	
	//private constructor
	private OverDueLayer(String isbn, String title, int copyNumber, LocalDate dueDate, boolean isOverDue, boolean isAvailable, String memberId){
	    this.isbn = isbn;
	    this.title = title;
	    this.copyNumber = ""+ copyNumber;
	    this.dueDate = dueDate != null ? dueDate.toString() : "NULL";
	    this.isOverDue = "" + isOverDue;
	    this.isAvailable = ""+isAvailable;
	    this.memberId = memberId;
	}
	
	// if a book is available
	public static OverDueLayer bookIsAvailable(String isbn, String title, int copyNumber) {
		return new OverDueLayer(isbn, title, copyNumber, null, false, true, "NaN");
	} 
	
	//if a book is not available
	public static OverDueLayer bookNotAvailable(String isbn, String title, int copyNumber, LocalDate dueDate, String memberId) {
			boolean dueStatus = dueDate.isAfter(LocalDate.now()) ? false : true;
		return new OverDueLayer(isbn, title, copyNumber, dueDate, dueStatus, false, memberId);
	}
	/*
	 * Getters and setters methods
	 * **/
	
	public String getIsbn() {
		return isbn;
	}

	public String getTitle() {
		return title;
	}

	public String getCopyNumber() {
		return copyNumber;
	}

	public String getDueDate() {
		return dueDate;
	}

	public String getIsOverDue() {
		return isOverDue;
	}

	public String getIsAvailable() {
		return isAvailable;
	}

	public String getMemberId() {
		return memberId;
	}
}