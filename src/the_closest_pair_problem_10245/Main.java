package the_closest_pair_problem_10245;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    private static final double EPS = 1e-9; // 10^9

    // Inner static class for representing the point.
    public static class PT {
        double x, y;

        public PT(double xx, double yy) {
            x = xx;
            y = yy;
        }

        public double dist(PT pt) {
            double dx = x - pt.x, dy = y - pt.y;
            return Math.sqrt(dx * dx + dy * dy);
        }
    }

    // Sorts points by X coordinate
    private static class X_Sort implements Comparator<PT> {
        @Override
        public int compare(PT pt1, PT pt2) {
            if (Math.abs(pt1.x - pt2.x) < EPS) return 0;
            return (pt1.x < pt2.x) ? -1 : +1;
        }
    }

    // Sorts points by Y coordinate first then X coordinate
    private static class YX_Sort implements Comparator<PT> {
        @Override
        public int compare(PT pt1, PT pt2) {
            if (Math.abs(pt1.y - pt2.y) < EPS) {
                if (Math.abs(pt1.x - pt2.x) < EPS) return 0;
                return (pt1.x < pt2.x) ? -1 : +1;
            }
            return (pt1.y < pt2.y) ? -1 : +1;
        }
    }

    // Finds the closest pair of points in a set of points
    public static PT[] closestPair(PT[] points) {

        if (points == null || points.length < 2) return new PT[] {};

        final int n = points.length;
        int xQueueFront = 0, xQueueBack = 0;

        // Sort all points by x-coordinate
        Arrays.sort(points, new X_Sort());
        TreeSet<PT> yWorkingSet = new TreeSet<>(new YX_Sort()); // Binary search tree sorted on x and y.

        PT pt1 = null, pt2 = null;
        double d = Double.POSITIVE_INFINITY; // Shortest distance thus far.

        for (int i = 0; i < n; i++) {

            PT nextPoint = points[i];

            // Remove all points (from both sets) where the distance to
            // the new point is greater than d (the smallest window size yet)
            while (xQueueFront != xQueueBack && nextPoint.x - points[xQueueFront].x > d) {
                PT pt = points[xQueueFront++];
                yWorkingSet.remove(pt);
            }

            // Look at all the points in our working set with a y-coordinate
            // above nextPoint.y but within a window of nextPoint.y + d
            double upperBound = nextPoint.y + d;
            PT next = yWorkingSet.higher(nextPoint);
            while (next != null && next.y <= upperBound) {
                double dist = nextPoint.dist(next);
                if (dist < d) {
                    pt1 = nextPoint;
                    pt2 = next;
                    d = dist;
                }
                next = yWorkingSet.higher(next);
            }

            // Look at all the points in our working set with a y-coordinate
            // below nextPoint.y but within a window of nextPoint.y - d
            double lowerBound = nextPoint.y - d;
            next = yWorkingSet.lower(nextPoint);
            while (next != null && next.y > lowerBound) {
                double dist = nextPoint.dist(next);
                if (dist < d) {
                    pt1 = nextPoint;
                    pt2 = next;
                    d = dist;
                }
                next = yWorkingSet.lower(next);
            }

            // Duplicate/stacked points --> Distance 0.
            // We break and return these points as the closest pair.
            if (yWorkingSet.contains(nextPoint)) {
                pt1 = pt2 = nextPoint;
                d = 0;
                break;
            }

            // Add the next point to the working set
            yWorkingSet.add(nextPoint);
            xQueueBack++;
        }

        return new PT[] {pt1, pt2};
    }

    // Method for calculating the distances between two points.
    public static double distanceBetween(PT p1, PT p2) {
        double x1 = p1.x;
        double y1 = p1.y;
        double x2 = p2.x;
        double y2 = p2.y;

        double distance = Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));

        if (distance >= 10000) {
            return -1; //INFINITY
        } else
            return distance;
    }

    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);
        scanner.useLocale(Locale.US); // :)
        int n;

        while (scanner.hasNextInt()) { // While n is not 0
            n = scanner.nextInt();

            if (n == 0) {
                break;
            }

            PT[] point = new PT[n];

            // As we need to fill all x:s and y:s, this takes care of the problem that occurs
            // if coordinates are not on the same line.
            for (int i = 0; i < n; i++) {
                double x = scanner.nextDouble();
                double y = scanner.nextDouble();

                // Create new point with values specified in the input
                point[i] = new PT(x, y);
            }

            // Run the closest-pair algorithm and store the closest pair in "result".
            PT[] result = closestPair(point);

            if (result.length < 2) {
                System.out.println("INFINITY");
            } else {

                double finalDistance = distanceBetween(result[0], result[1]); // Save the distance between our closest-pair.
                if (finalDistance == -1) {
                    System.out.println("INFINITY");
                } else {
                    // Correct formatting.
                    String stringedResult = String.format(Locale.US,"%.4f", finalDistance);

                    System.out.println(stringedResult);
                }
            }
        }
    }
}