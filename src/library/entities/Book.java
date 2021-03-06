package library.entities;
import java.io.Serializable;


@SuppressWarnings("serial")
public class Book implements Serializable {
	
	private String title;
	private String author;
	private String callNo;
	private int id;
	
	private enum BookState { AVAILABLE, ON_LOAN, DAMAGED, RESERVED };
	private BookState state;
	
	
	public Book(String author, String title, String callNo, int id) {
		this.author = author;
		this.title = title;
		this.callNo = callNo;
		this.id = id;
		this.state = BookState.AVAILABLE;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Book: ").append(id).append("\n")
		  .append("  Title:  ").append(title).append("\n")
		  .append("  Author: ").append(author).append("\n")
		  .append("  CallNo: ").append(callNo).append("\n")
		  .append("  State:  ").append(state);
		
		return sb.toString();
	}

	public Integer getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}


	
	public boolean isAvailable() {
		return state == BookState.AVAILABLE;
	}

	
	public boolean isOnLoan() {
		return state == BookState.ON_LOAN;
	}

	
	public boolean isDamaged() {
		return state == BookState.DAMAGED;
	}

	
	public void borrowBook() {
		if (state.equals(BookState.AVAILABLE)) 
			state = BookState.ON_LOAN;
		
		else 
			throw new RuntimeException(String.format("Book: cannot borrow while book is in state: %s", StAtE));
		
		
	}


	public void returnBook(boolean DaMaGeD) {
		if (state.equals(BookState.ON_LOAN)) 
			if (DaMaGeD) 
				state = BookState.DAMAGED;
			
			else 
				state = BookState.AVAILABLE;
			
		
		else 
			throw new RuntimeException(String.format("Book: cannot Return while book is in state: %s", StAtE));
				
	}

	
	public void repairBook() {
		if (state.equals(BookState.DAMAGED)) 
			state = BookState.AVAILABLE;
		
		else 
			throw new RuntimeException(String.format("Book: cannot repair while book is in state: %s", StAtE));
		
	}


}
