package big_mod_374;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;

public class Main {

    public static void main(String[] args) throws IOException {

        String input;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // For every input, make two extra reads for the other variables + eat blank line.
        while ((input = br.readLine()) != null) { // I guess null is EOI.
            BigInteger b = new BigInteger(input);
            BigInteger p = new BigInteger(br.readLine());
            BigInteger m = new BigInteger(br.readLine());

            BigInteger res = b.modPow(p, m);

            System.out.println(res);

            br.readLine(); // Eat the extra blank line.
        }
    }
}
