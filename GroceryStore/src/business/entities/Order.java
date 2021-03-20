package business.entities;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

import business.facade.Result;

/**
 * Order class represents a reorder of a single product in the GroceryStore Co-Op.
 * 
 * @author Abdi Ahmed, Asha Hassan, Elise Kurtz, Faisal Saeed
 *
 */

public class Order implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final String ORDER_STRING = "O";
    private static int idCounter;
    
    private String   id;
    private String   productName;
    private String   productID;
    private Calendar orderDate;
    private int 	 orderQuantity;
    private int  status;
    
    
    
    
    
    
	/**
	 * @param productName
	 * @param productID
	 * @param orderQuantity
	 * 
	 * create a unique id for the orderid
	 * Setting the orderDate to today and isFulliled to false
	 */
	public Order(String productName, String productID, int orderQuantity) {
		super();
		this.productName = productName;
		this.productID = productID;
		this.orderQuantity = orderQuantity;
		
		this.id = ORDER_STRING + ++idCounter;
		this.orderDate = new GregorianCalendar();
		this.status = Result.ORDER_IS_NOT_FULFILLED;
	}
	
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @return the productName
	 */
	public String getProductName() {
		return productName;
	}
	/**
	 * @return the productID
	 */
	public String getProductID() {
		return productID;
	}
	/**
	 * @return the orderDate
	 */
	public String getOrderDate() {
		int month = orderDate.get(Calendar.MONTH)+1;
		return orderDate.get(Calendar.DAY_OF_MONTH)
				+"/"+month
				+"/"+orderDate.get(Calendar.YEAR);
	}
	/**
	 * @return the orderQuantity
	 */
	public int getOrderQuantity() {
		return orderQuantity;
	}
	/**
	 * @return the isFulfilled
	 */
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status=status;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	
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
		Order other = (Order) obj;
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
    
    @Override
    public String toString() {
    	return productID + " " + productName +" " + orderQuantity+" "+ id;
    }

}
