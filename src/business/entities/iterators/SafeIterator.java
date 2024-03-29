/**
 * 
 */
package business.entities.iterators;

import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;

import business.entities.Member;
import business.entities.Order;
import business.entities.Product;
import business.entities.Transaction;

import business.entities.iterators.SafeIterator.Type.*;
import business.facade.Result;

/**
 * * This Iterator implementation is tailor-made to supply "read-only" versions of
 * Product, Order, and Member objects. It is generic. If the user chooses Product for the
 * generic parameter, they should also supply SafeIterator.PRODUCT as the second
 * parameter to the constructor. Similarly, if they choose Member instead, they
 * should also choose SafeIterator.MEMBER as the second parameter of
 * constructor.
 * 
 * @author Abdi Ahmed, Asha Hassan, Elise Kurtz, Faisal Saeed
 *
 * * @param <T> Either Product, Order, or Member


 */
public class SafeIterator<T> implements Iterator<Result>, Serializable {
	
	  private Iterator<T> iterator;
	    private Type type;
	    private Result result = new Result();
	    public static final Type PRODUCT = new SafeProduct();
	    public static final Type ORDER = new SafeOrder();
	    public static final Type MEMBER = new SafeMember();
	    public static final Type TRANSACTION = new SafeTransaction();

    /**
     * This class is designed to ensure that the appropriate object is used to copy
     * to the Result object.
     * 
 * @author Abdi Ahmed, Asha Hassan, Elise Kurtz, Faisal Saeed
     *
     */
    public abstract static class Type {
        /**
         * The copy method is used to copy the object to Result. Object is Product, Order or
         * Member at present.
         * 
         * @param result the Result object
         * @param object the Product, Order, or Member object
         */
        public abstract void copy(Result result, Object object);

        public static class SafeProduct extends Type {
            @Override
            public void copy(Result result, Object object) {
                Product product = (Product) object;
                result.setProductFields(product);
            }
        }
        
        public static class SafeOrder extends Type {
            @Override
            public void copy(Result result, Object object) {
                Order order = (Order) object;
                result.setOrderFields(order);
                result.setResultCode(order.getStatus());
            }
        }

        public static class SafeMember extends Type {
            @Override
            public void copy(Result result, Object object) {
                Member member = (Member) object;
                result.setMemberFields(member);
                result.setMemberDateJoined(member.getDateJoined());
            }
        }
        
        public static class SafeTransaction extends Type {
            @Override
            public void copy(Result result, Object object) {
                Transaction transaction = (Transaction) object;
                result.setTransactionFields(transaction);
                result.setTransactionDate(transaction.getDate());
            }
        }
    }
    
    /**
     * The user of SafeIterator must supply an Iterator to Product, Order, or Member. If
     * Iterator<Product> is passed as the first parameter, SafeItearator.PRODUCT should be
     * passed as the second parameter. If Iterator<Member> is passed as the first
     * parameter, SafeItearator.MEMBER should be the second parameter.
     * 
     * @param iterator Iterator<Product>, Iterator<Order> or Iterator<Member>
     * @param type     SafeItearator.Product, SafeItearator.Order or SafeItearator.MEMBER
     */
    public SafeIterator(Iterator<T> iterator, Type type) {
        this.iterator = iterator;
        this.type = type;
    } 

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public Result next() {
        if (iterator.hasNext()) {
            type.copy(result, iterator.next());
        } else {
            throw new NoSuchElementException("No such element");
        }
        return result;
    }
	

}
