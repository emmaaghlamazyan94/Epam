package pages;

import org.openqa.selenium.By;
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
    WebElement selectSection;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 10);
        PageFactory.initElements(driver, this);
    }

    public void searchAuthorName(String authorFullName, String section) {
        Select select = new Select(selectSection);
        select.selectByVisibleText(section);
        waitUntilPageLoads();
        searchEditBox.sendKeys(authorFullName + Keys.ENTER);
    }

    public void waitUntilPageLoads() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-belt")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-search")));
    }
}