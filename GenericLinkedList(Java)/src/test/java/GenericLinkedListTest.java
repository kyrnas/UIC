import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;


public class GenericLinkedListTest {
	
	private GenericLinkedList<Integer> l1;
	private GenericLinkedList<Double> l2;
	private GenericLinkedList<String> l3;
	
	@BeforeEach
	void init() {
		l1 = new GenericLinkedList<>(5);
		l2 = new GenericLinkedList<>(6.5);
		l3 = new GenericLinkedList<>("Apples");
	}
	
	@Test
	void defaultConstructorTest1(){
		assertEquals(1, l1.size(), "unexpected initialization size");

		assertEquals(5, l1.get(0), "unexpeced 0th value after initialization");
	}
	
	@Test
	void defaultConstructorTest2(){
		assertEquals(1, l2.size(), "unexpected initialization size");

		assertEquals(6.5, l2.get(0), "unexpeced 0th value after initialization");
	}
	
	@Test
	void defaultConstructorTest3(){
		assertEquals(1, l3.size(), "unexpected initialization size");

		assertEquals("Apples", l3.get(0), "unexpeced 0th value after initialization");
	}
	
	@Test
	void addFirstTest1() {
		l1.addFirst(1);

		assertEquals(2, l1.size(), "unexpected size after addFirst()");

		
		assertEquals(1, l1.get(0), "unexpeced 0th value after addFirst()");
	}
	
	@Test
	void addFirstTest2() {
		l2.addFirst(2.7);
		l2.addFirst(7.5);
		
		assertEquals(3, l2.size(), "unexpected size after addFirst()");
		
		assertEquals(7.5, l2.get(0), "unexpeced 0th value after addFirst()");
		assertEquals(2.7, l2.get(1), "unexpeced 1th value after addFirst()");
		assertEquals(6.5, l2.get(2), "unexpeced 1th value after addFirst()");
	}
	
	@Test
	void addFirstTest3() {
		l3.addFirst("Pie");
		
		assertEquals(2, l3.size(), "unexpected size after addFirst()");
		
		assertEquals("Pie", l3.get(0), "unexpeced 0th value after addFirst()");
	}
	
	@Test
	void addLastTest1() {
		l1.addLast(1);
		
		assertEquals(2, l1.size(), "unexpected size after addLast()");
		
		assertEquals(1, l1.get(1), "unexpeced 0th value after addLast()");
	}
	
	@Test
	void addLastTest2() {
		l2.addLast(2.7);
		l2.addLast(7.5);
		
		assertEquals(3, l2.size(), "unexpected size after addLast()");
		
		assertEquals(7.5, l2.get(2), "unexpeced 0th value after addLast()");
		assertEquals(2.7, l2.get(1), "unexpeced 1th value after addLast()");
	}
	
	@Test
	void addLastTest3() {
		l3.addLast("Pie");
		
		assertEquals(2, l3.size(), "unexpected size after addLast()");

		assertEquals("Pie", l3.get(1), "unexpeced 0th value after addLast()");
	}
	
	@Test
	void containsTest1() {
		l1.addFirst(4);
		l1.addFirst(3);
		l1.addFirst(2);
		l1.addFirst(1);
		l1.addFirst(0);
		
		assertEquals(true, l1.contains(5), "the list does not contain something it is supposed to");
		assertEquals(true, l1.contains(4), "the list does not contain something it is supposed to");
		assertEquals(true, l1.contains(2), "the list does not contain something it is supposed to");
		assertEquals(true, l1.contains(0), "the list does not contain something it is supposed to");
		assertEquals(false, l1.contains(6), "the list contains something it is not supposed to");
		assertEquals(false, l1.contains(7), "the list contains something it is not supposed to");
		assertEquals(false, l1.contains(-1), "the list contains something it is not supposed to");
		assertEquals(false, l1.contains(10), "the list contains something it is not supposed to");
		assertEquals(false, l1.contains(-25), "the list contains something it is not supposed to");
	}

	@Test
	void containsTest2() {
		l2.addFirst(4.0);
		l2.addFirst(3.0);
		l2.addFirst(2.0);
		l2.addFirst(1.0);
		l2.addFirst(0.0);
		
		assertEquals(true, l2.contains(6.5), "the list does not contain something it is supposed to");
		assertEquals(true, l2.contains(4.0), "the list does not contain something it is supposed to");
		assertEquals(true, l2.contains(2.0), "the list does not contain something it is supposed to");
		assertEquals(true, l2.contains(0.0), "the list does not contain something it is supposed to");
		assertEquals(false, l2.contains(6.0), "the list contains something it is not supposed to");
		assertEquals(false, l2.contains(7.0), "the list contains something it is not supposed to");
		assertEquals(false, l2.contains(-1.0), "the list contains something it is not supposed to");
		assertEquals(false, l2.contains(10.0), "the list contains something it is not supposed to");
		assertEquals(false, l2.contains(-25.0), "the list contains something it is not supposed to");
	}
	
	@Test
	void containsTest3() {
		l3.addFirst("Rasberry");
		l3.addLast("Strawberry");
		l3.addLast("Blueberry");
		
		assertEquals(true, l3.contains("Apples"), "the list does not contain something it is supposed to");
		assertEquals(true, l3.contains("Rasberry"), "the list does not contain something it is supposed to");
		assertEquals(true, l3.contains("Strawberry"), "the list does not contain something it is supposed to");
		assertEquals(true, l3.contains("Blueberry"), "the list does not contain something it is supposed to");
		assertEquals(false, l3.contains("Juice"), "the list contains something it is not supposed to");
		assertEquals(false, l3.contains("Pie"), "the list contains something it is not supposed to");
		assertEquals(false, l3.contains("The number 13"), "the list contains something it is not supposed to");
	}
	
	@Test
	void removeTest1() {
		l1.addFirst(3);
		l1.addFirst(7);
		l1.addFirst(5);
		l1.addFirst(17);
		l1.addFirst(8);
		l1.addFirst(22);
		l1.addFirst(-6);
		l1.addFirst(-10);
		
		assertEquals(9, l1.size(), "unexpected size");
		
		assertEquals(true, l1.remove(5), "something that should have been removed wasn't removed"); 
		assertEquals(true, l1.contains(5), "something that should be in the list isn't there");
		assertEquals(8, l1.size(), "unexpected size");
		
		assertEquals(true, l1.remove(5), "something that should have been removed wasn't removed"); 
		assertEquals(false, l1.contains(5), "something that shouldn't be in the list is there");
		assertEquals(7, l1.size(), "unexpected size");
		
		assertEquals(true, l1.remove(3), "something that should have been removed wasn't removed");
		assertEquals(true, l1.remove(-10), "something that should have been removed wasn't removed"); 
		assertEquals(false, l1.remove(-100), "something that shouldn't have been removed was removed"); 
		
		assertEquals(5, l1.size(), "unexpected size");
	}
	
	@Test
	void removeTest2() {
		l2.addFirst(3.0);
		l2.addFirst(7.0);
		l2.addFirst(5.0);
		l2.addLast(17.0);
		l2.addFirst(8.0);
		l2.addLast(22.0);
		l2.addFirst(-6.0);
		l2.addLast(-10.0);
		
		assertEquals(9, l2.size(), "unexpected size");
		
		assertEquals(true, l2.remove(5.0), "something that should have been removed wasn't removed"); 
		assertEquals(false, l2.contains(5.0), "something that should be in the list isn't there");
		assertEquals(8, l2.size(), "unexpected size");
		
		assertEquals(false, l2.remove(5.0), "something that should have been removed wasn't removed"); 
		assertEquals(false, l2.contains(5.0), "something that shouldn't be in the list is there");
		assertEquals(8, l2.size(), "unexpected size");
		
		assertEquals(true, l2.remove(3.0), "something that should have been removed wasn't removed");
		assertEquals(true, l2.remove(-10.0), "something that should have been removed wasn't removed"); 
		assertEquals(false, l2.remove(-100.0), "something that shouldn't have been removed was removed"); 
		
		assertEquals(6, l2.size(), "unexpected size");
	}
	
	@Test
	void removeTest3() {
		l3.addLast("Bannana");
		l3.addFirst("Cactus");
		l3.addFirst("GIANT PONY");
		l3.addLast("Documentation");
		
		assertEquals(5, l3.size(), "unexpected size");
		
		assertEquals(true, l3.remove("GIANT PONY"), "something that should have been removed wasn't removed");
		assertEquals(false, l3.contains("GIANT PONY"), "something that shouldn't be in the list is there");
		assertEquals(4, l3.size(), "unexpected size");
		
		assertEquals(false, l3.remove("GIANT PONY"), "something that shouldn't be in the list is there");
		assertEquals(4, l3.size(), "unexpected size");
		
	}
	
	@Test
	void clearTest1() {
		l1.addFirst(3);
		l1.addFirst(7);
		l1.addFirst(5);
		l1.addFirst(17);
		l1.addFirst(8);
		l1.addFirst(22);
		l1.addFirst(-6);
		l1.addFirst(-10);
		
		assertEquals(9, l1.size(), "unexpected size");
		l1.clear();
		assertEquals(0, l1.size(), "unexpected size");
	}
	
	@Test
	void clearTest2() {
		l2.addFirst(3.0);
		l2.addFirst(7.0);
		l2.addFirst(5.0);
		l2.addLast(17.0);
		l2.addFirst(8.0);
		l2.addLast(22.0);
		l2.addFirst(-6.0);
		l2.addLast(-10.0);
		
		assertEquals(9, l2.size(), "unexpected size");
		l2.clear();
		assertEquals(0, l2.size(), "unexpected size");
	}
	
	@Test
	void clearTest3() {
		l3.addLast("Bannana");
		l3.addFirst("Cactus");
		l3.addFirst("GIANT PONY");
		l3.addLast("Documentation");
		
		assertEquals(5, l3.size(), "unexpected size");
		l3.clear();
		assertEquals(0, l3.size(), "unexpected size");
	}
	
	@Test
	void getTest() {
		l1.addFirst(3);
		l1.addFirst(7);
		l1.addFirst(5);
		l1.addFirst(17);
		l1.addFirst(8);
		l1.addFirst(22);
		l1.addFirst(-6);
		l1.addFirst(-10);
		
		int[] arr = {-10, -6, 22, 8, 17, 5, 7, 3, 5};
		for(int i = 0; i < l1.size(); i++) {
			assertEquals(arr[i], l1.get(i), "invalid value at " + i);
		}
		
		assertEquals(null, l1.get(20), "supposed to be null");
	}
	
	@Test
	void setTest1() {
		l1.addFirst(3);
		l1.addFirst(7);
		l1.addFirst(5);
		l1.addFirst(17);
		
		assertEquals(17, l1.get(0), "invalid value at 0");
		assertEquals(17, l1.set(0, 100), "invalid value at 0");
		assertEquals(100, l1.get(0), "invalid value at 0");
		
		assertEquals(7, l1.get(2), "invalid value at 2");
		assertEquals(7, l1.set(2, 55), "invalid value at 2");
		assertEquals(55, l1.get(2), "invalid value at 2");
		
		assertEquals(5, l1.get(4), "invalid value at 4");
		assertEquals(5, l1.set(4, 555), "invalid value at 4");
		assertEquals(555, l1.get(4), "invalid value at 4");
		
		assertEquals(null, l1.set(50, 33), "index should be out of reach");
	}
	
	@Test
	void removeHeadTest() {
		l1.addFirst(3);
		
		assertEquals(3, l1.removeHead(), "wrong value removed from the front");
		assertEquals(5, l1.removeHead(), "wrong value removed from the front");
		
		assertEquals(0, l1.size(), "wrong size");
		assertEquals(null, l1.removeHead(), "should be null since nothing to remove");
		assertEquals(0, l1.size(), "wrong size");
	}
	
	@Test
	void removeTailTest() {
		l1.addFirst(3);
		assertEquals(5, l1.removeTail(), "wrong value removed from the tail");
		assertEquals(3, l1.removeTail(), "wrong value removed from the tail");
		
		assertEquals(0, l1.size(), "wrong size");
		assertEquals(null, l1.removeTail(), "should be null since nothing to remove");
		assertEquals(0, l1.size(), "wrong size");
	}
}
