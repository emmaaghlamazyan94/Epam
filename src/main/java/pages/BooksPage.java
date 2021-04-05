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
    private List<WebElement> authorNameToClick;

    @FindBy(xpath = "//div[@class='a-section a-spacing-none']//div[@class='a-row a-size-base a-color-secondary']")
    private List<WebElement> authors;

    @FindBy(id = "formatSelectorHeader")
    private WebElement actualAuthorName;

    private By books = By.xpath("//div[@class='s-include-content-margin s-border-bottom s-latency-cf-section']");

    public BooksPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 20);
        PageFactory.initElements(driver, this);
    }

    public String getActualText() {
        return actualAuthorName.getText().toLowerCase();
    }

    public void clickOnSearchResult() {
        for (WebElement authorLink : authorNameToClick) {
            wait.until(ExpectedConditions.elementToBeClickable(authorLink));
            authorLink.click();
            break;
        }
    }

    public boolean checkAllBooksBySameAuthor(String authorFullName) {
        List<WebElement> authorsList = authors;
        for (WebElement author : authorsList) {
            if (!author.getText().toLowerCase().contains(authorFullName)) {
                return false;
            }
        }
        return true;
    }

    public void waitUntilPageLoads() {
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan
                (books, 0));
    }
}