package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class BooksPage {
    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(xpath = "//div[@class='a-row a-size-base a-color-secondary']//a[@class='a-size-base a-link-normal']")
    private List<WebElement> authorsList;

    @FindBy(id = "formatSelectorHeader")
    private WebElement actualAuthorName;

    @FindBy(xpath = "//div[@class='s-include-content-margin s-border-bottom s-latency-cf-section']")
    private WebElement books;

    public BooksPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 20);
        PageFactory.initElements(driver, this);
    }

    public String getActualText() {
        return actualAuthorName.getText().toLowerCase();
    }

    public void clickOnSearchResult() {
        for (WebElement authorLink : authorsList) {
            wait.until(ExpectedConditions.elementToBeClickable(authorLink));
            authorLink.click();
            break;
        }
    }

    public void waitUntilPageLoads() {
        try {
            wait.until(ExpectedConditions.numberOfElementsToBeMoreThan
                    ((By) books, 0));
        } catch (Exception e) {
            System.out.println("No results found in Books.");
        }
    }
}