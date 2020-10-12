import java.util.Iterator;

/**
 * <h1>ReverseGLLIterator</h1>
 * Reverse Iterator for GenericLinkedList.
 * Iterates from tail to head.
 * 
 * @author Kyr Nastahunin (knasta2, knasta2@uic.edu)
 *
 * @since 9/20/2020
 */

public class ReverseGLLIterator<T> implements Iterator<T>{
	
	private GenericLinkedList<T> list;
	private int index;
	
	public ReverseGLLIterator(GenericLinkedList<T> param) {
		list = param;
		index = list.size()-1;
	}

	/**
	 * Returns true if the iteration has more elements
	 * 
	 * @return boolean True if has more elements
	 */
	@Override
	public boolean hasNext() {
		if(index <= 0) {
			return false;
		}
		else {
			return true;
		}
	}

	/**
	 * Returns the next element in the iteration.
	 * 
	 * @return next element
	 */
	@Override
	public T next() {
		if(this.hasNext()) {
			T result = list.get(index);
			index--;
			return result;
		}
		else {
			return null;
		}
	}

}
