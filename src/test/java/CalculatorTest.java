import org.testng.Assert;
import org.testng.annotations.Test;

public class CalculatorTest {

    Calculator calculator = new Calculator();

    @Test
    public void checkSumTest() {
        int actualSum = calculator.sum(10, 50);
        int expectedSum = 60;
        Assert.assertEquals(actualSum, expectedSum);
    }

    @Test
    public void checkSubTest() {
        double actualResult = calculator.sub(113, 13);
        double expectedResult = 100;
        Assert.assertTrue(actualResult == expectedResult, "Subtraction is false");
    }

    @Test(expectedExceptions = ArithmeticException.class)
    public void checkDivisionNegativeTest() throws ArithmeticException {
        calculator.div(5, 0);
    }

    @Test
    public void checkDivisionTest() {
        double actualDiv = (double) calculator.div(10, 2);
        double expectedDiv = 5;
        Assert.assertEquals(actualDiv, expectedDiv);
    }

    @Test
    public void multiplyTest() {
        double actualMultipl = calculator.mul(5.2, 8.3);
        double expectedMultipl = 43.16;
        Assert.assertEquals(actualMultipl, expectedMultipl, 0.01);
    }
}