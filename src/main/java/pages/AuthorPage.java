package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.ArrayList;
import java.util.List;

public class AuthorPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public AuthorPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 10);
    }

    public void clickOnSearchResult() {
        List<WebElement> authors = driver.findElements
                (By.xpath("//div[@class='a-row a-size-base a-color-secondary']//a[@class='a-size-base a-link-normal']"));
        for (WebElement authorLink : authors) {
            wait.until(ExpectedConditions.elementToBeClickable(authorLink));
            authorLink.click();
            break;
        }
    }

    public void clickToSort() {
        WebElement sortBy = driver.findElement(By.xpath("//span//span[@class='a-button-text a-declarative']"));
        sortBy.click();
        WebElement lowToHigh = driver.findElement(By.id("dropdown1_1"));
        lowToHigh.click();
    }

    public boolean sortByPrice() {
        List<WebElement> prices = driver.findElements(By.xpath("//span[@class='a-price']//span[@aria-hidden='true']"));
        ArrayList<Float> priceList = new ArrayList();
        for (int i = 0; i < prices.size(); i = i++) {
            priceList.add(Float.parseFloat(prices.get(i).getText()));
        }
        if (isListSorted(priceList)) {
            return true;
        }
        return false;
    }

    Boolean isListSorted(ArrayList<Float> data) {
        for (int i = 0; i < data.size() - 1; i++) {
            if (data.get(i) > data.get(i + 1)) {
                return false;
            }
        }
        return true;
    }

    public void waitUntilPageLoads() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("booksBySection")));
    }
}