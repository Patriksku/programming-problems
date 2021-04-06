package sunny_mountains_920;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Locale;

/**
 * Calculates the total length of sunny mountain sides by starting at the far right, and going through all the coordinates
 * to the left. Once a peak is reached, look for another higher peak. If a higher peak is found, draw a horizontal line
 * from the previous peak that crosses the path of the newly formed peak with its base. Calculate the coordinate of this point,
 * and calculate the distance from this point to the peak.
 *
 * Resources:
 * - y = mx + b: https://www.mathsisfun.com/equation_of_line.html
 * - y = mx + b calculator from 2 coordinates: https://www.mathsisfun.com/straight-line-graph-calculate.html
 * - Find the line passing two coordinates in Java: https://www.geeksforgeeks.org/program-find-line-passing-2-points/
 * - Transforming standard form to slope-intercept form: http://www.howe-two.com/mathematicat/standardslope.html
 * - Calculate intersection of two lines: https://www.baeldung.com/java-intersection-of-two-lines
 */
public class Main {

    static class Coordinate {
        double x;
        double y;

        Coordinate(double x, double y) {
            this.x = x;
            this.y = y;
        }

        double getX() {
            return x;
        }

        double getY() {
            return y;
        }
    }

    // y = mx + b
    static class Line {
        double m;
        double b;

        Line(double m, double b) {
            this.m = m;
            this.b = b;
        }
    }

    // Calculating m and b in the "y = mx + b" for the line between the two specified coordinates.
    // http://www.howe-two.com/mathematicat/standardslope.html
    static Line getLine(Coordinate part1, Coordinate part2) {

        // First standard form, Ax + By = C
        double a = part2.getY() - part1.getY();
        double B = part1.getX() - part2.getX();
        double c = a * (part1.getX()) + B * (part1.getY());

        /*System.out.println(a + "x " + B + "y " + "= " + c);*/
        // Now, begin transforming to Slope-Intercept Form.
        a = -a; // first, subtract

        a = a/B;
        c = c/B;
        B = B/B;

        double m = a;
        double b = c;

        return new Line(m, b);
    }

    // Returns the horizontal line from the specified coordinate.
    static Line getHorizontalLine(Coordinate coordinate) {
        return new Line(0, coordinate.getY());
    }

    static Coordinate getIntersectingCoordinate(Line horizontalLine, Line fullMountainSide) {
        double x = (fullMountainSide.b - horizontalLine.b) / (horizontalLine.m - fullMountainSide.m);
        double y = horizontalLine.m * x + horizontalLine.b;

        return new Coordinate(x, y);
    }

    static double calcCoordinateDistance(Coordinate pair1, Coordinate pair2) {
        double x1 = pair1.x;
        double x2 = pair2.x;
        double y1 = pair1.y;
        double y2 = pair2.y;

        return Math.sqrt((x2-x1)*(x2-x1) + (y2-y1)*(y2-y1));
    }


    public static void main(String[]args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        boolean EOI = false;

        int testCaseCounter = 0;
        int testCases = Integer.parseInt(br.readLine());


        while (!EOI) {

            int coordinatePairs = Integer.parseInt(br.readLine());

            ArrayList<Coordinate> coordinates = new ArrayList<Coordinate>();

            // Loop for as many coordinates that will follow in the input.
            for (int i = 0; i < coordinatePairs; i++) {
                if (i == coordinatePairs - 1) { // When we reach the last coordinate, also increment testCaseCounter, which is used for turning EOI true.
                    testCaseCounter++;
                }

                String[] currentPair = br.readLine().split(" ");
                coordinates.add(new Coordinate(Double.parseDouble(currentPair[0]), Double.parseDouble(currentPair[1]))); // Collect each pair of coordinates.
            }

            // Sort the array of coordinates in decreasing order of x-coordinates, meaning, we start at the far right end of the mountains!
            coordinates.sort(Comparator.comparingDouble(Coordinate::getX).reversed());

            Coordinate tempHighestTop = null;
            double resultingDistance = 0;


            // Looping through all the coordinates and calculating the total length of sunny mountainsides.
            for (int pair = 0; pair < coordinates.size(); pair++) {
                Coordinate thisCoordinate = coordinates.get(pair);

                // Put first coordinate as tempHighest.
                if (pair == 0) {
                    tempHighestTop = thisCoordinate;

                } else {

                    if (thisCoordinate.getY() == tempHighestTop.getY()) { // If current coordinate has the exact same Y-coordinate.
                        tempHighestTop = thisCoordinate;

                    } else if (thisCoordinate.getY() > tempHighestTop.getY()) { // If current coordinate has higher Y-coordinate.
                        Line horizontalLine = getHorizontalLine(tempHighestTop); // Create horizontal line from previously highest top.
                        Line fullMountainSide = getLine(thisCoordinate, coordinates.get(pair - 1)); // Create line between the new highest top and its base.


                        // Get coordinates of the point which intersects between the horizontal line and the newly discovered mountainside.
                        Coordinate intersectingLineCoordinate = getIntersectingCoordinate(horizontalLine, fullMountainSide);

                        resultingDistance += calcCoordinateDistance(thisCoordinate, intersectingLineCoordinate); // Add the distance of the sunny mountainside to the result.

                        tempHighestTop = thisCoordinate; // Finally, set the newly highest discovered top as highest.

                    }
                }
            }

            DecimalFormat df = new DecimalFormat("#.##", DecimalFormatSymbols.getInstance(Locale.US));
            resultingDistance = Double.parseDouble(df.format(resultingDistance));
            System.out.println(resultingDistance);

            if (testCaseCounter == testCases) {
                EOI = true;
            }
        }
    }
}
