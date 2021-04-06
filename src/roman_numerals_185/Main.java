package roman_numerals_185;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Main {

    static HashMap<Character, Integer> romanOfChar = new HashMap<>(); // Contains all the roman letters with their corresponing values.

    static int solvable = 0; // For the arabic encoding --> 0 = impossible, 1 = valid, >=2 = ambiguous.
    static boolean[] encodings = new boolean[10]; // The possible values for each character in arabic encoding (0 - 9).
    static int[] values = new int[90]; // Handling of the different values assigned to unique characters (0 - 9)
    static boolean[] startingCharsValues = new boolean[90]; // Used for the detection of beginning characters of each input-part.

    // Init HashMap so that roman letters correspond to their rightful values.
    public static void initRomanOfChar() {
        romanOfChar.put('I', 1);
        romanOfChar.put('X', 10);
        romanOfChar.put('C', 100);
        romanOfChar.put('M', 1000);

        romanOfChar.put('V', 5);
        romanOfChar.put('L', 50);
        romanOfChar.put('D', 500);
    }

    // Method for converting a string of roman letters to its number representation.
    public static int toRoman(String roman) {
        boolean[] processed = new boolean[roman.length()];
        int result = 0;

        for (int i = 0; i < roman.length(); i++) {
            if (!processed[i]) {
                if (i < roman.length() - 1) { // If current element is not the last element
                    if (romanOfChar.get(roman.charAt(i + 1)) > romanOfChar.get(roman.charAt(i))) { // If element to right is bigger than current
                        result += romanOfChar.get(roman.charAt(i + 1)) - romanOfChar.get(roman.charAt(i)); // Subtract bigger number with smaller number
                        processed[i] = true;
                        processed[i + 1] = true;
                    } else {
                        result += romanOfChar.get(roman.charAt(i));
                        processed[i] = true;
                    }
                } else { // If last element, just add the value of this letter to the result and return.
                    result += romanOfChar.get(roman.charAt(i));
                    processed[i] = true;
                }
            }
        }
        return result;
    }

    // Recursive-method with inspiration from [3.2]: https://www.baeldung.com/java-combinations-algorithm
    // Tries all possible assignments of values to the unique characters of the input, and calculates them in order to find
    // Whether they are possible arabic encodings. Uses the char-values of the unique characters for this purpose.
    public static void toArabicEncoding(int charNr, String[] parts, ArrayList<Character> uniqueInputChars) {

        // If-statement used for the recursive calls. Once all values of the unique characters have been set, we can start calculating this possible combination.
        if (charNr == uniqueInputChars.size()) {
            StringBuilder partBuilder = new StringBuilder(); // For correct ordering of each part with the currently assigned values.
            String currentValueAssigned;

            // Calculating the the currently assigned values for each unique character in all three parts. Each part is separated with "+"-sign in the StringBuilder.
            for (int i = 0; i < parts.length; i++) {
                for (int pos = 0; pos < parts[i].length(); pos++) {
                    currentValueAssigned = String.valueOf(values[parts[i].charAt(pos)]);
                    partBuilder.append(currentValueAssigned);
                }
                if (i < 2) {
                    partBuilder.append("+");
                }
            }

            // Extract the arabic encoding with their assigned values, and test if there is a valid solution.
            String[] possibleValuesOfParts = partBuilder.toString().split("[+]");
            int arabicPart1 = Integer.parseInt(possibleValuesOfParts[0]);
            int arabicPart2 = Integer.parseInt(possibleValuesOfParts[1]);
            int arabicPart3 = Integer.parseInt(possibleValuesOfParts[2]);

            // If the addition of part 1 and part 2 equals to part 3 (with the current assignments) --> We have found + 1 solution.
            if (arabicPart1 + arabicPart2 == arabicPart3) {
                solvable++;
            }

            return; // Return the recursive call.
        }


        // Assigning the different possible values of the unique characters.
        int starting = 0;
        int thisChar = uniqueInputChars.get(charNr); // Holds the char-value of this char.
        boolean beginningChar = startingCharsValues[thisChar];

        if (beginningChar) { // If this character is beginning. Can not start at 0 if it is a starting character.
            starting = 1;
        }

        for (int value = starting; value <= 9; value++) {
            if (!(solvable >= 2)) { // If we have reached the "ambiguous" status, there is no need to do any more calculations.
                if (!encodings[value]) {
                    encodings[value] = true;
                    values[thisChar] = value;

                    // Recursive call. Increments the current unique character. By recursion, this will always allow for
                    // The unique character at the last position to increment over all free available slots in values[].
                    // Once it reaches 9, one of the other characters is incremented, and this last position character
                    // Tries all possible positions again --> All possible combinations are tried!
                    toArabicEncoding(charNr + 1, parts, uniqueInputChars);
                    encodings[value] = false;
                }
            }
        }
    }

    public static void main(String[]args) throws IOException {
        initRomanOfChar();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input;
        String romanRes;
        String arabicRes;

        while (!(input = br.readLine()).equals("#")) {
            String[] parts = input.split("[+=]");

            if (toRoman(parts[0]) + toRoman(parts[1])  == toRoman(parts[2])) {
                romanRes = "Correct ";
            } else {
                romanRes = "Incorrect ";
            }

            // Reset value for every input.
            solvable = 0;

            // Making the characters from the input unique by running them through a hashset, which cannot contain duplicates.
            // What is left are non-repeating chars from the input.
            HashSet<Character> nonRepeatingChars = new HashSet<>();
            for (String part : parts) {
                for (int j = 0; j < part.length(); j++) {
                    nonRepeatingChars.add(part.charAt(j));
                }
            }
            // Allow for proper indexing by converting the hashset into an ArrayList.
            ArrayList<Character> uniqueInputChars = new ArrayList<>(nonRepeatingChars);

            // Array with all 3 starting chars
            char[] allStartingChars = new char[3];
            for (int i = 0; i < allStartingChars.length; i++) {
                allStartingChars[i] = parts[i].charAt(0);
            }

            // The ASCII representation of the first character of each part is set to true.
            // --> Used for marking the starting/first chars of the strings, which cannot be 0 in the arabic encoding.
            // Also removes the possibility of a single char becoming 0.
            for (char charValue : allStartingChars) {
                startingCharsValues[charValue] = true;
            }

            // Calculate the different possible values that each unique character can take.
            toArabicEncoding(0, parts, uniqueInputChars);

            // Resetting values again.
            values = new int[90];
            startingCharsValues = new boolean[90];

            // Conclusion from the arabic encoding.
            if (solvable == 0) {
                arabicRes = "impossible";
            } else if (solvable == 1) {
                arabicRes = "valid";
            } else {
                arabicRes = "ambiguous";
            }

            System.out.println(romanRes + arabicRes);
        }
    }
}
