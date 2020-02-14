import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import javax.swing.JOptionPane;

public class Dictionary {

	private HashMap<Integer, ArrayList<String>> dictionary = new HashMap<Integer, ArrayList<String>>();

	public static void main(String[] args) throws Exception {
		Dictionary ourDictionary = new Dictionary();

//		make a random String out of the chars in scrabble
		String yourLetters = JOptionPane.showInputDialog("Enter your letters without spaces");
//		create a Bag for all the substrings of our randomString
		Bag bag = new Bag(yourLetters);
		ArrayList<String> randomBag = bag.getSubStrings();
		System.out.println("Possible words out of " + yourLetters + " are: ");
		String empty = "There are no words for ";
//		check for each string in our bag, whether it has an anagram
		for (String string : randomBag) {
			String toBeLookedUp = string;
			ArrayList<String> foo = ourDictionary.lookUp(toBeLookedUp);
			if (foo.isEmpty()) {
				empty += string + ", ";
			}
//			print all the anagrams
			for (String strings : foo) {
				System.out.println(strings);
			}
		}
		System.out.println(empty);
	}

	private Dictionary() throws Exception {

		InputStream stream = Dictionary.class.getResourceAsStream("/AllLetterDictionary.txt");

		/**
		 * Fills the dictionary
		 */
		dictionary = hashFunction(singleWords(stream));

	}

	private ArrayList<String> singleWords(InputStream stream) throws Exception {

		ArrayList<String> wordList = new ArrayList<String>();

		int i;
		String word = "";
		while ((i = stream.read()) != -1) {
			Character position = (char) i;
			if (Character.isLetter(position)) {
				word += position;
			} else if (!(Character.isLetter(position))) {
				if (!word.isEmpty()) {
					wordList.add(word.toLowerCase());
					word = "";
				}
			}
		}
		stream.close();
		return wordList;
	}

	private HashMap<Integer, ArrayList<String>> hashFunction(ArrayList<String> words) {

		HashMap<Integer, ArrayList<String>> tempMap = new HashMap<Integer, ArrayList<String>>();

		for (String string : words) {
			char[] letterArray = string.toCharArray();

			int key = modKey(letterArray);

			if (!tempMap.containsKey(key)) {
				tempMap.put(key, new ArrayList<String>());
				tempMap.get(key).add(string);
			} else {
				tempMap.get(key).add(string);
			}
		}
		return tempMap;
	}

	private ArrayList<String> lookUp(String s) {
		char[] letters = s.toLowerCase().toCharArray();
		int key = modKey(letters);

		ArrayList<String> anagram = new ArrayList<String>();

		ArrayList<String> lookedUpStrings = dictionary.get(key);

//		only do this if there is a key in the dictionary (is not necessarily an anagram)
		if (lookedUpStrings != null) {
			for (String string : dictionary.get(key)) {
				if (isPermutation(s, string)) {
					anagram.add(string);
				}
			}
		}
//		is empty if there are no anagrams for a given string
		return anagram;
	}

	private boolean isPermutation(String word, String perm) {
		char[] wordArray = word.toCharArray();
		Arrays.sort(wordArray);
		char[] permArray = perm.toCharArray();
		Arrays.sort(permArray);

		for (int i = 0; i < wordArray.length; i++) {
			if (permArray[i] != wordArray[i]) {
				return false;
			}
		}
		return true;
	}

	private int modKey(char[] letters) {
//		Array with the int value of the alphabet position for each char in the string
		int[] letterInt = new int[letters.length];
		for (int i = 0; i < letterInt.length; i++) {
			int y = (int) letters[i];
			letterInt[i] = y - 96;
		}
//		sort the letters with quicksort
		Arrays.sort(letterInt);

//		needs to be a long, because the
		long keyLong = 0;

//		needed a second index for the for-loop
		int i = 0;

//		key += letter value * 27^j
//		j is an index that goes backwards
		for (int j = letterInt.length - 1; j >= 0; j--) {
			long temp = (long) (letterInt[i] * Math.pow(27, j));
			keyLong += temp;
			i++;
		}
		

//		casting the key back into an int
		return (int) (keyLong % 76001);
	}

}
