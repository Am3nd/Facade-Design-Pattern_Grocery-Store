package business.facade;

import business.entities.Member;

import business.entities.Product;


public class DataTransfer {

	private String memberId; 
	private String memberName;
	private String memberAddress;
	private String memberPhone;
	private double memberFeePaid;
	
	protected String productId;
	protected String productName;
	protected double productPrice;
		
	private int    productStockInHand;
	private int    productReOrderLevel;
	
	protected int transactionQuantity; 
	
	protected String    orderId;
	
	
	
	
    /**
     * This sets all fields to "none".
     */
    public DataTransfer() {
        reset();
    }
	
	public void setProductFields(Product product) {
		this.productId = product.getId();
		this.productName = product.getName();
		this.productStockInHand = product.getStockInHand();
		this.productPrice = product.getPrice();
		this.productReOrderLevel = product.getReOrderLevel();
	}
	
	public void setMemberFields(Member member) {
		this.memberId = member.getId();
		this.memberName = member.getName();
		this.memberAddress = member.getAddress();
		this.memberPhone = member.getPhone();
		this.memberFeePaid = member.getFeePaid();
		
	}
	


	

	
	

	public int getTransactionQuantity() {
		return transactionQuantity;
	}
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getMemberId() {
		return memberId;
	}


	public String getMemberName() {
		return memberName;
	}


	public String getMemberAddress() {
		return memberAddress;
	}


	public String getMemberPhone() {
		return memberPhone;
	}


	public double getMemberFeePaid() {
		return memberFeePaid;
	}


	public String getProductName() {
		return productName;
	}


	public int getProductStockInHand() {
		return productStockInHand;
	}


	public double getProductPrice() {
		return productPrice;
	}


	public int getProductReOrderLevel() {
		return productReOrderLevel;
	}

	public String getOrderId() {
		return orderId;
	}


	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}


	public void setMemberAddress(String memberAddress) {
		this.memberAddress = memberAddress;
	}


	public void setMemberPhone(String memberPhone) {
		this.memberPhone = memberPhone;
	}


	public void setMemberFeePaid(double memberFeePaid) {
		this.memberFeePaid = memberFeePaid;
	}


	public void setProductName(String productName) {
		this.productName = productName;
	}


	public void setProductStockInHand(int productStockInHand) {
		this.productStockInHand = productStockInHand;
	}


	public void setProductPrice(double productPrice) {
		this.productPrice = productPrice;
	}


	public void setProductReOrderLevel(int productReOrderLevel) {
		this.productReOrderLevel = productReOrderLevel;
	}


	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public void setTransactionQuantity(int productQuantity) {
		this.transactionQuantity = productQuantity;
	}
	/**
	 * Resets all parameters
	 */
	public void reset() {
		
		this.memberId = null;
		this.memberName = null;
		this.memberAddress = null;
		this.memberPhone = null;
		this.memberFeePaid = 0;
		this.productId = null;
		this.productName = null;
		this.productStockInHand = 0;
		this.productPrice = 0;
		this.productReOrderLevel = 0;
		this.orderId = null;
		this.transactionQuantity = 0;
	}

	
	
 
}
