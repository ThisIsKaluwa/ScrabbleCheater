import java.util.ArrayList;
import java.util.Arrays;
import java.lang.StringBuilder;

public class Bag {

	ArrayList<String> subStrings = new ArrayList<String>();

	//Constructor that takes a String and calls createSubStrings with said String
	public Bag(String ofChars) {
		createSubStrings(ofChars);
	}

	/**
	 * takes a String  that gets split into every possible (unordered) combination of every possible length down to 2 chars
	 * @param ofChars a String to create combinations from
	 */
	private void createSubStrings(String ofChars) {

		//turns String into char array
		char[] letters = ofChars.toCharArray();
		
		//quicksort the letters as order does not matter
		Arrays.sort(letters);
		
		String toBeAdded = "";
		
		//go over every char that is currently in the array and add them to String
		for (char c : letters) {
			toBeAdded += Character.toString(c);
		}
		
		//if our Bag doesn't contain the String already and the Strings length is greater than 1
		//(as there are no one letter words), we add it to the Bag
		
		if (!subStrings.contains(toBeAdded) && toBeAdded.length() > 1) {
			subStrings.add(toBeAdded);
		}
		
		//we go over the char array and take out one different letter at a time, to get every word String length -1
		for (int i = 0; i < letters.length; i++) {
			//Using StringBuilder, because String does not have methods for deleting specific chars easily
			StringBuilder shorterString = new StringBuilder(toBeAdded);
			shorterString.deleteCharAt(i);
			
			//turn StringBuilder back to normal String and call the method recursively 
			createSubStrings(shorterString.toString());
		}
	}

	//Getter for the Bag that we create
	public ArrayList<String> getSubStrings() {
		return subStrings;
	}
	
	
}
