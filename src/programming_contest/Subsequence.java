package programming_contest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Subsequence {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String prepare;

        while ((prepare = br.readLine()) != null) {
            String[] prepareParts = prepare.split(" ");

            int N = Integer.parseInt(prepareParts[0]);
            int S = Integer.parseInt(prepareParts[1]);

            String arrayFromInput = br.readLine();
            String[] arrayParts = arrayFromInput.split(" ");
            int[] array = new int[N];

            for (int i = 0; i < N; i++) {
                array[i] = Integer.parseInt(arrayParts[i]);
            }

            int minimum = 0;
            boolean first = true;

            for (int i = 0; i < array.length; i++) {
                int tempResult = 0;
                int counter = 0;

                for (int j = i; j < array.length; j++) {
                    tempResult += array[j];
                    counter++;
                    if (tempResult >= S && first) {
                        minimum = counter;
                        first = false;
                        break;
                    } else {
                        if (tempResult >= S) {
                            if (counter < minimum) {
                                minimum = counter;
                            }
                            break;
                        }
                    }
                }
            }

            System.out.println(minimum);
        }
    }
}