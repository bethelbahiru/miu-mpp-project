package business;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

final public class LibraryMember extends Person implements Serializable {
	
	private String memberId;
	private List<CheckoutRecord> checkouts;
	
	public List<CheckoutRecord> getCheckouts() {
		return checkouts;
	}
	
	public int getCheckoutRecords() {
		return checkouts.size();
	}

	public void setCheckouts(List<CheckoutRecord> checkouts) {
		this.checkouts = checkouts;
	}


	public LibraryMember(String memberId, String fname, String lname, String tel,Address add) {
		super(fname,lname, tel, add);
		this.memberId = memberId;
		checkouts = new ArrayList<CheckoutRecord>();
	}
	
	
	public String getMemberId() {
		return memberId;
	}

	public void checkOutFormated() {
		System.out.println("ISBN\t\t" + "Title\t\t\t" + "Checkedout Date\t\t" + "Due Date\t\t" + "Copy Number\n" );
		for(CheckoutRecord c : checkouts) {
			System.out.println(c);
		}
	}
	
	@Override
	public String toString() {
		return "Member Info: " + "ID: " + getMemberId() + ", name: " + getFirstName() + " " + getLastName() + 
				", " + getTelephone() + ", " + getAddress();
	}

	private static final long serialVersionUID = -2226197306790714013L;
}
