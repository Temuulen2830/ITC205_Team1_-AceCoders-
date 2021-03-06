package library.entities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
public class Library implements Serializable {
	
	private static final String LIBRARY_FILE = "library.obj"; 	//Changed "lIbRaRyFiLe" to LIBRARY_FILE
	private static final int LOAN_LIMIT = 2; 			//Changed "lOaNlImIt" to LOAN_LIMIT
	private static final int LOAN_PERIOD = 2; 			//Changed "loanPeriod" to LOAN_PERIOD
	private static final double FINE_PER_DAY = 1.0; 		//Changed "FiNe_PeR_DaY" to FINE_PER_DAY
	private static final double MAX_FINES_OWED = 1.0; 		//Changed "maxFinesOwed" to MAX_FINES_OWED
	private static final double DAMAGE_FEE = 2.0; 			//Changed "damageFee" to DAMAGE_FEE
	
	private static Library self; 	//Changed "SeLf" to self
	private int bookId; 		//Changed "bOok_Id" to bookId
	private int memberId; 		//Changed "mEmBeR_Id" to memberId
	private int loanId; 		//Changed "lOaN_Id" to loanId
	private Date loanDate;		//Changed "lOaN_DaTe" to loanDate
	
	private Map<Integer, Book> catalog;		//Changed "CaTaLoG" to catalog
	private Map<Integer, Member> members;		//Changed "MeMbErS" to members
	private Map<Integer, Loan> loans;		//Changed "LoAnS" to loans
	private Map<Integer, Loan> currentLoans;	//Changed "CuRrEnT_LoAnS" to currentLoans
	private Map<Integer, Book> damagedBooks;	//Changed "DaMaGeD_BoOkS" to damagedBooks
	

	private Library() {
		catalog = new HashMap<>();		//Changed "CaTaLoG" to catalog
		members = new HashMap<>();		//Changed "MeMbErS" to members
		loans = new HashMap<>();		//Changed "LoAnS" to loans
		currentLoans = new HashMap<>();		//Changed "CuRrEnT_LoAnS" to currentLoans
		damagedBooks = new HashMap<>();		//Changed "DaMaGeD_BoOkS" to damagedBooks
		bookId = 1;				//Changed "bOoK_Id" to bookId
		memberId = 1;				//Changed "mEmBeR_Id" to memberId
		loanId = 1;				//Changed "lOaN_Id" to loanId
	}

	
	public static synchronized Library getInstance() {					//Changed "GeTiNsTaNcE" to getInstance	
		if (self == null) {								//Changed "SeLf" to self
			Path path = Paths.get(libraryFile);					//Changed "PATH" to path and "lIbRaRyFiLe" to libraryFile in 54 and 56
			if (Files.exists(path)) {						//Changed "PATH" to path
				try (ObjectInputStream libraryFile = new ObjectInputStream(new FileInputStream(libraryFile));) {
			    									//Changed "LiBrArY_FiLe" to libraryFile in 56 and 58
					self = (Library) libraryFile.readObject();		//Changed "SeLf" to self
					Calendar.getInstance().setDate(self.loanDate);		//Changed "gEtInStAnCe().SeT_DaTe(SeLf.lOaN_DaTe)" to getInstance().setDate(self.loanDate)
					libraryFile.close();					//Changed "LiBrArY_FiLe" to libraryFile
				}
				catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
			else self = new Library();						//Changed "SeLf" to self
		}
		return self;									//Changed "SeLf" to self
	}

	
	public static synchronized void save() {					//Changed "SaVe" to save
		if (self != null) {							//Changed "SeLf" to self
			self.loanDate = Calendar.getInstance().getDate();		//Changed "SeLf.lOaN_DaTe" to self.loanDate and "gEtInStAnCe().gEt_DaTe" to getInstance().getDate
			try (ObjectOutputStream libraryFile = new ObjectOutputStream(new FileOutputStream(libraryFile));) {	//Changed "LiBrArY_fIlE" and "lIbRaRyFiLe" to libraryFile
				libraryFile.writeObject(self);				//Changed "SeLf" to self
				libraryFile.flush();					//Changed "LiBrArY_fIlE" to libraryFile
				libraryFile.close();					//Changed "LiBrArY_fIlE" to libraryFile
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	
	public int getBookId() {			//Changed "gEt_BoOkId" to getBookId
		return bookId;				//Changed "bOoK_Id" to bookId
	}
	
	
	public int getMemberId() {			//Changed "gEt_MeMbEr_Id" to getMemberId
		return memberId;			//Changed "mEmBer_Id" to memberId
	}
	
	
	private int getNextBookId() {			//Changed "gEt_NeXt_BoOk_Id" to getNextBookId
		return bookId++;			//Changed "bOoK_Id" to bookId
	}

	
	private int getNextMemberId() {			//Changed "gEt_NeXt_MeMbEr_Id" to getNextMemberId
		return memberId++;			//Changed "mEmBeR_Id" to memberId
	}

	
	private int getNextLoanId() {			//Changed "gEt_NeXt_LoAn_Id" to getNextLoanId
		return loanId++;			//Changed "lOaN_Id" to loanId
	}

	
	public List<Member> listMembers() {				//Changed "lIsT_MeMbErS" to listMembers
		return new ArrayList<Member>(members.values()); 	//Changed "MeMbErS" to members
	}


	public List<Book> listBooks() {					//Changed "lIsT_BoOkS" to listBooks
		return new ArrayList<Book>(catalog.values()); 		//Changed "CaTaLoG" to catalog
	}


	public List<Loan> listCurrentLoans() {				//Changed "lISt_CuRrEnT_LoAnS" to listCurrentLoans
		return new ArrayList<Loan>(currentLoans.values());	//Changed "CuRrEnT_LoAnS" to currentLoans
	}


	public Member addMember(String lastName, String firstName, String email, int phoneNo) {		//Changed "aDd_MeMbEr" to addMember
		Member member = new Member(lastName, firstName, email, phoneNo, getNextMemberId());	//Changed "gEt_NeXt_MeMbEr_Id" to getNextMemberId
		members.put(member.getId(), member);							//Changed "MeMbErS" to members and "GeT_ID" to getId
		return member;
	}

	
	public Book addBook(String a, String t, String c) {		//Changed "aDd_BoOk" to addBook
		Book b = new Book(a, t, c, getNextBookId());		//Changed "gEt_NeXt_BoOk_Id" to getNextBookId
		catalog.put(b.getId(), b);				//Changed "CaTaLoG" to catalog and "gEtId" to getId
		return b;
	}

	
	public Member getMember(int memberId) {				//Changed "gEt_MeMbEr" to getMember
		if (members.containsKey(memberId)) 			//Changed "MeMbErS" to members
			return members.get(memberId);			//Changed "MeMbErS" to members
		return null;
	}

	
	public Book getBook(int bookId) {				//Changed "gEt_BoOk" to getBook
		if (catalog.containsKey(bookId)) 			//Changed "CaTaLoG" to catalog
			return catalog.get(bookId);			//Changed "CaTaLoG" to catalog
		return null;
	}

	
	public int getLoanLimit() {					//Changed "gEt_LoAn_LiMiT" to getLoanLimit
		return loanLimit;					//Changed "lOaNlImIt" to loanLimit
	}

	
	public boolean canMemberBorrow(Member member) {				//Changed "cAn_MeMbEr_BoRrOw" to canMemberBorrow
		if (member.getNumberOfCurrentLoans() == loanLimit ) 		//Changed "gEt_nUmBeR_Of_CuRrEnT_LoAnS" to getNumberOfCurrentLoans
			return false;						//Changed "lOaNlImIt" to loanLimit
				
		if (member.finesOwed() >= maxFinesOwed) 			//Changed "FiNeS_OwEd" to finesOwed
			return false;
				
		for (Loan loan : member.getLoans()) 				//Changed "GeT_LoAnS" to getLoans
			if (loan.isOverDue()) 					//Changed "Is_OvEr_DuE" to isOverDue
				return false;
			
		return true;
	}

	
	public int getNumberOfLoansRemainingForMember(Member member) {	//Changed "gEt_NuMbEr_Of_LoAnS_ReMaInInG_FoR_MeMbEr" to getNumberOfLoansRemainingForMember and "MeMbEr" to member
		return loanLimit - member.getNumberOfCurrentLoans();	//Changed "lOaNlImIt" to loanLimit and "gEt_nUmBeR_Of_CuRrEnT_LoAnS" to getNumberOfCurrentLoans
	}

	
	public Loan issueLoan(Book book, Member member) {				//Changed "iSsUe_LoAn" to issueLoan
		Date dueDate = Calendar.getInstance().getDueDate(loanPeriod);		//Changed "gEtInStAnCe" to getInstance and "gEt_DuE_DaTe" to getDueDate
		Loan loan = new Loan(getNextLoanId(), book, member, dueDate);		//Changed "gEt_NeXt_LoAn_Id" to getNextLoanId
		member.takeOutLoan(loan);						//Changed "TaKe_OuT_LoAn" to takeOutLoan
		book.borrow();								//Changed "BoRrOw" to borrow
		loans.put(loan.getId(), loan);						//Changed "LoAnS" to loans and "GeT_Id" to getId
		currentLoans.put(book.getId(), loan);					//Changed "CuRrEnT_LoAnS" to currentLoans and "gEtId" getId
		return loan;
	}
	
	
	public Loan getLoanByBookId(int bookId) {			//Changed "GeT_LoAn_By_BoOkId" to getLoanByBookId
		if (currentLoans.containsKey(bookId)) 			//Changed "CuRrEnT_LoAnS" to currentLoans
			return currentLoans.get(bookId);		//Changed "CuRrEnT_LoAnS" to currentLoans
		
		return null;
	}

	
	public double calculateOverDueFine(Loan loan) {			//Changed "CaLcUlAtE_OvEr_DuE_FiNe" to calculateOverDueFine and "LoAn" to loan
		if (loan.isOverDue()) {					//Changed "LoAn.Is_OvEr_DuE" to loan.isOverDue and in line 201 "DaYs_OvEr_DuE" to daysOverDue and "gEtInStAnCe" to getInstance
			long daysOverDue = Calendar.getInstance().getDaysDifference(loan.getDueDate());	//Changed "GeT_DaYs_DiFfErEnCe" to getDaysDifference and "LoAn.GeT_DuE_DaTe" to loan.getDueDate
			double fine = daysOverDue * finePerDay;		//Changed "fInE" to fine and "DaYs_OvEr_DuE" to daysOverDue and "FiNe_PeR_DaY" to finePerDay
			return fine;					//Changed "fInE" to fine
		}
		return 0.0;		
	}


	public void dischargeLoan(Loan currentLoan, boolean isDamaged) {//Changed "DiScHaRgE_LoAn" to dischargeLoan, "cUrReNt_LoAn" to currentLoan and "iS_dAmAgEd" to isDamaged
		Member member = currentLoan.getMember();		//Changed "mEmBeR" to member and "cUrReNt_LoAn.GeT_MeMbEr" to currentLoan.getMember
		Book book  = currentLoan.getBook();			//Changed "bOoK" to book and "cUrReNt_LoAn.GeT_BoOk" to currentLoan.getBook
		
		double overDueFine = calculateOverDueFine(currentLoan);	//Changed "oVeR_DuE_FiNe" to overDueFine, "CaLcUlAtE_OvEr_DuE_FiNe(cUrReNt_LoAn)" to calculateOverDueFine(currentLoan)
		member.addFine(overDueFine);				//Changed "mEmBeR.AdD_FiNe(oVeR_DuE_FiNe)" to member.addFine(overDueFine)
		
		member.dischargeLoan(currentLoan);			//Changed "mEmBeR.dIsChArGeLoAn(cUrReNt_LoAn)" to member.dischargeLoan(currentLoan)
		book.return(isDamaged);					//Changed "bOoK.ReTuRn(iS_dAmAgEd)" to book.return(isDamaged)
		if (isDamaged) {					//Changed "iS_dAmAgEd" to isDamaged
			member.addFine(damageFee);			//Changed "mEmBeR.AdD_FiNe" to member.addFine
			damagedBooks.put(book.getId(), book);		//Changed "DaMaGeD_BoOkS.put(bOoK.gEtId(), bOoK)" to damagedBooks.put(book.getId(), book)
		}
		currentLoan.discharge();				//Changed "cUrReNt_LoAn.DiScHaRgE" to currentLoan.discharge
		currentLoans.remove(book.getId());			//Changed "CuRrEnT_LoAnS" to currentLoans and "bOoK.gEtId" to book.getId
	}


	public void checkCurrentLoans() {				//Changed "cHeCk_CuRrEnT_LoAnS" to checkCurrentLoans
		for (Loan loan : currentLoans.values()) 		//Changed "lOaN" to loan and "CuRrEnT_LoAnS" to currentLoans
			loan.checkOverDue();				//Changed "lOaN.cHeCk_OvEr_DuE" to loan.checkOverDue
				
	}


	public void repairBook(Book currentBook) {			//Changed "RePaIr_BoOk" to repairBook and "cUrReNt_BoOk" to currentBook
		if (damagedBooks.containsKey(currentBook.getId())) {	//Changed "DaMaGeD_BoOkS" to damagedBooks and "cUrReNt_BoOk.gEtId" to currentBook.getId
			currentBook.repair();				//Changed "cUrReNt_BoOk.RePaIr" to currentBook.repair
			damagedBooks.remove(currentBook.getId());	//Changed "DaMaGeD_BoOkS" to damagedBooks and "cUrReNt_BoOk.gEtId" to currentBook.getId
		}
		else 
			throw new RuntimeException("Library: repairBook: book is not damaged");
		
		
	}
	
	
}
