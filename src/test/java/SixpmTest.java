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
import java.util.concurrent.TimeUnit;

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
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    public void addProductToShoppingBagTest() {
        WebElement accessories = driver.findElement(By.xpath("//div[@class='eb-z']//a[@href='/c/accessories']"));
        actions = new Actions(driver);
        actions.moveToElement(accessories).build().perform();
        wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOf(accessories));
        WebElement aviators = driver.findElement(By.xpath("//a[text()='Aviators']"));
        actions.moveToElement(aviators);
        actions.click().build().perform();
        random = new Random();
        List<WebElement> aviatorsElements = driver.findElements(By.xpath("//article[@class='py-z ns-z']"));
        WebElement randomProduct = aviatorsElements.get(random.nextInt(100));
        wait.until(ExpectedConditions.visibilityOf(randomProduct));
        randomProduct.click();
        String codeOfRandomProduct = driver.findElement
                (By.xpath("//h1//span[@class='eN-z']")).getText().toLowerCase();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("XM-z")));
        WebElement addToBag = driver.findElement(By.xpath("//button[@type='submit' and text()='Add to Shopping Bag']"));
        addToBag.click();
        WebElement viewBag = driver.findElement(By.xpath("//a[@class='sz-z']"));
        viewBag.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='oh-z']")));
        String actualcodeOfRandomProduct = driver.findElement
                (By.xpath("//div[@class='Wk-z']//span[@class='Xk-z']")).getText().toLowerCase();
        Assert.assertEquals(actualcodeOfRandomProduct, codeOfRandomProduct);
    }

    @AfterMethod
    public void close6pm() {
        actions.moveToElement(driver.findElement
                (By.xpath("//select[@name='quantity']"))).click().build().perform();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@name='quantity']")));
        driver.findElement(By.xpath("//select//option[text()='Remove']")).click();
        driver.quit();
    }
}