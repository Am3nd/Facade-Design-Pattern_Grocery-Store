package business.entities;
/**
 * Member represents a member of the GroceryStore Co-Op.
 * 
 * @author Abdi Ahmed, Asha Hassan, Elise Kurtz, Faisal Saeed
 *
 */
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import business.entities.iterators.FilteredIterator;

public class Member implements Serializable {
    private static final long serialVersionUID = 1L;
    private static int idCounter;
    private static final String MEMBER_STRING = "M";

    private String   name;
    private String   address;
    private String   phone;
	private Calendar dateJoined;
    private double 	 feePaid;
    private String 	 id;
    
    private List<Transaction> transactions = new LinkedList<Transaction>();

	/**
	 * @param name
	 * @param address
	 * @param phone
	 * @param feePaid
	 * @param id
	 */
	public Member(String name, String address, String phone, double feePaid) {
		
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.feePaid = feePaid;
		this.id = MEMBER_STRING+ ++idCounter;
		this.dateJoined = new GregorianCalendar();
	}

    /**
     * Gets an iterator to a collection of selected transactions
     * 
     * @param startDate The starting date for which the transactions have to be retrieved
     * @param endDate The ending date for which the transactions have to be retrieved
     * @return the iterator to the collection
     */
    public Iterator<Transaction> getTransactionsOnDateRange(Calendar startDate, Calendar endDate) {
 
    	return new FilteredIterator(transactions.iterator(), transaction -> transaction.onDateRange(startDate, endDate));
    } 


    /**
     * Returns the list of all transactions for this member.
     * 
     * @return the iterator to the list of Transaction objects
     */
    public Iterator<Transaction> getTransactions() {
        return transactions.iterator();
    }
    
    
    
    /**
     * @param adds each transaction
     * @return true if transaction is add to transaction list
     */
    public boolean addTransaction(Transaction transaction) {
    	
    	return transactions.add(transaction);
    }
    
    
	public boolean checkNameMatch(String string) {
		return this.name.matches("(.*)"+string+"(.*)");
	}
	
	/**
	 * @return returns the name of the customer
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @return the dateJoined
	 */
	public String getDateJoined() {
		int month = dateJoined.get(Calendar.MONTH)+1;
		return dateJoined.get(Calendar.DAY_OF_MONTH)
				+"/"+ month
				+"/"+dateJoined.get(Calendar.YEAR);
	}

	/**
	 * @return the feePaid
	 */
	public double getFeePaid() {
		return feePaid;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}



	/**
	 * @param feePaid the feePaid to set
	 */
	public void setFeePaid(double feePaid) {
		this.feePaid = feePaid;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

    /**
     * Checks whether the member is equal to the one supplied
     * 
     * @param object the member who should be compared
     * @return true if the member id's match
     */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Member other = (Member) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}


	
    public static void save(ObjectOutputStream output) throws IOException {
        output.writeObject(idCounter);
    }

    public static void retrieve(ObjectInputStream input) throws IOException, ClassNotFoundException {
        idCounter = (int) input.readObject();
    }
    
    
    
    

}
