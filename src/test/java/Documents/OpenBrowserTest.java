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

        WebElement element = driver.findElement(By.linkText("Downloads"));
        element.click();
        String expectedVersion = "3.141.59";
        Thread.sleep(3000);
        WebElement lastVersion = driver.findElement(By.linkText("3.141.59"));
        String actualVersion = lastVersion.getText();

        WebElement element1 = driver.findElement(By.name("search"));
        element1.click();
        String expectedName = "selenium webdriver";
        element1.sendKeys(expectedName + Keys.ENTER);

        Assert.assertEquals(actualVersion, expectedVersion, "Check version");
        Assert.assertTrue(driver.getPageSource().contains("selenium webdriver"), "Search selenium webdriver");

        driver.quit();
    }
}


