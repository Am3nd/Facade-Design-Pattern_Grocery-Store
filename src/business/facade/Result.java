package business.facade;



import business.entities.Order;
import business.entities.Transaction;

public class Result extends DataTransfer {
	
    public static final int NO_SUCH_MEMBER = 1;
    public static final int NO_SUCH_PRODUCT = 2;
    public static final int ORDER_IS_FULFILLED = 3;
    public static final int ORDER_IS_NOT_FULFILLED = 4;
    public static final int OPERATION_COMPLETED = 5;
    public static final int OPERATION_FAILED = 6;
    public static final int NO_SUCH_ORDER = 7;

	
	
	private String memberDateJoined;
	
	private String transactionDate;
	private double 	 transactionTotal;	
	
	
	private String orderDate;
	private int orderQuantity;
	

    private int resultCode;

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }
	
	
	
	public void setTransactionFields(Transaction transaction) {

		this.productId = transaction.getProductID();
		this.transactionQuantity = transaction.getQuantity();
		this.transactionTotal = transaction.getTotal();
		this.productPrice = transaction.getUnitPrice();
		this.productName = transaction.getProductName();
		
	}
	
	public void setOrderFields(Order order) {
		this.orderId = order.getId();
		this.productId = order.getProductID();
		this.productName = order.getProductName();
		
		this.orderQuantity = order.getOrderQuantity();
		this.orderDate = order.getOrderDate();
	}
	
	public String getMemberDateJoined() {
		return memberDateJoined;
	}

	public String getOrderDate() {
		return orderDate;
	}
	public int getOrderQuantity() {
		return orderQuantity;
	}
	public String getTransactionDate() {
		return transactionDate;
	}
	public double getTransactionTotal() {
		return transactionTotal;
	}
	public void setMemberDateJoined(String memberDateJoined) {
		this.memberDateJoined = memberDateJoined;
	}


	public void setOrderQuantity(int orderQuantity) {
		this.orderQuantity = orderQuantity;
	}
	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}
	public void setTransactionTotal(double transactionTotal) {
		this.transactionTotal = transactionTotal;
	}
	
	
	
	
	
}
