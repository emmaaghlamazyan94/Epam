import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Random;

public class SixpmTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private Actions actions;
    private Random random;

    @BeforeMethod
    private void open6pm() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("https://www.6pm.com/");
    }

    @Test
    public void addProductToShoppingBagTest() {
        wait = new WebDriverWait(driver, 10);
        actions = new Actions(driver);
        random = new Random();
        WebElement accessories = driver.findElement(By.xpath("//div[@class='eb-z']//a[@href='/c/accessories']"));
        actions.moveToElement(accessories).build().perform();
        wait.until(ExpectedConditions.visibilityOf(accessories));
        WebElement aviators = driver.findElement(By.xpath("//a[text()='Aviators']"));
        actions.moveToElement(aviators);
        wait.until(ExpectedConditions.visibilityOf(aviators));
        aviators.click();
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath("//article[@class='py-z ns-z']"), 99));
        List<WebElement> aviatorsElements = driver.findElements(By.xpath("//article[@class='py-z ns-z']"));
        WebElement randomProduct = aviatorsElements.get(random.nextInt(100));
        wait.until(ExpectedConditions.visibilityOf(randomProduct));
        randomProduct.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("LR-z")));
        String codeOfProduct = driver.findElement
                (By.xpath("//h1//span[@class='eN-z']")).getText().toLowerCase();
        String brandOfProduct = driver.findElement(By.xpath("//a//span[@itemprop='name']")).getText().toLowerCase();
        String expectedPrice = driver.findElement(By.xpath("//div[@class='Ib-z']//span[@aria-hidden]")).getText().toLowerCase();
        WebElement addToBag = driver.findElement(By.xpath("//button[@type='submit' and text()='Add to Shopping Bag']"));
        addToBag.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@class='sz-z']")));
        WebElement viewBag = driver.findElement(By.xpath("//a[@class='sz-z']"));
        viewBag.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='oh-z']")));
        String actualcodeOfProduct = driver.findElement
                (By.xpath("//div[@class='Wk-z']//span[@class='Xk-z']")).getText().toLowerCase();
        String actualBrandOfProduct = driver.findElement(By.xpath("//div[@class='Wk-z']//span[1]")).getText().toLowerCase();
        String actualPrice = driver.findElement(By.xpath("//div[@class='rh-z']//em")).getText().toLowerCase();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='oh-z']")));
        Assert.assertEquals(actualcodeOfProduct, codeOfProduct,
                "Shopping bag is empty:product is not added to shopping bag");
        Assert.assertEquals(actualBrandOfProduct, brandOfProduct,
                "Product of " + brandOfProduct + " brand is not found");
        Assert.assertEquals(actualPrice, expectedPrice,
                "Price of product in shopping bag does not match the price of product you want to add");
    }

    @AfterMethod
    public void close6pm() {
        driver.findElement(By.xpath("//select[@name='quantity']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@name='quantity']")));
        driver.findElement(By.xpath("//select//option[text()='Remove']")).click();
        driver.quit();
    }
}