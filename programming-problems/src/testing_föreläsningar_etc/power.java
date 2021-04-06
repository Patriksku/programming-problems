package testing_föreläsningar_etc;

public class power {

    static int power(int a, int b) {
        int x;
        int y;

        if (b == 0) {
            return 1;
        }

        if (!(b % 2 == 0)) {
            x = a;
        } else {
            x = 1;
        }

        y = power(a,b/2);
        return y*y*x;
    }

    public static void main(String[] args) {
        System.out.println(power(2, 3));
    }
}
