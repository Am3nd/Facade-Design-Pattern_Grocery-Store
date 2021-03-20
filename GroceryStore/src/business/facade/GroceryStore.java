package business.facade;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
/**
 * 
 * ToDoList - 
 * 1) Check the toString functions for all classes.
 * 2) Add comments to all proper functions and classes
 * 3) XXXCOMPLETEDXXX transaction -> onDateRange need to be filled out.
 * 4) XXXCOMPLETEDXXX product stocks levels adjustment need to be setup.
 * 5)  XXXCOMPLETEDXXX [i put this method in product class]  checkProductMatch in product list needs to be filled out
 * 6)XXXCOMPLETEDXXX  [Finished just verify] All 3 classes member, product, and order class needs their idCounter to be serializable
 * 7) XXXCOMPLETEDXXX work on retrieve method at bottom for userInterface class
 * 8) XXXCOMPLETEDXXX the design document states that the user needs to enter the product id - confirm with professor
 * 9) Check to see if product need to deleted if so add methods product list
 * 10)XXXCOMPLETEDXXX  Make sure to add delete to member list
 * 11)XXXCOMPLETEDXXX  There are several types of quantity new product quantity checkout quantity order quantity
 * 		ask the professor should we combine all of them in the datatranfer classes or separate 
 * 		them based on request/results types - assumption is that they are seperate
 * 12) XXXCOMPLETEDXXX ask the professor if an order is meant for one product at a time, or multiple.
 * 13) XXXCOMPLETEDXXX ask professor the reason for reseting and should we reset using null as a value;
 * 14) XXXCOMPLETED ->we dont need to do this.XXX  add reset to both request and result
 * 15) XXXCOMPLETEDXXX What kind of error codes do we need.
 * 16) Look at result class is it okay to have all those variables or put them into datatransfer class
 * 17) XXXCOMPLETEDXXX getting the price from the user and changing to float/double....
 * 18) Ask professor if we need to do input validation
 * 19) XXXCOMPLETEDXXX remove product_string from product class
 * 20) XXXCOMPLETEDXXX Fix calendar in user interface when add a new user
 * 21) When reordering a product after it was just added do we need to do any checking
 * 		to see if it was added and do we display the info order id if we reordered
 * 22) Little bit iffy on the checkout process - check with team and prof
 * 23) Check to see every class has getters and setter no more than necessary
 * 		ie remove unnecessary one
 * 24) XXXCOMPLETEDXXX in the library design. why didnt the prof use safeiterator for getTransactions()
 * 25) XXXCOMPLETEDXXX Ask professor about using a generic itemlist class and to use generic search methods ..etc 
 * 		asked him in class and he said no penalty for not using it.
 * 26) Dont think we need the searchMembership() function look and see if any method is referring to it.
 * 
 * 
 * 
 * 
 * GroceryStore is a singleton, facade class that is responsible for accessing
 * methods and functions relating to the member and product class.
 * It contains the three private inner classes of productList, memberList, 
 * and orderList detailing the GroceryStore Co-Op.
 * 
 * @author Abdi Ahmed, Asha Hassan, Elise Kurtz, Faisal Saeed
 *
 */
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


import business.entities.*;
import business.entities.iterators.SafeIterator;



public class GroceryStore implements Serializable {
    private static final long serialVersionUID = 1L;
    private ProductList products = new ProductList();
    private MemberList members = new MemberList();
    private OrderList orders = new OrderList();
    private CheckOut checkOutCounter1 = new CheckOut();
    private static GroceryStore groceryStore;
	
    
    /**
     * Private for the singleton pattern Creates the product, member and order collection
     * objects
     */
    private GroceryStore() {
    }
    
    /**
     * Supports the singleton pattern
     * 
     * @return the singleton object
     */
    public static GroceryStore instance() {
        if (groceryStore == null) {
            return groceryStore = new GroceryStore();
        } else {
            return groceryStore;
        }
    }

    
    
    /**
     * Product Collections
     *
     */
    private class ProductList implements Iterable<Product>, Serializable {
        private static final long serialVersionUID = 1L;
        private List<Product> products = new LinkedList<Product>();
        
        
        /**
         * Will search the ProductList for a product that matches the given id.
         * @param productId the id of the product
         * @return the product if found otherwise will return null
         */
        public Product search(String productId) {
            for (Iterator<Product> iterator = products.iterator(); iterator.hasNext();) {
            	Product product = (Product) iterator.next();
                if (product.getId().equals(productId)) {
                    return product;
                }
            }
            return null;
        }
        
        
        /**
         * @param takes in a Product object.
         * @return true if the object is inserting into the ProductList
         */
        public boolean insertProduct(Product product) {
        	;
        	return products.add(product);
        }
        

        
        
		/**
		 * Returns an iterator to the ProductList
		 */
		@Override
		public Iterator<Product> iterator() {
		
			return products.iterator();
		}
		
		 /**
         * String form of the collection
         * 
         */
		@Override
        public String toString() {
            return products.toString();
        }
    	
    }
    
    /**
     * Member Collections
     *
     */
    private class MemberList implements Iterable<Member>, Serializable{

        private static final long serialVersionUID = 1L;
        private List<Member> members = new LinkedList<Member>();

        /**
         * Checks whether a member with a given member id exists.
         * 
         * @param memberId the id of the member
         * @return true if member is matched with memberID
         * 
         */
        public Member search(String memberId) {
            for (Iterator<Member> iterator = members.iterator(); iterator.hasNext();) {
                Member member = iterator.next();
                if (member.getId().equals(memberId)) {
                    return member;
                }
            }
            return null;
        }
        
        /**
         * Inserts a member into the collection
         * 
         * @param member the member to be inserted
         * @return true if the member could be inserted. Currently always true
         */
        public boolean insertMember(Member member) {
            members.add(member);
            return true;
        }
        
     
        /**
         * @param memberId uses it to check if member exists if not it will return false
         * 			
         * @return false if member does not exist, also false if unable to remove member
         *  	   true if member is successfully removed.
         */
        public boolean removeMember(String memberId) {
        	Member member = search(memberId);
        	if( member == null) {
        		return false;
        	}
        	else {
        		return members.remove(member);
        	}
        }


        
        /**
         * String form of the collection
         * 
         */
        @Override
        public String toString() {
            return members.toString();
        }
        
		@Override
		public Iterator<Member> iterator() {
			
			return members.iterator();
		}
		
    	
    }
    
    
    /**
     * Order collections
     *
     */
    private class OrderList implements Iterable<Order>, Serializable{

        private static final long serialVersionUID = 1L;
        private List<Order> orders = new LinkedList<Order>();
         
        /**
         * Checks whether a order with a given orderId exists.
         * 
         * @param orderId the id of the order
         * @return order found or null
         * 
         */
        public Order search(String orderID) {
            for (Iterator<Order> iterator = orders.iterator(); iterator.hasNext();) {
                Order order = iterator.next();
                if (order.getId().equals(orderID)) {
                    return order;
                }
            }
            return null;
        }
        
        /**
         * Inserts an order into the collection
         * 
         * @param order the order to be inserted
         * @return true if the order could be inserted. Currently always true
         */
        public boolean insertOrder(Order order) {
            orders.add(order);
            return true;
        }
        
        /**
         * String form of the collection
         * 
         */
        @Override
        public String toString() {
            return orders.toString();
        }
        
        
		@Override
		public Iterator<Order> iterator() {
			
			return orders.iterator();
		}
    	
    }
    
    
    private class CheckOut implements Iterable<Transaction>, Serializable{
        private static final long serialVersionUID = 1L;
        private List<Transaction> transactions = new LinkedList<Transaction>();
        private LinkedList<Order> reOrders = new LinkedList<Order>();
        private double total;
        private Member member;
        
  
        public boolean insertTransaction(Transaction transaction) {
        	this.total += transaction.getTotal();
        	
        	return transactions.add(transaction);
        }
        
		@Override
		public Iterator<Transaction> iterator() {
			
			return transactions.iterator();
		}

	
		public Iterator<Order> reOrderIterator() {
			
			return reOrders.iterator();
		} 
		
		public boolean process() {
			
			for ( Iterator<Transaction> iterator = transactions.iterator(); iterator.hasNext();) {
				Transaction transaction = iterator.next();
				
				Product product = products.search(transaction.getProductID());
				product.setStockInHand(product.getStockInHand() - transaction.getQuantity());
				
				 if (this.member.addTransaction(transaction)) {
					 transactions.remove(transaction);
				 }
				 else {
					 return false;
				 }
				if(product.getStockInHand() <= product.getReOrderLevel()) {
					Order newOrder = new Order(product.getName(), product.getId(), product.getReOrderLevel()*2);
			
					reOrders.add(newOrder);
				}
				
				
			}
			
			return true;
		}
		
		public void setCheckOutMember(Member member) {
			this.member = member;
		}
		
		public LinkedList<Order> getReOrders() {
			return this.reOrders;
		}
		

		
		
    }
    
    public Result addMember(Request request) {
    	
    	Result result = new Result();
    	Member member = new Member(request.getMemberName(), request.getMemberAddress()
    								, request.getMemberPhone(), request.getMemberFeePaid());
    	if(members.insertMember(member)) {
    		result.setResultCode(Result.OPERATION_COMPLETED);
    		result.setMemberFields(member);
    		result.setMemberDateJoined(member.getDateJoined());
    		return result;
    	}
    	else {
    		result.setResultCode(Result.OPERATION_FAILED);
    		return result;
    	}
    	
    }
    
    
    public Result removeMember(Request request) {
    	Result result = new Result();
    	Member member = members.search(request.getMemberId());
    	if(member == null) {
    		result.setResultCode(Result.NO_SUCH_MEMBER);
    		return result;
    	}
    	
    	result.setMemberFields(member);
    	if(  members.removeMember(request.getMemberId())   ) {
    		result.setResultCode(Result.OPERATION_COMPLETED);
    		return result;
    	}
    	else {
    		result.setResultCode(Result.OPERATION_FAILED);
    		return result;
    	}
    	
    }
    
    public Result startCheckOut(Request request) {
    	Result result = new Result();
    	Member member = members.search(request.getMemberId());
    	
    	if(member != null) {
    		checkOutCounter1.setCheckOutMember(member);
    		result.setResultCode(Result.OPERATION_COMPLETED);
    		return result;
    	}
    	else {
    		result.setResultCode(Result.NO_SUCH_MEMBER);
    		return result;
    	}
    }
    
    public Result checkOutProduct(Request request) {
    	
    	Result result = new Result();
    	Product product = products.search(request.getProductId());
    	if(product != null) {
    		Transaction transaction = new Transaction(product.getId(),product.getName(),
    								request.getTransactionQuantity(),product.getPrice());
    		if(checkOutCounter1.insertTransaction(transaction)) {
    			result.setTransactionFields(transaction);
    			result.setResultCode(Result.OPERATION_COMPLETED);
    			return result;
    		}
    		else {
    			result.setResultCode(Result.OPERATION_FAILED);
    		}
    		
    	}
    	else {
    		result.setResultCode(Result.NO_SUCH_PRODUCT);
    	}
    	
    
    	return result;
    }
    
    public Iterator<Result> endCheckOut() {
    	
    	if(checkOutCounter1.process()) {
    		LinkedList<Order> reOrder = checkOutCounter1.getReOrders();

    		for ( Order order: reOrder) {
    			orders.insertOrder(order);
    		}
    		return new SafeIterator<Order>(reOrder.iterator(), SafeIterator.ORDER);
    		
    	}
    	else {
    		return new SafeIterator<Order>(new LinkedList<Order>().iterator(), SafeIterator.ORDER);
    	}
    }
    
    public Result addProduct(Request request) {
    	Result result = new Result();
    	
    	Product product = new Product(request.getProductName(), request.getProductId(),
    								request.getProductPrice(), request.getProductStockInHand(),
    								request.getProductReOrderLevel());
    	if(products.insertProduct(product)) {
    		
    		Order order =  new Order(product.getName(), product.getId(), 
    								product.getReOrderLevel()*2); 
    		orders.insertOrder(order);
    		result.setOrderId(order.getId());
    		
    		result.setResultCode(Result.OPERATION_COMPLETED);
    		result.setProductFields(product);
    		return result;
    	}
    	else {
    		result.setResultCode(Result.OPERATION_FAILED);
    		return result;
    	}
    	
    	
    }
    
    
    
    public Result processShipment(Request request) {
    	 Result result = new Result();
    	 Order order = orders.search(request.getOrderId());
    	 if(order != null) {
    		 Product product = products.search(order.getProductID());
    		 product.setStockInHand(product.getStockInHand()+order.getOrderQuantity());
    		 order.setStatus(Result.ORDER_IS_FULFILLED);
    		 result.setProductFields(product);
    		 result.setResultCode(Result.OPERATION_COMPLETED);
    		 return result;
    	 }
    	 else {
    		 result.setResultCode(Result.NO_SUCH_ORDER);
    		 return result;
    	 }
    }
    
    public Result changePrice(Request request) {
    	Result result = new Result();
    	
    	Product product = products.search(request.getProductId());
    	
    	if( product == null) {
    		result.setResultCode(Result.NO_SUCH_PRODUCT);
    		return result;
    	}
    	if(product.setPrice(request.getProductPrice())) {
    		
    		
    		result.setProductFields(product);
    		result.setResultCode(Result.OPERATION_COMPLETED);
    		return result;
    		
    	}
    	else {
    		result.setResultCode(Result.OPERATION_FAILED);
    		return result;
    	}
    	
    }
    
    
    /**
     * Returns an iterator to product info. The Iterator returned is a safe one, in
     * the sense that only copies of the product fields are assembled into the
     * objects returned via next().
     * 
     * @return an Iterator to Result - only the product fields are valid.
     */
    public Iterator<Result> getProductInfo(Request request) {
    	String name = request.getProductName();
        List<Product> filteredProducts  = new LinkedList<Product>();
        
        
        for (Iterator<Product> iterator = products.iterator(); iterator.hasNext(); ) {
        	Product product = iterator.next();
        	if(product.checkNameMatch(name)) {
        		filteredProducts.add(product);
        	}
        }
       
    	
    	return new SafeIterator<Product>(filteredProducts.iterator(), SafeIterator.PRODUCT);
    }
    
    /**
     * Returns an iterator to Member info. The Iterator returned is a safe one, in
     * the sense that only copies of the Member fields are assembled into the
     * objects returned via next().
     * 
     * @return an Iterator to Result - only the Member fields are valid.
     */
    public Iterator<Result> getMemberInfo(Request request) {
    	String name = request.getMemberName();
        List<Member> filteredMembers  = new LinkedList<Member>();
        
        
        for (Iterator<Member> iterator = members.iterator(); iterator.hasNext(); ) {
        	Member member = iterator.next();
        	if(member.checkNameMatch(name)) {
        		filteredMembers.add(member);
        	}
        }
       
    	
    	return new SafeIterator<Member>(filteredMembers.iterator(), SafeIterator.MEMBER);
    }
    
    /**
     * Returns an iterator to Member info. The Iterator returned is a safe one, in
     * the sense that only copies of the Member fields are assembled into the
     * objects returned via next().
     * 
     * @return an Iterator to Result - only the Member fields are valid.
     */
    public Iterator<Result> getAllMembers() {
        return new SafeIterator<Member>(members.iterator(), SafeIterator.MEMBER);
    }
    
    
    
    public Iterator<Result> getTransactions(Request request){
        Member member = members.search(request.getMemberId());
 
        if (member == null) {
        	return new SafeIterator<Transaction>(new LinkedList<Transaction>().iterator(), SafeIterator.TRANSACTION);
        }
        return new SafeIterator<Transaction>(
        		member.getTransactionsOnDateRange(request.getStartDate(),request.getEndDate()),
        		SafeIterator.TRANSACTION);

    }
    
    
    /**
     * Returns an iterator to Orders info. The Iterator returned is a safe one, in
     * the sense that only copies of the Orders fields are assembled into the
     * objects returned via next().
     * 
     * @return an Iterator to Result - only the Orders fields are valid.
     */
    public Iterator<Result> getOutStandingOrders() {
        return new SafeIterator<Order>(orders.iterator(), SafeIterator.ORDER);
    }
    
    /**
     * Returns an iterator to Products info. The Iterator returned is a safe one, in
     * the sense that only copies of the Products fields are assembled into the
     * objects returned via next().
     * 
     * @return an Iterator to Result - only the Products fields are valid.
     */
    public Iterator<Result> getAllProducts() {
        return new SafeIterator<Product>(products.iterator(), SafeIterator.PRODUCT);
    }
    
    /**
     * Searches for a given member
     * 
     * @param memberId id of the member
     * @return true iff the member is in the member list collection
     */
    public Result searchMembership(Request request) {
        Result result = new Result();
        Member member = members.search(request.getMemberId());
        if (member == null) {
            result.setResultCode(Result.NO_SUCH_MEMBER);
        } else {
            result.setResultCode(Result.OPERATION_COMPLETED);
            result.setMemberFields(member);
        }
        return result;
    }
    
	public boolean save() {
        try {
            FileOutputStream file = new FileOutputStream("GroceryStoreData");
            ObjectOutputStream output = new ObjectOutputStream(file);
            output.writeObject(groceryStore);
            Member.save(output);
            Order.save(output);
            file.close();
            return true;
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return false;
        }
	
	}    
    public static GroceryStore retrieve() {
        try {
            FileInputStream file = new FileInputStream("GroceryStoreData");
            ObjectInputStream input = new ObjectInputStream(file);
            groceryStore = (GroceryStore) input.readObject();
            Member.retrieve(input);
            Order.retrieve(input);
            return groceryStore;
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return null;
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
            return null;
        }
    	
    }


    

}
