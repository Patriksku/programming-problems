package undirectional_tsp_116;

import java.util.*;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextInt()) {

            int row = scanner.nextInt();
            int col = scanner.nextInt();

            int lastRow = row - 1;
            int lastCol = col - 1;

            int up;
            int down;
            int right;
            int[] row_level = new int[3];

            int minimumCostOfPreviousTraverse;

            // Matrix reflecting the real values from input.
            int[][] matrix = new int[row][col];

            // minMatrix holds the minimum weight of current element [row][col]
            int[][] minMatrix = new int[row][col];

            // bwTraverse holds the row that we came from, to get to current element [row][col], as we are initially traversing backwards.
            int[][] bwTraverse = new int[row][col];

            // Translate input to matrix + makes sure that that every element is correctly translated
            // --> That is, as long as the elements are equal to row * col, it does NOT matter if they are written
            // on separate rows or not!
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    matrix[i][j] = scanner.nextInt();

                    // Necessary preparations for starting at the end, DP (lexicographical order).
                    // We know the cost for the last column is just the value of the elements in the matrix.
                    // Also fill in bwTraverse.
                    if (j == lastCol) {
                        minMatrix[i][j] = matrix[i][j];
                        bwTraverse[i][j] = -1;
                    } else {
                        // Initialize entire minMatrix with infinity (except for last row).
                        minMatrix[i][j] = Integer.MAX_VALUE;
                    }
                }
            }

            /*// Print the the Matrix
            for (int[] row_matrix : matrix) {
                System.out.println(Arrays.toString(row_matrix));
            }*/

            // Now when we have the necessary base, we keep on calculating the best path, from lastCol - 1.
            // We start at the upper corner to the right, moving downwards, and when we are done with the column,
            // We move a step to the left and repeat.
            for (int j = lastCol - 1; j >= 0; j--) { // j --> col
                for (int i = 0; i < row; i++) { // i --> row

                    // Setting current move in bwTraverse as infinity.
                    bwTraverse[i][j] = Integer.MAX_VALUE;

                    // Calculating up, down and right moves from current position --> These can be used together to map values correctly in 2D matrix.
                    if (i == 0) { // Upwards-move.
                        up = lastRow;
                    } else {
                        up = (i - 1);
                    }

                    if (i == lastRow) { // Downwards-move.
                        down = 0;
                    } else { // Right-move.
                        down = (i + 1);
                    }
                    right = (j + 1);

                    // Set row_level-values + sort
                    row_level[0] = up;
                    row_level[1] = i;
                    row_level[2] = down;
                    Arrays.sort(row_level);

                    // Calculating the previous move with the lowest cost.
                    int tempCompareOfCost = Math.min(minMatrix[up][right], minMatrix[i][right]);
                    minimumCostOfPreviousTraverse = Math.min(tempCompareOfCost, minMatrix[down][right]);
                    // Storing the lowest cost move with its cost at current position in minMatrix.
                    minMatrix[i][j] = (matrix[i][j] + minimumCostOfPreviousTraverse);

                    // Loop through possible row_levels (without right move)
                    for (int current = 0; current < row_level.length; current++) {

                        // If current position in bwTraverse equals infinity (first "outer" iteration always),
                        // Or if this possible move ( + right move, = previous move) is the lowest cost alternative
                        // && smaller than another possible move, set this move to be the current element in bwTraverse.
                        // Double check needed if two moves have same value --> This gives us lowest cost move as bwTraverse[i][j] from this position.
                        // bwTraverse saves row numbers for reverse-tracking.
                        if (bwTraverse[i][j] == Integer.MAX_VALUE || minMatrix[row_level[current]][right] ==
                                minimumCostOfPreviousTraverse && minMatrix[row_level[current]][right] < minMatrix[bwTraverse[i][j]] [right]) {
                            bwTraverse[i][j] = row_level[current];
                        }
                    }
                }
            }

            // We have to find the row at column 0 that has the lowest value (which is the total cost of following this path backwards).
            int minTotalCost = Integer.MAX_VALUE;
            int startingRowPos = 0;

            for (int i = 0; i < row; i++) {
                if (minMatrix[i][0] < minTotalCost) {
                    minTotalCost = minMatrix[i][0]; //
                    startingRowPos = i;
                }
            }

            // Then we follow this path backwards and print the result. If two or more rows have the same value, we have to pick the lower
            // row level --> This is how we get lexicographical ordering.
            StringBuilder bestPath = new StringBuilder();
            int bestRow = startingRowPos;
            bestPath.append(bestRow + 1);

            for (int j = 0; j < col; j++) {
                if (j != lastCol) {
                    bestRow = bwTraverse[bestRow][j];
                    bestPath.append(" ").append(bestRow + 1);
                }
            }

            String finalPath = bestPath.toString();
            System.out.println(finalPath);
            System.out.println(minTotalCost);
        }
    }
}