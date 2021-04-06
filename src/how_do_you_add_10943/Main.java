package how_do_you_add_10943;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;

/**
 * Binomial Coefficient Matrix and how it works --> https://www.youtube.com/watch?v=GmB0cIY7uMk
 * In relation to Pascals --> https://www.mathwords.com/b/binomial_coefficients_pascal.htm
 * Construct solution based on this.
 */
public class Main {

    // Returns the Binomial Coefficient if N and K.
    // DP with the help of Pascals Triangle - related to BC.
    // n --> Row, k --> Element.
    private BigInteger SolutionWithBinomialCoefficient(int n, int k) {
        BigInteger[] storingArray = new BigInteger[n + 1];
        storingArray[0] = BigInteger.valueOf(1); // First row of pascals triangle --> 1

        for (int i = 0; i <= n; i++) {
            storingArray[i] = BigInteger.valueOf(1);
            for (int j = i - 1; j > 0; j--) {
                storingArray[j] = storingArray[j].add(storingArray[j - 1]);
            }
        }
        return storingArray[k].mod(BigInteger.valueOf(1000000));
    }

    public static void main(String[]args) throws IOException {

        Main main = new Main();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input;

        // While not EOI.
        while (!(input = br.readLine()).equals("0 0")) {
            String[] arrayOfInput = input.split(" ");

            // Transform input according to the "Stars and Bars" method --> https://en.wikipedia.org/wiki/Stars_and_bars_%28combinatorics%29
            int n = (Integer.parseInt(arrayOfInput[0]) + Integer.parseInt(arrayOfInput[1])) - 1;
            int k = Integer.parseInt(arrayOfInput[0]);

            System.out.println(main.SolutionWithBinomialCoefficient(n, k));
        }
    }
}
