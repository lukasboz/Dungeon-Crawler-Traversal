/**
 * @author Lukas Bozinov
 *
 * @param <T>
 */
public class DLinkedNode<T> {
	
	private T dataItem; // generic variable T 
	private double priority; //priority of the nodes of the queue
	private DLinkedNode<T> next; //next pointer
	private DLinkedNode<T> prev; //prev pointer
	
	
	/**
	 * Constructor for a single node of the LinkedQueue. Takes data and priority in as parameters.
	 * 
	 * @param data
	 * @param prio
	 */
	public DLinkedNode(T data, double prio) {
		this.priority = prio;
		this.dataItem = data;
		this.next = this.prev = null;
	}
	
	/**
	 * Constructor for a single node of LinkedQueue without parameters.
	 */
	public DLinkedNode() {
		this.priority = 0;
		this.dataItem = null;
		this.next = this.prev = null;
		
	}

	
	/**
	 * Get method for the dataItem of a LinkedQueue node.
	 * 
	 * @return dataItem
	 */
	public T getDataItem() {
		return dataItem;
	}

	/**
	 * Set method for the dataItem of a LinkedQueue node.
	 * 
	 * @param dataItem
	 */
	public void setDataItem(T dataItem) {
		this.dataItem = dataItem;
	}

	/**
	 * Get method for the priority of a LinkedQueue node.
	 * 
	 * @return priority
	 */
	public double getPriority() {
		return priority;
	}

	/**
	 * Set method for the priority of a LinkedQueue node.
	 * 
	 * @param priority
	 */
	public void setPriority(double priority) {
		this.priority = priority;
	}

	/**
	 * Get method for the next node of "this" LinkedQueue node.
	 * 
	 * @return next
	 */
	public DLinkedNode<T> getNext() {
		return next;
	}

	/**
	 * Set method for the next node of "this" LinkedQueue node.
	 * 
	 * @param next
	 */
	public void setNext(DLinkedNode<T> next) {
		this.next = next;
	}

	/**
	 * Get method for the previous node of "this" LinkedQueue node.
	 * 
	 * @return previous
	 */
	public DLinkedNode<T> getPrev() {
		return prev;
	}

	/**
	 * Set method for the previous node of "this" LinkedQueue node.
	 * 
	 * @param previous
	 */
	public void setPrev(DLinkedNode<T> prev) {
		this.prev = prev;
	}

}
