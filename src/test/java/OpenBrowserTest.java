import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class OpenBrowserTest {
    @Test
    public void seleniumVersionTest() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.selenium.dev/");
        Thread.sleep(3000);

        WebElement download = driver.findElement(By.linkText("Downloads"));
        download.click();
        String expectedVersion = "3.141.59";
        Thread.sleep(3000);
        String actualVersion = driver.findElement
                (By.xpath("//p[text()='Latest stable version ']//a")).getText();
        Assert.assertEquals(actualVersion, expectedVersion, "Incorrect Latest Stable Version Number");

        WebElement searchBox = driver.findElement(By.name("search"));
        searchBox.click();
        String expectedName = "selenium webdriver".toLowerCase();
        searchBox.sendKeys(expectedName + Keys.ENTER);
        Thread.sleep(3000);
        List<WebElement> links = driver.findElements(By.xpath("//div[@class = 'gs-title']//a[@target]"));
        boolean checkCondition = false;
        for (WebElement link : links) {
            if (link.getText().toLowerCase().contains(expectedName)) {
                checkCondition = true;
                break;
            }
        }
        Assert.assertTrue(checkCondition, "No results found for " + expectedName);
        driver.quit();
    }
}