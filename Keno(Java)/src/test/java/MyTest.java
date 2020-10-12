import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MyTest {

	@Test
	void test1() {
		int example = GameManager.generateRandomNumber();
		boolean withinRange = false;
		if(example < 81 && example > 1) {
			withinRange = true;
		}
		assertEquals(true, withinRange, "number not in specified range");
	}

	@Test
	void test2() {
		int example = GameManager.generateRandomNumber();
		boolean withinRange = false;
		if(example < 81 && example > 0) {
			withinRange = true;
		}
		assertEquals(true, withinRange, "number not in specified range");
	}
	
	@Test
	void test3() {
		int example = GameManager.generateNumberIn(2, 8);
		boolean withinRange = false;
		if(example < 9 && example > 1) {
			withinRange = true;
		}
		assertEquals(true, withinRange, "number not in specified range");
	}
	
	@Test
	void test4() {
		int example = GameManager.generateNumberIn(50, 100);
		boolean withinRange = false;
		if(example < 101 && example > 49) {
			withinRange = true;
		}
		assertEquals(true, withinRange, "number not in specified range");
	}
	
	@Test
	void test5() {
		int example = GameManager.generateNumberIn(18, 40);
		boolean withinRange = false;
		if(example < 41 && example > 17) {
			withinRange = true;
		}
		assertEquals(true, withinRange, "number not in specified range");
	}
	
	@Test
	void test6() {
		ArrayList<Integer> arr = GameManager.generateNumbers(20);
		assertEquals(20, arr.size(), "invalid size");
	}
	
	@Test
	void test7() {
		ArrayList<Integer> arr = GameManager.generateNumbers(77);
		assertEquals(77, arr.size(), "invalid size");
	}
	
	@Test
	void test8() {
		ArrayList<Integer> arr = GameManager.generateNumbers(77);
		for(int i=0; i < arr.size(); i++) {
			assertNotEquals(null, arr.get(i), "invalid initialization");
		}
	}
	
	@Test
	void test9() {
		ArrayList<Integer> arr = GameManager.generateNumbers(3);
		for(int i=0; i < arr.size(); i++) {
			assertNotEquals(null, arr.get(i), "invalid initialization");
		}
	}
	
	@Test
	void test10() {
		ArrayList<Integer> arr = GameManager.generateNumbers(0);
		assertEquals(0, arr.size(), "invalid size");
	}
	
	// there is no more tests to write as those are the only methods that don't involve modifying the UI
}
