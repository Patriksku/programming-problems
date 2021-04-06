package haiku_review_5321;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws IOException {
        Main c = new Main();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String haikuString;
        char[] vowels = {'a', 'e', 'i', 'o', 'u', 'y'};

        while (true) {
            haikuString = br.readLine();
            if (haikuString.equals("e/o/i")) {
                break;
            }

            boolean prevWasVowel = false;
            boolean thisIsVowel;

            int counter = 0;
            int row = 1;

            // Loop through input.
            for (int i = 0; i < haikuString.length(); i++) {
                thisIsVowel = false;

                // If this character is a vowel and previous character is not, increment the counter.
                // This makes sure that we do not increment the counter for subsequent vowels.
                for (char v : vowels) {
                    if (haikuString.charAt(i) == v) {
                        thisIsVowel = true;
                        if (!prevWasVowel) {
                            counter++;
                        }
                        break;
                    }
                }
                // Updating the status of the previous character for the next iteration.
                // If the character in this current iteration was a vowel, make it true, else make it false.
                prevWasVowel = thisIsVowel;

                // If the character at this position is '/' or if we have reached the end of the line,
                // run the vowel check.
                if (haikuString.charAt(i) == '/' || i == haikuString.length() - 1) { //??
                    if (c.checkRow(row, counter)) {
                        row++;
                        counter = 0;
                    } else {
                        break;
                    }
                }
            }
        }
    }

    // A method for checking if the current row contains the correct amount of vowels.
    // It also prints the result. Each line will pass each case and print "Y" if it is a Haiku.
    public boolean checkRow ( int row, int counter){
        switch (row) {
            case 1:
                if (counter != 5) {
                    System.out.println(row);
                    return false;
                }
                break;
            case 2:
                if (counter != 7) {
                    System.out.println(row);
                    return false;
                }
                break;
            case 3:
                if (counter != 5) {
                    System.out.println(row);
                    return false;
                } else {
                    System.out.println("Y");
                }
                break;
        }
        return true;
    }
}