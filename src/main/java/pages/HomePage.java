package pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {
    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(id = "twotabsearchtextbox")
    private WebElement searchEditBox;

    @FindBy(name = "url")
    private WebElement selectSection;

    @FindBy(id = "nav-global-location-slot")
    private WebElement actualDeliveryCountry;

    @FindBy(id = "nav-belt")
    private WebElement header;

    @FindBy(id = "nav-search")
    private WebElement searchSection;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 10);
        PageFactory.initElements(driver, this);
    }

    public String getActualText() {
        return actualDeliveryCountry.getText().toLowerCase().replaceAll("\\s+", " ");
    }

    public void searchAuthorName(String authorFullName, String section) {
        Select select = new Select(selectSection);
        select.selectByVisibleText(section);
        waitUntilPageLoads();
        searchEditBox.sendKeys(authorFullName + Keys.ENTER);
    }

    public void waitUntilPageLoads() {
        wait.until(ExpectedConditions.visibilityOf(header));
        wait.until(ExpectedConditions.visibilityOf(searchSection));
    }
}