package decoding_edsac_data_5803;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Locale;

public class Main {

    public static void main(String[]args) throws IOException {
        Main p = new Main();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int set = 0;
        int setCounter = 0;
        boolean definedNumberOfSets = false;

        String input = "";
        StringBuilder binaryBuilder = new StringBuilder("");

        String[] choppedInput;
        HashMap<String, String> mapOfAlphabet = new HashMap<String, String>();
        p.initHashMap(mapOfAlphabet);

        String c = ""; int d = 0; String s = "";
        String c_inBinary = ""; String d_inBinary = ""; String s_inBinary = "";

        // First part of the input is always an integer defining the amount of data sets that will follow.
        // Save this integer to "set".
        while (!definedNumberOfSets) {
            set = Integer.parseInt(br.readLine());
            setCounter++;
            definedNumberOfSets = true;
        }

        // While our current counter is less than the total amount of sets that the input will consist of.
        while (setCounter <= set) {

            input = br.readLine();
            choppedInput = input.split(" ");

            // We know that input consists of 3 parts --> 'c_d_s'
            for (int i = 1; i <= 3; i++) {
                if (i == 1) {
                    c = choppedInput[i];

                    // Get the value of part 'c' from a hashmap which returns the binary representation of a letter
                    // in the EDSAC alphabet.
                    c_inBinary = mapOfAlphabet.get(c);
                }
                else if (i == 2) {
                    d = Integer.parseInt(choppedInput[i]);
                    d_inBinary = Integer.toBinaryString(d); // Convert decimal to its binary representation.
                    d_inBinary = p.convertToElevenBit(d_inBinary); // Convert it into 11-bit representation.
                }
                else {
                    // Last part of the input is either F or D.
                    s = choppedInput[i];
                    if (s.equals("F")) { // If "F", then the final number will be a 0.
                        s_inBinary = "0";
                    } else if (s.equals("D")) { // Else it will be a 1.
                        s_inBinary = "1";
                    }
                }
            }

            // Our string builder gets assembled with the help of the three parts of the input, which are now in binary.
            binaryBuilder.append(c_inBinary);
            binaryBuilder.append(d_inBinary);
            binaryBuilder.append(s_inBinary);

            // We print out the current set number with the decimal fraction representation of our 17 digit binary.
            System.out.println(choppedInput[0] + " " + p.binaryToDecimalFraction(binaryBuilder.toString()));

            //Clear the binary builder each loop
            binaryBuilder.setLength(0);
            setCounter++;
        }
    }

    // Converts a binary string into an 11-bit representation.
    // We know that input will be 11-bit at most, thus if it already is 11-bit,
    // We do not have to do anything.
    public String convertToElevenBit(String d_inBinary) {
        int lengthOfString = String.valueOf(d_inBinary).length();
        if(lengthOfString < 11) { // If input is smaller than 11-bit
            String temp = "";

            // Builds a temporary string consisting of 0's, which is the size of 11 - lengthOfString.
            for (int i = lengthOfString; i < 11; i++) {
                temp = 0 + temp;
            }

            // We simply add the temp string in front of our binary, as per the assignment, and return the 11-bit binary.
            d_inBinary = temp + d_inBinary;
            return d_inBinary;

        }
        return d_inBinary;
    }

    // Converts the binary number to decimal form with fractions.
    public String binaryToDecimalFraction(String input_inBinary) {
        double result = 0.0;
        String finalResult = "";
        int exponent = 0;

        // If first number is a 0, then it is a positive number (without minus sign in front in the result).
        if (Character.getNumericValue(input_inBinary.charAt(0)) == 0) {

            exponent = -16;

            // We start at the very end of the binary number and move backwards until the second position,
            // as the first position will not be a 1. We also increment the exponent with +1 each loop, this is how
            // we acquire the decimal fraction representation.
            for (int i = input_inBinary.length()-1; i > 0; i--) {
                if (Character.getNumericValue(input_inBinary.charAt(i)) == 1) {
                    result += Math.pow(2, exponent);
                }
                exponent += 1;
            }

            // Correctly format the result.
            finalResult = String.format(Locale.US,"%.16f", result);
            return stripAndPrint(finalResult, true); // Correctly strip the result as per the instructions, and return.

        } else { // Else it is negative.

            boolean first = true;
            // We take the "opposite" approach if the number is negative. We start from the beginning,
            // moving forwards with the initial exponent being 0 - incrementing it with +1 at each loop.
            for (int i = 0; i <= input_inBinary.length()-1; i++) { // all digits can be 1, thus it is supposed to traverse all digits.
                if (Character.getNumericValue(input_inBinary.charAt(i)) == 1) {
                    if (first) {
                        result = Math.pow(2, -exponent);
                        first = false;
                    } else {
                        result -= Math.pow(2, -exponent);
                    }
                }
                exponent += 1;
            }

            // Correctly format the result and make it a negative!
            finalResult = String.format(Locale.US,"%.16f", -result);
            return stripAndPrint(finalResult, false); // Correctly strip the result as per the instructions, and return.

        }
    }

    // Method for manually and correctly stripping away any trailing 0's as per the instructions of the assignment.
    public String stripAndPrint(String result, boolean positive) {

        if (positive) {

            for (int i = result.length() - 1; i > 1; i--) {
                if (Character.getNumericValue(result.charAt(i)) != 0) {
                    if (i != result.length() - 1) {
                        result = result.substring(0, i + 1);
                    }
                    break;
                } else {
                    if (i == 2) {
                        result = result.substring(0, i + 1);
                        break;
                    }
                }
            }
        } else {
            for (int i = result.length() - 1; i > 2; i--) {
                if (Character.getNumericValue(result.charAt(i)) != 0) {
                    if (i != result.length() - 1) {
                        result = result.substring(0, i + 1);
                    }
                    break;
                } else {
                    if (i == 3) {
                        result = result.substring(0, i + 1);
                        break;
                    }
                }
            }
        }
            return result;
    }

    public void initHashMap(HashMap<String, String> mapOfAlphabet) {
        mapOfAlphabet.put("P", "00000");
        mapOfAlphabet.put("Q", "00001");
        mapOfAlphabet.put("W", "00010");
        mapOfAlphabet.put("E", "00011");
        mapOfAlphabet.put("R", "00100");
        mapOfAlphabet.put("T", "00101");
        mapOfAlphabet.put("Y", "00110");
        mapOfAlphabet.put("U", "00111");
        mapOfAlphabet.put("I", "01000");
        mapOfAlphabet.put("O", "01001");
        mapOfAlphabet.put("J", "01010");
        mapOfAlphabet.put("#", "01011");
        mapOfAlphabet.put("S", "01100");
        mapOfAlphabet.put("Z", "01101");
        mapOfAlphabet.put("K", "01110");
        mapOfAlphabet.put("*", "01111");
        mapOfAlphabet.put("?", "10000");
        mapOfAlphabet.put("F", "10001");
        mapOfAlphabet.put("@", "10010");
        mapOfAlphabet.put("D", "10011");
        mapOfAlphabet.put("!", "10100");
        mapOfAlphabet.put("H", "10101");
        mapOfAlphabet.put("N", "10110");
        mapOfAlphabet.put("M", "10111");
        mapOfAlphabet.put("&", "11000");
        mapOfAlphabet.put("L", "11001");
        mapOfAlphabet.put("X", "11010");
        mapOfAlphabet.put("G", "11011");
        mapOfAlphabet.put("A", "11100");
        mapOfAlphabet.put("B", "11101");
        mapOfAlphabet.put("C", "11110");
        mapOfAlphabet.put("V", "11111");
    }
}
