
public class Calculator {


    public int sum(int a, int b) {
        return a + b;
    }

    public double sub(double a, double b) {
        return a - b;
    }

    public Number div(double a, double b) {
        if (b == 0) {
            throw new ArithmeticException();
        }
        return a / b;
    }

    public double mul(double a, double b) {
        return a * b;
    }

}
