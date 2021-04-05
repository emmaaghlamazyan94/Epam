package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.ArrayList;
import java.util.List;

public class AuthorPage {
    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(xpath = "//span//span[@class='a-button-text a-declarative']")
    private WebElement sortBy;

    @FindBy(linkText = "Price: Low to High")
    private WebElement lowToHigh;

    @FindBy(xpath = "//div[@class='a-fixed-right-grid-col a-col-left']")
    private WebElement sortedList;

    @FindBy(xpath = "//span[@class='a-size-base-plus a-color-price a-text-bold']")
    private List<WebElement> prices;

    @FindBy(id = "booksBySection")
    private WebElement booksByText;

    public AuthorPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 10);
        PageFactory.initElements(driver, this);
    }

    public void clickToSort() {
        sortBy.click();
        lowToHigh.click();
        wait.until(ExpectedConditions.visibilityOf(sortedList));
    }

    public boolean sortByPrice() {
        ArrayList<Float> priceList = new ArrayList<>();
        for (int i = 0; i < prices.size(); i++) {
            priceList.add(Float.parseFloat(prices.get(i).getText().replaceAll("\\$", "")));
        }
        return isListSorted(priceList);
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
        wait.until(ExpectedConditions.visibilityOf(booksByText));
    }
}