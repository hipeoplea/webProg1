public class Checker {

    public static boolean check(double x, double y, double r) {
        return checkSquare(x, y, r) || checkCircle(x, y, r) || checkTriangle(x, y, r);
    }


    private static boolean checkSquare(double x, double y, double r) {

        return (y <= r && y >= 0) && (x >= (-r) && x <= 0);
    }

    private static boolean checkCircle(double x, double y, double r) {
        return x <= 0 && y <= 0 && x * x + y * y < r * r;
    }

    private static boolean checkTriangle(double x, double y, double r) {
        if (!(x >= 0 && x <= (r / 2)) || !(y <= 0 && y >= -r)) {
            return false;
        }

        double xLength = (r / 2) - x;
        double tan = y / xLength;

        return tan <= 1;
    }

}
