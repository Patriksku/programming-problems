package box_of_bricks_5624;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String input;

        int lineNr = 1; // Row of the input.
        int set = 1; // the n number of stacks Bob has built.
        int setNr = 1; // The current set of input that we are on.
        String[] stringOfStacks; // A String of integers representing the height of each stack.
        int[] stacks; // How many stacks there are.

        int totalBoxes = 0;
        int minimumMoves = 0;

        while(true) {

            input = br.readLine();

            // If the row is 1 in the input, we know it is a integer.
            if (lineNr == 1) {
                set = Integer.parseInt(input);
                if (set == 0) { // The EOI condition - If this integer is a 0, then break
                    break;
                }
            } else { // If the row is 2 in the input, we know that we have a sequence of numbers.
                stringOfStacks = input.split(" "); // Remove spaces
                stacks = new int[stringOfStacks.length]; // Create an int array with the same size as the total number of stacks.

                for (int i = 0; i < stringOfStacks.length; i++) {
                    stacks[i] = Integer.parseInt(stringOfStacks[i]); // Transform the contents of the sequence of numbers to the int array.
                }

                // Loop through each box and increment the counter.
                for (int stack : stacks) {
                    totalBoxes += stack;
                }

                // We get what the size will be for all the stacks, if we make them the same size instead.
                int heightOfStacks = totalBoxes / set;

                // Loop through each stack of boxes.
                for (int currentLayerOfBoxes : stacks) {
                    // If this stack is larger than the heightOfStacks-threshold, add the result of the subtraction
                    // To the minimumMoves variable.
                    if (currentLayerOfBoxes > heightOfStacks) {
                        minimumMoves += currentLayerOfBoxes - heightOfStacks;
                    }
                }
                System.out.println("Set #" + setNr++);
                System.out.println("The minimum number of moves is " + minimumMoves + ".");
                System.out.println();

                // Reset necessary values for future inputs.
                set = 1;
                totalBoxes = 0;
                minimumMoves = 0;
            }

            lineNr++;
            // We also reset the lineNr.
            if (lineNr == 3) {
                lineNr = 1;
            }
        }
    }
}