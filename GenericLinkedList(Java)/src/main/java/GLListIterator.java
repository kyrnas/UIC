import java.util.ListIterator;

/**
 * <h1>GLListIterator</h1>
 * List-Iterator for GenericLinkedList
 * 
 * @author Kyr Nastahunin (knasta2, knasta2@uic.edu)
 *
 * @since 9/20/2020
 */


public class GLListIterator<T> implements ListIterator<T>{
	
	private GenericLinkedList<T> list;
	private int cursor;
	private int lastReturned; // this will keep track of what was returned the previous time (next or previous). Used in remove()
	
	public GLListIterator(GenericLinkedList<T> param, int index) {
		list = param;
		cursor = index - 1;
		lastReturned = 0;
	}

	/**
	 * Returns true if this list iterator has more elements 
	 * when traversing the list in the forward direction
	 * 
	 * @return boolean True if the list has next. Else false
	 */
	@Override
	public boolean hasNext() {
		if(cursor+1  >= list.size()) { // size()-1 ?!
			return false;
		}
		else {
			return true;
		}
	}

	/**
	 * Returns the next element in the list and advances the cursor position
	 * 
	 * @return T the next object in the list
	 */
	@Override
	public T next() {
		if(this.hasNext()) {
			cursor++;
			lastReturned = 1;
			return list.get(cursor);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns true if this list iterator has more elements 
	 * when traversing the list in the reverse direction.
	 * 
	 * @return boolean True if has previous. Else false.
	 */
	@Override
	public boolean hasPrevious() {
		if(cursor  <= -1) {
			return false;
		}
		else {
			return true;
		}
	}

	/**
	 * Returns the previous element in the list and moves the cursor position backwards.
	 * 
	 * @return T the previous object in the list
	 */
	@Override
	public T previous() {
		if(this.hasPrevious()) {
			T result = list.get(cursor);
			cursor--;
			lastReturned = -1;
			return result;
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the index of the element that would be returned by a subsequent call to next().
	 * 
	 * @return int Index of the next element
	 */
	@Override
	public int nextIndex() {
		if(cursor == list.size()-1) {
			return list.size();
		}
		else {
			return cursor+1;
		}
	}

	/**
	 * Returns the index of the element that would 
	 * be returned by a subsequent call to previous().
	 * 
	 * @return int Index of the previous element
	 */
	@Override
	public int previousIndex() {
		return cursor;
	}

	
	
	@Override
	public void remove() {
		if(lastReturned == 0) {
			return;
		}
		else if(lastReturned == 1) {
			list.removeAt(cursor);
		}
		else {
			list.removeAt(cursor+1);
		}
		lastReturned = 0;
	}

	@Override
	public void set(T e) {
		if(lastReturned == 0) {
			return;
		}
		else if(lastReturned == 1) {
			list.set(cursor, e);
		}
		else {
			list.set(cursor+1, e);
		}
	}

	
	
	@Override
	public void add(T e) {
		if(list.size() == 0) {
			list.addFirst(e);
		}
		else {
			list.addAt(e, cursor+1);
		}
		cursor++;
		lastReturned = 0;
	}

}
