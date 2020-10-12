import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Iterator;
import java.util.ListIterator;

//import java.util.LinkedList;


public class BigGLLTest {
	private GenericLinkedList<Integer> list;
	private GenericLinkedList<Integer> empty;
	
	@BeforeEach
	void init() {
		list = new GenericLinkedList<>(3);
		list.addFirst(2);
		list.addFirst(1);
		list.addLast(4);
		list.addLast(5);
		// 1->2->3->4->5
	}
	
	@Test
	void testAllFunctions() {
		assertEquals(5, list.size(), "invalid size");
		assertEquals(true, list.contains(1), "element not found in the GLL");
		assertEquals(true, list.contains(3), "element not found in the GLL");
		assertEquals(true, list.contains(5), "element not found in the GLL");
		assertEquals(false, list.contains(10), "element not found in the GLL");
		
		assertEquals(true, list.remove(4), "couldn't remove something that supposed to");
		assertEquals(true, list.remove(5), "couldn't remove something that supposed to");
		
		assertEquals(null, list.set(4, 10), "unexpected behavior in set function");
		
		assertEquals(null, list.get(8), "unexpected behaviour in get function");
		
		assertEquals(1, list.removeHead(), "unexpected value");
		assertEquals(3, list.removeTail(), "unexpected value");
		
		assertEquals(1, list.size(), "unexpeted size");
	}
	
	@Test
	void testEmpty() {
		empty = new GenericLinkedList<>(0);
		empty.clear();
		assertEquals(0, empty.size(), "size not 0");
		assertEquals(null, empty.removeTail(), "unexpected result");
		assertEquals(null, empty.removeHead(), "unexpected result");
		assertEquals(null, empty.set(5, 10), "unexpected result");
		assertEquals(null, empty.get(5), "unexpected result");
	}
	
	@Test
	void capacityAndIteratorTest() {
		int size = 1001;
		int arr[] = new int[size];
		list.clear();
		for(int i = 0; i < size; i++) {
			arr[i] = i;
			list.addLast(i);
		}
		assertEquals(size, list.size(), "unexpected size");
		Iterator<Integer> iter = list.iterator();
		for(int i = 0; i < size; i++) {
			assertEquals(arr[i], iter.next(), "wrong behaviour");
		}
	}
	
	@Test
	void iteratorTest() {
		Iterator<Integer> iter = list.iterator();
		int noOfRuns = 0;
		int arr[] = {1, 2, 3, 4, 5};
		while(iter.hasNext()) {
			assertEquals(arr[noOfRuns], iter.next(), "iterator doesn't match");
			noOfRuns++;
		}
	}
	
	@Test
	void reverseIteratorTest() {
		Iterator<Integer> iter = list.descendingIterator();
		int noOfRuns = 0;
		int arr[] = {5, 4, 3, 2, 1};
		while(iter.hasNext()) {
			assertEquals(arr[noOfRuns], iter.next(), "iterator doesn't match");
			noOfRuns++;
		}
	}
	
	@Test
	void listIteratorTest() {
		ListIterator<Integer> iter = list.listIterator(2); // 1 2^3 4 5
		assertEquals(true, iter.hasPrevious(), "iterator is supposed to have previous but it doesn't");
		assertEquals(true, iter.hasNext(), "iterator is supposed to have next but it doesn't");
		assertEquals(2, iter.previous(), "iterator did not return expected value");
		// 1^2 3 4 5
		assertEquals(1, iter.nextIndex(), "unexpected index");
		assertEquals(2, iter.next(), "iterator did not return expected value");
		// 1 2^3 4 5
		assertEquals(2, iter.nextIndex(), "unexpected index");
		assertEquals(1, iter.previousIndex(), "unexpected index");
	}
	
	@Test
	void forEachLoopTest() {
		int arr[] = {1, 2, 3, 4, 5};
		int i = 0;
		for(int var : list) {
			assertEquals(arr[i], var, "unexpeced behaviour in for each loop");
			i++;
		}
	}
	
	/*
	@Test
	void listIteratorTest2() {
		ListIterator<Integer> iter = list.listIterator(2); // 1 2^3 4 5
		LinkedList<Integer> ll = new LinkedList<>();
		
		ll.add(1);
		ll.add(2);
		ll.add(3);
		ll.add(4);
		ll.add(5);
		
		ListIterator<Integer> iter2 = ll.listIterator(2);
		
		assertEquals(iter2.previous(), iter.previous(), "iterator did not return expected value");
		// 1^2 3 4 5
		assertEquals(iter2.nextIndex(), iter.nextIndex(), "unexpected index");
		assertEquals(iter2.next(), iter.next(), "iterator did not return expected value");
		// 1 2^3 4 5
		assertEquals(iter2.nextIndex(), iter.nextIndex(), "unexpected index");
		assertEquals(iter2.previousIndex(), iter.previousIndex(), "unexpected index");
		
		
		// TESTING EXPECTED LIST ITERATOR BEHAVIOUR
		while(iter2.hasNext()) {
			iter2.next();
		}
		iter2.add(6);
		iter2.add(7);
		
		System.out.println("FOR EACH LOOP");
		for(int var : ll) {
			System.out.println(var);
		}
		
		System.out.println("LIST ITERATOR");
		while(iter2.hasPrevious()) {
			iter2.previous();
		}
		while(iter2.hasNext()) {
			System.out.println(iter2.next());
		}
		// PASS BY REFERENCE O.o !!!
	}*/
}
