import java.util.Iterator;
import java.util.ListIterator;

/**
 * <h1>GenericLinkedList</h1>
 * A generic liked list data structure.
 * Can be used to store any objects.
 * Also supports Iterators.
 * 
 * @author Kyr Nastahunin (knasta2, knasta2@uic.edu)
 *
 * @since 9/17/2020
 */
public class GenericLinkedList<T> implements Iterable<T>{
	
	private Node<T> head;
	private int length;
	
	private class Node<T>{
		T data;
		Node<T> next;
		Node<T> prev;
		
		public Node(T val) {
			this.data = val;
			this.next = null;
			this.prev = null;
		}
	}
	
	/**
	 * Initializes the head with val
	 * @param val The object to initialize with
	 */
	public GenericLinkedList(T val) {
		this.head = new Node<>(val);
		this.head.prev = null;
		this.length = 1;
	}
	
	/**
	 * Adds object "e" to the front of the linked list
	 * @param e The object that will be added to the list
	 */
	public void addFirst(T e) {
		Node<T> n = new Node<>(e); // create a new node to insert
		head.prev = n; // assign old head's prev to be new head
		n.next = head; // assign new node's next as old head
		n.prev = null; // assign new node's prev as null
		head = n; // assign new node as the head
		
		length++; // increase the size
	}
	
	/**
	 * Adds object "e" to the end of the linked list
	 * @param e The object that will be added to the list
	 */
	public void addLast(T e) {
		// create a new node to insert
		Node<T> n = new Node<>(e);
		// if the list is empty add at the front
		if(length == 0) { 
			// connect n
			head = n;
			n.prev = null;
			n.next = null;
		}
		else {
			Node<T> cur = head;
			// reach the end of the list
			while(cur.next != null) { 
				cur = cur.next;
			}
			// connect n
			cur.next = n;
			n.prev = cur;
			n.next = null;
		}
		length++;
	}
	
	/**
	 * Returns the size of linked list
	 * @return (int) This is the size of the linked list
	 */
	public int size() {
		return length;
	}
	
	/**
	 * Finds if the linked list contains an object "e"
	 * @param e The object to look for
	 * @return (boolean) This returns "true" if the object exists in the list or "false" if it's not in the list
	 */
	public boolean contains(T e) {
		// go through the list until we reach the end
		Node<T> cur = head; 
		while(cur != null) {
			//System.out.println(cur.data);
			// if we found the element >> return true
			if(cur.data.equals(e)) {
				return true;
			}
			cur = cur.next;
		}
		// we reached the end, e is not in the list
		return false;
	}
	
	/**
	 * Removes first occurrence of the specified element
	 * and returns true or returns false if the element does not exist.
	 * @param e Element to be removed
	 * @return (boolean) True if removed. False if the element does not exist.
	 */
	public boolean remove(T e) {
		// go through the list until we reach the end
		// or until we reach the element we want to remove
		Node<T> cur = head;
		while(cur != null) {
			if(cur.data.equals(e)) {
				break;
			}
			cur = cur.next;
		}
		if(cur == null) { // reached the end of the list without finding the element
			return false;
		}
		else if(cur == head) { // if removing the head the head has to be updated
			head = head.next;
			length--;
			return true;
		}
		else {
			Node<T> nextCur = cur.next;
			Node<T> prevCur = cur.prev;
			if(nextCur != null) {
				nextCur.prev = prevCur; // set next's prev to prev
			}
			
			if(prevCur != null) {
				prevCur.next = nextCur; // set prev's next to next
			}
			
			length--;
			return true;
		}
	}
	
	/**
	 * Removes all elements from the list
	 */
	public void clear() {
		head = null;
		length = 0;
	}
	
	/**
	 * Returns the element at the specified index or null if 
	 * index is out of bounds
	 * @param index The index of the element
	 * @return (T) The element at specified index or null if index is out of bounds
	 */
	public T get(int index) {
		if(index >= length) {
			return null;
		}
		// go through the list until we either reach the index or the end
		Node<T> cur = head;
		for(int i = 0; i < index; i++) {
			// if reached the end return null
			if(cur == null) {
				return null;
			}
			else {
				cur = cur.next;
			}
		}
		return cur.data;
	}
	
	/**
	 * Replaces the element at specified position in the list with
	 * the specified element and return the element previously at 
	 * the specified position. Return null if index is out of bounds
	 * @param index The index in the linked list 
	 * @param element The element to insert
	 * @return (T) the element that was at the location before or 
	 * null if index is out of bounds.
	 */
	public T set(int index, T element) {
		if(index >= length) {
			return null;
		}
		// go through the list until we either reach the index or the end
		T result = null;
		Node<T> cur = head; 
		for(int i = 0; i < index; i++) {
			if(cur == null) {
				return result;
			}
			else {
				cur = cur.next;
			}
		}
		// save the current data in this index
		result = cur.data;
		// insert new data to this index
		cur.data = element;
		
		return result;
	}
	
	/**
	 * Retrieves and removes the head of the list
	 * @return (T) The head element of the list
	 */
	public T removeHead() {
		if(length == 0) {
			return null;
		}
		else {
			T result = head.data;
			head = head.next;
			length--;
			return result; 
		}
	}
	
	/**
	 * Retrieves and removes the tail of the list
	 * @return (T) the element at the tail of the list
	 */
	public T removeTail() {
		if(length == 0) {
			return null;
		}
		else {
			// go through the list until reach the end
			Node<T> cur = head;
			while(cur.next != null) {
				cur = cur.next;
			}
			// save the data to return
			T result = cur.data;
			// unlink the tail Node from its predecessor 
			Node<T> prevCur = cur.prev;
			if(prevCur != null)
				prevCur.next = null;
			cur.prev = null;
			//decrease the length
			length--;
			return result;
		}
	}
	
	/**
	 * Returns a ListIterator that starts at the 
	 * specified index
	 * 
	 * @param index The index where ListIterator will start
	 * @return ListIterator to the GLL
	 */
	public ListIterator<T> listIterator(int index){
		GLListIterator<T> result = new GLListIterator<>(this, index);
		return result;
	}
	
	/**
	 * Returns an Iterator that goes in reverse order
	 * 
	 * @return Iterator that goes in the reverse order
	 */
	public Iterator<T> descendingIterator(){
		ReverseGLLIterator<T> result = new ReverseGLLIterator<>(this);
		return result;
	}

	/**
	 * Returns an Iterator to the list
	 * 
	 * @return Iterator iterator to the list
	 */
	@Override
	public Iterator<T> iterator() {
		GLLIterator<T> result = new GLLIterator<>(this);
		return result;
	}

	/**
	 * Adds a new element at a specified index shifting the old element 
	 * that was in this index and all other element after it to the right (+1 index). 
	 * Useful for ListIterator's add
	 * 
	 * @param value value to add
	 * @param index index where to add
	 */
	public void addAt(T value, int index) {
		if(index >= length) {
			return;
		}
		else if(index == length - 1) {
			this.addLast(value);
			return;
		}
		else if(index == 0) {
			this.addFirst(value);
			return;
		}
		Node<T> cur = head;
		for(int i = 0; i < index; i++) {
			cur = cur.next;
		}
		Node<T> replacement = new Node<>(value);
		Node<T> prev = cur.prev;
		prev.next = replacement;
		replacement.prev = prev;
		replacement.next = cur;
		cur.prev = replacement;
		length++;
	}
	
	/**
	 * Removes an element at specified index.
	 * Useful for ListIterator's remove
	 * 
	 * @param index index where to remove
	 */
	public void removeAt(int index) {
		if(index >= length) {
			return;
		}
		else if(index == 0) {
			Node<T> cur = head.next;
			cur.prev = null;
			head = cur;
			length--;
			return;
		}
		Node<T> cur = head;
		for(int i = 0; i < index; i++) {
			cur = cur.next;
		}
		Node<T> n = cur.next;
		Node<T> p = cur.prev;
		
		p.next = n;
		n.prev = p;
		length--;
	}
}
