package all_in_all_10340;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[]args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input;

        // While input is not EOI.
        while((input = br.readLine()) != null) {

            String result = "No";
            // Divide input into two slots in a String array.
            String[] inputArray = input.split(" ");
            String seq = inputArray[0];
            String formattedSeq = inputArray[1];

            int counter = 0;
            int currentCharOfSeq = 0;

            for (int i = 0; i < formattedSeq.length(); i++) {
                if (seq.charAt(currentCharOfSeq) == formattedSeq.charAt(i)) {
                    counter++;
                    currentCharOfSeq++;
                    if (counter == seq.length()) {
                        result = "Yes";
                        break;
                    }
                }
            }
            System.out.println(result);
        }
    }
}
