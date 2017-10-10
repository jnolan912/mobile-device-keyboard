package tests;

import org.junit.Test;

import keyboard.AutocompleteProvider;

public class AutocompleteTests {

	// Test with large input and test the time to get words
	@Test
	public void test() {
		System.out.println("Test 1: Large Input");
		AutocompleteProvider a = new AutocompleteProvider(3);
		a.trainFromFile("./inputs/constitution.txt");
		a.trainFromFile("./inputs/declaration_of_independence.txt");
		a.trainFromFile("./inputs/federalist_papers.txt");
		System.out.println("Start time (ms): " + System.currentTimeMillis());
		System.out.println(a.getWords("a"));
		System.out.println(a.getWords("b"));
		System.out.println(a.getWords("c"));
		System.out.println(a.getWords("d"));
		System.out.println(a.getWords("e"));
		System.out.println(a.getWords("z"));
		System.out.println(a.getWords("agr"));
		System.out.println(a.getWords(""));
		System.out.println("End time (ms): " + System.currentTimeMillis());
		System.out.println();
	}
	
	// Test with sample input from problem description
	@Test
	public void test2() {
		System.out.println("Test 2: Provided from Description");
		AutocompleteProvider a = new AutocompleteProvider(null);
		a.train("The third thing that I need to tell you is that this thing does not think thoroughly.");
		System.out.println(a.getWords("thi"));
		System.out.println(a.getWords("nee"));
		System.out.println(a.getWords("th"));
	}

}
