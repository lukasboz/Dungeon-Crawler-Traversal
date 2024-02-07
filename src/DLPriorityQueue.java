/**
 * @author Lukas BozInov
 * @param <T>
 */
public class DLPriorityQueue<T> implements PriorityQueueADT<T> {

	// lists the instance variables for the class
	private DLinkedNode<T> front; // front of the linked list queue
	private DLinkedNode<T> rear; // rear of the linked list queue
	private int count; // count of how many nodes the linked list queue has

	/**
	 * Creates a constructor for a DLPriorityQueue
	 */
	public DLPriorityQueue() {
		this.count = 0; // initializes the number of nodes in the linked list queue
		this.front = this.rear = null; // initializes both the front and rear of the linked list queue
	}

	/**
	 * This class adds a node to the PriorityQueue as a linked list implementation.
	 * The list is sorted based on priority once a node is added.
	 * 
	 * @param dataItem
	 * @param priority
	 */
	public void add(T dataItem, double priority) {
		DLinkedNode<T> givenNode = new DLinkedNode<T>(dataItem, priority); // takes in the node given when the method is
																			// called
		DLinkedNode<T> currentNode; // creates another node that will soon be referenced to front
		if (isEmpty()) { // if the queue is empty
			front = givenNode; // set the given node to the front of the queue

		} else if (givenNode.getPriority() < front.getPriority()) { // if the given node's priority is less than the
																	// front node's priority, the given node is put to
																	// the front
			givenNode.setNext(front); // set the next pointer of the given node to the front of the queue
			givenNode.getNext().setPrev(givenNode); // set the previous pointer of the node after the given node to the
													// given node
			front = givenNode; // set the front to the given node

		} else { // in every other case
			currentNode = front; // currentNode is set to the front of the queue

			// we iterate through the queue until the the node after the current node's
			// priority becomes equal or greater than the priority of the given node
			while (currentNode.getNext() != null && currentNode.getNext().getPriority() < givenNode.getPriority()) {
				currentNode = currentNode.getNext();
			}

			givenNode.setNext(currentNode.getNext()); // set the next pointer of the given node to the node AFTER the
														// current node

			// if there is a node after the current node
			if (currentNode.getNext() != null) {
				givenNode.getNext().setPrev(givenNode); // the node after the given node's previous pointer gets set to
														// the given node
			}

			currentNode.setNext(givenNode); // set the next pointer of the current node to the given node
			givenNode.setPrev(currentNode); // set the previous pointer of the given node to the current node

		}
		count++; // increase the count (size counter) of the queue

	}

	/**
	 * This class removes the front node from a PriorityQueue as a linked list
	 * implementation. Lowest priority is at the front of the queue.
	 * 
	 * @exception EmptyPriorityQueueException
	 */
	public T removeMin() throws EmptyPriorityQueueException {

		// if the queue is empty throw the empty queue exception
		if (isEmpty()) {
			throw new EmptyPriorityQueueException("empty queue"); // throw the exception + message
		}

		DLinkedNode<T> nodeBehindFront = front.getNext(); // create a new node that refers to the node AFTER the front
															// node
		T frontDataItem = front.getDataItem(); // store the front data item in a generic variable
		front = nodeBehindFront; // set the front variable to be the node AFTER the front node

		count--; // decrease the count because we've removed a node
		return frontDataItem; // return the data item of the removed front node
	}

	/**
	 * This class updates the priority for a node. This also sorts nodes again if
	 * needed.
	 *
	 * @param dataItem
	 * @param newPriority
	 */
	public void updatePriority(T dataItem, double newPriority) throws InvalidElementException {
		DLinkedNode<T> currentNode = front; // create a node that references the front node

		boolean found = false; // set the "found" variable to false (checking if the element in the list exists

		if (isEmpty()) { // if the list is empty, the element can't possibly be in it, so we throw the
							// exception
			throw new InvalidElementException("data item not in queue");
		}

		// go through the entire queue
		while (currentNode != null) {

			// if the element is in the list
			if (currentNode.getDataItem() == dataItem || currentNode.getDataItem().equals(dataItem)) {
				found = true; // change found to true

				// remove the item that matches data types (to be added in the correct order
				// later)

				if (currentNode.getNext() != null && currentNode.getPrev() != null) { //if both the node before and after the current one is not null
					currentNode.getNext().setPrev(currentNode.getPrev()); //set the previous pointer of the next node to the previous node of the current node
					currentNode.getPrev().setNext(currentNode.getNext()); //set the previous node's next pointer to the current node's next node
				} else if (currentNode.getNext() == null && currentNode.getPrev() != null) { //if the node after the current one is null, and the node previous to the current one isn't
					currentNode.getPrev().setNext(currentNode.getNext()); //set the previous node's next pointer to the current node's next node
					
				} else if (currentNode.getNext() != null && currentNode.getPrev() == null) { //if the node after the current one isn't null, and the node previous to the current one is
					currentNode.getNext().setPrev(currentNode.getPrev()); //set the previous pointer of the next node to the previous node of the current node
					front = currentNode.getNext();

				} else {
					removeMin(); //if all else isn't true, remove the front since that's the only option left
				}
				break; // break out of the if statements and loop
			}
			currentNode = currentNode.getNext(); // continue iterating

		}

		if (found == false) { // if the element isn't found, throw the exception
			throw new InvalidElementException("data item not in queue");
		}

		add(dataItem, newPriority); // add back the item that was deleted

	}

	/**
	 * Checks if the LinkedQueue is empty. If it is, return true. If not, return
	 * false.
	 */
	public boolean isEmpty() {
		if (front == null && rear == null) {
			return true;
		} else
			return false;
	}

	/**
	 * Returns the count of nodes in the LinkedQueue.
	 */
	public int size() {
		return count;
	}

	/**
	 * Gives the string representation of the LinkedQueue. Goes from the front to
	 * the back until there are no more nodes to print.
	 */
	public String toString() {
		String finalString = "";

		DLinkedNode<T> curr = front;
		while (curr != null) {
			finalString += curr.getDataItem();
			curr = curr.getNext();
		}
		return finalString;

	}

	/**
	 * Gets the very last element in the LinkedQueue.
	 * 
	 * @return rear
	 */
	public DLinkedNode<T> getRear() {
		return rear;
	}

}