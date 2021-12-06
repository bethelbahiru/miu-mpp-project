package business;

public class BookCopyLayer {
	
	private String isbn;
	private String title;
	private int numOfCopies;
	
	public BookCopyLayer(String isbn, String title, int numOfCopies) {
		this.isbn = isbn;
		this.title = title;
		this.numOfCopies = numOfCopies;
	}
	
	public String getIsbn() {
		return isbn;
	}
	public String getTitle() {
		return title;
	}
	public int getNumOfCopies() {
		return numOfCopies;
	}

}
