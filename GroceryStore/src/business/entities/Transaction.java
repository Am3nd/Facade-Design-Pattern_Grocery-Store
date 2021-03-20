package business.entities;
/**
 * Represents a single Transaction (productID, name, quantity,price )
 * 
 * @author Abdi Ahmed, Asha Hassan, Elise Kurtz, Faisal Saeed
 *
 */
import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class Transaction implements Serializable {
	
    private static final long serialVersionUID = 1L;
    private String productID;
    private String productName;
    private double    unitPrice;
    private int    quantity;
    private double    total;
    private Calendar date;
    
    
    /**
     * Creates the transaction with a product id, name, quantity and price. The date is the
     * 		current date.
	 * @param productID
	 * @param productName
	 * @param quantity the number of items of a specific product
	 * @param unitPrice
	 * @param date
	 */
	public Transaction(String productID, String productName, int quantity, double unitPrice) {
		
		this.productID = productID;
		this.productName = productName;
		this.quantity = quantity;
		this.unitPrice = unitPrice;
		this.total = unitPrice * quantity;
		
		this.date = new GregorianCalendar();;
	}

    public double getTotal() {
		return total;
	}



	/**
     * Checks whether this transaction is in the given date range
     * 
     * @param startDate The starting date for which the transactions have to be retrieved
     * @param endDate The ending date for which the transactions have to be retrieved
     * @return true if the dates match
     */
    public boolean onDateRange(Calendar startDate, Calendar endDate) {

    	return (date.getTimeInMillis()>= startDate.getTimeInMillis()&&
        		date.getTimeInMillis()<=endDate.getTimeInMillis()
        		);
    }
    
    /**
     * Returns the date of transaction as a String
     * 
     * @return date with month, date, and year
     */ 
    public String getDate() {
    	int month = date.get(Calendar.MONTH)+1;
        return date.get(Calendar.DAY_OF_MONTH) + "/" + 
        		month + "/" + 
        		date.get(Calendar.YEAR);
    }

    /**
     * returns a String in the form of the transaction
     *  with product name, quantity, price, and total price
     */
    @Override
    public String toString() {
        return (productName + " " + quantity + " "+ unitPrice + " " + total);
    }

	public String getProductID() {
		return productID;
	}

	public String getProductName() {
		return productName;
	}

	public double getUnitPrice() {
		return unitPrice;
	}

	public int getQuantity() {
		return quantity;
	}

    
	
}
