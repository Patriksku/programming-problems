package wine_trading_in_gergovia_11054;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[]args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 0 is EOI, and while this is not the case, we can continue.
        while(!br.readLine().equals("0")) {
            long cost = 0;

            // The list of buying/selling needs of each house/inhabitant.
            String wantInput = br.readLine();

            // Convert into array of string.
            String[] arrayOfWantInputs = wantInput.split(" ");
            long[] wants = new long[arrayOfWantInputs.length]; // Create long of equal size.

            // Fill the long wants-array with all the wants of the inhabitants.
            for (int i = 0; i < wants.length; i++) {
                wants[i] = Integer.parseInt(arrayOfWantInputs[i]);
            }

            // Loop through this long wants-array.
            for (int i = 0; i < wants.length; i++) {
                // If we are at the end of the array, break.
                if (i == (wants.length - 1)) {
                    break;
                }

                // The current sum of selling/buying wants of current neighbor and neighbor in front.
                long sumOfWantsOfNeighbours = wants[i] + wants[(i + 1)];

                if (wants[i] <= 0) { // If current want is <= 0
                    if (wants[(i + 1)] <= 0) { // If neighbor is <= 0
                        wants[(i + 1)] += wants[i]; // Move the cost of the current want to the neighbor.
                    } else { // If neighbor is a positive number > 0
                        wants[(i + 1)] = sumOfWantsOfNeighbours; // Merging/Moving the current want to the neighbor
                    }
                    cost += Math.abs(wants[i]); // It will cost the current want, to "move" this to the neighbor. + Making any potential negative numbers positive.

                } else { // If current want is > 0
                    wants[(i + 1)] = sumOfWantsOfNeighbours; // Merging/Moving the current want to the neighbor
                    cost += wants[i];
                }
            }
            System.out.println(cost);
        }
    }
}
