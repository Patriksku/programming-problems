package programming_contest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;

public class Water {

    public static void main(String[]args) throws IOException {
        BigInteger[] solutionArray = createArrayOfSolutiuons();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String input;

        while ((input = br.readLine()) != null) {
            int cities = (Integer.parseInt(input)) - 1;

            System.out.println(solutionArray[cities]);
        }
    }

    private static BigInteger[] createArrayOfSolutiuons() {
        BigInteger[] solutions = new BigInteger[100];
        solutions[0] = BigInteger.valueOf(1);
        solutions[1] = BigInteger.valueOf(3);

        for (int i = 2; i < 100; i++) {
            solutions[i] = ((solutions[i - 1].multiply(BigInteger.valueOf(2))).add(solutions[i - 1].subtract(solutions[i - 2])));
        }
        return solutions;
    }
}
