package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;

public class BooksPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public BooksPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 20);
    }

    public boolean checkAllBooksBySameAuthor(String authorFullName) {
        List<WebElement> authors = driver.findElements
                (By.xpath("//div[@class='sg-col-inner']//div[@class='a-section a-spacing-none']//div[@class='a-row a-size-base a-color-secondary']"));
        boolean checkCondition = false;
        for (WebElement author : authors) {
            if (author.getText().toLowerCase().contains(authorFullName)) {
                checkCondition = true;
            } else {
                return false;
            }
        }
        return checkCondition;
    }

    public void waitUntilPageLoads() {
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan
                (By.xpath("//div[@class='s-include-content-margin s-border-bottom s-latency-cf-section']"), 0));
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath("//span[@class='a-size-medium a-color-base a-text-normal']"), 0));
    }
}