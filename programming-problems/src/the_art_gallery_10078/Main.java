package the_art_gallery_10078;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * This problem is solved by checking if the polygon is convex.
 * If the polygon is convex, then all the turns (left or right), around the polygon will be the same.
 * This means that there is no critical point. If the polygon is not convex, then there is a critical point.
 */
public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            int n = Integer.parseInt(br.readLine());

            // If EOI condition is true, break.
            if (n == 0) {
                break;
            }

            // Handle the input
            String[] pureInput = new String[n];
            for (int i = 0; i < n; i++) {
                pureInput[i] = br.readLine();
            }

            float firstX = 0;
            float firstY = 0;
            float secondX = 0;
            float secondY = 0;

            float side = 0; // Can be < 0 || > 0.
            String result = "No";

            // Loop through all points.
            // Comparing current line with the next point using a formula.
            // Current line consists of current coordinates + next coordinates,
            // and the next point consists of the coordinates that come after the ending coordinates of the current line.
            for (int i = 0; i < n; i++) {
                String[] coordinates = pureInput[i].split(" ");
                float x1 = Float.parseFloat(coordinates[0]);
                float y1 = Float.parseFloat(coordinates[1]);

                // If all coordinates needed for the line and point can be calculated with the slots we have left in the array
                // from our position.
                if (i < (n - 2)) {
                    String[] nextCoordinates = pureInput[i + 1].split(" ");
                    String[] pointCoordinates = pureInput[i + 2].split(" ");

                    float x2 = Float.parseFloat(nextCoordinates[0]);
                    float y2 = Float.parseFloat(nextCoordinates[1]);
                    float pointX = Float.parseFloat(pointCoordinates[0]);
                    float pointY = Float.parseFloat(pointCoordinates[1]);

                    // We store the coordinates for the first lines, as these will be needed for the lines, and/or the point
                    // once we start coming near the end of the array.
                    if (i == 0) {
                        firstX = x1;
                        firstY = y1;
                        secondX = x2;
                        secondY = y2;

                        // Side is calculated once. All other sides of this polygon is compared to this side.
                        side = ((pointX - x1) * (y2 - y1) - (pointY - y1) * (x2 - x1));

                    } else {

                        // Calculating side for each line of the polygon.
                        float tempSide = ((pointX - x1) * (y2 - y1) - (pointY - y1) * (x2 - x1));

                        // If this current side does not correspond to the same argument of side,
                        // that is, having the value of either < 0 || > 0, then we know that this polygon
                        // is not convex.
                        if (side < 0 && tempSide > 0 || side > 0 && tempSide < 0) {
                            result = "Yes";
                            break;
                        }
                    }

                } else if (i == (n - 2)) {
                    String[] nextCoordinates = pureInput[i + 1].split(" ");

                    float x2 = Float.parseFloat(nextCoordinates[0]);
                    float y2 = Float.parseFloat(nextCoordinates[1]);
                    float pointX = firstX;
                    float pointY = firstY;

                    float tempSide = ((pointX - x1) * (y2 - y1) - (pointY - y1) * (x2 - x1));

                    if (side < 0 && tempSide > 0 || side > 0 && tempSide < 0) {
                        result = "Yes";
                        break;
                    }

                } else {

                    float x2 = firstX;
                    float y2 = firstY;
                    float pointX = secondX;
                    float pointY = secondY;

                    float tempSide = ((pointX - x1) * (y2 - y1) - (pointY - y1) * (x2 - x1));

                    if (side < 0 && tempSide > 0 || side > 0 && tempSide < 0) {
                        result = "Yes";
                        break;
                    }
                }
            }

            System.out.println(result);
        }
    }
}
