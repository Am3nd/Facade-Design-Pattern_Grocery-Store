package ui;
/**
 * This class is the user interface for the GroceryStore Application
 * 
 * @author Abdi Ahmed, Asha Hassan, Elise Kurtz, Faisal Saeed
 *
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.StringTokenizer;


import business.facade.GroceryStore;
import business.facade.Request;
import business.facade.Result;



public class UserInterface {

    private static UserInterface userInterface;
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static GroceryStore groceryStore;
    private static final int EXIT					 = 0;
    private static final int ADD_MEMBER 	 		 = 1;
    private static final int REMOVE_MEMBER   		 = 2;
    private static final int ADD_PRODUCT 			 = 3;
    private static final int CHECKOUT 				 = 4;
    private static final int PROCESS_SHIPMENT 		 = 5;
    private static final int CHANGE_PRICE 			 = 6;
    private static final int GET_PRODUCT_INFO   	 = 7;
    private static final int GET_MEMBER_INFO     	 = 8;
    private static final int GET_TRANSACTIONS        = 9;
    private static final int GET_OUTSTANDING_ORDERS  = 10;
    private static final int GET_ALL_MEMBERS 		 = 11;
    private static final int GET_ALL_PRODUCTS 		 = 12;
    private static final int SAVE 					 = 13;
    private static final int HELP 					 = 14;
    
    /**
     * Made private for singleton pattern. Conditionally looks for any saved data.
     * Otherwise, it gets a singleton GroceryStore object.
     */
    private UserInterface() {
        if (yesOrNo("Look for saved data and  use it?")) {
            retrieve();
        } else {
            groceryStore = GroceryStore.instance();
        }

    }
    /**
     * Supports the singleton pattern
     * 
     * @return the singleton object
     */
    public static UserInterface instance() {
        if (userInterface == null) {
            return userInterface = new UserInterface();
        } else {
            return userInterface;
        }
    }
    
    
    
    public void addMember() {
    	Request.instance().setMemberName(getResponse("Enter Member Name"));
    	Request.instance().setMemberAddress(getResponse("Enter Address"));
    	Request.instance().setMemberPhone(getResponse("Enter Phone Number"));
    	Request.instance().setMemberFeePaid(getMoney("Enter Fee Paid"));
    	
    	Result result = groceryStore.addMember(Request.instance());
    	
    	if(result.getResultCode() != Result.OPERATION_COMPLETED) {
    		System.out.println("Could not add this member");
    	}
    	else {
    		
    		System.out.println(result.getMemberName() + "'s id is " + result.getMemberId()
    							+ ", Joined on "+result.getMemberDateJoined());
    	}
    	
    	
    }
    
    
    
    
    public void removeMember() {
    	Request.instance().setMemberId(getResponse("Enter Member ID"));
    	Result result = groceryStore.removeMember(Request.instance()); 
    	
    	switch (result.getResultCode()) {
		case Result.NO_SUCH_MEMBER: {
			System.out.println("No such member with the id: "
							+ Request.instance().getMemberId()+
							" is Registered with the GroceryStore Co-OP");
			break;
		}
		case Result.OPERATION_COMPLETED: {
			System.out.println(result.getMemberName() + ", ID:"+ 
    				result.getMemberId()+ ", Phone:"+
    				result.getMemberPhone()+", Has been removed");
			break;
			
		}
		case Result.OPERATION_FAILED: {
			System.out.println("Unable to remove member");
		}
		default:
			System.out.println("An error has occurred");
		}
    }
    
    public void addProduct() {
    	
    	Request.instance().setProductName(getResponse("Please enter product Name"));
    	Request.instance().setProductId(getResponse("Please enter product ID"));
    	Request.instance().setProductPrice(getMoney("Please enter product price"));
    	Request.instance().setProductStockInHand(getNumber("Please enter Stock in hand"));
    	Request.instance().setProductReOrderLevel(getNumber("Please enter Re-Order Level"));
    	
    	Result result = groceryStore.addProduct(Request.instance());
    	
    	if(  result.getResultCode() != Result.OPERATION_COMPLETED) {
    		System.out.println("Product could not be added");
    	}
    	else {
    		System.out.println(result.getProductName()+ " has been added");
    	}
    	
    	
    }
    
    public void checkout() {
    	double checkoutTotal=0;
        Request.instance().setMemberId(getToken("Enter member id"));
        
        Result result = groceryStore.startCheckOut(Request.instance());
  
        if (result.getResultCode() != Result.OPERATION_COMPLETED) {
            System.out.println("No such member with id " + Request.instance().getMemberId());
            return;
        }
        do {
        	
        	Request.instance().setProductId(getResponse("Please enter product id"));
        	Request.instance().setTransactionQuantity(getNumber("Please enter product quantity"));
            result = groceryStore.checkOutProduct(Request.instance());
            if(result.getResultCode() == Result.OPERATION_COMPLETED) {
            	checkoutTotal += result.getTransactionTotal();
            	System.out.println(result.getProductName()+ " " + result.getTransactionQuantity()+ " " +
            					result.getProductPrice()+ " " +result.getTransactionTotal());
            }
            else {
            	System.out.println("No Such Product");
            }

			
		} while (yesOrNo("Add more products?"));
        
        Iterator<Result> iterator  = groceryStore.endCheckOut();
        System.out.println("The total of this checkout process is: $"+checkoutTotal);
        System.out.println("List of Re-Orders (name, Quantity, Order-id)");
//        System.out.println(iterator.hasNext());
        while (iterator.hasNext()) {
            Result reOrders = iterator.next();
            System.out.println(reOrders.getProductName()+" "+reOrders.getOrderQuantity()+" "+reOrders.getOrderId());
        }
        System.out.println("End of listing");
    }
    
    public void processShipment() {
    	
    	do {
    		
    		Request.instance().setOrderId(getResponse("Please enter the order numer"));
        	Result result = groceryStore.processShipment(Request.instance());
        	if(result.getResultCode() == Result.OPERATION_COMPLETED) {
        		System.out.println("Successfully Processed");
        		System.out.println(result.getProductId() +"   "+ result.getProductName()+"  "+ result.getProductStockInHand());
        	}
        	else {
        		System.out.println("Sorry that order cannot be found. Try again");
        	}
			
		} while (yesOrNo("Do you want to process more shipments?"));
    	
    }
    
    public void changePrice() {
    	Request.instance().setProductId(getResponse("Please Enter Product ID"));
    	Request.instance().setProductPrice(getMoney("Please Enter New Price"));
    	
    	Result result = groceryStore.changePrice(Request.instance());
    	
    	  switch (result.getResultCode()) {
    
          case Result.NO_SUCH_PRODUCT:
              System.out.println(" Product with id " + Request.instance().getProductId() +
            		  				" does not exist");
              break;
          case Result.OPERATION_FAILED:
              System.out.println("Unable to change product price!");
              break;
          case Result.OPERATION_COMPLETED:
              System.out.println("Sucess! The new price for, "+ result.getProductName()+" is: "
            		  				+ result.getProductPrice());
              break;
          default:
              System.out.println("An error has occurred");
          }
    }
    
    public void getProductInfo() {
    	Request.instance().setProductName(getResponse("Please enter product name"));
    	
    	Iterator<Result> iterator = groceryStore.getProductInfo(Request.instance());
        System.out.println("List of matched products (name, id, price, stock-in-hand, re-order-level)");
        
        
        while (iterator.hasNext()) {
        	Result result = iterator.next();
        	System.out.println(result.getProductName()+" "+result.getProductId()+" "+
        						result.getProductPrice()+" "+ result.getProductStockInHand()+" "+
        						result.getProductReOrderLevel());
        }
        
        System.out.println("End of listing");

    }
    
    public void getMemberInfo() {
    	Request.instance().setProductName(getResponse("Please enter member name"));
    	
    	Iterator<Result> iterator = groceryStore.getMemberInfo(Request.instance());
        System.out.println("List of matched members (name, id, address, FeePaid)");
        
        
        while (iterator.hasNext()) {
        	Result result = iterator.next();
        	System.out.println(result.getMemberName()+"   "+result.getMemberId()+"   "+
        						result.getMemberAddress()+"   "+result.getMemberFeePaid());
        }
        
        System.out.println("End of listing");
    }
    
    public void getTransactions() {
    	
        Request.instance().setMemberId(getToken("Enter member id"));
        Request.instance().setStartDate(getDate("Please enter the starting date for which you want records as dd/mm/yy"));
        Request.instance().setEndDate(getDate("Please enter the ending date for which you want records as dd/mm/yy"));
        
        Iterator<Result> iterator = groceryStore.getTransactions(Request.instance());
        System.out.println("List of matched transactions (product name, price, quantity, total price, date)");

        while (iterator.hasNext()) {
        	Result result = iterator.next();
        	System.out.println(result.getProductName()+"   "+result.getProductPrice()+"   "+
        						result.getTransactionQuantity()+"   "+result.getTransactionTotal()
        						+" "+ result.getTransactionDate());
        }
        
        System.out.println("End of listing");
    }
    
    public void getOutStandingOrders() {
        Iterator<Result> iterator = groceryStore.getOutStandingOrders();
        System.out.println("List of all outStanding orders ( id, Product name, orderdate, Quantity Odered)");
        while (iterator.hasNext()) {
            Result result = iterator.next();
            if (result.getResultCode() == Result.ORDER_IS_NOT_FULFILLED) {
            	System.out.println(result.getOrderId()+ " " + result.getProductName() + " " + 
            				result.getOrderDate() + " " + result.getOrderQuantity());
            }
            
        }
        System.out.println("End of listing");
    }
    
    public void getAllMembers() {
        Iterator<Result> iterator = groceryStore.getAllMembers();
        System.out.println("List of members (name, address, phone, id)");
        while (iterator.hasNext()) {
            Result result = iterator.next();

            	System.out.println(result.getMemberName()+ ", " + result.getMemberId() + ", " 
            			+ result.getMemberAddress() + ", " + result.getMemberPhone()
            			+ ", " + result.getMemberDateJoined());

            
        }
        System.out.println("End of listing");
    }
    
    public void getAllProducts() {
        Iterator<Result> iterator = groceryStore.getAllProducts();
        System.out.println("List of all Products (name, id, price, stock-in-hand, re-order-level)");
        while (iterator.hasNext()) {
            Result result = iterator.next();
            System.out.println(result.getProductName()+ ", " + result.getProductId() + ", " + 
            				result.getProductPrice() + ", " + result.getProductStockInHand()
                    + ", " + result.getProductReOrderLevel());
        }
        System.out.println("End of listing");
    } 
    
    
    
    
    
    /**
     * Gets a token after prompting
     * 
     * @param prompt - whatever the user wants as prompt
     * @return - the token from the keyboard
     * 
     */
    public String getToken(String prompt) {
        do {
            try {
                System.out.println(prompt);
                String line = reader.readLine();
                StringTokenizer tokenizer = new StringTokenizer(line, "\n\r\f");
                if (tokenizer.hasMoreTokens()) {
                    return tokenizer.nextToken();
                }
            } catch (IOException ioe) {
                System.exit(0);
            }
        } while (true);
    }



    private boolean yesOrNo(String prompt) {
        String more = getToken(prompt + " (Y|y)[es] or anything else for no");
        if (more.charAt(0) != 'y' && more.charAt(0) != 'Y') {
            return false;
        }
        return true;
    }
    
    /**
     * @param prompt - Get the response after being given this prompt.
     * @return the response given by user
     */
    private String getResponse(String prompt) {
        do {
            try {
                System.out.println(prompt);
                String line = reader.readLine();
                return line;
            } catch (IOException ioe) {
                System.exit(0);
            }
        } while (true);
    }
    
    /**
     * Converts the string to a double to handle money values
     * 
     * @param prompt the string for prompting
     * @return the double corresponding to the string
     * 
     */
    public double getMoney(String prompt) {
        do {
            try {
                String item = getToken(prompt);
                Double number = Double.valueOf(item);
                return number.doubleValue();
            } catch (NumberFormatException nfe) {
                System.out.println("Please input a number ");
            }
        } while (true);
    }
    
    /**
     * Converts the string to a number
     * 
     * @param prompt the string for prompting
     * @return the integer corresponding to the string
     * 
     */
    public int getNumber(String prompt) {
        do {
            try {
                String item = getToken(prompt);
                Integer number = Integer.valueOf(item);
                return number.intValue();
            } catch (NumberFormatException nfe) {
                System.out.println("Please input a number ");
            }
        } while (true);
    }
    
    
    /**
     * Prompts for a date and gets a date object
     * 
     * @param prompt the prompt
     * @return the data as a Calendar object
     */
    public Calendar getDate(String prompt) {
        do {
            try {
                Calendar date = new GregorianCalendar();
                String item = getToken(prompt);
                DateFormat dateFormat = SimpleDateFormat.getDateInstance(DateFormat.SHORT);
                date.setTime(dateFormat.parse(item));
                return date;
            } catch (Exception fe) {
                System.out.println("Please input a date as mm/dd/yy");
            }
        } while (true);
    }
    
    
    /**
     * Prompts for a command from the keyboard
     * 
     * @return a valid command
     * 
     */
    public int getCommand() {
        do {
            try {
                int value = Integer.parseInt(getToken("Enter command:" + HELP + " for help"));
                if (value >= EXIT && value <= HELP) {
                    return value;
                }
            } catch (NumberFormatException nfe) {
                System.out.println("Enter a number");
            }
        } while (true);
    }

    /**
     * Displays the help screen
     * 
     */
    public void help() {
        System.out.println("Enter a number between 0 and 12 as explained below:");
        System.out.println(EXIT + " to Exit\n");
        System.out.println(ADD_MEMBER + " to add a member");
        System.out.println(REMOVE_MEMBER + " to  remove a member");
        System.out.println(ADD_PRODUCT + " to  add a product to the catalog");
        System.out.println(CHECKOUT + " to  checkout a customer ");
        System.out.println(PROCESS_SHIPMENT + " to  process a shipment ");
        System.out.println(CHANGE_PRICE + " to change the price of a product");
        System.out.println(GET_PRODUCT_INFO + " to find a product's info");
        System.out.println(GET_MEMBER_INFO + " to find a member's info");
        System.out.println(GET_TRANSACTIONS + " to  print transactions");
        System.out.println(GET_OUTSTANDING_ORDERS + " to get a list of all outstanding orders");
        System.out.println(GET_ALL_MEMBERS + " to get a list of all members");
        System.out.println(GET_ALL_PRODUCTS + " to get a list of all products");
        System.out.println(SAVE + " to  save data");
        System.out.println(HELP + " for help");
    }
    
    
    private void save() {
        if (groceryStore.save()) {
            System.out.println(" The GroceryStore data has been successfully saved in the file GroceryStore \n");
        } else {
            System.out.println(" There has been an error in saving \n");
        }
    }   
    
    private void retrieve() {
        try {
            if (groceryStore == null) {
            	groceryStore = GroceryStore.retrieve();
                if (groceryStore != null) {
                    System.out.println(" The GroceryStore Data has been successfully retrieved from the file GroceryStore \n");
                } else {
                    System.out.println("File doesnt exist; creating new GroceryStore");
                    groceryStore = GroceryStore.instance();
                }
            }
        } catch (Exception cnfe) {
            cnfe.printStackTrace();
        }
    }
    
    /**
     * Orchestrates the whole process. Calls the appropriate method for the
     * different functionalities.
     * 
     */
    public void process() {
        int command;
        help();
        while ((command = getCommand()) != EXIT) {
            switch (command) {
            case ADD_MEMBER:
                addMember();
            
                break;
            case REMOVE_MEMBER:
                removeMember();
                break;
            case ADD_PRODUCT:
                addProduct();
                break;
            case CHECKOUT:
               checkout();
                break;
            case PROCESS_SHIPMENT:
                processShipment();
                break;
            case CHANGE_PRICE:
                changePrice();
                break;
            case GET_PRODUCT_INFO:
                getProductInfo();
                break;
            case GET_MEMBER_INFO:
                getMemberInfo();
                break;
            case GET_TRANSACTIONS:
                getTransactions();
                break;
            case GET_OUTSTANDING_ORDERS:
                getOutStandingOrders();
                break;
            case GET_ALL_MEMBERS:
                getAllMembers();
                break;
            case GET_ALL_PRODUCTS:
                getAllProducts();
                break;
            case SAVE:
                save();
                break;
            case HELP:
                help();
                break;
            }
        }
    }

    /**
     * The method to start the application. S14imply calls process().
     * 
     * @param args not used
     */
    public static void main(String[] args) {
        UserInterface.instance().process();
    }
    
    
}
