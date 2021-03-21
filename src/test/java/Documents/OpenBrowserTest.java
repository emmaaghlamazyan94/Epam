package Documents;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class OpenBrowserTest {
    @Test
    public void seleniumVersionTest() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "src/main/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.selenium.dev/");
        Thread.sleep(3000);

        WebElement download = driver.findElement(By.linkText("Downloads"));
        download.click();
        String expectedVersion = "3.141.59";
        Thread.sleep(3000);
        WebElement lastVersion = driver.findElement
                (By.xpath("//p[text()='Latest stable version ']//a[text()='3.141.59']"));
        String actualVersion = lastVersion.getText();
        Assert.assertEquals(actualVersion, expectedVersion, "Incorrect Selenium Server Version Number");

        WebElement searchBox = driver.findElement(By.name("search"));
        searchBox.click();
        String expectedName = "selenium webdriver";
        searchBox.sendKeys(expectedName + Keys.ENTER);
        String actualName = driver.findElement
                (By.xpath("//*[contains(text(),'Selenium WebDriver')]")).getText().toLowerCase();
        Assert.assertEquals(actualName, expectedName, "No results found for selenium webdriver");

        driver.quit();
    }
}