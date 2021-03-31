package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {
    private WebDriver driver;
    private WebDriverWait wait;
    private By header = By.id("nav-belt");
    private By searchDropdownBox = By.id("nav-search");

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 10);
    }

    public void searchAuthorName(String authorFullName, String section) {
        WebElement dropDownBox = driver.findElement(By.xpath("//div[@id='nav-search-dropdown-card']"));
        dropDownBox.click();
        Select select = new Select(driver.findElement(By.name("url")));
        select.selectByVisibleText(section);
        waitUntilPageLoads();
        WebElement search = driver.findElement(By.id("twotabsearchtextbox"));
        search.sendKeys(authorFullName + Keys.ENTER);
    }

    public void waitUntilPageLoads() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(header));
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchDropdownBox));
    }
}