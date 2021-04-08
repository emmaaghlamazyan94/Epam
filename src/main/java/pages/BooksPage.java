package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.ArrayList;
import java.util.List;

public class BooksPage extends BasePage{

    @FindBy(xpath = "//div[@class='a-row a-size-base a-color-secondary']//a[@class='a-size-base a-link-normal']")
    private List<WebElement> authorNameToClick;

    @FindBy(xpath = "//div[@class='a-section a-spacing-none']//div[@class='a-row a-size-base a-color-secondary']")
    private List<WebElement> authors;

    @FindBy(id = "formatSelectorHeader")
    private WebElement actualAuthorName;

    private By books = By.xpath("//div[@class='s-include-content-margin s-border-bottom s-latency-cf-section']");

    public BooksPage(WebDriver driver) {
        super(driver);
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

    public List<String> authorsList() {
        List<String> authorListText = new ArrayList<>();
        for (WebElement author : authors) {
            authorListText.add(author.getText().toLowerCase());
        }
        return authorListText;
    }

    public void waitUntilPageLoads() {
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan
                (books, 0));
    }
}