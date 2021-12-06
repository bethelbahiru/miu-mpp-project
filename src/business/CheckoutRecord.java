package business;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**??
 *
 */
final public class CheckoutRecord implements Serializable {

	private static final long serialVersionUID = 6110690276685962829L;
	// private int checkOutID;
	private BookCopy bookCopy;
	private LocalDate dueDate;
	private LocalDate checkedOutDate;
	private double fine;
	private boolean isCheckedIn;

	CheckoutRecord(LocalDate dueDate, BookCopy bookCopy) {
		this.dueDate = dueDate;
		this.bookCopy = bookCopy;
		this.checkedOutDate = LocalDate.now();
		this.isCheckedIn = false;
	}

	public LocalDate getCheckedOutDate() {
		return checkedOutDate;
	}
	
	public BookCopy getBookCopy() {
		return bookCopy;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public double getFine() {
		return fine;
	}

	public boolean getCheckInStatus() {
		return isCheckedIn;
	}

//	void setFine() {
//		long lateDayInterval = ChronoUnit.DAYS.between(dueDate, checkedOutDate);
//		if (lateDayInterval <= 0) {
//			this.fine = 0;
//		} else {
//			this.fine = lateDayInterval * 2;
//		}
//
//	}

	public BookCopy getCheckOutBook() {
		return bookCopy;
	}

	public String toString() {
		//my Change
			return bookCopy.getBook().getIsbn() + "\t\t" + bookCopy.getBook().getTitle() + "\t\t\t"+ checkedOutDate +"\t\t" + dueDate+ "\t\t" + bookCopy.getCopyNum()   +"\n"; 


	}

}

