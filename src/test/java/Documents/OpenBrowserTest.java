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
                (By.xpath("//p/a[contains(@href,'https://selenium-release.storage.googleapis.com/3.141/selenium-server-standalone-3.141.59.jar')]"));
        String actualVersion = lastVersion.getText();
        Assert.assertEquals(actualVersion, expectedVersion, "Incorrect Selenium Server Version Number");

        WebElement searchBox = driver.findElement(By.name("search"));
        searchBox.click();
        String expectedName = "selenium webdriver";
        searchBox.sendKeys(expectedName + Keys.ENTER);
        String actualname = driver.findElement(By.linkText("Selenium WebDriver")).getText().toLowerCase();
        Assert.assertEquals(actualname, expectedName, "Selenium Webdriver Not Found");

        driver.quit();
    }
}