package business.entities;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Product represents a single product of the of the GroceryStore Co-Op.
 * 
 * @author Abdi Ahmed, Asha Hassan, Elise Kurtz, Faisal Saeed
 *
 */
public class Product implements Serializable {
    private static final long serialVersionUID = 1L;


    private static int idCounter;

    private String name;
    private String id;
    private int stockInHand;
    private double price;
    private int reOrderLevel;
	
    
	/**
	 * Creates a single product
	 * Sets the id number using the static idCounter.
	 * 
	 * @param name
	 * @param stockInHand
	 * @param price
	 * @param reOrderLevel
	 */
	public Product(String name, String id, double price, int stockInHand, int reOrderLevel) {
		
		this.name = name;
		this.id = id;
		this.stockInHand = stockInHand;
		this.price = price;
		this.reOrderLevel = reOrderLevel;
	}
	
	public boolean checkNameMatch(String string) {
		return this.name.matches("(.*)"+string+"(.*)");
	}
	
	/**
	 * @param sets the id 
	 */
	public void setId(String id) {
		this.id = id;
	}	
	
	
	/**
	 * @param sets the price 
	 */
	public boolean setPrice(double price) {
		this.price = price;
		return this.price == price;
	}	
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @return the stockInHand
	 */
	public int getStockInHand() {
		return stockInHand;
	}
	/**
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}
	/**
	 * @return the reOrderLevel
	 */
	public int getReOrderLevel() {
		return reOrderLevel;
	}
	
	

	public void setStockInHand(int stockInHand) {
		this.stockInHand = stockInHand;
	}


	@Override
	public String toString() {
		return name + " " + price + " " + stockInHand + " " + reOrderLevel;
	}
    
    public static void save(ObjectOutputStream output) throws IOException {
        output.writeObject(idCounter);
    }

    public static void retrieve(ObjectInputStream input) throws IOException, ClassNotFoundException {
        idCounter = (int) input.readObject();
    }
    

}
